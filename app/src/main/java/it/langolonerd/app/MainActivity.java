/*
 * L'angolo nerd
 * Copyright (C) 2017 Aronne Brivio
 *
 * This file is part of L'angolo nerd Android application.
 *
 * OpenPGP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenPGP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with L'angolo nerd.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.langolonerd.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    private WebView webView;
    private CardView cardView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity);
        super.onCreate(savedInstanceState);

        // set statusbar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.rgb(0, 102, 92));

        // set webview params
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        // set cardview params (used as img shadow)
        cardView = (CardView) findViewById(R.id.imageShadow1);
        cardView.setRadius(25);
        cardView.setCardElevation(50);

        unvisible();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                visible();
            }
        });

        onNewIntent(getIntent());
    }

    private void visible() {
        WebView webview = (WebView) findViewById(R.id.webView1);
        ImageView logo = (ImageView) findViewById(R.id.imageView1);
        ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar1);
        TextView version = (TextView) findViewById(R.id.textView1);
        CardView shadow = (CardView) findViewById(R.id.imageShadow1);

        webview.setVisibility(View.VISIBLE);
        logo.setVisibility(View.GONE);
        shadow.setVisibility(View.GONE);
        bar.setVisibility(View.GONE);
        version.setVisibility(View.GONE);
    }

    private void unvisible() {
        WebView webview = (WebView) findViewById(R.id.webView1);
        ImageView logo = (ImageView) findViewById(R.id.imageView1);
        ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar1);
        TextView version = (TextView) findViewById(R.id.textView1);
        CardView shadow = (CardView) findViewById(R.id.imageShadow1);

        logo.setVisibility(View.VISIBLE);
        bar.setVisibility(View.VISIBLE);
        version.setVisibility(View.VISIBLE);
        shadow.setVisibility(View.VISIBLE);
        webview.setVisibility(View.GONE);
    }

    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String page = data.substring(data.lastIndexOf("/") + 1);
            webView.loadUrl("http://www.langolonerd.it/" + page);
        } else
            webView.loadUrl("http://www.langolonerd.it");
    }
}
