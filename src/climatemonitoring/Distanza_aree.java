/*

Mattia Cammalleri 748801 Varese
Keith Ceriani 755400 Varese
Gianluca Moret 754622 Varese
Meneghini Laura 753448 Varese

*/


package climatemonitoring;

public class Distanza_aree implements Comparable<Distanza_aree> {
    public double distanza;
    public String nome_area;

    public Distanza_aree(double distanza, String nome_area) {
        this.distanza = distanza;
        this.nome_area = nome_area;
    }
    
    public Distanza_aree(){
    
    }
    
    /**
     * Dato in input una stringa di coordinate restituisce vero se il formato delle coordinate è corretto "x, y"
     * @param s
     * @return
     */
    public boolean checkcoor(String s){
        Account a = new Account();
        
        if(s.split(", ").length != 2){
            System.out.println("Il formato delle coordinate deve essere: 'x, y'");
            return false;
        }
        
        if(!a.isNumeric(String.valueOf(s.charAt(0))) |  !a.isNumeric(String.valueOf(s.charAt(s.length()-1)))){
            System.out.println("Il formato delle coordinate deve essere: 'x, y'");
            return false;
        }
        
        return true;
    }
    
    /**
     * Prende in input un altro oggetto distanza e ne misura la distanza con la distanza dell'oggetto corrente.
     * Questa funzione serve nel programma solamente ad ordinare un array di distanze per trovare l'area più vicina
     * nelle coordinate cercate.
     * @param d
     * @return
     */
    @Override
    public int compareTo(Distanza_aree d) {
        if(this.distanza > d.distanza){
            return 1;
        }
        if(this.distanza == d.distanza){
            return 0;
        }
        
        if(this.distanza < d.distanza){
            return -1;
        }
        return 0;
    }
}
