package ufms.br.com.ufmsapp.pojo;


public class TituloProfessor {

    int id;
    String tituloProfessor;
    int tituloProfessorIdServidor;

    public TituloProfessor(int id, String tituloProfessor, int tituloProfessorIdServidor) {
        this.id = id;
        this.tituloProfessor = tituloProfessor;
        this.tituloProfessorIdServidor = tituloProfessorIdServidor;
    }

    public TituloProfessor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTituloProfessor() {
        return tituloProfessor;
    }

    public void setTituloProfessor(String tituloProfessor) {
        this.tituloProfessor = tituloProfessor;
    }

    public int getTituloProfessorIdServidor() {
        return tituloProfessorIdServidor;
    }

    public void setTituloProfessorIdServidor(int tituloProfessorIdServidor) {
        this.tituloProfessorIdServidor = tituloProfessorIdServidor;
    }

    @Override
    public String toString() {
        return "TituloProfessor{" +
                "id=" + id +
                ", tituloProfessor='" + tituloProfessor + '\'' +
                ", tituloProfessorIdServidor=" + tituloProfessorIdServidor +
                '}';
    }
}
