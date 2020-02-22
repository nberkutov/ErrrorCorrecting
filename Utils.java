package correcter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Utils {
    static void printArray(int[] arr) {
        for (var item: arr){
            System.out.print(item);
        }
        System.out.println();
    }

    static void printText(byte[] chars) {
        for (var ch : chars) {
            System.out.print((char) ch);
        }
        System.out.println();
    }

    static void printHexString(byte[] chars) {
        for (int i = 0; i < chars.length; i++) {
            System.out.print(Converter.byteToHexString(chars[i]) + " ");
        }
        System.out.println();
    }

    static void printBinaryString(byte[] chars) {
        for (int i = 0; i < chars.length; i++) {
            System.out.print(Converter.byteToBinaryString(chars[i]));
            System.out.print(" ");
        }
        System.out.println();
    }

    static void printTextView(byte[] chars) {
        System.out.print("text view: ");
        printText(chars);
    }

    static void printHexView(byte[] chars) {
        System.out.print("hex view: ");
        printHexString(chars);
    }

    static void printBinView(byte[] chars) {
        System.out.print("bin view: ");
        printBinaryString(chars);
    }



    static void printByte(byte b) {
        for (int i = 7; i >= 0; i--) {
            System.out.print((b >> i) & 1);
        }
    }

    static void printHammingExpand(int[] bits) {
        System.out.print("expand: ");
        for (int i = 0; i < bits.length - 7; i += 8) {
            System.out.print(".." + bits[i + 2] + "." + bits[i + 4]
                    + "" + bits[i + 5] + "" + bits[i + 6] + ". ");
        }
        System.out.println();
    }
    public static int[] bitsToIntArray(byte[] message) {
        int[] bits = new int[message.length * 8];
        int k = 0;
        for (int i = 0; i < message.length; i++) {
            for (int j = 7; j >= 0; j--) {
                bits[k] = message[i] >> j & 1;
                k++;
            }
        }
        return bits;
    }

    static void writeToFile(byte[] chars, String path) throws IOException {
        OutputStream writer = new FileOutputStream(path);
        for (var ch : chars) {
            writer.write(ch);
        }
        writer.close();
    }

    public static byte[] readFile(String path) throws IOException {
        byte[] _data = Files.readAllBytes(Paths.get(path));
        ArrayList<Byte> dataList = new ArrayList<>();
        for (int i = 0; i < _data.length; i++) {
            if (_data[i] != 10) {
                dataList.add(_data[i]);
            }
        }
        byte[] data = new byte[dataList.size()];
        int k = 0;
        for (var d: dataList) {
            data[k++] = d.byteValue();
        }
        return data;
    }

    static void printBytes(int[] bits) {
        for (int i = 0, j = 1; i < bits.length; i++, j++)  {
            System.out.print(bits[i]);
            if (j > 7) {
                System.out.print(" ");
                j = 0;
            }
        }
        System.out.println();
    }

    public static int getBit(int number, int numberOfBit) {
        return number >> numberOfBit & 1;
    }
}