package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity {

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO：发送phone number和password
                //TODO：加验证密码+TODO：加入global variable，记录当前用户的id
                finish();
            }
        });
    }


}