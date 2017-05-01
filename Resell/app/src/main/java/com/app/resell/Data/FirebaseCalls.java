package com.app.resell.Data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.resell.Account;
import com.app.resell.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by azza ahmed on 4/30/2017.
 */
public  class  FireBaseCalls {
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    //defining a database reference
    private DatabaseReference databaseReference;
   private ProgressDialog progressDialog;

    public FireBaseCalls() {
    }

    public  void  fireBaseRegistration(EditText editTextEmail,EditText editTextPassword,EditText age,EditText Name,EditText mobile,Spinner gender, final String profile_pic_path, final Context context, final boolean noProfilePictureFlag, final Activity Activity){

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(Activity);
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        final String mAge=age.getText().toString().trim();
        final String mName= Name.getText().toString().trim();
        final String mMobile=mobile.getText().toString().trim();
        final String mGender=gender.getSelectedItem().toString();
        final String pic_path=profile_pic_path;
        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Account myAccount;
                            if(!noProfilePictureFlag)
                                myAccount = new Account(mName, mAge, mMobile, mGender, email,pic_path);
                            else
                                myAccount = new Account(mName, mAge, mMobile, mGender, email, user.getPhotoUrl() + "");

                            DatabaseReference x = databaseReference.child("users").child(user.getUid());
                            x.setValue(myAccount);
                            String key = x.getKey();
                            HashMap<String, Object> result = new HashMap<>();
                            result.put("id", key);
                            x.updateChildren(result);

                            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show();
                            Bundle myBundle = new Bundle();
                            myBundle.putSerializable("accountinfo", (Serializable) myAccount);
                            Intent myIntent = new Intent(Activity, Home.class);
                            myIntent.putExtras(myBundle);
                            Activity.finish();
                            Activity.startActivity(myIntent);


                        } else {
                            //display some message here
                            Toast.makeText(context, "Account already exists", Toast.LENGTH_LONG).show();

                        }
                       progressDialog.dismiss();
                    }
                });

    }
}
