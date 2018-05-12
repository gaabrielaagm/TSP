package tsp;

import java.util.ArrayList;

public class Ruta {
    public ArrayList ruta;
    public int peso;
    
    public Ruta(){
        ruta = new ArrayList();
        peso = 0;
    }   
    
    public void calcu_peso(){
        ArrayList rc = new ArrayList();
        for(int i=0; i < this.ruta.size(); i++){
            int num_cd = 0;
            for(int j=0; j < 5; j++){
                if(this.ruta.get(i).equals(TSP.ids.get(j))){
                    num_cd = j;
                    break;
                }
            }
            rc.add(num_cd);
        }
        int x = 1;
        int pesototal = 0;
        while(x < 5){
            pesototal = pesototal + TSP.matrizDistancias[(int)rc.get(x-1)][(int)rc.get(x)];
            x++;
        }
        this.peso = pesototal;
        //*System.out.println("Ciudad: " + rc.get(0) + " ruta:" + this.ruta.toString() + " peso: " + this.peso);
        
        //return pesototal;
        /*
        for(int i=0; i < this.ruta.size(); i++){
            int num_cd = 0;
            for(int j=0; j < 5; j++){
                if(this.ruta.get(j).equals(TSP.ids.get(j))){
                    num_cd = j;
                }
            }
            //System.out.println("Ciudad: " + cd + " id:" + ruta.get(i) + " index del id: " +  TSP.ids.indexOf(this.ruta.get(i)));
            //int r = TSP.matrizDistancias[cd][i];
        }
        */
    }
}
