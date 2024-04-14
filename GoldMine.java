import java.util.Random;

public class GoldMine {

    // Constants for the program
    private static final int GOLD_SOURCES = 15; // Number of gold sources
    private static final int MAX_CYCLE = 50; // Maximum number of cycles
    private static final double MIN_GOLD = 0.0; // Minimum value for gold coordinates
    private static final double MAX_GOLD = 100.0; // Maximum value for gold coordinates
    private static final double PURITY = 10; // Minimum purity threshold

    // Random number generator
    private static Random rand = new Random();

    // Main method, entry point of the program
    public static void main(String[] args) {

        // Generate initial gold sources
        double goldSources[][] = generateGoldSources();

        // Run optimization algorithm for a fixed number of cycles
        for (int cycle = 0; cycle < MAX_CYCLE; cycle++) {
            employedPhase(goldSources); // Employed bee phase
            onlookerPhase(goldSources); // Onlooker bee phase
            scoutPhase(goldSources); // Scout bee phase
        }

        // Print all gold sources and their purities
        for (int i = 0; i < GOLD_SOURCES; i++) {
            System.out.println("Gold source " + i + ": x = " + goldSources[i][0] + ", y = " + goldSources[i][1] + ", purity = " + evaluate(goldSources[i]));
        }

        // Find the best purity among all gold sources and print it
        double bestPurity = bestGoldSource(goldSources);
        System.out.println("Best purity: " + bestPurity);
    }

    // Generate random initial gold sources within the specified range
    private static double[][] generateGoldSources() {
        double[][] goldSources = new double[GOLD_SOURCES][2];
        for (int i = 0; i < GOLD_SOURCES; i++) {
            goldSources[i][0] = MIN_GOLD + (MAX_GOLD - MIN_GOLD) * rand.nextDouble();
            goldSources[i][1] = MIN_GOLD + (MAX_GOLD - MIN_GOLD) * rand.nextDouble();
        }
        return goldSources;
    }

    // Employed bee phase of the optimization algorithm
    private static void employedPhase(double[][] goldSources) {
        for (int i = 0; i < GOLD_SOURCES; i++) {
            // Select a random neighbor
            int neighbour = rand.nextInt(GOLD_SOURCES);
            // Skip if the neighbor is the same as the current source
            if (neighbour == i) continue;

            // Do the local search by calculating a new gold position
            double newGold = goldSources[i][0] + rand.nextGaussian() * (goldSources[i][0] - goldSources[neighbour][0]);
            // Update the source if the new position is within the allowed range and has higher purity
            if (newGold >= MIN_GOLD && newGold <= MAX_GOLD) {
                double oldPurity = evaluate(goldSources[i]);
                double newPurity = evaluate(new double[]{newGold, goldSources[i][1]});
                if (newPurity > oldPurity) {
                    goldSources[i][0] = newGold;
                }
            }
        }
    }

    // Onlooker bee phase of the optimization algorithm
    private static void onlookerPhase(double[][] goldSources) {
        double totalPurity = 0;
        // Calculate the total purity of all gold sources
        for (int i = 0; i < GOLD_SOURCES; i++) {
            totalPurity += evaluate(goldSources[i]);
        }

        // Update the sources based on their probability proportional to their purity
        for (int i = 0; i < GOLD_SOURCES; i++) {
            double probability = evaluate(goldSources[i]) / totalPurity;
            if (rand.nextDouble() < probability) {
                int neighbour = rand.nextInt(GOLD_SOURCES);
                if (neighbour == i) continue;

                // Do the local search by calculating a new gold position
                double newGold = goldSources[i][0] + rand.nextGaussian() * (goldSources[i][0] - goldSources[neighbour][0]);
                // Update the source if the new position is within the allowed range and has higher purity
                if (newGold >= MIN_GOLD && newGold <= MAX_GOLD) {
                    double oldPurity = evaluate(goldSources[i]);
                    double newPurity = evaluate(new double[]{newGold, goldSources[i][1]});
                    if (newPurity > oldPurity) {
                        goldSources[i][0] = newGold;
                    }
                }
            }
        }
    }

    // Scout bee phase of the optimization algorithm
    private static void scoutPhase(double[][] goldSources) {
        // Check each source and randomly reinitialize it if its purity is below the threshold
        for (int i = 0; i < GOLD_SOURCES; i++) {
            if (evaluate(goldSources[i]) < PURITY) {
                goldSources[i][0] = MIN_GOLD + (MAX_GOLD - MIN_GOLD) * rand.nextDouble();
                goldSources[i][1] = MIN_GOLD + (MAX_GOLD - MIN_GOLD) * rand.nextDouble();
            }
        }
    }

    // Evaluate the gold source based on its purity
    private static double evaluate(double[] goldSources) {
        double x = goldSources[0];
        double y = goldSources[1];
        // Calculate the average of x and y coordinates
        double average = (x + y) / 2.0;
        // Normalize the average to represent a percentage of the maximum possible distance
        double purity = average / MAX_GOLD * 100.0;
        return purity;
    }

    // Find the gold source with the highest purity
    private static double bestGoldSource(double[][] goldSources) {
        double bestPurity = 0;
        // Iterate through all gold sources and find the one with the highest purity
        for (int i = 0; i < GOLD_SOURCES; i++) {
            double purity = evaluate(goldSources[i]);
            if (purity > bestPurity) {
                bestPurity = purity;
            }
        }
        return bestPurity;
    }
}
