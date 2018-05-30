package com.example.serwis.bakingapp.Network;

import java.io.Serializable;

/**
 * Created by serwis on 2018-05-22.
 */

public class steps implements Serializable {
    private String id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public String getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
