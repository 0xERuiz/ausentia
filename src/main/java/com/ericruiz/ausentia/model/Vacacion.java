package com.ericruiz.ausentia.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Vacacion {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Vacacion(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Vacacion() {}

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.fechaInicio.format(formatter) + " - " + this.fechaFin.format(formatter);
    }

}
