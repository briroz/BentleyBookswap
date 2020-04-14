package com.briroz.bentleybookswap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SortBuyingListActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_buying_list_activity);
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
