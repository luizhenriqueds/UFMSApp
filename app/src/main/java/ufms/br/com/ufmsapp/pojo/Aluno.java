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


public class Aluno {

    int id;
    String nome;
    String email;
    String rga;
    int statusAluno;
    int alunoIdServidor;

    public Aluno(String nome, String email, String rga, int statusAluno, int alunoIdServidor) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.rga = rga;
        this.statusAluno = statusAluno;
        this.alunoIdServidor = alunoIdServidor;
    }


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
