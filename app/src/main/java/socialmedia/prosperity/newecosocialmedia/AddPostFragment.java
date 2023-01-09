package socialmedia.prosperity.newecosocialmedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AddPostFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_post_screen, null);


        return view;
    }

//    public void changeFragment(final Fragment fragment, ImageView button){
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.walls, fragment);
//                transaction.commit();
//            }
//        });
//
//    }
}
