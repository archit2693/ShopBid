package hackathon.iron_man;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Iron_Man on 07/04/17.
 */

public class AccountProfile extends AppCompatActivity {

    LinearLayout linear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(hackathon.iron_man.R.layout.account_profile);
        linear=(LinearLayout)findViewById(hackathon.iron_man.R.id.linearSec);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Upload Code

            }
        });

    }
}
