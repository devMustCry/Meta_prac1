/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1meta;

import java.util.ArrayList;
import java.util.Vector;


/**
 *
 * @author Ismael
 */
public class Utils {
    
    public static long calculoCoste(int tam, ArrayList<ArrayList<Long>> matrizF, ArrayList<ArrayList<Long>> matrizD, 
            ArrayList<Integer> permutOptima){
        //Realizo el calculo del coste    
        long coste = 0;

        for(int i = 0; i < tam; i++){
            for(int j = 0; j < tam; j++){
                //Evitar sumar la diagonal principal (osea el punto (0,0), (1,1), (2,2), (3,3)
                if(i != j){
                    coste = coste + (matrizF.get(i).get(j) * matrizD.get(permutOptima.get(i)).get(permutOptima.get(j)));
                }
            }
        }
        return coste;
    }
    
    public static long factorizacion(int a, int b, int tam, Long costeActual, ArrayList<ArrayList<Long>> matrizF,
                                                                    ArrayList<ArrayList<Long>> matrizD,
                                                                    ArrayList<Integer> permutOptima){
        
        Long result = 0L;
        
        for(int i = 0; i < tam; i++ ){
            if(i != a && i != b){
                result += matrizF.get(a).get(i) * (matrizD.get(permutOptima.get(b)).get(permutOptima.get(i))-matrizD.get(permutOptima.get(a)).get(permutOptima.get(i)))+
                               matrizF.get(b).get(i) * (matrizD.get(permutOptima.get(a)).get(permutOptima.get(i))-matrizD.get(permutOptima.get(b)).get(permutOptima.get(i)))+
                               matrizF.get(i).get(a) * (matrizD.get(permutOptima.get(i)).get(permutOptima.get(b))-matrizD.get(permutOptima.get(i)).get(permutOptima.get(a)))+
                               matrizF.get(i).get(b) * (matrizD.get(permutOptima.get(i)).get(permutOptima.get(a))-matrizD.get(permutOptima.get(i)).get(permutOptima.get(b)));
            }
        }    
        
        return result;
    }
    
    public static void generaPermutOptimaAleatoria(int tam, ArrayList<Integer> permutOptima, int semilla){
        //https://stackoverflow.com/questions/196017/unique-non-repeating-random-numbers-in-o1

        semilla = (int) Math.random()*1000;

        for(int i = 0; i < tam; i++){
            permutOptima.set(i, i);
        }

        //Los mezclamos para que salgan diferentes cada vez
        for(int j = tam - 1; j > 0; j--) {
            int idx = (int)(Math.random() % (j + 1));
            int aux = permutOptima.get(idx);
            permutOptima.set(idx, permutOptima.get(j));
            permutOptima.set(j, aux);        
        }
   
    }
    
}
