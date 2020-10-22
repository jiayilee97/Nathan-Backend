package stacs.nathan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.entity.CodeValue;
import stacs.nathan.repository.CodeValueRepository;
import stacs.nathan.utils.enums.CodeType;
import java.util.List;

@Service
public class CodeValueServiceImpl implements CodeValueService {

  @Autowired
  private CodeValueRepository repository;

  public List<CodeValue> findByType(CodeType type){
    return repository.findByTypeAndIsVisible(type, true);
  }

}
