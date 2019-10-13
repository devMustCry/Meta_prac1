/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta_prac2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Ismael
 */
public class Utils {
    
    public static int TAMPOBLACION = 50;
    public static int MAXNUMEVALUACIONES = 50000;
    public static int PROBCRUCEESTACIONARIO = 1;
    public static float PROBCRUCEGENERACIONAL = 0.7f;
    public static float PROBMUTACIONCRUCE = 0.001f;
    
    
    
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
    
    
    public static void generaPermutacionAleatoria(int tamanio, ArrayList<Integer> permutacionOptima) {
        int high = tamanio;
        int i, j;

        i = 0;
        while (i < tamanio) {
            permutacionOptima.set(i, i);
            i++;
        }

        j = tamanio - 1;

        while (j > 0) {
            int index = (int) (Math.random()*high);
            Collections.swap(permutacionOptima, index, j);
            j--;
        }
    }
    
    
    
    public static void cruceOX(int tamanio, ArrayList<Integer> p1, ArrayList<Integer> p2, ArrayList<Integer> h1, ArrayList<Integer> h2) {

        ArrayList<Boolean> marcajePadre = new ArrayList<>();

        for(int i=0; i<tamanio; i++){
            marcajePadre.add(false);
        }
        int totalCopiadosSegmento = 0;
        int totalAlelosRestantes = 0;

        // ArrayListes para almacenar los alelos restantes de los dos padres
        ArrayList<Long> alelosRestantesP1 = new ArrayList<>();
        ArrayList<Long> alelosRestantesP2 = new ArrayList<>();

        // ArrayListes para almacenar los alelos restantes de los padres ordenados
        ArrayList<Long> alelosRestantesP1Ord = new ArrayList<>();
        ArrayList<Long> alelosRestantesP2Ord = new ArrayList<>();

        for(int i =0; i<totalAlelosRestantes; i++){
            alelosRestantesP1.add((long)0);
            alelosRestantesP2.add((long)0);
            alelosRestantesP1Ord.add((long)0);
            alelosRestantesP2Ord.add((long)0);
        }

        /*long posicion1 = (int)Math.random() * (1-(tamanio-1)) + (tamanio-1);
        long posicion2 = (int)Math.random() * (0 - ((tamanio - posicion1) + posicion1)) + ((tamanio - posicion1) + posicion1);*/
        
        int posicion1 = (int) (Math.random()*tamanio);
        int posicion2 = (int) (Math.random()*tamanio - posicion1) + posicion1;
        
        long posicionAlelo = 0;

        // Añadimos primero los genes del segmento marcodo de los padres a los hijos
        // y los marcamos como copiados
        for (int i = (int)posicion1; i < posicion2; i++) {
            h1.set(i, p1.get(i));
            h2.set(i, p2.get(i));
            marcajePadre.set(i, true);
            totalCopiadosSegmento++;
        }

        totalAlelosRestantes = tamanio - totalCopiadosSegmento;

        // Rellenamos los ArrayListes de alelos restantes con el resto de alelos del padre
        // que no forman parte del segmento marcado
        int index = 0;
        while (index < tamanio) {
            if (!(marcajePadre.get(index))) {
                alelosRestantesP1.add((long)p1.get(index));
                alelosRestantesP2.add((long)p2.get(index));
            }
            index++;
        }

        // En cada iteracion recorro los alelos restantes de cada padre para
        // ir odenandolos respecto al otro padre
        for (int i = (int)posicion2; i < tamanio; i++) {
            for (int j = 0; j < totalAlelosRestantes; j++) {
                if ((int)p2.get(i) == alelosRestantesP1.get(j)) {
                    alelosRestantesP1Ord.add(alelosRestantesP1.get(j));
                }
                if ((int)p1.get(i) == alelosRestantesP2.get(j)) {
                    alelosRestantesP2Ord.add(alelosRestantesP2.get(j));
                }
            }
        }

        for (int i = 0; i < posicion2; i++) {
            for (int j = 0; j < totalAlelosRestantes; j++) {
                if ((int)p2.get(j) == alelosRestantesP1.get(j)) {
                    alelosRestantesP1Ord.add(alelosRestantesP1.get(j));
                }
                if ((int)p1.get(i) == alelosRestantesP2.get(j)) {
                    alelosRestantesP2Ord.add(alelosRestantesP2.get(j));
                }
            }
        }


        // Copiamos en cada hijo los alelos restantes ordenados de los padres
        for (int i = (int)posicion2; i < tamanio; i++) {
            h1.set(i, alelosRestantesP1Ord.get((int)posicionAlelo).intValue());
            h2.set(i, alelosRestantesP2Ord.get((int)posicionAlelo).intValue());
            posicionAlelo++;
        }

        
        posicionAlelo = 0;
        
        for (int i = 0; i < posicion1; i++) {
            h1.set(i, alelosRestantesP1Ord.get((int)posicionAlelo).intValue());
            h2.set(i, alelosRestantesP2Ord.get((int)posicionAlelo).intValue());
            posicionAlelo++;
        }
    }

    public static void correspondencia(long tamanioSegmentos, ArrayList<Integer> segmentoP1, ArrayList<Integer> segmentoP2, int alelo) {

        int i = 0;
        while (i < tamanioSegmentos) {
            if (segmentoP1.get(i) == alelo) {
            
                alelo = segmentoP2.get(i);

                correspondencia(tamanioSegmentos, segmentoP1, segmentoP2, alelo);

            }
            
            i++;
            
        }
    }

    public static void calculoAlelosRestantes(int tamanio, long posicion1, long posicion2,
            ArrayList<Integer> segmentoP2, ArrayList<Integer> segmentoP1, ArrayList<Integer> alelosRestantesP,
            ArrayList<Integer> hijo, ArrayList<Boolean> hijoMarcado) {

        boolean padreMarcado = false;
        long tamanioSegmentos;
        long tamanioanoRestante;
        int alelo;


        tamanioSegmentos = tamanio - (tamanio - (posicion2 - posicion1 + 1));
        tamanioanoRestante = (tamanio - (posicion2 - posicion1 + 1));

        int a = 0;  // Para controlar el bucle while
        int b = 0;

        // En cada iteracion metemos los alelos restantes del padre 1 al hijo 1
        // comprobando si ya está previamente introducido
        for (int i = 0; i < tamanioanoRestante; i++) {
            for (int j = 0; j < tamanioSegmentos; j++) {
                if (segmentoP2.get(j) == alelosRestantesP.get(i)) {
                    padreMarcado = true;
                    break;
                } else {
                    padreMarcado = false;
                }
            }
            // Buscamos la primera posicion libre para introducir el primero
            // alelo en caso de que no haya sido copiado previamente
            if (!padreMarcado) {
                while (a < tamanio) {
                    if (!(hijoMarcado.get(a))) {
                        hijo.set(a, alelosRestantesP.get(i));
                        hijoMarcado.set(a, true);
                        break;
                    }
                    a++;
                }
            }
            // Si el alelo ya está en el hijo comprobamos correspondencia
            if (padreMarcado) {
                alelo = alelosRestantesP.get(i);
                correspondencia(tamanioSegmentos, segmentoP2, segmentoP1, alelo);
                alelosRestantesP.set(i, alelo);
                while (b < tamanio) {
                    if (!(hijoMarcado.get(b))) {
                        hijo.set(b, alelosRestantesP.get(i));
                        hijoMarcado.set(b, true);
                        break;
                    }
                    b++;
                }
            }
        }
    }

    public static void crucePMX(int tamanio, ArrayList<Integer> p1, ArrayList<Integer> p2, ArrayList<Integer> h1, ArrayList<Integer> h2) {

        ArrayList<Integer> alelosRestantesP1 = new ArrayList<>();
        ArrayList<Integer> alelosRestantesP2 = new ArrayList<>();
        ArrayList<Boolean> hijoMarcado1 = new ArrayList<>();
        ArrayList<Boolean> hijoMarcado2 = new ArrayList<>();
        
        
        for(int i=0; i < tamanio; i++){
            hijoMarcado1.add(false);
            hijoMarcado2.add(false);
        }
        
        /*long posicion1 = (int)Math.random() * (1-(tamanio-1)) + (tamanio-1);
        long posicion2 = (int)Math.random() * (0 - ((tamanio - posicion1) + posicion1)) + ((tamanio - posicion1) + posicion1);*/
        int posicion1 = (int) (Math.random()*tamanio);
        int posicion2 = (int) (Math.random()*tamanio - posicion1) + posicion1;

        long tamanioSegmentos = tamanio - (tamanio - (posicion2 - posicion1 + 1));

        ArrayList<Integer> segmentoP1 = new ArrayList<>();
        ArrayList<Integer> segmentoP2 = new ArrayList<>();
        
        for(int i =0; i<tamanioSegmentos; i++){
            segmentoP1.add(0);
            segmentoP2.add(0);
        }

        int j = 0;
        for (int i = (int)posicion1; i <= posicion2; i++) {
            segmentoP1.set(j, p1.get(i));
            segmentoP2.set(j, p2.get(i));
            j++;
        }

        for (int i = (int)posicion1; i <= posicion2; i++) {
            h2.set(i, p1.get(i));
            h1.set(i, p2.get(i));
            hijoMarcado1.set(i, true);
            hijoMarcado2.set(i, true);
        }

        int i = 0;
        while ( i < tamanio) {
            if (!(hijoMarcado1.get(i)) && !(hijoMarcado2.get(i))) {
                alelosRestantesP1.add(p1.get(i));
                alelosRestantesP2.add(p2.get(i));
            }
            i++;
        }

        // Copiamos en el hijo 1 los elementos restantes del padre 1
        calculoAlelosRestantes(tamanio, posicion1, posicion2, segmentoP2, segmentoP1, alelosRestantesP1, h1, hijoMarcado1);
        // Copiamos en el hijo 2 los alelos restantes del padre 2
        calculoAlelosRestantes(tamanio, posicion1, posicion2, segmentoP1, segmentoP2, alelosRestantesP2, h2, hijoMarcado2);

    }

    public static float RandomFloat(float a, float b) {
        
        float random = new Random().nextFloat();
        float diff = b - a;
        float r = random * diff;
        return a + r;
    }

    public static ArrayList<Long> calculaCostePoblacion(int tamanio, ArrayList<ArrayList<Long>> matrizFlujos,
                              ArrayList<ArrayList<Long>> matrizDistancias, ArrayList<ArrayList<Integer>> poblacion) {

        long coste = 0;
        ArrayList<Long> costePoblacion = new ArrayList<>();
        ArrayList<Integer> permutacionOptima = new ArrayList<>();
        
        
        for(int i =0; i < TAMPOBLACION; i++){
            costePoblacion.add(0L);
        }
        
        for(int i =0; i < tamanio; i++){
            permutacionOptima.add(0);
        }
        
        
        int i = 0;

        while (i < TAMPOBLACION) {
            permutacionOptima = poblacion.get(i);
            coste = calculoCoste(tamanio, matrizFlujos, matrizDistancias, permutacionOptima);
            costePoblacion.set(i, coste);
            i++;
        }

        return costePoblacion;
    }

    public static ArrayList<ArrayList<Integer>> creaPoblacion(int tamanio) {

        ArrayList<ArrayList<Integer>> poblacion = new ArrayList<>();
        ArrayList<Integer> individuo = new ArrayList<>();
        
        for(int i=0;i<TAMPOBLACION; i++){
            for(int j=0; j<tamanio; j++){
                poblacion.add(new ArrayList<>());
            }            
        }
        
        for(int i =0;i<tamanio; i++){
            individuo.add(0);
        }

        // Para la poblacion inicial generamos 50 individuos aleatorios
        int i = 0;
        while ( i < TAMPOBLACION) {
            generaPermutacionAleatoria(tamanio, individuo);
            poblacion.set(i, individuo);
            i++;
        }
        return poblacion;
    }
    
}
