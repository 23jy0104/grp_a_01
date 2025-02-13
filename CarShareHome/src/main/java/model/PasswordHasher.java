package model;
import java.security.MessageDigest;

public class PasswordHasher {
    public static String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("パスワードはnullであってはいけません");
        }

        try {
            // SHA-256アルゴリズムを使用してパスワードをハッシュ化
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString(); // ハッシュ化されたパスワードを返す
        } catch (Exception e) {
            throw new RuntimeException(e); // エラーが発生した場合は例外をスロー
        }
    }
}