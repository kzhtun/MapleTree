package com.info121.mapletree.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.Toast;

import com.adeel.library.easyFTP;
import com.info121.mapletree.api.RestClient;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.InetAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KZHTUN on 8/3/2017.
 */


public class FtpHelper {

    public static class uploadTask extends AsyncTask<String, String, String> {
        ProgressDialog prg;
        Context context;
        InputStream inputStream;
        String mJobNo;
        String mFileName;
        String mType;

        public uploadTask(Context context, InputStream inputStream) {
            this.context = context;
            this.inputStream = inputStream;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prg = new ProgressDialog(context);
            prg.setMessage("Initializing ...");
            prg.show();
        }

//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                easyFTP ftp = new easyFTP();
//
//                ftp.connect(params[0], params[1], params[2]);
//
//                boolean status = false;
//
//                if (!params[3].isEmpty()) {
//                    status = ftp.setWorkingDirectory(params[3]); // if User say provided any Destination then Set it , otherwise
//                }
//                // Upload will be stored on Default /root level on server
//                publishProgress("Uploading ...");
//                ftp.uploadFile(inputStream, params[4]);
//
//                publishProgress("Upload Successful ...");
//                mFileName = params[4];
//                mJobNo = params[5];
//                mType = params[6];
//
//                return new String("Upload Successful");
//
//            } catch (Exception e) {
//                String t = "Failure : " + e.getLocalizedMessage();
//                return t;
//            }
//        }


        @Override
        protected String doInBackground(String... params) {
            try {
                boolean status = false;

                mFileName = params[4];
                mJobNo = params[5];
                mType = params[6];

//                FTPClient ftpClient = new FTPClient();
//
//                ftpClient.connect(App.FTP_URL);
//                ftpClient.login(App.FTP_USER, App.FTP_PASSWORD);
//
//                if (!params[3].isEmpty()) {
//                    status = ftpClient.changeWorkingDirectory(params[3]); // if User say provided any Destination then Set it , otherwise
//                }
//
//                FileInputStream fs = new FileInputStream(mFileName);
//                ftpClient.storeFile(mFileName, fs);
//
//                ftpClient.logout();


                FTPClient ftpClient = new FTPClient();
                ftpClient.connect(InetAddress.getByName(params[0]));
                ftpClient.login(params[1], params[2]);

                if (!params[3].isEmpty()) {
                    status = ftpClient.changeWorkingDirectory(params[3]); // if User say provided any Destination then Set it , otherwise
                }


                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                BufferedInputStream buffIn = null;
                //   buffIn = new BufferedInputStream(new FileInputStream(file));
                ftpClient.enterLocalPassiveMode();
                ftpClient.storeFile(mFileName, inputStream);

                buffIn.close();
                ftpClient.logout();
                ftpClient.disconnect();


                publishProgress("Upload Successful ...");


                return new String("Upload Successful");

            } catch (Exception e) {
                String t = "Failure : " + e.getLocalizedMessage();
                return t;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            prg.setMessage(values[0]);
        }


        @Override
        protected void onPostExecute(String str) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    prg.dismiss();




                }
            }, 2000);


            // Toast.makeText(demo.this,str,Toast.LENGTH_LONG).show();
        }
    }

    public class downloadTask extends AsyncTask<String, Void, String> {
        ProgressDialog prg;
        Context context;
        InputStream inputStream;

        public downloadTask(Context context) {
            this.context = context;
            this.inputStream = inputStream;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prg = new ProgressDialog(context);
            prg.setMessage("Downloading...");
            prg.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                easyFTP ftp = new easyFTP();
//
                ftp.connect(params[0], params[1], params[2]);
                ftp.downloadFile(params[3], params[4]);
                return new String("Download Successful");
            } catch (Exception e) {
                String t = "Failure : " + e.getLocalizedMessage();
                return t;
            }
        }

        @Override
        protected void onPostExecute(String str) {
            prg.dismiss();
            // Toast.makeText(demo.this,str,Toast.LENGTH_LONG).show();
        }
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }




}
