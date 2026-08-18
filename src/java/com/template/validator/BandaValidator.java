package com.template.validator;

public class BandaValidator {

    private static final String REGRA_NOME = "^[a-zA-ZáéíóúàèìòùâêîôûãõçÇÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕ0-9\\s]+$";

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

    public static ResultadoValidacao validarCamposPreenchidos(String nome, String origem, String anoOrigem) {
        if (nome.isEmpty() || origem.isEmpty() || anoOrigem.isEmpty()) {
            return new ResultadoValidacao(false, "Por favor, preencha todos os campos!");
        }
        return new ResultadoValidacao(true, null);
    }

    public static ResultadoValidacao validarNome(String nome) {
        if (!nome.matches(REGRA_NOME)) {
            return new ResultadoValidacao(false, "Erro: O nome contém caracteres inválidos.");
        }
        return new ResultadoValidacao(true, null);
    }

    public static ResultadoValidacao validarAnoOrigem(String anoOrigem) {
        try {
            Integer.parseInt(anoOrigem);
            return new ResultadoValidacao(true, null);
        } catch (NumberFormatException e) {
            return new ResultadoValidacao(false, "O ano de origem deve ser numérico!");
        }
    }

    public static ResultadoValidacao validarBanda(String nome, String origem, String anoOrigem) {
        ResultadoValidacao camposPreenchidos = validarCamposPreenchidos(nome, origem, anoOrigem);
        if (!camposPreenchidos.isValido()) return camposPreenchidos;

        ResultadoValidacao ano = validarAnoOrigem(anoOrigem);
        if (!ano.isValido()) return ano;

        return validarNome(nome);
    }

    public static boolean validarTermo(String termo) {
        return !termo.isEmpty();
    }
}