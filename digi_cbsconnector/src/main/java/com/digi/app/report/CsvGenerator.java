package com.digi.app.report;

import com.digi.app.constants.ErrorMessageConstants;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
public class CsvGenerator {
    private CsvGenerator() {
    }

    /**
     * This method generates csv file taking list of object as input
     * The method uses opencsv dependency
     */
    public static void generateCsv(List<?> list, String fileName, HttpServletResponse response) {
        try {
            fileName = fileName + ".csv";
            response.setContentType("text/csv");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileName + "\"");
            response.setContentType("text/csv");
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(response.getWriter())
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            beanToCsv.write(list);
        } catch (Exception e) {
            log.error(ErrorMessageConstants.CSV_FILE_GENERATION_EXCEPTION_MESSAGE + "\n" + e);
        }
    }
}
