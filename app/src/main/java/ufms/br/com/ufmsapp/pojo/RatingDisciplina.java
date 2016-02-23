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


public class RatingDisciplina {

    int idRatingDisciplina;
    int alunoKey;
    int disciplinaKey;
    float rating;

    public RatingDisciplina() {
    }

    public RatingDisciplina(int alunoKey, int disciplinaKey, float rating) {
        this.alunoKey = alunoKey;
        this.disciplinaKey = disciplinaKey;
        this.rating = rating;
    }

    public RatingDisciplina(int idRatingDisciplina, int alunoKey, int disciplinaKey, float rating) {
        this.idRatingDisciplina = idRatingDisciplina;
        this.alunoKey = alunoKey;
        this.disciplinaKey = disciplinaKey;
        this.rating = rating;
    }

    public int getIdRatingDisciplina() {
        return idRatingDisciplina;
    }

    public void setIdRatingDisciplina(int idRatingDisciplina) {
        this.idRatingDisciplina = idRatingDisciplina;
    }

    public int getAlunoKey() {
        return alunoKey;
    }

    public void setAlunoKey(int alunoKey) {
        this.alunoKey = alunoKey;
    }

    public int getDisciplinaKey() {
        return disciplinaKey;
    }

    public void setDisciplinaKey(int disciplinaKey) {
        this.disciplinaKey = disciplinaKey;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "RatingDisciplina{" +
                "idRatingDisciplina=" + idRatingDisciplina +
                ", alunoKey=" + alunoKey +
                ", disciplinaKey=" + disciplinaKey +
                ", rating=" + rating +
                '}';
    }

}
