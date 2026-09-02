package ni.edu.uam.reto2.interfaces;

import java.util.List;

/**
 * Interfaz generica que define el contrato para las operaciones basicas de persistencia (CRUD).
 * <p>
 * Aplica el principio de segregacion de interfaces y abstraccion generica para que cualquier
 * clase DAO (Data Access Object) implemente los metodos estandar: Crear (Create), Leer (Read),
 * Actualizar (Update) y Eliminar (Delete).
 *
 * @param <T> Tipo de entidad u objeto de dominio que gestionara el repositorio.
 */
public interface CRUD<T> {

    /**
     * Agrega un nuevo elemento al almacenamiento.
     *
     * @param objeto Objeto de tipo T a registrar.
     */
    void agregar(T objeto);

    /**
     * Actualiza la informacion de un elemento existente.
     *
     * @param objeto Objeto de tipo T con los datos modificados.
     */
    void editar(T objeto);

    /**
     * Elimina un elemento del almacenamiento segun su posicion o indice.
     *
     * @param index Indice del elemento a remover.
     */
    void eliminar(int index);

    /**
     * Retorna la lista completa de todos los elementos almacenados.
     *
     * @return Lista con todos los elementos de tipo T.
     */
    List<T> obtenerTodos();
}
