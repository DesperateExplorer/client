package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.AppSingle;
import com.example.testing.R;
import com.example.testing.SearchActivity;
import com.example.testing.adapter.PlusTestAdapter;
import com.example.testing.adapter.RelationAdapter;
import com.example.testing.jsonTool.QuestionList;
import com.example.testing.jsonTool.ShowRelation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlusTestActivity extends AppCompatActivity {

    TextView set_course;
    String course; //eng
    Activity _this = this;

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
        set_course = findViewById(R.id.choose_course);

        //设置接收数据的局部变量
        ArrayList<QuestionList> Data = new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //TODO:从searchView中拿到需要搜索的关键词
                String keyword = searchView.getQuery().toString();

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60000, TimeUnit.MILLISECONDS).writeTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS)
                        .build();
                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme(AppSingle.scheme)
                        .host(AppSingle.host)
                        .port(AppSingle.port)
                        .addPathSegment("questionList")
                        .addQueryParameter("uriName", keyword)
                        .addQueryParameter("course", course)
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
                        if(response.isSuccessful()) {
                            JsonArray jsonArray = JsonParser.parseString(response.body().string()).getAsJsonArray();
                            for(JsonElement element: jsonArray) {
                                Data.add(new QuestionList(
                                        element.getAsJsonObject().get("answer").getAsString(),
                                        element.getAsJsonObject().get("body").getAsString(),
                                        element.getAsJsonObject().get("branchA").getAsString(),
                                        element.getAsJsonObject().get("branchB").getAsString(),
                                        element.getAsJsonObject().get("branchC").getAsString(),
                                        element.getAsJsonObject().get("branchD").getAsString()
                                ));
                            }
                            _this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    PlusTestAdapter adapter = new PlusTestAdapter(PlusTestActivity.this, Data, R.layout.question_item);
                                    listView.setAdapter(adapter);
                                }
                            });
                        } else {
                        }

                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        //QuestionList: 需要传入listView的数据格式，可以从jsonTool文件夹中找到
        //下面是一个例子
//        Data.add(new QuestionList("B","第17届亚运会于2014年9月19日—10月4日在韩国仁川举行,我国运动员取得了优异的成绩。关于韩国与我国的位置关系说法正确的是()","A.陆上相邻","B.隔海相望","C.既陆上相邻又隔海相望","D.既不陆上相邻又不隔海相望"));
//        Data.add(new QuestionList("B","第17届亚运会于2014年9月19日—10月4日在韩国仁川举行,我国运动员取得了优异的成绩。关于韩国与我国的位置关系说法正确的是()","A.陆上相邻","B.隔海相望","C.既陆上相邻又隔海相望","D.既不陆上相邻又不隔海相望"));



        //为提交按钮设置监听器
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int correct = AppSingle.getCorrect();
                int total = AppSingle.getTotalScore();
                if(total>0)
                {
                    double rate = (double)correct/(double)total * 100;
                    textView.setText("本次测试您的分数是："+correct+"分/"+total+"分, \n 正确率"+rate+"%, 继续加油吧！");
                }
                else{
                    textView.setText("快开始做题吧！");
                }
            }
        });

        //返回键的监听器
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //设置学科选择
        set_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }

    // 设置popup Menu
    private void showPopupMenu(View view) {
        // 这里的view代表popupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(PlusTestActivity.this, view);

        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.course_menu, popupMenu.getMenu());
        popupMenu.show();

        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                set_course.setText(item.getTitle());
                course = AppSingle.SUBJECT2ENG.get((String) item.getTitle());
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
}