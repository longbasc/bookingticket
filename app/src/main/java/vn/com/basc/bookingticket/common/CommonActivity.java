package vn.com.basc.bookingticket.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.com.basc.bookingticket.R;
import vn.com.basc.bookingticket.network.IWSResponse;

/**
 * Created by admin on 2016-04-07.
 */
public class CommonActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,IWSResponse
{

    protected ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //==================================================================================================
    public void setupHideKeyboard(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(CommonActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupHideKeyboard(innerView);
            }
        }
    }

    //==================================================================================================
    protected void setCommonUI(){


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top_bar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView bt_left_top_bar = (TextView) findViewById(R.id.bt_left_top_bar);
        bt_left_top_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonActivity.this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                finish();
            }
        });
    }

    //==================================================================================================
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public void processWSFinish(String wsCallingCode, String output){
        hideWaitingDialog();
    }
    public void processWSError(String wsCallingCode, String error){
        hideWaitingDialog();
        Toast.makeText(this,"Please check network", Toast.LENGTH_LONG);
    }

    public void processFileFinish(String wsCallingCode, String output, Object sourceInfo){

    }

    public void processFileError(String wsCallingCode, String error, Object sourceInfo){

    }


    //==================================================================================================
    protected  void showWaitingDialog(String title, String message){

        if(mProgress != null)
            return;


        mProgress = ProgressDialog.show(this, title,
                message, true);


    }

    //==================================================================================================
    protected  void hideWaitingDialog(){
        if(mProgress!=null) {
            mProgress.dismiss();
            mProgress = null;
        }
    }

    //==================================================================================================
    protected  void showWaitingDialog(){

        if(mProgress != null)
            return;

        mProgress = ProgressDialog.show(this, "","Processing", true);

    }


}
