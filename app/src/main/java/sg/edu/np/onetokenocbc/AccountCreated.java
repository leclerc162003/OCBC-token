package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

public class AccountCreated extends AppCompatActivity {
    public CountDownTimer myCountDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_account_created);


        startTimer(2);


    }


    private void startTimer(int time){
        myCountDown = new CountDownTimer(time*1000, 1000 ){
            public void onTick(long millisUntilFinished){



            }

            public void onFinish(){
                myCountDown.cancel();

                Intent i = new Intent(AccountCreated.this, newHomepage.class);
                AccountCreated.this.startActivity(i);







            }

        };
        myCountDown.start();
    }
}