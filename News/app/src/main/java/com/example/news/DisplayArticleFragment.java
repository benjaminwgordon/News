package com.example.news;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;


/*
    DisplayArticleFragment defines behavior for the fragment which solely contains a WebView
    where the currently selected article is displayed in full
 */
public class DisplayArticleFragment extends Fragment  {

    public DisplayArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_article, container, false);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        MainActivity context = (MainActivity) getActivity();
        Article displayArticle = context.getCurrentDisplayArticle();
        WebView webView = context.findViewById(R.id.webView);
        webView.loadUrl(displayArticle.getUrl());
    }
}
