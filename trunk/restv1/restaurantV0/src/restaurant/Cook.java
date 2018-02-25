/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import static restaurant.Utils.printWhiteline;

/**
 * 
 * @author Peter Verstappen
 * 
 * Cook class
 * A Runnable class that Extends the Employee class.
 * This class handles the preparations of the meal.
 * 
**/
public class Cook extends Employee {

    public Cook(String employeeName) {
        super(employeeName, "Cook");
    }

    @Override
    public void run() {
        System.out.println(getEmployeeName() + " the " + getOccupation() + " ready to work.");
        printWhiteline();
    }
    
}
