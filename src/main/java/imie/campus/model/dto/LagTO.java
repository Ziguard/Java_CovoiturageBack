package imie.campus.model.dto;

public class LagTO {

    private Integer carpoolingId;

    private String cause;

    private String date;

    private String broadcastMessage;

    public Integer getCarpoolingId() {
        return carpoolingId;
    }

    public void setCarpoolingId(Integer carpoolingId) {
        this.carpoolingId = carpoolingId;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBroadcastMessage() {
        return broadcastMessage;
    }

    public void setBroadcastMessage(String broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }
}
