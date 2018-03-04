package com.example.quizapplication;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private CalculatorFragment mFragment01;
    private CanvasFragment mFragment02;
    private ClockFragment mFragment03;
    private ImageManipulationFragment mFragment04;
    private FragmentManager mFragmentMgr;
    private  int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id = item.getItemId();

        if (id == R.id.nav_calculator) {
            // Handle the camera action
            mFragment01 = new CalculatorFragment();
            mFragmentMgr = getFragmentManager();
            mFragmentMgr.beginTransaction()
                    .replace(R.id.frameLay, mFragment01, "TAG-mFragment01")
                    .commit();
        } else if (id == R.id.nav_canvas) {
            mFragment02 = new CanvasFragment();
            mFragmentMgr = getFragmentManager();
            mFragmentMgr.beginTransaction()
                    .replace(R.id.frameLay, mFragment02, "TAG-mFragment02")
                    .commit();

        } else if (id == R.id.nav_clock) {
            mFragment03 = new ClockFragment();
            mFragmentMgr = getFragmentManager();
            mFragmentMgr.beginTransaction()
                    .replace(R.id.frameLay, mFragment03, "TAG-mFragment03")
                    .commit();

        } else if (id == R.id.nav_image_manipulation) {
            mFragment04 = new ImageManipulationFragment();
            mFragmentMgr = getFragmentManager();
            mFragmentMgr.beginTransaction()
                    .replace(R.id.frameLay, mFragment04, "TAG-mFragment04")
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("FRAGMENT", id);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        id = savedInstanceState.getInt("FRAGMENT");
    }
}
