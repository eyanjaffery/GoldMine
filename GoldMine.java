import java.util.Random;
public class GoldMine {

    private static final int GOLD_SOURCES = 15;
    private static final int MAX_CYCLE = 50;
    private static final double MIN_GOLD = 0.0;
    private static final double MAX_GOLD = 100.0;
    private static final double PURITY = 1;

    private static Random rand = new Random();

    public static void main(String[] args) {
        double goldSources[][] = generateGoldSources();
        for (int cycle = 0; cycle < MAX_CYCLE; cycle++) {
            employedPhase(goldSources);
            onlookerPhase(goldSources);
            scoutPhase(goldSources);
        }

        double bestPurity = bestGoldSource(goldSources);
        System.out.println("Best purity: " + bestPurity);
    }

    private static double[][] generateGoldSources() {
        double[][] goldSources = new double[GOLD_SOURCES][2];
        for (int i = 0; i < GOLD_SOURCES; i++) {
            goldSources[i][0] = MIN_GOLD + (MAX_GOLD - MIN_GOLD) * rand.nextDouble();
            goldSources[i][1] = MIN_GOLD + (MAX_GOLD - MIN_GOLD) * rand.nextDouble();
        }
        return goldSources;
    }

    private static void employedPhase(double[][] goldSources) {
        for (int i = 0; i < GOLD_SOURCES; i++) {

            int neighbour = rand.nextInt(GOLD_SOURCES);
            if (neighbour == i) continue;

            //Do the local search
            double newGold = goldSources[i][0] + rand.nextGaussian() * (goldSources[i][0] - goldSources[neighbour][0]);
            if ( newGold >= MIN_GOLD && newGold <= MAX_GOLD) {
                double oldPurity = evaluate(goldSources[i]);
                double newPurity = evaluate(new double[]{newGold, goldSources[i][1]});
                if (newPurity > oldPurity) {
                    goldSources[i][0] = newGold;
                }
            }
        }
    }

    private static void onlookerPhase(double[][] goldSources) {
        double totalPurity = 0;
        for (int i = 0; i < GOLD_SOURCES; i++) {
            totalPurity += evaluate(goldSources[i]);
        }

        for (int i = 0; i < GOLD_SOURCES; i++) {
            double probability = evaluate(goldSources[i]) / totalPurity;
            if (rand.nextDouble() < probability) {
                int neighbour = rand.nextInt(GOLD_SOURCES);
                if (neighbour == i) continue;

                //Do the local search
                double newGold = goldSources[i][0] + rand.nextGaussian() * (goldSources[i][0] - goldSources[neighbour][0]);
                if ( newGold >= MIN_GOLD && newGold <= MAX_GOLD) {
                    double oldPurity = evaluate(goldSources[i]);
                    double newPurity = evaluate(new double[]{newGold, goldSources[i][1]});
                    if (newPurity > oldPurity) {
                        goldSources[i][0] = newGold;
                    }
                }
            }
        }
    }

    private static void scoutPhase(double[][] goldSources) {
        for (int i = 0; i < GOLD_SOURCES; i++) {
            if (evaluate(goldSources[i]) > PURITY) {
                goldSources[i][0] = MIN_GOLD + (MAX_GOLD - MIN_GOLD) * rand.nextDouble();
                goldSources[i][1] = MIN_GOLD + (MAX_GOLD - MIN_GOLD) * rand.nextDouble();
            }
        }
    }

    //Evaluate the gold source based on the purity
    private static double evaluate(double[] goldSources) {
        double x = goldSources[0];
        double y = goldSources[1];
        //Check the purity of the gold
        double purity = Math.sqrt(x * x + y * y);
        return purity;
    }

    private static double bestGoldSource(double[][] goldSources) {
        double bestPurity = 0;
        for (int i = 0; i < GOLD_SOURCES; i++) {
            double purity = evaluate(goldSources[i]);
            if (purity > bestPurity) {
                bestPurity = purity;
            }
        }
        return bestPurity;
    }




}
