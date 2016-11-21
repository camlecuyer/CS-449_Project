package com.umkc.cj.ar_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// Class attached to initial activity

public class Main extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializes database
        Data sql = new Data(this);

        // sets listener for standard view button
        View normalView = findViewById(R.id.normalView_button);
        normalView.setOnClickListener(this);

        // sets listener for AR view button
        View ARView = findViewById(R.id.ARView_button);
        ARView.setOnClickListener(this);
    } // end onCreate

    @Override
    public void onClick(View v)
    {
        // switches based on the id of the button clicked
        switch (v.getId())
        {
            case R.id.normalView_button:
            {
                // create new intent
                Intent intent = new Intent(this, ListSelector.class);

                // launches the List Selector activity
                startActivity(intent);
                break;
            } // end case normalView_button
            case R.id.ARView_button:
            {
                // create new intent
                Intent intent = new Intent(this, AR_List.class);

                // launches the List Selector activity
                startActivity(intent);
                break;
            } // end case ARView_button
        } // end switch
    } // end OnClick
} // end main
