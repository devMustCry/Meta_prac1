/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta_prac2;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import meta_prac2.Utils.*;

/**
 *
 * @author Ismael
 */
public class Meta_prac2 {

    
    
    public static void main(String[] args) {
        ArrayList<ArrayList <Long>> matrizF = new ArrayList<ArrayList<Long>>();
        ArrayList<ArrayList <Long>> matrizD = new ArrayList<ArrayList<Long>>();
        ArrayList<Integer> permutacionOptima = new ArrayList<Integer>();

        String nomFich = "data/cnf01.dat";

        int tamanio = 0;
        int semilla = 26048109;
        // Fichero del que queremos leer
        File fichero = new File(nomFich);
        Scanner s = null;

        Long coste = 0L;
        
        int ite = 0;
        
        
        try {
            // Leemos el contenido del fichero
            s = new Scanner(fichero);

            // Leemos linea a linea el fichero
            while (s.hasNextLine()) {
                String linea = s.nextLine(); 	// Guardamos la linea en un String
                linea = linea.trim();
                String[] cortarString = linea.split(" ");

                ArrayList<Long> vectorLectura = new ArrayList<Long>();

                if (ite == 0) {
                    tamanio = Integer.parseInt(linea.trim());
                    System.out.println("tamanio " +tamanio);
                }else if(ite >= 2 && ite <= (tamanio + 1)){
                    for (int i = 0; i < cortarString.length; i++) {
                        if (!cortarString[i].equals("")) {
                            vectorLectura.add(Long.parseLong(cortarString[i].trim()));                            
                        }
                    }
                    matrizF.add(vectorLectura);
                }else if(ite > (tamanio + 2)){
                    for (int i = 0; i < cortarString.length; i++) {
                        if (!cortarString[i].equals("")) {
                            vectorLectura.add(Long.parseLong(cortarString[i].trim()));                            
                        }
                    }
                    matrizD.add(vectorLectura);
                }
                ite++;
            }

        } catch (Exception ex) {
            System.out.println("Mensaje:" + ite );
        } finally {
            // Cerramos el fichero tanto si la lectura ha sido correcta o no
            try {
                if (s != null) {
                    s.close();
                }
            } catch (Exception ex2) {
                System.out.println("Mensaje 2: " + ex2.getMessage());
            }
        }
        
        
        for(int i=0;i<tamanio;i++){
            permutacionOptima.add(0);
        }
        
        


        long startTime = System.currentTimeMillis();
        AGE age = new AGE(tamanio, matrizF, matrizD);
        age.algoritmoGeneticoEstacionario(tamanio, matrizF, matrizD, permutacionOptima, "OX");
        long endTime = System.currentTimeMillis();


        System.out.println("\nPermutacion Optima: ");
        for (int i = 0; i < tamanio; i++) {
            System.out.print((permutacionOptima.get(i) + 1) + " ");
        }

        System.out.println("");
        
         double duration = (((double)endTime - startTime)/(double)1000);

        coste = Utils.calculoCoste(tamanio, matrizF, matrizD, permutacionOptima);
        System.out.println("");
        System.out.println("El coste del Genético Estacionario OX : " + coste);
        System.out.println("Tiempo de ejecución: " + duration + " segundos");
        
        
        /*long startTime = System.currentTimeMillis();
        AGG agg = new AGG(tamanio, matrizF, matrizD);
        agg.algoritmoGeneticoGeneracional(tamanio, matrizF, matrizD, permutacionOptima, "PMX");
        long endTime = System.currentTimeMillis();


        System.out.println("");
        System.out.println("Permitacion Optima: ");
        for (int i = 0; i < tamanio; i++) {
            System.out.print((permutacionOptima.get(i) + 1) + "");
        }
        System.out.println("");
        coste = Utils.calculoCoste(tamanio, matrizF, matrizD, permutacionOptima);
        System.out.println("");
        
        double duration = (((double)endTime - startTime)/(double)1000);
        
        System.out.println("El coste del Genético Generacional OX : " + coste);
        System.out.println("Tiempo de ejecución: " + duration + " segundos");*/
        
        

        
    }
    
}
