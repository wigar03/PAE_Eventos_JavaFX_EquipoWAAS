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
 * Controlador de la interfaz grafica para el Reto #2 (Gestion de Lotes de Cooperativa).
 * <p>
 * Gestiona el registro, visualizacion, edicion y eliminacion de lotes de granos mediante
 * componentes JavaFX: formulario con campos de texto, ComboBox, DatePickers y una TableView reactiva.
 * Incluye soporte para menu contextual (clic derecho en tabla), doble clic para edicion rapida
 * y callback para retornar al Menu Integrador.
 */
public class LoteController {

    // ==========================================
    // CONTROLES DE FORMULARIO (Campos de Entrada)
    // ==========================================

    @FXML private TextField txtIdLote;
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtCantidadKilos;
    @FXML private ComboBox<String> cbTipoGrano;
    @FXML private DatePicker dpFechaEntrega;
    @FXML private DatePicker dpFechaCaducidad;

    // ==========================================
    // CONTROLES DE TABLA Y COLUMNAS
    // ==========================================

    @FXML private TableView<Lote> tvLotes;
    @FXML private TableColumn<Lote, String> colId;
    @FXML private TableColumn<Lote, String> colProducto;
    @FXML private TableColumn<Lote, String> colCantidad;
    @FXML private TableColumn<Lote, LocalDate> colFechaEntrega;
    @FXML private TableColumn<Lote, LocalDate> colFechaCaducidad;
    @FXML private TableColumn<Lote, String> colGrano;

    /** Objeto DAO para el acceso a datos y operaciones CRUD en memoria */
    private final LoteDAO dao = new LoteDAO();

    /** Lista observable reactiva conectada directamente con la TableView */
    private ObservableList<Lote> listaReactivaLotes;

    /** Callback para notificar al Menu Integrador cuando se desea retornar */
    private Runnable onVolverAlMenu;

    /**
     * Metodo de inicializacion invocado por JavaFX tras cargar el archivo FXML.
     * Configura las opciones del ComboBox, asocia las propiedades del modelo a las columnas
     * de la TableView y establece el menu contextual para operaciones con clic derecho.
     */
    @FXML
    public void initialize() {
        // 1. Cargar las opciones de tipos de grano en el ComboBox
        cbTipoGrano.setItems(FXCollections.observableArrayList(
                "Cafe",
                "Frijol",
                "Maiz",
                "Arroz",
                "Trigo"
        ));

        // 2. Asociar cada columna de la TableView con el getter correspondiente de la clase Lote
        colId.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadKilos"));
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
        colFechaCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        colGrano.setCellValueFactory(new PropertyValueFactory<>("tipoGrano"));

        // 3. Inicializar la lista reactiva y enlazarla a la tabla
        listaReactivaLotes = FXCollections.observableArrayList(dao.obtenerTodos());
        tvLotes.setItems(listaReactivaLotes);

        // 4. Crear y configurar el menu contextual (clic derecho) sobre la tabla
        ContextMenu menuTabla = new ContextMenu();

        MenuItem menuEditar = new MenuItem("Guardar Edicion");
        menuEditar.setOnAction(evento -> editarLote());

        MenuItem menuEliminar = new MenuItem("Eliminar Lote");
        menuEliminar.setOnAction(evento -> eliminarLote());

        menuTabla.getItems().addAll(menuEditar, menuEliminar);
        tvLotes.setContextMenu(menuTabla);
    }

    /**
     * Manejador del boton Agregar.
     * Lee los datos validados del formulario, los inserta en el DAO,
     * actualiza la vista de la tabla y limpia los campos de captura.
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
     * Manejador del boton Limpiar.
     * Restablece los campos del formulario a su estado inicial.
     */
    @FXML
    protected void limpiarOnClick() {
        limpiarCampos();
    }

    /**
     * Manejador del evento de raton sobre la TableView.
     * Detecta un doble clic sobre una fila y carga la informacion del lote seleccionado
     * en el formulario superior para facilitar su consulta o edicion.
     *
     * @param event Evento de mouse capturado.
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
     * Modifica el lote seleccionado en la tabla con los datos actuales del formulario.
     * Requiere que el usuario haya seleccionado una fila previamente.
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
     * Elimina el lote seleccionado de la tabla previa confirmacion del usuario.
     * Muestra una ventana modal de alerta antes de procesar el borrado en el DAO.
     */
    private void eliminarLote() {
        int index = tvLotes.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminacion");
            alerta.setHeaderText("Borrar registro del sistema");
            alerta.setContentText("¿Estas seguro de que deseas eliminar este lote?");

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
     * Lee y valida los datos ingresados en el formulario.
     * Verifica que ningun campo de texto este vacio y que las fechas y el combo esten seleccionados.
     *
     * @return Nueva instancia de {@link Lote} con los datos si la validacion es correcta; {@code null} en caso de error.
     */
    private Lote leerDatosFormulario() {
        String idLote = txtIdLote.getText();
        String nombreProducto = txtNombreProducto.getText();
        String cantidadKilos = txtCantidadKilos.getText();
        LocalDate fechaEntrega = dpFechaEntrega.getValue();
        LocalDate fechaCaducidad = dpFechaCaducidad.getValue();
        String tipoGrano = cbTipoGrano.getValue();

        // Validacion de campos requeridos
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
     * Sincroniza la coleccion observable reactiva con los datos actuales del DAO.
     */
    private void actualizarTabla() {
        listaReactivaLotes.setAll(dao.obtenerTodos());
    }

    /**
     * Limpia los controles de captura del formulario y enfoca el primer campo.
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
     * Muestra un cuadro de advertencia estandar con el mensaje indicado.
     *
     * @param titulo  Titulo o encabezado de la advertencia.
     * @param mensaje Cuerpo explicativo de la advertencia.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Asigna la accion ejecutable que se invocara cuando el usuario pulse el boton de retorno.
     *
     * @param onVolverAlMenu Accion de retorno hacia el menu integrador.
     */
    public void setOnVolverAlMenu(Runnable onVolverAlMenu) {
        this.onVolverAlMenu = onVolverAlMenu;
    }

    /**
     * Manejador del boton "Volver al Menu Principal".
     * Ejecuta el callback si fue configurado por el Menu Integrador, o cierra la ventana actual.
     *
     * @param event Evento de accion del boton.
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