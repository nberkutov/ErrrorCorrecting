package correcter;

public class HammingEncoder {
    public static byte getByte(int[] bits) {
        if (bits.length != 8) return 0;
        byte _byte = 0;
        for (int i = 0; i <= 7; i++) {
            _byte += bits[i] << (7 - i);
        }
        return _byte;
    }

    public static byte[] encode(byte[] message) {
        int[] bits = Utils.bitsToIntArray(message);
        byte[] encoded = new byte[bits.length / 4];

        for (int i = 0, k = 0; i < bits.length - 3; i += 4) {
            int[] _byte = new int[8];
            _byte[2] = bits[i];
            _byte[4] = bits[i + 1];
            _byte[5] = bits[i + 2];
            _byte[6] = bits[i + 3];
            _byte[7] = 0;

            int bitSum = 0;
            _byte[0] = _byte[2] ^ _byte[4] ^ _byte[6];
            _byte[1] = _byte[2] ^ _byte[5] ^ _byte[6];
            _byte[3] = _byte[4] ^ _byte[5] ^ _byte[6];

            encoded[k] = getByte(_byte);
            k++;
        }

        return encoded;
    }

    public static byte[] correct(byte[] message) {
        byte[] corrected = new byte[message.length];
        for (int i = 0; i < message.length; i++) {
            boolean p1 = Utils.getBit(message[i], 7)
                    == (Utils.getBit(message[i], 5) ^ Utils.getBit(message[i], 3)
                    ^ Utils.getBit(message[i], 1));
            boolean p2 = Utils.getBit(message[i], 6)
                    == (Utils.getBit(message[i], 5) ^ Utils.getBit(message[i], 2)
                    ^ Utils.getBit(message[i], 1));
            boolean p4 = Utils.getBit(message[i], 4)
                    == (Utils.getBit(message[i], 3) ^ Utils.getBit(message[i], 2)
                    ^ Utils.getBit(message[i], 1));

            int sum = 0;
            if (!p1) sum += 1;
            if (!p2) sum += 2;
            if (!p4) sum += 4;



            byte correctedByte = message[i];
            correctedByte ^= 1 << 8 - sum;
            if (Utils.getBit(message[i], 0) != 0) {
                correctedByte ^= 1 << 0;
            }
            corrected[i] = correctedByte;
        }

        return corrected;
    }

    public static byte[] decode(byte[] message) {
        byte[] decoded = new byte[message.length / 2];
        int[] bits = Utils.bitsToIntArray(message);
        int k = 0;
        for (int i = 0; i < bits.length - 15; i += 16) {
            int[] _byte = new int[8];
            _byte[0] = bits[i + 2];
            _byte[1] = bits[i + 4];
            _byte[2] = bits[i + 5];
            _byte[3] = bits[i + 6];

            _byte[4] = bits[i + 10];
            _byte[5] = bits[i + 12];
            _byte[6] = bits[i + 13];
            _byte[7] = bits[i + 14];

            decoded[k] = getByte(_byte);
            k++;
        }
        return decoded;
    }
}
