package com.example.testing;


import android.app.Application;

import com.example.testing.jsonTool.EntityDescription;
import com.example.testing.jsonTool.QuestionList;
import com.example.testing.jsonTool.EntityProperty;
import com.example.testing.jsonTool.EntityContent1;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化QMUISwipeBackActivityManager，否则点击屏幕时就程序就会崩溃
        QMUISwipeBackActivityManager.init(this);
        InitSubjectList();
    }

    /** 存储的用户名
     *
     */
    public String username;
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getUsername()
    {
        return username;
    }

    /** 存储的历史访问实体列表
     *  {label, uri, subject} 三元组
     */
    ArrayList<String> historyLabel;
    ArrayList<String> historyUri;
    ArrayList<String> historySubject;

    //set方法：login/register后初始化用
    public void setHistoryLabel(ArrayList<String> s)
    {
        historyLabel = s;
    }
    public void setHistoryUri(ArrayList<String> s)
    {
        historyUri = s;
    }
    public void setHistorySubject(ArrayList<String> s)
    {
        historySubject = s;
    }
    //get方法：在需要展示列表的时候用
    public ArrayList<String> getHistoryLabel()
    {
        return historyLabel;
    }
    public ArrayList<String> getHistoryUri()
    {
        return historyUri;
    }
    public ArrayList<String> getHistorySubject()
    {
        return historySubject;
    }
    //add方法：用户有新动作后，更新历史访问记录
    public void addLabel(String label)
    {
        historyLabel.add(0,label);
    }
    public void addUri(String Uri)
    {
        historyUri.add(0,Uri);
    }
    public void addSubject(String subject)
    {
        historySubject.add(0,subject);
    }

    //查找方法：判断某个实体是不是已经被访问过
    public boolean checkEntity(String uri, String subject)
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
    ArrayList<String> starLabel;
    ArrayList<String> starUri;
    ArrayList<String> starSubject;

    //set方法：login/register后初始化用
    public void setStarLabel(ArrayList<String> s)
    {
        starLabel = s;
    }
    public void setStarUri(ArrayList<String> s)
    {
        starUri = s;
    }
    public void setStarSubject(ArrayList<String> s)
    {
        starSubject = s;
    }
    //get方法：在需要展示列表的时候用
    public ArrayList<String> getStarLabel()
    {
        return starLabel;
    }
    public ArrayList<String> getStarUri()
    {
        return starUri;
    }
    public ArrayList<String> getStarSubject()
    {
        return starSubject;
    }
    //add方法：用户有新动作后，更新历史访问记录
    public void addStarLabel(String label)
    {
        starLabel.add(label);
    }
    public void addStarUri(String Uri)
    {
        starUri.add(Uri);
    }
    public void addStarSubject(String subject)
    {
        starSubject.add(subject);
    }
    //remove方法：用户删除某一收藏对象
    public int findId(String t_uri, String t_subject)
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
    public void removeStarLabel(int i)
    {
        starLabel.remove(i);
    }
    public void removeStarUri(int i)
    {
        starUri.remove(i);;
    }
    public void removeStarSubject(int i)
    {
        starSubject.remove(i);
    }

    //检查某个实体是不是被收藏
    public boolean checkStarEntity(String uri, String subject)
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
    ArrayList<EntityDescription> description;
    ArrayList<EntityProperty> property;
    ArrayList<EntityContent1> relation;
    ArrayList<QuestionList> practice;
    //set方法：login/register后初始化用
    public void setDescription(ArrayList<EntityDescription> description)
    {
        this.description = description;
    }
    public void setProperty(ArrayList<EntityProperty> s)
    {
        this.property = s;
    }
    public void setRelation(ArrayList<EntityContent1> s)
    {
        this.relation= s;
    }
    public void setPractice(ArrayList<QuestionList> s)
    {
        this.practice= s;
    }

    //get方法：在需要展示某个详情页的时候用
    public EntityDescription getDescription(int pos)
    {
        return description.get(pos);
    }
    public EntityProperty getProperty(int pos)
    {
        return property.get(pos);
    }
    public EntityContent1 getRelation(int pos)
    {
        return relation.get(pos);
    }
    public QuestionList getPractice(int pos)
    {
        return practice.get(pos);
    }

    //add方法：用户有新动作后，更新历史访问记录
    public void addDescription(EntityDescription s)
    {
        description.add(s);
    }
    public void addProperty(EntityProperty s)
    {
        property.add(s);
    }
    public void addRelation(EntityContent1 s)
    {
        relation.add(s);
    }
    public void addPractice(QuestionList s)
    {
        practice.add(s);
    }

    public List<Integer> subjectList = new LinkedList<>();
    public HashMap<String,Integer> SUBJECT2INT= new HashMap<String,Integer>(){
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

    public List getSubjectList(){
        return subjectList;
    }
    public void setSubjectList(List<Integer> newList){
        subjectList = newList;
    }
    private void InitSubjectList(){
        //Default: no user settings available
        for(int i=0; i<9;i++)
        {
            subjectList.add(i);
        }
    }

    private ArrayList<String> keyword = new ArrayList<>();

    public void SetKeyWord(ArrayList<String> s)
    {
        keyword = s;
    }
    public void addKeyWord(String s)
    {
        keyword.add(0,s);
    }
    public ArrayList<String> getKeyWord()
    {
        return keyword;
    }
}