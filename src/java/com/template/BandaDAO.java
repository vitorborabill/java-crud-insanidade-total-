package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BandaDAO {
    private static final Logger logger = Logger.getLogger(BandaDAO.class.getName());

    public BandaDAO() {
    }

    public ArrayList<BandaDTO> selecionarBanda() {
        ArrayList<BandaDTO> ListaBandas = new ArrayList<>();
        String sql = "SELECT id, nome, origem, ano_origem, e_da_resenha FROM banda";

        try (
                Connection conexao = (new Conexao()).conectaBD();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery();
        ) {
            while(resultado.next()) {
                BandaDTO banda = new BandaDTO();
                banda.setId(resultado.getInt("id"));
                banda.setNome(resultado.getString("nome"));
                banda.setOrigem(resultado.getString("origem"));
                banda.setAnoOrigem(resultado.getInt("ano_origem"));
                banda.setEDaResenha(resultado.getBoolean("e_da_resenha"));
                ListaBandas.add(banda);
            }
        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao listar banda", erro);
        }

        return ListaBandas;
    }

    public boolean cadastrarBanda(BandaDTO banda) {
        String sql = "INSERT INTO banda (nome, origem, ano_origem, e_da_resenha) VALUES (?, ?, ?, ?)";

        try (
                Connection conexao = (new Conexao()).conectaBD();
                PreparedStatement comando = conexao.prepareStatement(sql);
        ) {
            comando.setString(1, banda.getNome());
            comando.setString(2, banda.getOrigem());
            comando.setInt(3, banda.getAnoOrigem());
            comando.setBoolean(4, banda.isEDaResenha());
            comando.execute();
            return true;
        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao cadastrar banda", erro);
            return false;
        }
    }

    public void atualizarBanda(BandaDTO banda) {
        String sql = "UPDATE banda SET nome = ?, origem = ?, ano_origem = ?, e_da_resenha = ? WHERE id = ?";

        try (
                Connection conexao = (new Conexao()).conectaBD();
                PreparedStatement comando = conexao.prepareStatement(sql);
        ) {
            comando.setString(1, banda.getNome());
            comando.setString(2, banda.getOrigem());
            comando.setInt(3, banda.getAnoOrigem());
            comando.setBoolean(4, banda.isEDaResenha());
            comando.setInt(5, banda.getId());
            comando.execute();
        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao atualizar banda", erro);
        }
    }

    public void deletarBanda(BandaDTO banda) {
        String sql = "DELETE FROM banda WHERE id = ?";

        try (
                Connection conexao = (new Conexao()).conectaBD();
                PreparedStatement comando = conexao.prepareStatement(sql);
        ) {
            comando.setInt(1, banda.getId());
            comando.execute();
        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao deletar banda", erro);
        }
    }
}