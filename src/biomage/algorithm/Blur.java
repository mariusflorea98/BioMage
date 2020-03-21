/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

/**
 *
 * @author mariuster
 */
public class Blur implements iFilter {
    private final String id="blur";
    
    public void execute(){
        System.out.println("blur call");
    }
    
    public String getId(){
        return this.id;
    }
}
