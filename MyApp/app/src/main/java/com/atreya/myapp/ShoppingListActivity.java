package com.atreya.myapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static com.atreya.myapp.SharedPref.REMIND_ME_SHARED_PREF;

public class ShoppingListActivity extends AppCompatActivity {

    private ListView mShoppingList;
    private EditText mItemEdit;
    private Button mAddButton;
    private ArrayAdapter<String> mAdapter;
    public  static int listNo=0;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
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

        mShoppingList = (ListView) findViewById(R.id.shopping_listView);
        mItemEdit = (EditText) findViewById(R.id.item_editText);
        mAddButton = (Button) findViewById(R.id.add_button);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mShoppingList.setAdapter(mAdapter);
        sharedPreferences = getSharedPreferences(REMIND_ME_SHARED_PREF, 0);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = mItemEdit.getText().toString();
                mAdapter.add(item);
                mAdapter.notifyDataSetChanged();
                mItemEdit.setText("");
            }
        });
        getData();
    }

    private void getData() {
        String values=sharedPreferences.getString("listItem"+listNo,"no");
        if (!values.equals("no"))
        {
            String strArray[] = values.split(",");
            for (int i=0;i<strArray.length;i++)
            { Log.e("array","  "+strArray[i]);
                mAdapter.add(strArray[i]);
            }

        }

    }

    @Override
    public void onBackPressed() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < mAdapter.getCount(); ++i){
            // This assumes you only have the list items in the SharedPreferences.
            list.add(mAdapter.getItem(i));
        }

        Log.e("String."," "+String.valueOf(list));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("listItem"+listNo, String.valueOf(list));
        editor.commit();
        finish();
    }
}