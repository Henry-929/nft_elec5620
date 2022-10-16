package com.nft.util;


public class ParamUtil {

    public static Long tradeToLong(Object object){
        try {
            if (object != null && !"".equals(object.toString())) {
                return Long.parseLong(object.toString());
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    public static Integer tradeToInteger(Object object){
        try {
            if(object != null && !"".equals(object.toString())){
                return Integer.parseInt(object.toString());
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    public static Double tradeToDouble(Object object){
        try {
            if(object != null && !"".equals(object.toString())){
                return Double.parseDouble(object.toString());
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

//    public static Date tradeToDate(Object object){
//        try {
//            if(object != null && !"".equals(object.toString())){
//                return new DateConverterConfig().convert(object.toString());
//            }
//        }catch (Exception e){
//            return null;
//        }
//        return null;
//    }

    public static String tradeToString(Object object) {
        try {
            if(object != null && !"".equals(object.toString())){
                return object.toString();
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
