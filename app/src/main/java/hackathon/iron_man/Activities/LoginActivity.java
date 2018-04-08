package hackathon.iron_man.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import hackathon.iron_man.ChoiceActivity;
import hackathon.iron_man.Home.MainActivity;
import hackathon.iron_man.R;
import hackathon.iron_man.SessionManagement;

public class LoginActivity extends AppCompatActivity {

    Button new_account,login;
    EditText email,pass;

    private ProgressBar progressBar;
    private FirebaseAuth mauth;
    SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        new_account = (Button) findViewById(R.id.go_to_create_account);
        login = (Button) findViewById(R.id.login_1);

        email = (EditText) findViewById(R.id.edit1);
        pass = (EditText) findViewById(R.id.edit2);

        session = new SessionManagement(getApplicationContext());


        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        Log.e("currentime",timeStamp);

        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);

        mauth= FirebaseAuth.getInstance();
        Log.e("currentime",mauth.toString());


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logging_in();
            }
        });
    }


    public void new_Account(View v)
    {
//        Intent i=new Intent(this,RegisterActivity.class);
//        startActivity(i);
        finish();
    }

    public void logging_in()
    {
        final String m_email=email.getText().toString();
        final String m_pass=pass.getText().toString();


        if(!TextUtils.isEmpty(m_email) && !TextUtils.isEmpty(m_pass) )
        {
            session.createLoginSession(m_email,m_pass);
          //  Log.e("added to shred pref", session.getUserDetails().get(SessionManagement.KEY_EMAIL) +" "+
            //                        session.getUserDetails().get(SessionManagement.KEY_PASS));

            Log.e("names ",m_email+"emilsjbhadb"+m_pass);
            progressBar.setVisibility(View.VISIBLE);
            mauth.signInWithEmailAndPassword(m_email,m_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                       //do it sendToMain();
                        Toast.makeText(LoginActivity.this, "Login Successful! ", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);

                        Intent toItem=new Intent(LoginActivity.this, MainActivity.class);
                        toItem.putExtra("email",m_email);
                        startActivity(toItem);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Error logging in"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         progressBar.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }
    }

}
