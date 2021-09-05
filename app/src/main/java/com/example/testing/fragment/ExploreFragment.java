package com.example.testing.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.activity.SetSubject;
import com.example.testing.MyApplication;
import com.example.testing.R;
import com.example.testing.manager.QDSchemeManager;
import com.example.testing.search;
import com.example.testing.HomeActivity;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.qmuiteam.qmui.arch.annotation.FragmentScheme;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.skin.SkinWriter;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

@FragmentScheme(
        name = "tab",
        useRefreshIfCurrentMatched = true,
        activities = {HomeActivity.class},
        required = {"mode=2", "name"},
        keysWithIntValue = {"mode"})

public class ExploreFragment extends QMUIFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // For subject menu
    private final int TAB_COUNT = 9;
    @BindView(R.id.explore_tabSegment)
    QMUITabSegment mTabSegment;
    @BindView(R.id.explore_contentViewPager)
    ViewPager mContentViewPager;
    private MyApplication myApp;

    public enum ContentPage {
        Item1(0),
        Item2(1),
        Item3(2),
        Item4(3),
        Item5(4),
        Item6(5),
        Item7(6),
        Item8(7),
        Item9(8),
        Item10(9);
        private final int position;

        ContentPage(int pos) {
            position = pos;
        }

        public static ContentPage getPage(int position) {
            switch (position) {
                case 0:
                    return Item1;
                case 1:
                    return Item2;
                case 2:
                    return Item3;
                case 3:
                    return Item4;
                case 4:
                    return Item5;
                case 5:
                    return Item6;
                case 6:
                    return Item7;
                case 7:
                    return Item8;
                case 8:
                    return Item9;
                case 9:
                    return Item10;
                default:
                    return Item1;
            }
        }

        public int getPosition() {
            return position;
        }
    }

    private Map<ContentPage, View> mPageMap = new HashMap<>();
    private ContentPage mDestPage = ContentPage.Item1;
    private List<Integer> mSubjectList = new LinkedList<>();
    private int mCurrentItemCount = mSubjectList.size();
    private Map<Integer, String> getSubject = new HashMap<Integer,String>(){
        {
            put(0, "语文");
            put(1, "数学");
            put(2, "英语");
            put(3, "物理");
            put(4, "化学");
            put(5, "生物");
            put(6, "地理");
            put(7, "历史");
            put(8, "政治");
        }
    };

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mCurrentItemCount;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            ContentPage page = ContentPage.getPage(position);
            View view = getPageView(page);
            view.setTag(page);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, params);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            View view = (View) object;
            Object page = view.getTag();
            if (page instanceof ContentPage) {
                int pos = ((ContentPage) page).getPosition();
                if (pos >= mCurrentItemCount) {
                    return POSITION_NONE;
                }
                return POSITION_UNCHANGED;
            }
            return POSITION_NONE;
        }
    };

    //    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isStartedByScheme()) {
            Toast.makeText(getContext(), "started by scheme", Toast.LENGTH_SHORT).show();

            Bundle args = getArguments();
            if (args != null) {
                int mode = args.getInt("mode");
                String name = args.getString("name");
                Toast.makeText(getContext(), "mode = " + mode + "; name = " + name, Toast.LENGTH_SHORT).show();
            }
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        Button button = view.findViewById(R.id.search_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),search.class);
//                startActivity(intent);
//            }
//        });
//        return view;
//    }

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_explore, null);
        ButterKnife.bind(this, rootView);

        Button button = rootView.findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),search.class);
                startActivity(intent);
            }
        });

        //mQDItemDescription = QDDataManager.getInstance().getDescription(this.getClass());
        initTabAndPager();
//        initTopBar();

        return rootView;
    }


    //Build Top Bar
//    private void initTopBar() {
//
//        mTopBar.setTitle("实体链接功能");
//    }

    //For subject menu
    private void initTabAndPager() {
        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(mDestPage.getPosition(), false);

        //从Global variable更新当前的学科列表
        myApp = (MyApplication) getActivity().getApplication();
        mSubjectList = myApp.getSubjectList();
        mCurrentItemCount = mSubjectList.size();
        mPagerAdapter.notifyDataSetChanged();
        mTabSegment.reset();
        QMUITabBuilder tabBuilder = mTabSegment.tabBuilder().setColor(R.color.black,R.color.qmui_config_color_gray_9);

        for (int i = 0; i < mCurrentItemCount; i++) {
            mTabSegment.addTab(tabBuilder.setText(getSubject.get(mSubjectList.get(i))).build(getContext()));
        }
        mTabSegment.notifyDataChanged();

        System.out.println("current Item Count:"+mCurrentItemCount);

        int space = QMUIDisplayHelper.dp2px(getContext(), 16);
        mTabSegment.setIndicator(new QMUITabIndicator(
                QMUIDisplayHelper.dp2px(getContext(), 2), false, true));
        mTabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setPadding(space, 0, space, 0);
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                Toast.makeText(getContext(), "select index " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(int index) {
                Toast.makeText(getContext(), "unSelect index " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(int index) {
                Toast.makeText(getContext(), "reSelect index " + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleTap(int index) {
                Toast.makeText(getContext(), "double tap index " + index, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View getPageView(ContentPage page) {
        View view = mPageMap.get(page);
        if (view == null) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            textView.setText("这是 " + getSubject.get(mSubjectList.get(page.getPosition())) + " 的界面");
            QMUISkinHelper.setSkinValue(textView, new SkinWriter(){
                @Override
                public void write(QMUISkinValueBuilder builder) {
                    builder.textColor(R.color.black);
                }
            });
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    QDSchemeManager.getInstance().handle("qmui://tab?mode=2&name=xixi");
//                }
//            });
            view = textView;
            mPageMap.put(page, view);
        }
        return view;
    }

    @Override
    public void refreshFromScheme(@Nullable Bundle bundle) {
        Toast.makeText(getContext(),
                "refreshFromScheme: name = " + bundle.getString("name"),
                Toast.LENGTH_SHORT).show();
    }
}