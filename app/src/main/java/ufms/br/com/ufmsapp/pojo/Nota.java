package ufms.br.com.ufmsapp.pojo;

public class Nota {

    int id;
    double nota;
    String descricaoNota;
    int alunoXDisciplina;

    public Nota() {
    }

    public Nota(int id, String descricaoNota, double nota, int alunoXDisciplina) {
        this.id = id;
        this.nota = nota;
        this.descricaoNota = descricaoNota;
        this.alunoXDisciplina = alunoXDisciplina;
    }

    public Nota(String descricaoNota, double nota, int alunoXDisciplina) {
        this.id = id;
        this.nota = nota;
        this.descricaoNota = descricaoNota;
        this.alunoXDisciplina = alunoXDisciplina;
    }


    public String getDescricaoNota() {
        return descricaoNota;
    }

    public void setDescricaoNota(String descricaoNota) {
        this.descricaoNota = descricaoNota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getAlunoXDisciplina() {
        return alunoXDisciplina;
    }

    public void setAlunoXDisciplina(int alunoXDisciplina) {
        this.alunoXDisciplina = alunoXDisciplina;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "id=" + id +
                ", nota=" + nota +
                ", descricaoNota='" + descricaoNota + '\'' +
                ", alunoXDisciplina=" + alunoXDisciplina +
                '}';
    }
}
