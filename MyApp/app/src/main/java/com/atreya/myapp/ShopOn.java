package com.atreya.myapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.atreya.myapp.CustomAdapters.ShoppingListAdapter;

import java.util.ArrayList;

import static com.atreya.myapp.SharedPref.REMIND_ME_SHARED_PREF;

public class ShopOn extends AppCompatActivity {
RecyclerView imgList;
    FloatingActionButton addList;
    SharedPreferences sharedPreferences;
    public static ArrayList<String> ListName = new ArrayList<>();
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_on);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initViews();
        initListners();
    }

    private void initViews() {
        imgList=(RecyclerView)findViewById(R.id.picBoxList);
        imgList.setHasFixedSize(true);

        sharedPreferences = getSharedPreferences(REMIND_ME_SHARED_PREF, 0);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        imgList.setLayoutManager(layoutManager);
        addList=(FloatingActionButton)findViewById(R.id.addList);
        addList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
               addListItem();
            }
        });
        ShoppingListAdapter adapter = new ShoppingListAdapter(ShopOn.this, ListName);

        imgList.setAdapter(adapter);
    }

    private void initListners() {
        imgList .addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(ShopOn.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);

                    Toast.makeText(ShopOn.this, ""+position, Toast.LENGTH_SHORT).show();
                    ShoppingListActivity.listNo=position;
                    Intent intent = new Intent(ShopOn.this, ShoppingListActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    public void addListItem()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Name");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 String ListNam = input.getText().toString();
                ListName.add(ListNam);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    @Override
    public void onBackPressed() {

        finish();
    }
}
