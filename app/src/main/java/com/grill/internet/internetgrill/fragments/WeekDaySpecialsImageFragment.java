package com.grill.internet.internetgrill.fragments;

import android.view.View;
import android.widget.TextView;

import com.grill.internet.internetgrill.R;

/**
 * Created by denys on 28.03.17.
 */

public class WeekDaySpecialsImageFragment extends BaseFragment implements View.OnClickListener {
    private static View viewToolbarLayout;
    private static String strToolbarTitle;
    private View toolbarBackArrow;
    private TextView toolbarTitle;
    private TextView imageComment;


    @Override
    public void onResume() {
        super.onResume();
        viewToolbarLayout.findViewById(R.id.hamburgerHomeFragment).setVisibility(View.GONE);
        toolbarBackArrow.setVisibility(View.VISIBLE);
        toolbarTitle.setText(strToolbarTitle);
        imageComment.setText(strToolbarTitle);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_weekday_specials_image;
    }

    @Override
    public void initFragmentsViews(View view) {
        toolbarTitle = (TextView) viewToolbarLayout.findViewById(R.id.toolbarTitle);
        toolbarBackArrow = viewToolbarLayout.findViewById(R.id.backArrowFragment);
        toolbarBackArrow.setOnTouchListener(this);
        toolbarBackArrow.setOnClickListener(this);
        imageComment = (TextView) view.findViewById(R.id.weekDaySpecialsImageImageComment);
    }

    public static WeekDaySpecialsImageFragment newInstance(View toolbarLayout, String strToolbarTitle) {
        WeekDaySpecialsImageFragment.viewToolbarLayout = toolbarLayout;
        WeekDaySpecialsImageFragment.strToolbarTitle = strToolbarTitle;
        return new WeekDaySpecialsImageFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backArrowFragment:
                replaceFragment(WeekDaySpecialsFragment.newInstance(viewToolbarLayout));
                break;
        }
    }
}
