package com.example.utamobilevendingsystem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.utamobilevendingsystem.OperatorFragment;

public class RegistrationFragmentAdapter extends FragmentPagerAdapter {
    public RegistrationFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        OperatorFragment operatorFragment = new OperatorFragment();
        Bundle bundle = new Bundle();
        position+=1;
        if (position == 1){
            bundle.putString("message","Operator");
        }
        else if (position == 2){
            bundle.putString("message","User");
        }
        operatorFragment.setArguments(bundle);
        return operatorFragment;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
