package com.zanhd.dailyhelper.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.zanhd.dailyhelper.model.Customer;
import com.zanhd.dailyhelper.util.CustomerConstants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerCustomer extends SQLiteOpenHelper {
    Context context;
    public DatabaseHandlerCustomer(@Nullable Context context) {
        super(context, CustomerConstants.DB_NAME, null, CustomerConstants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + CustomerConstants.TABLE_NAME + "("
                + CustomerConstants.KEY_ID + " INTEGER PRIMARY KEY,"
                + CustomerConstants.KEY_PHONE_NUMBER + " TEXT,"
                + CustomerConstants.KEY_NAME + " TEXT,"
                + CustomerConstants.KEY_EMAIL + " TEXT,"
                + CustomerConstants.KEY_PASSWORD + " TEXT);";
        db.execSQL(CREATE_CUSTOMER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CustomerConstants.TABLE_NAME);
        onCreate(db);
    }

    //CRUD operations
    //Adding a Customer
    public void addCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CustomerConstants.KEY_PHONE_NUMBER,customer.getPhoneNumber());
        values.put(CustomerConstants.KEY_NAME,customer.getName());
        values.put(CustomerConstants.KEY_EMAIL,customer.getEmailAddress());
        values.put(CustomerConstants.KEY_PASSWORD,customer.getPassword());

        db.insert(CustomerConstants.TABLE_NAME,null,values);
    }

    //fetching a dailyWorker Record/object by its id
    public Customer getCustomer(int id) {
        Customer customer = new Customer();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CustomerConstants.TABLE_NAME,
                new String[]{CustomerConstants.KEY_ID, CustomerConstants.KEY_PHONE_NUMBER,
                        CustomerConstants.KEY_NAME, CustomerConstants.KEY_EMAIL,
                        CustomerConstants.KEY_PASSWORD},
                CustomerConstants.KEY_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor != null) {
            cursor.moveToFirst();

            customer.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CustomerConstants.KEY_ID))));
            customer.setPhoneNumber(cursor.getString(cursor.getColumnIndex(CustomerConstants.KEY_PHONE_NUMBER)));
            customer.setName(cursor.getString(cursor.getColumnIndex(CustomerConstants.KEY_NAME)));
            customer.setEmailAddress(cursor.getString(cursor.getColumnIndex(CustomerConstants.KEY_EMAIL)));
            customer.setPassword(cursor.getString(cursor.getColumnIndex(CustomerConstants.KEY_PASSWORD)));

        }

        return  customer;
    }

    //fetching all Customers records/objects  from db
    public List<Customer> getAllCustomers(){

        List<Customer> customerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CustomerConstants.TABLE_NAME,
                new String[]{CustomerConstants.KEY_ID, CustomerConstants.KEY_PHONE_NUMBER,
                        CustomerConstants.KEY_NAME, CustomerConstants.KEY_EMAIL,
                        CustomerConstants.KEY_PASSWORD},
                null,
                null,null,null,null);


        if(cursor.moveToFirst()) {
            do{
                Customer customer = new Customer();

                customer.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CustomerConstants.KEY_ID))));
                customer.setPhoneNumber(cursor.getString(cursor.getColumnIndex(CustomerConstants.KEY_PHONE_NUMBER)));
                customer.setName(cursor.getString(cursor.getColumnIndex(CustomerConstants.KEY_NAME)));
                customer.setEmailAddress(cursor.getString(cursor.getColumnIndex(CustomerConstants.KEY_EMAIL)));
                customer.setPassword(cursor.getString(cursor.getColumnIndex(CustomerConstants.KEY_PASSWORD)));

                customerList.add(customer);
            }while(cursor.moveToNext());
        }

        return customerList;
    }

    //updating Customer
    public int updateNote(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CustomerConstants.KEY_PHONE_NUMBER,customer.getPhoneNumber());
        values.put(CustomerConstants.KEY_NAME,customer.getName());
        values.put(CustomerConstants.KEY_EMAIL,customer.getEmailAddress());
        values.put(CustomerConstants.KEY_PASSWORD,customer.getPassword());

        //update the note in database
        return db.update(CustomerConstants.TABLE_NAME,values,CustomerConstants.KEY_ID + "=?",new String[]{String.valueOf(customer.getId())});
    }

    //delete Note
    public void deleteCustomer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CustomerConstants.TABLE_NAME,CustomerConstants.KEY_ID + "=?",new String[]{String.valueOf(id)});
        db.close();
    }

    //getNotesCount
    public int getCustomerCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + CustomerConstants.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery,null);
        return cursor.getCount();
    }

    //seaching in database(self)
    public int getCustomerId(Customer customer) {

        List<Customer> customerList = getAllCustomers();
        for (Customer cust : customerList) {
            if(cust.getPhoneNumber().equals(customer.getPhoneNumber()))
                return cust.getId();
        }
        return -1;
    }
}
