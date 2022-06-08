package com.cst2335.paul0319;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public final static String INTENT_EMAIL = "Intent_Email";

    private final static String ACTIVITY_NAME = "MAIN ACTIVITY";
    private final static String PREFERENCES_FILE = "MyData";
    private final static String PREFERENCE_RESERVED_EMAIL = "Reserve_Email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        log("In onCreate, creating the objects");
        setContentView(R.layout.activity_main);

        log("In onCreate, setting element variables");
        EditText emailInput = findViewById(R.id.input_email);
        Button loginButton = findViewById(R.id.login_button);

        log("In onCreate, getting shared preferences");
        SharedPreferences sharedPreference = getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreference_Editor = sharedPreference.edit();

        log("In onCreate, getting shared preferences values");
        String saved_email = sharedPreference.getString(PREFERENCE_RESERVED_EMAIL, "");

        log("In onCreate, setting inputs from saved values");
        emailInput.setText(saved_email);

        log("In onCreate, setting button events");
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                savePreferences();

                Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
                goToProfile.putExtra(INTENT_EMAIL, emailInput.getText().toString());
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        log("In onPause, beginning");

        log("In onPause, saving preferences");
        savePreferences();
    }

    private void log(String message) { Log.i(ACTIVITY_NAME, message); }

    private void savePreferences() {
        log("In Method savePreferences, setting input variables");
        EditText emailInput = findViewById(R.id.input_email);

        log("In Method savePreferences, getting shared preferences");
        SharedPreferences sharedPreference = getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreference_Editor = sharedPreference.edit();

        // Put Email
        log("In Method savePreferences, storing values");
        sharedPreference_Editor.putString(PREFERENCE_RESERVED_EMAIL, emailInput.getText().toString());

        // Save Changes
        log("In Method savePreferences, applying changes");
        sharedPreference_Editor.apply();
    }
}