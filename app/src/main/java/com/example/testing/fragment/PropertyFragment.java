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
import android.widget.SimpleAdapter;

import com.example.testing.R;
import com.example.testing.activity.EntityActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PropertyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertyFragment extends Fragment {

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
    private ArrayList<String> predicateLabel;
    private ArrayList<String> objectLabel;
    private EntityActivity activity;


    public PropertyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PropertyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PropertyFragment newInstance(String param1, String param2) {
        PropertyFragment fragment = new PropertyFragment();
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
        View view =  inflater.inflate(R.layout.fragment_property, container, false);

        //绑定组件
        listView = (ListView)view.findViewById(R.id.property_list_view);

        //获得变量的值
        predicateLabel = ((EntityActivity) getActivity()).getPredicate();
        objectLabel = ((EntityActivity) getActivity()).getObject();

        //在listView中显示出来
        final List listmap = new ArrayList<>();
        counter = 0;
        int oldCounter = counter;
        for (counter = oldCounter; counter < predicateLabel.size() && counter < objectLabel.size(); counter++) {
            Map map = new HashMap();
            map.put("predicate", predicateLabel.get(counter));
            map.put("object", objectLabel.get(counter));
            listmap.add(map);
        }

        adapter = new SimpleAdapter(getContext(), listmap, android.R.layout.simple_list_item_2, new String[]{"predicate","object"},new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(adapter);

        return view;
    }
}