package com.atreya.myapp.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atreya.myapp.DataClasses.BeanClass;
import com.atreya.myapp.R;

import java.util.ArrayList;

/**
 * Created by atreya on 2/6/17.
 */

public class ShoppingListAdapter  extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    Context context;

    public static ArrayList<String> products=new ArrayList<>();
    ArrayList<BeanClass> duplicate = null;

    public ShoppingListAdapter(Context context, ArrayList<String> products) {
        this.context=context;
        this.products=products;

    }


    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.shopping_list_items, parent, false);

//        int height = parent.getMeasuredHeight() / 4;
//        view1.setMinimumHeight(height);
        return new ShoppingListAdapter.ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.ViewHolder holder, int position) {

        holder.listName.setText(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView listName,todo_name;

        public ViewHolder(View view) {
            super(view);

            listName = (TextView) view.findViewById(R.id.listName);


        }
    }

    public void clear() {
        products.clear();
        notifyDataSetChanged();
    }

}

