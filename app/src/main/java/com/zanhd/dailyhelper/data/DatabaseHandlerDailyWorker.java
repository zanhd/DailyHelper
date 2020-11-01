package com.zanhd.dailyhelper.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.zanhd.dailyhelper.model.DailyWorker;
import com.zanhd.dailyhelper.util.DailyWorkerConstants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerDailyWorker extends SQLiteOpenHelper {

    Context context;
    public DatabaseHandlerDailyWorker(@Nullable Context context) {
        super(context, DailyWorkerConstants.DB_NAME, null, DailyWorkerConstants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DAILYWORKER_TABLE = "CREATE TABLE " + DailyWorkerConstants.TABLE_NAME + "("
                + DailyWorkerConstants.KEY_ID + " INTEGER PRIMARY KEY,"
                + DailyWorkerConstants.KEY_PHONE_NUMBER + " TEXT,"
                + DailyWorkerConstants.KEY_NAME + " TEXT,"
                + DailyWorkerConstants.KEY_EMAIL + " TEXT,"
                + DailyWorkerConstants.KEY_PASSWORD + " TEXT,"
                + DailyWorkerConstants.KEY_COST_PER_DAY + " TEXT);";
        db.execSQL(CREATE_DAILYWORKER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DailyWorkerConstants.TABLE_NAME);
        onCreate(db);
    }

    //CRUD operations
    //Adding a DailyWorker
    public void addDailyWorker(DailyWorker dailyWorker) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DailyWorkerConstants.KEY_PHONE_NUMBER,dailyWorker.getPhoneNumber());
        values.put(DailyWorkerConstants.KEY_NAME,dailyWorker.getName());
        values.put(DailyWorkerConstants.KEY_EMAIL,dailyWorker.getEmailAddress());
        values.put(DailyWorkerConstants.KEY_PASSWORD,dailyWorker.getPassword());

        db.insert(DailyWorkerConstants.TABLE_NAME,null,values);

    }

    //fetching a dailyWorker Record/object by its id
    public DailyWorker getDailyWorker(int id) {
        DailyWorker dailyWorker = new DailyWorker();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DailyWorkerConstants.TABLE_NAME,
                new String[]{DailyWorkerConstants.KEY_ID, DailyWorkerConstants.KEY_PHONE_NUMBER,
                        DailyWorkerConstants.KEY_NAME, DailyWorkerConstants.KEY_EMAIL,
                            DailyWorkerConstants.KEY_PASSWORD},
                DailyWorkerConstants.KEY_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor != null) {
            cursor.moveToFirst();

            dailyWorker.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DailyWorkerConstants.KEY_ID))));
            dailyWorker.setPhoneNumber(cursor.getString(cursor.getColumnIndex(DailyWorkerConstants.KEY_PHONE_NUMBER)));
            dailyWorker.setName(cursor.getString(cursor.getColumnIndex(DailyWorkerConstants.KEY_NAME)));
            dailyWorker.setEmailAddress(cursor.getString(cursor.getColumnIndex(DailyWorkerConstants.KEY_EMAIL)));
            dailyWorker.setPassword(cursor.getString(cursor.getColumnIndex(DailyWorkerConstants.KEY_PASSWORD)));

        }

        return  dailyWorker;
    }

    //fetching all DailyWorker records/objects  from db
    public List<DailyWorker> getAllDailyWorkers() {
        List<DailyWorker> dailyWorkersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DailyWorkerConstants.TABLE_NAME,
                new String[]{DailyWorkerConstants.KEY_ID, DailyWorkerConstants.KEY_PHONE_NUMBER,
                        DailyWorkerConstants.KEY_NAME, DailyWorkerConstants.KEY_EMAIL,
                        DailyWorkerConstants.KEY_PASSWORD},
                null,
                null,null,null,null);


        if(cursor.moveToFirst()) {
            do{
                DailyWorker dailyWorker = new DailyWorker();

                dailyWorker.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DailyWorkerConstants.KEY_ID))));
                dailyWorker.setPhoneNumber(cursor.getString(cursor.getColumnIndex(DailyWorkerConstants.KEY_PHONE_NUMBER)));
                dailyWorker.setName(cursor.getString(cursor.getColumnIndex(DailyWorkerConstants.KEY_NAME)));
                dailyWorker.setEmailAddress(cursor.getString(cursor.getColumnIndex(DailyWorkerConstants.KEY_EMAIL)));
                dailyWorker.setPassword(cursor.getString(cursor.getColumnIndex(DailyWorkerConstants.KEY_PASSWORD)));

                dailyWorkersList.add(dailyWorker);
            }while(cursor.moveToNext());
        }

        return dailyWorkersList;
    }

    //updating DailyWorker
    public int updateNote(DailyWorker dailyWorker) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DailyWorkerConstants.KEY_PHONE_NUMBER,dailyWorker.getPhoneNumber());
        values.put(DailyWorkerConstants.KEY_NAME,dailyWorker.getName());
        values.put(DailyWorkerConstants.KEY_EMAIL,dailyWorker.getEmailAddress());
        values.put(DailyWorkerConstants.KEY_PASSWORD,dailyWorker.getPassword());

        //update the note in database
        return db.update(DailyWorkerConstants.TABLE_NAME,values,DailyWorkerConstants.KEY_ID + "=?",new String[]{String.valueOf(dailyWorker.getId())});
    }

    //delete Note
    public void deleteDailyWorker(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DailyWorkerConstants.TABLE_NAME,DailyWorkerConstants.KEY_ID + "=?",new String[]{String.valueOf(id)});
        db.close();
    }

    //getNotesCount
    public int getDailyWorkerCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + DailyWorkerConstants.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery,null);
        return cursor.getCount();
    }

    //seaching in database(self)
    public int getDailyWorkerId(DailyWorker dailyWorker) {

        List<DailyWorker> dailyWorkerList = getAllDailyWorkers();
        for (DailyWorker dw : dailyWorkerList) {
            if(dw.getPhoneNumber().equals(dailyWorker.getPhoneNumber()))
                return dw.getId();
        }
        return -1;
    }
}
