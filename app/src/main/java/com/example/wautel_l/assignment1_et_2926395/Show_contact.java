package com.example.wautel_l.assignment1_et_2926395;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by wautel_l on 28/02/2017.
 */

public class Show_contact extends Activity {

    DBOpenHelper tdb;
    SQLiteDatabase sdb;
    Integer id;
    TextView nametv;
    TextView hometv;
    TextView mobiletv;
    TextView mailtv;

    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        Intent rec = this.getIntent();
        id = rec.getExtras().getInt("id");

        tdb = new DBOpenHelper(this,  "contact.db", null, 1);
        sdb = tdb.getWritableDatabase();

        nametv = (TextView) findViewById(R.id.nametv);
        hometv = (TextView) findViewById(R.id.hometv);
        mobiletv = (TextView) findViewById(R.id.mobtv);
        mailtv = (TextView) findViewById(R.id.mailtv);

        Contact tmp = tdb.getContactbyId(sdb, id);
        nametv.setText(tmp.getName());
        hometv.setText("Home number : " + tmp.getTel_home());
        mobiletv.setText("Mobile number : " + tmp.getTel_mobile());
        mailtv.setText("Mail : " + tmp.getMail());
    }

}
