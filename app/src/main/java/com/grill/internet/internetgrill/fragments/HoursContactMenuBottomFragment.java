package com.grill.internet.internetgrill.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grill.internet.internetgrill.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by denys on 30.03.17.
 */

public class HoursContactMenuBottomFragment extends BaseFragment implements View.OnClickListener {
    private static int layoutId;
    private static View checkedTextView;
    private static View viewToolbarLayout;
    private static ArrayList<View> listMenuBottomTabs;
    private ArrayList<String> fragmentInfoValueList;
    private ArrayList<String> fragmentInfoKeyList;
    double lat = 40.597410;
    double lng = -74.181969;

    @Override
    public int getFragmentLayout() {
        return layoutId;
    }

    public static HoursContactMenuBottomFragment newInstance(int id, View view, ArrayList<View> listMenuBottomTabs, View viewToolbarLayout) {
        switch (id) {
            case R.id.hoursContactFragmentMenuBottomAddress:
                layoutId = R.layout.fragment_hours_contact_menu_botom_address;
                break;
            case R.id.hoursContactFragmentMenuBottomInfo:
                layoutId = R.layout.fragment_hours_contact_menu_botom_info;
                break;
            case R.id.hoursContactFragmentMenuBottomContact:
                layoutId = R.layout.fragment_hours_contact_menu_botom_contact;
                break;
            case R.id.hoursContactFragmentMenuBottomHours:
                layoutId = R.layout.fragment_hours_contact_menu_botom_hours;
                break;
        }
        HoursContactMenuBottomFragment.listMenuBottomTabs = listMenuBottomTabs;
        HoursContactMenuBottomFragment.viewToolbarLayout = viewToolbarLayout;
        HoursContactMenuBottomFragment.checkedTextView = view;
        return new HoursContactMenuBottomFragment();
    }

    private void clearOtherMenuBottomTabs(View checkedView) {
        for (View view : listMenuBottomTabs) {
            if (view.getId() != checkedView.getId())
                view.setBackgroundColor(Color.TRANSPARENT);
            else
                view.setBackgroundColor(getResources().getColor(R.color.colorHoursContactMenuBottomTab));
        }
    }

    @Override
    public void initFragmentsViews(View view) {
        clearOtherMenuBottomTabs(checkedTextView);
        if (layoutId == R.layout.fragment_hours_contact_menu_botom_info) {
            initListForInfoFragment();
            createTextViews(view);
        }
        if (layoutId == R.layout.fragment_hours_contact_menu_botom_contact) {
            view.findViewById(R.id.hoursContactFragmentMenuBottomContactPhoneNumber).setOnClickListener(this);
            view.findViewById(R.id.hoursContactFragmentMenuBottomContactWebsite).setOnClickListener(this);
            view.findViewById(R.id.hoursContactFragmentMenuBottomContactEmail).setOnClickListener(this);
            view.findViewById(R.id.hoursContactFragmentMenuBottomContactPhoneNumber).setOnTouchListener(this);
            view.findViewById(R.id.hoursContactFragmentMenuBottomContactWebsite).setOnTouchListener(this);
            view.findViewById(R.id.hoursContactFragmentMenuBottomContactEmail).setOnTouchListener(this);
        }

        if (layoutId == R.layout.fragment_hours_contact_menu_botom_hours) {
            int currentDayTextViewId = getResources().getIdentifier("day_" + new Date().getDay(), "id", getActivity().getPackageName());
            view.findViewById(currentDayTextViewId).setBackgroundColor(getResources().getColor(R.color.colorHoursContactMenuBottomTabHours));
            TextView currentDay = (TextView) view.findViewById(currentDayTextViewId).findViewById(R.id.hoursContactFragmentMenuBottomHoursTextViewDay);
            currentDay.setText(currentDay.getText().toString() + " (Today)");
        }
        if (layoutId == R.layout.fragment_hours_contact_menu_botom_address) {
            view.findViewById(R.id.btnNavigate).setOnTouchListener(this);
            view.findViewById(R.id.btnNavigate).setOnClickListener(this);

        }

    }

    private void initListForInfoFragment() {
        fragmentInfoValueList = new ArrayList<>();
        fragmentInfoValueList.add("Casual");
        fragmentInfoValueList.add("Lot");
        fragmentInfoValueList.add("All");
        fragmentInfoValueList.add("24");
        fragmentInfoValueList.add("0");
        fragmentInfoValueList.add("No");
        fragmentInfoValueList.add("Yes");
        fragmentInfoValueList.add("Yes");
        fragmentInfoValueList.add("No");
        fragmentInfoValueList.add("Yes");
        fragmentInfoValueList.add("No");
        fragmentInfoValueList.add("Yes");

        fragmentInfoKeyList = new ArrayList<>();
        fragmentInfoKeyList.add("Dress Code");
        fragmentInfoKeyList.add("Parking");
        fragmentInfoKeyList.add("Age Level Preference");
        fragmentInfoKeyList.add("Seating Quantity");
        fragmentInfoKeyList.add("Max Group Size");
        fragmentInfoKeyList.add("Smoking Allowed");
        fragmentInfoKeyList.add("Delivery Available");
        fragmentInfoKeyList.add("Takeout Available");
        fragmentInfoKeyList.add("Reservations Available");
        fragmentInfoKeyList.add("Catering Available");
        fragmentInfoKeyList.add("Pets Allowed");
        fragmentInfoKeyList.add("Wheelchair Accessible");

    }

    private void createTextViews(View view) {
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.hoursContactFragmentMenuBottomInfoContainer);
        int padding = 25;
        for (int i = 0; i < fragmentInfoKeyList.size(); i++) {
            RelativeLayout relativeLayout = new RelativeLayout(getActivity());
            relativeLayout.setLayoutParams(new android.view.ViewGroup.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            TextView keyText;
            keyText = new TextView(getActivity());
            keyText.setLayoutParams(new android.view.ViewGroup.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            keyText.setPadding(padding, padding, padding, padding);
            keyText.setText(fragmentInfoKeyList.get(i));
            keyText.setTextColor(Color.BLACK);

            TextView valueText;
            valueText = new TextView(getActivity());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            valueText.setLayoutParams(params);
            valueText.setPadding(padding, padding, padding, padding);
            valueText.setText(fragmentInfoValueList.get(i));


            relativeLayout.addView(keyText);
            relativeLayout.addView(valueText);
            if (i % 2 == 0)
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.colorHoursContactMenuBottomTabInfo));
            linearLayout.addView(relativeLayout);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hoursContactFragmentMenuBottomContactPhoneNumber:
                makeCall();
                break;
            case R.id.hoursContactFragmentMenuBottomContactWebsite:
                replaceFragment(WebViewFragment.newInstance(getResources().getString(R.string.webSite), viewToolbarLayout));
                break;
            case R.id.hoursContactFragmentMenuBottomContactEmail:
                writeEmail();
                break;
            case R.id.btnNavigate:
                openNavigator();
                break;
        }
    }

    private void openNavigator() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=an+3785 Victory Boulevard \n" +
                        "Staten Island+NY"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            getActivity().startActivity(Intent.createChooser(intent, "Select your maps app"));
        }
    }

    private void writeEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getResources().getString(R.string.email), null));
        intent.putExtra(Intent.EXTRA_SUBJECT, true);
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
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
