package ni.edu.uam.reto2.interfaces;

import java.util.List;

public interface CRUD<T> {

    void agregar(T objeto);
    void editar(T objeto);
    void eliminar(int index);
    List<T> obtenerTodos();
}
