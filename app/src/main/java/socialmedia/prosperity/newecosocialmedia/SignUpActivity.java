package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

import socialmedia.prosperity.newecosocialmedia.Objects.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    EditText editTextName, editTextNickname, editTextBio, editTextPassword, editTextEmail, editTextCheckPassword;
    ImageView signUpButton, backButton, addPhotoIcon, personIcon;

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
        setContentView(R.layout.sign_up_screen);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.editTextPersonName);
        editTextNickname = findViewById(R.id.editTextPersonNickname);
        editTextBio = findViewById(R.id.editTextPersonBio);
        editTextPassword = findViewById(R.id.editTextPersonPassword);
        editTextEmail = findViewById(R.id.editTextPersonEmail);
        editTextCheckPassword = findViewById(R.id.editTextRepeatPersonPassword);

        signUpButton = findViewById(R.id.signin_button);
        signUpButton.setOnClickListener(this);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        addPhotoIcon = findViewById(R.id.add_photo_icon);
        personIcon = findViewById(R.id.personImage);
        addPhotoIcon.setOnClickListener(this);

        //String user_id = mAuth.getCurrentUser().getUid();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signin_button:
                registerUser();
                break;
            case R.id.back_button:
                back();
                break;
            case R.id.add_photo_icon:
                selectImage();
                break;
        }
    }
    // Select Image method
    private void selectImage() {
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                personIcon.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage() {
        if (filePath != null) {
            // Defining the child of storageReference
            StorageReference ref = storageReference.child("users/"+ mAuth.getCurrentUser().getUid() + "/"+ UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
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

    private void back(){
        Intent intent = new Intent(SignUpActivity.this,  SplashScreen.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String nickname = editTextNickname.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String bio = editTextBio.getText().toString().trim();
        String repeatedPassword = editTextCheckPassword.getText().toString().trim();
        String team = "no team";

        if(name.isEmpty()){
            editTextName.setError("Write your name!");
            editTextName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Write your email!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Write your email correctly!");
            editTextEmail.requestFocus();
            return;
        }

        if(nickname.isEmpty()){
            editTextNickname.setError("Write your nickname!");
            editTextNickname.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Write your password!");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            editTextPassword.setError("Password must be at least 6 symbols");
            editTextPassword.requestFocus();
            return;
        }

        if(bio.isEmpty()){
            editTextBio.setError("Write your bio!");
            editTextBio.requestFocus();
            return;
        }

        if (!password.equals(repeatedPassword)){
            editTextCheckPassword.setError("Password has written incorrectly!");
            editTextCheckPassword.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            User user = new User(name, nickname, email, bio, team);
//                            FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .child("team")
//                                    .setValue("");
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"User has been registered", Toast.LENGTH_LONG).show();
                                        uploadImage();
                                        Intent intent = new Intent(SignUpActivity.this,  SplashScreen.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Failed to register!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
//                        else{
//                            Toast.makeText(getApplicationContext(),"Failed to register!", Toast.LENGTH_LONG).show();
//                        }
                    }
                });
    }
}
