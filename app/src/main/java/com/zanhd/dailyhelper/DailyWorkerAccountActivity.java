package com.zanhd.dailyhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.zanhd.dailyhelper.data.DatabaseHandlerCustomer;
import com.zanhd.dailyhelper.data.DatabaseHandlerDailyWorker;
import com.zanhd.dailyhelper.model.Customer;
import com.zanhd.dailyhelper.model.DailyWorker;

public class DailyWorkerAccountActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_worker_account);

        toolbar = findViewById(R.id.dalyWorker_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.dailyWorker_drawer);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.dailyWorker_navmenu);
        View headerLayout = navigationView.getHeaderView(0);
        TextView nameText = headerLayout.findViewById(R.id.dailyWoker_name);
        TextView phoneText = headerLayout.findViewById(R.id.dailyWorker_phonenumber);
        TextView emailText = headerLayout.findViewById(R.id.dailyWoker_email);
        Bundle login_id = getIntent().getExtras();
        if(login_id!=null) {
            int id = login_id.getInt("ID");

            DatabaseHandlerDailyWorker dbDailyWoker = new DatabaseHandlerDailyWorker(this);
            DailyWorker dailyWorker = dbDailyWoker.getDailyWorker(id);

            nameText.setText(dailyWorker.getName());
            phoneText.setText(dailyWorker.getPhoneNumber());
            emailText.setText(dailyWorker.getEmailAddress());

        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home :
                        Toast.makeText(getApplicationContext(),"Home Panel is Open",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_setting :
                        Toast.makeText(getApplicationContext(),"Setting Panel is Open",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

    }
}