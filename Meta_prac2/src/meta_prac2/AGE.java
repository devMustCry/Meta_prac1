/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta_prac2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import static meta_prac2.Utils.*;

/**
 *
 * @author Ismael
 */

public class AGE {
    
    private int tam = 0 ;
    private ArrayList<ArrayList<Long>> mat_flujo = new ArrayList<>();
    private ArrayList<ArrayList<Long>> mat_distancia = new ArrayList<>();


    private ArrayList<ArrayList<Integer>> poblacion = new ArrayList<>();
    private ArrayList<Long> coste_poblacion = new ArrayList<>();
    private ArrayList<Integer> padre1 = new ArrayList<>();
    private ArrayList<Integer> padre2 = new ArrayList<>();
    private ArrayList<Integer> hijo1 = new ArrayList<>();
    private ArrayList<Integer> hijo2 = new ArrayList<>();
    private float probabilidad_mutacionCruce = 0;
    private int evaluaciones = 0;
    
    public AGE(int tam_, ArrayList<ArrayList<Long> > matFlujo_, ArrayList<ArrayList<Long> > matDistancia_){
        
        
        tam = tam_;
        mat_flujo = matFlujo_;
        mat_distancia = matDistancia_;
        poblacion = new ArrayList<>();
        coste_poblacion = new ArrayList<>();
        for(int i = 0; i< tam; i++){
            padre1.add(0);
            padre2.add(0);
            hijo1.add(0);
            hijo2.add(0);
            
        }
        
        for(int i=0;i<TAMPOBLACION; i++){
            coste_poblacion.add((long)0);
            for(int j=0;j<TAMPOBLACION; j++){
                poblacion.add(new ArrayList<>());
            }
        }
        
        evaluaciones = 0;
        probabilidad_mutacionCruce = PROBMUTACIONCRUCE * tam;
        
        
    }
    
    public void inicializaPoblacion(int tam) {
        poblacion = creaPoblacion(tam);
    }
    
    public void evaluarIndividuosPoblacion(ArrayList<ArrayList<Integer>> poblacion) {
        this.coste_poblacion = calculaCostePoblacion(tam, mat_flujo, mat_distancia, poblacion);
    }
    
    
    public void torneo(ArrayList<Integer> individuos, ArrayList<Long> costesIndividuos) {

        int high;


        high = TAMPOBLACION - 1;

        for (int i = 0; i < individuos.size(); i++) {
            individuos.set(i, (int) (Math.random() * (high + 1)));
            costesIndividuos.set(i, coste_poblacion.get(individuos.get(i)));
        }

        if (costesIndividuos.get(0) < costesIndividuos.get(1)){ // comparo el coste de los dos primeros individuos
            padre1.clear();
            padre1.addAll(poblacion.get(individuos.get(0))); // me quedo con el mejor de los dos (menor coste))
           
        }
        else
            padre1 = poblacion.get(individuos.get(1));

        if (costesIndividuos.get(2) < costesIndividuos.get(3)) // comparo el coste de los dos siguiente individuos
            padre2 = poblacion.get(individuos.get(2)); // me quedo con el mejor de los dos
        else
            padre2 = poblacion.get(individuos.get(3));

    }
    
    
    
    public void cruce(String tipo) {
        hijo1.clear();
        hijo2.clear();

        for(int i = 0; i< tam; i++){
                hijo1.add(0);
                hijo2.add(0);
            }

        if (tipo == "OX") {
            cruceOX(tam, padre1, padre2, hijo1, hijo2); // Realizo el cruce para obtener a los dos hijos
            evaluaciones++;
            evaluaciones++;
        }

        if (tipo == "PMX") {
            crucePMX(tam, padre1, padre2, hijo1, hijo2); // Realizo el cruce para obtener a los dos hijos
            evaluaciones++;
            evaluaciones++;
        }
    }
    
    
    public boolean mutacion(int tam, float probabilidad_mutacionCruce, ArrayList<Integer> hijo) {
        int pos1;

        int i = 0;
        while (i < tam) {
            if (probabilidad_mutacionCruce > RandomFloat(0, 1)) {
                pos1 = i;

                while (i == pos1) {
                    pos1 = (int)Math.floor((int)Math.random()*((1-tam+1)+tam));  // Valor entre M y N, ambos incluidos.
                   
                }
                
                Collections.swap(hijo, i, pos1); 
                return true;
            }
             i++;
        }
        return false;
    }
    
    
    public void reemplazo(ArrayList<Long> costes_hijos) {

        long peor_coste1 = 0;
        long peor_coste2 = 0;
        int posicion_peor_coste1 = 0;
        int posicion_peor_coste2 = 0;


        for (int i = 0; i < TAMPOBLACION; i++) {
            if (coste_poblacion.get(i) > peor_coste2) {
                if (coste_poblacion.get(i) > peor_coste1) {
                    peor_coste2 = peor_coste1;
                    peor_coste1 = coste_poblacion.get(i);
                    posicion_peor_coste2 = i;
                    posicion_peor_coste1 = i;
                } else {
                    peor_coste2 = coste_poblacion.get(i);
                    posicion_peor_coste2 = i;
                }
            }
        }

        if ((peor_coste1 > costes_hijos.get(0)) && (peor_coste2 > costes_hijos.get(1))) { 
            poblacion.set(posicion_peor_coste1, hijo1);
            poblacion.set(posicion_peor_coste2, hijo2);
            coste_poblacion.set(posicion_peor_coste1, costes_hijos.get(0));
            coste_poblacion.set(posicion_peor_coste2, costes_hijos.get(1));
        } else if ((peor_coste1 > costes_hijos.get(0)) && (peor_coste2 < costes_hijos.get(1))) {                   
            poblacion.set(posicion_peor_coste1,hijo1);
            coste_poblacion.set(posicion_peor_coste1,costes_hijos.get(0));
        } else if ((peor_coste1 < costes_hijos.get(0)) && (peor_coste2 > costes_hijos.get(1))) {  
            poblacion.set(posicion_peor_coste2,hijo2);
            coste_poblacion.set(posicion_peor_coste2,costes_hijos.get(1));
        }
    }
    
    public void algoritmoGeneticoEstacionario(int tam, ArrayList<ArrayList<Long>> mat_flujo,
        ArrayList<ArrayList<Long>> mat_distancia, ArrayList<Integer> permut_optima, String tipo_cruce) {

        ArrayList<Integer> individuos = new ArrayList<>(); // vector con los individuos que participaran en el torneo
        ArrayList<Long> costes_individuos = new ArrayList<>(); // Vector con los costes de los individuos que han participado en el  torneo
        ArrayList<Long> costes_hijos = new ArrayList<>(); // vector con el coste de los dos hijso creados.
        
        for(int i=0; i<4; i++){
            individuos.add(0);
            costes_individuos.add(0L);
        }
        
        costes_hijos.add(0L);
        costes_hijos.add(0L);

        int generacion = 0;

        // Coste del mejor individuo de la poblacion
        long individuo_mejor_coste;
        int pos_mejor_individuo = 0;
        
        try {    
            FileWriter fw=new FileWriter(".\\archivoLogEjecuciones.txt");       
        

            inicializaPoblacion(tam);

            evaluarIndividuosPoblacion(poblacion);
            evaluaciones += 50;

            while (evaluaciones < MAXNUMEVALUACIONES) {
                torneo(individuos, costes_individuos);

                cruce(tipo_cruce); 
     
                if (mutacion(tam, probabilidad_mutacionCruce, hijo1)) {
                    evaluaciones++;
                }

                if (mutacion(tam, probabilidad_mutacionCruce, hijo2)) {
                    evaluaciones++;
                }


                costes_hijos.set(0, calculoCoste(tam, mat_flujo, mat_distancia, hijo1));
                costes_hijos.set(1, calculoCoste(tam, mat_flujo, mat_distancia, hijo1));

                reemplazo(costes_hijos);

                individuo_mejor_coste = Integer.MAX_VALUE;
                pos_mejor_individuo = 0;

                int p = 0; 
                while (p < TAMPOBLACION) {
                    if (coste_poblacion.get(p) < individuo_mejor_coste) {
                        individuo_mejor_coste = coste_poblacion.get(p);
                        pos_mejor_individuo = p;
                    }
                    p++;
                }
                
                fw.write("POBLACION  " + (++generacion) + ": \n");
                
                fw.write("MEJOR INDIVIDUO : \n");

                int i = 0;
                while ( i < tam) {
                    fw.write(poblacion.get(pos_mejor_individuo).get(i) + 1);
                    i++;
                }
                fw.write("\nCOSTE MEJOR INDIVIDUO : " + coste_poblacion.get(pos_mejor_individuo) + "\n");
                fw.write("_____________________________________________________________________\n");

            }

            fw.close();
        
        } catch (IOException ex) {
            Logger.getLogger(AGE.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("NUMERO DE EVALUACIONES: " + evaluaciones);

        permut_optima = poblacion.get(pos_mejor_individuo);

    }
}
