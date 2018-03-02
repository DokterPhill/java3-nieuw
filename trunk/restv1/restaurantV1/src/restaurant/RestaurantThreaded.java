/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.io.Console;

/**
 *
 * @author Peter Verstappen
 */
public class RestaurantThreaded {
    
    private volatile Restaurant restaurant;
    private final Customer mrBig;
    private Object lock;
    
    private final Thread threadTakeOrders;
    private final Thread threadCookMeals;
    private final Thread threadServeMeals;
    
    public RestaurantThreaded(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.mrBig = new Customer(restaurant.getMenuNumbers());
        
        this.lock = new Object();
        
        threadTakeOrders = takeOrders();
        threadCookMeals = cookMeals();
        threadServeMeals = serveMeals();
    }
    
    public void openRestaurant() {
        restaurant.printMenu();
        restaurant.openRestaurant();
        restaurant.setCustomer( mrBig );
        
        threadTakeOrders.start();
        threadCookMeals.start();
        threadServeMeals.start();

        try {
            threadTakeOrders.join();
            threadCookMeals.join();
            threadServeMeals.join();
        } catch (InterruptedException e) {
        }
        
        System.out.println(mrBig.areYouSattisfied("Are you sattisfied?"));
        restaurant.closeRestaurant();
    }
    
    private Thread takeOrders() {
        //threadOrders deals with the orders of the customer.
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while(restaurant.getIsRestaurantOpen()) {
                    try {
                        synchronized (lock) {
                            restaurant.submitOrder(mrBig.wouldLike());
                            lock.notifyAll();
                        }
                    } catch (RestaurantException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }, "ThreadWaiterOrders");
    }
    
    private Thread cookMeals() {
        //Cook processes the orders and prepares the meals
        return new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (!restaurant.hasOrders()) {
                        try { lock.wait(); }
                        catch (InterruptedException e) { }
                    }
                    
                    restaurant.procesOrders();
                    lock.notifyAll();
                }
            }
        }, "ThreadCook");
    }
    
    private Thread serveMeals() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    while(restaurant.getIsRestaurantOpen()) {
                        while (!restaurant.hasMeals()) {
                            try { lock.wait(); }
                            catch (InterruptedException e) { }
                        }

                        restaurant.serveReadyMeals();
                        lock.notifyAll();
                    }
                }
            }
        }, "ThreadWaiterServing");
    }
    
}
