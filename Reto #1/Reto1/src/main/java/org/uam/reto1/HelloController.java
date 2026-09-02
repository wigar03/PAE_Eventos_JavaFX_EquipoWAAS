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
        // Enlazar columnas con las propiedades de Producto
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        // Asignar lista a la tabla
        tblProductos.setItems(listaProductos);

        // Datos iniciales de prueba
        listaProductos.add(new Producto("P001", "Arroz Faisán 1lb", 18.50, 50));
        listaProductos.add(new Producto("P002", "Frijoles Rojos 1lb", 32.00, 30));
        listaProductos.add(new Producto("P003", "Aceite Corona 1L", 65.00, 15));
        listaProductos.add(new Producto("P004", "Azúcar Blanca 1lb", 14.50, 40));
    }

    /**
     * Evento ActionEvent: Guarda o actualiza un producto en el inventario.
     */
    @FXML
    private void onGuardar(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();

        // 1. Validar campos vacíos
        if (codigo.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty() || cantidadTexto.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor complete todos los campos.");
            return;
        }

        // 2. Validar valores numéricos
        double precio;
        int cantidad;
        try {
            precio = Double.parseDouble(precioTexto);
            cantidad = Integer.parseInt(cantidadTexto);

            if (precio <= 0 || cantidad < 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Valores Inválidos", "El precio debe ser mayor a 0 y la cantidad no puede ser negativa.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato Incorrecto", "El precio y la cantidad deben ser valores numéricos válidos.");
            return;
        }

        // 3. Registrar o actualizar si ya existe el código
        Producto existente = buscarProducto(codigo);
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

        limpiarCampos();
    }

    /**
     * Evento KeyEvent: Busca existencias del producto al presionar la tecla ENTER.
     */
    @FXML
    private void onBuscar(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String codigo = txtBuscar.getText().trim();

            if (codigo.isEmpty()) {
                lblResultado.setText("⚠️ Ingrese el código de un producto para buscar.");
                return;
            }

            Producto p = buscarProducto(codigo);
            if (p != null) {
                tblProductos.getSelectionModel().select(p);
                tblProductos.scrollTo(p);
                lblResultado.setText(String.format("🔎 Encontrado: %s | Existencias: %d unidades | Precio: C$ %.2f",
                        p.getNombre(), p.getCantidad(), p.getPrecio()));
            } else {
                lblResultado.setText("❌ No se encontró ningún producto con el código '" + codigo + "'.");
            }
        }
    }

    /**
     * Evento ActionEvent: Limpia el formulario y la búsqueda.
     */
    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarCampos();
        txtBuscar.clear();
        lblResultado.setText("Presione ENTER en el buscador para consultar existencias.");
    }

    private Producto buscarProducto(String codigo) {
        for (Producto p : listaProductos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    private void limpiarCampos() {
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
