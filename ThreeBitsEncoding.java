package correcter;

import java.io.IOException;

public class ThreeBitsEncoding {
    static void encode() throws IOException {
        Msg message = new Msg(Utils.readFile("/home/nikita/send.txt"));

        System.out.println("send.txt:");
        Utils.printTextView(message.getMessage());
        Utils.printHexView(message.getMessage());
        Utils.printBinView(message.getMessage());
        System.out.println();

        System.out.println("encoded.txt:");
        message.encode();
        Utils.writeToFile(message.getEncodedMessage(), "/home/nikita/encoded.txt");
        byte[] msg = Utils.readFile("/home/nikita/encoded.txt");
        System.out.print("parity: ");
        Utils.printBinaryString(msg);
        Utils.printHexView(msg);
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
        byte[] correct = Msg.correct(msg);
        System.out.print("correct: ");
        Utils.printBinaryString(correct);
        int[] decodedBits = Msg.decodeBits(correct);
        System.out.print("decoded: ");
        Utils.printBytes(decodedBits);
        System.out.print("removed: ");
        int[] removed = Msg.remove(decodedBits);
        Utils.printBytes(removed);
        byte[] decoded = Msg.decode(removed);
        Utils.writeToFile(decoded, "/home/nikita/decoded.txt");
        byte[] data = Utils.readFile("/home/nikita/decoded.txt");
        Utils.printHexView(data);
        Utils.printTextView(data);
    }
}
