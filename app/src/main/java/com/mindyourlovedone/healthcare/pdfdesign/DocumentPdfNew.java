package com.mindyourlovedone.healthcare.pdfdesign;

import android.content.Context;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.Document;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by welcome on 12/8/2017.
 */
/**
 * Class: DocumentPdfNew
 * Screen: Document Sections
 * A class that manages pdf creation of Document Section
 */
public class DocumentPdfNew {

    public static ArrayList<String> messageAdvance = new ArrayList<String>();
    public static ArrayList<String> messageOther = new ArrayList<String>();
    public static ArrayList<String> messageRecord = new ArrayList<String>();
    Font BlackFont;

    /**
     * Function: Set Font
     */
    private void DocumentNewFont() {
        try {
        BaseFont base = BaseFont.createFont("assets/Lato-Regular.ttf", "UTF-8",BaseFont.EMBEDDED);
        BlackFont = new Font(base, 19, Font.NORMAL);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    }

    /**
     * Function: Create Advance Directive PDF
     * @param adList
     * @param ppys
     */
    public DocumentPdfNew(ArrayList<Document> adList, Image ppys) {
            DocumentNewFont();
            try {
                // HeaderNew.addEmptyLine(1);
                //  if (i==0) {
                HeaderNew.addNewChank("Advance Directives", ppys);
                messageAdvance.add("Advance Directives");
                HeaderNew.addEmptyLine(1);
                // }

                for (int i = 0; i < adList.size(); i++) {
                    PdfPTable table1;
                    table1 = new PdfPTable(1);
                    table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                    table1.setTableEvent(new RoundedBorder());
                 //   table1.getDefaultCell().setPaddingBottom(15);
                    table1.setKeepTogether(false);
                    table1.setSplitLate(false);
                    table1.setWidthPercentage(100);
                    PdfPCell cell1 = new PdfPCell();
                    cell1.setPaddingTop(10);
                  //  cell1.setPaddingBottom(10);
                    cell1.setBorder(Rectangle.NO_BORDER);
                    cell1.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
                    table1.setKeepTogether(false);
                    table1.setSplitLate(false);

                    PdfPTable table;
                    table = new PdfPTable(2);
                    table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                    table.setKeepTogether(false);
                    table.setSplitLate(false);
                    table.setWidthPercentage(100);


                    PdfPCell cell;
                    Paragraph k1;

                    Document s = adList.get(i);

                    String type = "";
                    String otherdoc = "";
                    if (s.getType() != null) {
                        type = s.getType();
                        if (s.getOtherDoc() != null) {
                            type = type + " - " + s.getOtherDoc();
                        }
                    }
                    // cell = new PdfPCell(new Phrase("Document Description:" + type));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Document Description:", type);
                    table.addCell(cell);

                    messageAdvance.add("Document Description:");
                    messageAdvance.add(type);


                    String person = "";
                    if (s.getPerson() != null) {
                        person = s.getPerson();
                    }
                    // cell = new PdfPCell(new Phrase("Document Received from:" + person));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Document Received from:", person);
                    table.addCell(cell);

                    messageAdvance.add("Document Received from :");
                    messageAdvance.add(person);

                    String file = "";
                    if (s.getName() != null) {
                        file = s.getName();
                    }
                    //  cell = new PdfPCell(new Phrase("Name of File:" + file));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Name of File:", file);
                    table.addCell(cell);


                    messageAdvance.add("Name of File :");
                    messageAdvance.add(file);

                    String date = "";
                    if (s.getDate() != null) {
                        date = s.getDate();
                    }
                    //   cell = new PdfPCell(new Phrase("Document Date:" + date));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Document Date:", date);
                    table.addCell(cell);

                    messageAdvance.add("Document Date :");
                    messageAdvance.add(date);

                    String holder = "";
                    if (s.getHolder() != null) {
                        holder = s.getHolder();
                    }
                    //   cell = new PdfPCell(new Phrase("Distributed to:" + holder));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Distributed to:", holder);
                    table.addCell(cell);

                    messageAdvance.add("Distributed to :");
                    messageAdvance.add(holder);

                    String location = "";
                    if (s.getLocation() != null) {
                        location = s.getLocation();
                    }
                    // cell = new PdfPCell(new Phrase("Location of Original:" + location));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Location of Original:", location);
                    table.addCell(cell);

                    messageAdvance.add("Location of Original :");
                    messageAdvance.add(location);

                    // cell = new PdfPCell(new Phrase(""));
                    String note = "";
                    if (s.getNote() != null) {
                        note = s.getNote();
                    }
                    // cell = new PdfPCell(new Phrase("Location of Original:" + location));
                    cell = new PdfPCell();
                    HeaderNew.cellDesignNoline(cell, table, "Notes:", note);
                    table.addCell(cell);

                    messageAdvance.add("Note :");
                    messageAdvance.add(note);

                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "", "Empty");
                    table.addCell(cell);


                    messageAdvance.add("");
                    messageAdvance.add("");


                    cell1.addElement(table);
                    table1.addCell(cell1);
                    HeaderNew.document.add(table1);
                    HeaderNew.addEmptyLine(1);
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }


    /**
     * Function: Create Oter Document PDF
     * @param otherList
     * @param y
     * @param ppys
     */
    public DocumentPdfNew(ArrayList<Document> otherList, int y, Image ppys) {
            DocumentNewFont();
            try {
                // HeaderNew.addEmptyLine(1);
                //  if (i==0) {
                HeaderNew.addNewChank("Other Documents", ppys);
                messageAdvance.add("Other Documents");
                HeaderNew.addEmptyLine(1);
                // }

                for (int i = 0; i < otherList.size(); i++) {
                    PdfPTable table1;
                    table1 = new PdfPTable(1);
                    table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                    table1.setTableEvent(new RoundedBorder());
                   // table1.getDefaultCell().setPaddingBottom(15);
                    table1.setKeepTogether(false);
                    table1.setSplitLate(false);
                    table1.setWidthPercentage(100);
                    PdfPCell cell1 = new PdfPCell();
                    cell1.setPaddingTop(10);
                    //cell1.setPaddingBottom(10);
                    cell1.setBorder(Rectangle.NO_BORDER);
                    cell1.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
                    table1.setKeepTogether(false);
                    table1.setSplitLate(false);

                    PdfPTable table;
                    table = new PdfPTable(2);
                    table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                    table.setKeepTogether(false);
                    table.setSplitLate(false);
                    table.setWidthPercentage(100);


                    PdfPCell cell;
                    Paragraph k1;

                Document s = otherList.get(i);

                String category = "";
                if (s.getCategory() != null) {
                    category = s.getCategory();
                    if (!s.getOtherCategory().equals("")) {
                        category = category + " - " + s.getOtherCategory();
                    }
                }
               // cell = new PdfPCell(new Phrase("Category:" + category));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Category:", category);
                    table.addCell(cell);

                messageOther.add("Category:");
                messageOther.add(category);

                String type = "";
                if (s.getType() != null) {
                    type = s.getType();
                }
                //cell = new PdfPCell(new Phrase("Document Description:" + type));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Document Description:", type);
                    table.addCell(cell);

                messageOther.add("Document Description:");
                messageOther.add(type);


                String person = "";
                if (s.getPerson() != null) {
                    person = s.getPerson();
                }
                //cell = new PdfPCell(new Phrase("Document Received from:" + person));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Document Received from:", person);
                    table.addCell(cell);

                messageOther.add("Document Received from :");
                messageOther.add(person);

                String file = "";
                if (s.getName() != null) {
                    file = s.getName();
                }
               // cell = new PdfPCell(new Phrase("Name of File:" + file));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Name of File:", file);
                    table.addCell(cell);

                messageOther.add("Name of File :");
                messageOther.add(file);

                String date = "";
                if (s.getDate() != null) {
                    date = s.getDate();
                }
              //  cell = new PdfPCell(new Phrase("Date of Document:" + date));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Date of Document:", date);
                    table.addCell(cell);

                messageOther.add("Date of Document :");
                messageOther.add(date);

                String holder = "";
                if (s.getHolder() != null) {
                    holder = s.getHolder();
                }
               // cell = new PdfPCell(new Phrase("Distributed to:" + holder));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Distributed to:", holder);
                    table.addCell(cell);

                messageOther.add("Distributed to :");
                messageOther.add(holder);

                String location = "";
                if (s.getLocation() != null) {
                    location = s.getLocation();
                }
               // cell = new PdfPCell(new Phrase("Location of Original:" + location));
                    cell = new PdfPCell();
                    HeaderNew.cellDesignNoline(cell, table, "Location of Original:", location);
                    table.addCell(cell);

                messageOther.add("Location of Original :");
                messageOther.add(location);

                    String note = "";
                    if (s.getNote() != null) {
                        note = s.getNote();
                    }
                    // cell = new PdfPCell(new Phrase("Location of Original:" + location));
                    cell = new PdfPCell();
                    HeaderNew.cellDesignNoline(cell, table, "Notes:", note);
                    table.addCell(cell);

                    messageOther.add("Note :");
                    messageOther.add(note);

                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "", "Empty");
                    table.addCell(cell);


                    messageOther.add("");
                    messageOther.add("");


                    cell1.addElement(table);
                    table1.addCell(cell1);
                    HeaderNew.document.add(table1);
                    HeaderNew.addEmptyLine(1);
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
    }

    /**
     * Function: Create Medical Record PDF
     * @param recordList
     * @param record
     * @param ppys
     */
    public DocumentPdfNew(ArrayList<Document> recordList, String record, Image ppys, Context context) {
        Preferences preferences=new Preferences(context);
            DocumentNewFont();
            try {
                // HeaderNew.addEmptyLine(1);
                //  if (i==0) {
                HeaderNew.addNewChank("Medical Records", ppys);
                messageAdvance.add("Medical Records");
                HeaderNew.addEmptyLine(1);
                // }

                for (int i = 0; i < recordList.size(); i++) {
                    PdfPTable table1;
                    table1 = new PdfPTable(1);
                    table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                    table1.setTableEvent(new RoundedBorder());
                   // table1.getDefaultCell().setPaddingBottom(15);
                    table1.setKeepTogether(false);
                    table1.setSplitLate(false);
                    table1.setWidthPercentage(100);
                    PdfPCell cell1 = new PdfPCell();
                    cell1.setPaddingTop(10);
                   // cell1.setPaddingBottom(10);
                    cell1.setBorder(Rectangle.NO_BORDER);
                    cell1.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
                    table1.setKeepTogether(false);
                    table1.setSplitLate(false);

                    PdfPTable table;
                    table = new PdfPTable(2);
                    table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                    table.setKeepTogether(false);
                    table.setSplitLate(false);
                    table.setWidthPercentage(100);


                    PdfPCell cell;
                    Paragraph k1;

                Document s = recordList.get(i);

                String type = "";
                if (s.getType() != null) {
                    type = s.getType();
                }
                //cell = new PdfPCell(new Phrase("Document Description:" + type));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Document Description:", type);
                    table.addCell(cell);

                messageRecord.add("Document Description:");
                messageRecord.add(type);


                String person = "";
                if (s.getPerson() != null) {
                    person = s.getPerson();
                }
                //cell = new PdfPCell(new Phrase("Name on Document:" + person));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Name on Document:", person);
                    table.addCell(cell);

                messageRecord.add("Name on Document :");
                messageRecord.add(person);

                String file = "";
                if (s.getName() != null) {
                    file = s.getName();
                }
               // cell = new PdfPCell(new Phrase("Name of File:" + file));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Name of File:", file);
                    table.addCell(cell);

                messageRecord.add("Name of File :");
                messageRecord.add(file);

                String date = "";
                if (s.getDate() != null) {
                    date = s.getDate();
                }
              //  cell = new PdfPCell(new Phrase("Date of Document:" + date));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Date of Document:", date);
                    table.addCell(cell);

                messageRecord.add("Date of Document :");
                messageRecord.add(date);

                String holder = "";
                if (s.getHolder() != null) {
                    holder = s.getHolder();
                }
              //  cell = new PdfPCell(new Phrase("Distributed to:" + holder));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Distributed to:", holder);
                    table.addCell(cell);

                messageRecord.add("Distributed to :");
                messageRecord.add(holder);

                String location = "";
                if (s.getLocation() != null) {
                    location = s.getLocation();
                }
               // cell = new PdfPCell(new Phrase("Location of Original:" + location));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Location of Original:", location);
                    table.addCell(cell);

                messageRecord.add("Location of Original :");
                messageRecord.add(location);

                String hosp = "";
                if (s.getHospital() != null) {
                    hosp = s.getHospital();
                }
              //  cell = new PdfPCell(new Phrase("Associated Physician or Hospital:" + hosp));
                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "Associated Physician or Hospital:", hosp);
                    table.addCell(cell);

                messageRecord.add("Associated Physician or Hospital :");
                messageRecord.add(hosp);


                String locator = "";
                if (s.getLocator() != null) {
                    locator = s.getLocator();
                }
                    if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(context.getResources().getString(R.string.India)))
                    {
// cell = new PdfPCell(new Phrase("Electronic Health Record (add web address, username and password):" + locator));
                        cell = new PdfPCell();
                        HeaderNew.cellDesign(cell, table, "Document Provider Name and Website:", locator);
                        table.addCell(cell);

                        messageRecord.add("Document Provider Name and Website:");
                        messageRecord.add(locator);
                    }else
                    {
                        // cell = new PdfPCell(new Phrase("Electronic Health Record (add web address, username and password):" + locator));
                        cell = new PdfPCell();
                        HeaderNew.cellDesign(cell, table, "Electronic Health Record (add web address, username and password):", locator);
                        table.addCell(cell);

                        messageRecord.add("Electronic Health Record (add web address, username and password) :");
                        messageRecord.add(locator);
                    }


                    String note = "";
                    if (s.getNote() != null) {
                        note = s.getNote();
                    }
                    // cell = new PdfPCell(new Phrase("Location of Original:" + location));
                    cell = new PdfPCell();
                    HeaderNew.cellDesignNoline(cell, table, "Notes:", note);
                    table.addCell(cell);

                    messageRecord.add("Note :");
                    messageRecord.add(note);

                    cell = new PdfPCell();
                    HeaderNew.cellDesign(cell, table, "", "Empty");
                    table.addCell(cell);


                    messageRecord.add("");
                    messageRecord.add("");


                    cell1.addElement(table);
                    table1.addCell(cell1);
                    HeaderNew.document.add(table1);
                    HeaderNew.addEmptyLine(1);
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
    }
}
