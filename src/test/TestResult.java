package test;

import domain.Exercise;

public class TestResult {

    public static void main(String[] args) {
        int N = 12;
        System.out.println("Завдання 7: подання числа " + N + " у вигляді добутків натуральних чисел");
        System.out.println(Exercise.Calculate(N));
    }
}
