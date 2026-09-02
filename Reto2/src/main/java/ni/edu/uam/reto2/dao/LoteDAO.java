package ni.edu.uam.reto2.dao;

import ni.edu.uam.reto2.interfaces.CRUD;
import ni.edu.uam.reto2.models.Lote;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos (DAO - Data Access Object) para la entidad {@link Lote}.
 * <p>
 * Implementa la interfaz {@link CRUD} gestionando una coleccion de lotes en memoria ({@link ArrayList}).
 * Separa la logica de almacenamiento y manipulacion de datos de la capa de presentacion (controlador),
 * facilitando el mantenimiento y permitiendo en el futuro cambiar el almacenamiento a una base de datos.
 */
public class LoteDAO implements CRUD<Lote> {

    /** Lista interna en memoria para almacenar los lotes registrados */
    private final List<Lote> lotes;

    /**
     * Inicializa el repositorio con una lista vacia en memoria.
     */
    public LoteDAO() {
        lotes = new ArrayList<>();
    }

    /**
     * Agrega un nuevo lote a la lista en memoria.
     *
     * @param lote Instancia de {@link Lote} a almacenar.
     */
    @Override
    public void agregar(Lote lote) {
        lotes.add(lote);
    }

    /**
     * Modifica los datos de un lote existente.
     * Utiliza el metodo {@code indexOf} (basado en {@code equals} por {@code idLote})
     * para ubicar la posicion del elemento y reemplazarlo.
     *
     * @param lote Instancia de {@link Lote} con los datos actualizados.
     */
    @Override
    public void editar(Lote lote) {
        int index = lotes.indexOf(lote);
        if (index != -1) {
            lotes.set(index, lote);
        }
    }

    /**
     * Remueve un lote de la lista segun su posicion numerica (indice).
     * Valida que el indice este dentro de los limites validos de la coleccion.
     *
     * @param index Posicion en base cero del lote a eliminar.
     */
    @Override
    public void eliminar(int index) {
        if (index >= 0 && index < lotes.size()) {
            lotes.remove(index);
        }
    }

    /**
     * Obtiene la lista completa de lotes registrados en memoria.
     *
     * @return Lista con los lotes de la cooperativa.
     */
    @Override
    public List<Lote> obtenerTodos() {
        return lotes;
    }
}