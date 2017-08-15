package com.example.yejt.olddriver;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Intent intent = this.getIntent();
        SearchResult item = (SearchResult)intent.getSerializableExtra("Item");

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public class SearchForDetails extends AsyncTask<SearchResult, Void, SearchResult>
    {
        @Override
        protected void onPostExecute(SearchResult searchResult)
        {

        }

        @Override
        protected SearchResult doInBackground(SearchResult... searchResults)
        {
            SearchResult input = searchResults[0];
            Document doc;
            try
            {
                doc = Jsoup.connect(SearchContract.PREFIX_DETAIL + input.linkToDetail).get();
                String magnetCode = doc.getElementById("wall").child(1).child(0).text();
                Element docListElement = doc.getElementById("wall").child(2);
                //TODO: Get doc list Strings

            }
            catch (IOException e)
            {
                Log.e("Details", "Connection failed");
            }

        }
    }

}
