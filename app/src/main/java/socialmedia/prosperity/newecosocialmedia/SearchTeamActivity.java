package socialmedia.prosperity.newecosocialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchTeamActivity extends AppCompatActivity implements View.OnClickListener {
    View createATeamButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_searching_screen);

        createATeamButton = findViewById(R.id.create_a_team);
        createATeamButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_a_team:
                startActivity(new Intent(SearchTeamActivity.this, CreateTeamActivity.class));
        }
    }
}
