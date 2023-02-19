package socialmedia.prosperity.newecosocialmedia;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    String TAG = "brainfuck";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_screen, null);

        postButton = view.findViewById(R.id.button_post);

//        r = view.findViewById(R.id.post_l);
        Log.d(TAG, "click soon lol");

        Log.d(TAG, "click lol");
        ((MainActivity)getActivity()).changeActivity2(postButton);

//        // we will get the default FirebaseDatabase instance
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//
//        // we will get a DatabaseReference for the database
//        // root node
//        DatabaseReference databaseReference = firebaseDatabase.getReference();
//
//        DatabaseReference getImage = databaseReference.child("image");
//        postsRef = FirebaseDatabase.getInstance().getReference("/Posts");
//        postIdKey = ((MainActivity)getActivity()).postIdKey;
//        postsRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(postIdKey);
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
//        storageReference = storageReference.child("post/"+ postIdKey);
//        Log.d(TAG, "post_ref: " + storageReference.toString());


//        if(((MainActivity)getActivity()).a != 0){
//
//        }

//        postsView = view.findViewById(R.id.recycler_view_posts);
//        postsView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
//    public void onStart(){
//        super.onStart();
//        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
//                .setQuery(FirebaseDatabase.getInstance().getReference("/Posts"), Post.class)
//                .build();
//        FirebaseRecyclerAdapter<Post, PostsViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Post, PostsViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull Post model) {
////                String postId = getRef(position).getKey();
//                holder.postName.setText(model.getName());
//                holder.postText.setText(model.getText());
//                String postImage = storageReference.toString();
//                Log.d(TAG, "post: " + postImage);
//
//
//                Picasso.get().load(postImage).into(holder.postImg);
//                postsRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
////                FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                        if(snapshot.hasChild("Posts")){
////                            String
////                        }
////                    }
////
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError error) {
////
////                    }
////                })
//            }
//
//            @NonNull
//            @Override
//            public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_post_item, parent, false);
//                PostsViewHolder postsViewHolder = new PostsViewHolder(view);
//                return postsViewHolder;
//            }
//        };
//
//        postsView.setAdapter(adapter);
//        adapter.startListening();
//    }

//    public static class PostsViewHolder extends RecyclerView.ViewHolder{
//
//        TextView postName, postText;
//        ImageView postImg;
//        public PostsViewHolder(@NonNull View itemView) {
//            super(itemView);
//            postName = itemView.findViewById(R.id.post_name);
//            postImg = itemView.findViewById(R.id.post_image);
//            postText = itemView.findViewById(R.id.post_text);
//
//        }
//    }



}
