package com.mindyourlovedone.healthcare.utility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;

//nikita - 5-10-19

public class WebPDFActivity extends AppCompatActivity {
    TextView txtTitle;
    ImageView imgBack;
    String name;
    ProgressBar progressBar;
    WebView webview_pdf;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_pdf);


        imgBack = findViewById(R.id.imgBack);
        txtTitle = findViewById(R.id.txtTitle);
        progressBar = findViewById(R.id.progressBar);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent i = getIntent();
        name = i.getExtras().getString("Name");
        txtTitle.setText(name);

        webview_pdf = (WebView) findViewById(R.id.webview_pdf);

        webview_pdf.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });

        if (name.equalsIgnoreCase("Support FAQs")||name.equalsIgnoreCase("User Guide")) {
            webview_pdf.setInitialScale(1);
            webview_pdf.getSettings().setLoadWithOverviewMode(true);
            webview_pdf.getSettings().setUseWideViewPort(true);
            webview_pdf.getSettings().setLoadsImagesAutomatically(true);
            webview_pdf.getSettings().setJavaScriptEnabled(true);
            webview_pdf.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webview_pdf.getSettings().setDomStorageEnabled(true);
            webview_pdf.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        webview_pdf.getSettings().setJavaScriptEnabled(true);
        if (getIntent().hasExtra("URL")) {
            String url = getIntent().getStringExtra("URL");
            pdfOpen(url);
        }
    }

    private void pdfOpen(String fileUrl) {

        webview_pdf.getSettings().setJavaScriptEnabled(true);
        webview_pdf.getSettings().setPluginState(WebSettings.PluginState.ON);

        //---you need this to prevent the webview from
        // launching another browser when a url
        // redirection occurs---
        webview_pdf.setWebViewClient(new Callback());
        if (fileUrl.contains("file:///")) {
            webview_pdf.loadUrl(fileUrl);
        } else if (fileUrl.contains(".pdf")) {
            webview_pdf.loadUrl(
                    "http://docs.google.com/gview?embedded=true&url=" + fileUrl);
        } else {
            webview_pdf.loadUrl(fileUrl);
        }
//            webview_pdf.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + url);//"http://docs.google.com/gview?embedded=true&url=" +
    }

    private class Callback extends WebViewClient {
        String urls = "javascript:(function() {"+"document.querySelector('[role=\"toolbar\"]').remove();})()";

        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webview_pdf.loadUrl(urls);
        }
    }
}
