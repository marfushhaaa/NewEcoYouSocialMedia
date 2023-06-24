package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail, editTextPassword;
    ImageView login_button, back_button;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String team_id;
    String TAG = "brainfuck";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(this);

        back_button = findViewById(R.id.back_button_login);
        back_button.setOnClickListener(this);

        editTextEmail = findViewById(R.id.editTextTextPersonEmail);
        editTextPassword = findViewById(R.id.editTextTextPasswordLogin);

        mAuth = FirebaseAuth.getInstance();
        team_id = "";

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
//        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                team_id = snapshot.child("team").getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
//        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                team_id = snapshot.child("team").getValue().toString();
//                Log.d(TAG, "team: " + team_id);
//
//                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
//                String login = preferences.getString("remember", "");
//                Log.d(TAG, "login: " + login);
//
//                if(login.equals("true") && !team_id.equals("no team")){
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    startActivity(intent);
//                }else if(login.equals("false")){
//                    Intent intent = new Intent(LoginActivity.this, SearchTeamActivity.class);
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    startActivity(intent);
//                    Toast.makeText(LoginActivity.this,"Please, sign in", Toast.LENGTH_LONG).show();
//                }else if(login.equals("true") && team_id.equals("no team")){
//                    Intent intent = new Intent(LoginActivity.this, SearchTeamActivity.class);
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        Log.d(TAG, "team: " + team_id);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                userLogin();
                break;
            case R.id.back_button_login:
                back();
                break;
        }
    }

    private void back() {
        Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            databaseReference.child(mFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "user: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Log.d(TAG, "team: " + snapshot.child("team").getValue().toString());

                    team_id = snapshot.child("team").getValue().toString();
                    Log.d(TAG, "user: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                    SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                    String login = preferences.getString("remember", "");
                    Log.d(TAG, "login: " + login);

                    //check password
                    if (login.equals("true") && !team_id.equals("no team")) {
                        SaveSharedPreference.setUserName(LoginActivity.this, FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Log.d(TAG, "logged in");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent);
                    } else if (login.equals("false")) {
                        Toast.makeText(LoginActivity.this, "Please, sign in", Toast.LENGTH_LONG).show();
                    } else if (login.equals("true") && team_id.equals("no team")) {
                        Intent intent = new Intent(LoginActivity.this, SearchTeamActivity.class);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
//        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                team_id = snapshot.child("team").getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        if (email.isEmpty()) {
            editTextEmail.setError("Напиши свій імейл!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Write your email correctly!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Напиши свій пароль");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Пароль має бути від 6 символів");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //redirect to user profile
                    SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();

                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(new Intent(LoginActivity.this, SearchTeamActivity.class));
                    Toast.makeText(getApplicationContext(), "User has been logged in!", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Failed to login!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
