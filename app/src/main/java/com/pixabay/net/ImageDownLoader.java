package com.pixabay.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.net.URL;

public class ImageDownLoader extends AsyncTask<String, Integer, Bitmap> {

    private ImageView imageView;
    private URL imgUrl;
    private String imgStr;
    private Bitmap bitmap;

    public ImageDownLoader(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            imgStr = strings[0];
            imgUrl = new URL(imgStr);
            bitmap = BitmapFactory.decodeStream(imgUrl.openConnection() .getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        Log.d("ImageDownloader", "imageView's Tag = " + imageView.getTag() + ", URL = " + imgStr);
        if(imgUrl.equals(imageView.getTag()))
            imageView.setImageBitmap(bitmap);
    }

}
