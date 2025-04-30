package org.example;

import java.util.Scanner;

public class CircleIntersection {

    static int MAX = 10000;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            int x1 = scanner.nextInt();
            int r1 = scanner.nextInt();
            int x2 = scanner.nextInt();
            int r2 = scanner.nextInt();

            validateInput(x1, r1, x2, r2);

            System.out.println(intersectionDescription(x1, r1, x2, r2));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");
        }
    }

    public static void validateInput(int x1, int r1, int x2, int r2) {
        if (r1 <= 0 || r2 <= 0) {
            throw new IllegalArgumentException("Radius must be positive.");
        }
        if (x1 < 0 || x2 < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative.");
        }

        if (r1 > MAX || r2 > MAX || x1 > MAX || x2 > MAX) {
            throw new IllegalArgumentException("Radius must be positive.");
        }
    }

    public static String intersectionDescription(int x1, int r1, int x2, int r2) {
        // Handle potential overflow in distance or sum of radius
        long dx = (long) x1 - x2;
        long d = Math.abs(dx);
        long rSum = (long) r1 + r2;
        long rDiff = Math.abs((long) r1 - r2);

        if (x1 == x2 && r1 == r2) {
            return "The circles coincide; there are infinitely many points of intersection";
        }

        if (d > rSum) {
            return "0 points";
        } else if (d < rDiff) {
            return "0 points";
        } else if (d == rSum || d == rDiff) {
            return "1 point";
        } else {
            return "2 points";
        }
    }
}