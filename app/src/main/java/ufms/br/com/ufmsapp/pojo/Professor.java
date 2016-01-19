package ufms.br.com.ufmsapp.pojo;

import java.util.Date;

public class Professor {

    private int id;
    private String nome;
    private String email;
    private Date anoIngresso;
    private String formacao;
    private int idProfessorServidor;
    private int tituloProfessor;

    public Professor() {
        // construtor vazio utilizado para referência por outras classes sem usar parâmetros
    }

    public Professor(int id, String nome, String email, Date anoIngresso, String formacao, int idProfessorServidor, int tituloProfessor) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.anoIngresso = anoIngresso;
        this.formacao = formacao;
        this.idProfessorServidor = idProfessorServidor;
        this.tituloProfessor = tituloProfessor;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getAnoIngresso() {
        return anoIngresso;
    }

    public void setAnoIngresso(Date anoIngresso) {
        this.anoIngresso = anoIngresso;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public int getTituloProfessor() {
        return tituloProfessor;
    }

    public void setTituloProfessor(int tituloProfessor) {
        this.tituloProfessor = tituloProfessor;
    }

    public int getIdProfessorServidor() {
        return idProfessorServidor;
    }

    public void setIdProfessorServidor(int idProfessorServidor) {
        this.idProfessorServidor = idProfessorServidor;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", anoIngresso=" + anoIngresso +
                ", formacao='" + formacao + '\'' +
                ", idProfessorServidor=" + idProfessorServidor +
                ", tituloProfessor=" + tituloProfessor +
                '}';
    }
}
