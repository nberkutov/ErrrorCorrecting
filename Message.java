package correcter;

public class Message {
    private byte[] message;
    private byte[] encodedMessage;

    public Message(byte[] message) {
        this.message = message;
    }

    public byte[] getEncodedMessage() {
        return encodedMessage;
    }

    public byte[] getMessage() {
        return message;
    }

    public void encode() {
        int bits = message.length * 8;
        int encLength = bits / 3;
        if (bits % 3 != 0) encLength++;

        byte[] encoded = new byte[encLength];
        int[] bit = new int[3];

        int byteNum = 0;
        int bitNum = 7;
        int index = 0;

        while (byteNum < message.length - 1) {
            for (int i = 0; i < 3; i++) {
                bit[i] = getBitOfNumber(message[byteNum], bitNum--);
                if (bitNum < 0) {
                    byteNum++;
                    bitNum = 7;
                }
            }
            encoded[index] = getByteEncoding(bit);
            index++;
        }

        while (bitNum > 0) {
            for (int i = 0; i < 3; i++) {
                bit[i] = getBitOfNumber(message[byteNum], bitNum--);
            }
            encoded[index] = getByteEncoding(bit);
            index++;
        }

        encodedMessage = encoded;
    }

    private int getBitOfNumber(byte number, int numberOfBit) {
        return number >> numberOfBit & 1;
    }

    private byte getByteEncoding(int[] bit) {
        byte t = 0;
        byte bitSum = 0;

        t += bit[0] << 7;
        t += bit[0] << 6;
        bitSum ^= bit[0];

        t += bit[1] << 5;
        t += bit[1] << 4;
        bitSum ^= bit[1];

        t += bit[2] << 3;
        t += bit[2] << 2;
        bitSum ^= bit[2];

        t += bitSum << 1;
        t += bitSum;

        return t;
    }
}
