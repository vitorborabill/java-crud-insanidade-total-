package com.template.controller;

import com.template.model.dao.BandaDAO;
import com.template.model.dto.BandaDTO;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.template.util.DialogUtil.mostrarErro;
import static com.template.util.DialogUtil.mostrarInfo;
import static com.template.validator.BandaValidator.validarBanda;

public class MainController {

    @FXML private Button btnSalvar;
    @FXML private Button btnEditar;
    @FXML private Button btnDeletar;
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
    @FXML private Label lblValidacao;
    @FXML private Label lblMensagemDados;

    @FXML
    public void initialize() {
        if (lblValidacao != null) lblValidacao.setVisible(false);
        if (lblMensagemDados != null) lblMensagemDados.setVisible(false);
        if (imgSadan != null) imgSadan.setVisible(false);

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
                    setText(item ? "Sim" : "Não");
                }
            }
        });

        btnEditar.disableProperty().bind(txtNome.textProperty().isEmpty());
        btnDeletar.disableProperty().bind(txtNome.textProperty().isEmpty());
        btnSalvar.disableProperty().bind(txtNome.textProperty().isEmpty());
        btnLimpar.disableProperty().bind(txtNome.textProperty().isEmpty());

        tblBanda.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                carregarCampos();
            }
        });

        carregarBandas();
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (!preencherCampos()) {
            mostrarErro("Erro! Preencha todos os campos corretamente!");
            return;
        }

        String nome = txtNome.getText().trim();
        String origem = txtOrigem.getText().trim();
        int anoOrigem = Integer.parseInt(txtAnoOrigem.getText().trim());
        boolean eDaResenha = chkResenha.isSelected();

        BandaDTO objbandadto = new BandaDTO();
        objbandadto.setNome(nome);
        objbandadto.setOrigem(origem);
        objbandadto.setAnoOrigem(anoOrigem);
        objbandadto.setEDaResenha(eDaResenha);

        BandaDAO objbandadao = new BandaDAO();
        boolean salvou = objbandadao.cadastrarBanda(objbandadto);

        if (salvou) {
            if (imgSadan != null) {
                imgSadan.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> imgSadan.setVisible(false));
                pause.play();
            }
            mostrarInfo("Banda cadastrada com sucesso!");
        }

        carregarBandas();
        btnLimparAction(null);
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        if (!preencherCampos()) {
            mostrarErro("Erro! Preencha todos os campos corretamente!");
            return;
        }

        BandaDTO bandaSelecionada = tblBanda.getSelectionModel().getSelectedItem();

        if (bandaSelecionada != null) {
            bandaSelecionada.setNome(txtNome.getText().trim());
            bandaSelecionada.setOrigem(txtOrigem.getText().trim());
            bandaSelecionada.setAnoOrigem(Integer.parseInt(txtAnoOrigem.getText().trim()));
            bandaSelecionada.setEDaResenha(chkResenha.isSelected());

            BandaDAO objbandadao = new BandaDAO();
            objbandadao.atualizarBanda(bandaSelecionada);

            carregarBandas();
            btnLimparAction(null);
            mostrarInfo("Banda atualizada com sucesso!");
        } else {
            mostrarErro("Selecione uma banda na tabela para editar.");
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        BandaDTO bandaSelecionada = tblBanda.getSelectionModel().getSelectedItem();

        if (bandaSelecionada != null) {
            BandaDAO objbandadao = new BandaDAO();
            objbandadao.deletarBanda(bandaSelecionada);

            carregarBandas();
            btnLimparAction(null);
            mostrarInfo("Banda deletada com sucesso!");
        } else {
            mostrarErro("Selecione uma banda para deletar!");
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        txtId.clear();
        txtNome.clear();
        txtOrigem.clear();
        txtAnoOrigem.clear();
        chkResenha.setSelected(false);
        if (lblAviso != null) lblAviso.setText("");
        tblBanda.getSelectionModel().clearSelection();
    }

    @FXML
    private void carregarBandas() {
        BandaDAO objBandaDAO = new BandaDAO();
        ArrayList<BandaDTO> listaBandas = objBandaDAO.selecionarBanda();
        tblBanda.setItems(FXCollections.observableArrayList(listaBandas));
    }

    @FXML
    private void carregarCampos() {
        BandaDTO bandaDto = tblBanda.getSelectionModel().getSelectedItem();

        if (bandaDto != null) {
            txtId.setText(String.valueOf(bandaDto.getId()));
            txtNome.setText(bandaDto.getNome());
            txtOrigem.setText(bandaDto.getOrigem());
            txtAnoOrigem.setText(String.valueOf(bandaDto.getAnoOrigem()));
            chkResenha.setSelected(bandaDto.isEDaResenha());
        }
    }

    private boolean verificarLetra(String texto) {
        String regra = "^[a-zA-ZáéíóúàèìòùâêîôûãõçÇÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕ0-9\\s]+$";
        return texto.matches(regra);
    }

    private boolean preencherCampos() {
        if (!validarBanda(txtNome.getText().trim(), txtOrigem.getText().trim(), txtAnoOrigem.getText().trim())) {
            if (lblValidacao != null) {
                lblValidacao.setText("Por favor, preencha todos os campos!");
                lblValidacao.setVisible(true);

                PauseTransition pausa = new PauseTransition(Duration.seconds(3));
                pausa.setOnFinished(e -> lblValidacao.setVisible(false));
                pausa.play();
            }
            return false;
        }

        if (!verificarLetra(txtNome.getText().trim())) {
            if (lblValidacao != null) {
                lblValidacao.setText("Erro: O nome contém caracteres inválidos.");
                lblValidacao.setVisible(true);

                PauseTransition pausa = new PauseTransition(Duration.seconds(3));
                pausa.setOnFinished(ev -> lblValidacao.setVisible(false));
                pausa.play();
            }
            return false;
        }

        try {
            Integer.parseInt(txtAnoOrigem.getText().trim());
        } catch (NumberFormatException e) {
            mostrarErro("Erro de formato!");
            if (lblMensagemDados != null) {
                lblMensagemDados.setText("O ano de origem deve ser numérico!");
                lblMensagemDados.setVisible(true);

                PauseTransition pausa = new PauseTransition(Duration.seconds(3));
                pausa.setOnFinished(ev -> lblMensagemDados.setVisible(false));
                pausa.play();
            }
            return false;
        }

        return true;
    }
}