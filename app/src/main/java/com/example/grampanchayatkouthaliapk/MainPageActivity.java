package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class MainPageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView iconAccountCircle;
    private ImageView homeProfileImage;
    private TextView navUsername;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);

        iconAccountCircle = headerView.findViewById(R.id.icon_account_circle);
        navUsername = headerView.findViewById(R.id.nav_username);
        homeProfileImage = findViewById(R.id.home_profile_image);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        homeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Toast.makeText(MainPageActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_profile) {
                    Toast.makeText(MainPageActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_settings) {
                    Toast.makeText(MainPageActivity.this, "Settings Selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_logout) {
                    signOut();
                    Toast.makeText(MainPageActivity.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                } else {
                    return false;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            String personName = account.getDisplayName();
            String personPhotoUrl = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : null;

            navUsername.setText(personName);

            if (personPhotoUrl != null) {
                Picasso.get().load(personPhotoUrl).transform(new CircleTransform()).into(iconAccountCircle);
                Picasso.get().load(personPhotoUrl).transform(new CircleTransform()).into(homeProfileImage);
            } else {
                iconAccountCircle.setImageResource(R.drawable.accountinfo);
                homeProfileImage.setImageResource(R.drawable.accountinfo);
            }
        } else {
            navUsername.setText("User");
            iconAccountCircle.setImageResource(R.drawable.accountinfo);
            homeProfileImage.setImageResource(R.drawable.accountinfo);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                updateUI(null);
            }
        });
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
