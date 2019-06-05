package com.android.notebook;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewTextActivity extends AppCompatActivity {

    private Button button;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_text);

        text = findViewById(R.id.et_new);
        button = findViewById(R.id.btn_new);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "";
                string = text.getText().toString();
                if("".equals(string)){
                    Toast.makeText(NewTextActivity.this,"请输入内容在保存！",Toast.LENGTH_SHORT).show();
                } else {
                    User us_wri = new User(NewTextActivity.this);
                    SQLiteDatabase sqLiteDatabase = us_wri.getWritableDatabase();
                    us_wri.adddata(sqLiteDatabase,string);
                    Toast.makeText(NewTextActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}
