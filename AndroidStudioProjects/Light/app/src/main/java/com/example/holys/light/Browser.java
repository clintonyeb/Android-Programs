package com.example.holys.light;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Browser extends AppCompatActivity {

    private WebView webView;
    ProgressBar bar;
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
        bar.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged (WebView view, int newProgress) {
                Browser.this.setTitle("Loading");
                Browser.this.setProgress(newProgress);
                bar.setProgress(newProgress);
                if (newProgress == 100) {
                    Browser.this.setTitle(getResources().getString(R.string.app_name) + " Browser");
                    bar.setProgress(0);
                }

                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(address);
    }
}
