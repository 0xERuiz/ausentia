package com.ericruiz.ausentia.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHelper {
    private static Scanner input = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static int leerEnteroValido() {
        int numero;
        while (true) {
            try {
                numero = Integer.parseInt(input.nextLine());
                return numero;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, por favor introduce un número entero válido.");
            }
        }
    }

    public static LocalDate leerFechaValida() {
        LocalDate fecha;
        while (true) {
            try {
                fecha = LocalDate.parse(input.nextLine(), formatter);
                return fecha;
            } catch (DateTimeParseException e) {
                System.out.println("Entrada inválida, por favor introduce una fecha con formato dd/MM/yyyy.");
            }
        }
    }

    public static String leerStringValido() {
        String linea;
        while (true) {
            try {
                linea = input.nextLine().trim();
                if(linea.isEmpty()) {
                    System.out.println("La entrada no puede estar vacía. Intenta de nuevo.");
                    continue;
                }
                return linea;
            } catch (IllegalArgumentException e) {
                //No se cuando se puede dar.
                System.out.println("Entrada inválida, por favor introduce una línea válida");
            }
        }
    }
}
