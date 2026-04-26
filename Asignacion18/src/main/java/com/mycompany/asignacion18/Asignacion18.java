/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.asignacion18;


import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.types.ObjectId;

/**
 *
 * @author daya
 */
public class Asignacion18 {

    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = cliente.getDatabase("restauranteAsignacion18");
        MongoCollection<Document> col = db.getCollection("restaurantes");
        
       //Insertar un solo documento:
        //• name = "Café de la Plaza"
        //• stars = 4.3
        //• categories = ["Café","Postres","Desayuno"]
        Document document = new Document();
        document.append("name", "Cafe de la plaza");
        document.append("stars","4.3");
        document.append("categories",Arrays.asList(new String[]{"Cafe","Postres","Desayunos"}));
        col.insertOne(document);
        
        //Insertar varios documentos adicionales:
        //• "Espresso Express" → stars = 4.8, categories = ["Café","Rápido","Takeaway"]
        //• "The Tea House" → stars = 3.9, categories = ["Té","Infusiones","Postres"]
        //• "Morning Brew" → stars = 4.0, categories = ["Café","Desayuno","Bakery"]
         ArrayList<Document> lista= new ArrayList<>();
         lista.add(new Document("name","Expresso Express")
                 .append("stars",4.8)
                 .append("categories",Arrays.asList(new String[]{"Cafe","Rapido","Desayunos"})));
         
         lista.add(new Document("name","The Tea House")
                .append("stars",3.9)
                .append("categories", Arrays.asList(new String[]{"Te","Infusiones","Postres"})));
        
         
         lista.add(new Document("name","Morning Brew")
                 .append("stars",4.0)
                 .append("categories",Arrays.asList(new String[]{"Cafe","Desayuno","Bakery"})));
         
           col.insertMany(lista);
           
        //Filtros para mostrar:
          //• Documentos con stars >= 4.5.
        System.out.println("Restaurantes arriba de 4.5 estrellas");
        for (Document d : col.find(
        Filters.and(
            Filters.gte("stars", 4.5),
            Filters.lte("stars", 5)
        )
        )) {
            System.out.println(d.toJson());
        }  
        System.out.println("");
        
        //• Documentos cuyo nombre contiene "Café".
        System.out.println("Documentos cuyo nombre contiene cafe");
        for(Document d: col.find(Filters.regex("name","Cafe"))){
            System.out.println(d.toJson());   
        }
        System.out.println("");
        
        //• Documentos con stars entre 3 y 4.3.
        System.out.println("Restaurantes entre 3 y 4.3 estrellas");
        for (Document d : col.find(
        Filters.and(
            Filters.gte("stars", 3),
            Filters.lte("stars", 4.3)
        )
        )) {
            System.out.println(d.toJson());
        }  
        System.out.println("");
        
        //• Documentos cuyo nombre empieza con "T".
        System.out.println("Documentos cuyo nombre empieza con T");
        for(Document d: col.find(Filters.regex("name","^T"))){
            System.out.println(d.toJson());   
        }
        
       //• Documentos con categories que incluyan "Postres".
       System.out.println("Documentos con categoria Postres");
        for(Document d: col.find(Filters.eq("categories", "Postres"))){
            System.out.println(d.toJson());
        }
        System.out.println("");
        
        //Updates:
        //• Cambiar stars a 4.5 para "Morning Brew".
        col.updateOne(
                Filters.eq("name", "Morning Brew"),
                Updates.set("stars", 4.5)
        );

        System.out.println("Se cambio stars a 4.5 para Morning Brew");


        //• Incrementar stars +0.2 para documentos con categories que contengan
        //"Bakery" o "Desayuno".
        col.updateMany(
                Filters.or(
                        Filters.eq("categories", "Bakery"),
                        Filters.eq("categories", "Desayuno")
                ),
                Updates.inc("stars", 0.2)
        );

        System.out.println("Se incremento stars +0.2 a Bakery o Desayuno");


        //• Agregar campos phone = "555-111-2222" y open = true a "Café de la Plaza".
        col.updateOne(
                Filters.eq("name", "Cafe de la plaza"),
                Updates.combine(
                        Updates.set("phone", "555-111-2222"),
                        Updates.set("open", true)
                )
        );

        System.out.println("Se agrego phone y open a Cafe de la plaza");
        System.out.println("");

            
        //Deletes:
        //• Eliminar documento con name = "Espresso Express".
        col.deleteOne(Filters.eq("name", "Expresso Express"));

        System.out.println("Se elimino Expresso Express");


        //• Eliminar todos los documentos con stars < 4.
        col.deleteMany(Filters.lt("stars", 4));

        System.out.println("Se eliminaron restaurantes con menos de 4 estrellas");


        //• Eliminar documentos con categories que contengan "Takeaway" o "Infusiones".
        col.deleteMany(
                Filters.or(
                        Filters.eq("categories", "Takeaway"),
                        Filters.eq("categories", "Infusiones")
                )
        );

        System.out.println("Se eliminaron documentos con Takeaway o Infusiones");
        }}
