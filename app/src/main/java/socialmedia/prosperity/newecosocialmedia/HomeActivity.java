package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseStorage storage;

    StorageReference storageReference;
    DatabaseReference database;
    ImageView back;
    RelativeLayout rl;

    @Override
    public void onBackPressed() {

    }

    String TAG = "brainfuck";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        recyclerView = findViewById(R.id.recycler_view_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rl = findViewById(R.id.rl2);

        back = findViewById(R.id.back_button_posts);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
        database = FirebaseDatabase.getInstance().getReference().child("Posts");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
//        storageReference = storageReference.child("post/");


    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_post, null);

        // create the popup window
        int width = RelativeLayout.LayoutParams.MATCH_PARENT;
        int height = RelativeLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


//        ImageView addChallenge = popupView.findViewById(R.id.);
        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken


    }

    @Override
    protected void onStart() {
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
                        holder.imageBgr.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onButtonShowPopupWindowClick(rl);
                                //load a popup window
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
