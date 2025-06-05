package com.ericruiz.ausentia.cli;

import com.ericruiz.ausentia.model.Trabajador;
import com.ericruiz.ausentia.model.Vacacion;
import com.ericruiz.ausentia.service.TrabajadorService;
import com.ericruiz.ausentia.utils.InputHelper;

import java.time.LocalDate;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


public class CliHandler {

    TrabajadorService trabajadores = new TrabajadorService();

    public CliHandler() {
    }

    private static void mostrarBienvenida() {
        System.out.println("===================================");
        System.out.println("       AUSENTIA - CLI v1.0      ");
        System.out.println("  Sistema de Control de Vacaciones");
        System.out.println("===================================");
        System.out.println("Para finalizar el programa presione Ctrl+C");
        System.out.println();
    }

    public void limpiarPantalla() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }


    public void start() {
        limpiarPantalla();
        mostrarBienvenida();
        LocalDate fechaInicio;
        LocalDate fechaFinal;
        LocalDate fechaContrato;
        boolean running = true;
        while (running) {
            System.out.println("Crear trabajador (c) - Eliminar trabajador (e) - Gestionar vacaciones (v) - Salir (s)");
            String command = InputHelper.leerStringValido();
            switch (command) {
                case "c": {
                    System.out.print("Nombre: ");
                    String nombre = InputHelper.leerStringValido();
                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacío");
                        break;
                    }
                    System.out.print("Fecha contrato: ");
                    fechaContrato = InputHelper.leerFechaValida();
                    trabajadores.addTrabajador(nombre, fechaContrato);
                    System.out.println("Trabajador creado");
                    break;
                }
                case "e": {
                    for (Trabajador trabajador : trabajadores.getTrabajadores()) {
                        System.out.println(trabajador.toString());
                    }
                    System.out.println("ID del trabajador a eliminar: ");
                    int id = InputHelper.leerEnteroValido();
                    if(!trabajadores.removeTrabajador(id)) {
                        System.out.println("Trabajador no econtrado");
                    } else {
                        System.out.println("Trabajador eliminado");
                    }
                    break;
                }
                case "v": {
                    limpiarPantalla();
                    System.out.println(trabajadores);
                    System.out.println("ID del trabajador: ");
                    int id = InputHelper.leerEnteroValido();
                    boolean encontrado = false;
                    for (Trabajador trabajador : trabajadores.getTrabajadores()) {
                        if (id == trabajador.getId()) {
                            encontrado = true;
                            boolean running2 = true;
                            while (running2) {
                                limpiarPantalla();
                                System.out.println("Añadir vacaciones (a) - Ver vacaciones (v) - Eliminar vacaciones (e) - Salir (s)");
                                String command2 = InputHelper.leerStringValido();
                                switch (command2) {
                                    case "a": {
                                        System.out.println("Introduce la fecha inicial en formato dd/MM/yyyy: ");
                                        fechaInicio = InputHelper.leerFechaValida();
                                        System.out.println("Introduce la fecha final en formato dd/MM/yyyy: ");
                                        fechaFinal = InputHelper.leerFechaValida();
                                        if (fechaFinal.isBefore(fechaInicio)) {
                                            System.out.println("La fecha final no puede ser anterior a la fecha inicial");
                                            break;
                                        }

                                        if (fechaInicio.isBefore(trabajador.getFechaContrato())) {
                                            System.out.println("Las vacaciones no pueden comenzar antes de la fecha de contratación");
                                            break;
                                        }

                                        boolean solapan = false;
                                        for (Vacacion vacacion : trabajador.getVacaciones()) {
                                            LocalDate inicioExistente = vacacion.getFechaInicio();
                                            LocalDate finExistente = vacacion.getFechaFin();
                                            if (!(fechaFinal.isBefore(inicioExistente) || fechaInicio.isAfter(finExistente))) {
                                                solapan = true;
                                                break;
                                            }
                                        }
                                        if (solapan) {
                                            System.out.println("Las fechas se solapan con vacaciones ya existentes");
                                            break;
                                        }


                                        if((trabajador.getDiasSolicitados() + (ChronoUnit.DAYS.between(fechaInicio, fechaFinal) + 1) > 22)) {
                                            System.out.println("ADVERTENCIA: Se han solicitado mas días de los disponibles por año");
                                        }

                                        if ((ChronoUnit.DAYS.between(fechaInicio, fechaFinal) + 1) > trabajador.getVacacionesDisponibles()) {
                                            System.out.println("ADVERTENCIA: Se han solicitado mas días de los disponibles.");
                                        }

                                        trabajador.addVacacion(new Vacacion(fechaInicio, fechaFinal));
                                        System.out.println("Vacaciones añadidas");
                                        break;

                                    }
                                    case "v": {
                                        List<Vacacion> arrayVacaciones = trabajador.getVacaciones();
                                        System.out.println("Vacaciones registradas:");
                                        if (arrayVacaciones.isEmpty()) {
                                            System.out.println("No hay vacaciones registradas.");
                                        } else {
                                            for (Vacacion vacacion : arrayVacaciones) {
                                                System.out.println(vacacion.toString());
                                            }
                                        }
                                        System.out.println("Días disponibles: " + trabajador.getVacacionesDisponibles());
                                        break;
                                    }
                                    case "e": {
                                        System.out.println("Introduce la fecha inicial en formato dd/MM/yyyy: ");
                                        fechaInicio = InputHelper.leerFechaValida();
                                        List<Vacacion> listaVacaciones = trabajador.getVacaciones();
                                        boolean encontradas = false;
                                        for (Vacacion vacacion : listaVacaciones) {
                                            if (vacacion.getFechaInicio().equals(fechaInicio)) {
                                                trabajador.removeVacacion(vacacion);
                                                encontradas = true;
                                                System.out.println("Vacaciones eliminadas");
                                                break;
                                            }
                                        }
                                        if (!encontradas) System.out.println("Vacaciones no encontradas");
                                        break;
                                    }
                                    case "s": {
                                        running2 = false;
                                        break;
                                    }
                                    default: {
                                        System.out.println("Comando no reconocido");
                                    }
                                }
                            }
                        }
                    }
                    if (!encontrado) System.out.println("Trabajador no encontrado");
                    break;
                }
                case "s": {
                    running = false;
                    break;
                }
                default: {
                    System.out.println("Comando no reconocido");
                }
            }
        }
    }
}

