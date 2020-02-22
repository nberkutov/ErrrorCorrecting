package correcter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ErrorEmulator {
    public static byte[] simulateError(byte[] s) {
        Random random = new Random();
        byte[] wrong = new byte[s.length];
        System.arraycopy(s, 0, wrong, 0, s.length);
        for (int i = 0; i < wrong.length; i++) {
            wrong[i] ^= 1 << random.nextInt(8);
        }

        return wrong;
    }
}
