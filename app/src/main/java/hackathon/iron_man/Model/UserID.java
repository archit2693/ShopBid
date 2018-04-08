package hackathon.iron_man.Model;

import android.support.annotation.NonNull;

/**
 * Created by sakshi on 7/4/18.
 */

public class UserID {

    public String userId;

    public <T extends UserID> T withId(@NonNull final String id)
    {
        this.userId=id;
        return (T) this;
    }
}
