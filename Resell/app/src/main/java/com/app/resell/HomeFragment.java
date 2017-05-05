package com.app.resell;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {


    private static final String TAG = "HomeFragment";
    private GridView gridview;
    private ItemsAdapter imageAdapter;
    public static ProgressDialog progress;
    FirebaseUser user;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);

         progress = new ProgressDialog(getActivity());
        if(isOnline()) {
        progress.setMessage("loading.....");
        progress.show();
        progress.setCancelable(false);
        }
        Firebase.setAndroidContext(getActivity());
        gridview = (GridView) view.findViewById(R.id.gridview);

        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        //Firebase itemListsRef = new Firebase("https://resell-8d488.firebaseio.com/").child("items").child(user.getUid());
        Firebase itemListsRef=null;
        if(isOnline()) {
            itemListsRef = new Firebase("https://resell-8d488.firebaseio.com/").child("items");

            //  if(itemListsRef.push()==null){
            if (itemListsRef == null) {
                Log.d(TAG, "check reference is null empty items");
                progress.dismiss();
            }

            imageAdapter = new ItemsAdapter(getActivity(), Item.class, R.layout.image_item, itemListsRef);

            gridview.setAdapter(imageAdapter);


            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item selectedItem = imageAdapter.getItem(position);
                    Log.v(TAG, "item clicked");
                    Intent intent = new Intent(getActivity(), itemDetails.class).putExtra("selectedItem", selectedItem);
                    startActivity(intent);
                }
            });
        }
        else{
            Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_LONG).show();
        }

        return view;

    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
