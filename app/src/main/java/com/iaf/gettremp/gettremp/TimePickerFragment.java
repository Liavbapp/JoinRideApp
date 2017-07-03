package com.iaf.gettremp.gettremp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
/**
 * Created by Liav Bachar on 7/2/2017.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private FirebaseDatabase    mFireBaseDataBase;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user


        String leavingtime=String.valueOf(hourOfDay)+":"+String.valueOf(minute);
        mauth=FirebaseAuth.getInstance();
        mFireBaseDataBase=FirebaseDatabase.getInstance();
        myRef=mFireBaseDataBase.getReference();

        FirebaseUser user=mauth.getCurrentUser();
        String UserId=user.getUid();
        myRef.child("users").child(UserId).child("LeavingTime").setValue(leavingtime);

        Intent moveToMainIntent=new Intent(getContext(),MainActivity.class);
        startActivity(moveToMainIntent);

    }



}
