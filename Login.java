package com.example.lenovo.bustracking;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.model.LoginModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText ed1,ed2;
    Button b;
    TextView tv_reg;
    DatabaseReference mReference;
    LoginModel loginModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed1=(EditText)findViewById(R.id.email);
        ed2=(EditText)findViewById(R.id.password);
        b=(Button)findViewById(R.id.login);
        tv_reg=(TextView)findViewById(R.id.reg);

        loginModel=new LoginModel();

//        BusLocationModel loginModel1=new BusLocationModel();
//        String loginID=mReference.push().getKey();
//        loginModel1.setBus_id("LJgj4cWzVlRP_7MQAYV");
//        loginModel1.setStatus("running");
//        loginModel1.setLast_updation("2018-08-12 11:45");
//        loginModel1.setLongitude("75.432");
//        loginModel1.setLattitude("11.343");
//        loginModel1.setPlace_name("kozhikode");
//        loginModel1.setLocation_id(loginID);
//
//        mReference.child(loginID).setValue(loginModel1).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(Login.this, "Completed...!!", Toast.LENGTH_SHORT).show();
//            }
//        });

        tv_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username=ed1.getText().toString();
                final String password=ed2.getText().toString();

                mReference= FirebaseDatabase.getInstance().getReference();
                Query query=mReference.child("login").orderByChild("user_name").equalTo(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int flag=0;
                        if(dataSnapshot.exists()){
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                               LoginModel model=snapshot.getValue(LoginModel.class);
                                if(model.getPassword().equals(password)){
                                   flag++;
                                   loginModel=model;
                                   break;
                                }
                            }
                        }

                        if(flag==1){

                            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor edt=sh.edit();
                            edt.putString("lid",loginModel.getLogin_id());
                            edt.commit();

                            if(loginModel.getUser_type().equalsIgnoreCase("admin")){

                                startActivity(new Intent(getApplicationContext(),AdminHome.class));
                            }
                            if(loginModel.getUser_name().equalsIgnoreCase("owner")){

                                startActivity(new Intent(getApplicationContext(),OwnerHome.class));
                            }
                            if(loginModel.getUser_type().equalsIgnoreCase("passenger")){

                                startActivity(new Intent(getApplicationContext(),PassengerHome.class));
                            }
                        }
                        else{
                            Toast.makeText(Login.this, "Invalid Username or Password...!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
