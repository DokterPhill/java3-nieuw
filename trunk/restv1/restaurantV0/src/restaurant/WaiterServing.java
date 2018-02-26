/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

/**
 *
 * @author Peter Verstappen
 */
public class WaiterServing extends Employee {

    public WaiterServing(String employeeName) {
        super(employeeName, "Waiter");
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
