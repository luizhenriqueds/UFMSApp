package ufms.br.com.ufmsapp.pojo;


public class StatusDisciplina {

    int id;
    String statusDisciplina;
    int idServidorDisciplina;

    public StatusDisciplina() {
    }

    public StatusDisciplina(int id, String statusDisciplina, int idServidorDisciplina) {
        this.id = id;
        this.statusDisciplina = statusDisciplina;
        this.idServidorDisciplina = idServidorDisciplina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusDisciplina() {
        return statusDisciplina;
    }

    public void setStatusDisciplina(String statusDisciplina) {
        this.statusDisciplina = statusDisciplina;
    }

    public int getIdServidorDisciplina() {
        return idServidorDisciplina;
    }

    public void setIdServidorDisciplina(int idServidorDisciplina) {
        this.idServidorDisciplina = idServidorDisciplina;
    }

    @Override
    public String toString() {
        return "StatusDisciplina{" +
                "id=" + id +
                ", statusDisciplina='" + statusDisciplina + '\'' +
                ", idServidorDisciplina=" + idServidorDisciplina +
                '}';
    }
}
