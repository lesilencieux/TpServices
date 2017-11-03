package com.bootcamp.jpa.entities;

import com.bootcamp.jpa.enums.TypeIndicateur;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-03T13:11:45")
@StaticMetamodel(IndicateurPerformance.class)
public class IndicateurPerformance_ { 

    public static volatile SingularAttribute<IndicateurPerformance, String> valeur;
    public static volatile SingularAttribute<IndicateurPerformance, String> nature;
    public static volatile SingularAttribute<IndicateurPerformance, String> libelle;
    public static volatile SingularAttribute<IndicateurPerformance, TypeIndicateur> typeIndicateur;
    public static volatile SingularAttribute<IndicateurPerformance, Integer> id;
    public static volatile SingularAttribute<IndicateurPerformance, String> propriete;
    public static volatile SingularAttribute<IndicateurPerformance, String> nom;

}