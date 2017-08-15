package com.example.yejt.olddriver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yejt on 2017/8/13 0013.
 */
public class SearchResult implements Comparable<SearchResult>
{
    public String title;
    public String linkToDetail;
    public String size;
    public int downloadHot;
    public Date createdDate;

    public List<String> docList = new ArrayList<>();
    public String magnetCode;

    @Override
    public int compareTo(SearchResult o)
    {
        return o.downloadHot - this.downloadHot;
    }

}
