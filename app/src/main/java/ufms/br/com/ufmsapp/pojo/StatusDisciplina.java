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


public class StatusDisciplina {

    int id;
    String statusDisciplina;
    int idServidorDisciplina;

    public StatusDisciplina() {
    }

    public StatusDisciplina(int id, String statusDisciplina, int idServidorDisciplina) {
        this.id = id;
        this.statusDisciplina = statusDisciplina;
        this.idServidorDisciplina = idServidorDisciplina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusDisciplina() {
        return statusDisciplina;
    }

    public void setStatusDisciplina(String statusDisciplina) {
        this.statusDisciplina = statusDisciplina;
    }

    public int getIdServidorDisciplina() {
        return idServidorDisciplina;
    }

    public void setIdServidorDisciplina(int idServidorDisciplina) {
        this.idServidorDisciplina = idServidorDisciplina;
    }

    @Override
    public String toString() {
        return "StatusDisciplina{" +
                "id=" + id +
                ", statusDisciplina='" + statusDisciplina + '\'' +
                ", idServidorDisciplina=" + idServidorDisciplina +
                '}';
    }
}
