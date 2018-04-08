package hackathon.iron_man.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import hackathon.iron_man.ChoiceActivity;
import hackathon.iron_man.R;
import hackathon.iron_man.SessionManagement;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    Button go_login, create_button;
    EditText name, email_s, pass_s;
    TextView tv;

    private Uri imageuri;
    SessionManagement sessionManagement;
    private ImageView imageView;

    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ProgressBar mprogressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sessionManagement = new SessionManagement(this);
        go_login = (Button) findViewById(R.id.back_to_login);
        create_button = (Button) findViewById(R.id.create_my_account);
        tv = (TextView) findViewById(R.id.create_new_account);

        imageuri = null;

        imageView = (ImageView) findViewById(R.id.profile_photo);

        name = (EditText) findViewById(R.id.name_signup);
        email_s = (EditText) findViewById(R.id.email_signup);
        pass_s = (EditText) findViewById(R.id.pass_signup);

        storageReference = FirebaseStorage.getInstance().getReference().child("images");
        //directory where images will be stored

        firebaseAuth = FirebaseAuth.getInstance();      //authentication
        firebaseFirestore = FirebaseFirestore.getInstance(); //used for storing data

        mprogressbar = (ProgressBar) findViewById(R.id.register_progressbar);
    }

    public void back_login(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void picture_select(View v)           //onclick of the circle imageview
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            imageuri = data.getData();
            imageView.setImageURI(imageuri);
        }
    }


    public void start_register(View v) {
        if (imageuri != null) {

            mprogressbar.setVisibility(View.VISIBLE);

            final String my_name = name.getText().toString();
            final String my_email = email_s.getText().toString();
            final String my_pass = pass_s.getText().toString();

            if (!TextUtils.isEmpty(my_name) && !TextUtils.isEmpty(my_email) && !TextUtils.isEmpty(my_pass)) {
                firebaseAuth.createUserWithEmailAndPassword(my_email, my_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            final String user_id = firebaseAuth.getCurrentUser().getUid();
                            StorageReference user_profile = storageReference.child(user_id + ".jpg");

                            user_profile.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadtask) {

                                    if (uploadtask.isSuccessful()) {
                                        String download_url = uploadtask.getResult().getDownloadUrl().toString(); //download url of image//


                                        Map<String, Object> usermap = new HashMap<>();
                                        usermap.put("name", my_name);
                                        usermap.put("image", download_url);

                                        firebaseFirestore.collection("Users").document(user_id).set(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                mprogressbar.setVisibility(View.INVISIBLE);
                                                sessionManagement.createLoginSession(my_email,my_pass);
                                                send_to_main();
                                            }
                                        });
                                    } else {
                                        mprogressbar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(RegisterActivity.this, "Error: in users " + uploadtask.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Failed to proceed registeration", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            mprogressbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterActivity.this, "Error in emailpass: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }else{
            Toast.makeText(this, "Please Select your profile image first.", Toast.LENGTH_LONG).show();
        }

    }

    public void send_to_main() {

        Intent main_intent = new Intent(this, ChoiceActivity.class);
        startActivity(main_intent);
        finish();
    }
}

