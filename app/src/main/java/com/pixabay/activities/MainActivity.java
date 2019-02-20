package com.pixabay.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pixabay.R;

public class MainActivity extends AppCompatActivity {

    private final int FUNC_BROWSE = 1;
    private String TAG = getClass().getSimpleName();
    private Button searchButton;
    private EditText inputKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputKeyword = findViewById(R.id.editText_Search);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchContents = inputKeyword.getText().toString();
                Log.d(TAG, "onClick: searchContents.length:" + searchContents.length());
                if(searchContents.length() == 0){
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main_constraint),
                            "Must to type something to search", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.YELLOW);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // ...
                        }
                    }).show();
                }
                else {
                    Intent browseIntent = new Intent(MainActivity.this, BrowserActivity.class);
                    browseIntent.putExtra("SearchContents", searchContents);
                    startActivityForResult(browseIntent, FUNC_BROWSE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
            Log.d(TAG, "onActivityResult: " + data.getStringExtra("backToMain"));
        else
            finish();
    }
}
