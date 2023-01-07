package socialmedia.prosperity.newecosocialmedia;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Assert;
import org.junit.Test;

public class CreateTeamTest {
    @Test
    public void shouldCreateTeam(){
        CreateTeamActivity createTeamActivity = new CreateTeamActivity();
//        createTeamActivity.registerTeam("Team", "#hashtag", "13", "we are a team", "07.01.2023");
        Assert.assertFalse(FirebaseDatabase.getInstance().getReference("Teams").toString().isEmpty());
    }
}
