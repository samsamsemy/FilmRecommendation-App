import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {
    private String filmName;
    private String link;
    private String timestamp;

    public Receipt(String filmName, String link) {
        this.filmName = filmName;
        this.link = link;
        this.timestamp = generateTimestamp();
    }

    private String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return dateFormat.format(new Date());
    }

    public void printReceipt() {
        System.out.println("===== Struk Pembelian Film =====" + "\n");
        System.out.println("Film: " + filmName);
        System.out.println("Waktu Pembelian: " + timestamp);
        System.out.println("Link: " + link + "\n");
        System.out.println("Terima kasih atas pembelian Anda!");
        System.out.println("================================");

        saveReceiptToFile();
    }

    private void saveReceiptToFile() {
        // Tentukan lokasi unduhan ke C:\Users\Perso\Downloads\
        String downloadsDirectory = "C:\\Users\\Perso\\Downloads\\";
        String fileName = downloadsDirectory + "receipt_" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("===== Struk Pembelian Film =====\n\n");
            writer.write("Film: " + filmName + "\n");
            writer.write("Waktu Pembelian: " + timestamp + "\n");
            writer.write("Link: " + link + "\n\n");
            writer.write("Terima kasih atas pembelian Anda!\n");
            writer.write("===============================\n");
            System.out.println("Struk telah disimpan di file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
