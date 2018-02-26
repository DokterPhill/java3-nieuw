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
public class WaiterOrders extends Employee {

    public WaiterOrders(String employeeName) {
        super(employeeName, "Waiter");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("TODO: delegate taking order functions from Main.java to this class");
    }
    
}
