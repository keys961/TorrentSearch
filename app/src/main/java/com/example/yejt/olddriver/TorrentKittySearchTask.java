package com.example.yejt.olddriver;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yejt on 2017/8/22 0022.
 */
public class TorrentKittySearchTask extends SearchTask
{

    public TorrentKittySearchTask(Context context, ListView listView, ProgressBar progressBar, TextView emptyView)
    {
        super(context, listView, progressBar, emptyView);
    }

    @Override
    protected void onPostExecute(List<SearchResult> searchResults)
    {
        super.onPostExecute(searchResults);
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
    }

    @Override
    protected List<SearchResult> doInBackground(String... strings)
    {
        return null;
    }
}
