package com.example.testing.activity;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.example.testing.AppSingle;
import com.example.testing.MyApplication;
import com.example.testing.R;
import com.example.testing.adapter.EntityAdapter;
import com.example.testing.jsonTool.EntityDescription;
import com.example.testing.jsonTool.QuestionList;
import com.example.testing.jsonTool.EntityProperty;
import com.example.testing.jsonTool.ShowRelation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EntityActivity extends QMUIFragmentActivity {

    private TextView textView;
    private ImageView imageView;
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    ImageView share;
    ImageView star;
    boolean isStarred = false;
    String label;
    String uri;
    String subject;
    private Drawable myTasksDrawable;
    Activity _this = this;

    private JSONObject detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);

        Intent intent = getIntent();
        label = intent.getStringExtra("label");
        uri = intent.getStringExtra("uri");
        subject = intent.getStringExtra("subject"); // already in Eng
        detail = AppSingle.detail;


        //TODO：先给后端发请求，看能不能拿到实体详情页的json
        //TODO：如果失败，则获取实体详情页的缓存信息
        // 这里的逻辑比较神奇，四个fragment需要从这个entityActivity中获得他们需要的数据
        // 我采用的方式是在这个类中定义四个接口函数，
        // 描述：EntityDescription1,EntityDescription2
        // 属性：EntityProperty
        // 关系：用entityContent1，entityContent2 解析json，最终整合到ShowRelation数组中
        // 问题：QuestionList


        // 绑定元件
        textView = findViewById(R.id.entity_header);
        imageView = findViewById(R.id.back_to_list);
        // Initialize the ViewPager and set an adapter
        pager = (ViewPager) findViewById(R.id.entity_pager);
        pager.setAdapter(new EntityAdapter(getSupportFragmentManager()));
        // Bind the tabs to the ViewPager
        tabs = findViewById(R.id.entity_tabs);
        tabs.setShouldExpand(true);
        tabs.setViewPager(pager);
        tabs.setTextSize(35);
        star = findViewById(R.id.star);
        share = findViewById(R.id.share);

        // 添加动作
        textView.setText(label);
        textView.setTextSize(30);

        if (AppSingle.checkStarEntity(uri, subject)) {
            myTasksDrawable = star.getDrawable();
            myTasksDrawable.setTint(getResources().getColor(R.color.yellow));
            isStarred = true;
        }

        //绑定收藏监听器
        //TODONE:向后端发送收藏信息更新请求
        star.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                OkHttpClient starClient = new OkHttpClient();
                FormBody starBody = new FormBody
                        .Builder()
                        .add("userId", AppSingle.getUsername())
                        .add("label", label)
                        .add("uri", uri)
                        .add("course", subject)
                        .add("favorite", Boolean.valueOf(!isStarred).toString())
                        .build();
                Request starRequest = new Request
                        .Builder()
                        .url(AppSingle.baseUrl + "/favorite")
                        .post(starBody)
                        .build();
                Call starCall = starClient.newCall(starRequest);
                starCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.err.println("star failed");
                        _this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "更改收藏状态失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.err.println(response.body().string());
                        if(response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = AppSingle.aCache.getAsJSONObject(AppSingle.getCacheKey(subject, uri));
                                jsonObject.put("favorite", !isStarred);
                                AppSingle.aCache.put(AppSingle.getCacheKey(subject, uri), jsonObject);
                                if (isStarred == false) {
                                    myTasksDrawable = star.getDrawable();
                                    myTasksDrawable.setTint(getResources().getColor(R.color.yellow));
                                    isStarred = true;
                                    //缓存到本地
                                    AppSingle.addStarLabel(label);
                                    AppSingle.addStarUri(uri);
                                    AppSingle.addStarSubject(subject);

                                } else {
                                    myTasksDrawable = star.getDrawable();
                                    myTasksDrawable.setTint(getResources().getColor(R.color.gray));
                                    isStarred = false;
                                    //缓存到本地
                                    int i = AppSingle.findId(uri, subject);
                                    AppSingle.removeStarLabel(i);
                                    AppSingle.removeStarUri(i);
                                    AppSingle.removeStarSubject(i);
                                }
                                System.err.println("star flipped");
                                _this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "更改收藏状态成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                System.err.println(e.getMessage());
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("star failed");
                        }
                    }
                });

            }
        });

        //绑定返回监听器
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //分享功能
        //当imageView被点击时显示：
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog ShareDialog = new Dialog(EntityActivity.this, R.style.my_dialog);
                LinearLayout root = (LinearLayout) LayoutInflater.from(EntityActivity.this).inflate(R.layout.share, null);

                TextView content = root.findViewById(R.id.content);
                //取消按钮
                root.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShareDialog.dismiss();
                    }
                });
                ShareDialog.setContentView(root);
                Window dialogWindow = ShareDialog.getWindow();
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
//                WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//                lp.x = 0; // 新位置X坐标
//                lp.y = -40; // 新位置Y坐标
//                lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
//                root.measure(0, 0);
//                lp.height = root.getMeasuredHeight();
//                lp.alpha = 9f; // 透明度
//                dialogWindow.setAttributes(lp);
                ShareDialog.show();
            }
        });

    }

    public ArrayList<String> getDescription()
    {
        JSONArray jsonData = new JSONArray();
//        JSONArray jsonData = "[{\n" +
//                "\t\t\t'feature_key': '下属于',\n" +
//                "\t\t\t'feature_value': '整式方程'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '定义',\n" +
//                "\t\t\t'feature_value': '只含有一个未知数，并且未知数的最高次数是2的整式方程叫做一元二次方程'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质1',\n" +
//                "\t\t\t'feature_value': '一元二次方程的一般形式是ax^2＋bx＋c=0（a≠0）'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质2',\n" +
//                "\t\t\t'feature_value': '通过开平方运算解一元二次方程的方法叫做直接开平方法'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质3',\n" +
//                "\t\t\t'feature_value': '对于一个一元二次方程，首先利用恒等变形通过配方把它化成一边是含有未知数的完全平方的形式，另一边是非负常数，再用开平方法解方程的方法就是配方法'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质4',\n" +
//                "\t\t\t'feature_value': '公式法是用求根公式求出一元二次方程的解的方法，它是解一元二次方程的一般方法'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质5',\n" +
//                "\t\t\t'feature_value': '当一元二次方程的一边为0，而另一边易分解成两个一次因式的乘积时，可分别得到两个一元一次方程，从而达到“降次”的目的，得到的两个解就是一元二次方程的解，这种解方程的方法叫做因式分解法'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质6',\n" +
//                "\t\t\t'feature_value': '配方法解一元二次方程的一般步骤：（1）移项：将常数项移到方程右边.（2）把二次项系数化为1：方程左右两边同时除以二次项系数.（3）配方：方程左右两边同时加上一次项系数一半的平方，把原方程化为（x＋m）2=n的形式.（4）用直接开平方法解变形后的方程'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质7',\n" +
//                "\t\t\t'feature_value': '一般地，常用字母“△”表示b^2－4ac，即Δ=b^2－4ac'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质8',\n" +
//                "\t\t\t'feature_value': '一元二次方程ax^2＋bx＋c=0（a≠0）,当Δ=b^2－4ac＞0时，方程有两个不相等的实数根'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质9',\n" +
//                "\t\t\t'feature_value': '一元二次方程ax^2＋bx＋c=0（a≠0）,当Δ=b^2－4ac=0时，方程有两个相等的实数根'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质10',\n" +
//                "\t\t\t'feature_value': '一元二次方程ax^2＋bx＋c=0（a≠0）,当Δ=b^2－4ac＜0时，方程没有实数根'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质11',\n" +
//                "\t\t\t'feature_value': '公式法解一元二次方程的一般步骤：（1）将一元二次方程整理成一般形式；（2）确定公式中a，b，c的值；（3）求出b2－4ac的值；（4）当b2－4ac≥0时，将a，b，c的值及b2－4ac的值代入求根公式即可；当b2－4ac＜0时，方程无实数根'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质12',\n" +
//                "\t\t\t'feature_value': '因式分解法解一元二次方程的一般步骤（1）将方程的右边化为0；（2）将方程的左边分解为两个一次因式的乘积；（3）令每个因式分别为零，得到两个一元一次方程；（4）解这两个一元一次方程，它们的解就是原方程的解'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质13',\n" +
//                "\t\t\t'feature_value': '如果ax^2＋bx＋c=0（a≠0）的两个实数根是x1，x2，那么x1＋x2=－b/a，x1x2=c/a'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质14',\n" +
//                "\t\t\t'feature_value': '如果方程x^2＋px＋q=0的两个根是x1，x2，那么x1＋x2=－p，x1x2=q'\n" +
//                "\t\t}, {\n" +
//                "\t\t\t'feature_key': '性质15',\n" +
//                "\t\t\t'feature_value': '以两个数x1，x2为根的一元二次方程（二次项系数为1）是x^2－（x1＋x2）x＋x1x2=0'\n" +
//                "\t\t}]";

        ArrayList<String> result = new ArrayList<>();
        try {
            jsonData = detail.getJSONArray("description");
            for(int i = 0; i < jsonData.length(); ++i) {
                result.add(jsonData.getJSONObject(i).getString("feature_key")+": "+jsonData.getJSONObject(i).getString("feature_value"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("qocnhwhejrfscjcsndfcsdf: " + jsonData.toString());
//        Gson gson = new Gson();
//        List<EntityDescription> list = gson.fromJson(jsonData,new TypeToken<List<EntityDescription>>(){}.getType());

//        for(EntityDescription entityDescription: list){
//            result.add(entityDescription.getFeature_key()+": "+entityDescription.getFeature_value());
//        }
        return result;
    }

    public ArrayList<EntityProperty> getProperty()
    {
        String jsonData = new String();
//        String jsonData = "[{\n" +
//                "                \"predicateLabel\": \"强相关于\",\n" +
//                "                \"objectLabel\": \"边\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"predicateLabel\": \"强相关于\",\n" +
//                "                \"objectLabel\": \"三角\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"predicateLabel\": \"出处\",\n" +
//                "                \"objectLabel\": \"1.2.14.1.3\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"predicateLabel\": \"名称\",\n" +
//                "                \"objectLabel\": \"余弦定理\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"predicateLabel\": \"页码\",\n" +
//                "                \"objectLabel\": \"高中数学知识清单133\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"predicateLabel\": \"强相关于\",\n" +
//                "                \"objectLabel\": \"角\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"predicateLabel\": \"内容\",\n" +
//                "                \"objectLabel\": \"三角形任何一边的平方等于其他两边的平方和减去这两边与它们夹角的余弦的积的两倍\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"predicateLabel\": \"强相关于\",\n" +
//                "                \"objectLabel\": \"三角形\"\n" +
//                "            }\n" +
//                "        ]";
        try {
            jsonData = detail.getJSONArray("property").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        ArrayList<String> tmp = new ArrayList<>();
        Gson gson = new Gson();
        ArrayList<EntityProperty> list = gson.fromJson(jsonData,new TypeToken<List<EntityProperty>>(){}.getType());
        return list;
    }
    public ArrayList<String> getPredicate()
    {
        ArrayList<EntityProperty> list = getProperty();
        ArrayList<String> result = new ArrayList<>();
        for(EntityProperty entityProperty: list){
            result.add(entityProperty.getPredicateLabel());
        }
        return result;
    }

    public ArrayList<String> getObject()
    {
        ArrayList<EntityProperty> list = getProperty();
        ArrayList<String> result = new ArrayList<>();
        for(EntityProperty entityProperty: list){
            result.add(entityProperty.getObjectLabel());
        }
        return result;
    }

    public ArrayList<QuestionList> getPractice()
    {
        String jsonData = new String();
//        String jsonData = "[{\n" +
//                "\t\"answer\": \"B\",\n" +
//                "\t\"body\": \"下列与我国隔海相望的国家中,纬度位置最高的是()\",\n" +
//                "\t\"branchA\": \"A.韩国\",\n" +
//                "\t\"branchB\": \"B.日本\",\n" +
//                "\t\"branchC\": \"C.印度尼西亚\",\n" +
//                "\t\"branchD\": \"D.菲律宾\"\n" +
//                "}, {\n" +
//                "\t\"answer\": \"D\",\n" +
//                "\t\"body\": \"近日朝韩局势日益紧张,如要了解朝鲜、韩国的位置,应查阅()\",\n" +
//                "\t\"branchA\": \"A.中国政区图\",\n" +
//                "\t\"branchB\": \"B.城市导游图\",\n" +
//                "\t\"branchC\": \"C.中国气候图\",\n" +
//                "\t\"branchD\": \"D.世界政治地图\"\n" +
//                "}, {\n" +
//                "\t\"answer\": \"A\",\n" +
//                "\t\"body\": \"我国在朝核六方会谈中占很重的分量,除了是因为我国是国际大国外,还与其中两国接壤,他们是()\",\n" +
//                "\t\"branchA\": \"A.俄罗斯、朝鲜\",\n" +
//                "\t\"branchB\": \"B.韩国、俄罗斯\",\n" +
//                "\t\"branchC\": \"C.朝鲜、日本\",\n" +
//                "\t\"branchD\": \"D.日本、朝鲜\"\n" +
//                "}, {\n" +
//                "\t\"answer\": \"D\",\n" +
//                "\t\"body\": \"与我国隔海相望的一组国家是()\",\n" +
//                "\t\"branchA\": \"A.印度、文莱、印度尼西亚\",\n" +
//                "\t\"branchB\": \"B.马来西亚、朝鲜、韩国\",\n" +
//                "\t\"branchC\": \"C.日本、朝鲜、印度尼西亚\",\n" +
//                "\t\"branchD\": \"D.文莱、马来西亚、印度尼西亚\"\n" +
//                "}, {\n" +
//                "\t\"answer\": \"D\",\n" +
//                "\t\"body\": \"下列四组国家中,全部与我国隔海相望的一组国家是()\",\n" +
//                "\t\"branchA\": \"A.印度、文莱、菲律宾\",\n" +
//                "\t\"branchB\": \"B.马来西亚、朝鲜、韩国\",\n" +
//                "\t\"branchC\": \"C.日本、朝鲜、印度尼西亚\",\n" +
//                "\t\"branchD\": \"D.文莱、菲律宾、日本\"\n" +
//                "}, {\n" +
//                "\t\"answer\": \"B\",\n" +
//                "\t\"body\": \"下列国家与我国陆地接壤的是()\",\n" +
//                "\t\"branchA\": \"A.韩国\",\n" +
//                "\t\"branchB\": \"B.朝鲜\",\n" +
//                "\t\"branchC\": \"C.日本\",\n" +
//                "\t\"branchD\": \"D.泰国\"\n" +
//                "}, {\n" +
//                "\t\"answer\": \"D\",\n" +
//                "\t\"body\": \"近年来,日本与邻国因岛屿争端,关系日益紧张。下列不属于日本近邻的国家是()\",\n" +
//                "\t\"branchA\": \"A.中国\",\n" +
//                "\t\"branchB\": \"B.韩国\",\n" +
//                "\t\"branchC\": \"C.朝鲜\",\n" +
//                "\t\"branchD\": \"D.印度\"\n" +
//                "}, {\n" +
//                "\t\"answer\": \"B\",\n" +
//                "\t\"body\": \"下列各国家中,人均国民生产总值最高的是()\",\n" +
//                "\t\"branchA\": \"A.印度、巴基斯坦\",\n" +
//                "\t\"branchB\": \"B.日本、新加坡\",\n" +
//                "\t\"branchC\": \"C.沙特阿拉伯、以色列\",\n" +
//                "\t\"branchD\": \"D.韩国、马来西亚\"\n" +
//                "}, {\n" +
//                "\t\"answer\": \"D\",\n" +
//                "\t\"body\": \"与我国隔海相望的国家有()\",\n" +
//                "\t\"branchA\": \"A.朝鲜、韩国、日本\",\n" +
//                "\t\"branchB\": \"B.越南、菲律宾、文莱\",\n" +
//                "\t\"branchC\": \"C.缅甸、越南、文莱\",\n" +
//                "\t\"branchD\": \"D.韩国、菲律宾、印度尼西亚\"\n" +
//                "}, {\n" +
//                "\t\"answer\": \"B\",\n" +
//                "\t\"body\": \"第17届亚运会于2014年9月19日—10月4日在韩国仁川举行,我国运动员取得了优异的成绩。关于韩国与我国的位置关系说法正确的是()\",\n" +
//                "\t\"branchA\": \"A.陆上相邻\",\n" +
//                "\t\"branchB\": \"B.隔海相望\",\n" +
//                "\t\"branchC\": \"C.既陆上相邻又隔海相望\",\n" +
//                "\t\"branchD\": \"D.既不陆上相邻又不隔海相望\"\n" +
//                "}]";

//        ArrayList<String> tmp = new ArrayList<>();
        try {
            jsonData = detail.getJSONArray("questionList").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        ArrayList<QuestionList> list = gson.fromJson(jsonData,new TypeToken<List<QuestionList>>(){}.getType());
        return list;
    }

    public ArrayList<String> getQuestions()
    {
        ArrayList<QuestionList> list = getPractice();
        ArrayList<String> tmp = new ArrayList<>();
        for(QuestionList questionList : list)
        {
            tmp.add(questionList.getBody());
        }
        return tmp;
    }
    public ArrayList<String> getAnswer()
    {
        ArrayList<QuestionList> list = getPractice();
        ArrayList<String> tmp = new ArrayList<>();
        for(QuestionList questionList : list)
        {
            tmp.add(questionList.getAnswer());
        }
        return tmp;
    }
    public ArrayList<String> getBranchA()
    {
        ArrayList<QuestionList> list = getPractice();
        ArrayList<String> tmp = new ArrayList<>();
        for(QuestionList questionList : list)
        {
            tmp.add(questionList.getBranchA());
        }
        return tmp;
    }
    public ArrayList<String> getBranchB()
    {
        ArrayList<QuestionList> list = getPractice();
        ArrayList<String> tmp = new ArrayList<>();
        for(QuestionList questionList : list)
        {
            tmp.add(questionList.getBranchB());
        }
        return tmp;
    }
    public ArrayList<String> getBranchC()
    {
        ArrayList<QuestionList> list = getPractice();
        ArrayList<String> tmp = new ArrayList<>();
        for(QuestionList questionList : list)
        {
            tmp.add(questionList.getBranchC());
        }
        return tmp;
    }
    public ArrayList<String> getBranchD()
    {
        ArrayList<QuestionList> list = getPractice();
        ArrayList<String> tmp = new ArrayList<>();
        for(QuestionList questionList : list)
        {
            tmp.add(questionList.getBranchD());
        }
        return tmp;
    }

    public ArrayList<ShowRelation> getContent() {
        ArrayList<ShowRelation> result = new ArrayList<>();
        try {
            JSONArray content1 = detail.getJSONArray("content1");
            JSONArray content2 = detail.getJSONArray("content2");
            for(int i = 0; i < content1.length(); ++i) {

                JSONObject object = content1.getJSONObject(i);
                result.add(new ShowRelation(
                        object.getString("predicateLabel"),
                        object.getString("subjectLabel"),
                        label
                ));
            }
            for(int i = 0; i < content2.length(); ++i) {
                JSONObject object = content2.getJSONObject(i);
                result.add(new ShowRelation(
                        object.getString("predicateLabel"),
                        label,
                        object.getString("objectLabel")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
