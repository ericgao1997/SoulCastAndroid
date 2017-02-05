package com.example.kevinzhang.soulpost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private MapActivity mapActivity;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    Device newdevice = new Device((float)1.11,(float)-1.11,(float)1.11, FirebaseInstanceId.getInstance().getToken());
    Soul newSoul = new Soul("SuccessAndroidSoul","S3keyMissing", 1000000000, -666,66.6,0.6,FirebaseInstanceId.getInstance().getToken());
//    Device newdevice = new Device(1,8, (float) 0.8,"android headers added");
//    Soul newSoul = new Soul("Success:androidSoul","S3keyMissing", 1000000000, -666,66.6,0.6,"android token not available");

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://soulcast.ml")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupFirebase();
//        devicePost();
//        soulPost();
//        deviceUpdate();

        //This is where we want to open the map fragment in SoulCast-Proto.
        Intent mapIntent = new Intent(this, MapActivity.class);
        startActivity(mapIntent);
        //Once this is finished, we want to getnearby.

        //this should maybe happen within the mapactivity, not here.
        //getNearby();
    }

    private void getNearby() {
        SoulpostAPI soulpostAPI = retrofit.create(SoulpostAPI.class);
        Call<Nearby> call = soulpostAPI.getNearby(newdevice.id);
        call.enqueue(new Callback<Nearby>() {
            @Override
            public void onResponse(Call<Nearby> call, Response<Nearby> response) {
                if (response.isSuccessful()){
                    Log.d("Devices nearby:", response.body().devicesNearby + "");
                }else{
                    Log.d("Resp not success:", response.body().devicesNearby + "");
                }

            }

            @Override
            public void onFailure(Call<Nearby> call, Throwable t) {
                //TODO Malform json
                Log.d("Nearby call failed:", t.toString());
            }
        });
    }

    private void setupFirebase() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        //local var
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();
        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("friendly_msg_length", 10L);
        // Apply config settings and default values.
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);
    }

    private void deviceUpdate() {
//        mockChange();
        // prepare call in Retrofit 2.0
        SoulpostAPI soulpostAPI = retrofit.create(SoulpostAPI.class);
        Call<Device> call = soulpostAPI.deviceUpdate(newdevice, newdevice.id);
        call.enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (response.isSuccessful()){
                    //Log.d("Server response success", new Gson().toJson(response));
                    Log.d("ID is :",response.body().id + "");
                    newdevice.id = response.body().id;
                }else {
                    //some kind of server error
                    //Log.d("Server response error",new Gson().toJson(response));
                    Log.d("Server error :",response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Log.d("FAIL SOUL POST", t.toString());
            }
        });
    }

    private void mockChange() {
        newdevice.id = 15;
        newdevice.longitude = (float) -787.7;
        newdevice.latitude = (float) 78.7;
    }

    private void soulPost() {
        // prepare call in Retrofit 2.0
        SoulpostAPI soulpostAPI = retrofit.create(SoulpostAPI.class);
        Call<Soul> call = soulpostAPI.soulPost(newSoul);
        call.enqueue(new Callback<Soul>() {
            @Override
            public void onResponse(Call<Soul> call, Response<Soul> response) {
                Log.d("onSUCCESS", "Success SoulPost");
            }

            @Override
            public void onFailure(Call<Soul> call, Throwable t) {
                Log.d("onFail", "Fail SoulPost");
            }
        });
    }

    private void devicePost()  {
        // prepare call in Retrofit 2.0
        SoulpostAPI soulpostAPI = retrofit.create(SoulpostAPI.class);
        Call<Device> call = soulpostAPI.devicePost(newdevice);
        call.enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (response.isSuccessful()){
                    //Log.d("Server response success", new Gson().toJson(response));
                    Log.d("ID is :",response.body().id + "");
                }else {
                    //some kind of server error
                    Log.d("Server response error",new Gson().toJson(response));
                    Log.d("ERIC Server error :",response.body() + "");
                }

            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Log.d("FAIL SOUL POST", t.toString());
            }
        });
    }


}
