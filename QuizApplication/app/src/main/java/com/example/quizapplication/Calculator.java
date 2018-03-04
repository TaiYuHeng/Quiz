package com.example.quizapplication;

import android.os.Bundle;

/**
 * Created by YuHeng_Tai on 2018/2/27.
 */

public class Calculator {
    private Double first = 0.0;
    private Double second = 0.0;
    private String operator = "";
    private String temp = "";
    private String answer = "";
    private Boolean isSetFirst = false;
    private Boolean isSetSecond = false;
    private Boolean isSetOperater = false;

    public String inputValue(String s){
        if (s.equals("C")){
            clear();
            return answer;
        }
        if (s.equals("=")){
            if (isSetFirst && isSetOperater && isSetSecond) {
                second = Double.valueOf(temp);
                if (operator.equals("+")) {
                    first = first + second;
                } else if (operator.equals("-")) {
                    first = first - second;
                } else if (operator.equals("*")) {
                    first = first * second;
                } else if (operator.equals("/")) {
                    first = first / second;
                }
                temp = "";
                isSetOperater = false;
                answer = String.valueOf(first);
            }
            return answer;
        }
        if(!isSetFirst && !isSetOperater) {
            if (!isOperater(s)) {
                temp = temp + s;
                answer = temp;
                isSetFirst = true;
            }
        } else if (isSetFirst && !isSetOperater) {
            if (!isOperater(s)) {
                temp = temp + s;
                answer = temp;
            } else {
                operator = s;
                isSetOperater = true;
                first = (temp.equals("")) ? first : Double.valueOf(temp);
                temp = "";
                answer = s;
            }
        } else if (isSetFirst && isSetOperater){
            if (!isOperater(s)) {
                temp = temp + s;
                answer = temp;
                isSetSecond = true;
            } else {
                if (temp.equals("")) {
                    operator = s;
                    isSetOperater = true;
                } else {
                    second = Double.valueOf(temp);
                    if (operator.equals("+")){
                        first = first + second;
                    } else if (operator.equals("-")) {
                        first = first - second;
                    } else if (operator.equals("*")) {
                        first = first * second;
                    } else if (operator.equals("/")) {
                        first = first / second;
                    }
                    temp = "";
                    operator = s;
                }
                answer = operator;
            }
        }
        return answer;
    }

    private boolean isOperater(String s){
        if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")){
            return  true;
        } else {
            return  false;
        }
    }
     private void clear(){
         first = null;
         second = null;
         operator = "";
         temp = "";
         isSetFirst = false;
         isSetOperater = false;
         answer = "";
     }

    //BEGIN Keep Data for Rotate
    public Double getFirst(){
         return first;
     }
    public Double getSecond(){
        return second;
    }
    public String getOerator(){
        return operator;
    }
    public String getTemp(){
        return temp;
    }
    public boolean getIsSetFirst(){
        return isSetFirst;
    }
    public boolean getIsSetSetSecond(){
        return isSetSecond;
    }
    public boolean getIsSetOperater(){
        return isSetOperater;
    }
    public String getAnswer(){
        return answer;
    }

    public void setData(Bundle savedInstanceState){
        first = savedInstanceState.getDouble("FIRST");
        second = savedInstanceState.getDouble("SECOND");
        operator = savedInstanceState.getString("OPERATOR");
        temp = savedInstanceState.getString("TEMP");
        isSetFirst = savedInstanceState.getBoolean("IS_SET_FIRST");
        isSetSecond = savedInstanceState.getBoolean("IS_SET_SECOND");
        isSetOperater = savedInstanceState.getBoolean("IS_SET_OPERATOR");
        answer = savedInstanceState.getString("ANSWER");
    }
    //END Keep Data for Rotate
}
