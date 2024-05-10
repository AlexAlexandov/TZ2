import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
class NumberProcessor {
    private String filename;
    public NumberProcessor(String filename) {
        this.filename = filename;
    }
    public int _min() throws IOException {
        int min = Integer.MAX_VALUE;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            String[] numbers = line.split(" ");
            for (String num : numbers) {
                int n = Integer.parseInt(num);
                if (n < min) {
                    min = n;
                }
            }
        }
        return min;
    }
    public int _max() throws IOException {
        int max = Integer.MIN_VALUE;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            String[] numbers = line.split(" ");
            for (String num : numbers) {
                int n = Integer.parseInt(num);
                if (n > max) {
                    max = n;
                }
            }
        }
        return max;
    }
    public int _sum() throws IOException {
        int sum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            String[] numbers = line.split(" ");
            for (String num : numbers) {
                sum += Integer.parseInt(num);
            }
        }
        return sum;
    }
    public long _mult() throws IOException {
        long product = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            String[] numbers = line.split(" ");
            for (String num : numbers) {
                product *= Long.parseLong(num);
            }
        }
        return product;
    }
}
class NumberProcessorTest {
    @Test
    void testFindMin() throws IOException {
        File file = createTestFile("1 2 3 4 5");
        NumberProcessor processor = new NumberProcessor(file.getPath());
        assertEquals(1, processor._min());
        file.delete();
    }
    @Test
    void testFindMax() throws IOException {
        File file = createTestFile("1 2 3 4 5");
        NumberProcessor processor = new NumberProcessor(file.getPath());
        assertEquals(5, processor._max());
        file.delete();
    }
    @Test
    void testCalculateSum() throws IOException {
        File file = createTestFile("1 2 3 4 5");
        NumberProcessor processor = new NumberProcessor(file.getPath());
        assertEquals(15, processor._sum());
        file.delete();
    }
    @Test
    void testCalculateProduct() throws IOException {
        File file = createTestFile("1 2 3 4 5");
        NumberProcessor processor = new NumberProcessor(file.getPath());
        assertEquals(120, processor._mult());
        file.delete();
    }
    @Test
    void testPerformance() throws IOException {
        int[] numCounts = {1000, 10000, 100000, 1000000};
        long[] times = new long[numCounts.length];
        for (int i = 0; i < numCounts.length; i++) {
            File file = createTestFile(generateNumbers(numCounts[i]));
            NumberProcessor processor = new NumberProcessor(file.getPath());
            long startTime = System.nanoTime();
            processor._sum();
            long endTime = System.nanoTime();
            times[i] = endTime - startTime;
            file.delete();
        }
        for (int i = 0; i < numCounts.length; i++) {
            System.out.println("Number of numbers: " + numCounts[i] + ", Time: " + times[i] + " nanoseconds");
        }
    }
    @Test
    void testMemoryUsage() throws IOException {
        int[] sizes = {1000, 10000, 100000, 1000000};
        for (int size : sizes) {
            File file = createTestFile(generateNumbers(size));
            NumberProcessor processor = new NumberProcessor(file.getPath());
            long beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            long res = processor._sum();
            long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            long memoryUsed = afterMemory - beforeMemory;
            System.out.println("Array size: " + size + ", Memory used: " + memoryUsed + " bytes");
            file.delete();
        }
    }
    private File createTestFile(String content) throws IOException {
        File file = File.createTempFile("test", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
        return file;
    }
    private String generateNumbers(int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= count; i++) {
            builder.append(i).append(" ");
        }
        return builder.toString().trim();
    }
}
