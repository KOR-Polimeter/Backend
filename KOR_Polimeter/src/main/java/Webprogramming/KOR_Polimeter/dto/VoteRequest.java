package Webprogramming.KOR_Polimeter.dto;

public class VoteRequest {
    private Long politicianId;

    // 기본 생성자
    public VoteRequest() {}

    // getter와 setter
    public Long getPoliticianId() {
        return politicianId;
    }

    public void setPoliticianId(Long politicianId) {
        this.politicianId = politicianId;
    }

    // 필요 시 추가 필드와 getter/setter 작성
}
