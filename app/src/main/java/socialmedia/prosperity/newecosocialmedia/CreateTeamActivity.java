package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTeamActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    EditText editTextName, editTextMemberNumber, editTextBio, editTextShortBio, editTextTeamHash;
    ImageView createButton, backButton;
    String admin_id;
    String TAG = "brainfuck";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_team_screen);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.editTextTeamName);
        editTextShortBio = findViewById(R.id.editTextTeamShortBio);
        editTextBio = findViewById(R.id.editTextTeamBio);
        editTextMemberNumber = findViewById(R.id.editTextTeamMembers);
        editTextTeamHash = findViewById(R.id.editTextTeamHash);

        createButton = findViewById(R.id.make_team_button);
        createButton.setOnClickListener(this);

        backButton = findViewById(R.id.back_button_team);
        backButton.setOnClickListener(this);

        admin_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.make_team_button:
                registerTeam();
                break;
            case (R.id.back_button_team):
                 back();
                 break;
        }
    }

    private void back(){
        Intent intent = new Intent(CreateTeamActivity.this,  SearchTeamActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void registerTeam() {
        String name = editTextName.getText().toString().trim();
        String shortBio = editTextShortBio.getText().toString().trim();
        String memberNum = editTextMemberNumber.getText().toString().trim();
        String bio = editTextBio.getText().toString().trim();
        String dateCreation = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        String hashtag = "#" + editTextTeamHash.getText().toString().trim();
        String teamPlaces = 0 + "/" + editTextMemberNumber.getText().toString().trim();

//        String totalMemberNum = "";

        if (name.isEmpty()) {
            editTextName.setError("Write your name!");
            editTextName.requestFocus();
            return;
        }

        if (shortBio.isEmpty()) {
            editTextShortBio.setError("Write your hashtag!");
            editTextShortBio.requestFocus();
            return;
        }

        if (memberNum.isEmpty()) {
            editTextMemberNumber.setError("Write your memberNum!");
            editTextMemberNumber.requestFocus();
            return;
        }

        if (bio.isEmpty()) {
            editTextBio.setError("Write your bio!");
            editTextBio.requestFocus();
            return;
        }
        Team team = new Team(name, bio, shortBio, memberNum, dateCreation, hashtag, teamPlaces);
        String teamIdKey = FirebaseDatabase.getInstance().getReference("Teams").push().getKey();
        Log.d(TAG, "mgkey:  " + teamIdKey);
        FirebaseDatabase.getInstance().getReference("Teams/" + teamIdKey)
                .setValue(team)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(CreateTeamActivity.this,  MainActivity.class);
                    intent.putExtra("admin_id", admin_id);
                    intent.putExtra("team_id", teamIdKey);
                    FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("team")
                            .setValue(teamIdKey);
                    Log.d(TAG, "admin_id: " + admin_id);
                     Toast.makeText(getApplicationContext(),"This team has been registered", Toast.LENGTH_LONG).show();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Failed to register!", Toast.LENGTH_LONG).show();
                                    }
            }
        });

    }
}
