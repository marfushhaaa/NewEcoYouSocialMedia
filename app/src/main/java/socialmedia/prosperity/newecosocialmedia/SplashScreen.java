package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    View registerbutt, loginbutt;
    String TAG = "brainfuck";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        String login = preferences.getString("remember", "");
        Log.d(TAG, "login: " + login);

        DatabaseReference teamId = FirebaseDatabase.getInstance().getReference("Users").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("team");
        teamId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String teamIdStr = snapshot.getValue(String.class);
                Log.d(TAG, teamIdStr);

                if(login.equals("true") && teamIdStr.equals("no team")){
                    Intent intent = new Intent(SplashScreen.this, SearchTeamActivity.class);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent);
                }
                else if(login.equals("true") && !teamIdStr.equals("no team")){
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //check password

//        else if(login.equals("false")){

////                    Intent intent = new Intent(SplashScreen.this, SearchTeamActivity.class);
////                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
////                    startActivity(intent);
//        }


//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // User is signed in
//            Intent i = new Intent(SplashScreen.this, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(i);
//        } else {
//
//            // User is signed out
////            Log.d(TAG, "onAuthStateChanged:signed_out");
//        }

        //paste here shared preferences

        registerbutt = findViewById(R.id.register_button);
        registerbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreen.this,  SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });


        loginbutt = findViewById(R.id.login_butt);
        loginbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SplashScreen.this,  LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

            }
        });
    }



}
