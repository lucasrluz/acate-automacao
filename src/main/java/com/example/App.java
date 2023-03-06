package com.example;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class App {
    public static void main(String[] args) {
        String COMPANY_LINK_A_ID = "#page > div.container > div.listing > div:nth-child(";
        String COMPANY_LINK_B_ID = ") > div > div.title > a";
        String COMPANY_NAME_ID = "#page > article > div > div.title > h1";
        String COMPANY_ADDRESS_ID = "#page > article > div > div.row > div.right > div.info > address";
        String COMPANY_TELEPHONE_ID = "#page > article > div > div.row > div.right > div.info > div.phone";
        String COMPANY_EMAIL_ID = "#page > article > div > div.row > div.right > div.info > div.email";
        String COMPANY_SITE_ID = "#page > article > div > div.row > div.right > div.info > div.site > a";

        try {
            // Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
            // Statement statement = connection.createStatement();
            // statement.setQueryTimeout(30);

            // statement.executeUpdate("DROP TABLE IF EXISTS company");
            // statement.executeUpdate("CREATE TABLE company (mainPage STRING, companyPage STRING, name STRING, address STRING, telephone STRING, email STRING, site STRING)");
        
            // statement.executeUpdate("INSERT INTO company VALUES(1, 'EmpresaB')");

            int i = 70;
            while (true) {
                if (i == 200) { // 200
                    break;
                }

                WebDriver mainDriver = new ChromeDriver();

                mainDriver.get("https://www.acate.com.br/associados/page/" + i);

                int ii = 1;
                while (true) {
                    if (ii == 13) { // 13
                        break;
                    }
                    String COMPLET_LINK = COMPANY_LINK_A_ID + ii + COMPANY_LINK_B_ID;

                    String companyLink = mainDriver.findElement(By.cssSelector(COMPLET_LINK)).getAttribute("href");

                    WebDriver companyDriver = new ChromeDriver();

                    companyDriver.get(companyLink);
                    companyDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                    String name = companyDriver.findElement(By.cssSelector(COMPANY_NAME_ID)).getAttribute("innerHTML");
                    String address = companyDriver.findElement(By.cssSelector(COMPANY_ADDRESS_ID)).getAttribute("innerHTML");
                    String telephone = companyDriver.findElement(By.cssSelector(COMPANY_TELEPHONE_ID)).getAttribute("innerHTML");
                    String email = companyDriver.findElement(By.cssSelector(COMPANY_EMAIL_ID)).getAttribute("innerHTML");
                    String site = companyDriver.findElement(By.cssSelector(COMPANY_SITE_ID)).getAttribute("href");

                    String city = findCity(address);

                    System.out.println(" ");
                    // System.out.println(i);
                    // System.out.println(ii);
                    // System.out.println(name.trim());
                    // System.out.println(address.trim());
                    // System.out.println(telephone.trim());
                    // System.out.println(email.trim());
                    // System.out.println(site.trim());
                    
                    
                    BufferedWriter writer = new BufferedWriter(new FileWriter("company.txt", true));
                    
                    String line = String.format("'%d', '%d', '%s', '%s', '%s', '%s', '%s'", i, ii, name.trim(), city, telephone.trim(), email.trim(), site.trim());
                    // String line = String.format("%d", ii);
                    System.out.println(line);
                    
                    writer.write(line);
                    writer.newLine();
                    writer.close();

                    // String sql = String.format("INSERT INTO company VALUES('%d', '%d', '%s', '%s', '%s', '%s', '%s')", i, ii, name.trim(), address.trim(), telephone.trim(), email.trim(), site.trim());
                    // System.out.println(sql);
                    // statement.executeUpdate(sql);

                    companyDriver.close();

                    ii++;
                }

                mainDriver.close();
                i++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String findCity(String address) {
        int stringLength = address.length();
        
        for (int index = stringLength; index > 0; index--) {
            if (address.substring(index - 1, index).equals(" ") && index != stringLength) {
                String city = address.substring(index, stringLength - 1);

                return city;
            }
        }

        return "";
    }
}
