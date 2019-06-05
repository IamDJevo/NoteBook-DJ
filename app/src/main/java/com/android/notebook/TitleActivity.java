package com.android.notebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TitleActivity extends AppCompatActivity {

    private List<userInfo> list;
    private User user_info;
    private SQLiteDatabase sqLiteDatabase;
    private String[] uesr_mes;
    private ListView user_list;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        initshow();
        button = findViewById(R.id.btn_newtxt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, NewTextActivity.class);
                startActivity(intent);
            }
        });

        user_info = new User(TitleActivity.this);
        sqLiteDatabase = user_info.getReadableDatabase();
        list = user_info.querydata(sqLiteDatabase);
        uesr_mes = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            uesr_mes[i] = list.get(i).toString();
        }

        //把获取到信息显示到ListView中
        user_list = findViewById(R.id.title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, uesr_mes);
        user_list.setAdapter(adapter);
        //为istView每个元素添加单击事件
        user_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int id = list.get(i).getId();
                final int finalid = i;
                new AlertDialog.Builder(TitleActivity.this).setTitle("系统提示")
                        .setMessage("请选择！")
                        .setPositiveButton("编辑", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(TitleActivity.this,EdtActivity.class);
                                intent.putExtra("id",String.valueOf(finalid));
                                startActivity(intent);
                            }
                        }).setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(TitleActivity.this).setTitle("系统提示")
                                .setMessage("确认删除该条记事本数据！")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        User user_del = new User(TitleActivity.this);
                                        SQLiteDatabase database = user_del.getWritableDatabase();
                                        user_del.delete(database,id);
                                        refresh();
                                        Toast.makeText(TitleActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                                    }
                                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                    }
                }).show();
            }
        });

    }

    private void initshow() {
        User user1 = new User(TitleActivity.this);
        SQLiteDatabase database1 = user1.getReadableDatabase();
        List<userInfo> list = user1.querydata(database1);
        if(list.isEmpty()){
            User user2 = new User(TitleActivity.this);
            SQLiteDatabase database2 = user2.getWritableDatabase();
            String string2 = "这是一条初始化的测试数据！另存为功能使用魅族15手机测试成功，存取路径为手机的根目录文件夹的\"test\"+id+\".txt\"，在华为和虚拟机上测试失败";
            user2.adddata(database2,string2);
        }
    }

    //刷新页面方法
    private void refresh() {
        finish();
        Intent intent = new Intent(TitleActivity.this,TitleActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refresh();
    }

}
