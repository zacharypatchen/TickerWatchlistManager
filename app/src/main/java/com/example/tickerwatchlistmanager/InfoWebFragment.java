package com.example.tickerwatchlistmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoWebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoWebFragment extends Fragment {
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_web, container, false);
        webView = view.findViewById(R.id.webViewInfoWeb);
        String defaultUrl = "https://seekingalpha.com";
        webView.loadUrl(defaultUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        return view;
    }

    public void loadWebsiteForTicker(String ticker) {
        String tickerUrl = "https://seekingalpha.com/symbol/" + ticker;
        webView.loadUrl(tickerUrl);
    }
}
