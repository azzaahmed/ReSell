package com.app.resell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    // *********************** upload image ***********************************//

    private String profile_pic_path;

    private Uri imageUri = null;

    private StorageReference mStorage;

    private static final int GALLERY_REQUEST = 1;

    private ImageView imageView_circle;

    boolean profilePic_attached = false;

    Bitmap bitmap;


    private static final String TAG = "SignInActivity";

    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutAge;
    private TextInputLayout inputLayoutMobile;
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutPass;
    private TextInputLayout inputLayoutGender;

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignUp;
    private ProgressDialog progressDialog;
    private EditText Name;
    private EditText age;
    private EditText mobile;
    private Spinner gender;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //defining a database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // *********************** upload image ***********************************//
        imageView_circle  = (ImageView) findViewById(R.id.buttonChoose);
        imageView_circle.setOnClickListener(this);

        mStorage = FirebaseStorage.getInstance().getReference();

        // ************************************************************************//

        // *********************** validation   ***********************************//
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutAge = (TextInputLayout) findViewById(R.id.input_layout_age);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_pass);
        inputLayoutGender = (TextInputLayout) findViewById(R.id.input_layout_Gender);
        // ************************************************************************//

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignUp = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        age = (EditText) findViewById(R.id.age);
        gender = (Spinner) findViewById(R.id.gender);
        Name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);

        buttonSignUp.setOnClickListener(this);

        Log.d(TAG, "in create");

        // Button listeners
        //    findViewById(R.id.sign_in_button).setOnClickListener(this);
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignup:
                if(validForm()) {
                    registerUser();
                }
                break;
            // *********************** upload image ***********************************//
            case R.id.buttonChoose:
                showFileChooser();
                break;


        }
    }



    private void registerUser(){
        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        final String mAge=age.getText().toString().trim();
        final String mName= Name.getText().toString().trim();
        final String mMobile=mobile.getText().toString().trim();
        final String mGender=gender.getSelectedItem().toString();
//            final String mgender=gender.getText().toString().trim();
//            final String msmoker=smoker.getText().toString().trim();
        // final String msmoker = smoker.getSelectedItem().toString();
     /*
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(mAge)){
            Toast.makeText(this,"Please enter your age",Toast.LENGTH_LONG).show();
            return;

        }
        if(TextUtils.isEmpty(mMobile)){
            Toast.makeText(this,"Please enter your mobile number",Toast.LENGTH_LONG).show();
            return;

        }
        if(mGender.equals("Gender")){
            Toast.makeText(this,"Please enter your gender",Toast.LENGTH_LONG).show();
            return;

        }
        if(TextUtils.isEmpty(mName)){
            Toast.makeText(this,"Please enter your name",Toast.LENGTH_LONG).show();
            return;

        }
        */
//            if(TextUtils.isEmpty(msmoker)){
//                Toast.makeText(this,"you are smoker or not",Toast.LENGTH_LONG).show();
//                return;
//
//            }

        if(profilePic_attached) {
            UploadImage();
        }
        else{
            fireBaseRegestration_noprofile_pic();
        }
    }

    // *********************** upload image ***********************************//

    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            imageUri = data.getData();
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8; // shrink it down otherwise we will use stupid amounts of memory
//                Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath(), options);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView_circle.setImageURI(imageUri);
            profilePic_attached = true;

        }

    }

    public void fireBaseRegestration_noprofile_pic(){

        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        final String mage=age.getText().toString().trim();
        final String mname= Name.getText().toString().trim();
        final String mmobile=mobile.getText().toString().trim();
        final String mgender=gender.getSelectedItem().toString();

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Account myaccount = new Account(mname, mage, mmobile, mgender, email, user.getPhotoUrl() + "");

                            //databaseReference.child("users").child(user.getUid()).setValue(myaccount);
                            DatabaseReference x = databaseReference.child("users").child(user.getUid());
                            x.setValue(myaccount);
                            String key = x.getKey();
                            HashMap<String, Object> result = new HashMap<>();
                            result.put("id", key);
                            x.updateChildren(result);

                            Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Bundle myBundle = new Bundle();
                            myBundle.putSerializable("accountinfo", (Serializable) myaccount);
                            Intent myIntent = new Intent(getApplicationContext(), Home.class);
                            myIntent.putExtras(myBundle);
                            finish();
                            startActivity(myIntent);
                        } else {
                            //display some message here
                            Toast.makeText(SignUp.this, "Account already exists", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void UploadImage(){

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        if(imageUri != null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,20, baos);
            byte[] bytes = baos.toByteArray();
            String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);// no need

            StorageReference filepath = mStorage.child("UsersImages").child(imageUri.getLastPathSegment());
            //********* national id ***************//

            /////////////////////////////////////////
            UploadTask uploadTask = filepath.putBytes(bytes);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this, "Can't upload your image ", Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    profile_pic_path = taskSnapshot.getDownloadUrl()+"";
                    fireBaseRegistration();
                }
            });

        }


    }

    public void fireBaseRegistration(){

        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        final String mAge=age.getText().toString().trim();
        final String mName= Name.getText().toString().trim();
        final String mMobile=mobile.getText().toString().trim();
        final String mGender=gender.getSelectedItem().toString();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();

                           Account myaccount = new Account(mName, mAge, mMobile, mGender, email, profile_pic_path);

                            databaseReference.child("users").child(user.getUid()).setValue(myaccount);
                            Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Bundle myBundle = new Bundle();
                            myBundle.putSerializable("accountinfo", (Serializable) myaccount);
                            Intent myIntent = new Intent(getApplicationContext(), Home.class);
                            myIntent.putExtras(myBundle);
                            finish();
                            startActivity(myIntent);


                        } else {
                            //display some message here
                            Toast.makeText(SignUp.this, "Account already exists", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private boolean validForm(){

        int counter = 0;
        if (Name.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("please enter your name");
            requestFocus(Name);
            counter ++;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        if (age.getText().toString().trim().isEmpty()) {
            inputLayoutAge.setError("please enter your age");
            requestFocus(age);
            counter ++;
        } else {
            if(Integer.parseInt(age.getText().toString().trim())<12 || Integer.parseInt(age.getText().toString().trim())> 90) {
                inputLayoutAge.setError("please enter reasonable age");
                requestFocus(age);
                counter++;
            }
            else
                inputLayoutAge.setErrorEnabled(false);
        }
        if (mobile.getText().toString().trim().isEmpty()) {
            inputLayoutMobile.setError("Invalid mobile number");
            requestFocus(mobile);
            counter ++;
        } else {
            inputLayoutMobile.setErrorEnabled(false);
        }
        if (editTextEmail.getText().toString().trim().isEmpty()) {
            inputLayoutEmail.setError("Invalid Email");
            requestFocus(editTextEmail);
            counter ++;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        if (editTextPassword.getText().toString().trim().isEmpty()|| editTextPassword.getText().toString().trim().length()<6) {
            inputLayoutPass.setError("password at least 6 characters");
            requestFocus(editTextPassword);
            counter ++;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }
        if(!isValidEmail(editTextEmail.getText())){
            inputLayoutEmail.setError("Invalid Email");
            requestFocus(editTextEmail);
            counter ++;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        if(!isValidPhone(mobile.getText())){
            inputLayoutMobile.setError("Invalid mobile number");
            requestFocus(mobile);
            counter ++;
        } else {
            inputLayoutMobile.setErrorEnabled(false);
        }
        if(gender.getSelectedItem().toString().equals("Gender")){
            //Toast.makeText(this,"Please enter your gender",Toast.LENGTH_LONG).show();
            inputLayoutGender.setError("Enter your gender");
            requestFocus(gender);
            counter ++;
        }else{
            inputLayoutGender.setErrorEnabled(false);
        }

        if(counter == 0){
            return true;
        }
        else return false;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public final static boolean isValidPhone(CharSequence target) {
        int x = target.length();
        if(x!=11){
            return false;
        }
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }
}