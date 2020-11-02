package com.zanhd.dailyhelper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.zanhd.dailyhelper.data.DatabaseHandlerCustomer;
import com.zanhd.dailyhelper.data.DatabaseHandlerDailyWorker;
import com.zanhd.dailyhelper.model.Customer;
import com.zanhd.dailyhelper.model.DailyWorker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText phoneNumber;
    private EditText password;
    private Button loginButton;
    private Button signupButton;
    private Spinner loginSpinner;
    private String  selectedSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumber = findViewById(R.id.phone_number_edittext);
        password = findViewById(R.id.password_edittext);
        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);

        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);

        loginSpinner = findViewById(R.id.login_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.login_spinner_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loginSpinner.setAdapter(adapter);
        loginSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.login_button :

                //read phoneNumber and password
                String inputPhoneNumber = phoneNumber.getText().toString().trim();
                String inputPassword = password.getText().toString().trim();

                if(!inputPhoneNumber.isEmpty() && !inputPassword.isEmpty()) {

                    if(selectedSpin.equals("DailyWorker")) {
                        //check details in database of daily workers and then login successfully
                        DailyWorker dailyWorker = new DailyWorker();
                        dailyWorker.setPhoneNumber(inputPhoneNumber);
                        dailyWorker.setPassword(inputPassword);

                        DatabaseHandlerDailyWorker dbDailyWorker = new DatabaseHandlerDailyWorker(this);
                        int id = dbDailyWorker.getDailyWorkerId(dailyWorker);

                        if(id == -1) {
                            //record don't exist
                            Snackbar.make(view,"Record Doesn't exists",Snackbar.LENGTH_SHORT).show();
                        } else {
                            //check its password after retriving the data
                            DailyWorker dailyWorker1 = dbDailyWorker.getDailyWorker(id);
                            if(dailyWorker.getPassword().equals(dailyWorker1.getPassword())) {
                                //now open the DailyWorker Account
                                Intent intent = new Intent(LoginActivity.this,DailyWorkerAccountActivity.class);
                                intent.putExtra("ID",id);
                                startActivity(intent);
                                //finish();
                            } else {
                                Snackbar.make(view,"Incorrect Password",Snackbar.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        //check details in database of customers and then login successfully
                        Customer customer = new Customer();
                        customer.setPhoneNumber(inputPhoneNumber);
                        customer.setPassword(inputPassword);

                        DatabaseHandlerCustomer dbCustomer = new DatabaseHandlerCustomer(this);
                        int id = dbCustomer.getCustomerId(customer);

                        if(id == -1) {
                            //record don't exist
                            Snackbar.make(view,"Record Doesn't exists",Snackbar.LENGTH_SHORT).show();
                        } else {
                            Customer customer1 = dbCustomer.getCustomer(id);
                            if(customer1.getPassword().equals(customer.getPassword())) {
                                //now open the Customer Account
                                Intent intent = new Intent(LoginActivity.this,CustomerAccountActivity.class);
                                intent.putExtra("ID",id);
                                startActivity(intent);
                                //finish();
                            } else {
                                Snackbar.make(view,"Incorrect Password",Snackbar.LENGTH_SHORT).show();
                            }
                        }

                    }

                } else {
                    String empty;
                    if(inputPhoneNumber.isEmpty()) empty = "Phone Number";
                    else empty = "Password";
                    Snackbar.make(view,"Enter " + empty,Snackbar.LENGTH_SHORT).show();
                }

                break;
            case R.id.signup_button :
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedSpin = (String) parent.getItemAtPosition(position);
        Toast.makeText(this,selectedSpin,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}