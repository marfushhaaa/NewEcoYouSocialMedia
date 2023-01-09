package socialmedia.prosperity.newecosocialmedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeamProfileFragment extends Fragment {
    TextView name, bio, dateOfCreation, members;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_profile_screen, null);
        name = view.findViewById(R.id.team_name_teamprofile);
        bio = view.findViewById(R.id.team_bio_teamprofile);
        dateOfCreation = view.findViewById(R.id.team_dateofcreation_teamprofile);
        members = view.findViewById(R.id.team_members_teamprofile);

        ((MainActivity)getActivity()).receivedTeamInfo(name, bio, dateOfCreation, members);
        return view;
    }


    public void changeFragment(final Fragment fragment, ImageView button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.commit();
            }
        });

    }
}
