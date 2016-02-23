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


public class AlunoXDisciplina {

    int id;
    int aluno;
    int disciplina;
    int statusDisciplina;
    int turma;
    int alunoXDisciplinaIdServidor;

    public AlunoXDisciplina(int id, int aluno, int disciplina, int statusDisciplina, int turma, int alunoXDisciplinaIdServidor) {
        this.id = id;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.statusDisciplina = statusDisciplina;
        this.turma = turma;
        this.alunoXDisciplinaIdServidor = alunoXDisciplinaIdServidor;
    }

    public AlunoXDisciplina() {
    }

    public int getAlunoXDisciplinaIdServidor() {
        return alunoXDisciplinaIdServidor;
    }

    public void setAlunoXDisciplinaIdServidor(int alunoXDisciplinaIdServidor) {
        this.alunoXDisciplinaIdServidor = alunoXDisciplinaIdServidor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAluno() {
        return aluno;
    }

    public void setAluno(int aluno) {
        this.aluno = aluno;
    }

    public int getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(int disciplina) {
        this.disciplina = disciplina;
    }

    public int getStatusDisciplina() {
        return statusDisciplina;
    }

    public void setStatusDisciplina(int statusDisciplina) {
        this.statusDisciplina = statusDisciplina;
    }

    public int getTurma() {
        return turma;
    }

    public void setTurma(int turma) {
        this.turma = turma;
    }

    @Override
    public String toString() {
        return "AlunoXDisciplina{" +
                "id=" + id +
                ", aluno=" + aluno +
                ", disciplina=" + disciplina +
                ", statusDisciplina=" + statusDisciplina +
                ", turma=" + turma +
                '}';
    }
}
