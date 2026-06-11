package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
    @FXML private CheckBox chkResenha;
    @FXML private ImageView imgSadan;
    @FXML private TableView<BandaDTO> tblBanda;
    @FXML private TableColumn<BandaDTO, Integer> colId;
    @FXML private TableColumn<BandaDTO, String> colNome;
    @FXML private TableColumn<BandaDTO, String> colOrigem;
    @FXML private TableColumn<BandaDTO, Integer> colAnoFormacao;
    @FXML private TableColumn<BandaDTO, Boolean> colResenheira;

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
        boolean eDaResenha = chkResenha.isSelected();

        BandaDTO objbandadto = new BandaDTO();
        objbandadto.setNome(nome);
        objbandadto.setOrigem(origem);
        objbandadto.setAnoFormacao(anoFormacao);
        objbandadto.setEDaResenha(eDaResenha);

        BandaDAO objbandadao = new BandaDAO();
        boolean salvou = objbandadao.cadastrarBanda(objbandadto);

        if (salvou) {
            imgSadan.setVisible(true);
            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
            pause.setOnFinished(e -> imgSadan.setVisible(false));
            pause.play();
        }
        carregarBandas();
        btnLimparActon(null);
    }

    @FXML
    private void btnLimparActon(ActionEvent event) {
        txtId.setText("");
        txtNome.setText("");
        txtOrigem.setText("");
        txtAnoFormacao.setText("");
        chkResenha.setSelected(false);
    }

    @FXML
    private void btnEditarAction() {
        BandaDTO bandadto = tblBanda.getSelectionModel().getSelectedItem();

        if (bandadto != null) {
            bandadto.setNome(txtNome.getText());
            bandadto.setOrigem(txtOrigem.getText());
            bandadto.setAnoFormacao(Integer.parseInt(txtAnoFormacao.getText()));
            bandadto.setEDaResenha(chkResenha.isSelected());

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
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
        colAnoFormacao.setCellValueFactory(new PropertyValueFactory<>("anoFormacao"));
        colResenheira.setCellValueFactory(new PropertyValueFactory<>("eDaResenha"));

        colResenheira.setCellFactory(tc -> new TableCell<BandaDTO, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if(item){
                        setText("Sim");
                    }else{
                        setText("Não");
                    }
                }
            }
        });

        carregarBandas();

        tblBanda.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(String.valueOf(newSelection.getId()));
                txtNome.setText(newSelection.getNome());
                txtOrigem.setText(newSelection.getOrigem());
                txtAnoFormacao.setText(String.valueOf(newSelection.getAnoFormacao()));
                chkResenha.setSelected(newSelection.isEDaResenha());
            }
        });

        System.out.println("FXML loaded successfully!");
    }
}