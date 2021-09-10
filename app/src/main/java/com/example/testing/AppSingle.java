package com.example.testing;

import android.content.Context;

import com.example.testing.jsonTool.EntityContent1;
import com.example.testing.jsonTool.EntityDescription;
import com.example.testing.jsonTool.EntityProperty;
import com.example.testing.jsonTool.QuestionList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AppSingle {
    private static AppSingle instance;

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
     * 缓存相关
     */


    /** 存储的历史访问实体列表
     *  {label, uri, subject} 三元组
     */
    private static ArrayList<String> historyLabel;
    private static ArrayList<String> historyUri;
    private static ArrayList<String> historySubject;

    /**
     * 网络请求相关
     */
    public final static String scheme = "http"; // can only be "http" or "https"
    public final static String host = "183.172.197.98";
    public final static int port = 8080;
    public final static String baseUrl = scheme + "://" + host + ":" + port;

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
                if(SUBJECT2INT.get(historySubject.get(i)) == SUBJECT2INT.get(subject))
                    return true;
            }
        }
        return false;
    }


    /** 收藏实体列表
     *  {label, uri, subject} 三元组
     */
    private static ArrayList<String> starLabel;
    private static ArrayList<String> starUri;
    private static ArrayList<String> starSubject;

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
                if(SUBJECT2INT.get(starSubject.get(i)) == SUBJECT2INT.get(t_subject))
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
                if(SUBJECT2INT.get(starSubject.get(i)) == SUBJECT2INT.get(subject))
                    return true;
            }
        }
        return false;
    }

    /** 历史访问的实体信息
     *
     */
    private static ArrayList<EntityDescription> description;
    private static ArrayList<EntityProperty> property;
    private static ArrayList<EntityContent1> relation;
    private static ArrayList<QuestionList> practice;

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

}
