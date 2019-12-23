package com.it.quanlyxevai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.it.quanlyxevai.api.AppHandler;
import com.it.quanlyxevai.api.AppUtils;
import com.it.quanlyxevai.base.AppPref;
import com.it.quanlyxevai.base.BaseActivity;
import com.it.quanlyxevai.base.Constant;
import com.it.quanlyxevai.model.LoginRequest;
import com.it.quanlyxevai.model.LoginResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.editTextUser)
    EditText edtUserName;
    @BindView(R.id.editTextPass)
    EditText edtPassWord;

    @BindView(R.id.buttonLogin)
    Button btLogin;

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.buttonLogin)
    public void onClick(View v){
        if (v.getId() == R.id.buttonLogin)
            loginSytem();
    }

    private void loginSytem() {
       AppHandler.loginSystem(new LoginRequest(edtUserName.getText().toString(), edtPassWord.getText().toString()))
       .subscribe(new Observer<LoginResponse>() {
           @Override
           public void onSubscribe(Disposable d) {

           }

           @Override
           public void onNext(LoginResponse model) {
               if (!TextUtils.isEmpty(model.getTokenString())) { // Đăng nhập thành công
                   AppUtils.showToast(getApplicationContext(), getString(R.string.text_login_success));


                   //Lưu token vào máy để dùng cho các hàm sau
                   AppPref.putString(getApplicationContext(), Constant.PREF_TOKEN, model.getTokenString());
                   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                   startActivity(intent);
               } else { // Đăng nhập thất bai
                   AppUtils.showToast(getApplicationContext(), getString(R.string.text_login_error));
               }
           }

           @Override
           public void onError(Throwable e) {
               AppUtils.showToast(getApplicationContext(), getString(R.string.text_login_error));
           }

           @Override
           public void onComplete() {

           }
       });
    }
}
