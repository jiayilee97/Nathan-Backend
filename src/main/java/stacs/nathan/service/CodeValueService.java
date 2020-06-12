package stacs.nathan.service;

import stacs.nathan.entity.CodeValue;
import stacs.nathan.utils.enums.CodeType;
import java.util.List;

public interface CodeValueService {

  List<CodeValue> findByType(CodeType type);

}
