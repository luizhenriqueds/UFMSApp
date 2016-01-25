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
