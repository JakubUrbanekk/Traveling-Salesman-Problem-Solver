import java.util.ArrayList;

public class App{
    public static void main (String[] args){
            // for (double crossoverProbability=0.8; crossoverProbability<1.1; crossoverProbability+=0.1 ){
           ArrayList <Double> mutationRate=new ArrayList<>();
           mutationRate.add(0.01);
           mutationRate.add(0.05);
           mutationRate.add(0.1);
           mutationRate.add(0.15);
           mutationRate.add(0.20);
      //     for(Double mutation:mutationRate){
       //     for(int i = 0; i < 5; i++){
                 String filename = "student/hard_" + 0 + ".ttp";
                 GenericAlgorithm genericAlgorithm = new GenericAlgorithm(1000, 500, 15, 0.8, 0.5, filename);
                 Reader reader = genericAlgorithm.reader;
                 long startTime=System.nanoTime();
           //      for(int j = 0; j < 10; j++){
                     System.out.println(genericAlgorithm);
                     genericAlgorithm.run();
             //    }
                 reader.printTheBestResult();
                 long endTime=System.nanoTime();
                 long elapsedTime=endTime-startTime;
                 double seconds = (double)elapsedTime / 1_000_000_000.0;
                 System.out.println("Czas wykonania " + seconds);
                 reader.printValue();
                  }
           //  }
   //     }
    }

