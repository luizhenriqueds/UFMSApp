package ufms.br.com.ufmsapp.pojo;


public class Material {

    int idMaterial;
    String pathMaterial;
    int eventoKey;

    public Material(int idMaterial, String pathMaterial, int eventoKey) {
        this.idMaterial = idMaterial;
        this.pathMaterial = pathMaterial;
        this.eventoKey = eventoKey;
    }

    public Material() {
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getPathMaterial() {
        return pathMaterial;
    }

    public void setPathMaterial(String pathMaterial) {
        this.pathMaterial = pathMaterial;
    }

    public int getEventoKey() {
        return eventoKey;
    }

    public void setEventoKey(int eventoKey) {
        this.eventoKey = eventoKey;
    }

    @Override
    public String toString() {
        return "Material{" +
                "idMaterial=" + idMaterial +
                ", pathMaterial='" + pathMaterial + '\'' +
                ", eventoKey=" + eventoKey +
                '}';
    }
}
