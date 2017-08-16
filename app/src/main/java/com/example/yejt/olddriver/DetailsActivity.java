package com.example.yejt.olddriver;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = this.getIntent();
        SearchResult item = (SearchResult)intent.getSerializableExtra("Item");

        this.setTitle(item.title);

        SearchForDetailsTask searchForDetailsTask = new SearchForDetailsTask();
        searchForDetailsTask.execute(item);

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        FloatingActionButton shareButton = (FloatingActionButton)findViewById(R.id.shareFloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                TextView magnetCodeText = (TextView)findViewById(R.id.details_magnet_code);
                ClipData data = ClipData.newPlainText("text", magnetCodeText.getText());
                cm.setPrimaryClip(data);

                Toast.makeText(DetailsActivity.this, "磁力链复制成功!", Toast.LENGTH_LONG).show();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                TextView textView = (TextView)findViewById(R.id.details_magnet_code);
                TextView listView = (TextView)findViewById(R.id.details_doc_list);
                String shareStr = "Title: " + DetailsActivity.this.getTitle() + "\n\n"
                        + "Magnet Code: " + textView.getText() + "\n\n"
                        + "Doc list: " + listView.getText();
                intent.putExtra(Intent.EXTRA_TEXT, shareStr);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
            }
        });
    }


    public class SearchForDetailsTask extends AsyncTask<SearchResult, Void, SearchResult>
    {
        @Override
        protected void onPostExecute(SearchResult searchResult)
        {

            TextView hotNumText = (TextView)findViewById(R.id.details_hot_num);
            hotNumText.setText("热度:" + Integer.toString(searchResult.downloadHot));

            TextView uploadDateText = (TextView)findViewById(R.id.details_upload_date);
            uploadDateText.setText("日期:" + searchResult.createdDate.toString());

            TextView sizeText = (TextView)findViewById(R.id.details_size);
            sizeText.setText("大小:" + searchResult.size);

            TextView magnetText = (TextView)findViewById(R.id.details_magnet_code);
            magnetText.setText(searchResult.magnetCode);

            TextView listText = (TextView)findViewById(R.id.details_doc_list);
            int i = 1;
            for(String s : searchResult.docList)
                listText.append("【" + i++ + "】: " + s + "\n\n");
        }

        @Override
        protected SearchResult doInBackground(SearchResult... searchResults)
        {
            SearchResult input = searchResults[0];
            Document doc;
            try
            {
                doc = Jsoup.connect(SearchContract.PREFIX_DETAIL + input.linkToDetail).get();
                String magnetCode = doc.getElementById("wall").child(1).child(0).text();
                input.magnetCode = magnetCode;
                Elements docListElement = doc.getElementById("wall").child(2).children();
                for(Element e : docListElement)
                    input.docList.add(e.ownText());
            }
            catch (IOException e)
            {
                Log.e("Details", "Connection failed");
            }

            return input;
        }
    }

    private int getBackgroundColor(int hotNum)
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
        return ContextCompat.getColor(this, colorResourceId);
    }
}
