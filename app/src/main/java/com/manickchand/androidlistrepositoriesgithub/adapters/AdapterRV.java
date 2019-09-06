package com.manickchand.androidlistrepositoriesgithub.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manickchand.androidlistrepositoriesgithub.R;
import com.manickchand.androidlistrepositoriesgithub.interfaces.RecyclerViewOnClickListenerHack;
import com.manickchand.androidlistrepositoriesgithub.model.Item;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterRV extends RecyclerView.Adapter<AdapterRV.MyViewHolder> {

    private List<Item> mlist;
    private LayoutInflater mlayoutInflater;
    private RecyclerViewOnClickListenerHack mReciclerViewOnClickListenerHack;
    private View view;

    public AdapterRV(Context context, List<Item> list) {
        this.mlist = list;
        this.mlayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = mlayoutInflater.inflate(R.layout.item_row, parent, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_repo_name.setText(mlist.get(position).getName());
        holder.tv_repo_description.setText(mlist.get(position).getDescription());
        holder.tv_repo_lang.setText(mlist.get(position).getLanguage());
        holder.tv_start_count.setText(String.valueOf(mlist.get(position).getStargazersCount()));

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setReciclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mReciclerViewOnClickListenerHack = r;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_repo_name, tv_repo_description, tv_repo_lang, tv_start_count ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_repo_name = itemView.findViewById(R.id.tv_repo_name);

            tv_repo_description = itemView.findViewById(R.id.tv_repo_description);
            tv_repo_lang = itemView.findViewById(R.id.tv_repo_lang);
            tv_start_count = itemView.findViewById(R.id.tv_start_count);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mReciclerViewOnClickListenerHack != null) {
                mReciclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }
}
