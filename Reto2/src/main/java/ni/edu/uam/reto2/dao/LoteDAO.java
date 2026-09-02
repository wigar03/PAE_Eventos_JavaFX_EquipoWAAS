package ni.edu.uam.reto2.dao;

import ni.edu.uam.reto2.interfaces.CRUD;
import ni.edu.uam.reto2.models.Lote;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del Patrón DAO (Data Access Object) para la entidad {@link Lote}.
 * 
 * Centraliza y abstrae todas las operaciones de persistencia en memoria (lista interna),
 * implementando la interfaz genérica {@link CRUD} tipada con Lote. Incluye validaciones
 * de seguridad en índices para evitar excepciones de tipo IndexOutOfBoundsException.
 * 
 * @author Equipo WAAS
 */
public class LoteDAO implements CRUD<Lote> {

    // Estructura de almacenamiento en memoria de los lotes
    private final List<Lote> lotes;

    /**
     * Constructor que inicializa la colección de datos.
     */
    public LoteDAO() {
        lotes = new ArrayList<>();
    }

    /**
     * Agrega un nuevo lote a la lista en memoria.
     * 
     * @param lote Instancia de Lote a almacenar.
     */
    @Override
    public void agregar(Lote lote) {
        lotes.add(lote);
    }

    /**
     * Edita un lote existente. Utiliza indexOf para ubicar el lote por su idLote
     * y reemplaza el registro únicamente si fue encontrado.
     * 
     * @param lote Instancia de Lote con los datos actualizados.
     */
    @Override
    public void editar(Lote lote) {
        int index = lotes.indexOf(lote);
        if (index != -1) {
            lotes.set(index, lote);
        }
    }

    /**
     * Elimina el lote ubicado en la posición index con validación de límites.
     * 
     * @param index Posición a eliminar.
     */
    @Override
    public void eliminar(int index) {
        if (index >= 0 && index < lotes.size()) {
            lotes.remove(index);
        }
    }

    /**
     * Obtiene la lista completa de lotes registrados.
     * 
     * @return Lista de lotes.
     */
    @Override
    public List<Lote> obtenerTodos() {
        return lotes;
    }
}