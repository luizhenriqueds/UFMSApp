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


public class TituloProfessor {

    int id;
    String tituloProfessor;
    int tituloProfessorIdServidor;

    public TituloProfessor(int id, String tituloProfessor, int tituloProfessorIdServidor) {
        this.id = id;
        this.tituloProfessor = tituloProfessor;
        this.tituloProfessorIdServidor = tituloProfessorIdServidor;
    }

    public TituloProfessor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTituloProfessor() {
        return tituloProfessor;
    }

    public void setTituloProfessor(String tituloProfessor) {
        this.tituloProfessor = tituloProfessor;
    }

    public int getTituloProfessorIdServidor() {
        return tituloProfessorIdServidor;
    }

    public void setTituloProfessorIdServidor(int tituloProfessorIdServidor) {
        this.tituloProfessorIdServidor = tituloProfessorIdServidor;
    }

    @Override
    public String toString() {
        return "TituloProfessor{" +
                "id=" + id +
                ", tituloProfessor='" + tituloProfessor + '\'' +
                ", tituloProfessorIdServidor=" + tituloProfessorIdServidor +
                '}';
    }
}
