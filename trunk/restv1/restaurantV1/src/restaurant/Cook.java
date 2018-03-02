/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jazz
 */
public class Cook implements Runnable {

    Restaurant rest;
    int orderCount;

    public Cook(Restaurant rest) {
        this.rest = rest;
    }

    /**
     * Process the orders in FIFO order.
     */
    public void procesOrders() {
        while (!rest.orderQEmpty()) {
            Order order = rest.returnOrder();
            int orderNR = order.getNumber();
            for (OrderLine ol : order) { //while (order.hasOrderLines()) {
                //OrderLine ol = order.getOrderLine();
                int mealNR = ol.getMealNR();
                int persons = ol.getPersons();
                for (int p = 0; p < persons; p++) {
                    rest.addToReadyQ(this.
                            prepareMeal(orderNR, mealNR));
                }
            }
        }
    }

    /**
     * Prepares a meal according to recipe (preparation time).
     *
     * @param orderNR
     * @param mealNR
     * @return the prepared meal.
     */
    private Meal prepareMeal(int orderNR, int mealNR) {
        Map<Integer, Recipe> recipes = rest.getRecipes();
        Recipe recipe = recipes.get(mealNR);
        String mealName = recipe.getName();
        int procTime = recipe.getPreparationTime();
        try {
            Thread.sleep(procTime);
            rest.addToCookTime(procTime);
            rest.incrementMealsPrepared();
        } catch (InterruptedException ex) {
            Logger.getLogger(Restaurant.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return new Meal(orderNR, mealNR, mealName);
    }

    @Override
    public void run() {
        this.procesOrders();
    }

}
