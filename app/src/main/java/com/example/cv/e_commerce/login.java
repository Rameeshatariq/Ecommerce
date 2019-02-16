package com.example.cv.e_commerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cv.e_commerce.Model.Users;
import com.example.cv.e_commerce.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class login extends AppCompatActivity {
    private EditText InputPhoneNo, InputPassword;
    private Button loginBtn;
    private ProgressDialog loadingBar;
    private String parenDbName="Users";
    private com.rey.material.widget.CheckBox checkBox;
    private TextView AdminLink, NotAdminLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.login_btn);
        InputPhoneNo = (EditText) findViewById(R.id.phoneno_input);
        InputPassword = (EditText) findViewById(R.id.password_input);
        loadingBar = new ProgressDialog(this);
        checkBox=(com.rey.material.widget.CheckBox) findViewById(R.id.rememberme);
        Paper.init(this);

        AdminLink=(TextView)findViewById(R.id.admin_panel);
        NotAdminLink=(TextView)findViewById(R.id.not_admin_panel);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();

            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parenDbName = "Admins";

            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parenDbName = "Users";
            }
        });

    }

    private void loginUser() {
        String phoneNo = InputPhoneNo.getText().toString();
        String password = InputPassword.getText().toString();


        if (TextUtils.isEmpty(phoneNo)) {
            Toast.makeText(this, "Please write your Phone Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your Password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait while we are checking the Credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phoneNo, password);

        }
    }

    private void AllowAccessToAccount(final String phoneNo, final String password) {

        if(checkBox.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey, phoneNo);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parenDbName).child(phoneNo).exists()){
                    Users userData=dataSnapshot.child(parenDbName).child(phoneNo).getValue(Users.class);
                    if(userData.getPhone().equals(phoneNo)){
                        if(userData.getPassword().equals(password)) {
                            if (parenDbName.equals("Admins")) {
                                Toast.makeText(login.this, "Admin Logged In Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(login.this, AminCategoryActivity.class);
                                startActivity(intent);
                            } else if (parenDbName.equals("Users")) {
                                Toast.makeText(login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(login.this, HomeActivity.class);
                                Prevalent.current0nlineUsers= userData;
                                startActivity(intent);
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                }
                else{
                    Toast.makeText(login.this, "Account with this "+phoneNo+ " number not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(login.this, "You need to make Account first in order to Login", Toast.LENGTH_SHORT).show();

                }
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
