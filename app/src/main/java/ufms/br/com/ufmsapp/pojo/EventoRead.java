package ufms.br.com.ufmsapp.pojo;


public class EventoRead {

    int eventoReadId;
    int eventoReadStatus;
    int eventoKey;

    public EventoRead(int eventoReadStatus, int eventoKey) {
        this.eventoReadStatus = eventoReadStatus;
        this.eventoKey = eventoKey;
    }

    public EventoRead(int eventoReadId, int eventoReadStatus, int eventoKey) {
        this.eventoReadId = eventoReadId;
        this.eventoReadStatus = eventoReadStatus;
        this.eventoKey = eventoKey;
    }

    public int getEventoReadId() {
        return eventoReadId;
    }

    public void setEventoReadId(int eventoReadId) {
        this.eventoReadId = eventoReadId;
    }

    public int getEventoReadStatus() {
        return eventoReadStatus;
    }

    public void setEventoReadStatus(int eventoReadStatus) {
        this.eventoReadStatus = eventoReadStatus;
    }

    public int getEventoKey() {
        return eventoKey;
    }

    public void setEventoKey(int eventoKey) {
        this.eventoKey = eventoKey;
    }

    @Override
    public String toString() {
        return "EventoRead{" +
                "eventoReadId=" + eventoReadId +
                ", eventoReadStatus=" + eventoReadStatus +
                ", eventoKey=" + eventoKey +
                '}';
    }
}
