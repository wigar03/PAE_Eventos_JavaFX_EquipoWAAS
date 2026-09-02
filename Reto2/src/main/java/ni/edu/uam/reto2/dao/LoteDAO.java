package ni.edu.uam.reto2.dao;

import ni.edu.uam.reto2.interfaces.CRUD;
import ni.edu.uam.reto2.models.Lote;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO implements CRUD<Lote> {

    private final List<Lote> lotes;

    public LoteDAO() {
        lotes = new ArrayList<>();
    }

    @Override
    public void agregar(Lote lote) {
        lotes.add(lote);
    }

    @Override
    public void editar(Lote lote) {
        int index = lotes.indexOf(lote);
        if (index != -1) {
            lotes.set(index, lote);
        }
    }

    @Override
    public void eliminar(int index) {
        if (index >= 0 && index < lotes.size()) {
            lotes.remove(index);
        }
    }

    @Override
    public List<Lote> obtenerTodos() {
        return lotes;
    }
}