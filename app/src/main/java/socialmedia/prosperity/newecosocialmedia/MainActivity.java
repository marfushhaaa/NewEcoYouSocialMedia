package socialmedia.prosperity.newecosocialmedia;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    DatabaseReference database2;
    String receiverTeamId;
    RelativeLayout relativeLayout;
    FirebaseAuth mAuth;
    FrameLayout frameLayout;
    String TAG = "brainfuck";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_screen);

        database2 = FirebaseDatabase.getInstance().getReference().child("Users");

        frameLayout = findViewById(R.id.frameLayout);
        add();


        //team id that he has chosen


//        if(!getIntent().getExtras().get("team_id").toString().equals("no team")){
//            receiverTeamId =getIntent().getExtras().get("team_id").toString();
//            Log.d(TAG, "if statement: " + receiverTeamId);
//
//        }else{
//            database2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    receiverTeamId = snapshot.child("team").getValue().toString();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }


        database = FirebaseDatabase.getInstance().getReference().child("Teams");

        mAuth = FirebaseAuth.getInstance();

        relativeLayout = findViewById(R.id.create_window);
        Team team = new Team();
        BottomNavigationView bView = findViewById(R.id.bottom_navigation);
        bView.setSelectedItemId(R.id.home);
        bView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.addContent:
                        addContent(relativeLayout);
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
    public void addContent(RelativeLayout relativeLayout){
        relativeLayout.setVisibility(View.VISIBLE);
    }

    public void receivedTeamInfo(TextView name, TextView bio, TextView members, TextView dateOfCreation){
        database2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                receiverTeamId = snapshot.child("team").getValue().toString();
                database.child(receiverTeamId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists() && snapshot.hasChild("name")){
                            Team team = new Team();


                            String team_name = snapshot.child("name").getValue().toString();
                            name.setText("“" + team_name + "”");
                            String team_bio = snapshot.child("shortBio").getValue().toString();
                            bio.setText(team_bio);
                            String team_members = snapshot.child("membernumber").getValue().toString();
                            members.setText("Учасники: " + team_members);
                            String team_dateCreation = snapshot.child("dateCreated").getValue().toString();
                            dateOfCreation.setText("Дата створення: " + team_dateCreation);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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
        relativeLayout.setVisibility(View.INVISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
    @Override
    public void onClick(View view) {

    }
}
