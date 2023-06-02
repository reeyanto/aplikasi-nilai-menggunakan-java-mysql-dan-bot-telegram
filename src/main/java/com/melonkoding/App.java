package com.melonkoding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.melonkoding.models.Mahasiswa;
import com.melonkoding.models.Matakuliah;
import com.melonkoding.models.Nilai;
import com.melonkoding.repositories.MahasiswaRepository;
import com.melonkoding.repositories.MatakuliahRepository;
import com.melonkoding.repositories.NilaiRepository;

public class App 
{

    // informasi database
    private static final String DB_URL = "jdbc:mysql://localhost/aplikasi_nilai_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    // kebutuhan koneksi ke database
    private static Connection connection;

    // kebutuhan handle masukan dari user
    private static Scanner scanner;
    private static String menu;
    private static String tambah;

    // kebutuhan integrasi bot telegram
    private static final String BOT_TOKEN = "GANTI DENGAN BOT TOKEN ANDA DISINI";
    private static final String BOT_USERNAME = "GANDI DENGAN BOT USERNAME ANDA DISINI";

    public static void main(String[] args) throws SQLException {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(BOT_TOKEN));
            System.out.println("Bot is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }


        scanner    = new Scanner(System.in);
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        
        do {
            System.out.println("Daftar Menu: ");
            System.out.println("=============");
            System.out.println("0. Keluar Aplikasi");
            System.out.println("1. Daftar Mahasiswa");
            System.out.println("2. Daftar Matakuliah");
            System.out.println("3. Daftar Nilai");

            System.out.print("Masukkan nomor menu: ");
            menu = scanner.nextLine();

            switch(menu) {
                case "1":
                    showMahasiswa();
                    break;

                case "2":
                    showMatakuliah();
                    break;

                case "3":
                    showNilai();
                    break;

                default:
                    // do nothing
                    break;
            }

        }
        while( ! menu.equals("0"));
    }


    private static void showMahasiswa() throws SQLException {
        System.out.println("Daftar Mahasiswa:");
        System.out.println("=================");

        MahasiswaRepository mahasiswaRepository = new MahasiswaRepository(connection);
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.getAllMahasiswa();

        for(Mahasiswa mahasiswa: mahasiswaList) {
            System.out.printf("NIM          : %s\n", mahasiswa.getNim());
            System.out.printf("Nama Lengkap : %s\n", mahasiswa.getNama());
            System.out.println("------------------------------");
        }

        do {
            System.out.print("Tambah data mahasiswa [Y/N]?: ");
            tambah = scanner.nextLine();

            if (tambah.equalsIgnoreCase("y")) {
                System.out.print("NIM          : ");
                String nim = scanner.nextLine();

                System.out.print("Nama Lengkap : ");
                String nama= scanner.nextLine();

                boolean hasil = mahasiswaRepository.addMahasiswa(new Mahasiswa(nim, nama));
                if (hasil) {
                    System.out.println("1 data berhasil ditambahkan");
                }
            }
        }
        while(tambah.equalsIgnoreCase("Y"));
    }


    private static void showMatakuliah() throws SQLException {
        System.out.println("Daftar Matakuliah:");
        System.out.println("==================");

        MatakuliahRepository matakuliahRepository = new MatakuliahRepository(connection);
        List<Matakuliah> matakuliahList = matakuliahRepository.getAllMatakuliah();

        for(Matakuliah matakuliah: matakuliahList) {
            System.out.printf("Kode Matakuliah : %s\n", matakuliah.getKode_mk());
            System.out.printf("Nama Matakuliah : %s\n", matakuliah.getNama_mk());
            System.out.printf("SKS             : %s\n", matakuliah.getSks());
            System.out.printf("Semester        : %s\n", matakuliah.getSemester());
            System.out.println("------------------------------");
        }

        do {
            System.out.print("Tambah data matakuliah [Y/N]?: ");
            tambah = scanner.nextLine();

            if (tambah.equalsIgnoreCase("y")) {
                System.out.print("Kode Matakuliah : ");
                String kode_mk = scanner.nextLine();

                System.out.print("Nama Matakuliah : ");
                String nama_mk = scanner.nextLine();

                System.out.print("SKS             : ");
                String sks = scanner.nextLine();

                System.out.print("Semester        : ");
                String semester = scanner.nextLine();

                boolean hasil = matakuliahRepository.addMatakuliah(new Matakuliah(kode_mk, nama_mk, Integer.parseInt(sks), Integer.parseInt(semester)));
                if (hasil) {
                    System.out.println("1 data berhasil ditambahkan");
                }
            }
        }
        while(tambah.equalsIgnoreCase("Y"));
    }


    private static void showNilai() throws SQLException {
        System.out.println("Daftar Nilai Mahasiswa:");
        System.out.println("=======================");

        NilaiRepository nilaiRepository = new NilaiRepository(connection);
        List<Nilai> nilaiList = nilaiRepository.getAllNilai();

        for(Nilai nilai: nilaiList) {
            System.out.printf("Nama Mahasiswa  : %s\n", nilai.getMahasiswa().getNama());
            System.out.printf("Nama Matakuliah : %s\n", nilai.getMatakuliah().getNama_mk());
            System.out.printf("Semester        : %s\n", nilai.getMatakuliah().getSemester());
            System.out.printf("Nilai           : %s\n", nilai.getNilai());
            System.out.println("--------------------------------------------");
        }

        do {
            System.out.print("Tambah data nilai [Y/N]?: ");
            tambah = scanner.nextLine();

            if (tambah.equalsIgnoreCase("y")) {
                System.out.print("NIM             : ");
                String nim = scanner.nextLine();

                MahasiswaRepository mahasiswaRepository = new MahasiswaRepository(connection);
                Mahasiswa mahasiswa = mahasiswaRepository.getMahasiswaByNim(nim);

                System.out.println("Nama Mahasiswa  : "+ mahasiswa.getNama());

                System.out.print("Kode Matakuliah : ");
                String kode_mk = scanner.nextLine();

                MatakuliahRepository matakuliahRepository = new MatakuliahRepository(connection);
                Matakuliah matakuliah = matakuliahRepository.getMatakuliahByKode(kode_mk);

                System.out.println("Nama Matakuliah : "+ matakuliah.getNama_mk());
                System.out.println("SKS             : "+ matakuliah.getSks());
                System.out.println("Semester        : "+ matakuliah.getSemester());


                System.out.print("Nilai           : ");
                String nilai = scanner.nextLine();

                boolean hasil = nilaiRepository.addNilai(mahasiswa, matakuliah, Integer.parseInt(nilai));
                if (hasil) {
                    System.out.println("1 data berhasil ditambahkan");
                }
            }
        }
        while(tambah.equalsIgnoreCase("Y"));
    }


    static class Bot extends TelegramLongPollingBot {

        public Bot(String token) {
            super(token);
        }

        @Override
        public void onUpdateReceived(Update update) {
            if (update.hasMessage() && update.getMessage().hasText()) {
                long chatId = update.getMessage().getChatId();
                String str  = update.getMessage().getText();

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);

                switch(str) {
                    case "/start":
                        String startStr = "Selamat datang di aplikasi nilai menggunakan java, mysql dan bot telegram.\n\nKetikkan NIM Anda";
                        sendMessage.setText(startStr);
                    break;


                    default:
                        NilaiRepository nilaiRepository = new NilaiRepository(connection);
                        try {
                            List<Nilai> nilaiList = nilaiRepository.getNilaiByNim(str);
                            String message = "";
        
                            if (nilaiList.size() > 0) {
                                message += String.format("Daftar nilai untuk %s adalah:\n\n", nilaiList.get(0).getMahasiswa().getNama());

                                for(Nilai nilai: nilaiList) {
                                    message += String.format("Kode Matakuliah: %s\nMatakuliah: %s\nNilai: %d\n\n", 
                                        nilai.getMatakuliah().getKode_mk(),
                                        nilai.getMatakuliah().getNama_mk(),
                                        nilai.getNilai()
                                    );
                                }
                            } else {
                                message = String.format("Tidak ada data untuk NIM: %s", str);
                            }

                            sendMessage.setText(message);
        
                        } catch (SQLException e) {                
                            e.printStackTrace();
                        }
                    break;
                }

                try {
                    execute(sendMessage);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public String getBotUsername() {
            return BOT_USERNAME;
        }
    }
}
