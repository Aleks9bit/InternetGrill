package com.grill.internet.internetgrill.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.grill.internet.internetgrill.R;

import java.util.ArrayList;

/**
 * Created by denys on 23.03.17.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static View viewToolbarLayout;
    private TextView toolbarTitle;
    private String strToolbarTitle;
    private View hamburgerButton;
    private View linearLayoutFirst;
    private ScrollView scrollView;
    private int screenHeight;
    private View homeFragmentView;
    private Animation homeFragmentFirstLayoutAnimation;
    public static boolean isFirstLaunch;
    private View background;
    private ImageView homeFragmentItemViewableOnlyMenuImage;
    private ArrayList<Bitmap> list;
    private int height;
    private Thread initMenuImageViews;
    private boolean isBitmapListReady;

    private SlideshowThread slideshowThread;
    private AnimationThread animationThread;

    @Override

    public int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    private void initMenuImagesList() {
        Bitmap bitmap;
        list = new ArrayList<>();
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap newBitmap;
        options.inMutable = true;
        int id;
        int y;
        int height;
        String packageName = getActivity().getPackageName();
        for (int i = 1; i < 13; i++) {
            id = getResources().getIdentifier("menu_" + i, "drawable", packageName);
            try {
                bitmap = BitmapFactory.decodeResource(getResources(), id);

                height = bitmap.getHeight();
                y = 0;
                if (height > 400) {
                    height = 400;
                    y = height / 2 - 1;
                }
                if (y + height > bitmap.getHeight()) y = 0;
                Log.d("denys", "b.height = " + bitmap.getHeight());
                Log.d("denys", "y = " + y);
                Log.d("denys", "height = " + height);
                newBitmap = Bitmap.createBitmap(bitmap, 0, y, bitmap.getWidth(), height);
                list.add(newBitmap);
            } catch (OutOfMemoryError e) {
                Log.d("denys", String.valueOf(e.getMessage()));
            }
        }
        isBitmapListReady = true;
    }

    @Override
    public void initFragmentsViews(View view) {
        isBitmapListReady = false;
        initMenuImageViews = new Thread(new Runnable() {
            @Override
            public void run() {
                initMenuImagesList();
            }
        });

        initMenuImageViews.start();

        slideshowThread = new SlideshowThread();
        animationThread = new AnimationThread();

        homeFragmentView = view;
        strToolbarTitle = getString(R.string.homeFragmentToolbarTitle);
        toolbarTitle = (TextView) viewToolbarLayout.findViewById(R.id.toolbarTitle);
        hamburgerButton = viewToolbarLayout.findViewById(R.id.hamburgerHomeFragment);

        homeFragmentItemViewableOnlyMenuImage = (ImageView) view.findViewById(R.id.homeFragmentItemViewableOnlyMenuImage);
        height = homeFragmentItemViewableOnlyMenuImage.getHeight();

        linearLayoutFirst = view.findViewById(R.id.homeFragmentItemFirst);

        homeFragmentFirstLayoutAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.homefragment_first_layout_animation);
        if (!isFirstLaunch) homeFragmentFirstLayoutAnimation.setDuration(1000);
        linearLayoutFirst.startAnimation(homeFragmentFirstLayoutAnimation);

        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        linearLayoutFirst.getLayoutParams().height = screenHeight;
        view.findViewById(R.id.btnHomeFragmentMakeCall).setOnClickListener(this);
        view.findViewById(R.id.btnHomeFragmentMakeCall).setOnTouchListener(this);

        view.findViewById(R.id.btnHomeFragmentStatenIsland).setOnClickListener(this);
        view.findViewById(R.id.btnHomeFragmentStatenIsland).setOnTouchListener(this);

        background = view.findViewById(R.id.homeFragmentBackground);

        hamburgerButton.setOnTouchListener(this);
        hamburgerButton.setOnClickListener(this);


        scrollView = (ScrollView) view.findViewById(R.id.homeFragmentScrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int scroll = v.getScrollY();
                float newAlpha = 1f - scroll * 0.002f;
                linearLayoutFirst.setAlpha(newAlpha);
                background.setAlpha(newAlpha);
//                Log.d("denys", "   " + newAlpha);
                return false;
            }
        });

        view.findViewById(R.id.homeFragmentItemOrderOnline).setOnClickListener(this);
        view.findViewById(R.id.homeFragmentItemViewableOnlyMenu).setOnClickListener(this);
        view.findViewById(R.id.homeFragmentItemMyAccount).setOnClickListener(this);
        view.findViewById(R.id.homeFragmentItemWeekDaySpecials).setOnClickListener(this);
        view.findViewById(R.id.homeFragmentItemFamilySpecials).setOnClickListener(this);
        view.findViewById(R.id.homeFragmentItemCatering).setOnClickListener(this);
        view.findViewById(R.id.homeFragmentItemFacebook).setOnClickListener(this);
        view.findViewById(R.id.homeFragmentItemHoursContact).setOnClickListener(this);
    }

    private boolean isViewVisible(View view) {
        Rect scrollBounds = new Rect();
        scrollView.getDrawingRect(scrollBounds);

        float top = view.getY();
        float bottom = top + view.getHeight();

        if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPause() {
        Log.i("denys", "onPause");
        isFirstLaunch = false;
        if (animationThread.isAlive()) {
            Log.i("denys", "animationThread was interrupted");
            animationThread.interrupt();
        }
        if (initMenuImageViews.isAlive()) {
            initMenuImageViews.interrupt();
            Log.i("denys", "initMenuImageViews was interrupted");
        }
        if (slideshowThread.isAlive()) {
            slideshowThread.interrupt();
            Log.i("denys", "slideshowThread was interrupted");
        }
        super.onPause();
    }

    private class AnimationThread extends Thread {
        @Override
        public void run() {
            while (homeFragmentView != null) {
                if (isViewVisible(homeFragmentView.findViewById(R.id.homeFragmentEmptyViewForDetect))) {
                    linearLayoutFirst.setAlpha(1f);
                    background.setAlpha(1f);
                }
            }
        }
    }

    private class SlideshowThread extends Thread {
        @Override
        public void run() {
            while (homeFragmentView != null) {
                if (isBitmapListReady)
                    for (final Bitmap bitmap : list) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    homeFragmentItemViewableOnlyMenuImage.setImageBitmap(bitmap);
                                }
                            });
                        } catch (NullPointerException e) {
                            continue;
                        }
                    }
            }
        }

    }

    //
    @Override
    public void onResume() {
        super.onResume();
        Log.i("denys", "onResume");
        if (!animationThread.isAlive()) {
            Log.i("denys", "animationThread starts");
            animationThread.start();
        }
        try {
            if (!slideshowThread.isAlive()) {
                Log.i("denys", "slideshowThread starts");
                slideshowThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("denys", e.getMessage());
            slideshowThread = new SlideshowThread();
            slideshowThread.start();
        }
        viewToolbarLayout.findViewById(R.id.backArrowFragment).setVisibility(View.GONE);
        hamburgerButton.setVisibility(View.VISIBLE);
        toolbarTitle.setText("");
    }

    public static HomeFragment newInstance(View viewToolbar) {
        HomeFragment.viewToolbarLayout = viewToolbar;
        return new HomeFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hamburgerHomeFragment:
                ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).openDrawer(Gravity.LEFT);
                break;
            case R.id.btnHomeFragmentMakeCall:
                makeCall();
                break;
            case R.id.btnHomeFragmentStatenIsland:
                replaceFragment(HoursContactFragment.newInstance(viewToolbarLayout));
                break;
            case R.id.homeFragmentItemOrderOnline:
                replaceFragment(WebViewFragment.newInstance(getString(R.string.orderOnlineUrl), viewToolbarLayout));
                break;
            case R.id.homeFragmentItemViewableOnlyMenu:
                replaceFragment(ViewableOnlyMenuFragment.newInstance(viewToolbarLayout));
                break;
            case R.id.homeFragmentItemMyAccount:
                replaceFragment(WebViewFragment.newInstance(getString(R.string.myAccountUrl), viewToolbarLayout));
                break;
            case R.id.homeFragmentItemWeekDaySpecials:
                replaceFragment(WeekDaySpecialsFragment.newInstance(viewToolbarLayout));
                break;
            case R.id.homeFragmentItemFamilySpecials:
                replaceFragment(WebViewFragment.newInstance(getString(R.string.familySpecialsUrl), viewToolbarLayout));
                break;
            case R.id.homeFragmentItemCatering:
                replaceFragment(WebViewFragment.newInstance(getString(R.string.cateringUrl), viewToolbarLayout));
                break;
            case R.id.homeFragmentItemFacebook:
                replaceFragment(WebViewFragment.newInstance(getString(R.string.facebookUrl), viewToolbarLayout));
                break;
            case R.id.homeFragmentItemHoursContact:
                replaceFragment(HoursContactFragment.newInstance(viewToolbarLayout));
                break;
        }
    }

    private void makeCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(getString(R.string.mobileNumber)));
        startActivity(intent);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getActivity().startActivity(intent);
    }
}
