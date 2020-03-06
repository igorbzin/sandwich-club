package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView tvAlsoKnownAs;
    private TextView tvDescription;
    private TextView tvOrigin;
    private TextView tvIngredients;
    private Sandwich sandwich;
    private TextView tvName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        tvDescription = findViewById(R.id.description_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvOrigin = findViewById(R.id.origin_tv);
        tvName = findViewById(R.id.tv_name);
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        StringBuilder alsoKnownAs = new StringBuilder();
        for (String alias : sandwich.getAlsoKnownAs()) {
            alsoKnownAs.append(alias).append(", ");
        }
        if(alsoKnownAs.length() == 0){
            tvAlsoKnownAs.setText("No aliases");
        } else {
            tvAlsoKnownAs.setText(alsoKnownAs.toString());
        }
        tvDescription.setText(sandwich.getDescription());
        tvOrigin.setText(sandwich.getPlaceOfOrigin());
        String ingredients = "";
        for (String ingredient : sandwich.getIngredients()) {
            ingredients += ingredient + ", ";
        }
        tvName.setText(sandwich.getMainName());
        tvIngredients.setText(ingredients);
    }
}
