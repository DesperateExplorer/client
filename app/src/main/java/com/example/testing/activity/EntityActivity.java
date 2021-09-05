package com.example.testing.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.example.testing.MyApplication;
import com.example.testing.R;
import com.example.testing.adapter.EntityAdapter;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.skin.SkinWriter;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class EntityActivity extends QMUIFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);

        Intent intent = getIntent();
        String s = intent.getStringExtra("label");
        //String url = intent.getStringExtra("label");

        //调用网络接口，获得实体详情页的所有信息

        //向fragment发送消息

        // 绑定元件
        TextView textView = findViewById(R.id.entity_header);
        ImageView imageView = findViewById(R.id.back_to_list);
        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) findViewById(R.id.entity_pager);
        pager.setAdapter(new EntityAdapter(getSupportFragmentManager()));
        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = findViewById(R.id.entity_tabs);
        tabs.setShouldExpand(true);
        tabs.setViewPager(pager);

        // 添加动作
        textView.setText(s);
        textView.setTextSize(30);

        //绑定返回监听器
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public ArrayList<String> getDescription()
    {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("下属于"+": "+"整式方程");
        tmp.add("定义" + ": " + "只含有一个未知数，并且未知数的最高次数是2的整式方程叫做一元二次方程");
        tmp.add("性质1" + ": "+ "一元二次方程的一般形式是ax^2＋bx＋c=0（a≠0）");
        tmp.add("性质2" + ": "+ "通过开平方运算解一元二次方程的方法叫做直接开平方法");
        return tmp;

    }



}