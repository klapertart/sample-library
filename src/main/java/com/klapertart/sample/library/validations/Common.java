package com.klapertart.sample.library.validations;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author tritr
 * @since 10/17/2023
 */

@Component
public class Common {
    public  boolean isNumeric(String strNum){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
