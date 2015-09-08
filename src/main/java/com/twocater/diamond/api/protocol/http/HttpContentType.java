package com.twocater.diamond.api.protocol.http;

public enum HttpContentType {

    plain("text/plain"), json("application/json"), tmap("application/octet-stream");
    //还有xml，jsonp等
    private String type;

    private HttpContentType(String type) {
        this.type = type;
    }

    public String getContentType() {
        return this.type;
    }

    public static HttpContentType getHttpContentType(String contentType) {
        if (contentType == null) {
            return null;
        }
        for (HttpContentType httpContentType : values()) {
            if (httpContentType.getContentType().equalsIgnoreCase(contentType)) {
                return httpContentType;
            }
        }
        return null;
    }
}
