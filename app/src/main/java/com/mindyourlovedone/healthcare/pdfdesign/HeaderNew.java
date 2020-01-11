package com.mindyourlovedone.healthcare.pdfdesign;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfShading;
import com.itextpdf.text.pdf.PdfShadingPattern;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.ShadingColor;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Class: HeaderNew
 * Screen: Header Footer of PDF
 * A class that manages pdf creation of Header Footer of PDF
 */
public class HeaderNew {

    public static Font GrayFont, GrayTitleFont, GreenFont, BlackFont, FooterFont, CompFont;

    //Set Font
    public static void HeaderNewfont() {
        //nikita - new code for font
       try {
            BaseFont base = BaseFont.createFont("assets/Lato-Regular.ttf", "UTF-8",BaseFont.EMBEDDED);
            GrayFont = new Font(base, 12,Font.NORMAL);
            GrayTitleFont = new Font(base, 12, Font.NORMAL);
            GreenFont = new Font(base, 20, Font.BOLD);
            BlackFont = new Font(base, 19, Font.NORMAL);
            FooterFont = new Font(base, 12, Font.NORMAL);
            CompFont = new Font(base, 13, Font.NORMAL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static Document document;
    public static float[] widths = {0.15f, 0.85f};
    public static PdfPTable table;
    public static String headertext,title;
    public static String pathimg;
    public static PdfWriter writer;
    private static PdfPCell cell;
    Image logostream = null;
    Image calendarstrem= null;
    Image profilestream= null;
    Image calendarWite= null;
    Image profileWite= null;
    /**
     * This function drow a border for all side
     *
     * @param cb =PdfContentByte
     */
    public static void drowBorder(PdfContentByte cb) {
        cb.setLineWidth(2.0f); // Make a bit thicker than 1.0 default
        cb.setColorStroke(BaseColor.LIGHT_GRAY);
//        cb.setRGBColorStroke(75, 47, 19);
        /**
         * left
         */
        cb.moveTo(20, 20);
        cb.lineTo(20, 825);
        /**
         * Right
         */
        cb.moveTo(575, 825);
        cb.lineTo(575, 20);

        /**
         * top
         */
        cb.moveTo(20, 824);
        cb.lineTo(575, 824);
        /**
         * Bottom
         */
        cb.moveTo(20, 21);
        cb.lineTo(575, 21);

        cb.stroke();
    }

    public static void addImage(String path) {
        Image image = null;
        try {
            // get input stream
            InputStream ims = new FileInputStream(path);
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image = Image.getInstance(stream.toByteArray());

            image.setAlignment(Element.ALIGN_RIGHT);
            image.scaleAbsoluteHeight(40);
            image.scaleAbsoluteWidth(20);
            image.scalePercent(50);
            image.setAbsolutePosition(50, 720);
            image.scaleAbsolute(59f, 59f);
            document.add(image);


        } catch (BadElementException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public static void addPhoto(byte[] photo) {
        Image image = null;
        try {
            // get input stream
           /* InputStream ims = new FileInputStream(path);
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);*/
            image = Image.getInstance(photo);

            image.setAlignment(Element.ALIGN_RIGHT);
            image.scaleAbsoluteHeight(50);
            image.scaleAbsoluteWidth(20);
            image.scalePercent(50);
            image.setAbsolutePosition(50, 720);
            image.scaleAbsolute(59f, 59f);
            document.add(image);


        } catch (BadElementException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    /**
     * add paragraph
     */

    public static void addParagraph(String[][] data) {
        HeaderNewfont();
        BlackFont.setColor(00, 00, 00);
        for (int i = 0; i < data.length; i++) {

            try {
                Paragraph p = new Paragraph(data[i][0] + ":" + data[i][1],
                        BlackFont);
                document.add(p);

            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */

    public static void addParagraph(String data) {
        HeaderNewfont();
        BlackFont.setColor(00, 00, 00);

        try {
            Paragraph p = new Paragraph(data, BlackFont);
            document.add(p);

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * add underline with text (chank)
     */

    public static void addChank(String chunk) {
        HeaderNewfont();
        BlackFont.setColor(102, 204, 0);//255, 99, 26);
        BlackFont.setStyle(Font.BOLD);
        Chunk underline = new Chunk(chunk, BlackFont);
//        underline.setUnderline(0.1f, -2f); // 0.1 thick, -2 y-location
        Paragraph p = new Paragraph(underline);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        try {
            document.add(p);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static Image addProfile(InputStream path) {
        Image image = null;
        try {
            // get input stream
            InputStream ims = path;
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 40, stream);
            image = Image.getInstance(stream.toByteArray());

            image.setAlignment(Element.ALIGN_RIGHT);
            image.scaleAbsoluteHeight(40);
            image.scaleAbsoluteWidth(20);
            image.scalePercent(50);
            image.setAbsolutePosition(50, 720);
            image.scaleAbsolute(59f, 59f);
            //  document.add(image);
            return image;


        } catch (BadElementException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return image;
    }

    public static void addNewChank(String chunk, Image pps) {

        HeaderNewfont();
        BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
        BlackFont.setSize(12);
        BlackFont.setStyle(Font.BOLD);

        Image images=pps;
        images.scaleAbsolute(25f, 25f);
        Paragraph p;
        Phrase pp ;
        p = new Paragraph();
        pp = new Phrase();

        images.setAlignment(Image.ALIGN_CENTER);
        p.setIndentationLeft(2f);
        //Add Imae
        Chunk cf;
            cf = new Chunk(images, 0, -8, true);

        p.add(cf);
        //Add Space between imae and Text
        Chunk underlined = new Chunk("  ", BlackFont);
        pp.add(underlined);
        p.add(pp);

        pp = new Phrase();
        Chunk underline = new Chunk(chunk, BlackFont);
        pp.add(underline);
        p.add(pp);
        p.setAlignment(Element.ALIGN_LEFT);

        try {
            document.add(p);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static Image addSectionProfile(String path) {
        Image image = null;
        try {
            // get input stream
            InputStream ims = new FileInputStream(path);
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 40, stream);
            image = Image.getInstance(stream.toByteArray());

            return image;


        } catch (BadElementException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return image;
    }

    public static void addImgChank(String chunk, String s) {
        HeaderNewfont();
        BlackFont.setColor(102, 204, 0);//255, 99, 26);
        BlackFont.setStyle(Font.BOLD);
        Chunk underline = new Chunk(chunk, BlackFont);
//        underline.setUnderline(0.1f, -2f); // 0.1 thick, -2 y-location
        Image images = addSectionProfile("mylopdf.PNG");
        images.scaleAbsoluteHeight(50);
        images.scaleAbsoluteWidth(50);
        images.scalePercent(10);
        images.setAbsolutePosition(20, 120);
        images.scaleAbsolute(30f, 30f);

        Paragraph p = new Paragraph();
        Phrase pp = new Phrase();
        p.add(new Chunk(images, 0, 0));
        pp.add(chunk);
        p.add(pp);
        p.setAlignment(Element.ALIGN_CENTER);

        p.add(pp);

        try {
            document.add(p);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void addCompany(String chunk) {
        HeaderNewfont();
        CompFont.setColor(102, 204, 0);//255, 99, 26);
        Chunk underline = new Chunk(chunk, CompFont);
//        underline.setUnderline(0.1f, -2f); // 0.1 thick, -2 y-location
        Paragraph p = new Paragraph(underline);
        p.setAlignment(Paragraph.ALIGN_LEFT);

        try {
            document.add(p);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void cellDesignbroadline(PdfPCell cell1, PdfPTable table1, String field, String value) {
        HeaderNewfont();
        BlackFont.setColor(00, 00, 00);//102, 204, 0);
        BlackFont.setStyle(Font.BOLD);
        BlackFont.setSize(11);

        GrayFont.setColor(WebColors.getRGBColor("#747474"));
        GrayFont.setStyle(Font.BOLD);
        GrayFont.setSize(11);

        GrayTitleFont.setColor(WebColors.getRGBColor("#747474"));
        GrayTitleFont.setSize(10);

        Paragraph f;
        Paragraph f1;
        Chunk chunk;
        f = new Paragraph(field, GrayTitleFont);
        //  f1 = new Paragraph();
        if (!value.equalsIgnoreCase("Empty")) {
            if (value.equalsIgnoreCase("")) {
                f1 = new Paragraph("Field is Empty", GrayFont);
            } else {
                f1 = new Paragraph(value, BlackFont);
            }
            //f1 = new Paragraph(chunk);
            f1.setSpacingBefore(-3);

            cell1.addElement(f);
            cell1.addElement(f1);
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            cell1.setPaddingTop(5);
            cell1.setPaddingLeft(16);
            cell1.setPaddingRight(16);
            // cell1.setPaddingBottom(2);
            cell1.setVerticalAlignment(Element.ALIGN_TOP);
            // cell1.setLeading(8,1);
            cell1.setUseDescender(true);
            cell1.setUseAscender(true);

            Paragraph k1;
            LineSeparator linek;
            k1 = new Paragraph(" ");
            k1.setAlignment(Element.ALIGN_MIDDLE);
            linek = new LineSeparator();
            k1.setSpacingBefore(-7);
            // k1.setSpacingAfter(2);
            linek.setLineColor(WebColors.getRGBColor("#D6D6D6"));
            linek.setLineWidth(3.5f);
            //
            // linek.setOffset(-5);
            //  k1.setIndentationLeft(1);
            //  k1.setIndentationRight(4);
            k1.add(linek);
            cell1.addElement(k1);
        } else {
            // chunk = new Chunk("", GrayFont);
            f1 = new Paragraph("", GrayFont);
            f1.setSpacingBefore(-3);
            cell1.addElement(f);
            cell1.addElement(f1);
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            cell1.setPaddingTop(5);
            cell1.setPaddingLeft(16);
            cell1.setPaddingRight(16);
            // cell1.setPaddingBottom(2);
            cell1.setVerticalAlignment(Element.ALIGN_TOP);
            // cell1.setLeading(8,1);
            cell1.setUseDescender(true);
            cell1.setUseAscender(true);

           /* Paragraph k1;
            LineSeparator linek;
            k1 = new Paragraph(" ");
            k1.setAlignment(Element.ALIGN_MIDDLE);
            linek = new LineSeparator();
            k1.setSpacingBefore(-7);
            linek.setLineColor(WebColors.getRGBColor("#D6D6D6"));
            linek.setLineWidth(1.5f);
            k1.add(linek);
            cell1.addElement(k1);*/
        }


    }


    public static void cellDesignNoline(PdfPCell cell1, PdfPTable table1, String field, String value) {
        HeaderNewfont();
        BlackFont.setColor(00, 00, 00);//102, 204, 0);
        BlackFont.setStyle(Font.BOLD);
        BlackFont.setSize(11);

        GrayFont.setColor(WebColors.getRGBColor("#747474"));
        GrayFont.setStyle(Font.BOLD);
        GrayFont.setSize(11);

        GrayTitleFont.setColor(WebColors.getRGBColor("#747474"));
        GrayTitleFont.setSize(10);

        Paragraph f;
        Paragraph f1;
        Chunk chunk;
        f = new Paragraph(field, GrayTitleFont);
        //  f1 = new Paragraph();
        if (!value.equalsIgnoreCase("Empty")) {
            if (value.equalsIgnoreCase("")) {
                f1 = new Paragraph("Field is Empty", GrayFont);
            } else {
                f1 = new Paragraph(value, BlackFont);
            }
            //f1 = new Paragraph(chunk);
            f1.setSpacingBefore(-3);

            cell1.addElement(f);
            cell1.addElement(f1);
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            cell1.setPaddingTop(5);
            cell1.setPaddingLeft(14);
            cell1.setPaddingRight(14);
            // cell1.setPaddingBottom(2);
            cell1.setVerticalAlignment(Element.ALIGN_TOP);
            // cell1.setLeading(8,1);
            cell1.setUseDescender(true);
            cell1.setUseAscender(true);

            Paragraph k1;
            LineSeparator linek;
            k1 = new Paragraph(" ");
            k1.setAlignment(Element.ALIGN_MIDDLE);
            linek = new LineSeparator();
            k1.setSpacingBefore(-7);
            // k1.setSpacingAfter(2);
            linek.setLineColor(WebColors.getRGBColor("#FFFFFF"));
            linek.setLineWidth(1.5f);
            //
            // linek.setOffset(-5);
            //  k1.setIndentationLeft(1);
            //  k1.setIndentationRight(4);
            k1.add(linek);
            cell1.addElement(k1);
        } else {
            // chunk = new Chunk("", GrayFont);
            f1 = new Paragraph("", GrayFont);
            f1.setSpacingBefore(-3);
            cell1.addElement(f);
            cell1.addElement(f1);
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            cell1.setPaddingTop(5);
            cell1.setPaddingLeft(14);
            cell1.setPaddingRight(14);
            // cell1.setPaddingBottom(2);
            cell1.setVerticalAlignment(Element.ALIGN_TOP);
            // cell1.setLeading(8,1);
            cell1.setUseDescender(true);
            cell1.setUseAscender(true);

           /* Paragraph k1;
            LineSeparator linek;
            k1 = new Paragraph(" ");
            k1.setAlignment(Element.ALIGN_MIDDLE);
            linek = new LineSeparator();
            k1.setSpacingBefore(-7);
            linek.setLineColor(WebColors.getRGBColor("#D6D6D6"));
            linek.setLineWidth(1.5f);
            k1.add(linek);
            cell1.addElement(k1);*/
        }


    }

    public static void cellDesign(PdfPCell cell1, PdfPTable table1, String field, String value) {
        HeaderNewfont();
        BlackFont.setColor(00, 00, 00);//102, 204, 0);
        BlackFont.setStyle(Font.BOLD);
        BlackFont.setSize(11);

        GrayFont.setColor(WebColors.getRGBColor("#747474"));
        GrayFont.setStyle(Font.BOLD);
        GrayFont.setSize(11);

        GrayTitleFont.setColor(WebColors.getRGBColor("#747474"));
        GrayTitleFont.setSize(10);

        Paragraph f;
        Paragraph f1;
        Chunk chunk;
        f = new Paragraph(field, GrayTitleFont);
      //  f1 = new Paragraph();
        if (!value.equalsIgnoreCase("Empty")) {
            if (value.equalsIgnoreCase("")) {
                f1 = new Paragraph("Field is Empty", GrayFont);
            } else {
                f1 = new Paragraph(value, BlackFont);
            }
            //f1 = new Paragraph(chunk);
            f1.setSpacingBefore(-3);

            cell1.addElement(f);
            cell1.addElement(f1);
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            cell1.setPaddingTop(5);
            cell1.setPaddingLeft(14);
            cell1.setPaddingRight(14);
           // cell1.setPaddingBottom(2);
            cell1.setVerticalAlignment(Element.ALIGN_TOP);
           // cell1.setLeading(8,1);
cell1.setUseDescender(true);
cell1.setUseAscender(true);

            Paragraph k1;
            LineSeparator linek;
            k1 = new Paragraph(" ");
            k1.setAlignment(Element.ALIGN_MIDDLE);
            linek = new LineSeparator();
            k1.setSpacingBefore(-7);
           // k1.setSpacingAfter(2);
            linek.setLineColor(WebColors.getRGBColor("#D6D6D6"));
            linek.setLineWidth(1.5f);
           //
            // linek.setOffset(-5);
          //  k1.setIndentationLeft(1);
          //  k1.setIndentationRight(4);
            k1.add(linek);
            cell1.addElement(k1);
        } else {
           // chunk = new Chunk("", GrayFont);
            f1 = new Paragraph("", GrayFont);
            f1.setSpacingBefore(-3);
            cell1.addElement(f);
            cell1.addElement(f1);
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            cell1.setPaddingTop(5);
            cell1.setPaddingLeft(14);
            cell1.setPaddingRight(14);
            // cell1.setPaddingBottom(2);
            cell1.setVerticalAlignment(Element.ALIGN_TOP);
            // cell1.setLeading(8,1);
            cell1.setUseDescender(true);
            cell1.setUseAscender(true);

           /* Paragraph k1;
            LineSeparator linek;
            k1 = new Paragraph(" ");
            k1.setAlignment(Element.ALIGN_MIDDLE);
            linek = new LineSeparator();
            k1.setSpacingBefore(-7);
            linek.setLineColor(WebColors.getRGBColor("#D6D6D6"));
            linek.setLineWidth(1.5f);
            k1.add(linek);
            cell1.addElement(k1);*/
        }


    }

    /**
     * http://www.geek-tutorials.com/java/itext/itext_table.php
     * http://www.java2s
     * .com/Code/Java/PDF-RTF/SpecificCellwithDifferentWidth.htm
     * http://www.java2s.com/Code/Java/PDF-RTF/LockingtableCellWidths.htm
     *
     * @param data
     * @throws DocumentException
     * @throws BadElementException
     * @throws IOException
     */
    public static void addTable(String data) {
        HeaderNewfont();
        cell = new PdfPCell(new Phrase(data, BlackFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

    }

    public static void addEmptyLine(int number) {
        for (int i = 0; i < number; i++) {
            try {
                document.add(new Paragraph(" "));
            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void addusereNameChank(String username) {
        HeaderNewfont();

        BlackFont.setColor(WebColors.getRGBColor("#ffffff"));//255, 99, 26);
        BlackFont.setStyle(Font.BOLD);
        BlackFont.setSize(12);


        PdfShading shading = (PdfShading)PdfShading.simpleAxial(writer, 0, PageSize.A4.getWidth(), 500, PageSize.A4.getWidth(), WebColors.getRGBColor("#A3D07D"), WebColors.getRGBColor("#2EACDF"));

        //Create a pattern from our shading object
        PdfShadingPattern pattern = new PdfShadingPattern(shading);

        //Create a color from our patter
        ShadingColor color = new ShadingColor(pattern);


        PdfPTable footer = new PdfPTable(1);
        footer.setTotalWidth(PageSize.A4.getWidth());
        footer.setLockedWidth(true);

        footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        footer.getDefaultCell()
                .setHorizontalAlignment(Element.ALIGN_CENTER);
        footer.getDefaultCell()
                .setVerticalAlignment(Element.ALIGN_MIDDLE);
        footer.getDefaultCell().setBackgroundColor(color);
        footer.getDefaultCell().setPaddingTop(16);
        footer.getDefaultCell().setPaddingBottom(8);

        Paragraph p1 = new Paragraph(username, BlackFont);

        footer.addCell(p1);

        try {
            document.add(footer);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void bottomLineSpace(PdfPCell cell1) {
        Paragraph k1 = new Paragraph(" ");
        k1.setSpacingAfter(-5);
        cell1.addElement(k1);
    }

    public static void addDottedLine(PdfPCell cell1) {
        Paragraph p1;
        DottedLineSeparator line1;
        cell1.setColspan(2);
        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
        cell1.setPaddingLeft(14);
        cell1.setPaddingRight(14);
       // cell1.setPaddingTop(2);
        cell1.setPaddingBottom(10);
        cell1.setVerticalAlignment(Element.ALIGN_TOP);

        p1 = new Paragraph(" ");
        CustomDashedLineSeparator customLine= new CustomDashedLineSeparator();
        customLine.setDash(5);
        customLine.setGap(4);
        customLine.setLineWidth(1.5f);
        customLine.setLineColor(WebColors.getRGBColor("#747474"));
        p1.add(customLine);

        cell1.addElement(customLine);
    }

    /**
     * Creates a PDF document.
     *
     * @throws DocumentException
     * @throws IOException
     * @throws SQLException
     */
    public void createPdfHeader(String RESULT, String header) {
        Rectangle pageSize = new Rectangle(PageSize.A4);
        pageSize.setBackgroundColor(WebColors.getRGBColor("#F3F3F3"));
        document = new Document(pageSize, 20, 20, 40, 40);

        try {
            headertext = header;

            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    RESULT));
            Background event = new Background();
            writer.setPageEvent(event);
            document.open();

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void createPdfHeaders(String RESULT, String header, String s, Image logostream, Image calendarstrem, Image profilestream, String personal_profile, Image calendarWite, Image profileWite) {
        Rectangle pageSize = new Rectangle(PageSize.A4);
        pageSize.setBackgroundColor(WebColors.getRGBColor("#F3F3F3"));
        document = new Document(pageSize, 20, 20, 40, 30);

        try {
            headertext = header;
            title=personal_profile;
            this.logostream=logostream;
            this.calendarstrem=calendarstrem;
            this.profilestream=profilestream;
            this.calendarWite=calendarWite;
            this.profileWite=profileWite;
            pathimg = s;
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    RESULT));
            Background event = new Background();
            writer.setPageEvent(event);
            document.open();

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public class Background extends PdfPageEventHelper {
        protected PdfPTable header;
        protected PdfPTable footer;

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            HeaderNewfont();
            BlackFont.setSize(9.5f);
            BlackFont.setStyle(Font.BOLD);
            PdfContentByte cby = writer.getDirectContent();
            PdfContentByte cb = writer.getDirectContent();

            //--Outline BOrder
            // drowBorder(cby);
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
            String formattedDate = df.format(c.getTime());
  //-------------eader--------------------------------------------------------------------------
            if (writer.getPageNumber()==1) {
                header = new PdfPTable(3);
                header.setTotalWidth(PageSize.A4.getWidth());
                header.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                PdfPCell cells = new PdfPCell();

                //---image logo
                Image image = logostream;
                image.scaleAbsoluteHeight(10);
                image.scaleAbsoluteWidth(50);
                image.scalePercent(10);
                image.setAbsolutePosition(20, 220);
                image.scaleAbsolute(100f, 30f);


                Image imagesf = calendarstrem;
                imagesf.scaleAbsolute(25f, 25f);

                Image imagedef = profilestream;
                imagedef.scaleAbsolute(25f, 25f);

                Image imaged = null;
                Image clipped = null;

                try {
                    imaged = Image.getInstance(pathimg);//addProfile(pathimg);//
                } catch (BadElementException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (imaged != null) {
                    // imaged.scaleAbsolute(25f, 25f);
                    float w = imaged.getScaledWidth();
                    float h = imaged.getScaledHeight();
                    float bitmapRatio = (float) w / (float) h;
                    if (bitmapRatio > 1) {
                        w = 900;
                        h = (int) (w / bitmapRatio);
                    } else {
                        h = 900;
                        w = (int) (h * bitmapRatio);
                    }

                    PdfTemplate t = PdfTemplate.createTemplate(writer, w, h);
                    t.ellipse(0, 0, w, h);
                    t.clip();
                    t.newPath();
                    try {
                        t.addImage(imaged, w, 0, 0, h, 0, 0);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }

                    try {
                        clipped = Image.getInstance(t);

                    } catch (BadElementException e) {
                        e.printStackTrace();
                    }
                    clipped.scaleAbsolute(25f, 25f);

                } else {

                }
                //-- Cell 1--------------------------------------------------------------------------
                cells = new PdfPCell();
                cells.setBorder(Rectangle.NO_BORDER);
                cells.setHorizontalAlignment(Element.ALIGN_LEFT);
                cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells.setBackgroundColor(WebColors.getRGBColor("#FFFFFF"));
                cells.setPaddingLeft(20);
                cells.setPaddingRight(20);
                cells.setPaddingTop(7);
                cells.setPaddingBottom(7);
                cells.setUseAscender(true);
                cells.setUseDescender(true);

                Paragraph p;
                Phrase pp;
                p = new Paragraph();
                pp = new Phrase();

                imagesf.setAlignment(Image.ALIGN_CENTER);
                p.setIndentationLeft(2f);
                //Add Imae
                Chunk cf;
                if (imaged != null) {
                    cf = new Chunk(clipped, 0, -8, true);
                } else {
                    cf = new Chunk(imagedef, 0, -8, true);
                }
                p.add(cf);
                //Add Space between imae and Text
                Chunk underlined = new Chunk("  ", BlackFont);
                pp.add(underlined);
                p.add(pp);

                pp = new Phrase();
                Chunk underline = new Chunk(headertext, BlackFont);
                pp.add(underline);
                p.add(pp);
                p.setAlignment(Element.ALIGN_LEFT);
                cells.addElement(p);
                cells.setUseAscender(true);
                cells.setUseDescender(true);
                header.addCell(cells);

                //--- Cell 2-------------------------------------------------------------------------
                cells = new PdfPCell(image, false);
                cells.setBackgroundColor(WebColors.getRGBColor("#FFFFFF"));
                cells.setBorder(Rectangle.NO_BORDER);
                cells.setHorizontalAlignment(Element.ALIGN_CENTER);
                cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells.setPaddingTop(10);
                cells.setPaddingBottom(10);
                cells.setUseAscender(true);
                cells.setUseDescender(true);
                //cells.addElement(image);
                header.addCell(cells);


                //-- CEll 3--------------------------------------------------------------------------
                cells = new PdfPCell();
                cells.setBackgroundColor(WebColors.getRGBColor("#FFFFFF"));
                cells.setBorder(Rectangle.NO_BORDER);
                cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells.setPaddingLeft(20);
                cells.setPaddingRight(20);
                cells.setPaddingTop(7);
                cells.setPaddingBottom(7);
                cells.setUseAscender(true);
                cells.setUseDescender(true);

                p = new Paragraph();
                pp = new Phrase();
                imagesf.setAlignment(Image.ALIGN_CENTER);
                p.setIndentationLeft(2f);
                //Add Imae
                Chunk cfd = new Chunk(imagesf, 0, -8, true);
                p.add(cfd);
                //Add Space between imae and Text
                Chunk underlinedn = new Chunk("  ", BlackFont);
                pp.add(underlinedn);
                p.add(pp);

                pp = new Phrase();

                //Add Text
                Chunk underlines = new Chunk(formattedDate, BlackFont);
                pp.add(underlines);

                p.add(pp);
                p.setAlignment(Element.ALIGN_RIGHT);
                cells.addElement(p);
                header.addCell(cells);

                header.writeSelectedRows(
                        0,
                        -1,
                        0, document.top()+40, cb);
            }

else {
                HeaderNewfont();
                BlackFont.setColor(WebColors.getRGBColor("#ffffff"));//255, 99, 26);
                BlackFont.setStyle(Font.BOLD);
                BlackFont.setSize(12);

                PdfShading shading = (PdfShading)PdfShading.simpleAxial(writer, 0, PageSize.A4.getWidth(), 500, PageSize.A4.getWidth(), WebColors.getRGBColor("#A3D07D"), WebColors.getRGBColor("#2EACDF"));
                //Create a pattern from our shading object
                PdfShadingPattern pattern = new PdfShadingPattern(shading);
                //Create a color from our patter
                ShadingColor color = new ShadingColor(pattern);
                header = new PdfPTable(3);
                header.setTotalWidth(PageSize.A4.getWidth());
                header.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                PdfPCell cells = new PdfPCell();

                //---image logo
                Image image = logostream;
                image.scaleAbsoluteHeight(10);
                image.scaleAbsoluteWidth(50);
                image.scalePercent(10);
                image.setAbsolutePosition(20, 220);
                image.scaleAbsolute(100f, 30f);


                Image imagesf = calendarWite;
                imagesf.scaleAbsolute(25f, 25f);

                Image imagedef = profileWite;
                imagedef.scaleAbsolute(25f, 25f);

                Image imaged = null;
                Image clipped = null;

                try {
                    imaged = Image.getInstance(pathimg);//addProfile(pathimg);//
                } catch (BadElementException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (imaged != null) {
                    // imaged.scaleAbsolute(25f, 25f);
                    float w = imaged.getScaledWidth();
                    float h = imaged.getScaledHeight();
                    float bitmapRatio = (float) w / (float) h;
                    if (bitmapRatio > 1) {
                        w = 900;
                        h = (int) (w / bitmapRatio);
                    } else {
                        h = 900;
                        w = (int) (h * bitmapRatio);
                    }

                    PdfTemplate t = PdfTemplate.createTemplate(writer, w, h);
                    t.ellipse(0, 0, w, h);
                    t.clip();
                    t.newPath();
                    try {
                        t.addImage(imaged, w, 0, 0, h, 0, 0);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }

                    try {
                        clipped = Image.getInstance(t);

                    } catch (BadElementException e) {
                        e.printStackTrace();
                    }
                    clipped.scaleAbsolute(25f, 25f);

                } else {

                }
                //-- Cell 1--------------------------------------------------------------------------
                cells = new PdfPCell();
                cells.setBorder(Rectangle.NO_BORDER);
                cells.setHorizontalAlignment(Element.ALIGN_LEFT);
                cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells.setBackgroundColor(color);
                cells.setPaddingLeft(20);
                cells.setPaddingRight(20);
                cells.setPaddingTop(7);
                cells.setPaddingBottom(7);
                cells.setUseAscender(true);
                cells.setUseDescender(true);

                Paragraph p;
                Phrase pp;
                p = new Paragraph();
                pp = new Phrase();

                imagesf.setAlignment(Image.ALIGN_CENTER);
                p.setIndentationLeft(2f);
                //Add Imae
                Chunk cf;
                if (imaged != null) {
                    cf = new Chunk(clipped, 0, -8, true);
                } else {
                    cf = new Chunk(imagedef, 0, -8, true);
                }
                p.add(cf);
                //Add Space between imae and Text
                Chunk underlined = new Chunk("  ", BlackFont);
                pp.add(underlined);
                p.add(pp);

                pp = new Phrase();
                Chunk underline = new Chunk(headertext, BlackFont);
                pp.add(underline);
                p.add(pp);
                p.setAlignment(Element.ALIGN_LEFT);
                cells.addElement(p);
                cells.setUseAscender(true);
                cells.setUseDescender(true);
                header.addCell(cells);

                //--- Cell 2-------------------------------------------------------------------------
                cells = new PdfPCell();
                p = new Paragraph(new Phrase(title));

                cells.setBackgroundColor(color);
                cells.setBorder(Rectangle.NO_BORDER);
                cells.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells.setPaddingTop(10);
                cells.setPaddingBottom(10);
                cells.setUseAscender(true);
                cells.setUseDescender(true);

                p.setFont(BlackFont);
                p.setAlignment(Element.ALIGN_CENTER);
                cells.addElement(p);
                header.addCell(cells);


                //-- CEll 3--------------------------------------------------------------------------
                cells = new PdfPCell();
                cells.setBackgroundColor(color);
                cells.setBorder(Rectangle.NO_BORDER);
                cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells.setPaddingLeft(20);
                cells.setPaddingRight(20);
                cells.setPaddingTop(7);
                cells.setPaddingBottom(7);
                cells.setUseAscender(true);
                cells.setUseDescender(true);

                p = new Paragraph();
                pp = new Phrase();
                imagesf.setAlignment(Image.ALIGN_CENTER);
                p.setIndentationLeft(2f);
                //Add Imae
                Chunk cfd = new Chunk(imagesf, 0, -8, true);
                p.add(cfd);
                //Add Space between imae and Text
                Chunk underlinedn = new Chunk("  ", BlackFont);
                pp.add(underlinedn);
                p.add(pp);

                pp = new Phrase();

                //Add Text
                Chunk underlines = new Chunk(formattedDate, BlackFont);
                pp.add(underlines);

                p.add(pp);
                p.setAlignment(Element.ALIGN_RIGHT);
                cells.addElement(p);
                header.addCell(cells);
//
                header.writeSelectedRows(
                        0,
                        -1,
                        0, document.top()+40, cb);

            }
//--------------------------Footer-----------------------------------------------------------------------------------
            FooterFont.setColor(WebColors.getRGBColor("#A5A5A5"));//255, 99, 26);
            FooterFont.setStyle(Font.BOLD);
            FooterFont.setSize(9);

            footer = new PdfPTable(2);
            footer.setTotalWidth(PageSize.A4.getWidth());
            footer.setLockedWidth(true);
            PdfPCell cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("WWW.MINDYOUR-LOVEDONES.COM", FooterFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#4B4B4B"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPaddingTop(13);
            cell.setPaddingBottom(10);
            cell.setPaddingRight(10);
            cell.setPaddingLeft(10);
            cell.setUseAscender(true);
            cell.setUseDescender(true);
            footer.addCell(cell);

            cell = new PdfPCell(new Phrase("PAGE " + String.format(""
                    + (writer.getPageNumber())), FooterFont));
            cell.setBackgroundColor(WebColors.getRGBColor("#4B4B4B"));
            cell.setPaddingTop(13);
            cell.setPaddingBottom(10);
            cell.setPaddingRight(10);
            cell.setPaddingLeft(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setUseAscender(true);
            cell.setUseDescender(true);
            footer.addCell(cell);

            /*footer.addCell(new Phrase(String.format(""
                    + (writer.getPageNumber()))));*/


           /* footer.writeSelectedRows(
                    0,
                    -1,
                    (document.right() - document.left() - 570) / 2
                            + document.leftMargin(), document.bottom() - 10, cb);*/

            footer.writeSelectedRows(
                    0,
                    -1,
                    0, document.bottom(), cb);

        }
    }

}