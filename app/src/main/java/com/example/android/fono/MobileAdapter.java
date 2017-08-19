package com.example.android.fono;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MobileAdapter extends RecyclerView.Adapter<MobileAdapter.MobileHolder>{
    private List<Mobile> mobileList;
   Context mContext;

    public MobileAdapter(Context mContext,List<Mobile> mobileList){
        this.mContext=mContext;
        this.mobileList=mobileList;
    }

    @Override
    public MobileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mobile,parent,false);
        return new MobileHolder(v);
    }

    @Override
    public void onBindViewHolder(MobileHolder holder,final int position) {
        final Mobile mobile=mobileList.get(position);
       holder.deviceNameTxt.setText(mobile.getDeviceName());
        /////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(mContext,"Hello Intent",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(mContext,DetailsActivity.class);
                intent.putExtra("mobile",mobileList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mobileList.size();
    }

    class MobileHolder extends RecyclerView.ViewHolder{
        private TextView deviceNameTxt;
       // private LinearLayout layout;

        public MobileHolder(View itemView) {
            super(itemView);
            deviceNameTxt=itemView.findViewById(R.id.deviceNameTxtId);
           // layout = itemView.findViewById(R.id.root);
        }
    }
}
