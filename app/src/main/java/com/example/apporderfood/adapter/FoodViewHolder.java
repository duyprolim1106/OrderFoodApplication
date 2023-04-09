package com.example.apporderfood.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apporderfood.R;

public class FoodViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView foodName, money, quantity;
    ImageButton add, minus, btnRemove;

    public FoodViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageCart);
        foodName = itemView.findViewById(R.id.foodNameCart);
        money = itemView.findViewById(R.id.foodMoneyCart);
        quantity = itemView.findViewById(R.id.quantityCart);
        add = itemView.findViewById(R.id.add);
        minus = itemView.findViewById(R.id.minus);
        btnRemove = itemView.findViewById(R.id.btnRemove);
    }

}
