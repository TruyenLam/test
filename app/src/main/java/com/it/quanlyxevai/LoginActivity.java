package com.it.quanlyxevai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    TextView edtuesr,edtpass;
    Button btnLogin;

    public static final String TAG = LoginActivity.class.getSimpleName();

    public static String API_KEY = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtuesr = (EditText) findViewById(R.id.editTextUser);
        edtpass= (EditText) findViewById(R.id.editTextPass);
        btnLogin = (Button) findViewById(R.id.buttonLogin);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postRequest();
                    //luu vao bo nho may
                    addtoken();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addtoken() {
        SharedPreferences sharedpreferences = getSharedPreferences("my_token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.clear();

        editor.putString("token", API_KEY);

        editor.commit();
        //
    }

    public void postRequest() throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "https://local.thttextile.com.vn/thtapigate/api/login/login";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("UserId", edtuesr.getText());
            postdata.put("PassWord", edtpass.getText());
        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

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
                Log.e(TAG, mMessage); //chỗ này ra: {"TokenString":"..."} --> cần parse ra object, không phải array
                //đã test ok - có TokenString
                //kiểm tra response code
                if(response.code() == 200){
                    //chuẩn bị truyền intent
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //truyền thêm ToKenString
                    try{
                        //lệnh dưới OK, tuy nhiên muốn lấy trực tiếp response, vốn đã là JSONObject rồi
                        //kq: không cast response.body() ra JSONObject được !!!!
                        JSONObject jObj = new JSONObject(mMessage);
                        String token = jObj.getString("TokenString");
                        //Truyền Token sang man hinh mới
                        intent.putExtra("TokenString", token);

                        API_KEY = token;
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //thực hiện
                    startActivity(intent);
                }

            }
        });
    }

}
