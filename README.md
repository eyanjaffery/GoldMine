GOLD MINE ALGORITHM BASED ON ABC ALGORITHM

Algorithm Phases:

1. Employed Bee Phase: Each employed bee explores a nearby gold source and tries to improve it. If the new position results in a higher purity, the bee updates the gold source.
2. Onlooker Bee Phase: Onlooker bees choose gold sources based on their purities and perform local searches to potentially improve them.
3. Scout Bee Phase: Scout bees check each gold source's purity. If it falls below a threshold, the source is randomly reinitialized.
   
Parameters:

GOLD_SOURCES: Number of gold sources (default: 15).
MAX_CYCLE: Maximum number of cycles (default: 50).
MIN_GOLD: Minimum value for gold coordinates (default: 0.0).
MAX_GOLD: Maximum value for gold coordinates (default: 100.0).
PURITY: Minimum purity threshold (default: 10).

Output:

The program outputs the initial and final purities of each gold source, as well as the best purity found.

