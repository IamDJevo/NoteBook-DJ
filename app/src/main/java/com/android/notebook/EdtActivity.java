package com.android.notebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class EdtActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_find,btn_refind,btn_save,btn_resave;
    private EditText edt_find,edt_refind,edt_edt;
    private int id,sql_id;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt);

        Intent intent = this.getIntent();
        String str_id = intent.getStringExtra("id");
        id = Integer.parseInt(str_id);
        init();
    }

    private void init() {
        btn_find = findViewById(R.id.btn_find);
        btn_refind = findViewById(R.id.btn_refind);
        btn_save = findViewById(R.id.btn_save);
        btn_resave = findViewById(R.id.btn_resave);
        btn_find.setOnClickListener(this);
        btn_refind.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_resave.setOnClickListener(this);
        edt_edt = findViewById(R.id.edt_edt);
        edt_find = findViewById(R.id.et_find);
        edt_refind = findViewById(R.id.et_refind);
        User user1 = new User(EdtActivity.this);
        SQLiteDatabase database1 = user1.getReadableDatabase();
        List<userInfo> list1 = user1.querydata(database1);
        string = list1.get(id).getMytext();
        sql_id = list1.get(id).getId();
        edt_edt.setText(string);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_find:
                String string1 = edt_find.getText().toString();
                string = edt_edt.getText().toString();
                if("".equals(string1)){
                    Toast.makeText(EdtActivity.this,"请输入查找内容！",Toast.LENGTH_SHORT).show();
                }else {
                    int a = string.indexOf(string1);
                    if(a<0){
                        Toast.makeText(EdtActivity.this,"没有找到！",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(EdtActivity.this,string1+" 位置在："+String.valueOf(a),Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_refind:
                String string2 = edt_find.getText().toString();
                String string3 = edt_refind.getText().toString();
                string = edt_edt.getText().toString();
                if("".equals(string2)||"".equals(string3)){
                    Toast.makeText(EdtActivity.this,"请输入查找替换内容！",Toast.LENGTH_SHORT).show();
                }else if(string2.equals(string3)){
                    Toast.makeText(EdtActivity.this,"查找内容和替换内容一样！",Toast.LENGTH_SHORT).show();
                }else {
                    int a = string.indexOf(string2);
                    if (a < 0) {
                        Toast.makeText(EdtActivity.this, "没有找到,无法替换！", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    String string4 = string.replaceAll(string2,string3);
                    edt_edt.setText(string4);
                    Toast.makeText(EdtActivity.this, "已经完成全部替换！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_save:
                string = edt_edt.getText().toString();
                if("".equals(string)){
                    new AlertDialog.Builder(EdtActivity.this).setTitle("系统提示")
                            .setMessage("确认保存为空文件?")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLsave();
                                }
                            }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }else SQLsave();
                break;
            case R.id.btn_resave:
                string = edt_edt.getText().toString();
                String fname = "test"+id+".txt";
                String state = Environment.getExternalStorageState();
                if(Environment.MEDIA_MOUNTED.equals(state)) {
                    File path = Environment.getExternalStorageDirectory();
                    File file = new File(path,fname);
                    FileOutputStream fileout;
                    try{
                        fileout = new FileOutputStream(file,true);
                        fileout.write(string.getBytes());
                        fileout.close();
                        Toast.makeText(EdtActivity.this, "文件另存为成功！", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(EdtActivity.this, "文件路径找不到！", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(EdtActivity.this, "文件另存为失败！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    //文本再编辑保存到数据库的操作
    public void SQLsave() {
        User user2 = new User(EdtActivity.this);
        SQLiteDatabase database2 = user2.getWritableDatabase();
        user2.update(database2,sql_id,string);
        Toast.makeText(EdtActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
        //感觉用不到这个了
        //finish();
    }

}
