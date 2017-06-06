package com.atreya.myapp.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atreya.myapp.DataClasses.ToDoObj;
import com.atreya.myapp.R;

import java.util.ArrayList;

/**
 * Created by atreya on 31/5/17.
 */



public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    Context context;

    public static ArrayList<ToDoObj> products=new ArrayList<>();
    ArrayList<ToDoObj> duplicate = null;

    public ToDoAdapter(Context context, ArrayList<ToDoObj> products) {
        this.context=context;
        this.products=products;

    }


    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.to_do_list_items, parent, false);

//        int height = parent.getMeasuredHeight() / 4;
//        view1.setMinimumHeight(height);
        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(ToDoAdapter.ViewHolder holder, int position) {

        final ToDoObj obj=products.get(position);
        holder.todo_name.setText(obj.getTaskName());
        holder.todo_date.setText(obj.getTastDate());
        holder.todo_date.setText(obj.getTastDate());
        holder.detail.setText(obj.getTastDetail());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todo_date,todo_name,detail;

        public ViewHolder(View view) {
            super(view);

            todo_date = (TextView) view.findViewById(R.id.todo_date);

            todo_name = (TextView) view.findViewById(R.id.todo_name);
            detail = (TextView) view.findViewById(R.id.detailtxt);
        }
    }

    public void clear() {
        products.clear();
        notifyDataSetChanged();
    }

}


