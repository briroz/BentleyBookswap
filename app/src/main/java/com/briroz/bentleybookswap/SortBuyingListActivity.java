package com.briroz.bentleybookswap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SortBuyingListActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonClassSort1;
    private Button buttonTitleSort1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_buying_list_activity);
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
}
