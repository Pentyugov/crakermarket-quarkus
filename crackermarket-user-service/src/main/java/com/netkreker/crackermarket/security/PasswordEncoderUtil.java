package com.netkreker.crackermarket.security;

import com.netkreker.crackermarket.model.User;

//import org.wildfly.security.password.PasswordFactory;
//import org.wildfly.security.password.WildFlyElytronPasswordProvider;
//import org.wildfly.security.password.interfaces.BCryptPassword;
//import org.wildfly.security.password.spec.EncryptablePasswordSpec;
//import org.wildfly.security.password.spec.IteratedSaltedPasswordAlgorithmSpec;

public class PasswordEncoderUtil {

    public static void encodePassword(User user) {
//        final Provider ELYTRON_PROVIDER = new WildFlyElytronPasswordProvider();
//        final String PASSWORD = user.getPassword();
//        try {
//            PasswordFactory passwordFactory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT, ELYTRON_PROVIDER);
//            int iterationCount = 10;
//
//            byte[] salt = new byte[BCryptPassword.BCRYPT_SALT_SIZE];
//
//            SecureRandom random = new SecureRandom();
//
//            random.nextBytes(salt);
//
//            IteratedSaltedPasswordAlgorithmSpec iteratedAlgorithmSpec = new IteratedSaltedPasswordAlgorithmSpec(iterationCount, salt);
//
//            EncryptablePasswordSpec encryptableSpec = new EncryptablePasswordSpec(PASSWORD.toCharArray(), iteratedAlgorithmSpec);
//
//            BCryptPassword original = (BCryptPassword) passwordFactory.generatePassword(encryptableSpec);
//
//            byte[] hash = original.getHash();
//
//            Base64.Encoder encoder = Base64.getEncoder();
//
//            user.setIterationCount(iterationCount);
//
//            user.setSalt(encoder.encodeToString(salt));
//
//            user.setPassword(encoder.encodeToString(hash));
//
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            e.printStackTrace();
//        }
//    }
    }
}
