package com.example.taskappsfornexus.dataclass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {

        private String id;

        @SerializedName("newsList")
        private List<ApiResponse> newsList;

        public List<ApiResponse> getNewsList() {
            return newsList;
        }
        public String getId() {
            return id;
        }

        // Add this method to get the data list
        public List<ApiResponse> getDataList() {
            return newsList;
        }


        @SerializedName("image")
        private Integer image;

        @SerializedName("title")
        private String title;

        @SerializedName("summary")
        private String summary;

        public Integer getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public String getSummary() {
            return summary;
        }
    }
}
