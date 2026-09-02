package ni.edu.uam.reto2.interfaces;

import java.util.List;

/**
 * Interfaz genérica que define el contrato de operaciones CRUD
 * (Create, Read, Update, Delete) para cualquier entidad del sistema.
 * 
 * @param <T> Tipo de dato genérico del modelo gestionado.
 * @author Equipo WAAS
 */
public interface CRUD<T> {

    /**
     * Registra un nuevo objeto en el almacén de datos.
     * 
     * @param objeto Instancia a agregar.
     */
    void agregar(T objeto);

    /**
     * Actualiza la información de un objeto existente.
     * 
     * @param objeto Instancia con los datos modificados.
     */
    void editar(T objeto);

    /**
     * Elimina un objeto del almacén de datos a partir de su índice posicional.
     * 
     * @param index Posición del elemento a remover.
     */
    void eliminar(int index);

    /**
     * Retorna la lista completa de todos los objetos registrados.
     * 
     * @return Lista con los elementos de tipo T.
     */
    List<T> obtenerTodos();
}
