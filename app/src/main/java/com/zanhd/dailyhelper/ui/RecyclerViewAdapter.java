package com.zanhd.dailyhelper.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.zanhd.dailyhelper.R;
import com.zanhd.dailyhelper.model.DailyWorker;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<DailyWorker> dailyWorkerList;

    public RecyclerViewAdapter(Context context, List<DailyWorker> dailyWorkerList) {
        this.context = context;
        this.dailyWorkerList = dailyWorkerList;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_customer,viewGroup,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        DailyWorker dailyWorker = dailyWorkerList.get(position);
        viewHolder.titleView.setText(dailyWorker.getName());
        viewHolder.detailsView.setText(MessageFormat.format("Contact : {0}", dailyWorker.getPhoneNumber()));
    }

    @Override
    public int getItemCount() {
        return dailyWorkerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        public TextView titleView;
        public TextView detailsView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.name_recycler_view);
            detailsView = itemView.findViewById(R.id.phonenumber_recycler_view);
        }

        @Override
        public void onClick(View view) {
            Snackbar.make(view,"Clicked",Snackbar.LENGTH_SHORT).show();
        }
    }
}
