package dk.lyngby.factory;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dk.lyngby.exceptions.TokenException;
import dk.lyngby.model.ClaimBuilder;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class TokenVerifier {

    public String verifyTokenAndReturnClaim(String token, byte[] secretKey, ClaimBuilder claimBuilder) throws TokenException {
        try {
            SignedJWT signedJWT = parseTokenAndVerify(token, secretKey);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return getClaimValues(claimsSet, claimBuilder);

        } catch (ParseException | JOSEException e) {
            throw new TokenException(e.getMessage());
        }
    }

    private String getClaimValues(JWTClaimsSet claimsSet, ClaimBuilder claimBuilder) throws TokenException {

        try {
            if (claimsSet.getExpirationTime() != null && new Date().after(claimsSet.getExpirationTime()))
                throw new TokenException("Token is expired");

            if (claimsSet.getIssuer() != null && !claimsSet.getIssuer().equals(claimBuilder.getIssuer()))
                throw new TokenException("Token issuer is invalid");

            if (claimsSet.getAudience() != null && !claimsSet.getAudience().get(0).equals(claimBuilder.getAudience()))
                throw new TokenException("Token audience is invalid");

            claimBuilder.getClaimSet().forEach((key, value) -> {
                if (claimsSet.getClaim(key) == null)
                    try {
                        throw new TokenException("Token claim is invalid");
                    } catch (TokenException e) {
                        throw new RuntimeException(e);
                    }
            });

            StringBuilder res = new StringBuilder();

            Set<Map.Entry<String, String>> entries = claimBuilder.getClaimSet().entrySet();
            int size = entries.size();

            for (int i = 0; i < size; i++) {
                String key = entries.toArray()[i].toString().split("=")[0];
                String value = entries.toArray()[i].toString().split("=")[1];
                if (value.startsWith("[") && value.endsWith("]")) {
                    res.append("\"").append(key).append("\"");
                    res.append(": ");
                    res.append(value);
                    res.append(size - 1 == i ? " }" : ", ");
                } else {
                    res.append("\"").append(key).append("\"");
                    res.append(": ");
                    res.append("\"").append(value).append("\"");
                    res.append(size - 1 == i ? " }" : ", ");
                }
            }

            res.insert(0, "{ ");
            res.append(" }");

            return res.toString().trim().substring(0, res.length() - 2);

        } catch (Exception e) {
            throw new TokenException(e.getMessage());
        }
    }

    private SignedJWT parseTokenAndVerify(String token, byte[] secretKey) throws ParseException, JOSEException, TokenException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(secretKey);

        if (!signedJWT.verify(verifier)) {
            throw new TokenException("Invalid token signature");
        }
        return signedJWT;
    }
}
