package vn.com.basc.bookingticket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import vn.com.basc.bookingticket.common.CommonActivity;
import vn.com.basc.bookingticket.common.Globals;
import vn.com.basc.bookingticket.model.Customer;
import vn.com.basc.bookingticket.network.WSAsyncTask;

public class TestNavActivity extends CommonActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nav);
        setCommonUI();



        TextView lb_center_top_bar = (TextView) findViewById(R.id.lb_center_top_bar);
        lb_center_top_bar.setText("BookingTicket.vn");

        RelativeLayout container_bottom_bar = (RelativeLayout) findViewById(R.id.include_bottom);
        container_bottom_bar.setBackgroundColor(Color.parseColor("#FFFF0000"));

        Button bt_left_bottom_bar = (Button) findViewById(R.id.bt_left_bottom_bar);
        bt_left_bottom_bar.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        /*
        TextView bt_left_top_bar = (TextView) findViewById(R.id.bt_left_top_bar);
        bt_left_top_bar.setVisibility(View.GONE);
        */

    }



    //===========================================================================
    public void onClickBtContact(View c){

        AlertDialog alertDialog = new AlertDialog.Builder(TestNavActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Go to contact page");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        /*
        Intent myIntent = new Intent(CurrentActivity.this, NextActivity.class);
        myIntent.putExtra("key", value); //Optional parameters
        CurrentActivity.this.startActivity(myIntent);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
        */
    }

    //===========================================================================
    public void onClickBtSignIn(View c){

        SharedPreferences prefs = this.getSharedPreferences(
                Globals.APP_DOMAIN, Context.MODE_PRIVATE);

        //Globals g = Globals.getInstance();


        String Username = prefs.getString(Globals.COOKIE_KEY_USERNAME, "longnguyen");
        String password = prefs.getString(Globals.COOKIE_KEY_PASSWORD, "password");


        showWaitingDialog();
        WSAsyncTask wsTask = new WSAsyncTask(TestNavActivity.this);
        wsTask.delegate = TestNavActivity.this;

        //post
        wsTask.execute("test_post.php",
                "Username=" + Username
                        + "&Password=" + password

                , "SIGN_IN");


        /*
        wsTask.execute("test_get.php?Username=" + Username
                        + "&Password=" + password
                ,"HTTP_GET"
                , "SIGN_IN");
        */

    }

    //===========================================================================
    public void processWSFinish(String wsCallingCode, String output){
        super.processWSFinish(wsCallingCode, output);
        if (output == null) {
            return;
        }

        try {
            Globals g = Globals.getInstance();
            JSONObject jsonData = new JSONObject(output);

            if(wsCallingCode.equalsIgnoreCase("SIGN_IN")) {

                int returnCode = jsonData.getInt("status");  //[[ResultData objectForKey:@"data"] intValue];
                if(returnCode == 200)
                {
                    Toast.makeText(getApplicationContext(), "sign in ok.",
                            Toast.LENGTH_SHORT).show();

                    g.mCustomer = new Customer();
                    g.mCustomer.mUserName = "longnguyen";
                    g.mCustomer.mPassword = "password";
                    g.mWasSignIn = true;


                    SharedPreferences prefs = this.getSharedPreferences(Globals.APP_DOMAIN, Context.MODE_PRIVATE);
                    prefs.edit().putString(Globals.COOKIE_KEY_USERNAME, g.mCustomer.mUserName).apply();
                    prefs.edit().putString(Globals.COOKIE_KEY_PASSWORD, g.mCustomer.mPassword).apply();


                }
                else{
                    Toast.makeText(getApplicationContext(), "Error: Could not sign in.",
                            Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Throwable t) {
            //Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
    }

    /*
    public void processWSError(String wsCallingCode, String error){
        Toast.makeText(this,"Please check network",Toast.LENGTH_LONG);
    }
    */

}