package com.material.components.mine.login;

import android.net.Uri;

import com.google.android.gms.auth.api.identity.SignInCredential;

public class GoogleAccountData {

    private SignInCredential credential;

    private Uri profilePictureUri;

    private String displayName;

    private String accountId;

    public GoogleAccountData(SignInCredential credential) {
        this.credential = credential;
        profilePictureUri = credential.getProfilePictureUri();
        displayName = credential.getDisplayName();
        accountId = credential.getId();
    }


    public Uri getProfilePictureUri() {
        return profilePictureUri;
    }

    public void setProfilePictureUri(Uri profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
