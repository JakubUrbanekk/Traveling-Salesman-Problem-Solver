import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Thief{
    List<Node> nodesList;
    double maxSpeed;
    double minSpeed;
    double currentSpeed;
    Knapsack knapsack;

    public Thief (){
        currentSpeed=1;
    }

    public Thief (double maxSpeed, double minSpeed, Knapsack knapsack){
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        this.knapsack = knapsack;
        this.currentSpeed=maxSpeed;
    }
    public Thief(Thief thief){
        this.maxSpeed=thief.maxSpeed;
        this.minSpeed=thief.minSpeed;
        this.currentSpeed=thief.currentSpeed;
        this.knapsack=thief.knapsack;
    }

    public double getMaxSpeed (){
        return maxSpeed;
    }

    public void setMaxSpeed (double maxSpeed){
        this.maxSpeed = maxSpeed;
    }

    public double getMinSpeed (){
        return minSpeed;
    }

    public void setMinSpeed (double minSpeed){
        this.minSpeed = minSpeed;
    }

    public List<Node> getNodesList (){
        return nodesList;
    }

    public void setNodesList (List<Node> nodesList){
        this.nodesList = nodesList;
    }

    public double getCurrentSpeed (){
        return currentSpeed;
    }

    public void setCurrentSpeed (double currentSpeed){
        this.currentSpeed = currentSpeed;
    }


    public Knapsack getKnapsack (){
        return knapsack;
    }

    public void setKnapsack (Knapsack knapsack){
        this.knapsack = knapsack;
    }

    public void createRoute(List<Node> nodes){
        List<Node>tempNodes=new ArrayList<>(nodes);
        Collections.shuffle(tempNodes);
        nodesList=tempNodes;
    }
    public double routeTravelTime (){
        double travelTime=0.0;
        Node fromCity;
        Node toCity;
        this.knapsack.clearKnapsack();
        currentSpeed=maxSpeed;
        int dimensions=nodesList.size();
        for (int i=0; i<dimensions; i++){
            fromCity=nodesList.get(i);
            if (i+1<dimensions){
                toCity=nodesList.get(i+1);
            }
            else {
                toCity=nodesList.get(0);
            }
            pickItem(fromCity);
            travelTime+=fromCity.distance(toCity)/currentSpeed;
        }
        return travelTime;
    }
    public boolean pickItem(Node city){
        int spaceLeft=knapsack.getMaxCapacity()-knapsack.getCurrentCapacity();
        ArrayList<Item> assignedItems=new ArrayList<Item>(city.getAssignedItems());
        Collections.sort(assignedItems);
        // System.out.println("Jestem zlodziejem " + this);
        // System.out.println("Jestem w miescie "+ city);
        // System.out.println("Lista przedmiotow "+ city.getAssignedItems());
        for (Item item: assignedItems){
           // System.out.println("Probuje podniesc "+ item);
            if(item.getWeight() < spaceLeft){
                // System.out.println("Podnoszony przedmiot "+ item);
                knapsack.addItem(item);
                this.updateCurrentSpeed();
                return true;
            }
        }
        return false;
    }
    public void updateCurrentSpeed(){
       // System.out.println("Predkosc przed podniesieniem " + currentSpeed);
        currentSpeed=maxSpeed-(knapsack.getCurrentCapacity()*((maxSpeed-minSpeed)/knapsack.getMaxCapacity()));
       // System.out.println("Predkosc po podniesieniu "+ currentSpeed);
    }
    public double fitnessFunction(){
        double routeTravelTime=this.routeTravelTime();
       // System.out.println("Travel time " + routeTravelTime);
        double totalValue=this.getKnapsack().getTotalValue();
       // System.out.println ("Knapsack value "+totalValue);
       // System.out.println();
        return totalValue-routeTravelTime;
    }
    public Thief crossover(Thief thief){
        Thief child=new Thief(this);
      // System.out.println("Pierwszy rodzic "+ this);
        //System.out.println("Drugi rodzic    "+ thief);
        int crossoverPoint = (int) (Math.random() * (nodesList.size() - 1)+1);
        List<Node>childNodes=adddAll(thief, crossoverPoint);
       // System.out.println("Punkt przeciecia " + crossoverPoint);

        int currentCityIndex=0;
        Node newNode;
        for (int i=0; i<nodesList.size(); i++){
            newNode=this.getNodesList().get(i);
            if (!childNodes.contains(newNode)){
                childNodes.set(currentCityIndex, newNode);
                currentCityIndex++;
            }
        }
        child.setNodesList(childNodes);
     //  System.out.println("Dziecko         "+ child);
        return child;
    }
    public ArrayList<Node> adddAll(Thief thief, int crossoverPoint){
        int nodeSize = nodesList.size();
        ArrayList<Node> childNodes=initNodeList(nodeSize);
        for(int i = crossoverPoint; i < nodeSize; i++){
            childNodes.set(i, thief.getNodesList().get(i));
        }
        return childNodes;
    }

    public void mutate(){
        int swapped=(int)(Math.random()*nodesList.size());
        int swapWith=(int)(Math.random()*nodesList.size());
        while (swapWith==swapped){
            swapWith=(int)(Math.random()*nodesList.size());
        }
        if (swapped>swapWith){
            int temp=swapWith;
            swapWith=swapped;
            swapped=temp;
        }
        ArrayList<Node> inversion=new ArrayList<>();
        for (int i=swapped; i<swapWith; i++){
            inversion.add(getNodesList().get(i));
        }
        Collections.reverse(inversion);
        int j=0;
        for (int i=swapped; i<swapWith; i++){
            this.getNodesList().set(i,inversion.get(j));
            j++;
        }
        }

    public ArrayList<Node> initNodeList(int size){
        ArrayList<Node> emptyList=new ArrayList<>();
        for (int i=0; i<size; i++){
            emptyList.add(new Node());
        }
        return emptyList;
    }
    @Override
    public String toString (){
        return " "  + nodesList + "\n" ;
    }
}
