package com.example.wautel_l.assignment1_et_2926395;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by wautel_l on 28/02/2017.
 */

public class Edit_contact extends Activity{

    EditText nameet;
    EditText homeet;
    EditText mobet;
    EditText mailet;
    DBOpenHelper tdb;
    SQLiteDatabase sdb;
    int id;

    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        tdb = new DBOpenHelper(this, "contact.db", null, 1);
       sdb = tdb.getWritableDatabase();

        Intent rec = this.getIntent();
        id = rec.getExtras().getInt("id");



        nameet = (EditText) findViewById(R.id.nameet);
        homeet = (EditText) findViewById(R.id.homeet);
        mobet  = (EditText) findViewById(R.id.mobet);
        mailet = (EditText) findViewById(R.id.mailet);

        Contact tmp = tdb.getContactbyId(sdb, id);
        nameet.setText(tmp.getName());
        homeet.setText(tmp.getTel_home());
        mobet.setText( tmp.getTel_mobile());
        mailet.setText(tmp.getMail());

        FloatingActionButton validate = (FloatingActionButton) findViewById(R.id.validate);
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
                    Contact to_create = new Contact(id, name_rec, home_rec, mob_rec, mail_rec);
                    tdb.edit_contact(sdb, to_create);
                    sdb.close();
                    finish();
                }
            }
        });
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


    protected void verif_color()
    {
        homeet.setHighlightColor(0);
        mailet.setHighlightColor(0);
        mobet.setHighlightColor(0);
    }

    protected boolean verif_num(String num, EditText numet)
    {

        if (!num.isEmpty() && num.matches("(\\+?[0-9]*)"))
            return true;
        else
            numet.setHighlightColor(Color.RED);
        return false;
    }

    protected boolean verif_mail(String mail, EditText mailet)
    {
        if (!mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            return true;
        }
        else
            mailet.setHighlightColor(Color.RED);
        return false;
    }

}
