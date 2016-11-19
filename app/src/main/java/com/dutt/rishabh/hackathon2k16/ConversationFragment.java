package com.dutt.rishabh.hackathon2k16;


import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends Fragment {


    public ConversationFragment() {
        // Required empty public constructor
    }


    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_conversation, container, false);

        Button speakbtn = (Button) view.findViewById(R.id.button);
        TextView display = (TextView) view.findViewById(R.id.textView2);
        speakbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en_US");
                try {
                    startActivityForResult(intent, 1);

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(MainActivity.this, "Sorry Your Device does not support Speech to Text feautre", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            TextView  display = (TextView) getActivity().findViewById(R.id.textView2);
            //Toast.makeText(ctx, "Works", Toast.LENGTH_SHORT).show();
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> al = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                display.append("\n");
                display.append(al.get(0));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
