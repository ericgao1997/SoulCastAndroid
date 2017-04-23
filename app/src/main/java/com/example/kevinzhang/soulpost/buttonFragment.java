package com.example.kevinzhang.soulpost;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Created by Administrator on 2017-04-13.
 */

public class buttonFragment extends Fragment {

    OnRecordButtonClickListener mCallback;
    Button mRecordButton;
    public interface OnRecordButtonClickListener {
        public void onButtonPressed();

        public void onButtonReleased();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            mCallback = (OnRecordButtonClickListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.button_fragment, container, false);

        mRecordButton = (Button) view.findViewById(R.id.record_button);

        mRecordButton.setOnTouchListener(new View.OnTouchListener() {
                                             @Override
                                             public boolean onTouch(View v, MotionEvent event) {
                                                 if(event.getAction() == MotionEvent.ACTION_DOWN){
                                                     mCallback.onButtonPressed();
                                                 }else if(event.getAction() == MotionEvent.ACTION_UP){
                                                     mCallback.onButtonReleased();
                                                 }
                                                 return false;
                                             }
                                         }


        );
        return view;
    }








}
