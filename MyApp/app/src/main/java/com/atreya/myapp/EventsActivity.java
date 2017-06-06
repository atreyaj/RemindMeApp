package com.atreya.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;

import static com.atreya.myapp.R.id.calendarView;
import static com.atreya.myapp.SharedPref.REMIND_ME_SHARED_PREF;

public class EventsActivity extends NavigationBaseActvity implements View.OnClickListener, OnDateSelectedListener {
    MaterialCalendarView c;SharedPreferences sharedPreferences;
    FloatingActionButton addFAB;
    TextView eventtxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(REMIND_ME_SHARED_PREF, 0);
        setContentView(R.layout.activity_events);
        c=(MaterialCalendarView)findViewById(calendarView);
        c.setOnDateChangedListener(this);
        addFAB=(FloatingActionButton)findViewById(R.id.addFAB);
        addFAB.setOnClickListener(this);
        eventtxt=(TextView)findViewById(R.id.eventtxt);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addFAB:{
                callAddEvent();
            }
        }

    }

    private void callAddEvent() {

        EventsActivity.AddSubCatDialogClass cdd = new EventsActivity.AddSubCatDialogClass(EventsActivity.this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.setCanceledOnTouchOutside(false);
        cdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cdd.show();

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        String sel= String.valueOf(c.getSelectedDate());
//        Toast.makeText(EventsActivity.this,sel,Toast.LENGTH_SHORT).show();
        searchInSaved(sel);
    }

    private void searchInSaved(String sel) {
        String result=sharedPreferences.getString(sel, "No");

        if (!result.equals("No"))
        {
            eventtxt.setText(result);
        }
        else
        {
            eventtxt.setText("No Event");
        }
    }

    public class AddSubCatDialogClass  extends Dialog implements View.OnClickListener, OnDateSelectedListener {
        ArrayList<String> cat = new ArrayList<>();
        public Activity c;
        Spinner spinner;String newSubCatval;
        MaterialCalendarView cal;
        public Dialog d; String dateSel="";
        EditText subCatName;
        SharedPreferences sharedPreferences;
        public Button ok, cancel;
        private ProgressDialog progress;

        public AddSubCatDialogClass(Context context) {
            super(context);
        }


        public AddSubCatDialogClass(Activity a) {
            super(a);
            this.c = a;
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_sub_cat);
            cal=(MaterialCalendarView)findViewById(R.id.cal);
            subCatName = (EditText) findViewById(R.id.subCatName);

            ok = (Button) findViewById(R.id.btnOK);

            cancel = (Button) findViewById(R.id.btnCancel);
            cal.setOnDateChangedListener(this);

            ok.setOnClickListener(this);
            cancel.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnOK:
                    newSubCatval=subCatName.getText().toString();
                    sharedPreferences = getSharedPreferences(REMIND_ME_SHARED_PREF, 0);
                    SharedPreferences.Editor sEdit = sharedPreferences.edit();
                    sEdit.putString(dateSel,newSubCatval);
                    sEdit.commit();

                    break;

                case R.id.btnCancel:
                    dismiss();
                    break;
                default:

                    break;

            }
            dismiss();
        }

        private void createdialog(String s) {
            new AlertDialog.Builder(getContext())

                    .setMessage(s)
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            dateSel= String.valueOf(cal.getSelectedDate());
        }
    }
}

//OnDateSelectedListener
