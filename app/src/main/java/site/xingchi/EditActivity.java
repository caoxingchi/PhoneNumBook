package site.xingchi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.sql.DatabaseMetaData;

import site.xingchi.pojo.Contacts;
import site.xingchi.utils.DataBaseHelper;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private TitleBar titleBarAddContacts;
    private EditText nameTxt;
    private EditText phoneNumTxt;
    private EditText infoTxt;
    private LinearLayout llSave;
    private Button save;

    private DataBaseHelper db_helper;
    private SQLiteDatabase db;
    private ContentValues values;
    private Contacts contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        initView();
        initData();
    }
    public void initView(){
        titleBarAddContacts = (TitleBar) findViewById(R.id.title_bar_add_contacts);
        nameTxt = (EditText) findViewById(R.id.nameTxt);
        phoneNumTxt = (EditText) findViewById(R.id.phoneNumTxt);
        infoTxt = (EditText) findViewById(R.id.infoTxt);
        llSave = (LinearLayout) findViewById(R.id.ll_save);
        save = (Button) findViewById(R.id.save);
    }
    public void initData(){
        titleBarAddContacts.setTitle("我的联系人");
        titleBarAddContacts.setRightTitle("编辑");
        nameTxt.setEnabled(false);
        phoneNumTxt.setEnabled(false);
        infoTxt.setEnabled(false);
        db_helper=new DataBaseHelper(EditActivity.this,"phone_db");
        save.setOnClickListener(this);
        titleBarAddContacts.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                EditActivity.this.finish();
            }
            @Override
            public void onTitleClick(View v) {
            }
            @Override
            public void onRightClick(View v) {
                nameTxt.setEnabled(true);
                phoneNumTxt.setEnabled(true);
                infoTxt.setEnabled(true);
                llSave.setVisibility(View.VISIBLE);
                save.setEnabled(true);
            }
        });
        Intent intent=getIntent();
        contacts = (Contacts) intent.getSerializableExtra("contacts");
        String name = contacts.getName();
        String phone_num = contacts.getPhone_num();
        String info = contacts.getInfo();
        if(TextUtils.isEmpty(info)){
            info="";
        }
        nameTxt.setText(name);
        phoneNumTxt.setText(phone_num);
        infoTxt.setText(info);
    }
    @Override
    public void onClick(View v) {
        String name=nameTxt.getText().toString().trim();
        String phoneNum=phoneNumTxt.getText().toString().trim();
        String info=infoTxt.getText().toString().trim();
        if(TextUtils.isEmpty(name)| TextUtils.isEmpty(phoneNum)){
            Toast.makeText(getApplicationContext(),"关键信息不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(info)){
            info="";
        }
        db = db_helper.getWritableDatabase();
        values = new ContentValues();
        values.put("name",name);
        values.put("phoneNum",phoneNum);
        values.put("info",info);
        db.update("contacts",values,"id=?",new String[]{contacts.getId()+""});
        Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
        db.close();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent,1);
    }
}
