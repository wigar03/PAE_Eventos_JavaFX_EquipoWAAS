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

public class HelloController implements Initializable {

    // Campos del formulario
    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    // Campo de búsqueda y etiqueta de resultado
    @FXML
    private TextField txtBuscar;

    @FXML
    private Label lblResultado;

    // Tabla y columnas de productos
    @FXML
    private TableView<Producto> tblProductos;

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colCantidad;

    // Lista en memoria de productos
    private final ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabla();
        cargarDatosIniciales();
    }

    private void configurarTabla() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tblProductos.setItems(listaProductos);
    }

    private void cargarDatosIniciales() {
        listaProductos.add(new Producto("P001", "Arroz Faisán 1lb", 18.50, 50));
        listaProductos.add(new Producto("P002", "Frijoles Rojos 1lb", 32.00, 30));
        listaProductos.add(new Producto("P003", "Aceite Corona 1L", 65.00, 15));
        listaProductos.add(new Producto("P004", "Azúcar Blanca 1lb", 14.50, 40));
    }

    /**
     * Evento del botón Guardar: delega la acción al método de lógica.
     */
    @FXML
    private void onGuardar(ActionEvent event) {
        guardarProducto();
    }

    /**
     * Evento de teclado en el buscador: delega al método de búsqueda al presionar ENTER.
     */
    @FXML
    private void onBuscar(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buscarProducto();
        }
    }

    /**
     * Evento del botón Limpiar: delega al método de limpieza del formulario.
     */
    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
    }

    /**
     * Coordina el proceso de validación, registro y actualización del producto.
     */
    private void guardarProducto() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();

        // 1. Validar campos obligatorios
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

        // 3. Registrar o actualizar en la lista
        registrarOActualizarProducto(codigo, nombre, precio, cantidad);
        limpiarCamposFormulario();
    }

    private boolean validarCamposObligatorios(String codigo, String nombre, String precio, String cantidad) {
        return !codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty() && !cantidad.isEmpty();
    }

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
     * Lógica de consulta de existencias por código.
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

    private Producto buscarProductoPorCodigo(String codigo) {
        for (Producto p : listaProductos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    private void limpiarFormulario() {
        limpiarCamposFormulario();
        txtBuscar.clear();
        lblResultado.setText("Presione ENTER en el buscador para consultar existencias.");
    }

    private void limpiarCamposFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        txtCodigo.requestFocus();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
