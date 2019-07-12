package br.ufjf.dcc196.hunterapp.Model;

public class Producao {

    private Long id;
    private String titulo;
    private String descricao;
    private String inicio;
    private String fim;

    private Long categoriaId;
    private Long candidatoId;

    public Producao() {;
    }

    public Producao(Long id, String titulo, String descricao, String inicio, String fim, Long categoriaId, Long candidatoId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
        this.categoriaId = categoriaId;
        this.candidatoId = candidatoId;
    }
    public Producao( String titulo, String descricao, String inicio, String fim, Long categoriaId, Long candidatoId) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
        this.categoriaId = categoriaId;
        this.candidatoId = candidatoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(Long candidatoId) {
        this.candidatoId = candidatoId;
    }

    @Override
    public String toString() {
        return "Producao{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                '}';
    }
}
