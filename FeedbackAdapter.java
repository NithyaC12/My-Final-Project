package com.example.lenovo.bustracking;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.model.BusModel;
import com.example.lenovo.model.FeedbackModel;
import com.example.lenovo.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedbackAdapter extends BaseAdapter {

    Context context;
    ArrayList<FeedbackModel> routeList;
    DatabaseReference mReference;

    public FeedbackAdapter(Context context, ArrayList<FeedbackModel> routeList) {
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
            gridView=inflator.inflate(R.layout.feedback_adapter, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        final TextView tv1=(TextView)gridView.findViewById(R.id.bname);
        final TextView tv2=(TextView)gridView.findViewById(R.id.bnumber);
        final TextView tv3=(TextView)gridView.findViewById(R.id.btype);
        final TextView tv4=(TextView)gridView.findViewById(R.id.uname);
        final TextView tv5=(TextView)gridView.findViewById(R.id.fdate);
        final TextView tv6=(TextView)gridView.findViewById(R.id.feedback);
        final ImageView img=(ImageView)gridView.findViewById(R.id.img);
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);


        final FeedbackModel model=routeList.get(position);
        Query query=mReference.child("bus").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        BusModel us=snapshot.getValue(BusModel.class);
                        if(us.getBus_id().replaceAll("-","").trim().equalsIgnoreCase(model.getBus_id().replaceAll("-","").trim())){

                            tv1.setText(us.getBus_name());
                            tv2.setText(us.getBus_number());
                            tv3.setText(us.getBus_type());

                            //display image
                            StorageReference storageReference= FirebaseStorage.getInstance().getReference();
                            storageReference.child("bus_images/"+us.getBus_image()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Picasso.with(context).load(uri.toString()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).error(R.drawable.buss).into(img);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query query2=mReference.child("user").orderByKey();
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        UserModel us=snapshot.getValue(UserModel.class);
                        if(us.getLogin_id().replaceAll("-","").trim().equalsIgnoreCase(model.getPassenger_id().replaceAll("-","").trim())){

                            tv4.setText(us.getFname()+" "+us.getSname());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tv5.setText(model.getFeedback_date());
        tv6.setText(model.getFeedback_detail());

        return gridView;
    }
}