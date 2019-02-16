package com.example.cv.e_commerce;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cv.e_commerce.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private CircleImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, addressEditText;
    private TextView profileChangeTextbtn, Closebtn, saveBtn;
    private Uri imageUri;
    private  String myUrl="";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicRef;
    private String checker= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storageProfilePicRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        profileImageView=(CircleImageView)findViewById(R.id.settings_profile_image);
        fullNameEditText=(EditText)findViewById(R.id.settings_full_name);
        userPhoneEditText=(EditText)findViewById(R.id.settings_phoneno);
        addressEditText=(EditText)findViewById(R.id.settings_address);
        profileChangeTextbtn=(TextView)findViewById(R.id.profile_img_change);
        Closebtn=(TextView)findViewById(R.id.close_settings);
        saveBtn=(TextView)findViewById(R.id.update_settings);

        userInfoDisplay(profileImageView, fullNameEditText, userPhoneEditText, addressEditText);

        Closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked")){
                    userInfosaved();
                }
                else{
                 updateonlyUserInfo();
                }
            }
        });
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data != null){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri =result.getUri();

            profileImageView.setImageURI(imageUri);


        }
        else{
            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }
    }

    private void updateonlyUserInfo() {
        if(TextUtils.isEmpty(fullNameEditText.getText().toString())){
            Toast.makeText(this, "Name is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(userPhoneEditText.getText().toString())){
            Toast.makeText(this, "Phone Number is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this, "Address is mandatory", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("UserName", fullNameEditText.getText().toString());
            userMap.put("Address", addressEditText.getText().toString());
            userMap.put("PhoneOrder", userPhoneEditText.getText().toString());
            ref.child(Prevalent.current0nlineUsers.getPhone()).updateChildren(userMap);

            startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
            Toast.makeText(SettingsActivity.this, "Profile Info Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void userInfosaved() {
        if(TextUtils.isEmpty(fullNameEditText.getText().toString())){
            Toast.makeText(this, "Name is mandatory", Toast.LENGTH_SHORT).show();
        }
       else if(TextUtils.isEmpty(userPhoneEditText.getText().toString())){
            Toast.makeText(this, "Phone Number is mandatory", Toast.LENGTH_SHORT).show();
        }
       else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this, "Address is mandatory", Toast.LENGTH_SHORT).show();
        }
       else {
            if (checker.equals("clicked")){
                uploadImage();

            }
        }

    }

    private void uploadImage() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating account Information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
      if(imageUri != null){
          final StorageReference fileRef=storageProfilePicRef.child((Prevalent.current0nlineUsers.getPhone() + ".jpg"));
          uploadTask=fileRef.putFile(imageUri);
          uploadTask.continueWithTask(new Continuation() {
              @Override
              public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                  return fileRef.getDownloadUrl();
              }
          }).addOnCompleteListener(new OnCompleteListener<Uri>() {
              @Override
              public void onComplete(@NonNull Task<Uri> task) {
                  if(task.isSuccessful()){
                      Uri downloadUrl= task.getResult();
                      myUrl=downloadUrl.toString();

                      DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
                      HashMap<String, Object> userMap= new HashMap<>();
                      userMap.put("UserName", fullNameEditText.getText().toString());
                      userMap.put("Address", addressEditText.getText().toString());
                      userMap.put("PhoneOrder", userPhoneEditText.getText().toString());
                      userMap.put("Image", myUrl);

                      ref.child(Prevalent.current0nlineUsers.getPhone()).updateChildren(userMap);
                      progressDialog.dismiss();
                      startActivity(new Intent(SettingsActivity.this, HomeActivity.class));

                      Toast.makeText(SettingsActivity.this, "Profile Info Updated Successfully", Toast.LENGTH_SHORT).show();
                      finish();
                  }
                  else{
                      progressDialog.dismiss();
                      Toast.makeText(SettingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                  }
              }
          });
      }
      else{
          Toast.makeText(this, "Image is nt selected", Toast.LENGTH_SHORT).show();
      }

    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameEditText, final EditText userPhoneEditText, final EditText addressEditText) {
        DatabaseReference UsrsRef=FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.current0nlineUsers.getPhone());
        UsrsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("Image").exists()){
                        String image= dataSnapshot.child("Image").getValue().toString();
                        String name= dataSnapshot.child("UserName").getValue().toString();
                        String phone= dataSnapshot.child("Phone").getValue().toString();
                        String address= dataSnapshot.child("Address").getValue().toString();

                        Picasso.get().load(image).into(profileImageView);
                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        addressEditText.setText(address);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SettingsActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
