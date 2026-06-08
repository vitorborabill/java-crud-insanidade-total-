package com.template;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;

public class BandaDAO {
    private static final Logger logger = getLogger(BandaDAO.class.getName());

    public ArrayList<BandaDTO> selecionarBanda() {
        ArrayList<BandaDTO> ListaBandas = new ArrayList<>();
        // Buscando as colunas específicas do banco (ano_origem)
        String sql = "SELECT id, nome, origem, ano_origem FROM banda";

        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                BandaDTO banda = new BandaDTO();
                banda.setId(resultado.getInt("id"));
                banda.setNome(resultado.getString("nome"));
                banda.setOrigem(resultado.getString("origem"));
                // Mapeia a coluna 'ano_origem' do banco para o 'anoFormacao' do Java
                banda.setAnoFormacao(resultado.getInt("ano_origem"));
                ListaBandas.add(banda);
            }

        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao listar banda", erro);
        }
        return ListaBandas;
    }

    public void cadastrarBanda(BandaDTO banda) {
        // Ajustado para 'ano_origem'
        String sql = "INSERT INTO banda (nome, origem, ano_origem) VALUES (?, ?, ?)";

        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, banda.getNome());
            comando.setString(2, banda.getOrigem());
            comando.setInt(3, banda.getAnoFormacao());

            comando.execute();

        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao cadastrar banda", erro);
        }
    }

    public void atualizarBanda(BandaDTO banda) {
        // Ajustado para 'ano_origem'
        String sql = "UPDATE banda SET nome = ?, origem = ?, ano_origem = ? WHERE id = ?";

        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, banda.getNome());
            comando.setString(2, banda.getOrigem());
            comando.setInt(3, banda.getAnoFormacao());
            comando.setInt(4, banda.getId());

            comando.execute();

        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao atualizar banda", erro);
        }
    }

    public void deletarBanda(BandaDTO banda) {
        String sql = "DELETE FROM banda WHERE id = ?";

        try (Connection conexao = new Conexao().conectaBD();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, banda.getId());
            comando.execute();

        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao deletar banda", erro);
        }
    }
}