package com.example.educationloancalculator.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.educationloancalculator.HistoryRepository.HistoryRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "HistoryInfo.db";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + HistoryMaster.Calculations.TABLE_NAME +
                " (" + HistoryMaster.Calculations._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HistoryMaster.Calculations.COLUMN_NAME_LOAN_AMOUNT + " REAL, " +
                HistoryMaster.Calculations.COLUMN_NAME_INTEREST_RATE + " REAL, " +
                HistoryMaster.Calculations.COLUMN_NAME_RATE_PER + " TEXT, " +
                HistoryMaster.Calculations.COLUMN_NAME_LOAN_TERM + " INTEGER, " +
                HistoryMaster.Calculations.COLUMN_NAME_TERM_PERIOD + " TEXT, " +
                HistoryMaster.Calculations.COLUMN_NAME_SYSTEM_DATE + " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addHistory(HistoryRepository historyRepository){
        //Get the data repository in write mode
        SQLiteDatabase database = getWritableDatabase();

        //System date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        //create new map of values where columns name the key
        ContentValues contentValues = new ContentValues();
        contentValues.put(HistoryMaster.Calculations.COLUMN_NAME_LOAN_AMOUNT, historyRepository.getlAmount());
        contentValues.put(HistoryMaster.Calculations.COLUMN_NAME_INTEREST_RATE, historyRepository.getiRate());
        contentValues.put(HistoryMaster.Calculations.COLUMN_NAME_RATE_PER, historyRepository.getRatePer());
        contentValues.put(HistoryMaster.Calculations.COLUMN_NAME_LOAN_TERM, historyRepository.getlTerm());
        contentValues.put(HistoryMaster.Calculations.COLUMN_NAME_TERM_PERIOD, historyRepository.getTermPeriod());
        contentValues.put(HistoryMaster.Calculations.COLUMN_NAME_SYSTEM_DATE, date);

        //Insert new row
        long newRowId = database.insert(HistoryMaster.Calculations.TABLE_NAME, null, contentValues);

        if(newRowId != -1){
            return true;
        }

        return false;
    }

    public ArrayList<HistoryRepository> readLatestFiveRecords(){
        //get readable db
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                HistoryMaster.Calculations._ID,
                HistoryMaster.Calculations.COLUMN_NAME_LOAN_AMOUNT,
                HistoryMaster.Calculations.COLUMN_NAME_INTEREST_RATE,
                HistoryMaster.Calculations.COLUMN_NAME_RATE_PER,
                HistoryMaster.Calculations.COLUMN_NAME_LOAN_TERM,
                HistoryMaster.Calculations.COLUMN_NAME_TERM_PERIOD,
                HistoryMaster.Calculations.COLUMN_NAME_SYSTEM_DATE
        };

        String sortOrder = HistoryMaster.Calculations._ID + " DESC LIMIT 5";

        Cursor cursor = db.query(
                HistoryMaster.Calculations.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        ArrayList<HistoryRepository> historyRepositories = new ArrayList<>();
        while (cursor.moveToNext()){
            HistoryRepository historyRepository = new HistoryRepository();
            historyRepository.setlAmount(cursor.getFloat(cursor.getColumnIndexOrThrow(HistoryMaster.Calculations.COLUMN_NAME_LOAN_AMOUNT)));
            historyRepository.setiRate(cursor.getFloat(cursor.getColumnIndexOrThrow(HistoryMaster.Calculations.COLUMN_NAME_INTEREST_RATE)));
            historyRepository.setRatePer(cursor.getString(cursor.getColumnIndexOrThrow(HistoryMaster.Calculations.COLUMN_NAME_RATE_PER)));
            historyRepository.setlTerm(cursor.getInt(cursor.getColumnIndexOrThrow(HistoryMaster.Calculations.COLUMN_NAME_LOAN_TERM)));
            historyRepository.setTermPeriod(cursor.getString(cursor.getColumnIndexOrThrow(HistoryMaster.Calculations.COLUMN_NAME_TERM_PERIOD)));
            historyRepository.setSysDate(cursor.getString(cursor.getColumnIndexOrThrow(HistoryMaster.Calculations.COLUMN_NAME_SYSTEM_DATE)));

            historyRepositories.add(historyRepository);
        }

        cursor.close();

        return historyRepositories;
    }
}
