/*

Mattia Cammalleri 748801 Varese
Keith Ceriani 755400 Varese
Gianluca Moret 754622 Varese
Meneghini Laura 753448 Varese

*/
package climatemonitoring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ClimateMonitor {
    //Funzioni

    /**
     * Inserite le coordinate di 2 punti nel globo, la funzione ne calcola la distanza e la restituisce arrotondata.
     * @param latitude1 latitudine del primo punto
     * @param longitude1 longitudine del primo punto
     * @param latitude2 latitudine del secondo punto
     * @param longitude2 longitudine del secondo punto
     * @return distanza arrotondata fra i 2 punti
     */
    
    public static double getDistanceBetweenPointsNew(double latitude1, double longitude1, double latitude2, double longitude2) {
        double theta = longitude1 - longitude2;
        double distance = 60 * 1.1515 * (180/Math.PI) * Math.acos(
            Math.sin(latitude1 * (Math.PI/180)) * Math.sin(latitude2 * (Math.PI/180)) + 
            Math.cos(latitude1 * (Math.PI/180)) * Math.cos(latitude2 * (Math.PI/180)) * Math.cos(theta * (Math.PI/180))
        );
    
       return Math.round(distance * 1.609344);
    }
    
    
    /**
     * Inserite le coordinate di un area o il suo nome stampa tutte le aree che contengono quel nome o le coordinate
     * Se viene eseguita una ricerca per coordinate ma non viene trovata nessuna area con le coordinate inserite allora
     * stampa l'area più vicina. La distanza è calcolata usando la funzione getDistanceBetweenPointsNew.
     * @param ricerca contenuto da ricercare
     * @param tipo per coordindate o per nome o stato
     */
    
    static void cercaAreaGeografica(String ricerca, int tipo) throws FileNotFoundException{
      File myObj = new File("CoordinateMonitoraggio.dati");
      Scanner myReader = new Scanner(myObj, "utf-8");
      String legenda = myReader.nextLine();//Salta la prima riga senza dati
      //String[] dati = data.split("\t");
      //dati[0] = Geoname ID / dati[1] = nome / dati[2] = nome ASCII / dati[3] = codice paese / dati[4] = nome paese / dati[5] = coordinate ("x, y")
      
      if(tipo==1){
          System.out.println("Risultato della ricerca");
          boolean trovato = false;
          
           while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //System.out.println(data);
                if(data.contains(ricerca) | data.equals(ricerca)){
                    System.out.println(legenda);
                    System.out.println(data);
                    trovato = true;
                }
            }
            myReader.close();
            if(trovato == false){
                System.out.println("L'area cercata non è presente nel programma");
            }
      }else{
          System.out.println("Ricerca per coordinate in corso");
          ArrayList<Distanza_aree> vicinanza = new ArrayList();
          String[] temp = ricerca.split(", ");
          System.out.println(ricerca);
          float lat = Float.parseFloat(temp[0]);
          float lon = Float.parseFloat(temp[1]);
          boolean trovato = false;
          
          while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dati = data.split("\t");
                String[] temp2 = dati[5].split(", ");
                float lat2 = Float.parseFloat(temp2[0]);
                float lon2 = Float.parseFloat(temp2[1]);
                String nome_area = data;
                double distanza = getDistanceBetweenPointsNew(lat, lon, lat2, lon2);
                Distanza_aree da = new Distanza_aree(distanza, nome_area);
                vicinanza.add(da);
                if(dati[5].equals(ricerca)){
                    System.out.println(data);
                    myReader.close();
                    trovato = true;
                    break;
                }
                
            }
          if(trovato == false){
            Collections.sort(vicinanza);
            System.out.println("Area piu' vicina alle coordinate inserite:");
            System.out.println(vicinanza.get(0).nome_area + "\nDistanza dalle coordinate inserite: " + vicinanza.get(0).distanza + "Km");
            myReader.close();
          }
      }
    }
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Account account = new Account();
        ParametriClimatici parametri_climatici = new ParametriClimatici();
        Distanza_aree distanza_aree = new Distanza_aree();
        
        String input_utente = "";
        boolean scelta_valida = false;
        Scanner scan = new Scanner(System.in);
        Scanner scan4 = new Scanner(System.in);
        Scanner scan6 = new Scanner(System.in);
        boolean cookie = false;
        
        
        
        
        System.out.println("Benvenuto nel centro di monitoraggio dati, cosa vuoi fare? (Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio)");
        while(true){
        
            do{
                System.out.println("Menu principale:");
                System.out.println("1. Ricerca un'area");
                System.out.println("2. Visualizza le informazioni di un'area");
                System.out.println("3. Registrati o Accedi(Solo per operatori dei centri di monitoraggio)");
                System.out.println("4. Gestisci le area di interesse o gestisci i centri di monitoraggio (Solo per operatori dei centri di monitoraggio)");
                System.out.println("5. Gestisci i parametri climatici di un area (Solo per operatori dei centri di monitoraggio)");
                System.out.println("6. Gestisci il tuo account (Solo per operatori dei centri di monitoraggio)");
                System.out.println("7. Esci dall'applicazione");


                
                input_utente = scan.nextLine();
                scelta_valida = account.isNumeric(input_utente);
                
                if(!scelta_valida){
                    System.out.println("Devi inserire un numero!");
                }
            }while(!scelta_valida);
            
            switch(Integer.valueOf(input_utente)){
                case 1:
                    boolean token_scelta = true;
                    do{
                        boolean input_corretto = false;
                        do{
                            System.out.println("1. Ricerca per denominazione\n2. Ricerca per coordinate\n3. Torna indietro");
                            input_utente = scan.nextLine();
                            input_corretto = account.isNumeric(input_utente);

                            if(!input_corretto){
                                System.out.println("Devi inserire un numero!");
                            }
                            
                        }while(!input_corretto);
                        
                        switch(Integer.valueOf(input_utente)){
                            case 1:
                                Scanner scan2 = new Scanner(System.in);
                                System.out.println("Inserisci il nome, lo stato o il Geoname ID dell'area");
                                String denominazione = scan2.nextLine();
                                cercaAreaGeografica(denominazione, Integer.valueOf(input_utente));
                                break;
                            case 2:
                                Scanner scan3 = new Scanner(System.in);
                                boolean coordinate_valide = false;
                                do{
                                    System.out.println("Inserisci le coordinate (Formato 'x, y') ");
                                    String coordinate = scan3.nextLine();
                                    
                                    coordinate_valide = distanza_aree.checkcoor(coordinate);
                                    
                                    if(coordinate_valide){
                                        cercaAreaGeografica(coordinate, Integer.valueOf(input_utente));
                                    }
                                    
                                }while(!coordinate_valide);
                               
                            case 3:
                                token_scelta = false;
                                break;
                                
                            default:
                                System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                break;
                        }
                            
                       }while(token_scelta);
                    break;
                
                
                case 2:
                    boolean token_scelta2 = true;
                    do{
                        boolean input_corretto = false;
                        Scanner scan2 = new Scanner(System.in);
                        do{
                            System.out.println("1. Visualizza le informazioni su un area\n2. Indietro");
                            input_utente = scan2.nextLine();
                            
                            input_corretto = account.isNumeric(input_utente);
                            
                            if(!input_corretto){
                                System.out.println("Devi inserire un numero!");
                            }
                            
                        }while(!input_corretto);
                        
                        switch(Integer.valueOf(input_utente)){
                            case 1:
                                System.out.println("Inserisci il Geoname ID dell'area di cui vuoi visualizzare le informazioni");
                                String geonameid = scan4.nextLine();
                                
                                
                                if(!parametri_climatici.esiste_area(geonameid)){
                                    System.out.println("Il Geoname ID inserito non appartiene a nessuna area!");
                                    break;
                                }
                                
                                
                                boolean data_valida = false;
                                Scanner scan5 = new Scanner(System.in);
                                String data;
                                do{
                                    System.out.println("Inserisci la data in cui sono state registrate le informazioni");
                                    data = scan5.nextLine();
                                    data_valida = parametri_climatici.check_data(data);
                                    
                                }while(!data_valida);
                                
                                parametri_climatici.visualizzaAreaGeografica(geonameid, data);
                                
                                break;
                            case 2:
                                token_scelta2 = false;
                                break;
                            
                            default:
                                System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                break;
                        }
                        
                    }while(token_scelta2);
                    break;
                
                case 3:
                    boolean token_scelta3 = true;
                    do{
                        boolean input_corretto = false;
                        Scanner scan2 = new Scanner(System.in);
                        do{
                            System.out.println("1. Registrati\n2. Accedi\n3. Indietro");
                            input_utente = scan2.nextLine();
                            
                            input_corretto = account.isNumeric(input_utente);
                            
                            if(!input_corretto){
                                System.out.println("Devi inserire un numero!");
                            }
                        }while(!input_corretto);
                        
                        switch(Integer.valueOf(input_utente)){
                            
                            case 1:
                                boolean esito = false;
                                do{
                                    Account acc = new Account();
                                    cookie = acc.registrazione();

                                        if(!cookie){
                                            
                                            while(!esito){
                                                /*Account acc2 = new Account();
                                                cookie = acc2.registrazione();*/
                                                esito = cookie;
                                                boolean input_valido2 = false;
                                                String riprova;
                                                do{
                                                    System.out.println("La registrazione non e' andata a buon fine. Cosa vuoi fare?");
                                                    System.out.println("1. Riprova\n2. Indietro");

                                                    riprova = scan6.nextLine();
                                                    if(!account.isNumeric(riprova)){
                                                        System.out.println("Devi inserire un numero!");
                                                    }else{
                                                        input_valido2 = true;
                                                    }
                                                }while(!input_valido2);
                                                
                                                switch(Integer.valueOf(riprova)){
                                                    
                                                    case 1:
                                                        cookie = acc.registrazione();
                                                        esito = cookie;
                                                        break;
                                                    case 2:
                                                        esito = true;
                                                        break;
                                                    
                                                    default:
                                                        System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                        break;
                                                }
                                                
                                            }
                                            
                                      
                                    }else{
                                        esito = true;
                                    }
                                
                                }while(!esito);
                                token_scelta3 = false;
                                break;
                                
                            case 2:
                                boolean esito2 = false;
                                do{
                                    Account acc = new Account();
                                    cookie = acc.login();

                                        if(!cookie){
                                            
                                            while(!esito2){
                                                
                                                esito2 = cookie;
                                                boolean input_valido2 = false;
                                                String riprova;
                                                do{
                                                    System.out.println("L'accesso non e' andato a buon fine. Cosa vuoi fare?");
                                                    System.out.println("1. Riprova\n2. Indietro");

                                                    riprova = scan6.nextLine();
                                                    if(!account.isNumeric(riprova)){
                                                        System.out.println("Devi inserire un numero!");
                                                    }else{
                                                        input_valido2 = true;
                                                    }
                                                }while(!input_valido2);
                                                
                                                switch(Integer.valueOf(riprova)){
                                                    
                                                    case 1:
                                                        cookie = acc.login();
                                                        esito2 = cookie;
                                                        break;
                                                    case 2:
                                                        esito2 = true;
                                                        break;
                                                    
                                                    default:
                                                        System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                        break;
                                                }
                                                
                                            }
                                            
                                      
                                    }else{
                                        esito2 = true;
                                    }
                                
                                }while(!esito2);
                                token_scelta3 = false;
                                break;
                            
                            case 3:
                                token_scelta3 = false;
                                break;
                            
                            default:
                                System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                break;

                        }
                    
                    }while(token_scelta3);
                    break;
                
                case 4:
                    boolean token_scelta4 = true;
                    do{
                        if(cookie){
                            boolean input_valido = false;
                            Scanner scan7 = new Scanner(System.in);
                            String input_gestione;
                            do{
                                System.out.println("1. Gestisci le aree di interesse o i centri\n2. Indietro");
                                input_gestione = scan7.nextLine();
                                
                                if(!account.isNumeric(input_utente)){
                                    System.out.println("Devi inserire un numero!");
                                }else{
                                   input_valido = true; 
                                }
                                
                            }while(!input_valido);
                            
                            switch(Integer.valueOf(input_gestione)){
                                case 1:
                                    boolean input_valido2 = false;
                                    boolean token_scelta42 = true;
                                    do{
                                        Scanner scan8 = new Scanner(System.in);
                                        String input_utente2;
                                        do{
                                            System.out.println("1. Gestisci le aree di interesse\n2. Gestisci i centri\n3. Indietro");
                                            input_utente2 = scan8.nextLine();

                                            if(!account.isNumeric(input_utente2)){
                                                System.out.println("Devi inserire un numero!");
                                            }else{
                                                input_valido2 = true; 
                                            }

                                        }while(!input_valido2);
                                        Scanner scan9 = new Scanner(System.in);
                                        
                                        
                                        switch(Integer.valueOf(input_utente2)){
                                            
                                            case 1:
                                                boolean token_scelta43 = true;
                                                do{
                                                    boolean input_valido3 = false;
                                                    String input_utente_3;
                                                    do{
                                                        System.out.println("1. Aggiungi un area di interesse\n2. Rimuovi un area di interesse\n3. Modifica un area di interesse\n4. Indietro");
                                                        input_utente_3 = scan9.nextLine();

                                                        if(!account.isNumeric(input_utente_3)){
                                                            System.out.println("Devi inserire un numero!");
                                                        }else{
                                                            input_valido3 = true;
                                                        }

                                                    }while(!input_valido3);
                                                    
                                                    switch(Integer.valueOf(input_utente_3)){
                                                        
                                                        case 1:
                                                            Scanner scan10 = new Scanner(System.in);
                                                            String geonameid;
                                                            String nome_area;
                                                            String codice_paese;
                                                            String nome_paese;
                                                            String coordinate2;
                                                                    
                                                            boolean no_tab = false;
                                                            do{
                                                                System.out.println("Inserisci il Geoname id dell'area");
                                                                geonameid = scan10.nextLine();
                                                                System.out.println("Inserisci il nome dell'area");
                                                                nome_area = scan10.nextLine();
                                                                System.out.println("Inserisci il codice del paese");
                                                                codice_paese = scan10.nextLine();
                                                                System.out.println("Inserisci il nome del paese");
                                                                nome_paese = scan10.nextLine();
                                                                boolean cv = false;
                                                                do{
                                                                    System.out.println("Inserisci le coordinare in formato 'x, y'");
                                                                    coordinate2 = scan10.nextLine();
                                                                    
                                                                    if(distanza_aree.checkcoor(coordinate2)){
                                                                        cv = true;
                                                                    }
                                                                    
                                                                }while(!cv);
                                                                
                                                                
                                                                if(geonameid.contains("\t") | geonameid.contains("-----") | nome_area.contains("\t") | nome_area.contains("-----") | codice_paese.contains("\t") | codice_paese.contains("-----") | nome_paese.contains("\t") | nome_paese.contains("-----") |  coordinate2.contains("\t") | coordinate2.contains("-----")){
                                                                    System.out.println("I dati non possono contenere tab o i 5 trattini '-----'");
                                                                }else{
                                                                    no_tab = true;
                                                                }
                                                                
                                                            }while(!no_tab);
                                                            String area_finale = geonameid+"\t"+nome_area+"\t"+nome_area+"\t"+codice_paese+"\t"+nome_paese+"\t"+coordinate2;
                                                            account.registraCentroAree(area_finale, 1);
                                                            break;
                                                          
                                                        case 2:
                                                            Scanner scan11 = new Scanner(System.in);
                                                            System.out.println("Inserisci il geoname id dell'area che vuoi rimuovere");
                                                            String geonameid2 = scan11.nextLine();
                                                            account.registraCentroAree(geonameid2, 2);
                                                            break;
                                                            
                                                        case 3:
                                                            Scanner scan12 = new Scanner(System.in);
                                                            System.out.println("Inserisci il Geoname id dell'area");
                                                            String geonameid3 = scan12.nextLine();
                                                            File myObj2 = new File("CoordinateMonitoraggio.dati");
                                                            Scanner myReader3 = new Scanner(myObj2, "UTF-8");
                                                            String mod = null;
                                                            boolean trovata = false;
                                                            while(myReader3.hasNextLine()){
                                                                String data = myReader3.nextLine();
                                                                String[] dati = data.split("\t");
                                                                if(dati[0].equals(geonameid3 ) ){
                                                                    trovata = true;
                                                                    mod = data;
                                                                    break;
                                                                }
                                                            }
                                                            if(trovata){
                                                                do{
                                                                    String[] parti = mod.split("\t");
                                                                    token_scelta2 = true;
                                                                    boolean scelta_valida2 = false;
                                                                    String n;
                                                                    do{
                                                                        System.out.println("Cosa vuoi modificare di questa area?");
                                                                        System.out.println("1. Il Geoname ID\n2. Il nome\n3. Il codice del paese\n4. Il paese\n5. le coordinate\n6. Indietro");
                                                                        n = scan12.nextLine();
                                                                        
                                                                        if(!account.isNumeric(n)){
                                                                            System.out.println("Devi inserire un numero!");
                                                                        }else{
                                                                            scelta_valida2 = true;
                                                                        }
                                                                        
                                                                    }while(!scelta_valida2);
                                                                    switch(Integer.valueOf(n)){
                                                                        case 1:
                                                                            System.out.println("Inserisci il nuovo geoname ID");
                                                                            String gnid = scan12.nextLine();
                                                                            
                                                                            if(gnid.contains("\t") | gnid.contains("-----")){
                                                                                System.out.println("Il Geoname ID non puo' contenere il carattere tab o i 5 trattini '-----'");
                                                                            }else{
                                                                                boolean esiste = parametri_climatici.esiste_area(gnid);


                                                                                if(!esiste){
                                                                                    String nuovo = gnid+"\t"+parti[1]+"\t"+parti[2]+"\t"+parti[3]+"\t"+parti[4]+"\t"+parti[5]+"-----"+mod;
                                                                                    account.registraCentroAree(nuovo, 3);
                                                                                    String[] temp = nuovo.split("-----");
                                                                                    mod = temp[0];
                                                                                    geonameid3 = gnid;
                                                                                }else{
                                                                                    System.out.println("Geoname ID già in uso");
                                                                                }
                                                                            
                                                                            }
                                                                            
                                                                            
                                                                            
                                                                            break;
                                                                        case 2:
                                                                            System.out.println("Inserisci il nuovo nome");
                                                                            String nnome = scan12.nextLine();
                                                                            
                                                                            if(nnome.contains("\t") | nnome.contains("-----")){
                                                                                System.out.println("Il nuovo nome non puo' contenere il carattere tab o i 5 trattini '-----'");
                                                                            }else{
                                                                                String nuovo2 = parti[0]+"\t"+nnome+"\t"+nnome+"\t"+parti[3]+"\t"+parti[4]+"\t"+parti[5]+"-----"+mod;
                                                                                account.registraCentroAree(nuovo2, 3);
                                                                                String[] temp2 = nuovo2.split("-----");
                                                                                mod = temp2[0];
                                                                                parti[1] = nnome;
                                                                                parti[2] = nnome;
                                                                            }
                                                                            
                                                                            
                                                                            break;
                                                                        case 3:
                                                                            System.out.println("Inserisci il nuovo codice paese");
                                                                            String cpaese = scan12.nextLine();
                                                                            
                                                                            if(cpaese.contains("\t") | cpaese.contains("-----")){
                                                                                System.out.println("Il nuovo codice paese non puo' contenere il carattere tab o i 5 trattini '-----'");
                                                                            }else{
                                                                                String nuovo3 = parti[0]+"\t"+parti[1]+"\t"+parti[2]+"\t"+cpaese+"\t"+parti[4]+"\t"+parti[5]+"-----"+mod;
                                                                                account.registraCentroAree(nuovo3, 3);
                                                                                String[] temp3 = nuovo3.split("-----");
                                                                                mod = temp3[0];
                                                                                parti[3] = cpaese;
                                                                            }
                                                                            
                                                                            
                                                                            break;
                                                                        case 4:
                                                                            System.out.println("Inserisci il nuovo nome paese");
                                                                            String npaese = scan12.nextLine();
                                                                            
                                                                            if(npaese.contains("\t") | npaese.contains("-----")){
                                                                                System.out.println("Il nuovo nome non puo' contenere il carattere tab o i 5 trattini '-----'");
                                                                            }else{
                                                                                String nuovo4 = parti[0]+"\t"+parti[1]+"\t"+parti[2]+"\t"+parti[3]+"\t"+npaese+"\t"+parti[5]+"-----"+mod;
                                                                                //System.out.println(nuovo4);
                                                                                account.registraCentroAree(nuovo4, 3);
                                                                                String[] temp4 = nuovo4.split("-----");
                                                                                mod = temp4[0];
                                                                                parti[4] = npaese;
                                                                            }
                                                                            
                                                                            
                                                                            break;
                                                                        case 5:
                                                                            boolean coord_valide = false;
                                                                            String ncoor;
                                                                            do{
                                                                                System.out.println("Inserisci le nuove coordinate in formato 'x, y' ");
                                                                                ncoor = scan12.nextLine();
                                                                                
                                                                                if(distanza_aree.checkcoor(ncoor)){
                                                                                    coord_valide = true;
                                                                                }
                                                                                
                                                                            }while(!coord_valide);
                                                                            boolean trovato = false;
                                                                            File myObj3 = new File("CoordinateMonitoraggio.dati");
                                                                            Scanner myReader4 = new Scanner(myObj3, "UTF-8");
                                                                            while(myReader4.hasNextLine()){
                                                                                String riga = myReader4.nextLine();
                                                                                String[] p = riga.split("\t");
                                                                                //System.out.println(riga);
                                                                                if(p[5].equals(ncoor)){
                                                                                    trovato = true;
                                                                                    break;
                                                                                }
                                                                            }
                                                                            
                                                                            if(trovato){
                                                                                System.out.println("Coordinate gia' associate ad un altra area");
                                                                            }else{
                                                                                String nuovo5 = parti[0]+"\t"+parti[1]+"\t"+parti[2]+"\t"+parti[3]+"\t"+parti[4]+"\t"+ncoor+"-----"+mod;
                                                                                account.registraCentroAree(nuovo5, 3);
                                                                                String[] temp5 = nuovo5.split("-----");
                                                                                mod = temp5[0];
                                                                                parti[5] = ncoor;
                                                                            }
                                                                            
                                                                            break;
                                                                        case 6:
                                                                            token_scelta2 = false;
                                                                            break;
                                                                        default:
                                                                            System.out.println("Scelta invalida");
                                                                            break;
                                                                    }
                                                                }while(token_scelta2);
                                                            }else{
                                                                System.out.println("Area non trovata");
                                                            }
                                                            
                                                            break;
                                                        
                                                            
                                                            
                                                        case 4:
                                                            token_scelta43 = false;
                                                            break;
                                                            
                                                         
                                                        default:
                                                            System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                            break;
                                                    }
                                                    
                                                    
                                                }while(token_scelta43);
                                                break;
                                             
                                            case 2:
                                                boolean token_scelta5 = true;
                                                do{
                                                    String sceltacentro;
                                                    boolean input_valido3 = false;
                                                    Scanner scan13 = new Scanner(System.in);
                                                    do{
                                                        System.out.println("1. Aggiungi un centro di monitoraggio\n2. Rimuovi un centro di monitoraggio\n3. Modifica un centro di monitoraggio\n4. Visualizza i centri di monitoraggio\n5. Indietro");
                                                        sceltacentro = scan13.nextLine();

                                                        if(!account.isNumeric(sceltacentro)){
                                                            System.out.println("Devi inserire un numero!");
                                                        }else{
                                                            input_valido3 = true;
                                                        }

                                                    }while(!input_valido3);
                                                    
                                                    Scanner scan14 = new Scanner(System.in);
                                                    Scanner scan15 = new Scanner(System.in);
                                                    
                                                    switch(Integer.valueOf(sceltacentro)){
                                                        
                                                        case 1:
                                                            boolean no_tab2 = false;
                                                            String nome_centro, nome_indirizzo;
                                                            do{
                                                                System.out.println("Inserisci il nome del centro");
                                                                nome_centro = scan14.nextLine();
                                                                System.out.println("Inserisci l'indirizzo del centro");
                                                                String indirizzo_centro = scan14.nextLine();
                                                                nome_indirizzo = nome_centro+"\t"+indirizzo_centro;
                                                                
                                                                if(nome_centro.contains("\t") | indirizzo_centro.contains("\t") | nome_centro.contains("-----") | indirizzo_centro.contains("-----")){
                                                                    System.out.println("Un centro non puo' contenere il carattere tab o i 5 trattini '-----'");
                                                                }else{
                                                                    no_tab2 = true;
                                                                }
                                                                
                                                            }while(!no_tab2);
                                                            
                                                            File centri = new File("CentroMonitoraggio.dati");
                                                            boolean presente = false;
                                                            Scanner scancentri = new Scanner(centri, "UTF-8");
                                                            while(scancentri.hasNextLine()){
                                                                String riga = scancentri.nextLine();
                                                                String[] dati = riga.split("\t");
                                                                if(dati[0].equals(nome_centro)){
                                                                    System.out.println("Centro gia' esistente");
                                                                    presente = true;
                                                                    break;
                                                                }
                                                            }
                                                            
                                                            if(!presente){
                                                                
                                                                String aree = "";
                                                                
                                                                String a;
                                                                do{
                                                                    System.out.println("Inserisci il GeonameID dell'area che vuoi aggiungere a questo centro, inserisci 'Stop' per finire");
                                                                    a = scan15.nextLine();
                                                                    boolean inserito = false; 
                                                                    
                                                                    String[] lista = aree.split("\t");
                                                                    
                                                                    for(int i=0;i<lista.length;i++){
                                                                        if(lista[i].equals(a)){
                                                                            System.out.println("Area gia' inserita in questo centro");
                                                                            inserito = true;
                                                                            break;
                                                                        }
                                                                    }
                                                                    
                                                                    if(!inserito){
                                                                        boolean esiste = false;
                                                                        File areef = new File("CoordinateMonitoraggio.dati");
                                                                        Scanner scanaree = new Scanner(areef, "UTF-8");
                                                                        scanaree.nextLine(); //Salta la prima riga senza dati
                                                                        while(scanaree.hasNextLine()){
                                                                            String riga = scanaree.nextLine();
                                                                            String[] dati = riga.split("\t");
                                                                            
                                                                            if(dati[0].equals(a)){
                                                                                aree = aree+"\t"+a;
                                                                                esiste = true;
                                                                                break;
                                                                            }
                                                                        }
                                                                        
                                                                        if(!esiste){
                                                                            System.out.println("Il Geoname ID inserito non esiste");
                                                                        }
                                                                    }
                                                                }while(!a.equals("Stop"));
                                                                
                                                                String nuovo_centro = nome_indirizzo+aree;
                                                                account.registraCentroAree(nuovo_centro, 4);
                                                                System.out.println(nuovo_centro);
                                                            }
                                                            
                                                            break;
                                                            
                                                        case 2:
                                                            
                                                            
                                                            File v = new File("CentroMonitoraggio.dati");
                                                            if(Files.readAllBytes(v.toPath()).length == 0){
                                                                System.out.println("Non e' presente alcun centro di monitoraggio");
                                                            }else{
                                                            
                                                                Scanner scan16 = new Scanner(System.in);
                                                                Scanner scan17 = new Scanner(System.in);
                                                                
                                                                System.out.println("Inserisci il nome del centro che vuoi rimuovere");
                                                                String nome_centro2 = scan16.nextLine();
                                                                System.out.println("Inserisci l'indirizzo del centro che vuoi rimuovere");
                                                                String indirizzo_centro2 = scan17.nextLine();
                                                                File centri2 = new File("CentroMonitoraggio.dati");
                                                                Scanner scancentri3 = new Scanner(centri2);
                                                                boolean trovato = false;
                                                                while(scancentri3.hasNextLine()){
                                                                    String riga = scancentri3.nextLine();
                                                                    String[] dati = riga.split("\t");
                                                                    if(dati[0].equals(nome_centro2) & dati[1].equals(indirizzo_centro2)){
                                                                        trovato = true;
                                                                        break;
                                                                    }
                                                                }

                                                                if(trovato){
                                                                    String centro_da_rimuovere = nome_centro2+"\t"+indirizzo_centro2;
                                                                    account.registraCentroAree(centro_da_rimuovere, 5);
                                                                }else{
                                                                    System.out.println("Centro non trovato");
                                                                }
                                                            }
                                                            
                                                            
                                                            
                                                            break;
                                                            
                                                        case 3:
                                                            
                                                            //Modifica centro di monitoraggio
                                                            Scanner scan2 = new Scanner(System.in);
                                                            File v2 = new File("CentroMonitoraggio.dati");
                                                            if(Files.readAllBytes(v2.toPath()).length == 0){
                                                                System.out.println("Non e' presente alcun centro di monitoraggio");
                                                            }else{
                                                                
                                                                System.out.println("Inserisci il nome del centro che vuoi modificare");
                                                                String nome_centro3 = scan2.nextLine();
                                                                System.out.println("Inserisci l'indirizzo del centro che vuoi modificare");
                                                                String indirizzo_centro3 = scan2.nextLine();
                                                                //Cerca se il centro inserito esiste
                                                                File centri3 = new File("CentroMonitoraggio.dati");
                                                                Scanner scancentri2 = new Scanner(centri3);
                                                                boolean trovato2 = false;
                                                                String centro_da_modificare = "";
                                                                while(scancentri2.hasNextLine()){
                                                                    String riga = scancentri2.nextLine();
                                                                    String[] dati = riga.split("\t");
                                                                    if(dati[0].equals(nome_centro3) & dati[1].equals(indirizzo_centro3)){
                                                                        System.out.println(riga);
                                                                        centro_da_modificare = riga;
                                                                        trovato2 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                
                                                                
                                                                if(!trovato2){
                                                                    System.out.println("Centro non trovato");
                                                                }else{
                                                                
                                                                    boolean token_scelta34 = true;
                                                                    String[] parti = centro_da_modificare.split("\t");
                                                                    Scanner scan11 = new Scanner(System.in);
                                                                    do{
                                                                        boolean input_valido4 = false;
                                                                        String scelta2;
                                                                        do{
                                                                            System.out.println("Cosa vuoi modificare di questo centro?\n1. Il nome\n2. La via\n3. Le aree\n4. Indietro");
                                                                            scelta2 = scan11.nextLine();
                                                                            
                                                                            if(!account.isNumeric(scelta2)){
                                                                                System.out.println("Devi inserire un numero!");
                                                                            }else{
                                                                                input_valido4 = true;
                                                                            }
                                                                            
                                                                        }while(!input_valido4);
                                                                        
                                                                        
                                                                        File centri4 = new File("CentroMonitoraggio.dati");
                                                                        Scanner scancentri3 = new Scanner(centri4);
                                                                        switch(Integer.valueOf(scelta2)){
                                                                            case 1:
                                                                                System.out.println("Inserisci il nuovo nome");
                                                                                String nnome2 = scan11.nextLine();
                                                                                
                                                                                if(nnome2.contains("-----") | nnome2.contains("\t")){
                                                                                    System.out.println("Il nuovo nome non puo' contenere il carattere tab o i 5 trattini '-----'");
                                                                                }else{
                                                                                String centro_modificato = nnome2+"\t"+parti[1];
                                                                                boolean trovato3 = false;
                                                                                while(scancentri3.hasNextLine()){
                                                                                    String riga = scancentri3.nextLine();
                                                                                    String[] dati = riga.split("\t");
                                                                                    //System.out.println(riga + "            dati.length : " + dati.length);
                                                                                    if(dati[0].equals(nnome2) & dati[1].equals(parti[1])){
                                                                                        trovato3 = true;
                                                                                        break;
                                                                                    }
                                                                                }
                                                                                
                                                                                if(trovato3){
                                                                                    System.out.println("Esiste gia' un centro con nome " + nnome2 + " e indirizzo " + parti[1]);
                                                                                }else{
                                                                                    for(int i=2; i<parti.length;i++){
                                                                                        //System.out.println("Parti["+i+"] = " + i);
                                                                                        centro_modificato = centro_modificato+"\t"+parti[i];

                                                                                     }
                                                                                    
                                                                                    System.out.println(centro_modificato);
                                                                                    
                                                                                    String riga_da_modificare = "";
                                                                                    for(int i=0;i<parti.length;i++){
                                                                                        if(i == 0){
                                                                                            riga_da_modificare = riga_da_modificare+parti[i];
                                                                                        }else{
                                                                                            riga_da_modificare = riga_da_modificare+"\t"+parti[i];
                                                                                        }
                                                                                        
                                                                                     }
                                                                                    
                                                                                    System.out.println(riga_da_modificare);
                                                                                    
                                                                                    String input = centro_modificato+"-----"+riga_da_modificare;
                                                                                    account.registraCentroAree(input, 6);
                                                                                    centro_da_modificare = centro_modificato;
                                                                                    
                                                                                    parti[0] = nnome2;
                                                                                }
                                                                                
                                                                                
                                                                                    
                                                                                }
                                                                                
                                                                                break;
                                                                            case 2:
                                                                                Scanner scan5 = new Scanner(System.in);
                                                                                System.out.println("Inserisci il nuovo indirizzo");
                                                                                String nindirizzo = scan5.nextLine();
                                                                                
                                                                                if(nindirizzo.contains("-----") | nindirizzo.contains("\t")){
                                                                                    System.out.println("Il nuovo indirizzo non puo' contenere il carattere tab o i 5 trattini '-----'");
                                                                                }else{
                                                                                    String centro_modificato2 = parti[0]+"\t"+nindirizzo;
                                                                                    boolean trovato4 = false;
                                                                                    while(scancentri3.hasNextLine()){
                                                                                        String riga = scancentri3.nextLine();
                                                                                        String[] dati = riga.split("\t");
                                                                                        if(dati[0].equals(parti[0]) & dati[1].equals(nindirizzo)){
                                                                                            trovato4 = true;
                                                                                            break;
                                                                                        }
                                                                                    }

                                                                                    if(trovato4){
                                                                                        System.out.println("Esiste gia' un centro con nome " + parti[0] + " e indirizzo " + nindirizzo);
                                                                                    }else{
                                                                                        for(int i=2; i<parti.length;i++){
                                                                                            centro_modificato2 = centro_modificato2+"\t"+parti[i];
                                                                                        }
                                                                                        String riga_da_modificare = "";
                                                                                        for(int i=0;i<parti.length;i++){
                                                                                            if(i == 0){
                                                                                                riga_da_modificare = riga_da_modificare+parti[i];
                                                                                            }else{
                                                                                                riga_da_modificare = riga_da_modificare+"\t"+parti[i];
                                                                                            }

                                                                                        }
                                                                                        String input = centro_modificato2+"-----"+riga_da_modificare;
                                                                                        account.registraCentroAree(input, 6);
                                                                                        System.out.println(riga_da_modificare);
                                                                                        System.out.println(centro_modificato2);
                                                                                        centro_da_modificare = centro_modificato2;
                                                                                        parti[1] = nindirizzo;
                                                                                }
                                                                                
                                                                                }
                                                                                
                                                                                break;
                                                                            case 3:
                                                                                boolean token_scelta45 = true;
                                                                                do{
                                                                                    boolean input_valido5 = false;
                                                                                    
                                                                                    String mscelta;
                                                                                    Scanner scan3 = new Scanner(System.in);
                                                                                    
                                                                                    do{
                                                                                        System.out.println("Come vuoi modificare le aree di questi centri?\n1. Aggiungerle\n2. Rimuoverle\n3. Modificare un Geoname ID\n4. Indietro");
                                                                                        mscelta = scan3.nextLine();
                                                                                        
                                                                                        if(!account.isNumeric(mscelta)){
                                                                                            System.out.println("Devi inserire un numero!");
                                                                                        }else{
                                                                                            input_valido5 = true;
                                                                                        }
                                                                                        
                                                                                    }while(!input_valido5);
                                                                                    Scanner scan12 = new Scanner(System.in);
                                                                                    switch(Integer.valueOf(mscelta)){
                                                                                        
                                                                                        default:
                                                                                            System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                                                            break;
                                                                                            
                                                                                        case 1:
                                                                                            System.out.println("Inserisci il Geoname ID dell'area che vuoi inserire in questo centro");
                                                                                            String agnid = scan12.nextLine();
                                                                                            
                                                                                            if(agnid.contains("-----") | agnid.contains("\t")){
                                                                                                System.out.println("Il Geoname ID non puo' contenere il carattere tab o i 5 trattini '-----'");
                                                                                            }else{
                                                                                                boolean inserito = false;

                                                                                                for(int i=2;i<parti.length;i++){
                                                                                                    if(parti[i].equals(agnid)){
                                                                                                        inserito = true;
                                                                                                        break;
                                                                                                    }
                                                                                                }

                                                                                                if(!inserito){
                                                                                                    File f3 = new File ("CoordinateMonitoraggio.dati");
                                                                                                    Scanner scane = new Scanner(f3);
                                                                                                    boolean esiste = false;
                                                                                                    scane.nextLine();
                                                                                                    while(scane.hasNextLine()){
                                                                                                        String riga = scane.nextLine();
                                                                                                        String[] dati = riga.split("\t");
                                                                                                        if(dati[0].equals(agnid)){
                                                                                                            esiste = true;
                                                                                                            break;
                                                                                                        }
                                                                                                    }

                                                                                                    if(esiste){
                                                                                                        String nuovo_centro = centro_da_modificare+"\t"+agnid;

                                                                                                        String riga_da_modificare = "";
                                                                                                        for(int i=0;i<parti.length;i++){
                                                                                                            if(i == parti.length-1){
                                                                                                                riga_da_modificare = riga_da_modificare+parti[i];
                                                                                                            }else{
                                                                                                                riga_da_modificare = riga_da_modificare+parti[i]+"\t";
                                                                                                            }

                                                                                                        }

                                                                                                        String input = nuovo_centro+"-----"+riga_da_modificare;
                                                                                                        account.registraCentroAree(input, 6);
                                                                                                        System.out.println(nuovo_centro);
                                                                                                        centro_da_modificare = nuovo_centro;
                                                                                                        parti = nuovo_centro.split("\t");
                                                                                                    }else{
                                                                                                        System.out.println("Il Geoname ID inserito non e' presente nel programma");
                                                                                                    }


                                                                                                }else{
                                                                                                    System.out.println("Geoname ID gia' inserito in questo centro");
                                                                                                }
                                                                                            
                                                                                            }
                                                                                            
                                                                                            
                                                                                            
                                                                                            break;
                                                                                        case 2:
                                                                                            if(parti.length == 2){
                                                                                                System.out.println("Questo centro non ha aree inserite");
                                                                                            }
                                                                                            
                                                                                            if(parti.length == 3){
                                                                                                System.out.println("L'unica area inserita in questo centro era " + parti[2] + " ed e' stata rimossa");
                                                                                                String nuovo_centro2 = parti[0]+"\t"+parti[1];
                                                                                                
                                                                                                String riga_da_modificare = "";
                                                                                                for(int i=0;i<parti.length;i++){
                                                                                                    if(i == parti.length-1){
                                                                                                        riga_da_modificare = riga_da_modificare+parti[i];
                                                                                                    }else{
                                                                                                        riga_da_modificare = riga_da_modificare+parti[i]+"\t";
                                                                                                    }

                                                                                                }
                                                                                                System.out.println(riga_da_modificare);
                                                                                                String input = nuovo_centro2+"-----"+riga_da_modificare;
                                                                                                account.registraCentroAree(input, 6);
                                                                                                System.out.println(nuovo_centro2);
                                                                                                parti = nuovo_centro2.split("\t");
                                                                                                centro_da_modificare = nuovo_centro2;
                                                                                            }
                                                                                            
                                                                                            if(parti.length > 3){
                                                                                                System.out.println("Inserisci il Geoname ID in questo centro che vuoi rimuovere");
                                                                                                String rgnid = scan3.nextLine();
                                                                                                String nuovo_centro = parti[0]+"\t"+parti[1];
                                                                                                
                                                                                                for(int i=2;i<parti.length;i++){
                                                                                                    if(!parti[i].equals(rgnid)){
                                                                                                        nuovo_centro = nuovo_centro+"\t"+parti[i];
                                                                                                        
                                                                                                    }
                                                                                                }
                                                                                                
                                                                                                String riga_da_modificare = "";
                                                                                                for(int i=0;i<parti.length;i++){
                                                                                                    if(i == parti.length-1){
                                                                                                        riga_da_modificare = riga_da_modificare+parti[i];
                                                                                                    }else{
                                                                                                        riga_da_modificare = riga_da_modificare+parti[i]+"\t";
                                                                                                    }

                                                                                                }
                                                                                                System.out.println(riga_da_modificare);
                                                                                                
                                                                                                String input = nuovo_centro+"-----"+riga_da_modificare;
                                                                                                account.registraCentroAree(input, 6);
                                                                                                System.out.println(nuovo_centro);
                                                                                                parti = nuovo_centro.split("\t");
                                                                                                centro_da_modificare = nuovo_centro;
                                                                                            }
                                                                                            
                                                                                            break;
                                                                                        case 3:
                                                                                            
                                                                                            if(parti.length == 2){
                                                                                                System.out.println("Questo centro non ha aree inserite");
                                                                                            }
                                                                                            
                                                                                            if(parti.length == 3){
                                                                                                System.out.println("L'unica area inserita in questo centro e' " + parti[2] + " come vuoi modificarla?");
                                                                                                String ngnid = scan3.nextLine();
                                                                                                String nuovo_centro2 = parti[0]+"\t"+parti[1]+"\t"+ngnid;
                                                                                                
                                                                                                
                                                                                                String riga_da_modificare = "";
                                                                                                for(int i=0;i<parti.length;i++){
                                                                                                    if(i == parti.length-1){
                                                                                                        riga_da_modificare = riga_da_modificare+parti[i];
                                                                                                    }else{
                                                                                                        riga_da_modificare = riga_da_modificare+parti[i]+"\t";
                                                                                                    }

                                                                                                }
                                                                                                System.out.println(riga_da_modificare);
                                                                                                
                                                                                                String input = nuovo_centro2+"-----"+riga_da_modificare;
                                                                                                account.registraCentroAree(input, 6);
                                                                                                System.out.println(nuovo_centro2);
                                                                                                parti = nuovo_centro2.split("\t");
                                                                                                centro_da_modificare = nuovo_centro2;
                                                                                            }
                                                                                            
                                                                                            if(parti.length > 3){
                                                                                                System.out.println("Quale Geoname ID vuoi modificare?");
                                                                                                for(int i=2;i<parti.length;i++){
                                                                                                    System.out.println(". " + parti[i]);
                                                                                                }
                                                                                                
                                                                                                String qgnid = scan3.nextLine();
                                                                                                
                                                                                                boolean trovato = false;
                                                                                                Scanner scan10 = new Scanner(System.in);
                                                                                                for(int i=2;i<parti.length;i++){
                                                                                                    if(parti[i].equals(qgnid)){
                                                                                                        boolean input_valido6 = false;
                                                                                                        String ngnid;
                                                                                                        do{
                                                                                                            trovato = true;
                                                                                                            System.out.println("Inserire il nuovo valore di " + parti[i]);
                                                                                                            ngnid = scan10.nextLine();
                                                                                                            
                                                                                                            if(ngnid.contains("\t") | ngnid.contains("-----")){
                                                                                                                System.out.println("Il Geoname ID non può contenere tab o i 5 trattini '-----'");
                                                                                                            }else{
                                                                                                                input_valido6 = true;
                                                                                                            }
                                                                                                            
                                                                                                        }while(!input_valido6);
                                                                                                        
                                                                                                        
                                                                                                        boolean esiste = false;
                                                                                                        //Controlla se l'area ngnid esiste
                                                                                                        //Se esiste inserirla nel centro
                                                                                                        
                                                                                                        File f4 = new File("CoordinateMonitoraggio.dati");
                                                                                                        Scanner scanf4 = new Scanner(f4);
                                                                                                        scanf4.nextLine();
                                                                                                        
                                                                                                        while(scanf4.hasNextLine()){
                                                                                                            String riga = scanf4.nextLine();
                                                                                                            String[] dati = riga.split("\t");
                                                                                                            if(dati[0].equals(ngnid)){
                                                                                                                esiste = true;
                                                                                                                break;
                                                                                                            }
                                                                                                        }
                                                                                                        
                                                                                                        if(esiste){
                                                                                                            boolean inserita = false;
                                                                                                            for(int k=2;k<parti.length;k++){
                                                                                                                if(parti[k].equals(ngnid)){
                                                                                                                    inserita = true;
                                                                                                                    break;
                                                                                                                }
                                                                                                            }
                                                                                                            
                                                                                                            if(inserita){
                                                                                                                System.out.println("Area gia' inserita in questo centro");
                                                                                                            }else{
                                                                                                                parti[i] = ngnid;
                                                                                                                String nuovo_centro = parti[0]+"\t"+parti[1];
                                                                                                                for(int j=2;j<parti.length;j++){
                                                                                                                    if(j == parti.length-1){
                                                                                                                        nuovo_centro = nuovo_centro+"\t"+parti[j];
                                                                                                                    }else{
                                                                                                                        nuovo_centro = nuovo_centro+"\t"+parti[j];
                                                                                                                    }
                                                                                                                }

                                                                                                                String riga_da_modificare = "";
                                                                                                                for(int h=0;h<parti.length;h++){
                                                                                                                    if(h == 0){
                                                                                                                        if(parti[h].equals(ngnid)){
                                                                                                                            riga_da_modificare = riga_da_modificare+qgnid;
                                                                                                                        }else{
                                                                                                                            riga_da_modificare = riga_da_modificare+parti[h];
                                                                                                                        }

                                                                                                                    }else{
                                                                                                                        if(parti[h].equals(ngnid)){
                                                                                                                            riga_da_modificare = riga_da_modificare+"\t"+qgnid;
                                                                                                                        }else{
                                                                                                                            riga_da_modificare = riga_da_modificare+"\t"+parti[h];
                                                                                                                        }

                                                                                                                    }

                                                                                                                }

                                                                                                                System.out.println(nuovo_centro);

                                                                                                                System.out.println(riga_da_modificare);

                                                                                                                String input = nuovo_centro+"-----"+riga_da_modificare;
                                                                                                                account.registraCentroAree(input, 6);
                                                                                                                System.out.println(nuovo_centro);
                                                                                                                parti = nuovo_centro.split("\t");
                                                                                                                centro_da_modificare = nuovo_centro;
                                                                                                            }
                                                                                                            
                                                                                                            
                                                                                                        }else{
                                                                                                            System.out.println("Il Geoname ID inserito non e' presente nel programma");
                                                                                                        }
                                                                                                        
                                                                                                    }
                                                                                                }
                                                                                                
                                                                                                if(!trovato){
                                                                                                    System.out.println("Geoname ID non trovato");
                                                                                                }
                                                                                                
                                                                                            }
                                                                                            
                                                                                            break;
                                                                                            
                                                                                        case 4:
                                                                                            token_scelta45 = false;
                                                                                            break;
                                                                                    }
                                                                                }while(token_scelta45);
                                                                                
                                                                                break;
                                                                            case 4:
                                                                                token_scelta34 = false;
                                                                                break;
                                                                        }

                                                                    }while(token_scelta34);
                                                                    
                                                                    
                                                                }
                                                                
                                                            }
                                                            
                                                            
                                                            break;
                                                        
                                                        case 4:
                                                            
                                                            boolean token_scelta46 = true;
                                                            Scanner scan17 = new Scanner(System.in);
                                                            do{
                                                                boolean input_valido7 = false;
                                                                String scelta2;
                                                                do{
                                                                    System.out.println("1. Visualizza tutti i centri di monitoraggio\n2. Cercane uno\n3. Indietro");
                                                                    scelta2 = scan17.nextLine();
                                                                    
                                                                    if(!account.isNumeric(scelta2)){
                                                                        System.out.println("Devi inserire un numero!");
                                                                    }else{
                                                                        input_valido7 = true;
                                                                    }
                                                                    
                                                                }while(!input_valido7);
                                                                
                                                                switch(Integer.valueOf(scelta2)){
                                                                    default:
                                                                        System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                                        break;
                                                                        
                                                                    case 1:
                                                                        File f = new File("CentroMonitoraggio.dati");
                                                                        Scanner scanft = new Scanner(f);
                                                                        if(Files.readAllBytes(f.toPath()).length == 0){
                                                                            System.out.println("Non e' presente nessun centro di monitoraggio");
                                                                        }else{
                                                                            System.out.println("NOME\tINDIRIZZO\tGEONAME ID DI AREE DI QUESTO CENTRO");
                                                                            while(scanft.hasNextLine()){
                                                                                String riga = scanft.nextLine();
                                                                                System.out.println(riga);
                                                                            }
                                                                        }
                                                                        break;
                                                                    case 2:
                                                                        Scanner scan16 = new Scanner(System.in);
                                                                        File f2 = new File("CentroMonitoraggio.dati");
                                                                        Scanner scanft2 = new Scanner(f2);
                                                                        if(Files.readAllBytes(f2.toPath()).length == 0){
                                                                            System.out.println("Non e' presente nessun centro di monitoraggio");
                                                                        }else{
                                                                            System.out.println("Inserisci il nome del centro che vuoi cercare");
                                                                            String nomec = scan16.nextLine();
                                                                            System.out.println("Inserisci l'indirizzo del centro che vuoi cercare");
                                                                            String indirizzoc = scan16.nextLine();
                                                                            
                                                                            boolean trovato = false;
                                                                            while(scanft2.hasNextLine()){
                                                                                String riga = scanft2.nextLine();
                                                                                String[] dati = riga.split("\t");
                                                                                if(dati[0].equals(nomec) & dati[1].equals(indirizzoc)){
                                                                                    System.out.println("NOME\tINDIRIZZO\tGEONAME ID DI AREE DI QUESTO CENTRO");
                                                                                    System.out.println(riga);
                                                                                    trovato = true;
                                                                                    break;
                                                                                }
                                                                            }
                                                                            
                                                                            if(!trovato){
                                                                                System.out.println("Il centro inserito non e' stato trovato");
                                                                            }
                                                                        }
                                                                        break;
                                                                    case 3:
                                                                        token_scelta46 = false;
                                                                        break;
                                                                }
                                                                
                                                            }while(token_scelta46);
                                                           
                                                            
                                                            
                                                            break;
                                                            
                                                        case 5:
                                                            token_scelta5 = false;
                                                            break;
                                                        
                                                        default:
                                                            System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                            break;

                                                    }
                                                    
                                                    
                                                }while(token_scelta5);
                                                
                                                break;
                                                
                                            case 3:
                                                token_scelta42 = false;
                                                break;
                                            
                                            default:
                                                System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                break;



                                        }
                                    }while(token_scelta42);
                                    
                                    
                                    break;
                                case 2:
                                    token_scelta4 = false;
                                    break;
                                
                                default:
                                    System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                    break;
                                    
                            }
                            
                            
                            
                        }else{
                            System.out.println("Non hai effettuato l'accesso");
                            boolean esito3 = false;
                            while(!esito3){
                                Account acc = new Account();
                                esito3 = cookie;
                                boolean input_valido2 = false;
                                String riprova;
                                do{
                                    System.out.println("1. Accedi\n2. Indietro");

                                    riprova = scan6.nextLine();
                                    if(!account.isNumeric(riprova)){
                                        System.out.println("Devi inserire un numero!");
                                    }else{
                                        input_valido2 = true;
                                    }
                                }while(!input_valido2);
                                                
                                switch(Integer.valueOf(riprova)){
                                                    
                                    case 1:
                                        cookie = acc.login();
                                        esito3 = cookie;
                                        break;
                                    case 2:
                                        esito3 = true;
                                        token_scelta4 = false;
                                        break;
                                                    
                                    default:
                                        System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                        break;
                                }
                                                
                            }
                        }
                        
                    }while(token_scelta4);
                    break;
                
                    
                    
                case 5:
                    //Gestione parametri climatici
                    boolean token_scelta5 = true;
                    do{
                     
                        if(cookie){
                            String sceltapc;
                                boolean input_valido = false;
                                Scanner scan7 = new Scanner(System.in);
                                do{
                                    System.out.println("1. Gestisci i parametri climatici su delle aree\n2. Indietro");
                                    sceltapc = scan7.nextLine();

                                    if(!account.isNumeric(sceltapc)){
                                        System.out.println("Devi inserire un numero!");
                                    }else{
                                        input_valido = true;
                                    }

                                }while(!input_valido);
                                
                                
                                switch(Integer.valueOf(sceltapc)){
                                    
                                    case 2:
                                        token_scelta5 = false;
                                        break;
                                    
                                    case 1:
                                        
                                        boolean token_scelta52 = true;
                                        
                                        String sceltapc2;
                                        Scanner scan2 = new Scanner(System.in);
                                        do{
                                            boolean input_valido2 = false;
                                            do{
                                                System.out.println("1. Aggiungi informazioni su un area");
                                                System.out.println("2. Modifica informazioni gia' inserite su un area");
                                                System.out.println("3. Elimina tutte le informazioni su un area");
                                                System.out.println("4. Indietro");
                                                
                                                sceltapc2 = scan2.nextLine();
                                                
                                                if(!account.isNumeric(sceltapc2)){
                                                    System.out.println("Devi inserire un numero!");
                                                }else{
                                                    input_valido2 = true;
                                                }
                                                
                                            }while(!input_valido2);
                                            
                                            Scanner scan3 = new Scanner(System.in);
                                            
                                            switch(Integer.valueOf(sceltapc2)){

                                                
                                                case 1:
                                                System.out.println("Inserisci il Geoname ID dell'area di cui vuoi inserire le informazioni");
                                                String geonameidarea = scan3.nextLine();
                                                
                                                boolean data_corretta = false;
                                                String datainformazioni = "";
                                                
                                                do{
                                                    System.out.println("Inserisci la data a cui fanno riferimento le informazioni formato giorno/mese/anno");
                                                    datainformazioni = scan3.nextLine();
                                                    
                                                    if(parametri_climatici.check_data(datainformazioni)){
                                                        data_corretta = true;
                                                    }
                                                    
                                                }while(!data_corretta);
                                                
                                                boolean esiste = parametri_climatici.esiste_area(geonameidarea);
                                                String[] dati_data = datainformazioni.split("/");
                                                File f = new File("."+File.separator+"Parametri"+File.separator+geonameidarea+"------"+dati_data[0]+dati_data[1]+dati_data[2]+".txt");
                                                boolean esiste2 = Files.exists(f.toPath());
                                                
                                                if(esiste2){
                                                
                                                    System.out.println("Esiste gia' un file dell'area con il Geoname ID inserito e con la data inserita");
                                                
                                                }else{
                                                    if(esiste){

                                                        Scanner scan5 = new Scanner(System.in);

                                                        //Vento
                                                        boolean vento_corretto = false;
                                                        String vento = "";

                                                        do{
                                                            System.out.println("Inserisci la velocita' del vento in Km/h");
                                                            String vel_vento = scan5.nextLine();
                                                            System.out.println("Inserisci un punteggio da 1 a 5 per la velocità del vento");
                                                            String vel_vento_punteggio = scan5.nextLine();
                                                            System.out.println("Inserisci note sulla velocita' del vento (Singola riga, max 256 caratteri, non inserire il carattere tab)");
                                                            String commenti_vento = scan5.nextLine();

                                                            if(vel_vento.equals("") | vel_vento_punteggio.equals("") | commenti_vento.equals("")){
                                                                System.out.println("Input vuoto");
                                                                
                                                            }else{
                                                            
                                                                if(!account.isNumeric(vel_vento) | !account.isNumeric(vel_vento_punteggio)){
                                                                    System.out.println("La velocita' del vento e il punteggio della velocita' del vento devono essere numeri, il punteggio deve essere compreso tra 1 e 5");
                                                                }else{
                                                                    if(Integer.valueOf(vel_vento) < 0| Integer.valueOf(vel_vento_punteggio) < 1 | Integer.valueOf(vel_vento_punteggio) > 5){
                                                                        System.out.println("La velocita' del vento e il punteggio della velocita' del vento devono essere numeri, il punteggio deve essere compreso tra 1 e 5");
                                                                    }else{
                                                                        if(commenti_vento.length() > 255 | commenti_vento.contains("\t")){
                                                                            System.out.println("I commenti non possono superari i 256 caratteri e non possono contenere il carattere tab");
                                                                        }else{
                                                                            vento = vel_vento+"\t"+vel_vento_punteggio+"\t"+commenti_vento;
                                                                            vento_corretto = true;
                                                                        }
                                                                    }
                                                                }
                                                            
                                                            }
                                                            
                                                            

                                                        }while(!vento_corretto);


                                                        //Umidità


                                                        boolean umidita_corretta = false;
                                                        String umidita = "";
                                                        do{
                                                            System.out.println("Inserisci la percentuale di umidita'");
                                                            String per_umi = scan5.nextLine();
                                                            System.out.println("Inserisci un punteggio da 1 a 5 per la percentuale di umidita'");
                                                            String per_umi_punteggio = scan5.nextLine();
                                                            System.out.println("Inserisci note sulla percentuale di umidita' (Singola riga, max 256 caratteri, non inserire il carattere tab)");
                                                            String commenti_umi = scan5.nextLine();

                                                            if(per_umi.equals("") | per_umi_punteggio.equals("") | commenti_umi.equals("")){
                                                                System.out.println("Input vuoto");
                                                                
                                                            }else{
                                                            
                                                                if(!account.isNumeric(per_umi) | !account.isNumeric(per_umi_punteggio)){
                                                                    System.out.println("La percentuale deve essere un numero compreso tra 0 e 100, il punteggio deve essere un numero compreso tra 1 e 5");
                                                                }else{
                                                                    if(Integer.valueOf(per_umi) < 0 | Integer.valueOf(per_umi) > 100 | Integer.valueOf(per_umi_punteggio) < 1 | Integer.valueOf(per_umi_punteggio) > 5){
                                                                        System.out.println("La percentuale deve essere un numero compreso tra 0 e 100, il punteggio deve essere un numero compreso tra 1 e 5");
                                                                    }else{
                                                                        if(commenti_umi.length() > 256){
                                                                            System.out.println("I commenti non possono superari i 256 caratteri e non possono contenere il carattere tab");
                                                                        }else{
                                                                            umidita = per_umi+"\t"+per_umi_punteggio+"\t"+commenti_umi;
                                                                            umidita_corretta = true;
                                                                        }
                                                                    }


                                                                }
                                                            }
                                                            
                                                            

                                                        }while(!umidita_corretta);



                                                        //Pressione
                                                        boolean pressione_corretta = false;
                                                        String pressione = "";

                                                        do{

                                                            System.out.println("Inserisci la pressione in hPa");
                                                            String pressione_hpa = scan5.nextLine();
                                                            System.out.println("Inserisci un punteggio da 1 a 5 per la pressione");
                                                            String pressione_punteggio = scan5.nextLine();
                                                            System.out.println("Inserisci note sulla pressione (Singola riga, max 256 caratteri)");
                                                            String commenti_pressione = scan5.nextLine();
                                                            
                                                            if(pressione_hpa.equals("") | pressione_punteggio.equals("") | commenti_pressione.equals("")){
                                                                System.out.println("Input vuoto");
                                                                
                                                            }else{
                                                            
                                                                if(!account.isNumeric(pressione_hpa) | !account.isNumeric(pressione_punteggio) ){
                                                                  System.out.println("La pressione deve essere un numero maggiore di 0 e il punteggio deve essere un numero compresa tra 1 e 5");
                                                              }else{
                                                                  if(Integer.valueOf(pressione_hpa) < 0 | Integer.valueOf(pressione_punteggio) < 1 | Integer.valueOf(pressione_punteggio) > 5){
                                                                      System.out.println("La pressione deve essere un numero maggiore di 0 e il punteggio deve essere un numero compresa tra 1 e 5");
                                                                  }else{
                                                                      if(commenti_pressione.length() > 256 | commenti_pressione.contains("\t")){
                                                                          System.out.println("I commenti non possono superare i 256 caratteri di lunghezza e non possono contenere il carattere tab");
                                                                      }else{
                                                                          pressione = pressione_hpa+"\t"+pressione_punteggio+"\t"+commenti_pressione;
                                                                          pressione_corretta = true;
                                                                      }
                                                                  }

                                                              }  
                                                                
                                                            }

                                                            



                                                        }while(!pressione_corretta);



                                                        //Temperatura

                                                        boolean temperatura_corretta = false;
                                                        String temperatura = "";
                                                        do{

                                                            System.out.println("Inserisci la temperatura in gradi");
                                                            String temp = scan5.nextLine();
                                                            System.out.println("Inserisci un punteggio da 1 a 5 per la temperatura");
                                                            String temp_punteggio = scan5.nextLine();
                                                            System.out.println("Inserisci note sulla temperatura (Singola riga, max 256 caratteri, non mettere il carattere tab)");
                                                            String commenti_temp = scan5.nextLine();

                                                            if(temp.equals("") | temp_punteggio.equals("") | commenti_temp.equals("")){
                                                                System.out.println("Input vuoto");
                                                                
                                                            }else{
                                                            
                                                              if(!account.isNumeric(temp) | !account.isNumeric(temp_punteggio)){
                                                                System.out.println("La temperatura e il punteggio devono essere un numero, il punteggio deve essere compreso tra 1 e 5");
                                                            }else{
                                                                if(Integer.valueOf(temp_punteggio) < 1 | Integer.valueOf(temp_punteggio) > 5){
                                                                    System.out.println("La temperatura e il punteggio devono essere un numero, il punteggio deve essere compreso tra 1 e 5");
                                                                }else{
                                                                    if(commenti_temp.length() > 256 | commenti_temp.contains("\t")){
                                                                        System.out.println("I commenti non possono superare i 256 caratteri di lunghezza e non posono contenere il carattere tab.");
                                                                    }else{
                                                                        temperatura = temp+"\t"+temp_punteggio+"\t"+commenti_temp;
                                                                        temperatura_corretta = true;
                                                                    }
                                                                }

                                                            }  
                                                            
                                                            }
                                                            
                                                            



                                                        }while(!temperatura_corretta);

                                                        //Precipitazioni

                                                        boolean precipitazioni_corretta = false;
                                                        String commenti_mm_prec = "";

                                                        String precipitazioni = "";

                                                        do{
                                                            System.out.println("Inserisci i millimetri delle precipitazioni");
                                                            String mm_prec = scan5.nextLine();
                                                            System.out.println("Inserisci un punteggio da 1 a 5 per le precipitazioni");
                                                            String mm_prec_punteggio = scan5.nextLine();
                                                            System.out.println("Inserisci note sulle precipitazioni (Singola riga, max 256 caratteri, non inserire il carattere tab)");
                                                            commenti_mm_prec = scan5.nextLine();
                                                            
                                                            if(mm_prec.equals("") | mm_prec_punteggio.equals("") | commenti_mm_prec.equals("")){
                                                                System.out.println("Input vuoto");
                                                                
                                                            }else{
                                                                if(!account.isNumeric(mm_prec) |  !account.isNumeric(mm_prec_punteggio)){
                                                                 System.out.println("I millimentri di pioggia devono essere numeri maggiori di -1 e il punteggio dei millimentri di pioggia deve essere un numero compreso tra 1 e 5");
                                                             }else{
                                                                 if(Integer.valueOf(mm_prec) < 0 | Integer.valueOf(mm_prec_punteggio) < 1 | Integer.valueOf(mm_prec_punteggio) > 5){
                                                                     System.out.println("I millimentri di pioggia devono essere numeri maggiori di -1 e il punteggio dei millimentri di pioggia deve essere un numero compreso tra 1 e 5");
                                                                 }else{
                                                                     if(commenti_mm_prec.length() > 256 | commenti_mm_prec.contains("\t")){
                                                                         System.out.println("I commenti sui millimentri di pioggia non possono superare i 256 caratteri e non devono contenere il carattere tab");
                                                                     }else{
                                                                         precipitazioni = mm_prec+"\t"+mm_prec_punteggio+"\t"+commenti_mm_prec;
                                                                         precipitazioni_corretta = true;
                                                                     }
                                                                 }

                                                             } 
                                                            }

                                                            

                                                        }while(!precipitazioni_corretta);






                                                        //Altitudine dei ghiacciai
                                                        boolean altitudine_corretta = false;
                                                        String altitudine_ghiacciai = "";

                                                        do{
                                                            System.out.println("Inserisci l'altitudine dei ghiacciai in metri");
                                                            String metri = scan5.nextLine();
                                                            System.out.println("Inserisci un punteggio da 1 a 5 per l'altitudine dei ghiacciai");
                                                            String metri_punteggio = scan5.nextLine();
                                                            System.out.println("Inserisci note sull'altitudine (Singola riga, max 256 caratteri)");
                                                            String commenti_metri = scan5.nextLine();
                                                            if(metri.equals("") | metri_punteggio.equals("") | commenti_metri.equals("")){
                                                                System.out.println("Input vuoto");
                                                                
                                                            }else{
                                                                if(!account.isNumeric(metri)){
                                                                    System.out.println("L'altitudine dei ghiacciai deve essere un numero positivo e il punteggio deve essere un numero compreso tra 1 e 5");
                                                                }else{
                                                                    if(Integer.valueOf(metri) < 0 | Integer.valueOf(metri_punteggio) < 1 | Integer.valueOf(metri_punteggio) > 5){
                                                                        System.out.println("L'altitudine dei ghiacciai deve essere un numero positivo e il punteggio deve essere un numero compreso tra 1 e 5");
                                                                    }else{
                                                                        if(commenti_metri.length() > 256 | commenti_metri.contains("\t")){
                                                                            System.out.println("I commenti sull'altitudine dei ghiacciai non possono superare i 256 caaratteri e non possono contenere il carattere tab");
                                                                        }else{
                                                                           altitudine_ghiacciai = metri+"\t"+metri_punteggio+"\t"+commenti_metri;
                                                                           altitudine_corretta = true;
                                                                        }
                                                                    }

                                                                }
                                                            }
                                                            




                                                        }while(!altitudine_corretta);

                                                        boolean massa_corretta = false;
                                                        String massa_ghiacciai = "";
                                                        do{

                                                        //Massa dei ghiacciai
                                                        System.out.println("Inserisci la massa dei ghiacciai in Kg");
                                                        String kg = scan5.nextLine();
                                                        System.out.println("Inserisci un punteggio da 1 a 5 per la massa dei ghiacciai");
                                                        String kg_punteggio = scan5.nextLine();
                                                        System.out.println("Inserisci note sulla massa (Singola riga, max 256 caratteri, non inserire il carattere tab)");
                                                        String commenti_kg = scan5.nextLine();
                                                            
                                                        if(kg.equals("") | kg_punteggio.equals("") | commenti_kg.equals("")){
                                                                System.out.println("Input vuoto");
                                                                
                                                        }else{
                                                        
                                                            if(!account.isNumeric(kg)){
                                                                System.out.println("La massa dei ghiacciai deve essere un numero positivo e il punteggio della massa dei ghiacciai deve essere un numero compreso tra 1 e 5");
                                                            }else{
                                                                if(Integer.valueOf(kg) < 0 | Integer.valueOf(kg_punteggio) < 1 | Integer.valueOf(kg_punteggio) > 5){
                                                                    System.out.println("La massa dei ghiacciai deve essere un numero positivo e il punteggio della massa dei ghiacciai deve essere un numero compreso tra 1 e 5");
                                                                }else{
                                                                    if(commenti_kg.length() > 256 | commenti_kg.contains("\t")){
                                                                        System.out.println("I commenti della massa dei ghiacciai non possono superare i 256 caratteri di lunghezza e non possono contenere il carattere tab");
                                                                    }else{
                                                                        massa_ghiacciai = kg+"\t"+kg_punteggio+"\t"+commenti_kg;
                                                                        massa_corretta = true;
                                                                    }
                                                                }

                                                            }
                                                        }
                                                        
                                                        




                                                        }while(!massa_corretta);

                                                        parametri_climatici.inserisciParametriClimatici(geonameidarea, datainformazioni, vento, umidita, pressione, temperatura, precipitazioni, altitudine_ghiacciai, massa_ghiacciai);

                                                    }else{
                                                        System.out.println("Il Geoname ID inserito non esiste");
                                                    }
                                                
                                                }
                                                break;
                                                    
                                        case 2:
                                        
                                        token_scelta5 = true;
                                        Scanner scan8 = new Scanner(System.in);
                                        System.out.println("Inserisci il Geoname ID dell'area di cui vuoi modificare i parametri climatici");
                                        String g_mod_parametri = scan8.nextLine();
                                        System.out.println("Inserisci la data a cui fanno riferimento le informazioni che vuoi modificare");
                                        String mod_data_parametri = scan8.nextLine();
                                        
                                        String[] dati_data2 = mod_data_parametri.split("/");
                                        File f2 = new File("."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt");
                                        if(!Files.exists(f2.toPath()) ){
                                            System.out.println("Non sono stati trovati dati sulle aree con Geoname ID " + g_mod_parametri + " nella data " + mod_data_parametri);
                                        }else{
                                            
                                            do{
                                               boolean input_valido3 = false;
                                               String input_mod_p;
                                               do{
                                               
                                                    System.out.println("Quali dati vuoi modificare?");
                                                    System.out.println("1. I dati del vento");
                                                    System.out.println("2. I dati dell'umidita' ");
                                                    System.out.println("3. I dati della pressione");
                                                    System.out.println("4. I dati della temperatura");
                                                    System.out.println("5. I dati delle precipitazioni");
                                                    System.out.println("6. I dati sull'altitudine dei ghiacciai");
                                                    System.out.println("7. I dati sulla massa dei ghiacciai");
                                                    System.out.println("8. Cambia il Geoname ID al quale si riferiscono i dati");
                                                    System.out.println("9. Cambia la data a cui si riferiscono i dati");
                                                    System.out.println("10. Indietro");
                                                    
                                                    input_mod_p = scan8.nextLine();
                                                    
                                                    if(!account.isNumeric(input_mod_p)){
                                                        System.out.println("Devi inserire un numero!");
                                                    }else{
                                                        input_valido3 = true;
                                                    }
                                               
                                               }while(!input_valido3);
                                               
                                               Scanner scan_par = new Scanner(f2);
                                               String[] parti2 = new String[9];
                                               
                                               for(int i = 0; i<7; i++){
                                                   parti2[i] = scan_par.nextLine();
                                               }
                                               
                                               parti2[7] = g_mod_parametri;
                                               parti2[8] = dati_data2[0]+dati_data2[1]+dati_data2[2];
                                               
                                               scan_par.close();
                                               
                                               Scanner scan10 = new Scanner(System.in);
                                               
                                               
                                               switch(Integer.valueOf(input_mod_p)){
                                                   
                                                   case 1:
                                                       boolean s_vento_v = false;
                                                       String scelta_vento;
                                                       String[] sub = parti2[0].split("\t");
                                                       do{
                                                            System.out.println("Quali dati del vento vuoi modificare?");
                                                            System.out.println("1. Velocita'");
                                                            System.out.println("2. Punteggio");
                                                            System.out.println("3. Commenti");
                                                            System.out.println("4. Indietro");
                                                            
                                                            scelta_vento = scan10.nextLine();
                                                            
                                                            if(!account.isNumeric(scelta_vento)){
                                                                System.out.println("Devi inserire un numero!");
                                                            }else{
                                                                s_vento_v = true;
                                                            }
                                                       
                                                       }while(!s_vento_v);
                                                       
                                                       Scanner scan11 = new Scanner(System.in);
                                                       
                                                       switch(Integer.valueOf(scelta_vento)){
                                                           
                                                           case 1:
                                                               System.out.println("Inserisci la nuova velocita' del vento");
                                                               String n_vento = scan11.nextLine();
                                                               
                                                               if(n_vento.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_vento)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_vento) < 0){
                                                                           System.out.println("Devi inserire un numero positivo!");
                                                                       }else{
                                                                           String nuovo_vento = n_vento+"\t"+sub[1]+"\t"+sub[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", 0);
                                                                           sub[0] = n_vento;
                                                                       }
                                                                   }
                                                                }
                                                               
                                                               break;
                                                               
                                                           case 2:
                                                               
                                                               System.out.println("Inserisci il nuovo punteggio della velocita' del vento");
                                                               String n_vento_pun = scan11.nextLine();
                                                               
                                                               if(n_vento_pun.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_vento_pun)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_vento_pun) < 0 | Integer.valueOf(n_vento_pun) > 5){
                                                                           System.out.println("Devi inserire un numero positivo compreso tra 1 e 5");
                                                                       }else{
                                                                           String nuovo_vento = sub[0]+"\t"+n_vento_pun+"\t"+sub[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", 0);
                                                                           sub[1] = n_vento_pun;
                                                                       }
                                                                   }
                                                                }
                                                               
                                                               
                                                               break;
                                                               
                                                           case 3:
                                                               
                                                               System.out.println("Inserisci i nuovi commenti della velocita' del vento");
                                                               String n_vento_com = scan11.nextLine();
                                                               
                                                               if(n_vento_com.equals("") | n_vento_com.contains("\t") | n_vento_com.length() > 256){
                                                                    System.out.println("L'input non può essere vuoto, non può contenere il carattere tab e non puo' essere piu' lungo di 256 caratteri");
                                                                
                                                                }else{
                                                                   String nuovo_vento = sub[0]+"\t"+sub[1]+"\t"+n_vento_com;
                                                                   parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", 0);
                                                                   sub[2] = n_vento_com;
                                                                }
                                                               
                                                               
                                                               break;
                                                            
                                                               
                                                           case 4:
                                                               break;
                                                           
                                                           default:
                                                               System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                               break;
                                                       }
                                                       
                                                       break;
                                                       
                                                   case 2:
                                                       
                                                       boolean s_umi_v = false;
                                                       String scelta_umi;
                                                       String[] sub2 = parti2[1].split("\t");
                                                       do{
                                                            System.out.println("Quali dati dell'umidita' vuoi modificare?");
                                                            System.out.println("1. Percentuale");
                                                            System.out.println("2. Punteggio");
                                                            System.out.println("3. Commenti");
                                                            System.out.println("4. Indietro");
                                                            
                                                            scelta_umi = scan10.nextLine();
                                                            
                                                            if(!account.isNumeric(scelta_umi)){
                                                                System.out.println("Devi inserire un numero!");
                                                            }else{
                                                                s_umi_v = true;
                                                            }
                                                       
                                                       }while(!s_umi_v);
                                                       
                                                       Scanner scan12 = new Scanner(System.in);
                                                       
                                                       switch(Integer.valueOf(scelta_umi)){
                                                           
                                                           case 1:
                                                               System.out.println("Inserisci la nuova percentuale di umidita'");
                                                               String n_umi = scan12.nextLine();
                                                               
                                                               if(n_umi.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_umi)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_umi) < 0 | Integer.valueOf(n_umi) > 100){
                                                                           System.out.println("Devi inserire un numero positivo compreso tra 0 e 100");
                                                                       }else{
                                                                           String nuovo_vento = n_umi+"\t"+sub2[1]+"\t"+sub2[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub2[0] = n_umi;
                                                                       }
                                                                   }
                                                                }
                                                               
                                                               break;
                                                               
                                                           case 2:
                                                               
                                                               System.out.println("Inserisci il nuovo punteggio della percentuale di umidita' ");
                                                               String n_vento_pun = scan12.nextLine();
                                                               
                                                               if(n_vento_pun.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_vento_pun)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_vento_pun) < 0 | Integer.valueOf(n_vento_pun) > 5){
                                                                           System.out.println("Devi inserire un numero positivo compreso tra 1 e 5");
                                                                       }else{
                                                                           String nuovo_vento = sub2[0]+"\t"+n_vento_pun+"\t"+sub2[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub2[1] = n_vento_pun;
                                                                       }
                                                                   }
                                                                }
                                                               
                                                               
                                                               break;
                                                               
                                                           case 3:
                                                               
                                                               System.out.println("Inserisci i nuovi commenti sulla percentuale di umidita'");
                                                               String n_umi_com = scan12.nextLine();
                                                               
                                                               if(n_umi_com.equals("") | n_umi_com.contains("\t") | n_umi_com.length() >256){
                                                                    System.out.println("L'input non puo' essere vuoto, non puo' contenere il carattere tab e non puo' essere più lungo di 256 caratteri");
                                                                
                                                                }else{
                                                                   String nuovo_vento = sub2[0]+"\t"+sub2[1]+"\t"+n_umi_com;
                                                                   parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                   sub2[2] = n_umi_com;
                                                                }
                                                               
                                                               
                                                               break;
                                                               
                                                           
                                                           case 4:
                                                               break;
                                                               
                                                           default:
                                                               System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                               break;
                                                       }
                                                       
                                                       break;
                                                       
                                                       
                                                   case 3:
                                                       
                                                       boolean s_press_v = false;
                                                       String scelta_press;
                                                       String[] sub3 = parti2[2].split("\t");
                                                       do{
                                                            System.out.println("Quali dati della pressione vuoi modificare?");
                                                            System.out.println("1. Valore pressione");
                                                            System.out.println("2. Punteggio");
                                                            System.out.println("3. Commenti");
                                                            System.out.println("4. Indietro");
                                                            
                                                            scelta_press = scan10.nextLine();
                                                            
                                                            if(!account.isNumeric(scelta_press)){
                                                                System.out.println("Devi inserire un numero!");
                                                            }else{
                                                                s_press_v = true;
                                                            }
                                                       
                                                       }while(!s_press_v);
                                                       
                                                       Scanner scan13 = new Scanner(System.in);
                                                       
                                                       switch(Integer.valueOf(scelta_press)){
                                                           
                                                           case 1:
                                                               System.out.println("Inserisci il nuovo valore della pressione");
                                                               String n_press = scan13.nextLine();
                                                               
                                                               if(n_press.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_press)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_press) < 0){
                                                                           System.out.println("Devi inserire un numero positivo!");
                                                                       }else{
                                                                           String nuovo_vento = n_press+"\t"+sub3[1]+"\t"+sub3[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub3[0] = n_press;
                                                                       }
                                                                   }
                                                                }
                                                               
                                                               break;
                                                               
                                                           case 2:
                                                               
                                                               System.out.println("Inserisci il nuovo punteggio della pressione");
                                                               String n_press_pun = scan13.nextLine();
                                                               
                                                               if(n_press_pun.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_press_pun)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_press_pun) < 0 | Integer.valueOf(n_press_pun) > 5){
                                                                           System.out.println("Devi inserire un numero positivo compreso tra 1 e 5");
                                                                       }else{
                                                                           String nuovo_vento = sub3[0]+"\t"+n_press_pun+"\t"+sub3[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub3[1] = n_press_pun;
                                                                       }
                                                                   }
                                                                }
                                                               
                                                               
                                                               break;
                                                               
                                                           case 3:
                                                               
                                                               System.out.println("Inserisci i nuovi commenti sulla pressione");
                                                               String n_press_com = scan13.nextLine();
                                                               
                                                               if(n_press_com.equals("") | n_press_com.contains("\t") | n_press_com.length() >256){
                                                                    System.out.println("L'input non puo' essere vuoto, non puo' contenere il carattere tab e non puo' essere più lungo di 256 caratteri");
                                                                
                                                                }else{
                                                                   String nuovo_vento = sub3[0]+"\t"+sub3[1]+"\t"+n_press_com;
                                                                   parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                   sub3[2] = n_press_com;
                                                                }
                                                               
                                                               
                                                               break;
                                                           
                                                           case 4:
                                                               break;
                                                           
                                                           default:
                                                               System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                               break;
                                                       }
                                                       
                                                       
                                                       break;
                                                       
                                                   case 4:
                                                       
                                                       
                                                       boolean s_temp_v = false;
                                                       String scelta_temp;
                                                       String[] sub4 = parti2[3].split("\t");
                                                       do{
                                                            System.out.println("Quali dati della temperatura vuoi modificare?");
                                                            System.out.println("1. Gradi temperatura");
                                                            System.out.println("2. Punteggio");
                                                            System.out.println("3. Commenti");
                                                            System.out.println("4. Indietro");
                                                            
                                                            scelta_press = scan10.nextLine();
                                                            
                                                            if(!account.isNumeric(scelta_press)){
                                                                System.out.println("Devi inserire un numero!");
                                                            }else{
                                                                s_temp_v = true;
                                                            }
                                                       
                                                       }while(!s_temp_v);
                                                       
                                                       Scanner scan14 = new Scanner(System.in);
                                                       
                                                       switch(Integer.valueOf(scelta_press)){
                                                           
                                                           case 1:
                                                               System.out.println("Inserisci la nuova temperatura");
                                                               String n_temp = scan14.nextLine();
                                                               
                                                               if(n_temp.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_temp)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{                            
                                                                           String nuovo_vento = n_temp+"\t"+sub4[1]+"\t"+sub4[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub4[0] = n_temp;
                                                                   }
                                                                }
                                                               
                                                               break;
                                                               
                                                           case 2:
                                                               
                                                               System.out.println("Inserisci il nuovo punteggio della temperatura");
                                                               String n_press_pun = scan14.nextLine();
                                                               
                                                               if(n_press_pun.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_press_pun)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_press_pun) < 0 | Integer.valueOf(n_press_pun) > 5){
                                                                           System.out.println("Devi inserire un numero positivo compreso tra 1 e 5");
                                                                       }else{
                                                                           String nuovo_vento = sub4[0]+"\t"+n_press_pun+"\t"+sub4[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub4[1] = n_press_pun;
                                                                       }
                                                                   }
                                                                }
                                                               
                                                               
                                                               break;
                                                               
                                                           case 3:
                                                               
                                                               System.out.println("Inserisci i nuovi commenti sulla temperatura");
                                                               String n_press_com = scan14.nextLine();
                                                               
                                                               if(n_press_com.equals("") | n_press_com.contains("\t") | n_press_com.length() >256){
                                                                    System.out.println("L'input non puo' essere vuoto, non puo' contenere il carattere tab e non puo' essere più lungo di 256 caratteri");
                                                                
                                                                }else{
                                                                   String nuovo_vento = sub4[0]+"\t"+sub4[1]+"\t"+n_press_com;
                                                                   parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                   sub4[2] = n_press_com;
                                                                }
                                                               
                                                               
                                                               break;
                                                             
                                                           case 4:
                                                               break;
                                                           
                                                           default:
                                                               System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                               break;
                                                       }
                                                       
                                                       break;
                                                       
                                                   case 5:
                                                       
                                                       boolean s_prec_v = false;
                                                       String scelta_prec;
                                                       String[] sub5 = parti2[4].split("\t");
                                                       do{
                                                            System.out.println("Quali dati delle precipitazioni vuoi modificare?");
                                                            System.out.println("1. Millimetri");
                                                            System.out.println("2. Punteggio");
                                                            System.out.println("3. Commenti");
                                                            System.out.println("4. Indietro");
                                                            
                                                            scelta_prec = scan10.nextLine();
                                                            
                                                            if(!account.isNumeric(scelta_prec)){
                                                                System.out.println("Devi inserire un numero!");
                                                            }else{
                                                                s_prec_v = true;
                                                            }
                                                       
                                                       }while(!s_prec_v);
                                                       
                                                       Scanner scan15 = new Scanner(System.in);
                                                       
                                                       switch(Integer.valueOf(scelta_prec)){
                                                           
                                                           case 1:
                                                               System.out.println("Inserisci i nuovi millimetri di pioggia");
                                                               String n_temp = scan15.nextLine();
                                                               
                                                               if(n_temp.equals("")){
                                                                   System.out.println("Input vuoto!");
                                                               }else{
                                                                   if(!account.isNumeric(n_temp)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_temp) < 0){
                                                                           System.out.println("Devi inserire un numero positivo!");
                                                                       }else{
                                                                          String nuovo_vento = n_temp+"\t"+sub5[1]+"\t"+sub5[2];
                                                                          parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                          sub5[0] = n_temp;
                                                                       }
                                                                   }
                                                               }
                                                               
                                                               
                                                               break;
                                                               
                                                           case 2:
                                                               
                                                               System.out.println("Inserisci il nuovo punteggio delle precipitazioni");
                                                               String n_press_pun = scan15.nextLine();
                                                               
                                                               if(n_press_pun.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_press_pun)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_press_pun) < 0 | Integer.valueOf(n_press_pun) > 5){
                                                                           System.out.println("Devi inserire un numero positivo compreso tra 1 e 5");
                                                                       }else{
                                                                           String nuovo_vento = sub5[0]+"\t"+n_press_pun+"\t"+sub5[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub5[1] = n_press_pun;
                                                                       }
                                                                   }
                                                                }
                                                               
                                                               
                                                               break;
                                                               
                                                           case 3:
                                                               
                                                               System.out.println("Inserisci i nuovi commenti sulle precipitazioni");
                                                               String n_press_com = scan15.nextLine();
                                                               
                                                               if(n_press_com.equals("") | n_press_com.contains("\t") | n_press_com.length() >256){
                                                                    System.out.println("L'input non puo' essere vuoto, non puo' contenere il carattere tab e non puo' essere più lungo di 256 caratteri");
                                                                
                                                                }else{
                                                                   String nuovo_vento = sub5[0]+"\t"+sub5[1]+"\t"+n_press_com;
                                                                   parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                   sub5[2] = n_press_com;
                                                                }
                                                               
                                                               
                                                               break;
                                                           
                                                           case 4:
                                                               break;
                                                           
                                                           default:
                                                               System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                               break;
                                                       }
                                                       
                                                       
                                                       break;
                                                       
                                                   case 6:
                                                       
                                                       boolean s_alt_v = false;
                                                       String scelta_alt;
                                                       String[] sub6 = parti2[5].split("\t");
                                                       do{
                                                            System.out.println("Quali dati dell'altitudine dei ghiacciai vuoi modificare?");
                                                            System.out.println("1. Altitudine");
                                                            System.out.println("2. Punteggio");
                                                            System.out.println("3. Commenti");
                                                            System.out.println("4. Indietro");
                                                            
                                                            scelta_alt = scan10.nextLine();
                                                            
                                                            if(!account.isNumeric(scelta_alt)){
                                                                System.out.println("Devi inserire un numero!");
                                                            }else{
                                                                s_alt_v = true;
                                                            }
                                                       
                                                       }while(!s_alt_v);
                                                       
                                                       Scanner scan16 = new Scanner(System.in);
                                                       
                                                       switch(Integer.valueOf(scelta_alt)){
                                                           
                                                           case 1:
                                                               System.out.println("Inserisci la nuova altitudine");
                                                               String n_temp = scan16.nextLine();
                                                               
                                                               if(n_temp.equals("")){
                                                                   System.out.println("Input vuoto!");
                                                               }else{
                                                                   if(!account.isNumeric(n_temp)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_temp) < 0){
                                                                           System.out.println("Devi inserire un numero positivo!");
                                                                       }else{
                                                                           String nuovo_vento = n_temp+"\t"+sub6[1]+"\t"+sub6[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub6[0] = n_temp;
                                                                       }
                                                                   }
                                                               }
                                                               
                                                               
                                                               break;
                                                               
                                                           case 2:
                                                               
                                                               System.out.println("Inserisci il nuovo punteggio dell'altitudine dei ghiacciai");
                                                               String n_press_pun = scan16.nextLine();
                                                               
                                                               if(n_press_pun.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_press_pun)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_press_pun) < 0 | Integer.valueOf(n_press_pun) > 5){
                                                                           System.out.println("Devi inserire un numero positivo compreso tra 1 e 5");
                                                                       }else{
                                                                           String nuovo_vento = sub6[0]+"\t"+n_press_pun+"\t"+sub6[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub6[1] = n_press_pun;
                                                                       }
                                                                   }
                                                                }
                                                               
                                                               
                                                               break;
                                                               
                                                           case 3:
                                                               
                                                               System.out.println("Inserisci i nuovi commenti dell'altitudine dei ghiacciai");
                                                               String n_press_com = scan16.nextLine();
                                                               
                                                               if(n_press_com.equals("") | n_press_com.contains("\t") | n_press_com.length() >256){
                                                                    System.out.println("L'input non puo' essere vuoto, non puo' contenere il carattere tab e non puo' essere più lungo di 256 caratteri");
                                                                
                                                                }else{
                                                                   String nuovo_vento = sub6[0]+"\t"+sub6[1]+"\t"+n_press_com;
                                                                   parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                   sub6[2] = n_press_com;
                                                                }
                                                               
                                                               
                                                               break;
                                                            
                                                           case 4:
                                                               break;
                                                           
                                                           default:
                                                               System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                               break;
                                                       }
                                                       
                                                       
                                                       break;
                                                       
                                                   case 7:
                                                       
                                                       boolean s_mass_v = false;
                                                       String scelta_mass;
                                                       String[] sub7 = parti2[6].split("\t");
                                                       do{
                                                            System.out.println("Quali dati della massa dei ghiacciai vuoi modificare?");
                                                            System.out.println("1. Massa");
                                                            System.out.println("2. Punteggio");
                                                            System.out.println("3. Commenti");
                                                            System.out.println("4. Indietro");
                                                            
                                                            scelta_mass = scan10.nextLine();
                                                            
                                                            if(!account.isNumeric(scelta_mass)){
                                                                System.out.println("Devi inserire un numero!");
                                                            }else{
                                                                s_mass_v = true;
                                                            }
                                                       
                                                       }while(!s_mass_v);
                                                       
                                                       Scanner scan17 = new Scanner(System.in);
                                                       
                                                       switch(Integer.valueOf(scelta_mass)){
                                                           
                                                           case 1:
                                                               System.out.println("Inserisci la nuova massa");
                                                               String n_temp = scan17.nextLine();
                                                               
                                                               if(n_temp.equals("")){
                                                                   System.out.println("Input vuoto!");
                                                               }else{
                                                                   if(!account.isNumeric(n_temp)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_temp) < 0){
                                                                           System.out.println("Devi inserire un numero positivo!");
                                                                       }else{
                                                                            String nuovo_vento = n_temp+"\t"+sub7[1]+"\t"+sub7[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub7[0] = n_temp;
                                                                       }
                                                                   }
                                                               }
                                                               
                                                               break;
                                                               
                                                           case 2:
                                                               
                                                               System.out.println("Inserisci il nuovo punteggio della massa dei ghiacciai");
                                                               String n_press_pun = scan17.nextLine();
                                                               
                                                               if(n_press_pun.equals("")){
                                                                    System.out.println("Input vuoto!");
                                                                
                                                                }else{
                                                                   if(!account.isNumeric(n_press_pun)){
                                                                       System.out.println("Devi inserire un numero!");
                                                                   }else{
                                                                       if(Integer.valueOf(n_press_pun) < 0 | Integer.valueOf(n_press_pun) > 5){
                                                                           System.out.println("Devi inserire un numero positivo compreso tra 1 e 5");
                                                                       }else{
                                                                           String nuovo_vento = sub7[0]+"\t"+n_press_pun+"\t"+sub7[2];
                                                                           parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                           sub7[1] = n_press_pun;
                                                                       }
                                                                   }
                                                                }
                                                               
                                                               
                                                               break;
                                                               
                                                           case 3:
                                                               
                                                               System.out.println("Inserisci i nuovi commenti della massa dei ghiacciai");
                                                               String n_press_com = scan17.nextLine();
                                                               
                                                               if(n_press_com.equals("") | n_press_com.contains("\t") | n_press_com.length() >256){
                                                                    System.out.println("L'input non puo' essere vuoto, non puo' contenere il carattere tab e non puo' essere più lungo di 256 caratteri");
                                                                
                                                                }else{
                                                                   String nuovo_vento = sub7[0]+"\t"+sub7[1]+"\t"+n_press_com;
                                                                   parametri_climatici.modifica(nuovo_vento,"."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt", Integer.valueOf(input_mod_p)-1);
                                                                   sub7[2] = n_press_com;
                                                                }
                                                               
                                                               
                                                               break;
                                                            
                                                           case 4:
                                                               break;
                                                           
                                                           default:
                                                               System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                               break;
                                                       }
                                                       
                                                       break;
                                                       
                                                   case 8:
                                                       //Modifica Geoname ID
                                                                                                              
                                                       System.out.println("Inserisci il nuovo Geoname ID");
                                                       Scanner scan5 = new Scanner(System.in);
                                                       String mod_geoname = scan5.nextLine();
                                                       
                                                       if(!parametri_climatici.esiste_area(mod_geoname)){
                                                           System.out.println("Il Geoname ID inserito non appartiene a nessun area!");
                                                       }else{
                                                            if(mod_geoname.contains("-----")){
                                                                System.out.println("Il nome non può contenere '-----'");
                                                            }else{
                                                                File fe = new File("."+File.separator+"Parametri"+File.separator+mod_geoname+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt");

                                                                 if(fe.exists()){
                                                                     System.out.println("Esiste gia' un file con Geoname ID: " + mod_geoname + " e data " + dati_data2[0]+"/"+dati_data2[1]+"/"+dati_data2[2]);
                                                                 }else{
                                                                     String n_gnid_f = "."+File.separator+"Parametri"+File.separator+mod_geoname+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt";
                                                                     String vecchio = "."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt";
                                                                     parametri_climatici.modifica(n_gnid_f+"-------"+vecchio, "", 7);
                                                                     g_mod_parametri = mod_geoname;
                                                                     f2 = new File("."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt");
                                                                     
                                                                 }
                                                            }
                                                       
                                                       }
                                                      
                                                       break;
                                                       
                                                   case 9:
                                                       //Modifica data
                                                       String mod_data = "";
                                                       boolean n_data_valida = false;
                                                       do{
                                                        System.out.println("Inserisci la nuova data");
                                                        Scanner scan9 = new Scanner(System.in);
                                                        mod_data = scan9.nextLine();
                                                        
                                                        if(parametri_climatici.check_data(mod_data)){
                                                           n_data_valida = true; 
                                                        }
                                                       
                                                       }while(!n_data_valida);
                                                       
                                                       String[] dati_data3 = mod_data.split("/");
                                                       
                                                       File fe2 = new File("."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data3[0]+dati_data3[1]+dati_data3[2]+".txt");
                                                       
                                                       if(fe2.exists()){
                                                           System.out.println("Esista gia' un file con Geoname ID: " + g_mod_parametri + " e data: " + mod_data);
                                                       }else{
                                                           String n_data_f = "."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data3[0]+dati_data3[1]+dati_data3[2]+".txt";
                                                           String vecchio2 = "."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt";
                                                           parametri_climatici.modifica(n_data_f+"-------"+vecchio2, "", 8);
                                                           dati_data2[0] = dati_data3[0];
                                                           dati_data2[1] = dati_data3[1];
                                                           dati_data2[2] = dati_data3[2];
                                                           f2 = new File("."+File.separator+"Parametri"+File.separator+g_mod_parametri+"------"+dati_data2[0]+dati_data2[1]+dati_data2[2]+".txt");
                                                       }
                                                       
                                                       
                                                       
                                                       
                                                       
                                                       break;
                                                       
                                                   case 10:
                                                       token_scelta5 = false;
                                                       break;
                                                   
                                                   
                                                   default:
                                                       System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                       break;
                                               }
                                        
                                            }while(token_scelta5); 
                                        }
                                       break;
                                                    
                                                
                                        case 3:
                                            System.out.println("Inserisci il Geoname ID dell'area di cui vuoi eliminare i dati");
                                            Scanner scan10 = new Scanner(System.in);
                                            String eg = scan10.nextLine();
                                            String ed = "";
                                            
                                            boolean ed_valido = false;
                                            
                                            do{
                                                System.out.println("Inserisci la data a cui fanno riferimenti le informazioni che vuoi eliminare");
                                                ed = scan10.nextLine();
                                                
                                                if(parametri_climatici.check_data(ed)){
                                                    ed_valido = true;
                                                }
                                                
                                            }while(!ed_valido);
                                            
                                            String[] dati_data4 = ed.split("/");
                                            
                                            File fe3 = new File("."+File.separator+"Parametri"+File.separator+eg+"------"+dati_data4[0]+dati_data4[1]+dati_data4[2]+".txt");
                                            
                                            if(!fe3.exists()){
                                                System.out.println("Non esiste nessun file con Geoname ID: " + eg + " e data: " + ed);
                                            }else{
                                                Files.delete(fe3.toPath());
                                                System.out.println("Dati eliminati con successo!");
                                            }
                                            
                                            break;
                                        
                                        
                                        case 4:
                                                token_scelta52 = false;
                                                break;


                                                default:
                                                    System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                                    break;
                                            }
                                            
                                        }while(token_scelta52);
                                        
                                        
                                        
                                        break;
                                        
                                    
                                    
                                    default:
                                        System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                        break;
                                }
                                
                                
                    }else{
                            System.out.println("Non hai effettuato l'accesso");
                            boolean esito3 = false;
                            while(!esito3){
                                Account acc = new Account();
                                esito3 = cookie;
                                boolean input_valido2 = false;
                                String riprova;
                                do{
                                    System.out.println("1. Accedi\n2. Indietro");

                                    riprova = scan6.nextLine();
                                    if(!account.isNumeric(riprova)){
                                        System.out.println("Devi inserire un numero!");
                                    }else{
                                        input_valido2 = true;
                                    }
                                }while(!input_valido2);
                                                
                                switch(Integer.valueOf(riprova)){
                                                    
                                    case 1:
                                        cookie = acc.login();
                                        esito3 = cookie;
                                        break;
                                    case 2:
                                        esito3 = true;
                                        token_scelta5 = false;
                                        break;
                                                    
                                    default:
                                        System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                        break;
                                }
                                                
                            }
                        }  
                    
                    }while(token_scelta5);
                    
                    
                    break;
                    
                    
                case 6:
                    //Gestione account
                    boolean token_scelta6 = true;
                    do{
                    
                        if(cookie){
                        Scanner scan8 = new Scanner(System.in);
                        
                        String sceltaacc;
                        
                        boolean scelta_valida2 = false;
                        
                        
                        
                        
                        do{
                        System.out.println("Cosa vuoi fare con il tuo account?");
                           System.out.println("1. Aggiungi un centro di monitoraggio");
                           System.out.println("2. Modifica i dati");
                           System.out.println("3. Elimina il tuo account");
                           System.out.println("4. Indietro");
                           
                           sceltaacc = scan8.nextLine();
                           
                           if(!account.isNumeric(sceltaacc)){
                               System.out.println("Devi inserire un numero!");
                           }else{
                               scelta_valida2 = true;
                           }
                           
                        }while(!scelta_valida2);
                           
                           switch(Integer.valueOf(sceltaacc)){
                               
                               default:
                                   System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                   break;
                               
                               case 1:
                                   
                                   String nuovo = "";
                                       File centri = new File("CentroMonitoraggio.dati");
                                       if(Files.readAllBytes(centri.toPath()).length == 0){
                                           System.out.println("Nel programma non e' inserito nessun centro di monitoraggio");
                                           break;
                                        }else{
                                            File t = new File("cookie.txt");
                                            Scanner scan9 = new Scanner(System.in);
                                            String username = Files.readString(t.toPath());
                                            
                                            boolean input_valido8 = false;
                                            String nome_centro;
                                            String indirizzo_centro;
                                            
                                            do{
                                                System.out.println("Inserisci il nome del centro di monitoraggio");
                                                nome_centro = scan9.nextLine();

                                                System.out.println("Inserisci l'indirizzo del centro di monitoraggio");
                                                indirizzo_centro = scan9.nextLine();

                                                if(nome_centro.contains("\t") | indirizzo_centro.contains("\t") | nome_centro.contains("-----") | indirizzo_centro.contains("-----")){
                                                    System.out.println("Il nome o l'indirizzo non possono contenere tab o i 5 trattini '-----'");
                                                }else{
                                                    input_valido8 = true;
                                                }
                                            }while(!input_valido8);
                                            
                                            //Vede se il centro inserito esiste
                                            Scanner scancentri = new Scanner(centri);
                                            boolean esiste = false;
                                            while(scancentri.hasNextLine()){
                                                String riga = scancentri.nextLine();
                                                //System.out.println(riga);
                                                String[] dati = riga.split("\t");
                                                //System.out.println(dati[0] + " e' uguale a " + nome_centro + " e " + dati[1] + " e' uguale a " + indirizzo_centro + " ?");
                                                if(dati[0].equals(nome_centro) & dati[1].equals(indirizzo_centro) ){
                                                    //System.out.println("e' uguale !");
                                                    esiste = true;
                                                }
                                            }
                                            
                                            if(esiste){
                                                                                            
                                                File operatori = new File("OperatoriRegistrati.dati");
                                                Scanner scanop = new Scanner(operatori);
                                                
                                                while(scanop.hasNextLine()){
                                                    String riga = scanop.nextLine();
                                                    String[] dati = riga.split("\t");
                                                    
                                                    //System.out.println(dati.length + " > 6 ?");
                                                    if(dati.length >= 6){
                                                        System.out.println("Sei gia' parte del centro di monitoraggio : "  + dati[5] + " non puoi far parte di piu' centri di monitoraggio");
                                                        break;
                                                        
                                                    }else{
                                                        if(dati[3].equals(username)){
                                                            String[] dati2 = riga.split("\t");
                                                            boolean inserito = false;
                                                            for(int i=0;i<dati2.length; i++){
                                                                if(dati2[i].equals(nome_centro)){
                                                                    inserito = true;
                                                                    break;
                                                                }
                                                            }
                                                            if(inserito){
                                                                System.out.println("Centro gia' inserito in questo centro");
                                                            }else{
                                                               nuovo = riga+"\t"+nome_centro;
                                                            }

                                                            break;
                                                        }

                                                    }
                                                    //System.out.println("risultato: " + nuovo);

                                                    account.gestione_account(nuovo, 1);
                                                    }
                                                }else{
                                                System.out.println("Il centro inserito non esiste");
                                                
                                            }
                                            
                                       }    
                                   
                                   
                                   break;
                               case 2:
                                   boolean token_scelta46 = true;
                                   File t2 = new File("cookie.txt");
                                   String username2 = Files.readString(t2.toPath());
                                   File operatori = new File("OperatoriRegistrati.dati");
                                   Scanner scanop = new Scanner(operatori);
                                   String riga_da_sostituire = "";
                                   while(scanop.hasNextLine()){
                                       String riga = scanop.nextLine();
                                       String[] dati = riga.split("\t");
                                       if(dati[3].equals(username2)){
                                           riga_da_sostituire = riga;
                                           break;
                                       }
                                   }
                                   String[] campi = riga_da_sostituire.split("\t");
                                   do{
                                        
                                        System.out.println("Quale dati del tuo account vuoi modificare?");
                                        System.out.println("1. Nome e Cognome");
                                        System.out.println("2. Codice fiscale");
                                        System.out.println("3. Email");
                                        System.out.println("4. Userid (Se lo cambi dovrai effettuare di nuovo l'accesso!)");
                                        System.out.println("5. Password");
                                        System.out.println("6. Centro di appartenenza");
                                        System.out.println("7. Indietro");
                                        Scanner scan2 = new Scanner(System.in);
                                        Scanner scan3 = new Scanner(System.in);
                                        int sceltama = scan2.nextInt();
                                        File operatori2 = new File("OperatoriRegistrati.dati");
                                        Scanner scanop2 = new Scanner(operatori2);

                                        switch(sceltama){
                                            case 1:
                                                System.out.println("Inserisci il nuovo nome e il nuovo cognome");
                                                String nnome_cognome = scan3.nextLine();
                                                
                                                if(nnome_cognome.contains("\t") | nnome_cognome.contains("-----")){
                                                    System.out.println("Il nome e il cognome non possono contenere tab o i 5 trattini '-----'");
                                                }else{
                                                    String nuovo2 = "";
                                                    if(campi.length >= 6){
                                                        nuovo2 = nnome_cognome+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                    }else{
                                                        nuovo2 = nnome_cognome+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4];
                                                    }
                                                    account.gestione_account(nuovo2, 2);
                                                    campi[0] = nnome_cognome;
                                                }
                                                
                                                break;
                                            case 2:
                                                boolean check_cf = true;
                                                String ncf = "";
                                                do{
                                                     System.out.println("Inserisci il tuo codice fiscale");
                                                     ncf = scan3.nextLine().toUpperCase();
                                                     check_cf = !account.convalida_cofice_fiscale(ncf);
                                                     if(ncf.contains("-----")){
                                                         System.out.println("Il codice fiscale non puo' contenere i 5 trattini '-----'");
                                                     }
                                                }while(check_cf & !ncf.contains("-----"));
                                                boolean trovato = false;
                                                while(scanop2.hasNextLine()){
                                                    String riga = scanop2.nextLine();
                                                    String[] dati = riga.split("\t");
                                                    if(!dati[3].equals(username2) & dati[1].equals(ncf)){
                                                        trovato = true;
                                                        System.out.println("Il codice fiscale inserito e' gia' associato ad un altro account");
                                                        break;
                                                    }
                                                }
                                                
                                                if(!trovato){
                                                    String nuovo3 = "";
                                                    if(campi.length >= 6){
                                                        nuovo3 = campi[0]+"\t"+ncf+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                    }else{
                                                        nuovo3 = campi[0]+"\t"+ncf+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4];
                                                    }
                                                    account.gestione_account(nuovo3, 2);
                                                    campi[1] = ncf; 
                                                }
                                                
                                                
                                                break;
                                            case 3:
                                                boolean check_m = true;
                                                String nmail = "";
                                                do{
                                                     System.out.println("Inserisci la nuova mail");
                                                     nmail = scan3.nextLine();
                                                     check_m = !account.convalida_mail(nmail);
                                                     
                                                     if(nmail.contains("-----")){
                                                         System.out.println("L'email non puo' contenere i 5 trattini '-----'");
                                                     }
                                                     
                                                }while(check_m & !nmail.contains("-----"));
                                                
                                                boolean trovato2 = false;
                                                while(scanop2.hasNextLine()){
                                                    String riga = scanop2.nextLine();
                                                    String[] dati = riga.split("\t");
                                                    if(!dati[3].equals(username2) & dati[2].equals(nmail)){
                                                        trovato2 = true;
                                                        System.out.println("L'email inserita e' gia' associata ad un altro account");
                                                        break;
                                                    }
                                                }
                                                
                                                if(!trovato2){
                                                    String nuovo4 = "";
                                                    if(campi.length >= 6){
                                                        nuovo4 = campi[0]+"\t"+campi[1]+"\t"+nmail+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                    }else{
                                                        nuovo4 = campi[0]+"\t"+campi[1]+"\t"+nmail+"\t"+campi[3]+"\t"+campi[4]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4];
                                                    }
                                                    account.gestione_account(nuovo4, 2);
                                                    campi[2] = nmail; 
                                                }
                                                
                                                
                                                break;
                                            case 4:
                                                System.out.println("Inserisci il nuovo userid");
                                                String nuserid = scan3.nextLine();
                                                String nuovo5 = "";
                                                
                                                if(nuserid.contains("-----")){
                                                    System.out.println("L'userid non puo' contenere i 5 trattini '-----'");
                                                }else{
                                                        boolean trovato3 = false;
                                                        while(scanop2.hasNextLine()){
                                                            String riga = scanop2.nextLine();
                                                            String[] dati = riga.split("\t");
                                                            if(dati[3].equals(nuserid)){
                                                                trovato3 = true;
                                                                System.out.println("L'userid inserito e' gia' associato ad un altro account");
                                                                break;
                                                            }
                                                        }

                                                        if(!trovato3){
                                                            if(campi.length >= 6){
                                                                nuovo5 = campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+nuserid+"\t"+campi[4]+"\t"+campi[5]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                            }else{
                                                                nuovo5 = campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+nuserid+"\t"+campi[4]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4];
                                                            }
                                                            account.gestione_account(nuovo5, 2);
                                                            campi[3] = nuserid;
                                                            cookie = false;
                                                            token_scelta46 = false;
                                                            token_scelta3 = false;
                                                        } 
                                                }
                                                break;
                                            case 5:
                                                System.out.println("Inserisci la nuova password");
                                                String npassword = scan3.nextLine();
                                                
                                                if(npassword.contains("-----")){
                                                    System.out.println("La password non puo' contenere i 5 trattini '-----'");
                                                }else{
                                                
                                                    String nuovo6 = "";
                                                
                                                    if(campi.length >= 6){
                                                        nuovo6 = campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+npassword+"\t"+campi[5]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                    }else{
                                                        nuovo6 = campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+npassword+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4];
                                                    }
                                                    account.gestione_account(nuovo6, 2);
                                                    campi[4] = npassword;  
                                                
                                                }
                                                
                                                break;
                                            case 6:
                                                String nuovo7 = "";
                                                if(campi.length >= 6){
                                                    //Inserire nome e via del centro
                                                    System.out.println("Inserire il nome del nuovo centro");
                                                    String nnome_centro = scan3.nextLine();
                                                    System.out.println("Inserisci l'indirizzo del nuovo centro");
                                                    Scanner scan5 = new Scanner(System.in);
                                                    String nindirizzo_centro = scan5.nextLine();
                                                    
                                                    if(nnome_centro.contains("-----") | nindirizzo_centro.contains("-----")){
                                                        System.out.println("Il nome e l'indirizzo del nuovo centro non possono contenere i 5 trattini '-----");
                                                    }else{
                                                    
                                                            File centri2 = new File("CentroMonitoraggio.dati");
                                                        Scanner scancentri2 = new Scanner(centri2);

                                                        //Vedere se esiste
                                                        boolean esiste = false;
                                                        while(scancentri2.hasNextLine()){
                                                            String riga = scancentri2.nextLine();
                                                            String[] dati = riga.split("\t");
                                                            if(dati[0].equals(nnome_centro) & dati[1].equals(nindirizzo_centro)){
                                                                esiste = true;
                                                            }
                                                        }



                                                        //Vedere se non e' già inserito

                                                        if(!nnome_centro.equals(campi[5]) & esiste){
                                                            nuovo7 = campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+nnome_centro+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                            account.gestione_account(nuovo7, 2);
                                                            campi[5] = nnome_centro;
                                                        }
                                                        
                                                    }
                                                    
                                                    //Se le condizione vengono soddisfatte inserirlo nella maxi stringa
                                                }else{
                                                    System.out.println("Non c'è nessun centro di appartenenza da modificare");
                                                }
                                                break;
                                            case 7:
                                                token_scelta46 = false;
                                                break;
                                        }
                                   }while(token_scelta46);
                                   break;
                               case 3:
                                   File t = new File("cookie.txt");
                                   
                                   String username = Files.readString(t.toPath());
                                   account.gestione_account(username, 3);
                                   Files.delete(t.toPath());
                                   cookie = false;
                                   token_scelta6 = false;
                                   
                                   break;
                               case 4:
                                   token_scelta6 = false;
                                   break;
                           }
                        
                        
                        
                        
                    }else{
                            System.out.println("Non hai effettuato l'accesso");
                            boolean esito3 = false;
                            while(!esito3){
                                Account acc = new Account();
                                esito3 = cookie;
                                boolean input_valido2 = false;
                                String riprova;
                                do{
                                    System.out.println("1. Accedi\n2. Indietro");

                                    riprova = scan6.nextLine();
                                    if(!account.isNumeric(riprova)){
                                        System.out.println("Devi inserire un numero!");
                                    }else{
                                        input_valido2 = true;
                                    }
                                }while(!input_valido2);
                                                
                                switch(Integer.valueOf(riprova)){
                                                    
                                    case 1:
                                        cookie = acc.login();
                                        esito3 = cookie;
                                        break;
                                    case 2:
                                        esito3 = true;
                                        token_scelta6 = false;
                                        break;
                                                    
                                    default:
                                        System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                                        break;
                                }
                                                
                            }
                        }
                    
                    }while(token_scelta6);
                    
                    
                    break;
                
                case 7:
                    //Uscita dal programma
                    try{
                        File t = new File("temp.txt");
                        File t2 = new File("cookie.txt");
                        Files.delete(t.toPath());
                        Files.delete(t2.toPath());
                    }catch(Exception e){
                        System.out.println(e);
                       //Significa solo che il file temp.txt o cookie.txt non sono stati creati perchè non servivano per la sessione corrente
                    }
                    System.out.println("Arrivederci");
                    System.exit(0);

                    
                default:
                    //Viene stampato il messaggio in caso l'utente inserisce un numero < 1 e > 7
                    System.out.println("Scelta invalida! Digita il numero corrispondente all'opzione che vuoi selezionare e premi invio");
                    break;
                }
            
                    
        
        }
        
        
        /*

               case 5:
                   token_scelta = true;
                   do{
                       
                       System.out.println("1. Gestisci i parametri climatici su delle aree\n2. Indietro");
                       input_utente = scan12.nextInt();
                       
                       switch(input_utente){
                           case 1:
                               if(cookie){
                                   //Menu
                                   boolean token_scelta2 = true;
                                   do{
                                        System.out.println("1. Aggiungi informazioni su un area");
                                        System.out.println("2. Modifica informazioni gia' inserite su un area");
                                        System.out.println("3. Elimina tutte le informazioni su un area");
                                        System.out.println("4. Indietro");
                                        ParametriClimatici pc = new ParametriClimatici();

                                        int scelta = scan48.nextInt();

                                        switch(scelta){
                                            case 1:
                                                System.out.println("Inserisci il Geoname ID dell'area di cui vuoi inserire le informazioni");
                                                String geonameidarea = scan49.nextLine();
                                                System.out.println("Inserisci la data a cui fanno riferimento le informazioni formato giorno/mese/anno");
                                                String datainformazioni = scan50.nextLine();
                                                File aree = new File("CoordinateMonitoraggio.dati");
                                                Scanner scanaree = new Scanner(aree);
                                                scanaree.nextLine();
                                                boolean esiste = false;
                                                while(scanaree.hasNextLine()){
                                                    String riga = scanaree.nextLine();
                                                    String [] dati = riga.split("\t");
                                                    if(dati[0].equals(geonameidarea)){
                                                        esiste = true;
                                                        break;
                                                    }
                                                }
                                                
                                                if(esiste){
                                                    //Vento
                                                    System.out.println("Inserisci la velocità del vento in Km/h");
                                                    String vel_vento = scan51.nextLine();
                                                    System.out.println("Inseriscil un punteggio da 1 a 5 per la velocità del vento");
                                                    String vel_vento_punteggio = scan52.nextLine();
                                                    System.out.println("Inserisci note sulla velocita' del vento (Singola riga, max 256 caratteri)");
                                                    String commenti_vento = scan53.nextLine();
                                                    
                                                    String vento = vel_vento+"\t"+vel_vento_punteggio+"\t"+commenti_vento;
                                                    
                                                    //Umidità
                                                    System.out.println("Inserisci la percentuale di umidità");
                                                    String per_umi = scan51.nextLine();
                                                    System.out.println("Inseriscil un punteggio da 1 a 5 per la percentuale di umidita'");
                                                    String per_umi_punteggio = scan52.nextLine();
                                                    System.out.println("Inserisci note sulla percentuale di umidita' (Singola riga, max 256 caratteri)");
                                                    String commenti_umi = scan53.nextLine();
                                                    
                                                    String umidita = per_umi+"\t"+per_umi_punteggio+"\t"+commenti_umi;
                                                    
                                                    //Pressione
                                                    System.out.println("Inserisci la pressione in hPa");
                                                    String pressione_hpa = scan54.nextLine();
                                                    System.out.println("Inseriscil un punteggio da 1 a 5 per la pressione");
                                                    String pressione_punteggio = scan55.nextLine();
                                                    System.out.println("Inserisci note sulla pressione (Singola riga, max 256 caratteri)");
                                                    String commenti_pressione = scan56.nextLine();
                                                    
                                                    String pressione = pressione_hpa+"\t"+pressione_punteggio+"\t"+commenti_pressione;
                                                    
                                                    //Temperatura
                                                    System.out.println("Inserisci la temperatura in gradi");
                                                    String temp = scan57.nextLine();
                                                    System.out.println("Inseriscil un punteggio da 1 a 5 per la temperatura");
                                                    String temp_punteggio = scan58.nextLine();
                                                    System.out.println("Inserisci note sulla temperatura (Singola riga, max 256 caratteri)");
                                                    String commenti_temp = scan59.nextLine();
                                                    
                                                    String temperatura = temp+"\t"+temp_punteggio+"\t"+commenti_temp;
                                                    
                                                    
                                                    //Precipitazioni
                                                    System.out.println("Inserisci i millimetri delle precipitazioni");
                                                    String mm_prec = scan60.nextLine();
                                                    System.out.println("Inseriscil un punteggio da 1 a 5 per le precipitazioni");
                                                    String mm_prec_punteggio = scan61.nextLine();
                                                    System.out.println("Inserisci note sulle precipitazioni (Singola riga, max 256 caratteri)");
                                                    String commenti_mm_prec = scan62.nextLine();
                                                    
                                                    String precipitazioni = mm_prec+"\t"+mm_prec_punteggio+"\t"+commenti_mm_prec;
                                                    
                                                    //Altitudine dei ghiacciai
                                                    System.out.println("Inserisci l'altitudine dei ghiacciai in metri");
                                                    String metri = scan63.nextLine();
                                                    System.out.println("Inseriscil un punteggio da 1 a 5 per l'altitudine dei ghiacciai");
                                                    String metri_punteggio = scan64.nextLine();
                                                    System.out.println("Inserisci note sull'altitudine (Singola riga, max 256 caratteri)");
                                                    String commenti_metri = scan65.nextLine();
                                                    
                                                    String altitudine_ghiacciai = metri+"\t"+metri_punteggio+"\t"+commenti_metri;
                                                    
                                                    //Massa dei ghiacciai
                                                    System.out.println("Inserisci la massa dei ghiacciai in Kg");
                                                    String kg = scan66.nextLine();
                                                    System.out.println("Inseriscil un punteggio da 1 a 5 per la massa dei ghiacciai");
                                                    String kg_punteggio = scan67.nextLine();
                                                    System.out.println("Inserisci note sulla massa (Singola riga, max 256 caratteri)");
                                                    String commenti_kg = scan68.nextLine();
                                                    
                                                    String massa_ghiacciai = kg+"\t"+kg_punteggio+"\t"+commenti_kg;
                                                    
                                                    pc.inserisci(geonameidarea, datainformazioni, vento, umidita, pressione, temperatura, precipitazioni, altitudine_ghiacciai, massa_ghiacciai);
                                                    
                                                }else{
                                                    System.out.println("Il Geoname ID inserito non esiste");
                                                }
                                                
                                                
                                                break;
                                            case 2:
                                                System.out.println("Inserisci il Geoname ID dell'area di cui vuoi modificare le informazioni");
                                                String geonameidarea2 = scan69.nextLine();
                                                System.out.println("Inserisci la data a cui fanno riferimento le informazioni che vuoi modificare formato giorno/mese/anno");
                                                String datainformazioni2 = scan70.nextLine();
                                                String da_modificare = geonameidarea2+"-----"+datainformazioni2+".txt";
                                                File f_mod = new File("./Parametri/"+da_modificare);
                                                if(Files.exists(f_mod.toPath())){
                                                    boolean token_scelta3 = false;
                                                    String[] righe_da_modificare = new String[7];
                                                    Scanner scanp = new Scanner(f_mod);
                                                    int i = 0;
                                                    while(scanp.hasNextLine()){
                                                        righe_da_modificare[i] = scanp.nextLine();
                                                        i++;
                                                    }
                                                    scanp.close();
                                                    
                                                    
                                                    
                                                    do{
                                                        System.out.println("Cosa vuoi modificare modificare?");
                                                        System.out.println("1. I parametri del vento");
                                                        System.out.println("2. I parametri dell'umidita'");
                                                        System.out.println("3. I parametri della pressione");
                                                        System.out.println("4. I parametri della temperatura");
                                                        System.out.println("5. I parametri delle precipitazioni");
                                                        System.out.println("6. I parametri dell'altitudine dei ghiacciai");
                                                        System.out.println("7. I parametri sulla massa dei ghiacciai");
                                                        System.out.println("8. Il Geoname ID");
                                                        System.out.println("9. La data");
                                                        System.out.println("10. Indietro");
                                                        
                                                        int scelta_mod = scan71.nextInt();
                                                        
                                                        
                                                        
                                                        
                                                        
                                                        switch(scelta_mod){
                                                            case 8:
                                                                System.out.println("Inseriscil il nuovo Geoname ID");
                                                                String ngeonameid = scan72.nextLine();
                                                                //Se il nuovo geoname esiste
                                                                String nuovo = "./Parametri/"+ngeonameid+"-----"+datainformazioni2+".txt------"+"./Parametri/"+da_modificare;
                                                                pc.modifica(nuovo, 3);
                                                                break;
                                                            case 9:
                                                                System.out.println("Inserisci la nuava data");
                                                                String ndata = scan72.nextLine();
                                                                //Se il nuovo geoname esiste
                                                                String nuovo2 = "./Parametri/"+geonameidarea2+"-----"+ndata+".txt------"+"./Parametri/"+da_modificare;
                                                                pc.modifica(nuovo2, 3);
                                                                break;
                                                            case 10:
                                                                token_scelta3 = false;
                                                                break;
                                                        }
                                                        
                                                        
                                                    }while(token_scelta3);
                                                }else{
                                                    System.out.println("Non ci sono informazioni sull'area scelta in quel determinato giorno");
                                                }
                                                break;
                                            case 3:
                                                break;
                                            case 4:
                                                break;
                                            default:
                                                System.out.println("Scelta inavilda");
                                                break;
                                        }
                                   }while(token_scelta2);
                                   
                               }else{
                                   System.out.println("Non hai effettuato l'accesso\nInserisci il tuo userid");
                                   String userid2 = scan13.nextLine();
                                   System.out.println("Inserisci la tua password");
                                   String password2 = scan13.nextLine();
                                   Account a2 = new Account();
                                   if(cookie = a2.login(userid2, password2)){
                                      //Menu 
                                       System.out.println("Login andato a buon fine");
                                   }else{
                                       boolean token_scelta2 = true;
                                       do{
                                           System.out.println("Il login non è andato a buon fine\n1. Riprova\n2. Registrati\n3. Indietro");
                                           int scelta = scan14.nextInt();
                                           
                                           switch(scelta){
                                               case 1:
                                                   System.out.println("Inserisci il tuo userid");
                                                   String userid3 = scan15.nextLine();
                                                   System.out.println("Inserisci la tua password");
                                                   String password3 = scan15.nextLine();
                                                   if(a2.login(userid3, password3)){
                                                       token_scelta2 = false;
                                                       cookie = true;
                                                   }
                                                   break;
                                                   
                                               case 2:
                                                    String nome_e_cognome, codice_fiscale, email, userid, password, centro_di_monitoraggio_di_afferenza;
                                                    System.out.println("Inserisci il tuo nome e cognome");
                                                    nome_e_cognome = scan16.nextLine();
                                                    System.out.println("Inserisci il tuo codice fiscale");
                                                    codice_fiscale = scan16.nextLine();
                                                    System.out.println("Inserisci il tuo indirizzo mail");
                                                    email = scan16.nextLine();
                                                    System.out.println("Inserisci il tuo nome userid");
                                                    userid = scan16.nextLine();
                                                    System.out.println("Inserisci la tua password");
                                                    password = scan16.nextLine();
                                                    System.out.println("Inserisci il tuo centro di monitoraggio di afferenza");
                                                    centro_di_monitoraggio_di_afferenza = scan16.nextLine();
                                                    Account a = new Account();
                                                    cookie = a.registrazione(nome_e_cognome, codice_fiscale, email, userid, password, centro_di_monitoraggio_di_afferenza);
                                                    if(!cookie){
                                                        System.out.println("Errore sconosciuto");
                                                        break;
                                                    }else{
                                                        token_scelta2 = false;
                                                    }
                                                    break;
                                                
                                               case 3:
                                                   token_scelta2 = false;
                                                   break;
                                               default:
                                                   System.out.println("Scelta invalida");
                                                   break;
                                           }
                                       }while(token_scelta2);
                                   }
                               }
                               break;
                           case 2:
                               token_scelta = false;
                               break;
                       }
                   }while(token_scelta);
                   
                   break;
               
                   
                   
               case 6:
                   boolean token_scelta3 = true;
                   Account a2 = new Account();
                   do{
                       if(cookie){
                           System.out.println("Cosa vuoi fare con il tuo account?");
                           System.out.println("1. Aggiungi un centro di monitoraggio");
                           System.out.println("2. Modifica i dati");
                           System.out.println("3. Elimina il tuo account");
                           System.out.println("4. Indietro");
                           
                           int sceltaacc = scan42.nextInt();
                           
                           switch(sceltaacc){
                               case 1:
                                   
                                   String nuovo = "";
                                       File centri = new File("CentroMonitoraggio.dati");
                                       if(Files.readAllBytes(centri.toPath()).length == 0){
                                           System.out.println("Nel programma non e' inserito nessun centro di monitoraggio");
                                           break;
                                        }else{
                                            File t = new File("cookie.txt");
                                   
                                            String username = Files.readString(t.toPath());
                                            System.out.println("Inserisci il nome del centro di monitoraggio (Inserisci 'Stop' per fermare l'inserimento)");
                                            String nome_centro = scan43.nextLine();
                                            if(nome_centro.equals("Stop")){
                                                break;
                                            }
                                            System.out.println("Inserisci l'indirizzo del centro di monitoraggio");
                                            String indirizzo_centro = scan44.nextLine();
                                            //Vede se il centro inserito esiste
                                            Scanner scancentri = new Scanner(centri);
                                            boolean esiste = false;
                                            while(scancentri.hasNextLine()){
                                                String riga = scancentri.nextLine();
                                                //System.out.println(riga);
                                                String[] dati = riga.split("\t");
                                                //System.out.println(dati[0] + " e' uguale a " + nome_centro + " e " + dati[1] + " e' uguale a " + indirizzo_centro + " ?");
                                                if(dati[0].equals(nome_centro) & dati[1].equals(indirizzo_centro) ){
                                                    //System.out.println("e' uguale !");
                                                    esiste = true;
                                                }
                                            }
                                            
                                            if(esiste){
                                                                                            
                                                File operatori = new File("OperatoriRegistrati.dati");
                                                Scanner scanop = new Scanner(operatori);
                                                
                                                while(scanop.hasNextLine()){
                                                    String riga = scanop.nextLine();
                                                    String[] dati = riga.split("\t");
                                                    
                                                    //System.out.println(dati.length + " > 6 ?");
                                                    if(dati.length >= 6){
                                                        System.out.println("Sei gia' parte del centro di monitoraggio : "  + dati[5] + " non puoi far parte di piu' centri di monitoraggio");
                                                        break;
                                                        
                                                    }else{
                                                        if(dati[3].equals(username)){
                                                            String[] dati2 = riga.split("\t");
                                                            boolean inserito = false;
                                                            for(int i=0;i<dati2.length; i++){
                                                                if(dati2[i].equals(nome_centro)){
                                                                    inserito = true;
                                                                    break;
                                                                }
                                                            }
                                                            if(inserito){
                                                                System.out.println("Centro gia' inserito in questo centro");
                                                            }else{
                                                               nuovo = riga+"\t"+nome_centro;
                                                            }

                                                            break;
                                                        }

                                                    }
                                                    //System.out.println("risultato: " + nuovo);

                                                    a2.gestione_account(nuovo, 1);
                                                    }
                                                }else{
                                                System.out.println("Il centro inserito non esiste");
                                                
                                            }
                                            
                                       }    
                                   
                                   
                                   break;
                               case 2:
                                   boolean token_scelta4 = true;
                                   File t2 = new File("cookie.txt");
                                   String username2 = Files.readString(t2.toPath());
                                   File operatori = new File("OperatoriRegistrati.dati");
                                   Scanner scanop = new Scanner(operatori);
                                   String riga_da_sostituire = "";
                                   while(scanop.hasNextLine()){
                                       String riga = scanop.nextLine();
                                       String[] dati = riga.split("\t");
                                       if(dati[3].equals(username2)){
                                           riga_da_sostituire = riga;
                                           break;
                                       }
                                   }
                                   String[] campi = riga_da_sostituire.split("\t");
                                   do{
                                        
                                        System.out.println("Quale dati del tuo account vuoi modificare?");
                                        System.out.println("1. Nome e Cognome");
                                        System.out.println("2. Codice fiscale");
                                        System.out.println("3. Email");
                                        System.out.println("4. Userid (Se lo cambi dovrai effettuare di nuovo l'accesso!)");
                                        System.out.println("5. Password");
                                        System.out.println("6. Centro di appartenenza");
                                        System.out.println("7. Indietro");

                                        int sceltama = scan45.nextInt();
                                        File operatori2 = new File("OperatoriRegistrati.dati");
                                        Scanner scanop2 = new Scanner(operatori2);

                                        switch(sceltama){
                                            case 1:
                                                System.out.println("Inserisci il nuovo nome e il nuovo cognome");
                                                String nnome_cognome = scan46.nextLine();
                                                String nuovo2 = "";
                                                if(campi.length >= 6){
                                                    nuovo2 = nnome_cognome+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                }else{
                                                    nuovo2 = nnome_cognome+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4];
                                                }
                                                a2.gestione_account(nuovo2, 2);
                                                campi[0] = nnome_cognome;
                                                break;
                                            case 2:
                                                boolean check_cf = true;
                                                String ncf = "";
                                                do{
                                                     System.out.println("Inserisci il tuo codice fiscale");
                                                     ncf = scan46.nextLine().toUpperCase();
                                                     check_cf = !a2.convalida_cofice_fiscale(ncf);
                                                }while(check_cf);
                                                boolean trovato = false;
                                                while(scanop2.hasNextLine()){
                                                    String riga = scanop2.nextLine();
                                                    String[] dati = riga.split("\t");
                                                    if(!dati[3].equals(username2) & dati[1].equals(ncf)){
                                                        trovato = true;
                                                        System.out.println("Il codice fiscale inserito e' gia' associato ad un altro account");
                                                        break;
                                                    }
                                                }
                                                
                                                if(!trovato){
                                                    String nuovo3 = "";
                                                    if(campi.length >= 6){
                                                        nuovo3 = campi[0]+"\t"+ncf+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                    }else{
                                                        nuovo3 = campi[0]+"\t"+ncf+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4];
                                                    }
                                                    a2.gestione_account(nuovo3, 2);
                                                    campi[1] = ncf; 
                                                }
                                                
                                                
                                                break;
                                            case 3:
                                                boolean check_m = true;
                                                String nmail = "";
                                                do{
                                                     System.out.println("Inserisci la nuova mail");
                                                     nmail = scan46.nextLine();
                                                     check_m = !a2.convalida_mail(nmail);
                                                }while(check_m);
                                                
                                                boolean trovato2 = false;
                                                while(scanop2.hasNextLine()){
                                                    String riga = scanop2.nextLine();
                                                    String[] dati = riga.split("\t");
                                                    if(!dati[3].equals(username2) & dati[2].equals(nmail)){
                                                        trovato2 = true;
                                                        System.out.println("L'email inserita e' gia' associata ad un altro account");
                                                        break;
                                                    }
                                                }
                                                
                                                if(!trovato2){
                                                    String nuovo4 = "";
                                                    if(campi.length >= 6){
                                                        nuovo4 = campi[0]+"\t"+campi[1]+"\t"+nmail+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                    }else{
                                                        nuovo4 = campi[0]+"\t"+campi[1]+"\t"+nmail+"\t"+campi[3]+"\t"+campi[4]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4];
                                                    }
                                                    a2.gestione_account(nuovo4, 2);
                                                    campi[2] = nmail; 
                                                }
                                                
                                                
                                                break;
                                            case 4:
                                                System.out.println("Inserisci il nuovo userid");
                                                String nuserid = scan46.nextLine();
                                                String nuovo5 = "";
                                                
                                                boolean trovato3 = false;
                                                while(scanop2.hasNextLine()){
                                                    String riga = scanop2.nextLine();
                                                    String[] dati = riga.split("\t");
                                                    if(dati[3].equals(nuserid)){
                                                        trovato3 = true;
                                                        System.out.println("L'userid inserito e' gia' associato ad un altro account");
                                                        break;
                                                    }
                                                }
                                                
                                                if(!trovato3){
                                                    if(campi.length >= 6){
                                                        nuovo5 = campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+nuserid+"\t"+campi[4]+"\t"+campi[5]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                    }else{
                                                        nuovo5 = campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+nuserid+"\t"+campi[4]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4];
                                                    }
                                                    a2.gestione_account(nuovo5, 2);
                                                    campi[3] = nuserid;
                                                    cookie = false;
                                                    token_scelta4 = false;
                                                    token_scelta3 = false;
                                                }
                                                
                                                
                                                break;
                                            case 5:
                                                System.out.println("Inserisci la nuova password");
                                                String npassword = scan46.nextLine();
                                                String nuovo6 = "";
                                                
                                                if(campi.length >= 6){
                                                    nuovo6 = campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+npassword+"\t"+campi[5]+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                }else{
                                                    nuovo6 = campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+npassword+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4];
                                                }
                                                a2.gestione_account(nuovo6, 2);
                                                campi[4] = npassword;
                                                break;
                                            case 6:
                                                String nuovo7 = "";
                                                if(campi.length >= 6){
                                                    //Inserire nome e via del centro
                                                    System.out.println("Inserire il nome del centro");
                                                    String nnome_centro = scan46.nextLine();
                                                    System.out.println("Inserisci l'indirizzo del centro");
                                                    String nindirizzo_centro = scan47.nextLine();
                                                    File centri2 = new File("CentroMonitoraggio.dati");
                                                    Scanner scancentri2 = new Scanner(centri2);
                                                    
                                                    //Vedere se esiste
                                                    boolean esiste = false;
                                                    while(scancentri2.hasNextLine()){
                                                        String riga = scancentri2.nextLine();
                                                        String[] dati = riga.split("\t");
                                                        if(dati[0].equals(nnome_centro) & dati[1].equals(nindirizzo_centro)){
                                                            esiste = true;
                                                        }
                                                    }
                                                    
                                                    
                                                    
                                                    //Vedere se non e' già inserito
                                                    
                                                    if(!nnome_centro.equals(campi[5]) & esiste){
                                                        nuovo7 = campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+nnome_centro+"-----"+campi[0]+"\t"+campi[1]+"\t"+campi[2]+"\t"+campi[3]+"\t"+campi[4]+"\t"+campi[5];
                                                        a2.gestione_account(nuovo7, 2);
                                                        campi[5] = nnome_centro;
                                                    }
                                                    
                                                    //Se le condizione vengono soddisfatte inserirlo nella maxi stringa
                                                }else{
                                                    System.out.println("Non c'è nessun centro di appartenenza da modificare");
                                                }
                                                break;
                                            case 7:
                                                token_scelta4 = false;
                                                break;
                                        }
                                   }while(token_scelta4);
                                   break;
                               case 3:
                                   File t = new File("cookie.txt");
                                   
                                   String username = Files.readString(t.toPath());
                                   a2.gestione_account(username, 3);
                                   Files.delete(t.toPath());
                                   token_scelta3 = false;
                                   break;
                               case 4:
                                   token_scelta3 = false;
                                   break;
                           }
                           
                       }else{
                           boolean token_scelta2 = true;
                           do{
                                System.out.println("Non hai effettuato l'accesso\n1. Accedi\n2. Registrati\n3. Indietro");
                                int scelta = scan14.nextInt();
                                           
                                    switch(scelta){
                                        case 1:
                                            System.out.println("Inserisci il tuo userid");
                                            String userid3 = scan15.nextLine();
                                            System.out.println("Inserisci la tua password");
                                            String password3 = scan15.nextLine();
                                            if(a2.login(userid3, password3)){
                                                token_scelta2 = false;
                                                cookie = true;
                                            }
                                            break;
                                                   
                                            case 2:
                                                String nome_e_cognome, codice_fiscale, email, userid, password, centro_di_monitoraggio_di_afferenza;
                                                System.out.println("Inserisci il tuo nome e cognome");
                                                nome_e_cognome = scan16.nextLine();
                                                System.out.println("Inserisci il tuo codice fiscale");
                                                codice_fiscale = scan16.nextLine();
                                                System.out.println("Inserisci il tuo indirizzo mail");
                                                email = scan16.nextLine();
                                                System.out.println("Inserisci il tuo nome userid");
                                                userid = scan16.nextLine();
                                                System.out.println("Inserisci la tua password");
                                                password = scan16.nextLine();
                                                System.out.println("Inserisci il tuo centro di monitoraggio di afferenza");
                                                centro_di_monitoraggio_di_afferenza = scan16.nextLine();
                                                Account a = new Account();
                                                cookie = a.registrazione(nome_e_cognome, codice_fiscale, email, userid, password, centro_di_monitoraggio_di_afferenza);
                                                if(!cookie){
                                                    //System.out.println("Errore sconosciuto");
                                                    break;
                                                }else{
                                                    token_scelta2 = false;
                                                }
                                                break;
                                                
                                            case 3:
                                                token_scelta2 = false;
                                                break;
                                            default:
                                                System.out.println("Scelta invalida");
                                                break;
                                        }
                            }while(token_scelta2);
                       }
                   }while(token_scelta3);
                   
                   break;
                   
                   
               case 7:
                   
                   
               default:
                   System.out.println("Scelta invalida");
                   break;
           }       
        }
        
        

        */
    }
    
}
