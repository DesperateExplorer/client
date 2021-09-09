
package com.example.testing.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.AppSingle;
import com.example.testing.R;
import com.example.testing.adapter.ChatAdapter;
import com.example.testing.model.ChatModel;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QAFragment extends QMUIFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    List<ChatModel> chatModelList = new ArrayList<ChatModel>();
    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    String currentSubject;


    public QAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QAFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QAFragment newInstance(String param1, String param2) {
        QAFragment fragment = new QAFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.qa_tabSegment)
    QMUITabSegment mTabSegment;
    @BindView(R.id.qa_contentViewPager)
    ViewPager mContentViewPager;

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

        public static QAFragment.ContentPage getPage(int position) {
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

    private Map<QAFragment.ContentPage, View> mPageMap = new HashMap<>();
    private QAFragment.ContentPage mDestPage = QAFragment.ContentPage.Item1;
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
            QAFragment.ContentPage page = QAFragment.ContentPage.getPage(position);
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
            if (page instanceof QAFragment.ContentPage) {
                int pos = ((QAFragment.ContentPage) page).getPosition();
                if (pos >= mCurrentItemCount) {
                    return POSITION_NONE;
                }
                return POSITION_UNCHANGED;
            }
            return POSITION_NONE;
        }
    };

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

    @Override
    public View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_q_a, null);
        ButterKnife.bind(this, view);

        // 聊天区域
        initRecycler(view);
        // 学科列表
        initTabAndPager();

        Button sendBtn = (Button) view.findViewById(R.id.send_btn);
        EditText editText = (EditText) view.findViewById(R.id.content_input);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();
                if (!msg.isEmpty()) {
                    sendMessage(msg);
                    reply(msg);
                    editText.setText("");
                } else {
                    Toast.makeText(getActivity(), "Cant be empty！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void initRecycler(View view) {
        chatModelList.clear();
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recyclerView);
        //给recyclerView创建布局方式
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        //创建适配器
        chatAdapter = new ChatAdapter(chatModelList);
        recyclerView.setAdapter(chatAdapter);
    }

    /**
     * 发送信息
     *
     * @param message
     */
    void sendMessage(String message) {
        ChatModel chatModel = new ChatModel(R.drawable.image, "我", message, ChatModel.SEND);
        chatModelList.add(chatModel);
        chatAdapter.notifyItemInserted(chatModelList.size() - 1);
        recyclerView.scrollToPosition(chatModelList.size() - 1);
    }

    /**
     * 接收信息
     *
     * @param message
     */
    void receiveMessage(String message) {
        ChatModel chatModel = new ChatModel(R.drawable.image, "智能机器人", message, ChatModel.RECEIVE);
        chatModelList.add(chatModel);
        chatAdapter.notifyItemInserted(chatModelList.size() - 1);
        recyclerView.scrollToPosition(chatModelList.size() - 1);
    }

    /**
     * 回复
     * @param msg
     */
    void reply(String msg){
        String rMsg="";
        //TODO:给后端发inputstring，subject；获得回答
        //当前学科：currentSubject——语文，数学...（中文字符串）
        switch (msg){
            case "hello":
                rMsg="hello! How are you？";
                break;
            case  "How old are you?":
                rMsg="22";
                break;
            case "Subject":
                rMsg = currentSubject;
                break;
        }
        if(!rMsg.isEmpty()){
            receiveMessage(rMsg);
        }
    }

    //For subject menu
    private void initTabAndPager() {
        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(mDestPage.getPosition(), false);

        //从Global variable更新当前的学科列表
        mSubjectList = AppSingle.getSubjectList();
        mCurrentItemCount = mSubjectList.size();
        mPagerAdapter.notifyDataSetChanged();
        mTabSegment.reset();
        QMUITabBuilder tabBuilder = mTabSegment.tabBuilder().setColor(R.color.black,R.color.qmui_config_color_gray_9);

        for (int i = 0; i < mCurrentItemCount; i++) {
            mTabSegment.addTab(tabBuilder.setText(getSubject.get(mSubjectList.get(i))).build(getContext()));
        }
        mTabSegment.notifyDataChanged();

        System.out.println("current Item Count:"+mCurrentItemCount);
        currentSubject = getSubject.get(mSubjectList.get(0));
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
                currentSubject = getSubject.get(mSubjectList.get(index));
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

    private View getPageView(QAFragment.ContentPage page) {
        View view = mPageMap.get(page);
        if (view == null) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            textView.setText("这是 " + getSubject.get(mSubjectList.get(page.getPosition())) + " 的界面");
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