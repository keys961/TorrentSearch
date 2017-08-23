package com.example.yejt.olddriver;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Yejt on 2017/8/22 0022.
 */
public class TorrentKittySearchForDetailsTask extends SearchForDetailsTask
{
    public TorrentKittySearchForDetailsTask(Context context, TextView hotNumText, TextView uploadDateText,
                                     TextView sizeText, TextView magnetText, TextView listText)
    {
        super(context, hotNumText, uploadDateText, sizeText, magnetText, listText);
    }

    @Override
    protected void onPostExecute(SearchResult searchResult)
    {
        super.onPostExecute(searchResult);
    }

    @Override
    protected SearchResult doInBackground(SearchResult... searchResults)
    {
        SearchResult input = searchResults[0];
        try
        {
            Document doc = Jsoup.connect(SearchContract.TorrentKittyContract.PREFIX_DETAIL
                    + input.linkToDetail).get();
            input.size = doc.getElementsByClass("detailSummary").get(0).
                    child(0).child(3).child(1).text();
            Elements torrentDetails = doc.getElementById("torrentDetail").child(0).children();
            for(int i = 1; i < torrentDetails.size(); i++)
                input.docList.add(torrentDetails.get(i).child(1).text());
        }
        catch (IOException e)
        {
            Log.e("Details", "Connection failed");
        }

        return input;
    }
}
