package com.mindyourlovedone.healthcare.pdfdesign;

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
import com.mindyourlovedone.healthcare.model.Form;
import com.mindyourlovedone.healthcare.model.PrescribeImage;
import com.mindyourlovedone.healthcare.model.Prescription;

import java.util.ArrayList;

/**
 * Edited by nikita on 04/11/2017.
 */
/**
 * Class: PrescriptionPdfNew
 * Screen: Prescription Sections
 * A class that manages pdf creation of Prescription Section
 */

public class PrescriptionPdfNew {
    public static ArrayList<String> messagePrescription = new ArrayList<String>();
    public static ArrayList<String> messageForm = new ArrayList<String>();

    Font BlackFont;

    //Set Font
    private void PrescriptionNewFont() {
        try {
            BaseFont base = BaseFont.createFont("assets/Lato-Regular.ttf", "UTF-8", BaseFont.EMBEDDED);
            BlackFont = new Font(base, 19, Font.NORMAL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Function: Create Prescription Information PDF
     * @param prescriptionList
     * @param pp
     */
    public PrescriptionPdfNew(ArrayList<Prescription> prescriptionList, Image pp) {
        PrescriptionNewFont();
        try {
            // HeaderNew.addEmptyLine(1);
            //  if (i==0) {
            HeaderNew.addNewChank("Prescription Information", pp);
            messagePrescription.add("Prescription Information");
            HeaderNew.addEmptyLine(1);
            // }

            for (int i = 0; i < prescriptionList.size(); i++) {
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

                String medicine = "";
                if (prescriptionList.get(i).getMedicine() != null) {
                    medicine = prescriptionList.get(i).getMedicine();
                }
                // cell = new PdfPCell(new Phrase("Name:" + medicine));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "Name of Medication or Supplement:", medicine);
                table.addCell(cell);
                messagePrescription.add("Name :");
                messagePrescription.add(medicine);

                String dosage = "";
                if (prescriptionList.get(i).getDose() != null) {
                    dosage = prescriptionList.get(i).getDose();
                }
                // cell = new PdfPCell(new Phrase("Dosage:" + dosage));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "Dosage:", dosage);
                table.addCell(cell);
                messagePrescription.add("Dosage :");
                messagePrescription.add(dosage);

                String frequency = "";
                if (prescriptionList.get(i).getFrequency() != null) {
                    frequency = prescriptionList.get(i).getFrequency();
                }
                // cell = new PdfPCell(new Phrase("Frequency:" + frequency));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "Frequency:", frequency);
                table.addCell(cell);
                messagePrescription.add("Frequency :");
                messagePrescription.add(frequency);

                String rx = "";
                if (prescriptionList.get(i).getRX() != null) {
                    rx = prescriptionList.get(i).getRX();
                }
                // cell = new PdfPCell(new Phrase("RX#:" + rx));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "RX# Number:", rx);
                table.addCell(cell);
                messagePrescription.add("RX# Number:");
                messagePrescription.add(rx);

                String treatment = "";
                if (prescriptionList.get(i).getPurpose() != null) {
                    treatment = prescriptionList.get(i).getPurpose();
                }
                //    cell = new PdfPCell(new Phrase("Treatment For:" + treatment));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "Treatment For:", treatment);
                table.addCell(cell);
                messagePrescription.add("Treatment For :");
                messagePrescription.add(treatment);

                String date = "";
                if (prescriptionList.get(i).getDates() != null) {
                    date = prescriptionList.get(i).getDates();
                }
                //     cell = new PdfPCell(new Phrase("Date of Prescription:" + date));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "Date of Prescription:", date);
                table.addCell(cell);
                messagePrescription.add("Date of Prescription :");
                messagePrescription.add(date);


                String doctor = "";
                if (prescriptionList.get(i).getDoctor() != null) {
                    doctor = prescriptionList.get(i).getDoctor();
                }
                //  cell = new PdfPCell(new Phrase("Prescribing Doctor:" + doctor));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "Prescribing Doctor:", doctor);
                table.addCell(cell);
                messagePrescription.add("Prescribing Doctor :");
                messagePrescription.add(doctor);

                String counter = "";
                if (prescriptionList.get(i).getPre() != null) {
                    counter = prescriptionList.get(i).getPre();
                }
                //    cell = new PdfPCell(new Phrase("Over-the-counter:" + counter));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "Over-the-counter:", counter);
                table.addCell(cell);
                messagePrescription.add("Over-the-counter :");
                messagePrescription.add(counter);



                String notes = "";
                if (prescriptionList.get(i).getNote() != null) {
                    notes = prescriptionList.get(i).getNote();
                }
                //    cell = new PdfPCell(new Phrase("Notes:" + notes));
                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table, "Notes:", notes);
                table.addCell(cell);
                messagePrescription.add("Notes :");
                messagePrescription.add(notes);
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);


                messagePrescription.add("");
                messagePrescription.add("");


                cell1.addElement(table);
                table1.addCell(cell1);
                HeaderNew.document.add(table1);
                HeaderNew.addEmptyLine(1);
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
               /* cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePrescription.add("");
                messagePrescription.add("");

                Header.document.add(table);

                Header.addEmptyLine(1);

                ArrayList<PrescribeImage> imagelist = new ArrayList<PrescribeImage>();
                if (prescriptionList.get(i).getPrescriptionImageList() != null) {
                    imagelist = prescriptionList.get(i).getPrescriptionImageList();

                    for (int j = 0; j < imagelist.size(); j++) {
*//*
                        byte[] IMG1 = imagelist.get(j).getImage();

                        Image img1 = Image.getInstance(IMG1);
                        img1.scaleAbsolute(500,500);
                        img1.setBorder(Rectangle.BOX);
                        img1.setBorderColor(BaseColor.DARK_GRAY);

                        PdfPTable table1;
                        table1 = new PdfPTable(1);
                        table1.setWidthPercentage(100);

                        PdfPCell cell1 = new PdfPCell();
                        cell1.setBorder(Rectangle.NO_BORDER);
                        cell1.addElement(img1);
                        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        table1.addCell(cell1);

                        Header.document.add(table1);

                        Header.addEmptyLine(1);*//*
                    }


                }


                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    /**
     * Function: Create Prescription List Upload PDF
     * @param prescriptionList
     * @param j
     * @param pp
     */
    public PrescriptionPdfNew(ArrayList<Form> prescriptionList, int j, Image pp) {
        PrescriptionNewFont();
        try {
            HeaderNew.addNewChank("Prescription List Upload", pp);
            messagePrescription.add("Prescription List Upload");
            HeaderNew.addEmptyLine(1);


            for (int i = 0; i < prescriptionList.size(); i++) {
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

                Form s = prescriptionList.get(i);

                String name = "";
                if (s.getName() != null) {
                    name = s.getName();
                }
                // cell = new PdfPCell(new Phrase("Prescription Name:" + name));
                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table, "Prescription Name:", name);
                table.addCell(cell);
                messageForm.add("Prescription Name:");
                messageForm.add(name);

               /* cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageForm.add("");
                messageForm.add(name);*/

                String date = "";
                if (s.getDate() != null) {
                    date = s.getDate();
                }
                //cell = new PdfPCell(new Phrase("Date::" + date));
                cell.setBorder(Rectangle.BOTTOM);
                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table, "Date:", date);
                table.addCell(cell);
                messageForm.add("Date::");
                messageForm.add(date);

                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);

                messagePrescription.add("");
                messagePrescription.add("");


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
