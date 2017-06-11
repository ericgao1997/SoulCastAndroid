package com.camvy.kevinzhang.soulcast;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.Button;

/**
 * Created by Administrator on 2017-04-13.
 */

public class ButtonFragment extends Fragment {

    OnRecordButtonClickListener mCallback;
    Button mButton;
    public interface OnRecordButtonClickListener {
        void onButtonPressed();
        void onButtonReleased();
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
        View mView = inflater.inflate(R.layout.button_fragment, container, false);

        mButton = (Button) mView.findViewById(R.id.soul_button);

        mButton.setOnTouchListener(new View.OnTouchListener() {
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
        return mView;
    }








}

