package com.example.hw02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textViewNoDrinks, textViewBACLevel, textViewStatus, textViewWeightGender;
    View viewStatus;
    Button buttonAddDrink;

    Profile profile;

    ArrayList<Drink> drinks = new ArrayList<>();

    public static final String DRINKS_LIST_KEY = "Drinks_List_Key";

    private ActivityResultLauncher<Intent> startSetProfileActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null &&
                            result.getData().getSerializableExtra(SetProfileActivity.PROFILE_KEY) != null) {
                        profile = (Profile) result.getData().getSerializableExtra(SetProfileActivity.PROFILE_KEY);
                        textViewWeightGender.setText(profile.getWeight() + " lbs (" + profile.getGender() + ")");
                        buttonAddDrink.setEnabled(true);
                    } else {
                        profile = null;
                        textViewWeightGender.setText("N/A");
                        // TODO: clear the list of drinks and bring back the UI to reset state
                        drinks.clear();
                        Calculate_BAC_Info();
                        buttonAddDrink.setEnabled(false);
                    }
                }
            });

    private ActivityResultLauncher<Intent> startAddDrinkActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null &&
                        result.getData().getSerializableExtra(AddDrinkActivity.DRINK_KEY) != null) {
                            Drink drink = (Drink) result.getData().getSerializableExtra(AddDrinkActivity.DRINK_KEY);
                            drinks.add(drink);
                            Calculate_BAC_Info();
                    } else {

                    }
                }
            });

    private ActivityResultLauncher<Intent> startViewDrinksActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        drinks = (ArrayList<Drink>) result.getData().getSerializableExtra(DRINKS_LIST_KEY);
                        Calculate_BAC_Info();
                    } else {

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewNoDrinks = findViewById(R.id.textViewNoDrinks);
        textViewBACLevel = findViewById(R.id.textViewBACLevel);
        textViewStatus = findViewById(R.id.textViewStatus);
        textViewWeightGender = findViewById(R.id.textViewWeightGender);
        viewStatus = findViewById(R.id.viewStatus);
        buttonAddDrink = findViewById(R.id.buttonAddDrink);
        buttonAddDrink.setEnabled(false);



        findViewById(R.id.buttonSetProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetProfileActivity.class);
                startSetProfileActivityForResult.launch(intent);
            }
        });

        findViewById(R.id.buttonViewDrinks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drinks.size() > 0) {
                    Intent intent = new Intent(MainActivity.this, ViewDrinksActivity.class);
                    intent.putExtra(DRINKS_LIST_KEY, drinks);
                    startViewDrinksActivityForResult.launch(intent);
                } else {
                    Toast.makeText(MainActivity.this, "You have no drinks to view!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.buttonAddDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDrinkActivity.class);
                startAddDrinkActivityForResult.launch(intent);
            }
        });

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinks.clear();
                profile = null;
                textViewWeightGender.setText("N/A");
                Calculate_BAC_Info();
                buttonAddDrink.setEnabled(false);
            }
        });
    }

    private void Calculate_BAC_Info() {

        if (drinks.size() == 0) {
            textViewBACLevel.setText("BAC Level: 0.000");
            textViewStatus.setText("You're safe!");
            textViewNoDrinks.setText("# drinks: " + drinks.size());
            viewStatus.setBackgroundResource(R.color.safe_color);
        } else {
            double valueA = 0.0;
            double valueR = 0.66;
            if (profile.getGender() == "Male") {
                valueR = 0.73;
            }

            for (Drink drink: drinks) {
                valueA = valueA + drink.getAlcohol_percentage() * drink.getDrink_size() / 100.0;
            }

            double bac = valueA * 5.14/(profile.getWeight() * valueR);

            textViewBACLevel.setText("BAC Level: " + bac);
            textViewNoDrinks.setText("# drinks: " + drinks.size());

            if (bac <= 0.08) {
                textViewStatus.setText("You're safe!");
                viewStatus.setBackgroundResource(R.color.safe_color);
            } else if (bac <= 0.2) {
                textViewStatus.setText("Be Careful!");
                viewStatus.setBackgroundResource(R.color.becareful_color);
            } else {
                textViewStatus.setText("Over Limit!");
                viewStatus.setBackgroundResource(R.color.overlimit_color);
            }
        }

    }
}
