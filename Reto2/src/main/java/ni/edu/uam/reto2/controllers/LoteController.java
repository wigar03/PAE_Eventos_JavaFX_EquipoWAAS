package ni.edu.uam.reto2.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ni.edu.uam.reto2.dao.LoteDAO;
import ni.edu.uam.reto2.models.Lote;

import java.time.LocalDate;
import java.util.Optional;

public class LoteController {

    // Campos del formulario
    @FXML private TextField txtIdLote;
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtCantidadKilos;
    @FXML private ComboBox<String> cbTipoGrano;
    @FXML private DatePicker dpFechaEntrega;
    @FXML private DatePicker dpFechaCaducidad;

    // Tabla
    @FXML private TableView<Lote> tvLotes;
    @FXML private TableColumn<Lote, String> colId;
    @FXML private TableColumn<Lote, String> colProducto;
    @FXML private TableColumn<Lote, String> colCantidad;
    @FXML private TableColumn<Lote, LocalDate> colFechaEntrega;
    @FXML private TableColumn<Lote, LocalDate> colFechaCaducidad;
    @FXML private TableColumn<Lote, String> colGrano;

    private final LoteDAO dao = new LoteDAO();
    private ObservableList<Lote> listaReactivaLotes;

    @FXML
    public void initialize() {
        // Opciones del ComboBox
        cbTipoGrano.setItems(FXCollections.observableArrayList(
                "Café",
                "Frijol",
                "Maíz",
                "Arroz",
                "Trigo"
        ));

        // Enlazar columnas con los atributos del modelo Lote
        colId.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadKilos"));
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
        colFechaCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        colGrano.setCellValueFactory(new PropertyValueFactory<>("tipoGrano"));

        // Cargar datos en la tabla
        listaReactivaLotes = FXCollections.observableArrayList(dao.obtenerTodos());
        tvLotes.setItems(listaReactivaLotes);

        // Menú contextual para editar y eliminar
        ContextMenu menuTabla = new ContextMenu();

        MenuItem menuEditar = new MenuItem("Guardar Edición");
        menuEditar.setOnAction(evento -> editarLote());

        MenuItem menuEliminar = new MenuItem("Eliminar Lote");
        menuEliminar.setOnAction(evento -> eliminarLote());

        menuTabla.getItems().addAll(menuEditar, menuEliminar);
        tvLotes.setContextMenu(menuTabla);
    }

    @FXML
    protected void agregarOnClick() {
        Lote nuevoLote = leerDatosFormulario();

        if (nuevoLote != null) {
            dao.agregar(nuevoLote);
            actualizarTabla();
            limpiarCampos();
        }
    }

    @FXML
    protected void limpiarOnClick() {
        limpiarCampos();
    }

    @FXML
    protected void onTablaMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Lote loteSeleccionado = tvLotes.getSelectionModel().getSelectedItem();

            if (loteSeleccionado != null) {
                txtIdLote.setText(loteSeleccionado.getIdLote());
                txtNombreProducto.setText(loteSeleccionado.getNombreProducto());
                txtCantidadKilos.setText(loteSeleccionado.getCantidadKilos());
                dpFechaEntrega.setValue(loteSeleccionado.getFechaEntrega());
                dpFechaCaducidad.setValue(loteSeleccionado.getFechaCaducidad());
                cbTipoGrano.setValue(loteSeleccionado.getTipoGrano());
            }
        }
    }

    private void editarLote() {
        int index = tvLotes.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            Lote loteActualizado = leerDatosFormulario();

            if (loteActualizado != null) {
                dao.editar(loteActualizado);
                actualizarTabla();
                limpiarCampos();
            }
        } else {
            mostrarAlerta("Seleccione un lote", "Debe seleccionar un lote de la tabla para editarlo.");
        }
    }

    private void eliminarLote() {
        int index = tvLotes.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación");
            alerta.setHeaderText("Borrar registro del sistema");
            alerta.setContentText("¿Estás seguro de que deseas eliminar este lote?");

            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                dao.eliminar(index);
                actualizarTabla();
                limpiarCampos();
            }
        } else {
            mostrarAlerta("Seleccione un lote", "Debe seleccionar un lote de la tabla para eliminarlo.");
        }
    }

    private Lote leerDatosFormulario() {
        String idLote = txtIdLote.getText();
        String nombreProducto = txtNombreProducto.getText();
        String cantidadKilos = txtCantidadKilos.getText();
        LocalDate fechaEntrega = dpFechaEntrega.getValue();
        LocalDate fechaCaducidad = dpFechaCaducidad.getValue();
        String tipoGrano = cbTipoGrano.getValue();

        if (idLote.isBlank()
                || nombreProducto.isBlank()
                || cantidadKilos.isBlank()
                || fechaEntrega == null
                || fechaCaducidad == null
                || tipoGrano == null) {

            mostrarAlerta("Campos incompletos", "Debe llenar todos los campos antes de guardar.");
            return null;
        }

        return new Lote(
                idLote,
                nombreProducto,
                cantidadKilos,
                fechaEntrega,
                fechaCaducidad,
                tipoGrano
        );
    }

    private void actualizarTabla() {
        listaReactivaLotes.setAll(dao.obtenerTodos());
    }

    private void limpiarCampos() {
        txtIdLote.clear();
        txtNombreProducto.clear();
        txtCantidadKilos.clear();
        dpFechaEntrega.setValue(null);
        dpFechaCaducidad.setValue(null);
        cbTipoGrano.getSelectionModel().clearSelection();
        txtIdLote.requestFocus();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}