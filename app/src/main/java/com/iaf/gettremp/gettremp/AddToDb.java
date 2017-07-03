package com.iaf.gettremp.gettremp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iaf.gettremp.gettremp.R;

/**
 * Created by Liav Bachar on 6/21/2017.
 */

public class AddToDb extends AppCompatActivity {
    private static final String TAG="com.iaf.gettremp.gettremp.AddToDb";
    private Button btnaddfood;
    private EditText etNewfood;
    private FirebaseDatabase    mFireBaseDataBase;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtodb);
        btnaddfood=(Button)findViewById(R.id.btnaddfood);
        etNewfood=(EditText)findViewById(R.id.etinsetfoodname);

        mauth=FirebaseAuth.getInstance();
        mFireBaseDataBase=FirebaseDatabase.getInstance();
        myRef=mFireBaseDataBase.getReference();

        btnaddfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newfood=etNewfood.getText().toString();
                if (!newfood.equals(""))
                {
                    FirebaseUser user=mauth.getCurrentUser();
                    String UserId=user.getUid();
                    myRef.child(UserId).child("FOOD").child("Favorite Food").child(newfood).setValue(true);
                    makeToast("added new food!");
                    etNewfood.setText("");
                }
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                        makeToast("succesfully login with "+user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    makeToast("Succesfully signed out");
                }
                // ...
            }
        };

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               Object value=dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mAuthListener);

//        User_1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////do nothing fow now
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mauth.removeAuthStateListener(mAuthListener);
        }
    }

    private void makeToast(String toastMessage)
    {
        Toast.makeText(this,toastMessage,Toast.LENGTH_SHORT).show();
    }
}
