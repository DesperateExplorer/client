package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    ImageView backButton;
    Button registerButton;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerButton = findViewById(R.id.Regbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText username = findViewById(R.id.Userid);
                EditText password = findViewById(R.id.Password);
                //TODO：需要发送username和password，get是否成功
                //TODO：加入global variable，记录当前用户的id
                //历史记录+收藏信息
                //输入的用户名：username.getText();
                //输入的密码：password.getText();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}