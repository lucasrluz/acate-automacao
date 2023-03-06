package com.example;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String COMPANY_ADDRESS_ID = "#page > article > div > div.row > div.right > div.info > address";

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.acate.com.br/associados/associado/?associado_id=228");

        String address = driver.findElement(By.cssSelector(COMPANY_ADDRESS_ID)).getAttribute("innerHTML");

        String city = findCity(address);

        System.out.println(city);

        driver.close();
    }

    public static String findCity(String str) {
        int stringLength = str.length();
        
        for (int index = stringLength; index > 0; index--) {
            if (str.substring(index - 1, index).equals(" ") && index != stringLength) {
                String city = str.substring(index, stringLength - 1);

                return city;
            }
        }

        return "";
    }
}

