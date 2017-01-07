package it.langolonerd.app;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;

/**
 * Created by aronne on 07/01/17.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
