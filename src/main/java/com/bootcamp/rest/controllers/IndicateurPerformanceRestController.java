/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.rest.controllers;

import com.bootcamp.jpa.entities.IndicateurPerformance; 
import com.bootcamp.jpa.repositories.IndicateurPerformanceRepository; 
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author root
 */
@Path("/indicateurs")
public class IndicateurPerformanceRestController {

    IndicateurPerformanceRepository ir = new IndicateurPerformanceRepository("punit-mysql");
    IndicateurPerformance indicateurPerformance = new IndicateurPerformance();
    List<IndicateurPerformance> indicateurPerformances = new  ArrayList<>();

//Cette methode cree une nouvelle instance de indicateur et la retourne
    @GET
    @Path("/unpersist_list")
    @Produces("application/json")
    public Response getList(){
        return Response.status(200).entity(new IndicateurPerformance()).build();
    }
    /*
    *
    *La methode ci-dessous  cree une instance de IndicateurPerformanceRepository (ir) , 
    *applique sur cette derniere la methode findAll de cette classe
    *puis essaie de retourner la liste des indicateurs persistés.
    *si tout ce passe bien la liste est retournee si l'exeption est levee 
    *et le message d'erreur est retournee dans le catch.
    * Cette liste est accessible avec URI /list
    *
    */
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getListIndicateurPerformanceFromDB(){
        try {
            return Response.status(200).entity(ir.findAll()).build();
        } catch (Exception e) {
            return Response.status(404).entity("Erreur ! Veuillez revoir l' URL et reessayez").build();
        }      
    }
    /*
    *
    *La methode  ci-dessous cree une instance de IndicateurPerformanceRepository (ir) , 
    *applique sur cette derniere la methode findById de la classe
    *pour retourner le indicateur   persisté avec l'indentifiant specifié dans 
    *l'URI d'acces qui est /list/id ou id est l'identifiant.
    *
    */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getIndicateurPerformanceByIdFromDB(@PathParam("id") int id) throws SQLException{
        IndicateurPerformance indicateur = ir.findById(id);
        try {
            //Verification de l'existance de l'id dans la base de donnee
            
            if(indicateur == null){
            return Response.status(500).entity("Aucun indicateur ne correspond a l'indifiant indique").build();
        } else
           return Response.status(200).entity(indicateur).build();
        } catch (Exception e) {
             return Response.status(401).entity("Veuillez verifier si c'est bien un URL correcte qque vous entrez").build();
        }  
    }
    
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response createBene(IndicateurPerformance indicateur) throws SQLException {
               
       
           
        if(existe(indicateur)){
            return Response.status(202).entity("Cette entite existe deja").build();
        }else{
            try {
                ir.create(indicateur);
            } catch (SQLException ex) {
               return Response.status(202).entity("Une erreur est survenu lors de la creation de l entite").build();
            }
          return Response.status(404).entity("Entite est cree est succes").build();  
        }

    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBene(IndicateurPerformance indicateur) throws SQLException {
        
        
        ir.update(indicateur);
        return Response.status(202).entity("l'entite est bien mise a jour ou cree s'il n'existait pas").build();
       
    }
    
    @DELETE
    @Path("/delete/{id}")
    public Response deleteBene(@PathParam("id") int id) throws SQLException {
          IndicateurPerformance b= ir.findById(id);
        try {
            ir.delete(b);
            return Response.status(202).entity("l'entite est supprime est succes").build();
        } catch (Exception e) {
             return Response.status(404).entity("Erreur de suppression de l'entite").build();
        }
    }
    
    @GET//Signifie que c est une methode de lecture
    @Path("/list/tries")//URI pour avoir la liste des Bailleurs tries suivant un attribut donne
    @Produces("application/json")//Permet de dire a la methode quelle va produire du JSON
    public Response triBailleur(@QueryParam("sort") String attribut){
       List<IndicateurPerformance>  bail = ir.findAll();//Recuperation de tous les bailleurs
       //Comparaison de la liste retournee
       Collections.sort(bail, (IndicateurPerformance indicateurPerformance1, IndicateurPerformance indicateurPerformance2) -> {
           int result =0;
           List<PropertyDescriptor> propertyDescriptors ;
           try {
               //La methode returnAskedPropertiesIfExist verifie si l attribut specifie
               //par l utilisateur fait partir des proprietse de la classe
               //Si oui la liste d attribut
               propertyDescriptors = ir.returnAskedPropertiesIfExist(IndicateurPerformance.class,attribut);
               if(!propertyDescriptors.isEmpty()){ //Verifions si la liste retournee n est vide
                   for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                       
                       //Suivant le nom de l attribut on compare pour le tri
                       switch (propertyDescriptor.getName()){
                           case "nom":
                               result = indicateurPerformance1.getNom().compareToIgnoreCase(indicateurPerformance2.getNom());
                               break;
                           case "TypeBailleur":
                               result = indicateurPerformance1.getTypeIndicateur().compareTo(indicateurPerformance2.getTypeIndicateur());
                               break;
                           default:
                               result =0;
                               break;
                       }
                   }
               }
           } catch (IntrospectionException | SQLException ex) {
               Logger.getLogger(IndicateurPerformanceRestController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           
           return result;
       });
       
       return Response.status(200).entity(bail).build();
   }
   
      @GET
        @Path("/list/recherche")
        @Produces("application/json")
        public Response SearcheBailleur(@QueryParam("attribut") String attribut,@QueryParam("value") String value) throws IntrospectionException, SQLException {
 
             List<PropertyDescriptor> propertyDescriptors = ir.returnAskedPropertiesIfExist(IndicateurPerformance.class,attribut);
             if(!propertyDescriptors.isEmpty()){
                 for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                     indicateurPerformances = ir.findSearche(attribut, value);
                     
                }
             }   
             
            return Response.status(200).entity(indicateurPerformances).build(); 
        }     
        
   @GET//Signifie qu il s agit d une methode de lecture 
   @Path("/attribut/essentiels/{id}")//Avec cette URI on donnera seulement les attributs que l on souhaite afficher pour un bailleur donne
   @Produces(MediaType.APPLICATION_JSON)//Production de JSON
   public Response getByIdParam(@PathParam("id") int id, @QueryParam("fields") String fields) throws SQLException, IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {

       HashMap<String, Object> responseMap = magik(id,fields);
       return Response.status(200).entity(responseMap).build();
   }
    
    @GET
        @Path("/list/paginer")
        @Produces("application/json")
        public Response findPagingBailleur(@QueryParam("offset") 
        @DefaultValue("1") Integer offset, @QueryParam("limit") @DefaultValue("25") Integer limit) {
            return Response.status(200).entity(ir.findPerPager(offset, limit)).build();
        }
      
    /**
     * Cette methode va verifier avant chaque insertion dans la base 
     *si  l entite existe deja
     *et elle renvoie true dans le cas echeant
    */
    
    private Boolean existe(IndicateurPerformance new_indicat){
        boolean b=false;
         List<IndicateurPerformance> indicateurs =ir.findAll();
            for (IndicateurPerformance indicateur1 : indicateurs) {
                b=new_indicat.equals(indicateurPerformance);
            }
        return b;
    }
    /**
     * Cette methode selection l entite de l identifiant est egale id si elle exite
     * puis verifie si les attributs demandes sur l attribut existent
     * si oui l entite est renvoyee
     * 
     */
    private HashMap magik(int id,String flds) throws IntrospectionException, SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
       
       HashMap<String, Object> responseMap = new HashMap<>();
       indicateurPerformance = ir.findById(id);
       List<PropertyDescriptor> propertyDescriptors = ir.returnAskedPropertiesIfExist(IndicateurPerformance.class,flds);
       
       if(!(propertyDescriptors.isEmpty())){
           for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
               Method method = propertyDescriptor.getReadMethod();
               responseMap.put(propertyDescriptor.getName(), method.invoke(indicateurPerformance));
           }
       }
       return responseMap;
    }
}
