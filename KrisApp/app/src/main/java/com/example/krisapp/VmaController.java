package com.example.krisapp;

import android.os.Handler;
import android.os.Looper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VmaController {
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VmaMessage {
        @JsonProperty("Headline")
        public String headline;
        @JsonProperty("Published")
        public String published;
        @JsonProperty("BodyText")
        public String bodyText;
        @JsonProperty("Area")
        public List<Area> areas;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Area {
        @JsonProperty("Description")
        public String description;
        @JsonProperty("CoordinateObject")
        public CoordinateObject coordinateObject;
        @JsonProperty("GeometryInformation")
        public GeometryInformation geometryInformation;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CoordinateObject {
        @JsonProperty("Latitude")
        public String latitude;
        @JsonProperty("Longitude")
        public String longitude;
        @JsonProperty("Altitude")
        public String altitude;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeometryInformation {
        @JsonProperty("PoleOfInInaccessibility")
        public PoleOfInaccessibility poleOfInaccessibility;
        @JsonProperty("Geometry")
        public Geometry geometry;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PoleOfInaccessibility {
        @JsonProperty("type")
        public String type;
        @JsonProperty("coordinates")
        public List<Double> coordinates;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {
        @JsonProperty("type")
        public String type;
        @JsonProperty("coordinates")
        public List<List<List<Double>>> coordinates;
    }

    public interface MessageDisplayer {
        void displayMessage(String message);
    }

    private MessageDisplayer displayer;

    public VmaController(MessageDisplayer displayer) {
        this.displayer = displayer;
    }

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
                mainHandler.post(() -> displayer.displayMessage("Failed to fetch data."));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    try {
                        VmaMessage[] vmaMessages = objectMapper.readValue(responseData, VmaMessage[].class);
                        if (vmaMessages.length > 0) {
                            VmaMessage vmaMessage = vmaMessages[0];
                            StringBuilder messageBuilder = new StringBuilder();
                            messageBuilder.append("Headline: ").append(vmaMessage.headline).append("\n")
                                    .append("Published: ").append(vmaMessage.published).append("\n")
                                    .append("BodyText: ").append(vmaMessage.bodyText).append("\n");

                            for (Area area : vmaMessage.areas) {
                                messageBuilder.append("Area Description: ").append(area.description).append("\n")
                                        .append("Latitude: ").append(area.coordinateObject.latitude).append("\n")
                                        .append("Longitude: ").append(area.coordinateObject.longitude).append("\n");
                            }
                            mainHandler.post(() -> displayer.displayMessage(messageBuilder.toString()));
                        } else {
                            mainHandler.post(() -> displayer.displayMessage("No VMA messages found."));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        mainHandler.post(() -> displayer.displayMessage("Error parsing the data: " + e.getMessage()));
                    }
                } else {
                    mainHandler.post(() -> displayer.displayMessage("Server responded with error."));
                }
            }
        });
    }
}