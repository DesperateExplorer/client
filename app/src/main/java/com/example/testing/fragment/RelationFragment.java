package com.example.testing.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.testing.R;
import com.example.testing.activity.EntityActivity;
import com.example.testing.adapter.RelationAdapter;
import com.example.testing.jsonTool.ShowRelation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RelationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RelationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;

    public RelationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RelationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RelationFragment newInstance(String param1, String param2) {
        RelationFragment fragment = new RelationFragment();
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
        View view =  inflater.inflate(R.layout.fragment_relation, container, false);

        //绑定组件
        listView = (ListView)view.findViewById(R.id.relation_listView);

        ArrayList<ShowRelation> Data = new ArrayList<>();

        //ShowRelation: 需要传入listView的数据格式，可以从jsonTool文件夹中找到
        //下面是一个例子
        Data.add(new ShowRelation("predicate","subject","object"));

        //获得变量的值:从content1获取 subject 和 predicate

        //从content2 获取 predicate 和 object

        //在listView中显示出来
        RelationAdapter adapter = new RelationAdapter(getContext(), Data, R.layout.relation_item);
        listView.setAdapter(adapter);

        return view;
    }
}