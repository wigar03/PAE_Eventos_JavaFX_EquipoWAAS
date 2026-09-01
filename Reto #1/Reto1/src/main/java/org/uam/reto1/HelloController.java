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
import javafx.scene.layout.HBox;
import org.uam.reto1.model.Producto;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador para la gestión del inventario de pulpería (Reto #1).
 * Implementa ActionEvent para guardar y KeyEvent para buscar con ENTER.
 */
public class HelloController implements Initializable {

    // Controles de captura de datos
    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Label lblMensaje;

    // Controles de búsqueda
    @FXML
    private TextField txtBuscar;

    @FXML
    private HBox panelResultadoBusqueda;

    @FXML
    private Label lblResultadoBusqueda;

    // Controles de tabla
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

    @FXML
    private Label lblTotalProductos;

    // Lista observable para almacenar los productos en memoria
    private final ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. Configurar las columnas de la tabla con las propiedades del modelo Producto
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        // Formatear visualmente la columna de precio
        colPrecio.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);
                if (empty || precio == null) {
                    setText(null);
                } else {
                    setText(String.format("C$ %.2f", precio));
                }
            }
        });

        // 2. Asociar la lista observable a la tabla
        tblProductos.setItems(listaProductos);

        // 3. Agregar datos iniciales de ejemplo
        listaProductos.add(new Producto("P001", "Arroz Faisán 1lb", 18.50, 50));
        listaProductos.add(new Producto("P002", "Frijoles Rojos 1lb", 32.00, 30));
        listaProductos.add(new Producto("P003", "Aceite Corona 1L", 65.00, 15));
        listaProductos.add(new Producto("P004", "Azúcar Blanca 1lb", 14.50, 40));

        actualizarContador();

        // 4. Doble clic en la tabla para cargar en el formulario
        tblProductos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tblProductos.getSelectionModel().getSelectedItem() != null) {
                Producto seleccionado = tblProductos.getSelectionModel().getSelectedItem();
                cargarProductoEnFormulario(seleccionado);
            }
        });
    }

    /**
     * Evento ActionEvent para registrar o actualizar un producto.
     * Valida campos obligatorios y tipos numéricos.
     */
    @FXML
    private void onGuardar(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        // Validación 1: Campos vacíos
        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos obligatorios", "Por favor complete todos los campos (Código, Nombre, Precio y Cantidad).");
            mostrarMensaje("⚠️ Todos los campos son obligatorios.", "status-error");
            return;
        }

        // Validación 2: Valor numérico para Precio (decimal positivo)
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Precio inválido", "El precio debe ser un número mayor a cero.");
                mostrarMensaje("⚠️ El precio debe ser mayor a 0.", "status-error");
                txtPrecio.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato de precio inválido", "El precio debe ser un número válido (ej. 18.50).");
            mostrarMensaje("⚠️ Ingrese un valor numérico para el precio.", "status-error");
            txtPrecio.requestFocus();
            return;
        }

        // Validación 3: Valor numérico para Cantidad (entero no negativo)
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad < 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Cantidad inválida", "La cantidad / existencias no puede ser un número negativo.");
                mostrarMensaje("⚠️ La cantidad no puede ser negativa.", "status-error");
                txtCantidad.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato de cantidad inválido", "La cantidad debe ser un número entero (ej. 25).");
            mostrarMensaje("⚠️ Ingrese un número entero para la cantidad.", "status-error");
            txtCantidad.requestFocus();
            return;
        }

        // Validación 4: Verificar si el código ya existe para actualizar o agregar nuevo
        Optional<Producto> productoExistente = buscarProductoPorCodigo(codigo);

        if (productoExistente.isPresent()) {
            Producto p = productoExistente.get();
            p.setNombre(nombre);
            p.setPrecio(precio);
            p.setCantidad(cantidad);
            tblProductos.refresh();
            mostrarMensaje("✓ Producto '" + codigo + "' actualizado correctamente.", "status-success");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Producto Actualizado", "El producto con código " + codigo + " fue actualizado exitosamente.");
        } else {
            Producto nuevoProducto = new Producto(codigo, nombre, precio, cantidad);
            listaProductos.add(nuevoProducto);
            mostrarMensaje("✓ Producto '" + codigo + "' guardado con éxito.", "status-success");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Producto Guardado", "Producto registrado en el inventario con éxito.");
        }

        actualizarContador();
        limpiarFormulario();
    }

    /**
     * Evento KeyEvent para buscar existencias al presionar la tecla ENTER.
     */
    @FXML
    private void onBuscarKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            realizarBusqueda();
        }
    }

    /**
     * Búsqueda ejecutada por botón auxiliar de búsqueda (ActionEvent).
     */
    @FXML
    private void onBuscarClick(ActionEvent event) {
        realizarBusqueda();
    }

    /**
     * Lógica de búsqueda de producto por código y visualización de existencias.
     */
    private void realizarBusqueda() {
        String criterio = txtBuscar.getText().trim();

        if (criterio.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Búsqueda", "Ingrese el código del producto que desea buscar.");
            panelResultadoBusqueda.setVisible(false);
            return;
        }

        Optional<Producto> resultado = buscarProductoPorCodigo(criterio);

        if (resultado.isPresent()) {
            Producto p = resultado.get();
            // Seleccionar y enfocar en la tabla
            tblProductos.getSelectionModel().select(p);
            tblProductos.scrollTo(p);

            // Mostrar resultado en panel destacado
            lblResultadoBusqueda.setText(String.format("🔎 Encontrado: [%s] %s | Precio: C$ %.2f | Existencias: %d unidades",
                    p.getCodigo(), p.getNombre(), p.getPrecio(), p.getCantidad()));
            panelResultadoBusqueda.setVisible(true);

            mostrarMensaje("✓ Producto encontrado: " + p.getNombre() + " (" + p.getCantidad() + " en stock).", "status-success");
        } else {
            panelResultadoBusqueda.setVisible(false);
            mostrarMensaje("❌ No se encontró ningún producto con el código: " + criterio, "status-error");
            mostrarAlerta(Alert.AlertType.INFORMATION, "No encontrado", "No existe ningún producto con el código '" + criterio + "'.");
        }
    }

    /**
     * Evento ActionEvent para limpiar el formulario.
     */
    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
        panelResultadoBusqueda.setVisible(false);
        txtBuscar.clear();
        mostrarMensaje("Campos restablecidos.", "status-info");
    }

    private Optional<Producto> buscarProductoPorCodigo(String codigo) {
        return listaProductos.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    private void cargarProductoEnFormulario(Producto p) {
        txtCodigo.setText(p.getCodigo());
        txtNombre.setText(p.getNombre());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtCantidad.setText(String.valueOf(p.getCantidad()));
        mostrarMensaje("Cargado producto: " + p.getCodigo(), "status-info");
    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        txtCodigo.requestFocus();
    }

    private void actualizarContador() {
        lblTotalProductos.setText("Total de productos: " + listaProductos.size());
    }

    private void mostrarMensaje(String texto, String estiloCss) {
        lblMensaje.setText(texto);
        lblMensaje.getStyleClass().removeAll("status-info", "status-success", "status-error");
        lblMensaje.getStyleClass().add(estiloCss);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
