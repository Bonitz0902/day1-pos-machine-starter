package pos.machine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {

        List<ReceiptItem> receiptItemList = decodeToItems(barcodes);
        List<Integer> calculatedReceiptItem = calculateCostPerItem(receiptItemList);
        int totalCostOfItems = calculateTotalCost(calculatedReceiptItem);


        return renderReceipt(receiptItemList, calculatedReceiptItem, totalCostOfItems);
    }

    private String renderReceipt(List<ReceiptItem> receiptItemList, List<Integer> calculatedReceiptItem,
                                 int totalCostOfItems) {
        StringBuilder itemReceipt = new StringBuilder("***<store earning no money>Receipt***\n");
        for(int i = 0; i<receiptItemList.size(); i++){
             itemReceipt.append(generateItemsReceipt(receiptItemList.get(i), calculatedReceiptItem.get(i))).append("\n");
        }
        itemReceipt.append( String.format("----------------------\n" +
                "Total: %d (yuan)\n" +
                "**********************", totalCostOfItems));
        return itemReceipt.toString();
    }

    private String generateItemsReceipt(ReceiptItem receiptItem, int calculatedReceiptItem) {
        //"Name: Coca-Cola, Quantity: 4, Unit price: 3 (yuan), Subtotal: 12 (yuan)
        StringBuilder itemReceipt = new StringBuilder();

        StringBuilder append = itemReceipt.append(String.format("Name: %s, Quantity: %d, Unit price: %d (yuan) Subtotal: %d (yuan)"
                , receiptItem.getItem(), receiptItem.getNumberOfItems(), receiptItem.getPrice(), calculatedReceiptItem));
        return append.toString();
    }

    public List<Integer> calculateCostPerItem(List<ReceiptItem> receiptItemList) {

        return receiptItemList.stream()
                .map(receiptItem -> calculateItemCost(receiptItem))
                .collect(Collectors.toList());


    }

    public Integer calculateTotalCost(List<Integer> calculatedCostPerItem) {
        return calculateTotalPrice(calculatedCostPerItem);
    }


    private int calculateTotalPrice(List<Integer> listOfTotalPriceEachItem) {
        return listOfTotalPriceEachItem.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static int calculateItemCost(ReceiptItem receiptItem) {
        return receiptItem.getPrice() * receiptItem.getNumberOfItems();
    }

    private List<ReceiptItem> decodeToItems(List<String> barcodes) {
        List<ReceiptItem> receiptItemList = new ArrayList<>();
        List<Item> items = ItemsLoader.loadAllItems();

        Map<String, Long> barcodeOccurrence = barcodes.stream()
                .collect(Collectors.toMap(
                        barcode -> barcode,
                        barcode -> 1L, // Initial count is 1 for each barcode
                        (existingCount, newCount) -> existingCount + newCount,
                        LinkedHashMap::new// Merge function
                ));


        Map<String, List<Item>> barcodeToItems = items.stream()
                .filter(item -> barcodeOccurrence.containsKey(item.getBarcode()))
                .collect(Collectors.groupingBy(Item::getBarcode));

        for (Map.Entry<String, List<Item>> entry : barcodeToItems.entrySet()) {
            String barcode = entry.getKey();
            long count = barcodeOccurrence.get(barcode);
            List<Item> itemsList = entry.getValue();
            receiptItemList.add(new ReceiptItem(itemsList.get(0).getName(), itemsList.get(0).getPrice(), (int) count));
        }

        return receiptItemList;
    }

}
