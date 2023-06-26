/*

Mattia Cammalleri 748801 Varese
Keith Ceriani 755400 Varese
Gianluca Moret 754622 Varese
Meneghini Laura 753448 Varese

*/

package climatemonitoring;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class ParametriClimatici {
    
    public ParametriClimatici(){
        
    }
    
    /**
     *
     * @param geonameid Geoname ID dell'area di cui si vogliono visualizzare le informazioni
     * @param data Data a cui fanno riferimento le informazioni
     * Inserendo un Geoname ID e una data di potranno visulizzare tutte le informazioni inerenti in caso il file esiste
     * Se il file non esiste, allora con la funzione 'showFiles' si cerceranno file con la stessa data o con lo stesso Geoname ID
     * e verranno suggeriti all'utente i risultati
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void visualizzaAreaGeografica(String geonameid, String data) throws FileNotFoundException, IOException{
        String[] dati_data = data.split("/");
        
        File parametri = new File("C:\\Climate Monitoring"+File.separator+"Parametri"+File.separator+geonameid+"------"+dati_data[0]+dati_data[1]+dati_data[2]+".txt");
        
        if(Files.exists(parametri.toPath())){
            //System.out.println("Il file e' stato trovato!");
            Scanner scan = new Scanner(parametri);
            String dati = "";
            String temp = "";
            String temp2[];
            
            //Vento
            temp = scan.nextLine();
            temp2 = temp.split("\t");
            dati = "Velocita' vento: " + temp2[0] + "Km/h Punteggio: " + temp2[1] + " Commenti:\n" + temp2[2];
            System.out.println(dati);
            
            //Umidita
            
            temp = scan.nextLine();
            temp2 = temp.split("\t");
            dati = "Percentuale umidita': " + temp2[0] + "% Punteggio: " + temp2[1] + " Commenti:\n" + temp2[2];
            System.out.println(dati);
            
            //Pressione
            
            temp = scan.nextLine();
            temp2 = temp.split("\t");
            dati = "Pressione: " + temp2[0] + "hPa Punteggio: " + temp2[1] + " Commenti:\n" + temp2[2];
            System.out.println(dati);
            
            //Temperatura
            temp = scan.nextLine();
            temp2 = temp.split("\t");
            dati = "Temperatura: " + temp2[0] + "C Punteggio: " + temp2[1] + " Commenti:\n" + temp2[2];
            System.out.println(dati);
            
            //Precipitazioni
            temp = scan.nextLine();
            temp2 = temp.split("\t");
            dati = "Precipitazioni: " + temp2[0] + "mm Punteggio: " + temp2[1] + " Commenti:\n" + temp2[2];
            System.out.println(dati);
            
            
            //Altitudine dei ghiacciai
            temp = scan.nextLine();
            temp2 = temp.split("\t");
            dati = "Altitudine dei ghiacciai: " + temp2[0] + "m Punteggio: " + temp2[1] + " Commenti:\n" + temp2[2];
            System.out.println(dati);
            
            
            //Massa dei ghiacciai
            temp = scan.nextLine();
            temp2 = temp.split("\t");
            dati = "Massa dei ghiacciai: " + temp2[0] + "Kg Punteggio: " + temp2[1] + " Commenti:\n" + temp2[2];
            System.out.println(dati);
            
            scan.close();
            
        }else{
            System.out.println("Non sono stati trovati dati sull'area con Geoname ID " + geonameid + " nella data " + data);
             
            File dir = new File("C:\\Climate Monitoring"+File.separator+"Parametri"+File.separator);
            this.showFiles(dir.listFiles(), geonameid, data);
        }
    }
    
    /**
     * Quando si cercano delle informazioni su un area ma esse non esistono questa funzione stampa altre informazioni che contengono quel Geoname ID o quella data
     * poichè queste informazioni potrebbero interessare all'utente.
     * @param files Cartella dove sono presenti i dati delle aree geografiche
     * @param geonameid Il Geoname ID che dell'area di cui si cercano le informazioni
     * @param data La data a cui fanno riferimento le informazioni cercate
     * @throws IOException
     */
    public void showFiles(File[] files, String geonameid, String data) throws IOException {
        String[] dati_data = data.split("/");
        String d = dati_data[0]+dati_data[1]+dati_data[2];
        ArrayList<String> simili = new ArrayList();
        for (File file : files) {
                String name = file.getName();
                
                if (name.contains(geonameid)){
                    String[] dati = name.split("------");
                    String[] data_pc = dati[1].split(".txt");
                    //System.out.println("data_pc[0].charAt(0): " + data_pc[0].charAt(0));
                    
                    StringBuilder giorno_b = new StringBuilder().append(data_pc[0].charAt(0)).append(data_pc[0].charAt(1));
                    String giorno = giorno_b.toString();
                    
                    
                    StringBuilder mese_b = new StringBuilder().append(data_pc[0].charAt(2)).append(data_pc[0].charAt(3));
                    String mese = mese_b.toString();
                    
                    StringBuilder anno_b = new StringBuilder().append(data_pc[0].charAt(4)).append(data_pc[0].charAt(5)).append(data_pc[0].charAt(6)).append(data_pc[0].charAt(7));
                    String anno = anno_b.toString();
                    
                    String data_file = giorno+"/"+mese+"/"+anno;
                    
                    String interesse = "Geoname ID: " + geonameid + " Data: " + data_file;
                    simili.add(interesse);
                }
                
                if(name.contains(d)){
                    String[] dati = name.split("------");
                    String interesse = "Geoname ID: " + dati[0] + " Data: " + data;
                    simili.add(interesse);
                }
        }
        
        if(simili.size() > 0){
            System.out.println("Dati che potrebbero interessarti:");
            for(int i=0; i<simili.size(); i++){
                System.out.println(simili.get(i));
            }
        }else{
            System.out.println("Non sono stati trovati file attenenti alla tua ricerca");
        }
    }
    
    /**
     * Dato in input un Geoname ID ritorna vero se esiste un area con quel Geoname ID nel programma, altrimenti ritorna falso
     * @param geonameid
     * @return true o false
     * @throws FileNotFoundException
     */
    public boolean esiste_area(String geonameid) throws FileNotFoundException{
        
        File aree = new File("C:\\Climate Monitoring\\CoordinateMonitoraggio.dati");
        Scanner scan = new Scanner(aree);
        scan.nextLine();
        while(scan.hasNextLine()){
            String riga = scan.nextLine();
            String[] dati = riga.split("\t");
            if(dati[0].equals(geonameid)){
                return true;
            }
        }
        
        return false;
        
    
    }
    
    /**
     *
     * @param input File vecchio e nuovo separati da '------' o semplicemente il percorso del file in caso non di debbanoi modificare Geoname ID o data
     * @param file Percorso del file di cui bisogna modificare i parametri, in caso di modifica di Geoname ID o data, il percorso è vuoto perchè inutilizzato
     * @param scelta da qui si ricava la riga del parametro da modificare oppure decide se modificare il Geoname ID o la data
     * @throws IOException
     */
    public void modifica(String input, String file, int scelta) throws IOException{
        
        if(scelta <= 6){
            
        File temp = new File("C:\\Climate Monitoring\\temp.txt");
        temp.createNewFile();
        Scanner scant = new Scanner(temp);
        BufferedWriter wt = new BufferedWriter(new FileWriter(temp));
        File f = new File(file);
        Scanner scanf = new Scanner(f);
            
            
            for(int i=0; i<7;i++){
                    String riga = scanf.nextLine();
                    if(i == scelta & i == 6){
                        wt.write(input);
                    }
                    
                    if(i == scelta & i != 6){
                        wt.write(input + "\n");
                    }
                    
                    if(i == 6 & i != scelta){
                        wt.write(riga);
                    }
                    
                    if(i != scelta & i != 6){
                        wt.write(riga + "\n");
                    }
                }
                
                wt.close();
                scanf.close();
                
                BufferedWriter wf = new BufferedWriter(new FileWriter(f));
                
                while(scant.hasNextLine()){
                    String riga = scant.nextLine();
                    
                    if(!scant.hasNextLine()){
                        wf.write(riga);
                    }else{
                        wf.write(riga + "\n");
                    }
                    
                    
                }
                
                wf.close();
                scant.close();
                
                System.out.println("Modifica del file effettuata!");
        }else{
            switch(scelta+1){


                case 8:
                    
                    String[] dati_file = input.split("-------");
                    
                    
                    //0 nuovo file / 1 vecchio file
                    
                    File nuovo = new File(dati_file[0]);
                    
                    nuovo.createNewFile();
                    
                    File vecchio = new File(dati_file[1]);
                    
                    Scanner scanvecchio = new Scanner(vecchio);
                    
                    BufferedWriter bf = new BufferedWriter(new FileWriter(nuovo));
                    
                    while(scanvecchio.hasNextLine()){
                        String riga = scanvecchio.nextLine();
                        if(!scanvecchio.hasNextLine()){
                            bf.write(riga);
                        }else{
                            bf.write(riga + "\n");
                        }
                    }
                    
                    
                    
                    bf.close();
                    scanvecchio.close();
                                        
                    Files.delete(vecchio.toPath());
                    
                    
                    System.out.println("Geoname ID modificato con successo");
                    
                    break;
                    
                    
                case 9:
                    //Modifica data
                    
                    
                    String[] dati_file2 = input.split("-------");
                    
                    //System.out.println("Nuovo: " + dati_file[0] + " vecchio da eliminare: " + dati_file[1]);
                    
                    //0 nuovo file / 1 vecchio file
                    
                    File nuovo2 = new File(dati_file2[0]);
                    
                    nuovo2.createNewFile();
                    
                    File vecchio2 = new File(dati_file2[1]);
                    
                    Scanner scanvecchio2 = new Scanner(vecchio2);
                    
                    BufferedWriter bf2 = new BufferedWriter(new FileWriter(nuovo2));
                    
                    while(scanvecchio2.hasNextLine()){
                        String riga = scanvecchio2.nextLine();
                        if(!scanvecchio2.hasNextLine()){
                            bf2.write(riga);
                        }else{
                            bf2.write(riga + "\n");
                        }
                    }
                    
                    
                    
                    bf2.close();
                    scanvecchio2.close();
                                        
                    Files.delete(vecchio2.toPath());
                    
                    
                    System.out.println("Data modificata con successo");
                    
                    break;
                    
                default:
                    System.out.println("Errore");
                    break;
            }
            
        
        }
        
        
        
    }
    
    /**
     *
     * @param geonameid Geoname ID dell'area
     * @param data Data a cui fanno riferimento le informazioni
     * @param vento Velocita' del vento, punteggio e commenti
     * @param umidita Percentuale di umidita', punteggio e commenti
     * @param pressione Pressione, punteggio e commenti
     * @param temperatura Temperatura, punteggio e commenti
     * @param precipitazioni Millimetri di pioggia, punteggio e commenti
     * @param altitudine_ghiacciai Altitudine dei ghiacciai, punteggio e commenti
     * @param massa_ghiacciai Massa dei ghiacciai, punteggio e commenti
     * Crea un file con le informazioni di un'area e la data a cui fanno riferimento le informazioni
     * 
     * @throws IOException
     */
    public void inserisciParametriClimatici(String geonameid, String data, String vento, String umidita, String pressione, String temperatura, String precipitazioni, String altitudine_ghiacciai, String massa_ghiacciai) throws IOException{
        String[] dati_data = data.split("/");
        File f = new File("C:\\Climate Monitoring"+File.separator+"Parametri"+File.separator+geonameid+"------"+dati_data[0]+dati_data[1]+dati_data[2]+".txt");
        f.createNewFile();
        BufferedWriter bf = new BufferedWriter(new FileWriter(f));
        bf.write(vento+"\n"+umidita+"\n"+pressione+"\n"+temperatura+"\n"+precipitazioni+"\n"+altitudine_ghiacciai+"\n"+massa_ghiacciai);
        bf.close();
        
        
        System.out.println("Scrittura completata");
        
    }
    
    /**
     * Data in input una data, controlla se è nel formato giussto gg/mm/aaaa
     * se è nel formato giusto restituisce vero, altrimenti falso
     * @param data
     * @return
     */
    public boolean check_data(String data){
        
        if(data.split("/").length != 3){
            System.out.println("Il formato della data deve essere: giorno/mese/anno");
            return false;
        }
        
        String[] dati = data.split("/");
        
        Account a = new Account();
        
        if(!a.isNumeric(dati[0]) | !a.isNumeric(dati[1]) | !a.isNumeric(dati[2])){
            System.out.println("La data deve contenere solo numeri");
            return false;
        }
        
        if( dati[0].length() != 2 | dati[1].length() != 2 | dati[2].length() != 4 ){
            System.out.println("Il formato della data deve essere: gg/mm/aaaa rispettivamente 2 cifre per il giorno e per il mese, 4 per l'anno");
            return false;
        }
        
        if(Integer.valueOf(dati[0]) > 31 | Integer.valueOf(dati[0]) < 1 |Integer.valueOf(dati[1]) > 12 | Integer.valueOf(dati[1]) < 1 ){
            System.out.println("Data inesistente");
            return false;
        }
        
        
       return true;
    }
    
}
