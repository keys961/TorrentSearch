package com.example.yejt.olddriver;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Date;
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
        String query = strings[0];
        Document doc = null;
        try
        {
            doc = Jsoup.connect(SearchContract.TorrentKittyContract.PREFIX + query).get();
        }
        catch (IOException e)
        {
            Log.e("Connection Error", "doInBackground: ", e.getCause());
        }
        int pageNum = 1;
        try
        {
            Element element = doc.getElementsByClass("pagination").first();
            Element child = element.children().get(element.children().size() - 2);
            pageNum = Integer.parseInt(child.text());
        }
        catch (Exception e)
        {
            Log.e("No more pages", "doInBackground", e.getCause());
        }
        for(int i = 1; i <= pageNum; i++)
        {
            try
            {
                doc = Jsoup.connect(SearchContract.TorrentKittyContract.PREFIX + query
                        + "/" + i).get();
            }
            catch (IOException e)
            {
                Log.e("Connection Error", "doInBackground: ", e.getCause());
            }

            Elements searchItems;
            try
            {
                searchItems = doc.getElementById("archiveResult").child(0).children();
            }
            catch (Exception e)
            {
                break;
            }

            for(int j = 1; j < searchItems.size(); j++)
            {
                SearchResult result = new SearchResult();
                result.title = searchItems.get(j).child(0).text();
                result.size = searchItems.get(j).child(1).text().toUpperCase();
                if(result.size.equals("")) //No valid result
                    break;
                result.createdDate = Date.valueOf(searchItems.get(j).child(2).text());
                result.magnetCode = searchItems.get(j).child(3).child(1).attr("href");
                result.linkToDetail = searchItems.get(j).child(3).child(0).attr("href");
                list.add(result);
            }
        }
        return list;
    }
}
