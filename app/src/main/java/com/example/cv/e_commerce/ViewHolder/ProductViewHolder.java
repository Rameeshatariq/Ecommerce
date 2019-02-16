package com.example.cv.e_commerce.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cv.e_commerce.R;
import com.example.cv.e_commerce.interfaces.ItemClickListner;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(View itemView) {
        super(itemView);

        imageView=(ImageView)itemView.findViewById(R.id.product_image);
        txtProductDescription=(TextView) itemView.findViewById(R.id.product_description);
        txtProductName=(TextView) itemView.findViewById(R.id.product_name);
        txtProductPrice=(TextView) itemView.findViewById(R.id.product_price);

    }

    public void onItemClickListner(ItemClickListner listner){
this.listner=listner;
    }

    @Override
    public void onClick(View view) {
    listner.onClick(view, getAdapterPosition(), false);
    }
}
