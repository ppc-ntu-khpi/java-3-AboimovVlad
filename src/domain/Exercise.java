package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class contains methods to compute unique representations of a positive integer
 * as a product of natural numbers.
 *
 * The chosen practical task is task 7: find all different representations of a
 * number N as a product of K natural numbers. If K = 0, return all possible
 * products. Representations that differ only by the order of factors are
 * considered identical.
 */
public class Exercise {

    /**
     * Calculates all unique representations of a positive integer N as a product
     * of natural numbers and returns a formatted description of the result.
     *
     * @param N positive integer to represent as a product
     * @return formatted text with all unique product representations
     */
    public static String Calculate(int N) {
        if (N <= 1) {
            return "Число має бути більше за 1.";
        }

        List<List<Integer>> baseFactorizations = new ArrayList<>();
        buildFactorizations(N, 2, new ArrayList<>(), baseFactorizations);

        if (baseFactorizations.isEmpty() || baseFactorizations.stream().noneMatch(list -> list.size() == 1 && list.get(0) == N)) {
            List<Integer> trivial = new ArrayList<>();
            trivial.add(N);
            baseFactorizations.add(trivial);
        }

        Set<String> representations = new HashSet<>();
        for (List<Integer> base : baseFactorizations) {
            int baseSize = base.size();
            int maxOnes = N - 1 - baseSize;
            for (int ones = 0; ones <= maxOnes; ones++) {
                int totalSize = baseSize + ones;
                if (totalSize < 2) {
                    continue;
                }
                List<Integer> representation = new ArrayList<>(ones + baseSize);
                for (int i = 0; i < ones; i++) {
                    representation.add(1);
                }
                representation.addAll(base);
                representations.add(formatProduct(representation));
            }
        }

        if (representations.isEmpty()) {
            return "Для числа " + N + " немає подань у вигляді добутку K натуральних чисел за умовою 1 < K < N.";
        }

        List<String> sorted = new ArrayList<>(representations);
        sorted.sort((a, b) -> {
            int cmp = Integer.compare(countFactors(a), countFactors(b));
            return cmp != 0 ? cmp : a.compareTo(b);
        });

        StringBuilder output = new StringBuilder();
        output.append("Усі різні подання числа ").append(N).append(" у вигляді добутку натуральних чисел:\n");
        for (String representation : sorted) {
            output.append(" - ").append(representation).append("\n");
        }
        output.append("Всього: ").append(sorted.size()).append(" унікальних подань.");
        return output.toString();
    }

    private static void buildFactorizations(int remaining, int minFactor, List<Integer> current, List<List<Integer>> result) {
        if (remaining == 1) {
            if (!current.isEmpty()) {
                result.add(new ArrayList<>(current));
            }
            return;
        }

        for (int factor = minFactor; factor <= remaining; factor++) {
            if (factor > 1 && factor * factor > remaining && factor != remaining) {
                continue;
            }
            if (remaining % factor != 0) {
                continue;
            }
            current.add(factor);
            buildFactorizations(remaining / factor, factor, current, result);
            current.remove(current.size() - 1);
        }
    }

    private static String formatProduct(List<Integer> factors) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < factors.size(); i++) {
            builder.append(factors.get(i));
            if (i < factors.size() - 1) {
                builder.append(" * ");
            }
        }
        return builder.toString();
    }

    private static int countFactors(String representation) {
        return representation.isEmpty() ? 0 : representation.split("\\*").length;
    }
}
