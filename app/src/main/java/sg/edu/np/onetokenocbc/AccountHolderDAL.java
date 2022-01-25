package sg.edu.np.onetokenocbc;


import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountHolderDAL {

    public AccountHolderDAL(){

    };
    @SuppressLint("NewApi")
    public Connection AccountHolderConnection(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://pfd-asg2-jointacc-server.database.windows.net:1433;ssl=require;DatabaseName=JointAccHostedDB;user=pfdasg2team1@pfd-asg2-jointacc-server;password=Hongshaoji!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
            Log.d("connection", connection.toString());

        }
        catch (SQLException | ClassNotFoundException se){
            Log.d("32434",se.getMessage());
        }
        return connection;

    }
}
