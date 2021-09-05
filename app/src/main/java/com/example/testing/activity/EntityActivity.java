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

    public ArrayList<String> getPredicate()
    {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("强相关于");
        tmp.add("强相关于");
        tmp.add("出处");
        tmp.add("名称");
        tmp.add("类型");
        tmp.add("页码");
        tmp.add("强相关于");
        tmp.add("内容");
        tmp.add("强相关于");
        return tmp;
    }

    public ArrayList<String> getObject()
    {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("边");
        tmp.add("三角");
        tmp.add("出处");
        tmp.add("名称");
        tmp.add("类型");
        tmp.add("页码");
        tmp.add("角");
        tmp.add("三角形任何一边的平方等于其他两边的平方和减去这两边与它们夹角的余弦的积的两倍");
        tmp.add("三角形");
        return tmp;
    }

    public ArrayList<String> getQuestions()
    {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("下列与我国隔海相望的国家中,纬度位置最高的是()");
        tmp.add("近日朝韩局势日益紧张,如要了解朝鲜、韩国的位置,应查阅()");
        tmp.add("我国在朝核六方会谈中占很重的分量,除了是因为我国是国际大国外,还与其中两国接壤,他们是()");
        tmp.add("与我国隔海相望的一组国家是()");
        tmp.add("下列四组国家中,全部与我国隔海相望的一组国家是()");
        tmp.add("列国家与我国陆地接壤的是()");
        tmp.add("近年来,日本与邻国因岛屿争端,关系日益紧张。下列不属于日本近邻的国家是()");
        tmp.add("下列各国家中,人均国民生产总值最高的是()");
        tmp.add("与我国隔海相望的国家有()");
        tmp.add("第17届亚运会于2014年9月19日—10月4日在韩国仁川举行,我国运动员取得了优异的成绩。关于韩国与我国的位置关系说法正确的是()");
        return tmp;
    }


}