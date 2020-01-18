package com.example.sportivo.reservationbyhour_screen;

import android.view.View;
import android.widget.TextView;

import com.example.sportivo.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ItemViewHolder extends ChildViewHolder {

    private TextView mTextView,mTextView2;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mTextView=itemView.findViewById(R.id.res_time_tv);
        mTextView2=itemView.findViewById(R.id.res_price_tv);

    }
    public void bind(Item item){
        mTextView.setText(item.teren);
        mTextView2.setText(item.price);
    }
}