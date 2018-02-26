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
public class RestaurantThreaded {
    
    private volatile Restaurant restaurant;
    private final Customer mrBig;
    
    private final Thread threadTakeOrders;
    private final Thread threadCookMeals;
    private final Thread threadServeMeals;
    
    public RestaurantThreaded(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.mrBig = new Customer(restaurant.getMenuNumbers());
        
        threadTakeOrders = takeOrders();
        threadCookMeals = cookMeals();
        threadServeMeals = serveMeals();
    }
    
    public synchronized void openRestaurant() {
        restaurant.printMenu();
        restaurant.openRestaurant();
        restaurant.setCustomer( mrBig );
        
        threadTakeOrders.start();

        try {
            threadTakeOrders.join();
            threadCookMeals.join();
            threadServeMeals.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(mrBig.areYouSattisfied("Are you sattisfied?"));
        restaurant.closeRestaurant();
    }
    
    private synchronized Thread takeOrders() {
        //threadOrders deals with the orders of the customer.
        return new Thread(new Runnable() {
            @Override
            public void run() {
                threadCookMeals.start();
                threadCookMeals.interrupt();
                int numberOfOrders = 4; //(int)(Math.random() * 32);
                for (int i = 0; i < numberOfOrders; i++) {
                    try {
                        restaurant.submitOrder(mrBig.wouldLike());
                        if(threadCookMeals.isInterrupted()) {
                            threadCookMeals.notify();
                        }
                    } catch (RestaurantException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
    }
    
    private synchronized Thread cookMeals() {
        //Cook processes the orders and prepares the meals
        return new Thread(new Runnable() {
            @Override
            public void run() {
                threadServeMeals.start();
                threadServeMeals.interrupt();
                while (restaurant.hasOrders()) {
                    restaurant.procesOrders();
                    if(threadServeMeals.isInterrupted()) {
                        threadServeMeals.notify();
                    }
                    threadCookMeals.interrupt();
                }
            }
        });
    }
    
    private synchronized Thread serveMeals() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while (restaurant.hasMeals()) {
                    restaurant.serveReadyMeals();
                    threadServeMeals.interrupt();
                }
            }
        });
    }
    
}
