package pos.machine;

public class Item {
    private final String barcode;
    private final String name;
    private final int price;
    private int scanCount;


    public Item(String barcode, String name, int price, int scanCount) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.scanCount = 0;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getScanCount() {
        return scanCount;
    }

    public void incrementScanCount(){
        scanCount++;
    }
}
