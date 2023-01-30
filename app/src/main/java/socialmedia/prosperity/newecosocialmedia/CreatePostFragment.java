package socialmedia.prosperity.newecosocialmedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class CreatePostFragment extends Fragment {
    ImageView back_button, post_button, addImage_button;
    EditText name_post, bio_post;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_post_screen, null);

        post_button = view.findViewById(R.id.post_a_post_button);
        addImage_button = view.findViewById(R.id.add_post_image_button);
        name_post = view.findViewById(R.id.create_post_name);
        bio_post = view.findViewById(R.id.create_post_text);


        ((MainActivity)getActivity()).postIcon = view.findViewById(R.id.post_img);
        addImage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).selectImage();
            }
        });
        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).uploadImage();
                changeFragment(new HomePageFragment(), post_button);

            }
        });

        back_button = view.findViewById(R.id.back_button_post);
        changeFragment(new HomePageFragment(), back_button);

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
