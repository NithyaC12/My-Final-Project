package com.example.lenovo.bustracking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.model.BusModel;
import com.example.lenovo.model.BusScheduleModel;
import com.example.lenovo.model.BusStopModel;
import com.example.lenovo.model.RouteModel;
import com.example.lenovo.model.StopAllocationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OwnerSendRouteRequest extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText ed_search,ed_stime,ed_etime;
    ListView lv_routes,lv_stops;
    Button send;
    DatabaseReference mReference;
    ArrayList<RouteModel> routeList;
    ArrayList<BusStopModel> stopList;
    ArrayList<StopAllocationModel> stopAllocationList;
    BusModel busModel;
    BaseAdapter adapter,adapter2;
    int selectedRoutePosition=0;
    SimpleDateFormat sdf;
    int totalTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_send_route_request);

        ed_search=(EditText)findViewById(R.id.search);
        ed_stime=(EditText)findViewById(R.id.stime);
        ed_etime=(EditText)findViewById(R.id.etime);
        lv_routes=(ListView)findViewById(R.id.routes);
        lv_stops=(ListView)findViewById(R.id.stops);
        send=(Button)findViewById(R.id.send);
        sdf=new SimpleDateFormat("HH:mm");
        ed_stime.setText("00:00");
        ed_etime.setText("00:00");
        ed_etime.setEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mReference= FirebaseDatabase.getInstance().getReference();
        busModel=Owner_ViewBus.busList.get(Owner_ViewBus.pos);
        searchRoutes("");
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchRoutes(ed_search.getText().toString());
            }
        });

        ed_stime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    Date d = sdf.parse(ed_stime.getText().toString());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    cal.add(Calendar.MINUTE, totalTime);
                    String newTime = sdf.format(cal.getTime());
                    ed_etime.setText(newTime);
                }catch (Exception e){}
                
            }
        });

        lv_routes.setOnItemClickListener(this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mReference=FirebaseDatabase.getInstance().getReference("bus_schedule");
                BusScheduleModel busScheduleModel=new BusScheduleModel();
                busScheduleModel.setBus_schedule_id(mReference.push().getKey());
                busScheduleModel.setBus_id(busModel.getBus_id());
                busScheduleModel.setRoute_id(routeList.get(selectedRoutePosition).getRoute_id());
                busScheduleModel.setStart_time(ed_stime.getText().toString());
                busScheduleModel.setSchedule_status("pending");
                busScheduleModel.setEnd_time(ed_etime.getText().toString());
                
                mReference.child(busScheduleModel.getBus_schedule_id()).setValue(busScheduleModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(OwnerSendRouteRequest.this, "Request Send...!!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Owner_AllocateBus.class));
                    }
                });
            }
        });
    }

    private void searchRoutes(final String keyword){

        Query query=mReference.child("route").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                routeList=new ArrayList<>();
                adapter=new BusRouteAdapter(getApplicationContext(),routeList);
                lv_routes.setAdapter(adapter);
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        RouteModel routeModel=snapshot.getValue(RouteModel.class);
                        if(routeModel.getRoute_name().contains(keyword)||routeModel.getRoute_from().contains(keyword)){
                            routeList.add(routeModel);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        if(parent==lv_routes){

            selectedRoutePosition=position;
            stopAllocationList=new ArrayList<>();
            stopList=new ArrayList<>();
            totalTime=0;
            adapter2=new BusStopAdapter(getApplicationContext(),stopList);
            lv_stops.setAdapter(adapter2);
            Query query=mReference.child("stop_allocation").orderByKey();
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            final StopAllocationModel allocationModel=snapshot.getValue(StopAllocationModel.class);
                            if(allocationModel.getRoute_id().replaceAll("-","").trim().equalsIgnoreCase(routeList.get(position).getRoute_id().replaceAll("-","").trim())){


                                Query query2=mReference.child("bus_stop").child(allocationModel.getStop_id());
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()){

                                            BusStopModel stopModel=dataSnapshot.getValue(BusStopModel.class);
                                            if(stopModel.getStop_type().contains(busModel.getBus_type())) {
                                                stopAllocationList.add(allocationModel);
                                                stopList.add(stopModel);
                                                adapter2.notifyDataSetChanged();
                                                if(busModel.getBus_type().equalsIgnoreCase("ordinary")){
                                                    totalTime+=Integer.parseInt(allocationModel.getOrdinary());
                                                }
                                                if(busModel.getBus_type().equalsIgnoreCase("limited stop")){
                                                    totalTime+=Integer.parseInt(allocationModel.getLimited_stop());
                                                }
                                                if(busModel.getBus_type().equalsIgnoreCase("fast passenger")){
                                                    totalTime+=Integer.parseInt(allocationModel.getFast_passenger());
                                                }
                                                if(busModel.getBus_type().equalsIgnoreCase("super fast")){
                                                    totalTime+=Integer.parseInt(allocationModel.getSuper_fast());
                                                }
                                                if(busModel.getBus_type().equalsIgnoreCase("super express")){
                                                    totalTime+=Integer.parseInt(allocationModel.getSuper_express());
                                                }
                                                if(busModel.getBus_type().equalsIgnoreCase("super delux")){
                                                    totalTime+=Integer.parseInt(allocationModel.getSuper_delux());
                                                }
                                                try {
                                                    Date d = sdf.parse(ed_stime.getText().toString());
                                                    Calendar cal = Calendar.getInstance();
                                                    cal.setTime(d);
                                                    cal.add(Calendar.MINUTE, totalTime);
                                                    String newTime = sdf.format(cal.getTime());
                                                    ed_etime.setText(newTime);
                                                }catch (Exception e){
                                                    Log.d("===============",e.toString());
                                                }
                                                Log.d("====ttt==",totalTime+"");
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
        }
    }
}
