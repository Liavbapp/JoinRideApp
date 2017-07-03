package com.iaf.gettremp.gettremp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Liav Bachar on 6/21/2017.
 */

public class ViewDatabase extends AppCompatActivity {
    private static final String TAG="ViewDatabase";

    private FirebaseDatabase mFireBaseDataBase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private ListView   mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_database_layout);
        mListView=(ListView)findViewById(R.id.listview);

        mAuth=FirebaseAuth.getInstance();
        mFireBaseDataBase=FirebaseDatabase.getInstance();
        myRef=mFireBaseDataBase.getReference();
        FirebaseUser user=mAuth.getCurrentUser();
        userID=user.getUid();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                    makeToast("succesfully login with "+user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                    makeToast("Succesfully signed out");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            UserInformation uInfo=new UserInformation();
            uInfo.setName(ds.child(userID).getValue(UserInformation.class).getName());
            uInfo.setEmail(ds.child(userID).getValue(UserInformation.class).getEmail());
            uInfo.setIsFirstLogin(ds.child(userID).getValue(UserInformation.class).getIsFirstLogin());

            Log.d(TAG,"showData: name"+uInfo.getName());
            Log.d(TAG,"showData: email"+uInfo.getEmail());

            ArrayList<String>array=new ArrayList<>();
            array.add(uInfo.getName());
            array.add(uInfo.getEmail());
            array.add(String.valueOf(uInfo.getIsFirstLogin()));
            ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

//        });
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void makeToast(String toastMessage)
    {
        Toast.makeText(this,toastMessage,Toast.LENGTH_SHORT).show();
    }


}

