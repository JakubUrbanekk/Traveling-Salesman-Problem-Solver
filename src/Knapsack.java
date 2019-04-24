public class Knapsack{
    int currentCapacity;
    int maxCapacity;
    int totalValue;

    public Knapsack (){
    }

    public Knapsack (int maxCapacity){
        this.maxCapacity = maxCapacity;
        this.currentCapacity=0;
        this.totalValue=0;
    }
    public void addItem(Item item){
        currentCapacity+=item.getWeight();
        totalValue+=item.getValue();
    }
    public void clearKnapsack(){
        currentCapacity=0;
        totalValue=0;
    }

    public int getMaxCapacity (){
        return maxCapacity;
    }

    public void setMaxCapacity (int maxCapacity){
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCapacity (){
        return currentCapacity;
    }

    public void setCurrentCapacity (int currentCapacity){
        this.currentCapacity = currentCapacity;
    }

    public int getTotalValue (){
        return totalValue;
    }

    public void setTotalValue (int totalValue){
        this.totalValue = totalValue;
    }

    @Override
    public String toString (){
        return "Knapsack{" + "currentCapacity=" + currentCapacity + ", maxCapacity=" + maxCapacity + ", totalValue=" + totalValue + '}';
    }
}
