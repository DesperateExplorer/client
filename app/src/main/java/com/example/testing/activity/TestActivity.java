package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.testing.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    String chosen = new String();
    String answer = new String();
    HashMap<Integer,String> map = new HashMap<Integer, String>(){
        {
            put(0,"A");
            put(1,"B");
            put(2,"C");
            put(3,"D");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //绑定组件
        TextView body = findViewById(R.id.body);
        TextView tv_answer = findViewById(R.id.answer);
        RadioGroup group = findViewById(R.id.radio_group);
        RadioButton A = findViewById(R.id.branchA);
        RadioButton B = findViewById(R.id.branchB);
        RadioButton C = findViewById(R.id.branchC);
        RadioButton D = findViewById(R.id.branchD);
        Button button = findViewById(R.id.submit);
        ImageButton back = findViewById(R.id.imageButton_back);

        //获取text
        Intent intent = getIntent();
        body.setText(intent.getStringExtra("question"));
        answer = intent.getStringExtra("answer");
        A.setText(intent.getStringExtra("A"));
        B.setText(intent.getStringExtra("B"));
        C.setText(intent.getStringExtra("C"));
        D.setText(intent.getStringExtra("D"));

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                chosen = map.get(i);
            }
        });

        //设置提交事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //把按钮取消可选
                A.setEnabled(false);
                B.setEnabled(false);
                C.setEnabled(false);
                D.setEnabled(false);


                tv_answer.setText("本题的答案是："+answer);
                tv_answer.setVisibility(View.VISIBLE);
            }
        });

        //设置返回事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}