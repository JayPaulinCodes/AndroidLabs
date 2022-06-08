package com.cst2335.paul0319;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreference = getSharedPreferences(getString(R.string.sharedPreference), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreference_Editor = sharedPreference.edit();

        // Put Email
        sharedPreference_Editor.putString(getString(R.string.sharedPreference_Email), email);

        // Put Password
        sharedPreference_Editor.putString(getString(R.string.sharedPreference_Password), password);

        // Save Changes
        sharedPreference_Editor.apply();
    }
}