package com.example.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
    CustomArrayAdapter is an extending class of ArrayAdapter that defines how each line of the
    ListView will be rendered
 */

public class CustomArrayAdapter extends ArrayAdapter<Article>  {

    private Context localContext;
    private List<Article> articleList = new ArrayList<>();

    public CustomArrayAdapter(Context context, ArrayList<Article> articles){
        super(context, 0, articles);
        localContext = context;
        articleList = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(localContext).inflate(R.layout.row, parent, false);
        }

        Article currentArticle = articleList.get(position);

        TextView source = listItem.findViewById(R.id.source);
        source.setText(currentArticle.getSource());

        TextView author = listItem.findViewById(R.id.author);
        author.setText(currentArticle.getAuthor());

        return listItem;
    }

}
