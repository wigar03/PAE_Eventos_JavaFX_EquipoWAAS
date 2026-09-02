package org.uam.reto1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.uam.reto1.model.Producto;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la interfaz del Reto #1: Inventario de Pulpería.
 * 
 * Gestiona el formulario de productos, validaciones de tipos de datos, inserción/actualización
 * en la tabla reactiva (TableView), búsqueda por código con tecla ENTER y navegación de retorno.
 * 
 * @author Equipo WAAS
 */
public class HelloController implements Initializable {

    // --- Componentes FXML del Formulario ---
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtCantidad;

    // --- Componentes FXML de Botones ---
    @FXML private Button btnGuardar;
    @FXML private Button btnLimpiar;

    // --- Componentes FXML de Búsqueda Rápida ---
    @FXML private TextField txtBuscar;
    @FXML private Label lblResultado;

    // --- Componentes FXML del TableView ---
    @FXML private TableView<Producto> tblProductos;
    @FXML private TableColumn<Producto, String> colCodigo;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colCantidad;

    // Lista observable reactiva en memoria para la tabla
    private final ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    // Callback para retorno al Menú Integrador
    private Runnable onVolverAlMenu;

    /**
     * Inicializa el controlador tras la carga del FXML.
     * Enlaza las columnas de la tabla con los getters de Producto y carga registros iniciales.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabla();
        cargarDatosIniciales();
        configurarEfectosBotones();
    }

    /**
     * Configura transiciones visuales de hover en botones mediante controladores de eventos JavaFX puros.
     */
    private void configurarEfectosBotones() {
        if (btnGuardar != null) {
            String base = "-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 7 14; -fx-cursor: hand;";
            String hover = "-fx-background-color: #1d4ed8; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 7 14; -fx-cursor: hand;";
            btnGuardar.setOnMouseEntered(e -> btnGuardar.setStyle(hover));
            btnGuardar.setOnMouseExited(e -> btnGuardar.setStyle(base));
        }
        if (btnLimpiar != null) {
            String base = "-fx-background-color: #f1f5f9; -fx-text-fill: #334155; -fx-border-color: #cbd5e1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-weight: bold; -fx-padding: 7 14; -fx-cursor: hand;";
            String hover = "-fx-background-color: #e2e8f0; -fx-text-fill: #334155; -fx-border-color: #cbd5e1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-weight: bold; -fx-padding: 7 14; -fx-cursor: hand;";
            btnLimpiar.setOnMouseEntered(e -> btnLimpiar.setStyle(hover));
            btnLimpiar.setOnMouseExited(e -> btnLimpiar.setStyle(base));
        }
    }

    /**
     * Vincula las columnas con las propiedades del modelo y asigna la lista a la tabla.
     */
    private void configurarTabla() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tblProductos.setItems(listaProductos);
    }

    /**
     * Carga un conjunto inicial de productos en el inventario para demostración.
     */
    private void cargarDatosIniciales() {
        listaProductos.add(new Producto("P001", "Arroz Faisán 1lb", 18.50, 50));
        listaProductos.add(new Producto("P002", "Frijoles Rojos 1lb", 32.00, 30));
        listaProductos.add(new Producto("P003", "Aceite Corona 1L", 65.00, 15));
        listaProductos.add(new Producto("P004", "Azúcar Blanca 1lb", 14.50, 40));
    }

    /**
     * Maneja el clic en el botón Guardar.
     * 
     * @param event Evento de acción.
     */
    @FXML
    private void onGuardar(ActionEvent event) {
        guardarProducto();
    }

    /**
     * Maneja eventos de teclado en la barra de búsqueda.
     * Al presionar ENTER, ejecuta la consulta de existencias.
     * 
     * @param event Evento de teclado.
     */
    @FXML
    private void onBuscar(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buscarProducto();
        }
    }

    /**
     * Maneja el clic en el botón Limpiar.
     * 
     * @param event Evento de acción.
     */
    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
    }

    /**
     * Procesa la validación y guardado (alta o edición) de un producto.
     */
    private void guardarProducto() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();

        // 1. Validar que no existan campos vacíos
        if (!validarCamposObligatorios(codigo, nombre, precioTexto, cantidadTexto)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor complete todos los campos.");
            return;
        }

        // 2. Validar tipos de datos numéricos
        Double precio = parsearPrecio(precioTexto);
        Integer cantidad = parsearCantidad(cantidadTexto);

        if (precio == null || cantidad == null) {
            return;
        }

        // 3. Registrar o actualizar en la colección reactiva
        registrarOActualizarProducto(codigo, nombre, precio, cantidad);
        limpiarCamposFormulario();
    }

    /**
     * Valida que ninguno de los campos de texto obligatorios esté en blanco.
     */
    private boolean validarCamposObligatorios(String codigo, String nombre, String precio, String cantidad) {
        return !codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty() && !cantidad.isEmpty();
    }

    /**
     * Convierte y valida que el precio sea un número positivo.
     */
    private Double parsearPrecio(String precioTexto) {
        try {
            double precio = Double.parseDouble(precioTexto);
            if (precio <= 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Valor Inválido", "El precio debe ser mayor a 0.");
                return null;
            }
            return precio;
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato Incorrecto", "El precio debe ser un número decimal válido.");
            return null;
        }
    }

    /**
     * Convierte y valida que la cantidad sea un entero no negativo.
     */
    private Integer parsearCantidad(String cantidadTexto) {
        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            if (cantidad < 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Valor Inválido", "La cantidad no puede ser negativa.");
                return null;
            }
            return cantidad;
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato Incorrecto", "La cantidad debe ser un número entero válido.");
            return null;
        }
    }

    /**
     * Agrega un nuevo producto o actualiza sus datos si el código ya existe en la lista.
     */
    private void registrarOActualizarProducto(String codigo, String nombre, double precio, int cantidad) {
        Producto existente = buscarProductoPorCodigo(codigo);

        if (existente != null) {
            existente.setNombre(nombre);
            existente.setPrecio(precio);
            existente.setCantidad(cantidad);
            tblProductos.refresh();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Producto Actualizado", "El producto '" + codigo + "' fue actualizado exitosamente.");
        } else {
            listaProductos.add(new Producto(codigo, nombre, precio, cantidad));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Producto Guardado", "Producto registrado correctamente en el inventario.");
        }
    }

    /**
     * Busca existencias por código y selecciona la fila correspondiente en la tabla.
     */
    private void buscarProducto() {
        String codigo = txtBuscar.getText().trim();

        if (codigo.isEmpty()) {
            lblResultado.setText("Ingrese el código de un producto para buscar.");
            return;
        }

        Producto encontrado = buscarProductoPorCodigo(codigo);
        if (encontrado != null) {
            tblProductos.getSelectionModel().select(encontrado);
            tblProductos.scrollTo(encontrado);
            lblResultado.setText(String.format("Encontrado: %s | Existencias: %d unidades | Precio: C$ %.2f",
                    encontrado.getNombre(), encontrado.getCantidad(), encontrado.getPrecio()));
        } else {
            lblResultado.setText("No se encontró ningún producto con el código '" + codigo + "'.");
        }
    }

    /**
     * Realiza una búsqueda lineal de un producto a partir de su código.
     */
    private Producto buscarProductoPorCodigo(String codigo) {
        for (Producto p : listaProductos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Restablece el formulario y el área de búsqueda.
     */
    private void limpiarFormulario() {
        limpiarCamposFormulario();
        txtBuscar.clear();
        lblResultado.setText("Presione ENTER en el buscador para consultar existencias.");
    }

    /**
     * Vacía únicamente los campos de entrada del formulario.
     */
    private void limpiarCamposFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        txtCodigo.requestFocus();
    }

    /**
     * Despliega una alerta modal en pantalla.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Asigna el callback para retornar al Menú Integrador.
     * 
     * @param onVolverAlMenu Acción de retorno.
     */
    public void setOnVolverAlMenu(Runnable onVolverAlMenu) {
        this.onVolverAlMenu = onVolverAlMenu;
    }

    /**
     * Maneja el clic en el botón "← Volver al Menú Principal".
     * 
     * @param event Evento de acción.
     */
    @FXML
    private void volverAlMenu(ActionEvent event) {
        if (onVolverAlMenu != null) {
            onVolverAlMenu.run();
        } else {
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
