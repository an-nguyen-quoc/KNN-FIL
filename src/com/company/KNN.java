package com.company;

public class KNN {

    public static char predictChar = '_';
    private static final int K_nearest = 6;
    static double[] minArray = new double[K_nearest + 1];
    public static int[] charFreq = new int[26];
    static char[] keyArray = new char[K_nearest + 1];
    public static char Predict (double value, double[] matrix, char[] label) {
        Init();
        for (int i = 0; i < matrix.length; i++)
        {
            Insert(computeDistance(value,matrix[i]), label[i]);
        }
        printProbability();
        //System.out.println("Predicted : "+ predictChar);
        return predictChar;
    }

    public static double computeDistance(double arr1, double arr2)
    {

        return (arr1 - arr2)*(arr1 - arr2);
    }

    public static void Insert(double value, char key)
    {
        int j = K_nearest - 1;
        while (j >= 0 && minArray[j] == -1 ) j = j -1;
        while (j >= 0 && value < minArray[j])
        {
            minArray[j+ 1] = minArray[j];
            keyArray[j+1] = keyArray[j];
            j = j - 1;
        }
        minArray[j+1] = value;
        keyArray[j+1] = key;
    }

    public static void Init()
    {
        for (int i = 0; i < K_nearest + 1; i++) {
            minArray[i] = -1;
            keyArray[i] = 'Z' + 1;
        }
        for (int j = 0; j < 26; j++)
            charFreq[j] = 0;
    }

    public  static void getCharProbability()
    {
        int max = 0;
        for (int i = 0; i < K_nearest; i++)
        {
            charFreq[(int)keyArray[i] - 97] ++;
            if (charFreq[(int)keyArray[i] - 97] > max)
            {
                max = charFreq[(int)keyArray[i] - 97];
                predictChar = keyArray[i];
            }
        }
    }

    static void printProbability()
    {
        getCharProbability();
        for (int i = 0; i < 26; i++)
            if(charFreq[i] != 0);
                //System.out.println((char)('A' + i) + ": " + charFreq[i]);
    }
    static void printArray(double[] arr)
    {
        int n = arr.length;
        for (double v : arr) System.out.println(v + " ");
    }
    static void printArray(char[] arr)
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.println(arr[i] + " ");
    }
    static void printDistanceLabel()
    {
        int n = K_nearest;
        for (int i = 0; i < n; ++i)
            System.out.println("ID = " + i + ", Khoang cach = " + minArray[i] + ", Label =  " + keyArray[i]);
    }
}
