/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1meta;

import java.util.ArrayList;

/**
 *
 * @author Ismael
 */
public class Greedy {
    
   
    public void calcularPotencialesFlujoYDistancia(int tam, ArrayList<Long> vecPotFlujo,
        ArrayList<Long> vecPotDistancia,
        ArrayList<ArrayList<Long>> matrizF,
        ArrayList<ArrayList<Long>> matrizD) {
    
        Long sumFlujo, sumDistancia = 0L;

//        for (int i = 0; i < tam; i++) {
//            sumFlujo = 0L;
//            sumDistancia = 0L;
//            for (int j = 0; j < tam; j++) {
//                if (i != j) {
//                    sumFlujo += matrizF.get(i).get(j);
//                    sumDistancia += matrizD.get(i).get(j);
//                }
//            }
//            vecPotFlujo.add(i,sumFlujo);
//            vecPotDistancia.add(i, sumDistancia);
//        }
            
        for (int i = 0; i < matrizF.size(); i++) {
            sumFlujo = 0L;
            for (int j = 0; j < matrizF.get(i).size(); j++) {
                if (i != j) {
                    sumFlujo += matrizF.get(i).get(j);
                }
            }
            vecPotFlujo.add(i,sumFlujo);
        }
        
        
        for (int i = 0; i < matrizD.size(); i++) {
            sumDistancia = 0L;
            for (int j = 0; j < matrizD.get(i).size(); j++) {
                if (i != j) {
                    sumDistancia += matrizD.get(i).get(j);
                }
            }
            vecPotDistancia.add(i, sumDistancia);
        }
        
    }
    
    public void algoritmoGreedy(int tam, ArrayList<ArrayList<Long>> matFlujo,
        ArrayList<ArrayList<Long>> matDistancia,
        ArrayList<Integer> permutOptima) {
    
        //Declaro los dos ArrayListes para calcular el potencial
        ArrayList<Long> vecPotFlujo = new ArrayList();
        ArrayList<Long> vecPotDistancia = new ArrayList();
        //ArrayList de booleanos para marcaje
        ArrayList<Boolean> banderasFluj = new ArrayList();
        ArrayList<Boolean> banderasDist = new ArrayList();
        Long mayor = -100L;
        Long menor = 99999999L;
        int posDis = 0;
        int posFlu = 0;
        
        
        for(int i = 0; i < tam; i++){
            banderasFluj.add(false);
            banderasDist.add(false);
        }

        //Calculamos los potenciales de cada uno
        calcularPotencialesFlujoYDistancia(tam, vecPotFlujo, vecPotDistancia, matFlujo, matDistancia);  
        
            
        for (int i = 0; i < vecPotDistancia.size(); i++) {
            System.out.println(vecPotDistancia.get(i));
        }
        
        
        // Recorro n veces los dos ArrayListes a la vez para ir comprobando en cada
        // vuelta quien es el mayor y el menor para así poder calcular la 
        // permutacion optima    
        for (int j = 0; j < tam; j++) {
            menor = 999999999L;
            mayor = -100L;
            //Recorro para obtener el menor y el mayor de ambos ArrayListes
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

            banderasFluj.add(posFlu, true);
            banderasDist.add(posDis, true);
            //situo en la posiciones
            permutOptima.add(posFlu, posDis); //Selecciono la unidad Di y la coloco en Fi 
        }
        
        
        
//        for (int i = 0; i < vecPotFlujo.size(); i++) {
//            menor = 999999999L;
//            mayor = -100L;
//            for (int j = 0; j < vecPotFlujo.size(); j++) {
//                if (vecPotFlujo.get(j) >= mayor && banderasFluj.get(j) != true) {
//                    mayor = vecPotFlujo.get(j);
//                    posFlu = j; //Almaceno la posicion del mayor flujo
//                }
//            }
//            
//            banderasFluj.add(posFlu, true);
//        }
//        
//        for (int i = 0; i < vecPotDistancia.size(); i++) {
//            menor = 999999999L;
//            mayor = -100L;
//            for (int j = 0; j < vecPotDistancia.size(); j++) {
//                //Obtengo el menor de las distancias
//                if (vecPotDistancia.get(j) <= menor && banderasDist.get(j) != true) {
//                    menor = vecPotDistancia.get(j);
//                    posDis = j; //Almaceno la posicion de la menor distancia
//                }
//            }
//            
//            banderasDist.add(posFlu, true);
//        }


    }
    
}
