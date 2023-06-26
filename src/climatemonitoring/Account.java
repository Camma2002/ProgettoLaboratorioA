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
import java.util.Scanner;


public class Account {
    
    private String nome_e_cognome;
    private String codice_fiscale;
    private String email;
    private String userid;
    private String password;
    private String centro_di_monitoraggio_di_afferenza;

    public Account(String nome_e_cognome, String codice_fiscale, String email, String userid, String password, String centro_di_monitoraggio_di_afferenza) {
        this.nome_e_cognome = nome_e_cognome;
        this.codice_fiscale = codice_fiscale;
        this.email = email;
        this.userid = userid;
        this.password = password;
        this.centro_di_monitoraggio_di_afferenza = centro_di_monitoraggio_di_afferenza;
    }
    
    public Account(){
        
    }

    public void setNome_e_cognome(String nome_e_cognome) {
        this.nome_e_cognome = nome_e_cognome;
    }

    public void setCodice_fiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCentro_di_monitoraggio_di_afferenza(String centro_di_monitoraggio_di_afferenza) {
        this.centro_di_monitoraggio_di_afferenza = centro_di_monitoraggio_di_afferenza;
    }
    
    /**
     * Questa funzione serve a modificare i dati dell'account sostituendo la riga vecchia con i dati vecchi con una riga nuova di dati aggiornati
     * Si può anche eliminare il proprio account
     * @param s dati vecchi da sostituire e dati aggiornati separati da '-----'
     * @param scelta cosa modificare dell'account
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void gestione_account(String s, int scelta) throws FileNotFoundException, IOException{
        File o = new File("C:\\Climate Monitoring\\OperatoriRegistrati.dati");
        File t = new File("C:\\Climate Monitoring\\temp.txt");
        BufferedWriter t2 = new BufferedWriter(new FileWriter("temp.txt"));
        Scanner scano = new Scanner(o);
        Scanner scant = new Scanner(t);
        
        switch(scelta){
            case 1:
                //Aggiungi un centro all'account
                boolean trovato2 = false;
                while(scano.hasNextLine()){
                    String riga = scano.nextLine();                    
                    if(s.contains(riga)){
                        t2.write(s+"\n");
                        trovato2 = true;
                    }else{
                        t2.write(riga+"\n");
                        
                    }
                }
                t2.close();
                if(trovato2){
                    BufferedWriter o2 = new BufferedWriter(new FileWriter("OperatoriRegistrati.dati"));
                    
                    if(scant.hasNextLine()){
                        o2.write(scant.nextLine());
                    }
                    
                    
                    while(scant.hasNextLine()){
                        String riga = scant.nextLine();
                        //System.out.println("Scrittura di " + riga + " su operatori registrati");
                        o2.write("\n"+riga);
                    }
                    o2.close();
                    System.out.println("Centro aggiunto con successo");
                }else{
                    System.out.println("Errore");
                }
                scano.close();
                break;
            case 2:
                //Modifica i dati dell'account
                String[] stringhe = s.split("-----");
                //stringhe[1] = stringa nel file da sostituire
                boolean trovato3 = false;
                
                String pr = scano.nextLine();
                if(pr.equals(stringhe[1])){
                        t2.write(stringhe[0]);
                        trovato3 = true;
                    }else{
                        t2.write(pr);
                    }
                
                while(scano.hasNextLine()){
                    String riga = scano.nextLine();
                    if(riga.equals(stringhe[1])){
                        t2.write("\n"+stringhe[0]);
                        trovato3 = true;
                    }else{
                        t2.write("\n"+riga);
                    }
                }
                t2.close();
                if(trovato3){
                    BufferedWriter o2 = new BufferedWriter(new FileWriter("OperatoriRegistrati.dati"));
                    if(scant.hasNextLine()){
                        o2.write(scant.nextLine());
                    }
                    
                    while(scant.hasNextLine()){
                        String riga = scant.nextLine();
                        o2.write("\n"+riga);
                    }
                    o2.close();
                    System.out.println("Account modificato con successo");
                    scano.close();
                    scant.close();
                }else{
                    System.out.println("Errore");
                }
                
                break;
            case 3:
                //Elimina account
                boolean trovato = false;
                while(scano.hasNextLine()){
                    String riga = scano.nextLine();
                    String[] dati = riga.split("\t");
                    //System.out.println("dati[3]: " + dati[3] + " e' uguale a " + s + " ?");
                    if(!dati[3].equals(s)){
                        t2.write(riga+"\n");
                    }else{
                        trovato = true;
                    }
                }
                
                t2.close();
                if(trovato){
                    BufferedWriter o2 = new BufferedWriter(new FileWriter("OperatoriRegistrati.dati"));
                    
                    if(scant.hasNextLine()){
                        o2.write(scant.nextLine());
                    }
                    
                    
                    while(scant.hasNextLine()){
                        String riga = scant.nextLine();
                        System.out.println("Scrittura di " + riga + " su operatori registrati");
                        o2.write("\n"+riga);
                    }
                    o2.close();
                    System.out.println("Account Eliminato con successo");
                    scant.close();
                    scano.close();
                }else{
                    System.out.println("Errore");
                }
                
                break;
        }
    }
    
    /**
     * Prende in input una mail e restituisce vero se è nel formato corretto abc@df.gh
     * altrimenti restituisce falso
     * @param s mail
     * @return
     */
    public boolean convalida_mail(String s){
        
        if(!s.contains("@")){
            System.out.println("L'email deve contenere una @");
            return false;
        }
        
        String[] p = s.split("@");
        
        
        if(p.length > 2){
            System.out.println("L'email deve contenere solo una @");
            return false;
        }
        
        if(p.length < 2){
            System.out.println("La mail deve contenere un dominio (almeno un carattere dopo la @ e un punto dopo quel carattere Es: @abc.dfg) ");
            return false;
        }
        
        if(p[0].equals("")){
            System.out.println("La mail deve contenere almeno un carattere prima della @");
            return false;
        }
        
        if(!p[1].contains(".") | p[1].contains(".@")){
            System.out.println("La mail deve contenere un dominio (almeno un carattere dopo la @ e un punto dopo quel carattere Es: @abc.dfg) ");
            return false;
        }
        
        return true;
    }
    
    /**
     * Prende in input un codice fiscale e controlla che sia corretto
     * Se è corretto restituisce vero, altrimenti falso
     * @param s
     * @return
     */
    public boolean convalida_cofice_fiscale(String s){
        
        if(s.length() != 16){
            System.out.println("Il codice fiscale inserito non ha 16 caratteri");
            return false;
        }
        
        if( this.isNumeric(String.valueOf(s.charAt(0))) | this.isNumeric(String.valueOf(s.charAt(1))) | this.isNumeric(String.valueOf(s.charAt(2))) | this.isNumeric(String.valueOf(s.charAt(3)))  |   this.isNumeric(String.valueOf(s.charAt(4))) | this.isNumeric(String.valueOf(s.charAt(5)))  ){
            System.out.println("Uno dei primi 6 caratteri non e' una lettera: " + s.substring(0,6));
            return false;
        }
        
        if( !this.isNumeric(String.valueOf(s.charAt(6))) | !this.isNumeric(String.valueOf(s.charAt(7))) ){
            System.out.println("Il carattere 7 o 8 (O entrambi)  non e' un numero: " + s.charAt(6) + s.charAt(7));
            return false;
        }
        
        if(this.isNumeric(String.valueOf(s.charAt(8)))){
            System.out.println("Il nono carattere non e' una lettera: " + s.charAt(8));
            return false;
        }
        
        if( !this.isNumeric(String.valueOf(s.charAt(9))) | !this.isNumeric(String.valueOf(s.charAt(10))) ){
            System.out.println("Il carattere 10 o 11 (O entrambi)  non e' un numero: " + s.charAt(9) + s.charAt(10));
            return false;
        }
        
        if(this.isNumeric(String.valueOf(s.charAt(11)))){
            System.out.println("Il dodicesimo carattere non e' una lettera: " + s.charAt(11));
            return false;
        }
        
        if( !this.isNumeric(String.valueOf(s.charAt(12))) | !this.isNumeric(String.valueOf(s.charAt(13))) |  !this.isNumeric(String.valueOf(s.charAt(14)))){
            System.out.println("Il carattere 13 o 14 o 15 (O tutti questi)  non e' un numero: " + s.charAt(12) + s.charAt(13) + s.charAt(14));
            return false;
        }
        
        if(this.isNumeric(String.valueOf(s.charAt(15)))){
            System.out.println("L'ultimo carattere non e' una lettera: " + s.charAt(15));
            return false;
        }
        
        return true;
        
    }
    
    /**
     * Data in input una stringa ritorna vero se il contenuto è un numero o falso se non lo è
     * @param strNum
     * @return
     */
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    /**
     * Questa funzione permette di aggiungere, rimuovere e modificare le aree di interesse e i centri di monitoraggio
     * @param s Area o centro
     * @param n scelta di modifica/aggiunta/rimozione di un'area o un centro
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void registraCentroAree(String s, int n) throws FileNotFoundException, IOException{
        
        File myObj = new File("C:\\Climate Monitoring\\CoordinateMonitoraggio.dati");
        
        Scanner myReader2 = new Scanner(myObj, "utf-8");
        switch(n){
            case 1:
                //Aggiungi area
                
                
                
                boolean trovata = false;
                while(myReader2.hasNextLine()){
                    String data = myReader2.nextLine();
                    String[] dati = data.split("\t");
                    String[] s2 = s.split("\t");
                    String gns = s2[0];
                    String cs = s2[5];
                    if(gns.equals(dati[0]) | cs.equals(dati[5])){
                        System.out.println("L'area inserita esiste gia' : " + data);
                        trovata = true;
                        break;
                    }
                }
                
                if(!trovata){
                    BufferedWriter writer2 = new BufferedWriter(new FileWriter("CoordinateMonitoraggio.dati", true));
                    writer2.write("\n"+s);
                    System.out.println("Area aggiunta con successo"); 
                    writer2.close();
                }
                
                myReader2.close();
                
                break;
            case 2:
                //Rimuovi area
                BufferedWriter writer3 = new BufferedWriter(new FileWriter("temp.txt"));
                File myObj2 = new File("C:\\Climate Monitoring\\CoordinateMonitoraggio.dati");
                Scanner myReader3 = new Scanner(myObj2, "UTF-8");
                boolean trovata2 = false;
                while(myReader3.hasNextLine()){
                    String riga = myReader3.nextLine();
                    //System.out.println(riga);
                    String[] dati = riga.split("\t");
                    if(dati[0].equals(s)){
                        trovata2 = true;
                        //System.out.println(riga);
                        //break;
                    }else{
                        //System.out.println("Scrittura di " + riga);
                        writer3.write(riga+"\n");
                    }
                }
                
                if(!trovata2){
                    System.out.println("Area non trovata");
                }else{
                    writer3.close();
                    BufferedWriter writer4 = new BufferedWriter(new FileWriter("CoordinateMonitoraggio.dati"));

                    File temp = new File("C:\\Climate Monitoring\\temp.txt");

                    Scanner scan = new Scanner(temp, "UTF-8");
                    
                    
                    if(scan.hasNextLine()){
                        String prima_riga = scan.nextLine();
                        writer4.write(prima_riga);
                    }
                    
                    
                    while(scan.hasNextLine()){
                        String data = scan.nextLine();
                        //System.out.println(data);
                        writer4.write("\n"+data);
                    }
                    writer4.close();
                    scan.close();
                    
                    System.out.println("Area rimossa con successo");
                }
                break;
            case 3:
                //Modifica area
                String[] stringhe = s.split("-----");
                //System.out.println("Stringa da modificare: " + stringhe[1]);
                //System.out.println("Stringa modificata da sostituire: " + stringhe[0]);
                BufferedWriter writer5 = new BufferedWriter(new FileWriter("temp.txt"));
                File myObj3 = new File("C:\\Climate Monitoring\\CoordinateMonitoraggio.dati");
                Scanner myReader4 = new Scanner(myObj3, "UTF-8");
                boolean trovata3 = false;
                
                
                
                if(myReader4.hasNextLine()){
                    String prima_riga2 = myReader4.nextLine();
                    
                    if(prima_riga2.equals(stringhe[1])){
                        trovata3 = true;
                        writer5.write("\n"+stringhe[0]);
                    }else{
                        writer5.write(prima_riga2);
                    }
                    
                    
                }
                
                
                while(myReader4.hasNextLine()){
                    String riga = myReader4.nextLine();
                    //System.out.println("Riga: " + riga + " è uguale a " + stringhe[1]);
                    if(riga.equals(stringhe[1])){
                        trovata3 = true;
                        writer5.write("\n"+stringhe[0]);
                        //System.out.println(riga);
                        //break;
                    }else{
                        //System.out.println("Scrittura di " + riga);
                        writer5.write("\n"+riga);
                    }
                }
                
                if(!trovata3){
                    System.out.println("Area non trovata");
                }else{
                    writer5.close();
                    BufferedWriter writer4 = new BufferedWriter(new FileWriter("CoordinateMonitoraggio.dati"));

                    File temp = new File("C:\\Climate Monitoring\\temp.txt");

                    Scanner scan = new Scanner(temp, "UTF-8");
                    
                    String prima_riga3 = scan.nextLine();
                    
                    writer4.write(prima_riga3);

                    while(scan.hasNextLine()){
                        String data = scan.nextLine();
                        writer4.write("\n"+data);
                    }
                    writer4.close();
                    scan.close();
                    System.out.println("Area modificata con successo");
                }
                
                
                
                break;
            case 4:
                //Aggiungi centro
                BufferedWriter writer2 = new BufferedWriter(new FileWriter("CentroMonitoraggio.dati", true));
                writer2.write(s+"\n");
                System.out.println("Centro aggiunto con successo"); 
                writer2.close();
                break;
            case 5:
                //Rimuovi centro
                BufferedWriter writer6 = new BufferedWriter(new FileWriter("temp.txt"));
                File myObj4 = new File("C:\\Climate Monitoring\\CentroMonitoraggio.dati");
                Scanner myReader5 = new Scanner(myObj4, "UTF-8");
                boolean trovata4 = false;
                while(myReader5.hasNextLine()){
                    String riga = myReader5.nextLine();
                    //System.out.println(riga);
                    if(riga.contains(s)){
                        trovata4 = true;
                        //System.out.println(riga);
                        //break;
                    }else{
                        //System.out.println("Scrittura di " + riga);
                        writer6.write(riga+"\n");
                    }
                }
                
                if(!trovata4){
                    System.out.println("Centro non trovato");
                }else{
                    writer6.close();
                    BufferedWriter writer7 = new BufferedWriter(new FileWriter("CentroMonitoraggio.dati"));

                    File temp = new File("C:\\Climate Monitoring\\temp.txt");

                    Scanner scan = new Scanner(temp, "UTF-8");

                    while(scan.hasNextLine()){
                        String data = scan.nextLine();
                        //System.out.println(data);
                        writer7.write(data+"\n");
                    }
                    writer7.close();
                    scan.close();
                    System.out.println("Centro rimosso con successo");
                    
                }
                break;
            case 6:
                //Modifica centro
                String[] stringhe2 = s.split("-----");
                //System.out.println("Stringa da modificare: " + stringhe2[1]);
                //System.out.println("Stringa modificata da sostituire: " + stringhe2[0]);
                BufferedWriter writer8 = new BufferedWriter(new FileWriter("temp.txt"));
                File myObj5 = new File("C:\\Climate Monitoring\\CentroMonitoraggio.dati");
                Scanner myReader6 = new Scanner(myObj5, "UTF-8");
                boolean trovata5 = false;
                while(myReader6.hasNextLine()){
                    String riga = myReader6.nextLine();
                    //System.out.println("Riga: " + riga + " è uguale a " + stringhe2[1]);
                    if(riga.equals(stringhe2[1])){
                        trovata5 = true;
                        writer8.write(stringhe2[0]+"\n");
                        //System.out.println(riga);
                        //break;
                    }else{
                        //System.out.println("Scrittura di " + riga);
                        writer8.write(riga+"\n");
                    }
                }
                
                if(!trovata5){
                    System.out.println("Area non trovata");
                }else{
                    writer8.close();
                    BufferedWriter writer4 = new BufferedWriter(new FileWriter("CentroMonitoraggio.dati"));

                    File temp = new File("C:\\Climate Monitoring\\temp.txt");

                    Scanner scan = new Scanner(temp, "UTF-8");

                    while(scan.hasNextLine()){
                        String data = scan.nextLine();
                        writer4.write(data+"\n");
                    }
                    writer4.close();
                    scan.close();
                    System.out.println("Area modificata con successo con successo");
                }
                
                break;
        }
              
        
        
        
    }
    
    /**
     * Inseriti username e password resituisce vero se l'account con quei dati esiste, falso se non esiste.
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean login() throws FileNotFoundException, IOException{
        
        Scanner scan5 = new Scanner(System.in);
        
        System.out.println("Inserisci il tuo nome userid");
        userid = scan5.nextLine();
        System.out.println("Inserisci la tua password");
        password = scan5.nextLine();
        
        boolean esiste = false;
        this.setUserid(userid);
        this.setPassword(password);
        
        while(!esiste){
           File myObj = new File("C:\\Climate Monitoring\\OperatoriRegistrati.dati");
           Scanner myReader = new Scanner(myObj, "utf-8");
           
           while (myReader.hasNextLine() & esiste == false) {
                String data = myReader.nextLine();
                String[] credenziali = data.split("\t");
                //System.out.println(credenziali.length);
                if(credenziali[3].equals(userid) & credenziali[4].equals(password)){
                    esiste = true;
                    BufferedWriter cookie = new BufferedWriter(new FileWriter("cookie.txt"));
                    cookie.write(userid);
                    cookie.close();
                    System.out.println("Accesso effettuato, benvenuto/a " + userid);
                    return true;
                }
                
            }
           if(!esiste){
               System.out.println("Accesso fallito, l'account non esiste!");
               return false;
               
           }
        }
        System.out.println("Accesso fallito, ricontrollare le credenziali");
        return false;
        
    
    }

    /**
     * Inseriti Nome, Cognome, Codice Fiscale, Mail, Username, Password e Centro di monitoraggio creaun account e restituisce vero.
     * Restituisce falso se esiste un account con le stesse credenziali. Se la creazione va' a buon fine viene scritto un file di testo
     * chiamato Cookie.txt con il nome utente.
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean registrazione() throws FileNotFoundException, IOException{
        
        Scanner scan5 = new Scanner(System.in);
        boolean nome_valido = false;
        
        do{
            System.out.println("Inserisci il tuo nome e cognome");
            nome_e_cognome = scan5.nextLine();
            
            if(nome_e_cognome.contains("\t") | nome_e_cognome.contains("-----")){
                System.out.println("Il nome non puo' contenere il carattere tab o i 5 trattini '-----' ");
            }else{
                nome_valido = true;
            }
        
        }while(!nome_valido);
        
        
        boolean check_cf = true;                      
        do{
            System.out.println("Inserisci il tuo codice fiscale");
            codice_fiscale = scan5.nextLine().toUpperCase();
            check_cf = !this.convalida_cofice_fiscale(codice_fiscale);
        }while(check_cf);
                               
                               
        boolean check_m = true;
                               
        do{
            System.out.println("Inserisci il tuo indirizzo mail");
            email = scan5.nextLine();
            check_m = !this.convalida_mail(email);
            
            if(email.contains("-----")){
                System.out.println("L'email non puo' contenere i 5 trattini '-----' ");
            }
            
        }while(check_m & !email.contains("-----"));
                               
        boolean userid_valido = false;
        boolean password_valida = false;
        boolean centro_valido = false;
        
        do{
            System.out.println("Inserisci il tuo nome userid");
            userid = scan5.nextLine();
            
            if(userid.contains("\t") | userid.contains("-----")){
                System.out.println("L'user ID non puo' contenere tab o i 5 trattini '-----'");
            }else{
                userid_valido = true;
            }
            
        }while(!userid_valido);
            
        do{
            System.out.println("Inserisci la tua password");
            password = scan5.nextLine();
            
            if(password.contains("\t") | password.contains("-----")){
                System.out.println("La password non puo' contenere tab o i 5 trattini '-----'");
            }else{
                password_valida = true;
            }
            
        }while(!password_valida);
        
        do{
            System.out.println("Inserisci il tuo centro di monitoraggio di afferenza");
            centro_di_monitoraggio_di_afferenza = scan5.nextLine();
            
            if(password.contains("\t")){
                System.out.println("La password non puo' contenere tab");
            }else{
                centro_valido = true;
            }
            
        }while(!centro_valido);
        
        
        
        
        this.setCodice_fiscale(codice_fiscale);
        this.setEmail(email);
        this.setNome_e_cognome(nome_e_cognome);
        this.setPassword(password);
        this.setUserid(userid);
        this.setCentro_di_monitoraggio_di_afferenza(centro_di_monitoraggio_di_afferenza);
        
        
        //Separatore "\t" in ogni riga del file i campi sono separati da questa stringa
        while(true){
            //Ricerca se l'utente esiste già per non creare duplicati
            File myObj = new File("C:\\Climate Monitoring\\OperatoriRegistrati.dati");
            Scanner myReader = new Scanner(myObj, "utf-8");
            
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dati = data.split("\t");
                if(data.contains(this.getNome_e_cognome()+"\t"+this.getCodice_fiscale()+"\t"+this.getEmail()+"\t"+this.getUserid()+"\t"+this.getPassword())){
                    System.out.println("Account esistente login in corso");
                    BufferedWriter cookie = new BufferedWriter(new FileWriter("cookie.txt"));
                    cookie.write(userid);
                    cookie.close();
                    return true;//this.login(this.getUserid(), this.getPassword());
                }
                
                if(dati[1].equals(this.getCodice_fiscale())){
                    System.out.println("Il codice fiscale inserito appartiene ad un altro account");
                    return false;
                }
                
                if(dati[2].equals(this.getEmail())){
                    System.out.println("L'email inserita appartiene ad un altro account");
                    return false;
                }
                
                if(dati[3].equals(this.getUserid())){
                    System.out.println("L'userID inserito appartiene ad un altro account");
                    return false;
                }
                
            }
            
            myReader.close();
            
            

            //Ricerca centro
            File myObj2 = new File("C:\\Climate Monitoring\\CentroMonitoraggio.dati");
            Scanner myReader2 = new Scanner(myObj2, "utf-8");
            boolean trovato2 = false;
            while (myReader2.hasNextLine() & trovato2 == false) {
                String data = myReader2.nextLine();
                if(data.contains(centro_di_monitoraggio_di_afferenza)){
                    trovato2 = true;
                }
            }
            
            myReader2.close();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("OperatoriRegistrati.dati", true));
            if(trovato2){
                File operatori = new File("C:\\Climate Monitoring\\OperatoriRegistrati.dati");
                String vuoto = Files.readString(operatori.toPath());
                if(vuoto.length() == 0){
                    writer.write(this.getNome_e_cognome()+"\t"+this.getCodice_fiscale().toUpperCase()+"\t"+this.getEmail()+"\t"+this.getUserid()+"\t"+this.getPassword()+"\t"+this.getCentro_di_monitoraggio_di_afferenza());

                }else{
                    writer.write("\n"+this.getNome_e_cognome()+"\t"+this.getCodice_fiscale().toUpperCase()+"\t"+this.getEmail()+"\t"+this.getUserid()+"\t"+this.getPassword()+"\t"+this.getCentro_di_monitoraggio_di_afferenza());
                }
                writer.close();
                System.out.println("Account creato correttamente con centro di monitoraggio");
                BufferedWriter cookie = new BufferedWriter(new FileWriter("cookie.txt"));
                cookie.write(userid);
                cookie.close();
                
                return true;
            }else{
                /*
                writer.write(this.getNome_e_cognome()+"\t"+this.getCodice_fiscale().toUpperCase()+"\t"+this.getEmail()+"\t"+this.getUserid()+"\t"+this.getPassword()+"\n");
                writer.close();
                System.out.println("Account creato correttamente, login in corso");
                System.out.println("Il centro di monitoraggio inserito non e' presente nel programma, al tuo account non e' associato alcun centro di monitoraggio");
                BufferedWriter cookie = new BufferedWriter(new FileWriter("cookie.txt"));
                cookie.write(userid);
                cookie.close();*/
                System.out.println("Il centro di monitoraggio inserito non e' presente nel programma, registrazione fallita");
                return false;
            }
            
            
        }
        
    }

    public String getNome_e_cognome() {
        return nome_e_cognome;
    }

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    public String getEmail() {
        return email;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public String getCentro_di_monitoraggio_di_afferenza() {
        return centro_di_monitoraggio_di_afferenza;
    }

    @Override
    public String toString() {
        return "Account{" + "nome_e_cognome=" + nome_e_cognome + ", codice_fiscale=" + codice_fiscale + ", email=" + email + ", userid=" + userid + ", password=" + password + ", centro_di_monitoraggio_di_afferenza=" + centro_di_monitoraggio_di_afferenza + '}';
    }
    
    
    
    
    
}
