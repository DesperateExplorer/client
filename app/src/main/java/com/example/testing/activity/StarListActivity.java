package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.testing.AppSingle;
import com.example.testing.MyApplication;
import com.example.testing.R;
import com.example.testing.SearchActivity;

import java.util.ArrayList;

public class StarListActivity extends AppCompatActivity {

    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_list);

        //绑定组件
        ListView listView = findViewById(R.id.starList_listView);
        ImageView imageView = findViewById(R.id.starlist_back);
        ArrayList<String> label = AppSingle.getStarLabel();
        ArrayList<String> uri = AppSingle.getStarUri();
        ArrayList<String> subject = AppSingle.getStarSubject();

        adapter = new ArrayAdapter<String>(StarListActivity.this, android.R.layout.simple_list_item_1, label);
        listView.setAdapter(adapter);

        //跳转到实体详情页上,可以复用实体详情页activity界面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO：label，subject 获得实体详情页的json
                Intent intent = new Intent(StarListActivity.this,EntityActivity.class);
                intent.putExtra("label", label.get(i));
                intent.putExtra("uri", uri.get(i));
                intent.putExtra("subject", subject.get(i));
                startActivity(intent);
            }
        });

        //设置返回监听器
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}