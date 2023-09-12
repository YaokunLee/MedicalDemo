package com.material.components.mine.login;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class GoogleSignInManager {
    private static final String TAG = "GoogleSignInManager";
    // 写一个单例
    private static GoogleSignInManager instance = null;

    private GoogleSignInManager() {

    }

    public static GoogleSignInManager getInstance() {
        if (instance == null) {
            synchronized (GoogleSignInManager.class) {
                if (instance == null) {
                    instance = new GoogleSignInManager();
                }
            }
        }
        return instance;
    }


    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private BeginSignInRequest signUpRequest;

    private static final String SERVER_CLIENT_ID = "155836323468-imcua48m067k0voglq8gpqdj4m9j0okh.apps.googleusercontent.com";

    private void initSignIn(Activity activity) {
        oneTapClient = Identity.getSignInClient(activity);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(SERVER_CLIENT_ID)
                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(false)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();


        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(SERVER_CLIENT_ID)
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();
    }

    ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;

    private GoogleAccountData googleAccountData;

    public static interface OnLoginResult {
        public void onSuccess(SignInCredential credential);

        public void onFail() ;
    }

    public GoogleAccountData getGoogleAccountData() {
        return googleAccountData;
    }

    public void initLoginButton(View loginButton, AppCompatActivity activity, OnLoginResult onLoginResult) {
        initSignIn(activity);
        activityResultLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            try {
                                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                                String idToken = credential.getGoogleIdToken();
                                googleAccountData = new GoogleAccountData(credential);
                                if (idToken != null) {
                                    // Got an ID token from Google. Use it to authenticate
                                    // with your backend.
                                    Log.d(TAG, "Got ID token." + credential.getDisplayName());
                                    Toast.makeText(activity, "Got ID token." + credential.getDisplayName(), Toast.LENGTH_SHORT).show();
                                }
                                Log.d(TAG, "Got ID token.");

                                onLoginResult.onSuccess(credential);
                            } catch (com.google.android.gms.common.api.ApiException e) {
                                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                                // todo 如果进入这个ApiException, 处理方式
                            }
                        } else {
                            Log.w(TAG, "signInResult:failed code=");
                            onLoginResult.onFail();
                        }
                    }
                });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTapClient.beginSignIn(signInRequest)
                        .addOnSuccessListener(activity, new OnSuccessListener<BeginSignInResult>() {
                            @Override
                            public void onSuccess(BeginSignInResult result) {
                                IntentSenderRequest intentSenderRequest = new IntentSenderRequest
                                        .Builder(result.getPendingIntent().getIntentSender())
                                        .build();
                                activityResultLauncher.launch(intentSenderRequest);
                                Log.d(TAG, "begin SignIn onSuccess");
                            }
                        })
                        .addOnFailureListener(activity, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // No saved credentials found. Launch the One Tap sign-up flow, or
                                // do nothing and continue presenting the signed-out UI.
                                Log.d(TAG, "beginSignIn on fail" + e.getLocalizedMessage());
                                signUp(activity);
                            }
                        });
            }
        });
    }


    private void signUp(AppCompatActivity activity) {
        if (oneTapClient != null && signUpRequest != null && activityResultLauncher != null) {
            oneTapClient.beginSignIn(signUpRequest)
                    .addOnSuccessListener(activity, new OnSuccessListener<BeginSignInResult>() {
                        @Override
                        public void onSuccess(BeginSignInResult result) {
                            IntentSenderRequest intentSenderRequest = new IntentSenderRequest
                                            .Builder(result.getPendingIntent().getIntentSender())
                                            .build();
                            activityResultLauncher.launch(intentSenderRequest);
                            Log.d(TAG, "begin Sign Up onSuccess");
                        }
                    })
                    .addOnFailureListener(activity, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // No saved credentials found. Launch the One Tap sign-up flow, or
                            // do nothing and continue presenting the signed-out UI.
                            Log.d(TAG, "begin SignUp on fail" + e.getLocalizedMessage());
                        }
                    });
        }

    }

}
