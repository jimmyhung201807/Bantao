package com.example.chi.dbtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {
    Bundle bundle;
    TextView txt_name, txt_phone, txt_mail, txt_birth;
    int cursor_id, index;
    Dbadapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        //取得Intent參數, 此處取得的是id值
        bundle = this.getIntent().getExtras();
        cursor_id = bundle.getInt("item_id");
        //設定dbAdapter
        dbAdapter = new Dbadapter(this);
        //透過dbAdapter的queryById方法取得資料
        Cursor cursor = dbAdapter.queryById(cursor_id);
        //將資料顯示在畫面上
        index = cursor.getInt(0);
        txt_name.setText(cursor.getString(1));
        txt_phone.setText(cursor.getString(2));
        txt_mail.setText(cursor.getString(3));
        txt_birth.setText(cursor.getString(4));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShowActivity.this, EditActivity.class);
                i.putExtra("item_id",index);
                i.putExtra("type","edit");
                startActivity(i);
            }
        });
    }
    private void initView(){
        txt_name = findViewById(R.id.txtName);
        txt_phone = findViewById(R.id.txtPhone);
        txt_mail = findViewById(R.id.txtMail);
        txt_birth = findViewById(R.id.txtBirth);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.del_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_del:
                //刪除聯絡人
                AlertDialog dialog = null;
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(this);
                builder.setTitle("警告")
                        .setMessage("確定要刪除此筆資料? 刪除後無法回復")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            //設定確定按鈕
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Boolean isDeleted = dbAdapter.deleteContacts(index);
                                if(isDeleted)
                                    Toast.makeText(ShowActivity.this,"已刪除!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            //設定取消按鈕
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
