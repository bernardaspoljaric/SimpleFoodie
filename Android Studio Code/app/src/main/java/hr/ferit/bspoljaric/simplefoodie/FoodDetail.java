package hr.ferit.bspoljaric.simplefoodie;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import hr.ferit.bspoljaric.simplefoodie.Database.Database;
import hr.ferit.bspoljaric.simplefoodie.Model.Food;
import hr.ferit.bspoljaric.simplefoodie.ViewHolder.FoodViewHolder;

public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_ingredients, food_preparation;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foodDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        foodDetail = database.getReference("Food");

        food_name = findViewById(R.id.food_name);
        food_ingredients = findViewById(R.id.food_ingredients);
        food_preparation = findViewById(R.id.food_preparation);
        food_image = findViewById(R.id.img_food);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);

        if (getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty()){

            getDetailFood(foodId);
        }

    }

    private void getDetailFood(String foodId) {
        foodDetail.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(food.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(food.getName());
                food_name.setText(food.getName());
                food_ingredients.setText(food.getIngredients());
                food_preparation.setText(food.getPreparation());
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
