package com.smart.account.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smart.account.DeleteUserActivity;
import com.smart.account.ForgetPwdActivity;
import com.smart.account.LoginActivity;
import com.smart.account.R;
import com.smart.account.UpdatePersonActivity;
import com.smart.account.UpdatePwdActivity;


public class PersonalSettingFragment extends Fragment {

    Button mUpdatePwdBtn;
    Button mUpdatePersonBtn;
    Button mDeleteUserBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_personal_setting, container, false);
        initView(view);

        return view;
    }

    public static PersonalSettingFragment getInstance() {
        return new PersonalSettingFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initView(View view){
        mUpdatePwdBtn = view.findViewById(R.id.update_password_btn);
        mUpdatePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdatePwdActivity.class);
                startActivity(intent);
            }
        });

        mUpdatePersonBtn = view.findViewById(R.id.update_person_btn);
        mUpdatePersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdatePersonActivity.class);
                startActivity(intent);
            }
        });

        mDeleteUserBtn = view.findViewById(R.id.delte_user_btn);
        mDeleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DeleteUserActivity.class);
                startActivity(intent);
            }
        });
    };

}
