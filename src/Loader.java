import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Loader{
    List<Item> items;
    List<Node> nodes;
    int maxCapacity;
    double maxSpeed;
    double minSpeed;
    String filename;
    Scanner sc;
    public Loader(String filename){
        items=new ArrayList<>();
        nodes=new ArrayList<>();
        maxCapacity=0;
        maxSpeed=0;
        minSpeed=0;
        this.filename=filename;
    }
    public void load(){
        try{
            sc=new Scanner(new File(filename));
            sc.useLocale(Locale.UK);
            while (sc.hasNext()){
                String capacityLine=sc.findInLine("CAPACITY OF KNAPSACK");
                String maxSpeedLine=sc.findInLine("MAX SPEED");
                String minSpeedLine=sc.findInLine("MIN SPEED");
                String nodesLine=sc.findInLine("(INDEX, X, Y)");
                String itemsLine=sc.findInLine("ITEMS SECTION");
                if (capacityLine!=null){
                    maxCapacity=findNextInt();
                }
                if (minSpeedLine!=null){
                    minSpeed=findNextDouble();
                }
                if (maxSpeedLine!=null){
                    maxSpeed=findNextDouble();
                }
                if (nodesLine!=null){
                    findAllNodes();
                }
                if (itemsLine!=null){
                    findAllItems();
                }

                sc.nextLine();
            }
            addItemsToNode();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void findAllItems (){
        Item item;
        while (!sc.hasNextInt()){
            sc.next();
        }
        while (sc.hasNextInt()){
            int index=sc.nextInt();
            int profit=sc.nextInt();
            int weight=sc.nextInt();
            int assignedNodeNumber=sc.nextInt();
            item=new Item(index,weight,profit,assignedNodeNumber);
            items.add(item);
        }
    }
    private void findAllNodes (){
        Node node;
        while (!sc.hasNextInt()){
            sc.next();
        }
        while (sc.hasNextInt()){
            int index=sc.nextInt();
            double x=sc.nextDouble();
            double y=sc.nextDouble();
            node=new Node(index,x,y);
            nodes.add(node);
        }
    }

    public int findNextInt (){
        while (!sc.hasNextInt()){
            sc.next();
        }
        return sc.nextInt();
    }

    public double findNextDouble(){
        while (!sc.hasNextFloat()){
            sc.next();
        }
        return sc.nextDouble();
    }
    public void addItemsToNode(){
        for(int i = 0; i < items.size(); i++){
            for(int j = 0; j < nodes.size(); j++){
                if(items.get(i).assignedNodeNumber == nodes.get(j).index){
                    nodes.get(j).getAssignedItems().add(items.get(i));
                }
            }
        }
    }


    public List<Item> getItems (){
        return items;
    }

    public void setItems (List<Item> items){
        this.items = items;
    }

    public List<Node> getNodes (){
        return nodes;
    }

    public void setNodes (List<Node> nodes){
        this.nodes = nodes;
    }

    public int getMaxCapacity (){
        return maxCapacity;
    }

    public void setMaxCapacity (int maxCapacity){
        this.maxCapacity = maxCapacity;
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
}
