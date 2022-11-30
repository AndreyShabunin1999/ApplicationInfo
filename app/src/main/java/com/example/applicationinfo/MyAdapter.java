package com.example.applicationinfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private static ArrayList<ModelApp> arrayListApp;

    public MyAdapter(Context context, ArrayList<ModelApp> arrayListApp){
        this.context = context;
        this.arrayListApp = arrayListApp;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mTextView.setText(arrayListApp.get(position).getTitles());
        Glide.with(context)
                //.load(images.get(position))
                .load(arrayListApp.get(position).getmImages())
                .error(R.drawable.icon)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return arrayListApp.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mTextView;
        Context context = itemView.getContext();


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageview);
            mTextView = itemView.findViewById(R.id.textview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    for(int i = 0; i < arrayListApp.size(); i++){
                        if(arrayListApp.get(i).getTitles().equals(mTextView.getText().toString())){
                            Intent intent = new Intent(context, InfoApplication.class);
                            intent.putExtra("index", i);
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }

}
