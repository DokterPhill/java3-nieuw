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
        
        //TODO: delegate taking orders to the waiter class
        //Waiter 1 takes the orders
        
        try {
            restaurant.submitOrder( mrBig.wouldLike() );
        } catch ( RestaurantException e ) {
            System.out.println( e.getMessage() );
        }
        
        try {
            restaurant.submitOrder( mrBig.wouldLike() );
        } catch ( RestaurantException e ) {
            System.out.println( e.getMessage() );
        }
        
        //Cook processes the orders and prepares the meals
        new Cook("Louie").start();
        restaurant.procesOrders();
        
        //Waiter 2 serves the meals
        restaurant.serveReadyMeals();
        
        try {
            restaurant.submitOrder( mrBig.wouldLike() );
        } catch ( RestaurantException e ) {
            System.out.println( e.getMessage() );
        }
        try {
            restaurant.submitOrder( mrBig.wouldLike() );
        } catch ( RestaurantException e ) {
            System.out.println( e.getMessage() );
        }
        restaurant.procesOrders();
        restaurant.serveReadyMeals();
        System.out.println( mrBig.areYouSattisfied("Are you sattisfied?") );
        restaurant.closeRestaurant();
    }
}
