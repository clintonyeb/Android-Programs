package com.example.clinton.companion.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.clinton.companion.R;

public class CompanionBrowser extends AppCompatActivity {

    ProgressBar bar;
    private WebView webView;

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack())
        {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (webView.canGoBack())
                    webView.goBack();
                else finish();
                break;
        }
        return true;
    }

    @Override
    protected void onPause () {
        super.onPause();
        webView.clearCache(true);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        Intent intent = getIntent();
        String address = intent.getStringExtra(Intent.EXTRA_TEXT);
        webView = (WebView)findViewById(R.id.webViewer);
        bar = (ProgressBar)findViewById(R.id.browser_progrss_bar);
        if (bar != null) {
            bar.setMax(100);
            bar.getProgressDrawable().setColorFilter(
                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString("AndroidWebView");
        CompanionBrowser.this.setTitle("Loading");
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                CompanionBrowser.this.setTitle(title);
            }

            @Override
            public void onProgressChanged (WebView view, int newProgress) {
                CompanionBrowser.this.setProgress(newProgress);
                bar.setProgress(newProgress);
                if (newProgress > 40)
                    bar.getProgressDrawable().setColorFilter(
                            Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
                if (newProgress > 80)
                    bar.getProgressDrawable().setColorFilter(
                            getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                if (newProgress == 100) {
                    bar.setProgress(100);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.loadUrl(address);

    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.stopLoading();
    }
}
