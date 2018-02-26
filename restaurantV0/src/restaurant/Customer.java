/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.util.Iterator;
import java.util.Set;
import nl.fontys.sevenlo.util.TallyMap;
import nl.fontys.sevenlo.util.TallyMap2;

/**
 * A simple eater.
 *
 * @author hom
 */
class Customer implements Iterable<int[][]> {

    private final int[][][] orders = new int[][][]{
        { { 11, 7 }, { 13, 4 } },
        { { 44, 4 }, { 47, 8 }, { 13, 9 }, { 11, 5 } },
        { { 45, 1 }, { 47, 2 }, { 10, 2 } },
        { { 10, 2 }, { 33, 2 }, { 19, 2 } }
    };

    @Override
    public Iterator<int[][]> iterator() {
        return new Iterator() {
            private volatile int idx = -1; // start below

            @Override
            public boolean hasNext() {
                return idx < orders.length - 1;
            }

            @Override
            public int[][] next() {
                return orders[ ++idx ];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException( "Not supported yet." );
            }
        };
    }
    private Iterator<int[][]> itr = iterator();
    private static String[] emptyOrder = new String[ 0 ];

    /**
     * Get next order, if Any.
     *
     * @return array of string, representing the order or empty array when
     *         satisfied.
     *
     */
    public String[] wouldLike() {
        if ( itr.hasNext() ) {
            int[][] r = itr.next();
            String[] result = new String[ r.length ];
            for ( int i = 0; i < r.length; i++ ) {
                int mealNr = r[ i ][ 0 ];
                int servings = r[ i ][ 1 ];
                orderedMap.addTallyForKey( mealNr, servings );
                result[ i ] = String.format( "%d,%d", r[ i ][ 0 ], r[ i ][ 1 ] );
            }
            return result;
        } else {
            return emptyOrder;
        }
    }

    /**
     * Serve a meal to this customer.
     *
     * @param meal
     *
     * @return the answer/payment of the customer.
     */
    public String serveTo( Meal meal ) {
        servedMap.addTallyForKey( meal.getNumber(), 1 );
        if ( meal.getNumber() == 404 ) {
            return "Sorry to see that " + meal.getName();
        } else {
            return "Ha, my order, mjamy " + meal + ", looks good";
        }
    }
    private final TallyMap<Integer> orderedMap;
    private final TallyMap<Integer> servedMap;

    /**
     * Create a customer, handing the menu items.
     *
     * @param menuNumbers the items from the menu that can be ordered.
     */
    Customer( Set<Integer> menuNumbers ) {
        orderedMap = new TallyMap2<>( menuNumbers );
        servedMap = new TallyMap2<>( menuNumbers );
    }

    /**
     * End of the transactions. Ask if customer's expectation was met.
     *
     * @param question to the customer, possibly spoken by the waiter.
     *
     * @return
     */
    public String areYouSattisfied( String question ) {
        //orderedMap.incrementForKey( 13);
        System.out.println( "ordered:" + orderedMap.toString() );
        System.out.println( "received:" + servedMap.toString() );
        return "You asked \"" + question + "\" " + ( orderedMap.snapShotEquals(
                servedMap )
                        ? "Yes, very. Thank you\n"
                        : "No I missed some: " + orderedMap.snapShotDiff( servedMap ) + "\n" );
    }
}
