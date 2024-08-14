package com.wakatuts.pages.attributes;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.lang.reflect.Field;

import static com.codeborne.selenide.Selenide.$;

public interface Fillable<T> {

    @Step("Filling up form")
    @SuppressWarnings("unchecked")
    default T populate(Object data) {
       Field[] selectorFields = this.getClass().getDeclaredFields();
       Field[] dataFields = data.getClass().getDeclaredFields();

       try {
           for (Field dataField : dataFields) {
               dataField.setAccessible(true);
               String dataFieldName = dataField.getName();
               for (Field selectorField : selectorFields) {
                   selectorField.setAccessible(true);
                   String selectorFieldName = selectorField.getName();
                   if (selectorFieldName.startsWith(dataFieldName)) {
                       if (selectorFieldName.toLowerCase().contains("input")) {
                           $((By) selectorField.get(this)).setValue((String) dataField.get(data));
                       }
                   }
               }
           }
       } catch (IllegalAccessException e) {
           Assert.fail("Select for data not found!", e);
       }

       return (T) this;
    }

}
