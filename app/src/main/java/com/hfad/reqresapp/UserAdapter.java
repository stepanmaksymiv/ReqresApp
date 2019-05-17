package com.hfad.reqresapp;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hfad.reqresapp.model.Data;

import java.util.List;

import static com.hfad.reqresapp.model.Data.callBack;

public class UserAdapter extends PagedListAdapter<Data, UserAdapter.MyViewHolder> {

    private Context context;
    private Listener listener;

    public UserAdapter(Context context) {
        super(Data.callBack);
        this.context = context;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener{
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data data = getItem(position);

        if (data != null) {
            holder.first_name.setText(data.getFirstName());
            holder.last_name.setText(data.getLastName());

            Glide.with(context).load(data.getAvatar()).into(holder.avatar);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView first_name, last_name;


        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            first_name = itemView.findViewById(R.id.first_name);
            last_name = itemView.findViewById(R.id.last_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
