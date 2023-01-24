package socialmedia.prosperity.newecosocialmedia;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;

public class AdminMainActivity extends AppCompatActivity {
    String receiverAdminId;
    String receiverTeamId;
    String TAG = "brainfuck";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_admin_profile_fragment);

        receiverAdminId =getIntent().getExtras().get("admin_id").toString();
        receiverTeamId =getIntent().getExtras().get("team_id").toString();
        Log.d(TAG, "admin_id in mainActivity: " + receiverAdminId);
        FirebaseDatabase.getInstance().getReference("Teams/" + receiverTeamId)
                .child("admin")
                .setValue(receiverAdminId);
        Log.d(TAG, "put admin_id in db: " + receiverAdminId);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
