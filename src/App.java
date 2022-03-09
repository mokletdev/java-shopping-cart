import java.util.Arrays;
import java.util.Scanner;

public class App {
    // Main method / flow utama
    public static void main(String[] args) {
        // Deklarasi array baru dan memasukan method produk ke array
        Product[] arr = new Product[2], newArr;
        // Deklarasi variable yang akan dipakai
        int menu = 0, indexHapus = -1, indexUbah = -1;
        boolean restart = true;
        String search = "";

        // Diisi arraynya manual
        arr[0] = new Product(2, "Susu");
        arr[1] = new Product(2, "Minyak");

        // Looping agar mengulangi lagi dari awal jika tidak memilih 5. keluar
        do {
            // Print pilihan menu
            System.out.println("\n--------Pilih Menu--------");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Hapus Produk");
            System.out.println("3. Ubah Produk");
            System.out.println("4. Lihat Produk");
            System.out.println("5. Keluar");

            // Inputan buat menu
            menu = inputInt("Pilihan");

            // Kondisi milih menu yang mana, kalau nggak ada dari 1-5 maka mengulang lagi
            switch (menu) {
                case 1:
                    System.out.println("\n--------Tambah Produk--------");
                    // Input nama dan jumlah ke variable name dan qty
                    String name = inputString("Nama Produk");
                    int qty = inputInt("Jumlah Produk");
                    // Menambahkan panjang array arr 1 wadah (panjang array saat ini + 1)
                    arr = Arrays.copyOf(arr, arr.length + 1);
                    // Masukin data produk yang baru ke array
                    arr[arr.length - 1] = new Product(qty, name);
                    // Pesan sukses
                    System.out.print("Berhasil menambahkan ");
                    arr[arr.length - 1].display();
                    break;
                case 2:
                    System.out.println("\n--------Hapus Produk--------");
                    // Perulangan untuk ngecek apakah produk ada atau tidak
                    // Jika tidak maka akan tanya lagi
                    do {
                        // Input data search
                        search = inputString("Masukan nama Produk");
                        // mencari index atau urutan data yang nama nya sama dengan value search
                        indexHapus = searchProduct(arr, search);
                    } while (indexHapus < 0);
                    // Memunculkan konfirmasi untuk menghapus
                    boolean delete = confirm("menghapus produk ini dari keranjang Anda");
                    // Kondisi jika tidak jadi dihapus
                    if (!delete)
                        break;
                    // membuat array baru dengan panjang -1 dari arrau yang sudah ada
                    newArr = new Product[arr.length - 1];
                    // Looping array
                    for (int i = 0; i < arr.length; i++) {
                        // Cek jika i sama dengan nilai indexHapus, maka tidak akan dimasukan ke array
                        // baru
                        if (i < indexHapus)
                            newArr[i] = arr[i];
                        else if (i > indexHapus)
                            newArr[i - 1] = arr[i];
                    }
                    System.out.println("Berhasil menghapus !");
                    // Memasukan nilai array baru ke arr
                    arr = Arrays.copyOf(newArr, newArr.length);
                    break;
                case 3:
                    System.out.println("\n--------Ubah Produk--------");
                    // Perulangan untuk ngecek apakah produk ada atau tidak
                    // Jika tidak maka akan tanya lagi
                    do {
                        // Input data search
                        search = inputString("Masukan nama Produk");
                        // mencari index atau urutan data yang nama nya sama dengan value search
                        indexUbah = searchProduct(arr, search);
                    } while (indexUbah < 0);
                    // Input jumlah baru
                    int newQty = inputInt("Jumlah Baru");
                    System.out.println("\n" + arr[indexUbah].name + " (" + newQty + ")");
                    // Memunculkan konfirmasi untuk update jumlah
                    boolean update = confirm("dengan jumlah produk saat ini");
                    // Kondisi jika tidak jadi ubah
                    if (!update)
                        break;
                    // Mengubah nilai qty menggunakan method pada produk
                    arr[indexUbah].changeQty(newQty);
                    System.out.print("Berhasil merubah ");
                    arr[indexUbah].display();
                    break;
                case 4:
                    System.out.println("\n--------Daftar Produk--------\n");
                    // Looping untuk menampilkan semua produk
                    for (int i = 0; i < arr.length; i++) {
                        // memanggil method di produk untuk menampilkan value
                        arr[i].display();
                    }
                    break;
                case 5:
                    // Mengubah value restart menjadi false agar keluar dari looping
                    restart = false;
                    break;
                default:
                    break;
            }

        } while (restart);
        System.out.println("\n--------Selesai--------");
    }

    // Method input string
    public static String inputString(String message) {
        Scanner input = new Scanner(System.in);
        System.out.print(message + " : ");
        return input.nextLine();
    }

    // Method input int
    public static int inputInt(String message) {
        Scanner input = new Scanner(System.in);
        System.out.print(message + " : ");
        return input.nextInt();
    }

    // Method cari produk berdasarkan nama
    public static int searchProduct(Product[] listProduct, String search) {
        int foundIndex = -1;

        // Looping list produk
        for (int i = 0; i < listProduct.length; i++)
            // Masuk ke method di produk cek apakah nama sama
            // jika sama, foundIndex akan berisi i
            if (listProduct[i].checkName(search))
                foundIndex = i;

        // Jiak tidak ketemu / foundIndex kurang dari 0 maka muncul pesan error
        if (foundIndex < 0)
            System.out.println("Produk tidak ditemukan\n");
        else {
            // Jika ketemu maka muncul pesan berhasil
            // dan menampilkan produk yang sama
            System.out.println("Produk ditemukan !");
            listProduct[foundIndex].display();
        }

        return foundIndex;
    }

    // Method untuk konfirmasi / confirm action
    public static boolean confirm(String message) {
        String answ;

        // Looping => jika yang di inputkan buka ya atau tidak maka akan terus berulang
        do {
            // Masuk ke method input string buat konfirmasi sesuai message
            answ = inputString("\nApakah Anda yakin " + message + "?\n[Ya | Tidak]").toLowerCase();
        } while (!(answ.equals("ya") || answ.equals("tidak")));

        // Cek jika answ / inputan user itu "ya" maka kembali true
        return answ.equals("ya");
    }
}

// Object produk
class Product {
    public int qty;
    public String name;

    // Default method yang dipanggil saat membuat produk baru
    // Untuk mengisi nama dan jumlah sesuai yang diinputkan
    Product(int qty, String name) {
        this.qty = qty;
        this.name = name;
    }

    // method untuk mengubah jumlah barang sesuai inputan
    void changeQty(int qty) {
        this.qty = qty;
    }

    // method untuk menampilkan detail barang
    void display() {
        System.out.print(name + " (" + qty + ")\n");
    }

    // Method yang mengembalikan nilai true jika name yang dimasukan sama dengan
    // nama produk
    boolean checkName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase());
    }
}
