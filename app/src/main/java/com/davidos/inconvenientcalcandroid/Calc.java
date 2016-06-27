package com.davidos.inconvenientcalcandroid;

import android.content.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Calc {
    private Display mDisplay;
    private List<BigDecimal> mOperandContainer;
    private List<Character> mOperatorContainer;
    private String mBigText;
    private String mSmallText;
    private boolean mChecker;

    public Calc(Context context){
        mDisplay = new Display(context);
        mBigText = "0";
        mSmallText = "";
        mOperandContainer = new ArrayList<>();
        mOperatorContainer = new ArrayList<>();
    }
    //Displays numbers on main textView
    public void showTextBig() {
        mDisplay.setCalcTextView(mBigText);
    }

    //Displays numbers in process on smaller textView
    public void showTextSmall() {
        mDisplay.setProcessTextView(mSmallText);
    }

    public void handleSymbols(char chr) {

        //handles operators
        if (Character.toString(chr).matches("[-\\+\\*/=]")) {
            calculate(chr);
            return;
        }

        //defines C
        if (chr == 'C') {
            mBigText = "0";
            showTextBig();
            mOperatorContainer.clear();
            mOperandContainer.clear();
            mSmallText = "";
            showTextSmall();
            mChecker = false;
            return;
        }

        //starts a new number on main textView
        if (Character.isDigit(chr) && mChecker) {
            mBigText = "0";
            mBigText = String.valueOf(new BigDecimal(mBigText += chr));
            showTextBig();
            mChecker = false;
            return;
        }

        //adds typed digit to THE number
        if ((Character.isDigit(chr) && (mBigText.length() <= 9))) {
            mBigText = String.valueOf(new BigDecimal(mBigText += chr));
            showTextBig();
            return;
        }

        //handles .
        if (chr == '.' && !mBigText.contains(".") && mBigText.length() <= 8) {
            mBigText = String.valueOf(new BigDecimal(mBigText)) + chr;
            showTextBig();
        }
    }

    private void calculate(char operator) {
        //changes previous operator to a newly typed one
        if (operator != '=' && mSmallText.length() != 0 && Character.toString(mSmallText.charAt(mSmallText.length() - 1)).matches("[-\\+\\*/]") && mChecker) {
            mSmallText = mSmallText.substring(0, mSmallText.length() - 1) + operator;
            showTextSmall();
            mOperatorContainer.add(operator);
            return;
        }

        //initiates result
        BigDecimal result = BigDecimal.valueOf(0);

        //creates a number out of entered digits
        mOperandContainer.add(new BigDecimal(mBigText));

        //while only one number is entered
        if (operator != '=' && (mOperatorContainer.size() < 1)) {
            mOperatorContainer.add(operator);
            showTextBig();
            mSmallText = mBigText + " " + operator;
            showTextSmall();
            mChecker = true;
            return;
        }

        //calculation
        if (mOperatorContainer.size() >= 1) {

            mOperatorContainer.add(operator);
            char previousOperator = mOperatorContainer.get(mOperatorContainer.size() - 2);
            //does not allow division by 0
            if (mBigText.equals("0") && previousOperator == '/') {
                mBigText = "D'OH!";
                showTextBig();
                mBigText = "0";
                mSmallText = "";
                showTextSmall();
                return;
            }

            //calculation by operators
            switch (previousOperator) {
                case '+':
                    result = mOperandContainer.get(mOperandContainer.size() - 2).add(mOperandContainer.get(mOperandContainer.size() - 1));
                    break;
                case '-':
                    result = mOperandContainer.get(mOperandContainer.size() - 2).subtract(mOperandContainer.get(mOperandContainer.size() - 1));
                    break;
                case '*':
                    result = mOperandContainer.get(mOperandContainer.size() - 2).multiply(mOperandContainer.get(mOperandContainer.size() - 1));
                    break;
                case '/':
                    result = mOperandContainer.get(mOperandContainer.size() - 2).divide(mOperandContainer.get(mOperandContainer.size() - 1), 8, RoundingMode.HALF_UP).stripTrailingZeros();
                    break;
            }

            //handles =
            if (operator == '=') {
                mSmallText = "";
                showTextSmall();
                mBigText = result.stripTrailingZeros().toPlainString();
                showTextBig();
                mOperatorContainer.clear();
                mOperandContainer.clear();
                return;
            }

            mOperandContainer.add(result);
            mSmallText = mSmallText + " " + mBigText + " " + operator;
            showTextSmall();
            mBigText = result.stripTrailingZeros().toPlainString();
            showTextBig();
            mChecker = true;
        }
    }
}