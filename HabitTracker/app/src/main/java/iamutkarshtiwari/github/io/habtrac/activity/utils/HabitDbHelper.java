package iamutkarshtiwari.github.io.habtrac.activity.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import iamutkarshtiwari.github.io.habtrac.activity.models.HabitContract;

/**
 * Created by utkarshtiwari on 16/10/17.
 */

public class HabitDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "habittracker.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABITS_TABLE =  "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
                + HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.HabitEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_START_DATE + " DATETIME NOT NULL DEFAULT CURRENT_DATE, "
                + HabitContract.HabitEntry.COLUMN_NUMBER_OF_TIMES + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}