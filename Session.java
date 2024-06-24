abstract class Session {
    int sessionLength;
    String sessionType;
    Boolean completed;

    Session(int sessionTime, String sessionType) {
        sessionLength = sessionTime;
        this.sessionType = sessionType;
        completed = false;
    }

    @Override
    public String toString() {
        return sessionType;
    }

    int getSessionLength() {
        return sessionLength;
    }

    Boolean completed() {
        return completed;
    }
}
