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
