import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class PasswordUtil {
    private static final String SALT = "UL"; // salt prefix

    /**
     * Method used to hash a plain text string (password in this case),
     * to a SHA-256 hash in order to store in the csv database
     * 
     * @param plainTextPassword plain text password
     * @return SHA-256 of the plain text password
     */
    public static String hashPassword(String plainTextPassword) {
        try {
            String saltedPassword = SALT + plainTextPassword;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hash the password by converting it to byte array
            byte[] hash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            // Convert byte array to hex string
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    /**
     * Method used to compare a plain text string to a hash
     * 
     * @param plainTextPassword the password in plaintext
     * @param storedHash        the hash of the corresponding plaintext password
     *                          that is stored on the csv database
     * @return whether the plaintext and hashed passwword match
     */
    public static boolean checkPassword(String plainTextPassword, String storedHash) {
        // Hash the input password and compare it with the stored hash
        String hashedInput = hashPassword(plainTextPassword);
        return hashedInput.equals(storedHash);
    }
}