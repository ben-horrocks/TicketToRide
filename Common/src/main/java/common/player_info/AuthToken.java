package common.player_info;

import java.io.Serializable;
import java.util.*;

public class AuthToken implements Serializable
{
    private String token;
    private long generationTime;
    private static Set<String> validTokens = new HashSet<>();

    public AuthToken()
    {
        this.token = generateNewToken();
        validTokens.add(this.token);
        this.generationTime = System.currentTimeMillis();
    }

    public static boolean isValidToken(String token)
    {
        return validTokens.contains(token);
    }

    public static TimerTask generateTimeout(String authToken)
    {
        return new Timeout(authToken);
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    private String generateNewToken()
    {

        StringBuilder token = new StringBuilder();
        token.setLength(0);
        token.append(UUID.randomUUID().toString());
        token.deleteCharAt(23);
        token.deleteCharAt(18);
        token.deleteCharAt(13);
        token.deleteCharAt(8);
        return token.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof AuthToken))
        {
            return false;
        }

        AuthToken authToken = (AuthToken) o;

        if (generationTime != authToken.generationTime)
        {
            return false;
        }
        return token != null ? token.equals(authToken.token) : authToken.token == null;
    }

    @Override
    public int hashCode()
    {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (int) (generationTime ^ (generationTime >>> 32));
        return result;
    }

    static class Timeout extends TimerTask
    {
        private final String authToken;

        Timeout(String authToken)
        {
            this.authToken = authToken;
        }

        @Override
        public void run()
        {
            validTokens.remove(this.authToken);
        }
    }

}
