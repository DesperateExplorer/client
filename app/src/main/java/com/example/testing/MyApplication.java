package com.example.testing;


import android.app.Application;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

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

    public List<Integer> subjectList = new LinkedList<>();
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