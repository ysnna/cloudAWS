package com.example.cloud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        AWSMobileClient.getInstance().Initialize(getApplicationContext(), new Callback<UserStateDetails>()  {

            @Override
            public void onResult(UserStateDetails userStateDetails) {
                log.i(TAG, userStateDetails.getUserState().toString());
                switch (userStateDetails.getUserState()) {
                    case SIGNED_IN:
                        Intent i = new Intent(createPackageContext: AuthenticationActivity.this, MainActivity.class);
                        startAction(i);
                    case SIGNED_OUT:
                        showSignIn();
                        break;
                    default:
                        AWSMobileClient.getInstance().SignOut();
                        showSignIn();
                        break;
                }
            }

            @Override
            public void onError(Exception e)    {
                Log.e(TAG, e.toStrong());
            }
        });
    }
    private void showSignIn()   {
        try {
            AWSMobileClient.getInstance().showSignIn( callingActivity: this,
                    SignInUIOptions.builder().nextActivity(MainActivity.class).build());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
