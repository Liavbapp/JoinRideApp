package com.iaf.gettremp.gettremp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DefaultPage extends AppCompatActivity {

    public static AutoCompleteTextView autocomplete2 = null;
    public static AutoCompleteTextView autocomplete = null;
    private FirebaseDatabase    mFireBaseDataBase;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.defaultpage);


        String[] arr = {"ראשון לציון-משה דיין", "תל אביב-השלום", "תחנה מרכזית באר שבע",
                "שער ראשון", "שער יפו"};

        autocomplete = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);

        autocomplete2 = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView2);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, arr);


        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);

        autocomplete2.setThreshold(2);
        autocomplete2.setAdapter(adapter);



    }


//    public void sendMessage(View view) {
//
////        Intent intent = new Intent(this, Main2Activity.class);
//        intent.putExtra("Source", autocomplete.getText().toString());
//        intent.putExtra("Dest", autocomplete2.getText().toString());
//        startActivity(intent);

//    }
    public void showTimePickerDialog(View v) {
        mauth= FirebaseAuth.getInstance();
        mFireBaseDataBase= FirebaseDatabase.getInstance();
        myRef=mFireBaseDataBase.getReference();
        FirebaseUser user=mauth.getCurrentUser();
        String UserId=user.getUid();
        myRef.child("users").child(UserId).child("Departure").setValue(autocomplete.getText().toString());
        myRef.child("users").child(UserId).child("Destination").setValue(autocomplete2.getText().toString());

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    private void makeToast(String toastMessage)
    {
        Toast.makeText(this,toastMessage,Toast.LENGTH_SHORT).show();

    }

}

