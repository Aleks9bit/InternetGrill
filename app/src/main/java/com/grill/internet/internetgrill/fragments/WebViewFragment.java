package com.grill.internet.internetgrill.fragments;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.grill.internet.internetgrill.R;

/**
 * Created by denys on 24.03.17.
 */

public class WebViewFragment extends BaseFragment implements View.OnClickListener {
    private WebView mWebView;
    private static String url;
    private static View viewToolbarLayout;
    private TextView urlTitle;
    private TextView toolbarTitle;
    private View webViewNavigationBack;
    private View webViewNavigationForward;
    private View toolbarBackArrow;
    private ProgressBar progressBar;
    private View webViewNavigationLayout;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_webview;
    }

    @Override
    public void initFragmentsViews(View view) {
        urlTitle = (TextView) view.findViewById(R.id.urlTitle);
        toolbarTitle = (TextView) viewToolbarLayout.findViewById(R.id.toolbarTitle);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        webViewNavigationLayout = view.findViewById(R.id.webViewNavigationLayout);
        webViewNavigationLayout.setVisibility(View.GONE);

        toolbarBackArrow = viewToolbarLayout.findViewById(R.id.backArrowFragment);
        toolbarBackArrow.setOnTouchListener(this);
        toolbarBackArrow.setOnClickListener(this);

        webViewNavigationBack = view.findViewById(R.id.webViewNavigationBack);
        webViewNavigationBack.setOnClickListener(this);
        webViewNavigationBack.setOnTouchListener(this);

        webViewNavigationForward = view.findViewById(R.id.webViewNavigationForward);
        webViewNavigationForward.setOnClickListener(this);
        webViewNavigationForward.setOnTouchListener(this);

        mWebView = (WebView) view.findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                urlTitle.setText(view.getTitle());
                progressBar.setVisibility(View.GONE);
                webViewNavigationLayout.setVisibility(View.VISIBLE);
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        if (url.equals(getString(R.string.facebookUrl)))
            mWebView.getSettings().setUserAgentString("Mozilla/6.0 (Windows NT 6.2; WOW64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1");
        mWebView.loadUrl(url);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewToolbarLayout.findViewById(R.id.hamburgerHomeFragment).setVisibility(View.GONE);
        toolbarBackArrow.setVisibility(View.VISIBLE);
        toolbarTitle.setText(getCurrentToolbarTitle(url));
        Log.d("denys", "onResume");
    }

    private String getCurrentToolbarTitle(String url) {
        if (url.equals(getString(R.string.facebookUrl)))
            return "Facebook";
        if (url.equals(getString(R.string.cateringUrl)))
            return "Catering";
        if (url.equals(getString(R.string.orderOnlineUrl)))
            return "Order Online";
        if (url.equals(getString(R.string.myAccountUrl)))
            return "My Account";
        if (url.equals(getString(R.string.familySpecialsUrl)))
            return "Family Specials";
        if (url.equals(getResources().getString(R.string.webSite)))
            return "Internet Grill";
        return "";
    }

    public static WebViewFragment newInstance(String url, View viewToolbar) {
        WebViewFragment.viewToolbarLayout = viewToolbar;
        WebViewFragment.url = url;
        return new WebViewFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.webViewNavigationBack:
                if (mWebView.copyBackForwardList().getCurrentIndex() > 0) {
                    mWebView.goBack();
                }
                break;
            case R.id.webViewNavigationForward:
                mWebView.goForward();
                break;
            case R.id.backArrowFragment:
                replaceFragment(HomeFragment.newInstance(viewToolbarLayout));
                break;
        }
    }
}
