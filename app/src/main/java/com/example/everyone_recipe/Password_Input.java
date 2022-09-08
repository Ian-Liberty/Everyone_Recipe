package com.example.everyone_recipe;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.everyone_recipe.retrofit2.EmailCheckInterface;
import com.example.everyone_recipe.retrofit2.LoginInterface;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Password_Input extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ImageButton back_BTN;
    private EditText password_input_ET;
    private CheckBox show_password_CB;
    private TextView password_reset;
    private Button login_btn;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_input);

        initViews();
        initListeners();
    }
    private void initViews(){

        back_BTN=findViewById(R.id.back_BTN);
        password_input_ET=findViewById(R.id.password_input_ET);
        show_password_CB=findViewById(R.id.show_password_CB);
        password_reset=findViewById(R.id.password_reset);
        login_btn=findViewById(R.id.login_btn);
        show_password_CB = findViewById(R.id.show_password_CB);

    }

    private void initListeners() {

        back_BTN.setOnClickListener(this);
        password_reset.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        show_password_CB.setOnCheckedChangeListener(this);
        password_input_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(password_input_ET.length()>0){
                    login_btn.setEnabled(true);
                }
                else{
                    login_btn.setEnabled(false);
                }

            }
        });


    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.back_BTN:
//
                Log.e(TAG, "뒤로가기 버튼");
                finish();
                break;
            case R.id.password_reset:
//
                Log.e(TAG, "비밀번호 재설정");

                break;
            case R.id.login_btn:
//
                Log.e(TAG, "로그인 버튼");
                login();
                break;
        }

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch(compoundButton.getId()){
            case  R.id.show_password_CB:
                if(!b){
                    password_input_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password_input_ET.setSelection(password_input_ET.length());
                }else{
                    password_input_ET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password_input_ET.setSelection(password_input_ET.length());
                }
            break;
        }

    }

    private void login() {
        email = getIntent().getStringExtra("email");
        password = password_input_ET.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginInterface.LOGIN_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        LoginInterface api = retrofit.create(LoginInterface.class);
        Call<String> call = api.login(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("onSuccess", response.body());

                String jsonResponse = response.body();

                try
                {
                    user_Data(jsonResponse);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    private void user_Data(String response) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true"))
        {
            Log.i("정보태그", "로그인 성공");
            Intent mIntent = new Intent(getApplicationContext(), Home.class);
            mIntent.putExtra("email", email);
            mIntent.putExtra("data", jsonObject.optString("data"));
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mIntent);
        }
        else if(jsonObject.optString("status").equals("false")){
            Toast.makeText(getApplicationContext(), jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
            Log.i("정보태그", "로그인 실패");

        }
        else
        {
            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }

    }


}