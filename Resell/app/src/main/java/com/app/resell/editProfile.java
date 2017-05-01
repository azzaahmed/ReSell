package com.app.resell;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;

// when first sign in with google to take the extra information
public class editProfile extends AppCompatActivity implements  View.OnClickListener {

    // *********************** upload image ***********************************//

    private Button buttonChoose;

    private ImageView imageView;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    boolean profilepic_attached = false;

    // ************************************************************************//

    private EditText Name;
    private EditText age;
    private EditText mobile;
    private Spinner gender;

    String mage;
    String mname;
    String mgender;
    String mmobile,oldurl;
    Account account;
    private FirebaseAuth firebaseAuth;
    //defining a database reference
    private DatabaseReference databaseReference;

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // *********************** upload image ***********************************//

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        imageView  = (ImageView) findViewById(R.id.imgView);
        buttonChoose.setOnClickListener(this);


        // ************************************************************************//


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        firebaseAuth = FirebaseAuth.getInstance();
        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        account = (Account)intent.getSerializableExtra("accountinfo");

        age = (EditText) findViewById(R.id.age);
        gender = (Spinner) findViewById(R.id.gender);
        Name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.cancel:
//                cancel();
//                break;
            // *********************** upload image ***********************************//
            case R.id.buttonChoose:
                showFileChooser();
                break;

        }
    }

    private void edit(){
        user = firebaseAuth.getCurrentUser();
        mage=age.getText().toString().trim();
        mname= Name.getText().toString().trim();
        mmobile=mobile.getText().toString().trim();
        mgender=gender.getSelectedItem().toString();


        if(account!=null) {
            if (mage.isEmpty()) mage = account.getAge();
            if (mname.isEmpty()) mname = account.getName();
            if (mmobile.isEmpty()) mmobile = account.getMobile();
            // msmoker = smoker.getSelectedItem().toString();////////////////////////////////
            // get selected radio button from radioGroup
            oldurl=account.getImage_url();

            mgender=gender.getSelectedItem().toString();
//    if (mgender.isEmpty()) mgender = account.getGender();
//    if (msmoker.isEmpty()) msmoker = account.getSmoker();
        }

        else{
            if(TextUtils.isEmpty(mname)&&user.getDisplayName()==null){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show();
                return;

            } else if(TextUtils.isEmpty(mname)&&user.getDisplayName()!=null) mname=user.getDisplayName();

            if(TextUtils.isEmpty(mgender)){
                Toast.makeText(this,"Please enter your gender",Toast.LENGTH_LONG).show();
                return;

            }
            if(TextUtils.isEmpty(mage)){
                Toast.makeText(this,"Please enter your age",Toast.LENGTH_LONG).show();
                return;

            }
            if(TextUtils.isEmpty(mmobile)){
                Toast.makeText(this,"Please enter your mobile number",Toast.LENGTH_LONG).show();
                return;

            }
            if(mgender.equals("Gender")){
                Toast.makeText(this,"Please enter your Gender",Toast.LENGTH_LONG).show();
                return;
            }

//    if(TextUtils.isEmpty(msmoker)){
//        Toast.makeText(this,"smoker or non-smoker",Toast.LENGTH_LONG).show();
//        return;
//
//    }


        }
        if(!profilepic_attached) {
            firebasedit_noprofile_pic();
        }

    }

    // *********************** upload image ***********************************//

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                profilepic_attached = true ;
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebasedit_noprofile_pic(){

        Account myaccount = new Account(mname, mage, mmobile, mgender, user.getEmail(),user.getPhotoUrl()+"");


        DatabaseReference x=  databaseReference.child("users").child(user.getUid());
        x.setValue(myaccount);
        // pushing key as id field in the table after pushing object
        String key= x.getKey();
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", key);
        x.updateChildren(result);
//        Toast.makeText(editProfile.this, "data saved",Toast.LENGTH_SHORT).show();

        // startActivity(new Intent(this, MyProfile.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if(account!=null) {
            finish();
        }
        // your code.
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            edit();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
