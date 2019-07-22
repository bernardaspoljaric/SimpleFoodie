package hr.ferit.bspoljaric.simplefoodie.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hr.ferit.bspoljaric.simplefoodie.Interface.ItemClickListener;
import hr.ferit.bspoljaric.simplefoodie.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView foodName;
    public ImageView foodImage,favImage;

    private ItemClickListener itemClickListener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        foodName = (TextView)itemView.findViewById(R.id.food_name);
        foodImage = (ImageView)itemView.findViewById(R.id.food_image);
        favImage = (ImageView)itemView.findViewById(R.id.fav);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
