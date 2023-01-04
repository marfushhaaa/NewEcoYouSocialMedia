package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchTeamActivity extends AppCompatActivity implements View.OnClickListener {
    View createATeamButton;
    RecyclerView recyclerView;
    FirebaseRecyckerAdapter adapter;

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



        FirebaseRecyclerOptions<Team> options = new FirebaseRecyclerOptions.Builder<Team>()
                .setQuery(FirebaseDatabase.getInstance().getReference("/Teams"), Team.class)
                .build();
        adapter = new FirebaseRecyckerAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

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
