package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.testing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PlusRecommendActivity extends AppCompatActivity {

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

        //TODO：获得变量的值
        //下面是一个例子
        body.add("近年来,日本与邻国因岛屿争端,关系日益紧张。下列不属于日本近邻的国家是()");
        answers.add("D");
        BranchA.add("A.中国");
        BranchB.add("B.韩国");
        BranchC.add("C.朝鲜");
        BranchD.add("D.印度");

        body.add("下列各国家中,人均国民生产总值最高的是()");
        answers.add("B");
        BranchA.add("A.印度、巴基斯坦");
        BranchB.add("B.日本、新加坡");
        BranchC.add("C.沙特阿拉伯、以色列");
        BranchD.add("D.韩国、马来西亚");


        //在listView中显示出来
        ArrayList<String> list = new ArrayList<>();
        for(String q: body)
        {
            list.add(q);
        }
        BaseAdapter adapter = new ArrayAdapter<String>(PlusRecommendActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

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