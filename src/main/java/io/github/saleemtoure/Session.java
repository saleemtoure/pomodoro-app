package io.github.saleemtoure;

abstract class Session {
    int sessionLength;
    String sessionType;
    String sessionIcon;
    Boolean completed;

    Session(int sessionTime, String sessionType, String sessionIcon) {
        sessionLength = sessionTime;
        this.sessionType = sessionType;
        this.sessionIcon = sessionIcon;
        completed = false;
    }

    @Override
    public String toString() {
        return sessionType;
    }

    String getSessionIcon() {
        return sessionIcon;
    }

    int getSessionLength() {
        return sessionLength;
    }

    Boolean completed() {
        return completed;
    }

    void setComplete(Boolean completed) {
        this.completed = completed;
    }
}
