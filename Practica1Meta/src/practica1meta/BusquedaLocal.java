/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1meta;

import java.util.Vector;
import static practica1meta.MetodosVarios.calculoCoste;
import static practica1meta.MetodosVarios.factorizacion;


/**
 *
 * @author Ismael
 */
public class BusquedaLocal {
    
    
    public void busquedaLocal(int tam, Vector<Vector<Long>> matFlujo,
        Vector<Vector<Long>> matDistancia,
        Vector<Integer> permutOptima) {

        //Declaro el costo actual
        Long costeActual = 0L;
        Long costeFactorizado = 0L;
        int evaluaciones = 0;

        //Vector de enteros para controlar el Don't Look Bits inicializado a 0
        Vector<Integer> dlb = new Vector();;

        //Calculamos el costo
        costeActual = calculoCoste(tam, matFlujo, matDistancia, permutOptima);

        //bandera de mejora
        boolean mejoraGlobal = false;

        do {

            for (int i = 0; i < tam; i++) {
                if (dlb.get(i) == 0) {
                    mejoraGlobal = false;

                    for (int j = 0; j < tam; j++) {
                        if (i != j) {
                            costeFactorizado = factorizacion(i, j, tam, costeActual, matFlujo, matDistancia, permutOptima);

                            if (costeFactorizado < costeActual) {
                                int aux = permutOptima.get(i);
                                permutOptima.set(i,permutOptima.get(j));
                                permutOptima.set(j,aux);
                                
                                dlb.set(i,0);
                                dlb.set(j,0);
                                mejoraGlobal = true;
                            }
                        }
                    }
                }

                //Si no mejora le ponemos un 1 a este vecino
                if (mejoraGlobal == false) {
                    dlb.set(i,1);
                }
                evaluaciones++;
            }
        } while (evaluaciones < 50000 && mejoraGlobal == true);

    }
    
}
