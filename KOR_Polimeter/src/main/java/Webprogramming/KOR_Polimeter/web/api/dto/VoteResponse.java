package Webprogramming.KOR_Polimeter.web.api.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class VoteResponse {

    private int userId;
    private List<Vote> votes;

    public void setUserId(long userId) {
    }

    @Getter
    @Setter
    public static class Vote {
        private int polId;
        private String name;
        private String party;
    }
}
