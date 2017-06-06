package com.atreya.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.atreya.myapp.SharedPref.REMIND_ME_SHARED_PREF;

public class LoginActivity extends AppCompatActivity {
EditText userName,Password;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName=(EditText)findViewById(R.id.editText);
        Password=(EditText)findViewById(R.id.editText2);
        sharedPreferences = getSharedPreferences(REMIND_ME_SHARED_PREF, 0);

    }

    public void login(View v)
    {
        String user=userName.getText().toString();
        String pass=Password.getText().toString();
        if (user.equals("test") && pass.equals("test"))
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("isLogin",1);
            editor.commit();

            Intent mainIntent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
            finish();
        }else
        {
            Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();

        }

    }


}
