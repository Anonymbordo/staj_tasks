package com.murathan.task1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Seciminiz: ");

            switch (choice) {
                case 1:
                    add();
                    break;
                case 2:
                    subtract();
                    break;
                case 3:
                    multiply();
                    break;
                case 4:
                    divide();
                    break;
                case 5:
                    squareRoot();
                    break;
                case 6:
                    power();
                    break;
                case 0:
                    System.out.println("Programdan cikiliyor. Iyi gunler!");
                    running = false;
                    break;
                default:
                    System.out.println("Gecersiz secim. Lutfen listedeki islemlerden birini secin.");
                    break;
            }

            if (running) {
                System.out.println();
            }
        }
    }

    private static void printMenu() {
        System.out.println("===== Hesap Makinesi =====");
        System.out.println("1 - Toplama");
        System.out.println("2 - Cikarma");
        System.out.println("3 - Carpma");
        System.out.println("4 - Bolme");
        System.out.println("5 - Karekok alma");
        System.out.println("6 - Us alma");
        System.out.println("0 - Cikis");
    }

    private static void add() {
        double firstNumber = readDouble("Birinci sayi: ");
        double secondNumber = readDouble("Ikinci sayi: ");
        printResult(firstNumber + secondNumber);
    }

    private static void subtract() {
        double firstNumber = readDouble("Birinci sayi: ");
        double secondNumber = readDouble("Ikinci sayi: ");
        printResult(firstNumber - secondNumber);
    }

    private static void multiply() {
        double firstNumber = readDouble("Birinci sayi: ");
        double secondNumber = readDouble("Ikinci sayi: ");
        printResult(firstNumber * secondNumber);
    }

    private static void divide() {
        double firstNumber = readDouble("Bolunen sayi: ");
        double secondNumber = readDouble("Bolen sayi: ");

        if (secondNumber == 0) {
            System.out.println("Bir sayi sifira bolunemez.");
            return;
        }

        printResult(firstNumber / secondNumber);
    }

    private static void squareRoot() {
        double number = readDouble("Karekoku alinacak sayi: ");

        if (number < 0) {
            System.out.println("Negatif sayilarin reel karekoku hesaplanamaz.");
            return;
        }

        printResult(Math.sqrt(number));
    }

    private static void power() {
        double base = readDouble("Taban sayi: ");
        double exponent = readDouble("Us sayi: ");
        printResult(Math.pow(base, exponent));
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println("Lutfen gecerli bir tam sayi girin.");
                scanner.nextLine();
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextDouble();
            } catch (InputMismatchException exception) {
                System.out.println("Lutfen gecerli bir sayi girin.");
                scanner.nextLine();
            }
        }
    }

    private static void printResult(double result) {
        System.out.printf("Sonuc: %.2f%n", result);
    }
}
