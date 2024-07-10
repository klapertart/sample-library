package com.klapertart.sample.library.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tritr
 * @since 10/17/2023
 */

@SpringBootTest(classes = Common.class)
class CommonTest {

    @Autowired
    private Common common;

    @Test
    void testNumeric() {
        String str = "1212dfd1";
        Assertions.assertFalse(common.isNumeric(str));

        String str2 = "1212";
        Assertions.assertTrue(common.isNumeric(str2));
    }
}