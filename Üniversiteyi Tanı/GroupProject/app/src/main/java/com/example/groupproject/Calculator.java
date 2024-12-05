package com.example.groupproject;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.*;

public class Calculator extends AppCompatActivity {

    EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        display = findViewById(R.id.display);
        display.setShowSoftInputOnFocus(false);


        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getString(R.string.tap_here).equals(display.getText().toString())) {

                            display.setText("");
                }

            }
        });

    }

    public void anyButton(View view) { //Seçilen butonlara göre işlem yapar

        switch (view.getId()){
            case R.id.btn_C:display.setText("0"); break;
            case R.id.btn_brackets:addBracets();break;
            case R.id.btn_exponent: updateDispaly("^");break;
            case R.id.btn_mul: updateDispaly("*");break;
            case R.id.btn_add: updateDispaly("+");break;
            case R.id.btn_div: updateDispaly("/");break;
            case R.id.btn_result: result();break;
            case R.id.btn_sub: updateDispaly("-");break;
            case R.id.btn_zero: updateDispaly("0");break;
            case R.id.btn_one: updateDispaly("1");break;
            case R.id.btn_two: updateDispaly("2");break;
            case R.id.btn_three: updateDispaly("3");break;
            case R.id.btn_four: updateDispaly("4");break;
            case R.id.btn_five: updateDispaly("5");break;
            case R.id.btn_six: updateDispaly("6");break;
            case R.id.btn_seven: updateDispaly("7");break;
            case R.id.btn_eight: updateDispaly("8");break;
            case R.id.btn_nine: updateDispaly("9");break;
            case R.id.btn_dot: updateDispaly(".");break;
            case R.id.btn_reverse: reverse();break;

            //updateDispaly metoduna belirtilen değerleri gönderir
        }
    }


    private void reverse() { //Cursor'un bulunduğu yerden her basıldığında bir karakter siler(sağdan)

        int cursor_pos = display.getSelectionStart();

        if (cursor_pos>0){
            String oldDisplay = display.getText().toString();
            String leftSideOnDisplay = oldDisplay.substring(0,cursor_pos-1);
            String rightSightOnDisplay = oldDisplay.substring(cursor_pos);
            String newText = leftSideOnDisplay+rightSightOnDisplay;
            display.setText(newText);
            display.setSelection(cursor_pos-1);
        }

    }

    private void result() { //"=" butonuna basıldığında sonucu ekrana yazar

        String textDisplay = display.getText().toString();
        String reTextDisplay = textDisplay.replaceAll("/","/");
        reTextDisplay = reTextDisplay.replaceAll("x","*");
        Expression exp = new Expression(reTextDisplay);
        String result = String.valueOf(exp.calculate()).toString();

        if (!result.equals("NaN")){
            display.setText(result);
            display.setSelection(result.length());//cursoru en saga atar...
        } else{
                showToast("Wrong value!!!");
        }
    }

    private void updateDispaly(String s) {
        //gönderilen parametreyi Cursor'un bulunduğu yere ekleyerek ekranı günceller(sağdan ekleme yapar)
        int cursor_pos = display.getSelectionStart();

        if (getString(R.string.tap_here).equals(display.getText().toString())) {

            display.setText(s);
        } else {
            String oldDisplay = display.getText().toString();
            String leftSideOnDisplay = oldDisplay.substring(0,cursor_pos);
            String rightSightOnDisplay = oldDisplay.substring(cursor_pos);
            String newText = leftSideOnDisplay+s+rightSightOnDisplay;
            display.setText(newText);
        }
        display.setSelection(cursor_pos+1);
    }


    private void addBracets() { // '(' ve ')' ekler

        String textDisplay = display.getText().toString();
      //  int cursor_pos = display.getSelectionStart();
        int countBracets = 0;

        for (int i = 0; i < textDisplay.length();i++){
            if (textDisplay.substring(i,i+1).equalsIgnoreCase("(")) countBracets++;
            if (textDisplay.substring(i,i+1).equalsIgnoreCase(")")) countBracets--;
        }
        String lastCharOfTextDisplay = textDisplay.substring(textDisplay.length()-1);
        if (countBracets==0 || lastCharOfTextDisplay.equals("(")) updateDispaly("(");
        else if (countBracets>0 && !lastCharOfTextDisplay.equals(")")) updateDispaly(")");

    }

    public void showToast(String text){ //tespit edilen hataları ekrana mesaj olarak verir
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup) findViewById(R.id.toast_layout));
        Toast toast = new Toast(getApplicationContext());
        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(text);

        toast.setGravity(Gravity.CENTER,0,-200);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public void clcSin(View v) { //ekrandaki sayının Sin'ini hesaplar
        display.setText("sin(" + display.getText().toString() + ")");
        result();
    }


    public void clcCos(View v) { //ekrandaki sayının Cos'unu hesaplar
        display.setText("cos(" + display.getText().toString() + ")");
        result();
    }
}