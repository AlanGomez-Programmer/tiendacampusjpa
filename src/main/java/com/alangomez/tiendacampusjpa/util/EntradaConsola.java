package com.alangomez.tiendacampusjpa.util;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;

@Service
public class EntradaConsola {

    private Scanner entrada = new Scanner(System.in);

    public String ingresarTexto(String mensaje){
        while (true){
            System.out.print(mensaje);
            String txt = entrada.nextLine().trim();
            if (txt.isEmpty()){
                System.out.println("No puedes dejar vacío");
            } else {
                return txt;
            }
        }
    }

    public int ingresarEntero(String mensaje){
        while (true){
            try {
                int num = Integer.parseInt(ingresarTexto(mensaje));
                if (num < 0){
                    System.out.println("Error: No se aceptan valores menores a 0");
                } else {
                    return num;
                }
            } catch (NumberFormatException e){
                System.out.println("Error: Solo se aceptan números enteros e.j. 1");
            }
        }
    }

    public BigDecimal ingresarBigDecimal(String mensaje){
        while(true){
            try {
                String num = ingresarTexto(mensaje).replace(",",".");
                return new BigDecimal(num);
            } catch (NumberFormatException e) {
                System.out.println("Error: Solo se aceptan números decimales e.j. 100.1");
            }
        }
    }

    public int ingresarOpcion(String mensaje){
        return ingresarEntero(mensaje);
    }

    public boolean confirmar(String mensaje){
        while (true){
            String txt = ingresarTexto(mensaje).toUpperCase();
            if (txt.equals("S")){
                return true;
            } else if (txt.equals("N")){
                return false;
            } else {
                System.out.println("Error: Solo se aceptan S o N ");
            }
        }
    }
}
