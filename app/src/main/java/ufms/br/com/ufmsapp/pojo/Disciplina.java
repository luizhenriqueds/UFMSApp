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

public class Disciplina implements Parcelable {

    private int id;
    private String titulo;
    private String codigo;
    private String descricao;
    private int cargaHoraria;
    private int idDisciplinaServidor;
    private int tipoDisciplina;
    private int professor;

    public Disciplina() {
        // construtor vazio utilizado para criação padrão de um objeto Disciplina
    }

    public Disciplina(int id, String titulo, String codigo, String descricao, int cargaHoraria, int idDisciplinaServidor, int tipoDisciplina, int professor) {
        this.id = id;
        this.titulo = titulo;
        this.codigo = codigo;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.idDisciplinaServidor = idDisciplinaServidor;
        this.tipoDisciplina = tipoDisciplina;
        this.professor = professor;
    }

    public Disciplina(Parcel input) {
        id = input.readInt();
        titulo = input.readString();
        codigo = input.readString();
        descricao = input.readString();
        cargaHoraria = input.readInt();
        idDisciplinaServidor = input.readInt();
        tipoDisciplina = input.readInt();
        professor = input.readInt();
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getIdDisciplinaServidor() {
        return idDisciplinaServidor;
    }

    public void setIdDisciplinaServidor(int idDisciplinaServidor) {
        this.idDisciplinaServidor = idDisciplinaServidor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipoDisciplina() {
        return tipoDisciplina;
    }

    public void setTipoDisciplina(int tipoDisciplina) {
        this.tipoDisciplina = tipoDisciplina;
    }

    public int getProfessor() {
        return professor;
    }

    public void setProfessor(int professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", codigo='" + codigo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", idDisciplinaServidor=" + idDisciplinaServidor +
                ", tipoDisciplina=" + tipoDisciplina +
                ", professor=" + professor +
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
        dest.writeString(codigo);
        dest.writeString(descricao);
        dest.writeInt(cargaHoraria);
        dest.writeInt(idDisciplinaServidor);
        dest.writeInt(tipoDisciplina);
        dest.writeInt(professor);
    }

    public static final Parcelable.Creator<Disciplina> CREATOR = new Parcelable.Creator<Disciplina>() {
        public Disciplina createFromParcel(Parcel in) {
            return new Disciplina(in);
        }

        @Override
        public Disciplina[] newArray(int size) {
            return new Disciplina[size];
        }
    };
}
