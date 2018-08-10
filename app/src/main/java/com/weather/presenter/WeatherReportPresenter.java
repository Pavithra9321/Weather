package com.weather.presenter;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.weather.model.WeatherModel;
import com.weather.view.mvp_view.WeatherMVP;
import com.weather.view.utill.AppConstants;
import com.weather.view.utill.AppController;
import com.weather.view.utill.ConnectivityReceiver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherReportPresenter implements WeatherMVP.Presenter {

    WeatherMVP.WeatherView mView;
    Context mcontext;
    ArrayList<WeatherModel> list;

    public WeatherReportPresenter(WeatherMVP.WeatherView view, Context context) {
        mView = view;
        mcontext =context;
    }

    @Override
    public void getWeatherReport(String city) {
        if (ConnectivityReceiver.isConnected()) {

            //AppController.getInstance().clearAllQueue();
            final ANRequest request = AndroidNetworking.get(AppConstants.getWeatherReport+ city+AppConstants.WeatherReportMode)
                    .addHeaders("x-api-key",AppConstants.WeatherAPIKey)
                    .setPriority(Priority.HIGH)
                    .build();
            Log.w("Success", "URL : " + request.getUrl());
            request.getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.e("Response :",  response.toString(4));
                        String message =response.getString("message");
                        String location =response.getJSONObject("city").getString("name")+", "+
                                response.getJSONObject("city").getString("country");
                        JSONArray jsonArray = new JSONArray();
                        list = new ArrayList<>();
                        jsonArray = response.getJSONArray("list");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            WeatherModel model = new WeatherModel();
                            model.setMrgTemp(jsonObject.getJSONObject("temp").getString("morn"));
                            model.setNightTemp(jsonObject.getJSONObject("temp").getString("night"));
                            model.setDayTemp(jsonObject.getJSONObject("temp").getString("day"));
                            model.setDescription(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
                            model.setCloud(jsonObject.getString("clouds"));
                            model.setHumidity(jsonObject.getString("humidity"));
                            model.setPressure(jsonObject.getString("pressure"));
                            model.setAddress(location);
                            list.add(model);

                        }
                        mView.showSuccess(message,list);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError error) {
                    final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
                    final Gson gson = builder.create();
                    mView.showError("error", AppConstants.COMMON_EXCEPTION);
                    error.printStackTrace();
                }
            });
        }

    }
}
