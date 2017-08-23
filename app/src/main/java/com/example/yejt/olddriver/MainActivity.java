package com.example.yejt.olddriver;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity
{
    private SearchView searchView;
    private ListView resListView;
    private ProgressBar progressBar;
    private TextView emptyView;

    private int source = SearchContract.SOURCE_SOBT8;

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
                SearchFactory factory = SearchFactory.getFactory(source);
                SearchTask searchTask = factory.getSearchTask(MainActivity.this, resListView, progressBar, emptyView);
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
                bundle.putSerializable("Factory", SearchFactory.getFactory(source));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.source_sobt8:
                if(source != SearchContract.SOURCE_SOBT8)
                {
                    source = SearchContract.SOURCE_SOBT8;
                    resListView.setAdapter(null);
                }
                break;
            case R.id.source_torrent_kitty:
                if(source != SearchContract.SOURCE_TORRENT_KITTY)
                {
                    source = SearchContract.SOURCE_TORRENT_KITTY;
                    resListView.setAdapter(null);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
