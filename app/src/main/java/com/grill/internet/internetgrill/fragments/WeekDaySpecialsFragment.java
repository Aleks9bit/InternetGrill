package com.grill.internet.internetgrill.fragments;

import android.view.View;
import android.widget.TextView;

import com.grill.internet.internetgrill.R;

/**
 * Created by denys on 28.03.17.
 */

public class WeekDaySpecialsFragment extends BaseFragment implements View.OnClickListener {
    private static View viewToolbarLayout;
    private TextView toolbarTitle;
    private View toolbarBackArrow;
    private TextView weekDaySpecialsImageComment;
    private final String strToolbarTitle = "Weekday Specials";

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_weekday_specials;
    }

    public static WeekDaySpecialsFragment newInstance(View toolbarLayout) {
        WeekDaySpecialsFragment.viewToolbarLayout = toolbarLayout;
        return new WeekDaySpecialsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewToolbarLayout.findViewById(R.id.hamburgerHomeFragment).setVisibility(View.GONE);
        toolbarBackArrow.setVisibility(View.VISIBLE);
        toolbarTitle.setText(strToolbarTitle);
    }

    @Override
    public void initFragmentsViews(View view) {
        view.findViewById(R.id.weekDaySpecialsImage).setOnClickListener(this);

        weekDaySpecialsImageComment = (TextView) view.findViewById(R.id.weekDaySpecialsImageComment);

        toolbarTitle = (TextView) viewToolbarLayout.findViewById(R.id.toolbarTitle);

        toolbarBackArrow = viewToolbarLayout.findViewById(R.id.backArrowFragment);
        toolbarBackArrow.setOnTouchListener(this);
        toolbarBackArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backArrowFragment:
                replaceFragment(HomeFragment.newInstance(viewToolbarLayout));
                break;
            case R.id.weekDaySpecialsImage:
                replaceFragment(WeekDaySpecialsImageFragment.newInstance(viewToolbarLayout, weekDaySpecialsImageComment.getText().toString()));
                break;
        }
    }
}
