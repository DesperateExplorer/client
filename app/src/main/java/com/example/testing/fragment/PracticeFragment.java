package com.example.testing.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.testing.R;
import com.example.testing.activity.EntityActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PracticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PracticeFragment extends Fragment {

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
    private ArrayList<String> questions;
    private EntityActivity activity;

    public PracticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PracticeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PracticeFragment newInstance(String param1, String param2) {
        PracticeFragment fragment = new PracticeFragment();
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
        View view =  inflater.inflate(R.layout.fragment_practice, container, false);

        //绑定组件
        listView = (ListView)view.findViewById(R.id.practice_list_view);

        //获得变量的值
        questions = ((EntityActivity) getActivity()).getQuestions();

        //在listView中显示出来
        final ArrayList<String> list = new ArrayList<>();
        counter = 0;
        int oldCounter = counter;
        for (counter = oldCounter; counter < questions.size(); counter++) {
            list.add(questions.get(counter));
        }
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        //点击试题跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),EntityActivity.class);
//                intent.putExtra("answer", .get(i));
//                intent.putExtra("question", .get(i));
//                intent.putExtra("answer", .get(i));
//                intent.putExtra("answer", .get(i));
//                intent.putExtra("answer", .get(i));
//                intent.putExtra("answer", .get(i));
//                intent.putExtra("question", .get(i));
//                intent.putExtra("answer", .get(i));
//                intent.putExtra("answer", .get(i));
//                intent.putExtra("answer", .get(i));
                startActivity(intent);
            }
        });
        return view;
    }
}