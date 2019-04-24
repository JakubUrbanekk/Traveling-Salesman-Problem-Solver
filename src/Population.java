import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population{
    ArrayList<Thief> population;
    int tournamentSize;
    String filename;
    double crossoverProbability;
    double mutationRate;
    int size;

    public Population (int size,int tournamentSize, double crossoverProbability, double mutationRate, String filename){
        this.population = new ArrayList<>();
        this.filename=filename;
        this.tournamentSize=tournamentSize;
        this.crossoverProbability=crossoverProbability;
        this.mutationRate=mutationRate;
        this.size=size;
    }

    public void initPopulation(){
        population.clear();
        Loader loader=new Loader(filename);
        loader.load();
        double maxSpeed=loader.getMaxSpeed();
        double minSpeed=loader.getMinSpeed();
        List<Node> nodes=loader.getNodes();
        int maxCapacity=loader.getMaxCapacity();

        for(int i = 0; i < size; i++){
            Thief thief=new Thief(maxSpeed,minSpeed,new Knapsack(maxCapacity));
            thief.createRoute(nodes);
            population.add(thief);
        }
    }
    public ArrayList<Double> calculateFitness(){
        ArrayList<Double> fitnessResults=new ArrayList<>();
        Thief individual;
        double fitnessResult;
            for (int i=0; i<population.size(); i++){
                individual=population.get(i);
                fitnessResult=individual.fitnessFunction();
                fitnessResults.add(fitnessResult);
            }
        return fitnessResults;
    }
    public void selection(){
        ArrayList<Double> fitnessResults=calculateFitness();
        ArrayList<Thief> selectionResults=new ArrayList<>();
        Thief participant;
        double participantResult;
        Thief rival;
        double rivalResult;
        int randomNumber;
        int secondRandomNumber;

        while(selectionResults.size()!=size){
            randomNumber=(int)(Math.random()*size);
            participant=population.get(randomNumber);
            participantResult=fitnessResults.get(randomNumber);
            for (int j=1; j<tournamentSize; j++){
                secondRandomNumber=(int)(Math.random()*size);
                System.out.println(" First numbre "+randomNumber+ " second number " + secondRandomNumber);
                rival=population.get(secondRandomNumber);
                if (secondRandomNumber==randomNumber){
                    j--;
                }
                else{
                    rivalResult = fitnessResults.get(secondRandomNumber);
                    if(rivalResult > participantResult){
                        participant = rival;
                        randomNumber=secondRandomNumber;
                    }
                }
            }
            selectionResults.add(participant);
        }
        population=selectionResults;
    }
    public boolean crossover(){
        Thief parentOne;
        Thief parentTwo;
        Thief child1;
        Thief child2;
        int parentTwoIndex;
        double randomNumber = 0;
        for(int i = 0; i < population.size(); i++){
            if (i+1<population.size()){
                randomNumber = Math.random();
                if(randomNumber <= crossoverProbability){
                    parentTwoIndex = i + 1;
                    parentOne = population.get(i);
                    parentTwo = population.get(parentTwoIndex);
                    child1 = parentOne.crossover(parentTwo);
                    child2 = parentTwo.crossover(parentOne);
                    population.set(i, child1);
                    population.set(parentTwoIndex, child2);
                    i++;
                }
                else{
                    i++;
                }
            }
        }
        return false;
    }
    public void mutate(){
            double randomNumber;
            for (int i=0; i<population.size(); i++){
                randomNumber = Math.random();
                if(randomNumber <= mutationRate){
                    population.get(i).mutate();
                }
            }
    }
    public void rouleteSelection(){
        ArrayList<Double> fitnessResults=calculateFitness();
        ArrayList<Thief> selectionResults=new ArrayList<>();
        ArrayList<Double> culmulativeFitness=new ArrayList<>();
        ArrayList<Double>ruleteProcent=new ArrayList<>();
        double worstFitness=0;
        for (int i=0; i<fitnessResults.size(); i++){
            if (fitnessResults.get(i)<worstFitness){
                worstFitness=fitnessResults.get(i);
            }
        }
        for (int i=0; i<fitnessResults.size(); i++){
            double postivieFitnessResult=fitnessResults.get(i)-worstFitness;
            culmulativeFitness.add(postivieFitnessResult);
        }
        double totalFintess=0;
        for (int i=0; i<fitnessResults.size(); i++){
            totalFintess+=culmulativeFitness.get(i);
        }
        ruleteProcent.add((culmulativeFitness.get(0)/totalFintess)*100);
        for (int i=1; i<fitnessResults.size(); i++){
            double fitnessPercent=((culmulativeFitness.get(i)/totalFintess)*100);
            ruleteProcent.add(fitnessPercent+ruleteProcent.get(i-1));
        }

        double randomFitness;
        for (int i=0; i<fitnessResults.size(); i++){
            randomFitness=Math.random()*100;
            int index=Collections.binarySearch(ruleteProcent, randomFitness);
            if (index < 0)
            {
                index = Math.abs(index)-1;
            }
            selectionResults.add(population.get(index));
        }
        population=selectionResults;
    }

    @Override
    public String toString (){
        return "Population{" + "tournamentSize=" + tournamentSize  + ", crossoverProbability=" + crossoverProbability + ", mutationRate=" + mutationRate + ", size=" + size  + ", filename='" + filename + ']';
    }
}