package com.it.quanlyxevai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.it.quanlyxevai.LoginActivity.API_KEY;


public class MainActivity extends AppCompatActivity {

    String key = API_KEY;
    Button btnYeuCauXe,btnThongTinXe;
    ListView lvXe;
    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ánh xạ từ xml
        btnThongTinXe = (Button) findViewById(R.id.buttonThongTinXe);
        btnYeuCauXe = (Button) findViewById(R.id.buttonYeuCauXe);
        lvXe = (ListView) findViewById(R.id.listViewXe);


        //Toast.makeText(MainActivity.this,keytoken,Toast.LENGTH_LONG).show();
        btnYeuCauXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    getHttpResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadToken() {
        SharedPreferences pre=getSharedPreferences ("my_token",MODE_PRIVATE);
        String keytoken = pre.getString("token","");
        Log.d("tokenMainActivity",keytoken);
    }
    public void getHttpResponse() throws IOException {

        //tạo url có parameter
        String url = "http://local.thttextile.com.vn/thtapigate/api/QLSX/XeVai/Get_ThongTinXe?xe=";

//        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://local.thttextile.com.vn/thtapigate/api/QLSX/XeVai/Get_ThongTinXe").newBuilder();
//        urlBuilder.addQueryParameter("xe", "");
//        String url = urlBuilder.build().toString();

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
            }
        });


    }
}


