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

        SearchForDetailsTask searchForDetailsTask = new Sobt8SearchForDetailsTask(this,
                (TextView)findViewById(R.id.details_hot_num),
                (TextView)findViewById(R.id.details_upload_date),
                (TextView)findViewById(R.id.details_magnet_code),
                (TextView)findViewById(R.id.details_size),
                (TextView)findViewById(R.id.details_doc_list));
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
}
