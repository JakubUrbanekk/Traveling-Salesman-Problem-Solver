import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Reader{
    ArrayList <Integer> populationNumbers;
    ArrayList <Double> bestResults;
    ArrayList <Double> worstResults;
    ArrayList <Double> averageResults;
    ArrayList <Double> averageBestResults;
    ArrayList <Double> averageWorstResults;
    ArrayList <Double> averageAverageResults;
    int generationsAmount, size, tournamentSize;
    double crossoverProbability, mutationRate;
    String filename;

    Thief bestThief;

    public Reader (){
        this.populationNumbers=new ArrayList<>();
        this.bestResults=new ArrayList<>();
        this.worstResults=new ArrayList<>();
        this.averageResults=new ArrayList<>();
        this.bestThief=new Thief();
    }
    public Reader(GenericAlgorithm ga){
        this.populationNumbers=new ArrayList<>();
        this.bestResults=new ArrayList<>();
        this.worstResults=new ArrayList<>();
        this.averageResults=new ArrayList<>();
        this.bestThief=new Thief();
        this.generationsAmount=ga.generationsAmount;
        this.size=ga.population.size;
        this.tournamentSize=ga.population.tournamentSize;
        this.crossoverProbability=ga.population.crossoverProbability;
        this.mutationRate=ga.population.mutationRate;
        this.filename=ga.population.filename;
        this.averageAverageResults=new ArrayList<>();
        this.averageBestResults=new ArrayList<>();
        this.averageWorstResults=new ArrayList<>();

    }
    public void read(Population population,int generationNumber){
        ArrayList<Double> fitness=population.calculateFitness();
        double bestResult=-214748364;
        double worstResult=0;
        double averageResult=0;
        double result=0;

        for (int i=0; i<fitness.size(); i++){
            result=fitness.get(i);
            if (result>bestResult){
                bestResult=result;
                bestThief=population.population.get(i);
            }
            if (result<worstResult){
                worstResult=result;
            }
            averageResult+=result;
        }
        averageResult=averageResult/fitness.size();
        bestResults.add(bestResult);
        worstResults.add(worstResult);
        averageResults.add(averageResult);

    }
    public void readAverage(){
        averageWorstResults=initList();
        averageBestResults=initList();
        averageAverageResults=initList();
        for(int j = 0; j <generationsAmount; j++){
            for(int i = j; i < bestResults.size(); i +=generationsAmount){
                averageBestResults.set(j,bestResults.get(i)+averageBestResults.get(j));
                averageAverageResults.set(j,averageResults.get(i)+averageAverageResults.get(j));
                averageWorstResults.set(j,worstResults.get(i)+averageWorstResults.get(j));
            }
        }
        for (int i=0; i<generationsAmount; i++){
            averageBestResults.set(i,averageBestResults.get(i)/(bestResults.size()/generationsAmount));
            averageWorstResults.set(i,averageWorstResults.get(i)/(bestResults.size()/generationsAmount));
            averageAverageResults.set(i,averageAverageResults.get(i)/(bestResults.size()/generationsAmount));
        }
    }
    public ArrayList<Double> initList(){
        ArrayList<Double> list=new ArrayList<>();
        for (int i=0; i<generationsAmount; i++){
            list.add(0.0);
        }
        return list;
    }

    public double printTheBestResult(int k){
        double theBestResult=-22222222;
        double result=0;
        int theBestResultIndex=0;
        for (int i=0; i<bestResults.size(); i++){
            result = bestResults.get(i);
            if (result>theBestResult){
                theBestResult=result;
                theBestResultIndex=i;
            }
        }
        String message="Najlepszy wynik symulacji "+ "Pokolenie "+ theBestResultIndex + " Wartosc "+ theBestResult;
        System.out.println(message);
        return theBestResult;

    }
    public double printTheBestResult(){
        double theBestResult=-22222222;
        double result=0;
        int theBestResultIndex=0;
        for (int i=0; i<bestResults.size(); i++){
            result = bestResults.get(i);
            if (result>theBestResult){
                theBestResult=result;
                theBestResultIndex=i;
            }
        }
        return theBestResult;

    }
    public double printTheBestAverageResult(){
        double theBestResult=-22222222;
        double result=0;
        for (int i=0; i<averageBestResults.size(); i++){
            result = averageBestResults.get(i);
            if (result>theBestResult){
                theBestResult=result;
            }
        }
        return theBestResult;

    }
    public void printValue(){

        String title="Liczba generacji "+ generationsAmount + "| Wielkość populacji " + size + "| Turniej " + tournamentSize+  "| Prawdopodobienstwo krzyzowania " + crossoverProbability + " | Prawdopodobienstwo mutacji "+ mutationRate + " | Nazwa pliku " + filename;
        XYChart chart = new XYChartBuilder().width(1300).height(500).title(title).xAxisTitle("Populacja").yAxisTitle("Wartość fitness").build();
        readAverage();
        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        chart.getStyler().setYAxisDecimalPattern("#,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        String filenameNumber=filename.substring(8,14);
        for (int i=1; i<generationsAmount+1; i++){
            populationNumbers.add(i);
        }
        chart.addSeries("Najlepszy wynik w historii" + printTheBestResult(), populationNumbers, averageWorstResults).setMarkerColor(Color.white).setLineColor(Color.white).setFillColor(Color.white);
        chart.addSeries("Najlepszy średni wynik" + printTheBestAverageResult(), populationNumbers, averageWorstResults).setMarkerColor(Color.white).setLineColor(Color.white).setFillColor(Color.white);
        chart.addSeries("Najlepszy osobnik", populationNumbers, averageBestResults);
        chart.addSeries("Średnia populacji", populationNumbers, averageAverageResults);
        chart.addSeries("Najgorszy osobnik", populationNumbers, averageWorstResults);

        String text="Turniej skrajnosc tournamentSize = 1 " +   " " + filenameNumber;
      /*  try(FileWriter fw = new FileWriter("./"+text+".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(text+ " " +printTheBestResult());
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        } */
        try{
            BitmapEncoder.saveBitmap(chart, "./"+text, BitmapEncoder.BitmapFormat.PNG);
        } catch(IOException e){
            e.printStackTrace();
        }
        new SwingWrapper(chart).displayChart();
    }
    public void printValue(double elapsedTime){

        String title="Liczba generacji "+ generationsAmount + "| Wielkość populacji " + size + "| Turniej " + tournamentSize+  "| Prawdopodobienstwo krzyzowania " + crossoverProbability + " | Prawdopodobienstwo mutacji "+ mutationRate + " | Nazwa pliku " + filename;
        XYChart chart = new XYChartBuilder().width(1300).height(500).title(title).xAxisTitle("Populacja").yAxisTitle("Wartość fitness").build();
        readAverage();
        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        chart.getStyler().setYAxisDecimalPattern("#,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        String filenameNumber=filename.substring(8,14);
        for (int i=1; i<generationsAmount+1; i++){
            populationNumbers.add(i);
        }
        chart.addSeries("Czas wykonania" + elapsedTime + "s", populationNumbers, averageWorstResults).setMarkerColor(Color.white).setLineColor(Color.white).setFillColor(Color.white);
        chart.addSeries("Najlepszy wynik w historii" + printTheBestResult(), populationNumbers, averageWorstResults).setMarkerColor(Color.white).setLineColor(Color.white).setFillColor(Color.white);
        chart.addSeries("Najlepszy średni wynik" + printTheBestAverageResult(), populationNumbers, averageWorstResults).setMarkerColor(Color.white).setLineColor(Color.white).setFillColor(Color.white);
        chart.addSeries("Najlepszy osobnik", populationNumbers, averageBestResults);
        chart.addSeries("Średnia populacji", populationNumbers, averageAverageResults);
        chart.addSeries("Najgorszy osobnik", populationNumbers, averageWorstResults);

        String text="Liczba generacji " + generationsAmount+   " " + filenameNumber;
      /*  try(FileWriter fw = new FileWriter("./"+text+".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(text+ " " +printTheBestResult());
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        } */
        try{
            BitmapEncoder.saveBitmap(chart, "./"+text, BitmapEncoder.BitmapFormat.PNG);
        } catch(IOException e){
            e.printStackTrace();
        }
        new SwingWrapper(chart).displayChart();
    }
    public void printBestValues(){

        ArrayList<Integer> xData=new ArrayList<>();
        for (int i=0; i<populationNumbers.size(); i++){
            xData.add(i);
        }
        ArrayList<Double> yData=new ArrayList<>();
        for (int i=0; i<populationNumbers.size(); i++){
            yData.add(bestResults.get(i));
        }
        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

        // Show it
        new SwingWrapper(chart).displayChart();
    }

    public void write(){
        String file="log.csv";
        try{
            FileWriter out = new FileWriter(file);
            ArrayList<String> headersList=createHeaders();
            String[] headers=new String[headersList.size()];
            headers=headersList.toArray(headers);
            CSVFormat fmt = CSVFormat.EXCEL.withDelimiter(';');
            int bestResult;
            int worstResult;
            int averageResult;
            try(CSVPrinter pw = new CSVPrinter(out, fmt.withHeader(headers))){
                for (int i=0; i<populationNumbers.size(); i++){
                    bestResult=bestResults.get(i).intValue();
                    worstResult=worstResults.get(i).intValue();
                    averageResult=averageResults.get(i).intValue();
                    pw.printRecord(i, bestResult,worstResult,averageResult);
                }
            }

            } catch(IOException e){
                e.printStackTrace();
            }
    }
    public ArrayList<String> createHeaders(){
        ArrayList<String> headers=new ArrayList<>();
        headers.add("Populacja");
        headers.add("Najlepszy wynik");
        headers.add("Najgorszy wynik");
        headers.add("Sredni wynik");
        return headers;
    }
}
