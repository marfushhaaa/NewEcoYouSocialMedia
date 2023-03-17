package socialmedia.prosperity.newecosocialmedia;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class HomePageFragment extends Fragment {
    ImageView postButton;
    String postIdKey;
    TextView name, bio;
    String TAG = "brainfuck";
    RecyclerView recyclerView;
    FirebaseStorage storage;

    StorageReference storageReference;
    DatabaseReference database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_screen, null);

        postButton = view.findViewById(R.id.button_post);
        name = view.findViewById(R.id.challenge_name);
        bio = view.findViewById(R.id.challenge_info);

        database = FirebaseDatabase.getInstance().getReference().child("Challenges");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        recyclerView = view.findViewById(R.id.recycler_view_ecochallenges);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        r = view.findViewById(R.id.post_l);
        Log.d(TAG, "click soon lol");

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

        FirebaseRecyclerAdapter<Challenge, ViewHolder> recyclerAdapter =
                new FirebaseRecyclerAdapter<Challenge, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Challenge model) {
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
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ecochallenge_item, parent, false);
                        ViewHolder viewHolder = new ViewHolder(view);
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ecochallenge_name);
            image = itemView.findViewById(R.id.ecochallenge_photo);
        }
    }



}
