package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import com.example.testing.activity.EntityActivity;
import com.example.testing.adapter.SearchListAdapter;
import com.example.testing.jsonTool.ListEntity;
import com.example.testing.jsonTool.SearchListEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class SearchActivity extends AppCompatActivity {


    // 初始化搜索框变量
    private SearchView searchView;
    private TextView sort_textView;
    private TextView filter_textView;
    private ArrayList<String> result = new ArrayList<>();

    private ListView listView;
//    private BaseAdapter adapter;
    private int counter;
    Integer sort;
    Integer filter;
    String currentSubject;
    MyApplication myApp;
    ArrayList<SearchListEntity> list;
    ArrayList<String> label = new ArrayList<>();
    ArrayList<String> uri = new ArrayList<>();
    ArrayList<Integer> visited = new ArrayList<>();

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

        // 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

                //TODO：string是用户输入的关键词，需要存
                //TODO：调用网络接口得到result
                //parameters: string, subject, sort, filter

                //subject
                Intent intent = getIntent();
                currentSubject = intent.getStringExtra("subject");
                System.out.println("search activity: "+currentSubject);
                //sort: 本文件声明的sort

                //把string写入本地缓存
                ((MyApplication)getApplication()).addKeyWord(string);

                //得到的结果
                String jsonData = "[{\n" +
                        "\t\"label\": \"高原山地气候\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-00394d65c2457f426594cadeb580c0d2\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"帕米尔高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-02f4536779809332f1b5d983a62d7f56\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"青藏高原湖区\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-04449fbfae465d7b992e2a6793966058\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"黄土高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-0572e26c53bb893644c2ef0fbdc4fd5f\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"高原山地气候\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-07831daaee3979437343286fa86ea5fc\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"青藏高原区\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-08d8377b7756d4a741841ad2353753c9\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"内蒙古高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-13a1db4c3bd7cb738f29c2155d63ff80\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-182df86eeea94e47f4eeb25f482a26ea\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"青藏高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-1cd960145524ae1ebcd10cbf0cb228bc\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"巴西高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-2628c2a0028eac88b1435039f197efea\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"蒙古高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-2e40bad3fdbba6a4b0413b4e5ef82aaa\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"高原和高山气候\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-4f8acd02869a00b72345f7145a5944a4\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"高原山地气候\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-53274100a4a242d6acc6a96c286591f9\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"德干高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-574a9f20bb45a0d97dcc7c1bd2adc032\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"云贵高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-61d51de2e3b2b5c5a9c58ac93741a900\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"四大高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-9fc6d4b3c83fcba1e187f447a7f117bc\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"高原高山区\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-ac019b1ceaccfed6abb78dd264df05f3\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"伊朗高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-c7abb9664aa5df27793c1ccc10b88945\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"马达加斯加岛的东侧、澳大利亚的东北部、巴西高原东南沿海和中美地峡的东侧等地的热带 ..\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-e9147b0d1182cbc114f20dec2b274686\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-eabb4d4b96a0a4dae649d9ca41d4be52\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"黄土高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-ebf76fed513c4fb763cdef0dc18eeb21\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"黄土高原的黄土是从我国西北内陆吹来的\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-f0027938b7aca6ed8cd1792cd8dd5714\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"中西伯利亚高原\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-f0675a357bcb560cb96844e7d6cb97a3\"\n" +
                        "}, {\n" +
                        "\t\"label\": \"赤道附近的东非高原呈现热带草原景观\",\n" +
                        "\t\"uri\": \"http://edukb.org/knowledge/0.1/instance/geo#-fc45fef52d35a9d33f2e04c9286ac45e\"\n" +
                        "}]";

                Gson gson = new Gson();
                list = gson.fromJson(jsonData,new TypeToken<List<SearchListEntity>>(){}.getType());
                label = new ArrayList<>();
                uri = new ArrayList<>();
                visited = new ArrayList<>();

                for(SearchListEntity searchListEntity: list){
                    label.add(searchListEntity.getLabel());
                    uri.add(searchListEntity.getUri());
                }

                myApp = (MyApplication) getApplication();
                //检查当前实体是不是已经被访问
                for(String s :uri)
                {
                    boolean t = myApp.checkEntity(s,currentSubject);
                    if(t == true){
                        visited.add(1);
                    }

                    else {
                        visited.add(0);
                    }
                }

//                BaseAdapter adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, label);
//                listView.setAdapter(adapter);

                SearchListAdapter adapter = new SearchListAdapter(SearchActivity.this, label, list, visited, R.layout.item);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //TODO：label，subject 获得实体详情页的json
                        Intent intent = new Intent(SearchActivity.this,EntityActivity.class);

                        //加入访问列表
                        myApp.addLabel(label.get(i));
                        myApp.addUri(uri.get(i));
                        myApp.addSubject(currentSubject);
                        System.out.println(currentSubject);
                        visited.set(i,1);

                        //告诉adapter
                        adapter.notifyDataSetChanged();

                        intent.putExtra("label", label.get(i));
                        intent.putExtra("uri", uri.get(i));
                        intent.putExtra("subject", currentSubject);
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