package com.dutt.rishabh.hackathon2k16;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class VNoteFragment extends Fragment {
    public static  String x;
    private static TextView t2;
    public VNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_vnote, container, false);
        Button btn1=(Button) v.findViewById(R.id.button);
        Button btn2=(Button) v.findViewById(R.id.button1);
        final DatabaseHelper dbHelper=new DatabaseHelper(v.getContext());
        t2=(TextView) v.findViewById(R.id.textView5);
        Button btn3=(Button) v.findViewById(R.id.button9);
        final EditText et=(EditText) v.findViewById(R.id.editText3);
        Button btn4=(Button) v.findViewById(R.id.button10);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Say something:");
                try {
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(v.getContext(),
                            "The speech to text is not supported by your device!",
                            Toast.LENGTH_SHORT).show();
                }

                //Log.d("tag2",t2.getText().toString());

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x=t2.getText().toString();
                if(!x.equals("")) {
                    Date date = new Date();
                    String sdate = date.toString();
                    Boolean m = dbHelper.addNotes(x, sdate);
                    if (m) {
                        Toast.makeText(v.getContext(), "Note is added!", Toast.LENGTH_SHORT).show();
                        t2.setText("");
                    } else {
                        Toast.makeText(v.getContext(), "Note is not added!", Toast.LENGTH_SHORT).show();
                        t2.setText("");
                    }
                }else{
                    Toast.makeText(v.getContext(), "Please give input!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor m = dbHelper.viewAll();
                if (m.getCount() == 0) {
                    Toast.makeText(v.getContext(), "No Notes found!", Toast.LENGTH_SHORT).show();
                }
                else{
                    StringBuffer sb = new StringBuffer();
                    while (m.moveToNext()) {
                        sb.append("ID:" + m.getString(0) + "\n");
                        sb.append("DATE: " + m.getString(2) + "\n");
                        sb.append("NOTE: " + m.getString(1) + "\n\n");
                    }
                    showMessage("NOTES DETAIL:", sb.toString());
            }
        }});
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t=et.getText().toString();
                Boolean x=dbHelper.deleteNote(t);
                if(x){
                    Toast.makeText(v.getContext(),"Note is deleted!",Toast.LENGTH_SHORT).show();
                    et.setText("");
                }
                else{
                    Toast.makeText(v.getContext(),"Note is not deleted!",Toast.LENGTH_SHORT).show();
                    et.setText("");
                }
                }
        });
        return v;
    }
    public void showMessage(String title,String  mess){
        AlertDialog.Builder x=new AlertDialog.Builder(this.getContext());
        x.setCancelable(true);
        x.setTitle(title);
        x.setMessage(mess);
        x.show();
    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         t2=(TextView) getActivity().findViewById(R.id.textView5);
        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d("tag1",result.toString());
                    t2.append(result.get(0)+"\n");

                }
                break;
            }

        }
    }

}
