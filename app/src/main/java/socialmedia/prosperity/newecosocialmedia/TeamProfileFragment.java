package socialmedia.prosperity.newecosocialmedia;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class TeamProfileFragment extends Fragment {
    TextView name, bio, dateOfCreation, members;
    RecyclerView recyclerView;
    FirebaseStorage storage;

    StorageReference storageReference;
    DatabaseReference database;
    ImageView back;
    RelativeLayout rl;
    //new devise test
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_profile_screen, null);
        name = view.findViewById(R.id.team_name_teamprofile);
        bio = view.findViewById(R.id.team_bio_teamprofile);
        dateOfCreation = view.findViewById(R.id.team_dateofcreation_teamprofile);
        members = view.findViewById(R.id.team_members_teamprofile);

        ((MainActivity)getActivity()).receivedTeamInfo(name, bio, dateOfCreation, members);

        recyclerView = view.findViewById(R.id.recycler_view_teamposts);
        recyclerView.setLayoutManager(new LinearLayoutManager(((MainActivity)getActivity()).getBaseContext()));
        rl = view.findViewById(R.id.rl3);
        database = FirebaseDatabase.getInstance().getReference().child("Posts");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        return view;
    }


    public void changeFragment(final Fragment fragment, ImageView button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.commit();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(FirebaseDatabase.getInstance().getReference("/Posts"), Post.class)
                .build();

        FirebaseRecyclerAdapter<Post, ViewHolder> recyclerAdapter =
                new FirebaseRecyclerAdapter<Post, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Post model) {
                        holder.name.setText(model.getName());
                        holder.bio.setText(model.getText());
                        storageReference.child("post/" + model.getPhoto_link()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(holder.image);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_post_item, parent, false);
                        ViewHolder viewHolder = new ViewHolder(view);
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, bio;
        ImageView image, imageBgr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.post_name);
            bio = itemView.findViewById(R.id.post_text);
            image = itemView.findViewById(R.id.post_image);
            imageBgr = itemView.findViewById(R.id.post_background);
        }
    }
}
