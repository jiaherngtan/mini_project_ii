package com.vttp2022.backend.models;

public class Notification {

    private String title;
    private String message;
    private String target;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Notification [title=" + title + ", message=" + message + ", target=" + target + "]";
    }

}
