package correcter;

public class Converter {
    public static String byteToBinaryString(byte b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            int bit = (b >> i) & 1;
            sb.append(bit);
        }
        return sb.toString();
    }

    public static String byteToHexString(byte b) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 7, j = 3; i >= 4; i--, j--) {
            int bit1 = (b >> i) & 1;
            int bit2 = (b >> j) & 1;
            sb1.append(bit1);
            sb2.append(bit2);
        }
        String result = Integer.toHexString(Integer.parseInt(sb1.toString(), 2))
                + Integer.toHexString(Integer.parseInt(sb2.toString(), 2));
        return result.toUpperCase();
    }
}
