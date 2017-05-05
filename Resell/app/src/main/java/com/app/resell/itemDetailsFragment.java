package com.app.resell;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class itemDetailsFragment extends Fragment {
    private Item clickedItem;
    TextView price,size,description,ownerName;
    ImageView itemImage;
    ImageView profileImage;
    private DatabaseReference databaseReference;
    String TAG="itemDetailsFragment";
    public itemDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_item_details, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference();


        Intent intent = getActivity().getIntent();

        clickedItem= (Item) intent.getSerializableExtra("selectedItem");

        price = (TextView) view.findViewById(R.id.price);
        description=(TextView) view.findViewById(R.id.description);
        size=(TextView) view.findViewById(R.id.size);
        profileImage=(ImageView) view.findViewById(R.id.item_photo);
        itemImage=(ImageView) view.findViewById(R.id.header_cover_image);
        ownerName=(TextView) view.findViewById(R.id.user_profile_name);
        Log.d(TAG, "on create fragment details");

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //preview  owner profile profile activity;
                Intent intent = new Intent(getActivity(), Profile.class).putExtra("displayOwner", true);

                intent.putExtra("OwnerId", clickedItem.getUserId());
                Log.d(TAG, "onclick link");
                startActivity(intent);

            }
        });
        if(isOnline()) {
            if (clickedItem != null)
                getUserInfo(clickedItem.getUserId());
            else {
                Log.v(TAG, "clicked item null");

            }
        } else Toast.makeText(getContext(),"no internet connection",Toast.LENGTH_SHORT).show();
        return view;
    }


    public void settingData(){
        price.setText(clickedItem.getPrice());
        description.setText(clickedItem.getDescription());

       // clickedItem.getUserId();
        size.setText(clickedItem.getSize());
        Picasso.with(getActivity())
                .load(clickedItem.getImageUrl()).fit().centerCrop()
                .into(itemImage);

//        if(clickedOfferedPost.getPassengerState()!=null)
//            if(clickedOfferedPost.getPassengerState().equals("Cancel")){
//                RequestLayout1.setVisibility(View.GONE);
//                RequestLayout2.setVisibility(View.GONE);
//                requestOuterLayout.setVisibility(View.GONE);
//            }


//        if(clickedOfferedPost.getRequestedByNo()>0) {
//            Requestby.setVisibility(View.VISIBLE);
//
//            String mystring=Requestby.getText()+"";
//            SpannableString content = new SpannableString(mystring);
//            content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
//            Requestby.setText(content);
//
//        }else  Requestby.setVisibility(View.GONE);
//
//


    }


    public void getUserInfo (String user_id) {

        final Account[] account = new Account[1];


        databaseReference.child("users").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // You can get the text using getValue. Since the DataSnapshot is of the exact
                // data you asked for (the node listName), when you use getValue you know it
                // will return a String.
                account[0] = dataSnapshot.getValue(Account.class);

                if (account[0] != null) {
                    Log.d("My profile", "account from get user info method");

                        if (account[0].getImage_url() != null)
                            Picasso.with(getActivity())
                                    .load(account[0].getImage_url()).fit().centerCrop()
                                    .into(profileImage);

                    if (account[0].getName() != null) ownerName.setText(account[0].getName());

                    settingData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
