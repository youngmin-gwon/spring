package youngmin.spring.boot.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import youngmin.spring.boot.user.vo.ScientistVo;

public interface ScientistDao {

  List<ScientistVo> findAll();
  
  ScientistVo findById(@Param("id") String id);
}
