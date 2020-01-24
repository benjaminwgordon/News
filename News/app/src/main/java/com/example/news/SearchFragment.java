package com.example.news;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
    Search fragment defines behavior for the main page of the application, where the user
    can input search terms, press a submit button, and receive a list view of all the articles
    which can be clicked to receive a details page
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    private EditText editTextView;
    private ListView listView;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Button searchSubmitButton = (Button) view.findViewById(R.id.searchSubmitButton);
        searchSubmitButton.setOnClickListener(this);
        return view;
    }

    //gets a reference to the views so they can be accessed by the main activity
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        editTextView = getActivity().findViewById(R.id.editText);
        listView = getActivity().findViewById(R.id.listView);
    }

    @Override
    public void onClick(View view){
        MainActivity parent = (MainActivity) getActivity();
        parent.onSearchSubmit();
    }

    public String getSearchTerm(){
        return editTextView.getText().toString();
    }

    /*
        converts the JSON object returned by the async task in MainActivity into an ArrayList of
        Article Objects, and then attaches a custom ArrayAdapter to display these Article objects
     */
    public void updateListView(JSONObject jsonObject){
        try {
            //parse the json into an array list
            JSONArray articles = jsonObject.getJSONArray("articles");
            final ArrayList<Article> listItems = new ArrayList<Article>();
            for (int i = 0; i < articles.length(); i++){
                System.out.println(articles.getJSONObject(i).toString());
                String source = articles.getJSONObject(i).getJSONObject("source").getString("name");
                String author = articles.getJSONObject(i).getString("author");
                String title = articles.getJSONObject(i).getString("title");
                String preview = articles.getJSONObject(i).getString("content");
                String url = articles.getJSONObject(i).getString("url");
                if (source.length() > 40){
                    source = source.substring(0,40) + "...";
                }
                if (author.length() > 40){
                    author = author.substring(0,40) + "...";
                }
                listItems.add(new Article(source, author, preview, title, url));
            }

            CustomArrayAdapter articleAdapter = new CustomArrayAdapter(getContext(), listItems);
            listView.setAdapter(articleAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    System.out.println("calling go to preview for: " + listItems.get(position).getTitle());
                    mainActivity.goToPreview(listItems.get(position));
                }
            });
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}
