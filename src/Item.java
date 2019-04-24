public class Item implements Comparable<Item>{
    int index;
    int weight;
    int value;
    int assignedNodeNumber;
    double itemFactor;

    public Item (){
    }

    public Item (int index, int weight, int value, int assignedNodeNumber){
        this.index = index;
        this.weight = weight;
        this.value = value;
        this.assignedNodeNumber = assignedNodeNumber;
        this.itemFactor=(double)value/weight;
    }

    public double getItemFactor (){
        return itemFactor;
    }

    public void setItemFactor (double itemFactor){
        this.itemFactor = itemFactor;
    }


    public int getIndex (){
        return index;
    }

    public void setIndex (int index){
        this.index = index;
    }

    public int getWeight (){
        return weight;
    }

    public void setWeight (int weight){
        this.weight = weight;
    }

    public int getValue (){
        return value;
    }

    public void setValue (int value){
        this.value = value;
    }

    public int getAssignedNodeNumber (){
        return assignedNodeNumber;
    }

    public void setAssignedNodeNumber (int assignedNodeNumber){
        this.assignedNodeNumber = assignedNodeNumber;
    }

    @Override
    public String toString (){
        return "weight " + weight + "value " + value + "assignedNodeNumber " + assignedNodeNumber + "itemFactor=" + itemFactor + "\n";
    }

    @Override
    public int compareTo (Item o){
        double factorDiffrence=this.itemFactor-o.itemFactor;
        if (factorDiffrence>0)
            return 1;
        else if (factorDiffrence==0)
            return 0;
        else
            return -1;
    }
}
