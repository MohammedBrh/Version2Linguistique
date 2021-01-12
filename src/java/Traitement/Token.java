/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Traitement;

/**
 *
 * @author Mohammed
 */
public class Token {
    private String Stem;
    private String Procl;
    private String Encli;

    public Token() {
    }

    public Token(String Stem, String Procl, String Encli) {
        this.Stem = Stem;
        this.Procl = Procl;
        this.Encli = Encli;
    }

    public String getStem() {
        return Stem;
    }

    public void setStem(String Stem) {
        this.Stem = Stem;
    }

    public String getProcl() {
        return Procl;
    }

    public void setProcl(String Procl) {
        this.Procl = Procl;
    }

    public String getEncli() {
        return Encli;
    }

    public void setEncli(String Encli) {
        this.Encli = Encli;
    }
    
    
}
