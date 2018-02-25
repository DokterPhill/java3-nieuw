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
public abstract class Employee extends Thread {
    
    private String employeeName;
    public String getEmployeeName() {
        return employeeName;
    }
    
    private String occupation;
    public String getOccupation() {
        return occupation;
    }

    public Employee(String employeeName, String occupation) {
        this.employeeName = employeeName;
        this.occupation = occupation;
    }
    
    @Override
    public abstract void run();
    
}
