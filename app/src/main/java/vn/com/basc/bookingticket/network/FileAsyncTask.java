package vn.com.basc.bookingticket.network;

import android.content.Context;
import android.os.AsyncTask;

import vn.com.basc.bookingticket.common.Globals;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by longnguyen on 14/08/2015.
 */
public class FileAsyncTask extends AsyncTask<String, Integer, String> {

    public IWSResponse delegate=null;
    private String wsCallingCode = "";
    Context context;
    private Object mSourceObj;




    public FileAsyncTask(Context context, Object sourceObj){
        this.context=context;
        this.mSourceObj = sourceObj;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... urlparams) {

            int count;
            try {

                String fileName =  urlparams[0];//"cpa2x.png";
                wsCallingCode = urlparams[1];

                if(fileName==null || fileName.isEmpty() || fileName.equalsIgnoreCase("null")) {
                    return "";
                }

                String encodedFilename = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
                String fileURL = Globals.SERVER_IMAGE_URL + encodedFilename;


                File mydir = context.getFilesDir(); //get your internal directory
                File myFile = new File(mydir, fileName);

                if(!myFile.exists())
                {
                    URL url = new URL(fileURL);
                    URLConnection conection = url.openConnection();
                    conection.connect();

                    // this will be useful so that you can show a tipical 0-100%
                    // progress bar
                    int lenghtOfFile = conection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream(),
                            8192);

                    // Output stream
                    //OutputStream output = new FileOutputStream(Environment
                    //        .getExternalStorageDirectory().toString()
                    //       + "/cpa2x.png");


                    OutputStream output = context.openFileOutput(fileName, Context.MODE_PRIVATE);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        //publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                        publishProgress((int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                    return myFile.getAbsolutePath();
                }
                else {
                    return myFile.getAbsolutePath();
                }


                //myFile.delete();
                //ImageView MyImageView = (ImageView)findViewById(R.id.imageView2);
                //MyImageView.setImageBitmap(decodeSampledBitmapFromFile(myFile.getAbsolutePath(), 100, 100));


            } catch (Exception e) {
                //Log.e("Error: ", e.getMessage());
                delegate.processFileError(wsCallingCode,e.getMessage(),mSourceObj);
            }

            return null;
    }



    protected void onPostExecute(String result){
        //pb.setVisibility(View.GONE);
        //Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        delegate.processFileFinish(wsCallingCode, result, mSourceObj);
    }

    protected void onProgressUpdate(Integer... progress){
        //pb.setProgress(progress[0]);
    }


}

