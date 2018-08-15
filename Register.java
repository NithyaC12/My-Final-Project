package com.example.lenovo.bustracking;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lenovo.model.LoginModel;
import com.example.lenovo.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText ed_fname,ed_sname,ed_dob,ed_place,ed_phone,ed_email,ed_password;
    Button save;
    RadioButton female;
    DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_fname=(EditText)findViewById(R.id.fname);
        ed_sname=(EditText)findViewById(R.id.sname);
        ed_dob=(EditText)findViewById(R.id.dob);
        ed_place=(EditText)findViewById(R.id.place);
        ed_phone=(EditText)findViewById(R.id.mobile);
        ed_email=(EditText)findViewById(R.id.email);
        ed_password=(EditText)findViewById(R.id.password);
        save=(Button)findViewById(R.id.save);
        female=(RadioButton)findViewById(R.id.female);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LoginModel loginModel=new LoginModel();
                loginModel.setUser_name(ed_email.getText().toString());
                loginModel.setPassword(ed_password.getText().toString());
                loginModel.setUser_type("passenger");

                mReference= FirebaseDatabase.getInstance().getReference("login");
                loginModel.setLogin_id(mReference.push().getKey());

                mReference.child(loginModel.getLogin_id()).setValue(loginModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        mReference=FirebaseDatabase.getInstance().getReference("user");

                        UserModel userModel=new UserModel();
                        userModel.setUser_id(mReference.push().getKey());
                        userModel.setLogin_id(loginModel.getLogin_id());
                        userModel.setFname(ed_fname.getText().toString());
                        userModel.setSname(ed_sname.getText().toString());
                        userModel.setDob(ed_dob.getText().toString());
                        String gender="male";
                        if(female.isChecked()){
                            gender="female";
                        }
                        userModel.setGender(gender);
                        userModel.setPlace(ed_place.getText().toString());
                        userModel.setPhone(ed_phone.getText().toString());
                        userModel.setEmail(ed_email.getText().toString());
                        
                        mReference.child(userModel.getUser_id()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(Register.this, "Success...!!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
}
