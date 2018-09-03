package com.example.lenovo.bustracking;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.model.BusModel;
import com.example.lenovo.model.ComplaintModel;
import com.example.lenovo.model.RatingModel;
import com.example.lenovo.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class ComplaintsReply extends AppCompatActivity {

    EditText ed_complaint, ed_reply;
    Button send;
    ImageView img, call;
    TextView tv_bname, tv_number, tv_type, tv_status, tv_uname, tv_uplace, tv_uphone, tv_date;
    RatingBar avg_rating;
    DatabaseReference mReference;
    ComplaintModel selectedComplaint = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_reply);

        ed_complaint = (EditText) findViewById(R.id.complaint);
        ed_reply = (EditText) findViewById(R.id.reply);
        send = (Button) findViewById(R.id.send);
        img = (ImageView) findViewById(R.id.img);
        tv_bname = (TextView) findViewById(R.id.bname);
        tv_number = (TextView) findViewById(R.id.bnumber);
        tv_type = (TextView) findViewById(R.id.btype);
        tv_status = (TextView) findViewById(R.id.bstatus);
        tv_uname = (TextView) findViewById(R.id.uname);
        tv_uplace = (TextView) findViewById(R.id.uplace);
        tv_uphone = (TextView) findViewById(R.id.uphone);
        tv_date = (TextView) findViewById(R.id.date);
        avg_rating = (RatingBar) findViewById(R.id.avg_rating);
        call = (ImageView) findViewById(R.id.call);

        avg_rating.setEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        selectedComplaint = ViewComplaints.complaintList.get(ViewComplaints.pos);

        mReference = FirebaseDatabase.getInstance().getReference();

        Query query = mReference.child("user").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserModel us = snapshot.getValue(UserModel.class);
                        if (us.getLogin_id().replaceAll("-", "").trim().equalsIgnoreCase(selectedComplaint.getPassenger_id().replaceAll("-", "").trim())) {

                            tv_uname.setText(us.getFname() + " " + us.getSname());
                            tv_uplace.setText(us.getPlace());
                            tv_uphone.setText(us.getPhone());

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query q2 = mReference.child("bus").orderByKey();
        q2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final BusModel us = snapshot.getValue(BusModel.class);
                        if (us.getBus_id().replaceAll("-", "").trim().equalsIgnoreCase(selectedComplaint.getBus_id().replaceAll("-", "").trim())) {

                            tv_bname.setText(us.getBus_name());
                            tv_number.setText(us.getBus_number());
                            tv_type.setText(us.getBus_type());
                            tv_status.setText(us.getBus_type());

                            //display image
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                            storageReference.child("bus_images/" + us.getBus_image()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Picasso.with(getApplicationContext()).load(uri.toString()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).error(R.drawable.buss).into(img);
                                }
                            });

                            //fetching bus avg rating
                            Query query = mReference.child("bus_rating").orderByKey();
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {
                                        float total = 0;
                                        int count = 0;
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            RatingModel ratingModel = snapshot.getValue(RatingModel.class);

                                            if (ratingModel.getBus_id().replaceAll("-", "").trim().equals(us.getBus_id().replaceAll("-", "").trim())) {
                                                total += Float.parseFloat(ratingModel.getBus_rating());
                                                count++;
                                            }
                                        }
                                        if (count > 0) {
                                            total /= count;
                                            avg_rating.setRating(total);
                                        } else {
                                            Toast.makeText(ComplaintsReply.this, "No Passenger Rated...!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

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

        tv_date.setText(selectedComplaint.getComplaint_date());
        ed_complaint.setText(selectedComplaint.getComplaint_detail());
        ed_reply.setText(selectedComplaint.getReply());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedComplaint.setReply(ed_reply.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                selectedComplaint.setReply_date(sdf.format(new Date()));

                mReference = FirebaseDatabase.getInstance().getReference("complaint");
                mReference.child(selectedComplaint.getComplaint_id()).setValue(selectedComplaint).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(ComplaintsReply.this, "Updated...!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ViewComplaints.class));
                    }
                });
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_CALL);
                in.setData(Uri.parse("tel:" + tv_uphone.getText().toString()));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                }
                startActivity(in);
            }
        });
    }
}
