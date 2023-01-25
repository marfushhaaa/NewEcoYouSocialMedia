package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchTeamActivity extends AppCompatActivity implements View.OnClickListener {
    View createATeamButton;
    RecyclerView recyclerView;
    Team team = new Team();
    long childrenCount;
    DatabaseReference database;
    String team_places, member_number;
    int team_places_int, member_number_int;
    @Override
    public void onBackPressed() {

    }
    String TAG = "brainfuck";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_searching_screen);

        createATeamButton = findViewById(R.id.create_a_team);
        createATeamButton.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_view_teamsearching);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance().getReference().child("Teams");


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Team> options = new FirebaseRecyclerOptions.Builder<Team>()
                .setQuery(FirebaseDatabase.getInstance().getReference("/Teams"), Team.class)
                .build();

        FirebaseRecyclerAdapter<Team, ViewHolder> recyclerAdapter =
                new FirebaseRecyclerAdapter<Team, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Team model) {
                        holder.name.setText(model.getName());
                        holder.bio.setText(model.getShortBio());
                        holder.teamHash.setText(model.getHashtag());
                        holder.membernumber.setText(model.getMembernumber());
                        holder.dateCreation.setText(model.getDateCreated());
                        holder.teamPlaces.setText(model.getTeamPlaces());
                        holder. joinButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String team_id = getRef(position).getKey();

                                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Teams/"  + team_id + "/users_in_team");
                                printChildrenCount(rootRef, team_id);

                                FirebaseDatabase.getInstance().getReference("Teams/"  + team_id + "/users_in_team" + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                FirebaseDatabase.getInstance().getReference("Teams/" + team_id)
                                        .child("users");
                                FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("team")
                                        .setValue(team_id);
                                team.getUserList().add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                Intent intent = new Intent(SearchTeamActivity.this,  MainActivity.class);
                                intent.putExtra("team_id", team_id);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);
                        ViewHolder viewHolder = new ViewHolder(view);
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();
    }

    private void printChildrenCount(DatabaseReference ref, String team_id){


        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    database.child(team_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            childrenCount = task.getResult().getChildrenCount();
                            team_places = snapshot.child("users").getValue().toString();
                            member_number = snapshot.child("membernumber").getValue().toString();
                            team_places_int = Integer.parseInt(team_places);
                            member_number_int = Integer.parseInt(member_number);
                            if(team_places_int < member_number_int){
                                FirebaseDatabase.getInstance().getReference("Teams/"  + team_id + "/users")
                                        .setValue(childrenCount+1);

                            }
                            Log.d(TAG, "users: " + team_places);
//                            team.setMembernumber(member_number);
                            team.setTeamPlaces(team_places + "/" + member_number);
                            Log.d(TAG, "memberNumber: " + team.getMembernumber());
                            Log.d(TAG, "member_number: " + member_number);
                            FirebaseDatabase.getInstance().getReference("Teams/"  + team_id + "/teamPlaces")
                                    .setValue(team_places + "/" + member_number);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Log.d(TAG, "1childrenCount: " + childrenCount);
                } else {
                    Log.d(TAG, task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, bio, teamHash, membernumber, dateCreation, teamPlaces;
        View joinButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            bio = itemView.findViewById(R.id.team_bio);
            teamHash = itemView.findViewById(R.id.team_hash);
            membernumber = itemView.findViewById(R.id.team_members_number);
            dateCreation = itemView.findViewById(R.id.team_date_creation);
            joinButton = itemView.findViewById(R.id.join_team_button);
            teamPlaces = itemView.findViewById(R.id.team_places);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_a_team:
                startActivity(new Intent(SearchTeamActivity.this, CreateTeamActivity.class));
//                finish();
                break;
        }
    }
}
