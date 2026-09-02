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
        // 1. Opciones de categorías
        cbCategoria.getItems().addAll("Textil", "Cerámica", "Madera", "Cuero", "Joyería", "Otro");
        cbCategoria.setValue("Textil");

        // 2. Configuración de columnas
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        // 3. Vincular lista a la tabla
        tblProductos.setItems(listaProductos);

        // Cargar datos al seleccionar en la tabla
        tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarEnFormulario(newVal);
            }
        });

        // 4. Cargar artesanías iniciales
        cargarDatosIniciales();
        actualizarEstado("Catálogo cargado con " + listaProductos.size() + " artesanías.");
    }

    private void cargarDatosIniciales() {
        listaProductos.add(new Producto("ART-001", "Hamaca Matrimonial Masaya", "Textil", 1250.00, 15));
        listaProductos.add(new Producto("ART-002", "Jarrón de Cerámica Negra", "Cerámica", 450.00, 20));
        listaProductos.add(new Producto("ART-003", "Máscara El Güegüense", "Madera", 680.00, 8));
        listaProductos.add(new Producto("ART-004", "Faja de Cuero Camoapa", "Cuero", 350.00, 25));
        listaProductos.add(new Producto("ART-005", "Aretes de Filigrana", "Joyería", 820.00, 12));
    }

    /**
     * Acción: Limpiar formulario para nuevo registro.
     */
    @FXML
    private void onNuevo(ActionEvent event) {
        limpiarFormulario();
        actualizarEstado("Listo para ingresar una nueva artesanía.");
    }

    /**
     * Acción: Guardar o actualizar producto.
     */
    @FXML
    private void onGuardar(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = cbCategoria.getValue();
        String precioStr = txtPrecio.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        // 1. Validar campos vacíos
        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor complete todos los datos de la artesanía.");
            return;
        }

        // 2. Validar números
        double precio;
        int cantidad;
        try {
            precio = Double.parseDouble(precioStr);
            cantidad = Integer.parseInt(cantidadStr);

            if (precio <= 0 || cantidad < 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Datos Inválidos", "El precio debe ser > 0 y la cantidad >= 0.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato Incorrecto", "Precio y cantidad deben ser valores numéricos válidos.");
            return;
        }

        // 3. Buscar si ya existe para actualizar o registrar
        Producto existente = buscarProducto(codigo);
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

        limpiarFormulario();
    }

    /**
     * Acción: Buscar artesanía por código o nombre.
     */
    @FXML
    private void onBuscar(ActionEvent event) {
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

    /**
     * Evento de teclado para buscar al presionar ENTER.
     */
    @FXML
    private void onBuscarKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onBuscar(null);
        }
    }

    /**
     * Acción de Ventas: Registrar venta rápida de 1 unidad del producto seleccionado.
     */
    @FXML
    private void onVender(ActionEvent event) {
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

    /**
     * Acción de Ventas: Ver resumen de inventario / catálogo.
     */
    @FXML
    private void onResumenVentas(ActionEvent event) {
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
     * Menú Ayuda: Acerca de.
     */
    @FXML
    private void onAcercaDe(ActionEvent event) {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Acerca de la Tienda",
                "Tienda de Artesanías Nicaragüenses\nReto #3 - Menús, ToolBar, Eventos y Navegación\nDesarrollado para Programación de Escritorio - UAM");
    }

    /**
     * Salir de la aplicación.
     */
    @FXML
    private void onSalir(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Limpiar campos.
     */
    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
        actualizarEstado("Formulario limpio.");
    }

    private Producto buscarProducto(String codigo) {
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

    private void limpiarFormulario() {
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
