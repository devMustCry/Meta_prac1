/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1meta;

import java.util.Vector;

/**
 *
 * @author Ismael
 */
public class Greedy {
    
   
    public void calcularPotencialesFlujoYDistancia(int tam, Vector<Long> vecPotFlujo,
        Vector<Long> vecPotDistancia,
        Vector<Vector<Long>> matrizF,
        Vector<Vector<Long>> matrizD) {
    
        Long sumFlujo, sumDistancia = 0L;

        for (int i = 0; i < tam; i++) {
            sumFlujo = 0L;
            sumDistancia = 0L;
            for (int j = 0; j < tam; j++) {
                if (i != j) {
                    sumFlujo += matrizF.get(i).get(j);
                    sumDistancia += matrizD.get(i).get(j);
                }
            }
            vecPotFlujo.set(i,sumFlujo);
            vecPotDistancia.set(i, sumDistancia);
        }
    }
    
    public void algoritmoGreedy(int tam, Vector<Vector<Long>> matFlujo,
        Vector<Vector<Long>> matDistancia,
        Vector<Integer> permutOptima) {
    
        //Declaro los dos Vectores para calcular el potencial
        Vector<Long> vecPotFlujo = new Vector();
        Vector<Long> vecPotDistancia = new Vector();
        //Vector de booleanos para marcaje
        Vector<Boolean> banderasFluj = new Vector();
        Vector<Boolean> banderasDist = new Vector();
        Long mayor = -100L;
        Long menor = 99999999L;
        int posDis = 0;
        int posFlu = 0;

        //Calculamos los potenciales de cada uno
        calcularPotencialesFlujoYDistancia(tam, vecPotFlujo, vecPotDistancia, matFlujo, matDistancia);



        // Recorro n veces los dos Vectores a la vez para ir comprobando en cada
        // vuelta quien es el mayor y el menor para así poder calcular la 
        // permutacion optima    
        for (int j = 0; j < tam; j++) {
            menor = 999999999L;
            mayor = -100L;
            //Recorro para obtener el menor y el mayor de ambos Vectores
            for (int i = 0; i < tam; i++) {
                //Obtengo el mayor de los flujos
                if (vecPotFlujo.get(i) >= mayor && banderasFluj.get(i) != true) {
                    mayor = vecPotFlujo.get(i);
                    posFlu = i; //Almaceno la posicion del mayor flujo
                }

                //Obtengo el menor de las distancias
                if (vecPotDistancia.get(i) <= menor && banderasDist.get(i) != true) {
                    menor = vecPotDistancia.get(i);
                    posDis = i; //Almaceno la posicion de la menor distancia
                }
            }

            banderasFluj.set(posFlu, true);
            banderasDist.set(posDis, true);
            //situo en la posiciones
            permutOptima.set(posFlu, posDis); //Selecciono la unidad Di y la coloco en Fi 
        }


    }
    
}
