package socialmedia.prosperity.newecosocialmedia;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import socialmedia.prosperity.newecosocialmedia.Objects.Challenge;
import socialmedia.prosperity.newecosocialmedia.Objects.Idea;

public class HomePageFragment extends Fragment {
    ImageView postButton;
    String postIdKey;
    TextView name, bio;
    String TAG = "brainfuck";
    RecyclerView challengeRecyclerView, ideaRecyclerView;
    FirebaseStorage storage;

    StorageReference storageReference;
    DatabaseReference challengeDatabase, ideaDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_screen, null);

        postButton = view.findViewById(R.id.button_post);
        name = view.findViewById(R.id.challenge_name);
        bio = view.findViewById(R.id.challenge_info);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //challenges
        challengeDatabase = FirebaseDatabase.getInstance().getReference().child("Challenges");
        challengeRecyclerView = view.findViewById(R.id.recycler_view_ecochallenges);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        challengeRecyclerView.setLayoutManager(linearLayoutManager);
        Log.d(TAG, "click soon lol");

        //ideas
        ideaDatabase = FirebaseDatabase.getInstance().getReference().child("Ideas");
        ideaRecyclerView = view.findViewById(R.id.recycler_view_ecoideas);
        LinearLayoutManager idealinearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        ideaRecyclerView.setLayoutManager(idealinearLayoutManager);
        Log.d(TAG, "click lol");
        ((MainActivity)getActivity()).changeActivity2(postButton);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Challenge> options = new FirebaseRecyclerOptions.Builder<Challenge>()
                .setQuery(FirebaseDatabase.getInstance().getReference("/Challenges"), Challenge.class)
                .build();
        FirebaseRecyclerOptions<Idea> options2 = new FirebaseRecyclerOptions.Builder<Idea>()
                .setQuery(FirebaseDatabase.getInstance().getReference("/Ideas"), Idea.class)
                .build();

        FirebaseRecyclerAdapter<Challenge, challegeViewHolder> challengesRecyclerAdapter =
                new FirebaseRecyclerAdapter<Challenge, challegeViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull challegeViewHolder holder, int position, @NonNull Challenge model) {
                        holder.name.setText(model.getChallengeName());

                        storageReference.child("challenge/" + model.getChallengePhotoLink()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                    public challegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ecochallenge_item, parent, false);
                        challegeViewHolder challegeViewHolder = new challegeViewHolder(view);
                        return challegeViewHolder;
                    }
                };

        FirebaseRecyclerAdapter<Idea, ideasViewHolder> ideasRecyclerAdapter =
                new FirebaseRecyclerAdapter<Idea, ideasViewHolder>(options2) {
                    @Override
                    protected void onBindViewHolder(@NonNull ideasViewHolder holder, int position, @NonNull Idea model) {
                        holder.name.setText(model.getName());

                        storageReference.child("ideas/" + model.getPhotoLink()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                    public ideasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ecochallenge_item, parent, false);
                        ideasViewHolder ideasViewHolder = new ideasViewHolder(view);
                        return ideasViewHolder;
                    }
                };

        challengeRecyclerView.setAdapter(challengesRecyclerAdapter);
        challengesRecyclerAdapter.startListening();

        ideaRecyclerView.setAdapter(ideasRecyclerAdapter);
        ideasRecyclerAdapter.startListening();
    }

    public static class challegeViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;
        public challegeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ecochallenge_name);
            image = itemView.findViewById(R.id.ecochallenge_photo);
        }
    }
    public static class ideasViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;
        public ideasViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ecochallenge_name);
            image = itemView.findViewById(R.id.ecochallenge_photo);
        }
    }



}
