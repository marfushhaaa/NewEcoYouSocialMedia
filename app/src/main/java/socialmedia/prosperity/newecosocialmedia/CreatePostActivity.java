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
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CreatePostActivity extends AppCompatActivity {
    ImageView back_button, post_button, addImage_button, postIcon;
    ScrollView scrollView;
    EditText name_post, bio_post;
    private Uri filePath;

    String TAG = "brainfuck";
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    String postIdKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_screen);

        post_button = findViewById(R.id.post_a_post_button);
        addImage_button = findViewById(R.id.add_post_image_button);
        name_post = findViewById(R.id.create_post_name);
        bio_post = findViewById(R.id.create_post_text);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        postIcon = findViewById(R.id.post_img);
        postIdKey = UUID.randomUUID().toString();


        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: click");
                uploadImage(name_post, bio_post);
//                changeFragment(new HomePageFragment());
                // change activity to main

            }
        });

        addImage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        back_button = findViewById(R.id.back_button_post);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();
            }
        });
    }

    public void selectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    public void uploadImage(EditText editTextName, EditText editTextText) {
        String name = editTextName.getText().toString().trim();
        String text = editTextText.getText().toString().trim();
        String dateCreation = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        String userCreated = mAuth.getCurrentUser().getUid();
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

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("post/"+ postIdKey);
            Post post = new Post(name, text, postIdKey, dateCreation, userCreated);
            FirebaseDatabase.getInstance().getReference("Posts/" + postIdKey)
                    .setValue(post);
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Post posted!", Toast.LENGTH_LONG).show();
                            changeActivity();
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
    private void changeActivity(){
        Intent intent = new Intent(CreatePostActivity.this,  MainActivity.class);
        intent.putExtra("postIdKey", postIdKey);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
