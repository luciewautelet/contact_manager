package com.example.wautel_l.assignment1_et_2926395;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class List_contact extends AppCompatActivity {

    DBOpenHelper tdb;
    SQLiteDatabase sdb;
    ListView lv;

    ArrayList<Contact> contacts;
    ArrayList<String> results;
    private int MENU_EDIT = Menu.FIRST;
    private int  MENU_DELETE = Menu.FIRST + 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        lv = (ListView) findViewById(R.id.mlist);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact tmp = contacts.get(position);
                int id_tmp = tmp.getId();

                Intent tmp_int = new Intent(List_contact.this, Show_contact.class);
                tmp_int.putExtra("id", id_tmp);
                startActivity(tmp_int);
            }
        });

        registerForContextMenu(lv);


        tdb = new DBOpenHelper(this, "contact.db", null, 1);

        sdb = tdb.getWritableDatabase();


        load_list();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tmp = new Intent(List_contact.this, Add_contact.class);
                startActivity(tmp);
            }
        });



    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Contact tmp = contacts.get(position);
        int id_tmp = tmp.getId();


        if (item.getItemId() == MENU_EDIT)
        {
            Intent tmp_int = new Intent(List_contact.this, Edit_contact.class);
            tmp_int.putExtra("id", id_tmp);
            startActivity(tmp_int);
            load_list();

        }
        else if (item.getItemId() == MENU_DELETE)
        {
            tdb.delete_by_id(sdb, id_tmp);
            load_list();
        }
        return super.onContextItemSelected(item);
    }


    @Override

    public void onCreateContextMenu(ContextMenu menu, View vue, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, vue, menuInfo);

        menu.add(Menu.NONE, MENU_EDIT,0, "Edit");

        menu.add(Menu.NONE, MENU_DELETE, 1, "Delete");

    }
    protected void load_list()
    {
        results = null;
        contacts = null;
        contacts = tdb.get_contact(sdb);
        results = store_in_array();
        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(List_contact.this, android.R.layout.simple_list_item_1, results);
        lv.setAdapter(adapter);
    }

    protected ArrayList<String> store_in_array()
    {
        if (results == null)
            results = new ArrayList<>();
        for(Contact tmp : contacts){
            results.add(tmp.name);
        }
        return results;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        lv.setAdapter(null);
        load_list();
    }

}
