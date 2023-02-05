package com.example.uttampujari;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    LayoutInflater inflater;
    List<Model> models;
    Context context;

    public Adapter(Context context, List<Model>models){
        this.inflater = LayoutInflater.from(context);
        this.models = models;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recview,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Model temp = models.get(position);

        holder.name.setText(models.get(position).getFIRST_NAME()+" "+models.get(position).getLAST_NAME());
        holder.email.setText(models.get(position).getEMAIL());

        Glide.with(context)
                .load(models.get(position).getIMAGE())
                .into(holder.imageView);
        int id = models.get(position).getID();
        holder.itemView.setTag(id);
       // holder.imageView.setText(models.get(position).getEMAIL());

    }

    @Override
    public int getItemCount() {
        return models.size();

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,email;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);;
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            imageView = itemView.findViewById(R.id.imageView);


        }
    }
}
