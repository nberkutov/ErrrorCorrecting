package correcter;

public class Msg {
    private byte[] message;
    private byte[] encodedMessage;
    private byte[] decodedMessage;

    public Msg(byte[] message) {
        this.message = message;
    }

    public byte[] getEncodedMessage() {
        return encodedMessage;
    }

    public byte[] getMessage() {
        return message;
    }

    public static int getBit(int number, int numberOfBit) {
        return number >> numberOfBit & 1;
    }

    public void encode() {
        System.out.print("expand: ");
        int[] msgBits = Utils.bitsToIntArray(message);
        int length = message.length * 8 / 3;
        if (message.length * 8 % 3 != 0)
            length++;

        byte[] encoded = new byte[length];
        int k = 0;
        int i = 0;

        for (; i < msgBits.length - 3; i += 3) {
            byte bitSum = 0;

            byte newByte = (byte)(msgBits[i] << 7);
            newByte += (byte)(msgBits[i] << 6);
            bitSum ^= msgBits[i];
            System.out.print(msgBits[i] + "" + msgBits[i]);

            newByte += (byte)(msgBits[i + 1] << 5);
            newByte += (byte)(msgBits[i + 1] << 4);
            bitSum ^= msgBits[i + 1];
            System.out.print(msgBits[i + 1] + "" + msgBits[i + 1]);

            newByte += (byte)(msgBits[i + 2] << 3);
            newByte += (byte)(msgBits[i + 2] << 2);
            bitSum ^= msgBits[i + 2];
            System.out.print(msgBits[i + 2] + "" + msgBits[i + 2] + ".. ");

            newByte += (byte)(bitSum << 1);
            newByte += bitSum;

            encoded[k] = newByte;
            k++;
        }

        byte newByte = 0;
        byte bitSum = 0;
        int t = 0;
        for (int j = 7; j >= 3; j -= 2) {
            t = 0;
            if (i <= msgBits.length - 1) t = msgBits[i];
            newByte += t << j;
            newByte += t << (j - 1);
            bitSum ^= t;
            i++;
            if(i > msgBits.length) {
                System.out.print("..");
            } else {
                System.out.print(t + "" + t);
            }

        }
        newByte += bitSum << 1;
        newByte += bitSum;
        encoded[k] = newByte;
        System.out.println("..");

        encodedMessage = encoded;
    }

    public static byte[] correct(byte[] wrong) {
        byte[] correct = new byte[wrong.length];
        int k = 0;
        for (var b: wrong) {
            byte t = 0;
            if (getBit(b, 7) == getBit(b, 6)) {
                t += (getBit(b, 7) << 7) + (getBit(b, 6) << 6);
            } else {
                int x = getBit(b, 5) ^ getBit(b, 3) ^ getBit(b, 1);
                t += (x << 7) + (x << 6);
            }

            if (getBit(b, 5) == getBit(b, 4)) {
                t += (getBit(b, 5) << 5) + (getBit(b, 4) << 4);
            } else {
                int x = getBit(b, 7) ^ getBit(b, 3) ^ getBit(b, 1);
                t += (x << 5) + (x << 4);
            }

            if (getBit(b, 3) == getBit(b, 2)) {
                t += (getBit(b, 3) << 3) + (getBit(b, 2) << 2);
            } else {
                int x = getBit(b, 5) ^ getBit(b, 7) ^ getBit(b, 1);
                t += (x << 3) + (x << 2);
            }

            if (getBit(b, 1) == getBit(b, 0)) {
                t += (getBit(b, 1) << 1) + (getBit(b, 0) << 0);
            } else {
                int x = getBit(b, 5) ^ getBit(b, 3) ^ getBit(b, 7);
                t += (x << 1) + (x << 0);
            }
            correct[k] = t;
            k++;
        }

        return correct;
    }

    public static int[] decodeBits(byte[] message) {
        int[] bits = new int[message.length * 3];

        int k = 0;
        for (int i = 0; i < message.length; i++) {
            bits[k] = getBit(message[i], 7);
            k++;
            bits[k] = getBit(message[i], 5);
            k++;
            bits[k] = getBit(message[i], 3);
            k++;
        }

        return bits;
    }

    public static int[] remove(int[] bits) {
        int[] result = new int[bits.length / 8 * 8];
        for (int i = 0; i < result.length; i++) {
            result[i] = bits[i];
        }
        return result;
    }

    public static byte[] decode(int[] bits) {
        byte[] data = new byte[bits.length / 8];
        int k = 0;
        for (int i = 0; i < bits.length - 7; i += 8) {
            for (int j = 0; j <= 7; j++) {
                data[k] += bits[i + j] << (7 - j);
            }
            k++;
        }
        return data;
    }
}
