package com.example.yejt.olddriver;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Created by Yejt on 2017/8/22 0022.
 */
public class Sobt8SearchTask extends SearchTask
{
    public Sobt8SearchTask(Context context, ListView listView, ProgressBar progressBar, TextView emptyView)
    {
        super(context, listView, progressBar, emptyView);
    }

    @Override
    protected void onPostExecute(List<SearchResult> searchResults)
    {
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        emptyView.setText("没找到所搜索项！");

        adapter = new SearchResultAdapter(context, searchResults);
        listView.setAdapter(adapter);
        Toast.makeText(context, "共搜索到" + searchResults.size() + "条记录", Toast.LENGTH_LONG).show();
    }

    @Override
    protected List<SearchResult> doInBackground(String... queries)
    {
        String url = SearchContract.Sobt8Contract.PREFIX + queries[0] +
                SearchContract.Sobt8Contract.POSTFIX;
        Document doc = null;
        try
        {
            doc = Jsoup.connect(url).get();
            //Log.i()
        }
        catch (IOException e)
        {
            Log.e("Connection Error", "doInBackground: ", e.getCause());
        }
        int pageNum = 1;
        try
        {
            String[] pages = doc.getElementsByClass("last_p").last().child(0).attr("href").
                    split("\\/|_|\\.");
            pageNum = Integer.parseInt(pages[pages.length - 2]);
            //~.com/q/xx_rel_num.html

        }
        catch (Exception e)
        {
            Log.e("No more pages", "doInBackground", e.getCause());
        }

        for (int i = 1; i <= pageNum; i++)
        {
            try
            {
                doc = Jsoup.connect(SearchContract.Sobt8Contract.PREFIX + queries[0] +
                        SearchContract.Sobt8Contract.INFIX + i + SearchContract.Sobt8Contract.POSTFIX).get();
            }
            catch (IOException e)
            {
                Log.e("Connection Error", "doInBackground: ", e.getCause());
            }

            Elements searchItems;
            try
            {
                searchItems = doc.getElementsByClass("search-item");
            }
            catch (NullPointerException e)
            {
                break;
            }

            for(Element item : searchItems)
            {
                SearchResult result = new SearchResult();
                result.title = item.child(0).child(0).child(0).text();
                result.linkToDetail = item.child(0).child(0).child(0).attr("href");
                result.downloadHot = Integer.parseInt(item.child(2).child(2).child(0).text());
                result.createdDate = Date.valueOf(item.child(2).child(0).child(0).text());
                result.size = item.child(2).child(1).child(0).text();
                list.add(result);
            }
        }
        return list;
    }

    @Override
    protected void onPreExecute()
    {
        this.progressBar.setVisibility(View.VISIBLE);
        this.listView.setVisibility(View.INVISIBLE);
        if(adapter != null)
            adapter.clear();
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {

        progressBar.setProgress(values[0]);
    }
}
