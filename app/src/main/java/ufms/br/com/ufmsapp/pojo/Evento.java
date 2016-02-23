/*
 * Copyright [2016] [UFMS]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ufms.br.com.ufmsapp.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Evento implements Parcelable {

    private int id;
    private String titulo;
    private String descricao;
    private Date dataEventoCriado;
    private Date dataLimiteEvento;
    private String smallIcon;
    private String bigIcon;
    private int idEventoServidor;
    private int tipoEvento;
    private int disciplina;

    public Evento() {
    }

    public Evento(int id, String titulo, String descricao, Date dataEventoCriado, Date dataLimiteEvento, String smallIcon, String bigIcon, int idEventoServidor, int tipoEvento, int disciplina) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataEventoCriado = dataEventoCriado;
        this.dataLimiteEvento = dataLimiteEvento;
        this.smallIcon = smallIcon;
        this.bigIcon = bigIcon;
        this.idEventoServidor = idEventoServidor;
        this.tipoEvento = tipoEvento;
        this.disciplina = disciplina;
    }

    public Evento(Parcel input) {
        id = input.readInt();
        titulo = input.readString();
        descricao = input.readString();
        tipoEvento = input.readInt();
        disciplina = input.readInt();

        smallIcon = input.readString();
        bigIcon = input.readString();

        idEventoServidor = input.readInt();

        long tempDate = input.readLong();
        dataEventoCriado = (tempDate == -1 ? null : new Date(tempDate));

        long tempDataLimite = input.readLong();
        dataLimiteEvento = (tempDataLimite == -1 ? null : new Date(tempDataLimite));
    }

    public int getIdEventoServidor() {
        return idEventoServidor;
    }

    public void setIdEventoServidor(int idEventoServidor) {
        this.idEventoServidor = idEventoServidor;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataLimiteEvento() {
        return dataLimiteEvento;
    }

    public void setDataLimiteEvento(Date dataLimiteEvento) {
        this.dataLimiteEvento = dataLimiteEvento;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }

    public String getBigIcon() {
        return bigIcon;
    }

    public void setBigIcon(String bigIcon) {
        this.bigIcon = bigIcon;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public int getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(int tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public int getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(int disciplina) {
        this.disciplina = disciplina;
    }

    public Date getDataEventoCriado() {
        return dataEventoCriado;
    }

    public void setDataEventoCriado(Date dataEventoCriado) {
        this.dataEventoCriado = dataEventoCriado;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataEventoCriado=" + dataEventoCriado +
                ", dataLimiteEvento=" + dataLimiteEvento +
                ", smallIcon='" + smallIcon + '\'' +
                ", bigIcon='" + bigIcon + '\'' +
                ", idEventoServidor=" + idEventoServidor +
                ", tipoEvento=" + tipoEvento +
                ", disciplina=" + disciplina +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeInt(tipoEvento);
        dest.writeInt(disciplina);
        dest.writeString(smallIcon);
        dest.writeString(bigIcon);
        dest.writeInt(idEventoServidor);
        dest.writeLong(dataEventoCriado.getTime());
        dest.writeLong(dataLimiteEvento.getTime());
    }

    public static final Parcelable.Creator<Evento> CREATOR = new Parcelable.Creator<Evento>() {
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };
}
