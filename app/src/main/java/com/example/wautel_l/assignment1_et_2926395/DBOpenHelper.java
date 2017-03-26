package com.example.wautel_l.assignment1_et_2926395;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wautel_l on 28/02/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context, name, cursorFactory, version);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(create_table);
    }

    public void onUpgrade(SQLiteDatabase db, int version_old, int version_new)
    {
        db.execSQL(drop_table);
        db.execSQL(create_table);
    }

    private static final String create_table = "create table contact(" + "ID integer primary key autoincrement, "
            + "NAME string, " + "HOME string, " + "MOBILE string," + "MAIL string" + ")";

    private static final String drop_table = "drop table contact";

    public void add_contact(SQLiteDatabase db,Contact to_create)
    {
        ContentValues cv = new ContentValues();
        cv.put("NAME", to_create.getName());
        cv.put("HOME", to_create.getTel_home());
        cv.put("MOBILE", to_create.getTel_mobile());
        cv.put("MAIL", to_create.getMail());
        db.insert("contact", null, cv);

    }

    public void edit_contact(SQLiteDatabase db, Contact to_edit)
    {
        ContentValues cv = new ContentValues();
        cv.put("NAME", to_edit.getName());
        cv.put("HOME", to_edit.getTel_home());
        cv.put("MOBILE", to_edit.getTel_mobile());
        cv.put("MAIL", to_edit.getMail());
        db.update("contact", cv, "id="+to_edit.getId(), null);
    }

    public ArrayList<String> get_all_name(SQLiteDatabase db){
        ArrayList<String> names=  new ArrayList<>();
        Cursor c = db.query("contact", new String[]{"NAME"}, null, null, null, null, null);
        if (c.moveToFirst())
        {
            do {
                names.add(c.getString(0));

            }while (c.moveToNext());
        }
        c.close();
        return names;
    }

    public void delete_by_id(SQLiteDatabase db, int id)
    {
        db.delete("contact", "id" + "=" + id, null);
    }

    public Contact getContactbyId(SQLiteDatabase db, Integer id)
    {
        Cursor c = db.query("contact", new String[]{"ID", "NAME", "HOME", "MOBILE", "MAIL"}, "id=?", new String[]{Integer.toString(id)}, null, null, "NAME DESC");
        if (c.moveToFirst())
        {
            do {
                    return (new Contact(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4)));

            }while (c.moveToNext());
        }
        c.close();
        return null;
    }

    public ArrayList<Contact> get_contact(SQLiteDatabase db)
    {
        ArrayList<Contact> contat_list = new ArrayList<>();
        Cursor c = db.query("contact", new String[]{"ID", "NAME", "HOME", "MOBILE", "MAIL"}, null, null, null, null, "NAME DESC");
        if (c.moveToFirst())
        {
            do {
                contat_list.add(new Contact(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4)));

            }while (c.moveToNext());
        }
        c.close();
        return contat_list;
    }
}
