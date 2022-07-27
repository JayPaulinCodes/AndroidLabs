package com.cst2335.paul0319;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static String ACTIVITY_NAME = "TEST TOOLBAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        log("In onCreate (1)");
        setContentView(R.layout.activity_test_toolbar);

        log("In onCreate (2)");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        log("In onCreate (3)");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        log("In onCreate (4)");
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        log("In onNavigationItemSelected (1)");
        Integer option = null;

        log("In onNavigationItemSelected (2)");
        switch (item.getItemId()) {
            case R.id.drawerMenu_navigate_goToChat:
                log("In onNavigationItemSelected (2.1)");
                option = 1;
                break;
            case R.id.drawerMenu_navigate_goBackToProfile:
                log("In onNavigationItemSelected (2.2)");
                option = 2;
                break;
        }

        log("In onNavigationItemSelected (3)");
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        log("In onNavigationItemSelected (4)");
        if (option != null) {
            log("In onNavigationItemSelected (5)");
            if (option == 1) {
                log("In onNavigationItemSelected (6)");
                Intent goToChatRoom = new Intent(TestToolbar.this, ChatRoomActivity.class);

                log("In onNavigationItemSelected (6.1)");
                startActivity(goToChatRoom);
            } else if (option == 2) {
                log("In onNavigationItemSelected (7)");
                finish();
            }
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        log("In onCreateOptionsMenu (1)");
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        log("In onCreateOptionsMenu (2)");
        return super.onCreateOptionsMenu(menu);
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        log("In onOptionsItemSelected (1)");
        String message = null;

        switch (item.getItemId()) {
            case R.id.menuItem_choice1:
                message = "You choose choice number 1";
                break;

            case R.id.menuItem_choice2:
                message = "You choose choice number 2";
                break;

            case R.id.menuItem_choice3:
                message = "You choose choice number 3";
                break;

            case R.id.menuItem_choice4:
                message = "You clicked on the overflow menu";
                break;

        }

        if (message != null) Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        return true;
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