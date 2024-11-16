package app.db.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PdfExporter {

    public static void exportToPdf(List<?> list, String dest) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Table Data"));

        if (!list.isEmpty()) {
            var firstObject = list.getFirst();
            Field[] fields = firstObject.getClass().getDeclaredFields();

            var builder = new StringBuilder();
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    builder.append(field.getName()).append("\t\t\t");
                }
            }
            document.add(new Paragraph(builder.toString()));
            document.add(new Paragraph(""));
        }

        for (var obj : list) {
            StringBuilder row = new StringBuilder();
            Field[] fields = obj.getClass().getDeclaredFields();
            List<Field> filteredFields = new ArrayList<>();

            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    filteredFields.add(field);
                }
            }
            for (Field field : filteredFields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    row.append(value).append("\t\t\t");
                } catch (IllegalAccessException e) {
                    e.printStackTrace(System.out);
                }
            }
            document.add(new Paragraph(row.toString().trim()));
        }

        document.close();
    }
}