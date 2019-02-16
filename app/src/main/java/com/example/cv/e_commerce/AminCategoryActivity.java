package com.example.cv.e_commerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AminCategoryActivity extends AppCompatActivity {

    private ImageView tshirts, sportTShirts, femaleDresses, sweathers;
    private ImageView glasses, hats, purses, shoes;
    private ImageView headphoes, laptops, watches, mobilephones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amin_category);

        tshirts=(ImageView)findViewById(R.id.t_shirts);
        sportTShirts=(ImageView)findViewById(R.id.sport_t_shirts);
        femaleDresses=(ImageView)findViewById(R.id.female_dresses);
        sweathers=(ImageView)findViewById(R.id.sweather);

        glasses=(ImageView)findViewById(R.id.glasses);
        hats=(ImageView)findViewById(R.id.hats);
        purses=(ImageView)findViewById(R.id.purses);
        shoes=(ImageView)findViewById(R.id.shoes);

        headphoes=(ImageView)findViewById(R.id.headphones);
        laptops=(ImageView)findViewById(R.id.laptops);
        watches=(ImageView)findViewById(R.id.watches);
        mobilephones=(ImageView)findViewById(R.id.mobile);

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","tshirts");
                startActivity(intent);
            }
        });

        sportTShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Sport tShirts");
                startActivity(intent);
            }
        });
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Female Dresses");
                startActivity(intent);
            }
        });
        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Sweathers");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Glasses");
                startActivity(intent);
            }
        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Hat Caps");
                startActivity(intent);
            }
        });
        purses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Bags Purses");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);
            }
        });
        headphoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Headphones Handsfrees");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Watches");
                startActivity(intent);
            }
        });
        mobilephones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("category","Mobile Phones");
                startActivity(intent);
            }
        });

    }
}
