package br.ufjf.dcc196.hunterapp.Model;

public class Candidato {

    private Long id;
    private String nome;
    private String nascimento;
    private String telefone;
    private String perfil;
    private String email;

    private Double sumHorasAtividades;


    public Candidato() {
    }

    public Candidato(Long id, String nome, String nascimento, String telefone, String perfil, String email) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.perfil = perfil;
        this.email = email;
    }
    public Candidato( String nome, String nascimento, String telefone, String perfil, String email) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.perfil = perfil;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSumHorasAtividades() {
        return sumHorasAtividades;
    }

    public void setSumHorasAtividades(Double sumHorasAtividades) {
        this.sumHorasAtividades = sumHorasAtividades;
    }

    @Override
    public String toString() {
        return "Candidato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
