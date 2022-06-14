package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterArrival extends BaseAdapter{

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ModelItems> Data;

    public AdapterArrival(Context context, ArrayList<ModelItems> data) {
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
        View view = mLayoutInflater.inflate(R.layout.listlayout, null);

        TextView product_name = (TextView) view.findViewById(R.id.text_product_name);
        TextView product_number = (TextView) view.findViewById(R.id.text_product_number);
        TextView product_price = (TextView) view.findViewById(R.id.text_product_price);
        TextView product_manu = (TextView) view.findViewById(R.id.text_product_manu);
        TextView product_expired = (TextView) view.findViewById(R.id.text_product_expired);

        product_name.setText("  상품명: " + Data.get(position).getItemName());
        product_number.setText("  상품 번호: " + Data.get(position).getItemId());
        product_price.setText(" 상품 가격: " + Data.get(position).getItemPrice() + "원");
        product_manu.setText("  제조일자: " + Data.get(position).getItemDeliveryDate());
        product_expired.setText("  유통기한: " + Data.get(position).getItemExpirationDate() + "일 남음");

//        foodname.setText("상품명: " + Data.get(position).getItemName() + " | 제품 번호: " + Data.get(position).getItemId());
//        foodattrib.setText("상품 가격: " + Integer.toString(Data.get(position).getItemPrice()) + "원");
//        foodcontent2.setText("유통기한이 " + Integer.toString(Data.get(position).getItemExpirationDate()) + "일 남았습니다! | " + "제조일자: " + Data.get(position).getItemDeliveryDate());
        return view;
    }
}
