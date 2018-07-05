package com.example.chi.dbtest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Dbadapter dbAdapter;
    TextView no_contact;
    ListView list_contacts;
    private Intent intent;
    private SimpleCursorAdapter dataSimpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbAdapter = new Dbadapter(this);
        Log.i("dbCount=",String.valueOf(dbAdapter.listContacts().getCount()));
        no_contact = findViewById(R.id.no_contact);
        list_contacts = findViewById(R.id.list_contacts);
        displayList();
    }
    private void displayList(){
        Cursor cursor = dbAdapter.listContacts();
        String[] columns = new String[]{
                dbAdapter.KEY_NAME,
                dbAdapter.KEY_PHONE,
                dbAdapter.KEY_BIRTH
        };
        int[] view = new int[]{
                R.id.txtName,
                R.id.txtPhone,
                R.id.txtMail
        };
        dataSimpleAdapter = new SimpleCursorAdapter(this, R.layout.item_view, cursor, columns, view, 0);
        list_contacts.setAdapter(dataSimpleAdapter);
        list_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor current_cursor = (Cursor) list_contacts.getItemAtPosition(position);
                int item_id = current_cursor.getInt(current_cursor.getColumnIndexOrThrow("_id"));
                intent = new Intent();
                intent.putExtra("item_id", item_id);
                intent.setClass(MainActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent i = new Intent(this, EditActivity.class);
                i.putExtra("type","add");
                startActivity(i);

                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
