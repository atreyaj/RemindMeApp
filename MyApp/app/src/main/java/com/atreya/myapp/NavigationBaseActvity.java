package com.atreya.myapp;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import static com.atreya.myapp.SharedPref.REMIND_ME_SHARED_PREF;
import static com.atreya.myapp.SharedPref.sharedpreferences;

public class NavigationBaseActvity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected LinearLayout fullLayout;
    protected CoordinatorLayout actContent;
    @Override
    public void setContentView(final int layoutResID) {
        // Your base layout here
        fullLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_navigation_base_actvity, null);
        actContent = (CoordinatorLayout) fullLayout.findViewById(R.id.drawCordinate);

        // Setting the content of layout your provided to the act_content frame
        getLayoutInflater().inflate(layoutResID, actContent, true);
        super.setContentView(fullLayout);    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
//        Intent intent=new Intent(Nav_BaseActivity.this,FarmerHome.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
//        finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent mainIntent = null;
        if (id == R.id.home) {
//            mainIntent = new Intent(Nav_BaseActivity.this,FarmerHome.class);
//        }if (id == R.id.my_acc) {
//            mainIntent = new Intent(Nav_BaseActivity.this,ProfileActivity.class);
//
//            // Handle the camera action
//        } else if (id == R.id.my_produce) {
//            mainIntent = new Intent(Nav_BaseActivity.this,ProduceOnSale.class);
//        }
//        else if (id == R.id.sale_history) {
//            mainIntent = new Intent(Nav_BaseActivity.this,Sale_History.class);
//        }
//        else if (id == R.id.about_us) {
//            mainIntent = new Intent(Nav_BaseActivity.this,About_Us.class);
//        } else if (id == R.id.help) {
//            mainIntent = new Intent(Nav_BaseActivity.this,Help.class);
//        } else if (id == R.id.legal) {
//            mainIntent = new Intent(Nav_BaseActivity.this,Legal_Activity.class);
        } else if (id == R.id.logout) {
            sharedpreferences = getSharedPreferences(REMIND_ME_SHARED_PREF, 0);
            sharedpreferences.edit().clear().apply();
            sharedpreferences=null;

//            this.finishAffinity();
            mainIntent = new Intent(NavigationBaseActvity.this,LoginActivity.class);
            NavigationBaseActvity.this.startActivity(mainIntent);
            finish();
//
        }
//        Nav_BaseActivity.this.startActivity(mainIntent);
//        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
//        finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
