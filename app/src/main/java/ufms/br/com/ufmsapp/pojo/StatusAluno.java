package ufms.br.com.ufmsapp.pojo;


public class StatusAluno {

    int idStatusAluno;
    String statusAlunoDescricao;
    int statusAlunoIdServidor;

    public StatusAluno(int idStatusAluno, String statusAlunoDescricao, int statusAlunoIdServidor) {
        this.idStatusAluno = idStatusAluno;
        this.statusAlunoDescricao = statusAlunoDescricao;
        this.statusAlunoIdServidor = statusAlunoIdServidor;
    }

    public StatusAluno() {
    }

    public int getIdStatusAluno() {
        return idStatusAluno;
    }

    public void setIdStatusAluno(int idStatusAluno) {
        this.idStatusAluno = idStatusAluno;
    }

    public String getStatusAlunoDescricao() {
        return statusAlunoDescricao;
    }

    public void setStatusAlunoDescricao(String statusAlunoDescricao) {
        this.statusAlunoDescricao = statusAlunoDescricao;
    }

    public int getStatusAlunoIdServidor() {
        return statusAlunoIdServidor;
    }

    public void setStatusAlunoIdServidor(int statusAlunoIdServidor) {
        this.statusAlunoIdServidor = statusAlunoIdServidor;
    }

    @Override
    public String toString() {
        return "StatusAluno{" +
                "idStatusAluno=" + idStatusAluno +
                ", statusAlunoDescricao='" + statusAlunoDescricao + '\'' +
                ", statusAlunoIdServidor=" + statusAlunoIdServidor +
                '}';
    }
}
