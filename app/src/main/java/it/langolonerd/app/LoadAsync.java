package it.langolonerd.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by aronne on 16/01/17.
 */

public class LoadAsync extends AsyncTask<Object, Void, String> {
    private MainActivity mainActivity;
    private WebView webView;
    private Intent newIntent;
    private String post;
    private String action;
    private String data;
    private String token;

    @Override
    protected String doInBackground(Object[] params) {
        webView = (WebView) params[0];
        newIntent = (Intent) params[1];
        mainActivity = (MainActivity) params[2];
        action = newIntent.getAction();
        data = newIntent.getDataString();
        // Send the Firebase token to the server
        // Used for push notification service
        token = FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN 1:", "> " + token);
        while(token==null) {
            token = FirebaseInstanceId.getInstance().getToken();
            Log.d("TOKEN LOOP:", "> " + token);
        }
        return token;
    }

    @Override
    protected void onPostExecute(String unused) {
        String app_version = mainActivity.getString(R.string.app_version);
        Log.d("VER:", "> " + app_version);
        post = "";
        try {
            post = "token=" + URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        post = "ver=" + app_version + "&" + post;

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String page = data.substring(data.lastIndexOf("/") + 1);
            if(!page.contains("?"))
                post = "?" + post;
            else
                post = "&" + post;
            webView.loadUrl("http://www.langolonerd.it/" + page + post);
        } else {
            webView.loadUrl("http://www.langolonerd.it/?" + post);
        }
    }
}
