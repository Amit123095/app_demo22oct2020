package com.mindyourlovedone.healthcare.pdfdesign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.mindyourlovedone.healthcare.model.Aides;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Finance;
import com.mindyourlovedone.healthcare.model.Hospital;
import com.mindyourlovedone.healthcare.model.Insurance;
import com.mindyourlovedone.healthcare.model.Pharmacy;
import com.mindyourlovedone.healthcare.model.Specialist;

import java.util.ArrayList;

/**
 * Created by welcome on 11/6/2017.
 */

public class Specialty {

    public static ArrayList<String> messageDoctor = new ArrayList<String>();
    public static ArrayList<String> messageHospital = new ArrayList<String>();
    public static ArrayList<String> messagePharmacy = new ArrayList<String>();
    public static ArrayList<String> messageAides = new ArrayList<String>();
    public static ArrayList<String> messageFinance = new ArrayList<String>();


    public Specialty(ArrayList<Specialist> specialistsList, String doctors) {
        try {

            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
           // Header.addEmptyLine(1);

            Header.addChank("Doctors");
            messageDoctor.add("Doctors");
            Header.addEmptyLine(1);


            for (int i = 0; i < specialistsList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);
               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Doctors " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Doctors " + k + " :");
                messageDoctor.add("");*/

                Specialist s = specialistsList.get(i);

                String speciality = "";
                if (s.getType() != null) {
                    speciality = s.getType();
                    if (speciality.equals("Other")) {
                        speciality = speciality + " - " + s.getOtherType();
                    }
                }
                cell = new PdfPCell(new Phrase("Specialty:" + speciality));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Specialty :");
                messageDoctor.add(speciality);

              /*  String specialityOther = "";
                if (s.getOtherType() != null) {
                    specialityOther = s.getOtherType();
                }
                cell = new PdfPCell(new Phrase("Speciality:" + specialityOther));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Speciality :");
                messageDoctor.add(specialityOther);*/

                String name = "";
                if (s.getName() != null) {
                    name = s.getName();
                }
                cell = new PdfPCell(new Phrase("Name of Doctor/Health Professionals\n:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Name of Doctor/Health Professionals\n :");
                messageDoctor.add(name);

                String officePhone = "";
                if (s.getOfficePhone() != null) {
                    officePhone = s.getOfficePhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Office Phone :");
                messageDoctor.add(officePhone);

                String afterHoursPhone = "";
                if (s.getHourPhone() != null) {
                    afterHoursPhone = s.getHourPhone();
                }
                cell = new PdfPCell(new Phrase("After Hours Phone:" + afterHoursPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("After Hours Phone :");
                messageDoctor.add(afterHoursPhone);

                String otherPhone = "";
                if (s.getOtherPhone() != null) {
                    otherPhone = s.getOtherPhone();
                }
                cell = new PdfPCell(new Phrase("Other Phone:" + otherPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Other Phone :");
                messageDoctor.add(otherPhone);

                String officeFax = "";
                if (s.getFax() != null) {
                    officeFax = s.getFax();
                }
                cell = new PdfPCell(new Phrase("Office Fax:" + officeFax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Office Fax :");
                messageDoctor.add(officeFax);


                String address = "";
                if (s.getAddress() != null) {
                    address = s.getAddress();
                }
                cell = new PdfPCell(new Phrase("Address:" + address));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Address :");
                messageDoctor.add(address);

                String website = "";
                if (s.getWebsite() != null) {
                    website = s.getWebsite();
                }
                cell = new PdfPCell(new Phrase("Website:" + website));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Website :");
                messageDoctor.add(website);

                String medicalPracticeName = "";
                if (s.getPracticeName() != null) {
                    medicalPracticeName = s.getPracticeName();
                }
                cell = new PdfPCell(new Phrase("Medical Practice Name:" + medicalPracticeName));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Medical Practice Name :");
                messageDoctor.add(medicalPracticeName);

                String hospitalAffiliations = "";
                if (s.getHospAffiliation() != null) {
                    hospitalAffiliations = s.getHospAffiliation();
                }
                cell = new PdfPCell(new Phrase("Hospital Affiliations:" + hospitalAffiliations));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Hospital Affiliations :");
                messageDoctor.add(hospitalAffiliations);

                String networkStatus = "";
                if (s.getNetwork() != null) {
                    networkStatus = s.getNetwork();
                }
                cell = new PdfPCell(new Phrase("Network Status:" + networkStatus));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Network Status :");
                messageDoctor.add(networkStatus);

                String lastSeen = "";
                if (s.getLastseen() != null) {
                    lastSeen = s.getLastseen();
                }
                cell = new PdfPCell(new Phrase("Last Visit:" + lastSeen));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Last Visit :");
                messageDoctor.add(lastSeen);

                String locator = "";
                if (s.getLocator() != null) {
                    locator = s.getLocator();
                }
                cell = new PdfPCell(new Phrase("Electronic Health Record (add web address, username and password):" + locator));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Electronic Health Record (add web address, username and password) :");
                messageDoctor.add(locator);

                String note = "";
                if (s.getNote() != null) {
                    note = s.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + note));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Notes :");
                messageDoctor.add(note);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("");
                messageDoctor.add(note);


                Header.document.add(table);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);
            }


            Header.document.add(table1);
          //  Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Specialty(String hospital, ArrayList<Hospital> hospitalList) {
        try {
            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);

           // Header.addEmptyLine(1);
            Header.addChank("Hospitals And Other Health Professionals");
            messageHospital.add("Hospitals And Other Health Professionals");
            Header.addEmptyLine(1);

            for (int i = 0; i < hospitalList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);
               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Hospitals And Other Health Professionals " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Hospitals And Other Health Professionals " + k + " :");
                messageHospital.add("");*/

                Hospital h = hospitalList.get(i);

                String category = "";
                if (h.getCategory() != null) {
                    category = h.getCategory();
                    if (category.equals("Other")) {
                        category = category + " - " + h.getOtherCategory();
                    }
                }
                cell = new PdfPCell(new Phrase("Category:" + category));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Category :");
                messageHospital.add(category);

                /*String othercategory = "";
                if (h.getOtherCategory() != null) {
                    othercategory = h.getOtherCategory();
                }
                cell = new PdfPCell(new Phrase("Other Category:" + othercategory));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Other Category :");
                messageHospital.add(othercategory);*/

                String name = "";
                if (h.getName() != null) {
                    name = h.getName();
                }
                cell = new PdfPCell(new Phrase("Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Name :");
                messageHospital.add(name);

                String officePhone = "";
                if (h.getOfficePhone() != null) {
                    officePhone = h.getOfficePhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Office Phone :");
                messageHospital.add(officePhone);

               /* String mobile = "";
                if (h.getHourPhone() != null) {
                    mobile = h.getHourPhone();
                }
                cell = new PdfPCell(new Phrase("Mobile Phone:" + mobile));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Mobile Phone :");
                messageHospital.add(mobile);*/

                String otherPhone = "";
                if (h.getOtherPhone() != null) {
                    otherPhone = h.getOtherPhone();
                }
                cell = new PdfPCell(new Phrase("Other Phone:" + otherPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Other Phone :");
                messageHospital.add(otherPhone);

                String officeFax = "";
                if (h.getFax() != null) {
                    officeFax = h.getFax();
                }
                cell = new PdfPCell(new Phrase("Office Fax:" + officeFax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Office Fax :");
                messageHospital.add(officeFax);


                String address = "";
                if (h.getAddress() != null) {
                    address = h.getAddress();
                }
                cell = new PdfPCell(new Phrase("Address:" + address));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Address :");
                messageHospital.add(address);

                String website = "";
                if (h.getWebsite() != null) {
                    website = h.getWebsite();
                }
                cell = new PdfPCell(new Phrase("Website:" + website));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Website :");
                messageHospital.add(website);

                String companyName = "";
                if (h.getPracticeName() != null) {
                    companyName = h.getPracticeName();
                }
                cell = new PdfPCell(new Phrase("Contact Person:" + companyName));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Contact Person :");
                messageHospital.add(companyName);

                String network = "";
                if (h.getLocation() != null) {
                    network = h.getLocation();
                }
                cell = new PdfPCell(new Phrase("Network Status:" + network));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Network Status :");
                messageHospital.add(network);

                String lastSeen = "";
                if (h.getLastseen() != null) {
                    lastSeen = h.getLastseen();
                }
                cell = new PdfPCell(new Phrase("Last Visit:" + lastSeen));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Last Visit :");
                messageHospital.add(lastSeen);

                String locator = "";
                if (h.getLocator() != null) {
                    locator = h.getLocator();
                }
                cell = new PdfPCell(new Phrase("Electronic Health Record (add web address, username and password) ):" + locator));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Electronic Health Record (add web address, username and password)  :");
                messageHospital.add(locator);

                String note = "";
                if (h.getNote() != null) {
                    note = h.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + note));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Notes :");
                messageHospital.add(note);


                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("");
                messageHospital.add(note);

                Header.document.add(table);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);
            }

            Header.document.add(table1);

           // Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Specialty(ArrayList<Pharmacy> pharmacyList) {
        try {
            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);

            //Header.addEmptyLine(1);
            Header.addChank("Pharmacies & Home Medical Equipment");
            messagePharmacy.add("Pharmacies & Home Medical Equipment");
            Header.addEmptyLine(1);

            for (int i = 0; i < pharmacyList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);
               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Pharmacies And Home Medical Equipment " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Pharmacies And Home Medical Equipment " + k + " :");
                messagePharmacy.add("");*/

                Pharmacy p = pharmacyList.get(i);

                String name = "";
                if (p.getName() != null) {
                    name = p.getName();
                }
                cell = new PdfPCell(new Phrase("Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Name :");
                messagePharmacy.add(name);

                String address = "";
                if (p.getAddress() != null) {
                    address = p.getAddress();
                }
                cell = new PdfPCell(new Phrase("Address:" + address));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Address :");
                messagePharmacy.add(address);

                String phone = "";
                if (p.getPhone() != null) {
                    phone = p.getPhone();
                }
                cell = new PdfPCell(new Phrase("Phone:" + phone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Phone :");
                messagePharmacy.add(phone);

                String Fax = "";
                if (p.getFax() != null) {
                    Fax = p.getFax();
                }
                cell = new PdfPCell(new Phrase("Fax:" + Fax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Fax :");
                messagePharmacy.add(Fax);


                String website = "";
                if (p.getWebsite() != null) {
                    website = p.getWebsite();
                }
                cell = new PdfPCell(new Phrase("Website:" + website));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Website :");
                messagePharmacy.add(website);

                String locator = "";
                if (p.getLocator() != null) {
                    locator = p.getLocator();
                }
                cell = new PdfPCell(new Phrase("Electronic Health Record (add web address, username and password):" + locator));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Electronic Health Record (add web address, username and password) :");
                messagePharmacy.add(locator);

                String note = "";
                if (p.getNote() != null) {
                    note = p.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + note));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Notes :");
                messagePharmacy.add(note);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("");
                messagePharmacy.add(note);

                Header.document.add(table);
                Paragraph ps = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                ps.add(line);
                Header.document.add(ps);
                Header.addEmptyLine(1);
            }
            Header.document.add(table1);
          //  Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public Specialty(ArrayList<Aides> aidesList, int i) {
        try {

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);

            Header.addEmptyLine(1);
            Header.addChank("Home Health Services");
            messageAides.add("Home Health Services");
            Header.addEmptyLine(1);

            for (i = 0; i < aidesList.size(); i++) {
                int k = i + 1;
                cell = new PdfPCell(new Phrase("Home Health Services" + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAides.add("Home Health Services" + k + " :");
                messageAides.add("");

                Aides a = aidesList.get(i);

                String name = "";
                if (a.getAidName() != null) {
                    name = a.getAidName();
                }
                cell = new PdfPCell(new Phrase("Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAides.add("Name :");
                messageAides.add(name);

                String officePhone = "";
                if (a.getOfficePhone() != null) {
                    officePhone = a.getOfficePhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAides.add("Office Phone :");
                messageAides.add(officePhone);

                String afterHoursPhone = "";
                if (a.getHourPhone() != null) {
                    afterHoursPhone = a.getHourPhone();
                }
                cell = new PdfPCell(new Phrase("After Hours Phone:" + afterHoursPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAides.add("After Hours Phone :");
                messageAides.add(afterHoursPhone);

                String otherPhone = "";
                if (a.getOtherPhone() != null) {
                    otherPhone = a.getOtherPhone();
                }
                cell = new PdfPCell(new Phrase("Other Phone:" + otherPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAides.add("Other Phone :");
                messageAides.add(otherPhone);

                String officeFax = "";
                if (a.getFax() != null) {
                    officeFax = a.getFax();
                }
                cell = new PdfPCell(new Phrase("Office Fax:" + officeFax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAides.add("Office Fax :");
                messageAides.add(officeFax);

                String email = "";
                if (a.getEmail() != null) {
                    email = a.getEmail();
                }
                cell = new PdfPCell(new Phrase("Email:" + email));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAides.add("Email :");
                messageAides.add(email);


                String address = "";
                if (a.getAddress() != null) {
                    address = a.getAddress();
                }
                cell = new PdfPCell(new Phrase("Address:" + address));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAides.add("Address :");
                messageAides.add(address);

                String website = "";
                if (a.getWebsite() != null) {
                    website = a.getWebsite();
                }
                cell = new PdfPCell(new Phrase("Website:" + website));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAides.add("Website :");
                messageAides.add(website);

                String note = "";
                if (a.getNote() != null) {
                    note = a.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + note));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageAides.add("Notes :");
                messageAides.add(note);

            }

            Header.document.add(table);
            Paragraph p = new Paragraph(" ");
            DottedLineSeparator line = new DottedLineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.BLACK);
            p.add(line);
            Header.document.add(p);
          //  Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Specialty(int i, ArrayList<Finance> financeList) {
        try {
            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);

            Header.addChank("Finance, Legal, Other");
            messageFinance.add("Finance, Legal, Other");
            Header.addEmptyLine(1);

            for (i = 0; i < financeList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);

               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Finance,Insurance,Legal " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Finance,Insurance,Legal " + k + " :");
                messageFinance.add("");*/

                Finance f = financeList.get(i);

                String category = "";
                if (f.getOtherCategory() != null) {
                    category = f.getOtherCategory();
                }
                cell = new PdfPCell(new Phrase("Category:" + category));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Category :");
                messageFinance.add(category);

                String name = "";
                if (f.getName() != null) {
                    name = f.getName();
                }
                cell = new PdfPCell(new Phrase("Firm Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Firm Name :");
                messageFinance.add(name);

                String person = "";
                if (f.getContactName() != null) {
                    person = f.getContactName();
                }
                cell = new PdfPCell(new Phrase("Contact Name:" + person));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Contact Name :");
                messageFinance.add(person);

                String email = "";
                if (f.getEmail() != null) {
                    email = f.getEmail();
                }
                cell = new PdfPCell(new Phrase("Email:" + email));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Email :");
                messageFinance.add(email);


                String officePhone = "";
                if (f.getOfficePhone() != null) {
                    officePhone = f.getOfficePhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Office Phone :");
                messageFinance.add(officePhone);

                String mobile = "";
                if (f.getHourPhone() != null) {
                    mobile = f.getHourPhone();
                }
                cell = new PdfPCell(new Phrase("Mobile Phone:" + mobile));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Mobile Phone :");
                messageFinance.add(mobile);

                String otherPhone = "";
                if (f.getOtherPhone() != null) {
                    otherPhone = f.getOtherPhone();
                }
                cell = new PdfPCell(new Phrase("Other Phone:" + otherPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Other Phone :");
                messageFinance.add(otherPhone);

               /* String location = "";
                if (f.getLocation() != null) {
                    location = f.getLocation();
                }
                cell = new PdfPCell(new Phrase("Location:" +location));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Location :");
                messageFinance.add(location);*/

                String officeFax = "";
                if (f.getFax() != null) {
                    officeFax = f.getFax();
                }
                cell = new PdfPCell(new Phrase("Office Fax:" + officeFax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Office Fax :");
                messageFinance.add(officeFax);


                String address = "";
                if (f.getAddress() != null) {
                    address = f.getAddress();
                }
                cell = new PdfPCell(new Phrase("Address:" + address));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Address :");
                messageFinance.add(address);

                String website = "";
                if (f.getWebsite() != null) {
                    website = f.getWebsite();
                }
                cell = new PdfPCell(new Phrase("Website:" + website));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Website :");
                messageFinance.add(website);

               /* String companyName = "";
                if (f.getPracticeName() != null) {
                    companyName = f.getFirm();
                }
                cell = new PdfPCell(new Phrase("Company Name:" +companyName));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Company Name :");
                messageFinance.add(companyName);*/

                String lastSeen = "";
                if (f.getLastseen() != null) {
                    lastSeen = f.getLastseen();
                }
                cell = new PdfPCell(new Phrase("Last Visit:" + lastSeen));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Last Visit :");
                messageFinance.add(lastSeen);

                String note = "";
                if (f.getNote() != null) {
                    note = f.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + note));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Notes :");
                messageFinance.add(note);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("");
                messageFinance.add(note);

                Header.document.add(table);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);
            }

            Header.document.add(table1);
           // Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Specialty(Specialist s, String doctors, ArrayList<ContactData> phonelists, int i) {
        try {

            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
            // Header.addEmptyLine(1);
            if (i==0) {
                Header.addChank("Doctors & Other Health Care Professionals");
                messageDoctor.add("Doctors & Other Health Care Professionals");
                Header.addEmptyLine(1);
            }


          //  for (int i = 0; i < specialistsList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);
               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Doctors " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Doctors " + k + " :");
                messageDoctor.add("");*/

              //  Specialist s = specialistsList.get(i);

                String speciality = "";
                if (s.getType() != null) {
                    speciality = s.getType();
                    if (speciality.equals("Other")) {
                        speciality = speciality + " - " + s.getOtherType();
                    }
                }
                cell = new PdfPCell(new Phrase("Specialty:" + speciality));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Specialty :");
                messageDoctor.add(speciality);

              /*  String specialityOther = "";
                if (s.getOtherType() != null) {
                    specialityOther = s.getOtherType();
                }
                cell = new PdfPCell(new Phrase("Speciality:" + specialityOther));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Speciality :");
                messageDoctor.add(specialityOther);*/

                String name = "";
                if (s.getName() != null) {
                    name = s.getName();
                }
                cell = new PdfPCell(new Phrase("Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Name :");
                messageDoctor.add(name);

            for (int t=0;t<phonelists.size();t++)
            {
                ContactData c=phonelists.get(t);
                String num="";
                String type="";
                if (c.getValue() != null) {
                    num =c.getValue();
                }

                if (c.getContactType() != null) {
                    type =c.getContactType();
                }
                cell = new PdfPCell(new Phrase(type+" Phone:" + num));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageDoctor.add(type+" Phone:");
                messageDoctor.add(num);
            }

                /*String officePhone = "";
                if (s.getOfficePhone() != null) {
                    officePhone = s.getOfficePhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Office Phone :");
                messageDoctor.add(officePhone);

                String afterHoursPhone = "";
                if (s.getHourPhone() != null) {
                    afterHoursPhone = s.getHourPhone();
                }
                cell = new PdfPCell(new Phrase("After Hours Phone:" + afterHoursPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("After Hours Phone :");
                messageDoctor.add(afterHoursPhone);

                String otherPhone = "";
                if (s.getOtherPhone() != null) {
                    otherPhone = s.getOtherPhone();
                }
                cell = new PdfPCell(new Phrase("Other Phone:" + otherPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Other Phone :");
                messageDoctor.add(otherPhone);

                String officeFax = "";
                if (s.getFax() != null) {
                    officeFax = s.getFax();
                }
                cell = new PdfPCell(new Phrase("Office Fax:" + officeFax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Office Fax :");
                messageDoctor.add(officeFax);
*/

                String address = "";
                if (s.getAddress() != null) {
                    address = s.getAddress();
                }
                cell = new PdfPCell(new Phrase("Address:" + address));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Address :");
                messageDoctor.add(address);

                String website = "";
                if (s.getWebsite() != null) {
                    website = s.getWebsite();
                }
                cell = new PdfPCell(new Phrase("Website:" + website));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Website :");
                messageDoctor.add(website);

                String medicalPracticeName = "";
                if (s.getPracticeName() != null) {
                    medicalPracticeName = s.getPracticeName();
                }
                cell = new PdfPCell(new Phrase("Medical Practice Name:" + medicalPracticeName));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Medical Practice Name :");
                messageDoctor.add(medicalPracticeName);

                String hospitalAffiliations = "";
                if (s.getHospAffiliation() != null) {
                    hospitalAffiliations = s.getHospAffiliation();
                }
                cell = new PdfPCell(new Phrase("Hospital Affiliations:" + hospitalAffiliations));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Hospital Affiliations :");
                messageDoctor.add(hospitalAffiliations);

                String networkStatus = "";
                if (s.getNetwork() != null) {
                    networkStatus = s.getNetwork();
                }
                cell = new PdfPCell(new Phrase("Network Status:" + networkStatus));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Network Status :");
                messageDoctor.add(networkStatus);

                String lastSeen = "";
                if (s.getLastseen() != null) {
                    lastSeen = s.getLastseen();
                }
                cell = new PdfPCell(new Phrase("Last Visit:" + lastSeen));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Last Visit :");
                messageDoctor.add(lastSeen);

                String locator = "";
                if (s.getLocator() != null) {
                    locator = s.getLocator();
                }
                cell = new PdfPCell(new Phrase("Electronic Health Record (add web address, username and password):" + locator));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Electronic Health Record (add web address, username and password) :");
                messageDoctor.add(locator);

                String note = "";
                if (s.getNote() != null) {
                    note = s.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + note));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("Notes :");
                messageDoctor.add(note);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageDoctor.add("");
                messageDoctor.add(note);

            String card = "";
            if (s.getHas_card() != null) {
                card = s.getHas_card();
            }
            cell = new PdfPCell(new Phrase("Do you have a business card:" + card));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);
            messageDoctor.add(("Do you have a business card:"));
            messageDoctor.add(card);

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);
            messageDoctor.add("");
            messageDoctor.add(card);


                Header.document.add(table);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);



            Header.document.add(table1);
            //  Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

}

    public Specialty(Hospital h, String hospital1, ArrayList<ContactData> phonelists, int f) {
        try {
            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
            if (f==0) {
                // Header.addEmptyLine(1);
                Header.addChank("Urgent Care, TeleMed, Hospitals, Rehab, Home Care");
                messageHospital.add("Urgent Care, TeleMed, Hospitals, Rehab, Home Care");
                Header.addEmptyLine(1);
            }
          //  for (int i = 0; i < hospitalList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);
               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Hospitals And Other Health Professionals " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Hospitals And Other Health Professionals " + k + " :");
                messageHospital.add("");*/

             //   Hospital h = hospitalList.get(i);

                String category = "";
                if (h.getCategory() != null) {
                    category = h.getCategory();
                    if (category.equals("Other")) {
                        category = category + " - " + h.getOtherCategory();
                    }
                }
                cell = new PdfPCell(new Phrase("Category:" + category));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Category :");
                messageHospital.add(category);

                /*String othercategory = "";
                if (h.getOtherCategory() != null) {
                    othercategory = h.getOtherCategory();
                }
                cell = new PdfPCell(new Phrase("Other Category:" + othercategory));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Other Category :");
                messageHospital.add(othercategory);*/

                String name = "";
                if (h.getName() != null) {
                    name = h.getName();
                }
                cell = new PdfPCell(new Phrase("Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Name :");
                messageHospital.add(name);

               /* String officePhone = "";
                if (h.getOfficePhone() != null) {
                    officePhone = h.getOfficePhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Office Phone :");
                messageHospital.add(officePhone);

               *//* String mobile = "";
                if (h.getHourPhone() != null) {
                    mobile = h.getHourPhone();
                }
                cell = new PdfPCell(new Phrase("Mobile Phone:" + mobile));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Mobile Phone :");
                messageHospital.add(mobile);*//*

                String otherPhone = "";
                if (h.getOtherPhone() != null) {
                    otherPhone = h.getOtherPhone();
                }
                cell = new PdfPCell(new Phrase("Other Phone:" + otherPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Other Phone :");
                messageHospital.add(otherPhone);

                String officeFax = "";
                if (h.getFax() != null) {
                    officeFax = h.getFax();
                }
                cell = new PdfPCell(new Phrase("Office Fax:" + officeFax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Office Fax :");
                messageHospital.add(officeFax);
*/
            for (int t=0;t<phonelists.size();t++)
            {
                ContactData c=phonelists.get(t);
                String num="";
                String type="";
                if (c.getValue() != null) {
                    num =c.getValue();
                }

                if (c.getContactType() != null) {
                    type =c.getContactType();
                }
                cell = new PdfPCell(new Phrase(type+" Phone:" + num));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageHospital.add(type+" Phone:");
                messageHospital.add(num);
            }
                String address = "";
                if (h.getAddress() != null) {
                    address = h.getAddress();
                }
                cell = new PdfPCell(new Phrase("Address:" + address));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Address :");
                messageHospital.add(address);

                String website = "";
                if (h.getWebsite() != null) {
                    website = h.getWebsite();
                }
                cell = new PdfPCell(new Phrase("Website:" + website));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Website :");
                messageHospital.add(website);

                String companyName = "";
                if (h.getPracticeName() != null) {
                    companyName = h.getPracticeName();
                }
                cell = new PdfPCell(new Phrase("Contact Person:" + companyName));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Contact Person :");
                messageHospital.add(companyName);

                String network = "";
                if (h.getLocation() != null) {
                    network = h.getLocation();
                }
                cell = new PdfPCell(new Phrase("Network Status:" + network));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Network Status :");
                messageHospital.add(network);

                String lastSeen = "";
                if (h.getLastseen() != null) {
                    lastSeen = h.getLastseen();
                }
                cell = new PdfPCell(new Phrase("Last Visit:" + lastSeen));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Last Visit :");
                messageHospital.add(lastSeen);

                String locator = "";
                if (h.getLocator() != null) {
                    locator = h.getLocator();
                }
                cell = new PdfPCell(new Phrase("Electronic Health Record (add web address, username and password):" + locator));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Electronic Health Record (add web address, username and password) :");
                messageHospital.add(locator);

                String note = "";
                if (h.getNote() != null) {
                    note = h.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + note));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("Notes :");
                messageHospital.add(note);


                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageHospital.add("");
                messageHospital.add(note);

            String card = "";
            if (h.getHas_card() != null) {
                card = h.getHas_card();
            }
            cell = new PdfPCell(new Phrase("Do you have a business card:" + card));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);
            messageHospital.add(("Do you have a business card:"));
            messageHospital.add(card);

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);
            messageHospital.add("");
            messageHospital.add(card);

                Header.document.add(table);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);


            Header.document.add(table1);

            // Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Specialty(Pharmacy p, String pharmacy1, ArrayList<ContactData> phonelists, int i) {
        try {
            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
            if (i==0) {
                //Header.addEmptyLine(1);
                Header.addChank("Pharmacies & Home Medical Equipment");
                messagePharmacy.add("Pharmacies & Home Medical Equipment");
                Header.addEmptyLine(1);
            }

          //  for (int i = 0; i < pharmacyList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);
               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Pharmacies And Home Medical Equipment " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Pharmacies And Home Medical Equipment " + k + " :");
                messagePharmacy.add("");*/

            //    Pharmacy p = pharmacyList.get(i);

                String name = "";
                if (p.getName() != null) {
                    name = p.getName();
                }
                cell = new PdfPCell(new Phrase("Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Name :");
                messagePharmacy.add(name);

                String address = "";
                if (p.getAddress() != null) {
                    address = p.getAddress();
                }
                cell = new PdfPCell(new Phrase("Address:" + address));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Address :");
                messagePharmacy.add(address);

            for (int t=0;t<phonelists.size();t++)
            {
                ContactData c=phonelists.get(t);
                String num="";
                String type="";
                if (c.getValue() != null) {
                    num =c.getValue();
                }

                if (c.getContactType() != null) {
                    type =c.getContactType();
                }
                cell = new PdfPCell(new Phrase(type+" Phone:" + num));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messagePharmacy.add(type+" Phone:");
                messagePharmacy.add(num);
            }

               /* String phone = "";
                if (p.getPhone() != null) {
                    phone = p.getPhone();
                }
                cell = new PdfPCell(new Phrase("Phone:" + phone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Phone :");
                messagePharmacy.add(phone);

                String Fax = "";
                if (p.getFax() != null) {
                    Fax = p.getFax();
                }
                cell = new PdfPCell(new Phrase("Fax:" + Fax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Fax :");
                messagePharmacy.add(Fax);
*/

                String website = "";
                if (p.getWebsite() != null) {
                    website = p.getWebsite();
                }
                cell = new PdfPCell(new Phrase("Website:" + website));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Website :");
                messagePharmacy.add(website);

                String locator = "";
                if (p.getLocator() != null) {
                    locator = p.getLocator();
                }
                cell = new PdfPCell(new Phrase("Electronic Health Record (add web address, username and password):" + locator));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Electronic Health Record (add web address, username and password) :");
                messagePharmacy.add(locator);

                String note = "";
                if (p.getNote() != null) {
                    note = p.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + note));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("Notes :");
                messagePharmacy.add(note);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePharmacy.add("");
                messagePharmacy.add(note);

            String card = "";
            if (p.getHas_card() != null) {
                card = p.getHas_card();
            }
            cell = new PdfPCell(new Phrase("Do you have a business card:" + card));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);
            messagePharmacy.add(("Do you have a business card:"));
            messagePharmacy.add(card);

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);
            messageFinance.add("");
            messageFinance.add(note);

                Header.document.add(table);
                Paragraph ps = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                ps.add(line);
                Header.document.add(ps);
                Header.addEmptyLine(1);
        //}
            Header.document.add(table1);
            //  Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Specialty(Finance f, String finance1, ArrayList<ContactData> phonelists, int i) {
        try {
            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
            if (i==0) {
                Header.addChank("Finance, Legal, Other");
                messageFinance.add("Finance, Legal, Other");
                Header.addEmptyLine(1);
            }
           // for (i = 0; i < financeList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);

               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Finance,Insurance,Legal " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Finance,Insurance,Legal " + k + " :");
                messageFinance.add("");*/

             //   Finance f = financeList.get(i);

                String category = "";
                if (f.getOtherCategory() != null) {
                    category = f.getOtherCategory();
                }
                cell = new PdfPCell(new Phrase("Category:" + category));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Category :");
                messageFinance.add(category);

                String name = "";
                if (f.getName() != null) {
                    name = f.getName();
                }
                cell = new PdfPCell(new Phrase("Firm Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Firm Name :");
                messageFinance.add(name);

                String person = "";
                if (f.getContactName() != null) {
                    person = f.getContactName();
                }
                cell = new PdfPCell(new Phrase("Contact Name:" + person));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Contact Name :");
                messageFinance.add(person);

                String email = "";
                if (f.getEmail() != null) {
                    email = f.getEmail();
                }
                cell = new PdfPCell(new Phrase("Email:" + email));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Email :");
                messageFinance.add(email);

            for (int t=0;t<phonelists.size();t++)
            {
                ContactData c=phonelists.get(t);
                String num="";
                String type="";
                if (c.getValue() != null) {
                    num =c.getValue();
                }

                if (c.getContactType() != null) {
                    type =c.getContactType();
                }
                cell = new PdfPCell(new Phrase(type+" Phone:" + num));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageFinance.add(type+" Phone:");
                messageFinance.add(num);
            }
                /*String officePhone = "";
                if (f.getOfficePhone() != null) {
                    officePhone = f.getOfficePhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Office Phone :");
                messageFinance.add(officePhone);

                String mobile = "";
                if (f.getHourPhone() != null) {
                    mobile = f.getHourPhone();
                }
                cell = new PdfPCell(new Phrase("Mobile Phone:" + mobile));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Mobile Phone :");
                messageFinance.add(mobile);

                String otherPhone = "";
                if (f.getOtherPhone() != null) {
                    otherPhone = f.getOtherPhone();
                }
                cell = new PdfPCell(new Phrase("Other Phone:" + otherPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Other Phone :");
                messageFinance.add(otherPhone);
*/
               /* String location = "";
                if (f.getLocation() != null) {
                    location = f.getLocation();
                }
                cell = new PdfPCell(new Phrase("Location:" +location));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Location :");
                messageFinance.add(location);*/

               /* String officeFax = "";
                if (f.getFax() != null) {
                    officeFax = f.getFax();
                }
                cell = new PdfPCell(new Phrase("Office Fax:" + officeFax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Office Fax :");
                messageFinance.add(officeFax);
*/

                String address = "";
                if (f.getAddress() != null) {
                    address = f.getAddress();
                }
                cell = new PdfPCell(new Phrase("Address:" + address));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Address :");
                messageFinance.add(address);

                String website = "";
                if (f.getWebsite() != null) {
                    website = f.getWebsite();
                }
                cell = new PdfPCell(new Phrase("Website:" + website));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Website :");
                messageFinance.add(website);

               /* String companyName = "";
                if (f.getPracticeName() != null) {
                    companyName = f.getFirm();
                }
                cell = new PdfPCell(new Phrase("Company Name:" +companyName));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Company Name :");
                messageFinance.add(companyName);*/

                String lastSeen = "";
                if (f.getLastseen() != null) {
                    lastSeen = f.getLastseen();
                }
                cell = new PdfPCell(new Phrase("Last Visit:" + lastSeen));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Last Visit :");
                messageFinance.add(lastSeen);

                String note = "";
                if (f.getNote() != null) {
                    note = f.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + note));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("Notes :");
                messageFinance.add(note);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageFinance.add("");
                messageFinance.add(note);

            String card = "";
            if (f.getHas_card() != null) {
                card = f.getHas_card();
            }
            cell = new PdfPCell(new Phrase("Do you have a business card:" + card));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);
            messageFinance.add(("Do you have a business card:"));
            messageFinance.add(card);

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);
            messageFinance.add("");
            messageFinance.add(note);

                Header.document.add(table);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);


            Header.document.add(table1);
            // Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
