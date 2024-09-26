package ecosystem;

public class WorldConstants {

    //World
    public final static double[] WINDOW = {-10, 10, -10, 10};

    //Terrain
    public final static int NROWS = 45;
    public final static int NCOLS = 60;
    public enum PatchType{
        EMPTY, OBSTACLE, FERTILE, FOOD
    }
    public final static double[] PATCH_TYPE_PROB1 = {0.6f,0f,.0f,.4f};
    public final static double[] PATCH_TYPE_PROB2 = {0.57f,0f,.0f,.43f};
    public final static double[] PATCH_TYPE_PROB3 = {0.55f,0f,.0f,.45f};
    public final static int NSTATES = PatchType.values().length;
    public static int[][] TERRAIN_COLORS = {
            {200+50, 200, 60}, {160, 30, 70}, {200, 200, 60}, {40, 200, 20},
    };
    public final static float[] REGENARATION_TIME = {5.f, 10.f};//seconds

    //Prey population
    public final static float PREY_SIZE = .2f;
    public final static float PREY_MASS = 1f;
    public final static int INI_PREY_POPULATION = 10;
    public final static float INI_PREY_ENERGY = 10f;
    public final static float ENERGY_FROM_PLANT = 4f;
    public final static float PREY_ENERGY_TO_REPRODUCE = 25f;
    public final static int[] PREY_COLOR = {80, 100, 220};
    public final static float PREDATOR_SIZE = .4f;
    public final static float PREDATOR_MASS = 1f;
    public final static int INI_PREDATOR_POPULATION = 3;
    public final static float INI_PREDATOR_ENERGY = 25;
    public final static float ENERGY_FROM_KILL = 10f;
    public final static float PREDATOR_ENERGY_TO_REPRODUCE = 75;
    public final static int[] PREDATOR_COLOR = {255, 0, 0};

    public final static float Vegan_SIZE = .25f;
    public final static float Vegan_MASS = 0.5f;
    public final static int INI_Vegan_POPULATION = 8;
    public final static float INI_Vegan_ENERGY = 10;
    public final static float Vegan_ENERGY_TO_REPRODUCE = 55;
    public final static int[] Vegan_COLOR = {255, 255, 0};
}
