package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter extends BaseAdapter{

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ModelItems> Data;

    public Adapter(Context context, ArrayList<ModelItems> data) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        Data = data;
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ModelItems getItem(int position) {
        return Data.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listlayout2, null);

        TextView product_name2 = (TextView) view.findViewById(R.id.text_product_name2);
        TextView product_number2 = (TextView) view.findViewById(R.id.text_product_number2);
        TextView product_price2 = (TextView) view.findViewById(R.id.text_product_price2);
        TextView product_discount = (TextView) view.findViewById(R.id.text_product_discount);
        TextView product_manu = (TextView) view.findViewById(R.id.text_product_manu);
        TextView product_expired = (TextView) view.findViewById(R.id.text_product_expired);

        product_name2.setText("  상품명: " + Data.get(position).getItemName());
        product_number2.setText("  상품 번호: " + Data.get(position).getItemId());
        product_price2.setText("  상품 가격: " + Integer.toString(Data.get(position).getItemPrice()) + "원");
        product_discount.setText("  할인된 가격: " + Integer.toString(Data.get(position).getItemDiscountPrice()) + "원");
        product_manu.setText("  제조일자: " + Data.get(position).getItemDeliveryDate());
        product_expired.setText("  유통기한: " + Integer.toString(Data.get(position).getItemExpirationDate()) + "일 남음");
        return view;
    }
}
