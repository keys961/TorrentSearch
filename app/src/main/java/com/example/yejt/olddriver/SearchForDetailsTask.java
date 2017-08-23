package com.example.yejt.olddriver;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Yejt on 2017/8/22 0022.
 */
public abstract class SearchForDetailsTask extends AsyncTask<SearchResult, Void, SearchResult>
{
    protected Context context;
    protected TextView hotNumText;
    protected TextView uploadDateText;
    protected TextView sizeText;
    protected TextView magnetText;
    protected TextView listText;


    public SearchForDetailsTask(Context context, TextView hotNumText, TextView uploadDateText,
                                TextView sizeText, TextView magnetText, TextView listText)
    {
        this.context = context;
        this.hotNumText = hotNumText;
        this.uploadDateText = uploadDateText;
        this.sizeText = sizeText;
        this.magnetText = magnetText;
        this.listText = listText;
    }

    @Override
    protected void onPostExecute(SearchResult searchResult)
    {
        hotNumText.setText("热度:" + Integer.toString(searchResult.downloadHot));
        uploadDateText.setText("日期:" + searchResult.createdDate.toString());
        sizeText.setText("大小:" + searchResult.size);
        magnetText.setText(searchResult.magnetCode);
        int i = 1;
        for (String s : searchResult.docList)
            listText.append("【" + i++ + "】: " + s + "\n\n");
    }

    @Override
    protected SearchResult doInBackground(SearchResult... searchResults)
    {
        SearchResult input = searchResults[0];
        Document doc;
        try
        {
            doc = Jsoup.connect(SearchContract.Sobt8Contract.PREFIX_DETAIL + input.linkToDetail).get();
            String magnetCode = doc.getElementById("wall").child(1).child(0).text();
            input.magnetCode = magnetCode;
            Elements docListElement = doc.getElementById("wall").child(2).children();
            for (Element e : docListElement)
                input.docList.add(e.ownText());
        }
        catch (IOException e)
        {
            Log.e("Details", "Connection failed");
        }

        return input;
    }


    protected int getBackgroundColor(int hotNum)
    {
        int colorResourceId;
        switch (hotNum / 50)
        {
            case 0:
                colorResourceId = R.color.level_1;
                break;
            case 1:
                colorResourceId = R.color.level_2;
                break;
            case 2:
                colorResourceId = R.color.level_3;
                break;
            case 3:
                colorResourceId = R.color.level_4;
                break;
            case 4:
                colorResourceId = R.color.level_5;
                break;
            case 5:
                colorResourceId = R.color.level_6;
                break;
            case 6:
                colorResourceId = R.color.level_7;
                break;
            case 7:
                colorResourceId = R.color.level_8;
                break;
            case 8:
                colorResourceId = R.color.level_9;
                break;
            case 9:
                colorResourceId = R.color.level_10;
                break;
            default:
                colorResourceId = R.color.level_10;
        }
        return ContextCompat.getColor(context, colorResourceId);
    }
}
