package com.grill.internet.internetgrill.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grill.internet.internetgrill.R;

/**
 * Created by denys on 28.03.17.
 */

public class ViewableOnlyMenuFragment extends BaseFragment implements View.OnClickListener {
    private static View viewToolbarLayout;
    private TextView toolbarTitle;
    private View toolbarBackArrow;
    private final String strToolbarTitle = "Viewable Only Menu";

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_viewable_menu_only;
    }

    @Override
    public void initFragmentsViews(final View view) {
        toolbarTitle = (TextView) viewToolbarLayout.findViewById(R.id.toolbarTitle);

        toolbarBackArrow = viewToolbarLayout.findViewById(R.id.backArrowFragment);
        toolbarBackArrow.setOnTouchListener(this);
        toolbarBackArrow.setOnClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 13; i++) {
                    createImageViews(view, i);
                }
            }
        }).start();
    }

    private void createImageViews(View view, final int index) {
        if (view == null) return;
        final LinearLayout scrollView = (LinearLayout) view.findViewById(R.id.linearLayoutViewableMenuOnly);
        int padding = 20;
        String packageName = getActivity().getPackageName();
        final ImageView image;
        image = new ImageView(getActivity());
        image.setLayoutParams(new android.view.ViewGroup.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        image.setPadding(padding, padding, padding, padding);
        image.setImageResource(getResources().getIdentifier("menu_" + index, "drawable", packageName));
        image.setAdjustViewBounds(true);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scrollView.addView(image);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewToolbarLayout.findViewById(R.id.hamburgerHomeFragment).setVisibility(View.GONE);
        toolbarBackArrow.setVisibility(View.VISIBLE);
        toolbarTitle.setText(strToolbarTitle);
    }

    public static ViewableOnlyMenuFragment newInstance(View toolbarLayout) {
        ViewableOnlyMenuFragment.viewToolbarLayout = toolbarLayout;
        return new ViewableOnlyMenuFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backArrowFragment:
                replaceFragment(HomeFragment.newInstance(viewToolbarLayout));
                break;
        }
    }
}
