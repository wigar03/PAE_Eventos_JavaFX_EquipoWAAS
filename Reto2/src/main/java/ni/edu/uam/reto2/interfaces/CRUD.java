package ni.edu.uam.reto2.interfaces;

import ni.edu.uam.reto2.models.Lote;

import java.util.List;

public interface CRUD <T>{

    public void agregar(Lote lote);
    public void editar(Lote lote);
    public void eliminar(int idLote);

    List<Lote> obtenerTodos();


}
