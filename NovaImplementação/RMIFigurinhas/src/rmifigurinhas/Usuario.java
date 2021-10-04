/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmifigurinhas;
import java.util.ArrayList;
import java.io.Serializable;
/**
 *
 * @author grobs
 */
public class Usuario implements Serializable{
    private String nome_de_usuario;
    private String senha;
    private AlbumFigurinhas album;
    private float coins;
    private ArrayList<Integer> figurinhas_sem_colar;
    
    public Usuario(String nomeusuario,String senha){
        this.nome_de_usuario = nomeusuario;
        this.senha = senha;
        this.album = new AlbumFigurinhas();
        this.coins = 0;
        this.figurinhas_sem_colar = new ArrayList();
    }
    
    public boolean ComparaNomeSenha(String nome, String senha){
        if(nome.equals(this.nome_de_usuario) && senha.equals(this.senha)){
            return true;
        }
        return false;
    }

    public String getNome(){
        return this.nome_de_usuario;
    }

    public boolean AdicionaCoins(float valor){
        this.coins += valor;
        return true;
    }
    
    public boolean CompraPacoteFigurinha(int pacotes){
        if(this.coins >= 5 * pacotes ){
            this.coins -= (5*pacotes);
            for(int i=0;i<pacotes;i++){
                PacoteFigurinha Pacote = new PacoteFigurinha();
                int figurinhas[] = Pacote.GetFigurinhas();
                for(int y=0;y<4;y++){
                    //System.out.println("Fig: "+figurinhas[i]);
                    this.figurinhas_sem_colar.add(figurinhas[y]);
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean ColaFigurinha(Integer figurinha){
        if(this.figurinhas_sem_colar.contains(figurinha)){
            if(this.album.AdicionaFigurinha(figurinha)){
                this.figurinhas_sem_colar.remove(figurinha);
                return true;
            }
            else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    public boolean RetiraFigurinhaSemColar(Integer figurinha){
        if(this.figurinhas_sem_colar.contains(figurinha)){
            this.figurinhas_sem_colar.remove(figurinha);
            return true;
        }else{
            return false;
        }
    }
    
    public void AdicionaFigurinhaSemColar(int figurinha){
        this.figurinhas_sem_colar.add(figurinha);
    }
    
    public float GetCoins(){
        return this.coins;
    }
    
    public boolean SubtraiCoins(float valor){
        if(this.coins>=valor){
            this.coins-=valor;
            return true;
        }
        else{
            return false;
        }
    }
    
    public void SomaCoins(float valor){
        this.coins+=valor;
    }
    
    public void PrintaAlbum(){
        this.album.Printa();
    }
    
    public void PrintaFigurinhas(){
        for(int i=0;i<this.figurinhas_sem_colar.size();i++){
            System.out.print(this.figurinhas_sem_colar.get(i)+" ");
        }
        System.out.println("\nCoins na carteira: "+this.coins);
    }
    
    public void ColaTodasFigurinhas(){
        for(int i=0;i<this.figurinhas_sem_colar.size();i++){
            this.ColaFigurinha(this.figurinhas_sem_colar.get(i));
        }
        //System.out.println("Todas as figurinhas disponiveis coladas!");
    }
    
}
