package socialmedia.prosperity.newecosocialmedia;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeamProfileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView name, bio, dateOfCreation, members;
    ImageView teamPhoto;
    DatabaseReference database;
    FirebaseDatabase firebaseDatabase;
    String receiverTeamId;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_profile_screen);

        name = findViewById(R.id.team_name_teamprofile);
        bio = findViewById(R.id.team_bio_teamprofile);
        dateOfCreation = findViewById(R.id.team_dateofcreation_teamprofile);
        members = findViewById(R.id.team_members_teamprofile);

        //team id that he has chosen
        receiverTeamId =getIntent().getExtras().get("team_id").toString();

        database = FirebaseDatabase.getInstance().getReference().child("Teams");
        receivedTeamInfo();

        mAuth = FirebaseAuth.getInstance();

    }

    public void receivedTeamInfo(){
        database.child(receiverTeamId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.hasChild("name")){
                    String team_name = snapshot.child("name").getValue().toString();
                    name.setText("“" + team_name + "”");
                    String team_bio = snapshot.child("bio").getValue().toString();
                    bio.setText(team_bio);
                    String team_members = snapshot.child("membernumber").getValue().toString();
                    members.setText("Учасники" + team_members);
                    String team_dateCreation = snapshot.child("dateCreated").getValue().toString();
                    dateOfCreation.setText("Дата створення: " + team_dateCreation);

                    FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("team")
                            .setValue(receiverTeamId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
