package sg.edu.np.onetokenocbc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
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

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class userLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public EditText userEmail;
    public EditText userPassword;
    public ImageView login;
    public ImageView loginBiometric;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_login);

        mAuth = FirebaseAuth.getInstance();

        this.userEmail = findViewById(R.id.loginID);
        this.userPassword = findViewById(R.id.loginpassword);
        this.login = findViewById(R.id.loginSi);
        this.loginBiometric = findViewById(R.id.loginFi);

        // Biometrics
        Executor executor = Executors.newSingleThreadExecutor();
        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Fingerprint Authentication")
                .setSubtitle("Please touch the fingerprint sensor to login to the OCBC Application.")
                .setDescription("")
                .setNegativeButton("Cancel", executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).build();

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
                    SharedPreferences.Editor loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                    loginInfo.putString("email", email);
                    loginInfo.putString("password", password);
                    loginInfo.apply();
                    signIn(email, password);
                    userEmail.setText("");
                    userPassword.setText("");
                }


            }
        });

        userLogin activity = this;

        loginBiometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
                String email = loginInfo.getString("email", "def");
                String password = loginInfo.getString("password", "def");
                if (password == "def" || email == "def"){
                    new AlertDialog.Builder(userLogin.this)
                            .setTitle("Please Log In to enable fingerprint log in.")
                            .setMessage("You need to log in first to enable fingerprint log in.")
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton("Close", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else{
                    Log.d("onClick", "clicked");
                    biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("onClick", "Authenticated");
                                    SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
                                    String email = loginInfo.getString("email", "def");
                                    String password = loginInfo.getString("password", "def");
                                    signIn(email, password);
                                    userEmail.setText("");
                                    userPassword.setText("");
                                }
                            });
                        }
                        @Override
                        public void onAuthenticationError(int errorCode, CharSequence errString) {
                            Log.d("AuthenticationError",errString.toString());
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            Log.d("AuthenticationError","Failed");
                        }
                    });
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