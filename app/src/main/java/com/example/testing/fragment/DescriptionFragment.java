package com.example.testing.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.testing.R;
import com.example.testing.activity.EntityActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private ListView listView;
    private BaseAdapter adapter;
    private int counter;
    private ArrayList<String> result;
    private EntityActivity activity;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DescriptionFragment newInstance(String param1, String param2) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_description, container, false);

        //绑定组件
        listView = (ListView)view.findViewById(R.id.description_list_view);

        //获得变量的值
        result = ((EntityActivity) getActivity()).getDescription();

        //在listView中显示出来
        final ArrayList<String> list = new ArrayList<>();
        counter = 0;
        int oldCounter = counter;
        for (counter = oldCounter; counter < oldCounter + 15 && counter<result.size(); counter++) {
            list.add(result.get(counter));
        }

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != 0) { // 不为0则表示有下拉动作
                    if ((firstVisibleItem + visibleItemCount) > totalItemCount - 2) { // 当前第一个完全可见的item再下拉一个页面长度，即变为倒数第二个时
                        // 在此加载数据
                        int oldCounter = counter;
                        for (counter = oldCounter; counter < oldCounter + 15 && counter<result.size(); counter++) {
                            list.add(result.get(counter));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return view;
    }


}