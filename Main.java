import java.util.*;
import java.io.*;

class Item {
    int id, quantity;
    String name;
    double price;

    Item(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    void display() {
        System.out.println(id + " | " + name + " | "
                + quantity + " | Rs." + price);
    }
}

class Inventory {
    ArrayList<Item> items = new ArrayList<>();
    int nextId = 101;

    // 1. Insert an item
    void addItem(String name, int qty, double price) {
        items.add(new Item(nextId, name, qty, price));
        System.out.println("Item added! ID: " + nextId);
        nextId++;
    }

    // 2. Traverse and display items
    void display() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        for (Item i : items)
            i.display();
    }

    // 3. Linear Search
    Item search(int id) {
        for (Item i : items) {
            if (i.id == id)
                return i;
        }
        return null;
    }

    // 4. Sell an item
    void sell(int id, int qty) {
        Item i = search(id);

        if (i == null)
            System.out.println("Item not found.");
        else if (qty <= 0)
            System.out.println("Invalid quantity.");
        else if (qty > i.quantity)
            System.out.println("Not enough stock.");
        else {
            i.quantity -= qty;
            System.out.println("Item sold successfully!");
        }
    }

    // 5. Update stock
    void update(int id, int qty) {
        Item i = search(id);

        if (i == null)
            System.out.println("Item not found.");
        else if (qty < 0)
            System.out.println("Invalid quantity.");
        else {
            i.quantity = qty;
            System.out.println("Stock updated!");
        }
    }

    // 6. Low stock report
    void lowStock() {
        boolean found = false;

        for (Item i : items) {
            if (i.quantity < 5) {
                i.display();
                found = true;
            }
        }

        if (!found)
            System.out.println("No low-stock items.");
    }

    // 7. Bubble Sort
    void sort() {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = 0; j < items.size() - i - 1; j++) {
                if (items.get(j).quantity > items.get(j + 1).quantity) {
                    Item temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                }
            }
        }

        System.out.println("Items sorted by quantity:");
        display();
    }

    // 8. Save data in file
    void save() {
        try {
            PrintWriter p = new PrintWriter("inventory.txt");

            for (Item i : items)
                p.println(i.id + "|" + i.name + "|"
                        + i.quantity + "|" + i.price);

            p.close();
            System.out.println("Data saved!");
        } catch (Exception e) {
            System.out.println("Saving error!");
        }
    }

    // 9. Load data from file
    void load() {
        try {
            File f = new File("inventory.txt");
            if (!f.exists()) return;

            Scanner file = new Scanner(f);

            while (file.hasNextLine()) {
                String[] d = file.nextLine().split("\\|", -1);

                if (d.length == 4) {
                    int id = Integer.parseInt(d[0]);
                    int qty = Integer.parseInt(d[2]);
                    double price = Double.parseDouble(d[3]);

                    if (!d[1].trim().isEmpty() && qty >= 0 && price > 0) {
                        items.add(new Item(id, d[1], qty, price));
                        nextId = Math.max(nextId, id + 1);
                    }
                }
            }

            file.close();
        } catch (Exception e) {
            System.out.println("Loading error!");
        }
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Inventory inv = new Inventory();
        inv.load();

        int choice = 0;

        do {
            System.out.println("\n===== INVENTORY TRACKER =====");
            System.out.println("1. Add Item");
            System.out.println("2. Display Inventory");
            System.out.println("3. Search Item by ID");
            System.out.println("4. Sell Item");
            System.out.println("5. Update Stock");
            System.out.println("6. Low Stock Report");
            System.out.println("7. Sort by Quantity");
            System.out.println("8. Save Inventory");
            System.out.println("9. Exit");

            try {
                System.out.print("Enter choice: ");
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Item name: ");
                        String name = sc.nextLine();

                        System.out.print("Quantity: ");
                        int qty = Integer.parseInt(sc.nextLine());

                        System.out.print("Price: ");
                        double price = Double.parseDouble(sc.nextLine());

                        if (name.trim().isEmpty() || name.contains("|")
                                || qty < 0 || price <= 0)
                            System.out.println("Invalid details!");
                        else
                            inv.addItem(name, qty, price);
                        break;

                    case 2:
                        inv.display();
                        break;

                    case 3:
                        System.out.print("Enter ID: ");
                        Item item = inv.search(Integer.parseInt(sc.nextLine()));

                        if (item == null)
                            System.out.println("Item not found.");
                        else
                            item.display();
                        break;

                    case 4:
                        System.out.print("Enter ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Quantity to sell: ");
                        qty = Integer.parseInt(sc.nextLine());

                        inv.sell(id, qty);
                        break;

                    case 5:
                        System.out.print("Enter ID: ");
                        id = Integer.parseInt(sc.nextLine());

                        System.out.print("New quantity: ");
                        qty = Integer.parseInt(sc.nextLine());

                        inv.update(id, qty);
                        break;

                    case 6:
                        inv.lowStock();
                        break;

                    case 7:
                        inv.sort();
                        break;

                    case 8:
                        inv.save();
                        break;

                    case 9:
                        inv.save();
                        System.out.println("Thank you!");
                        break;

                    default:
                        System.out.println("Wrong choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers.");
            }

        } while (choice != 9);
    }
}