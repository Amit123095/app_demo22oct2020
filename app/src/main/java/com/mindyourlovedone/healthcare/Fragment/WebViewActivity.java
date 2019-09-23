package com.mindyourlovedone.healthcare.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;

public class WebViewActivity extends AppCompatActivity {
    Context context=this;
    WebView webView;
    TextView txtTitle, txtName;
    ImageView imgDrawer, imgBack;
    String name;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initUI();
        //initListener();
    }

    private void initUI() {
        imgBack = findViewById(R.id.imgBack);
        txtTitle=findViewById(R.id.txtTitle);
        progressBar=findViewById(R.id.progressBar);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView =findViewById(R.id.webView);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent i=getIntent();
        name=i.getExtras().getString("Name");
        txtTitle.setText(name);

        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (name.equalsIgnoreCase("Support FAQs"))
        {
            webView.loadUrl("http://mindyour-lovedones.com/MYLO/faq.html");
        }else if (name.equalsIgnoreCase("User Guide"))
        {
            String pdf = "http://mindyour-lovedones.com/MYLO/uploads/User_Guide.pdf";
            webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
            // webView.loadUrl("http://mindyour-lovedones.com/MYLO/uploads/User_Guide.pdf");
        }
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });
    }
}
