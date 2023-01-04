package socialmedia.prosperity.newecosocialmedia;

import android.os.Bundle;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    EditText editTextName, editTextNickname, editTextBio, editTextPassword, editTextEmail;
    ImageView signUpButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.editTextTextPersonName);
        editTextNickname = findViewById(R.id.editTextTeamHashtag);
        editTextBio = findViewById(R.id.editTextTextPersonBIo);
        editTextPassword = findViewById(R.id.editTextTeamMembers);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);

        signUpButton = findViewById(R.id.signin_button);
        signUpButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signin_button:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String nickname = editTextNickname.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String bio = editTextBio.getText().toString().trim();

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


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            User user = new User(name, nickname, email, bio);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"User has been registered", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Failed to register!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(),"Failed to register!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
