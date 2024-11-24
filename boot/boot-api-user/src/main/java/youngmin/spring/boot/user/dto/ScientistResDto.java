package youngmin.spring.boot.user.dto;

import java.util.List;

import lombok.Data;
import youngmin.spring.boot.user.vo.ScientistVo;

@Data
public class ScientistResDto {

  public static class Scientist extends ScientistVo { }
  
  @Data
  public static class ScientistList {
    private List<Scientist> list;
  }
}