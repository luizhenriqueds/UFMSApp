package ufms.br.com.ufmsapp.pojo;

public class Nota {

    int id;
    double nota;
    int alunoXDisciplina;

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
}
