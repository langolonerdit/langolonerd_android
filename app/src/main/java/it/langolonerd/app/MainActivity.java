/*
 * L'angolo nerd
 * Copyright (C) 2017 Aronne Brivio
 *
 * This file is part of L'angolo nerd Android application.
 *
 * L'angolo nerd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * L'angolo nerd is distributed in the hope that it will be useful,
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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends Activity {
    private WebView webView;
    private CardView cardView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        webView.getSettings().setUserAgentString("WEBAPP");
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
                // The first http call is a POST request that send push tokens to the server
                // Splash screen has to load until the 2nd http call (loadUrl) finishes
                if(!url.equalsIgnoreCase("http://www.langolonerd.it/pushreg.php"))
                    visible();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("http://www.langolonerd.it") || url.startsWith("www.langolonerd.it"))
                    view.loadUrl(url);
                else
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        });

        onNewIntent(getIntent());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private String sendToken() throws UnsupportedEncodingException {
        // Send the Firebase token to the server
        // Used for push notification service
        String token = FirebaseInstanceId.getInstance().getToken();
        String post = "token=" + URLEncoder.encode(token, "UTF-8");
        //webView.postUrl("http://www.langolonerd.it/pushreg.php", post.getBytes());
        return post;
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
        String params = "";
        try {
            params = sendToken();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String page = data.substring(data.lastIndexOf("/") + 1);
            if(!page.contains("?"))
                params = "?" + params;
            else
                params = "&" + params;
            webView.loadUrl("http://www.langolonerd.it/" + page + params);
        } else {
            webView.loadUrl("http://www.langolonerd.it/?" + params);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("L'angolo nerd")
                .setUrl(Uri.parse("http://www.langolonerd.it"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
