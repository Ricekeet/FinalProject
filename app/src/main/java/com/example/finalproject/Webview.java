package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class Webview extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get references to the web view and progress bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_widgets_webview);
        WebView myWebView = (WebView) findViewById(R.id.webView);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //myWebView.loadUrl("https://www.conestogac.on.ca/");
        // enable JavaScript for the web view
        myWebView.getSettings().setJavaScriptEnabled(true);

        // load URLs in the web view, not in browser app
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        // display the progress bar until the page is 100% loaded
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        // load the content from the specified URL into the web view
        myWebView.loadUrl("https://www.conestogac.on.ca/");
    }
}
