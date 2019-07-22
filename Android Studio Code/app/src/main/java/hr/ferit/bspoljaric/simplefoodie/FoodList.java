package hr.ferit.bspoljaric.simplefoodie;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;



import hr.ferit.bspoljaric.simplefoodie.Database.Database;
import hr.ferit.bspoljaric.simplefoodie.Interface.ItemClickListener;

import hr.ferit.bspoljaric.simplefoodie.Model.Food;
import hr.ferit.bspoljaric.simplefoodie.ViewHolder.FoodViewHolder;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String CategoryId = "";

    Database localDB;

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");
        localDB = new Database(this);

        recyclerView= findViewById(R.id.recyclerList);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent() != null){
            CategoryId = getIntent().getStringExtra("CategoryId");
        }
        if (!CategoryId.isEmpty() && CategoryId != null){
            loadFoodList(CategoryId);
        }
    }

    private void loadFoodList(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.food_item,FoodViewHolder.class,foodList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(final FoodViewHolder viewHolder, final Food model, final int position) {
                viewHolder.foodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.foodImage);

                if (localDB.isFavorite(adapter.getRef(position).getKey())){
                    viewHolder.favImage.setImageResource(R.drawable.ic_favorite_black_24dp);

                }

                viewHolder.favImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!localDB.isFavorite(adapter.getRef(position).getKey())){

                            localDB.addToFavorites(adapter.getRef(position).getKey());
                            viewHolder.favImage.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Toast.makeText(FoodList.this, ""+model.getName()+" added to Favorites!", Toast.LENGTH_SHORT).show();

                        }
                        else{

                            localDB.removeFromFavorites(adapter.getRef(position).getKey());
                            viewHolder.favImage.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            Toast.makeText(FoodList.this, ""+model.getName()+" removed from Favorites!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(FoodList.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
    recyclerView.setAdapter(adapter);
    }
}
