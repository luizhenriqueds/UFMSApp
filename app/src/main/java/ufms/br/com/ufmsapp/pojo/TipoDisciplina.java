package ufms.br.com.ufmsapp.pojo;


public class TipoDisciplina {

    int id;
    String tipoDisciplinaDescricao;
    int tipoDisciplinaIdServidor;


    public TipoDisciplina() {
    }

    public TipoDisciplina(int id, String tipoDisciplinaDescricao, int tipoDisciplinaIdServidor) {
        this.id = id;
        this.tipoDisciplinaDescricao = tipoDisciplinaDescricao;
        this.tipoDisciplinaIdServidor = tipoDisciplinaIdServidor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoDisciplinaDescricao() {
        return tipoDisciplinaDescricao;
    }

    public void setTipoDisciplinaDescricao(String tipoDisciplinaDescricao) {
        this.tipoDisciplinaDescricao = tipoDisciplinaDescricao;
    }

    public int getTipoDisciplinaIdServidor() {
        return tipoDisciplinaIdServidor;
    }

    public void setTipoDisciplinaIdServidor(int tipoDisciplinaIdServidor) {
        this.tipoDisciplinaIdServidor = tipoDisciplinaIdServidor;
    }

    @Override
    public String toString() {
        return "TipoDisciplina{" +
                "id=" + id +
                ", tipoDisciplinaDescricao='" + tipoDisciplinaDescricao + '\'' +
                ", tipoDisciplinaIdServidor=" + tipoDisciplinaIdServidor +
                '}';
    }
}
