package com.grill.internet.internetgrill.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.grill.internet.internetgrill.R;


/**
 * Created by Denys Parvadov on 18.01.17.
 */

public abstract class BaseFragment extends Fragment implements View.OnTouchListener {

    public static int layout;

    private final String FRAGMENT_TAG = "FRAGMENT_TAG";

    public abstract int getFragmentLayout();

    public abstract void initFragmentsViews(View view);

    public void replaceFragment(BaseFragment fragment) {
        if (fragment instanceof WeekDaySpecialsImageFragment || fragment instanceof HomeFragment) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.home, fragment, FRAGMENT_TAG).addToBackStack(null)
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.home, fragment, FRAGMENT_TAG).addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.55F);
        v.startAnimation(buttonClick);
        return false;
    }

    public static BaseFragment newInstance() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = getFragmentLayout();
        View rootView = inflater.inflate(layout, container, false);
        initFragmentsViews(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        setToolbarTitle();
        //Override
    }

}
