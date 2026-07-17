package com.murathan.task2;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        boolean playAgain = true;

        System.out.println("===== Sayi Tahmin Etme Oyunu =====");

        while (playAgain) {
            GameSettings settings = chooseGameSettings();
            playGame(settings);
            playAgain = askPlayAgain();
        }

        System.out.println("Oyun kapatildi. Iyi gunler!");
    }

    private static GameSettings chooseGameSettings() {
        System.out.println();
        System.out.println("Sayi araligini secin:");
        System.out.println("1 - 1 ile 100 arasi");
        System.out.println("2 - 1 ile 1000 arasi");

        int choice = readIntBetween("Seciminiz: ", 1, 2);

        if (choice == 1) {
            return new GameSettings(100, 7, 60);
        }

        return new GameSettings(1000, 10, 90);
    }

    private static void playGame(GameSettings settings) {
        int secretNumber = random.nextInt(settings.maxNumber()) + 1;
        int remainingAttempts = settings.maxAttempts();
        long endTime = System.currentTimeMillis() + settings.timeLimitSeconds() * 1000L;
        boolean won = false;

        System.out.println();
        System.out.printf("1 ile %d arasinda bir sayi tuttum.%n", settings.maxNumber());
        System.out.printf("Deneme hakki: %d - Sure: %d saniye%n", settings.maxAttempts(), settings.timeLimitSeconds());

        while (remainingAttempts > 0 && System.currentTimeMillis() < endTime) {
            long remainingSeconds = Math.max(0, (endTime - System.currentTimeMillis()) / 1000);
            System.out.printf("%nKalan sure: %d saniye - Kalan deneme: %d%n", remainingSeconds, remainingAttempts);

            int guess = readIntBetween("Tahmininiz: ", 1, settings.maxNumber());
            remainingAttempts--;

            if (System.currentTimeMillis() > endTime) {
                break;
            }

            if (guess == secretNumber) {
                won = true;
                System.out.printf("Tebrikler! Dogru sayi: %d%n", secretNumber);
                break;
            }

            if (guess > secretNumber) {
                System.out.println("Cok Yuksek");
            } else {
                System.out.println("Cok Dusuk");
            }
        }

        if (!won) {
            System.out.println();
            System.out.println("Oyunu kaybettiniz.");
            System.out.printf("Gercek sayi: %d%n", secretNumber);
        }
    }

    private static boolean askPlayAgain() {
        System.out.println();
        System.out.println("Tekrar oynamak ister misiniz?");
        System.out.println("1 - Evet");
        System.out.println("2 - Hayir");

        return readIntBetween("Seciminiz: ", 1, 2) == 1;
    }

    private static int readIntBetween(String message, int min, int max) {
        while (true) {
            int value = readInt(message);

            if (value >= min && value <= max) {
                return value;
            }

            System.out.printf("Lutfen %d ile %d arasinda bir sayi girin.%n", min, max);
        }
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

    private static class GameSettings {
        private final int maxNumber;
        private final int maxAttempts;
        private final int timeLimitSeconds;

        private GameSettings(int maxNumber, int maxAttempts, int timeLimitSeconds) {
            this.maxNumber = maxNumber;
            this.maxAttempts = maxAttempts;
            this.timeLimitSeconds = timeLimitSeconds;
        }

        private int maxNumber() {
            return maxNumber;
        }

        private int maxAttempts() {
            return maxAttempts;
        }

        private int timeLimitSeconds() {
            return timeLimitSeconds;
        }
    }
}
