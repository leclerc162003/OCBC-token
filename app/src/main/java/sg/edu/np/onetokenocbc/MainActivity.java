package sg.edu.np.onetokenocbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public EditText Email;
    public EditText Password;
    public Button signIn;
    public ImageView login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        this.login = findViewById(R.id.loginmain);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, userLogin.class);
                MainActivity.this.startActivity(i);
            }
        });

//        this.Email = findViewById(R.id.userEmail);
//        this.Password = findViewById(R.id.userPassword);
//        this.signIn = findViewById(R.id.signin);

//        signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //validate user input for email and password
//                String email = String.valueOf(Email.getText());
//                String password = Password.getText().toString();
//
//                //check if email is empty
//                if(email.equals("")){
//                    Email.setError("can't be blank.");
//                }
//                //check if email is a valid email
//                else if (!isEmailValid(email)){
//                    Email.setError("invalid email.");
//                }
//                //check if password is empty and characters more than 6
//                else if(password.equals("") || password.length() < 6){
//                    Password.setError("must be more than 6 characters.");
//                }
//                //else sign in user
//                else{
//                    signIn(email, password);
//                    Email.setText("");
//                    Password.setText("");
//                }
//
//
//            }
//        });








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
                            Intent i = new Intent(MainActivity.this, Home.class);
                            MainActivity.this.startActivity(i);

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