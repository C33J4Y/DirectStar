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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class SearchActivity extends AppCompatActivity {
    TextView name, email;
    Button signOutButton;
    GoogleSignInClient mGoogleSignInClient;
    public static final String EXTRA_MESSAGE = "com.example.directstar"; //Extra message to pass Keyword
    public static final String EXTRA_MESSAGE2 = "com.example.directstar2"; //Extra message to pass location



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Calls function to set Username and email onCreate
        retrieveMessage();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        signOutButton = findViewById(R.id.signOutButton);


        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.signOutButton:
                        // TODO: Create IF to log out without using google.
                        String currentUser = name.getText().toString();
                        if(currentUser.equals("admin")){

                            finish(); // Call finish to go back to MainActivity

                    }else{
                        Toast.makeText(SearchActivity.this,name.getText().toString(),Toast.LENGTH_LONG).show();
                        signOut();}
                        break;

                }
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();


            name.setText(personName);
            email.setText(personEmail);
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

    //Function retrieves username and displays is as textview and hard codes a fictitious email
    public void retrieveMessage(){
        //Grab username and set email when login in with hard coded values
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        name.setText(username);
        email.setText("admin@gmail.com");


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
