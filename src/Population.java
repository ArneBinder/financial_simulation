import java.util.*;


/**
 * Created by binder on 29.07.14.
 */
public class Population {
    int populationSize = 1000000;
    int maxValue = 100;
    float shiftRate = 0.01f; //1%
    int shiftFrequency = populationSize;
    float threshold_maxDif = maxValue * 0.1f;


    float[] population = new float[populationSize];
    Random random = new Random();

    public static void main(String[] args){
        System.out.println("start...");

        Population population = new Population();
        population.initPopulation();
        population.doSimulation();
        //population.printSorted();
    }

    public void initPopulation(){
        System.out.println("PopulationSize: "+populationSize);
        Zipf zipf = new Zipf(maxValue, 0.5);
        for (int i = 0; i < populationSize; i++) {
            population[i] = zipf.nextInt();
        }
    }

    private float[] getSortedPopulation(){
        float[] temp = new float[populationSize];
        System.arraycopy(population,0,temp,0,populationSize);
        Arrays.sort(temp);
        return temp;
    }
    public void printSorted(){
        for(float individual: getSortedPopulation()){
            System.out.println(individual);
        }
    }

    public void doSimulation(){
        int maxIter = 1000000;
        float[] sorted = getSortedPopulation();
        System.out.println("MAXDIF(start): "+(sorted[populationSize-1] - sorted[0]));

        int i =0;
        for (; !nicePopulation() && i < maxIter; i++) {
            shiftAll();
            //for (int j = 0; j < shiftFrequency; j++) {
                //shift();
            //}
        }
        System.out.println("ITERATIONS: "+i);
        System.out.println("SHIFTS: "+i*shiftFrequency);


    }

    private boolean nicePopulation(){
        float[] sorted = getSortedPopulation();
        float maxDif = sorted[populationSize-1] - sorted[0];
        if(maxDif < threshold_maxDif){
            System.out.println("achieved a nice population :-)");
            System.out.println("MAXDIF(end): "+maxDif);
            return true;
        }
        return false;
    }

    public void shift(){
        int i = random.nextInt(populationSize);
        int j = random.nextInt(populationSize);
        float dif = population[i]*shiftRate;
        population[i] -= dif;
        population[j] += dif;
    }

    private void shiftAll(){
        float[] diffs = new float[populationSize];
        for (int i = 0; i < populationSize; i++) {
            diffs[random.nextInt(populationSize)] = population[i] * shiftRate;
            population[i] -= population[i] * shiftRate;
        }
        for (int i = 0; i < populationSize; i++) {
            population[i] += diffs[i];
        }
    }
}
