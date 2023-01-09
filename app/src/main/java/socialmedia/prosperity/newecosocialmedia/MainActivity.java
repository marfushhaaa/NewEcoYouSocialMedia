package socialmedia.prosperity.newecosocialmedia;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView teamPhoto;
    DatabaseReference database;
    String receiverTeamId;
    FirebaseAuth mAuth;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_screen);

        frameLayout = findViewById(R.id.frameLayout);
        add();
        //team id that he has chosen
        receiverTeamId =getIntent().getExtras().get("team_id").toString();

        database = FirebaseDatabase.getInstance().getReference().child("Teams");
//        receivedTeamInfo();

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bView = findViewById(R.id.bottom_navigation);
        bView.setSelectedItemId(R.id.home);
        bView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.addContent:
                        changeFragment(new AddPostFragment());
                        return true;
                    case R.id.team:
                        changeFragment(new TeamProfileFragment());
                        return true;
                    case R.id.home:
                        changeFragment(new HomePageFragment());
                        return true;
                }
                return false;
            }
        });

    }

    public void receivedTeamInfo(TextView name, TextView bio, TextView members, TextView dateOfCreation){
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

    private void add(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout,new TeamProfileFragment());
        transaction.commit();
    }
    public void changeFragment(final Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
    @Override
    public void onClick(View view) {

    }
}
