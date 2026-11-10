package com.adhyaapan.lord_buddha_kawasoti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.UUID;

/* loaded from: classes.dex */
public class databaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "gps.db";

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table students (id integer primary key autoincrement, uuid varchar(20) not null, name varchar(50) not null, category varchar(20) not null )");
        sQLiteDatabase.execSQL("create table gsm (id integer primary key autoincrement, mobile varchar(10) not null, title varchar(50) not null, time varchar(20) not null, remark vachar(20) not null )");
        sQLiteDatabase.execSQL("create table settings (id integer primary key autoincrement, parameter varchar(20) not null, value varchar(100) not null)");
        sQLiteDatabase.execSQL("insert into settings(parameter,value) values('mobile','m')");
        sQLiteDatabase.execSQL("insert into settings(parameter,value) values('lastUpdate','l')");
        sQLiteDatabase.execSQL("insert into settings(parameter,value) values('gpsId','')");
        sQLiteDatabase.execSQL("insert into settings(parameter,value) values('pickUp','')");
        sQLiteDatabase.execSQL("insert into settings(parameter,value) values('dropOff','')");
        sQLiteDatabase.execSQL("insert into settings(parameter,value) values('pick_time','')");
        sQLiteDatabase.execSQL("insert into settings(parameter,value) values('drop_time','')");
        sQLiteDatabase.execSQL("insert into settings(parameter,value) values('week_days','')");
        String replaceAll = UUID.randomUUID().toString().replaceAll("-", "");
        sQLiteDatabase.execSQL("insert into settings(parameter,value) values('androidId','" + replaceAll + "')");
    }

    public void insertStudent(String str, String str2, String str3) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uuid", str);
        contentValues.put("name", str2);
        contentValues.put("category", str3);
        if (writableDatabase.update("students", contentValues, "uuid=?", new String[]{str}) == 0) {
            writableDatabase.insert("students", null, contentValues);
        }
    }

    public Cursor getStudents(String str) {
        return getWritableDatabase().rawQuery("select * from Students where category=?", new String[]{str});
    }

    public Cursor getStudent(String str) {
        return getWritableDatabase().rawQuery("select * from Students where uuid=?", new String[]{str});
    }

    public Integer deleteStudent(String str) {
        return Integer.valueOf(getWritableDatabase().delete("students", "uuid=?", new String[]{str}));
    }

    public Cursor getGSM(String str) {
        return getWritableDatabase().rawQuery("select * from gsm where mobile=? limit 1", new String[]{str});
    }

    public void insertGSM(String str, String str2, String str3, String str4) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mobile", str);
        contentValues.put("title", str2);
        contentValues.put("time", str3);
        contentValues.put("remark", str4);
        if (writableDatabase.update("gsm", contentValues, "mobile=?", new String[]{str}) == 0) {
            writableDatabase.insert("gsm", null, contentValues);
        }
    }

    public String getSettings(String str) {
        Cursor rawQuery = getWritableDatabase().rawQuery("select * from settings where parameter=? limit 1", new String[]{str});
        rawQuery.moveToFirst();
        String string = rawQuery.getString(2);
        rawQuery.close();
        return string;
    }

    public void insertSettings(String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("parameter", str);
        contentValues.put("value", str2);
        writableDatabase.insert("settings", null, contentValues);
    }

    public boolean updateSettings(String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", str2);
        return ((long) writableDatabase.update("settings", contentValues, "parameter=?", new String[]{str})) != -1;
    }
}
