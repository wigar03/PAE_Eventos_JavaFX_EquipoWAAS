package ni.edu.uam.reto2.models;



import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;



import java.time.LocalDate;



@Getter

@Setter

@AllArgsConstructor

@NoArgsConstructor



public class Lote {



    private String idLote;

    private String nombreProducto;

    private String cantidadKilos;

    LocalDate fechaEntrega;

    LocalDate fechaCaducidad;

    private String tipoGrano;



}

