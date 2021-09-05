package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testing.R;

public class LinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        EditText editText = findViewById(R.id.link_input);
        Button button = findViewById(R.id.link_finish);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:发送网络请求:input,subject
                Editable input = editText.getText();
                Intent intent = getIntent();
                String subject = intent.getStringExtra("subject");
                System.out.println("link activity: "+subject);
                //返回实体列表：[{'label','url'}]
            }
        });

        //TODO：前端，写部分文字高亮+可以点击
    }
}