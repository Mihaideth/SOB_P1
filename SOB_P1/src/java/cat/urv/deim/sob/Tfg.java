/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.urv.deim.sob;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jordi Borrell
 */
public class Tfg {

    private int id;
    private String titol;
    private String descripcio = "";
    private LinkedList<String> professors;
    private String estat;   //proposat, assignat, acabat, pendent de defensa, defensat.
    private String estudiants = "";
    private String estudis;
    private String recursos = "";
    private Date dataDefensa;
    private String qualificació = "";        //Nota (entre 0 i 10 o MH - per Matrícula d'Honor).
    private Date dataCreacio;
    private Date dataModificacio;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public LinkedList<String> getProfessors() {
        return professors;
    }

    public void setProfessors(LinkedList<String> professors) {
        this.professors = professors;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getEstudiants() {
        return estudiants;
    }

    public void setEstudiants(String estudiants) {
        this.estudiants = estudiants;
    }

    public String getEstudis() {
        return estudis;
    }

    public void setEstudis(String estudis) {
        this.estudis = estudis;
    }

    public String getRecursos() {
        return recursos;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }

    public Date getDataDefensa() {
        return dataDefensa;
    }

    public void setDataDefensa(Date dataDefensa) {
        this.dataDefensa = dataDefensa;
    }

    public String getQualificacio() {
        return qualificació;
    }

    public void setQualificacio(String qualificació) {
        this.qualificació = qualificació;
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public Date getDataModificacio() {
        return dataModificacio;
    }

    public void setDataModificacio(Date dataModificacio) {
        this.dataModificacio = dataModificacio;
    }

}
