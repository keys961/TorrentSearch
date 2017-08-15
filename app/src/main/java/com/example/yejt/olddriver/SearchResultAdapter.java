package com.example.yejt.olddriver;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yejt on 2017/8/15 0015.
 */
public class SearchResultAdapter extends ArrayAdapter<SearchResult>
{
    public SearchResultAdapter(Context context, List<SearchResult> list)
    {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        SearchResult item = getItem(position);
        int hotNum = item.downloadHot;
        String uploadDate = item.createdDate.toString();
        String title = item.title;
        String size = item.size;
        int listId = position + 1;
        //Item id
        TextView listIdText = (TextView)listItemView.findViewById(R.id.listId);
        listIdText.setText(Integer.toString(listId));
        listIdText.setTextColor(getColor(hotNum));
        //Title
        TextView titleText = (TextView)listItemView.findViewById(R.id.itemTitle);
        titleText.setText(title);
        //Size
        TextView sizeText = (TextView)listItemView.findViewById(R.id.size);
        sizeText.setText("大小: " + size);
        //HotNum
        TextView hotText = (TextView)listItemView.findViewById(R.id.hotNum);
        hotText.setText("热度：" + hotNum);
        //Date
        TextView dateText = (TextView)listItemView.findViewById(R.id.uploadDate);
        dateText.setText("日期: " + uploadDate);

        //GradientDrawable circle = (GradientDrawable)listIdText.getBackground();


        return listItemView;
    }

    private int getColor(int hotNum)
    {
        int colorResourceId;
        switch (hotNum / 50)
        {
            case 0: colorResourceId = R.color.level_1; break;
            case 1: colorResourceId = R.color.level_2; break;
            case 2: colorResourceId = R.color.level_3; break;
            case 3: colorResourceId = R.color.level_4; break;
            case 4: colorResourceId = R.color.level_5; break;
            case 5: colorResourceId = R.color.level_6; break;
            case 6: colorResourceId = R.color.level_7; break;
            case 7: colorResourceId = R.color.level_8; break;
            case 8: colorResourceId = R.color.level_9; break;
            case 9: colorResourceId = R.color.level_10; break;
            default: colorResourceId = R.color.level_10;
        }
        return ContextCompat.getColor(getContext(), colorResourceId);
    }
}
