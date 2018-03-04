package com.example.quizapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class CalculatorFragment extends Fragment {

    private TextView textView;
    private Button button0;
    private Button button01;
    private Button button02;
    private Button button03;
    private Button button04;
    private Button button05;
    private Button button06;
    private Button button07;
    private Button button08;
    private Button button09;
    private Button buttonAdd;
    private Button buttonSub;
    private Button buttonMul;
    private Button buttonDiv;
    private Button buttonEqual;
    private Button buttonCancel;
    private Calculator mCalculator;

    public CalculatorFragment() {
        mCalculator = new Calculator();
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            restoreData(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        setCalculator(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setCalculator(View view){
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(mCalculator.getAnswer());
        button01 = (Button) view.findViewById(R.id.button1);
        button01.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("1"));
            }
        });
        button02 = (Button) view.findViewById(R.id.button2);
        button02.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("2"));
            }
        });
        button03 = (Button) view.findViewById(R.id.button3);
        button03.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("3"));
            }
        });
        button04 = (Button) view.findViewById(R.id.button4);
        button04.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("4"));
            }
        });
        button05 = (Button) view.findViewById(R.id.button5);
        button05.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("5"));
            }
        });
        button06 = (Button) view.findViewById(R.id.button06);
        button06.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("6"));
            }
        });
        button07 = (Button) view.findViewById(R.id.button07);
        button07.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("7"));
            }
        });
        button08 = (Button) view.findViewById(R.id.button8);
        button08.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("8"));
            }
        });
        button09 = (Button) view.findViewById(R.id.button9);
        button09.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("9"));
            }
        });
        button0 = (Button) view.findViewById(R.id.button0);
        button0.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("0"));
            }
        });
        buttonAdd = (Button) view.findViewById(R.id.Addition);
        buttonAdd.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("+"));
            }
        });
        buttonSub = (Button) view.findViewById(R.id.subtraction);
        buttonSub.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("-"));
            }
        });
        buttonMul = (Button) view.findViewById(R.id.multiplication);
        buttonMul.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("*"));
            }
        });
        buttonDiv = (Button) view.findViewById(R.id.division);
        buttonDiv.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("/"));
            }
        });
        buttonEqual = (Button) view.findViewById(R.id.equal);
        buttonEqual.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("="));
            }
        });
        buttonCancel = (Button) view.findViewById(R.id.buttonC);
        buttonCancel.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(mCalculator.inputValue("C"));
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("FIRST", mCalculator.getFirst());
        outState.putDouble("SECOND", mCalculator.getSecond());
        outState.putString("OPERATOR", mCalculator.getOerator());
        outState.putString("TEMP", mCalculator.getTemp());
        outState.putBoolean("IS_SET_FIRST", mCalculator.getIsSetFirst());
        outState.putBoolean("IS_SET_SECOND", mCalculator.getIsSetSetSecond());
        outState.putBoolean("IS_SET_OPERATOR", mCalculator.getIsSetOperater());
        outState.putString("ANSWER", mCalculator.getAnswer());
    }

    private void restoreData(Bundle savedInstanceState){
        mCalculator.setData(savedInstanceState);
    }
}
