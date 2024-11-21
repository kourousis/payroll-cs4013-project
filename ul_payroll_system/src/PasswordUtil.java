import org.mindrot.jbcrypt.BCrypt;
// Bcrypt library for hashing passwords

public class PasswordUtil {
    private static final int ROUNDS = 12; // Number of rounds to hash the password

    // Hashes the password
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(ROUNDS));
    }

    // Checks if the password is correct
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}