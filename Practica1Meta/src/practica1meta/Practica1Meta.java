/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1meta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Timer;
import java.util.Vector;

/**
 *
 * @author Ismael
 */
public class Practica1Meta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int tama;
    ArrayList<ArrayList <Long>> matrizF = new ArrayList<ArrayList<Long>>();
    ArrayList<ArrayList <Long>> matrizD = new ArrayList<ArrayList<Long>>();
    ArrayList<Integer> permutOptima = new ArrayList<Integer>();
    int fila, columna;
    String nomFich = "data/cnf01.dat";
    int tamanio = 0;
    int semilla = 78161758;
    Timer tiempo;
    int opt, opt2, opt3, opt4;
    Long cost = 0L;
    
    
    // Fichero del que queremos leer
    File fichero = new File(nomFich);
    Scanner s = null;

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
    
    
    /*****************************************/
    /*greddy*/
    
//    for(int i=0;i<tamanio;i++){
//        permutOptima.add(0);
//    }
//    
//    Greedy greedy = new Greedy();
//    
//    greedy.algoritmoGreedy(tamanio, matrizF, matrizD, permutOptima);
//    
//    
//    System.out.println("La permutacion óptima es: ");
//    for (int i = 0; i < tamanio; i++) {
//        System.out.print(permutOptima.get(i) + 1 + " ");
//    }
//
//    System.out.println();
//
//    cost = Utils.calculoCoste(tamanio, matrizF, matrizD, permutOptima);
//    System.out.println("El coste del Greedy es: " + cost);
    
    
    
    /******BL*******/
//    for(int i=0;i<tamanio;i++){
//        permutOptima.add(0);
//    }
//    
//    //Genero una permutacon aleatoria dentro de un fichero .dat
//    Utils.generaPermutOptimaAleatoria(tamanio, permutOptima, semilla);
//
//    System.out.println("Permutación aleatoria a partir de semilla :");
//    for (int i = 0; i < tamanio; i++) {
//        System.out.println(permutOptima.get(i)+1);
//    }
//
//    BusquedaLocal bl = new BusquedaLocal();
//
//    //Calculamos el tiempo de ejecucion
////    tiempo.start();
//    bl.busquedaLocal(tamanio, matrizF, matrizD, permutOptima);
////    tiempo.stop();
//
//    //Imprimo la permutación óptima
//    System.out.println("La permutacion óptima es: ");
//    for (int i = 0; i < tamanio; i++) {
//        System.out.print(permutOptima.get(i) + 1 + " ");
//    }
//
//    cost = Utils.calculoCoste(tamanio, matrizF, matrizD, permutOptima);
//    System.out.println("El coste de BL es: " + cost);


    /***********ENFRIAMIENTO SIMULADO***********/
    
    
    /********************GEOMETRICO************************/
    //permutOptima.resize(tama, 0);

    //Genero una permutacon aleatoria dentro de un fichero .dat
    for(int i=0;i<tamanio;i++){
        permutOptima.add(0);
    }
    
    Utils.generaPermutOptimaAleatoria(tamanio, permutOptima, semilla);

    //Calculamos el tiempo de ejecucion
//    tiempo.start();
    EnfriamientoSimulado enfriamiento = new EnfriamientoSimulado();
    enfriamiento.enfriamientoSimulado(tamanio, matrizF, matrizD, permutOptima, "geometrico");
//    tiempo.stop();

    //Imprimo la permutación óptima
    System.out.println("La permutacion óptima es: ");
    for (int i = 0; i < tamanio; i++) {
        System.out.print(permutOptima.get(i) + 1 + " ");
    }

    cost = Utils.calculoCoste(tamanio, matrizF, matrizD, permutOptima);
    System.out.println("El coste de ENFRIAMIENTO GEOMETRICO es: " + cost);

    /*for(int h = 0; h < tamanio; h++){
        for(int a = 0; a < tamanio; a++){
            System.out.print(" " + matrizD.get(h).get(a));
        }
        System.out.println();
        
    }*/
    
    
    /*while((linea=file.readLine())!=null && linea.length()!=0){
        String[] asdf = linea.split(" ");
        char[] ch=linea.toCharArray();    
        
        for(int h = 0; h < asdf.length; h++){
            System.out.println("SEGUNDO VECTOR char:" + asdf[h]);
        }
    }*/
 
    //Cargamos los datos del fichero
    //cargaFichero(nomFich, tama, matrizF, matrizD);

    /********************************************************************/
    /******************************MENÚ *********************************/
    /********************************************************************/
    /*do {
        cout << "-------------------------------------" << endl;
        cout << "------- METAHEURÍSTICA 2018/19 ------" << endl;
        cout << "-------------------------------------" << endl;
        cout << "1. Seleccionar fichero" << endl;
        cout << "2. Seleccionar semilla" << endl;
        cout << "3. Mostrar Matrices" << endl;
        cout << "4. Greedy" << endl;
        cout << "5. Búsqueda Local" << endl;
        cout << "6. Enfriamiento Simulado" << endl;
        cout << "0. Salir" << endl;
        cin >> opt;
        switch (opt) {
            case 1:
            {
                do{
                cout << "-------------------------------------" << endl;
                cout << "-------- SELECIONE UN FICHERO -------" << endl;
                cout << "-------------------------------------" << endl;
                cout << "1. cnf01" << endl;
                cout << "2. cnf02" << endl;
                cout << "3. cnf03" << endl;
                cout << "4. cnf04" << endl;
                cout << "5. cnf05" << endl;
                cout << "6. cnf06" << endl;
                cout << "7. cnf07" << endl;
                cout << "8. cnf08" << endl;
                cout << "9. cnf09" << endl;
                cout << "10. cnf10" << endl;
                cin >> opt2;
                switch (opt2) {
                    case 1:
                    {
                        matrizF.resize(0);
                        matrizD.resize(0);
                        nomFich = "datos/cnf01.dat";
                        cargaFichero(nomFich, tama, matrizF, matrizD);
                        break;
                    }
                    case 2:
                    {
                        matrizF.resize(0);
                        matrizD.resize(0);
                        nomFich = "datos/cnf02.dat";
                        cargaFichero(nomFich, tama, matrizF, matrizD);
                        break;
                    }
                    case 3:
                    {
                        matrizF.resize(0);
                        matrizD.resize(0);
                        nomFich = "datos/cnf03.dat";
                        cargaFichero(nomFich, tama, matrizF, matrizD);
                        break;
                    }
                    case 4:
                    {
                        matrizF.resize(0);
                        matrizD.resize(0);
                        nomFich = "datos/cnf04.dat";
                        cargaFichero(nomFich, tama, matrizF, matrizD);
                        break;
                    }
                    case 5:
                    {
                        matrizF.resize(0);
                        matrizD.resize(0);
                        nomFich = "datos/cnf05.dat";
                        cargaFichero(nomFich, tama, matrizF, matrizD);
                        break;
                    }
                    case 6:
                    {
                        matrizF.resize(0);
                        matrizD.resize(0);
                        nomFich = "datos/cnf06.dat";
                        cargaFichero(nomFich, tama, matrizF, matrizD);
                        break;
                    }
                    case 7:
                    {
                        matrizF.resize(0);
                        matrizD.resize(0);
                        nomFich = "datos/cnf07.dat";
                        cargaFichero(nomFich, tama, matrizF, matrizD);
                        break;
                    }
                    case 8:
                    {
                        matrizF.resize(0);
                        matrizD.resize(0);
                        nomFich = "datos/cnf08.dat";
                        cargaFichero(nomFich, tama, matrizF, matrizD);
                        break;
                    }
                    case 9:
                    {
                        matrizF.resize(0);
                        matrizD.resize(0);
                        nomFich = "datos/cnf09.dat";
                        cargaFichero(nomFich, tama, matrizF, matrizD);
                        break;
                    }
                    case 10:
                    {
                        matrizF.resize(0);
                        matrizD.resize(0);
                        nomFich = "datos/cnf10.dat";
                        cargaFichero(nomFich, tama, matrizF, matrizD);
                        break;
                    }
                    default:
                    {
                        cout << "Opción no válida" << endl;
                        break;
                    }
                }
                }while(opt2 <1 || opt2 > 10);
                break;
            }
            case 2:
            {
                do{
                cout << "-------------------------------------" << endl;
                cout << "-------- SELECIONE UNA SEMILLA -------" << endl;
                cout << "-------------------------------------" << endl;
                cout << "1. 26497784" << endl;
                cout << "2. 64977842" << endl;
                cout << "3. 49778426" << endl;
                cout << "4. 97784264" << endl;
                cout << "5. 77842649" << endl;
                cin >> opt3;

                switch (opt3) {
                    case 1:
                    {
                        permutOptima.resize(tama, 0);
                        semilla = 26497784;
                        generaPermutOptimaAleatoria(tama, permutOptima, semilla);
                        break;
                    }
                    case 2:
                    {
                        permutOptima.resize(tama, 0);
                        semilla = 64977842;
                        generaPermutOptimaAleatoria(tama, permutOptima, semilla);
                        break;
                    }
                    case 3:
                    {
                        permutOptima.resize(tama, 0);
                        semilla = 49778426;
                        generaPermutOptimaAleatoria(tama, permutOptima, semilla);
                        break;
                    }
                    case 4:
                    {
                        permutOptima.resize(tama, 0);
                        semilla = 97784264;
                        generaPermutOptimaAleatoria(tama, permutOptima, semilla);
                        break;
                    }
                    case 5:
                    {
                        permutOptima.resize(tama, 0);
                        semilla = 77842649;
                        generaPermutOptimaAleatoria(tama, permutOptima, semilla);
                        break;
                    }
                    default:
                    {
                        cout << "Opcion no valida" << endl;
                        break;
                    }

                }
                }while(opt3 <1 || opt3 >5);
                break;
            }
            case 3:
            {
                if (nomFich == "") {
                    cout << "ANTES DEBES SELECCIONAR UN FICHERO " << endl;
                    break;
                }
                //VISUALIZACION DE LAS MATRICES
                cout << "TAMAÑO MATRICES " << tama << "*" << tama << endl << endl;
                cout << "MATRIZ FLUJO (Nº piezas que pasan entre unidades de montaje)" << endl;
                for (int i = 0; i < tama; i++) {
                    for (int j = 0; j < tama; j++) {
                        cout << matrizF[i][j] << " ";
                    }
                    cout << endl;
                }
                cout << endl;

                cout << "MATRIZ DISTANCIA (Distancia entre unidades de montaje)" << endl;
                for (int i = 0; i < tama; i++) {
                    for (int j = 0; j < tama; j++) {
                        cout << matrizD[i][j] << " ";
                    }
                    cout << endl;
                }
                cout << endl;

                break;
            }
            case 4:
            {
                if (nomFich == "") {
                    cout << "ANTES DEBES SELECCIONAR UN FICHERO " << endl;
                    break;
                }
                cout << "-------------------------------------" << endl;
                cout << "-------------- GREEDY ---------------" << endl;
                cout << "-------------------------------------" << endl;
                permutOptima.resize(tama, 0);
                cout << "FICHERO " << nomFich << endl;
                //Calculamos el tiempo de ejecucion
                tiempo.start();
                algoritmoGreedy(tama, matrizF, matrizD, permutOptima);
                tiempo.stop();


                //Imprimo la permutación óptima
                cout << endl;
                cout << "La permutacion óptima es: " << endl;
                for (int i = 0; i < tama; i++) {
                    cout << permutOptima[i] + 1 << " ";
                }

                cout << endl;

                cost = calculoCoste(tama, matrizF, matrizD, permutOptima);
                cout << "\nEl coste del Greedy es: " << cost << endl;
                cout << "\nTiempo de ejecución: " << tiempo.getElapsedTimeInMilliSec() << " ms" << endl;
                break;
            }
            case 5:
            {
                if (nomFich == "" && semilla == 0) {
                    cout << "ANTES DEBES SELECCIONAR UN FICHERO Y UNA SEMILLA" << endl;
                    break;
                }
                if (nomFich == "") {
                    cout << "ANTES DEBES SELECCIONAR UN FICHERO " << endl;
                    break;
                }
                if (semilla == 0) {
                    cout << "ANTES DEBES SELECCIONAR UNA SEMILLA " << endl;
                    break;
                }
                cout << "-------------------------------------" << endl;
                cout << "---------- BÚSQUEDA LOCAL -----------" << endl;
                cout << "-------------------------------------" << endl;
                cout << "FICHERO " << nomFich << endl;
                cout << "SEMILLA  " << semilla << endl;
                //permutOptima.resize(tama, 0);

                //Genero una permutacon aleatoria dentro de un fichero .dat
                //generaPermutOptimaAleatoria(tama, permutOptima);

                cout << "Permutación aleatoria a partir de semilla : " << endl;
                for (int i = 0; i < tama; i++) {
                    cout << permutOptima[i] + 1 << " ";
                }
                cout << endl;

                //Calculamos el tiempo de ejecucion
                tiempo.start();
                busquedaLocal(tama, matrizF, matrizD, permutOptima);
                tiempo.stop();

                //Imprimo la permutación óptima
                cout << endl;
                cout << "La permutación óptima es: " << endl;
                for (int i = 0; i < tama; i++) {
                    cout << permutOptima[i] + 1 << " ";
                }

                cost = calculoCoste(tama, matrizF, matrizD, permutOptima);
                cout << "\n\nEl coste de la Búsqueda Local es: " << cost << endl;
                cout << "\nTiempo de ejecución: " << tiempo.getElapsedTimeInMilliSec() << " ms" << endl;


                break;
            }
            case 6:
            {  
                if (nomFich == "" && semilla == 0) {
                    cout << "ANTES DEBES SELECCIONAR UN FICHERO Y UNA SEMILLA" << endl;
                    break;
                }
                if (nomFich == "") {
                    cout << "ANTES DEBES SELECCIONAR UN FICHERO " << endl;
                    break;
                }
                if (semilla == 0) {
                    cout << "ANTES DEBES SELECCIONAR UNA SEMILLA " << endl;
                    break;
                }
                cout << "-------------------------------------" << endl;
                cout << "------- ENFRIAMIENTO SIMULADO -------" << endl;
                cout << "-------------------------------------" << endl;

                do {
                    cout << "0. Volver" << endl;
                    cout << "1. Método Geométrico" << endl;
                    cout << "2. Boltzmann" << endl;
                    cin >> opt4;

                    switch (opt4) {
                        case 0:
                        {
                            break;
                        }
                        case 1:
                        {
                            cout << "-------------------------------------" << endl;
                            cout << "--------- MÉTODO GEOMÉTRICO ---------" << endl;
                            cout << "-------------------------------------" << endl;

                            //permutOptima.resize(tama, 0);

                            //Genero una permutacon aleatoria dentro de un fichero .dat
                            //generaPermutOptimaAleatoria(tama, permutOptima);
                            cout << "FICHERO " << nomFich << endl;
                            cout << "SEMILLA  " << semilla << endl;
                            cout << "Permutación aleatoria a partir de semilla : " << endl;
                            for (int i = 0; i < tama; i++) {
                                cout << permutOptima[i] + 1 << " ";
                            }
                            cout << endl;

                            //Calculamos el tiempo de ejecucion
                            tiempo.start();
                            enfriamientoSimulado(tama, matrizF, matrizD, permutOptima, "geometrico");
                            tiempo.stop();

                            //Imprimo la permutación óptima
                            cout << endl;
                            cout << "La permutación óptima es: " << endl;
                            for (int i = 0; i < tama; i++) {
                                cout << permutOptima[i] + 1 << " ";
                            }

                            cost = calculoCoste(tama, matrizF, matrizD, permutOptima);
                            cout << "\n\nEl coste del Enfriamiento Simulado con metodo geometrico es: " << cost << endl;
                            cout << "\nTiempo de ejecución: " << tiempo.getElapsedTimeInMilliSec() << " ms" << endl;

                            break;
                        }
                        case 2:
                        {
                            cout << "-------------------------------------" << endl;
                            cout << "---------- METODO BOLTZMANN ---------" << endl;
                            cout << "-------------------------------------" << endl;

                            //permutOptima.resize(tama, 0);

                            //Genero una permutacon aleatoria dentro de un fichero .dat
                            //generaPermutOptimaAleatoria(tama, permutOptima);
                            cout << "FICHERO " << nomFich << endl;
                            cout << "SEMILLA  " << semilla << endl;
                            cout << "Permutación aleatoria a partir de semilla : " << endl;
                            for (int i = 0; i < tama; i++) {
                                cout << permutOptima[i] + 1 << " ";
                            }
                            cout << endl;

                            //Calculamos el tiempo de ejecucion
                            tiempo.start();
                            enfriamientoSimulado(tama, matrizF, matrizD, permutOptima, "boltzmann");
                            tiempo.stop();

                            //Imprimo la permutación óptima
                            cout << endl;
                            cout << "La permutación óptima es: " << endl;
                            for (int i = 0; i < tama; i++) {
                                cout << permutOptima[i] + 1 << " ";
                            }

                            cost = calculoCoste(tama, matrizF, matrizD, permutOptima);
                            cout << "\n\nEl coste del Enfriamiento Simulado con metodo Boltzmann es: " << cost << endl;
                            cout << "\nTiempo de ejecución: " << tiempo.getElapsedTimeInMilliSec() << " ms" << endl;

                            break;
                        }
                        default:{
                            cout<<"Opcion no valida"<<endl;
                            break;
                        }
                    }
                } while (opt4 < 0 || opt4>2);
            }
            case 0:
            {
                break;
            }
            default:
            {
                cout << "Opción no válida" << endl;
                break;
            }
        }

    } while (opt != 0);*/

    }
    
}
