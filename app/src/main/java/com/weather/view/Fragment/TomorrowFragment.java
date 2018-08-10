package com.weather.view.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weather.R;
import com.weather.view.activities.WeatherActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TomorrowFragment extends Fragment {

    TextView place,DateTime,dayTemp,MrgTemp,NightTemp,humidity,pressure,Cloud,Description;

    public TomorrowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        place = rootView.findViewById(R.id.place);
        DateTime = rootView.findViewById(R.id.DateTime);
        dayTemp = rootView.findViewById(R.id.dayTemp);
        MrgTemp = rootView.findViewById(R.id.MrgTemp);
        NightTemp = rootView.findViewById(R.id.NightTemp);
        humidity = rootView.findViewById(R.id.humidity);
        pressure = rootView.findViewById(R.id.pressure);
        Cloud = rootView.findViewById(R.id.Cloud);
        Description = rootView.findViewById(R.id.Description);

        if(WeatherActivity.arrayList.size()!=0){
            MrgTemp.setText("Morning : "+WeatherActivity.arrayList.get(1).getMrgTemp()+" "+(char) 0x00B0 +"C");
            NightTemp.setText( "Night : "+WeatherActivity.arrayList.get(1).getNightTemp()+" "+(char) 0x00B0 +"C");
            dayTemp.setText("Day : "+WeatherActivity.arrayList.get(1).getDayTemp()+" "+(char) 0x00B0 +"C");
            place.setText("Location : "+WeatherActivity.arrayList.get(1).getAddress());
            humidity.setText("Humiditity : "+WeatherActivity.arrayList.get(1).getHumidity());
            pressure.setText("Pressure : "+WeatherActivity.arrayList.get(1).getPressure());
            Description.setText("Description : "+WeatherActivity.arrayList.get(1).getDescription());
            Cloud.setText("Cloud : "+WeatherActivity.arrayList.get(1).getCloud());
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");
            String formattedDate = df.format(c);
            DateTime.setText("Date : "+formattedDate);

        }


        return rootView;
    }

}