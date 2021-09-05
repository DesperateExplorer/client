package com.example.testing.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.testing.fragment.DescriptionFragment;
import com.example.testing.fragment.PropertyFragment;
import com.example.testing.fragment.RelationFragment;
import com.example.testing.fragment.PracticeFragment;

public class EntityAdapter extends FragmentPagerAdapter {

    private String[] titles = {"描述","属性","关系","关联试题"};
    public EntityAdapter(FragmentManager fm) {
        super(fm);
    }

    private DescriptionFragment fragment1;
    private PropertyFragment fragment2;
    private RelationFragment fragment3;
    private PracticeFragment fragment4;


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(fragment1 == null)
                    fragment1 = new DescriptionFragment();
                return fragment1;
            case 1:
                if(fragment2 == null)
                    fragment2 = new PropertyFragment();
                return fragment2;
            case 2:
                if(fragment3 == null)
                    fragment3 = new RelationFragment();
                return fragment3;
            case 3:
                if(fragment4 == null)
                    fragment4 = new PracticeFragment();
                return fragment4;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public String getPageTitle(int i){
        return titles[i];
    }

}
