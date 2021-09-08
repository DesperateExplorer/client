package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity {

    ImageView backButton;
    EditText username;
    EditText password;
    String UserID;
    MyApplication myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backButton = findViewById(R.id.BackButton);
        username = findViewById(R.id.Userid_edit_text);
        password = findViewById(R.id.Password_edit_text);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO：发送Username和password
                //TODO：加验证密码+TODO：加入global variable，记录当前用户的id
                myapp = (MyApplication) getApplication();
                UserID = username.getText().toString();
                myapp.setUsername(UserID);
                finish();
            }
        });
    }


}