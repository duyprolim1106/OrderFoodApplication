package com.example.apporderfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    ImageButton btnBurger, btnCheese;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        btnBurger = view.findViewById(R.id.btnBurger);
        btnCheese = view.findViewById(R.id.btnCheese);

        btnBurger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentValues(R.drawable.beef_burger_biggest, getString(R.string.s48), getString(R.string.BeefBurger), getString(R.string.s20), getString(R.string.BigJuicyBurger));
            }
        });

        btnCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentValues(R.drawable.pizza_big, getString(R.string.s45), getString(R.string.CheesePizza), getString(R.string.s32), getString(R.string.PizzaDist));
            }
        });

        return view;
    }

    public void IntentValues(int idImage, String rate, String title, String money, String detail) {
        Intent intent = new Intent(getContext(), DetailFoodActivity.class);

        intent.putExtra("image", idImage);
        intent.putExtra("rate", rate);
        intent.putExtra("foodName", title);
        intent.putExtra("money", money);
        intent.putExtra("foodDetail", detail);
        startActivity(intent);
    }
}
