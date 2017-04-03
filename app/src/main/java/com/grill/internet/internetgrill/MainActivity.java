package com.grill.internet.internetgrill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import com.grill.internet.internetgrill.fragments.BaseFragment;
import com.grill.internet.internetgrill.fragments.HomeFragment;
import com.grill.internet.internetgrill.fragments.HoursContactFragment;
import com.grill.internet.internetgrill.fragments.ViewableOnlyMenuFragment;
import com.grill.internet.internetgrill.fragments.WebViewFragment;
import com.grill.internet.internetgrill.fragments.WeekDaySpecialsFragment;
import com.grill.internet.internetgrill.fragments.WeekDaySpecialsImageFragment;
import com.testfairy.TestFairy;

public class MainActivity extends AppCompatActivity {
    private final int HOME_FRAGMENT = 0;
    private final int ORDER_ONLINE_FRAGMENT = 1;
    private final int VIEWABLE_ONLY_MENU_FRAGMENT = 2;
    private final int MY_ACCOUNT_FRAGMENT = 3;
    private final int WEEKDAY_SPECIALS_FRAGMENT = 4;
    private final int FAMILY_SPECIALS_FRAGMENT = 5;
    private final int CATERING_FRAGMENT = 6;
    private final int FACEBOOK_FRAGMENT = 7;
    private final int HOURS_CONTACT_FRAGMENT = 8;
    private View toolbarLayout;
    private final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private DrawerLayout drawerLayout;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestFairy.begin(this, "907cde26075076385fe80724340ccac1f0f90d72");
        setContentView(R.layout.activity_main);
        toolbarLayout = findViewById(R.id.frameLayoutAndToolbar);
        ((DrawerLayout) findViewById(R.id.drawer_layout)).setStatusBarBackgroundColor(getResources().getColor(R.color.colorStatusBarMainActivity));
        initViews();
    }

    private void initViews() {
        setFragment(HOME_FRAGMENT);
        initNavViewMenu();
    }

    private void initNavViewMenu() {
        RelativeLayout relHome = (RelativeLayout) findViewById(R.id.menuHome);
        RelativeLayout relOrderOnline = (RelativeLayout) findViewById(R.id.menuOrderOnline);
        RelativeLayout relViewableOnlyMenu = (RelativeLayout) findViewById(R.id.menuViewableMenu);
        RelativeLayout relMyAccount = (RelativeLayout) findViewById(R.id.menuMyAccount);
        RelativeLayout relWeekdaySpecials = (RelativeLayout) findViewById(R.id.menuWeekdaySpecials);
        RelativeLayout relFamilySpecials = (RelativeLayout) findViewById(R.id.menuFamilySpecials);
        RelativeLayout relCatering = (RelativeLayout) findViewById(R.id.menuCatering);
        RelativeLayout relFacebook = (RelativeLayout) findViewById(R.id.menuFacebook);
        RelativeLayout relHoursContact = (RelativeLayout) findViewById(R.id.menuHoursContact);

        relHome.setOnClickListener(menuItemListener(HOME_FRAGMENT));
        relOrderOnline.setOnClickListener(menuItemListener(ORDER_ONLINE_FRAGMENT));
        relViewableOnlyMenu.setOnClickListener(menuItemListener(VIEWABLE_ONLY_MENU_FRAGMENT));
        relMyAccount.setOnClickListener(menuItemListener(MY_ACCOUNT_FRAGMENT));
        relWeekdaySpecials.setOnClickListener(menuItemListener(WEEKDAY_SPECIALS_FRAGMENT));
        relFamilySpecials.setOnClickListener(menuItemListener(FAMILY_SPECIALS_FRAGMENT));
        relCatering.setOnClickListener(menuItemListener(CATERING_FRAGMENT));
        relFacebook.setOnClickListener(menuItemListener(FACEBOOK_FRAGMENT));
        relHoursContact.setOnClickListener(menuItemListener(HOURS_CONTACT_FRAGMENT));
    }

    private View.OnClickListener menuItemListener(final int id) {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == HOME_FRAGMENT) {
                    BaseFragment myFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                    if (myFragment instanceof HomeFragment) {
                        drawer.closeDrawer(GravityCompat.START);
                        return;
                    }
                }
                setFragment(id);
                drawer.closeDrawer(GravityCompat.START);
            }
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        BaseFragment myFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (myFragment != null && myFragment.isVisible()) {
            if (myFragment instanceof HomeFragment) {
                finish();
            } else if (myFragment instanceof WeekDaySpecialsImageFragment) {
                super.onBackPressed();
            } else {
                setFragment(HOME_FRAGMENT);
            }
        } else finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public void setFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);

        switch (position) {
            case HOME_FRAGMENT:
                BaseFragment myFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                if (!(myFragment != null && myFragment.isVisible()))
                    fragmentTransaction.setCustomAnimations(0, 0);
                else
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.home, HomeFragment.newInstance(toolbarLayout), FRAGMENT_TAG);
                break;
            case MY_ACCOUNT_FRAGMENT:
                fragmentTransaction.replace(R.id.home, WebViewFragment.newInstance(getString(R.string.myAccountUrl), toolbarLayout), FRAGMENT_TAG);
                break;
            case FAMILY_SPECIALS_FRAGMENT:
                fragmentTransaction.replace(R.id.home, WebViewFragment.newInstance(getString(R.string.familySpecialsUrl), toolbarLayout), FRAGMENT_TAG);
                break;
            case ORDER_ONLINE_FRAGMENT:
                fragmentTransaction.replace(R.id.home, WebViewFragment.newInstance(getString(R.string.orderOnlineUrl), toolbarLayout), FRAGMENT_TAG);
                break;
            case CATERING_FRAGMENT:
                fragmentTransaction.replace(R.id.home, WebViewFragment.newInstance(getString(R.string.cateringUrl), toolbarLayout), FRAGMENT_TAG);
                break;
            case FACEBOOK_FRAGMENT:
                fragmentTransaction.replace(R.id.home, WebViewFragment.newInstance(getString(R.string.facebookUrl), toolbarLayout), FRAGMENT_TAG);
                break;
            case VIEWABLE_ONLY_MENU_FRAGMENT:
                fragmentTransaction.replace(R.id.home, ViewableOnlyMenuFragment.newInstance(toolbarLayout), FRAGMENT_TAG);
                break;
            case WEEKDAY_SPECIALS_FRAGMENT:
                fragmentTransaction.replace(R.id.home, WeekDaySpecialsFragment.newInstance(toolbarLayout), FRAGMENT_TAG);
                break;
            case HOURS_CONTACT_FRAGMENT:
                fragmentTransaction.replace(R.id.home, HoursContactFragment.newInstance(toolbarLayout), FRAGMENT_TAG);
                break;
        }
        fragmentTransaction.commit();
    }

}
