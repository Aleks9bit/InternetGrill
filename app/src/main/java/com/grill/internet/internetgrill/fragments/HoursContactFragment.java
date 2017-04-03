package com.grill.internet.internetgrill.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grill.internet.internetgrill.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by denys on 29.03.17.
 */

public class HoursContactFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback {
    private static View viewToolbarLayout;
    private View toolbarBackArrow;
    private View currentView;
    private TextView toolbarTitle;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    double lat = 40.597410;
    double lng = -74.181969;
    private ArrayList listBottomTabsView = new ArrayList();;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("494 Chicken"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                openNavigator();
            }
        });
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

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_hours_contact;
    }

    public static HoursContactFragment newInstance(View viewToolbar) {
        HoursContactFragment.viewToolbarLayout = viewToolbar;
        return new HoursContactFragment();
    }

    private void createListMenuBottomTabs(View view) {
        listBottomTabsView.clear();
        listBottomTabsView.add(view.findViewById(R.id.hoursContactFragmentMenuBottomAddress));
        listBottomTabsView.add(view.findViewById(R.id.hoursContactFragmentMenuBottomInfo));
        listBottomTabsView.add(view.findViewById(R.id.hoursContactFragmentMenuBottomContact));
        listBottomTabsView.add(view.findViewById(R.id.hoursContactFragmentMenuBottomHours));
    }

    @Override
    public void initFragmentsViews(View view) {
        createListMenuBottomTabs(view);
        currentView = getView();

        toolbarTitle = (TextView) viewToolbarLayout.findViewById(R.id.toolbarTitle);

        toolbarBackArrow = viewToolbarLayout.findViewById(R.id.backArrowFragment);
        toolbarBackArrow.setOnTouchListener(this);
        toolbarBackArrow.setOnClickListener(this);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        view.findViewById(R.id.map).getLayoutParams().height = getActivity().getWindowManager()
                .getDefaultDisplay().getHeight() / 3;

        view.findViewById(R.id.hoursContactFragmentMenuBottomAddress).setOnClickListener(this);
        view.findViewById(R.id.hoursContactFragmentMenuBottomInfo).setOnClickListener(this);
        view.findViewById(R.id.hoursContactFragmentMenuBottomContact).setOnClickListener(this);
        view.findViewById(R.id.hoursContactFragmentMenuBottomHours).setOnClickListener(this);

        openDefaultMenuBottomFragment(view.findViewById(R.id.hoursContactFragmentMenuBottomAddress));
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarTitle.setText(getResources().getString(R.string.menuHoursContact));
        viewToolbarLayout.findViewById(R.id.hamburgerHomeFragment).setVisibility(View.GONE);
        toolbarBackArrow.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backArrowFragment:
                replaceFragment(HomeFragment.newInstance(viewToolbarLayout));
                break;
            case R.id.hoursContactFragmentMenuBottomAddress:
                setMenuBottomFragment(R.id.hoursContactFragmentMenuBottomAddress, v);
                break;
            case R.id.hoursContactFragmentMenuBottomInfo:
                setMenuBottomFragment(R.id.hoursContactFragmentMenuBottomInfo, v);
                break;
            case R.id.hoursContactFragmentMenuBottomContact:
                setMenuBottomFragment(R.id.hoursContactFragmentMenuBottomContact, v);
                break;
            case R.id.hoursContactFragmentMenuBottomHours:
                setMenuBottomFragment(R.id.hoursContactFragmentMenuBottomHours, v);
                break;
        }
    }

    private void openDefaultMenuBottomFragment(View view) {
        setMenuBottomFragment(R.id.hoursContactFragmentMenuBottomAddress, view);
    }

    private void setMenuBottomFragment(int id, View selectedTab) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.hoursContactFragmentMenuBottomFragment
                        , HoursContactMenuBottomFragment.newInstance(id, selectedTab, listBottomTabsView, viewToolbarLayout))
                .commit();
    }
}
