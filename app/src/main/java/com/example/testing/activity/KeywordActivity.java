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

import java.util.ArrayList;

public class KeywordActivity extends AppCompatActivity {

    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);

        //绑定组件
        ListView listView = findViewById(R.id.keyword_listView);
        ImageView imageView = findViewById(R.id.keyword_back);

        ArrayList<String> HistoryKeyWord = AppSingle.getKeyWord();
        adapter = new ArrayAdapter<String>(KeywordActivity.this, android.R.layout.simple_list_item_1, HistoryKeyWord);
        listView.setAdapter(adapter);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}