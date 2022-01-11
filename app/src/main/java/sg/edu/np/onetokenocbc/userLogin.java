package sg.edu.np.onetokenocbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class userLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public EditText userEmail;
    public EditText userPassword;
    public ImageView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_login);

        mAuth = FirebaseAuth.getInstance();

        this.userEmail = findViewById(R.id.username);
        this.userPassword = findViewById(R.id.password);
        this.login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validate user input for email and password
                String email = String.valueOf(userEmail.getText());
                String password = userPassword.getText().toString();

                Log.d("email", email);
                Log.d("password", password);

                //check if email is empty
                if(email.equals("")){
                    userEmail.setError("can't be blank.");
                }
                //check if email is a valid email
                else if (!isEmailValid(email)){
                    userEmail.setError("invalid email.");
                }
                //check if password is empty and characters more than 6
                else if(password.equals("") || password.length() < 6){
                    userPassword.setError("must be more than 6 characters.");
                }
                //else sign in user
                else{
                    signIn(email, password);
                    userEmail.setText("");
                    userPassword.setText("");
                }


            }
        });
    }


    private void signIn(String email, String password){
        //use firebase method to sign in user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, bring user to homepage with the signed-in user's information
                            Log.d("create", "createUserWithEmail:success");
                            Context context = getApplicationContext();
                            Toast.makeText(context, "Log in success.", Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(Signin.this, Profilepage.class);
//                            Signin.this.startActivity(i);
                            Intent i = new Intent(userLogin.this, newHomepage.class);
                            userLogin.this.startActivity(i);

                        }
                        else {
                            Context context = getApplicationContext();
                            // If sign in fails, display a message to the user.
                            if(!password.contentEquals("def")){
                                Toast.makeText(context, "Email or Password is incorrect.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}