package com.template.controller;

import com.template.model.dto.BandaDTO;
import com.template.service.BandaService;
import com.template.service.BandaService.ResultadoOperacao;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static com.template.util.DialogUtil.mostrarErro;
import static com.template.util.DialogUtil.mostrarInfo;

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

    private final BandaService bandaService = new BandaService();

    @FXML
    public void initialize() {
        if (lblValidacao != null) lblValidacao.setVisible(false);
        if (lblMensagemDados != null) lblMensagemDados.setVisible(false);
        if (imgSadan != null) imgSadan.setVisible(false);

        configurarColunasTabela();

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

    private void configurarColunasTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
        colAnoOrigem.setCellValueFactory(new PropertyValueFactory<>("anoOrigem"));
        colResenheira.setCellValueFactory(new PropertyValueFactory<>("eDaResenha"));

        colResenheira.setCellFactory(tc -> new TableCell<BandaDTO, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : (item ? "Sim" : "Não"));
            }
        });
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        ResultadoOperacao resultadoCadastro = bandaService.cadastrarBanda(
                txtNome.getText().trim(),
                txtOrigem.getText().trim(),
                txtAnoOrigem.getText().trim(),
                chkResenha.isSelected()
        );

        if (!resultadoCadastro.isSucesso()) {
            exibirErroValidacao(resultadoCadastro.getMensagem());
            return;
        }

        exibirConfirmacaoVisual();
        mostrarInfo(resultadoCadastro.getMensagem());
        carregarBandas();
        btnLimparAction(null);
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        BandaDTO bandaSelecionada = tblBanda.getSelectionModel().getSelectedItem();

        if (bandaSelecionada == null) {
            mostrarErro("Selecione uma banda na tabela para editar.");
            return;
        }

        ResultadoOperacao resultadoEdicao = bandaService.atualizarBanda(
                bandaSelecionada,
                txtNome.getText().trim(),
                txtOrigem.getText().trim(),
                txtAnoOrigem.getText().trim(),
                chkResenha.isSelected()
        );

        if (!resultadoEdicao.isSucesso()) {
            exibirErroValidacao(resultadoEdicao.getMensagem());
            return;
        }

        carregarBandas();
        btnLimparAction(null);
        mostrarInfo(resultadoEdicao.getMensagem());
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        BandaDTO bandaSelecionada = tblBanda.getSelectionModel().getSelectedItem();
        ResultadoOperacao resultadoExclusao = bandaService.deletarBanda(bandaSelecionada);

        if (!resultadoExclusao.isSucesso()) {
            mostrarErro(resultadoExclusao.getMensagem());
            return;
        }

        carregarBandas();
        btnLimparAction(null);
        mostrarInfo(resultadoExclusao.getMensagem());
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

    private void carregarBandas() {
        tblBanda.setItems(FXCollections.observableArrayList(bandaService.listarBandas()));
    }

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

    private void exibirErroValidacao(String mensagem) {
        mostrarErro(mensagem);
        Label labelAlvo = lblValidacao != null ? lblValidacao : lblMensagemDados;
        if (labelAlvo == null) return;

        labelAlvo.setText(mensagem);
        labelAlvo.setVisible(true);

        PauseTransition pausa = new PauseTransition(Duration.seconds(3));
        pausa.setOnFinished(e -> labelAlvo.setVisible(false));
        pausa.play();
    }

    private void exibirConfirmacaoVisual() {
        if (imgSadan == null) return;
        imgSadan.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> imgSadan.setVisible(false));
        pause.play();
    }
}
