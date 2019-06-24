package lk.ac.kln.threadtutorial01;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void downloadUniLogo(View view) {
        ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
        imageDownloadTask.execute(getString(R.string.ImageURL));
    }

    private class ImageDownloadTask extends AsyncTask<String,Void,Bitmap>{
        private ProgressDialog progressDialog = null;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            //*************************************************************
            //You cant do like this, Because ImageDownloadTask Java class
            // is separate class in MainActivity.java, You cant use same
            // context
            //*************************************************************
            //progressDialog = new ProgressDialog(getApplicationContext());
            //*************************************************************

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Downloading image....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected Bitmap doInBackground(String[] strings){
            Bitmap bitmap = null;
            try {
                URL imageURL = new URL(strings[0]);
                HttpURLConnection connection =(HttpURLConnection) imageURL.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream,null,null);
            }catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_LONG).show();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            ImageView imageView = findViewById(R.id.downloadedImage);
            if(bitmap!=null){
                progressDialog.hide();
                imageView.setImageBitmap(bitmap);
            }else{
                Toast.makeText(getApplicationContext(),"Null bitmap object",Toast.LENGTH_LONG).show();
            }
        }
    }


}
