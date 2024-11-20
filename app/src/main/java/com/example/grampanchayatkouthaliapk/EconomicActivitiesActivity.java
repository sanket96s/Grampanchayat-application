package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class EconomicActivitiesActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.economic_activities);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_village_info_overview) {
                Intent intent = new Intent(EconomicActivitiesActivity.this, VillageActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_public_facilities) {
                Intent intent = new Intent(EconomicActivitiesActivity.this, PublicFacilitiesActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_community_cultural_info) {
                Intent intent = new Intent(EconomicActivitiesActivity.this, CommunityCulturalInfoActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_economic_activities) {
                Intent intent = new Intent(EconomicActivitiesActivity.this, EconomicActivitiesActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_infrastructure) {
                Intent intent = new Intent(EconomicActivitiesActivity.this, InfrastructureActivity.class);
                startActivity(intent);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
