package com.company;

public class Predict {
    static int NUMBER_OF_SAMPLE = 188;
    public static int PredictResultInt = 0;
    public static void PredictSample(String file)
    {

        // write your code here

        int [] charFrequency = new int[26];
        int [] predictOverAllTimestep  = new int[26];
        double [][] TestData_1 = Read2Matrix.GetAllSampleOneFeature("src/1.csv");
        double [][] TestData_2 = Read2Matrix.GetAllSampleOneFeature("src/2.csv");
        double [][] TestData_3 = Read2Matrix.GetAllSampleOneFeature("src/2.csv");
        double [][] TestData_4 = Read2Matrix.GetAllSampleOneFeature("src/2.csv");
        double [][] TestData_5 = Read2Matrix.GetAllSampleOneFeature("src/2.csv");
        double [][] TestData_x = Read2Matrix.GetAllSampleOneFeature("src/x.csv");
        double [][] TestData_y = Read2Matrix.GetAllSampleOneFeature("src/y.csv");
        double [][] TestData_z = Read2Matrix.GetAllSampleOneFeature("src/z.csv");
//        System.out.println(TestData);

        char[] label =  {'a','a','a','a','a','a','a','a','a','a','b','b','b','b','b','b','b','b','c','c','c','c','c','c','c','c','c','c','d','d','d','d','d','d','d','d','d','d','e','e','e','e','e','e','e','e','e','e','f','f','f','f','f','f','f','f','f','f','g','g','g','g','g','g','g','g','g','g','h','h','h','h','h','h','h','h','h','h','i','i','i','i','i','i','i','i','i','i','j','j','j','j','j','j','j','j','j','j','k','k','k','k','k','k','k','k','k','k','l','l','l','l','l','l','l','l','m','m','m','m','m','m','m','n','n','n','n','n','o','o','o','o','o','p','p','p','p','p','q','q','q','q','q','r','r','r','r','r','s','s','s','s','s','t','t','t','t','t','u','u','u','u','u','v','v','v','v','v','w','w','w','w','w','x','x','x','x','x','y','y','y','y','y','z','z','z','z','z'};
        double [][] Sample = Read2Matrix.GetSample(file);
        double [] matrix_1 = new double[NUMBER_OF_SAMPLE];
        double [] matrix_2 = new double[NUMBER_OF_SAMPLE];
        double [] matrix_3 = new double[NUMBER_OF_SAMPLE];
        double [] matrix_4 = new double[NUMBER_OF_SAMPLE];
        double [] matrix_5 = new double[NUMBER_OF_SAMPLE];
        double [] matrix_x = new double[NUMBER_OF_SAMPLE];
        double [] matrix_y = new double[NUMBER_OF_SAMPLE];
        double [] matrix_z = new double[NUMBER_OF_SAMPLE];
        for (int i = 0; i < 25; i ++) {
            for (int j = 0; j < NUMBER_OF_SAMPLE; j++) {
                matrix_1[j] = TestData_1[i][j];
                matrix_2[j] = TestData_2[i][j];
                matrix_3[j] = TestData_3[i][j];
                matrix_4[j] = TestData_4[i][j];
                matrix_5[j] = TestData_5[i][j];
                matrix_x[j] = TestData_x[i][j];
                matrix_y[j] = TestData_y[i][j];
                matrix_z[j] = TestData_z[i][j];
            }
//            for (int k = 0; k < 8; k++) {
//                Test[k] =
//            }

            KNN.Predict(Sample[i][0], matrix_1, label);
            charFrequency = combineFreq(charFrequency, KNN.charFreq);
            KNN.Predict(Sample[i][1], matrix_2, label);
            charFrequency = combineFreq(charFrequency, KNN.charFreq);
            KNN.Predict(Sample[i][2], matrix_3, label);
            charFrequency = combineFreq(charFrequency, KNN.charFreq);
            KNN.Predict(Sample[i][3], matrix_4, label);
            charFrequency = combineFreq(charFrequency, KNN.charFreq);
            KNN.Predict(Sample[i][4], matrix_5, label);
            charFrequency = combineFreq(charFrequency, KNN.charFreq);
            KNN.Predict(Sample[i][5], matrix_x, label);
            charFrequency = combineFreq(charFrequency, KNN.charFreq);
            KNN.Predict(Sample[i][6], matrix_y, label);
            charFrequency = combineFreq(charFrequency, KNN.charFreq);
            KNN.Predict(Sample[i][7], matrix_z, label);

            System.out.println("Du doan o time step " + i + " la " + (char)('A' +PredictChar(charFrequency)));

            predictOverAllTimestep[PredictChar(charFrequency)] ++;
            //System.out.println(charFrequency);
//            System.out.println(KNN.charFreq[5]);
//            KNN.Predict(Test[2], matrix_2,label);
        }


//        for (int i = 0 ; i < 8; i++)
//        KNN.Predict(Test[i], )

        System.out.println("Du doan cuoi cung la " + (char)('A' +PredictChar(predictOverAllTimestep)));



    }

    public static int [] combineFreq(int[] arr1, int[] arr2 )
    {
        int [] res = new int[26];
        for (int i = 0; i < 26; i++)
            res[i] = arr1[i] + arr2[i];
        return res;
    }

    public static int PredictChar(int [] combine)
    {
        int max = combine[0];
        int index = 0;
        for (int i = 0; i < 26; i++)
        {
            if (combine[i] > max)
            {
                max = combine[i];
                index = i;
            }
        }
        PredictResultInt = index;
        return ( index);
    }

}
