package pos.machine;

public class ReceiptItem {
    private String item;
    private int price;

    private int numberOfItems;
    public ReceiptItem(String item, int price, int numberOfItems) {
        this.item = item;
        this.price = price;
        this.numberOfItems = numberOfItems;
    }


    public String getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }
}
