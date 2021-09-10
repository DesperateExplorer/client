package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testing.jsonTool.EntityDescription;
import com.example.testing.jsonTool.ListEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    ImageView backButton;
    Button registerButton;
    Button loginButton;

    Activity _this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText username = findViewById(R.id.Userid);
        EditText password = findViewById(R.id.Password);

        backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
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


                //TODONE：需要发送username和password，get是否成功
                //TODO: 历史记录+收藏信息
                String userId = username.getText().toString();
                String pwd = password.getText().toString();

                //TODO: 只有登录成功：保存用户名
                AppSingle.setUsername(userId);

                OkHttpClient client = new OkHttpClient();
                FormBody loginBody = new FormBody
                        .Builder()
                        .add("userId", userId)
                        .add("password", pwd)
                        .build();
                Request request = new Request
                        .Builder()
                        .url(AppSingle.baseUrl + "/login")
                        .post(loginBody)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.err.println("Login: " + e.getMessage());
                        _this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), AppSingle.failMsg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        System.err.println(result);
                        if(response.isSuccessful()) {
                            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
                            if(jsonObject.get("code").getAsInt() == 0) {
                                JsonArray history = jsonObject.get("history").getAsJsonArray();
                                String courses = jsonObject.get("courses").getAsString();
                                AppSingle.setSubjectList(AppSingle.TF2List(courses));
                                try {
                                    for(JsonElement elem: history) {
                                            System.err.println(elem.isJsonObject());
                                            JSONObject temp = new JSONObject(elem.getAsJsonObject().toString());
    //                                        String label = temp.getString("label");
                                            String uri = temp.getString("uri");
                                            String course = temp.getString("course");
                                            AppSingle.aCache.put(AppSingle.getCacheKey(course, uri), temp);
                                            AppSingle.addLabel(temp.getString("label"));
                                            AppSingle.addUri(uri);
                                            AppSingle.addSubject(course);
                                            if(temp.getBoolean("favorite")) {
                                                AppSingle.addStarLabel(temp.getString("label"));
                                                AppSingle.addStarUri(uri);
                                                AppSingle.addStarSubject(course);
                                        }
                                    }
                                    AppSingle.setUsername(userId);
                                    _this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            username.getText().clear();
                                            password.getText().clear();
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                                catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                            } else {
                                _this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "用户名不存在或密码错误", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            System.err.println("response code: " + response.code());
                        }
                    }
                });
            }
        });
    }
}