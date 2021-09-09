package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testing.AppSingle;
import com.example.testing.R;
import com.example.testing.SearchActivity;
import com.example.testing.adapter.SearchListAdapter;
import com.example.testing.jsonTool.SearchListEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.SearchView;

public class LinkActivity extends AppCompatActivity {

    // 初始化搜索框变量
    private TextView textView;
    private ArrayList<String> result = new ArrayList<>();

    private ListView listView;
    String currentSubject;
    ArrayList<SearchListEntity> list;
    ArrayList<String> label = new ArrayList<>();
    ArrayList<String> uri = new ArrayList<>();
    ArrayList<Integer> visited = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        EditText editText = findViewById(R.id.link_input);
        Button button = findViewById(R.id.link_finish);
        listView = (ListView)findViewById(R.id.link_list_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:发送网络请求:input,subject
                Editable input = editText.getText();
                Intent intent = getIntent();
                currentSubject = intent.getStringExtra("subject");
                System.out.println("link activity: "+currentSubject);

                //返回实体列表：[{'label','url'}]
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

//                //检查当前实体是不是已经被访问
//                for(String s :uri)
//                {
//                    boolean t = myApp.checkEntity(s,currentSubject);
//                    if(t == true){
//                        visited.add(1);
//                    }
//
//                    else {
//                        visited.add(0);
//                    }
//                }

                BaseAdapter adapter = new ArrayAdapter<String>(LinkActivity.this, android.R.layout.simple_list_item_1, label);
                listView.setAdapter(adapter);

//                SearchListAdapter adapter = new SearchListAdapter(SearchActivity.this, label, list, visited, R.layout.item);
//                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //TODO：label，subject 获得实体详情页的json
                        Intent intent = new Intent(LinkActivity.this,EntityActivity.class);

                        //加入历史访问列表
                        AppSingle.addLabel(label.get(i));
                        AppSingle.addUri(uri.get(i));
                        AppSingle.addSubject(currentSubject);
                        System.out.println(currentSubject);
                        visited.set(i,1);

//                        //告诉adapter
//                        adapter.notifyDataSetChanged();

                        intent.putExtra("label", label.get(i));
                        intent.putExtra("uri", uri.get(i));
                        intent.putExtra("subject", currentSubject);
                        startActivity(intent);
                    }
                });
            }
        });

        //TODO：前端，写部分文字高亮+可以点击
    }
}