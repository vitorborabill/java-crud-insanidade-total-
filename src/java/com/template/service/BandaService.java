package com.template.service;

import com.template.model.dao.BandaDAO;
import com.template.model.dto.BandaDTO;
import com.template.validator.BandaValidator;
import com.template.validator.BandaValidator.ResultadoValidacao;

import java.util.ArrayList;

public class BandaService {

    private final BandaDAO bandaDAO = new BandaDAO();

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

    public ResultadoOperacao cadastrarBanda(String nome, String origem, String anoOrigemTexto, boolean eDaResenha) {
        ResultadoValidacao validacao = BandaValidator.validarBanda(nome, origem, anoOrigemTexto);
        if (!validacao.isValido()) {
            return new ResultadoOperacao(false, validacao.getMensagem());
        }

        BandaDTO novaBanda = new BandaDTO();
        novaBanda.setNome(nome.trim());
        novaBanda.setOrigem(origem.trim());
        novaBanda.setAnoOrigem(Integer.parseInt(anoOrigemTexto.trim()));
        novaBanda.setEDaResenha(eDaResenha);

        boolean cadastrou = bandaDAO.cadastrarBanda(novaBanda);
        return cadastrou
                ? new ResultadoOperacao(true, "Banda cadastrada com sucesso!")
                : new ResultadoOperacao(false, "Não foi possível cadastrar a banda.");
    }

    public ResultadoOperacao atualizarBanda(BandaDTO bandaSelecionada, String nome, String origem,
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

    public ResultadoOperacao deletarBanda(BandaDTO bandaSelecionada) {
        if (bandaSelecionada == null) {
            return new ResultadoOperacao(false, "Selecione uma banda para deletar!");
        }
        bandaDAO.deletarBanda(bandaSelecionada);
        return new ResultadoOperacao(true, "Banda deletada com sucesso!");
    }
}
