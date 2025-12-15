//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Dessert Entity Class
class Dessert implements Serializable {
    private final String dessertId;
    private final String dessertName;
    private final String flavor;
    private final double price;
    private final int stock;
    private final boolean isSeasonal;

    // Constructor
    public Dessert(String dessertId, String dessertName, String flavor, double price, int stock, boolean isSeasonal) {
        this.dessertId = dessertId;
        this.dessertName = dessertName;
        this.flavor = flavor;
        this.price = price;
        this.stock = stock;
        this.isSeasonal = isSeasonal;
    }

    // Getters and Setters
    public String getDessertId() { return dessertId; }

    public String getDessertName() { return dessertName; }

    public String getFlavor() { return flavor; }

    public double getPrice() { return price; }

    public boolean isSeasonal() { return isSeasonal; }

    @Override
    public String toString() {
        return "🍮 Dessert Info\nID: " + dessertId + "\nName: " + dessertName + "\nFlavor: " + flavor + "\nPrice: $" + price + "\nStock: " + stock + " servings\nSeasonal Limited: " + (isSeasonal ? "Yes" : "No") + "\n";
    }
}

// Management Logic Class
class DessertShopManager {
    private static final String FILE_PATH = "desserts.dat";
    private List<Dessert> dessertList = new ArrayList<>();

    // Load data from file
    public void loadData() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            dessertList = (List<Dessert>) ois.readObject();
            System.out.println("📥 Data loaded successfully!");
        } catch (Exception e) {
            System.out.println("Data load failed: " + e.getMessage());
        }
    }

    // Save data to file
    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(dessertList);
        } catch (Exception e) {
            System.out.println("Data save failed: " + e.getMessage());
        }
    }

    // Check if ID exists
    private boolean isIdExist(String id) {
        return dessertList.stream().anyMatch(d -> d.getDessertId().equals(id));
    }

    // Add dessert
    public void addDessert(Dessert dessert) {
        if (dessert != null && !isIdExist(dessert.getDessertId())) {
            dessertList.add(dessert);
            saveData();
            System.out.println("✅ Added successfully!");
        } else {
            System.out.println("❌ Add failed (duplicate ID/empty info)!");
        }
    }

    // Update dessert
    public void updateDessert(String id, Dessert newDessert) {
        for (int i = 0; i < dessertList.size(); i++) {
            if (dessertList.get(i).getDessertId().equals(id)) {
                dessertList.set(i, newDessert);
                saveData();
                System.out.println("✅ Updated successfully!");
                return;
            }
        }
        System.out.println("❌ Update failed (dessert not found)!");
    }

    // Delete dessert
    public void deleteDessert(String id) {
        boolean removed = dessertList.removeIf(d -> d.getDessertId().equals(id));
        if (removed) {
            saveData();
            System.out.println("✅ Deleted successfully!");
        } else {
            System.out.println("❌ Delete failed!");
        }
    }

    // View all desserts
    public void showAllDesserts() {
        if (dessertList.isEmpty()) {
            System.out.println("📭 No dessert data available!");
            return;
        }
        System.out.println("\n===== Dessert Shop Menu =====");
        for (Dessert d : dessertList) {
            System.out.println("-----------------");
            System.out.println(d);
        }
    }

    // Search by ID
    public Dessert searchById(String id) {
        for (Dessert d : dessertList) {
            if (d.getDessertId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    // Fuzzy search by name/flavor
    public List<Dessert> searchByKeyword(String keyword) {
        List<Dessert> result = new ArrayList<>();
        for (Dessert d : dessertList) {
            if (d.getDessertName().contains(keyword) || d.getFlavor().contains(keyword)) {
                result.add(d);
            }
        }
        return result;
    }

    // Filter seasonal desserts
    public List<Dessert> filterSeasonalDesserts() {
        List<Dessert> seasonalDesserts = new ArrayList<>();
        for (Dessert d : dessertList) {
            if (d.isSeasonal()) {
                seasonalDesserts.add(d);
            }
        }
        return seasonalDesserts;
    }

    // ====================== 新增数组相关代码开始 ======================
    // 将甜品列表转换为Dessert类型的数组（核心数组操作）
    public Dessert[] convertDessertListToArray() {
        // 1. 创建和列表长度相同的Dessert数组
        Dessert[] dessertArray = new Dessert[dessertList.size()];
        // 2. 将ArrayList中的元素填充到数组中（List转数组的核心方法）
        dessertList.toArray(dessertArray);
        // 3. 返回转换后的数组
        return dessertArray;
    }

    // 统计不同价格区间的甜品数量（数组应用：用数组存储价格区间统计结果）
    public void countPriceRange() {
        // 定义价格区间：0-10$、10-20$、20$以上，用数组存储各区间数量
        int[] priceRangeCount = new int[3]; // index0:0-10$, index1:10-20$, index2:>20$

        // 遍历甜品列表，统计各区间数量
        for (Dessert d : dessertList) {
            double price = d.getPrice();
            if (price >= 0 && price <= 10) {
                priceRangeCount[0]++;
            } else if (price > 10 && price <= 20) {
                priceRangeCount[1]++;
            } else if (price > 20) {
                priceRangeCount[2]++;
            }
        }

        // 打印数组中的统计结果
        System.out.println("\n💰 甜品价格区间统计（数组存储）：");
        System.out.println("0-10美元：" + priceRangeCount[0] + "款");
        System.out.println("10-20美元：" + priceRangeCount[1] + "款");
        System.out.println("20美元以上：" + priceRangeCount[2] + "款");
    }
    // ====================== 新增数组相关代码结束 ======================
}

// Main Interactive Class
public class DessertShopMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DessertShopManager manager = new DessertShopManager();

    public static void main(String[] args) {
        manager.loadData();
        while (true) {
            System.out.println("\n🎂 Dessert Shop Management System (Full Edition)");
            System.out.println("1. Add Dessert  2. Update Dessert  3. Delete Dessert  4. View All");
            System.out.println("5. Search by ID  6. Fuzzy Search (Name/Flavor)  7. Filter Seasonal");
            // ====================== 新增数组相关菜单选项开始 ======================
            System.out.println("8. Show Desserts in Array  9. Count Price Range (Array)  10. Exit");
            // ====================== 新增数组相关菜单选项结束 ======================
            System.out.print("Please enter operation number (1-10): ");
            String input = scanner.next();
            // ====================== 修正输入验证范围开始 ======================
            if (!input.matches("[1-9]|10")) {
                // ====================== 修正输入验证范围结束 ======================
                System.out.println("❌ Invalid input! Please enter 1-10!");
                continue;
            }
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1 -> addDessert();
                case 2 -> updateDessert();
                case 3 -> deleteDessert();
                case 4 -> manager.showAllDesserts();
                case 5 -> searchById();
                case 6 -> searchByKeyword();
                case 7 -> filterSeasonalDesserts();
                // ====================== 新增数组相关功能调用开始 ======================
                case 8 -> showDessertsInArray(); // 展示数组形式的甜品
                case 9 -> manager.countPriceRange(); // 展示价格区间统计（数组存储）
                // ====================== 新增数组相关功能调用结束 ======================
                case 10 -> {
                    System.out.println("👋 Exiting system!");
                    scanner.close();
                    System.exit(0);
                }
            }
        }
    }

    // ====================== 新增数组相关方法：展示数组形式的甜品开始 ======================
    // 展示数组中的甜品信息（遍历数组）
    private static void showDessertsInArray() {
        System.out.println("\n----- Desserts in Array Format -----");
        // 获取转换后的甜品数组
        Dessert[] dessertArray = manager.convertDessertListToArray();

        if (dessertArray.length == 0) {
            System.out.println("📭 No dessert data available in array!");
            return;
        }

        // 遍历数组（数组的核心遍历方式）
        for (int i = 0; i < dessertArray.length; i++) {
            System.out.println("🔹 Array Index " + i + ":");
            System.out.println("-----------------");
            System.out.println(dessertArray[i]);
        }
        // 演示数组的长度属性
        System.out.println("📊 Total elements in array: " + dessertArray.length);
    }
    // ====================== 新增数组相关方法：展示数组形式的甜品结束 ======================

    // Add dessert with input validation
    private static void addDessert() {
        System.out.println("\n----- Add New Dessert -----");
        System.out.print("ID (e.g., D001): ");
        String id = scanner.next();
        scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Flavor: ");
        String flavor = scanner.nextLine();

        double price;
        while (true) {
            System.out.print("Price ($): ");
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                if (price >= 0) break;
                else System.out.println("❌ Price cannot be negative!");
            } else {
                System.out.println("❌ Please enter a number!");
                scanner.next();
            }
        }

        int stock;
        while (true) {
            System.out.print("Stock: ");
            if (scanner.hasNextInt()) {
                stock = scanner.nextInt();
                if (stock >= 0) break;
                else System.out.println("❌ Stock cannot be negative!");
            } else {
                System.out.println("❌ Please enter an integer!");
                scanner.next();
            }
        }

        boolean seasonal = false;
        while (true) {
            System.out.print("Seasonal Limited (Yes/No): ");
            String s = scanner.next();
            if (s.equalsIgnoreCase("Yes")) {
                seasonal = true;
                break;
            } else if (s.equalsIgnoreCase("No")) {
                break;
            } else {
                System.out.println("❌ Please enter 'Yes' or 'No'!");
            }
        }
        manager.addDessert(new Dessert(id, name, flavor, price, stock, seasonal));
    }

    // Update dessert with input validation
    private static void updateDessert() {
        System.out.println("\n----- Update Dessert -----");
        System.out.print("Enter ID of dessert to update: ");
        String id = scanner.next();
        Dessert oldDessert = manager.searchById(id);
        if (oldDessert == null) {
            System.out.println("❌ Dessert not found!");
            return;
        }
        System.out.println("Current Dessert Info:");
        System.out.println(oldDessert);
        scanner.nextLine();
        System.out.print("New Name: ");
        String newName = scanner.nextLine();
        System.out.print("New Flavor: ");
        String newFlavor = scanner.nextLine();

        double newPrice;
        while (true) {
            System.out.print("New Price ($): ");
            if (scanner.hasNextDouble()) {
                newPrice = scanner.nextDouble();
                if (newPrice >= 0) break;
                else System.out.println("❌ Price cannot be negative!");
            } else {
                System.out.println("❌ Please enter a number!");
                scanner.next();
            }
        }

        int newStock;
        while (true) {
            System.out.print("New Stock: ");
            if (scanner.hasNextInt()) {
                newStock = scanner.nextInt();
                if (newStock >= 0) break;
                else System.out.println("❌ Stock cannot be negative!");
            } else {
                System.out.println("❌ Please enter an integer!");
                scanner.next();
            }
        }

        boolean newSeasonal = false;
        while (true) {
            System.out.print("New Seasonal Limited (Yes/No): ");
            String s = scanner.next();
            if (s.equalsIgnoreCase("Yes")) {
                newSeasonal = true;
                break;
            } else if (s.equalsIgnoreCase("No")) {
                break;
            } else {
                System.out.println("❌ Please enter 'Yes' or 'No'!");
            }
        }
        manager.updateDessert(id, new Dessert(id, newName, newFlavor, newPrice, newStock, newSeasonal));
    }

    // Delete dessert
    private static void deleteDessert() {
        System.out.println("\n----- Delete Dessert -----");
        System.out.print("Enter ID of dessert to delete: ");
        String id = scanner.next();
        manager.deleteDessert(id);
    }

    // Search by ID
    private static void searchById() {
        System.out.println("\n----- Search by ID -----");
        System.out.print("Enter dessert ID: ");
        String id = scanner.next();
        Dessert dessert = manager.searchById(id);
        if (dessert != null) {
            System.out.println("🔍 Search Result:");
            System.out.println(dessert);
        } else {
            System.out.println("❌ Dessert not found!");
        }
    }

    // Fuzzy search by name/flavor
    private static void searchByKeyword() {
        System.out.println("\n----- Fuzzy Search (Name/Flavor) -----");
        System.out.print("Enter search keyword: ");
        scanner.nextLine();
        String keyword = scanner.nextLine();
        List<Dessert> result = manager.searchByKeyword(keyword);
        if (result.isEmpty()) {
            System.out.println("❌ No matching results!");
        } else {
            System.out.println("🔍 Found " + result.size() + " matching desserts:");
            for (Dessert d : result) {
                System.out.println("-----------------");
                System.out.println(d);
            }
        }
    }

    // Filter seasonal desserts
    private static void filterSeasonalDesserts() {
        System.out.println("\n----- Seasonal Limited Desserts -----");
        List<Dessert> seasonalDesserts = manager.filterSeasonalDesserts();
        if (seasonalDesserts.isEmpty()) {
            System.out.println("📭 No seasonal limited desserts available!");
        } else {
            System.out.println("🍓 Seasonal Limited Desserts:");
            for (Dessert d : seasonalDesserts) {
                System.out.println("-----------------");
                System.out.println(d);
            }
        }
    }
}
