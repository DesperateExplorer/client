/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.testing.base;

import android.content.Context;
import android.util.Log;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.SwipeBackLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.example.testing.fragment.HomeFragment;
//import com.qmuiteam.qmui.util.QMUIViewHelper;
//import com.qmuiteam.qmui.widget.QMUITopBar;
//import com.qmuiteam.qmui.widget.QMUITopBarLayout;
//import com.qmuiteam.qmuidemo.QDMainActivity;
//import com.qmuiteam.qmuidemo.fragment.home.HomeFragment;
//import com.qmuiteam.qmuidemo.manager.QDDataManager;
//import com.qmuiteam.qmuidemo.model.QDItemDescription;

/**
 * Created by cgspine on 2018/1/7.
 */

public abstract class BaseFragment extends QMUIFragment {

    private static final String TAG = "BaseFragment";


    public BaseFragment() {
    }

    @Override
    protected int backViewInitOffset(Context context, int dragDirection, int moveEdge) {
        if (moveEdge == SwipeBackLayout.EDGE_TOP || moveEdge == SwipeBackLayout.EDGE_BOTTOM) {
            return 0;
        }
        return QMUIDisplayHelper.dp2px(context, 100);
    }

    @Override
    public void onResume() {
        super.onResume();
        //QDUpgradeManager.getInstance(getContext()).runUpgradeTipTaskIfExist(getActivity());
        Log.i(TAG, getClass().getSimpleName() + " onResume");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, getClass().getSimpleName() + " onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, getClass().getSimpleName() + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, getClass().getSimpleName() + " onStop");
    }

    @Override
    public Object onLastFragmentFinish() {
        return new HomeFragment();
    }


}
