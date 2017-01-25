package com.suglob.pharmacy.validation;

import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.service.exception.ServiceCheckException;
import org.junit.Test;
import org.junit.Assert;

public class ValidatorTest {

    @Test (expected = ServiceCheckException.class)
    public void checkDrugCategoryExceptionTest() throws ServiceCheckException{
        Validator.checkDrugCategory("123");
    }

    @Test
    public void checkIntegerTrueTest(){
        Assert.assertTrue(Validator.checkInteger("23", "-5"));
    }

    @Test
    public void checkIntegerFalseTest(){
        Assert.assertFalse(Validator.checkInteger("twenty three"));
    }

    @Test
    public void checkExtendRecipeTest(){
        Assert.assertEquals(MessageConstant.EXTEND_RECIPE_OK, Validator.checkExtendRecipe("qwerty"));
    }
}
