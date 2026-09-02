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

public class HelloController implements Initializable {

    // Campos del formulario
    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private ComboBox<String> cbCategoria;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    // Buscador y barra de estado
    @FXML
    private TextField txtBuscar;

    @FXML
    private Label lblEstado;

    // Tabla y columnas
    @FXML
    private TableView<Producto> tblProductos;

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colCantidad;

    // Lista de productos en memoria
    private final ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarCategorias();
        configurarTabla();
        cargarDatosIniciales();
        actualizarEstado("Catálogo cargado con " + listaProductos.size() + " artesanías.");
    }

    private void configurarCategorias() {
        cbCategoria.getItems().addAll("Textil", "Cerámica", "Madera", "Cuero", "Joyería", "Otro");
        cbCategoria.setValue("Textil");
    }

    private void configurarTabla() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tblProductos.setItems(listaProductos);

        tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarEnFormulario(newVal);
            }
        });
    }

    private void cargarDatosIniciales() {
        listaProductos.add(new Producto("ART-001", "Hamaca Matrimonial Masaya", "Textil", 1250.00, 15));
        listaProductos.add(new Producto("ART-002", "Jarrón de Cerámica Negra", "Cerámica", 450.00, 20));
        listaProductos.add(new Producto("ART-003", "Máscara El Güegüense", "Madera", 680.00, 8));
        listaProductos.add(new Producto("ART-004", "Faja de Cuero Camoapa", "Cuero", 350.00, 25));
        listaProductos.add(new Producto("ART-005", "Aretes de Filigrana", "Joyería", 820.00, 12));
    }

    // =========================================================================
    // MANEJADORES DE EVENTOS (Principio SOLID: Single Responsibility Principle)
    // Los eventos de UI solo despachan la llamada al método con la lógica.
    // =========================================================================

    @FXML
    private void onNuevo(ActionEvent event) {
        prepararNuevoRegistro();
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        guardarProducto();
    }

    @FXML
    private void onBuscar(ActionEvent event) {
        buscarProducto();
    }

    @FXML
    private void onBuscarKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buscarProducto();
        }
    }

    @FXML
    private void onVender(ActionEvent event) {
        venderProductoSeleccionado();
    }

    @FXML
    private void onResumenVentas(ActionEvent event) {
        mostrarResumenInventario();
    }

    @FXML
    private void onAcercaDe(ActionEvent event) {
        mostrarInformacionAcercaDe();
    }

    @FXML
    private void onSalir(ActionEvent event) {
        cerrarAplicacion();
    }

    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
    }

    // =========================================================================
    // MÉTODOS DE LÓGICA DE NEGOCIO Y CONTROL (Responsabilidades separadas)
    // =========================================================================

    private void prepararNuevoRegistro() {
        limpiarCamposFormulario();
        actualizarEstado("Listo para ingresar una nueva artesanía.");
    }

    private void guardarProducto() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = cbCategoria.getValue();
        String precioStr = txtPrecio.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        // 1. Validar campos obligatorios
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

        // 3. Registrar o actualizar
        registrarOActualizarProducto(codigo, nombre, categoria, precio, cantidad);
        limpiarCamposFormulario();
    }

    private boolean validarCamposObligatorios(String codigo, String nombre, String precio, String cantidad) {
        return !codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty() && !cantidad.isEmpty();
    }

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

    private void registrarOActualizarProducto(String codigo, String nombre, String categoria, double precio, int cantidad) {
        Producto existente = buscarProductoPorCodigo(codigo);

        if (existente != null) {
            existente.setNombre(nombre);
            existente.setCategoria(categoria);
            existente.setPrecio(precio);
            existente.setCantidad(cantidad);
            tblProductos.refresh();
            actualizarEstado("✅ Artesanía '" + codigo + "' actualizada.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Actualizado", "La artesanía fue actualizada correctamente.");
        } else {
            Producto nuevo = new Producto(codigo, nombre, categoria, precio, cantidad);
            listaProductos.add(nuevo);
            tblProductos.getSelectionModel().select(nuevo);
            actualizarEstado("✅ Artesanía '" + codigo + "' agregada al catálogo.");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado", "Artesanía agregada al catálogo con éxito.");
        }
    }

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
                actualizarEstado(String.format("🔎 Encontrado: %s | Existencias: %d | Precio: C$ %.2f",
                        p.getNombre(), p.getCantidad(), p.getPrecio()));
                return;
            }
        }

        actualizarEstado("❌ No se encontró ninguna artesanía que coincida con: " + criterio);
    }

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
        actualizarEstado("💰 Venta realizada: 1 unidad de " + seleccionado.getNombre() + " (Quedan: " + seleccionado.getCantidad() + ")");
    }

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

    private void mostrarInformacionAcercaDe() {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Acerca de la Tienda",
                "Tienda de Artesanías Nicaragüenses\nReto #3 - Menús, ToolBar, Eventos y Navegación\nDesarrollado para Programación de Escritorio - UAM");
    }

    private void cerrarAplicacion() {
        Platform.exit();
    }

    private void limpiarFormulario() {
        limpiarCamposFormulario();
        actualizarEstado("Formulario limpio.");
    }

    private Producto buscarProductoPorCodigo(String codigo) {
        for (Producto p : listaProductos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    private void cargarEnFormulario(Producto p) {
        txtCodigo.setText(p.getCodigo());
        txtNombre.setText(p.getNombre());
        cbCategoria.setValue(p.getCategoria());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtCantidad.setText(String.valueOf(p.getCantidad()));
    }

    private void limpiarCamposFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        cbCategoria.setValue("Textil");
        txtPrecio.clear();
        txtCantidad.clear();
        txtCodigo.requestFocus();
    }

    private void actualizarEstado(String mensaje) {
        lblEstado.setText(mensaje);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
