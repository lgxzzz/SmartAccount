package com.smart.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.smart.account.bean.AccountPerson;
import com.smart.account.bean.User;
import com.smart.account.data.DBManger;
import com.smart.account.view.SpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class UpdatePersonActivity extends AppCompatActivity {

    private EditText mNowNameEd;
    private Spinner mBeforeNameSp;
    private Button mRegBtn;
    private Button mCancelBtn;

    private AccountPerson mAccountPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ac_person);

        init();
        refreshPerson();
    }

    public void init(){

        mNowNameEd = findViewById(R.id.now_name_ed);
        mBeforeNameSp = findViewById(R.id.before_name_sp);
        mRegBtn = findViewById(R.id.sure_btn);
        mCancelBtn = findViewById(R.id.cancel_btn);

        mNowNameEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                mAccountPerson.setName(editable.toString());
            }
        });


        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nowName = mNowNameEd.getEditableText().toString();

                if (nowName.length()==0){
                    Toast.makeText(UpdatePersonActivity.this,"名字不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                mAccountPerson = mPersonData.get(mIndex);
                String oldName = mAccountPerson.getName();
                mAccountPerson.setName(nowName);
                DBManger.getInstance(UpdatePersonActivity.this).updateAccountPerson(oldName,mAccountPerson, new DBManger.IListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(UpdatePersonActivity.this,"修改成功！",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(UpdatePersonActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(UpdatePersonActivity.this,"修改失败！",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePersonActivity.this.finish();
            }
        });
    }

    int mIndex = 0;
    List<AccountPerson> mPersonData = new ArrayList<>();
    public void refreshPerson(){

        final ArrayList<String> mPersonNameData = new ArrayList<>();

        mPersonData= DBManger.getInstance(UpdatePersonActivity.this).getAllAccountPerson();

        for (int i =0;i<mPersonData.size();i++){
            AccountPerson accountPerson = mPersonData.get(i);
            mPersonNameData.add(accountPerson.getName());
        }
        mAccountPerson = mPersonData.get(0);

        SpinnerAdapter TypeAdapter = new SpinnerAdapter(UpdatePersonActivity.this,android.R.layout.simple_spinner_item,mPersonNameData);
        mBeforeNameSp.setAdapter(TypeAdapter);
        mBeforeNameSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}
