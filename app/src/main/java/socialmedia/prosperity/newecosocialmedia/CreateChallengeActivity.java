package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CreateChallengeActivity extends AppCompatActivity {
    private Uri filePath;
    DatabaseReference refChallengeAmount;
    int challengeAmount;
    String teamCreated;

    String TAG = "brainfuck";
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    String challengeIdKey;
    ImageView postIcon, postChallenge;
    EditText challengeName, challengeBio, challengeHashtagDistricts, challengeHashtagTimeForDoing, challengeHashtagTeamOwner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_add_screen);

        ImageView backButton = findViewById(R.id.back_button_challenge);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        postIcon = findViewById(R.id.setImageChallenge);
        postChallenge = findViewById(R.id.postAChallengeB);
        challengeIdKey = UUID.randomUUID().toString();

        challengeName = findViewById(R.id.challengeName);
        challengeBio = findViewById(R.id.challengeBio);
        challengeHashtagDistricts = findViewById(R.id.challengeDistricts);
        challengeHashtagTimeForDoing = findViewById(R.id.challengeTimeForDoing);
        challengeHashtagTeamOwner = findViewById(R.id.challengeTeamOwn);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("team");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teamCreated = snapshot.getValue(String.class);
                DatabaseReference refChallengeAmount = FirebaseDatabase.getInstance().getReference("Teams/")
                        .child(teamCreated).child("challengeCount");
                refChallengeAmount.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        challengeAmount = snapshot.getValue(Integer.class);
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




        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();
            }
        });

        postIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        postChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(challengeName, challengeBio, challengeHashtagDistricts, challengeHashtagTimeForDoing, challengeHashtagTeamOwner);
            }
        });

    }
    private void changeActivity(){
        Intent intent = new Intent(CreateChallengeActivity.this,  MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
    public void selectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    public void uploadImage(EditText challengeName, EditText challengeBio, EditText challengeHashtagDistricts, EditText challengeHashtagTimeForDoing,
                          EditText challengeHashtagTeamOwner) {
        String name = challengeName.getText().toString().trim();
        String bio = challengeBio.getText().toString().trim();
        String district = challengeHashtagDistricts.getText().toString().trim();
        String timeForDoing = challengeHashtagTimeForDoing.getText().toString().trim();
        String teamOwnHashtag = challengeHashtagTeamOwner.getText().toString().trim();


        String dateCreation = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());

        if (name.isEmpty()) {
            challengeName.setError("Write your name!");
            challengeName.requestFocus();
            return;
        }
        if (bio.isEmpty()) {
            challengeBio.setError("Write your text!");
            challengeBio.requestFocus();
            return;
        }
        if (district.isEmpty()) {
            challengeHashtagDistricts.setError("Write your hashtag!");
            challengeHashtagDistricts.requestFocus();
            return;
        }
        if (timeForDoing.isEmpty()) {
            challengeHashtagTimeForDoing.setError("Write your hashtag!");
            challengeHashtagTimeForDoing.requestFocus();
            return;
        }
        if (teamOwnHashtag.isEmpty()) {
            challengeHashtagTeamOwner.setError("Write your hashtag!");
            challengeHashtagTeamOwner.requestFocus();
            return;
        }

        if (filePath != null) {
            //post id, future path

            // Defining the child of storageReference
            StorageReference ref2 = storageReference.child("challenge/"+ challengeIdKey);
            Challenge challenge = new Challenge(name, bio, district, timeForDoing, teamOwnHashtag, challengeIdKey, dateCreation, teamCreated);
            FirebaseDatabase.getInstance().getReference("Teams/" + teamCreated).child("challengeCount").setValue(challengeAmount+1);
            String challengeAmountString = String.valueOf(challengeAmount+1);
            FirebaseDatabase.getInstance().getReference("Teams/"  + teamCreated + "/challenges")
                    .child(challengeAmountString)
                    .setValue(challengeIdKey);
            FirebaseDatabase.getInstance().getReference("Challenges/" + challengeIdKey)
                    .setValue(challenge);
            // adding listeners on upload
            // or failure of image
            ref2.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Challenge posted!", Toast.LENGTH_LONG).show();
                            changeActivity2();
                        }
                    })


            ;
        }
    }

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
    private void changeActivity2(){
        Intent intent = new Intent(CreateChallengeActivity.this,  MainActivity.class);
        intent.putExtra("postIdKey", challengeIdKey);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
