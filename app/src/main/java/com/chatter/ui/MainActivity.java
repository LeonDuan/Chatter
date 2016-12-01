package com.chatter.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatter.Constants;
import com.chatter.model.ChatterContact;
import com.chatter.model.ChatterMessage;
import com.chatter.model.MessageViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.chatter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private GoogleApiClient mGoogleApiClient;
    private String mUsername;
    private String mUserID;
    private String mUseremail;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private EditText etChat;
    private Button bSingleChat;
    private EditText etContact;
    private Button bAddContact;
    private TextView tvUserEmail;
    private  FirebaseDatabase mFirebaseDatabase;
    private boolean contactFound = false;
    private Set<String> emails = new HashSet();

    private Constants constants;
    private String TAG = "MainActivity";

    private FirebaseRecyclerAdapter<ChatterMessage, MessageViewHolder> mFirebaseAdapter;
    private boolean found;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        // firebase database reference
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();


        //keep Updating Email Set
        keepUpdatingEmailSet();

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mUserID = mFirebaseUser.getUid();
            mUseremail = mFirebaseUser.getEmail();
            if (mUsername == null) {
                mUsername = mUseremail;
            }
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        // initialize welcome message email
        tvUserEmail = (TextView) findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(mUseremail);

        // initialize google API client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .addApi(AppInvite.API)
                .build();

        // button to start chat
        bSingleChat = (Button) findViewById(R.id.bSingleChat);
        bSingleChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //etChat = (EditText) findViewById(R.id.etChat);
                //String chat = etChat.getText().toString();
                String chat = chooseRandomChat();
                while (chat.equals(mFirebaseUser.getEmail()))
                {
                    chat = chooseRandomChat();
                }
                if(chat != ""){
                    final String tmp = mUseremail.split("@")[0] + chat.split("@")[0];
                    final String tmp2 = chat.split("@")[0] + mUseremail.split("@")[0];

                    mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.hasChild(tmp) && !dataSnapshot.hasChild(tmp2)){
                                ChatterMessage system = new ChatterMessage("You can now start chatting.", "System");
                                mFirebaseDatabaseReference.child(tmp).push().setValue(system);
                            }

                            Intent intent = new Intent(MainActivity.this, SingleChatActivity.class);
                            if(dataSnapshot.hasChild(tmp)){
                                intent.putExtra("roomname", tmp);
                            }else {
                                intent.putExtra("roomname", tmp2);
                            }
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        // button to add contact
        bAddContact = (Button) findViewById(R.id.bAddContact);
        bAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etContact = (EditText) findViewById(R.id.etContact);
                if(etContact!=null){
                    final String contact = etContact.getText().toString();

                    //check the desired contact is in the database
                    mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ChatterContact tmp = new ChatterContact(contact);
                            mFirebaseDatabaseReference.child(constants.CONTACTS_CHILD).child(mUserID).push().setValue(tmp);
                            Toast.makeText(MainActivity.this, contact + " added", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
//                final String contact = etContact.getText().toString();
//
//                isEmailInServer(contact);
//                if (found == true) {
//                    ChatterContact tmp = new ChatterContact(contact);
//                    mFirebaseDatabaseReference.child(constants.CONTACTS_CHILD).child(mUserID).push().setValue(tmp);
//
//                }
//                if(found == true){
//                    Toast.makeText(MainActivity.this, contact + " added", Toast.LENGTH_SHORT).show();
//                    found = false;
//                }
//                else{
//                    Toast.makeText(MainActivity.this, contact + " is not on Chatter", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    private String chooseRandomChat() {
        String chat = "";
        Random r = new Random();
        int toChoose = r.nextInt(emails.size());
        int i = 0;
        for (String email : emails){
            if (toChoose == i){
                chat = email;
                break;
            }
            i+=1;
        }
        return chat;
    }

    private void keepUpdatingEmailSet() {
        mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot email : dataSnapshot.child("EmailList").getChildren())
                {
                    emails.add(email.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.
        if(mFirebaseUser==null){
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.group_chat_menu:
                startActivity(new Intent(this, GroupChatActivity.class));
                return true;
            case R.id.contact_menu:
                startActivity(new Intent(this, ContactActivity.class));
                return true;
            case R.id.upload_menu:
                startActivity(new Intent(this, UploadActivity.class));
                return true;
            case R.id.gallery_menu:
                startActivity(new Intent(this, GalleryActivity.class));
                return true;
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void isEmailInServer(String email) {
        final String emailToFind = email;
        mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot email : dataSnapshot.child("EmailList").getChildren())
                {
                    if (emailToFind.equals(email.getValue().toString())){
                        found = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
