package com.example.apporderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apporderfood.R;
import com.example.apporderfood.database.DatabaseHandler;
import com.example.apporderfood.model.Food;

import org.w3c.dom.Text;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder>{
    List<Food> foodList;
    String TAG = "";
    private TextView totalMoney;
    private TextView numberFoodInCart;
    int total;
    int pos = -1; //tạo biến lấy vị trí

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public FoodAdapter(List<Food> foodList, TextView total, TextView numberFoodInCart) {
        this.foodList = foodList;
        this.totalMoney = total;
        this.numberFoodInCart = numberFoodInCart;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_food_cart_fragment, parent, false);
        return new FoodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Food food = foodList.get(position);
        holder.imageView.setImageResource(food.getImage());
        holder.foodName.setText(food.getFoodName());
        holder.money.setText("$"+food.getMoney());
        holder.quantity.setText(String.valueOf(food.getQuantity()));
        holder.add.setOnClickListener(v->{
            DatabaseHandler db = new DatabaseHandler(context);
            food.setQuantity(food.getQuantity() + 1);
            holder.quantity.setText(food.getQuantity() + "");
            db.updateFoodQuantity(food);
            total = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                foodList.forEach(e -> total += e.getMoney()*e.getQuantity());
            }
            totalMoney.setText("$" + total);
        });
        holder.minus.setOnClickListener(v->{
            DatabaseHandler db = new DatabaseHandler(context);
            if (food.getQuantity() > 1)
                food.setQuantity(food.getQuantity() - 1);
            holder.quantity.setText(food.getQuantity() + "");
            db.updateFoodQuantity(food);
            total = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                foodList.forEach(e -> total += e.getMoney()*e.getQuantity());
            }
            totalMoney.setText("$" + total);
        });
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(context);
                db.deleteFood(food);
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onItemClick(position);
                }
                foodList.remove(position);
                numberFoodInCart.setText(String.valueOf(foodList.size()));
                total = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    foodList.forEach(t -> total += t.getMoney()*t.getQuantity());
                }
                totalMoney.setText("$" + total);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
