package com.example.journeyapplicationfyp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class MainActivityLogin extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 123;
    private List<AuthUI.IdpConfig> mProviders;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mProviders = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        showSignInOptions();
    }
    public void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(mProviders)
                        //.setLogo(R.drawable.spaphoto)
                        .setIsSmartLockEnabled(false)
                        .setTosAndPrivacyPolicyUrls(
                                "xoxoxo.HTML",
                                "XOXOXOX.HTML")
                        .build(), MY_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(new Intent(this, MainActivityHome.class));
                Log.d(this.getClass().getName(), "This user signed in with " + response.getProviderType());
                Toast.makeText(this, "Welcome " + mUser.getEmail(),Toast.LENGTH_LONG).show();
                saveTDatabase();
                finish();
            } else {
                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void saveTDatabase() {
    }
    //get reference
    //DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USERS_TABLE).child(user.getUid());

    //build child
    //ref.child(mUser.getUid()).setValue(user_class);


    {
    }
}

