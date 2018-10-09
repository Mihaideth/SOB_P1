/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.urv.deim.sob;

import java.util.LinkedList;

/**
 *
 * @author Mihai Serban Copil
 */
public class ProfessorTfgs {
    private Tfgs llistatfgsprofe;
    private User professor=new User();

    public Tfgs getLlistatfgsprofe() {
        return llistatfgsprofe;
    }

    public void setLlistatfgsprofe(Tfgs llistatfgsprofe) {
        this.llistatfgsprofe = llistatfgsprofe;
    }

    public User getProfessor() {
        return professor;
    }

    public void setProfessor(User professor) {
        this.professor = professor;
    }
    
    
    public ProfessorTfgs(User professor, Tfgs llistatfgs){
        this.professor=professor;
        llistatfgsprofe=llistatfgs;
    }
    
}
