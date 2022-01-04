package one.primedoc.backend.utils;

import one.primedoc.backend.model.BillModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@Service
public class BillGenerator {
    private final static SimpleDateFormat NO_TIME = new SimpleDateFormat("dd.MM.yyyy");

    public String generateHtmlBill(BillModel model){
        try {
        File file = new File("app/bill_sample.html");
        Scanner scanner = null;
            scanner = new Scanner(file);
        StringBuilder fileInString = new StringBuilder();
        while (scanner.hasNextLine()){
            fileInString.append(scanner.nextLine());
        }
            System.out.println(fileInString);
        return String.format(fileInString.toString(),
                NO_TIME.format(new Date()) ,
                model.getOid(),
                model.getFirstname(),
                model.getLastname(),
                model.getSum(),
                model.getTotalSum(),
                model.getLast(),
                model.getAuthCode(),
                model.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
