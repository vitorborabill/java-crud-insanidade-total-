package com.template;

public class BandaDTO {
    private int id;
    private String nome;
    private String origem;
    private int anoOrigem;
    private boolean eDaResenha;

    public BandaDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public int getAnoOrigem() {
        return anoOrigem;
    }

    public void setAnoOrigem(int anoOrigem) {
        this.anoOrigem = anoOrigem;
    }

    public boolean isEDaResenha() {
        return eDaResenha;
    }

    public void setEDaResenha(boolean eDaResenha) {
        this.eDaResenha = eDaResenha;
    }
}