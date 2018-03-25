package com.example.indrapro.indra_1202152164_studycase5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

//import android.support.v7.widget.RecyclerView;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<com.example.indrapro.indra_1202152164_studycase5.Model.Activity> mActivityList;
    private LayoutInflater inflater;

//    //Membuat Konstruktor pada Class RecyclerViewAdapter
    public RecyclerAdapter(Context context, LinkedList<com.example.indrapro.indra_1202152164_studycase5.Model.Activity> mActivityList){
        inflater = LayoutInflater.from(context);
        this.mActivityList = mActivityList;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView activity, desc, priority;
        final RecyclerAdapter mAdapter;

        public ViewHolder(View itemView, RecyclerAdapter adapter) {
            super(itemView);
            //Menginisialisasi View-View untuk kita gunakan pada RecyclerView
            activity = itemView.findViewById(R.id.todo);
            desc = itemView.findViewById(R.id.desc);
            priority = itemView.findViewById(R.id.priority);
            this.mAdapter = adapter;
        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View mItemView = inflater.inflate(R.layout.activity_info, parent, false);
        return new ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        holder.activity.setText(mActivityList.get(position).getTodo());
        holder.desc.setText(mActivityList.get(position).getDescription());
        holder.priority.setText(mActivityList.get(position).getPriority());
    }

    @Override
    public int getItemCount() {
        return mActivityList.size();
    }

    public void dismissData(int data){
        DatabaseHandler db = new DatabaseHandler(inflater.getContext());
        boolean deleted = db.delete(mActivityList.get(data));
        if (deleted){
            mActivityList.remove(data);
            this.notifyItemRemoved(data);
            Toast.makeText(inflater.getContext(),"Deleted Success",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(inflater.getContext(),"Deleted Failed",Toast.LENGTH_SHORT).show();
        }
    }

    public static class SwipeHelper extends ItemTouchHelper.SimpleCallback {
        RecyclerAdapter adapter;

        public SwipeHelper(int dragDirs, int swipeDirs){
            super(dragDirs, swipeDirs);
        }

        public SwipeHelper(RecyclerAdapter adapter){
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.dismissData(viewHolder.getAdapterPosition());
        }
    }
}
