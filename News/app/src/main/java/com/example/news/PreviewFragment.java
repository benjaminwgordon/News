package com.example.news;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
/*
    Preview fragment defines behavior for the details page of the application, where the user
    can get the title and a preview of the article, and can click on a button that opens up a
    DisplayArticleFragment where the full WebView of the website is displayed
 */

public class PreviewFragment extends Fragment implements View.OnClickListener {

    public PreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        Button goToArticleButton = (Button) view.findViewById(R.id.goToArticleButton);
        goToArticleButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        MainActivity context = (MainActivity) getActivity();
        Article displayArticle = context.getCurrentDisplayArticle();
        TextView title = context.findViewById(R.id.title);
        title.setText(displayArticle.getTitle());
        TextView previewText = context.findViewById(R.id.previewText);
        previewText.setText(displayArticle.getPreview());
    }


    @Override
    public void onClick(View view){
        MainActivity context = (MainActivity) getActivity();
        context.openFullArticle();
    }


}
