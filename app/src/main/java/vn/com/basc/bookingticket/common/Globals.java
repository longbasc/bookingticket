package vn.com.basc.bookingticket.common;

import vn.com.basc.bookingticket.model.Customer;

/**
 * Created by admin on 2016-04-07.
 */
public class Globals {


    public static final String SERVER_URL = "http://www.basc.com.vn/bkticket/";
    public static final String SERVER_IMAGE_URL = "http://www.basc.com.vn/bkticket/";

    public static final String APP_DOMAIN = "vn.com.basc.bookingticket";
    public static final String COOKIE_KEY_USERNAME = Globals.APP_DOMAIN + ".username";
    public static final String COOKIE_KEY_PASSWORD = Globals.APP_DOMAIN + ".password";


    public static final int BUILD_NUM = 1;



    public boolean mWasSignIn = false;
    public Customer mCustomer;
    private static Globals instance;

    // Restrict the constructor from being instantiated
    private Globals(){}


    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }


}
