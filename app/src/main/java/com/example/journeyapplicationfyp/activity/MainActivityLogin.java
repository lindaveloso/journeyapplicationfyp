package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.journeyapplicationfyp.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import timber.log.Timber;


public class MainActivityLogin extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 123;
    private List<AuthUI.IdpConfig> mProviders;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        mProviders = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        showSignInOptions();
    }

    public void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(mProviders)
                        .setLogo(R.drawable.linda_splashscreenlogo)
                        .setIsSmartLockEnabled(false)
                        .setTosAndPrivacyPolicyUrls(
                                ".HTML",
                                ".HTML")
                        .build(), MY_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(new Intent(this, Main_HomeActivity.class));
                Timber.d("This user signed in with %s", response.getProviderType());
                Toast.makeText(this, "Welcome " + mUser.getEmail(), Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Welcome " + mUser.getPhoneNumber(), Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Welcome " + mUser.getProviderId(), Toast.LENGTH_LONG).show();
                finish();
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(mUser);

            } else {
                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    //get reference
    //DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USERS_TABLE).child(user.getUid());

    //build child
    //ref.child(mUser.getUid()).setValue(user_class);

    private void saveToDatabase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();



      /*  FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            // User is logged in


        }
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(mUser);
        Toast.makeText(this, "user Inserted into Database",Toast.LENGTH_SHORT);
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USERS_TABLE).child(mUser.getUid());
*/

    }
}
