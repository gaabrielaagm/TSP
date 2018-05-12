
package tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TSP {
    public static int num_ciudades=10;
    public static int matrizDistancias [][] = {{8,7,4,3,20,15}, {6,10,16,22,9,16}, {4,1,13,11,18,17}, {3,21,2,30,7,18}, {14,15,29,26,27,19}, {4,5,19,16,5,22}};
    public static ArrayList<String> bits = new ArrayList<>(Arrays.asList("000", "001", "010", "011", "100", "101", "110", "111"));
    public static ArrayList ids = new ArrayList();
    public static Ruta[][] rutas = new Ruta[6][6];
    public static ArrayList<Ruta> mejoresRutas = new ArrayList<Ruta>();
    
     public static void main(String[] args){
        
         /*
        
        //Pedir el numero de ciudades
        System.out.println("Dime número de ciudades:");
        Scanner reader = new Scanner(System.in);
        num_ciudades = reader.nextInt();
        int matrizDist[][] = new int[num_ciudades][num_ciudades];
         
        //Generar distancias aleatorias
        for(int i=0; i<num_ciudades; i++){
            for(int j=0; j<num_ciudades; j++){
                matrizDist[i][j] = (int) (Math.random() * 50) + 1;
            }
        }
        
         */
        
         
        // Generar identificadores
        System.out.println("Identificadores (Bits):\n");
        for(int i=0;i<6;i++){
            Random rand = new Random();
            int  n = rand.nextInt(bits.size());
            String id = bits.get(n);
            System.out.println("CD: " + (i+1) + " Num: "+ bits.get(n));
            ids.add(id);
            bits.remove(n);           
        }
        
        //Inicializar matriz de rutas finales
        for(int n=0; n < 6; n++){ //n=ciudad
            for(int m=0; m<6;m++){ //m=ruta
                rutas[n][m] = new Ruta();
            }
        } 
        
        int i = 0;
        while(i < 100){
            // Genera nueva ruta aleatoria
            //if(){
                ArrayList ids2 = new ArrayList();
                ids2 = (ArrayList) ids.clone();
                ArrayList ruta_gen = new ArrayList();
                for(int j=0; j<6; j++){
                    Random rand = new Random();
                    int  n = rand.nextInt(ids2.size());
                    ruta_gen.add(ids2.get(n));
                    ids2.remove(n);
                }
            //}
            
            // Ver en que ciudad empezó
            // Ver el primer cromosoma de la ruta generada
            int num_cd=0;
            for(int k=0; k<6; k++){
                if(ids.get(k).equals(ruta_gen.get(0))){
                    num_cd = k;
                }
            }
            //System.out.println("CROMOSOMA 1: " + ruta_gen.get(0) + " CIUDAD: " + (num_cd+1) );
            
            // Tratar de llenar las primeras 6 rutas que se generen
            boolean inserto = false;
            for(int m=0; m < 6; m++){
                if(rutas[num_cd][m].ruta.isEmpty() && inserto == false){
                    //agrega la ruta_gen a la matriz de rutas en su atributo ruta de la posición vacía(copia ruta_gen a esa posicion) 
                    for(int n=0; n < 6; n++){ 
                        rutas[num_cd][m].ruta.add(ruta_gen.get(n));
                    }
                    //System.out.println("Size=" + rutas[num_cd][m].ruta.size());
                    rutas[num_cd][m].calcu_peso();
                    //int peso = calcu_peso(rutas[num_cd][m].ruta);
                    //rutas[num_cd][m].peso = peso;
                    inserto = true;
                }
            }
            
            // Ver si existe (repitio) o no
            boolean existe = false;
            if(inserto == false){
                for(int p=0; p < 6; p++){
                    int q = 0;
                    while(q < 6){
                        if(rutas[num_cd][p].ruta.get(q).equals(ruta_gen.get(q)) == false){
                            break;
                        } 
                        q++;
                    }      
                    if(q == 6){
                        //Mutación ISSUE#1:@CITLALY
                        existe = true;
                    }
                }
            }
            
            //**System.out.println("RUTA: " + ruta_gen.toString());
            //**System.out.println("La ruta existe = " + existe);
            
            // Mutar (generar una nueva ruta)
            if(existe){
                //**System.out.println("Ruta anterior: " + ruta_gen.toString());
                Random rand = new Random();
                int  n1 = rand.nextInt(6); 
                int n2;
                if(n1 >= 5){
                    n2 = n1-1;
                }else{
                    n2 = n1+1;
                }
                /*int  n2 = rand.nextInt(bits.size()); 
                while(n2 == n1){
                    n2 = rand.nextInt(bits.size()); 
                }
                */
                String aux = (String)ruta_gen.get(n1);
                ruta_gen.set(n1, ruta_gen.get(n2));
                ruta_gen.set(n2, aux);             
                //**System.out.println("Ruta mutada: " + ruta_gen.toString());
            }
            
            // Comparar pesos para ver si sustituye a una ruta introducida
            if(existe == false){
                int peso_ruta_gen = calcu_peso(ruta_gen);
                
                // Saca el peso mayor
                int peso_mayor = rutas[num_cd][0].peso;
                int pos = 0;
                for(int r=0; r < 6; r++){
                    if(peso_mayor < rutas[num_cd][r].peso){
                        peso_mayor = rutas[num_cd][r].peso;
                        pos = r;
                    }
                }
                
                //Compara si lo puede sustituir
                if(peso_ruta_gen < peso_mayor){
                    rutas[num_cd][pos].ruta.clear();
                    for(int s=0; s < 6; s++){ 
                        rutas[num_cd][pos].ruta.add(ruta_gen.get(s));
                    }
                    //rutas[num_tienda][pos].calcu_peso();
                    //*System.out.println("Peso metodo: " + rutas[num_cd][pos].peso + " Peso mayor: " + peso_mayor);
                    inserto = true;
                } 
            }
            
            i++;
        }        
        
        //Obtener la mejor ruta de las cinco
        for(int u=0; u<6; u++){
            Ruta mejor = new Ruta();
            //int peso = 0;
            int pos = 0;
            int menorPeso = 1000;
            for(int v=0; v<6; v++){
                if(rutas[u][v].peso < menorPeso){
                    menorPeso = rutas[u][v].peso;
                    pos = v;
                }
            }
            //Llenamos vectores de mejores rutas
            mejor.peso = menorPeso;
            //vaciar la mejor ruta
            for(int w=0; w<6; w++){
                mejor.ruta.add(rutas[u][pos].ruta.get(w));
            }    
            
            mejoresRutas.add(mejor);
        }
        
        //*System.out.println("Numero de mejores ciudades: " + mejoresRutas.size());
        
        imprimirMatrizDistancias();
        
        imprimirMatrizRutas();
        
        imprimirMejoresRutas();
    }
     
    public static int calcu_peso(ArrayList ruta){
        ArrayList rc = new ArrayList();
        for(int i=0; i < ruta.size(); i++){
            int num_cd = 0;
            for(int j=0; j < 6; j++){
                if(ruta.get(i).equals(ids.get(j))){
                    num_cd = j;
                    break;
                }
            }
            rc.add(num_cd);
        }
        int x = 1;
        int pesototal = 0;
        while(x < 6){
            pesototal = pesototal + matrizDistancias[(int)rc.get(x-1)][(int)rc.get(x)];
            x++;
        }
        //**System.out.println("Ciudad: " + rc.get(0) + " ruta:" + ruta.toString() + " peso: " + pesototal);
        return pesototal;
    }
    
    public static void imprimirMatrizRutas(){
        System.out.println("\n\nRutas generadas");
        for(int a=0; a < 6; a++){
            for(int b=0; b < 6; b++){
                System.out.println("#CD: " + (a+1) + " #RUTA: " + (b+1) + " RUTA: " + rutas[a][b].ruta.toString() + " PESO: " + rutas[a][b].peso);
                        
            }
        }
    }
    
    public static void imprimirMejoresRutas(){
        System.out.println("\n\nMejores rutas:\n");
        for(int a=0; a < 6; a++){
            System.out.println("#CD: " + (a+1) + " MEJOR RUTA: " + mejoresRutas.get(a).ruta + " PESO: " + mejoresRutas.get(a).peso);
        }
    }
    
    public static void imprimirMatrizDistancias(){
        System.out.println("\n\nMatriz de distancias:\n");
        for(int a=0; a < 6; a++){
            for(int b=0; b < 6; b++){
                if(matrizDistancias[a][b]>9){
                    System.out.print("["+matrizDistancias[a][b]+"] ");
                }else{
                    System.out.print("[0"+matrizDistancias[a][b]+"] ");
                }
                        
            }
            System.out.print("\n");
        }        
    }
}
