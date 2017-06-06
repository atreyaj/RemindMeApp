package com.atreya.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends NavigationBaseActvity {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onCardClick(View v)
    {Intent intent = null;
        switch (v.getId())
        {
            case R.id.cardView1:
            {
                intent = new Intent(HomeActivity.this, ToDo.class);

            }
                break;
            case R.id.cardView2:
            {
                intent = new Intent(HomeActivity.this, EventsActivity.class);

            } break;
            case R.id.cardView3:
            {
                intent = new Intent(HomeActivity.this, ShopOn.class);

            } break;
            case R.id.cardView4:
            {
                intent = new Intent(HomeActivity.this, PicBox.class);

            } break;
        }
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);

    }


}
