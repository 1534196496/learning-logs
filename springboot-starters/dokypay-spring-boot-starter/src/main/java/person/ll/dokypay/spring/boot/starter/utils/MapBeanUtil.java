package person.ll.dokypay.spring.boot.starter.utils;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * map转bean，bean转map
 */
public class MapBeanUtil {

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map<String,String>  object2Map(Object obj) throws IllegalArgumentException, IllegalAccessException{
        if (obj == null) {
            return null;
        }
        if(obj instanceof JSONObject){
            return  JSONObject.toJavaObject((JSONObject) obj, Map.class);
        }

        Map<String, String> map = new HashMap<>();
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.get(obj) !=null){
                map.put(field.getName(), field.get(obj).toString());
            }
        }

        return map;
    }

//    /**
//     * Map转成实体对象
//     *
//     * @param map   实体对象包含属性
//     * @param clazz 实体对象类型
//     * @return
//     */
//    public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
//        if (map == null) {
//            return null;
//        }
//        Object obj = null;
//        try {
//            obj = clazz.newInstance();
//
//            Field[] fields = obj.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                int mod = field.getModifiers();
//                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
//                    continue;
//                }
//                field.setAccessible(true);
//                field.set(obj, map.get(field.getName()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return obj;
//    }
}