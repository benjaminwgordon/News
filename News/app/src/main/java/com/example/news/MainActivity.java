package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private SearchFragment searchFragment;
    public Article currentDisplayArticle;
    public String searchTerm;


    /*
        Application begins with a SearchFragment loaded
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        fragmentTransaction.add(R.id.mainActivityContainerView, searchFragment);
        fragmentTransaction.commit();
        this.searchFragment = searchFragment;
    }

    /*
        Replaces SearchFragment with a PreviewFragment that shows details of selected article
     */
    public void goToPreview(Article article){
        currentDisplayArticle = article;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PreviewFragment previewFragment = new PreviewFragment();
        fragmentTransaction.replace(R.id.mainActivityContainerView, previewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    public void openFullArticle(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DisplayArticleFragment displayArticleFragment = new DisplayArticleFragment();
        fragmentTransaction.replace(R.id.mainActivityContainerView, displayArticleFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*
        Builds a url string to query News API for relevant articles, calls a new AsyncTask to
        complete the query
     */
    public void onSearchSubmit(){
        String queryTerm = this.searchFragment.getSearchTerm();
        System.out.println(queryTerm);
        if(!queryTerm.isEmpty()) {
            this.searchTerm = queryTerm;
            String weekPriorDate = getWeekPriorDate();
            String key = "ed2b9061d5eb4c95ae44f7230877e6f7";
            String url = String.format("https://newsapi.org/v2/everything?sortBy=publishedAt&q=%s&from=%s&apiKey=%s", queryTerm, weekPriorDate, key);
            System.out.println(url);
            new AsyncLoadNews().execute(url);
        }
    }

    //returns a formatted string representing the date one week prior
    public String getWeekPriorDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        String weekPriorDate = dateFormat.format(calendar.getTime());
        return weekPriorDate;
    }

    public Article getCurrentDisplayArticle(){
        return this.currentDisplayArticle;
    }

    /*
        async load the JSON object from the given url, calls updateListView(), which loads all the
        values into the
     */
    private class AsyncLoadNews extends AsyncTask<String,String, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                String json = "";
                String line;
                URL url = new URL(strings[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((line = in.readLine()) != null) {
                    System.out.println("JSON LINE " + line);
                    json += line;
                }
                in.close();
                JSONObject jsonObject = new JSONObject(json);
                return jsonObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            searchFragment.updateListView(jsonObject);
        }
    }
}
