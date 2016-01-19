package ufms.br.com.ufmsapp.pojo;


public class Aluno {

    int id;
    String nome;
    String email;
    String rga;
    int statusAluno;
    int alunoIdServidor;

    public Aluno(int id, String nome, String email, String rga, int statusAluno, int alunoIdServidor) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.rga = rga;
        this.statusAluno = statusAluno;
        this.alunoIdServidor = alunoIdServidor;
    }

    public Aluno() {
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

    public String getRga() {
        return rga;
    }

    public void setRga(String rga) {
        this.rga = rga;
    }

    public int getStatusAluno() {
        return statusAluno;
    }

    public void setStatusAluno(int statusAluno) {
        this.statusAluno = statusAluno;
    }

    public int getAlunoIdServidor() {
        return alunoIdServidor;
    }

    public void setAlunoIdServidor(int alunoIdServidor) {
        this.alunoIdServidor = alunoIdServidor;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", rga='" + rga + '\'' +
                ", statusAluno=" + statusAluno +
                ", alunoIdServidor=" + alunoIdServidor +
                '}';
    }
}
