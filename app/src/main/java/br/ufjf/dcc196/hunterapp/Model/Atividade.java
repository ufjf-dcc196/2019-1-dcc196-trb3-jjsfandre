package br.ufjf.dcc196.hunterapp.Model;

public class Atividade {

    private Long id;
    private String descricao;
    private String data;
    private Double horas;

    private Long producaoId;


    public Atividade() {
    }

    public Atividade(Long id, String descricao, String data, Double horas, Long producaoId) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.horas = horas;
        this.producaoId = producaoId;
    }
    public Atividade( String descricao, String data, Double horas, Long producaoId) {
        this.descricao = descricao;
        this.data = data;
        this.horas = horas;
        this.producaoId = producaoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getHoras() {
        return horas;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    public Long getProducaoId() {
        return producaoId;
    }

    public void setProducaoId(Long producaoId) {
        this.producaoId = producaoId;
    }

    @Override
    public String toString() {
        return "Atividade{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
