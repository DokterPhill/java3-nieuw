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
        Restaurant china = new Restaurant( "Mei Ling" );
        china.printMenu();
        china.openRestaurant();
        Customer mrBig = new Customer(china.getMenuNumbers());
        china.setCustomer( mrBig );
        try {
            china.submitOrder( mrBig.wouldLike() );
        } catch ( RestaurantException e ) {
            System.out.println( e.getMessage() );
        }
        try {
            china.submitOrder( mrBig.wouldLike() );
        } catch ( RestaurantException e ) {
            System.out.println( e.getMessage() );
        }
        china.procesOrders();
        china.serveReadyMeals();
        try {
            china.submitOrder( mrBig.wouldLike() );
        } catch ( RestaurantException e ) {
            System.out.println( e.getMessage() );
        }
        try {
            china.submitOrder( mrBig.wouldLike() );
        } catch ( RestaurantException e ) {
            System.out.println( e.getMessage() );
        }
        china.procesOrders();
        china.serveReadyMeals();
        System.out.println( mrBig.areYouSattisfied("Are you sattisfied?") );
        china.closeRestaurant();
    }
}
