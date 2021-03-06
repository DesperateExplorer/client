package com.example.testing;

import android.content.Context;
import com.example.testing.jsonTool.EntityContent1;
import com.example.testing.jsonTool.EntityDescription;
import com.example.testing.jsonTool.EntityProperty;
import com.example.testing.jsonTool.QuestionList;
import com.example.testing.util.ACache;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AppSingle {
    private static Context singleContext;
    private static AppSingle instance;

    private AppSingle(Context context) {
        singleContext = context;
    }

    public static synchronized AppSingle getInstance() {
        if(instance == null) {
            instance = new AppSingle(singleContext);
        }
        return instance;
    }

    /**
     * 存储的用户名
     */
    private static String username;
    public static void setUsername(String s)
    { username = s;
    }
    public static String getUsername()
    {
        return username;
    }

    /**
     * 网络请求相关
     */
    public final static String scheme = "http"; // can only be "http" or "https"
    public final static String host = "183.173.130.228";
    public final static int port = 8080;
    public final static String baseUrl = scheme + "://" + host + ":" + port;
    public final static String failMsg = "OpenEduKG or Network failed!";

    public static ACache aCache;
    public static String getCacheKey(String c, String u) {
        return c + "|" + u;// course + "|" + uri
    }

    /**
     * 缓存相关
     */
    public static JSONObject detail;
    public static void initDetail() {
        detail = new JSONObject();
        try {
            detail.put("uri", "");
            detail.put("label", "");
            detail.put("course", "");
            detail.put("favorite", false);
            detail.put("description", new JSONArray(new ArrayList<>()));
            detail.put("description1", new JSONArray(new ArrayList<>()));
            detail.put("property", new JSONArray(new ArrayList<>()));
            detail.put("content1", new JSONArray(new ArrayList<>()));
            detail.put("content2", new JSONArray(new ArrayList<>()));
            detail.put("questionList", new JSONArray(new ArrayList<>()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    /** 存储的历史访问实体列表
     *  {label, uri, subject} 三元组
     */
    private static ArrayList<String> historyLabel = new ArrayList<>();
    private static ArrayList<String> historyUri = new ArrayList<>();
    private static ArrayList<String> historySubject = new ArrayList<>();

    //set方法：login/register后初始化用
    public static void setHistoryLabel(ArrayList<String> s)
    {
        historyLabel = s;
    }
    public static void setHistoryUri(ArrayList<String> s)
    {
        historyUri = s;
    }
    public static void setHistorySubject(ArrayList<String> s)
    {
        historySubject = s;
    }
    //get方法：在需要展示列表的时候用
    public static ArrayList<String> getHistoryLabel()
    {
        return historyLabel;
    }
    public static ArrayList<String> getHistoryUri()
    {
        return historyUri;
    }
    public static ArrayList<String> getHistorySubject()
    {
        return historySubject;
    }
    //add方法：用户有新动作后，更新历史访问记录
    public static void addLabel(String label)
    {
        historyLabel.add(0,label);
    }
    public static void addUri(String Uri)
    {
        historyUri.add(0,Uri);
    }
    public static void addSubject(String subject)
    {
        historySubject.add(0,subject);
    }

    //查找方法：判断某个实体是不是已经被访问过
    public static boolean checkEntity(String uri, String subject)
    {
        System.out.println("history: ");
        System.out.println(uri);
        System.out.println(subject);
        for(int i = 0;i < historyUri.size();i++)
        {
            if(historyUri.get(i).equals(uri))
            {
                System.out.println("yes");
                System.out.println(historySubject.get(i));
                if(historySubject.get(i).equals(subject))
                    return true;
            }
        }
        return false;
    }

    public static void DedupHistory(String uri, String subject)
    {
        System.out.println("history: ");
        System.out.println(uri);
        System.out.println(subject);
        for(int i = 0;i < historyUri.size();i++)
        {
            if(historyUri.get(i).equals(uri))
            {
                System.out.println("yes");
                System.out.println(historySubject.get(i));
                if(historySubject.get(i).equals(subject)) {
                    historyLabel.remove(i);
                    historySubject.remove(i);
                    historyUri.remove(i);
                    return;
                }
            }
        }
        return;
    }


    /** 收藏实体列表
     *  {label, uri, subject} 三元组
     */
    private static ArrayList<String> starLabel = new ArrayList<>();
    private static ArrayList<String> starUri = new ArrayList<>();
    private static ArrayList<String> starSubject = new ArrayList<>();

    //set方法：login/register后初始化用
    public static void setStarLabel(ArrayList<String> s)
    {
        starLabel = s;
    }
    public static void setStarUri(ArrayList<String> s)
    {
        starUri = s;
    }
    public static void setStarSubject(ArrayList<String> s)
    {
        starSubject = s;
    }
    //get方法：在需要展示列表的时候用
    public static ArrayList<String> getStarLabel()
    {
        return starLabel;
    }
    public static ArrayList<String> getStarUri()
    {
        return starUri;
    }
    public static ArrayList<String> getStarSubject()
    {
        return starSubject;
    }
    //add方法：用户有新动作后，更新历史访问记录
    public static void addStarLabel(String label)
    {
        starLabel.add(label);
    }
    public static void addStarUri(String Uri)
    {
        starUri.add(Uri);
    }
    public static void addStarSubject(String subject)
    {
        starSubject.add(subject);
    }
    //remove方法：用户删除某一收藏对象
    public static int findId(String t_uri, String t_subject)
    {
        for(int i = 0;i < starUri.size();i++)
        {
            if(starUri.get(i).equals(t_uri))
            {
                if(starSubject.get(i).equals(t_subject))
                    return i;
            }
        }
        return -1;
    }
    public static void removeStarLabel(int i)
    {
        starLabel.remove(i);
    }
    public static void removeStarUri(int i)
    {
        starUri.remove(i);;
    }
    public static void removeStarSubject(int i)
    {
        starSubject.remove(i);
    }

    //检查某个实体是不是被收藏
    public static boolean checkStarEntity(String uri, String subject)
    {
        System.out.println("star: ");
        System.out.println(uri);
        System.out.println(subject);
        for(int i = 0;i < starUri.size();i++)
        {
            System.out.println(starUri.get(i));
            System.out.println(starSubject.get(i));
            if(starUri.get(i).equals(uri))
            {
                System.out.println("yes");
                if(starSubject.get(i).equals(subject))
                    return true;
            }
        }
        return false;
    }

    /** 历史访问的实体信息
     *
     */
    private static ArrayList<EntityDescription> description = new ArrayList<>();
    private static ArrayList<EntityProperty> property = new ArrayList<>();
    private static ArrayList<EntityContent1> relation = new ArrayList<>();
    private static ArrayList<QuestionList> practice = new ArrayList<>();

    //set方法：login/register后初始化用
    public static void setDescription(ArrayList<EntityDescription> Description)
    {
        description = Description;
    }
    public static void setProperty(ArrayList<EntityProperty> s)
    {
        property = s;
    }
    public static void setRelation(ArrayList<EntityContent1> s)
    {
        relation= s;
    }
    public static void setPractice(ArrayList<QuestionList> s)
    {
        practice= s;
    }

    //get方法：在需要展示某个详情页的时候用
    public static EntityDescription getDescription(int pos)
    {
        return description.get(pos);
    }
    public static EntityProperty getProperty(int pos)
    {
        return property.get(pos);
    }
    public static EntityContent1 getRelation(int pos)
    {
        return relation.get(pos);
    }
    public static QuestionList getPractice(int pos)
    {
        return practice.get(pos);
    }

    //add方法：用户有新动作后，更新历史访问记录
    public static void addDescription(EntityDescription s)
    {
        description.add(s);
    }
    public static void addProperty(EntityProperty s)
    {
        property.add(s);
    }
    public static void addRelation(EntityContent1 s)
    {
        relation.add(s);
    }
    public static void addPractice(QuestionList s)
    {
        practice.add(s);
    }

    private static List<Integer> subjectList = new LinkedList<>();
    public final static HashMap<String,Integer> SUBJECT2INT= new HashMap<String,Integer>(){
        {
            put("语文",0);
            put("数学",1);
            put("英语",2);
            put("物理",3);
            put("化学",4);
            put("生物",5);
            put("地理",6);
            put("历史",7);
            put("政治",8);
        }

    };
    public final static HashMap<Integer,String> INT2SUBJECT= new HashMap<Integer,String>(){
        {
            put(0,"语文");
            put(1,"数学");
            put(2,"英语");
            put(3,"物理");
            put(4,"化学");
            put(5,"生物");
            put(6,"地理");
            put(7,"历史");
            put(8,"政治");
        }

    };
    public final static HashMap<String, String> SUBJECT2ENG = new HashMap<String, String>() {
        {
            put("语文", "chinese");
            put("英语", "english");
            put("数学", "math");
            put("物理", "physics");
            put("化学", "chemistry");
            put("生物", "biology");
            put("历史", "history");
            put("地理", "geo");
            put("政治", "politics");
        }
    };

    public static List getSubjectList(){
        return subjectList;
    }
    public static void setSubjectList(List<Integer> newList){
        subjectList = newList;
    }
    public static void InitSubjectList(){
        //Default: no user settings available
        for(int i=0; i<9;i++)
        {
            subjectList.add(i);
        }
    }

    public static List<Integer> TF2List(String tf)
    {
        List<Integer> list = new LinkedList<>();
        for(int i = 0; i< tf.length(); i++)
        {
            Character c = tf.charAt(i);
            if(c.equals('T'))
            {
                list.add(i);
            }
        }
        return list;
    }

    public static String List2TF(List<Integer> newList)
    {
        String tf = new String();
        for(int i=0;i<9;i++)
        {
            if(newList.contains(i))
            {
                tf+="T";
            }
            else
                tf+="F";
        }
        return tf;
    }

    private static ArrayList<String> keyword = new ArrayList<>();

    public static void SetKeyWord(ArrayList<String> s)
    {
        keyword = s;
    }
    public static void addKeyWord(String s)
    {
        keyword.add(0,s);
    }
    public static ArrayList<String> getKeyWord()
    {
        return keyword;
    }

    /**
     * 附加功能：专题测试记分
     */
    public static Integer total_score;
    public static Integer correct_score;

    public static void ClearScore(){
        total_score = 0;
        correct_score = 0;
    }
    public static void setScore(boolean correct){
        total_score+=1;
        if(correct)
        {
            correct_score+=1;
        }
    }
    public static int getCorrect()
    {
        return correct_score;
    }
    public static int getTotalScore()
    {
        return total_score;
    }

    //清空函数
    public static void ClearAll()
    {
        username = "";
        historyLabel.clear();
        historyUri.clear();
        historySubject.clear();
        starLabel.clear();
        starUri.clear();
        starSubject.clear();
        description.clear();
        property.clear();
        relation.clear();
        practice.clear();
        subjectList.clear();
        keyword.clear();
        aCache.clear();

        InitSubjectList();
    }

}
