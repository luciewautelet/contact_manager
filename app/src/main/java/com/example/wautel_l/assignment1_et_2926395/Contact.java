

package com.example.wautel_l.assignment1_et_2926395;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by wautel_l on 08/03/2017.
 */

public class Contact {

    Integer id;
    String name;
    String tel_home;
    String tel_mobile;
    String mail;

    public Contact()
    {

    }

    public Contact(Integer id, String name, String tel_home, String tel_mobile, String mail)
    {
        this.id = id;
        this.name = name;
        this.tel_home = tel_home;
        this.tel_mobile = tel_mobile;
        this.mail = mail;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getTel_home()
    {
        return this.tel_home;
    }

    public String getTel_mobile()
    {
        return  this.tel_mobile;
    }

    public String getMail()
    {
        return  this.mail;
    }
}
