package com.briroz.bentleybookswap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSell1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSell1 = findViewById(R.id.buttonSell);
        buttonSell1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getApplicationContext(),SellBookActivity.class);
        startActivity(i);
        //setContentView(R.layout.add_item_activity);
    }
}
