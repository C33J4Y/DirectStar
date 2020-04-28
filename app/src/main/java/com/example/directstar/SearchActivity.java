package com.example.directstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class SearchActivity extends AppCompatActivity {
    TextView name, email, id;
    Button signOutButton;
    GoogleSignInClient mGoogleSignInClient;
    public static final String EXTRA_MESSAGE = "com.example.directstar"; //Extra message to pass Keyword
    public static final String EXTRA_MESSAGE2 = "com.example.directstar2"; //Extra message to pass location



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        //id = findViewById(R.id.textID);
        signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.signOutButton:
                        signOut();
                        break;
                    // ...
                }
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            //Url personPhoto = acct.getPhotoUrl();

            name.setText(personName);
            email.setText(personEmail);
            //id.setText(personId);
        }


    }

    public void sendMessage(View view){
        // Grabs Keyword and Location and passes it to searchResultActivity
        Intent intent = new Intent(this, searchResultActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        String keyword = editText.getText().toString();
        String location = editText2.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, keyword); //Extra message needs to be different with
        intent.putExtra(EXTRA_MESSAGE2, location); // each putExtra() to avoid overwriting values
        startActivity(intent);

    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SearchActivity.this,"Signed Out!",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }

}
