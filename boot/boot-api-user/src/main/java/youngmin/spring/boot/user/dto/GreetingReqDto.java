package youngmin.spring.boot.user.dto;

import lombok.Data;

public class GreetingReqDto {
  
  private GreetingReqDto() {}

  @Data
  public static class GreetingReq {
    private String lang;
  }
}
