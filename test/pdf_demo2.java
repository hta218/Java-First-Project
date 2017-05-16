
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Huynh
 */
public class pdf_demo2 {

    private static String path = "F:/FirstPdf.pdf";
    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public static void main(String[] args) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // view these below informations in File -> Properties (using Adobe Reader)        
    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private static void addTitlePage(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        // We add one empty line
        addEmptyLine(paragraph, 1);
        // Lets write a big header
        paragraph.add(new Paragraph("Title of the document", titleFont));

        addEmptyLine(paragraph, 1);
        // Will create: Report generated by: _name, _date
        paragraph.add(new Paragraph(
                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), smallBold));
        addEmptyLine(paragraph, 3);
        paragraph.add(new Paragraph(
                "This document describes something which is very important ",
                smallBold));

        addEmptyLine(paragraph, 8);
        paragraph.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement "
                + "or any other agreement with vogella.com ;-).", redFont));

        document.add(paragraph);
        // Start a new page
        document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", titleFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter ex: 1. Chapter 1 or 2. Chapter 2
        Chapter chapter = new Chapter(new Paragraph(anchor), 2);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subSection = chapter.addSection(subPara);
        subSection.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subSection = chapter.addSection(subPara);
        subSection.add(new Paragraph("Paragraph 1"));
        subSection.add(new Paragraph("Paragraph 2"));
        subSection.add(new Paragraph("Paragraph 3"));

        // add a list
        createList(subSection);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subSection.add(paragraph);

        // add a table
        createTable(subSection);

        // now add all this to the document
        document.add(chapter);

        // Next section
        anchor = new Anchor("Second Chapter", titleFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        chapter = new Chapter(new Paragraph(anchor), 2);

        subPara = new Paragraph("Subcategory", subFont);
        subSection = chapter.addSection(subPara);
        subSection.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(chapter);
    }

    private static void createTable(Section section) throws BadElementException {
        // number of table-column = 3
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
        
        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
//        table.setHeaderRows(1);     ??

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        section.add(table);
    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}