package socialmedia.prosperity.newecosocialmedia;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import socialmedia.prosperity.newecosocialmedia.Objects.Team;

public class FirebaseRecyckerAdapter extends FirebaseRecyclerAdapter<Team, FirebaseRecyckerAdapter.ViewHolder> {


    public FirebaseRecyckerAdapter(@NonNull FirebaseRecyclerOptions<Team> options) {
        super(options);
    }

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

            }
        });
//        holder.teamPlaces.setText(model.getTotalMembers());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);
        return new ViewHolder(view);
    }
    public void loadAnActivity(){

    }
    class ViewHolder extends RecyclerView.ViewHolder{
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
}
