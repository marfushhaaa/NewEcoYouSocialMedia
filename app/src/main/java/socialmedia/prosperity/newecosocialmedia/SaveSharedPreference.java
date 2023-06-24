package socialmedia.prosperity.newecosocialmedia;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_NAME = "username";
    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_NAME, username);
        editor.commit();
    }

    public static String getUserName(Context context){
        return getSharedPreferences(context).getString(PREF_USER_NAME, "");
    }

}
