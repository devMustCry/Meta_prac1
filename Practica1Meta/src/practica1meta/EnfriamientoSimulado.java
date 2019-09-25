/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1meta;

import java.util.ArrayList;
import static practica1meta.MetodosVarios.factorizacion;
import static practica1meta.Utils.calculoCoste;

/**
 *
 * @author Ismael
 */
public class EnfriamientoSimulado {
    
    public void enfriamientoSimulado(int tam, ArrayList<ArrayList<Long>> matFlujo,
        ArrayList<ArrayList<Long>> matDistancia,
        ArrayList<Integer> permutOptima, String tipo) {

        //Variables Enfriamiento simulado
        double tempInicial;
        double tempFinal;
        Long mejorCoste = 0L;
        Long difCostes = 0L;

        //Declaro el costo actual
        Long costeActual = 0L;
        Long costeFactorizado = 0L;
        int evaluaciones = 0;

        //ArrayList de enteros para controlar el Don't Look Bits inicializado a 0
        ArrayList<Integer> dlb = new ArrayList();

        //Calculamos el costo
        costeActual = Utils.calculoCoste(tam, matFlujo, matDistancia, permutOptima);
        mejorCoste = costeActual;

        //Inicializo las temperaturas
        tempInicial = 1.5f * costeActual;
        tempFinal = tempInicial;

        //bandera de mejora
        boolean mejoraGlobal = false;

        //Realizo las comprobaciones oportunas para ver si se sale o si sigue
        do {
            for (int i = 0; i < tam; i++) {

                if (dlb.get(i) == 0) {
                    mejoraGlobal = false;

                    for (int j = 0; j < tam; j++) {
                        if (i != j) {
                            //Calculo el nuevo coste intercambiando dos posiciones
                            costeFactorizado = Utils.factorizacion(i, j, tam, costeActual, matFlujo, matDistancia, permutOptima);

                            //Calculo el diferencial de costes(si es negativo es porque mejora)
                            difCostes = costeFactorizado - costeActual;

                            //Si costeFactorizado mejora o se cumple el criterio de aceptacion
                            if ((difCostes < 0) || (RandomFloat(0, 1) <= Math.exp(-difCostes / (tempFinal)))) {
                                int aux = permutOptima.get(i);
                                permutOptima.set(i, permutOptima.get(j));
                                permutOptima.set(j, aux);
                                
                                dlb.set(i,0);
                                dlb.set(j,0);
                                mejoraGlobal = true;
                                costeActual = costeFactorizado;

                                if (costeActual < mejorCoste) {
                                    mejorCoste = costeActual;
                                }
                            }
                        }
                    }
                }

                //Si no mejora le ponemos un 1 a este vecino
                if (mejoraGlobal == false) {
                    dlb.set(i,1);
                }

                //Si hemos escogido el metodo geometrico
                //https://sci2s.ugr.es/sites/default/files/files/Teaching/GraduatesCourses/Metaheuristicas/Tema05-Metodos%20basados%20en%20trayectorias-17-18.pdf
                if (tipo.equals("geometrico")) {
                    tempFinal *= (0.9); //Utilizamos como Alfa = 0.9
                }

                //Si hemos escogido el metodo de Boltzmann
                if (tipo.equals("boltzmann")) {
                    tempFinal = (tempInicial / (1 + Math.log(evaluaciones)));
                }

                evaluaciones++;
            }
        } while (evaluaciones < 50000 && mejoraGlobal == true && tempFinal == (tempInicial * 0.05));
    }
    
    float RandomFloat(float a, float b) {
        float random = ((float)  Math.random()) / 32767;
        float diff = b - a;
        float r = random * diff;
        return a + r;
    }
    
}
