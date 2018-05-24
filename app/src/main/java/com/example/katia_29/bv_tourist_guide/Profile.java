package com.example.katia_29.bv_tourist_guide;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class Profile extends AppCompatActivity {
    private final static int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_INTENT = 2;
    ImageView profileImage;
    EditText name, contactNumber, dateOfBirth;
    RadioGroup gender, status;
    Spinner profession;
    Button save, capture, upload;
    StorageReference storageReference;
    private int mYear, mMonth, mDay;
    private ProgressDialog progressDialog;
    private String profString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progressDialog = new ProgressDialog(this);
        profileImage = (ImageView) findViewById(R.id.profile_pic);
        save = (Button) findViewById(R.id.save);
        capture = (Button) findViewById(R.id.capture);
        upload = (Button) findViewById(R.id.upload);
        profession = (Spinner) findViewById(R.id.spinnerProfession);
        contactNumber = (EditText) findViewById(R.id.contact_no);
        dateOfBirth = (EditText) findViewById(R.id.birthdate);
        gender = (RadioGroup) findViewById(R.id.gender);
        status = (RadioGroup) findViewById(R.id.status);
        name = (EditText) findViewById(R.id.name);

        //Array Adapter for Spinner
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(Profile.this, R.array.Profession, android.R.layout.simple_spinner_item);
        profession.setAdapter(arrayAdapter);
        profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view;

                profString = txt.getText().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Firebase Storage Reference
        storageReference = FirebaseStorage.getInstance().getReference();

        //Capture Intent
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
        //Image Upload Intent
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Open Date Picker On click of Edittext
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Profile.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateOfBirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Profile", "onActivityResult: " + requestCode);
        // image upload using camera
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            progressDialog.setMessage("Uploading the image");
            progressDialog.show();

            profileImage.setImageBitmap(photo);
            progressDialog.dismiss();


//            StorageReference filepath = storageReference.child("Photos").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(Profile.this, "Upload Success", Toast.LENGTH_LONG).show();
//                    progressDialog.dismiss();
//
//
//                    //Retrive image from database
//                    Uri downloaduri = taskSnapshot.getDownloadUrl();
//                    Picasso.get().load(downloaduri).into(profileImage);
//              }
//            });
        }
        //gallery intent
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            progressDialog.setMessage("Uploading the image");
            progressDialog.show();
            StorageReference filepath = storageReference.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Profile.this, "Upload Success", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    //Retrive image from database
                    Uri downloaduri = taskSnapshot.getDownloadUrl();
                    Picasso.get().load(downloaduri).into(profileImage);
//                    Picasso.with(Post_add.this).load(downloaduri).fit().centerCrop().into(upimage);
                }
            });

        }

    }
}
