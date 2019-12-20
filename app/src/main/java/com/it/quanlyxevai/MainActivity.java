package com.it.quanlyxevai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    String key;
    String keytoken = null;
    public static final String TAG = LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences pre=getSharedPreferences ("my_token",MODE_PRIVATE);
        keytoken = pre.getString("token","");

        Toast.makeText(MainActivity.this,keytoken,Toast.LENGTH_LONG).show();
    }

}


