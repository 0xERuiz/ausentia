package com.ericruiz.ausentia.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Trabajador {
    private final int id;
    private String nombre;
    private List<Vacacion> vacaciones = new ArrayList<>();
    private static int nextId = 0;
    private enum tipoContrato {
        TEMPORAL,
        SUSTITUCION,
        INDEFINIDO
    }
    private tipoContrato tipoContrato;
    private int diasVacaciones;
    private LocalDate fechaContrato;

    public Trabajador(String nombre, LocalDate fechaContrato) {
        this.id = generateId();
        this.nombre = nombre;
        this.fechaContrato = fechaContrato;
        this.vacaciones = new ArrayList<>();
        long trabajado = ChronoUnit.MONTHS.between(fechaContrato, LocalDate.now());
        this.diasVacaciones = (int) (1.8333 * trabajado);
    }

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public List<Vacacion> getVacaciones() {
        return this.vacaciones;
    }

    public int getDiasVacaciones() {
        return diasVacaciones;
    }

    public int getVacacionesDisponibles() {
        return diasVacaciones - getDiasSolicitados();
    }

    public int getDiasSolicitados() {
        int diasSolicitados = 0;
        for(Vacacion vacacion : this.vacaciones) {
            diasSolicitados += 1 + (int) ChronoUnit.DAYS.between(vacacion.getFechaInicio(), vacacion.getFechaFin());
        }
        return diasSolicitados;
    }

    public LocalDate getFechaContrato() {
        return this.fechaContrato;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addVacacion(Vacacion vacacion) {
        this.vacaciones.add(vacacion);
    }

    public void removeVacacion(Vacacion vacacion) {
        this.vacaciones.remove(vacacion);
    }

    public String toString() {
        return "Trabajador " + this.id + " - " + this.nombre +
                " | Fecha contrato: " + this.fechaContrato +
                " | Vacaciones: " + this.diasVacaciones +
                " | Disponibles: " + this.getVacacionesDisponibles();

    }

    private static synchronized int generateId() {
        return ++nextId;
    }
}
