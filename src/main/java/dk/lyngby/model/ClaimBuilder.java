package dk.lyngby.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
public class ClaimBuilder {

    private String issuer;
    private String audience;
    private Map<String, String> claimSet;
    private Long issueTime;
    private Long expirationTime;

}
