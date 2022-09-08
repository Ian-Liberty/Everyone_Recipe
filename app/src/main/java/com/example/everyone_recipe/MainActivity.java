package com.example.everyone_recipe;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.everyone_recipe.retrofit2.EmailCheckInterface;
import com.example.everyone_recipe.retrofit2.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText Email_input;
    private Button CNT_BTN;
    private ImageButton Exit_BTN;
    private ImageButton Kakao_login_btn;
    private TextView validation;
    private PreferenceHelper preferenceHelper;
    private String input_email;
    private Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceHelper = new PreferenceHelper(this);

        initViews();
        initListeners();

    }

    private void validationemail() {
        input_email=Email_input.getText().toString().trim();
        if(pattern.matcher(input_email).matches()){
            //이메일 맞음!
            Log.i("정보태그", "이메일 유효");
            validation.setText("유효한 이메일 입니다!");
            validation.setTextColor(Color.BLACK);
            validation.setVisibility(View.VISIBLE);
            CNT_BTN.setEnabled(true);
        } else {
            //이메일 아님!
            Log.i("정보태그", "이메일 유효하지 않음");
            validation.setText("유효한 이메일을 입력해 주세요!");
            validation.setTextColor(Color.RED);
            validation.setVisibility(View.VISIBLE);
            CNT_BTN.setEnabled(false);
        }
    }

    private void initViews(){
        Exit_BTN=findViewById(R.id.EXIT);
        Email_input = findViewById(R.id.EMAIL_ENT_INPUT_ET);
        CNT_BTN = findViewById(R.id.CNT_BTN);
        Kakao_login_btn = findViewById(R.id.kakao_login);
        validation = findViewById(R.id.validation);

    }

    private void initListeners() {
        Exit_BTN.setOnClickListener(this);
        CNT_BTN.setOnClickListener(this);
        Kakao_login_btn.setOnClickListener(this);
        Email_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("정보태그", "이메일정보 변경");
                validationemail();

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case  R.id.CNT_BTN:
//                다음으로 이동하기위한 메서드
                Log.e(TAG, "다음버튼 클릭");
                check_email();
                break;
            case R.id.kakao_login:
//                카카오 로그인
                Log.e(TAG, "카카오 클릭");
                break;
            case R.id.EXIT:
//                카카오 로그인
                Log.e(TAG, "앱 종료");
                finish();
                break;

        }

    }
    /**
     * Email_input 에 입력되어있는 이메일 을 체크하여
     * 신규일경우 Email_Check
     * 기존 유저일 경우 Password_input
     * 으로 이동시켜줌
     */
    private void check_email(){
        input_email=Email_input.getText().toString();
        //입력된 이메일 값 가져오기

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EmailCheckInterface.LOGIN_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        EmailCheckInterface api = retrofit.create(EmailCheckInterface.class);
        Call<String> call = api.getEmail(input_email);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
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

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
    private void parseRegData(String response) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("login"))
        {
            Toast.makeText(MainActivity.this, "기존유저 입니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Password_Input.class);
            intent.putExtra("email", input_email);
            startActivity(intent);

        }
        else if(jsonObject.optString("status").equals("signup")){
            Toast.makeText(MainActivity.this, "신규 회원입니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Email_Check.class);
            intent.putExtra("email", input_email);
            startActivity(intent);

        }
        else
        {
            Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }




}