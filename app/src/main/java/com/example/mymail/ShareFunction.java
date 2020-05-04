package com.example.mymail;


import android.app.Activity;
import android.content.Intent;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public  class ShareFunction {
    public static void setFragment(Fragment fragment, FrameLayout container, FragmentActivity mCtx,boolean left)
    {
        FragmentTransaction fragmentTransaction = mCtx.getSupportFragmentManager().beginTransaction();
        if(left==true)fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        else fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(container.getId(),fragment);
        fragmentTransaction.commit();
    }
  
}
