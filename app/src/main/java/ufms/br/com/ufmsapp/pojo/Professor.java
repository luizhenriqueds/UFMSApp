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
