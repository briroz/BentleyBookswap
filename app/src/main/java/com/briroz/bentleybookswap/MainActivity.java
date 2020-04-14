package com.briroz.bentleybookswap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSell1;
    private Button buttonBuy1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSell1 = findViewById(R.id.buttonSell);
        buttonSell1.setOnClickListener(this);
        buttonBuy1 = findViewById(R.id.buttonBuy);
        buttonBuy1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBuy:
                Intent i1 = new Intent(getApplicationContext(),SortBuyingListActivity.class);
                startActivity(i1);
                break;
            case R.id.buttonSell:
                Intent i2 = new Intent(getApplicationContext(),SellBookActivity.class);
                startActivity(i2);
        }

    }
}
