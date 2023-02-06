package merge_sort.test;

import java.io.*;
import java.util.Random;
import java.util.stream.IntStream;

public class TestInputGenerator
{
    public static void custom(String[] args)
    {
        String fileName = "MediumIntInput.txt";
        int COUNT = 1_000_000;
        try (FileWriter fr = new FileWriter(fileName, true); BufferedWriter out = new BufferedWriter(fr))
        {
            Random r = new Random();
            IntStream is = r.ints(COUNT);
            is.forEach((i) -> {
                try
                {
                    out.write(i + "");
                    out.newLine();
                }
                catch (IOException e)
                {
                }
            });
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void demo() {
//        try (FileWriter fr = new FileWriter(fileName, true); BufferedWriter out = new BufferedWriter(fr))
//        {
//            Random r = new Random();
//            IntStream is = r.ints(COUNT);
//            is.forEach((i) -> {
//                try
//                {
//                    out.write(i + "");
//                    out.newLine();
//                }
//                catch (IOException e)
//                {
//                }
//            });
//        }
//        catch (IOException e)
//        {
//            throw new RuntimeException(e);
//        }
    }

    public static void main(String[] args)
    {
        demo();
    }
}
