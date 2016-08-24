package com.app.phr.peru.peruphr_app.JAVA;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.app.phr.peru.peruphr_app.R;


public class FragmentEducationInfo extends Fragment{

    public FragmentEducationInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     //   hideKeyboard();

        return inflater.inflate(R.layout.fragment_educationinfo, container, false);
    }

}
