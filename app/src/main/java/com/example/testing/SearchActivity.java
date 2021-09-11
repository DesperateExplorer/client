package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import com.example.testing.activity.EntityActivity;
import com.example.testing.adapter.SearchListAdapter;
import com.example.testing.jsonTool.ListEntity;
import com.example.testing.jsonTool.SearchListEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class SearchActivity extends AppCompatActivity {


    // 初始化搜索框变量
    private SearchView searchView;
    private TextView subject_hint;
    private TextView sort_textView;
    private TextView filter_textView;
    private ArrayList<String> result = new ArrayList<>();

    private ListView listView;
//    private BaseAdapter adapter;
    private int counter;
    Integer sort = 0;
    Integer filter = 100;
    String currentSubject;
    MyApplication myApp;
    ArrayList<SearchListEntity> list;
    ArrayList<String> label = new ArrayList<>();
    ArrayList<String> uri = new ArrayList<>();
    ArrayList<Boolean> visited = new ArrayList<>();
    SearchListAdapter adapter;
    private Activity _this = this;

    private HashMap<String,Integer> mapping = new HashMap<String,Integer>(){
        {
            put("默认排序",0);
            put("升序",1);
            put("降序",2);
            put("音序升序",3);
            put("音序降序",4);
        }
    };

    private HashMap<String,Integer> mapping2 = new HashMap<String,Integer>(){
        {
            put("无筛选",11);
            put("长度筛选:3",3);
            put("长度筛选:5",5);
            put("长度筛选:7",7);
            put("长度筛选:10",10);
        }
    };


    // 设置popup Menu
    private void showPopupMenu(View view) {
        // 这里的view代表popupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(SearchActivity.this, view);

        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.show();

        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sort_textView.setText(item.getTitle());
                sort = mapping.get((String) item.getTitle());
                return true;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // 控件消失时的事件
            }
        });

    }

    private void showPopupMenu2(View view) {
        PopupMenu popupMenu2 = new PopupMenu(SearchActivity.this, view);

        popupMenu2.getMenuInflater().inflate(R.menu.popup_menu2, popupMenu2.getMenu());
        popupMenu2.show();

        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                filter_textView.setText(item.getTitle());
                filter = mapping2.get((String) item.getTitle());
                return true;
            }
        });
        popupMenu2.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // 控件消失时的事件
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 绑定视图
        setContentView(R.layout.activity_search);

        // 绑定组件
        searchView = (SearchView) findViewById(R.id.search_view);
        sort_textView = findViewById(R.id.sort);
        filter_textView = findViewById(R.id.filter);
        listView = (ListView)findViewById(R.id.list_view);
        subject_hint = findViewById(R.id.subject_hint);

        subject_hint.setText("当前选择的学科是："+getIntent().getStringExtra("subject"));

        // 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

                //DeprecateTODO：string是用户输入的关键词，需要存
                //TODONE：调用网络接口得到result
                //parameters: string, subject, sort, filter

                //subject
                Intent intent = getIntent();
                currentSubject = intent.getStringExtra("subject");
                System.out.println("search activity: "+currentSubject);
                //sort: 本文件声明的sort

                //Deprecate: 把string写入本地缓存
//                AppSingle.addKeyWord(string);

                OkHttpClient client = new OkHttpClient();
                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme(AppSingle.scheme)
                        .host(AppSingle.host)
                        .port(AppSingle.port)
                        .addPathSegment("searchInstance")
                        .addQueryParameter("userId", AppSingle.getUsername())
                        .addQueryParameter("keyword", string)
                        .addQueryParameter("course", AppSingle.SUBJECT2ENG.get(currentSubject))
                        .addQueryParameter("sort", sort.toString())
                        .addQueryParameter("filter", filter.toString())
                        .build();
                Request request = new Request.Builder()
                        .url(httpUrl).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.err.println(AppSingle.failMsg);
                        e.printStackTrace();
                        _this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "错误：对不起，OpenEduKG或网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        JsonArray jsonData = JsonParser.parseString(response.body().string()).getAsJsonArray();
                        Gson gson = new Gson();
                        list = gson.fromJson(jsonData,new TypeToken<List<SearchListEntity>>(){}.getType());
                        label = new ArrayList<>();
                        uri = new ArrayList<>();
                        visited = new ArrayList<>();
                        for(SearchListEntity searchListEntity: list){
                            label.add(searchListEntity.getLabel());
                            uri.add(searchListEntity.getUri());
                            visited.add(AppSingle.checkEntity(searchListEntity.getUri(),currentSubject));
                        }
                        _this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new SearchListAdapter(SearchActivity.this, label, list, visited, R.layout.item);
                                listView.setAdapter(adapter);
                            }
                        });
                    }
                });


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                        OkHttpClient client = new OkHttpClient();
                        HttpUrl httpUrl = new HttpUrl.Builder()
                                .scheme(AppSingle.scheme)
                                .host(AppSingle.host)
                                .port(AppSingle.port)
                                .addPathSegment("instanceDetail")
                                .addQueryParameter("userId", AppSingle.getUsername())
                                .addQueryParameter("label", label.get(i))
                                .addQueryParameter("course", AppSingle.SUBJECT2ENG.get(currentSubject))
                                .addQueryParameter("uri", uri.get(i))
                                .build();
                        Request request = new Request
                                .Builder().url(httpUrl).build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                AppSingle.detail = AppSingle.aCache.getAsJSONObject(AppSingle.getCacheKey(AppSingle.SUBJECT2ENG.get(currentSubject), uri.get(i)));
                                if(AppSingle.detail == null) {
                                    _this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "网络异常 且 没有缓存！", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    _this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //告诉adapter
                                            adapter.notifyDataSetChanged();
                                            System.out.println(label.get(i));

                                            Toast.makeText(getApplicationContext(), "从本地缓存加载", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SearchActivity.this,EntityActivity.class);
                                            intent.putExtra("label", label.get(i));
                                            intent.putExtra("uri", uri.get(i));
                                            intent.putExtra("subject", AppSingle.SUBJECT2ENG.get(currentSubject));
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String result = response.body().string();
                                System.err.println(result);
                                if(response.isSuccessful()) {
                                    try {
                                        AppSingle.detail = new JSONObject(result);
                                        AppSingle.aCache.put(AppSingle.getCacheKey(AppSingle.SUBJECT2ENG.get(currentSubject), uri.get(i)), AppSingle.detail);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //加入访问列表
                                    AppSingle.addLabel(label.get(i));
                                    AppSingle.addUri(uri.get(i));
                                    AppSingle.addSubject(currentSubject);
                                    System.out.println(currentSubject);
                                    visited.set(i,true);
                                    _this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //告诉adapter
                                            adapter.notifyDataSetChanged();
                                            System.out.println(label.get(i));

                                            Intent intent = new Intent(SearchActivity.this,EntityActivity.class);
                                            intent.putExtra("label", label.get(i));
                                            intent.putExtra("uri", uri.get(i));
                                            intent.putExtra("subject", AppSingle.SUBJECT2ENG.get(currentSubject));
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    System.err.println("response code: " + response.code());
                                }
                            }
                        });

                    }
                });
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });

        //设置排序方式
        sort_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        filter_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu2(view);
            }
        });
    }

}