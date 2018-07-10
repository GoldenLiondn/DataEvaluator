package com.qualityunit;

import com.qualityunit.beans.DataLine;
import com.qualityunit.beans.QueryLine;
import com.qualityunit.beans.WaitingTimeLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Maksim Ovcharenko
 * 09.07.2018
 */
public class App {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        App app = new App();
        ArrayList<DataLine> inputData = app.getInputData();
        for (int i = 0; i < inputData.size(); i++) {
            if (inputData.get(i) instanceof QueryLine)
                System.out.println(((QueryLine) inputData.get(i)).evaluate(inputData, i));
        }
    }

    /**
     * Method makes list of objects from input strings
     *
     * @return list of instances of DataLine
     */
    private ArrayList<DataLine> getInputData() {
        ArrayList<DataLine> list = new ArrayList<>();
        String inputStrings[];
        while (list.isEmpty()) {
            try {
                inputStrings = getInputStringsFromConsole();
                for (int i = 0; i < Integer.parseInt(inputStrings[0]); i++) {
                    list.add(getDataObjectFromString(inputStrings[i + 1]));
                }
            } catch (IOException e) {
                System.out.println("Sorry! Some Input/Output Error");
                list.clear();
            } catch (InvalidDataInStringException | NumberFormatException e) {
                System.out.println("Incorrect data!");
                list.clear();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return list;
    }

    /**
     * Method reads input lines from console and put them into array
     * @return Array of input strings from console
     * @throws IOException
     * @throws InvalidDataInStringException
     */
    private String[] getInputStringsFromConsole() throws IOException, InvalidDataInStringException {
        String result[];
        System.out.println("Enter input data...");
        int countOfAllLines = Integer.parseInt(reader.readLine());

        //First line contains count S (<= 100.000) of all lines.
        if (countOfAllLines > 100000 || countOfAllLines < 1) {
            throw new InvalidDataInStringException();
        }
        result = new String[countOfAllLines + 1];
        result[0] = String.valueOf(countOfAllLines);
        for (int i = 0; i < countOfAllLines; ) {
            result[i + 1] = reader.readLine();
            if (!result[i + 1].isEmpty()) {
                i++;
            }
        }
        return result;
    }


    /**
     * Method makes an correct instance of DataLine from input string
     *
     * @param inputString input string describing query
     * @return instance of DataLine depending on input string parameters
     * @throws InvalidDataInStringException
     */
    private DataLine getDataObjectFromString(String inputString) throws InvalidDataInStringException, ParseException {
        DataLine result;
        String[] fieldsOfObject = inputString.split(" ");

        //creating correct type of object depending on parameter C/D
        if (fieldsOfObject[0].equals("C"))
            result = new WaitingTimeLine();
        else if (fieldsOfObject[0].equals("D"))
            result = new QueryLine();
        else {
            System.out.println("Error in string: " + inputString);
            throw new InvalidDataInStringException();
        }

        // 0 means *
        //set service_id & variation_id
        if (fieldsOfObject[1].charAt(0) == '*' && fieldsOfObject[1].length() == 1) {
            //if * service_id & variation_id will be 0
        } else if (fieldsOfObject[1].length() == 1 || fieldsOfObject[1].length() == 2) {
            result.setService_id(Integer.parseInt(fieldsOfObject[1]));
        } else if (fieldsOfObject[1].length() == 3 && fieldsOfObject[1].charAt(1) == '.') {
            result.setService_id(Character.getNumericValue(fieldsOfObject[1].charAt(0)));
            result.setVariation_id(Character.getNumericValue(fieldsOfObject[1].charAt(2)));
        } else {
            System.out.println("Error in string: " + inputString);
            throw new InvalidDataInStringException();
        }


        //set question_type_id[.category_id.[sub-category_id]]
        if (fieldsOfObject[2].charAt(0) == '*' && fieldsOfObject[2].length() == 1) {
            //if * question_type_id[.category_id.[sub-category_id]] will be 0
        } else if (fieldsOfObject[2].length() == 1 || fieldsOfObject[2].length() == 2) {
            result.setQuestion_type_id(Integer.parseInt(fieldsOfObject[2]));
        } else {
            String[] temp = fieldsOfObject[2].split("\\.");
            result.setQuestion_type_id(Integer.parseInt(temp[0]));
            result.setCategory_id(Integer.parseInt(temp[1]));
            if (temp.length == 3) {
                result.setSub_category_id(Integer.parseInt(temp[2]));
            } else if (temp.length > 3) {
                System.out.println("Error in string: " + inputString);
                throw new InvalidDataInStringException();
            }
        }

        //checking for errors in input data
        if (result.getService_id() < 0 || result.getService_id() > 10
                || result.getVariation_id() < 0 || result.getVariation_id() > 3
                || result.getQuestion_type_id() < 0 || result.getQuestion_type_id() > 10
                || result.getCategory_id() < 0 || result.getCategory_id() > 20
                || result.getSub_category_id() < 0 || result.getSub_category_id() > 5) {
            System.out.println("Error in string: " + inputString);
            throw new InvalidDataInStringException();
        }

        //set P/N
        if (fieldsOfObject[3].equals("P"))
            result.setFirstAnswer(true);
        else if (fieldsOfObject[3].equals("N"))
            result.setFirstAnswer(false);
        else {
            System.out.println("Error in string: " + inputString);
            throw new InvalidDataInStringException();
        }

        //set date and waiting time to WaitingTimeLine objects
        if (result instanceof WaitingTimeLine) {
            SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
            Date date = ft.parse(fieldsOfObject[4]);
            ((WaitingTimeLine) result).setDate(date);
            ((WaitingTimeLine) result).setWaitingTime(Integer.parseInt(fieldsOfObject[5]));
            //set dateFrom and dateTo to QueryLine objects
        } else if (result instanceof QueryLine) {
            String[] temp = fieldsOfObject[4].split("-");
            SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
            Date dateFrom = ft.parse(temp[0]);
            ((QueryLine) result).setDateFrom(dateFrom);
            if (temp.length > 1) {
                Date dateTo = ft.parse(temp[1]);
                ((QueryLine) result).setDateTo(dateTo);
            }
        }

        return result;
    }
}