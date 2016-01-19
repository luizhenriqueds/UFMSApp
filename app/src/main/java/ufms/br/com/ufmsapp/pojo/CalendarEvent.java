package ufms.br.com.ufmsapp.pojo;


public class CalendarEvent {

    private int day;
    private String eventTitle;
    private String eventSubtitle;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventSubtitle() {
        return eventSubtitle;
    }

    public void setEventSubtitle(String eventSubtitle) {
        this.eventSubtitle = eventSubtitle;
    }
}
