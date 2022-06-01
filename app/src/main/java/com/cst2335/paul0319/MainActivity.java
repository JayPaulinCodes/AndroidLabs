package com.cst2335.paul0319;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_constraint);

        Toast toast_message = Toast.makeText(this, R.string.ToastText, Toast.LENGTH_LONG);

        Button button = findViewById(R.id.button1);
        Switch switchThing = findViewById(R.id.switch1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast_message.show();
            }
        });

        switchThing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String msg = getString(R.string.snackbar_message);

                if (isChecked) { msg += getString(R.string.on); }
                else { msg += getString(R.string.off); }

                Snackbar snackbar = Snackbar.make(buttonView,  msg, Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Undo", click -> buttonView.setChecked(!isChecked));
            }
        });
    }
}