package com.weather.view.activities;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.weather.R;
import com.weather.model.WeatherModel;
import com.weather.presenter.WeatherReportPresenter;
import com.weather.view.mvp_view.WeatherMVP;
import com.weather.view.utill.AppConstants;
import com.weather.view.utill.AppPermissions;
import com.weather.view.utill.Progressdialogue;
import com.weather.view.utill.StartLocationAlert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherActivity extends BaseActivity implements WeatherMVP.WeatherView,LocationListener, View.OnClickListener {

    WeatherReportPresenter presenter;
    Context context;
    protected LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    double lat =0.0, lng =0.0;
    private SearchView searchView;
    TextView dateTime,dayNightTemp,Degree,place;
    Button more;
    ImageView share_icon;
    public static ArrayList<WeatherModel> arrayList;
    private AppPermissions mRuntimePermission;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        context = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        dateTime= findViewById(R.id.dateTime);
        dayNightTemp= findViewById(R.id.dayNightTemp);
        Degree= findViewById(R.id.Degree);
        place= findViewById(R.id.place);
        more= findViewById(R.id.more);
        share_icon= findViewById(R.id.share_icon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.search_hint);
        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        presenter = new WeatherReportPresenter(this, this);
        arrayList = new ArrayList<>();

        mRuntimePermission = new AppPermissions(this);
        if (mRuntimePermission.hasPermission(AppConstants.LOCATION_PERMISSIONS)) {
            Log.d("Success","All permission already given");
            try {
                locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
            }
            catch(SecurityException e) {
                e.printStackTrace();
            }
            getLocation();


        } else {
            mRuntimePermission.requestPermission(AppConstants.LOCATION_PERMISSIONS,0);
        }

        more.setOnClickListener(this);
        share_icon.setOnClickListener(this);

    }
    public void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        String city="";

                        if (location != null) {
                            lat=location.getLatitude();
                            lng = location.getLongitude();

                            try {
                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                String address = addresses.get(0).getAddressLine(0);
                                city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();

                                Log.e("city", city);

                                // Log.e("state",state);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Progressdialogue.showDialog(WeatherActivity.this);
                            presenter.getWeatherReport(city);

                        }


                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            getLocation();
        }
    }

    @Override
    public void showError(String status, String message) {

    }

    @Override
    public void showSuccess(String success, ArrayList<WeatherModel> list) {
        if(list.size()!=0){
            arrayList = new ArrayList<>();
            dayNightTemp.setText("Mrg : "+list.get(0).getMrgTemp()+" "+(char) 0x00B0 +"C"+"\t\t"+
                    "Night : "+list.get(0).getNightTemp()+" "+(char) 0x00B0 +"C");
            Degree.setText(list.get(0).getDayTemp()+" "+(char) 0x00B0 +"C");
            place.setText(list.get(0).getAddress());
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");
            String formattedDate = df.format(c);
            dateTime.setText(formattedDate);
            arrayList .addAll(list);
            more.setVisibility(View.VISIBLE);
            share_icon.setVisibility(View.VISIBLE);
            Progressdialogue.dismiss();
        }

    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            lat=location.getLatitude();
            lng = location.getLongitude();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("query", query);
                hideKeyboard();
                Progressdialogue.showDialog(WeatherActivity.this);
                presenter.getWeatherReport(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }

        StartLocationAlert startLocationAlert = new StartLocationAlert(WeatherActivity.this);
    }

    public void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Location : "+arrayList.get(0).getAddress()+"\n"+"weather : "+arrayList.get(0).getDayTemp()+(char) 0x00B0+"\n"+"Description : "+arrayList.get(0).getDescription()+"\n"+"Cloud : "+arrayList.get(0).getCloud());
        sendIntent.setType("text/plain");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(sendIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_icon:
                share();
                break;
            case R.id.more:
                startActivity(new Intent(WeatherActivity.this, WeatherDeatilActivity.class));
                break;
        }
    }
}
