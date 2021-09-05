package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import com.example.testing.activity.EntityActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class search extends AppCompatActivity {


    // 初始化搜索框变量
    private SearchView searchView;
    private TextView textView;
    private ArrayList<String> result = new ArrayList<>();

    private ListView listView;
    private BaseAdapter adapter;
    private int counter;

    // 设置popup Menu
    private void showPopupMenu(View view) {
        // 这里的view代表popupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(search.this, view);
        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.show();
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 控件每一个item的点击事件
                System.out.println(item);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 绑定视图
        setContentView(R.layout.activity_search);

        // 绑定组件
        searchView = (SearchView) findViewById(R.id.search_view);
        textView = findViewById(R.id.sort);
        listView = (ListView)findViewById(R.id.list_view);

        // 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                //调用网络接口得到result
                //System.out.println("我收到了" + string);
                //result = func(string, subject)

                result.add("高原山地气候");
                result.add("帕米尔高原");
                result.add("青藏高原湖区");
                result.add("黄土高原");
                result.add("高原山地气候");
                result.add("青藏高原区");
                result.add("内蒙古高原");
                result.add("高原");
                result.add("青藏高原");
                result.add("巴西高原");
                result.add("蒙古高原");
                result.add("高原和高山气候");
                result.add("高原山地气候");
                result.add("德干高原");
                result.add("云贵高原");
                result.add("四大高原");
                result.add("高原高山区");
                result.add("马达加斯加岛的东侧、澳大利亚的东北部、巴西高原东南沿海和中美地峡的东侧等地的热带 ");
                result.add("高原");
                result.add("黄土高原");
                result.add("中西伯利亚高原");

                final ArrayList<String> list = new ArrayList<>();
                counter = 0;
                int oldCounter = counter;
                for (counter = oldCounter; counter < oldCounter + 15 && counter<result.size(); counter++) {
                    list.add(result.get(counter));
                }

                adapter = new ArrayAdapter<String>(search.this, android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (firstVisibleItem != 0) { // 不为0则表示有下拉动作
                            if ((firstVisibleItem + visibleItemCount) > totalItemCount - 2) { // 当前第一个完全可见的item再下拉一个页面长度，即变为倒数第二个时
                                // 在此加载数据
                                int oldCounter = counter;
                                for (counter = oldCounter; counter < oldCounter + 15 && counter<result.size(); counter++) {
                                    list.add(result.get(counter));
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
                
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(search.this,EntityActivity.class);
                        intent.putExtra("label", list.get(i));
                        startActivity(intent);
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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }
}