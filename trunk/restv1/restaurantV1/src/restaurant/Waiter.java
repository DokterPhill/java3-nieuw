/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jazz
 */
public class Waiter implements Runnable {

    Restaurant rest;
    int orderCount;
    String[] ordered;
    
    public Waiter(Restaurant rest, String[] ordered){
        this.ordered = ordered;
        this.rest = rest;
    }
    
    //Returnvalue necessary?
    /**
     * Place order. An order is a list of strings, each string interpreted as an
     * integer pair, separated by a comma and possibly white space. The first
     * integer is the meal number from the menu, the second value is the number
     * of servings.
     *
     * @param ordered array of strings
     * @return the next order number
     * @throws RestaurantException
     */
    public void submitOrder() throws RestaurantException {
        orderCount = rest.getOrderCount();
        orderCount++;
        Order order = new Order(orderCount);
        for (int i = 0; i < ordered.length; i++) {
            String[] lineParts = ordered[i].split("\\s*,\\s*", 2);
            int mealNR = 0;
            int servings = 0;
            try {
                mealNR = Integer.parseInt(lineParts[0].trim());
                servings = Integer.parseInt(lineParts[1].trim());
            } catch (NumberFormatException nfe) {
                throw new RestaurantException(nfe);
            }
            if (!rest.getRecipes().containsKey(mealNR)) {
                // prepare a 401, with as many servings asked.
                Meal notAvailable = new Meal(orderCount, 404, String.format(
                        "Menu item %d does not exist.", mealNR));
                for (int s = 0; s < servings; s++) {
                    System.out.println(rest.getCustomer().serveTo(notAvailable));
                }
                //                throw new RestaurantException( "Order nr. " + orderCount
                //                        + ": a non existing meal (nr.=" + mealNR + ") ordered!" );
            } else {
                order.addMeal(mealNR, servings);
            }
        }
        System.out.println(order.toString());
        rest.addOrdertoQ(order);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Restaurant.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        //return orderCount;
    }

    @Override
    public void run() {
        try {
            this.submitOrder();
        } catch (RestaurantException ex) {
            Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
