package com.applicaster.feedintegrationsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.applicaster.feed.ui.FeedButtonView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.feed_menu_button);

        FeedButtonView feedButtonView = new FeedButtonView(this);

        feedButtonView.setLayoutParams(new RelativeLayout.LayoutParams(55,55));

        feedButtonView.setPadding(5,0,5,0);

        item.setActionView(feedButtonView);

        return true;
    }
}

