package com.example.krisapp;

import android.os.Handler;
import android.os.Looper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VmaController {
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public VmaController(MessageDisplayer displayer) {
        this.displayer = displayer;
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VmaMessage {
        @JsonProperty("Identifier")
        public String identifier;
        @JsonProperty("PushMessage")
        public String pushMessage;
        @JsonProperty("Updated")
        public String updated;
        @JsonProperty("Published")
        public String published;
        @JsonProperty("Headline")
        public String headline;
        @JsonProperty("Preamble")
        public String preamble;
        @JsonProperty("BodyText")
        public String bodyText;
        @JsonProperty("Area")
        public List<Area> areas;
        @JsonProperty("Web")
        public String web;
        @JsonProperty("Language")
        public String language;
        @JsonProperty("Event")
        public String event;
        @JsonProperty("SenderName")
        public String senderName;
        @JsonProperty("BodyLinks")
        public String bodyLinks;
        @JsonProperty("SourceID")
        public int sourceID;
        @JsonProperty("IsTest")
        public boolean isTest;
    }

    public static class Area {
        @JsonProperty("Type")
        public String type;
        @JsonProperty("Description")
        public String description;
        @JsonProperty("Coordinate")
        public String coordinate;
        @JsonProperty("CoordinateObject")
        public CoordinateObject coordinateObject;
    }

    public static class CoordinateObject {
        @JsonProperty("Latitude")
        public String latitude;
        @JsonProperty("Longitude")
        public String longitude;
        @JsonProperty("Altitude")
        public String altitude;
    }

    public interface MessageDisplayer {
        void displayMessages(List<VMAMessageObject> messages);
    }

    private MessageDisplayer displayer;

    public void fetchAndDisplayMessage() {
        String url = "https://api.krisinformation.se/v3/testvmas";
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                mainHandler.post(() -> displayer.displayMessages(Collections.emptyList()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    try {
                        VmaMessage[] vmaMessages = objectMapper.readValue(responseData, VmaMessage[].class);
                        List<VMAMessageObject> messageObjects = new ArrayList<>();
                        for (VmaMessage message : vmaMessages) {
                            VMAMessageObject messageObject = new VMAMessageObject(
                                    message.headline,
                                    message.published,
                                    message.bodyText
                            );
                            messageObjects.add(messageObject);
                        }
                        mainHandler.post(() -> displayer.displayMessages(messageObjects));
                    } catch (Exception e) {
                        e.printStackTrace();
                        mainHandler.post(() -> displayer.displayMessages(Collections.emptyList()));
                    }
                } else {
                    mainHandler.post(() -> displayer.displayMessages(Collections.emptyList()));
                }
            }
        });
    }
}
