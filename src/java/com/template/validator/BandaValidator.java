package com.template.validator;

import static com.template.util.DialogUtil.mostrarErro;

public class BandaValidator {

    public static boolean validarBanda(String nome, String origem, String anoOrigem) {
        if (nome.isEmpty() || origem.isEmpty() || anoOrigem.isEmpty()) {
            mostrarErro("Preencha todos os campos antes de prosseguir");
            return false;
        }
        return true;
    }

    public static boolean validarTermo(String termo) {
        if (termo.isEmpty()) {
            mostrarErro("Digite um termo de pesquisa");
            return false;
        }
        return true;
    }
}