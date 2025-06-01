package com.ericruiz.ausentia.service;

import com.ericruiz.ausentia.model.Trabajador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorService {
    private List<Trabajador> trabajadores = new ArrayList<>();

    public TrabajadorService() {
    }

    public List<Trabajador> getTrabajadores() {
        return new ArrayList<>(this.trabajadores);
    }

    public Trabajador addTrabajador(String nombre, LocalDate fechaContrato) {
        Trabajador nuevo = new Trabajador(nombre, fechaContrato);
        trabajadores.add(nuevo);
        return nuevo;
    }

}
