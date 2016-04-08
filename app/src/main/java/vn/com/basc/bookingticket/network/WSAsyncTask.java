package vn.com.basc.bookingticket.network;

import android.content.Context;
import android.os.AsyncTask;
import vn.com.basc.bookingticket.common.Globals;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by longnguyen on 14/08/2015.
 */
public class WSAsyncTask extends AsyncTask<String, Integer, String> {

    public IWSResponse delegate=null;
    private String wsCallingCode = "";
    Context context;
    private int mTryCount = 2;



    public WSAsyncTask(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... urlparams) {



        try {
            Globals g = Globals.getInstance();
            String request = Globals.SERVER_URL + urlparams[0] ;//  "http://104.238.116.179:8000/mv_api/gateway.php?controller=store.get_store_info";
            String postParams = urlparams[1]; //"StoreID=1";
            wsCallingCode = urlparams[2];

            if(postParams.equalsIgnoreCase("HTTP_GET")){

                HttpGet httppost = new HttpGet(request);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    //JSONObject jsono = new JSONObject(data);
                    return data;
                }
                return null;

            }

            //==================================
            //for POST method

            //auto attach BusinessID
            postParams += "&&os=android&build="+Globals.BUILD_NUM;

            //if(g.MV_Customer != null && g.isSignedIn){
            if(g.mWasSignIn && g.mCustomer != null){
                postParams +=  "&Username=" + g.mCustomer.mUserName;
            }


            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(postParams.getBytes().length));
            connection.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            StringBuilder sb = new StringBuilder();
            int HttpResult =connection.getResponseCode();
            if(HttpResult ==HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        connection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();



            }else{
                //System.out.println(connection.getResponseMessage());
                if(delegate != null)
                    delegate.processWSError(wsCallingCode,connection.getResponseMessage());

            }

            return sb.toString();
        }
        catch (Exception e) {
            --mTryCount;
            if(mTryCount>0)
            {
                doInBackground(urlparams);
                return "";
            }
            else {
                //Log.e("Error: ", e.getMessage());
                if(delegate != null)
                    delegate.processWSError(wsCallingCode, e.getMessage());
                //Toast.makeText(this.context, "command error", Toast.LENGTH_LONG).show();
                return null;
            }

        }


    }



    protected void onPostExecute(String result){
        //pb.setVisibility(View.GONE);
        //Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();

        //if result == "" then trying more time
        if(result==null || result.length()>0) {
            if(delegate != null)
                delegate.processWSFinish(wsCallingCode, result);
        }
    }

    protected void onProgressUpdate(Integer... progress){
        //pb.setProgress(progress[0]);
    }


}

