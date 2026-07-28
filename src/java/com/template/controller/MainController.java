package com.template.controller;

import com.template.model.dao.BandaDAO;
import com.template.model.dto.BandaDTO;
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
import javafx.scene.control.Label;

public class MainController {
    @FXML private Button btnDeletar;
    @FXML private Button btnEditar;
    @FXML private Button btnSalvar;
    @FXML private Button btnLimpar;
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtOrigem;
    @FXML private TextField txtAnoOrigem;
    @FXML private CheckBox chkResenha;
    @FXML private ImageView imgSadan;
    @FXML private TableView<BandaDTO> tblBanda;
    @FXML private TableColumn<BandaDTO, Integer> colId;
    @FXML private TableColumn<BandaDTO, String> colNome;
    @FXML private TableColumn<BandaDTO, String> colOrigem;
    @FXML private TableColumn<BandaDTO, Integer> colAnoOrigem;
    @FXML private TableColumn<BandaDTO, Boolean> colResenheira;
    @FXML private Label lblAviso;

    @FXML
    private void carregarBandas() {
        BandaDAO objBandaDAO = new BandaDAO();
        ArrayList<BandaDTO> listaBandas = objBandaDAO.selecionarBanda();
        tblBanda.setItems(FXCollections.observableArrayList(listaBandas));
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        try {
            String nome = txtNome.getText();
            String origem = txtOrigem.getText();
            int anoOrigem = Integer.parseInt(txtAnoOrigem.getText());
            boolean eDaResenha = chkResenha.isSelected();

            BandaDTO objbandadto = new BandaDTO();
            objbandadto.setNome(nome);
            objbandadto.setOrigem(origem);
            objbandadto.setAnoOrigem(anoOrigem);
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

        } catch (NumberFormatException e) {
            lblAviso.setText("O ano de origem deve conter apenas números!");
        }
    }

    @FXML
    private void btnLimparActon(ActionEvent event) {
        txtId.setText("");
        txtNome.setText("");
        txtOrigem.setText("");
        txtAnoOrigem.setText("");
        chkResenha.setSelected(false);
        lblAviso.setText("");
    }

    @FXML
    private void btnEditarAction() {
        BandaDTO bandadto = tblBanda.getSelectionModel().getSelectedItem();

        if (bandadto != null) {
            try {
                bandadto.setNome(txtNome.getText());
                bandadto.setOrigem(txtOrigem.getText());
                bandadto.setAnoOrigem(Integer.parseInt(txtAnoOrigem.getText()));
                bandadto.setEDaResenha(chkResenha.isSelected());

                BandaDAO objbandadao = new BandaDAO();
                objbandadao.atualizarBanda(bandadto);

                carregarBandas();
                btnLimparActon(null);

            } catch (NumberFormatException e) {
                lblAviso.setText("Não foi possível editar. O ano deve ser apenas números!");
            }
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
        colAnoOrigem.setCellValueFactory(new PropertyValueFactory<>("anoOrigem"));
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
                txtAnoOrigem.setText(String.valueOf(newSelection.getAnoOrigem()));
                chkResenha.setSelected(newSelection.isEDaResenha());
                lblAviso.setText("");
            }
        });

        System.out.println("FXML loaded successfully!");
    }
}