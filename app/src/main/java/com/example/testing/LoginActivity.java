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
                        System.err.println(response.toString());
                        if(response.isSuccessful()) {
                            JsonObject jsonObject = JsonParser.parseString(response.body().string()).getAsJsonObject();
                            if(jsonObject.get("code").getAsInt() == 0) {
                                JsonArray history = jsonObject.get("history").getAsJsonArray();
                                String courses = jsonObject.get("courses").getAsString();
                                AppSingle.setSubjectList(AppSingle.TF2List(courses));
                                try {
                                    for(JsonElement elem: history) {

                                            JSONObject temp = new JSONObject(elem.getAsString());
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


                //TODO:登录成功：发送网络请求，获得历史数据
                //Toy data
                String jsonData_star = "[{\n" +
                        "\t\"label\": \"高原山地气候\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-00394d65c2457f426594cadeb580c0d2\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"青藏高原湖区\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-04449fbfae465d7b992e2a6793966058\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"青藏高原区\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-08d8377b7756d4a741841ad2353753c9\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-182df86eeea94e47f4eeb25f482a26ea\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"青藏高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-1cd960145524ae1ebcd10cbf0cb228bc\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"巴西高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-2628c2a0028eac88b1435039f197efea\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"蒙古高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-2e40bad3fdbba6a4b0413b4e5ef82aaa\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"高原和高山气候\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-4f8acd02869a00b72345f7145a5944a4\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"高原山地气候\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-53274100a4a242d6acc6a96c286591f9\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"德干高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-574a9f20bb45a0d97dcc7c1bd2adc032\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"云贵高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-61d51de2e3b2b5c5a9c58ac93741a900\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"四大高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-9fc6d4b3c83fcba1e187f447a7f117bc\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"高原高山区\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-ac019b1ceaccfed6abb78dd264df05f3\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"伊朗高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-c7abb9664aa5df27793c1ccc10b88945\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"马达加斯加岛的东侧、澳大利亚的东北部、巴西高原东南沿海和中美地峡的东侧等地的热带 ..\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-e9147b0d1182cbc114f20dec2b274686\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-eabb4d4b96a0a4dae649d9ca41d4be52\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"黄土高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-ebf76fed513c4fb763cdef0dc18eeb21\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"黄土高原的黄土是从我国西北内陆吹来的\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-f0027938b7aca6ed8cd1792cd8dd5714\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"中西伯利亚高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-f0675a357bcb560cb96844e7d6cb97a3\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}, {\n" +
                        "\t\"label\": \"赤道附近的东非高原呈现热带草原景观\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-fc45fef52d35a9d33f2e04c9286ac45e\",\n" +
                        "\t\"subject\": \"地理\"\n" +"}]";
                String jsonData_history = jsonData_star;
                //String jsonData_description =

                //收藏数据
                Gson gson = new Gson();
                List<ListEntity> list = gson.fromJson(jsonData_star,new TypeToken<List<ListEntity>>(){}.getType());
                ArrayList<String> label = new ArrayList<>();
                ArrayList<String> uri = new ArrayList<>();
                ArrayList<String> subject = new ArrayList<>();
                for(ListEntity listEntity: list){
                    label.add(listEntity.getLabel());
                    uri.add(listEntity.getUri());
                    subject.add(listEntity.getSubject());
                }
                AppSingle.setStarLabel(label);
                AppSingle.setStarUri(uri);
                AppSingle.setStarSubject(subject);

                //访问过的列表:
                list = gson.fromJson(jsonData_history,new TypeToken<List<ListEntity>>(){}.getType());
                label = new ArrayList<>();
                uri = new ArrayList<>();
                subject = new ArrayList<>();
                for(ListEntity listEntity: list){
                    label.add(listEntity.getLabel());
                    uri.add(listEntity.getUri());
                    subject.add(listEntity.getSubject());
                }
                AppSingle.setHistoryLabel(label);
                AppSingle.setHistoryUri(uri);
                AppSingle.setHistorySubject(subject);

                //Deprecate_TODO:访问过的实体
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}