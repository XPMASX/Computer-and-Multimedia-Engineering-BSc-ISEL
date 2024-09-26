package ecosystem;

import processing.core.PApplet;

public interface IAnimal {
    public Animal reproduce(boolean mutate, int mode);
    public void eat(Terrain terrain);
    public void energy_consumption(float dt, Terrain terrain, int mode);
    public boolean die();

}
