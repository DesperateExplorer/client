package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testing.AppSingle;
import com.example.testing.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlusRecommendActivity extends AppCompatActivity {

    Activity _this = this;
    ListView listView;
    ImageButton back;
    private EntityActivity activity;
    private ArrayList<String> body = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();
    private ArrayList<String> BranchA = new ArrayList<>();
    private ArrayList<String> BranchB = new ArrayList<>();
    private ArrayList<String> BranchC = new ArrayList<>();
    private ArrayList<String> BranchD = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_recommend);

        //绑定组件
        listView = findViewById(R.id.recommend_list_view);
        back = findViewById(R.id.recommend_back);

        OkHttpClient client = new OkHttpClient
                .Builder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS).writeTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS)
                .build();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(AppSingle.scheme)
                .host(AppSingle.host)
                .port(AppSingle.port)
                .addPathSegment("questionRecommend")
                .addQueryParameter("userId", AppSingle.getUsername())
                .build();
        Request request = new Request.Builder()
                .url(httpUrl).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println(AppSingle.failMsg);
                e.printStackTrace();
                _this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "错误：对不起，OpenEduKG或网络异常！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if(response.isSuccessful()) {
                    JsonArray jsonArray = JsonParser.parseString(result).getAsJsonArray();
                    for(JsonElement element: jsonArray) {
                        body.add(element.getAsJsonObject().get("body").getAsString());
                        answers.add(element.getAsJsonObject().get("answer").getAsString());
                        BranchA.add(element.getAsJsonObject().get("branchA").getAsString());
                        BranchB.add(element.getAsJsonObject().get("branchB").getAsString());
                        BranchC.add(element.getAsJsonObject().get("branchC").getAsString());
                        BranchD.add(element.getAsJsonObject().get("branchD").getAsString());
                    }
                    ArrayList<String> list = new ArrayList<>();
                    for(String q: body)
                    {
                        list.add(q);
                    }
                    _this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //在listView中显示出来
                            BaseAdapter adapter = new ArrayAdapter<String>(PlusRecommendActivity.this, android.R.layout.simple_list_item_1, list);
                            listView.setAdapter(adapter);
                        }
                    });
                } else  {

                }
            }
        });

        //点击试题跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PlusRecommendActivity.this, TestActivity.class);
                intent.putExtra("answer",answers.get(i));
                intent.putExtra("question",body.get(i));
                intent.putExtra("A",BranchA.get(i));
                intent.putExtra("B",BranchB.get(i));
                intent.putExtra("C",BranchC.get(i));
                intent.putExtra("D",BranchD.get(i));
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}