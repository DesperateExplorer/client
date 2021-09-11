package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testing.AppSingle;
import com.example.testing.HomeActivity;
import com.example.testing.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SetSubjectActivity extends AppCompatActivity {

    private Activity _this = this;
    private List<Integer> subList;
    private Map<Integer, String> int2Subject = new HashMap<Integer,String>(){
        {
            put(0, "chinese");
            put(1, "math");
            put(2, "english");
            put(3, "physics");
            put(4, "chemistry");
            put(5, "biology");
            put(6, "geography");
            put(7, "history");
            put(8, "politics");
        }
    };
    int MODE = 0; //default
    List<Boolean> Selected = new LinkedList<Boolean>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_subject);

        subList = AppSingle.getSubjectList();

        Resources res = getResources();

        ImageView imageView = findViewById(R.id.back_arrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetSubjectActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        //初始化学科选中表
        if(Selected.size()==0) {
            for (int i = 0; i < int2Subject.size(); i++) {
                Selected.add(false);
            }
        }

        //只显示已选中的学科
        System.out.println(subList);
        for(int i=0; i<subList.size(); i++) {
            int id = res.getIdentifier("b" + (subList.get(i) + 1), "id", getPackageName());
            Button button = findViewById(id);
            button.setVisibility(View.VISIBLE);
            button.setEnabled(false);
            button.setActivated(true);
            Selected.set(subList.get(i), true);
        }

        //设置button监听器
        for(int i=0; i<int2Subject.size(); i++)
        {
            int id = res.getIdentifier("b" + (i + 1), "id", getPackageName());
            Button button = findViewById(id);
            int j = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Selected.get(j) == false)
                    {
                        Selected.set(j, true);
                        button.setActivated(true);
                    }
                    else
                    {
                        Selected.set(j, false);
                        button.setActivated(false);
                    }
                }
            });
        }


        //开启编辑模式
        TextView textView = findViewById(R.id.alter_subject);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MODE == 0) //default
                {
                    MODE = 1;
                    textView.setText("完成");
                    textView.setTextColor(res.getColor(R.color.red));
                    for(int i=0; i<int2Subject.size(); i++)
                    {
                        int j = i;
                        int id = res.getIdentifier("b"+(i+1), "id", getPackageName());
                        Button button = findViewById(id);
                        button.setEnabled(true);
                        button.setVisibility(View.VISIBLE);
                        if(Selected.get(j) == true)
                            button.setActivated(true);
                        else
                            button.setActivated(false);
                    }
                }
                else
                {
                    MODE = 0;
                    textView.setText("编辑");
                    textView.setTextColor(res.getColor(R.color.black));
                    for(int i=0; i<Selected.size();i++)
                    {
                        int j = i;
                        int id = res.getIdentifier("b"+(i+1), "id", getPackageName());
                        Button button = findViewById(id);
                        if(Selected.get(j) == false)
                        {
                            button.setVisibility(View.GONE);
                        }
                        else
                        {
                            button.setVisibility(View.VISIBLE);
                        }
                        button.setEnabled(false);
                    }

                    //finish a change, send the new subject list to Global variable
                    List<Integer> newList = new LinkedList<>();
                    for(int i=0; i<int2Subject.size(); i++)
                    {
                        if(Selected.get(i) == true)
                            newList.add(i);
                    }
                    AppSingle.setSubjectList(newList);
                    System.out.println(newList);

                    //TODO:发送改后的学科列表
                    //string =
                    OkHttpClient client = new OkHttpClient();
                    FormBody formBody = new FormBody
                            .Builder()
                            .add("userId", AppSingle.getUsername())
                            .add("courses", AppSingle.List2TF(AppSingle.getSubjectList()))
                            .build();
                    Request request = new Request
                            .Builder()
                            .url(AppSingle.baseUrl + "/courses")
                            .post(formBody)
                            .build();
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            _this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "学科分类列表仅本地修改!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if(response.isSuccessful()) {
                                _this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "学科分类列表已同步到后端", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                            }
                        }
                    });
                }
            }
        });

    }
}