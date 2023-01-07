package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchTeamActivity extends AppCompatActivity implements View.OnClickListener {
    View createATeamButton;
    RecyclerView recyclerView;
//    FirebaseRecyckerAdapter adapter;

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
    String TAG = "brainfuck";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_searching_screen);

        createATeamButton = findViewById(R.id.create_a_team);
        createATeamButton.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_view_teamsearching);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//
//
//        FirebaseRecyclerOptions<Team> options = new FirebaseRecyclerOptions.Builder<Team>()
//                .setQuery(FirebaseDatabase.getInstance().getReference("/Teams"), Team.class)
//                .build();
//        adapter = new FirebaseRecyckerAdapter(options);
//        recyclerView.setAdapter(adapter);
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
                        holder.bio.setText(model.getBio());
                        holder.shortBio.setText(model.getShortBio());
                        holder.membernumber.setText(model.getMembernumber());
                        holder.dateCreation.setText(model.getDateCreated());
                        holder. joinButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String unique_team_id = getRef(position).getKey();
                                Log.d("brainfuck", "Click" + unique_team_id);
                                Intent intent = new Intent(SearchTeamActivity.this,  SplashScreen.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
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
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, bio, shortBio, membernumber, dateCreation, teamPlaces;
        View joinButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            bio = itemView.findViewById(R.id.team_bio);
            shortBio = itemView.findViewById(R.id.team_hash);
            membernumber = itemView.findViewById(R.id.team_members_number);
            dateCreation = itemView.findViewById(R.id.team_date_creation);
            joinButton = itemView.findViewById(R.id.join_team_button);

//            teamPlaces = itemView.findViewById(R.id.team_places);
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        recycle.stopListening();
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_a_team:
                startActivity(new Intent(SearchTeamActivity.this, CreateTeamActivity.class));
                finish();
                break;
        }
    }
}
