package com.atreya.myapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.atreya.myapp.CustomAdapters.ToDoAdapter;
import com.atreya.myapp.DataClasses.ToDoObj;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ToDo extends NavigationBaseActvity implements View.OnClickListener {
    ToDoAdapter adapter;
    RecyclerView toDoList; EditText details;
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> toDo = new ArrayList<>();
FloatingActionButton addFAB;
 public  static  ArrayList<ToDoObj> toDoListArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        toDoList=(RecyclerView)findViewById(R.id.toDoList);
        toDoList.setHasFixedSize(true);
        addFAB=(FloatingActionButton)findViewById(R.id.addFAB);
        addFAB.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        toDoList.setLayoutManager(layoutManager);


         adapter= new ToDoAdapter(ToDo.this,toDoListArray);

        toDoList.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addFAB:{
                callAddTodo();
            }
        }
    }

    private void callAddTodo() {
        ToDo.AddSubCatDialogClass cdd = new ToDo.AddSubCatDialogClass(ToDo.this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        cdd.setCanceledOnTouchOutside(false);
        cdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cdd.show();


    }

    public class AddSubCatDialogClass  extends Dialog implements View.OnClickListener, OnDateSelectedListener {
        ArrayList<String> cat = new ArrayList<>();
        public Activity c;
        Spinner spinner;String newSubCatval;
        MaterialCalendarView cal;
        public Dialog d; String dateSel="";
        EditText name,dateVal;
        SharedPreferences sharedPreferences;
        public Button ok, cancel;
        private ProgressDialog progress;
        ImageView getScanner;
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
            setContentView(R.layout.addtodo);
            name = (EditText) findViewById(R.id.name);
            dateVal = (EditText) findViewById(R.id.date);
            details = (EditText) findViewById(R.id.details);

            getScanner=(ImageView)findViewById(R.id.getScanner);
            getScanner.setOnClickListener(this);

            ok = (Button) findViewById(R.id.btnOK);

            cancel = (Button) findViewById(R.id.btnCancel);


            ok.setOnClickListener(this);
            dateVal.setOnClickListener(this);
            cancel.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnOK:
                    newSubCatval=name.getText().toString();
                    String scn=details.getText().toString();
                    String dateValu=dateVal.getText().toString();

                    ToDoObj obj=new ToDoObj();

                    obj.setTaskName(newSubCatval);
                    obj.setTastDate(dateValu);
                    obj.setTastDetail(scn);
                    toDoListArray.add(obj);
                    adapter.notifyDataSetChanged();

//                    Gson gson = new Gson();
//                    String json = gson.toJson(obj);
//
//                    Log.e("string "," "+json);
//                    SharedPref.setPreference(ToDo.this,"Todo","");
                    dismiss();
                    break;

                case R.id.btnCancel:
                    dismiss();
                    break;

                case R.id.date:
                   openDateDialog();
                    break;
                case R.id.getScanner:
                   scanOCR();
                    break;
                default:
                    dismiss();
                    break;

            }

        }

        private void scanOCR() {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(ToDo.this);
            //builder.setTitle("Add Docs!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            builder.detectFileUriExposure();
                        }

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);


                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);

                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }




        private void openDateDialog() {

                DatePickerDialog mDatePicker;

                int mYear, mMonth, mDay, mHour, mMinute;
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(ToDo.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

//                            holder.txtViewvalue.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                dateVal.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                Log.e("date","" +dateVal);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap=null;

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
//                imagePath= f.getPath();

                Log.e("file=",""+f);
                String filePath = f.getAbsolutePath();
                try
                {
                    bitmap = ImageResizer.decodeSampledBitmapFromFile(f.getAbsolutePath(), 1500, 800);
                   inspectFromBitmap(bitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();

                bitmap = ImageResizer.decodeSampledBitmapFromFile(picturePath, 1500, 800);
//
//                imagePath=picturePath;
                Log.w("path of image from", picturePath + "");
                inspectFromBitmap(bitmap);


            }
        }
    }

    private void inspectFromBitmap(Bitmap bitmap) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
        try {
            if (!textRecognizer.isOperational()) {
                new AlertDialog.
                        Builder(this).
                        setMessage("Text recognizer could not be set up on your device").show();
                return;
            }

            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> origTextBlocks = textRecognizer.detect(frame);
            List<TextBlock> textBlocks = new ArrayList<>();
            for (int i = 0; i < origTextBlocks.size(); i++) {
                TextBlock textBlock = origTextBlocks.valueAt(i);
                textBlocks.add(textBlock);
            }
            Collections.sort(textBlocks, new Comparator<TextBlock>() {
                @Override
                public int compare(TextBlock o1, TextBlock o2) {
                    int diffOfTops = o1.getBoundingBox().top - o2.getBoundingBox().top;
                    int diffOfLefts = o1.getBoundingBox().left - o2.getBoundingBox().left;
                    if (diffOfTops != 0) {
                        return diffOfTops;
                    }
                    return diffOfLefts;
                }
            });

            StringBuilder detectedText = new StringBuilder();
            for (TextBlock textBlock : textBlocks) {
                if (textBlock != null && textBlock.getValue() != null) {
                    detectedText.append(textBlock.getValue());
                    detectedText.append("\n");
                }
            }


            details.setText(detectedText);
        }
        finally {
            textRecognizer.release();
        }

    }
}