package com.example.socailmedia;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

public class SectionsPagerAdapter extends PagerAdapter {
    public SectionsPagerAdapter(IndividualPost individualPost, FragmentManager supportFragmentManager) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
