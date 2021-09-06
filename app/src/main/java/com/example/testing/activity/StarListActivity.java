package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.testing.MyApplication;
import com.example.testing.R;
import com.example.testing.SearchActivity;

import java.util.ArrayList;

public class StarListActivity extends AppCompatActivity {

    MyApplication myApp;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_list);

        //绑定组件
        ListView listView = findViewById(R.id.starList_listView);
        myApp = (MyApplication) getApplication();
        ArrayList<String> label = myApp.getStarLabel();

        adapter = new ArrayAdapter<String>(StarListActivity.this, android.R.layout.simple_list_item_1, label);
        listView.setAdapter(adapter);

        //和前端数据库交互，点击后跳转到实体详情页
    }
}