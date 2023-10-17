package dk.lyngby.factory;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import dk.lyngby.exceptions.TokenException;
import dk.lyngby.model.ClaimBuilder;

import java.util.Date;

public class TokenCreator {

    private static JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();

    public String createToken(ClaimBuilder claimBuilder, byte[] secretKey) throws TokenException {
        if (secretKey == null) throw new TokenException("Secret key not set");
        if (claimBuilder.getClaimSet().isEmpty()) throw new TokenException("Claim set is empty");

        JWTClaimsSet claimSet = createClaimSet(claimBuilder);
        JWSObject jwsObject = createHeaderAndPayload(claimSet);
        return signTokenWithSecretKey(jwsObject, secretKey);
    }

    private String signTokenWithSecretKey(JWSObject jwsObject, byte[] secretKey) throws TokenException {

        try {
            JWSSigner signer = new MACSigner(secretKey);
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new TokenException(e.getMessage());
        }
    }

    private JWSObject createHeaderAndPayload(JWTClaimsSet claimsSet) {
        return new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claimsSet.toJSONObject()));
    }

    private JWTClaimsSet createClaimSet(ClaimBuilder claimBuilder) {
        claimBuilder.getClaimSet().forEach((key, value) -> {
            builder = builder.claim(key, value);
        });

        Date date = new Date();

        builder = builder.issuer(claimBuilder.getIssuer());
        builder = builder.audience(claimBuilder.getAudience());
        builder = builder.expirationTime(new Date(date.getTime() + claimBuilder.getExpirationTime()));
        builder = builder.issueTime(new Date(date.getTime() - claimBuilder.getIssueTime()));
        return builder.build();
    }


}
