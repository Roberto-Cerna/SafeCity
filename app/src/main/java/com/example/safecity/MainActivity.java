package com.example.safecity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safecity.connection.MainRetrofit;
import com.example.safecity.connection.user.DefaultResult;
import com.example.safecity.connection.user.InfoResult;
import com.example.safecity.ui.SafePreferences;
import com.example.safecity.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safecity.databinding.ActivityMainBinding;

import com.example.safecity.data.user.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public TextView headerNameTextView;
    public TextView headerEmailTextView;
    private SafePreferences preferences;
    private Button logoutBotton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new SafePreferences(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_emergency_contacts,
                R.id.nav_recent_reports, R.id.nav_report)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        headerNameTextView = navigationView.getHeaderView(0).findViewById(R.id.headerNameTextView);
        headerEmailTextView = navigationView.getHeaderView(0).findViewById(R.id.headerEmailTextView);

        Call<InfoResult> call = MainRetrofit.userAPI.getInfo(User.id);
        call.enqueue(new Callback<InfoResult>() {
            @Override
            public void onResponse(Call<InfoResult> call, Response<InfoResult> response) {
                if(response.code() == 200) {
                    InfoResult infoResult = response.body();
                    headerNameTextView.setText(infoResult.getName());
                    headerEmailTextView.setText(infoResult.getEmail());
                    preferences = new SafePreferences(getApplicationContext());
                    preferences.SetName(infoResult.getName());
                    preferences.setEmail(infoResult.getEmail());
                    preferences.setPhone(infoResult.getPhone());
                    User.name = infoResult.getName();
                    User.email = infoResult.getEmail();
                    User.phone = infoResult.getPhone();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Ocurrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InfoResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ocurrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
            }
        });

        logoutBotton = navigationView.findViewById(R.id.logoutButton);
        logoutBotton.setOnClickListener(v -> onLogout(preferences));
    }

    private void onLogout(SafePreferences preferences) {
        preferences.setLoggedIn(false);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}