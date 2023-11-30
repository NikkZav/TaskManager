package org.leti.oop.kursach;

import com.openhtmltopdf.DOMBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Пример с сайта develnotes.org
 * Печать PDF документа на основе HTML шаблона
 * @author Alex. Dl.
 */
public class HTMLPrintUtil {

    private static final String DEFAULT_FONT_NAME = "Open Sans";
    private static final String DEFAULT_FONT_PATH = "/fonts/open_sans.ttf";
    private static final String REPORTS_FOLDER = "/reports/";
    private static final String ENCODING = "UTF-8";
    private static final String ROW_TEMPLATE_CLASS = "row-template";

    /**
     * Распечатать PDF документ, используя HTML шаблон reportName, заполнив его
     * данными data
     *
     * @param reportTemplate Имя файла шаблона
     * @param data       Данные для заполнения PDF отчета. Значения в {@link java.util.Map} data могут быть следущих типов:
     *                   1. {@link java.lang.String} - В этом случае значение будет записано в HTML тег с id,
     *                   соответствующим ключу в {@link java.util.Map} data.
     *                   2. {@link java.util.List<java.util.Map<String,String>} -  Список строк таблицы.
     *                   В этом случае таблица будет записана в HTML тег table, с id, соответствующим ключу в {@link java.util.Map} data.
     *                   Строки таблицы представлены в виде объекта типа {@link java.util.Map<String,String>} ,
     *                   где в качестве ключа указывается CSS класс
     *                   столбца, а в качестве значения - содержимое столбца.
     * @return
     */
    public static byte[] printPdfReport(String reportTemplate, Map<String, Object> data)
            throws Exception {

        Document report = fillReportTemplate(reportTemplate, data);
        org.w3c.dom.Document w3cDoc = DOMBuilder.jsoup2DOM(report);

        return buildPdfReportFromDocument(w3cDoc);
    }

    private static Document fillReportTemplate(String reportName, Map<String, Object> data)
            throws IOException {

        Document reportTemplate = getReportTemplate(reportName);

        data.keySet().forEach(key -> {

            Object value = data.get(key);

            if (value instanceof String) {
                reportTemplate.select("#" + key).html((String) data.get(key));

            } else if (value instanceof List) {

                Element targetTable = reportTemplate.select("table#"+key).first();
                if (targetTable != null)
                    fillTableByData(targetTable, (List<Map<String, String>>) value);
            }
        });

        return reportTemplate;
    }

    private static void fillTableByData(Element targetHtmlTable, List<Map<String, String>> data) {

        data.forEach(map -> {

            Element row = targetHtmlTable.getElementsByClass(ROW_TEMPLATE_CLASS).first().clone();

            row.removeClass(ROW_TEMPLATE_CLASS);

            map.keySet().forEach(mapKey -> {
                Element column = row.select("." + mapKey).first();
                column.html(map.get(mapKey));
            });

            targetHtmlTable.append(row.html());

        });

        targetHtmlTable.getElementsByClass(ROW_TEMPLATE_CLASS).remove();
    }

    private static Document getReportTemplate(String reportName) throws IOException {

        InputStream inputStream = HTMLPrintUtil.class.getResourceAsStream(REPORTS_FOLDER + reportName);
        String html = IOUtils.toString(inputStream, ENCODING);
        inputStream.close();

        return Jsoup.parse(html);
    }

    private static byte[] buildPdfReportFromDocument(org.w3c.dom.Document w3cDoc)
            throws Exception {

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withW3cDocument(w3cDoc, w3cDoc.getBaseURI());
        builder.useFont(() -> HTMLPrintUtil.class.
                getResourceAsStream(DEFAULT_FONT_PATH), DEFAULT_FONT_NAME);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        builder.toStream(out);
        builder.run();
        out.close();

        return out.toByteArray();
    }
}
