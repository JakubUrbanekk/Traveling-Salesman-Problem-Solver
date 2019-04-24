public class GenericAlgorithm{
    Population population;
    int generationsAmount;
    Reader reader;

    public GenericAlgorithm (int generationsAmount, int size, int tournamentSize, double crossoverProbability, double mutationRate, String filename){
        population = new Population(size, tournamentSize, crossoverProbability, mutationRate, filename);
        this.generationsAmount=generationsAmount;
        this.reader=new Reader(this);
    }

    public void run(){
        population.initPopulation();

        for (int i=0; i<generationsAmount; i++){
            reader.read(population,i);
            population.selection();
            population.crossover();
            population.mutate();
        }
    }


    @Override
    public String toString (){
        return "GenericAlgorithm{" + "population=" + population + ", generationsAmount=" + generationsAmount;
    }
}

