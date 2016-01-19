package ufms.br.com.ufmsapp.pojo;


public class Curso {

    int id;
    String nome;
    int cargaHoraria;
    String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    int periodoCurso;
    int tipoCurso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getPeriodoCurso() {
        return periodoCurso;
    }

    public void setPeriodoCurso(int periodoCurso) {
        this.periodoCurso = periodoCurso;
    }

    public int getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(int tipoCurso) {
        this.tipoCurso = tipoCurso;
    }
}
