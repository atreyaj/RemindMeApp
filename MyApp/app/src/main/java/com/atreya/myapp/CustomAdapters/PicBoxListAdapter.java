package com.atreya.myapp.CustomAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atreya.myapp.BirmapConversion;
import com.atreya.myapp.DataClasses.BeanClass;
import com.atreya.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atreya on 18/4/17.
 */

public class PicBoxListAdapter extends RecyclerView.Adapter<PicBoxListAdapter.ViewHolder> {

    Context context;

    public static ArrayList<BeanClass> products=new ArrayList<>();
    ArrayList<BeanClass> duplicate = null;

    public PicBoxListAdapter(Context context, ArrayList<BeanClass> products) {
        this.context=context;
        this.products=products;

    }


    @Override
    public PicBoxListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.pic_box_list_items, parent, false);

//        int height = parent.getMeasuredHeight() / 4;
//        view1.setMinimumHeight(height);
        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(PicBoxListAdapter.ViewHolder holder, int position) {

        final BeanClass obj=products.get(position);
        holder.productName.setText(obj.getValue());
        Log.e("prod name","  "+obj.getLabel());

//        String ob=obj.getValue();
        Bitmap bit= BirmapConversion.decodeToBase64(obj.getLabel());
//
        holder.productImg.setImageBitmap(bit);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView productImg;

        public ViewHolder(View view) {
            super(view);

            productName = (TextView) view.findViewById(R.id.prodctName);

            productImg = (ImageView) view.findViewById(R.id.productImg);
        }
    }

    public void clear() {
        products.clear();
        notifyDataSetChanged();
    }

    //to return values for search
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<BeanClass> results = new ArrayList<BeanClass>();
                if (duplicate == null)
                    duplicate = products;
                if (constraint != null) {
                    if (duplicate != null & duplicate.size() > 0) {
                        for ( final BeanClass g :duplicate) {
                            if (g.getValue().toLowerCase().contains(constraint.toString()))results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                products = (ArrayList<BeanClass>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}


