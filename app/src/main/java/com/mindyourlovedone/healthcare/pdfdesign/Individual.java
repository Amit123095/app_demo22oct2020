package com.mindyourlovedone.healthcare.pdfdesign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.mindyourlovedone.healthcare.model.Allergy;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Emergency;
import com.mindyourlovedone.healthcare.model.History;
import com.mindyourlovedone.healthcare.model.Implant;
import com.mindyourlovedone.healthcare.model.Living;
import com.mindyourlovedone.healthcare.model.MedInfo;
import com.mindyourlovedone.healthcare.model.PersonalInfo;
import com.mindyourlovedone.healthcare.model.Pet;
import com.mindyourlovedone.healthcare.model.Proxy;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.model.Specialist;
import com.mindyourlovedone.healthcare.model.Vaccine;

import java.util.ArrayList;




public class Individual {
    public static Font CompFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);

    public static Font BlackFont = new Font(Font.FontFamily.TIMES_ROMAN, 19,
            Font.NORMAL);

    public static ArrayList<String> messageInfo = new ArrayList<String>();
    public static ArrayList<String> messageInfo2 = new ArrayList<String>();
    public static ArrayList<String> messageInfo3 = new ArrayList<String>();
    public static ArrayList<String> messageEmergency = new ArrayList<String>();
    public static ArrayList<String> messagePhysician = new ArrayList<String>();
    public static ArrayList<String> messageProxy = new ArrayList<String>();
    public static ArrayList<String> messageLiving = new ArrayList<String>();
    String name = "";
    String address = "";
    String realtion = "";
    String mPhone = "";
    String hPhone = "";
    String bdate = "";
    String gender = "";
    String height = "";
    String weight = "";
    String eyes = "";
    String employedBy = "";
    String telephone = "";
    String language = "";
    String marital_status = "";
    String religionNote = "";
    String profession = "";
    String Veteran = "";
    String Pets = "";
    String idNumber = "";
    String Bdate = "";
    String notes = "";


    public Individual(RelativeConnection connection, ArrayList<Pet> Petlist, ArrayList<ContactData> phonelist) {
        try {
            // Header.addEmptyLine(1);
            Header.addChank("Personal Profile");
            messageInfo.add("Personal Profile");
            Header.addEmptyLine(1);
            BlackFont.setColor(00, 00, 00);//102, 204, 0);
            BlackFont.setStyle(Font.BOLD);

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);

            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
            if (connection.getName() != null) {
                name = connection.getName();
            }
            cell1 = new PdfPCell(new Phrase("Name:" + name));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo.add("Name :");
            messageInfo.add(name);
            if (connection.getRelationType() != null) {
                if (connection.getRelationType().equals("Other")) {
                    realtion = connection.getRelationType() + " - " + connection.getOtherRelation();
                } else {
                    realtion = connection.getRelationType();
                }
            }

            cell1 = new PdfPCell(new Phrase("Relationship:" + realtion));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo.add("Relationship :");
            messageInfo.add(realtion);

            String email = "";
            if (connection.getEmail() != null) {
                email = connection.getEmail();
            }
            cell1 = new PdfPCell(new Phrase("Email:" + email));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Email :");
            messageInfo2.add(email);


            for (int t=0;t<phonelist.size();t++)
            {
                ContactData c=phonelist.get(t);
                String num="";
                String type="";
                if (c.getValue() != null) {
                    num =c.getValue();
                }

                if (c.getContactType() != null) {
                    type =c.getContactType();
                }
                cell1 = new PdfPCell(new Phrase(type+" Phone:" + num));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageInfo2.add(type+" Phone:");
                messageInfo2.add(num);
            }
            /*if (connection.getMobile() != null) {
                mPhone = connection.getMobile();
            }
            cell1 = new PdfPCell(new Phrase("Mobile:" + mPhone));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Mobile :");
            messageInfo2.add(mPhone);

            if (connection.getPhone() != null) {
                hPhone = connection.getPhone();
            }
            cell1 = new PdfPCell(new Phrase("Home Phone:" + hPhone));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Home Phone :");
            messageInfo2.add(hPhone);


            String workph = "";
            if (connection.getWorkPhone() != null) {
                workph = connection.getWorkPhone();
            }
            cell1 = new PdfPCell(new Phrase("Work Phone:" + workph));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);
            messageInfo2.add("Work Phone :");
            messageInfo2.add(workph);
*/

            if (connection.getAddress() != null) {
                address = connection.getAddress();
            }
            cell1 = new PdfPCell(new Phrase("Address:" + address));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Address :");
            messageInfo2.add(address);

            Header.document.add(table1);
            Paragraph p1 = new Paragraph(" ");
            DottedLineSeparator line1 = new DottedLineSeparator();
            line1.setOffset(-4);
            line1.setLineColor(BaseColor.BLACK);
            p1.add(line1);
            Header.document.add(p1);
            Header.addEmptyLine(1);

//--------------------------------------------
            PdfPTable table8;
            table8 = new PdfPTable(2);
            PdfPCell cell8;
            table8.setWidthPercentage(100);

            String bdates = "";
            if (connection.getDob() != null) {
                bdates = connection.getDob();
            }
            cell8 = new PdfPCell(new Phrase("Birthday:" + bdates));
            cell8.setBorder(Rectangle.BOTTOM);
            cell8.setUseBorderPadding(true);
            cell8.setBorderWidthBottom(5);
            cell8.setBorderColorBottom(BaseColor.WHITE);
            table8.addCell(cell8);
            messageInfo2.add("Birthday :");
            messageInfo2.add(bdates);

            String genders = "";
            if (connection.getGender() != null) {
                genders = connection.getGender();
            }
            cell8 = new PdfPCell(new Phrase("Gender:" + genders));
            cell8.setBorder(Rectangle.BOTTOM);
            cell8.setUseBorderPadding(true);
            cell8.setBorderWidthBottom(5);
            cell8.setBorderColorBottom(BaseColor.WHITE);
            table8.addCell(cell8);

            messageInfo2.add("Gender :");
            messageInfo2.add(genders);


            if (connection.getHeight() != null) {
                height = connection.getHeight();
            }
            cell8 = new PdfPCell(new Phrase("Height:" + height));
            cell8.setBorder(Rectangle.BOTTOM);
            cell8.setUseBorderPadding(true);
            cell8.setBorderWidthBottom(5);
            cell8.setBorderColorBottom(BaseColor.WHITE);
            table8.addCell(cell8);

            messageInfo2.add("Height :");
            messageInfo2.add(height);

            if (connection.getWeight() != null) {
                weight = connection.getWeight();
            }
            cell8 = new PdfPCell(new Phrase("Weight:" + weight));
            cell8.setBorder(Rectangle.BOTTOM);
            cell8.setUseBorderPadding(true);
            cell8.setBorderWidthBottom(5);
            cell8.setBorderColorBottom(BaseColor.WHITE);
            table8.addCell(cell8);

            messageInfo2.add("Weight :");
            messageInfo2.add(weight);

            if (connection.getEyes() != null) {
                eyes = connection.getEyes();
            }
            cell8 = new PdfPCell(new Phrase("Eyes:" + eyes));
            cell8.setBorder(Rectangle.BOTTOM);
            cell8.setUseBorderPadding(true);
            cell8.setBorderWidthBottom(5);
            cell8.setBorderColorBottom(BaseColor.WHITE);
            table8.addCell(cell8);

            messageInfo2.add("Eyes :");
            messageInfo2.add(eyes);

            cell8 = new PdfPCell(new Phrase(""));
            cell8.setBorder(Rectangle.BOTTOM);
            cell8.setUseBorderPadding(true);
            cell8.setBorderWidthBottom(5);
            cell8.setBorderColorBottom(BaseColor.WHITE);
            table8.addCell(cell8);

            messageInfo2.add("");
            messageInfo2.add("");

            Header.document.add(table8);
            Paragraph p8 = new Paragraph(" ");
            DottedLineSeparator line8 = new DottedLineSeparator();
            line8.setOffset(-4);
            line8.setLineColor(BaseColor.BLACK);
            p8.add(line8);
            Header.document.add(p8);
            Header.addEmptyLine(1);

            PdfPTable tablem;
            tablem = new PdfPTable(2);
            PdfPCell cellm;
            tablem.setWidthPercentage(100);
            if (connection.getMarital_status() != null) {
                marital_status = connection.getMarital_status();
            }
            cellm = new PdfPCell(new Phrase("Marital Status:" + marital_status));
            cellm.setBorder(Rectangle.BOTTOM);
            cellm.setUseBorderPadding(true);
            cellm.setBorderWidthBottom(5);
            cellm.setBorderColorBottom(BaseColor.WHITE);
            tablem.addCell(cellm);

            messageInfo2.add("Marital Status :");
            messageInfo2.add(marital_status);

            cellm = new PdfPCell(new Phrase(""));
            cellm.setBorder(Rectangle.BOTTOM);
            cellm.setUseBorderPadding(true);
            cellm.setBorderWidthBottom(5);
            cellm.setBorderColorBottom(BaseColor.WHITE);
            tablem.addCell(cellm);

            messageInfo2.add("");
            messageInfo2.add("");

            Header.document.add(tablem);
            Paragraph pm = new Paragraph(" ");
            DottedLineSeparator linem = new DottedLineSeparator();
            linem.setOffset(-4);
            linem.setLineColor(BaseColor.BLACK);
            pm.add(linem);
            Header.document.add(pm);
            Header.addEmptyLine(1);

            PdfPTable table2;
            table2 = new PdfPTable(2);
            PdfPCell cell2;
            table2.setWidthPercentage(100);

            String live = "";
            if (connection.getLive() != null) {
                live = connection.getLive();
            }
            cell2 = new PdfPCell(new Phrase("Do you live alone?:" + live));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Do you live alone? :");
            messageInfo2.add(live);

            String children = "";
            if (connection.getChildren() != null) {
                children = connection.getChildren();
            }
            cell2 = new PdfPCell(new Phrase("Children:" + children));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Children :");
            messageInfo2.add(children);

            String friend = "";
            if (connection.getFriend() != null) {
                friend = connection.getFriend();
            }
            cell2 = new PdfPCell(new Phrase("Friend:" + friend));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Friend :");
            messageInfo2.add(friend);

            String grandParents = "";
            if (connection.getGrand() != null) {
                grandParents = connection.getGrand();
            }
            cell2 = new PdfPCell(new Phrase("Grandparent(s):" + grandParents));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Grandparent(s) :");
            messageInfo2.add(grandParents);


            String parents = "";
            if (connection.getParents() != null) {
                parents = connection.getParents();
            }
            cell2 = new PdfPCell(new Phrase("Parent(s):" + parents));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Parent(s) :");
            messageInfo2.add(parents);


            String spouse = "";
            if (connection.getSpouse() != null) {
                spouse = connection.getSpouse();
            }
            cell2 = new PdfPCell(new Phrase("Spouse:" + spouse));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Spouse :");
            messageInfo2.add(spouse);

            String sibling = "";
            if (connection.getSibling() != null) {
                sibling = connection.getSibling();
            }
            cell2 = new PdfPCell(new Phrase("Sibling:" + sibling));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Sibling :");
            messageInfo2.add(sibling);

            String significant = "";
            if (connection.getSign_other() != null) {
                significant = connection.getSign_other();
            }
            cell2 = new PdfPCell(new Phrase("Significant Other:" + significant));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Significant Other :");
            messageInfo2.add(significant);

            String other = "";
            if (connection.getOther_person() != null) {
                other = connection.getOther_person();
            }
            cell2 = new PdfPCell(new Phrase("Other:" + other));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            String people = "";
            if (connection.getPeople() != null) {
                people = connection.getPeople();
            }
            cell2 = new PdfPCell(new Phrase("Names of People in Household:" + people));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);


            Header.document.add(table2);
            Paragraph p2 = new Paragraph(" ");
            DottedLineSeparator line2 = new DottedLineSeparator();
            line2.setOffset(-4);
            line2.setLineColor(BaseColor.BLACK);
            p2.add(line2);
            Header.document.add(p2);
            Header.addEmptyLine(1);

            //--------------------
            PdfPTable tablep;
            tablep = new PdfPTable(2);
            PdfPCell cellp;
            tablep.setWidthPercentage(100);

            if (connection.getProfession() != null) {
                profession = connection.getProfession();
            }
            cellp = new PdfPCell(new Phrase("Profession:" + profession));
            cellp.setBorder(Rectangle.BOTTOM);
            cellp.setUseBorderPadding(true);
            cellp.setBorderWidthBottom(5);
            cellp.setBorderColorBottom(BaseColor.WHITE);
            tablep.addCell(cellp);

            messageInfo2.add("Profession :");
            messageInfo2.add(profession);


            if (connection.getManager_phone() != null) {
                telephone = connection.getManager_phone();
            }
            cellp = new PdfPCell(new Phrase("Manager Phone:" + telephone));
            cellp.setBorder(Rectangle.BOTTOM);
            cellp.setUseBorderPadding(true);
            cellp.setBorderWidthBottom(5);
            cellp.setBorderColorBottom(BaseColor.WHITE);
            tablep.addCell(cellp);

            messageInfo2.add("Manager Phone :");
            messageInfo2.add(telephone);

            if (connection.getEmployed() != null) {
                employedBy = connection.getEmployed();
            }
            cellp = new PdfPCell(new Phrase("Employed By:" + employedBy));
            cellp.setBorder(Rectangle.BOTTOM);
            cellp.setUseBorderPadding(true);
            cellp.setBorderWidthBottom(5);
            cellp.setBorderColorBottom(BaseColor.WHITE);
            tablep.addCell(cellp);

            messageInfo2.add("Employed By :");
            messageInfo2.add(employedBy);


            cellp = new PdfPCell(new Phrase(""));
            cellp.setBorder(Rectangle.BOTTOM);
            cellp.setUseBorderPadding(true);
            cellp.setBorderWidthBottom(5);
            cellp.setBorderColorBottom(BaseColor.WHITE);
            tablep.addCell(cellp);

            messageInfo2.add("");
            messageInfo2.add(profession);

            Header.document.add(tablep);
            Paragraph pp = new Paragraph(" ");
            DottedLineSeparator linep = new DottedLineSeparator();
            linep.setOffset(-4);
            linep.setLineColor(BaseColor.BLACK);
            pp.add(linep);
            Header.document.add(pp);
            Header.addEmptyLine(1);
            //----------------------------------------

            PdfPTable table3;
            table3 = new PdfPTable(2);
            PdfPCell cell3;
            table3.setWidthPercentage(100);


           /* if (connection.getProfession() != null) {
                profession = connection.getProfession();
            }
            cell3 = new PdfPCell(new Phrase("Profession:" + profession));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo2.add("Profession :");
            messageInfo2.add(profession);*/

            String english = "";
            if (connection.getEnglish() != null) {
                english = connection.getEnglish();
            }
            cell3 = new PdfPCell(new Phrase("Understand English:" + english));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo2.add("Understand English :");
            messageInfo2.add(english);

            if (connection.getLanguage() != null) {
                language = connection.getLanguage();
                if (connection.getLanguage().equalsIgnoreCase("Other"));
                {
                    language=language+" - "+connection.getOtherLang();
                }
            }
            cell3 = new PdfPCell(new Phrase("Language Spoken:" + language));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo2.add("Language Spoken :");
            messageInfo2.add(language);

            cell3 = new PdfPCell(new Phrase(""));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo2.add(" ");
            messageInfo2.add(" ");


            Header.document.add(table3);
            Paragraph p3 = new Paragraph(" ");
            DottedLineSeparator line3 = new DottedLineSeparator();
            line3.setOffset(-4);
            line3.setLineColor(BaseColor.BLACK);
            p3.add(line3);
            Header.document.add(p3);
            Header.addEmptyLine(1);



            PdfPTable tabler;
            tabler = new PdfPTable(2);
            PdfPCell cellr;
            tabler.setWidthPercentage(100);

            if (connection.getReligion() != null) {
                religionNote = connection.getReligion();
            }
            cellr = new PdfPCell(new Phrase("Religious Affiliation & Notes: " + religionNote));
            cellr.setBorder(Rectangle.BOTTOM);
            cellr.setUseBorderPadding(true);
            cellr.setBorderWidthBottom(5);
            cellr.setBorderColorBottom(BaseColor.WHITE);
            tabler.addCell(cellr);

            messageInfo2.add("Religious Affiliation & Notes :");
            messageInfo2.add(religionNote);

            cellr = new PdfPCell(new Phrase(""));
            cellr.setBorder(Rectangle.BOTTOM);
            cellr.setUseBorderPadding(true);
            cellr.setBorderWidthBottom(5);
            cellr.setBorderColorBottom(BaseColor.WHITE);
            tabler.addCell(cellr);

            messageInfo2.add("");
            messageInfo2.add("");


            Header.document.add(tabler);
            Paragraph pmr = new Paragraph(" ");
            DottedLineSeparator linemr = new DottedLineSeparator();
            linemr.setOffset(-4);
            linemr.setLineColor(BaseColor.BLACK);
            pmr.add(linemr);
            Header.document.add(pmr);
            Header.addEmptyLine(1);

            /**/


            PdfPTable table4;
            table4 = new PdfPTable(2);
            PdfPCell cell4;
            table4.setWidthPercentage(100);

            if (connection.getVeteran() != null) {
                Veteran = connection.getVeteran();
            }
            cell4 = new PdfPCell(new Phrase("Veteran:" + Veteran));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setUseBorderPadding(true);
            cell4.setBorderWidthBottom(5);
            cell4.setBorderColorBottom(BaseColor.WHITE);
            table4.addCell(cell4);

            messageInfo2.add("Veteran :");
            messageInfo2.add(Veteran);

            if (connection.getIdnumber() != null) {
                idNumber = connection.getIdnumber();
            }
            cell4 = new PdfPCell(new Phrase("Id Number:" + idNumber));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setUseBorderPadding(true);
            cell4.setBorderWidthBottom(5);
            cell4.setBorderColorBottom(BaseColor.WHITE);
            table4.addCell(cell4);

            messageInfo2.add("Id Number :");
            messageInfo2.add(idNumber);

            Header.document.add(table4);
            Paragraph p4 = new Paragraph(" ");
            DottedLineSeparator line4 = new DottedLineSeparator();
            line4.setOffset(-4);
            line4.setLineColor(BaseColor.BLACK);
            p4.add(line4);
            Header.document.add(p4);
            Header.addEmptyLine(1);


           /* PdfPTable table5;
            table5= new PdfPTable(2);
            PdfPCell cell5;
            table5.setWidthPercentage(100);*/


            if (connection.getPet() != null) {
                Pets = connection.getPet();
            }
            cell = new PdfPCell(new Phrase("Pet(s):" + Pets));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageInfo2.add("Pet(s) :");
            messageInfo2.add(Pets);

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageInfo2.add("");
            messageInfo2.add("");

            String name = "";
            String breed = "";
            String color = "";
            String microchip = "";
            String veterian = "";
            String person = "";
            for (int i = 0; i < Petlist.size(); i++) {
              /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Pets " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Pets " + k + " :");
                messageInfo3.add("");*/

                Pet a = Petlist.get(i);
                if (a.getName() != null) {
                    name = a.getName();
                }
                cell = new PdfPCell(new Phrase("Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Name :");
                messageInfo3.add(name);

                if (a.getBreed() != null) {
                    breed = a.getBreed();
                }

                cell = new PdfPCell(new Phrase("Type of Pet / Breed :" + breed));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Breed :");
                messageInfo3.add(breed);

                if (a.getColor() != null) {
                    color = a.getColor();
                }
                cell = new PdfPCell(new Phrase("Color:" + color));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Color :");
                messageInfo3.add(color);

                if (a.getChip() != null) {
                    microchip = a.getChip();
                }
                cell = new PdfPCell(new Phrase("Microchip number:" + microchip));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Microchip number :");
                messageInfo3.add(microchip);


                if (a.getVeterian() != null) {
                    veterian = a.getVeterian();
                }
                cell = new PdfPCell(new Phrase("Veterinarian Name:" + veterian));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Veterinarian Name :");
                messageInfo3.add(veterian);

                String vadd="";
                if (a.getVeterian_add() != null) {
                    vadd = a.getVeterian_add();
                }
                cell = new PdfPCell(new Phrase("Veterinarian Address:" + vadd));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Veterinarian :");
                messageInfo3.add(vadd);

                String vpone="";
                if (a.getVeterian_ph() != null) {
                    vpone = a.getVeterian_ph();
                }
                cell = new PdfPCell(new Phrase("Veterinarian Phone:" + vpone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Veterinarian Phone :");
                messageInfo3.add(vpone);


                if (a.getGuard() != null) {
                    person = a.getGuard();
                }
                cell = new PdfPCell(new Phrase("Person(s) Name who will care for pet:" + person));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Person(s) Name who will care for pet :");
                messageInfo3.add(person);

                String cname="";
                if (a.getCare_add() != null) {
                    cname = a.getCare_add();
                }
                cell = new PdfPCell(new Phrase("Person(s) Address who will care for pet:" + cname));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Person(s) Address who will care for pet :");
                messageInfo3.add(cname);


                String cAdd="";
                if (a.getCare_ph() != null) {
                    cAdd = a.getCare_ph();
                }
                cell = new PdfPCell(new Phrase("Person(s) Phone who will care for pet:" + cAdd));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Person(s) Phone who will care for pet :");
                messageInfo3.add(cAdd);


                if (a.getBdate() != null) {
                    Bdate = a.getBdate();
                }
                cell = new PdfPCell(new Phrase("Birthday:" + Bdate));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Birthday:");
                messageInfo3.add(Bdate);

                if (a.getNotes() != null) {
                    notes = a.getNotes();
                }
                cell = new PdfPCell(new Phrase("Notes about Pet:" + notes));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Notes about Pet :");
                messageInfo3.add(notes);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("");
                messageInfo3.add("");

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("");
                messageInfo3.add(notes);


            }
            Header.document.add(table);
            Paragraph p = new Paragraph(" ");
            DottedLineSeparator line = new DottedLineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.BLACK);
            p.add(line);
            Header.document.add(p);
            Header.addEmptyLine(1);
          /*  if (connection.get == null) {
                oPhone = "";
            }
            Header.addTable("Medical Conditions :");
            Header.addTable(oPhone);
            messageInfo.add("Medical Conditions :");
            messageInfo.add(oPhone);
            if (cellPhone == null) {
                cellPhone = "";
            }
            Header.addTable("Preferred Hospital :");
            Header.addTable(cellPhone);
            messageInfo.add("Preferred Hospital :");
            messageInfo.add(cellPhone);*/
//        Header.table.setWidthPercentage(100f);

            PdfPTable tablef;
            tablef = new PdfPTable(2);
            PdfPCell cellf;
            tablef.setWidthPercentage(100);

            String card="";
            if (connection.getHas_card() != null) {
                card = connection.getHas_card();
            }
            cellf = new PdfPCell(new Phrase("Do you have a business card:" + card));
            cellf.setBorder(Rectangle.BOTTOM);
            cellf.setUseBorderPadding(true);
            cellf.setBorderWidthBottom(5);
            cellf.setBorderColorBottom(BaseColor.WHITE);
            tablef.addCell(cellf);

            messageInfo2.add("Do you have a business card:");
            messageInfo2.add(card);

            cellf = new PdfPCell(new Phrase("" ));
            cellf.setBorder(Rectangle.BOTTOM);
            cellf.setUseBorderPadding(true);
            cellf.setBorderWidthBottom(5);
            cellf.setBorderColorBottom(BaseColor.WHITE);
            tablef.addCell(cellf);

            messageInfo2.add("");
            messageInfo2.add("");

            Header.document.add(tablef);
            Paragraph pmf = new Paragraph(" ");
            DottedLineSeparator linemf = new DottedLineSeparator();
            linemr.setOffset(-4);
            linemr.setLineColor(BaseColor.BLACK);
            pmf.add(linemf);
            Header.document.add(pmf);
            Header.addEmptyLine(1);




        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        /*            String organDonor = "";
            if (medInfo.getDonor() != null) {
                organDonor = medInfo.getDonor();
            }
            cell = new PdfPCell(new Phrase("Organ Donor:" + organDonor));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageInfo3.add("Organ Donor :");
            messageInfo3.add(organDonor);*//*
         *//*  if (connection.get == null) {
                oPhone = "";
            }
            Header.addTable("Medical Conditions :");
            Header.addTable(oPhone);
            messageInfo.add("Medical Conditions :");
            messageInfo.add(oPhone);
            if (cellPhone == null) {
                cellPhone = "";
            }
            Header.addTable("Preferred Hospital :");
            Header.addTable(cellPhone);
            messageInfo.add("Preferred Hospital :");
            messageInfo.add(cellPhone);*//*
//        Header.table.setWidthPercentage(100f);


            Header.document.add(table);

            Paragraph p = new Paragraph(" ");
            DottedLineSeparator line = new DottedLineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.LIGHT_GRAY);
            p.add(line);
            Header.document.add(p);
            Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    public Individual(PersonalInfo connection, ArrayList<Pet> PetList) {
        try {
            //  Header.addEmptyLine(1);
            Header.addChank("Personal Profile");
            messageInfo2.add("Personal Profile");
            Header.addEmptyLine(1);

//        Header.widths[0] = 0.15f;
//        Header.widths[1] = 0.85f;
//        Header.table = new PdfPTable(Header.widths);
//        Header.table.getDefaultCell().setBorder(Rectangle.BOTTOM);

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);

            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
            if (connection.getName() != null) {
                name = connection.getName();
            }

            cell1 = new PdfPCell(new Phrase("Profile Name:" + name));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo.add("Profile Name :");
            messageInfo.add(name);

            if (connection.getEmail() != null) {
                realtion = connection.getEmail();
            }

            cell1 = new PdfPCell(new Phrase("Email:" + realtion));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo.add("Email :");
            messageInfo.add(realtion);

            if (connection.getPhone() != null) {
                mPhone = connection.getPhone();
            }
            cell1 = new PdfPCell(new Phrase("Mobile:" + mPhone));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Mobile :");
            messageInfo2.add(mPhone);

            if (connection.getHomePhone() != null) {
                hPhone = connection.getHomePhone();
            }
            cell1 = new PdfPCell(new Phrase("Home Phone:" + hPhone));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Home Phone :");
            messageInfo2.add(hPhone);


            if (connection.getAddress() != null) {
                address = connection.getAddress();
            }
            cell1 = new PdfPCell(new Phrase("Address:" + address));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Address :");
            messageInfo2.add(address);


            String bdate = "";
            if (connection.getDob() != null) {
                bdate = connection.getDob();
            }
            cell1 = new PdfPCell(new Phrase("Birth Date:" + bdate));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);
            messageInfo2.add("Birth Date :");
            messageInfo2.add(bdate);

            if (connection.getGender() != null) {
                gender = connection.getGender();
            }
            cell1 = new PdfPCell(new Phrase("Gender:" + gender));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Gender :");
            messageInfo2.add(gender);


            if (connection.getHeight() != null) {
                height = connection.getHeight();
            }
            cell1 = new PdfPCell(new Phrase("Height:" + height));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Height :");
            messageInfo2.add(height);

            if (connection.getWeight() != null) {
                weight = connection.getWeight();
            }
            cell1 = new PdfPCell(new Phrase("Weight:" + weight));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Weight :");
            messageInfo2.add(weight);

            if (connection.getEyes() != null) {
                eyes = connection.getEyes();
            }
            cell1 = new PdfPCell(new Phrase("Eyes:" + eyes));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo2.add("Eyes :");
            messageInfo2.add(eyes);

            Header.document.add(table1);
            Paragraph p1 = new Paragraph(" ");
            DottedLineSeparator line1 = new DottedLineSeparator();
            line1.setOffset(-4);
            line1.setLineColor(BaseColor.BLACK);
            p1.add(line1);
            Header.document.add(p1);
            Header.addEmptyLine(1);


            PdfPTable table2;
            table2 = new PdfPTable(2);
            PdfPCell cell2;
            table2.setWidthPercentage(100);

            String live = "";
            if (connection.getLive() != null) {
                live = connection.getLive();
            }
            cell2 = new PdfPCell(new Phrase("Do you live alone?:" + live));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Do you live alone? :");
            messageInfo2.add(live);

            String children = "";
            if (connection.getChildren() != null) {
                children = connection.getChildren();
            }
            cell2 = new PdfPCell(new Phrase("Children:" + children));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Children :");
            messageInfo2.add(children);

            String friend = "";
            if (connection.getFriend() != null) {
                friend = connection.getFriend();
            }
            cell2 = new PdfPCell(new Phrase("Friend:" + friend));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Friend :");
            messageInfo2.add(friend);

            String grandParents = "";
            if (connection.getGrand() != null) {
                grandParents = connection.getGrand();
            }
            cell2 = new PdfPCell(new Phrase("GrandParents:" + grandParents));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("GrandParents :");
            messageInfo2.add(grandParents);


            String parents = "";
            if (connection.getParents() != null) {
                parents = connection.getParents();
            }
            cell2 = new PdfPCell(new Phrase("Parents:" + parents));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Parents :");
            messageInfo2.add(parents);


            String spouse = "";
            if (connection.getSpouse() != null) {
                spouse = connection.getSpouse();
            }
            cell2 = new PdfPCell(new Phrase("Spouse:" + spouse));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Spouse :");
            messageInfo2.add(spouse);


            String significant = "";
            if (connection.getSign_other() != null) {
                significant = connection.getSign_other();
            }
            cell2 = new PdfPCell(new Phrase("Significant Other:" + significant));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Significant Other :");
            messageInfo2.add(significant);

            String other = "";
            if (connection.getOther_person() != null) {
                other = connection.getOther_person();
            }
            cell2 = new PdfPCell(new Phrase("Other:" + other));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo2.add("Other :");
            messageInfo2.add(other);

            Header.document.add(table2);
            Paragraph p2 = new Paragraph(" ");
            DottedLineSeparator line2 = new DottedLineSeparator();
            line2.setOffset(-4);
            line2.setLineColor(BaseColor.BLACK);
            p2.add(line2);
            Header.document.add(p2);
            Header.addEmptyLine(1);


            PdfPTable tablep;
            tablep = new PdfPTable(2);
            PdfPCell cellp;
            tablep.setWidthPercentage(100);


            if (connection.getEmployed() != null) {
                employedBy = connection.getEmployed();
            }
            cellp = new PdfPCell(new Phrase("Employed By:" + employedBy));
            cellp.setBorder(Rectangle.BOTTOM);
            cellp.setUseBorderPadding(true);
            cellp.setBorderWidthBottom(5);
            cellp.setBorderColorBottom(BaseColor.WHITE);
            tablep.addCell(cellp);

            messageInfo2.add("Employed By :");
            messageInfo2.add(employedBy);

            if (connection.getManager_phone() != null) {
                telephone = connection.getManager_phone();
            }
            cellp = new PdfPCell(new Phrase("Manager Phone:" + telephone));
            cellp.setBorder(Rectangle.BOTTOM);
            cellp.setUseBorderPadding(true);
            cellp.setBorderWidthBottom(5);
            cellp.setBorderColorBottom(BaseColor.WHITE);
            tablep.addCell(cellp);

            messageInfo2.add("Manager Phone :");
            messageInfo2.add(telephone);


            cellp = new PdfPCell(new Phrase(""));
            cellp.setBorder(Rectangle.BOTTOM);
            cellp.setUseBorderPadding(true);
            cellp.setBorderWidthBottom(5);
            cellp.setBorderColorBottom(BaseColor.WHITE);
            tablep.addCell(cellp);

            messageInfo2.add("");
            messageInfo2.add("");

            cellp = new PdfPCell(new Phrase(""));
            cellp.setBorder(Rectangle.BOTTOM);
            cellp.setUseBorderPadding(true);
            cellp.setBorderWidthBottom(5);
            cellp.setBorderColorBottom(BaseColor.WHITE);
            tablep.addCell(cellp);

            messageInfo2.add("");
            messageInfo2.add("");

            Header.document.add(tablep);
            Paragraph pp = new Paragraph(" ");
            DottedLineSeparator linep = new DottedLineSeparator();
            linep.setOffset(-4);
            linep.setLineColor(BaseColor.BLACK);
            pp.add(linep);
            Header.document.add(pp);
            Header.addEmptyLine(1);


            PdfPTable table3;
            table3 = new PdfPTable(2);
            PdfPCell cell3;
            table3.setWidthPercentage(100);

            if (connection.getLanguage() != null) {
                language = connection.getLanguage();
                if (connection.getLanguage().equalsIgnoreCase("Other"));
                {
                    language=language+" - "+connection.getOtherLang();
                }
            }
            cell3 = new PdfPCell(new Phrase("Language Spoken:" + language));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo2.add("Language Spoken :");
            messageInfo2.add(language);

            String english = "";
            if (connection.getEnglish() != null) {
                english = connection.getEnglish();
            }
            cell3 = new PdfPCell(new Phrase("Understand English:" + english));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo2.add("Understand English :");
            messageInfo2.add(english);

            if (connection.getProfession() != null) {
                profession = connection.getProfession();
            }
            cell3 = new PdfPCell(new Phrase("Profession:" + profession));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo2.add("Profession :");
            messageInfo2.add(profession);

            cell3 = new PdfPCell(new Phrase(""));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);
            messageInfo3.add("");
            messageInfo3.add("");


            Header.document.add(table3);
            Paragraph p3 = new Paragraph(" ");
            DottedLineSeparator line3 = new DottedLineSeparator();
            line3.setOffset(-4);
            line3.setLineColor(BaseColor.BLACK);
            p3.add(line3);
            Header.document.add(p3);
            Header.addEmptyLine(1);


            PdfPTable tablem;
            tablem = new PdfPTable(2);
            PdfPCell cellm;
            tablem.setWidthPercentage(100);
            if (connection.getMarital_status() != null) {
                marital_status = connection.getMarital_status();
            }
            cellm = new PdfPCell(new Phrase("Marital Status:" + marital_status));
            cellm.setBorder(Rectangle.BOTTOM);
            cellm.setUseBorderPadding(true);
            cellm.setBorderWidthBottom(5);
            cellm.setBorderColorBottom(BaseColor.WHITE);
            tablem.addCell(cellm);

            messageInfo2.add("Marital Status :");
            messageInfo2.add(marital_status);

            if (connection.getReligion() != null) {
                religionNote = connection.getReligion();
            }
            cellm = new PdfPCell(new Phrase("Religion Note:" + religionNote));
            cellm.setBorder(Rectangle.BOTTOM);
            cellm.setUseBorderPadding(true);
            cellm.setBorderWidthBottom(5);
            cellm.setBorderColorBottom(BaseColor.WHITE);
            tablem.addCell(cellm);

            messageInfo2.add("Religion Note :");
            messageInfo2.add(religionNote);

            Header.document.add(tablem);
            Paragraph pm = new Paragraph(" ");
            DottedLineSeparator linem = new DottedLineSeparator();
            linem.setOffset(-4);
            linem.setLineColor(BaseColor.BLACK);
            pm.add(linem);
            Header.document.add(pm);
            Header.addEmptyLine(1);


            PdfPTable table4;
            table4 = new PdfPTable(2);
            PdfPCell cell4;
            table4.setWidthPercentage(100);

            if (connection.getVeteran() != null) {
                Veteran = connection.getVeteran();
            }
            cell4 = new PdfPCell(new Phrase("Veteran:" + Veteran));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setUseBorderPadding(true);
            cell4.setBorderWidthBottom(5);
            cell4.setBorderColorBottom(BaseColor.WHITE);
            table4.addCell(cell4);

            messageInfo2.add("Veteran :");
            messageInfo2.add(Veteran);

            if (connection.getIdnumber() != null) {
                idNumber = connection.getIdnumber();
            }
            cell4 = new PdfPCell(new Phrase("Id Number:" + idNumber));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setUseBorderPadding(true);
            cell4.setBorderWidthBottom(5);
            cell4.setBorderColorBottom(BaseColor.WHITE);
            table4.addCell(cell4);

            messageInfo2.add("Id Number :");
            messageInfo2.add(idNumber);

            Header.document.add(table4);
            Paragraph p4 = new Paragraph(" ");
            DottedLineSeparator line4 = new DottedLineSeparator();
            line4.setOffset(-4);
            line4.setLineColor(BaseColor.BLACK);
            p4.add(line4);
            Header.document.add(p4);
            Header.addEmptyLine(1);


           /* PdfPTable table5;
            table5= new PdfPTable(2);
            PdfPCell cell5;
            table5.setWidthPercentage(100);*/


            if (connection.getPet() != null) {
                Pets = connection.getPet();
            }
            cell = new PdfPCell(new Phrase("Pets:" + Pets));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageInfo2.add("Pets :");
            messageInfo2.add(Pets);

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageInfo2.add("");
            messageInfo2.add("");

            String name = "";
            String breed = "";
            String color = "";
            String microchip = "";
            String veterian = "";
            String person = "";
            for (int i = 0; i < PetList.size(); i++) {
              /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Pets " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Pets " + k + " :");
                messageInfo3.add("");*/

                Pet a = PetList.get(i);
                if (a.getName() != null) {
                    name = a.getName();
                }
                cell = new PdfPCell(new Phrase("Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Name :");
                messageInfo3.add(name);

                if (a.getBreed() != null) {
                    breed = a.getBreed();
                }

                cell = new PdfPCell(new Phrase("Breed:" + breed));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Breed :");
                messageInfo3.add(breed);

                if (a.getColor() != null) {
                    color = a.getColor();
                }
                cell = new PdfPCell(new Phrase("Color:" + color));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Color :");
                messageInfo3.add(color);

                if (a.getChip() != null) {
                    microchip = a.getChip();
                }
                cell = new PdfPCell(new Phrase("Microchip number:" + microchip));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Microchip number :");
                messageInfo3.add(microchip);


                if (a.getVeterian() != null) {
                    veterian = a.getVeterian();
                }
                cell = new PdfPCell(new Phrase("Veterinarian:" + veterian));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Veterinarian :");
                messageInfo3.add(veterian);


                if (a.getGuard() != null) {
                    person = a.getGuard();
                }
                cell = new PdfPCell(new Phrase("Person(s) who will care for pet:" + person));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Person(s) who will care for pet :");
                messageInfo3.add(person);

                if (a.getBdate() != null) {
                    Bdate = a.getBdate();
                }
                cell = new PdfPCell(new Phrase("Birthdate:" + Bdate));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Birthdate :");
                messageInfo3.add(Bdate);

                if (a.getNotes() != null) {
                    notes = a.getNotes();
                }
                cell = new PdfPCell(new Phrase("Notes about Pet:" + notes));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Notes about Pet :");
                messageInfo3.add(notes);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("");
                messageInfo3.add("");

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("");
                messageInfo3.add(notes);

            }

          /*  if (connection.get == null) {
                oPhone = "";
            }
            Header.addTable("Medical Conditions :");
            Header.addTable(oPhone);
            messageInfo.add("Medical Conditions :");
            messageInfo.add(oPhone);
            if (cellPhone == null) {
                cellPhone = "";
            }
            Header.addTable("Preferred Hospital :");
            Header.addTable(cellPhone);
            messageInfo.add("Preferred Hospital :");
            messageInfo.add(cellPhone);*/
//        Header.table.setWidthPercentage(100f);


            Header.document.add(table);
            Paragraph p = new Paragraph(" ");
            DottedLineSeparator line = new DottedLineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.BLACK);
            p.add(line);
            Header.document.add(p);
            Header.addEmptyLine(1);

//card


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public Individual(String emergency, ArrayList<Emergency> emergencyList, ArrayList<ContactData> phonelist) {
        try {
            // Header.addEmptyLine(1);
            Header.addChank("Emergency Contacts & Health Care Proxy Agents");
            messageEmergency.add("Emergency Contacts & Health Care Proxy Agents");
            Header.addEmptyLine(1);

//        Header.widths[0] = 0.15f;
//        Header.widths[1] = 0.85f;
//        Header.table = new PdfPTable(Header.widths);
//        Header.table.getDefaultCell().setBorder(Rectangle.BOTTOM);
            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
            for (int i = 0; i < emergencyList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);

               /* int k = i + 1;

                cell = new PdfPCell(new Phrase("Emergency Contact " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Emergency Contact " + k + " :");
                messageEmergency.add("");*/

                Emergency e = emergencyList.get(i);

                String name = "";
                if (e.getName() != null) {
                    name = e.getName();
                }
                cell = new PdfPCell(new Phrase("Name:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Name :");
                messageEmergency.add(name);

                String reationType = "";
                if (e.getRelationType() != null) {
                    if (e.getRelationType().equals("Other")) {
                        reationType = e.getRelationType() + " - " + e.getOtherRelation();
                    } else {
                        reationType = e.getRelationType();
                    }
                }
                cell = new PdfPCell(new Phrase("Relation Type:" + reationType));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Relation Type :");
                messageEmergency.add(reationType);


                String priority = "";
                if (e.getIsPrimary() == 0) {
                    priority = "Primary Emergency Contact";

                } else if (e.getIsPrimary() == 1) {
                    priority = "Primary Health Care Proxy Agent";
                } else if (e.getIsPrimary() == 2) {
                    priority = "Secondary Emergency Contact";
                } else if (e.getIsPrimary() == 3) {

                    priority = "Secondary Health Care Proxy Agent";
                }else if (e.getIsPrimary() == 4) {

                    priority = "Primary Emergency Contact and Health Care Proxy Agent";
                }


                cell = new PdfPCell(new Phrase("Priority:" + priority));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Priority :");
                messageEmergency.add(priority);



             /*   String relationOther = "";
                if (e.getOtherRelation() != null) {
                    relationOther = e.getOtherRelation();
                }
                cell = new PdfPCell(new Phrase("Other:" + relationOther));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Other :");
                messageEmergency.add(relationOther);*/


                /*String officePhone = "";
                if (e.getWorkPhone() != null) {
                    officePhone = e.getWorkPhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Office Phone :");
                messageEmergency.add(officePhone);

                if (e.getMobile() != null) {
                    mPhone = e.getMobile();
                }
                cell = new PdfPCell(new Phrase("Mobile Phone:" + mPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Mobile Phone :");
                messageEmergency.add(mPhone);
                String bdate = "";

                if (e.getPhone() != null) {
                    hPhone = e.getPhone();
                }
                cell = new PdfPCell(new Phrase("Home Phone:" + hPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Home Phone :");
                messageEmergency.add(hPhone);
*/
                for (int t=0;t<phonelist.size();t++)
                {
                    ContactData c=phonelist.get(t);
                    String num="";
                    String type="";
                    if (c.getValue() != null) {
                        num =c.getValue();
                    }

                    if (c.getContactType() != null) {
                        type =c.getContactType();
                    }
                    cell = new PdfPCell(new Phrase(type+":" + num));
                    cell.setBorder(Rectangle.BOTTOM);
                    cell.setUseBorderPadding(true);
                    cell.setBorderWidthBottom(5);
                    cell.setBorderColorBottom(BaseColor.WHITE);
                    table1.addCell(cell);

                    messageEmergency.add(type+":");
                    messageEmergency.add(num);
                }

                String email = "";
                if (e.getEmail() != null) {
                    email = e.getEmail();
                }
                cell = new PdfPCell(new Phrase("Email:" + email));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Email :");
                messageEmergency.add(email);

                if (e.getAddress() != null) {
                    address = e.getAddress();
                }
                cell = new PdfPCell(new Phrase("Home Address:" + address));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Home Address :");
                messageEmergency.add(address);



                String ascard = "";
                if (e.getHas_card() != null) {
                    ascard = e.getHas_card();
                }
                cell = new PdfPCell(new Phrase("Do you have a business card:"+ascard));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Do you have a business card?");
                messageEmergency.add(ascard);

                String note = "";
                if (e.getNote() != null) {
                    note = e.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + note));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Notes :");
                messageEmergency.add(note);

                Header.document.add(table);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);
            }
//        Header.table.setWidthPercentage(100f);

            Header.document.add(table1);
            Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Individual(ArrayList<Specialist> specialistsList, String physician) {
        try {

            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
            // Header.addEmptyLine(1);

            Header.addChank("Primary Physicians");
            messagePhysician.add("Primary Physicians");
            Header.addEmptyLine(1);


            for (int i = 0; i < specialistsList.size(); i++) {

                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);
              /*  int k = i + 1;
                cell = new PdfPCell(new Phrase("Primary Physician " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePhysician.add("Primary Physician" + k + " :");
                messagePhysician.add("");*/

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
                messagePhysician.add("Specialty :");
                messagePhysician.add(speciality);

                /*String specialityOther = "";
                if (s.getOtherType() != null) {
                    specialityOther = s.getOtherType();
                }
                cell = new PdfPCell(new Phrase("Other Speciality:" + specialityOther));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePhysician.add("Other Speciality :");
                messagePhysician.add(specialityOther);*/

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
                messagePhysician.add("Name :");
                messagePhysician.add(name);

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
                messagePhysician.add("Office Phone :");
                messagePhysician.add(officePhone);

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
                messagePhysician.add("After Hours Phone :");
                messagePhysician.add(afterHoursPhone);

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
                messagePhysician.add("Other Phone :");
                messagePhysician.add(otherPhone);

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
                messagePhysician.add("Office Fax :");
                messagePhysician.add(officeFax);


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
                messagePhysician.add("Address :");
                messagePhysician.add(address);

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
                messagePhysician.add("Website :");
                messagePhysician.add(website);

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
                messagePhysician.add("Medical Practice Name :");
                messagePhysician.add(medicalPracticeName);

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
                messagePhysician.add("Hospital Affiliations :");
                messagePhysician.add(hospitalAffiliations);

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
                messagePhysician.add("Network Status :");
                messagePhysician.add(networkStatus);

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
                messagePhysician.add("Last Visit :");
                messagePhysician.add(lastSeen);

                String locator = "";
                if (s.getLocator() != null) {
                    locator = s.getLocator();
                }
                cell = new PdfPCell(new Phrase("Electronic Health Record (add web address, username and password) :" + locator));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePhysician.add("Electronic Health Record (add web address, username and password)  :");
                messagePhysician.add(locator);

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
                messagePhysician.add("Notes :");
                messagePhysician.add(note);

                String ascard = "";
                if (s.getHas_card() != null) {
                    ascard = s.getHas_card();
                }
                cell = new PdfPCell(new Phrase("Do you have a business card:"+ascard));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messagePhysician.add("Do you have a business card?");
                messagePhysician.add(ascard);


                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messagePhysician.add("");
                messagePhysician.add("");

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
            Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Individual(ArrayList<Proxy> proxyList) {
        try {
            Header.addEmptyLine(1);
            Header.addChank("Health Care Proxy Agent");
            messageProxy.add("Health Care Proxy Agent");
            Header.addEmptyLine(1);

//        Header.widths[0] = 0.15f;
//        Header.widths[1] = 0.85f;
//        Header.table = new PdfPTable(Header.widths);
//        Header.table.getDefaultCell().setBorder(Rectangle.BOTTOM);

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);

            for (int i = 0; i < proxyList.size(); i++) {
                int k = i + 1;
                cell = new PdfPCell(new Phrase("Health Care Proxy Agent " + k + ":"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageProxy.add("Health Care Proxy Agent " + k + " :");
                messageProxy.add("");

                Proxy p = proxyList.get(i);

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


                messageProxy.add("Name :");
                messageProxy.add(name);

                String relationShip = "";
                if (p.getRelationType() != null) {
                    if (p.getRelationType().equals("Other")) {
                        relationShip = p.getRelationType() + " - " + p.getOtherRelation();
                    } else {
                        relationShip = p.getRelationType();
                    }
                }
                cell = new PdfPCell(new Phrase("Relationship:" + relationShip));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageProxy.add("Relationship :");
                messageProxy.add(relationShip);

                String mobile = "";
                if (p.getMobile() != null) {
                    mobile = p.getMobile();
                }
                cell = new PdfPCell(new Phrase("Mobile Number:" + mobile));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageProxy.add("Mobile Number :");
                messageProxy.add(mobile);

                String homePhone = "";
                if (p.getWorkPhone() != null) {
                    homePhone = p.getWorkPhone();
                }
                cell = new PdfPCell(new Phrase("Home Phone:" + homePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageProxy.add("Home Phone :");
                messageProxy.add(homePhone);

                String officePhone = "";
                if (p.getPhone() != null) {
                    officePhone = p.getPhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageProxy.add("Office Phone :");
                messageProxy.add(officePhone);

                String email = "";
                if (p.getEmail() != null) {
                    email = p.getEmail();
                }
                cell = new PdfPCell(new Phrase("Email Address:" + email));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageProxy.add("Email Address :");
                messageProxy.add(email);

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

                messageProxy.add("Address :");
                messageProxy.add(address);

                String priority = "";
                if (p.getIsPrimary() == 0) {
                    priority = "Primary";
                } else {
                    priority = "Successor";
                }
                cell = new PdfPCell(new Phrase("Proxy Agent Priority:" + priority));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageProxy.add("Proxy Agent Priority :");
                messageProxy.add(priority);

            }
//        Header.table.setWidthPercentage(100f);


            Header.document.add(table);
            Paragraph p = new Paragraph(" ");
            DottedLineSeparator line = new DottedLineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.BLACK);
            p.add(line);
            Header.document.add(p);
            Header.addEmptyLine(1);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Individual(MedInfo medInfo, ArrayList<Allergy> allargyLists, ArrayList<Implant> implantsList, ArrayList<History> historList, ArrayList<String> hospitalList, ArrayList<String> conditionList, ArrayList<Vaccine> vaccineList) {
        try {
            // Header.addEmptyLine(1);
            String preNote = "";
            String glass = "";
            String lense = "";
            String blind = "";
            String upper = "";
            String lower = "";
            String visionNote = "";
            Header.addChank("Medical Profile");
            messageInfo3.add("Medical Profile");
            Header.addEmptyLine(1);

//        Header.widths[0] = 0.15f;
//        Header.widths[1] = 0.85f;
//        Header.table = new PdfPTable(Header.widths);
//        Header.table.getDefaultCell().setBorder(Rectangle.BOTTOM);

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);


            PdfPTable table6;
            table6 = new PdfPTable(2);
            PdfPCell cell6;
            table6.setWidthPercentage(100);

            cell6 = new PdfPCell(new Phrase("Allergies:" + ""));
            cell6.setBorder(Rectangle.BOTTOM);
            cell6.setUseBorderPadding(true);
            cell6.setBorderWidthBottom(5);
            cell6.setBorderColorBottom(BaseColor.WHITE);

            table6.addCell(cell6);

            messageInfo3.add("Allergies :");
            messageInfo3.add("");

            cell6 = new PdfPCell(new Phrase(""));
            cell6.setBorder(Rectangle.BOTTOM);
            cell6.setUseBorderPadding(true);
            cell6.setBorderWidthBottom(5);
            cell6.setBorderColorBottom(BaseColor.WHITE);
            table6.addCell(cell6);
            messageInfo3.add("");
            messageInfo3.add("");

            String allergy = "";
            String treatment = "";
            String reaction = "";
            for (int i = 0; i < allargyLists.size(); i++) {
                int k = i + 1;

               /* cell = new PdfPCell(new Phrase("Allergy " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Allergy " + k + " :");
                messageInfo3.add("");*/

                Allergy a = allargyLists.get(i);
                if (a.getAllergy() != null) {
                    allergy = a.getAllergy();
                }
                cell6 = new PdfPCell(new Phrase("Allergy:" + allergy));
                cell6.setBorder(Rectangle.BOTTOM);
                cell6.setUseBorderPadding(true);
                cell6.setBorderWidthBottom(5);
                cell6.setBorderColorBottom(BaseColor.WHITE);
                table6.addCell(cell6);

                messageInfo3.add("Allergy :");
                messageInfo3.add(allergy);

                if (a.getReaction() != null) {
                    reaction = a.getReaction();
                    if (reaction.equals("Other")) {
                        reaction = reaction + " - " + a.getOtherReaction();
                    }
                }
                cell6 = new PdfPCell(new Phrase("Reaction:" + reaction));
                cell6.setBorder(Rectangle.BOTTOM);
                cell6.setUseBorderPadding(true);
                cell6.setBorderWidthBottom(5);
                cell6.setBorderColorBottom(BaseColor.WHITE);
                table6.addCell(cell6);

                messageInfo3.add("Reaction :");
                messageInfo3.add(reaction);

                if (a.getTreatment() != null) {
                    treatment = a.getTreatment();
                }
                cell6 = new PdfPCell(new Phrase("Treatment:" + treatment));
                cell6.setBorder(Rectangle.BOTTOM);
                cell6.setUseBorderPadding(true);
                cell6.setBorderWidthBottom(5);
                cell6.setBorderColorBottom(BaseColor.WHITE);
                table6.addCell(cell6);

                messageInfo3.add("Treatment :");
                messageInfo3.add(treatment);

                cell6 = new PdfPCell(new Phrase(""));
                cell6.setBorder(Rectangle.BOTTOM);
                cell6.setUseBorderPadding(true);
                cell6.setBorderWidthBottom(5);
                cell6.setBorderColorBottom(BaseColor.WHITE);
                table6.addCell(cell6);

                messageInfo3.add("");
                messageInfo3.add("");

                cell6 = new PdfPCell(new Phrase(""));
                cell6.setBorder(Rectangle.BOTTOM);
                cell6.setUseBorderPadding(true);
                cell6.setBorderWidthBottom(5);
                cell6.setBorderColorBottom(BaseColor.WHITE);
                table6.addCell(cell6);

                messageInfo3.add("");
                messageInfo3.add("");

                cell6 = new PdfPCell(new Phrase(""));
                cell6.setBorder(Rectangle.BOTTOM);
                cell6.setUseBorderPadding(true);
                cell6.setBorderWidthBottom(5);
                cell6.setBorderColorBottom(BaseColor.WHITE);
                table6.addCell(cell6);

                messageInfo3.add("");
                messageInfo3.add("");

            }

            cell6 = new PdfPCell(new Phrase("Allergy Note:" + medInfo.getAllergyNote()));
            cell6.setBorder(Rectangle.BOTTOM);
            cell6.setUseBorderPadding(true);
            cell6.setBorderWidthBottom(5);
            cell6.setBorderColorBottom(BaseColor.WHITE);
            table6.addCell(cell6);

            messageInfo3.add("Allergy Note :");
            messageInfo3.add(medInfo.getAllergyNote());

            cell6 = new PdfPCell(new Phrase(""));
            cell6.setBorder(Rectangle.BOTTOM);
            cell6.setUseBorderPadding(true);
            cell6.setBorderWidthBottom(5);
            cell6.setBorderColorBottom(BaseColor.WHITE);
            table6.addCell(cell6);

            messageInfo3.add("");
            messageInfo3.add("");


            Header.document.add(table6);
            Paragraph p6 = new Paragraph(" ");
            DottedLineSeparator line6 = new DottedLineSeparator();
            line6.setOffset(-4);
            line6.setLineColor(BaseColor.BLACK);
            p6.add(line6);
            Header.document.add(p6);
            Header.addEmptyLine(1);


            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);

            cell1 = new PdfPCell(new Phrase("Pre Existing Medical Conditions " + ":"));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo3.add("Pre Existing Medical Conditions " + ":");
            messageInfo3.add("");

            cell1 = new PdfPCell(new Phrase(""));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo3.add("");
            messageInfo3.add("");
            for (int i = 0; i < conditionList.size(); i++) {
                int k = i + 1;


                cell1 = new PdfPCell(new Phrase("Medical Conditions" + " :" + conditionList.get(i)));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageInfo3.add("Medical Conditions" + " :");
                messageInfo3.add(conditionList.get(i));

            }

            if (medInfo.getNote() != null) {
                preNote = medInfo.getNote();
            }
            cell1 = new PdfPCell(new Phrase("Medical Condition Note:" + preNote));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setUseBorderPadding(true);
            cell1.setBorderWidthBottom(5);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            table1.addCell(cell1);

            messageInfo3.add("Medical Condition Note :");
            messageInfo3.add(preNote);

            if ((conditionList.size() + 1) % 2 != 0) {

                cell1 = new PdfPCell(new Phrase(""));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageInfo3.add("");
                messageInfo3.add("");
            }

            Header.document.add(table1);
            Paragraph p1 = new Paragraph(" ");
            DottedLineSeparator line1 = new DottedLineSeparator();
            line1.setOffset(-4);
            line1.setLineColor(BaseColor.BLACK);
            p1.add(line1);
            Header.document.add(p1);
            Header.addEmptyLine(1);


            PdfPTable table7;
            table7 = new PdfPTable(2);
            PdfPCell cell7;
            table7.setWidthPercentage(100);

            // String Implants="";
            cell7 = new PdfPCell(new Phrase("Medical Implants :" + ""));
            cell7.setBorder(Rectangle.BOTTOM);
            cell7.setUseBorderPadding(true);
            cell7.setBorderWidthBottom(5);
            cell7.setBorderColorBottom(BaseColor.WHITE);
            table7.addCell(cell7);

            messageInfo3.add("Medical Implants :");
            messageInfo3.add("");

            cell7 = new PdfPCell(new Phrase(""));
            cell7.setBorder(Rectangle.BOTTOM);
            cell7.setUseBorderPadding(true);
            cell7.setBorderWidthBottom(5);
            cell7.setBorderColorBottom(BaseColor.WHITE);
            table7.addCell(cell7);
            messageInfo3.add("");
            messageInfo3.add("");

            for (int i = 0; i < implantsList.size(); i++) {
               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Medical Implants " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInfo3.add("Medical Implants " + k + " :");
                messageInfo3.add("");*/
                Implant a = implantsList.get(i);
                String name = "";
                if (a.getName() != null) {
                    name = a.getName();
                    if (name.equals("Other")) {
                        name = name + " - " + a.getOther();
                    }
                    if (name.equals("Joint Replacements (specify)")) {
                        name = name + " - " + a.getOther();
                    }
                }

                cell7 = new PdfPCell(new Phrase("Implants:" + name));
                cell7.setBorder(Rectangle.BOTTOM);
                cell7.setUseBorderPadding(true);
                cell7.setBorderWidthBottom(5);
                cell7.setBorderColorBottom(BaseColor.WHITE);
                table7.addCell(cell7);
                messageInfo3.add("Implants :");
                messageInfo3.add(name);

                String date = "";
                if (a.getDate() != null) {
                    date = a.getDate();
                }
                cell7 = new PdfPCell(new Phrase("Date:" + date));
                cell7.setBorder(Rectangle.BOTTOM);
                cell7.setUseBorderPadding(true);
                cell7.setBorderWidthBottom(5);
                cell7.setBorderColorBottom(BaseColor.WHITE);
                table7.addCell(cell7);
                messageInfo3.add("Date :");
                messageInfo3.add(date);

                String loc = "";
                if (a.getLocation() != null) {
                    loc = a.getLocation();
                }
                cell7 = new PdfPCell(new Phrase("Location:" + loc));
                cell7.setBorder(Rectangle.BOTTOM);
                cell7.setUseBorderPadding(true);
                cell7.setBorderWidthBottom(5);
                cell7.setBorderColorBottom(BaseColor.WHITE);
                table7.addCell(cell7);
                messageInfo3.add("Location :");
                messageInfo3.add(loc);

                String details = "";
                if (a.getDetails() != null) {
                    details = a.getDetails();
                }
                cell7 = new PdfPCell(new Phrase("Details:" + details));
                cell7.setBorder(Rectangle.BOTTOM);
                cell7.setUseBorderPadding(true);
                cell7.setBorderWidthBottom(5);
                cell7.setBorderColorBottom(BaseColor.WHITE);
                table7.addCell(cell7);
                messageInfo3.add("Details :");
                messageInfo3.add(details);

                String note = "";
                if (a.getNotes() != null) {
                    note = a.getNotes();
                }
                cell7 = new PdfPCell(new Phrase("Note:" + note));
                cell7.setBorder(Rectangle.BOTTOM);
                cell7.setUseBorderPadding(true);
                cell7.setBorderWidthBottom(5);
                cell7.setBorderColorBottom(BaseColor.WHITE);
                table7.addCell(cell7);
                messageInfo3.add("Note :");
                messageInfo3.add(note);

                cell7 = new PdfPCell(new Phrase(""));
                cell7.setBorder(Rectangle.BOTTOM);
                cell7.setUseBorderPadding(true);
                cell7.setBorderWidthBottom(5);
                cell7.setBorderColorBottom(BaseColor.WHITE);
                table7.addCell(cell7);
                messageInfo3.add("");
                messageInfo3.add("");

            }
            if ((implantsList.size()) % 2 != 0) {

                cell7 = new PdfPCell(new Phrase(""));
                cell7.setBorder(Rectangle.BOTTOM);
                cell7.setUseBorderPadding(true);
                cell7.setBorderWidthBottom(5);
                cell7.setBorderColorBottom(BaseColor.WHITE);
                table7.addCell(cell7);

                messageInfo3.add("");
                messageInfo3.add("");
            }

            String impNote= "";
            if (medInfo.getImplantnote() != null) {
                impNote = medInfo.getImplantnote();
            }
            cell7 = new PdfPCell(new Phrase("Medical Implants Note:" + impNote));
            cell7.setBorder(Rectangle.BOTTOM);
            cell7.setUseBorderPadding(true);
            cell7.setBorderWidthBottom(5);
            cell7.setBorderColorBottom(BaseColor.WHITE);
            table7.addCell(cell7);

            messageInfo3.add("Medical Implants Note :");
            messageInfo3.add(impNote);

            cell7 = new PdfPCell(new Phrase(""));
            cell7.setBorder(Rectangle.BOTTOM);
            cell7.setUseBorderPadding(true);
            cell7.setBorderWidthBottom(5);
            cell7.setBorderColorBottom(BaseColor.WHITE);
            table7.addCell(cell7);

            Header.document.add(table7);
            Paragraph p7 = new Paragraph(" ");
            DottedLineSeparator line7 = new DottedLineSeparator();
            line7.setOffset(-4);
            line7.setLineColor(BaseColor.BLACK);
            p7.add(line7);
            Header.document.add(p7);
            Header.addEmptyLine(1);


            PdfPTable table10;
            table10 = new PdfPTable(2);
            PdfPCell cell10;
            table10.setWidthPercentage(100);

            cell10 = new PdfPCell(new Phrase("Surgical/Hospitalization History:" + ""));
            cell10.setBorder(Rectangle.BOTTOM);
            cell10.setUseBorderPadding(true);
            cell10.setBorderWidthBottom(5);
            cell10.setBorderColorBottom(BaseColor.WHITE);
            table10.addCell(cell10);

            messageInfo3.add("Surgical/Hospitalization History :");
            messageInfo3.add("");

            cell10 = new PdfPCell(new Phrase(""));
            cell10.setBorder(Rectangle.BOTTOM);
            cell10.setUseBorderPadding(true);
            cell10.setBorderWidthBottom(5);
            cell10.setBorderColorBottom(BaseColor.WHITE);
            table10.addCell(cell10);
            messageInfo3.add("");
            messageInfo3.add("");
            for (int i = 0; i < historList.size(); i++) {
              /*  int k = i + 1;

                cell = new PdfPCell(new Phrase("Surgical/Hospitalization History " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Surgical/Hospitalization History " + k + " :");
                messageInfo3.add("");*/
                History a = historList.get(i);
                String name = "";
                if (a.getName() != null) {
                    name = a.getName();
                    if (name.equals("Other")) {
                        name = name + " - " + a.getOther();
                    }
                }
                cell10 = new PdfPCell(new Phrase("History:" + name));
                cell10.setBorder(Rectangle.BOTTOM);
                cell10.setUseBorderPadding(true);
                cell10.setBorderWidthBottom(5);
                cell10.setBorderColorBottom(BaseColor.WHITE);
                table10.addCell(cell10);

                messageInfo3.add("History :");
                messageInfo3.add(name);

                cell10 = new PdfPCell(new Phrase("Date:" + historList.get(i).getDate()));
                cell10.setBorder(Rectangle.BOTTOM);
                cell10.setUseBorderPadding(true);
                cell10.setBorderWidthBottom(5);
                cell10.setBorderColorBottom(BaseColor.WHITE);
                table10.addCell(cell10);

                messageInfo3.add("Date :");
                messageInfo3.add(historList.get(i).getDate());


                cell10 = new PdfPCell(new Phrase("Doctor:" + historList.get(i).getDoctor()));
                cell10.setBorder(Rectangle.BOTTOM);
                cell10.setUseBorderPadding(true);
                cell10.setBorderWidthBottom(5);
                cell10.setBorderColorBottom(BaseColor.WHITE);
                table10.addCell(cell10);

                messageInfo3.add("Doctor :");
                messageInfo3.add(historList.get(i).getDoctor());


                cell10 = new PdfPCell(new Phrase("Location:" + historList.get(i).getDone()));
                cell10.setBorder(Rectangle.BOTTOM);
                cell10.setUseBorderPadding(true);
                cell10.setBorderWidthBottom(5);
                cell10.setBorderColorBottom(BaseColor.WHITE);
                table10.addCell(cell10);

                messageInfo3.add("Location :");
                messageInfo3.add(historList.get(i).getDone());

                cell10 = new PdfPCell(new Phrase(""));
                cell10.setBorder(Rectangle.BOTTOM);
                cell10.setUseBorderPadding(true);
                cell10.setBorderWidthBottom(5);
                cell10.setBorderColorBottom(BaseColor.WHITE);
                table10.addCell(cell10);
                messageInfo3.add("");
                messageInfo3.add("");

                cell10 = new PdfPCell(new Phrase(""));
                cell10.setBorder(Rectangle.BOTTOM);
                cell10.setUseBorderPadding(true);
                cell10.setBorderWidthBottom(5);
                cell10.setBorderColorBottom(BaseColor.WHITE);
                table10.addCell(cell10);
                messageInfo3.add("");
                messageInfo3.add("");
            }

            String surNote= "";
            if (medInfo.getImplantnote() != null) {
                surNote = medInfo.getImplantnote();
            }
            cell10 = new PdfPCell(new Phrase("Surgical/Hospitalization History Note:" + surNote));
            cell10.setBorder(Rectangle.BOTTOM);
            cell10.setUseBorderPadding(true);
            cell10.setBorderWidthBottom(5);
            cell10.setBorderColorBottom(BaseColor.WHITE);
            table10.addCell(cell10);

            messageInfo3.add("Surgical/Hospitalization History Note :");
            messageInfo3.add(surNote);

            cell10 = new PdfPCell(new Phrase(""));
            cell10.setBorder(Rectangle.BOTTOM);
            cell10.setUseBorderPadding(true);
            cell10.setBorderWidthBottom(5);
            cell10.setBorderColorBottom(BaseColor.WHITE);
            table10.addCell(cell10);

            Header.document.add(table10);
            Paragraph p10 = new Paragraph(" ");
            DottedLineSeparator line10 = new DottedLineSeparator();
            line10.setOffset(-4);
            line10.setLineColor(BaseColor.BLACK);
            p10.add(line10);
            Header.document.add(p10);
            Header.addEmptyLine(1);
//        Header.table.setWidthPercentage(100f);


            PdfPTable table9;
            table9 = new PdfPTable(2);
            PdfPCell cell9;
            table9.setWidthPercentage(100);

            cell9 = new PdfPCell(new Phrase("Preferred Hospital:" + ""));
            cell9.setBorder(Rectangle.BOTTOM);
            cell9.setUseBorderPadding(true);
            cell9.setBorderWidthBottom(5);
            cell9.setBorderColorBottom(BaseColor.WHITE);
            table9.addCell(cell9);

            messageInfo3.add("Preferred Hospitals :");
            messageInfo3.add("");

            cell9 = new PdfPCell(new Phrase(""));
            cell9.setBorder(Rectangle.BOTTOM);
            cell9.setUseBorderPadding(true);
            cell9.setBorderWidthBottom(5);
            cell9.setBorderColorBottom(BaseColor.WHITE);
            table9.addCell(cell9);
            messageInfo3.add("");
            messageInfo3.add("");
            for (int i = 0; i < hospitalList.size(); i++) {
                int k = i + 1;
/*
                cell = new PdfPCell(new Phrase("Preferred Hospital " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageInfo3.add("Preferred Hospital " + k + " :");
                messageInfo3.add("");*/

                cell9 = new PdfPCell(new Phrase("Hospital:" + hospitalList.get(i)));
                cell9.setBorder(Rectangle.BOTTOM);
                cell9.setUseBorderPadding(true);
                cell9.setBorderWidthBottom(5);
                cell9.setBorderColorBottom(BaseColor.WHITE);
                table9.addCell(cell9);

                messageInfo3.add("Hospital :");
                messageInfo3.add(hospitalList.get(i));
                cell9 = new PdfPCell(new Phrase(""));
                cell9.setBorder(Rectangle.BOTTOM);
                cell9.setUseBorderPadding(true);
                cell9.setBorderWidthBottom(5);
                cell9.setBorderColorBottom(BaseColor.WHITE);
                table9.addCell(cell9);

                messageInfo3.add("");
                messageInfo3.add("");
            }

            if ((hospitalList.size()) % 2 != 0) {

                cell9 = new PdfPCell(new Phrase(""));
                cell9.setBorder(Rectangle.BOTTOM);
                cell9.setUseBorderPadding(true);
                cell9.setBorderWidthBottom(5);
                cell9.setBorderColorBottom(BaseColor.WHITE);
                table9.addCell(cell9);

                messageInfo3.add("");
                messageInfo3.add("");
            }

            Header.document.add(table9);
            Paragraph p9 = new Paragraph(" ");
            DottedLineSeparator line9 = new DottedLineSeparator();
            line9.setOffset(-4);
            line9.setLineColor(BaseColor.BLACK);
            p9.add(line9);
            Header.document.add(p9);
            Header.addEmptyLine(1);


            PdfPTable table5;
            table5 = new PdfPTable(2);
            PdfPCell cell5;
            table5.setWidthPercentage(100);

          /*  cell = new PdfPCell(new Phrase("Functional Status:" + ""));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageInfo3.add("Functional Status :");
            messageInfo3.add("");

            String feeding = "";
            if (medInfo.getFeed() != null) {
                feeding = medInfo.getFeed();
            }
            cell = new PdfPCell(new Phrase("Feeding:" + feeding));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageInfo3.add("Feeding :");
            messageInfo3.add(feeding);

            String toileting = "";
            if (medInfo.getToilet() != null) {
                toileting = medInfo.getToilet();
            }
            cell = new PdfPCell(new Phrase("Toileting:" + toileting));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageInfo3.add("Toileting :");
            messageInfo3.add(toileting);

            String selfMedicate = "";
            if (medInfo.getMedicate() != null) {
                selfMedicate = medInfo.getMedicate();
            }
            cell = new PdfPCell(new Phrase("Self Medicate:" + selfMedicate));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageInfo3.add("Self Medicate :");
            messageInfo3.add(selfMedicate);

            String functionNote = "";
            if (medInfo.getFunctionnote() != null) {
                functionNote = medInfo.getFunctionnote();
            }
            cell = new PdfPCell(new Phrase("Function Notes:" + functionNote));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageInfo3.add("Function Notes :");
            messageInfo3.add(functionNote);*/

            /*cell5 = new PdfPCell(new Phrase("Diet:" + ""));
            cell5.setBorder(Rectangle.BOTTOM);
            cell5.setUseBorderPadding(true);
            cell5.setBorderWidthBottom(5);
            cell5.setBorderColorBottom(BaseColor.WHITE);
            table5.addCell(cell5);

            messageInfo3.add("Diet :");
            messageInfo3.add("");*/


            String dietNote = "";
            if (medInfo.getDietNote() != null) {
                dietNote = medInfo.getDietNote();
            }
            cell5 = new PdfPCell(new Phrase("Diet Notes:" + dietNote));
            cell5.setBorder(Rectangle.BOTTOM);
            cell5.setUseBorderPadding(true);
            cell5.setBorderWidthBottom(5);
            cell5.setBorderColorBottom(BaseColor.WHITE);
            table5.addCell(cell5);

            messageInfo3.add("Diet Notes :");
            messageInfo3.add(dietNote);

            String bloodNote = "";
            if (medInfo.getBloodType() != null) {
                bloodNote = medInfo.getBloodType();
            }
            cell5 = new PdfPCell(new Phrase("Blood Type:" + bloodNote));
            cell5.setBorder(Rectangle.BOTTOM);
            cell5.setUseBorderPadding(true);
            cell5.setBorderWidthBottom(5);
            cell5.setBorderColorBottom(BaseColor.WHITE);
            table5.addCell(cell5);

            messageInfo3.add("Blood Type :");
            messageInfo3.add(bloodNote);


            String organDonor = "";
            if (medInfo.getDonor() != null) {
                organDonor = medInfo.getDonor();
            }
            cell5 = new PdfPCell(new Phrase("Organ Donor:" + organDonor));
            cell5.setBorder(Rectangle.BOTTOM);
            cell5.setUseBorderPadding(true);
            cell5.setBorderWidthBottom(5);
            cell5.setBorderColorBottom(BaseColor.WHITE);
            table5.addCell(cell5);

            messageInfo3.add("Organ Donor :");
            messageInfo3.add(organDonor);

            cell5 = new PdfPCell(new Phrase("" ));
            cell5.setBorder(Rectangle.BOTTOM);
            cell5.setUseBorderPadding(true);
            cell5.setBorderWidthBottom(5);
            cell5.setBorderColorBottom(BaseColor.WHITE);
            table5.addCell(cell5);

            messageInfo3.add(" ");
            messageInfo3.add(organDonor);


            Header.document.add(table5);
            Paragraph p5 = new Paragraph(" ");
            DottedLineSeparator line5 = new DottedLineSeparator();
            line5.setOffset(-4);
            line5.setLineColor(BaseColor.BLACK);
            p5.add(line5);
            Header.document.add(p5);
            Header.addEmptyLine(1);


            PdfPTable table3;
            table3 = new PdfPTable(2);
            PdfPCell cell3;
            table3.setWidthPercentage(100);


            cell3 = new PdfPCell(new Phrase("Dental:" + ""));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo3.add("Dental ");
            messageInfo3.add("");

            cell3 = new PdfPCell(new Phrase(""));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);
            messageInfo3.add("");
            messageInfo3.add("");

            if (medInfo.getFalses() != null) {
                upper = medInfo.getFalses();
            }
            cell3 = new PdfPCell(new Phrase("Dentures- Removable Upper:" + upper));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo3.add("Dentures- Removable Upper :");
            messageInfo3.add(upper);

            if (medInfo.getImplants() != null) {
                lower = medInfo.getImplants();
            }
            cell3 = new PdfPCell(new Phrase("Dentures- Removable Lower:" + lower));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo3.add("Dentures- Removable Lower :");
            messageInfo3.add(lower);

            String drymouth = "";
            if (medInfo.getMouth() != null) {
                drymouth = medInfo.getMouth();
            }
            cell3 = new PdfPCell(new Phrase("Dry Mouth:" + drymouth));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo3.add("Dry Mouth :");
            messageInfo3.add(drymouth);

            String mouthnote = "";
            if (medInfo.getMouthnote() != null) {
                mouthnote = medInfo.getMouthnote();
            }
            cell3 = new PdfPCell(new Phrase("Notes:" + mouthnote));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setUseBorderPadding(true);
            cell3.setBorderWidthBottom(5);
            cell3.setBorderColorBottom(BaseColor.WHITE);
            table3.addCell(cell3);

            messageInfo3.add("Notes :");
            messageInfo3.add(mouthnote);

            Header.document.add(table3);
            Paragraph p3 = new Paragraph(" ");
            DottedLineSeparator line3 = new DottedLineSeparator();
            line3.setOffset(-4);
            line3.setLineColor(BaseColor.BLACK);
            p3.add(line3);
            Header.document.add(p3);
            Header.addEmptyLine(1);


            PdfPTable table4;
            table4 = new PdfPTable(2);
            PdfPCell cell4;
            table4.setWidthPercentage(100);

            cell4 = new PdfPCell(new Phrase("Hearing & Speech:" + ""));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setUseBorderPadding(true);
            cell4.setBorderWidthBottom(5);
            cell4.setBorderColorBottom(BaseColor.WHITE);
            table4.addCell(cell4);

            messageInfo3.add("Hearing & Speech :");
            messageInfo3.add("");

            cell4 = new PdfPCell(new Phrase(""));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setUseBorderPadding(true);
            cell4.setBorderWidthBottom(5);
            cell4.setBorderColorBottom(BaseColor.WHITE);
            table4.addCell(cell4);
            messageInfo3.add("");
            messageInfo3.add("");

            String aid = "";
            if (medInfo.getAid() != null) {
                aid = medInfo.getAid();
            }
            cell4 = new PdfPCell(new Phrase("Hearing Aid(s):" + aid));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setUseBorderPadding(true);
            cell4.setBorderWidthBottom(5);
            cell4.setBorderColorBottom(BaseColor.WHITE);
            table4.addCell(cell4);

            messageInfo3.add("Hearing Aid(s) :");
            messageInfo3.add(aid);

            String speech = "";
            if (medInfo.getSpeech() != null) {
                speech = medInfo.getSpeech();
            }
            cell4 = new PdfPCell(new Phrase("Speech Impaired:" + speech));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setUseBorderPadding(true);
            cell4.setBorderWidthBottom(5);
            cell4.setBorderColorBottom(BaseColor.WHITE);
            table4.addCell(cell4);

            messageInfo3.add("Speech Impaired :");
            messageInfo3.add(speech);

            String aidNote = "";
            if (medInfo.getAideNote() != null) {
                aidNote = medInfo.getAideNote();
            }
            cell4 = new PdfPCell(new Phrase("Notes:" + aidNote));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setUseBorderPadding(true);
            cell4.setBorderWidthBottom(5);
            cell4.setBorderColorBottom(BaseColor.WHITE);
            table4.addCell(cell4);

            messageInfo3.add("Notes :");
            messageInfo3.add(aidNote);

            cell4 = new PdfPCell(new Phrase(""));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setUseBorderPadding(true);
            cell4.setBorderWidthBottom(5);
            cell4.setBorderColorBottom(BaseColor.WHITE);
            table4.addCell(cell4);
            messageInfo3.add("");
            messageInfo3.add("");

            Header.document.add(table4);
            Paragraph p4 = new Paragraph(" ");
            DottedLineSeparator line4 = new DottedLineSeparator();
            line4.setOffset(-4);
            line4.setLineColor(BaseColor.BLACK);
            p4.add(line4);
            Header.document.add(p4);
            Header.addEmptyLine(1);


            PdfPTable table8;
            table8 = new PdfPTable(2);
            PdfPCell cell8;
            table8.setWidthPercentage(100);
            // String Implants="";
            cell8 = new PdfPCell(new Phrase("Immunizations/Vaccines :" + ""));
            cell8.setBorder(Rectangle.BOTTOM);
            cell8.setUseBorderPadding(true);
            cell8.setBorderWidthBottom(5);
            cell8.setBorderColorBottom(BaseColor.WHITE);
            table8.addCell(cell8);

            messageInfo3.add("Immunizations/Vaccines :");
            messageInfo3.add("");

            cell8 = new PdfPCell(new Phrase(""));
            cell8.setBorder(Rectangle.BOTTOM);
            cell8.setUseBorderPadding(true);
            cell8.setBorderWidthBottom(5);
            cell8.setBorderColorBottom(BaseColor.WHITE);
            table8.addCell(cell8);
            messageInfo3.add("");
            messageInfo3.add("");

            for (int i = 0; i < vaccineList.size(); i++) {
                /*int k = i + 1;
                cell = new PdfPCell(new Phrase("Immunizations/Vaccines " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInfo3.add("Immunizations/Vaccines " + k + " :");
                messageInfo3.add("");*/
                Vaccine a = vaccineList.get(i);
                String name = "";
                if (a.getName() != null) {
                    name = a.getName();
                    if (name.equals("Other")) {
                        name = name + " - " + a.getOther();
                    }
                }

                cell8 = new PdfPCell(new Phrase("Immunization/Vaccine:" + name));
                cell8.setBorder(Rectangle.BOTTOM);
                cell8.setUseBorderPadding(true);
                cell8.setBorderWidthBottom(5);
                cell8.setBorderColorBottom(BaseColor.WHITE);
                table8.addCell(cell8);
                messageInfo3.add("Immunization/Vaccine :");
                messageInfo3.add(name);

            /*    cell8 = new PdfPCell(new Phrase("Other Immunization/Vaccine:" + vaccineList.get(i).getOther()));
                cell8.setBorder(Rectangle.BOTTOM);
                cell8.setUseBorderPadding(true);
                cell8.setBorderWidthBottom(5);
                cell8.setBorderColorBottom(BaseColor.WHITE);
                table8.addCell(cell8);
                messageInfo3.add("Other Immunization/Vaccine :");
                messageInfo3.add(vaccineList.get(i).getOther());
*/
                cell8 = new PdfPCell(new Phrase("Note (Approximate Date, Location, Details):" + vaccineList.get(i).getDate()));
                cell8.setBorder(Rectangle.BOTTOM);
                cell8.setUseBorderPadding(true);
                cell8.setBorderWidthBottom(5);
                cell8.setBorderColorBottom(BaseColor.WHITE);
                table8.addCell(cell8);
                messageInfo3.add("Note (Approximate Date, Location, Details) :");
                messageInfo3.add(vaccineList.get(i).getDate());

                cell8 = new PdfPCell(new Phrase(""));
                cell8.setBorder(Rectangle.BOTTOM);
                cell8.setUseBorderPadding(true);
                cell8.setBorderWidthBottom(5);
                cell8.setBorderColorBottom(BaseColor.WHITE);
                table8.addCell(cell8);
                messageInfo3.add("");
                messageInfo3.add("");

                cell8 = new PdfPCell(new Phrase(""));
                cell8.setBorder(Rectangle.BOTTOM);
                cell8.setUseBorderPadding(true);
                cell8.setBorderWidthBottom(5);
                cell8.setBorderColorBottom(BaseColor.WHITE);
                table8.addCell(cell8);
                messageInfo3.add("");
                messageInfo3.add("");

               /* cell8 = new PdfPCell(new Phrase(""));
                cell8.setBorder(Rectangle.BOTTOM);
                cell8.setUseBorderPadding(true);
                cell8.setBorderWidthBottom(5);
                cell8.setBorderColorBottom(BaseColor.WHITE);
                table8.addCell(cell8);
                messageInfo3.add("");
                messageInfo3.add("");*/
            }

            if ((vaccineList.size()) % 2 != 0) {

                cell8 = new PdfPCell(new Phrase(""));
                cell8.setBorder(Rectangle.BOTTOM);
                cell8.setUseBorderPadding(true);
                cell8.setBorderWidthBottom(5);
                cell8.setBorderColorBottom(BaseColor.WHITE);
                table8.addCell(cell8);

                messageInfo3.add("");
                messageInfo3.add("");
            }

            String vacNote= "";
            if (medInfo.getVaccinenote() != null) {
                vacNote = medInfo.getVaccinenote();
            }
            cell8 = new PdfPCell(new Phrase("Immunization Note:" + vacNote));
            cell8.setBorder(Rectangle.BOTTOM);
            cell8.setUseBorderPadding(true);
            cell8.setBorderWidthBottom(5);
            cell8.setBorderColorBottom(BaseColor.WHITE);
            table8.addCell(cell8);

            messageInfo3.add("Immunization Note :");
            messageInfo3.add(vacNote);

            cell8 = new PdfPCell(new Phrase(""));
            cell8.setBorder(Rectangle.BOTTOM);
            cell8.setUseBorderPadding(true);
            cell8.setBorderWidthBottom(5);
            cell8.setBorderColorBottom(BaseColor.WHITE);
            table8.addCell(cell8);


            Header.document.add(table8);
            Paragraph p8 = new Paragraph(" ");
            DottedLineSeparator line8 = new DottedLineSeparator();
            line8.setOffset(-4);
            line8.setLineColor(BaseColor.BLACK);
            p8.add(line8);
            Header.document.add(p8);
            Header.addEmptyLine(1);


            PdfPTable table2;
            table2 = new PdfPTable(2);
            PdfPCell cell2;
            table2.setWidthPercentage(100);


            cell2 = new PdfPCell(new Phrase("Vision:" + ""));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);
            messageInfo3.add("Vision :");
            messageInfo3.add("");

            cell2 = new PdfPCell(new Phrase(""));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);
            messageInfo3.add("");
            messageInfo3.add("");


            if (medInfo.getGlass() != null) {
                glass = medInfo.getGlass();
            }
            cell2 = new PdfPCell(new Phrase("Glasses:" + glass));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo3.add("Glasses :");
            messageInfo3.add(glass);

            if (medInfo.getLense() != null) {
                lense = medInfo.getLense();
            }
            cell2 = new PdfPCell(new Phrase("Contact Lenses:" + lense));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo3.add("Contact Lense :");
            messageInfo3.add(lense);

            if (medInfo.getBlind() != null) {
                blind = medInfo.getBlind();
            }
            cell2 = new PdfPCell(new Phrase("Color Blind:" + blind));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo3.add("Color Blind :");
            messageInfo3.add(blind);

            if (medInfo.getVisionNote() != null) {
                visionNote = medInfo.getVisionNote();
            }
            cell2 = new PdfPCell(new Phrase("Notes:" + visionNote));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setUseBorderPadding(true);
            cell2.setBorderWidthBottom(5);
            cell2.setBorderColorBottom(BaseColor.WHITE);
            table2.addCell(cell2);

            messageInfo3.add("Notes :");
            messageInfo3.add(visionNote);

            Header.document.add(table2);
            Paragraph p2 = new Paragraph(" ");
            DottedLineSeparator line2 = new DottedLineSeparator();
            line2.setOffset(-4);
            line2.setLineColor(BaseColor.BLACK);
            p2.add(line2);
            Header.document.add(p2);
            Header.addEmptyLine(1);


            PdfPTable table16;
            table16 = new PdfPTable(2);
            PdfPCell cell16;
            table16.setWidthPercentage(100);


            String tobaco = "";
            if (medInfo.getTobaco() != null) {
                tobaco = medInfo.getTobaco();
            }
            cell16 = new PdfPCell(new Phrase("Smoking / Tobacco Use:" + tobaco));
            cell16.setBorder(Rectangle.BOTTOM);
            cell16.setUseBorderPadding(true);
            cell16.setBorderWidthBottom(5);
            cell16.setBorderColorBottom(BaseColor.WHITE);
            table16.addCell(cell16);
            messageInfo3.add("Smoking / Tobacco Use :");
            messageInfo3.add(tobaco);

            String tobacoType = "";
            if (medInfo.getT_type() != null) {
                tobacoType = medInfo.getT_type();
            }
            cell16 = new PdfPCell(new Phrase("Type:" + tobacoType));
            cell16.setBorder(Rectangle.BOTTOM);
            cell16.setUseBorderPadding(true);
            cell16.setBorderWidthBottom(5);
            cell16.setBorderColorBottom(BaseColor.WHITE);
            table16.addCell(cell16);

            messageInfo3.add("Type :");
            messageInfo3.add(tobacoType);

            String tobacoAmt = "";
            if (medInfo.getT_amt() != null) {
                tobacoAmt = medInfo.getT_amt();
            }
            cell16 = new PdfPCell(new Phrase("Amount/Frequency:" + tobacoAmt));
            cell16.setBorder(Rectangle.BOTTOM);
            cell16.setUseBorderPadding(true);
            cell16.setBorderWidthBottom(5);
            cell16.setBorderColorBottom(BaseColor.WHITE);
            table16.addCell(cell16);

            messageInfo3.add("Amount/Frequency :");
            messageInfo3.add(tobacoAmt);

            String tobacoYear = "";
            if (medInfo.getT_year() != null) {
                tobacoYear = medInfo.getT_year();
            }
            cell16 = new PdfPCell(new Phrase("Number of Years & When Stopped (if applicable):" + tobacoYear));
            cell16.setBorder(Rectangle.BOTTOM);
            cell16.setUseBorderPadding(true);
            cell16.setBorderWidthBottom(5);
            cell16.setBorderColorBottom(BaseColor.WHITE);
            table16.addCell(cell16);

            messageInfo3.add("Number of Years & When Stopped (if applicable) :");
            messageInfo3.add(tobacoYear);


            Header.document.add(table16);
            Paragraph p16 = new Paragraph(" ");
            DottedLineSeparator line16 = new DottedLineSeparator();
            line16.setOffset(-4);
            line16.setLineColor(BaseColor.BLACK);
            p16.add(line16);
            Header.document.add(p16);
            Header.addEmptyLine(1);

            /**/
            PdfPTable table17;
            table17 = new PdfPTable(2);
            PdfPCell cell17;
            table17.setWidthPercentage(100);


            String alcohol = "";
            if (medInfo.getDrink() != null) {
                alcohol = medInfo.getDrink();
            }
            cell17 = new PdfPCell(new Phrase("Alcohol:" + alcohol));
            cell17.setBorder(Rectangle.BOTTOM);
            cell17.setUseBorderPadding(true);
            cell17.setBorderWidthBottom(5);
            cell17.setBorderColorBottom(BaseColor.WHITE);
            table17.addCell(cell17);
            messageInfo3.add("Alcohol :");
            messageInfo3.add(alcohol);


            String alcoholAmt = "";
            if (medInfo.getDrink_amt() != null) {
                alcoholAmt = medInfo.getDrink_amt();
            }
            cell17 = new PdfPCell(new Phrase("Amount/Frequency/Other Notes:" + alcoholAmt));
            cell17.setBorder(Rectangle.BOTTOM);
            cell17.setUseBorderPadding(true);
            cell17.setBorderWidthBottom(5);
            cell17.setBorderColorBottom(BaseColor.WHITE);
            table17.addCell(cell17);

            messageInfo3.add("Amount/Frequency/Other Notes :");
            messageInfo3.add(alcoholAmt);

            String alcoholYear = "";
            if (medInfo.getDrink_year() != null) {
                alcoholYear = medInfo.getDrink_year();
            }
            cell17 = new PdfPCell(new Phrase("Number of Years & When Stopped (if applicable):" + alcoholYear));
            cell17.setBorder(Rectangle.BOTTOM);
            cell17.setUseBorderPadding(true);
            cell17.setBorderWidthBottom(5);
            cell17.setBorderColorBottom(BaseColor.WHITE);
            table17.addCell(cell17);

            messageInfo3.add("Number of Years & When Stopped (if applicable) :");
            messageInfo3.add(alcoholYear);

            cell17 = new PdfPCell(new Phrase(""));
            cell17.setBorder(Rectangle.BOTTOM);
            cell17.setUseBorderPadding(true);
            cell17.setBorderWidthBottom(5);
            cell17.setBorderColorBottom(BaseColor.WHITE);
            table17.addCell(cell17);

            messageInfo3.add("");
            messageInfo3.add("");

            Header.document.add(table17);
            Paragraph p17 = new Paragraph(" ");
            DottedLineSeparator line17 = new DottedLineSeparator();
            line17.setOffset(-4);
            line17.setLineColor(BaseColor.BLACK);
            p17.add(line17);
            Header.document.add(p17);
            Header.addEmptyLine(1);


            /**/
            PdfPTable table18;
            table18 = new PdfPTable(2);
            PdfPCell cell18;
            table18.setWidthPercentage(100);


            String drug = "";
            if (medInfo.getDrug() != null) {
                drug = medInfo.getDrug();
            }
            cell18 = new PdfPCell(new Phrase("Recreational Drug Use:" + drug));
            cell18.setBorder(Rectangle.BOTTOM);
            cell18.setUseBorderPadding(true);
            cell18.setBorderWidthBottom(5);
            cell18.setBorderColorBottom(BaseColor.WHITE);
            table18.addCell(cell18);
            messageInfo3.add("Recreational Drug Use :");
            messageInfo3.add(drug);


            String drugType = "";
            if (medInfo.getDrug_type() != null) {
                drugType = medInfo.getDrug_type();
            }
            cell18 = new PdfPCell(new Phrase("Type:" + drugType));
            cell18.setBorder(Rectangle.BOTTOM);
            cell18.setUseBorderPadding(true);
            cell18.setBorderWidthBottom(5);
            cell18.setBorderColorBottom(BaseColor.WHITE);
            table18.addCell(cell18);

            messageInfo3.add("Type :");
            messageInfo3.add(drugType);


            String drugAmt = "";
            if (medInfo.getDrug_amt() != null) {
                drugAmt = medInfo.getDrug_amt();
            }
            cell18 = new PdfPCell(new Phrase("Amount/Frequency/Other Notes:" + drugAmt));
            cell18.setBorder(Rectangle.BOTTOM);
            cell18.setUseBorderPadding(true);
            cell18.setBorderWidthBottom(5);
            cell18.setBorderColorBottom(BaseColor.WHITE);
            table18.addCell(cell18);

            messageInfo3.add("Amount/Frequency/Other Notes :");
            messageInfo3.add(drugAmt);


            String drugYear = "";
            if (medInfo.getDrug_year() != null) {
                drugYear = medInfo.getDrug_year();
            }
            cell18 = new PdfPCell(new Phrase("Number of Years & When Stopped (if applicable):" + drugYear));
            cell18.setBorder(Rectangle.BOTTOM);
            cell18.setUseBorderPadding(true);
            cell18.setBorderWidthBottom(5);
            cell18.setBorderColorBottom(BaseColor.WHITE);
            table18.addCell(cell18);

            messageInfo3.add("Number of Years & When Stopped (if applicable) :");
            messageInfo3.add(drugYear);


            Header.document.add(table18);
            Paragraph p18 = new Paragraph(" ");
            DottedLineSeparator line18 = new DottedLineSeparator();
            line18.setOffset(-4);
            line18.setLineColor(BaseColor.BLACK);
            p18.add(line18);
            Header.document.add(p18);
            Header.addEmptyLine(1);


            Header.document.add(table);
            //  Header.addEmptyLine(1);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    public Individual(ArrayList<Living> livingList, int i) {

        try {
            Header.addEmptyLine(1);
            Header.addChank("Activities of Daily Living");
            messageLiving.add("Activities of Daily Living");
            Header.addEmptyLine(1);

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);

            for (i = 0; i < livingList.size(); i++) {

                PdfPTable table1;
                table1 = new PdfPTable(2);
                PdfPCell cell1;
                table1.setWidthPercentage(100);

                cell1 = new PdfPCell(new Phrase("Activities of Daily Living(ADL) " + ""));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageLiving.add("Activities of Daily Living(ADL)" + "");
                messageLiving.add("");

                cell1 = new PdfPCell(new Phrase(""));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);
                messageLiving.add("");
                messageLiving.add("");

                cell1 = new PdfPCell(new Phrase("Needs Help With" + " :"));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageLiving.add("Needs Help With" + " :");
                messageLiving.add("");

                cell1 = new PdfPCell(new Phrase(""));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);
                messageLiving.add("");
                messageLiving.add("");

                Living s = livingList.get(i);

                String bathing = "";
                if (s.getBath() != null) {
                    bathing = s.getBath();
                }

                cell1 = new PdfPCell(new Phrase("Bathing:" + bathing));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageLiving.add("Bathing :");
                messageLiving.add(bathing);

                String continence = "";
                if (s.getContinence() != null) {
                    continence = s.getContinence();
                }
                cell1 = new PdfPCell(new Phrase("Continence:" + continence));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageLiving.add("Continence :");
                messageLiving.add(continence);

                String dressing = "";
                if (s.getDress() != null) {
                    dressing = s.getDress();
                }
                cell1 = new PdfPCell(new Phrase("Dressing:" + dressing));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);
                messageLiving.add("Dressing :");
                messageLiving.add(dressing);

                String eating = "";
                if (s.getFeed() != null) {
                    eating = s.getFeed();
                }
                cell1 = new PdfPCell(new Phrase("Eating:" + eating));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageLiving.add("Eating :");
                messageLiving.add(eating);

                String toileting = "";
                if (s.getToileting() != null) {
                    toileting = s.getToileting();
                }

                cell1 = new PdfPCell(new Phrase("Toileting:" + toileting));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageLiving.add("Toileting :");
                messageLiving.add(toileting);

                String transfering = "";
                if (s.getTransfer() != null) {
                    transfering = s.getTransfer();
                }

                cell1 = new PdfPCell(new Phrase("Transfering:" + transfering));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageLiving.add("Transfering :");
                messageLiving.add(transfering);

                String functionOther = "";
                if (s.getFunctionOther() != null) {
                    functionOther = s.getFunctionOther();
                }
                cell1 = new PdfPCell(new Phrase("Other-specify:" + functionOther));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);
                messageLiving.add("Other-specify :");
                messageLiving.add(functionOther);

                String functionNote = "";
                if (s.getFunctionNote() != null) {
                    functionNote = s.getFunctionNote();
                }
                cell1 = new PdfPCell(new Phrase("Note:" + functionNote));
                cell1.setBorder(Rectangle.BOTTOM);
                cell1.setUseBorderPadding(true);
                cell1.setBorderWidthBottom(5);
                cell1.setBorderColorBottom(BaseColor.WHITE);
                table1.addCell(cell1);

                messageLiving.add("Note :");
                messageLiving.add(functionNote);

                Header.document.add(table1);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                Header.document.add(p);
                Header.addEmptyLine(1);

                PdfPTable table2;
                table2 = new PdfPTable(2);
                PdfPCell cell2;
                table2.setWidthPercentage(100);

                cell2 = new PdfPCell(new Phrase("Instrumental Activities of Daily Living(IADL) " + ""));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Instrumental Activities of Daily Living(IADL)" + "");
                messageLiving.add("");

                cell2 = new PdfPCell(new Phrase(""));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);
                messageLiving.add("");
                messageLiving.add("");

                cell2 = new PdfPCell(new Phrase("Needs Help With" + " :"));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Needs Help With" + " :");
                messageLiving.add("");

                cell2 = new PdfPCell(new Phrase(""));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);
                messageLiving.add("");
                messageLiving.add("");

                String access = "";
                if (s.getTransport() != null) {
                    access = s.getTransport();
                }
                cell2 = new PdfPCell(new Phrase("Accessing transportation:" + access));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Accessing transportation :");
                messageLiving.add(access);

                String carePet = "";
                if (s.getPets() != null) {
                    carePet = s.getPets();
                }
                cell2 = new PdfPCell(new Phrase("Caring for pets:" + carePet));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Caring for pets :");
                messageLiving.add(carePet);

                String driving = "";
                if (s.getDrive() != null) {
                    driving = s.getDrive();
                }
                cell2 = new PdfPCell(new Phrase("Driving:" + driving));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Driving :");
                messageLiving.add(driving);

                String housekeeping = "";
                if (s.getKeep() != null) {
                    housekeeping = s.getKeep();
                }
                cell2 = new PdfPCell(new Phrase("Housekeeping:" + housekeeping));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Housekeeping :");
                messageLiving.add(housekeeping);

                String manage = "";
                if (s.getMedication() != null) {
                    manage = s.getMedication();
                }
                cell2 = new PdfPCell(new Phrase("Manage medications:" + manage));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Manage medications :");
                messageLiving.add(manage);

                String finance = "";
                if (s.getFinance() != null) {
                    finance = s.getFinance();
                }
                cell2 = new PdfPCell(new Phrase("Managing personal finances:" + finance));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);
                messageLiving.add("Managing personal finances :");
                messageLiving.add(finance);

                String meal = "";
                if (s.getPrepare() != null) {
                    meal = s.getPrepare();
                }
                cell2 = new PdfPCell(new Phrase("Preparing meals:" + meal));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Preparing meals :");
                messageLiving.add(meal);

                String shopping = "";
                if (s.getShop() != null) {
                    shopping = s.getShop();
                }
                cell2 = new PdfPCell(new Phrase("Shopping for groceries or clothes:" + shopping));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Shopping for groceries or clothes :");
                messageLiving.add(shopping);

                String telephone = "";
                if (s.getUse() != null) {
                    telephone = s.getUse();
                }
                cell2 = new PdfPCell(new Phrase("Using telephone:" + telephone));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Using telephone :");
                messageLiving.add(telephone);

                String instOther = "";
                if (s.getInstOther() != null) {
                    instOther = s.getInstOther();
                }
                cell2 = new PdfPCell(new Phrase("Other-specify:" + instOther));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Other-specify :");
                messageLiving.add(instOther);

                String instNote = "";
                if (s.getInstNote() != null) {
                    instNote = s.getInstNote();
                }
                cell2 = new PdfPCell(new Phrase("Note:" + instNote));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);

                messageLiving.add("Note :");
                messageLiving.add(instNote);

                cell2 = new PdfPCell(new Phrase(""));
                cell2.setBorder(Rectangle.BOTTOM);
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidthBottom(5);
                cell2.setBorderColorBottom(BaseColor.WHITE);
                table2.addCell(cell2);
                messageLiving.add("");
                messageLiving.add("");


                Header.document.add(table2);
                Paragraph p1 = new Paragraph(" ");
                DottedLineSeparator line1 = new DottedLineSeparator();
                line1.setOffset(-4);
                line1.setLineColor(BaseColor.BLACK);
                p1.add(line1);
                Header.document.add(p1);
                Header.addEmptyLine(1);

            }
            Header.document.add(table);
            Header.addEmptyLine(1);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Individual(String emergency1, Emergency e, ArrayList<ContactData> phonelist, int i) {
        try {
            // Header.addEmptyLine(1);
            if (i==0) {
                Header.addChank("Emergency Contacts & Health Care Proxy Agents");
                messageEmergency.add("Emergency Contacts & Health Care Proxy Agents");
                Header.addEmptyLine(1);
            }

//        Header.widths[0] = 0.15f;
//        Header.widths[1] = 0.85f;
//        Header.table = new PdfPTable(Header.widths);
//        Header.table.getDefaultCell().setBorder(Rectangle.BOTTOM);
            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);

               /* int k = i + 1;

                cell = new PdfPCell(new Phrase("Emergency Contact " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Emergency Contact " + k + " :");
                messageEmergency.add("");*/



            String name = "";
            if (e.getName() != null) {
                name = e.getName();
            }
            cell = new PdfPCell(new Phrase("Name:" + name));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageEmergency.add("Name :");
            messageEmergency.add(name);

            String reationType = "";
            if (e.getRelationType() != null) {
                if (e.getRelationType().equals("Other")) {
                    reationType = e.getRelationType() + " - " + e.getOtherRelation();
                } else {
                    reationType = e.getRelationType();
                }
            }
            cell = new PdfPCell(new Phrase("Relationship:" + reationType));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageEmergency.add("Relationship :");
            messageEmergency.add(reationType);


            String priority = "";
            if (e.getIsPrimary() == 0) {
                priority = "Primary Emergency Contact";
            } else if (e.getIsPrimary() == 1) {
                priority = "Primary Health Care Proxy Agent";
            } else if (e.getIsPrimary() == 2) {
                priority = "Secondary Emergency Contact";
            } else if (e.getIsPrimary() == 3) {
                priority = "Secondary Health Care Proxy Agent";
            }else if (e.getIsPrimary() == 4) {
                priority = "Primary Emergency Contact and Health Care Proxy Agent ";
            }


            cell = new PdfPCell(new Phrase("Priority:" + priority));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageEmergency.add("Priority :");
            messageEmergency.add(priority);



             /*   String relationOther = "";
                if (e.getOtherRelation() != null) {
                    relationOther = e.getOtherRelation();
                }
                cell = new PdfPCell(new Phrase("Other:" + relationOther));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Other :");
                messageEmergency.add(relationOther);*/


                /*String officePhone = "";
                if (e.getWorkPhone() != null) {
                    officePhone = e.getWorkPhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Office Phone :");
                messageEmergency.add(officePhone);

                if (e.getMobile() != null) {
                    mPhone = e.getMobile();
                }
                cell = new PdfPCell(new Phrase("Mobile Phone:" + mPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Mobile Phone :");
                messageEmergency.add(mPhone);
                String bdate = "";

                if (e.getPhone() != null) {
                    hPhone = e.getPhone();
                }
                cell = new PdfPCell(new Phrase("Home Phone:" + hPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Home Phone :");
                messageEmergency.add(hPhone);
*/
            for (int t=0;t<phonelist.size();t++)
            {
                ContactData c=phonelist.get(t);
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

                messageEmergency.add(type+" Phone:");
                messageEmergency.add(num);
            }

            String email = "";
            if (e.getEmail() != null) {
                email = e.getEmail();
            }
            cell = new PdfPCell(new Phrase("Email:" + email));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageEmergency.add("Email :");
            messageEmergency.add(email);

            if (e.getAddress() != null) {
                address = e.getAddress();
            }
            cell = new PdfPCell(new Phrase("Home Address:" + address));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageEmergency.add("Home Address :");
            messageEmergency.add(address);



            String ascard = "";
            if (e.getHas_card() != null) {
                ascard = e.getHas_card();
            }
            cell = new PdfPCell(new Phrase("Do you have a business card:"+ascard));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageEmergency.add("Do you have a business card?");
            messageEmergency.add(ascard);

            String note = "";
            if (e.getNote() != null) {
                note = e.getNote();
            }
            cell = new PdfPCell(new Phrase("Notes:" + note));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageEmergency.add("Notes :");
            messageEmergency.add(note);

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messageEmergency.add("");
            messageEmergency.add("");


            Header.document.add(table);
            Paragraph p = new Paragraph(" ");
            DottedLineSeparator line = new DottedLineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.BLACK);
            p.add(line);
            Header.document.add(p);
            Header.addEmptyLine(1);

//        Header.table.setWidthPercentage(100f);

            Header.document.add(table1);
            Header.addEmptyLine(1);
        } catch (Exception ee) {
            // TODO Auto-generated catch block
            ee.printStackTrace();
        }
    }

    public Individual(String physician, Specialist s, ArrayList<ContactData> phonelists, int i) {
        try {

            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
            // Header.addEmptyLine(1);
            if (i==0) {
                Header.addChank("Primary Physician");
                messagePhysician.add("Primary Physician");
                Header.addEmptyLine(1);

            }
            //    for (int i = 0; i < specialistsList.size(); i++) {

            PdfPTable table;
            table = new PdfPTable(2);
            PdfPCell cell;
            table.setWidthPercentage(100);
              /*  int k = i + 1;
                cell = new PdfPCell(new Phrase("Primary Physician " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePhysician.add("Primary Physician" + k + " :");
                messagePhysician.add("");*/

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
            messagePhysician.add("Specialty :");
            messagePhysician.add(speciality);

                /*String specialityOther = "";
                if (s.getOtherType() != null) {
                    specialityOther = s.getOtherType();
                }
                cell = new PdfPCell(new Phrase("Other Speciality:" + specialityOther));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePhysician.add("Other Speciality :");
                messagePhysician.add(specialityOther);*/

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
            messagePhysician.add("Name :");
            messagePhysician.add(name);

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

                messagePhysician.add(type+" Phone:");
                messagePhysician.add(num);
            }
     /*           String officePhone = "";
                if (s.getOfficePhone() != null) {
                    officePhone = s.getOfficePhone();
                }
                cell = new PdfPCell(new Phrase("Office Phone:" + officePhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePhysician.add("Office Phone :");
                messagePhysician.add(officePhone);

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
                messagePhysician.add("After Hours Phone :");
                messagePhysician.add(afterHoursPhone);

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
                messagePhysician.add("Other Phone :");
                messagePhysician.add(otherPhone);

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
                messagePhysician.add("Office Fax :");
                messagePhysician.add(officeFax);

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
            messagePhysician.add("Address :");
            messagePhysician.add(address);

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
            messagePhysician.add("Website :");
            messagePhysician.add(website);

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
            messagePhysician.add("Medical Practice Name :");
            messagePhysician.add(medicalPracticeName);

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
            messagePhysician.add("Hospital Affiliations :");
            messagePhysician.add(hospitalAffiliations);

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
            messagePhysician.add("Network Status :");
            messagePhysician.add(networkStatus);

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
            messagePhysician.add("Last Visit :");
            messagePhysician.add(lastSeen);

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
            messagePhysician.add("Electronic Health Record (add web address, username and password) :");
            messagePhysician.add(locator);

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
            messagePhysician.add("Notes :");
            messagePhysician.add(note);

            String ascard = "";
            if (s.getHas_card() != null) {
                ascard = s.getHas_card();
            }
            cell = new PdfPCell(new Phrase("Do you have a business card:"+ascard));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messagePhysician.add("Do you have a business card?");
            messagePhysician.add(ascard);


            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.BOTTOM);
            cell.setUseBorderPadding(true);
            cell.setBorderWidthBottom(5);
            cell.setBorderColorBottom(BaseColor.WHITE);
            table.addCell(cell);

            messagePhysician.add("");
            messagePhysician.add("");

            Header.document.add(table);
            Paragraph p = new Paragraph(" ");
            DottedLineSeparator line = new DottedLineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.BLACK);
            p.add(line);
            Header.document.add(p);
            Header.addEmptyLine(1);
            //}


            Header.document.add(table1);
            Header.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
