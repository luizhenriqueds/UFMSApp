package ufms.br.com.ufmsapp.pojo;


public class EventoFavorite {

    int eventoFavoriteId;
    int eventoFavoriteStatus;
    int eventoKey;

    public EventoFavorite(int eventoFavoriteStatus, int eventoKey) {
        this.eventoFavoriteStatus = eventoFavoriteStatus;
        this.eventoKey = eventoKey;
    }

    public EventoFavorite(int eventoFavoriteId, int eventoFavoriteStatus, int eventoKey) {
        this.eventoFavoriteId = eventoFavoriteId;
        this.eventoFavoriteStatus = eventoFavoriteStatus;
        this.eventoKey = eventoKey;
    }

    public int getEventoFavoriteId() {
        return eventoFavoriteId;
    }

    public void setEventoFavoriteId(int eventoFavoriteId) {
        this.eventoFavoriteId = eventoFavoriteId;
    }

    public int getEventoFavoriteStatus() {
        return eventoFavoriteStatus;
    }

    public void setEventoFavoriteStatus(int eventoFavoriteStatus) {
        this.eventoFavoriteStatus = eventoFavoriteStatus;
    }

    public int getEventoKey() {
        return eventoKey;
    }

    public void setEventoKey(int eventoKey) {
        this.eventoKey = eventoKey;
    }

    @Override
    public String toString() {
        return "EventoFavorite{" +
                "eventoFavoriteId=" + eventoFavoriteId +
                ", eventoFavoriteStatus=" + eventoFavoriteStatus +
                ", eventoKey=" + eventoKey +
                '}';
    }
}
