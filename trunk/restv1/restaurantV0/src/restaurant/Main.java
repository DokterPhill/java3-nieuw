package restaurant;

import java.util.Iterator;
import static restaurant.Utils.*;

/**
 * Restaurant driver class. This implements Donalds orginal 'algorithm', take
 * two orders, cook and serve them, all in a loop until the customer is
 * satisfied.
 *
 * @author ode
 * @author hom
 */
public class Main {

    public static void main( String[] args ) {
        Restaurant restaurant = new Restaurant( "Mei Ling" );
        restaurant.printMenu();
        restaurant.openRestaurant();
        Customer mrBig = new Customer(restaurant.getMenuNumbers());
        restaurant.setCustomer( mrBig );
        
        //threadOrders deals with the orders of the customer.
        Thread threadOrders = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mrBig.iterator().hasNext()) {
                    try {
                        restaurant.submitOrder(mrBig.wouldLike());
                    } catch (RestaurantException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        threadOrders.start();
        
        //Cook processes the orders and prepares the meals
        Thread threadCook = new Thread(new Runnable() {
            @Override
            public void run() {
                while(restaurant.getIsRestaurantOpen()) {
                    if(restaurant.hasOrders()) {
                        restaurant.procesOrders();
                    }
                }
            }
        });
        threadCook.start();
        
        Thread threadServe = new Thread(new Runnable() {
            @Override
            public void run() {
                while (restaurant.getIsRestaurantOpen()) {
                    if (restaurant.hasMeals()) {
                        restaurant.serveReadyMeals();
                    }
                }
            }
        });
        threadServe.start();
        
        while(!threadOrders.isAlive() && !threadCook.isAlive() && !threadServe.isAlive()) {
            System.out.println( mrBig.areYouSattisfied("Are you sattisfied?") );
            restaurant.closeRestaurant();
        }
    }
}
