package com.example.lenovo.bustracking;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.model.DriverModel;
import com.example.lenovo.model.LoginModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverReg extends AppCompatActivity {

    Button save;
    EditText ed_name,ed_license,ed_password,ed_cpassword;
    DatabaseReference mReference;
    LoginModel loginModel;
    DriverModel driverModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reg);
        
        save=(Button)findViewById(R.id.save);
        ed_name=(EditText) findViewById(R.id.name);
        ed_license=(EditText)findViewById(R.id.license);
        ed_password=(EditText)findViewById(R.id.password);
        ed_cpassword=(EditText)findViewById(R.id.cpassword);
        
        loginModel=new LoginModel();
        driverModel=new DriverModel();
        
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        
                mReference= FirebaseDatabase.getInstance().getReference("login");
                loginModel.setLogin_id(mReference.push().getKey());
                loginModel.setUser_name(ed_license.getText().toString());
                loginModel.setPassword(ed_password.getText().toString());
                loginModel.setUser_type("driver");
                
                mReference.child(loginModel.getLogin_id()).setValue(loginModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                   
                        mReference=FirebaseDatabase.getInstance().getReference("driver");
                        driverModel.setDriver_id(mReference.push().getKey());
                        driverModel.setBus_id("0");
                        driverModel.setLogin_id(loginModel.getLogin_id());
                        driverModel.setDriver_name(ed_name.getText().toString());
                        driverModel.setLicense_number(ed_license.getText().toString());
                        
                        mReference.child(driverModel.getDriver_id()).setValue(driverModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(DriverReg.this, "Success...!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
}
