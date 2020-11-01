package com.zanhd.dailyhelper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.zanhd.dailyhelper.data.DatabaseHandlerCustomer;
import com.zanhd.dailyhelper.data.DatabaseHandlerDailyWorker;
import com.zanhd.dailyhelper.model.Customer;
import com.zanhd.dailyhelper.model.DailyWorker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText nameText;
    private EditText phoneNumber;
    private Spinner signupSpinner;
    private EditText emailAddress;
    private EditText password;
    private Button signup_button;
    private TextView alreadyHaveAccount;
    private String selectedSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameText = findViewById(R.id.signup_name_edittext);
        phoneNumber = findViewById(R.id.signup_phone_number_edittext);

        signupSpinner = findViewById(R.id.signup_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.login_spinner_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupSpinner.setAdapter(adapter);
        signupSpinner.setOnItemSelectedListener(this);

        emailAddress = findViewById(R.id.signup_email_edittext);
        password = findViewById(R.id.signup_password_edittext);
        signup_button = findViewById(R.id.signup_signup_button);
        alreadyHaveAccount = findViewById(R.id.already_have_account_text);

        signup_button.setOnClickListener(this);
        alreadyHaveAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.signup_signup_button :
                if(selectedSpin.equals("DailyWorker")) {
                    //save all data into database of DailyWorker
                    saveDailyWorkerInDatabase(view);
                } else {
                    //save all data into database of customer
                    saveCustomerInDatabase(view);
                }
                break;
            case R.id.already_have_account_text :
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }

    private void saveDailyWorkerInDatabase(View view) {

        DatabaseHandlerDailyWorker dbDailyWorker  = new DatabaseHandlerDailyWorker(this);
        DailyWorker dailyWorker = new DailyWorker();
        dailyWorker.setPhoneNumber(phoneNumber.getText().toString().trim());
        dailyWorker.setName(nameText.getText().toString().trim());
        dailyWorker.setEmailAddress(emailAddress.getText().toString().trim());
        dailyWorker.setPassword(password.getText().toString().trim());

        if(!dailyWorker.getName().isEmpty() && !dailyWorker.getPhoneNumber().isEmpty() && !dailyWorker.getPassword().isEmpty()) {
            dbDailyWorker.addDailyWorker(dailyWorker);
            Snackbar.make(view, "Signed up successfully", Snackbar.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                    finish();
                }
            },500);

        } else {
            String  empty;
            if(dailyWorker.getName().isEmpty()) empty = "Name";
            else if(dailyWorker.getPhoneNumber().isEmpty()) empty = "Phone Number";
            else empty = "Password";
            Snackbar.make(view,empty + " can't be empty",Snackbar.LENGTH_SHORT).show();
        }
    }

    private void saveCustomerInDatabase(View view) {
        DatabaseHandlerCustomer dbCustomer  = new DatabaseHandlerCustomer(this);
        Customer customer = new Customer();
        customer.setPhoneNumber(phoneNumber.getText().toString().trim());
        customer.setName(nameText.getText().toString().trim());
        customer.setEmailAddress(emailAddress.getText().toString().trim());
        customer.setPassword(password.getText().toString().trim());

        if(!customer.getName().isEmpty() && !customer.getPhoneNumber().isEmpty() && !customer.getPassword().isEmpty()) {
            dbCustomer.addCustomer(customer);
            Snackbar.make(view, "Signed up successfully", Snackbar.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                    finish();
                }
            },500);

        } else {
            String  empty;
            if(customer.getName().isEmpty()) empty = "Name";
            else if(customer.getPhoneNumber().isEmpty()) empty = "Phone Number";
            else empty = "Password";
            Snackbar.make(view,empty + " can't be empty",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedSpin = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}