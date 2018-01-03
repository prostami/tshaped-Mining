package TopFrequentTags;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class TopElements {
    
    public static LinkedHashMap<String, Integer> getTopElements(LinkedHashMap<String, Integer> object_property, int limit) {
        LinkedHashMap<String, Integer> newObject_property = new LinkedHashMap<>();
        int count = 1;
        for (Map.Entry<String, Integer> objectItem : object_property.entrySet()) {
            String object = objectItem.getKey();
            int propertyValue = objectItem.getValue();
            if(count <= limit){
                newObject_property.put(object, propertyValue);
                count++;
            }else
                break;
        }
        return newObject_property;
    }
}
