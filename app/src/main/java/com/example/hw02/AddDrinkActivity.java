package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddDrinkActivity extends AppCompatActivity {
    RadioGroup radioGroupDrinkSize;
    TextView textViewAlcoholPercentage;
    SeekBar seekBarAlcohol;

    Drink drink;

    public static final String DRINK_KEY = "Drink_Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        radioGroupDrinkSize = findViewById(R.id.radioGroupDrinkSize);
        textViewAlcoholPercentage = findViewById(R.id.textViewAlcoholPercentage);
        seekBarAlcohol = findViewById(R.id.seekBarAlcohol);

        seekBarAlcohol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewAlcoholPercentage.setText(String.valueOf(i) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.buttonAddDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int alcohol_percentage = seekBarAlcohol.getProgress();
                int drink_size = 1;
                if (radioGroupDrinkSize.getCheckedRadioButtonId() == R.id.radioButton5oz) {
                    drink_size = 5;
                } else if (radioGroupDrinkSize.getCheckedRadioButtonId() == R.id.radioButton12oz) {
                    drink_size = 12;
                }

                if (alcohol_percentage > 0) {
                    drink = new Drink(alcohol_percentage, drink_size);
                    Intent intent = new Intent();
                    intent.putExtra(DRINK_KEY, drink);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(AddDrinkActivity.this, "Alcohol should be > 0%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}