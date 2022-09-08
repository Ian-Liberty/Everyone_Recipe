package com.example.everyone_recipe;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CompoundButton;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.everyone_recipe.retrofit2.SignupInterface;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Signup extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ImageButton back_BTN;
    private EditText nickname_ET;
    private EditText password_ET;
    private EditText password_check_ET;
    private CheckBox show_password_CB;
    private Button signup_BTN;
    private String nickname;
    private String password;
    private String check_password;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        initListeners();
    }
    private void initViews(){
        back_BTN=findViewById(R.id.back_BTN);
        nickname_ET=findViewById(R.id.nickname_ET);
        password_ET=findViewById(R.id.password_ET);
        password_check_ET=findViewById(R.id.password_check_ET);
        show_password_CB=findViewById(R.id.show_password_CB);
        signup_BTN=findViewById(R.id.signup_BTN);

    }

    private void initListeners() {
        back_BTN.setOnClickListener(this);
        signup_BTN.setOnClickListener(this);
        show_password_CB.setOnCheckedChangeListener(this);

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch(compoundButton.getId()){
            case  R.id.show_password_CB:
                if(!b){
                    password_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password_ET.setSelection(password_ET.length());
                }else{
                    password_ET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password_ET.setSelection(password_ET.length());
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case  R.id.back_BTN:
//                뒤로가기
                Log.e(TAG, "뒤로가기");
                finish();
                break;

            case R.id.signup_BTN:
//                회원가입
                Log.e(TAG, "회원가입 버튼");
                signup();
                break;

        }
    }

    private void signup() {
        email = getIntent().getStringExtra("email");
        nickname=nickname_ET.getText().toString();
        password=password_ET.getText().toString();
        check_password=password_check_ET.getText().toString();

        Log.i("입력정보", "email:"+email+" nickname: "+nickname+" password: "+password+" password_check:"+check_password);

        if(password.equals(check_password)){
            //패스워드 확인 일치
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SignupInterface.LOGIN_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            SignupInterface api = retrofit.create(SignupInterface.class);
            Call<String> call =api.signup(email, nickname, password);
            call.enqueue(new Callback<String>()
            {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response)
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                    Log.e("onSuccess", response.body());

                    String jsonResponse = response.body();

                    try
                    {
                        parseRegData(jsonResponse);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    }
                    else{
                       Log.e("오류태그", String.valueOf(response.isSuccessful()));
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "에러 = " + t.getMessage());
                }
            });

        }
        else
        {
     Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
        }

    }
    private void parseRegData(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true"))
        {
           Log.i("정보태그", "회원가입 완료");
           Toast.makeText(getApplicationContext(), "모두의 레시피에 오신것을 환영합니다!",Toast.LENGTH_SHORT).show();
           Intent mIntent = new Intent(getApplicationContext(), Home.class);
           mIntent.putExtra("email", email);
           mIntent.putExtra("data", jsonObject.optString("data"));
           mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(mIntent);

        }
        else if(jsonObject.optString("status").equals("false")){
           Log.i("정보태그", "회원가입 실패");
           Toast.makeText(getApplicationContext(), jsonObject.optString("message"),Toast.LENGTH_SHORT).show();

        }
        else{

        }

    }


}