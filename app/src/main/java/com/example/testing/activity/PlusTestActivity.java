package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.testing.AppSingle;
import com.example.testing.R;
import com.example.testing.adapter.PlusTestAdapter;
import com.example.testing.adapter.RelationAdapter;
import com.example.testing.jsonTool.QuestionList;
import com.example.testing.jsonTool.ShowRelation;

import java.util.ArrayList;

public class PlusTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_test);
        AppSingle.ClearScore();

        //绑定组件
        SearchView searchView = findViewById(R.id.plus_test_search);
        ListView listView = findViewById(R.id.plus_test_listview);
        Button button = findViewById(R.id.test_finish);
        TextView textView = findViewById(R.id.test_result);
        ImageView imageView = findViewById(R.id.plus_test_back);

        //设置接收数据的局部变量
        ArrayList<QuestionList> Data = new ArrayList<>();
        
        
        //TODO:从searchView中拿到需要搜索的关键词

        //QuestionList: 需要传入listView的数据格式，可以从jsonTool文件夹中找到
        //下面是一个例子
        Data.add(new QuestionList("B","第17届亚运会于2014年9月19日—10月4日在韩国仁川举行,我国运动员取得了优异的成绩。关于韩国与我国的位置关系说法正确的是()","A.陆上相邻","B.隔海相望","C.既陆上相邻又隔海相望","D.既不陆上相邻又不隔海相望"));
        Data.add(new QuestionList("B","第17届亚运会于2014年9月19日—10月4日在韩国仁川举行,我国运动员取得了优异的成绩。关于韩国与我国的位置关系说法正确的是()","A.陆上相邻","B.隔海相望","C.既陆上相邻又隔海相望","D.既不陆上相邻又不隔海相望"));

        //在listView中显示出来
        PlusTestAdapter adapter = new PlusTestAdapter(PlusTestActivity.this, Data, R.layout.question_item);
        listView.setAdapter(adapter);

        //为提交按钮设置监听器
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int correct = AppSingle.getCorrect();
                int total = AppSingle.getTotalScore();
                double rate = (double)correct/(double)total * 100;

                textView.setText("本次测试您的分数是："+correct+"分/"+total+"分, \n 正确率"+rate+"%, 继续加油吧！");
            }
        });

        //返回键的监听器
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}