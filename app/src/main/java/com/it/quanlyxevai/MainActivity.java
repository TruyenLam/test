package com.it.quanlyxevai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.it.quanlyxevai.api.AppUtils;
import com.it.quanlyxevai.base.AppPref;
import com.it.quanlyxevai.base.BaseActivity;
import com.it.quanlyxevai.base.Constant;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    String key = "";
    Button btnYeuCauXe,btnThongTinXe;
    ListView lvXe;
    public static final String TAG = LoginActivity.class.getSimpleName();
    String responseBody;

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        // Get token đã lưu khi đăng nhập !
        String token = AppPref.getString(getApplicationContext(), Constant.PREF_TOKEN, "");
        AppUtils.showToast(getApplicationContext(), "Token is : " + token);

        //ánh xạ từ xml
        btnThongTinXe = (Button) findViewById(R.id.buttonThongTinXe);
        btnYeuCauXe = (Button) findViewById(R.id.buttonYeuCauXe);
        lvXe = (ListView) findViewById(R.id.listViewXe);

        btnYeuCauXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    getHttpResponse();
                    Toast.makeText(MainActivity.this,responseBody,Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getHttpResponse() throws IOException {

        //tạo url có parameter
        String url = "http://local.thttextile.com.vn/thtapigate/api/QLSX/XeVai/Get_ThongTinXe?xe=";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                //.header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + key)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                Log.d(TAG,body);//ok
                responseBody = body;
            }
        });


    }
}


