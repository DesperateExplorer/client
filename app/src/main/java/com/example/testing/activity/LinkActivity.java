package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.AppSingle;
import com.example.testing.R;
import com.example.testing.SearchActivity;
import com.example.testing.StringDesignUtil;
import com.example.testing.adapter.SearchListAdapter;
import com.example.testing.jsonTool.LinkEntity;
import com.example.testing.jsonTool.SearchListEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import scut.carson_ho.searchview.SearchView;

public class LinkActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private Button button;
    private Button button2;
    private ImageButton back;
    private String currentSubject;
    Activity _this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        // 初始化搜索框变量
        editText = findViewById(R.id.link_input);
        textView = findViewById(R.id.link_result);
        button = findViewById(R.id.link_finish);
        button2 = findViewById(R.id.link_clear);
        back = findViewById(R.id.link_back);

        textView.setTextSize(20);
        textView.setPadding(10, 20, 10, 20);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODONE:发送网络请求:input,subject
                String input = editText.getText().toString();
                Intent intent = getIntent();
                currentSubject = intent.getStringExtra("subject");
                Log.d(this.getClass().getSimpleName(), "link activity: "+currentSubject);
                Log.d(this.getClass().getSimpleName(), "input: "+input);
                Log.d(this.getClass().getSimpleName(), "course: "+currentSubject);
                if(input.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "输入不得为空！", Toast.LENGTH_SHORT).show();
                } else {
                OkHttpClient client = new OkHttpClient();
                HttpUrl nerUrl = new HttpUrl
                        .Builder()
                        .scheme(AppSingle.scheme)
                        .host(AppSingle.host)
                        .port(AppSingle.port)
                        .addPathSegment("NER")
                        .addQueryParameter("keyword", input)
                        .addQueryParameter("course", AppSingle.SUBJECT2ENG.get(currentSubject))
                        .build();
                Request request = new Request
                        .Builder()
                        .url(nerUrl)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(this.getClass().getSimpleName(), AppSingle.failMsg);
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
                        ArrayList<Integer> start_index = new ArrayList<>();
                        ArrayList<Integer> end_index = new ArrayList<>();
                        JsonArray jsonArray = JsonParser.parseString(response.body().string()).getAsJsonArray();
                        for(JsonElement e: jsonArray) {
                            start_index.add(e.getAsJsonObject().get("start_index").getAsInt());
                            end_index.add(e.getAsJsonObject().get("end_index").getAsInt()  + 1);
                        }
                        _this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(StringDesignUtil.getSpannableStringBuilder(input, Color.RED, start_index, end_index));
                            }
                        });

                    }
                });
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.getText().clear();
                textView.setText("");
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