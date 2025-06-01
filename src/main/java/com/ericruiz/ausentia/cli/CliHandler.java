package com.ericruiz.ausentia.cli;

import com.ericruiz.ausentia.model.Trabajador;
import com.ericruiz.ausentia.model.Vacacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CliHandler {
    private List<Trabajador> trabajadores = new ArrayList<>();

    public CliHandler() {
    }

    public int leerEnteroValido(Scanner input) {
        int numero;
        while (true) {
            String linea = input.nextLine();
            try {
                numero = Integer.parseInt(linea);
                return numero; // Salimos con valor válido
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, por favor introduce un número entero válido.");
            }
        }
    }


    public void start() {
        Scanner input = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaContrato;
        LocalDate fechaInicio;
        LocalDate fechaFinal;
        System.out.println("Bienvenido al sistema de gestión de vacaciones");
        System.out.println("Para salir presione Ctrl+C");
        boolean running = true;
        while (running) {
            System.out.println("Crear trabajador (c) - Eliminar trabajador (e) - Gestionar vacaciones (v) - Salir (s)");
            String command = input.nextLine();
            switch (command) {
                case "c": {
                    System.out.print("Nombre: ");
                    String nombre = input.nextLine();
                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacío");
                        break;
                    }
                    System.out.print("Fecha contrato: ");
                    try {
                        fechaContrato = LocalDate.parse(input.nextLine(), formatter);
                    } catch (DateTimeParseException e) {
                        System.out.println("Fecha inválida");
                        break;
                    }
                    trabajadores.add(new Trabajador(nombre, fechaContrato));
                    System.out.println("Trabajador creado");
                    break;
                }
                case "e": {
                    for (Trabajador trabajador : trabajadores) {
                        System.out.println(trabajador.toString());
                    }
                    System.out.println("ID del trabajador a eliminar: ");
                    int id = leerEnteroValido(input);
                    boolean encontrado = false;
                    for (Trabajador trabajador : trabajadores) {
                        if (id == trabajador.getId()) {
                            encontrado = true;
                            trabajadores.remove(trabajador);
                            System.out.println("Trabajador eliminado");
                            break;
                        }
                    }
                    if (!encontrado) System.out.println("Trabajador no econtrado");
                    break;
                }
                case "v": {
                    for (Trabajador trabajador : trabajadores) {
                        System.out.println(trabajador.toString());
                    }
                    System.out.println("ID del trabajador: ");
                    int id = leerEnteroValido(input);
                    boolean encontrado = false;
                    for (Trabajador trabajador : trabajadores) {
                        if (id == trabajador.getId()) {
                            encontrado = true;
                            boolean running2 = true;
                            while (running2) {
                                System.out.println("Añadir vacaciones (a) - Ver vacaciones (v) - Eliminar vacaciones (e) - Salir (s)");
                                String command2 = input.nextLine();
                                switch (command2) {
                                    case "a": {
                                        System.out.println("Introduce la fecha inicial en formato dd/MM/yyyy: ");
                                        String stringFechaInicio = input.nextLine();
                                        try {
                                            fechaInicio = LocalDate.parse(stringFechaInicio, formatter);
                                        } catch (DateTimeParseException e) {
                                            System.out.println("Fecha inválida: " + e.getMessage());
                                            break;
                                        }
                                        System.out.println("Introduce la fecha final en formato dd/MM/yyyy: ");
                                        String stringFechaFinal = input.nextLine();
                                        try {
                                            fechaFinal = LocalDate.parse(stringFechaFinal, formatter);
                                        } catch (DateTimeParseException e) {
                                            System.out.println("Fecha inválida: " + e.getMessage());
                                            break;
                                        }
                                        if (fechaFinal.isBefore(fechaInicio)) {
                                            System.out.println("La fecha final no puede ser anterior a la fecha inicial");
                                            break;
                                        }

                                        if (ChronoUnit.DAYS.between(fechaInicio, fechaFinal) > trabajador.getVacacionesDisponibles()) {
                                            System.out.println("Ha escogido más días de los disponibles");
                                            break;
                                        }

                                        trabajador.addVacacion(new Vacacion(fechaInicio, fechaFinal));
                                        System.out.println("Vacaciones añadidas");
                                        break;

                                    }
                                    case "v": {
                                        List<Vacacion> arrayVacaciones = trabajador.getVacaciones();
                                        for (Vacacion vacacion : arrayVacaciones) {
                                            System.out.println(vacacion.toString());
                                        }
                                        break;
                                    }
                                    case "e": {
                                        System.out.println("Introduce la fecha inicial en formato dd/MM/yyyy: ");
                                        String stringFechaInicio = input.nextLine();
                                        try {
                                            fechaInicio = LocalDate.parse(stringFechaInicio, formatter);
                                        } catch (DateTimeParseException e) {
                                            System.out.println("Fecha inválida: " + e.getMessage());
                                            break;
                                        }
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

