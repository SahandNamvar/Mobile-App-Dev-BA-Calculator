package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewDrinksActivity extends AppCompatActivity {
    TextView textViewDrinksCount, textViewDrinkSize, textViewDrinkAlcoholPercent, textViewDrinkAddedOn;
    ImageView imageViewPrevious, imageViewNext, imageViewDelete;
    ArrayList<Drink> drinks = new ArrayList<>();
    int current_index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drinks);

        textViewDrinksCount = findViewById(R.id.textViewDrinksCount);
        textViewDrinkSize = findViewById(R.id.textViewDrinkSize);
        textViewDrinkAlcoholPercent = findViewById(R.id.textViewDrinkAlcoholPercent);
        textViewDrinkAddedOn = findViewById(R.id.textViewDrinkAddedOn);
        imageViewPrevious = findViewById(R.id.imageViewPrevious);
        imageViewNext = findViewById(R.id.imageViewNext);
        imageViewDelete = findViewById(R.id.imageViewDelete);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.DRINKS_LIST_KEY)) {
            drinks = (ArrayList<Drink>) getIntent().getSerializableExtra(MainActivity.DRINKS_LIST_KEY);
            int current_index = 0;
            displayCurrentDrink();
        }

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.DRINKS_LIST_KEY, drinks);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_index == drinks.size() -1 ) {
                    current_index = 0;
                } else {
                    current_index = current_index + 1;
                }
                displayCurrentDrink();
            }
        });

        imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_index == 0) {
                    current_index = drinks.size() - 1;
                } else {
                    current_index = current_index - 1;
                }
                displayCurrentDrink();
            }
        });

        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drink drink = drinks.get(current_index);
                drinks.remove(drink);

                if (drinks.size() == 0) {
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.DRINKS_LIST_KEY, drinks);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (current_index == 0) {
                        current_index = drinks.size() - 1;
                    } else {
                        current_index = current_index -1;
                    }
                    displayCurrentDrink();
                }
            }
        });
    }

    private void displayCurrentDrink(){
        Drink drink = drinks.get(current_index);
        textViewDrinksCount.setText("Drink " + (current_index+1) + " Out of " + drinks.size());
        textViewDrinkSize.setText(drink.getDrink_size() + " oz");
        textViewDrinkAlcoholPercent.setText(drink.getAlcohol_percentage() + "% Alcohol");
        textViewDrinkAddedOn.setText(drink.getAddedOn().toString());
    }
}