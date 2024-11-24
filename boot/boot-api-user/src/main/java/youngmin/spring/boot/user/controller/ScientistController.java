package youngmin.spring.boot.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import youngmin.spring.boot.user.dao.ScientistDao;
import youngmin.spring.boot.user.dto.ScientistResDto;
import youngmin.spring.boot.user.vo.ScientistVo;

@RestController
@RequiredArgsConstructor
public class ScientistController {

  final ModelMapper modelMapper;
  final ScientistDao scientistDao;
  
  @GetMapping("/user/v1/scientist/all")
  public ResponseEntity<ScientistResDto.ScientistList> findAll() {
    List<ScientistVo> result = scientistDao.findAll();
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/user/v1/scientist/{id}")
  public ResponseEntity<ScientistResDto.Scientist> findById(@PathVariable(name = "id") String id) {
    ScientistVo result = scientistDao.findById(id);
    ScientistResDto.Scientist resDto = modelMapper.map(result, ScientistResDto.Scientist.class);
    return ResponseEntity.ok(resDto);
  }
}
