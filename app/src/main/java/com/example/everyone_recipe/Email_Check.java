package com.example.everyone_recipe;
import android.content.Intent;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.everyone_recipe.retrofit2.EmailCheckInterface;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Email_Check extends AppCompatActivity implements View.OnClickListener {
    private TextView resend_TV;
    private TextView timer_TV;
    private EditText aut_input_ET;
    private Button check_BTN;
    private ImageButton back_BTN;
    private Intent getintent;
    private String email;
    private String conversionTime;
    private String aut_code;
    private CountDownTimer count;
    private boolean timecheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_check);
        timecheck = true;
        getintent = getIntent();
        email = getintent.getStringExtra("email");
        Log.i("email", email);
        conversionTime = "0300";
        // 인증메일 발송
        aut_generate(email);

        initViews();
        initListeners();
        countDown(conversionTime);
        count.start();
    }

    private void initViews(){
        resend_TV = findViewById(R.id.resend_TV);
        aut_input_ET = findViewById(R.id.aut_input_ET);
        check_BTN = findViewById(R.id.check_BTN);
        back_BTN = findViewById(R.id.back_BTN);
        timer_TV = findViewById(R.id.timer_TV);

    }

    private void initListeners() {

        resend_TV.setOnClickListener(this);
        check_BTN.setOnClickListener(this);
        back_BTN.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case  R.id.resend_TV:
//
                Log.e(TAG, "다시보내기 클릭");
                timecheck = true;
                aut_generate(email);
                count.cancel();
                count.start();
                Toast.makeText(getApplicationContext(), "인증코드를 다시 전송하였습니다.",Toast.LENGTH_SHORT).show();
                break;

            case R.id.check_BTN:
//
                Log.e(TAG, "확인버튼 클릭");
                check_aut();
                break;

            case R.id.back_BTN:
//
                Log.e(TAG, "뒤로가기 클릭");
                finish();
                break;
        }

    }
    private void aut_generate(String email){
        Log.d("aut_generate", "메서드 실행");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EmailCheckInterface.LOGIN_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        EmailCheckInterface api = retrofit.create(EmailCheckInterface.class);
        Call<String> call = api.getaut_code(email);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("onSuccess", response.body());

                String jsonResponse = response.body();

                try
                {
                    set_aut_Data(jsonResponse);
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
    private void set_aut_Data(String response) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(response);

        aut_code = jsonObject.optString("aut_code");
    }
    private void check_aut(){
        String input_aut_code=aut_input_ET.getText().toString();
        if(timecheck){
        if(input_aut_code.equals(aut_code)){
            Log.i("정보태그", "일치");
            Toast.makeText(getApplicationContext(), "인증되었습니다!",Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(getApplicationContext(), Signup.class);
            mIntent.putExtra("email", email);
            startActivity(mIntent);
            finish();
        }
        else{
            Log.i("정보태그", "불일치");
            Toast.makeText(getApplicationContext(), "유효한 코드가 아닙니다.",Toast.LENGTH_SHORT).show();
        }
        }
        else{
            Log.i("정보태그", "유효시간 만료");
            Toast.makeText(getApplicationContext(), "유효시간이 만료되었습니다. 다시 시도해주세요",Toast.LENGTH_SHORT).show();
        }
    }
    private void countDown(String time) {
        long conversionTime = 0;
        // 1000 단위가 1초
        // 60000 단위가 1분
        // 60000 * 3600 = 1시간

        String getMin = time.substring(0, 2);
        String getSecond = time.substring(2, 4);

        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }

        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }

        // 변환시간
        conversionTime = Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;

        count=new CountDownTimer(conversionTime, 1000) {

            // 특정 시간마다 뷰 변경
            public void onTick(long millisUntilFinished) {

                // 분단위
                long getMin = millisUntilFinished - (millisUntilFinished / (60 * 60 * 1000)) ;
                String min = String.valueOf(getMin / (60 * 1000)); // 몫

                // 초단위
                String second = String.valueOf((getMin % (60 * 1000)) / 1000); // 나머지

                // 밀리세컨드 단위
                String millis = String.valueOf((getMin % (60 * 1000)) % 1000); // 몫

                // 분이 한자리면 0을 붙인다
                if (min.length() == 1) {
                    min = "0" + min;
                }

                // 초가 한자리면 0을 붙인다
                if (second.length() == 1) {
                    second = "0" + second;
                }

                timer_TV.setText(min + ":" + second);
            }

            // 제한시간 종료시
            public void onFinish() {

                // 변경 후
                timer_TV.setText("유효시간 만료");
                timecheck = false;

                // TODO : 타이머가 모두 종료될때 어떤 이벤트를 진행할지

            }
        };
    }
    private void parseRegData(String response) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true"))
        {
            Toast.makeText(getApplicationContext(), "인증 완료되었습니다.",Toast.LENGTH_SHORT).show();
        }
        else if(jsonObject.optString("status").equals("false")){
            Toast.makeText(getApplicationContext(), "인증에 실패하였습니다.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }
}