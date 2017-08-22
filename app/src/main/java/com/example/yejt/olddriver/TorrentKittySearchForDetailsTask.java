package com.example.yejt.olddriver;

import android.content.Context;
import android.widget.TextView;

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
        return super.doInBackground(searchResults);
    }
}
