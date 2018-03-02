/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.util.Map;
import static restaurant.Utils.printSeparator;

/**
 *
 * @author Jazz
 */
public class Server implements Runnable {

    private Restaurant rest;

    public Server(Restaurant rest) {
        this.rest = rest;
    }

    public void serveReadyMeals() {
        printSeparator("Pleased to serve your meals");
        while (rest.hasMeals()) {
            Meal meal = rest.getNextMeal();
            Map<Integer, Recipe> recipes = rest.getRecipes();
            rest.increaseTurnover(recipes.get(meal.getNumber()).getPrice());
            System.out.println(rest.getCustomer().serveTo(meal));
        }
        printSeparator("Bon appetit");

    }

    @Override
    public void run() {
        this.serveReadyMeals();
    }

}
