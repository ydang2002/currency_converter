package question_2_2_missing_number;

public class MissingNumber {
    //Apply the formula to calculate the sum from 1 to n, sum = n*(n+1)/2
    //We replace n = n+1, to the formula to calculate the sum from 1 to n + 1, sum = (n +1)*(n+2)/2
    //Algorithm complexity O(n)
    public static int findMissingNumber(int[] arr, int n) {
        // Expected sum of the range from 1 to n + 1
        int expectedSum = (n + 1) * (n + 2) / 2;

        // Calculate the actual sum of the elements in the array
        int actualSum = 0;
        for (int num : arr) {
            actualSum += num;
        }

        // Missing number is the difference between the expected total and the actual total
        return expectedSum - actualSum;
    }


    //If the numbers in the array are too large, the sum may exceed the limit of the integer type (int),
    // resulting in incorrect results.

    //XOR does not have an overflow number problem
    public static int findMissingNumberUseXOR(int[] arr, int n) {
        // XOR of all numbers from 1 to n+1
        int xorFull = 0;
        for (int i = 1; i <= n + 1; i++) {
            xorFull ^= i;
        }

        // XOR of all elements in the array
        int xorArr = 0;
        for (int num : arr) {
            xorArr ^= num;
        }

        // XOR of all elements in the array
        return xorFull ^ xorArr;
    }

    public static void main(String[] args) {
        int[] arr = {3, 7, 1, 2, 6, 4};
        int n = arr.length;
        System.out.println("Using the arithmetic sum solution, the missing number is: " + findMissingNumber(arr, n) + "\n");
        System.out.println("Using the XOR solution, the missing number is: " + findMissingNumberUseXOR(arr, n));
    }
}

