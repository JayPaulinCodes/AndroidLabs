package com.cst2335.paul0319;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    private final static String ACTIVITY_NAME = "PROFILE ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        log("In onCreate, creating the objects");
        setContentView(R.layout.activity_profile);

        log("In onCreate, getting passed intent values");
        Intent fromMain = getIntent();

        log("In onCreate, setting element variables");
        ImageButton image_button = findViewById(R.id.input_5_picture);
        EditText emailInput = findViewById(R.id.input_4_email);
        Button chatRoomButton = findViewById(R.id.button_chat);
        Button toolbarPageButton = findViewById(R.id.button_to_toolbar);

        log("In onCreate, setting email text based off of the intent passed values");
        emailInput.setText(fromMain.getStringExtra(MainActivity.INTENT_EMAIL));

        ActivityResultLauncher<Intent> myPictureTakerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                        image_button.setImageBitmap(imgbitmap); // the imageButton
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED)
                        log("User refused to capture a picture.");
                }
            } );


        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    myPictureTakerLauncher.launch(takePictureIntent);
                }
            }
        });

        chatRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToChatRoom = new Intent(ProfileActivity.this, ChatRoomActivity.class);

                startActivity(goToChatRoom);
            }
        });

        toolbarPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToToolbarPage = new Intent(ProfileActivity.this, TestToolbar.class);

                startActivity(goToToolbarPage);
            }
        });

    }

    @Override //screen is visible but not responding
    protected void onStart() {
        super.onStart();
        log("In onStart, visible but not responding");
    }

    @Override //screen is visible but not responding
    protected void onResume() {
        super.onResume();
        log("In onResume");
    }

    @Override //screen is visible but not responding
    protected void onPause() {
        super.onPause();
        log("In onPause");
    }

    @Override //not visible
    protected void onStop() {
        super.onStop();
        log("In onStop");
    }

    @Override  //garbage collected
    protected void onDestroy() {
        super.onDestroy();
        log("In onDestroy");
    }

    private void log(String message) { Log.e(ACTIVITY_NAME, message); }
}