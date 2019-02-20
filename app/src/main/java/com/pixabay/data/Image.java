package com.pixabay.data;

import java.net.MalformedURLException;
import java.net.URL;

public class Image {

    private String id;
    private URL previewURL;

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URL getPreviewURL() {
        if(previewURL != null)
            return previewURL;
        else
            return null;
    }

    public void setPreviewURL(String previewURL) {
        try {
            this.previewURL = new URL(previewURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
