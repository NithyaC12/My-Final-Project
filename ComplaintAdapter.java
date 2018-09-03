package com.example.lenovo.bustracking;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.model.ComplaintModel;
import com.example.lenovo.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComplaintAdapter extends BaseAdapter {

    Context context;
    ArrayList<ComplaintModel> routeList;
    DatabaseReference mReference;
    String name="";

    public ComplaintAdapter(Context context, ArrayList<ComplaintModel> routeList) {
        this.context = context;
        this.routeList = routeList;
        mReference= FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public int getCount() {
        return routeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.complaint_adapter, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.date);
        final TextView tv2=(TextView)gridView.findViewById(R.id.passenger);
        TextView tv3=(TextView)gridView.findViewById(R.id.complaint);
        TextView tv4=(TextView)gridView.findViewById(R.id.reply);
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);

        final ComplaintModel model=routeList.get(position);
        Query query=mReference.child("user").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        UserModel us=snapshot.getValue(UserModel.class);
                        Log.d("======inccccc",us.getFname());
                        if(us.getLogin_id().replaceAll("-","").trim().equalsIgnoreCase(model.getPassenger_id().replaceAll("-","").trim())){
                            name=us.getFname()+" "+us.getSname();
                            Log.d("====ffffff",name);
                            tv2.setText(us.getFname()+" "+us.getSname());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tv1.setText(model.getComplaint_date());
        tv2.setText(name);
        tv3.setText("Complaint: "+model.getComplaint_detail());
        tv4.setText("Reply: "+model.getReply());
        return gridView;
    }
}
