package com.template.validator;

import java.util.ArrayList;
import java.util.List;

public class BandaValidator {

    public static class ResultadoValidacao {
        private final boolean valido;
        private final String mensagem;

        public ResultadoValidacao(boolean valido, String mensagem) {
            this.valido = valido;
            this.mensagem = mensagem;
        }

        public boolean isValido() {
            return valido;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

    public static ResultadoValidacao validarBanda(String nome, String origem, String anoOrigem) {
        List<Validator<String>> validadores = new ArrayList<>();

        validadores.add(new CampoObrigatorioValidador("Nome", nome));
        validadores.add(new CampoObrigatorioValidador("Origem", origem));
        validadores.add(new CampoObrigatorioValidador("Ano de origem", anoOrigem));
        validadores.add(new NomeBandaValidador(nome));
        validadores.add(new AnoOrigemValidador(anoOrigem));

        for (Validator<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                return new ResultadoValidacao(false, validador.getMensagemErro());
            }
        }
        return new ResultadoValidacao(true, null);
    }

    public static boolean validarTermo(String termo) {
        return !termo.isEmpty();
    }
}
