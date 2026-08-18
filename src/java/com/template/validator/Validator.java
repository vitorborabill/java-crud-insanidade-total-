package com.template.validator;

import java.util.regex.Pattern;

public interface Validator<T> {
    boolean validar(T valor);
    String getMensagemErro();
    T getValor();
}

class CampoObrigatorioValidador implements Validator<String> {
    private final String nomeCampo;
    private final String valor;

    public CampoObrigatorioValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        return valorAtual != null && !valorAtual.trim().isEmpty();
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve ser preenchido.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}

class NomeBandaValidador implements Validator<String> {
    private static final String REGRA_NOME = "^[a-zA-ZáéíóúàèìòùâêîôûãõçÇÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕ0-9\\s]+$";
    private final Pattern pattern = Pattern.compile(REGRA_NOME);
    private final String nome;

    public NomeBandaValidador(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean validar(String valorAtual) {
        return nome != null && pattern.matcher(nome).matches();
    }

    @Override
    public String getMensagemErro() {
        return "Erro: O nome contém caracteres inválidos.";
    }

    @Override
    public String getValor() {
        return nome;
    }
}

class AnoOrigemValidador implements Validator<String> {
    private final String anoOrigem;

    public AnoOrigemValidador(String anoOrigem) {
        this.anoOrigem = anoOrigem;
    }

    @Override
    public boolean validar(String valorAtual) {
        try {
            Integer.parseInt(anoOrigem.trim());
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "O ano de origem deve ser numérico!";
    }

    @Override
    public String getValor() {
        return anoOrigem;
    }
}
