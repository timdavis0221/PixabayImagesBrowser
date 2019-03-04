package com.pixabay.net;

import android.os.AsyncTask;
import android.util.SparseArray;
import com.pixabay.activities.BrowserActivity;
import com.pixabay.adapters.PixabayRecyclerAdapter;
import com.pixabay.data.Image;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageInfoRetriever extends AsyncTask<String, Void, Void>{

    private final String TAG = getClass().getSimpleName();

    private final String APIKey = "?key=11649612-7ef07c333de0dd4de6157a97c";
    private String pixabayHost = "https://pixabay.com/api/";
    private String params = "&image_type=photo&per_page=120&pretty=false";

    private WeakReference<BrowserActivity> browserActivityWeakReference;
    private BrowserActivity browserActivity;

    private PixabayRecyclerAdapter pixabayRecyclerAdapter;
    private SparseArray<Image> imagesArray;

    private URL url;
    private HttpURLConnection conn;

    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private int responseCode;
    private String resultString;
    private JSONObject jsonObject;
    private JSONArray jsonArray;


    public ImageInfoRetriever(BrowserActivity context) {
        this.browserActivityWeakReference = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... values) {
        try{
            searchPhotosInfo(values[0]);
            parseReceivedPhotosInfo();
        } catch(IOException | JSONException exception){
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void values) {
        super.onPostExecute(values);
        browserActivity = browserActivityWeakReference.get();

        pixabayRecyclerAdapter = new PixabayRecyclerAdapter(imagesArray);
        browserActivity.getRecyclerView().setAdapter(pixabayRecyclerAdapter);
    }

    private void searchPhotosInfo(String keywords) throws IOException {
        url = new URL(pixabayHost + APIKey + "&q=" + keywords + params);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);

        responseCode = conn.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        stringBuilder = new StringBuilder();

        while ((resultString = bufferedReader.readLine()) != null) {
            stringBuilder.append(resultString + "\n");
        }

        bufferedReader.close();
    }

    private void parseReceivedPhotosInfo() throws JSONException {
        jsonObject = new JSONObject(stringBuilder.toString());
        jsonArray = jsonObject.getJSONArray("hits");

        imagesArray = new SparseArray<>();
        for(int index = 0; index < jsonArray.length(); index++){
            Image image = new Image();
            image.setId(jsonArray.getJSONObject(index).getString("id"));
            image.setPreviewURL(jsonArray.getJSONObject(index).getString("previewURL"));

            imagesArray.put(index, image);
        }
    }
}
