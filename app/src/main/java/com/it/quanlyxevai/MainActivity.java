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
    public void getHttpResponse() throws IOException {
        //tạo url có parameter
        //String url = "https://local.thttextile.com.vn/thtapigate/api/QLSX/XeVai/Get_YeuCauXe";

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://local.thttextile.com.vn/thtapigate/api/QLSX/XeVai/Get_YeuCauXe").newBuilder();
        urlBuilder.addQueryParameter("yeucauxe", "");
        String url = urlBuilder.build().toString();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + keytoken)
                .build();

//        Response response = client.newCall(request).execute();
//        Log.e(TAG, response.body().string());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                Log.e(TAG, mMessage); //đã thành công: thấy log ghi json string là các yêu cầu về xe
                //Dữ liệu cho listview dạng danh sách hoặc mảng --> cần chuyển danh sách các đối tượng ra mảng
                try {
                    // cast ra JSONArray
                    JSONArray jArray = new JSONArray(mMessage);
                    // implement for loop for getting users list data
                    for (int i = 0; i < jArray.length(); i++) {
                        // create a JSONObject for fetching single user data
                        JSONObject yeuCauXeDetail = jArray.getJSONObject(i);
                        //dua vao cai arrayobject
                        String id = yeuCauXeDetail.getString("Id");
                        String ngayYeuCau = yeuCauXeDetail.getString("NgayYeuCau");
                        Log.e(TAG, id + " " + ngayYeuCau );


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


