package ufms.br.com.ufmsapp.pojo;


public class NotifyStatus {

    int notifyId;
    int notifyStatus;
    int eventoKey;


    public NotifyStatus(int notifyStatus, int eventoKey) {
        this.eventoKey = eventoKey;
        this.notifyStatus = notifyStatus;
    }

    public NotifyStatus(int notifyId, int notifyStatus, int eventoKey) {
        this.notifyId = notifyId;
        this.notifyStatus = notifyStatus;
        this.eventoKey = eventoKey;
    }

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public int getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(int notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public int getEventoKey() {
        return eventoKey;
    }

    public void setEventoKey(int eventoKey) {
        this.eventoKey = eventoKey;
    }

    @Override
    public String toString() {
        return "NotifyStatus{" +
                "notifyId=" + notifyId +
                ", notifyStatus=" + notifyStatus +
                ", eventoKey=" + eventoKey +
                '}';
    }
}
