package com.app.phr.peru.peruphr_app.JAVA;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.phr.peru.peruphr_app.R;

public class FragmentMyInfo extends Fragment {
    private EditText oldPW;
    private EditText newPW1, newPW2;
    private Button btn;
    XmlParser parser;
    private String key, id;
    private PwChangeTask task = null;
    private SharedPreferences preferences;
    private ProgressDialog progressDoalog;
    private Boolean flag = false;
    public FragmentMyInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);
        key = preferences.getString(PreferencePutter.PREF_KEY, "null");
        id = preferences.getString(PreferencePutter.PREF_ID, "null");


    }

    @Override
    public void onResume(){
        super.onResume();
        oldPW = (EditText) getActivity().findViewById(R.id.MyInfo_password);
        newPW1 = (EditText) getActivity().findViewById(R.id.MyInfo_NewPassword1);
        newPW2 = (EditText) getActivity().findViewById(R.id.MyInfo_NewPassword2);
        btn = (Button) getActivity().findViewById(R.id.changePW);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                    String old, new1, new2;

                    old = oldPW.getText().toString();
                    new1 = newPW1.getText().toString();
                    new2 = newPW2.getText().toString();
                    SharedPreferences preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);

                    if (!old.equals("") && !new1.equals("") && !new2.equals("")) {
                        if (!new1.equals(new2)) {
                            Toast.makeText(getActivity().getApplicationContext(), "different", Toast.LENGTH_SHORT).show();
                            newPW1.setText("");
                            newPW2.setText("");
                        } else {
                            request(old, new1);
                        }
                    } else {
                            // old password가 틀렸을때 (판단을 어디서?)
                    }
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "need to connect network", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_myinfo, container, false);
    }


    private void request(String old, String newPW) {
        if (task != null) {
            return;
        }
        task = new PwChangeTask(old, newPW);
        task.execute();

    }

    public class PwChangeTask extends AsyncTask<Void, Void, String> {

        private HTTPClient client;
        private String result;
        XmlParser parser;
        String old, newPW;
        PwChangeTask(String old, String newPW) {
            result = "";
            this.old = old;
            this.newPW = newPW;
            XmlWriter writer = new XmlWriter();
            client = new HTTPClient();
            client.setDoc(writer.getXmlForChange(id, key, old, newPW));
        }

        @Override
        protected String doInBackground(Void... params) {
            result = client.connect();
            result = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                    "<Response statusCode='100'>\n" +
                    "</Response>";
            //get keyCD  result = client.getKey();
            return result;
        }


        @Override
        protected void onPostExecute(final String result) {
            task = null;
            flag = true;
            //showProgress(false);
            parser = new XmlParser();
            if (result.equals("connection error")) {
                Toast.makeText(getActivity().getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            }
            else{
                int response = parser.checkResponse(result);
                if(response == 200){

                    Toast.makeText(getActivity().getApplicationContext(),"Fail to chage password",Toast.LENGTH_SHORT).show();
                }
                else if(response == 100) {
                    Log.d("myinfo","change success");
                    Toast.makeText(getActivity().getApplicationContext(),"change success",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(PreferencePutter.PREF_PW, old);
                    editor.commit();
                    reset();
                    // server에게 login 인증 후 phr data 요청

                    //start next Activity
                }
            }
        }

        @Override
        protected void onCancelled() {
            task = null;
            flag = true;
            //showProgress(false);
        }
    }
    private void reset(){
        newPW1.setText("");
        newPW2.setText("");
        oldPW.setText("");
        if(newPW1.isFocused())
            newPW1.clearFocus();
        if(newPW2.isFocused())
            newPW2.clearFocus();
        if(oldPW.isFocused())
            oldPW.clearFocus();
    }

}
