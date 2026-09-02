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

/**
 * Controlador de la interfaz gráfica del Reto #2: Cooperativa Agrícola.
 * 
 * Gestiona el formulario de captura de lotes, la validación de campos, la integración
 * con la capa de persistencia mediante {@link LoteDAO}, la interacción con el TableView
 * (doble clic y menú contextual) y la navegación hacia el Menú Integrador.
 * 
 * @author Equipo WAAS
 */
public class LoteController {

    // --- Componentes del Formulario de Registro ---
    @FXML private TextField txtIdLote;
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtCantidadKilos;
    @FXML private ComboBox<String> cbTipoGrano;
    @FXML private DatePicker dpFechaEntrega;
    @FXML private DatePicker dpFechaCaducidad;

    // --- Componentes de la Tabla de Visualización (TableView) ---
    @FXML private TableView<Lote> tvLotes;
    @FXML private TableColumn<Lote, String> colId;
    @FXML private TableColumn<Lote, String> colProducto;
    @FXML private TableColumn<Lote, String> colCantidad;
    @FXML private TableColumn<Lote, LocalDate> colFechaEntrega;
    @FXML private TableColumn<Lote, LocalDate> colFechaCaducidad;
    @FXML private TableColumn<Lote, String> colGrano;

    // Instancia del DAO para operaciones CRUD desacopladas
    private final LoteDAO dao = new LoteDAO();

    // Colección observable reactiva vinculada a los elementos de la tabla
    private ObservableList<Lote> listaReactivaLotes;

    /**
     * Inicialización del controlador tras el inflado del FXML.
     * Configura el catálogo de granos, enlaza las columnas de la tabla con las propiedades
     * del modelo Lote y asocia el menú contextual (clic derecho) para edición y eliminación.
     */
    @FXML
    public void initialize() {
        // Carga de opciones fijas en el selector de tipo de grano
        cbTipoGrano.setItems(FXCollections.observableArrayList(
                "Café",
                "Frijol",
                "Maíz",
                "Arroz",
                "Trigo"
        ));

        // Vinculación reflectiva de columnas con los métodos getters de Lote
        colId.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadKilos"));
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
        colFechaCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        colGrano.setCellValueFactory(new PropertyValueFactory<>("tipoGrano"));

        // Inicializa la lista observable y la asigna a la tabla
        listaReactivaLotes = FXCollections.observableArrayList(dao.obtenerTodos());
        tvLotes.setItems(listaReactivaLotes);

        // Construcción del menú contextual (clic derecho) sobre la tabla
        ContextMenu menuTabla = new ContextMenu();

        MenuItem menuEditar = new MenuItem("Guardar Edición");
        menuEditar.setOnAction(evento -> editarLote());

        MenuItem menuEliminar = new MenuItem("Eliminar Lote");
        menuEliminar.setOnAction(evento -> eliminarLote());

        menuTabla.getItems().addAll(menuEditar, menuEliminar);
        tvLotes.setContextMenu(menuTabla);
    }

    /**
     * Evento asociado al botón "Registrar Lote".
     * Lee los datos del formulario, valida completitud, persiste mediante el DAO
     * y refresca la tabla.
     */
    @FXML
    protected void agregarOnClick() {
        Lote nuevoLote = leerDatosFormulario();

        if (nuevoLote != null) {
            dao.agregar(nuevoLote);
            actualizarTabla();
            limpiarCampos();
        }
    }

    /**
     * Evento asociado al botón "Limpiar".
     * Restablece los campos del formulario a su estado vacío inicial.
     */
    @FXML
    protected void limpiarOnClick() {
        limpiarCampos();
    }

    /**
     * Evento de interacción con el ratón sobre la tabla.
     * Al detectar un doble clic sobre una fila, traslada los datos del lote seleccionado
     * hacia los campos del formulario para facilitar su inspección o edición.
     * 
     * @param event Información del evento del mouse.
     */
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

    /**
     * Guarda las modificaciones del lote seleccionado a partir de los datos en el formulario.
     */
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

    /**
     * Elimina el lote actualmente seleccionado en la tabla tras confirmación del usuario.
     */
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

    /**
     * Extrae y valida los valores introducidos en el formulario.
     * 
     * @return Instancia de {@link Lote} si todos los datos son válidos, o null si faltan campos obligatorios.
     */
    private Lote leerDatosFormulario() {
        String idLote = txtIdLote.getText();
        String nombreProducto = txtNombreProducto.getText();
        String cantidadKilos = txtCantidadKilos.getText();
        LocalDate fechaEntrega = dpFechaEntrega.getValue();
        LocalDate fechaCaducidad = dpFechaCaducidad.getValue();
        String tipoGrano = cbTipoGrano.getValue();

        // Validación de campos obligatorios
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

    /**
     * Sincroniza la lista observable de la interfaz con los datos vigentes del DAO.
     */
    private void actualizarTabla() {
        listaReactivaLotes.setAll(dao.obtenerTodos());
    }

    /**
     * Limpia los campos de texto, restablece las fechas y devuelve el foco al campo ID.
     */
    private void limpiarCampos() {
        txtIdLote.clear();
        txtNombreProducto.clear();
        txtCantidadKilos.clear();
        dpFechaEntrega.setValue(null);
        dpFechaCaducidad.setValue(null);
        cbTipoGrano.getSelectionModel().clearSelection();
        txtIdLote.requestFocus();
    }

    /**
     * Despliega un cuadro de diálogo modal de tipo advertencia.
     * 
     * @param titulo Título de la alerta.
     * @param mensaje Mensaje explicativo.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // --- Navegación de Retorno al Menú Principal ---
    private Runnable onVolverAlMenu;

    /**
     * Inyecta la acción a ejecutar cuando se solicite volver al menú principal.
     * 
     * @param onVolverAlMenu Callback provisto por el Menú Integrador.
     */
    public void setOnVolverAlMenu(Runnable onVolverAlMenu) {
        this.onVolverAlMenu = onVolverAlMenu;
    }

    /**
     * Evento del botón "← Volver al Menú Principal".
     * Ejecuta el callback registrado para cerrar la ventana del Reto 2 y reabrir el menú.
     * 
     * @param event Evento de acción del botón.
     */
    @FXML
    private void volverAlMenu(javafx.event.ActionEvent event) {
        if (onVolverAlMenu != null) {
            onVolverAlMenu.run();
        } else {
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}