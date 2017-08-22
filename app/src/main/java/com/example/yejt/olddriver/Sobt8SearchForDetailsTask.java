package com.example.yejt.olddriver;

import android.content.Context;
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
public class Sobt8SearchForDetailsTask extends SearchForDetailsTask
{
    public Sobt8SearchForDetailsTask(Context context, TextView hotNumText, TextView uploadDateText,
                                TextView sizeText, TextView magnetText, TextView listText)
    {
        super(context, hotNumText, uploadDateText, sizeText, magnetText, listText);
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
}
