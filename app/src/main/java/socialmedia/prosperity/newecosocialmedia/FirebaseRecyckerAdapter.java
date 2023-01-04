package socialmedia.prosperity.newecosocialmedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class FirebaseRecyckerAdapter extends FirebaseRecyclerAdapter<Team, FirebaseRecyckerAdapter.ViewHolder> {

    public FirebaseRecyckerAdapter(@NonNull FirebaseRecyclerOptions<Team> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Team model) {
        holder.name.setText(model.getName());
        holder.bio.setText(model.getBio());
        holder.hashtag.setText(model.getHash());
        holder.membernumber.setText(model.getMembernumber());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, bio, hashtag, membernumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            bio = itemView.findViewById(R.id.team_bio);
            hashtag = itemView.findViewById(R.id.team_hash);
            membernumber = itemView.findViewById(R.id.team_members_number);
        }
    }
}
