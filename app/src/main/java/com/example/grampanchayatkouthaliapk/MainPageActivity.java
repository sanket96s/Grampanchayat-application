package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MainPageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView iconAccountCircle;
    private ImageView homeProfileImage;
    private TextView navUsername;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);

        iconAccountCircle = headerView.findViewById(R.id.home_profile_image);
        navUsername = headerView.findViewById(R.id.nav_username);
        homeProfileImage = findViewById(R.id.home_profile_image);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        homeProfileImage.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                Intent intent = new Intent(MainPageActivity.this, ProfileActivity.class);
                startActivity(intent);
            } else if (id == R.id.help_and_support) {
                Intent intent = new Intent(MainPageActivity.this, HelpAndSupportActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_logout) {
                signOut();
            } else {
                return false;
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);

        TextView textvillageinfo = findViewById(R.id.text_village_information);
        textvillageinfo.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, VillageActivity.class);
            startActivity(intent);
        });


        TextView textPayTax = findViewById(R.id.text_pay_tax);
        textPayTax.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, TaxPayActivity.class);
            startActivity(intent);
        });


        TextView textcertificate = findViewById(R.id.text_apply_certificate);
        textcertificate.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, ApplyForCertificateActivity.class);
            startActivity(intent);
        });

        TextView textProjects = findViewById(R.id.text_projects);
        textProjects.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, ProjectsActivity.class);
            startActivity(intent);
        });


        TextView textGramsabha = findViewById(R.id.text_events);

        textGramsabha.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, GramsabhaActivity.class);
            startActivity(intent);
        });

        TextView txtComplaint = findViewById(R.id.text_problem_report);

        txtComplaint.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, ComplaintActivity.class);
            startActivity(intent);
        });

        TextView textGovernmentSchemes = findViewById(R.id.text_government_schemes);

        textGovernmentSchemes.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, GovernmentSchemesActivity.class);
            startActivity(intent);
        });
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
            navUsername.setText(getString(R.string.user_text));
            iconAccountCircle.setImageResource(R.drawable.accountinfo);
            homeProfileImage.setImageResource(R.drawable.accountinfo);
        }
    }

    private void signOut() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mAuth.signOut();
        }

        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            updateUI(null);
            Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed () {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
