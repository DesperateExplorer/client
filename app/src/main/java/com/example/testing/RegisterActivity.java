package com.example.testing;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.Runnable;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private ImageView backButton;
    private Button button;
    private EditText username;
    private EditText password;

    private Activity _thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backButton = findViewById(R.id.BackButton);
        button = findViewById(R.id.register_confirm);

        username = findViewById(R.id.Userid_edit_text);
        password = findViewById(R.id.Password_edit_text);
        _thisActivity = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                TODO_OK：发送Username和password（已完成，已测试）
//                TODO：加（二次）验证密码+TODO（未完成！）

                String userId = username.getText().toString();
                String passwordText = password.getText().toString();
                // for debug
                System.err.println("before post: here!");

                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody
                        .Builder()
                        .add("userId", userId)
                        .add("password", passwordText)
                        .build();
                Request request = new Request
                        .Builder()
                        .url(AppSingle.baseUrl + "/register")
                        .post(body)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.err.println("Register: " + e.getMessage());
                        _thisActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.err.println(response.toString());
                        if(response.isSuccessful()) {
                            String codeText = response.body().string();
                            System.err.println("Register codeText: " + codeText);
                            JsonObject jsonObject = JsonParser.parseString(codeText).getAsJsonObject();
                            int code = jsonObject.get("code").getAsInt();

                            _thisActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    username.getText().clear();
                                    password.getText().clear();
                                    if (userId.isEmpty() || passwordText.isEmpty()) {
                                        Toast.makeText(getApplicationContext(), "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                                    } else if(code == 0) {
                                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                        AppSingle.setUsername(userId);
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        _thisActivity.startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "用户名已存在，注册失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                        }
                    }
                });
//                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}