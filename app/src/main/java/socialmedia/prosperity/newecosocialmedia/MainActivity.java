package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView teamPhoto, addPost, addChallenge, addIdea, postIcon;
    DatabaseReference database;
    DatabaseReference database2;
    String receiverTeamId;
    RelativeLayout relativeLayout;
    ScrollView scrollView;
    FirebaseAuth mAuth;
    FrameLayout frameLayout;
    String TAG = "brainfuck";
    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_screen);

        database2 = FirebaseDatabase.getInstance().getReference().child("Users");

        frameLayout = findViewById(R.id.frameLayout);
        add();

        addChallenge = findViewById(R.id.add_challenge_button);
        addIdea = findViewById(R.id.add_idea_button);
        addPost = findViewById(R.id.add_post_button);
        //postIcon = findViewById(R.id.add_post_image_button);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //team id that he has chosen


//        if(!getIntent().getExtras().get("team_id").toString().equals("no team")){
//            receiverTeamId =getIntent().getExtras().get("team_id").toString();
//            Log.d(TAG, "if statement: " + receiverTeamId);
//
//        }else{
//            database2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    receiverTeamId = snapshot.child("team").getValue().toString();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }


        database = FirebaseDatabase.getInstance().getReference().child("Teams");

        mAuth = FirebaseAuth.getInstance();

        relativeLayout = findViewById(R.id.create_window);
//        scrollView = findViewById(R.id.create_scroll_view);
//        ScrollView scrollView = findViewById(R.id.create_scroll_view);
        Team team = new Team();
        BottomNavigationView bView = findViewById(R.id.bottom_navigation);
        bView.setSelectedItemId(R.id.home);
        bView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.addContent:
                        addContent(relativeLayout, addChallenge, addIdea, addPost);
                        return true;
                    case R.id.team:
                        changeFragment(new TeamProfileFragment());
                        return true;
                    case R.id.home:
                        changeFragment(new HomePageFragment());
                        return true;
                }
                return false;
            }
        });

    }
    public void addContent(RelativeLayout relativeLayout, ImageView bChall, ImageView bIdea, ImageView bPost){
        relativeLayout.setVisibility(View.VISIBLE);
        bPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();
            }
        });
    }

    public void receivedTeamInfo(TextView name, TextView bio, TextView members, TextView dateOfCreation){
        database2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                receiverTeamId = snapshot.child("team").getValue().toString();
                database.child(receiverTeamId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists() && snapshot.hasChild("name")){
                            Team team = new Team();


                            String team_name = snapshot.child("name").getValue().toString();
                            name.setText("“" + team_name + "”");
                            String team_bio = snapshot.child("shortBio").getValue().toString();
                            bio.setText(team_bio);
                            String team_members = snapshot.child("membernumber").getValue().toString();
                            members.setText("Учасники: " + team_members);
                            String team_dateCreation = snapshot.child("dateCreated").getValue().toString();
                            dateOfCreation.setText("Дата створення: " + team_dateCreation);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void add(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout,new HomePageFragment());
        transaction.commit();
    }
    public void changeFragment(final Fragment fragment){
        relativeLayout.setVisibility(View.INVISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
    @Override
    public void onClick(View view) {

    }
    // Select Image method
    public void selectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }
    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                postIcon.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void changeActivity(){
        Intent intent = new Intent(MainActivity.this,  CreatePostActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
    // UploadImage method
    public void uploadImage(EditText editTextName, EditText editTextText) {
        String name = editTextName.getText().toString().trim();
        String text = editTextText.getText().toString().trim();
        String dateCreation = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        String userCreated = FirebaseDatabase.getInstance().getReference(mAuth.getCurrentUser().getUid()).toString();
        if (name.isEmpty()) {
            editTextName.setError("Write your name!");
            editTextName.requestFocus();
            return;
        }
        if (text.isEmpty()) {
            editTextText.setError("Write your hashtag!");
            editTextText.requestFocus();
            return;
        }
        if (filePath != null) {
            //post id, future path
            String postIdKey = UUID.randomUUID().toString();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("post/"+ mAuth.getCurrentUser().getUid() + "/"+ postIdKey);
            Post post = new Post(name, text, postIdKey, dateCreation, userCreated);
            FirebaseDatabase.getInstance().getReference("Posts/" + postIdKey)
                    .push()
                    .setValue(post);
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Post posted!", Toast.LENGTH_LONG).show();
                        }
                    })
//                    .addOnSuccessListener(
//                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                                @Override
//                                public void onSuccess(
//                                        UploadTask.TaskSnapshot taskSnapshot)
//                                {
//
//                                    // Image uploaded successfully
//                                    // Dismiss dialog
//                                    progressDialog.dismiss();
//                                    Toast
//                                            .makeText(SignUpActivity.this,
//                                                    "Image Uploaded!!",
//                                                    Toast.LENGTH_SHORT)
//                                            .show();
//                                }
//                            })


            ;
        }
    }

//    public void post(EditText editTextName, EditText editTextText) {
//        String name = editTextName.getText().toString().trim();
//        String text = editTextText.getText().toString().trim();
//        String dateCreation = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
//
////        String totalMemberNum = "";
//
//        if (name.isEmpty()) {
//            editTextName.setError("Write your name!");
//            editTextName.requestFocus();
//            return;
//        }
//
//        if (text.isEmpty()) {
//            editTextText.setError("Write your hashtag!");
//            editTextText.requestFocus();
//            return;
//        }
//
//        Post post = new Post(name, text, )
//
//        String teamIdKey = FirebaseDatabase.getInstance().getReference("Teams").push().getKey();
//        Log.d(TAG, "mgkey:  " + teamIdKey);
//        FirebaseDatabase.getInstance().getReference("Teams/" + teamIdKey)
//                .setValue(team)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            Intent intent = new Intent(CreateTeamActivity.this,  AdminMainActivity.class);
//                            intent.putExtra("admin_id", admin_id);
//                            intent.putExtra("team_id", teamIdKey);
//                            FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .child("team")
//                                    .setValue(teamIdKey);
//                            Log.d(TAG, "admin_id: " + admin_id);
//                            Toast.makeText(getApplicationContext(),"This team has been registered", Toast.LENGTH_LONG).show();
//                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                            startActivity(intent);
//                            finish();
//                        }else{
//                            Toast.makeText(getApplicationContext(),"Failed to register!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//
//    }
}
