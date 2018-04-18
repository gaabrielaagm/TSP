
package tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TSP2 {
    public static int matrizDistancias [][] = {{8,7,4,3,20}, {6,10,16,22,9}, {4,1,13,11,18}, {3,21,2,30,7}, {14,15,29,26,27}};
    public static ArrayList<String> bits = new ArrayList<>(Arrays.asList("000", "001", "010", "011", "100", "101", "110", "111"));
    public static ArrayList ids = new ArrayList();
    
    public static void main(String[] args){
        // GENERAR IDENTIFICADORES
        for(int i=0;i<5;i++){
            Random rand = new Random();
            int  n = rand.nextInt(bits.size());
            String id = bits.get(n);
            System.out.println("CD: " + (i+1) + " Num: "+ bits.get(n));
            ids.add(id);
            bits.remove(n);           
        }
        
        //Inicializar matriz de rutas finales
        Ruta[][] rutas = new Ruta[5][5];
        for(int n=0; n < 5; n++){ //n=tiendas
            for(int m=0; m<5;m++){ //m=ruta
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
                for(int j=0; j<5; j++){
                    Random rand = new Random();
                    int  n = rand.nextInt(ids2.size());
                    ruta_gen.add(ids2.get(n));
                    ids2.remove(n);
                }
            //}
            
            // Ver en que tienda empezó
            // Ver el primer cromosoma de la ruta generada
            int num_tienda=0;
            for(int k=0; k<5; k++){
                if(ids.get(k).equals(ruta_gen.get(0))){
                    num_tienda = k;
                }
            }
            
            // Tratar de llenar las primeras 5 rutas que se generen
            boolean inserto = false;
            for(int m=0; m < 5; m++){
                if(rutas[num_tienda][m].ruta.isEmpty()){
                    //agrega la ruta_gen a la matriz de rutas en su atributo ruta de la posición vacía(copia ruta_gen a esa posicion) 
                    for(int n=0; n < 5; n++){ 
                        rutas[num_tienda][m].ruta.add(ruta_gen.get(n));
                    }
                    rutas[num_tienda][m].calcu_peso();
                    inserto = true;
                }
            }      
            
            //Ver si existe (repitio) o no
            boolean existe = false;
            if(inserto == false){
                for(int p=0; p < 5; p++){
                    int q = 0;
                    while(q < 5){
                        if(rutas[num_tienda][p].ruta.get(q).equals(ruta_gen.get(q)) == false){
                            break;
                        } 
                        q++;
                    }      
                    if(q == 5){
                        //Mutación ISSUE#1:@CITLALY
                        existe = true;
                    }
                }
            }
            
            // Mutar (generar una nueva ruta)
            if(existe){
                System.out.println("Ruta anterior: " + ruta_gen.toString());
                Random rand = new Random();
                int  n1 = rand.nextInt(bits.size()); 
                int  n2 = rand.nextInt(bits.size()); 
                while(n2 == n1){
                    n2 = rand.nextInt(bits.size()); 
                }
                String aux = (String)ruta_gen.get(n1);
                ruta_gen.set(n1, ruta_gen.get(n2));
                ruta_gen.set(n2, aux);             
                System.out.println("Ruta mutada: " + ruta_gen.toString());
            }
            
            // Comparar pesos para ver si sustituye a una ruta introducida
            if(existe == false){
                int peso_ruta_gen = calcu_peso(ruta_gen);
                
                // Saca el peso mayor
                int peso_mayor = rutas[num_tienda][0].peso;
                int pos = 0;
                for(int r=0; r < 5; r++){
                    if(peso_mayor < rutas[num_tienda][r].peso){
                        peso_mayor = rutas[num_tienda][r].peso;
                        pos = r;
                    }
                }
                
                //Compara si lo puede sustituir
                if(peso_ruta_gen < peso_mayor){
                    rutas[num_tienda][pos].ruta.clear();
                    for(int s=0; s < 5; s++){ 
                        rutas[num_tienda][pos].ruta.add(ruta_gen.get(s));
                    }
                    //rutas[num_tienda][pos].calcu_peso();
                    System.out.print("Peso metodo: " + rutas[num_tienda][pos].peso + " Peso mayor: " + peso_mayor);
                    inserto = true;
                } 
            }            
            
            // *************CHECALE
            // checar si sirve
            // checar bandera inserto
            // checar si cuando muto i++;
            // acabar el if
            i++;
        }
    }
    
    public static int calcu_peso(ArrayList ruta){
        ArrayList rc = new ArrayList();
        for(int i=0; i < ruta.size(); i++){
            int num_cd = 0;
            for(int j=0; j < 5; j++){
                if(ruta.get(i).equals(TSP2.ids.get(j))){
                    num_cd = j;
                    break;
                }
            }
            rc.add(num_cd);
        }
        int x = 1;
        int pesototal = 0;
        while(x < 5){
            pesototal = pesototal + TSP2.matrizDistancias[(int)rc.get(x-1)][(int)rc.get(x)];
            x++;
        }
        System.out.println("Ciudad: " + rc.get(0) + " ruta:" + ruta.toString() + " peso: " + pesototal);
        return pesototal;
    }
}
