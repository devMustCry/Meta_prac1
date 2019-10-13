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
public class AGG {
    
    private int tam;
    private ArrayList<ArrayList<Long>> mat_flujo = new ArrayList<>();
    private ArrayList<ArrayList<Long>> mat_distancia = new ArrayList<>();

    private ArrayList<ArrayList<Integer>> cromosomas = new ArrayList<>(); 
    private ArrayList<Long> coste_cromosomas = new ArrayList<>(); 
    private ArrayList<ArrayList<Integer>> cromosomas_aux = new ArrayList<>(); 
    private ArrayList<ArrayList<Integer>> cromosomas_hijos = new ArrayList<>(); 
   
    private ArrayList<Integer> cromosoma_intermedio1 = new ArrayList<>();
    private ArrayList<Integer> cromosoma_intermedio2 = new ArrayList<>();
    
    private ArrayList<ArrayList<Integer>> mejor_cromosoma_poblacion = new ArrayList<>();
    private ArrayList<Integer> marcajes = new ArrayList<>();

    private int evaluaciones;
    private float probabilidad_mutacion_cruce_por_gen;

    public AGG(int tam, ArrayList<ArrayList<Long>> matFlujo, ArrayList<ArrayList<Long>> matDistancia) {
        this.tam = tam;
        this.mat_flujo = matFlujo;
        this.mat_distancia = matDistancia;
        for(int i=0;i<TAMPOBLACION; i++){
            cromosomas.add(new ArrayList<>());
            coste_cromosomas.add(0L);
            cromosomas_aux.add(new ArrayList<>());
            cromosomas_hijos.add(new ArrayList<>());
            mejor_cromosoma_poblacion.add(new ArrayList<>());
            marcajes.add(0);
            
        }
        
        for(int i=0;i<tam;i++){
            cromosoma_intermedio1.add(0);
            cromosoma_intermedio2.add(0);
            
        }
        
        probabilidad_mutacion_cruce_por_gen = PROBMUTACIONCRUCE * tam;
        evaluaciones = 0;
    }
   
    
    
    
    
    public void algoritmoGeneticoGeneracional(int tam, ArrayList<ArrayList<Long>> matFlujo, ArrayList<ArrayList<Long> > matDistancia, ArrayList<Integer> permutOptima, String tipoCruce) {

        long mejor_coste_cromosoma;
        int posPeorCromosoma = 0;
        long peorCoste = 0;
        
        int posMejorCromosoma = 0;
        long mejorCoste = Integer.MAX_VALUE;


        
        int generacion = 1;
        
        try {    
            FileWriter fw=new FileWriter(".\\ficheroRegistroGeneracional.txt");

            
            cromosomas = creaPoblacion(tam);

            
            coste_cromosomas = calculaCostePoblacion(tam, matFlujo, matDistancia, cromosomas);
            evaluaciones += 50;

            while (evaluaciones < MAXNUMEVALUACIONES) {

                
                mejor_coste_cromosoma = Integer.MAX_VALUE;
                buscaMejorCromosomaPoblacion(cromosomas, coste_cromosomas, mejor_coste_cromosoma);

                
                
                torneo(cromosomas, cromosomas_aux, coste_cromosomas);

                cruce(cromosoma_intermedio1, cromosoma_intermedio2, marcajes, tipoCruce);


                
                boolean muta = false;
                for (int i = 0; i < TAMPOBLACION; i++) {
                    muta = mutacion(tam, probabilidad_mutacion_cruce_por_gen, cromosomas_hijos.get(i));
                    
                    if (muta) {
                        coste_cromosomas.set(i, calculoCoste(tam, matFlujo, matDistancia, cromosomas_hijos.get(i)));
                        evaluaciones++;
                        marcajes.set(i, 1);
                    }
                }

                peorCoste = 0;
                mejorCoste = Integer.MAX_VALUE;

                reemplazo(posPeorCromosoma, posMejorCromosoma, peorCoste, mejorCoste);
                
                elitismo(posPeorCromosoma, posMejorCromosoma, mejor_coste_cromosoma, mejorCoste);

                for (int i = 0; i < TAMPOBLACION; i++) {
                    if (marcajes.get(i) == 1) {
                        cromosomas.set(i, cromosomas_hijos.get(i));
                    }
                }
                fw.write("Generacion  " + (generacion++) + ": \n");
                fw.write("Mejor : ");
                for (int i = 0; i < tam; i++) {
                    fw.write((mejor_cromosoma_poblacion.get(0).get(i) + 1) + " ");
                }
                fw.write("\nCoste : " + mejor_coste_cromosoma +"\n");
                fw.write("\n");
                /****************************************************************/
            }
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(AGE.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        permutOptima = mejor_cromosoma_poblacion.get(0);
    }

    public void buscaMejorCromosomaPoblacion(ArrayList<ArrayList<Integer>> cromosomas, ArrayList<Long> costeCromosomas,
            long mejorCosteCromosoma) {
        for (int i = 0; i < TAMPOBLACION; i++) {
            if (costeCromosomas.get(i) < mejorCosteCromosoma) {
                mejor_cromosoma_poblacion.set(0, cromosomas.get(i));
                mejorCosteCromosoma = costeCromosomas.get(i);
            }
        }
    }

    public void torneo(ArrayList<ArrayList<Integer>> cromosomas, ArrayList<ArrayList<Integer>> cromosomasAux,
            ArrayList<Long> costeCromosomas) {

        int pos, pos2;
        for (int i = 0; i < TAMPOBLACION; i++) {
            pos = pos2 = i;
            while (pos == pos2) {
                pos = (int)Math.floor((int)Math.random()*(0-(TAMPOBLACION-1)+0)+(TAMPOBLACION-1)); 
                pos2 = (int)Math.floor((int)Math.random()*(0-(TAMPOBLACION-1)+0)+(TAMPOBLACION-1)); 
            }

            if (costeCromosomas.get(pos) < costeCromosomas.get(pos2)) { 
                cromosomasAux.set(i, cromosomas.get(pos)); 
            } else { 
                cromosomasAux.set(i, cromosomas.get(pos2));
            }

        }

        
        for (int i = 0; i < TAMPOBLACION; i++) {
            cromosomas.set(i, cromosomasAux.get(i));
        }
    }

    public void cruce(ArrayList<Integer> cromosomaIntermedio1, ArrayList<Integer> cromosomaIntermedio2, ArrayList<Integer> vMarcaje, String tipoCruce) {
        vMarcaje.clear();
        for(int i=0;i<TAMPOBLACION; i++){
            vMarcaje.add(i, 0);
        }
        int cont = 0;

        int pos, pos2;

        if (tipoCruce == "OX") {
            cont = 0;

            
            for (int i = 0; i < TAMPOBLACION / 2; i++) {
                
                cromosomaIntermedio1.clear();
                cromosomaIntermedio2.clear();
                for(int j=0;j<tam;j++){
                    cromosomaIntermedio1.add(j, 0);
                    cromosomaIntermedio2.add(j, 0);
                }
                
              

                pos = (int) (Math.random() * TAMPOBLACION -1); 
                pos2 = (int) (Math.random() * TAMPOBLACION - 1); 

                
                while (pos == pos2) {
                    pos = (int) (Math.random() * TAMPOBLACION - 1); 
                    pos2 = (int) (Math.random() * TAMPOBLACION - 1); 
                }

                if (PROBCRUCEGENERACIONAL >= RandomFloat(0, 1)) {
                    
                    cruceOX(tam, cromosomas.get(pos), cromosomas.get(pos2), cromosomaIntermedio1, cromosomaIntermedio2);

                    
                    cromosomas_hijos.set(cont,cromosomaIntermedio1);
                    coste_cromosomas.set(cont, calculoCoste(tam, mat_flujo, mat_distancia, cromosomaIntermedio1));
                    evaluaciones++;
                    vMarcaje.set(cont, 1);
                    cont++;

                    
                    cromosomas_hijos.set(cont, cromosomaIntermedio2);
                    coste_cromosomas.set(cont, calculoCoste(tam, mat_flujo, mat_distancia, cromosomaIntermedio2));
                    evaluaciones++;
                    vMarcaje.set(cont, 1);
                    cont++;
                } else {
                    
                    cromosomas_hijos.set(cont, cromosomas.get(i));
                    cont++;
                    cromosomas_hijos.set(cont, cromosomas.get(pos));
                    cont++;
                }
            }
        }

        if (tipoCruce == "PMX") {
            cont = 0;

            
            for (int i = 0; i < TAMPOBLACION / 2; i++) {
                
                cromosomaIntermedio1.clear();
                cromosomaIntermedio2.clear();
                
                for(int j=0;j<tam;j++){
                    cromosomaIntermedio1.add(j, 0);
                    cromosomaIntermedio2.add(j, 0);
                }

                pos = (int) (Math.random() * TAMPOBLACION -1); 
                pos2 = (int) (Math.random() * TAMPOBLACION - 1); 

                
                while (pos == pos2) {
                    pos = (int) (Math.random() * TAMPOBLACION - 1); 
                    pos2 = (int) (Math.random() * TAMPOBLACION - 1); 
                }

                if (PROBCRUCEGENERACIONAL >= RandomFloat(0, 1)) {
                    
                    crucePMX(tam, cromosomas.get(pos), cromosomas.get(pos2), cromosomaIntermedio1, cromosomaIntermedio2);

                    
                    cromosomas_hijos.set(cont, cromosomaIntermedio1);
                    coste_cromosomas.set(cont, calculoCoste(tam, mat_flujo, mat_distancia, cromosomaIntermedio1));
                    evaluaciones++;
                    vMarcaje.set(cont, 1);
                    cont++;

                    
                    cromosomas_hijos.set(cont, cromosomaIntermedio2);
                    coste_cromosomas.set(cont, calculoCoste(tam, mat_flujo, mat_distancia, cromosomaIntermedio2));
                    evaluaciones++;
                    vMarcaje.set(cont, 1);
                    cont++;
                } else {
                    
                    cromosomas_hijos.set(cont, cromosomas.get(i));
                    cont++;
                    cromosomas_hijos.set(cont, cromosomas.get(pos));
                    cont++;
                }
            }

        }

    }

    public boolean mutacion(int tam, float probabilidadMutacionCruce, ArrayList<Integer> hijo) {
        long pos1;

        int i = 0;
        while (i < tam) {
            if (probabilidadMutacionCruce > RandomFloat(0, 1)) {
                pos1 = i;

                while (i == pos1) {
                    pos1 = (int) (Math.random() * tam - 1);
                }
                Collections.swap(hijo, i, (int)pos1); 
                return true;
            }
            i++;
        }
        
        return false;
    }

    public void reemplazo(int posPeorCromosoma,
            int posMejorCromosoma, long peorCoste, long mejorCoste) {
        
        for (int i = 0; i < TAMPOBLACION; i++) {
            
            if (coste_cromosomas.get(i) > peorCoste) {
                peorCoste = coste_cromosomas.get(i);
                posPeorCromosoma = i;
            }
            
            if (coste_cromosomas.get(i) < mejorCoste) {
                mejorCoste = coste_cromosomas.get(i);
                posMejorCromosoma = i;
            }
        }
    }

    public void elitismo(int posPeorCromosoma,
                int posMejorCromosoma, long mejorCosteCromosoma, long mejorCoste) {
        if (mejorCosteCromosoma < mejorCoste) {
            cromosomas_hijos.set(posPeorCromosoma, mejor_cromosoma_poblacion.get(0));
        } else { 
            mejor_cromosoma_poblacion.set(0, cromosomas_hijos.get(posMejorCromosoma));
            mejorCosteCromosoma = mejorCoste;
        }
    }

}
