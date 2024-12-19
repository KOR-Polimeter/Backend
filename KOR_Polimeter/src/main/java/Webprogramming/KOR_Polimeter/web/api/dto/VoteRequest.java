package Webprogramming.KOR_Polimeter.web.api.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class VoteRequest {

    private int userId;
    private List<Vote> votes;

    @Getter
    @Setter
    public static class Vote {
        private int polId;
    }
}
