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

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化QMUISwipeBackActivityManager，否则点击屏幕时就程序就会崩溃
        QMUISwipeBackActivityManager.init(this);
        AppSingle.InitSubjectList();
    }

}