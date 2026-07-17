package com.murathan.task3;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 100)
                .boxed()
                .collect(Collectors.toList());

        printDivisibleByTwoAndFourCount(numbers);
        printOddAndEvenStatistics(numbers);
        printAdditionalNumericExamples(numbers);
        printAlphabeticExamples();
    }

    private static void printDivisibleByTwoAndFourCount(List<Integer> numbers) {
        long count = numbers.stream()
                .filter(number -> number % 2 == 0 && number % 4 == 0)
                .count();

        System.out.println("===== 2 ve 4 ile Bolunebilen Sayilar =====");
        System.out.printf("Adet: %d%n", count);
        System.out.println();
    }

    private static void printOddAndEvenStatistics(List<Integer> numbers) {
        IntSummaryStatistics oddStatistics = numbers.stream()
                .filter(number -> number % 2 != 0)
                .mapToInt(Integer::intValue)
                .summaryStatistics();

        IntSummaryStatistics evenStatistics = numbers.stream()
                .filter(number -> number % 2 == 0)
                .mapToInt(Integer::intValue)
                .summaryStatistics();

        System.out.println("===== Tek ve Cift Sayi Istatistikleri =====");
        System.out.printf("Tek sayi adedi: %d%n", oddStatistics.getCount());
        System.out.printf("Tek sayi toplami: %d%n", oddStatistics.getSum());
        System.out.printf("Cift sayi adedi: %d%n", evenStatistics.getCount());
        System.out.printf("Cift sayi toplami: %d%n", evenStatistics.getSum());
        System.out.println();
    }

    private static void printAdditionalNumericExamples(List<Integer> numbers) {
        List<Integer> divisibleByFive = numbers.stream()
                .filter(number -> number % 5 == 0)
                .collect(Collectors.toList());

        List<Integer> firstTenSquares = numbers.stream()
                .limit(10)
                .map(number -> number * number)
                .collect(Collectors.toList());

        int sumOfNumbersGreaterThanSeventy = numbers.stream()
                .filter(number -> number > 70)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("===== Ek Numerik Stream Ornekleri =====");
        System.out.println("5 ile bolunebilen sayilar: " + divisibleByFive);
        System.out.println("Ilk 10 sayinin kareleri: " + firstTenSquares);
        System.out.println("70'ten buyuk sayilarin toplami: " + sumOfNumbersGreaterThanSeventy);
        System.out.println();
    }

    private static void printAlphabeticExamples() {
        List<String> names = List.of(
                "Murathan",
                "Ahmet",
                "Ayse",
                "Mehmet",
                "Merve",
                "Zeynep",
                "Ali",
                "Deniz",
                "Buse"
        );

        List<String> namesStartingWithA = names.stream()
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());

        List<String> sortedByLengthThenName = names.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(String::compareTo))
                .collect(Collectors.toList());

        Map<Character, List<String>> groupedByFirstLetter = names.stream()
                .collect(Collectors.groupingBy(name -> name.charAt(0)));

        String upperCaseNames = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));

        System.out.println("===== Alfabetik Stream Ornekleri =====");
        System.out.println("A harfi ile baslayan isimler: " + namesStartingWithA);
        System.out.println("Uzunluga ve alfabeye gore sirali isimler: " + sortedByLengthThenName);
        System.out.println("Ilk harfe gore gruplanmis isimler: " + groupedByFirstLetter);
        System.out.println("Buyuk harfli isimler: " + upperCaseNames);
    }
}
