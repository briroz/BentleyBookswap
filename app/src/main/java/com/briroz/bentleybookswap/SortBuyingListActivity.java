package com.briroz.bentleybookswap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SortBuyingListActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonClassSort1;
    private Button buttonTitleSort1;
    private RelativeLayout layout;
    private ImageView image;
    private ImageView image2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_buying_list_activity);

        layout = (RelativeLayout)findViewById(R.id.layout);
        image = (ImageView) findViewById(R.id.img);
        image.setImageResource(R.drawable.falcon);
        image2 = (ImageView) findViewById(R.id.img2);
        image2.setImageResource(R.drawable.books);

        // Load the appropriate animation
        Animation an =  AnimationUtils.loadAnimation(this, R.anim.animation);
        layout.startAnimation(an);

        ActionBar actionBar = getSupportActionBar();  // Adds back button to list view
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        buttonClassSort1 = findViewById(R.id.buttonClassSort);
        buttonClassSort1.setOnClickListener(this);
        buttonTitleSort1 = findViewById(R.id.buttonTitleSort);
        buttonTitleSort1.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonClassSort:
                Intent i1 = new Intent(getApplicationContext(),BookListActivityByClass.class);
                startActivity(i1);
                break;
            case R.id.buttonTitleSort:
                Intent i2 = new Intent(getApplicationContext(),BookListActivityByTitle.class);
                startActivity(i2);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemID = item.getItemId();  //get id of menu item picked
        if (itemID == android.R.id.home) {
            this.finish();  // Go back
        }
        return false;
    }

}
