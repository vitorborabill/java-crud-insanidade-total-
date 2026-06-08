package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class MainController {
    @FXML private Button btnDeletar;
    @FXML private Button btnEditar;
    @FXML private Button btnSalvar;
    @FXML private Button btnLimpar;
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtOrigem;
    @FXML private TextField txtAnoFormacao;

    @FXML private TableView<BandaDTO> tblBanda;
    @FXML private TableColumn<BandaDTO, Integer> colId;
    @FXML private TableColumn<BandaDTO, String> colNome;
    @FXML private TableColumn<BandaDTO, String> colOrigem;
    @FXML private TableColumn<BandaDTO, Integer> colAnoFormacao;

    @FXML
    private void carregarBandas() {
        BandaDAO objBandaDAO = new BandaDAO();
        ArrayList<BandaDTO> listaBandas = objBandaDAO.selecionarBanda();
        tblBanda.setItems(FXCollections.observableArrayList(listaBandas));
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        String nome = txtNome.getText();
        String origem = txtOrigem.getText();
        int anoFormacao = Integer.parseInt(txtAnoFormacao.getText());

        BandaDTO objbandadto = new BandaDTO();
        objbandadto.setNome(nome);
        objbandadto.setOrigem(origem);
        objbandadto.setAnoFormacao(anoFormacao);

        BandaDAO objbandadao = new BandaDAO();
        objbandadao.cadastrarBanda(objbandadto);

        carregarBandas();
        btnLimparActon(null);
    }

    // Mantido o nome "btnLimparActon" sem o 'i' para bater exatamente com a sua tag onAction no FXML
    @FXML
    private void btnLimparActon(ActionEvent event) {
        txtId.setText("");
        txtNome.setText("");
        txtOrigem.setText("");
        txtAnoFormacao.setText("");
    }

    @FXML
    private void btnEditarAction() {
        BandaDTO bandadto = tblBanda.getSelectionModel().getSelectedItem();

        if (bandadto != null) {
            bandadto.setNome(txtNome.getText());
            bandadto.setOrigem(txtOrigem.getText());
            bandadto.setAnoFormacao(Integer.parseInt(txtAnoFormacao.getText()));

            BandaDAO objbandadao = new BandaDAO();
            objbandadao.atualizarBanda(bandadto);

            carregarBandas();
            btnLimparActon(null);
        }
    }

    @FXML
    private void btnDeletarAction() {
        BandaDTO bandadto = tblBanda.getSelectionModel().getSelectedItem();

        if (bandadto != null) {
            BandaDAO objbandadao = new BandaDAO();
            objbandadao.deletarBanda(bandadto);

            carregarBandas();
            btnLimparActon(null);
        }
    }

    @FXML
    private void initialize() {
        // Configura as colunas da tabela para puxar os atributos do BandaDTO automaticamente
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
        colAnoFormacao.setCellValueFactory(new PropertyValueFactory<>("anoFormacao"));

        // Carrega os dados do banco assim que a tela abre
        carregarBandas();

        // Listener opcional: preenche os campos de texto ao clicar em uma linha da tabela (facilita o Editar/Deletar)
        tblBanda.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(String.valueOf(newSelection.getId()));
                txtNome.setText(newSelection.getNome());
                txtOrigem.setText(newSelection.getOrigem());
                txtAnoFormacao.setText(String.valueOf(newSelection.getAnoFormacao()));
            }
        });

        System.out.println("FXML loaded successfully!");
    }
}