package com.example.testing.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testing.AppSingle;
import com.example.testing.MyApplication;
import com.example.testing.R;
import com.example.testing.SearchActivity;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class StarListActivity extends AppCompatActivity {

    private BaseAdapter adapter;
    Activity _this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_list);

        //绑定组件
        ListView listView = findViewById(R.id.starList_listView);
        ImageView imageView = findViewById(R.id.starlist_back);
        ArrayList<String> label = AppSingle.getStarLabel();
        ArrayList<String> uri = AppSingle.getStarUri();
        ArrayList<String> subject = AppSingle.getStarSubject();

        adapter = new ArrayAdapter<String>(StarListActivity.this, android.R.layout.simple_list_item_1, label);
        listView.setAdapter(adapter);

        //跳转到实体详情页上,可以复用实体详情页activity界面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListenableFuture<JSONObject> future = AppSingle.service.submit(new Callable<JSONObject>() {
                    @Override
                    public JSONObject call() throws Exception {
                        return AppSingle.aCache.getAsJSONObject(AppSingle.getCacheKey(subject.get(i), uri.get(i)));
                    }
                });
                Futures.addCallback(future, new FutureCallback<JSONObject>() {
                    @Override
                    public void onSuccess(@Nullable JSONObject result) {
                        AppSingle.detail = result;
                        _this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "从本地缓存加载", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(StarListActivity.this,EntityActivity.class);
                                intent.putExtra("label", label.get(i));
                                intent.putExtra("uri", uri.get(i));
                                intent.putExtra("subject", subject.get(i));
                                intent.putExtra("backto","History");
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        _this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "加载缓存失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, AppSingle.service);
            }
        });

        //设置返回监听器
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}