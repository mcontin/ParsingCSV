package com.mattia.parsingcsv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
*/

public class Parser {

    public void doStuff () {
        try{
            //17 ';', 18 keywords
            FileInputStream file = new FileInputStream("/app/src/main/res/raw/attiv_commerc.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(file));
            String line = null;	//read header
            br.readLine();
            String[] element = new String[18];	//string array to store data of a line

            int id = 0;	//id dell'indirizzo

            Map<Address, ArrayList<Integer>> addresses = new HashMap<Address, ArrayList<Integer>>();
            Address address = null;

            ArrayList<Integer> sameAddress = new ArrayList<Integer>();	//lista di id di attività allo stesso indirizzo
            ArrayList<Integer> temp = null;	//lista temporanea per confronto
            while((line = br.readLine()) != null){
                //loop per prendere la linea completa di ogni record
                while(countOccurrences(line, ';') < 17){
                    line = line.concat(br.readLine());
                }

                //array che salva i valori del record
                element = line.split(";");

                //se l'indirizzo è valido, crea oggetto
                if(element[2].length() > 0 && element[3].length() > 0 && element[4].length() > 0){
                    address = new com.mattia.parsingcsv.Address(element[2], element[3], element[4], element[5]);
                    ++id;

                    //se l'indirizzo non esiste nella map, crea una lista e ci aggiunge l'id
                    if(!addresses.containsKey(address)){
                        sameAddress = new ArrayList<Integer>();
                        sameAddress.add(id);
                        addresses.put(address, sameAddress);
                    }
                    else { //altrimenti aggiunge l'id alla lista e la reinserisce al relativo indirizzo
                        temp = addresses.get(address);
                        temp.add(id);
                    }
                }
            }


            //System.out.println("Numero record: " + (id-1));

            FileWriter fw = new FileWriter("/app/src/main/res/raw/output_attivita_commerciali.csv");
            PrintWriter pw = new PrintWriter(fw);
            pw.println("ID;Latitudine;Longitudine;Indirizzo Google");

            /*
            GeoApiContext context = null;
            GeocodingResult[] results = null;

            int k=0; //indice temporaneo per testare 10 elementi

            for(Address tAddress: addresses.keySet()) {
                temp = addresses.get(tAddress);
                if(k<10){
                    //System.out.println(tAddress.toString());	//sysout per vedere l'indirizzo preso dal csv
                    context = new GeoApiContext().setApiKey(args[0]);
                    results =  GeocodingApi.geocode(context,
                            "Milano " + tAddress.toString()).await();
                    //System.out.println(results[0].formattedAddress);	//sysout per vedere come google traduce l'indirizzo
                    for(int index = 0; index<temp.size(); ++index)
                        pw.println(temp.get(index)+";"	//indice attività
                                +results[0].geometry.location.lat+";"	//latitudine
                                +results[0].geometry.location.lng+";"	//longitudine
                                +results[0].formattedAddress);	//indirizzo secondo google
                }
                ++k;
            }
            */
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int countOccurrences(String str, char c){
        int i = 0;
        for(int j = 0; j<str.length(); ++j){
            if(str.charAt(j) == c)
                ++i;
        }
        return i;
    }
}

