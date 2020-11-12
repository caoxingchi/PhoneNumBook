package site.xingchi;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import site.xingchi.adapter.ItemAdapter;
import site.xingchi.listener.ItemListener;
import site.xingchi.pojo.Contacts;
import site.xingchi.utils.DataBaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView listContacts;
    private FloatingActionButton fab;

    private DataBaseHelper db_help;

    private List<Contacts> contactsList;

    private SQLiteDatabase db;




    private static final String TAG = "MainActivity";
    private ItemAdapter itemAdapter;
    private PopupMenu popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initData();

    }
    private void initView() {
        listContacts = (RecyclerView) findViewById(R.id.list_contacts);
        fab = (FloatingActionButton) findViewById(R.id.fab);

    }
    private void initData() {
        db_help = new DataBaseHelper(getApplicationContext(), "phone_db");
        fab.setOnClickListener(this);
        contactsList=new ArrayList<>();
        findAll();
        itemAdapter = new ItemAdapter(getApplicationContext(),contactsList);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        //格子布局
        GridLayoutManager glm=new GridLayoutManager(this,2);
        listContacts.setLayoutManager(llm);
        listContacts.setAdapter(itemAdapter);
        itemAdapter.setItemListener(itemListener);
    }
    private ItemListener itemListener=new ItemListener() {
        @Override
        public void onItemClick(int position, Contacts contacts) {
            Intent intent=new Intent(MainActivity.this,EditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("contacts", contacts);
            intent.putExtras(bundle);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"我被点击了"+position,Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onItemLongClick(final int position, final Contacts contacts, View view) {
            Log.i(TAG, "onItemLongClick: ");
            popup = new PopupMenu(MainActivity.this,view);
            popup.getMenuInflater().inflate(R.menu.menu_pop, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.delete:
                        {
                            confirmDeleteAlert(position,contacts);
                            break;
                        }
                    }
                    return true;
                }
            });
            popup.show();
        }
    };

    /**
     * 确认删除操作
     * @param position
     * @param contacts
     */
    public void  confirmDeleteAlert(final int position,final Contacts contacts){
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.mipmap.warning)
                .setTitle("警告")
                .setMessage("确认删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(contacts.getId());
                        contactsList.remove(contacts);
                        itemAdapter.notifyDataSetChanged();
                        itemAdapter.notifyItemChanged(position);
                        Toast.makeText(getApplicationContext(),"删除成功！！",Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).create().show();
    }

    /**
     * 查询所有并将其显示
     */
    public void findAll() {
        Contacts contacts=null;
        db = db_help.getReadableDatabase();
        Cursor cursor = db.query("contacts", null, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            Log.i(TAG, "findAll: 未查询到联系人");
            Toast.makeText(getApplicationContext(), "未查询到联系人", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToFirst();
            contacts=new Contacts(cursor.getInt(0),cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("phoneNum")),cursor.getString(cursor.getColumnIndex("info")));
            contactsList.add(contacts);
        }
        while(cursor.moveToNext()){
            contactsList.add(new Contacts(cursor.getInt(0),cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("phoneNum")),cursor.getString(cursor.getColumnIndex("info"))));
        }
        cursor.close();
        db.close();
    }
    /**
     * 删除操作
     * @param id
     */
    public void delete(int id){
        db = db_help.getWritableDatabase();
        db.delete("contacts","id=?",new String[]{id+""});
        //itemAdapter.notifyDataSetChanged();
        //Toast.makeText(this, "数据删除成功", Toast.LENGTH_SHORT).show();
        db.close();
    }
    @Override
    public void onClick(View v) {
        /**
         * 跳转到新建页面
         */
        switch (v.getId()){
            case R.id.fab:{
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddContactsActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    /**
     * 跳转回来更新数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
            itemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
