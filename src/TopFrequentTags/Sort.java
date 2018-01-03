package TopFrequentTags;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Sort {
    public static LinkedHashMap<String,Integer> getSortedObjects(LinkedHashMap<String,Integer> object_property,String orderType){
        LinkedHashMap<String,Integer> sortedObject_count = new LinkedHashMap<>();
        TreeMap<Integer,HashSet<String>> propery_objectList = getObjectListBasedOnSortedProperty(object_property,orderType);
        for(Map.Entry<Integer,HashSet<String>> propertyItem: propery_objectList.entrySet()){
            int propertyValue = propertyItem.getKey();
            HashSet<String> ObjectList = (HashSet<String>) propertyItem.getValue().clone();
            for(String object : ObjectList){
                sortedObject_count.put(object, propertyValue);
            }
        }
        return sortedObject_count;
    }

    private static TreeMap<Integer, HashSet<String>> getObjectListBasedOnSortedProperty(LinkedHashMap<String, Integer> object_property, String orderType) {
        TreeMap<Integer,HashSet<String>> property_ObjectList;
        property_ObjectList = (orderType.equals("DESC")?new TreeMap<>(Collections.reverseOrder()):new TreeMap<>()) ;
        for(Map.Entry<String,Integer> ObjectItem: object_property.entrySet()){
            String object = ObjectItem.getKey();
            int propertyValue = ObjectItem.getValue();
            if(property_ObjectList.containsKey(propertyValue)){
                HashSet<String> ObjectList = property_ObjectList.get(propertyValue);
                ObjectList.add(object);
                property_ObjectList.replace(propertyValue, ObjectList);
            }else{
                HashSet<String> ObjectList = new HashSet<>();
                ObjectList.add(object);
                property_ObjectList.put(propertyValue, ObjectList);
            }
        }
        return property_ObjectList;
    }
    
}
