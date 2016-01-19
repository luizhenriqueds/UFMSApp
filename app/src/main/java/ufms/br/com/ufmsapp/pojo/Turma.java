package ufms.br.com.ufmsapp.pojo;


public class Turma {

    int id;
    String nomeTurma;
    int idServidorTurma;

    public Turma(int id, String nomeTurma, int idServidorTurma) {
        this.id = id;
        this.nomeTurma = nomeTurma;
        this.idServidorTurma = idServidorTurma;
    }

    public Turma() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public int getIdServidorTurma() {
        return idServidorTurma;
    }

    public void setIdServidorTurma(int idServidorTurma) {
        this.idServidorTurma = idServidorTurma;
    }

    @Override
    public String toString() {
        return "Turma{" +
                "id=" + id +
                ", nomeTurma='" + nomeTurma + '\'' +
                ", idServidorTurma=" + idServidorTurma +
                '}';
    }
}
