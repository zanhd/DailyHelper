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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.zanhd.dailyhelper.data.DatabaseHandlerCustomer;
import com.zanhd.dailyhelper.model.Customer;

public class CustomerAccountActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_account);

        toolbar = findViewById(R.id.customer_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.customer_drawer);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.customer_navmenu);
        View headerLayout = navigationView.getHeaderView(0);
        TextView nameText = headerLayout.findViewById(R.id.customer_name);
        TextView phoneText = headerLayout.findViewById(R.id.customer_phonenumber);
        TextView emailText = headerLayout.findViewById(R.id.customer_email);
        Bundle login_id = getIntent().getExtras();
        if(login_id!=null) {
            int id = login_id.getInt("ID");

            DatabaseHandlerCustomer dbCustomer = new DatabaseHandlerCustomer(this);
            Customer customer = dbCustomer.getCustomer(id);

            nameText.setText(customer.getName());
            phoneText.setText(customer.getPhoneNumber());
            emailText.setText(customer.getEmailAddress());

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