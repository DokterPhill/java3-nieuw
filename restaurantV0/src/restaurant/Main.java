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
        RestaurantThreaded restaurantThreaded = new RestaurantThreaded(new Restaurant( "Mei Ling" ));
        restaurantThreaded.openRestaurant();
    }
}
