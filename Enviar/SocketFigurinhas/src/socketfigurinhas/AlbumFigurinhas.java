/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfigurinhas;
import java.io.Serializable;
/**
 *
 * @author grobs
 */
public class AlbumFigurinhas implements Serializable {
    private int matriz[];
    public AlbumFigurinhas(){
        this.matriz = new int[151];
        for (int i=0;i<151;i++){
            this.matriz[i]=0;
        }      
    }
    
    public boolean AdicionaFigurinha(int figurinha){
        if(this.matriz[figurinha]==1){
            return false;
        }else{
            this.matriz[figurinha]=1;
            return true;    
        }
    }
    
    public int GetStatusFigurinha(int figurinha){
        return this.matriz[figurinha];
    }
    
    public void Printa(){
        for(int i=0;i<151;i++){
            System.out.print(matriz[i]+" ");
        }
        System.out.println();
    }
}
