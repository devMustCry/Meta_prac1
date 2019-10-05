/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta_prac2;

import java.util.ArrayList;
import static meta_prac2.Utils.PROBMUTACIONCRUCE;
import static meta_prac2.Utils.TAMPOBLACION;

/**
 *
 * @author Ismael
 */

public class AGE {
    
    private int tam;
    private ArrayList<ArrayList<Long>> matFlujo;
    private ArrayList<ArrayList<Long>> matDistancia;


    private ArrayList<ArrayList<Integer>> poblacion;
    private ArrayList<Long> costePoblacion;
    private ArrayList<Integer> padre1;
    private ArrayList<Integer> padre2;
    private ArrayList<Integer> hijo1;
    private ArrayList<Integer> hijo2;
    private float probabilidadMutacionCruce;
    private int evaluaciones;
    
    public AGE(int tam_, ArrayList<ArrayList<Long> > matFlujo_, ArrayList<ArrayList<Long> > matDistancia_){
        
        
        tam = tam_;
        matFlujo = matFlujo_;
        matDistancia = matDistancia_;
        poblacion(TAMPOBLACION, ArrayList<Integer>(tam));
        costePoblacion(TAMPOBLACION, 0);
        for(int i = 0; i< tam; i++){
            padre1.add(0);
            padre2.add(0);
            hijo1.add(0);
            hijo2.add(0);
        }
        
        evaluaciones = 0;
        probabilidadMutacionCruce(PROBMUTACIONCRUCE * tam);
        
        
    }
    
    public void inicializaPoblacion(int tam) {
        poblacion = creaPoblacion(tam);
    }
    
    public void evaluarIndividuosPoblacion(ArrayList<ArrayList<Integer>> poblacion) {
        this.costePoblacion = calculaCostePoblacion(tam, matFlujo, matDistancia, poblacion);
    }
    
    
    public void torneo(ArrayList<Integer> individuos, ArrayList<Long> costesIndividuos) {

        int high;


        high = TAMPOBLACION - 1;

        for (int i = 0; i < individuos.size(); i++) {
            individuos.set(i, (int) (Math.random() * high + 1));
            costesIndividuos.set(i, costePoblacion.get(individuos.get(i)));
        }

        if (costesIndividuos.get(0) < costesIndividuos.get(1)){ // comparo el coste de los dos primeros individuos
            padre1.clear();
            padre1.addAll(poblacion.get(individuos.get(0))); // me quedo con el mejor de los dos (menor coste))
           
        }
        else
            padre1 = poblacion[individuos[1]];

        if (costesIndividuos[2] < costesIndividuos[3]) // comparo el coste de los dos siguiente individuos
            padre2 = poblacion[individuos[2]]; // me quedo con el mejor de los dos
        else
            padre2 = poblacion[individuos[3]];

    }
}
