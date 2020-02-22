package correcter;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String sendPath = "/home/nikita/send.txt";
        String receivedPath = "/home/nikita/received.txt";
        String mode = "";
        try {
            System.out.print("Write a mode: ");
            Scanner scanner = new Scanner(System.in);
            mode = scanner.nextLine();
            System.out.println();

            switch (mode) {
                case "encode":
                    encode();
                    break;
                case "send":
                    send();
                    break;
                case "decode":
                    decode();
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            System.out.println("Error occurs " + e.getMessage());
        }
    }

    static void encode() throws IOException {
        System.out.println("send.txt:");
        byte[] message = Utils.readFile("/home/nikita/send.txt");
        Utils.printTextView(message);
        Utils.printHexView(message);
        Utils.printBinView(message);
        System.out.println();

        System.out.println("encoded.txt:");
        byte[] encoded = HammingEncoder.encode(message);
        Utils.printHammingExpand(Utils.bitsToIntArray(encoded));
        System.out.print("parity: ");
        Utils.printBinaryString(encoded);
        Utils.printHexView(encoded);
        Utils.writeToFile(encoded, "/home/nikita/encoded.txt");
    }

    static void send() throws IOException {
        System.out.println("encoded.txt:");
        byte[] msg = Utils.readFile("/home/nikita/encoded.txt");
        Utils.printHexView(msg);
        Utils.printBinView(msg);
        System.out.println();

        System.out.println("received.txt");
        byte[] wrong = ErrorEmulator.simulateError(msg);
        Utils.writeToFile(wrong,"/home/nikita/received.txt");
        Utils.printBinView(wrong);
        Utils.printHexView(wrong);
    }

    static void decode() throws IOException {
        System.out.println("received.txt");
        byte[] msg = Utils.readFile("/home/nikita/received.txt");
        Utils.printHexView(msg);
        Utils.printBinView(msg);
        System.out.println();

        System.out.println("decoded.txt");
        System.out.print("correct: ");
        byte[] correct = HammingEncoder.correct(msg);
        Utils.printBinaryString(correct);
        byte[] decoded = HammingEncoder.decode(correct);

        System.out.print("decoded: ");
        Utils.printBinaryString(decoded);
        Utils.writeToFile(decoded, "/home/nikita/decoded.txt");
        byte[] data = Utils.readFile("/home/nikita/decoded.txt");
        Utils.printHexView(data);
        Utils.printTextView(data);
    }
}
