package com.example.myapplication;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private mview mview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mview = findViewById(R.id.mview);
        final TextView timing = findViewById(R.id.timing);
        timing.setText(mview.timings[mview.timeNote]);
        Log.i("mainview", ",mview.timings[mview.timeNote]="+mview.timings[mview.timeNote]);
        //buttons
        Button plus = findViewById(R.id.plus);
        Button minus = findViewById(R.id.minus);
        Button button = findViewById(R.id.button);
        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mview.deleteNotes();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mview.addNotes();
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mview.timingPlus();
                timing.setText(mview.timings[mview.timeNote]);
                Log.i("mview","timningPlus done");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mview.timingMinus();
                timing.setText(mview.timings[mview.timeNote]);
                Log.i("mview","timningMinus done");
            }
        });
        //note spinner
        final String[] notes = {"散音", "泛音", "按音","绰","注","上","下", "撮","反撮","泛起", "泛止","空"};
        Spinner note = findViewById(R.id.note);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, notes);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        note.setAdapter(adapter3);
        note.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mview.leftNote = mview.notetypes[position];
                Log.i("mview","setting leftNote" + mview.notetypes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        //xians spinner
        String[] xians = {"一弦", "二弦", "三弦", "四弦", "五弦", "六弦", "七弦"};
        Spinner xian = findViewById(R.id.xian);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, xians);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        xian.setAdapter(adapter);
        xian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mview.xianNote = position;
                Log.i("mview","setting xianNote" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        //huis spinner

        final String[] huis = {"一徽", "二徽", "三徽","四徽","五徽","六徽","七徽","八徽","九徽", "十徽","十一徽", "十二徽", "十三徽"};
        final String[] huisnote = {"1", "2", "3","4","5","6","7","8","9", "10","11", "12", "13"};
        Spinner hui = findViewById(R.id.hui);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, huis);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hui.setAdapter(adapter2);
        hui.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mview.huiNote = huisnote[position];
                Log.i("mview","setting huiNote" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }
}
