package com.example.lenovo.bustracking;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lenovo.model.BusModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class Owner_AddBus extends AppCompatActivity {

    Spinner sp_type;
    EditText ed_name;
    EditText ed_number,ed_imei;
    ImageView im_image;
    Button save;
    String selectedFilePath="";
    private static final int CAMERA_PIC_REQUEST = 2500,RESULT_LOAD_IMAGE=3500;
    String[] types={"ordinary","limited stop","fast passenger","super fast","super express","super delux"};

    BusModel busModel;
    DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__add_bus);

        sp_type=(Spinner)findViewById(R.id.type);
        ed_imei=(EditText)findViewById(R.id.imei);
        ed_name=(EditText)findViewById(R.id.bus_name);
        ed_number=(EditText)findViewById(R.id.bus_number);
        im_image=(ImageView)findViewById(R.id.imagec);
        save=(Button)findViewById(R.id.save);

        busModel=new BusModel();

        sp_type.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,types));
        im_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items={"Capture","Browse"};

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(Owner_AddBus.this);
                alertDialog.setTitle("");
                alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(items[which].equals("Capture")){

                            Uri uriSavedImage=Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/bus_img.jpg"));
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                            startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);

                        }
                        if(items[which].equals("Browse")){

                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        }
                    }
                });

                AlertDialog dialog=alertDialog.create();
                dialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                mReference= FirebaseDatabase.getInstance().getReference("bus");
                busModel.setBus_id(mReference.push().getKey());
                busModel.setBus_name(ed_name.getText().toString());
                busModel.setBus_number(ed_number.getText().toString());
                busModel.setBus_type(sp_type.getSelectedItem().toString());
                busModel.setImei(ed_imei.getText().toString());
                busModel.setStatus("pending");
                busModel.setUser_id(sh.getString("lid","00000"));
                busModel.setBus_image(busModel.getBus_id()+".jpg");

                mReference.child(busModel.getBus_id()).setValue(busModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        uploadFiles();
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_PIC_REQUEST)

        {
            if (resultCode == RESULT_OK)
            {
                Bitmap image = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/bus_img.jpg");
                selectedFilePath=Environment.getExternalStorageDirectory()+"/bus_img.jpg";
                im_image.setImageBitmap(image);
            }

        }
        if (requestCode == RESULT_LOAD_IMAGE)

        {
            if (resultCode == RESULT_OK)
            {
                Uri uri = data.getData();
                String path=getRealPathFromURI(uri);
                selectedFilePath=path;
                Bitmap image = BitmapFactory.decodeFile(path);
                im_image.setImageBitmap(image);
            }
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private void uploadFiles(){
        String filename=busModel.getBus_image();
        Uri fileURI=Uri.fromFile(new File(selectedFilePath));

        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Uploading files...!!");
        pd.show();

        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference storageReference1=storageReference.child("bus_images/"+filename);
        storageReference1.putFile(fileURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(Owner_AddBus.this, "File uploaded", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("==rrr",e.toString());
                        Toast.makeText(Owner_AddBus.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        pd.setMessage("Uploaded "+((int)progress)+"% completed");
                    }
                });
    }
}
