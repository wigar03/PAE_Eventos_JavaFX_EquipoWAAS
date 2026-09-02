package org.uam.reto2;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.uam.reto2.model.Producto;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la interfaz gráfica del Reto #3: Tienda de Artesanías Nicaragüenses.
 * 
 * Gestiona el menú superior multinivel (MenuBar), la barra de herramientas de acceso rápido (ToolBar),
 * la búsqueda dinámica en el catálogo artesanal, el registro de ventas con decremento de stock,
 * el cálculo de valorización del inventario y la navegación de retorno al Menú Integrador.
 * 
 * Sigue el principio de responsabilidad única (SRP), separando los despachadores de eventos FXML
 * de la lógica de negocio y validaciones.
 * 
 * @author Equipo WAAS
 */
public class HelloController implements Initializable {

    // --- Componentes FXML del Formulario de Artesanías ---
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cbCategoria;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtCantidad;

    // --- Componentes FXML de Búsqueda y Barra de Estado ---
    @FXML private TextField txtBuscar;
    @FXML private Label lblEstado;

    // --- Componentes FXML del Catálogo (TableView) ---
    @FXML private TableView<Producto> tblProductos;
    @FXML private TableColumn<Producto, String> colCodigo;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, String> colCategoria;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colCantidad;

    // Colección observable reactiva que almacena las artesanías en memoria
    private final ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    // Callback suministrado por el Menú Integrador para navegación de retorno
    private Runnable onVolverAlMenu;

    /**
     * Inicialización del controlador tras cargar la plantilla FXML.
     * Carga el catálogo de categorías, enlaza las columnas de la tabla y puebla los datos iniciales.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarCategorias();
        configurarTabla();
        cargarDatosIniciales();
        actualizarEstado("Catálogo cargado con " + listaProductos.size() + " artesanías.");
    }

    /**
     * Configura las opciones del ComboBox de categorías con tipos tradicionales de artesanía.
     */
    private void configurarCategorias() {
        cbCategoria.getItems().addAll("Textil", "Cerámica", "Madera", "Cuero", "Joyería", "Otro");
        cbCategoria.setValue("Textil");
    }

    /**
     * Enlaza reflexivamente las columnas de la tabla con las propiedades del modelo {@link Producto}
     * y asocia un escuchador de selección para cargar la artesanía en el formulario.
     */
    private void configurarTabla() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tblProductos.setItems(listaProductos);

        // Al seleccionar una fila, se transfieren sus datos al formulario para edición
        tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarEnFormulario(newVal);
            }
        });
    }

    /**
     * Inserta piezas artesanales nicaragüenses representativas para la demostración inicial.
     */
    private void cargarDatosIniciales() {
        listaProductos.add(new Producto("ART-001", "Hamaca Matrimonial Masaya", "Textil", 1250.00, 15));
        listaProductos.add(new Producto("ART-002", "Jarrón de Cerámica Negra", "Cerámica", 450.00, 20));
        listaProductos.add(new Producto("ART-003", "Máscara El Güegüense", "Madera", 680.00, 8));
        listaProductos.add(new Producto("ART-004", "Faja de Cuero Camoapa", "Cuero", 350.00, 25));
        listaProductos.add(new Producto("ART-005", "Aretes de Filigrana", "Joyería", 820.00, 12));
    }

    // =========================================================================
    // MANEJADORES DE EVENTOS FXML
    // =========================================================================

    /**
     * Acción del menú/botón "Nuevo Producto" (Atajo: Ctrl+N).
     */
    @FXML
    private void onNuevo(ActionEvent event) {
        prepararNuevoRegistro();
    }

    /**
     * Acción del menú/botón "Guardar" (Atajo: Ctrl+S).
     */
    @FXML
    private void onGuardar(ActionEvent event) {
        guardarProducto();
    }

    /**
     * Acción del botón "Buscar" en la barra de herramientas.
     */
    @FXML
    private void onBuscar(ActionEvent event) {
        buscarProducto();
    }

    /**
     * Evento de teclado en el cuadro de búsqueda; ejecuta al pulsar la tecla ENTER.
     */
    @FXML
    private void onBuscarKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buscarProducto();
        }
    }

    /**
     * Acción del menú/botón "Vender": descuenta una unidad del stock seleccionado.
     */
    @FXML
    private void onVender(ActionEvent event) {
        venderProductoSeleccionado();
    }

    /**
     * Acción del menú "Resumen de Inventario": calcula totales y valorización económica.
     */
    @FXML
    private void onResumenVentas(ActionEvent event) {
        mostrarResumenInventario();
    }

    /**
     * Acción del menú "Acerca de": presenta información institucional y académica.
     */
    @FXML
    private void onAcercaDe(ActionEvent event) {
        mostrarInformacionAcercaDe();
    }

    /**
     * Acción del menú "Salir": finaliza la aplicación.
     */
    @FXML
    private void onSalir(ActionEvent event) {
        cerrarAplicacion();
    }

    /**
     * Acción del botón "Limpiar": restablece campos y estado del formulario.
     */
    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
    }

    // =========================================================================
    // MÉTODOS DE LÓGICA DE NEGOCIO
    // =========================================================================

    /**
     * Prepara el formulario para ingresar un nuevo registro en blanco.
     */
    private void prepararNuevoRegistro() {
        limpiarCamposFormulario();
        actualizarEstado("Listo para ingresar una nueva artesanía.");
    }

    /**
     * Valida los campos ingresados y persiste la artesanía en la colección observable.
     */
    private void guardarProducto() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = cbCategoria.getValue();
        String precioStr = txtPrecio.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        // 1. Validar que no existan campos vacíos
        if (!validarCamposObligatorios(codigo, nombre, precioStr, cantidadStr)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor complete todos los datos de la artesanía.");
            return;
        }

        // 2. Validar valores numéricos
        Double precio = parsearPrecio(precioStr);
        Integer cantidad = parsearCantidad(cantidadStr);

        if (precio == null || cantidad == null) {
            return;
        }

        // 3. Registrar nuevo producto o actualizar existente
        registrarOActualizarProducto(codigo, nombre, categoria, precio, cantidad);
        limpiarCamposFormulario();
    }

    /**
     * Comprueba que todos los campos requeridos contengan texto.
     */
    private boolean validarCamposObligatorios(String codigo, String nombre, String precio, String cantidad) {
        return !codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty() && !cantidad.isEmpty();
    }

    /**
     * Convierte y valida que el precio sea mayor que cero.
     */
    private Double parsearPrecio(String precioStr) {
        try {
            double precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Datos Inválidos", "El precio debe ser mayor a 0.");
                return null;
            }
            return precio;
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato Incorrecto", "El precio debe ser un número válido.");
            return null;
        }
    }

    /**
     * Convierte y valida que la cantidad en existencia sea un entero no negativo.
     */
    private Integer parsearCantidad(String cantidadStr) {
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad < 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Datos Inválidos", "La cantidad no puede ser negativa.");
                return null;
            }
            return cantidad;
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato Incorrecto", "La cantidad debe ser un número entero válido.");
            return null;
        }
    }

    /**
     * Inserta una nueva artesanía en el catálogo o actualiza los datos si el código ya existe.
     */
    private void registrarOActualizarProducto(String codigo, String nombre, String categoria, double precio, int cantidad) {
        Producto existente = buscarProductoPorCodigo(codigo);

        if (existente != null) {
            existente.setNombre(nombre);
            existente.setCategoria(categoria);
            existente.setPrecio(precio);
            existente.setCantidad(cantidad);
            tblProductos.refresh();
            actualizarEstado("Artesanía '" + codigo + "' actualizada.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Actualizado", "La artesanía fue actualizada correctamente.");
        } else {
            Producto nuevo = new Producto(codigo, nombre, categoria, precio, cantidad);
            listaProductos.add(nuevo);
            tblProductos.getSelectionModel().select(nuevo);
            actualizarEstado("Artesanía '" + codigo + "' agregada al catálogo.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado", "Artesanía agregada al catálogo con éxito.");
        }
    }

    /**
     * Busca coincidencias parciales por código o nombre y resalta el producto en la tabla.
     */
    private void buscarProducto() {
        String criterio = txtBuscar.getText().trim().toLowerCase();

        if (criterio.isEmpty()) {
            actualizarEstado("Ingrese un código o nombre para buscar.");
            return;
        }

        for (Producto p : listaProductos) {
            if (p.getCodigo().toLowerCase().contains(criterio) || p.getNombre().toLowerCase().contains(criterio)) {
                tblProductos.getSelectionModel().select(p);
                tblProductos.scrollTo(p);
                actualizarEstado(String.format("Encontrado: %s | Existencias: %d | Precio: C$ %.2f",
                        p.getNombre(), p.getCantidad(), p.getPrecio()));
                return;
            }
        }

        actualizarEstado("No se encontró ninguna artesanía que coincida con: " + criterio);
    }

    /**
     * Registra una venta reduciendo en 1 el stock del producto seleccionado en la tabla.
     */
    private void venderProductoSeleccionado() {
        Producto seleccionado = tblProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccionar Producto", "Seleccione un producto en la tabla para registrar la venta.");
            return;
        }

        if (seleccionado.getCantidad() <= 0) {
            mostrarAlerta(Alert.AlertType.ERROR, "Sin Existencias", "No hay existencias disponibles de " + seleccionado.getNombre());
            return;
        }

        seleccionado.setCantidad(seleccionado.getCantidad() - 1);
        tblProductos.refresh();
        cargarEnFormulario(seleccionado);
        actualizarEstado("Venta realizada: 1 unidad de " + seleccionado.getNombre() + " (Quedan: " + seleccionado.getCantidad() + ")");
    }

    /**
     * Computa y presenta un resumen estadístico de las existencias y valor total del inventario.
     */
    private void mostrarResumenInventario() {
        int totalUnidades = 0;
        double valorTotal = 0;

        for (Producto p : listaProductos) {
            totalUnidades += p.getCantidad();
            valorTotal += (p.getPrecio() * p.getCantidad());
        }

        String mensaje = String.format("• Total de Artesanías Registradas: %d\n• Total de Unidades en Existencia: %d\n• Valor Total del Inventario: C$ %.2f",
                listaProductos.size(), totalUnidades, valorTotal);

        mostrarAlerta(Alert.AlertType.INFORMATION, "Resumen de Inventario", mensaje);
    }

    /**
     * Muestra el cuadro informativo sobre la autoría y propósito del Reto 3.
     */
    private void mostrarInformacionAcercaDe() {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Acerca de la Tienda",
                "Tienda de Artesanías Nicaragüenses\nReto #3 - Menús, ToolBar, Eventos y Navegación\nDesarrollado para Programación de Escritorio - UAM");
    }

    /**
     * Cierra la aplicación completa.
     */
    private void cerrarAplicacion() {
        Platform.exit();
    }

    /**
     * Restablece los campos de captura y la barra de estado.
     */
    private void limpiarFormulario() {
        limpiarCamposFormulario();
        actualizarEstado("Formulario limpio.");
    }

    /**
     * Búsqueda por coincidencia exacta de código.
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
     * Traslada los valores de un producto hacia los controles del formulario.
     */
    private void cargarEnFormulario(Producto p) {
        txtCodigo.setText(p.getCodigo());
        txtNombre.setText(p.getNombre());
        cbCategoria.setValue(p.getCategoria());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtCantidad.setText(String.valueOf(p.getCantidad()));
    }

    /**
     * Vacía las cajas de texto y reinicia la categoría a su valor predeterminado.
     */
    private void limpiarCamposFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        cbCategoria.setValue("Textil");
        txtPrecio.clear();
        txtCantidad.clear();
        txtCodigo.requestFocus();
    }

    /**
     * Actualiza el texto de la etiqueta de la barra de estado inferior.
     */
    private void actualizarEstado(String mensaje) {
        lblEstado.setText(mensaje);
    }

    /**
     * Despliega un cuadro de diálogo modal en la interfaz.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Establece el callback para regresar al Menú Integrador.
     */
    public void setOnVolverAlMenu(Runnable onVolverAlMenu) {
        this.onVolverAlMenu = onVolverAlMenu;
    }

    /**
     * Maneja el clic en "Volver al Menú Principal" para cerrar el Reto 3 y restaurar el menú.
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
