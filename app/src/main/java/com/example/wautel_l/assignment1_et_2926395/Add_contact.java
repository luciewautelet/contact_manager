package com.example.wautel_l.assignment1_et_2926395;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by wautel_l on 28/02/2017.
 */

public class Add_contact extends Activity {

    DBOpenHelper tdb;
    SQLiteDatabase sdb;
    FloatingActionButton validate;
    EditText nameet;
    EditText mobet;
    EditText homeet;
    EditText mailet;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        tdb = new DBOpenHelper(this, "contact.db", null, 1);
        sdb = tdb.getWritableDatabase();

        nameet = (EditText) findViewById(R.id.nameet);
        homeet = (EditText) findViewById(R.id.homeet);
       mobet = (EditText) findViewById(R.id.mobet);
        mailet = (EditText) findViewById(R.id.mailet);

        validate = (FloatingActionButton) findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verif_color();

                String name_rec = nameet.getText().toString();
                String home_rec = homeet.getText().toString();
                String mob_rec = mobet.getText().toString();
                String mail_rec = mailet.getText().toString();

                if (verif_num(mob_rec, mobet) && verif_num(home_rec, homeet) && verif_mail(mail_rec, mailet) && verif_name(name_rec, nameet))
                {
                    Contact to_create = new Contact(-1, name_rec, home_rec, mob_rec, mail_rec);
                    tdb.add_contact(sdb, to_create);
                    sdb.close();
                    finish();
                }
            }
        });

    }

    protected void verif_color()
    {
        homeet.setHighlightColor(0);
        mailet.setHighlightColor(0);
        mobet.setHighlightColor(0);
    }

    protected  boolean verif_name(String name, EditText namet)
    {
        if (name == null || name.length() == 0)
        {
            Toast.makeText(this, "enter a name",Toast.LENGTH_SHORT).show();
            namet.setHighlightColor(Color.RED);
            return  false;
        }

        ArrayList<String> names = tdb.get_all_name(sdb);
        for (String tmp : names){
            if (tmp.equalsIgnoreCase(name))
            {
                Toast.makeText(this, "name already existing",Toast.LENGTH_SHORT).show();
                namet.setHighlightColor(Color.RED);
                return  false;
            }
        }

        return true;
    }

    protected boolean verif_num(String num, EditText numet)
    {

        if (!num.isEmpty() && num.matches("(\\+?[0-9]*)"))
            return true;
        else {
            Toast.makeText(this, "bad number format",Toast.LENGTH_SHORT).show();
            numet.setHighlightColor(Color.RED);
        }
        return false;
    }

    protected boolean verif_mail(String mail, EditText mailet)
    {
        if (!mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            return true;
        }
        else {
            Toast.makeText(this, "bad mail format",Toast.LENGTH_SHORT).show();
            mailet.setHighlightColor(Color.RED);
        }
        return false;
    }

}
