package com.example.testing;


import android.app.Application;
import android.content.Entity;
import android.util.EventLogTags;

import com.example.testing.jsonTool.EntityDescription;
import com.example.testing.jsonTool.EntityPractice;
import com.example.testing.jsonTool.EntityProperty;
import com.example.testing.jsonTool.EntityRelation;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

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
     *  {label, url, subject} 三元组
     */
    ArrayList<String> historyLabel;
    ArrayList<String> historyUrl;
    ArrayList<String> historySubject;

    //set方法：login/register后初始化用
    public void setHistoryLabel(ArrayList<String> s)
    {
        historyLabel = s;
    }
    public void setHistoryUrl(ArrayList<String> s)
    {
        historyUrl = s;
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
    public ArrayList<String> getHistoryUrl()
    {
        return historyUrl;
    }
    public ArrayList<String> getHistorySubject()
    {
        return historySubject;
    }
    //add方法：用户有新动作后，更新历史访问记录
    public void addLabel(String label)
    {
        historyLabel.add(label);
    }
    public void addUrl(String Url)
    {
        historyUrl.add(Url);
    }
    public void addSubject(String subject)
    {
        historySubject.add(subject);
    }
    //查找方法：判断某个实体是不是已经被访问过
    public boolean checkEntity(String uri, String subject)
    {
        System.out.println("star: ");
        System.out.println(uri);
        System.out.println(subject);
        for(int i = 0;i < starUrl.size();i++)
        {
            System.out.println(starUrl.get(i));
            System.out.println(starSubject.get(i));
            if(starUrl.get(i).equals(uri))
            {
                System.out.println("yes");
                if(SUBJECT2INT.get(starSubject.get(i)) == SUBJECT2INT.get(subject))
                    return true;
            }
        }
        return false;
    }


    /** 收藏实体列表
     *  {label, url, subject} 三元组
     */
    ArrayList<String> starLabel;
    ArrayList<String> starUrl;
    ArrayList<String> starSubject;

    //set方法：login/register后初始化用
    public void setStarLabel(ArrayList<String> s)
    {
        starLabel = s;
    }
    public void setStarUrl(ArrayList<String> s)
    {
        starUrl = s;
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
    public ArrayList<String> getStarUrl()
    {
        return starUrl;
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
    public void addStarUrl(String Url)
    {
        starUrl.add(Url);
    }
    public void addStarSubject(String subject)
    {
        starSubject.add(subject);
    }
    //remove方法：用户删除某一收藏对象
    public int findId(String t_url, String t_subject)
    {
        for(int i = 0;i < starUrl.size();i++)
        {
            if(starUrl.get(i).equals(t_url))
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
    public void removeStarUrl(int i)
    {
        starUrl.remove(i);;
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
        for(int i = 0;i < starUrl.size();i++)
        {
            System.out.println(starUrl.get(i));
            System.out.println(starSubject.get(i));
            if(starUrl.get(i).equals(uri))
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
    ArrayList<EntityRelation> relation;
    ArrayList<EntityPractice> practice;
    //set方法：login/register后初始化用
    public void setDescription(ArrayList<EntityDescription> description)
    {
        this.description = description;
    }
    public void setProperty(ArrayList<EntityProperty> s)
    {
        this.property = s;
    }
    public void setRelation(ArrayList<EntityRelation> s)
    {
        this.relation= s;
    }
    public void setPractice(ArrayList<EntityPractice> s)
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
    public EntityRelation getRelation(int pos)
    {
        return relation.get(pos);
    }
    public EntityPractice getPractice(int pos)
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
    public void addRelation(EntityRelation s)
    {
        relation.add(s);
    }
    public void addPractice(EntityPractice s)
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
}