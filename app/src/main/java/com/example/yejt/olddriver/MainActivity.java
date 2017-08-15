package com.example.yejt.olddriver;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity
{
    SearchView searchView;
    ListView resListView;
    ProgressBar progressBar;
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = (SearchView)findViewById(R.id.searchView);
        resListView = (ListView)findViewById(R.id.resultListView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        emptyView = (TextView)findViewById(R.id.empty_view);

        resListView.setEmptyView(emptyView);
        progressBar.setVisibility(View.INVISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                SearchTask searchTask = new SearchTask(MainActivity.this, resListView, progressBar, emptyView);
                searchTask.execute(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });


        final Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        resListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                SearchResult item = (SearchResult)parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Item", item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

}
