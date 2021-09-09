package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testing.AppSingle;
import com.example.testing.HomeActivity;
import com.example.testing.MyApplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testing.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SetSubjectActivity extends AppCompatActivity {

    private MyApplication myApp;
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
        myApp = (MyApplication) getApplication();
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
                }
            }
        });

    }
}