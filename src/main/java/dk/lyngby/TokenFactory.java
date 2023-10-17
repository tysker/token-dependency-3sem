package dk.lyngby;

import dk.lyngby.exceptions.TokenException;
import dk.lyngby.factory.TokenCreator;
import dk.lyngby.factory.TokenVerifier;
import dk.lyngby.model.ClaimBuilder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TokenFactory {

    private static final TokenCreator creator = new TokenCreator();
    private static final TokenVerifier verifier = new TokenVerifier();
    public static String createToken(ClaimBuilder claimBuilder, String secretKey) throws TokenException {
        return creator.createToken(claimBuilder, secretKey.getBytes());
    }

    public static String verifyToken(String token, String secretKey, ClaimBuilder claimBuilder) throws TokenException {
        return verifier.verifyTokenAndReturnClaim(token, secretKey.getBytes(), claimBuilder);
    }

    public static String getUsernameFromToken(String token) throws TokenException {
        return verifier.getUsernameFromToken(token);
    }

//    public static void main(String[] args) throws TokenException {
//        TokenCreator token = new TokenCreator();
//        TokenVerifier verifier = new TokenVerifier();
//        String secret = "secret841D8A6C80CBA4FCAD32D5367C18C53B";
//
//        ClaimBuilder claimBuilder = ClaimBuilder.builder()
//                .issuer("lyngby")
//                .audience("datamatiker")
//                .claimSet(Map.of("username", "steve", "roles", "[ADMIN, USER]"))
//                .expirationTime(3600000L)
//                .issueTime(3600000L)
//                .build();
//
//        ClaimBuilder claimBuilderVerify = ClaimBuilder.builder()
//                .issuer("lyngby")
//                .audience("datamatiker")
//                .claimSet(Map.of("username", "steve", "roles", "[ADMIN, USER]"))
//                .expirationTime(3600000L)
//                .issueTime(3600000L)
//                .build();
//
//        String token1 = token.createToken(claimBuilder, secret.getBytes());
//
//        String usernameFromToken = getUsernameFromToken(token1);
//        System.out.println("Steve: " + usernameFromToken);
//        System.out.println(token1);
//        String verified = verifier.verifyTokenAndReturnClaim(token1, secret.getBytes(), claimBuilderVerify);
//        System.out.println(verified);
//    }
}