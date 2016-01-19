package ufms.br.com.ufmsapp.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class TipoEvento implements Parcelable {

    int tipoEventoId;
    String nomeTipoEvento;
    int tipoEventoIdServidor;

    public TipoEvento() {
    }

    public TipoEvento(int tipoEventoId, String nomeTipoEvento, int tipoEventoIdServidor) {
        this.tipoEventoId = tipoEventoId;
        this.nomeTipoEvento = nomeTipoEvento;
        this.tipoEventoIdServidor = tipoEventoIdServidor;
    }

    public TipoEvento(Parcel input) {
        tipoEventoId = input.readInt();
        nomeTipoEvento = input.readString();
        tipoEventoIdServidor = input.readInt();
    }

    public int getTipoEventoId() {
        return tipoEventoId;
    }

    public void setTipoEventoId(int tipoEventoId) {
        this.tipoEventoId = tipoEventoId;
    }

    public String getNomeTipoEvento() {
        return nomeTipoEvento;
    }

    public void setNomeTipoEvento(String nomeTipoEvento) {
        this.nomeTipoEvento = nomeTipoEvento;
    }

    public int getTipoEventoIdServidor() {
        return tipoEventoIdServidor;
    }

    public void setTipoEventoIdServidor(int tipoEventoIdServidor) {
        this.tipoEventoIdServidor = tipoEventoIdServidor;
    }

    @Override
    public String toString() {
        return "TipoEvento{" +
                "tipoEventoId=" + tipoEventoId +
                ", nomeTipoEvento='" + nomeTipoEvento + '\'' +
                ", tipoEventoIdServidor=" + tipoEventoIdServidor +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tipoEventoId);
        dest.writeString(nomeTipoEvento);
        dest.writeInt(tipoEventoIdServidor);
    }

    public static final Parcelable.Creator<TipoEvento> CREATOR = new Parcelable.Creator<TipoEvento>() {
        public TipoEvento createFromParcel(Parcel in) {
            return new TipoEvento(in);
        }

        @Override
        public TipoEvento[] newArray(int size) {
            return new TipoEvento[size];
        }
    };
}
