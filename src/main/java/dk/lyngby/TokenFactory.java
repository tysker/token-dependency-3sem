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

}