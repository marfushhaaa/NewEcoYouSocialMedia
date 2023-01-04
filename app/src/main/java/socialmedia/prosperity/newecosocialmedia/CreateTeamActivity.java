package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

public class CreateTeamActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    EditText editTextName, editTextMemberNumber, editTextBio, editTextHashtag;
    ImageView createButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_team_screen);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.editTextTeamName);
        editTextHashtag = findViewById(R.id.editTextTeamHashtag);
        editTextBio = findViewById(R.id.editTextTeamBio);
        editTextMemberNumber = findViewById(R.id.editTextTeamMembers);

        createButton = findViewById(R.id.make_team_button);
        createButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.make_team_button:
                registerTeam();
                startActivity(new Intent(CreateTeamActivity.this, SearchTeamActivity.class));
                finish();
                break;
        }
    }

    private void registerTeam() {
        String name = editTextName.getText().toString().trim();
        String hashtag = editTextHashtag.getText().toString().trim();
        String memberNum = editTextMemberNumber.getText().toString().trim();
        String bio = editTextBio.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("Write your name!");
            editTextName.requestFocus();
            return;
        }

        if (hashtag.isEmpty()) {
            editTextHashtag.setError("Write your hashtag!");
            editTextHashtag.requestFocus();
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
        Team team = new Team(name, bio, hashtag, memberNum);
        FirebaseDatabase.getInstance().getReference("Teams")
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(team).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"This team has been registered", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Failed to register!", Toast.LENGTH_LONG).show();
                                    }
            }
        });

    }
}
