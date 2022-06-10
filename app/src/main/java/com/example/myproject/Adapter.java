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
        View view = mLayoutInflater.inflate(R.layout.listlayout, null);

        TextView foodname = (TextView) view.findViewById(R.id.list_title);
        TextView foodattrib = (TextView) view.findViewById(R.id.list_attrib);
        TextView foodcontent2 = (TextView) view.findViewById(R.id.list_content2);

        foodname.setText("상품명: " + Data.get(position).getItemName() + " | 제품 번호: " + Data.get(position).getItemId());
        foodattrib.setText("상품 가격: " + Integer.toString(Data.get(position).getItemPrice()) + "원 | 할인된 가격: " + Integer.toString(Data.get(position).getItemDiscountPrice()) + "원");
        foodcontent2.setText("유통기한이 " + Integer.toString(Data.get(position).getItemExpirationDate()) + "일 남았습니다! | " + "제조일자: " + Data.get(position).getItemDeliveryDate());
        return view;
    }
}
