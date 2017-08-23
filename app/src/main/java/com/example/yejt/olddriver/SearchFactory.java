package com.example.yejt.olddriver;

import android.content.Context;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yejt on 2017/8/22 0022.
 */
public class SearchFactory implements Serializable
{
    private int source;

    private static SearchFactory sobt8Factory;

    private static SearchFactory torrentKittyFactory;

    private SearchFactory(int source)
    {
        this.source = source;
    }

    public int getSource()
    {
        return this.source;
    }

    public static SearchFactory getFactory(int source)
    {
        switch (source)
        {
            case SearchContract.SOURCE_SOBT8:
                if(sobt8Factory == null)
                    return sobt8Factory = new SearchFactory(SearchContract.SOURCE_SOBT8);
                return sobt8Factory;
            case SearchContract.SOURCE_TORRENT_KITTY:
                if(torrentKittyFactory == null)
                    return torrentKittyFactory = new SearchFactory(SearchContract.SOURCE_TORRENT_KITTY);
                return torrentKittyFactory;
        }
        return (sobt8Factory == null) ?
                sobt8Factory = new SearchFactory(SearchContract.SOURCE_SOBT8) :
                sobt8Factory;
    }

    public SearchTask getSearchTask(Context context, ListView listView, ProgressBar progressBar,
                                    TextView emptyView)
    {
        switch (source)
        {
            case SearchContract.SOURCE_SOBT8: return new Sobt8SearchTask(context,
                    listView, progressBar, emptyView);
            case SearchContract.SOURCE_TORRENT_KITTY: return new TorrentKittySearchTask(context,
                    listView, progressBar, emptyView);
            default: return null;
        }
    }

    public SearchForDetailsTask getSearchForDetailsTask(Context context, TextView hotNumText, TextView uploadDateText,
                                                        TextView sizeText, TextView magnetText, TextView listText)
    {
        switch (source)
        {
            case SearchContract.SOURCE_SOBT8:
                return new Sobt8SearchForDetailsTask(context, hotNumText, uploadDateText,
                        sizeText, magnetText, listText);
            case SearchContract.SOURCE_TORRENT_KITTY:
                return new TorrentKittySearchForDetailsTask(context, hotNumText, uploadDateText,
                        sizeText, magnetText, listText);
            default: return null;
        }
    }
}
