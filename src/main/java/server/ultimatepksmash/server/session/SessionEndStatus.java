package server.ultimatepksmash.server.session;

import java.util.Calendar;

public class SessionEndStatus {
    private SessionEndReason reason;
    private Calendar endData;

    public SessionEndStatus(SessionEndReason reason) {
        this.endData = Calendar.getInstance();
        this.reason = reason;
    }

    public SessionEndReason getReason() {
        return reason;
    }

    public Calendar getEndData() {
        return endData;
    }
}
