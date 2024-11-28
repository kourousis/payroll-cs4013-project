import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class PasswordUtil {
    private static final String SALT = "UL"; //salt prefix
    
    /**
     * 
     * @param plainTextPassword
     * @return
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
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    public static boolean checkPassword(String plainTextPassword, String storedHash) {
        // Hash the input password and compare it with the stored hash
        String hashedInput = hashPassword(plainTextPassword);
        return hashedInput.equals(storedHash);
    }
}