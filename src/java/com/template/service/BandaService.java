package com.template.service;

import com.template.model.dao.BandaDAO;
import com.template.model.dto.BandaDTO;
import com.template.validator.BandaValidator;
import com.template.validator.BandaValidator.ResultadoValidacao;

import java.util.ArrayList;

public class BandaService {

    private final BandaDAO bandaDAO;

    public BandaService() {
        this.bandaDAO = new BandaDAO();
    }

    public static class ResultadoOperacao {
        private final boolean sucesso;
        private final String mensagem;

        public ResultadoOperacao(boolean sucesso, String mensagem) {
            this.sucesso = sucesso;
            this.mensagem = mensagem;
        }

        public boolean isSucesso() {
            return sucesso;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

    public ArrayList<BandaDTO> listarBandas() {
        return bandaDAO.selecionarBanda();
    }

    public ResultadoOperacao cadastrar(String nome, String origem, String anoOrigemTexto, boolean eDaResenha) {
        ResultadoValidacao validacao = BandaValidator.validarBanda(nome, origem, anoOrigemTexto);
        if (!validacao.isValido()) {
            return new ResultadoOperacao(false, validacao.getMensagem());
        }

        BandaDTO banda = new BandaDTO();
        banda.setNome(nome.trim());
        banda.setOrigem(origem.trim());
        banda.setAnoOrigem(Integer.parseInt(anoOrigemTexto.trim()));
        banda.setEDaResenha(eDaResenha);

        boolean salvou = bandaDAO.cadastrarBanda(banda);
        return salvou
                ? new ResultadoOperacao(true, "Banda cadastrada com sucesso!")
                : new ResultadoOperacao(false, "Não foi possível cadastrar a banda.");
    }

    public ResultadoOperacao atualizar(BandaDTO bandaSelecionada, String nome, String origem,
                                       String anoOrigemTexto, boolean eDaResenha) {
        ResultadoValidacao validacao = BandaValidator.validarBanda(nome, origem, anoOrigemTexto);
        if (!validacao.isValido()) {
            return new ResultadoOperacao(false, validacao.getMensagem());
        }

        bandaSelecionada.setNome(nome.trim());
        bandaSelecionada.setOrigem(origem.trim());
        bandaSelecionada.setAnoOrigem(Integer.parseInt(anoOrigemTexto.trim()));
        bandaSelecionada.setEDaResenha(eDaResenha);

        bandaDAO.atualizarBanda(bandaSelecionada);
        return new ResultadoOperacao(true, "Banda atualizada com sucesso!");
    }

    public ResultadoOperacao deletar(BandaDTO bandaSelecionada) {
        if (bandaSelecionada == null) {
            return new ResultadoOperacao(false, "Selecione uma banda para deletar!");
        }
        bandaDAO.deletarBanda(bandaSelecionada);
        return new ResultadoOperacao(true, "Banda deletada com sucesso!");
    }
}