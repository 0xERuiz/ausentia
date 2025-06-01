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

    public boolean removeTrabajador(int id) {
        return trabajadores.removeIf(trabajador -> trabajador.getId() == id);
    }

    public Trabajador findById(int id) {
        for (Trabajador t : trabajadores) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public String toString() {
        if (trabajadores.isEmpty()) return "No hay trabajadores registrados.";
        StringBuilder salida = new StringBuilder();
        for(Trabajador t : trabajadores) {
            salida.append(t).append("\n");
        }
        return salida.toString();
    }
}
