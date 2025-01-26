package service;

import java.util.List;
import java.util.Map;

public class RegistrationInputFieldService {

   public boolean isEmptyErrorMessage(Map<String, List<String>> stringListMap) {
       List<String> errorMessages = stringListMap.values().stream()
               .flatMap(List::stream)
               .toList();
       return errorMessages.isEmpty();
    }
}
