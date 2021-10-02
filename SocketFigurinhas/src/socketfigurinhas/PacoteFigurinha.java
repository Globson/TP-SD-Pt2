/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfigurinhas;
import java.util.Random;
import java.io.Serializable;
/**
 *
 * @author grobs
 */
public class PacoteFigurinha implements Serializable {
    private int Pacote[];
    public PacoteFigurinha(){
        this.Pacote = new int[4];
        Random Rand = new Random();
        for (int i=0;i<4;i++){
            this.Pacote[i]=Rand.nextInt(151);
            //System.out.println("Fig: "+ Pacote[i]);
        }
    }
    public int [] GetFigurinhas(){
        return this.Pacote;
    }
}
