import java.util.ArrayList;

public class Node{
    int index;
    double x;
    double y;
    ArrayList<Item> assignedItems;

    public Node (int index, double x, double y){
        this.index = index;
        this.x = x;
        this.y = y;
        this.assignedItems=new ArrayList<Item>();
    }

    public Node (int index, double x, double y, ArrayList<Item> assignedItems){
        this.index = index;
        this.x = x;
        this.y = y;
        this.assignedItems = assignedItems;
    }

    public ArrayList<Item> getAssignedItems (){
        return assignedItems;
    }

    public void setAssignedItems (ArrayList<Item> assignedItems){
        this.assignedItems = assignedItems;
    }

    public Node(Node node){
        this.index=node.index;
        this.x=node.x;
        this.y=node.y;
        this.assignedItems=node.assignedItems;
    }

    public Node (){
        this.index=0;
        this.x=0;
        this.y=0;
    }

    public int getIndex (){
        return index;
    }

    public void setIndex (int index){
        this.index = index;
    }

    public double getX (){
        return x;
    }

    public void setX (double x){
        this.x = x;
    }

    public double getY (){
        return y;
    }

    public void setY (double y){
        this.y = y;
    }

    @Override
    public String toString (){
        return " " + index;
    }

    public int distance(Node node){
        double xDis=Math.abs(this.getX()-node.getX());
        double yDis=Math.abs(this.getY()-node.getY());
        double distance=Math.sqrt(Math.pow(xDis,2)+ Math.pow(yDis,2));
        return (int)distance+1;
    }
}
