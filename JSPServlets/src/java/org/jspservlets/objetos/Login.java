/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jspservlets.objetos;

/**
 *
 * @author marcelo
 */
public class Login {
    private String name;
    private String pass;

    public Login(){
        name = null;
        pass = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
