package site.xingchi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import site.xingchi.pojo.Contacts;
import site.xingchi.utils.DataBaseHelper;

public class AddContactsActivity extends AppCompatActivity{

    private EditText nameTxt;
    private EditText phoneNumTxt;
    private EditText infoTxt;
    private TitleBar titleBarAddContacts;
    private DataBaseHelper db_help;
    private SQLiteDatabase db;
    private ContentValues values;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initData();
    }
    private void initView(){
        nameTxt = (EditText) findViewById(R.id.nameTxt);
        phoneNumTxt = (EditText) findViewById(R.id.phoneNumTxt);
        infoTxt = (EditText) findViewById(R.id.infoTxt);
        titleBarAddContacts = (TitleBar) findViewById(R.id.title_bar_add_contacts);
    }
    private void initData(){
        db_help = new DataBaseHelper(getApplicationContext(), "phone_db");
        intent=new Intent(getApplicationContext(),MainActivity.class);
        titleBarAddContacts.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                AddContactsActivity.this.finish();
            }
            @Override
            public void onTitleClick(View v) {
            }
            @Override
            public void onRightClick(View v) {
                String name=nameTxt.getText().toString().trim();
                String phoneNum=phoneNumTxt.getText().toString().trim();
                String info=infoTxt.getText().toString().trim();
                if(TextUtils.isEmpty(name)| TextUtils.isEmpty(phoneNum)){
                    Toast.makeText(getApplicationContext(),"输入关键信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(info)){
                    info="";
                }
                Contacts contacts=new Contacts(name,phoneNum,info);
                addContacts(contacts);
                Toast.makeText(getApplicationContext(),"保存成功！",Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent,1);
            }
        });
    }
    private void addContacts(Contacts contacts){
        db=db_help.getWritableDatabase();
        values=new ContentValues();
        values.put("name",contacts.getName());
        values.put("phoneNum",contacts.getPhone_num());
        values.put("info",contacts.getInfo());
        db.insert("contacts",null,values);
        db.close();
    }
}
