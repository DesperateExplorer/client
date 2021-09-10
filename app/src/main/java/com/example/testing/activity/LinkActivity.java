package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
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

import com.example.testing.AppSingle;
import com.example.testing.R;
import com.example.testing.SearchActivity;
import com.example.testing.StringDesignUtil;
import com.example.testing.adapter.SearchListAdapter;
import com.example.testing.jsonTool.LinkEntity;
import com.example.testing.jsonTool.SearchListEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.SearchView;

public class LinkActivity extends AppCompatActivity {

    String currentSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        // 初始化搜索框变量
        EditText editText = findViewById(R.id.link_input);
        TextView textView = findViewById(R.id.link_result);
        Button button = findViewById(R.id.link_finish);
        Button button2 = findViewById(R.id.link_clear);
        ImageButton back = findViewById(R.id.link_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable input = editText.getText();
                Intent intent = getIntent();
                currentSubject = intent.getStringExtra("subject");
                String text = input.toString();
//                //测试用，后面取消
//                String text = "中国位于亚洲东部，拥有世界屋脊青藏高原，沿海为季风气候";

                //TODO: 获得数据
                ArrayList<LinkEntity> list = new ArrayList<>();
                list.add(new LinkEntity("0","1","中国","","http://edukb.org/knowledge/0.1/instance/geo#-33070e7e6be2460dd9ddcbce5d44eb28"));
                list.add(new LinkEntity("4","5","亚洲","",""));
                list.add(new LinkEntity("6","7","亚洲","",""));

                //整理为我需要的格式
                ArrayList<Integer> start_index = new ArrayList<>();
                ArrayList<Integer> end_index = new ArrayList<>();

                for(LinkEntity linkEntity:list)
                {
                    start_index.add(Integer.valueOf(linkEntity.getStart_index()));
                    end_index.add(Integer.valueOf(linkEntity.getEnd_index())+1);
                }

                textView.setTextSize(20);
                textView.setPadding(10, 20, 10, 20);
                textView.setText(StringDesignUtil.getSpannableStringBuilder(text, Color.RED, start_index, end_index));
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
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