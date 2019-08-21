package com.mindyourlovedone.healthcare.pdfdesign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
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


public class IndividualNew {
    public static Font BlackFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
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
     //int alone=0;

    public static void IndividualNewFont() {
        try {
            BaseFont base = BaseFont.createFont("assets/Lato-Regular.ttf", "UTF-8", BaseFont.EMBEDDED);
            BlackFont = new Font(base, 19, Font.NORMAL);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public IndividualNew(RelativeConnection connection, ArrayList<Pet> Petlist, ArrayList<ContactData> phonelist, Image ppys) {

        try {
            // Font
            IndividualNewFont();

            HeaderNew.addNewChank("Personal Profile", ppys);
            messageInfo.add("Personal Profile");
            HeaderNew.addEmptyLine(1);

            int alone=0;

            PdfPTable table;
            table = new PdfPTable(1);
            table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table.setTableEvent(new RoundedBorder());
            //table.getDefaultCell().setPaddingBottom(15);
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();
            cell.setPaddingTop(10);

            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);

            PdfPTable table1;
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);


            PdfPCell cell1;
            Paragraph k1;

            if (connection.getName() != null) {
                name = connection.getName();
            }

            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Name:", name);
            table1.addCell(cell1);
            messageInfo.add("Name :");
            messageInfo.add(name);

            ;

            if (connection.getRelationType() != null) {
                if (connection.getRelationType().equals("Other")) {
                    realtion = connection.getRelationType() + " - " + connection.getOtherRelation();
                } else {
                    realtion = connection.getRelationType();
                }
            }
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Relationship:", realtion);
            table1.addCell(cell1);


            messageInfo.add("Relationship :");
            messageInfo.add(realtion);

            String email = "";
            if (connection.getEmail() != null) {
                email = connection.getEmail();
            }

            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Email:", email);
            table1.addCell(cell1);

            messageInfo2.add("Email :");
            messageInfo2.add(email);

            for (int t = 0; t < phonelist.size(); t++) {

                ContactData c = phonelist.get(t);
                String num = "";
                String type = "";
                if (c.getValue() != null) {
                    num = c.getValue();
                }

                if (c.getContactType() != null) {
                    type = c.getContactType();
                }
                int j = t + 1;


                cell1 = new PdfPCell();
                HeaderNew.cellDesign(cell1, table1, "Contact" + j + ":", type + " : " + num);
                table1.addCell(cell1);


                messageInfo2.add(type + " Phone:");
                messageInfo2.add(num);
            }


            if (connection.getAddress() != null) {
                address = connection.getAddress();
            }
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Address:", address);
            table1.addCell(cell1);

            messageInfo2.add("Address :");
            messageInfo2.add(address);

           /* if (phonelist.size()%2!=0)
            {
                cell1 = new PdfPCell();
                HeaderNew.cellDesign(cell1, table1, "", "Empty");
                table1.addCell(cell1);
            }*/


            /*cell1 = new PdfPCell();
            HeaderNew.addDottedLine(cell1);
            table1.addCell(cell1);*/


            String bdates = "";
            if (connection.getDob() != null) {
                bdates = connection.getDob();
            }

            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Birthday:", bdates);
            table1.addCell(cell1);

            messageInfo2.add("Birthday :");
            messageInfo2.add(bdates);

            String genders = "";
            if (connection.getGender() != null) {
                genders = connection.getGender();
            }

            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Gender:", genders);
            table1.addCell(cell1);

            messageInfo2.add("Gender :");
            messageInfo2.add(genders);


            if (connection.getHeight() != null) {
                height = connection.getHeight();
            }
            //   cell8 = new PdfPCell(new Phrase("Height:" + height));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Height(Ft-Inches):", height);
            table1.addCell(cell1);


            messageInfo2.add("Height(Ft-Inches):");
            messageInfo2.add(height);

            if (connection.getWeight() != null) {
                weight = connection.getWeight();
            }
            // cell8 = new PdfPCell(new Phrase("Weight:" + weight));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Weight(lbs):", weight);
            table1.addCell(cell1);

            messageInfo2.add("Weight(lbs):");
            messageInfo2.add(weight);

            if (connection.getEyes() != null) {
                eyes = connection.getEyes();
            }
            // cell8 = new PdfPCell(new Phrase("Eyes:" + eyes));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Eye Color:", eyes);
           /* Paragraph k2 = new Paragraph(" ");
            k2.setSpacingAfter(-5);
            cell8.addElement(k2);*/
            table1.addCell(cell1);


            messageInfo2.add("Eyes :");
            messageInfo2.add(eyes);

            /*cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1,table1,"","Empty");
            table1.addCell(cell1);

            messageInfo2.add("");
            messageInfo2.add("");*/

           /* cell1 = new PdfPCell();
            HeaderNew.addDottedLine(cell1);
            table1.addCell(cell1);*/

            if (connection.getMarital_status() != null) {
                marital_status = connection.getMarital_status();
            }
            // cellm = new PdfPCell(new Phrase("Marital Status:" + marital_status));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Marital Status:", marital_status);
            table1.addCell(cell1);

            messageInfo2.add("Marital Status :");
            messageInfo2.add(marital_status);

           /* cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1,table1,"","Empty");
            table1.addCell(cell1);

            messageInfo2.add("");
            messageInfo2.add("");
*/
          /* cell1 = new PdfPCell();
            HeaderNew.addDottedLine(cell1);
            table1.addCell(cell1); */

            String live = "";
            if (connection.getLive() != null) {
                live = connection.getLive();
            }
            //cell2 = new PdfPCell(new Phrase("Do you live alone?:" + live));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Do you live alone?:", live);
            table1.addCell(cell1);

            messageInfo2.add("Do you live alone? :");
            messageInfo2.add(live);

            String children = "";
            if (connection.getChildren() != null) {
                children = connection.getChildren();

            }
            // cell2 = new PdfPCell(new Phrase("Children:" + children));
            if (children.equalsIgnoreCase("YES")) {
                alone++;
                cell1 = new PdfPCell();
                HeaderNew.cellDesign(cell1, table1, "Children:", children);
                table1.addCell(cell1);
                messageInfo2.add("Children :");
                messageInfo2.add(children);
            }




            String friend = "";
            if (connection.getFriend() != null) {
                friend = connection.getFriend();
                if (friend.equalsIgnoreCase("YES")) {
                    alone++;
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "Friend:", friend);
                    table1.addCell(cell1);

                    messageInfo2.add("Friend :");
                    messageInfo2.add(friend);
                }
            }
            // cell2 = new PdfPCell(new Phrase("Friend:" + friend));


            String grandParents = "";
            if (connection.getGrand() != null) {
                grandParents = connection.getGrand();

                if (grandParents.equalsIgnoreCase("YES")) {
                    alone++;
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "Grandparent(s):", grandParents);
                    table1.addCell(cell1);

                    messageInfo2.add("Grandparent(s) :");
                    messageInfo2.add(grandParents);
                }
            }

            String parents = "";
            if (connection.getParents() != null) {
                parents = connection.getParents();

                if (parents.equalsIgnoreCase("YES")) {
                    alone++;
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "Parent(s):", parents);
                    table1.addCell(cell1);

                    messageInfo2.add("Parent(s) :");
                    messageInfo2.add(parents);
                }
        }

            String spouse = "";
            if (connection.getSpouse() != null) {
                spouse = connection.getSpouse();

                if (spouse.equalsIgnoreCase("YES")) {
                    alone++;
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "Spouse:", spouse);
                    table1.addCell(cell1);

                    messageInfo2.add("Spouse :");
                    messageInfo2.add(spouse);
                }
    }
            String sibling = "";
            if (connection.getSibling() != null) {
                sibling = connection.getSibling();

                if (sibling.equalsIgnoreCase("YES")) {
                    alone++;
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "Sibling:", sibling);
                    table1.addCell(cell1);
                    messageInfo2.add("Sibling :");
                    messageInfo2.add(sibling);
                }
            }
            String significant = "";
            if (connection.getSign_other() != null) {
                significant = connection.getSign_other();

                if (significant.equalsIgnoreCase("YES")) {
                    alone++;
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "Significant Other:", significant);
                    table1.addCell(cell1);

                    messageInfo2.add("Significant Other :");
                    messageInfo2.add(significant);
                    String other = "";

                    if (connection.getOther_person() != null) {
                        other = connection.getOther_person();
                    }
                    alone++;
                        cell1 = new PdfPCell();
                        HeaderNew.cellDesign(cell1, table1, "Other:", other);
                        table1.addCell(cell1);

                }
            }

            String people = "";
            if (connection.getPeople() != null) {
                people = connection.getPeople();
            }
            // cell2 = new PdfPCell(new Phrase("Names of People in Household:" + people));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Names of People in Household:", people);

            table1.addCell(cell1);

           /* cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1,table1,"","Empty");
            table1.addCell(cell1);
*/
           /* cell1 = new PdfPCell();
            HeaderNew.addDottedLine(cell1);
            table1.addCell(cell1);*/

            if (connection.getProfession() != null) {
                profession = connection.getProfession();
            }

            //cellp = new PdfPCell(new Phrase("Profession:" + profession));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Profession:", profession);
            table1.addCell(cell1);

            messageInfo2.add("Profession :");
            messageInfo2.add(profession);

            if (connection.getEmployed() != null) {
                employedBy = connection.getEmployed();
            }
            //  cellp = new PdfPCell(new Phrase("Employed By:" + employedBy));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Employed by and Name of Manager:", employedBy);
            table1.addCell(cell1);

            messageInfo2.add("Employed By :");
            messageInfo2.add(employedBy);

            if (connection.getManager_phone() != null) {
                telephone = connection.getManager_phone();
            }
            //cellp = new PdfPCell(new Phrase("Manager Phone:" + telephone));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Telephone# of Manager:", telephone);
            table1.addCell(cell1);

            messageInfo2.add("Manager Phone :");
            messageInfo2.add(telephone);




            /*cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1,table1,"","Empty");
            table1.addCell(cell1);

            messageInfo2.add("");
            messageInfo2.add("");*/

            /*cell1 = new PdfPCell();
            HeaderNew.addDottedLine(cell1);
            table1.addCell(cell1);*/

            String english = "";
            if (connection.getEnglish() != null) {
                english = connection.getEnglish();
            }
            //cell3 = new PdfPCell(new Phrase("Understand English:" + english));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Understand English:", english);
            table1.addCell(cell1);

            messageInfo2.add("Understand English :");
            messageInfo2.add(english);

            if (connection.getLanguage() != null) {
                language = connection.getLanguage();
                if (language.equalsIgnoreCase("Other"))
                {
                    language = language + " - " + connection.getOtherLang();
                }
            }

            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Language Spoken:", language);
            table1.addCell(cell1);

            messageInfo2.add("Language Spoken :");
            messageInfo2.add(language);

            /*cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1,table1,"","Empty");
            table1.addCell(cell1);*/

            messageInfo2.add(" ");
            messageInfo2.add(" ");

           /* cell1 = new PdfPCell();
            HeaderNew.addDottedLine(cell1);
            table1.addCell(cell1);*/

            if (connection.getReligion() != null) {
                religionNote = connection.getReligion();
            }
            // cellr = new PdfPCell(new Phrase("Religious Affiliation & Notes: " + religionNote));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Religious Affiliation & Notes:", religionNote);
            table1.addCell(cell1);

            messageInfo2.add("Religious Affiliation & Notes :");
            messageInfo2.add(religionNote);

           /* cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1,table1,"","Empty");
            table1.addCell(cell1);

            messageInfo2.add("");
            messageInfo2.add("");*/

            /*cell1 = new PdfPCell();
            HeaderNew.addDottedLine(cell1);
            table1.addCell(cell1);*/

            if (connection.getVeteran() != null) {
                Veteran = connection.getVeteran();
            }
            //cell4 = new PdfPCell(new Phrase("Veteran:" + Veteran));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Veteran:", Veteran);
            table1.addCell(cell1);

            messageInfo2.add("Veteran :");
            messageInfo2.add(Veteran);

            if (connection.getIdnumber() != null) {
                idNumber = connection.getIdnumber();
            }
            //cell4 = new PdfPCell(new Phrase("Id Number:" + idNumber));
            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table1, "Id Number:", idNumber);
            table1.addCell(cell1);

            messageInfo2.add("Id Number :");
            messageInfo2.add(idNumber);


            String card = "";
            if (connection.getHas_card() != null) {
                card = connection.getHas_card();
            }

            if (connection.getPet() != null) {
                Pets = connection.getPet();
            }


           alone=alone+phonelist.size();
            System.out.println(alone);
            if (alone % 2 != 0) {
                if (Petlist.size()!=0)
                {
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign (cell1, table1, "Do you have a business card:", card);
                    table1.addCell(cell1);

                    messageInfo2.add("Do you have a business card:");
                    messageInfo2.add(card);

                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "Pet(s):", Pets);
                    table1.addCell(cell1);

                    messageInfo2.add("Pet(s) :");
                    messageInfo2.add(Pets);

                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "", "Empty");
                    table1.addCell(cell1);

                }else{
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign (cell1, table1, "Do you have a business card:", card);
                    table1.addCell(cell1);

                    messageInfo2.add("Do you have a business card:");
                    messageInfo2.add(card);
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesignNoline(cell1, table1, "Pet(s):", Pets);
                    table1.addCell(cell1);

                    messageInfo2.add("Pet(s) :");
                    messageInfo2.add(Pets);

                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "", "Empty");
                    table1.addCell(cell1);
                }

            }
            else{
                if (Petlist.size()!=0) {
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "Do you have a business card:", card);
                    table1.addCell(cell1);

                    messageInfo2.add("Do you have a business card:");
                    messageInfo2.add(card);

                    cell1 = new PdfPCell();
                    HeaderNew.cellDesign(cell1, table1, "Pet(s):", Pets);
                    table1.addCell(cell1);

                    messageInfo2.add("Pet(s) :");
                    messageInfo2.add(Pets);

                }else{
                    cell1 = new PdfPCell();
                    HeaderNew.cellDesignNoline(cell1, table1, "Do you have a business card:", card);
                    table1.addCell(cell1);

                    messageInfo2.add("Do you have a business card:");
                    messageInfo2.add(card);

                    cell1 = new PdfPCell();
                    HeaderNew.cellDesignNoline(cell1, table1, "Pet(s):", Pets);
                    table1.addCell(cell1);

                    messageInfo2.add("Pet(s) :");
                    messageInfo2.add(Pets);
                }



            }
            messageInfo2.add("");
            messageInfo2.add("");


            if (Pets.equalsIgnoreCase("YES")) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                Paragraph pf = new Paragraph();
                Phrase pps = new Phrase();
                Chunk underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                Chunk underline = new Chunk("Pet(s) Details", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                if (Petlist.size() != 0) {
                    cell1.addElement(pf);

                }
                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.setPaddingLeft(10);
                cell1.setPaddingRight(10);
                cell1.addElement(new Paragraph(" "));

    /*cell5.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
    cell5.setPaddingLeft(10);
    cell5.setPaddingRight(5);
    cell5.setPaddingTop(0);
    cell5.setPaddingBottom(5);
    cell5.setVerticalAlignment(Element.ALIGN_TOP);*/


                String name = "";
                String breed = "";
                String color = "";
                String microchip = "";
                String veterian = "";
                String person = "";

                //cell5 = new PdfPCell();
                PdfPTable tableIN;

                for (int i = 0; i < Petlist.size(); i++) {

                    int k = i + 1;
                    tableIN = new PdfPTable(2);
                    PdfPCell cellIN;
                    tableIN.setWidthPercentage(98);
                    tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                    tableIN.setTableEvent(new RoundedBorder());
                    //tableIN.getDefaultCell().setPadding(2);
                   // tableIN.setKeepTogether(false);
                    //tableIN.setSplitLate(false);


                    Pet a = Petlist.get(i);
                    if (a.getName() != null) {
                        name = a.getName();
                    }
                    //cellIN = new PdfPCell(new Phrase("Name:" + name));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Pet " + k + " Name:", name);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);

                    tableIN.addCell(cellIN);

                    messageInfo3.add("Name :");
                    messageInfo3.add(name);

                    if (a.getBreed() != null) {
                        breed = a.getBreed();
                    }

                    //  cell = new PdfPCell(new Phrase("Type of Pet / Breed :" + breed));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Type of Pet / Breed:", breed);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    messageInfo3.add("Breed :");
                    messageInfo3.add(breed);

                    if (a.getColor() != null) {
                        color = a.getColor();
                    }
                    // cell = new PdfPCell(new Phrase("Color:" + color));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Color:", color);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    tableIN.addCell(cellIN);

                    messageInfo3.add("Color :");
                    messageInfo3.add(color);

                    if (a.getChip() != null) {
                        microchip = a.getChip();
                    }
                    // cell = new PdfPCell(new Phrase("Microchip number:" + microchip));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Microchip number:", microchip);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    tableIN.addCell(cellIN);

                    messageInfo3.add("Microchip number :");
                    messageInfo3.add(microchip);


                    if (a.getVeterian() != null) {
                        veterian = a.getVeterian();
                    }
                    //  cell = new PdfPCell(new Phrase("Veterinarian Name:" + veterian));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Veterinarian Name:", veterian);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    tableIN.addCell(cellIN);

                    messageInfo3.add("Veterinarian Name :");
                    messageInfo3.add(veterian);

                    String vadd = "";
                    if (a.getVeterian_add() != null) {
                        vadd = a.getVeterian_add();
                    }
                    //  cell = new PdfPCell(new Phrase("Veterinarian Address:" + vadd));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Veterinarian Address:", vadd);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    tableIN.addCell(cellIN);

                    messageInfo3.add("Veterinarian :");
                    messageInfo3.add(vadd);

                    String vpone = "";
                    if (a.getVeterian_ph() != null) {
                        vpone = a.getVeterian_ph();
                    }
                    //  cell = new PdfPCell(new Phrase("Veterinarian Phone:" + vpone));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Veterinarian Phone:", vpone);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    tableIN.addCell(cellIN);

                    messageInfo3.add("Veterinarian Phone :");
                    messageInfo3.add(vpone);


                    if (a.getGuard() != null) {
                        person = a.getGuard();
                    }
                    //  cell = new PdfPCell(new Phrase("Person(s) Name who will care for pet:" + person));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "ICE - who will care for pet?:", person);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    tableIN.addCell(cellIN);

                    messageInfo3.add("ICE - who will care for pet?:");
                    messageInfo3.add(person);

                    String cname = "";
                    if (a.getCare_add() != null) {
                        cname = a.getCare_add();
                    }
                    //  cell = new PdfPCell(new Phrase("Person(s) Address who will care for pet:" + cname));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Address of emergency pet caretaker:", cname);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    tableIN.addCell(cellIN);

                    messageInfo3.add("Address of emergency pet caretaker:");
                    messageInfo3.add(cname);


                    String cAdd = "";
                    if (a.getCare_ph() != null) {
                        cAdd = a.getCare_ph();
                    }
                    // cell = new PdfPCell(new Phrase("Person(s) Phone who will care for pet:" + cAdd));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Tel # of emergency pet caretaker:", cAdd);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    tableIN.addCell(cellIN);

                    messageInfo3.add("Tel # of emergency pet caretaker:");
                    messageInfo3.add(cAdd);


                    if (a.getBdate() != null) {
                        Bdate = a.getBdate();
                    }
                    //  cell = new PdfPCell(new Phrase("Birthday:" + Bdate));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "Birthday:", Bdate);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    tableIN.addCell(cellIN);

                    messageInfo3.add("Birthday:");
                    messageInfo3.add(Bdate);

                    if (a.getNotes() != null) {
                        notes = a.getNotes();
                    }
                    //  cell = new PdfPCell(new Phrase("Notes about Pet:" + notes));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "Notes about Pet:", notes);
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    tableIN.addCell(cellIN);

                    messageInfo3.add("Notes about Pet :");
                    messageInfo3.add(notes);

                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
                    tableIN.addCell(cellIN);

                    messageInfo3.add("");
                    messageInfo3.add("");
                    cell1.addElement(tableIN);
                    cell1.addElement(new Paragraph(" "));
                }

                table1.addCell(cell1);
            }
            cell.addElement(table1);
            table.addCell(cell);
            HeaderNew.document.add(table);
            HeaderNew.addEmptyLine(1);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public IndividualNew(PersonalInfo connection, ArrayList<Pet> PetList) {
        try {
            //  HeaderNew.addEmptyLine(1);
            HeaderNew.addChank("Personal Profile");
            messageInfo2.add("Personal Profile");
            HeaderNew.addEmptyLine(1);

//        HeaderNew.widths[0] = 0.15f;
//        HeaderNew.widths[1] = 0.85f;
//        HeaderNew.table = new PdfPTable(HeaderNew.widths);
//        HeaderNew.table.getDefaultCell().setBorder(Rectangle.BOTTOM);

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

            HeaderNew.document.add(table1);
            Paragraph p1 = new Paragraph(" ");
            DottedLineSeparator line1 = new DottedLineSeparator();
            line1.setOffset(-4);
            line1.setLineColor(BaseColor.BLACK);
            p1.add(line1);
            HeaderNew.document.add(p1);
            HeaderNew.addEmptyLine(1);


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

            HeaderNew.document.add(table2);
            Paragraph p2 = new Paragraph(" ");
            DottedLineSeparator line2 = new DottedLineSeparator();
            line2.setOffset(-4);
            line2.setLineColor(BaseColor.BLACK);
            p2.add(line2);
            HeaderNew.document.add(p2);
            HeaderNew.addEmptyLine(1);


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

            HeaderNew.document.add(tablep);
            Paragraph pp = new Paragraph(" ");
            DottedLineSeparator linep = new DottedLineSeparator();
            linep.setOffset(-4);
            linep.setLineColor(BaseColor.BLACK);
            pp.add(linep);
            HeaderNew.document.add(pp);
            HeaderNew.addEmptyLine(1);


            PdfPTable table3;
            table3 = new PdfPTable(2);
            PdfPCell cell3;
            table3.setWidthPercentage(100);

            if (connection.getLanguage() != null) {
                language = connection.getLanguage();
                if (connection.getLanguage().equalsIgnoreCase("Other")) ;
                {
                    language = language + " - " + connection.getOtherLang();
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


            HeaderNew.document.add(table3);
            Paragraph p3 = new Paragraph(" ");
            DottedLineSeparator line3 = new DottedLineSeparator();
            line3.setOffset(-4);
            line3.setLineColor(BaseColor.BLACK);
            p3.add(line3);
            HeaderNew.document.add(p3);
            HeaderNew.addEmptyLine(1);


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

            HeaderNew.document.add(tablem);
            Paragraph pm = new Paragraph(" ");
            DottedLineSeparator linem = new DottedLineSeparator();
            linem.setOffset(-4);
            linem.setLineColor(BaseColor.BLACK);
            pm.add(linem);
            HeaderNew.document.add(pm);
            HeaderNew.addEmptyLine(1);


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

            HeaderNew.document.add(table4);
            Paragraph p4 = new Paragraph(" ");
            DottedLineSeparator line4 = new DottedLineSeparator();
            line4.setOffset(-4);
            line4.setLineColor(BaseColor.BLACK);
            p4.add(line4);
            HeaderNew.document.add(p4);
            HeaderNew.addEmptyLine(1);


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
            HeaderNew.addTable("Medical Conditions :");
            HeaderNew.addTable(oPhone);
            messageInfo.add("Medical Conditions :");
            messageInfo.add(oPhone);
            if (cellPhone == null) {
                cellPhone = "";
            }
            HeaderNew.addTable("Preferred Hospital :");
            HeaderNew.addTable(cellPhone);
            messageInfo.add("Preferred Hospital :");
            messageInfo.add(cellPhone);*/
//        HeaderNew.table.setWidthPercentage(100f);


            HeaderNew.document.add(table);
            Paragraph p = new Paragraph(" ");
            DottedLineSeparator line = new DottedLineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.BLACK);
            p.add(line);
            HeaderNew.document.add(p);
            HeaderNew.addEmptyLine(1);

//card


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public IndividualNew(String emergency, ArrayList<Emergency> emergencyList, ArrayList<ContactData> phonelist) {
        try {
            // HeaderNew.addEmptyLine(1);
            HeaderNew.addChank("Emergency Contacts & Health Care Proxy Agents");
            messageEmergency.add("Emergency Contacts & Health Care Proxy Agents");
            HeaderNew.addEmptyLine(1);

//        HeaderNew.widths[0] = 0.15f;
//        HeaderNew.widths[1] = 0.85f;
//        HeaderNew.table = new PdfPTable(HeaderNew.widths);
//        HeaderNew.table.getDefaultCell().setBorder(Rectangle.BOTTOM);
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
                } else if (e.getIsPrimary() == 4) {

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
                for (int t = 0; t < phonelist.size(); t++) {
                    ContactData c = phonelist.get(t);
                    String num = "";
                    String type = "";
                    if (c.getValue() != null) {
                        num = c.getValue();
                    }

                    if (c.getContactType() != null) {
                        type = c.getContactType();
                    }
                    cell = new PdfPCell(new Phrase(type + "  : " + num));
                    cell.setBorder(Rectangle.BOTTOM);
                    cell.setUseBorderPadding(true);
                    cell.setBorderWidthBottom(5);
                    cell.setBorderColorBottom(BaseColor.WHITE);
                    table1.addCell(cell);

                    messageEmergency.add(type + ":");
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
                cell = new PdfPCell(new Phrase("Do you have a business card:" + ascard));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messageEmergency.add("Do you have a business card");
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

                HeaderNew.document.add(table);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                HeaderNew.document.add(p);
                HeaderNew.addEmptyLine(1);
            }
//        HeaderNew.table.setWidthPercentage(100f);

            HeaderNew.document.add(table1);
            HeaderNew.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public IndividualNew(ArrayList<Specialist> specialistsList, String physician) {
        try {

            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);
            // HeaderNew.addEmptyLine(1);

            HeaderNew.addChank("Primary Physician");
            messagePhysician.add("Primary Physician");
            HeaderNew.addEmptyLine(1);


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
                cell = new PdfPCell(new Phrase("In Network Status:" + networkStatus));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePhysician.add("In Network Status :");
                messagePhysician.add(networkStatus);

                String lastSeen = "";
                if (s.getLastseen() != null) {
                    lastSeen = s.getLastseen();
                }
                cell = new PdfPCell(new Phrase("Last Seen:" + lastSeen));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messagePhysician.add("Last Seen :");
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
                cell = new PdfPCell(new Phrase("Do you have a business card:" + ascard));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messagePhysician.add("Do you have a business card");
                messagePhysician.add(ascard);


                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);

                messagePhysician.add("");
                messagePhysician.add("");

                HeaderNew.document.add(table);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                HeaderNew.document.add(p);
                HeaderNew.addEmptyLine(1);
            }


            HeaderNew.document.add(table1);
            HeaderNew.addEmptyLine(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public IndividualNew(ArrayList<Proxy> proxyList) {
        try {
            HeaderNew.addEmptyLine(1);
            HeaderNew.addChank("Health Care Proxy Agent");
            messageProxy.add("Health Care Proxy Agent");
            HeaderNew.addEmptyLine(1);

//        HeaderNew.widths[0] = 0.15f;
//        HeaderNew.widths[1] = 0.85f;
//        HeaderNew.table = new PdfPTable(HeaderNew.widths);
//        HeaderNew.table.getDefaultCell().setBorder(Rectangle.BOTTOM);

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
//        HeaderNew.table.setWidthPercentage(100f);


            HeaderNew.document.add(table);
            Paragraph p = new Paragraph(" ");
            DottedLineSeparator line = new DottedLineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.BLACK);
            p.add(line);
            HeaderNew.document.add(p);
            HeaderNew.addEmptyLine(1);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public IndividualNew(MedInfo medInfo, ArrayList<Allergy> allargyLists, ArrayList<Implant> implantsList, ArrayList<History> historList, ArrayList<String> hospitalList, ArrayList<String> conditionList, ArrayList<Vaccine> vaccineList, Image ppys) {
//        try {
//            HeaderNew.addNewChank("Medical Profile", ppys);
//            messagePhysician.add("Medical Profile");
//            HeaderNew.addEmptyLine(1);
//
//            // HeaderNew.addEmptyLine(1);
//            String preNote = "";
//            String glass = "";
//            String lense = "";
//            String blind = "";
//            String upper = "";
//            String lower = "";
//            String visionNote = "";
////            HeaderNew.addChank("Medical Profile");
////            messageInfo3.add("Medical Profile");
////            HeaderNew.addEmptyLine(1);
//
////        HeaderNew.widths[0] = 0.15f;
////        HeaderNew.widths[1] = 0.85f;
////        HeaderNew.table = new PdfPTable(HeaderNew.widths);
////        HeaderNew.table.getDefaultCell().setBorder(Rectangle.BOTTOM);
//
////            PdfPTable table;
////            table = new PdfPTable(2);
////            PdfPCell cell;
////            table.setWidthPercentage(100);
//
//            PdfPTable table;
//            table = new PdfPTable(1);
//            table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
//            table.setTableEvent(new RoundedBorder());
//            table.getDefaultCell().setPadding(15);
//            table.setKeepTogether(false);
//            table.setSplitLate(false);
//            table.setWidthPercentage(100);
//
//            PdfPTable table6;
//            table6 = new PdfPTable(2);
//            table6.setWidthPercentage(98);
//            table6.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
//            table6.setTableEvent(new RoundedBorder());
//            table6.getDefaultCell().setPadding(10);
//            table6.setKeepTogether(false);
//            table6.setSplitLate(false);
//
//
////            table6 = new PdfPTable(2);
//            PdfPCell cell6;
////            table6.setWidthPercentage(100);
//
//            cell6 = new PdfPCell(new Phrase("Allergies:" + ""));
//            cell6.setBorder(Rectangle.BOTTOM);
//            cell6.setUseBorderPadding(true);
//            cell6.setBorderWidthBottom(5);
//            cell6.setBorderColorBottom(BaseColor.WHITE);
//
//            table6.addCell(cell6);
//
//            messageInfo3.add("Allergies :");
//            messageInfo3.add("");
//
//            cell6 = new PdfPCell(new Phrase(""));
//            cell6.setBorder(Rectangle.BOTTOM);
//            cell6.setUseBorderPadding(true);
//            cell6.setBorderWidthBottom(5);
//            cell6.setBorderColorBottom(BaseColor.WHITE);
//            cell6.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
//            table6.addCell(cell6);
//            messageInfo3.add("");
//            messageInfo3.add("");
//
//            String allergy = "";
//            String treatment = "";
//            String reaction = "";
//            for (int i = 0; i < allargyLists.size(); i++) {
//                int k = i + 1;
//
//               /* cell = new PdfPCell(new Phrase("Allergy " + k + " :"));
//                cell.setBorder(Rectangle.BOTTOM);
//                cell.setUseBorderPadding(true);
//                cell.setBorderWidthBottom(5);
//                cell.setBorderColorBottom(BaseColor.WHITE);
//                table.addCell(cell);
//
//                messageInfo3.add("Allergy " + k + " :");
//                messageInfo3.add("");*/
//
//                Allergy a = allargyLists.get(i);
//                if (a.getAllergy() != null) {
//                    allergy = a.getAllergy();
//                }
//                cell6 = new PdfPCell(new Phrase("Allergy:" + allergy));
//                cell6.setBorder(Rectangle.BOTTOM);
//                cell6.setUseBorderPadding(true);
//                cell6.setBorderWidthBottom(5);
//                cell6.setBorderColorBottom(BaseColor.WHITE);
//                cell6.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
//                table6.addCell(cell6);
//
//                messageInfo3.add("Allergy :");
//                messageInfo3.add(allergy);
//
//                if (a.getReaction() != null) {
//                    reaction = a.getReaction();
//                    if (reaction.equals("Other")) {
//                        reaction = reaction + " - " + a.getOtherReaction();
//                    }
//                }
//                cell6 = new PdfPCell(new Phrase("Reaction:" + reaction));
//                cell6.setBorder(Rectangle.BOTTOM);
//                cell6.setUseBorderPadding(true);
//                cell6.setBorderWidthBottom(5);
//                cell6.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
//                cell6.setBorderColorBottom(BaseColor.WHITE);
//                table6.addCell(cell6);
//
//                messageInfo3.add("Reaction :");
//                messageInfo3.add(reaction);
//
//                if (a.getTreatment() != null) {
//                    treatment = a.getTreatment();
//                }
//                cell6 = new PdfPCell(new Phrase("Treatment:" + treatment));
//                cell6.setBorder(Rectangle.BOTTOM);
//                cell6.setUseBorderPadding(true);
//                cell6.setBorderWidthBottom(5);
//                cell6.setBorderColorBottom(BaseColor.WHITE);
//                table6.addCell(cell6);
//
//                messageInfo3.add("Treatment :");
//                messageInfo3.add(treatment);
//
//                cell6 = new PdfPCell(new Phrase(""));
//                cell6.setBorder(Rectangle.BOTTOM);
//                cell6.setUseBorderPadding(true);
//                cell6.setBorderWidthBottom(5);
//                cell6.setBorderColorBottom(BaseColor.WHITE);
//                table6.addCell(cell6);
//
//                messageInfo3.add("");
//                messageInfo3.add("");
//
//                cell6 = new PdfPCell(new Phrase(""));
//                cell6.setBorder(Rectangle.BOTTOM);
//                cell6.setUseBorderPadding(true);
//                cell6.setBorderWidthBottom(5);
//                cell6.setBorderColorBottom(BaseColor.WHITE);
//                table6.addCell(cell6);
//
//                messageInfo3.add("");
//                messageInfo3.add("");
//
//                cell6 = new PdfPCell(new Phrase(""));
//                cell6.setBorder(Rectangle.BOTTOM);
//                cell6.setUseBorderPadding(true);
//                cell6.setBorderWidthBottom(5);
//                cell6.setBorderColorBottom(BaseColor.WHITE);
//                table6.addCell(cell6);
//
//                messageInfo3.add("");
//                messageInfo3.add("");
//
//            }
//
//            cell6 = new PdfPCell(new Phrase("Allergy Note:" + medInfo.getAllergyNote()));
//            cell6.setBorder(Rectangle.BOTTOM);
//            cell6.setUseBorderPadding(true);
//            cell6.setBorderWidthBottom(5);
//            cell6.setBorderColorBottom(BaseColor.WHITE);
//            table6.addCell(cell6);
//
//            messageInfo3.add("Allergy Note :");
//            messageInfo3.add(medInfo.getAllergyNote());
//
//            cell6 = new PdfPCell(new Phrase(""));
//            cell6.setBorder(Rectangle.BOTTOM);
//            cell6.setUseBorderPadding(true);
//            cell6.setBorderWidthBottom(5);
//            cell6.setBorderColorBottom(BaseColor.WHITE);
//            table6.addCell(cell6);
//
//            messageInfo3.add("");
//            messageInfo3.add("");
//
//            table.addCell(table6);
////            HeaderNew.document.add(table6);
//            Paragraph p6 = new Paragraph(" ");
//            DottedLineSeparator line6 = new DottedLineSeparator();
//            line6.setOffset(-4);
//            line6.setLineColor(BaseColor.BLACK);
//            p6.add(line6);
//            table.addCell(p6);
////            HeaderNew.document.add(p6);
//            HeaderNew.addEmptyLine(1);
//
//
//            PdfPTable table1;
//            table1 = new PdfPTable(2);
//            PdfPCell cell1;
//            table1.setWidthPercentage(100);
//
//            cell1 = new PdfPCell(new Phrase("Pre Existing Medical Conditions " + ":"));
//            cell1.setBorder(Rectangle.BOTTOM);
//            cell1.setUseBorderPadding(true);
//            cell1.setBorderWidthBottom(5);
//            cell1.setBorderColorBottom(BaseColor.WHITE);
//            table1.addCell(cell1);
//
//            messageInfo3.add("Pre Existing Medical Conditions " + ":");
//            messageInfo3.add("");
//
//            cell1 = new PdfPCell(new Phrase(""));
//            cell1.setBorder(Rectangle.BOTTOM);
//            cell1.setUseBorderPadding(true);
//            cell1.setBorderWidthBottom(5);
//            cell1.setBorderColorBottom(BaseColor.WHITE);
//            table1.addCell(cell1);
//
//            messageInfo3.add("");
//            messageInfo3.add("");
//            for (int i = 0; i < conditionList.size(); i++) {
//                int k = i + 1;
//
//
//                cell1 = new PdfPCell(new Phrase("Medical Conditions" + " :" + conditionList.get(i)));
//                cell1.setBorder(Rectangle.BOTTOM);
//                cell1.setUseBorderPadding(true);
//                cell1.setBorderWidthBottom(5);
//                cell1.setBorderColorBottom(BaseColor.WHITE);
//                table1.addCell(cell1);
//
//                messageInfo3.add("Medical Conditions" + " :");
//                messageInfo3.add(conditionList.get(i));
//
//            }
//
//            if (medInfo.getNote() != null) {
//                preNote = medInfo.getNote();
//            }
//            cell1 = new PdfPCell(new Phrase("Medical Condition Note:" + preNote));
//            cell1.setBorder(Rectangle.BOTTOM);
//            cell1.setUseBorderPadding(true);
//            cell1.setBorderWidthBottom(5);
//            cell1.setBorderColorBottom(BaseColor.WHITE);
//            table1.addCell(cell1);
//
//            messageInfo3.add("Medical Condition Note :");
//            messageInfo3.add(preNote);
//
//            if ((conditionList.size() + 1) % 2 != 0) {
//
//                cell1 = new PdfPCell(new Phrase(""));
//                cell1.setBorder(Rectangle.BOTTOM);
//                cell1.setUseBorderPadding(true);
//                cell1.setBorderWidthBottom(5);
//                cell1.setBorderColorBottom(BaseColor.WHITE);
//                table1.addCell(cell1);
//
//                messageInfo3.add("");
//                messageInfo3.add("");
//            }
//            table.addCell(table1);
////            HeaderNew.document.add(table1);
//            Paragraph p1 = new Paragraph(" ");
//            DottedLineSeparator line1 = new DottedLineSeparator();
//            line1.setOffset(-4);
//            line1.setLineColor(BaseColor.BLACK);
//            p1.add(line1);
//            table.addCell(p1);
////            HeaderNew.document.add(p1);
//            HeaderNew.addEmptyLine(1);
//
//
//            PdfPTable table7;
//            table7 = new PdfPTable(2);
//            PdfPCell cell7;
//            table7.setWidthPercentage(100);
//
//            // String Implants="";
//            cell7 = new PdfPCell(new Phrase("Medical Implants :" + ""));
//            cell7.setBorder(Rectangle.BOTTOM);
//            cell7.setUseBorderPadding(true);
//            cell7.setBorderWidthBottom(5);
//            cell7.setBorderColorBottom(BaseColor.WHITE);
//            table7.addCell(cell7);
//
//            messageInfo3.add("Medical Implants :");
//            messageInfo3.add("");
//
//            cell7 = new PdfPCell(new Phrase(""));
//            cell7.setBorder(Rectangle.BOTTOM);
//            cell7.setUseBorderPadding(true);
//            cell7.setBorderWidthBottom(5);
//            cell7.setBorderColorBottom(BaseColor.WHITE);
//            table7.addCell(cell7);
//            messageInfo3.add("");
//            messageInfo3.add("");
//
//            for (int i = 0; i < implantsList.size(); i++) {
//               /* int k = i + 1;
//                cell = new PdfPCell(new Phrase("Medical Implants " + k + " :"));
//                cell.setBorder(Rectangle.BOTTOM);
//                cell.setUseBorderPadding(true);
//                cell.setBorderWidthBottom(5);
//                cell.setBorderColorBottom(BaseColor.WHITE);
//                table.addCell(cell);
//                messageInfo3.add("Medical Implants " + k + " :");
//                messageInfo3.add("");*/
//                Implant a = implantsList.get(i);
//                String name = "";
//                if (a.getName() != null) {
//                    name = a.getName();
//                    if (name.equals("Other")) {
//                        name = name + " - " + a.getOther();
//                    }
//                    if (name.equals("Joint Replacements (specify)")) {
//                        name = name + " - " + a.getOther();
//                    }
//                }
//
//                cell7 = new PdfPCell(new Phrase("Implants:" + name));
//                cell7.setBorder(Rectangle.BOTTOM);
//                cell7.setUseBorderPadding(true);
//                cell7.setBorderWidthBottom(5);
//                cell7.setBorderColorBottom(BaseColor.WHITE);
//                table7.addCell(cell7);
//                messageInfo3.add("Implants :");
//                messageInfo3.add(name);
//
//                String date = "";
//                if (a.getDate() != null) {
//                    date = a.getDate();
//                }
//                cell7 = new PdfPCell(new Phrase("Date:" + date));
//                cell7.setBorder(Rectangle.BOTTOM);
//                cell7.setUseBorderPadding(true);
//                cell7.setBorderWidthBottom(5);
//                cell7.setBorderColorBottom(BaseColor.WHITE);
//                table7.addCell(cell7);
//                messageInfo3.add("Date :");
//                messageInfo3.add(date);
//
//                String loc = "";
//                if (a.getLocation() != null) {
//                    loc = a.getLocation();
//                }
//                cell7 = new PdfPCell(new Phrase("Location:" + loc));
//                cell7.setBorder(Rectangle.BOTTOM);
//                cell7.setUseBorderPadding(true);
//                cell7.setBorderWidthBottom(5);
//                cell7.setBorderColorBottom(BaseColor.WHITE);
//                table7.addCell(cell7);
//                messageInfo3.add("Location :");
//                messageInfo3.add(loc);
//
//                String details = "";
//                if (a.getDetails() != null) {
//                    details = a.getDetails();
//                }
//                cell7 = new PdfPCell(new Phrase("Details:" + details));
//                cell7.setBorder(Rectangle.BOTTOM);
//                cell7.setUseBorderPadding(true);
//                cell7.setBorderWidthBottom(5);
//                cell7.setBorderColorBottom(BaseColor.WHITE);
//                table7.addCell(cell7);
//                messageInfo3.add("Details :");
//                messageInfo3.add(details);
//
//                String note = "";
//                if (a.getNotes() != null) {
//                    note = a.getNotes();
//                }
//                cell7 = new PdfPCell(new Phrase("Note:" + note));
//                cell7.setBorder(Rectangle.BOTTOM);
//                cell7.setUseBorderPadding(true);
//                cell7.setBorderWidthBottom(5);
//                cell7.setBorderColorBottom(BaseColor.WHITE);
//                table7.addCell(cell7);
//                messageInfo3.add("Note :");
//                messageInfo3.add(note);
//
//                cell7 = new PdfPCell(new Phrase(""));
//                cell7.setBorder(Rectangle.BOTTOM);
//                cell7.setUseBorderPadding(true);
//                cell7.setBorderWidthBottom(5);
//                cell7.setBorderColorBottom(BaseColor.WHITE);
//                table7.addCell(cell7);
//                messageInfo3.add("");
//                messageInfo3.add("");
//
//            }
//            if ((implantsList.size()) % 2 != 0) {
//
//                cell7 = new PdfPCell(new Phrase(""));
//                cell7.setBorder(Rectangle.BOTTOM);
//                cell7.setUseBorderPadding(true);
//                cell7.setBorderWidthBottom(5);
//                cell7.setBorderColorBottom(BaseColor.WHITE);
//                table7.addCell(cell7);
//
//                messageInfo3.add("");
//                messageInfo3.add("");
//            }
//
//            String impNote = "";
//            if (medInfo.getImplantnote() != null) {
//                impNote = medInfo.getImplantnote();
//            }
//            cell7 = new PdfPCell(new Phrase("Medical Implants Note:" + impNote));
//            cell7.setBorder(Rectangle.BOTTOM);
//            cell7.setUseBorderPadding(true);
//            cell7.setBorderWidthBottom(5);
//            cell7.setBorderColorBottom(BaseColor.WHITE);
//            table7.addCell(cell7);
//
//            messageInfo3.add("Medical Implants Note :");
//            messageInfo3.add(impNote);
//
//            cell7 = new PdfPCell(new Phrase(""));
//            cell7.setBorder(Rectangle.BOTTOM);
//            cell7.setUseBorderPadding(true);
//            cell7.setBorderWidthBottom(5);
//            cell7.setBorderColorBottom(BaseColor.WHITE);
//            table7.addCell(cell7);
//
//            table.addCell(table7);
////            HeaderNew.document.add(table7);
//            Paragraph p7 = new Paragraph(" ");
//            DottedLineSeparator line7 = new DottedLineSeparator();
//            line7.setOffset(-4);
//            line7.setLineColor(BaseColor.BLACK);
//            p7.add(line7);
//            table.addCell(p7);
////            HeaderNew.document.add(p7);
//            HeaderNew.addEmptyLine(1);
//
//
//            PdfPTable table10;
//            table10 = new PdfPTable(2);
//            PdfPCell cell10;
//            table10.setWidthPercentage(100);
//
//            cell10 = new PdfPCell(new Phrase("Surgical/Hospitalization History:" + ""));
//            cell10.setBorder(Rectangle.BOTTOM);
//            cell10.setUseBorderPadding(true);
//            cell10.setBorderWidthBottom(5);
//            cell10.setBorderColorBottom(BaseColor.WHITE);
//            table10.addCell(cell10);
//
//            messageInfo3.add("Surgical/Hospitalization History :");
//            messageInfo3.add("");
//
//            cell10 = new PdfPCell(new Phrase(""));
//            cell10.setBorder(Rectangle.BOTTOM);
//            cell10.setUseBorderPadding(true);
//            cell10.setBorderWidthBottom(5);
//            cell10.setBorderColorBottom(BaseColor.WHITE);
//            table10.addCell(cell10);
//            messageInfo3.add("");
//            messageInfo3.add("");
//            for (int i = 0; i < historList.size(); i++) {
//              /*  int k = i + 1;
//
//                cell = new PdfPCell(new Phrase("Surgical/Hospitalization History " + k + " :"));
//                cell.setBorder(Rectangle.BOTTOM);
//                cell.setUseBorderPadding(true);
//                cell.setBorderWidthBottom(5);
//                cell.setBorderColorBottom(BaseColor.WHITE);
//                table.addCell(cell);
//
//                messageInfo3.add("Surgical/Hospitalization History " + k + " :");
//                messageInfo3.add("");*/
//                History a = historList.get(i);
//                String name = "";
//                if (a.getName() != null) {
//                    name = a.getName();
//                    if (name.equals("Other")) {
//                        name = name + " - " + a.getOther();
//                    }
//                }
//                cell10 = new PdfPCell(new Phrase("History:" + name));
//                cell10.setBorder(Rectangle.BOTTOM);
//                cell10.setUseBorderPadding(true);
//                cell10.setBorderWidthBottom(5);
//                cell10.setBorderColorBottom(BaseColor.WHITE);
//                table10.addCell(cell10);
//
//                messageInfo3.add("History :");
//                messageInfo3.add(name);
//
//                cell10 = new PdfPCell(new Phrase("Date:" + historList.get(i).getDate()));
//                cell10.setBorder(Rectangle.BOTTOM);
//                cell10.setUseBorderPadding(true);
//                cell10.setBorderWidthBottom(5);
//                cell10.setBorderColorBottom(BaseColor.WHITE);
//                table10.addCell(cell10);
//
//                messageInfo3.add("Date :");
//                messageInfo3.add(historList.get(i).getDate());
//
//
//                cell10 = new PdfPCell(new Phrase("Doctor:" + historList.get(i).getDoctor()));
//                cell10.setBorder(Rectangle.BOTTOM);
//                cell10.setUseBorderPadding(true);
//                cell10.setBorderWidthBottom(5);
//                cell10.setBorderColorBottom(BaseColor.WHITE);
//                table10.addCell(cell10);
//
//                messageInfo3.add("Doctor :");
//                messageInfo3.add(historList.get(i).getDoctor());
//
//
//                cell10 = new PdfPCell(new Phrase("Location:" + historList.get(i).getDone()));
//                cell10.setBorder(Rectangle.BOTTOM);
//                cell10.setUseBorderPadding(true);
//                cell10.setBorderWidthBottom(5);
//                cell10.setBorderColorBottom(BaseColor.WHITE);
//                table10.addCell(cell10);
//
//                messageInfo3.add("Location :");
//                messageInfo3.add(historList.get(i).getDone());
//
//                cell10 = new PdfPCell(new Phrase(""));
//                cell10.setBorder(Rectangle.BOTTOM);
//                cell10.setUseBorderPadding(true);
//                cell10.setBorderWidthBottom(5);
//                cell10.setBorderColorBottom(BaseColor.WHITE);
//                table10.addCell(cell10);
//                messageInfo3.add("");
//                messageInfo3.add("");
//
//                cell10 = new PdfPCell(new Phrase(""));
//                cell10.setBorder(Rectangle.BOTTOM);
//                cell10.setUseBorderPadding(true);
//                cell10.setBorderWidthBottom(5);
//                cell10.setBorderColorBottom(BaseColor.WHITE);
//                table10.addCell(cell10);
//                messageInfo3.add("");
//                messageInfo3.add("");
//            }
//
//            String surNote = "";
//            if (medInfo.getImplantnote() != null) {
//                surNote = medInfo.getImplantnote();
//            }
//            cell10 = new PdfPCell(new Phrase("Surgical/Hospitalization History Note:" + surNote));
//            cell10.setBorder(Rectangle.BOTTOM);
//            cell10.setUseBorderPadding(true);
//            cell10.setBorderWidthBottom(5);
//            cell10.setBorderColorBottom(BaseColor.WHITE);
//            table10.addCell(cell10);
//
//            messageInfo3.add("Surgical/Hospitalization History Note :");
//            messageInfo3.add(surNote);
//
//            cell10 = new PdfPCell(new Phrase(""));
//            cell10.setBorder(Rectangle.BOTTOM);
//            cell10.setUseBorderPadding(true);
//            cell10.setBorderWidthBottom(5);
//            cell10.setBorderColorBottom(BaseColor.WHITE);
//            table10.addCell(cell10);
//
//            table.addCell(table10);
////            HeaderNew.document.add(table10);
//            Paragraph p10 = new Paragraph(" ");
//            DottedLineSeparator line10 = new DottedLineSeparator();
//            line10.setOffset(-4);
//            line10.setLineColor(BaseColor.BLACK);
//            p10.add(line10);
//            table.addCell(p10);
////            HeaderNew.document.add(p10);
//            HeaderNew.addEmptyLine(1);
////        HeaderNew.table.setWidthPercentage(100f);
//
//
//            PdfPTable table9;
//            table9 = new PdfPTable(2);
//            PdfPCell cell9;
//            table9.setWidthPercentage(100);
//
//            cell9 = new PdfPCell(new Phrase("Preferred Hospital:" + ""));
//            cell9.setBorder(Rectangle.BOTTOM);
//            cell9.setUseBorderPadding(true);
//            cell9.setBorderWidthBottom(5);
//            cell9.setBorderColorBottom(BaseColor.WHITE);
//            table9.addCell(cell9);
//
//            messageInfo3.add("Preferred Hospitals :");
//            messageInfo3.add("");
//
//            cell9 = new PdfPCell(new Phrase(""));
//            cell9.setBorder(Rectangle.BOTTOM);
//            cell9.setUseBorderPadding(true);
//            cell9.setBorderWidthBottom(5);
//            cell9.setBorderColorBottom(BaseColor.WHITE);
//            table9.addCell(cell9);
//            messageInfo3.add("");
//            messageInfo3.add("");
//            for (int i = 0; i < hospitalList.size(); i++) {
//                int k = i + 1;
///*
//                cell = new PdfPCell(new Phrase("Preferred Hospital " + k + " :"));
//                cell.setBorder(Rectangle.BOTTOM);
//                cell.setUseBorderPadding(true);
//                cell.setBorderWidthBottom(5);
//                cell.setBorderColorBottom(BaseColor.WHITE);
//                table.addCell(cell);
//
//                messageInfo3.add("Preferred Hospital " + k + " :");
//                messageInfo3.add("");*/
//
//                cell9 = new PdfPCell(new Phrase("Hospital:" + hospitalList.get(i)));
//                cell9.setBorder(Rectangle.BOTTOM);
//                cell9.setUseBorderPadding(true);
//                cell9.setBorderWidthBottom(5);
//                cell9.setBorderColorBottom(BaseColor.WHITE);
//                table9.addCell(cell9);
//
//                messageInfo3.add("Hospital :");
//                messageInfo3.add(hospitalList.get(i));
//                cell9 = new PdfPCell(new Phrase(""));
//                cell9.setBorder(Rectangle.BOTTOM);
//                cell9.setUseBorderPadding(true);
//                cell9.setBorderWidthBottom(5);
//                cell9.setBorderColorBottom(BaseColor.WHITE);
//                table9.addCell(cell9);
//
//                messageInfo3.add("");
//                messageInfo3.add("");
//            }
//
//            if ((hospitalList.size()) % 2 != 0) {
//
//                cell9 = new PdfPCell(new Phrase(""));
//                cell9.setBorder(Rectangle.BOTTOM);
//                cell9.setUseBorderPadding(true);
//                cell9.setBorderWidthBottom(5);
//                cell9.setBorderColorBottom(BaseColor.WHITE);
//                table9.addCell(cell9);
//
//                messageInfo3.add("");
//                messageInfo3.add("");
//            }
//
//            table.addCell(table9);
////            HeaderNew.document.add(table9);
//            Paragraph p9 = new Paragraph(" ");
//            DottedLineSeparator line9 = new DottedLineSeparator();
//            line9.setOffset(-4);
//            line9.setLineColor(BaseColor.BLACK);
//            p9.add(line9);
//            table.addCell(p9);
////            HeaderNew.document.add(p9);
//            HeaderNew.addEmptyLine(1);
//
//
//            PdfPTable table5;
//            table5 = new PdfPTable(2);
//            PdfPCell cell5;
//            table5.setWidthPercentage(100);
//
//          /*  cell = new PdfPCell(new Phrase("Functional Status:" + ""));
//            cell.setBorder(Rectangle.BOTTOM);
//            cell.setUseBorderPadding(true);
//            cell.setBorderWidthBottom(5);
//            cell.setBorderColorBottom(BaseColor.WHITE);
//            table.addCell(cell);
//
//            messageInfo3.add("Functional Status :");
//            messageInfo3.add("");
//
//            String feeding = "";
//            if (medInfo.getFeed() != null) {
//                feeding = medInfo.getFeed();
//            }
//            cell = new PdfPCell(new Phrase("Feeding:" + feeding));
//            cell.setBorder(Rectangle.BOTTOM);
//            cell.setUseBorderPadding(true);
//            cell.setBorderWidthBottom(5);
//            cell.setBorderColorBottom(BaseColor.WHITE);
//            table.addCell(cell);
//
//            messageInfo3.add("Feeding :");
//            messageInfo3.add(feeding);
//
//            String toileting = "";
//            if (medInfo.getToilet() != null) {
//                toileting = medInfo.getToilet();
//            }
//            cell = new PdfPCell(new Phrase("Toileting:" + toileting));
//            cell.setBorder(Rectangle.BOTTOM);
//            cell.setUseBorderPadding(true);
//            cell.setBorderWidthBottom(5);
//            cell.setBorderColorBottom(BaseColor.WHITE);
//            table.addCell(cell);
//
//            messageInfo3.add("Toileting :");
//            messageInfo3.add(toileting);
//
//            String selfMedicate = "";
//            if (medInfo.getMedicate() != null) {
//                selfMedicate = medInfo.getMedicate();
//            }
//            cell = new PdfPCell(new Phrase("Self Medicate:" + selfMedicate));
//            cell.setBorder(Rectangle.BOTTOM);
//            cell.setUseBorderPadding(true);
//            cell.setBorderWidthBottom(5);
//            cell.setBorderColorBottom(BaseColor.WHITE);
//            table.addCell(cell);
//
//            messageInfo3.add("Self Medicate :");
//            messageInfo3.add(selfMedicate);
//
//            String functionNote = "";
//            if (medInfo.getFunctionnote() != null) {
//                functionNote = medInfo.getFunctionnote();
//            }
//            cell = new PdfPCell(new Phrase("Function Notes:" + functionNote));
//            cell.setBorder(Rectangle.BOTTOM);
//            cell.setUseBorderPadding(true);
//            cell.setBorderWidthBottom(5);
//            cell.setBorderColorBottom(BaseColor.WHITE);
//            table.addCell(cell);
//
//            messageInfo3.add("Function Notes :");
//            messageInfo3.add(functionNote);*/
//
//            /*cell5 = new PdfPCell(new Phrase("Diet:" + ""));
//            cell5.setBorder(Rectangle.BOTTOM);
//            cell5.setUseBorderPadding(true);
//            cell5.setBorderWidthBottom(5);
//            cell5.setBorderColorBottom(BaseColor.WHITE);
//            table5.addCell(cell5);
//
//            messageInfo3.add("Diet :");
//            messageInfo3.add("");*/
//
//
//            String dietNote = "";
//            if (medInfo.getDietNote() != null) {
//                dietNote = medInfo.getDietNote();
//            }
//            cell5 = new PdfPCell(new Phrase("Diet Notes:" + dietNote));
//            cell5.setBorder(Rectangle.BOTTOM);
//            cell5.setUseBorderPadding(true);
//            cell5.setBorderWidthBottom(5);
//            cell5.setBorderColorBottom(BaseColor.WHITE);
//            table5.addCell(cell5);
//
//            messageInfo3.add("Diet Notes :");
//            messageInfo3.add(dietNote);
//
//            String bloodNote = "";
//            if (medInfo.getBloodType() != null) {
//                bloodNote = medInfo.getBloodType();
//            }
//            cell5 = new PdfPCell(new Phrase("Blood Type:" + bloodNote));
//            cell5.setBorder(Rectangle.BOTTOM);
//            cell5.setUseBorderPadding(true);
//            cell5.setBorderWidthBottom(5);
//            cell5.setBorderColorBottom(BaseColor.WHITE);
//            table5.addCell(cell5);
//
//            messageInfo3.add("Blood Type :");
//            messageInfo3.add(bloodNote);
//
//
//            String organDonor = "";
//            if (medInfo.getDonor() != null) {
//                organDonor = medInfo.getDonor();
//            }
//            cell5 = new PdfPCell(new Phrase("Organ Donor:" + organDonor));
//            cell5.setBorder(Rectangle.BOTTOM);
//            cell5.setUseBorderPadding(true);
//            cell5.setBorderWidthBottom(5);
//            cell5.setBorderColorBottom(BaseColor.WHITE);
//            table5.addCell(cell5);
//
//            messageInfo3.add("Organ Donor :");
//            messageInfo3.add(organDonor);
//
//            cell5 = new PdfPCell(new Phrase(""));
//            cell5.setBorder(Rectangle.BOTTOM);
//            cell5.setUseBorderPadding(true);
//            cell5.setBorderWidthBottom(5);
//            cell5.setBorderColorBottom(BaseColor.WHITE);
//            table5.addCell(cell5);
//
//            messageInfo3.add(" ");
//            messageInfo3.add(organDonor);
//
//            table.addCell(table5);
////            HeaderNew.document.add(table5);
//            Paragraph p5 = new Paragraph(" ");
//            DottedLineSeparator line5 = new DottedLineSeparator();
//            line5.setOffset(-4);
//            line5.setLineColor(BaseColor.BLACK);
//            p5.add(line5);
//            table.addCell(p5);
//            HeaderNew.addEmptyLine(1);
//
//
//            PdfPTable table3;
//            table3 = new PdfPTable(2);
//            PdfPCell cell3;
//            table3.setWidthPercentage(100);
//
//
//            cell3 = new PdfPCell(new Phrase("Dental:" + ""));
//            cell3.setBorder(Rectangle.BOTTOM);
//            cell3.setUseBorderPadding(true);
//            cell3.setBorderWidthBottom(5);
//            cell3.setBorderColorBottom(BaseColor.WHITE);
//            table3.addCell(cell3);
//
//            messageInfo3.add("Dental ");
//            messageInfo3.add("");
//
//            cell3 = new PdfPCell(new Phrase(""));
//            cell3.setBorder(Rectangle.BOTTOM);
//            cell3.setUseBorderPadding(true);
//            cell3.setBorderWidthBottom(5);
//            cell3.setBorderColorBottom(BaseColor.WHITE);
//            table3.addCell(cell3);
//            messageInfo3.add("");
//            messageInfo3.add("");
//
//            if (medInfo.getFalses() != null) {
//                upper = medInfo.getFalses();
//            }
//            cell3 = new PdfPCell(new Phrase("Dentures- Removable Upper:" + upper));
//            cell3.setBorder(Rectangle.BOTTOM);
//            cell3.setUseBorderPadding(true);
//            cell3.setBorderWidthBottom(5);
//            cell3.setBorderColorBottom(BaseColor.WHITE);
//            table3.addCell(cell3);
//
//            messageInfo3.add("Dentures- Removable Upper :");
//            messageInfo3.add(upper);
//
//            if (medInfo.getImplants() != null) {
//                lower = medInfo.getImplants();
//            }
//            cell3 = new PdfPCell(new Phrase("Dentures- Removable Lower:" + lower));
//            cell3.setBorder(Rectangle.BOTTOM);
//            cell3.setUseBorderPadding(true);
//            cell3.setBorderWidthBottom(5);
//            cell3.setBorderColorBottom(BaseColor.WHITE);
//            table3.addCell(cell3);
//
//            messageInfo3.add("Dentures- Removable Lower :");
//            messageInfo3.add(lower);
//
//            String drymouth = "";
//            if (medInfo.getMouth() != null) {
//                drymouth = medInfo.getMouth();
//            }
//            cell3 = new PdfPCell(new Phrase("Dry Mouth:" + drymouth));
//            cell3.setBorder(Rectangle.BOTTOM);
//            cell3.setUseBorderPadding(true);
//            cell3.setBorderWidthBottom(5);
//            cell3.setBorderColorBottom(BaseColor.WHITE);
//            table3.addCell(cell3);
//
//            messageInfo3.add("Dry Mouth :");
//            messageInfo3.add(drymouth);
//
//            String mouthnote = "";
//            if (medInfo.getMouthnote() != null) {
//                mouthnote = medInfo.getMouthnote();
//            }
//            cell3 = new PdfPCell(new Phrase("Notes:" + mouthnote));
//            cell3.setBorder(Rectangle.BOTTOM);
//            cell3.setUseBorderPadding(true);
//            cell3.setBorderWidthBottom(5);
//            cell3.setBorderColorBottom(BaseColor.WHITE);
//            table3.addCell(cell3);
//
//            messageInfo3.add("Notes :");
//            messageInfo3.add(mouthnote);
//
//            table.addCell(table3);
//            Paragraph p3 = new Paragraph(" ");
//            DottedLineSeparator line3 = new DottedLineSeparator();
//            line3.setOffset(-4);
//            line3.setLineColor(BaseColor.BLACK);
//            p3.add(line3);
//            table.addCell(p3);
//            HeaderNew.addEmptyLine(1);
//
//
//            PdfPTable table4;
//            table4 = new PdfPTable(2);
//            PdfPCell cell4;
//            table4.setWidthPercentage(100);
//
//            cell4 = new PdfPCell(new Phrase("Hearing & Speech:" + ""));
//            cell4.setBorder(Rectangle.BOTTOM);
//            cell4.setUseBorderPadding(true);
//            cell4.setBorderWidthBottom(5);
//            cell4.setBorderColorBottom(BaseColor.WHITE);
//            table4.addCell(cell4);
//
//            messageInfo3.add("Hearing & Speech :");
//            messageInfo3.add("");
//
//            cell4 = new PdfPCell(new Phrase(""));
//            cell4.setBorder(Rectangle.BOTTOM);
//            cell4.setUseBorderPadding(true);
//            cell4.setBorderWidthBottom(5);
//            cell4.setBorderColorBottom(BaseColor.WHITE);
//            table4.addCell(cell4);
//            messageInfo3.add("");
//            messageInfo3.add("");
//
//            String aid = "";
//            if (medInfo.getAid() != null) {
//                aid = medInfo.getAid();
//            }
//            cell4 = new PdfPCell(new Phrase("Hearing Aid(s):" + aid));
//            cell4.setBorder(Rectangle.BOTTOM);
//            cell4.setUseBorderPadding(true);
//            cell4.setBorderWidthBottom(5);
//            cell4.setBorderColorBottom(BaseColor.WHITE);
//            table4.addCell(cell4);
//
//            messageInfo3.add("Hearing Aid(s) :");
//            messageInfo3.add(aid);
//
//            String speech = "";
//            if (medInfo.getSpeech() != null) {
//                speech = medInfo.getSpeech();
//            }
//            cell4 = new PdfPCell(new Phrase("Speech Impaired:" + speech));
//            cell4.setBorder(Rectangle.BOTTOM);
//            cell4.setUseBorderPadding(true);
//            cell4.setBorderWidthBottom(5);
//            cell4.setBorderColorBottom(BaseColor.WHITE);
//            table4.addCell(cell4);
//
//            messageInfo3.add("Speech Impaired :");
//            messageInfo3.add(speech);
//
//            String aidNote = "";
//            if (medInfo.getAideNote() != null) {
//                aidNote = medInfo.getAideNote();
//            }
//            cell4 = new PdfPCell(new Phrase("Notes:" + aidNote));
//            cell4.setBorder(Rectangle.BOTTOM);
//            cell4.setUseBorderPadding(true);
//            cell4.setBorderWidthBottom(5);
//            cell4.setBorderColorBottom(BaseColor.WHITE);
//            table4.addCell(cell4);
//
//            messageInfo3.add("Notes :");
//            messageInfo3.add(aidNote);
//
//            cell4 = new PdfPCell(new Phrase(""));
//            cell4.setBorder(Rectangle.BOTTOM);
//            cell4.setUseBorderPadding(true);
//            cell4.setBorderWidthBottom(5);
//            cell4.setBorderColorBottom(BaseColor.WHITE);
//            table4.addCell(cell4);
//            messageInfo3.add("");
//            messageInfo3.add("");
//
//            table.addCell(table4);
//            Paragraph p4 = new Paragraph(" ");
//            DottedLineSeparator line4 = new DottedLineSeparator();
//            line4.setOffset(-4);
//            line4.setLineColor(BaseColor.BLACK);
//            p4.add(line4);
//            table.addCell(p4);
//            HeaderNew.addEmptyLine(1);
//
//
//            PdfPTable table8;
//            table8 = new PdfPTable(2);
//            PdfPCell cell8;
//            table8.setWidthPercentage(100);
//            // String Implants="";
//            cell8 = new PdfPCell(new Phrase("Immunizations/Vaccines :" + ""));
//            cell8.setBorder(Rectangle.BOTTOM);
//            cell8.setUseBorderPadding(true);
//            cell8.setBorderWidthBottom(5);
//            cell8.setBorderColorBottom(BaseColor.WHITE);
//            table8.addCell(cell8);
//
//            messageInfo3.add("Immunizations/Vaccines :");
//            messageInfo3.add("");
//
//            cell8 = new PdfPCell(new Phrase(""));
//            cell8.setBorder(Rectangle.BOTTOM);
//            cell8.setUseBorderPadding(true);
//            cell8.setBorderWidthBottom(5);
//            cell8.setBorderColorBottom(BaseColor.WHITE);
//            table8.addCell(cell8);
//            messageInfo3.add("");
//            messageInfo3.add("");
//
//            for (int i = 0; i < vaccineList.size(); i++) {
//                /*int k = i + 1;
//                cell = new PdfPCell(new Phrase("Immunizations/Vaccines " + k + " :"));
//                cell.setBorder(Rectangle.BOTTOM);
//                cell.setUseBorderPadding(true);
//                cell.setBorderWidthBottom(5);
//                cell.setBorderColorBottom(BaseColor.WHITE);
//                table.addCell(cell);
//                messageInfo3.add("Immunizations/Vaccines " + k + " :");
//                messageInfo3.add("");*/
//                Vaccine a = vaccineList.get(i);
//                String name = "";
//                if (a.getName() != null) {
//                    name = a.getName();
//                    if (name.equals("Other")) {
//                        name = name + " - " + a.getOther();
//                    }
//                }
//
//                cell8 = new PdfPCell(new Phrase("Immunization/Vaccine:" + name));
//                cell8.setBorder(Rectangle.BOTTOM);
//                cell8.setUseBorderPadding(true);
//                cell8.setBorderWidthBottom(5);
//                cell8.setBorderColorBottom(BaseColor.WHITE);
//                table8.addCell(cell8);
//                messageInfo3.add("Immunization/Vaccine :");
//                messageInfo3.add(name);
//
//            /*    cell8 = new PdfPCell(new Phrase("Other Immunization/Vaccine:" + vaccineList.get(i).getOther()));
//                cell8.setBorder(Rectangle.BOTTOM);
//                cell8.setUseBorderPadding(true);
//                cell8.setBorderWidthBottom(5);
//                cell8.setBorderColorBottom(BaseColor.WHITE);
//                table8.addCell(cell8);
//                messageInfo3.add("Other Immunization/Vaccine :");
//                messageInfo3.add(vaccineList.get(i).getOther());
//*/
//                cell8 = new PdfPCell(new Phrase("Note (Approximate Date, Location, Details):" + vaccineList.get(i).getDate()));
//                cell8.setBorder(Rectangle.BOTTOM);
//                cell8.setUseBorderPadding(true);
//                cell8.setBorderWidthBottom(5);
//                cell8.setBorderColorBottom(BaseColor.WHITE);
//                table8.addCell(cell8);
//                messageInfo3.add("Note (Approximate Date, Location, Details) :");
//                messageInfo3.add(vaccineList.get(i).getDate());
//
//                cell8 = new PdfPCell(new Phrase(""));
//                cell8.setBorder(Rectangle.BOTTOM);
//                cell8.setUseBorderPadding(true);
//                cell8.setBorderWidthBottom(5);
//                cell8.setBorderColorBottom(BaseColor.WHITE);
//                table8.addCell(cell8);
//                messageInfo3.add("");
//                messageInfo3.add("");
//
//                cell8 = new PdfPCell(new Phrase(""));
//                cell8.setBorder(Rectangle.BOTTOM);
//                cell8.setUseBorderPadding(true);
//                cell8.setBorderWidthBottom(5);
//                cell8.setBorderColorBottom(BaseColor.WHITE);
//                table8.addCell(cell8);
//                messageInfo3.add("");
//                messageInfo3.add("");
//
//               /* cell8 = new PdfPCell(new Phrase(""));
//                cell8.setBorder(Rectangle.BOTTOM);
//                cell8.setUseBorderPadding(true);
//                cell8.setBorderWidthBottom(5);
//                cell8.setBorderColorBottom(BaseColor.WHITE);
//                table8.addCell(cell8);
//                messageInfo3.add("");
//                messageInfo3.add("");*/
//            }
//
//            if ((vaccineList.size()) % 2 != 0) {
//
//                cell8 = new PdfPCell(new Phrase(""));
//                cell8.setBorder(Rectangle.BOTTOM);
//                cell8.setUseBorderPadding(true);
//                cell8.setBorderWidthBottom(5);
//                cell8.setBorderColorBottom(BaseColor.WHITE);
//                table8.addCell(cell8);
//
//                messageInfo3.add("");
//                messageInfo3.add("");
//            }
//
//            String vacNote = "";
//            if (medInfo.getVaccinenote() != null) {
//                vacNote = medInfo.getVaccinenote();
//            }
//            cell8 = new PdfPCell(new Phrase("Immunization Note:" + vacNote));
//            cell8.setBorder(Rectangle.BOTTOM);
//            cell8.setUseBorderPadding(true);
//            cell8.setBorderWidthBottom(5);
//            cell8.setBorderColorBottom(BaseColor.WHITE);
//            table8.addCell(cell8);
//
//            messageInfo3.add("Immunization Note :");
//            messageInfo3.add(vacNote);
//
//            cell8 = new PdfPCell(new Phrase(""));
//            cell8.setBorder(Rectangle.BOTTOM);
//            cell8.setUseBorderPadding(true);
//            cell8.setBorderWidthBottom(5);
//            cell8.setBorderColorBottom(BaseColor.WHITE);
//            table8.addCell(cell8);
//
//
//            table.addCell(table8);
//            Paragraph p8 = new Paragraph(" ");
//            DottedLineSeparator line8 = new DottedLineSeparator();
//            line8.setOffset(-4);
//            line8.setLineColor(BaseColor.BLACK);
//            p8.add(line8);
//            table.addCell(p8);
//            HeaderNew.addEmptyLine(1);
//
//
//            PdfPTable table2;
//            table2 = new PdfPTable(2);
//            PdfPCell cell2;
//            table2.setWidthPercentage(100);
//
//
//            cell2 = new PdfPCell(new Phrase("Vision:" + ""));
//            cell2.setBorder(Rectangle.BOTTOM);
//            cell2.setUseBorderPadding(true);
//            cell2.setBorderWidthBottom(5);
//            cell2.setBorderColorBottom(BaseColor.WHITE);
//            table2.addCell(cell2);
//            messageInfo3.add("Vision :");
//            messageInfo3.add("");
//
//            cell2 = new PdfPCell(new Phrase(""));
//            cell2.setBorder(Rectangle.BOTTOM);
//            cell2.setUseBorderPadding(true);
//            cell2.setBorderWidthBottom(5);
//            cell2.setBorderColorBottom(BaseColor.WHITE);
//            table2.addCell(cell2);
//            messageInfo3.add("");
//            messageInfo3.add("");
//
//
//            if (medInfo.getGlass() != null) {
//                glass = medInfo.getGlass();
//            }
//            cell2 = new PdfPCell(new Phrase("Glasses:" + glass));
//            cell2.setBorder(Rectangle.BOTTOM);
//            cell2.setUseBorderPadding(true);
//            cell2.setBorderWidthBottom(5);
//            cell2.setBorderColorBottom(BaseColor.WHITE);
//            table2.addCell(cell2);
//
//            messageInfo3.add("Glasses :");
//            messageInfo3.add(glass);
//
//            if (medInfo.getLense() != null) {
//                lense = medInfo.getLense();
//            }
//            cell2 = new PdfPCell(new Phrase("Contact Lenses:" + lense));
//            cell2.setBorder(Rectangle.BOTTOM);
//            cell2.setUseBorderPadding(true);
//            cell2.setBorderWidthBottom(5);
//            cell2.setBorderColorBottom(BaseColor.WHITE);
//            table2.addCell(cell2);
//
//            messageInfo3.add("Contact Lense :");
//            messageInfo3.add(lense);
//
//            if (medInfo.getBlind() != null) {
//                blind = medInfo.getBlind();
//            }
//            cell2 = new PdfPCell(new Phrase("Color Blind:" + blind));
//            cell2.setBorder(Rectangle.BOTTOM);
//            cell2.setUseBorderPadding(true);
//            cell2.setBorderWidthBottom(5);
//            cell2.setBorderColorBottom(BaseColor.WHITE);
//            table2.addCell(cell2);
//
//            messageInfo3.add("Color Blind :");
//            messageInfo3.add(blind);
//
//            if (medInfo.getVisionNote() != null) {
//                visionNote = medInfo.getVisionNote();
//            }
//            cell2 = new PdfPCell(new Phrase("Notes:" + visionNote));
//            cell2.setBorder(Rectangle.BOTTOM);
//            cell2.setUseBorderPadding(true);
//            cell2.setBorderWidthBottom(5);
//            cell2.setBorderColorBottom(BaseColor.WHITE);
//            table2.addCell(cell2);
//
//            messageInfo3.add("Notes :");
//            messageInfo3.add(visionNote);
//
//            table.addCell(table2);
//            Paragraph p2 = new Paragraph(" ");
//            DottedLineSeparator line2 = new DottedLineSeparator();
//            line2.setOffset(-4);
//            line2.setLineColor(BaseColor.BLACK);
//            p2.add(line2);
//            table.addCell(p2);
//            HeaderNew.addEmptyLine(1);
//
//
//            PdfPTable table16;
//            table16 = new PdfPTable(2);
//            PdfPCell cell16;
//            table16.setWidthPercentage(100);
//
//
//            String tobaco = "";
//            if (medInfo.getTobaco() != null) {
//                tobaco = medInfo.getTobaco();
//            }
//            cell16 = new PdfPCell(new Phrase("Smoking / Tobacco Use:" + tobaco));
//            cell16.setBorder(Rectangle.BOTTOM);
//            cell16.setUseBorderPadding(true);
//            cell16.setBorderWidthBottom(5);
//            cell16.setBorderColorBottom(BaseColor.WHITE);
//            table16.addCell(cell16);
//            messageInfo3.add("Smoking / Tobacco Use :");
//            messageInfo3.add(tobaco);
//
//            String tobacoType = "";
//            if (medInfo.getT_type() != null) {
//                tobacoType = medInfo.getT_type();
//            }
//            cell16 = new PdfPCell(new Phrase("Type:" + tobacoType));
//            cell16.setBorder(Rectangle.BOTTOM);
//            cell16.setUseBorderPadding(true);
//            cell16.setBorderWidthBottom(5);
//            cell16.setBorderColorBottom(BaseColor.WHITE);
//            table16.addCell(cell16);
//
//            messageInfo3.add("Type :");
//            messageInfo3.add(tobacoType);
//
//            String tobacoAmt = "";
//            if (medInfo.getT_amt() != null) {
//                tobacoAmt = medInfo.getT_amt();
//            }
//            cell16 = new PdfPCell(new Phrase("Amount/Frequency:" + tobacoAmt));
//            cell16.setBorder(Rectangle.BOTTOM);
//            cell16.setUseBorderPadding(true);
//            cell16.setBorderWidthBottom(5);
//            cell16.setBorderColorBottom(BaseColor.WHITE);
//            table16.addCell(cell16);
//
//            messageInfo3.add("Amount/Frequency :");
//            messageInfo3.add(tobacoAmt);
//
//            String tobacoYear = "";
//            if (medInfo.getT_year() != null) {
//                tobacoYear = medInfo.getT_year();
//            }
//            cell16 = new PdfPCell(new Phrase("Number of Years & When Stopped (if applicable):" + tobacoYear));
//            cell16.setBorder(Rectangle.BOTTOM);
//            cell16.setUseBorderPadding(true);
//            cell16.setBorderWidthBottom(5);
//            cell16.setBorderColorBottom(BaseColor.WHITE);
//            table16.addCell(cell16);
//
//            messageInfo3.add("Number of Years & When Stopped (if applicable) :");
//            messageInfo3.add(tobacoYear);
//
//
//            table.addCell(table16);
//            Paragraph p16 = new Paragraph(" ");
//            DottedLineSeparator line16 = new DottedLineSeparator();
//            line16.setOffset(-4);
//            line16.setLineColor(BaseColor.BLACK);
//            p16.add(line16);
//            table.addCell(p16);
//            HeaderNew.addEmptyLine(1);
//
//            /**/
//            PdfPTable table17;
//            table17 = new PdfPTable(2);
//            PdfPCell cell17;
//            table17.setWidthPercentage(100);
//
//
//            String alcohol = "";
//            if (medInfo.getDrink() != null) {
//                alcohol = medInfo.getDrink();
//            }
//            cell17 = new PdfPCell(new Phrase("Alcohol:" + alcohol));
//            cell17.setBorder(Rectangle.BOTTOM);
//            cell17.setUseBorderPadding(true);
//            cell17.setBorderWidthBottom(5);
//            cell17.setBorderColorBottom(BaseColor.WHITE);
//            table17.addCell(cell17);
//            messageInfo3.add("Alcohol :");
//            messageInfo3.add(alcohol);
//
//
//            String alcoholAmt = "";
//            if (medInfo.getDrink_amt() != null) {
//                alcoholAmt = medInfo.getDrink_amt();
//            }
//            cell17 = new PdfPCell(new Phrase("Amount/Frequency/Other Notes:" + alcoholAmt));
//            cell17.setBorder(Rectangle.BOTTOM);
//            cell17.setUseBorderPadding(true);
//            cell17.setBorderWidthBottom(5);
//            cell17.setBorderColorBottom(BaseColor.WHITE);
//            table17.addCell(cell17);
//
//            messageInfo3.add("Amount/Frequency/Other Notes :");
//            messageInfo3.add(alcoholAmt);
//
//            String alcoholYear = "";
//            if (medInfo.getDrink_year() != null) {
//                alcoholYear = medInfo.getDrink_year();
//            }
//            cell17 = new PdfPCell(new Phrase("Number of Years & When Stopped (if applicable):" + alcoholYear));
//            cell17.setBorder(Rectangle.BOTTOM);
//            cell17.setUseBorderPadding(true);
//            cell17.setBorderWidthBottom(5);
//            cell17.setBorderColorBottom(BaseColor.WHITE);
//            table17.addCell(cell17);
//
//            messageInfo3.add("Number of Years & When Stopped (if applicable) :");
//            messageInfo3.add(alcoholYear);
//
//            cell17 = new PdfPCell(new Phrase(""));
//            cell17.setBorder(Rectangle.BOTTOM);
//            cell17.setUseBorderPadding(true);
//            cell17.setBorderWidthBottom(5);
//            cell17.setBorderColorBottom(BaseColor.WHITE);
//            table17.addCell(cell17);
//
//            messageInfo3.add("");
//            messageInfo3.add("");
//
//            table.addCell(table17);
//            Paragraph p17 = new Paragraph(" ");
//            DottedLineSeparator line17 = new DottedLineSeparator();
//            line17.setOffset(-4);
//            line17.setLineColor(BaseColor.BLACK);
//            p17.add(line17);
//            table.addCell(p17);
//            HeaderNew.addEmptyLine(1);
//
//
//            /**/
//            PdfPTable table18;
//            table18 = new PdfPTable(2);
//            PdfPCell cell18;
//            table18.setWidthPercentage(100);
//
//
//            String drug = "";
//            if (medInfo.getDrug() != null) {
//                drug = medInfo.getDrug();
//            }
//            cell18 = new PdfPCell(new Phrase("Recreational Drug Use:" + drug));
//            cell18.setBorder(Rectangle.BOTTOM);
//            cell18.setUseBorderPadding(true);
//            cell18.setBorderWidthBottom(5);
//            cell18.setBorderColorBottom(BaseColor.WHITE);
//            table18.addCell(cell18);
//            messageInfo3.add("Recreational Drug Use :");
//            messageInfo3.add(drug);
//
//
//            String drugType = "";
//            if (medInfo.getDrug_type() != null) {
//                drugType = medInfo.getDrug_type();
//            }
//            cell18 = new PdfPCell(new Phrase("Type:" + drugType));
//            cell18.setBorder(Rectangle.BOTTOM);
//            cell18.setUseBorderPadding(true);
//            cell18.setBorderWidthBottom(5);
//            cell18.setBorderColorBottom(BaseColor.WHITE);
//            table18.addCell(cell18);
//
//            messageInfo3.add("Type :");
//            messageInfo3.add(drugType);
//
//
//            String drugAmt = "";
//            if (medInfo.getDrug_amt() != null) {
//                drugAmt = medInfo.getDrug_amt();
//            }
//            cell18 = new PdfPCell(new Phrase("Amount/Frequency/Other Notes:" + drugAmt));
//            cell18.setBorder(Rectangle.BOTTOM);
//            cell18.setUseBorderPadding(true);
//            cell18.setBorderWidthBottom(5);
//            cell18.setBorderColorBottom(BaseColor.WHITE);
//            table18.addCell(cell18);
//
//            messageInfo3.add("Amount/Frequency/Other Notes :");
//            messageInfo3.add(drugAmt);
//
//
//            String drugYear = "";
//            if (medInfo.getDrug_year() != null) {
//                drugYear = medInfo.getDrug_year();
//            }
//            cell18 = new PdfPCell(new Phrase("Number of Years & When Stopped (if applicable):" + drugYear));
//            cell18.setBorder(Rectangle.BOTTOM);
//            cell18.setUseBorderPadding(true);
//            cell18.setBorderWidthBottom(5);
//            cell18.setBorderColorBottom(BaseColor.WHITE);
//            table18.addCell(cell18);
//
//            messageInfo3.add("Number of Years & When Stopped (if applicable) :");
//            messageInfo3.add(drugYear);
//
//
//            table.addCell(table18);
//            Paragraph p18 = new Paragraph(" ");
//            DottedLineSeparator line18 = new DottedLineSeparator();
//            line18.setOffset(-4);
//            line18.setLineColor(BaseColor.BLACK);
//            p18.add(line18);
//            table.addCell(p18);
//            HeaderNew.addEmptyLine(1);
//
//
//            HeaderNew.document.add(table);
//            //  HeaderNew.addEmptyLine(1);
//
//        }


        // New PDF changes - Nikita
        try {
            // Font
            IndividualNewFont();

            HeaderNew.addNewChank("Medical Profile", ppys);
            messageInfo.add("Medical Profile");

            //Allergy -1
            HeaderNew.addEmptyLine(1);

            PdfPTable table;
            table = new PdfPTable(1);

            table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table.setTableEvent(new RoundedBorder());
            table.getDefaultCell().setPaddingBottom(15);
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();
            cell.setPaddingTop(10);
            cell.setPaddingBottom(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
          //  table.setKeepTogether(false);
          //  table.setSplitLate(false);

            PdfPTable table1;
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            //table1.setKeepTogether(false);
           // table1.setSplitLate(false);
            table1.setWidthPercentage(100);

            PdfPCell cell1;

            //if (!allargyLists.isEmpty()) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                Paragraph pf = new Paragraph();
                Phrase pps = new Phrase();
                Chunk underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                Chunk underline = new Chunk("Allergies & Medication Reactions  ", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                //cell1.setPaddingTop(10);
//                cell1.setPaddingBottom(10);
                cell1.setPaddingRight(10);
                cell1.setPaddingLeft(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));

                //cell5 = new PdfPCell();
                PdfPTable tableIN;
                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
              //  tableIN.setKeepTogether(false);
              //  tableIN.setSplitLate(false);

                for (int i = 0; i < allargyLists.size(); i++) {

                    int k = i + 1;
                    PdfPCell cellIN;
                    Allergy a = allargyLists.get(i);

                    //cellIN = new PdfPCell(new Phrase("Name:" + name));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Allergy " + k + "", a.getAllergy());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    //  cell = new PdfPCell(new Phrase("Type of Pet / Breed :" + breed));
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Reaction", a.getReaction());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    if (a.getReaction().equalsIgnoreCase("other")) {
                        cellIN = new PdfPCell();
                        if (i == allargyLists.size() - 1) {
                            HeaderNew.cellDesignNoline(cellIN, tableIN, "Other Reaction", a.getOtherReaction());
                        } else {
                            HeaderNew.cellDesign(cellIN, tableIN, "Other Reaction", a.getOtherReaction());
                        }
                        cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                        cellIN.setPaddingTop(14);
                        tableIN.addCell(cellIN);
                    }

                    // cell = new PdfPCell(new Phrase("Color:" + color));
                    cellIN = new PdfPCell();
                    if (i == allargyLists.size() - 1) {
                        HeaderNew.cellDesignNoline(cellIN, tableIN, "Treatment", a.getTreatment());
                    } else {
                        HeaderNew.cellDesign(cellIN, tableIN, "Treatment", a.getTreatment());
                    }
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    if (!a.getReaction().equalsIgnoreCase("other")) {
                        cellIN = new PdfPCell();
                        HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
                        cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                        tableIN.addCell(cellIN);
                    }

//                    cell1.addElement(new Paragraph(" "));
                }
                cell1.addElement(tableIN);
                table1.addCell(cell1);
          //  }
            cell.addElement(table1);
            table.addCell(cell);

            PdfPCell cellIN = new PdfPCell();
            HeaderNew.cellDesignbroadline(cellIN, table, "Allergy Note", medInfo.getAllergyNote());
           // cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
            cellIN.setPaddingTop(10);
            table.addCell(cellIN);

            cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);

            //Pre-exist -2
          //  HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            //table.setKeepTogether(false);
           // table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

          //  if (!conditionList.isEmpty()) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Medical Conditions", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

               // cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);

                for (int i = 0; i < conditionList.size(); i++) {

                    int k = i + 1;

                    cellIN = new PdfPCell();
                    if (i == conditionList.size() - 1) {
                        HeaderNew.cellDesignNoline(cellIN, tableIN, "Medical Condition " + k + "", conditionList.get(i));
                    } else {
                        HeaderNew.cellDesign(cellIN, tableIN, "Medical Condition " + k + "", conditionList.get(i));
                    }
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                }
                cell1.addElement(tableIN);
                table1.addCell(cell1);
       //     }
            cell.addElement(table1);
            table.addCell(cell);

            cellIN = new PdfPCell();
            HeaderNew.cellDesignbroadline(cellIN, table, "Medical Conditions Note", medInfo.getNote());
           // cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
            cellIN.setPaddingTop(10);
            table.addCell(cellIN);

            cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);

            //Implants -3
          //  HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

          //  if (!implantsList.isEmpty()) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Medical Implants", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

               // cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);

                for (int i = 0; i < implantsList.size(); i++) {

                    int k = i + 1;

                    Implant implant = implantsList.get(i);

                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Medical Implant " + k + "", implant.getName());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    if (implant.getName().equalsIgnoreCase("other")) {
                        cellIN = new PdfPCell();
                        HeaderNew.cellDesign(cellIN, tableIN, "Other", implant.getOther());
                        cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                        cellIN.setPaddingTop(14);
                        tableIN.addCell(cellIN);
                    }

                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Date", implant.getDate());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Hospital and Doctor", implant.getLocation());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    cellIN = new PdfPCell();
                    if (i == implantsList.size() - 1) {
                        HeaderNew.cellDesign(cellIN, tableIN, "Details and Serial Number", implant.getDetails());
                    } else {
                        HeaderNew.cellDesign(cellIN, tableIN, "Details and Serial Number", implant.getDetails());
                    }
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    cellIN = new PdfPCell();
                    if (i == implantsList.size() - 1) {
                        HeaderNew.cellDesignNoline(cellIN, tableIN, "Notes", implant.getNotes());
                    } else {
                        HeaderNew.cellDesign(cellIN, tableIN, "Notes", implant.getNotes());
                    }
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    if (!implant.getName().equalsIgnoreCase("other")) {
                        cellIN = new PdfPCell();
                        HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
                        cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                        tableIN.addCell(cellIN);
                    }

                }
                cell1.addElement(tableIN);
                table1.addCell(cell1);
       //     }
            cell.addElement(table1);
            table.addCell(cell);

            cellIN = new PdfPCell();
            HeaderNew.cellDesignbroadline(cellIN, table, "Medical Implant Note", medInfo.getImplantnote());
           // cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
            cellIN.setPaddingTop(10);
            table.addCell(cellIN);

            /*cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);*/

            //History -4
         //   HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

        //    if (!historList.isEmpty()) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Surgical/Hospitalization History", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);

                for (int i = 0; i < historList.size(); i++) {

                    int k = i + 1;

                    History implant = historList.get(i);

                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Surgical History " + k + "", implant.getName());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    if (implant.getName().equalsIgnoreCase("other")) {
                        cellIN = new PdfPCell();
                        HeaderNew.cellDesign(cellIN, tableIN, "Other", implant.getOther());
                        cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                        cellIN.setPaddingTop(14);
                        tableIN.addCell(cellIN);
                    }

                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Date", implant.getDate());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    cellIN = new PdfPCell();
                    if (i == historList.size() - 1 && !implant.getName().equalsIgnoreCase("other")) {
                        HeaderNew.cellDesignNoline(cellIN, tableIN, "Doctor", implant.getDoctor());
                    } else {
                        HeaderNew.cellDesign(cellIN, tableIN, "Doctor", implant.getDoctor());
                    }
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    cellIN = new PdfPCell();
                    if (i == historList.size() - 1) {
                        HeaderNew.cellDesignNoline(cellIN, tableIN, "Treatment Location & Other Details", implant.getDone());
                    } else {
                        HeaderNew.cellDesign(cellIN, tableIN, "Treatment Location & Other Details", implant.getDone());
                    }
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    if (implant.getName().equalsIgnoreCase("other")) {
                        cellIN = new PdfPCell();
                        HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
                        cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                        tableIN.addCell(cellIN);
                    }

                }
                cell1.addElement(tableIN);
                table1.addCell(cell1);
          //  }
            cell.addElement(table1);
            table.addCell(cell);

            cellIN = new PdfPCell();
            HeaderNew.cellDesignbroadline(cellIN, table, "Surgical/Hospitalization History Note", medInfo.getHistorynote());
           // cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
            cellIN.setPaddingTop(10);
            table.addCell(cellIN);

            /*cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);*/

            //hospital -5
           // HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

         //   if (!hospitalList.isEmpty()) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Preferred Hospitals", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);

                for (int i = 0; i < hospitalList.size(); i++) {

                    int k = i + 1;

                    String history = hospitalList.get(i);

                    cellIN = new PdfPCell();
                    if (i == hospitalList.size() - 1) {
                        HeaderNew.cellDesignNoline(cellIN, tableIN, "Hospital Preference" + k + "", history);
                    } else {
                        HeaderNew.cellDesign(cellIN, tableIN, "Hospital Preference" + k + "", history);
                    }
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);
                }
                cell1.addElement(tableIN);
                table1.addCell(cell1);
          //  }
            cell.addElement(table1);
            table.addCell(cell);

          /*  cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);*/

            //blood -6
            //HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);



                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Blood Type", BlackFont);
                pps.add(underline);

                pf.add(pps);

                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);

                cellIN = new PdfPCell();
                HeaderNew.cellDesignNoline(cellIN, tableIN, "Blood Type", medInfo.getBloodType());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cellIN = new PdfPCell();
                HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
            cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cell1.addElement(tableIN);
                table1.addCell(cell1);


            cell1 = new PdfPCell();
            HeaderNew.cellDesign(cell1, table, "", "Empty");
            table1.addCell(cell1);

            cell.addElement(table1);
            table.addCell(cell);

            //dental -7
           // HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

            if (medInfo != null) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Dental", BlackFont);
                pps.add(underline);

                pf.add(pps);

                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);


                cellIN = new PdfPCell();
                HeaderNew.cellDesign(cellIN, tableIN, "Dentures - Removable Upper", medInfo.getFalses());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cellIN = new PdfPCell();
                HeaderNew.cellDesign(cellIN, tableIN, "Dry Mouth", medInfo.getMouth());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cellIN = new PdfPCell();
                HeaderNew.cellDesignNoline(cellIN, tableIN, "Dentures - Removable Lower", medInfo.getImplants());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cellIN = new PdfPCell();
                HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cell1.addElement(tableIN);
                table1.addCell(cell1);
            }
            cell.addElement(table1);
            table.addCell(cell);

            cellIN = new PdfPCell();
            HeaderNew.cellDesignbroadline(cellIN, table, "Dental Note", medInfo.getMouthnote());
           // cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
            cellIN.setPaddingTop(10);
            table.addCell(cellIN);

            cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);

            //diet -8
           // HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

          //  if (!medInfo.getDietNote().isEmpty()) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Diet", BlackFont);
                pps.add(underline);

                pf.add(pps);

                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);


                cellIN = new PdfPCell();
                HeaderNew.cellDesignNoline(cellIN, tableIN, "Diet Note", medInfo.getDietNote());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cellIN = new PdfPCell();
                HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
            cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cell1.addElement(tableIN);
                table1.addCell(cell1);
       //     }
            cell.addElement(table1);
            table.addCell(cell);

            cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);

            //speech -9
          //  HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

            if (medInfo != null) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Hearing & Speech", BlackFont);
                pps.add(underline);

                pf.add(pps);

                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);


                cellIN = new PdfPCell();
                HeaderNew.cellDesignNoline(cellIN, tableIN, "Hearing Aid(s)", medInfo.getAid());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cellIN = new PdfPCell();
                HeaderNew.cellDesignNoline(cellIN, tableIN, "Speech Impairment", medInfo.getSpeech());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cell1.addElement(tableIN);
                table1.addCell(cell1);
            }
            cell.addElement(table1);
            table.addCell(cell);

            cellIN = new PdfPCell();
            HeaderNew.cellDesignbroadline(cellIN, table, "Hearing & Speech Note", medInfo.getAideNote());
           // cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
            cellIN.setPaddingTop(10);
            table.addCell(cellIN);

            cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);

            //vaccine -10
           // HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

          //  if (!vaccineList.isEmpty()) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Immunizations/Vaccines", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);

                for (int i = 0; i < vaccineList.size(); i++) {

                    int k = i + 1;

                    Vaccine history = vaccineList.get(i);

                    cellIN = new PdfPCell();
                    if (i == vaccineList.size() - 1 && !history.getName().equalsIgnoreCase("other")) {
                        HeaderNew.cellDesignNoline(cellIN, tableIN, "Immunization/Vaccine " + k + "", history.getName());
                    } else {
                        HeaderNew.cellDesign(cellIN, tableIN, "Immunization/Vaccine " + k + "", history.getName());
                    }
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    if (history.getName().equalsIgnoreCase("other")) {
                        cellIN = new PdfPCell();
                        HeaderNew.cellDesign(cellIN, tableIN, "Other", history.getOther());
                        cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                        cellIN.setPaddingTop(14);
                        tableIN.addCell(cellIN);
                    }

                    cellIN = new PdfPCell();
                    if (i == vaccineList.size() - 1) {
                        HeaderNew.cellDesignNoline(cellIN, tableIN, "Date", history.getDate());
                    } else {
                        HeaderNew.cellDesign(cellIN, tableIN, "Date", history.getDate());
                    }
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    if (history.getName().equalsIgnoreCase("other")) {
                        cellIN = new PdfPCell();
                        HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
                        cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                        cellIN.setPaddingTop(14);
                        tableIN.addCell(cellIN);
                    }
                }
                cell1.addElement(tableIN);
                table1.addCell(cell1);
         //   }
            cell.addElement(table1);
            table.addCell(cell);

            cellIN = new PdfPCell();
            HeaderNew.cellDesignbroadline(cellIN, table, "Immunizations/Vaccines Note", medInfo.getVaccinenote());
           // cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
            cellIN.setPaddingTop(10);
            table.addCell(cellIN);

            cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);

            //donor -11
           // HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

          //  if (!medInfo.getDietNote().isEmpty()) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Organ Donor", BlackFont);
                pps.add(underline);

                pf.add(pps);

                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);


                cellIN = new PdfPCell();
                HeaderNew.cellDesignNoline(cellIN, tableIN, "Are you an Organ Donor?", medInfo.getDonor());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cellIN = new PdfPCell();
                HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
            cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);

                cell1.addElement(tableIN);
                table1.addCell(cell1);
         //   }
            cell.addElement(table1);
            table.addCell(cell);

            cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);


            //vaccine -12
           // HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

            if (medInfo != null) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Vision", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);


                cellIN = new PdfPCell();
                HeaderNew.cellDesign(cellIN, tableIN, "Glasses", medInfo.getGlass());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);


                cellIN = new PdfPCell();
                HeaderNew.cellDesign(cellIN, tableIN, "Contact Lenses", medInfo.getLense());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);


                cellIN = new PdfPCell();
                HeaderNew.cellDesignNoline(cellIN, tableIN, "Color Blind", medInfo.getBlind());
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);


                cellIN = new PdfPCell();
                HeaderNew.cellDesign(cellIN, tableIN, "", "Empty");
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);


                cell1.addElement(tableIN);
                table1.addCell(cell1);
            }
            cell.addElement(table1);
            table.addCell(cell);

            cellIN = new PdfPCell();
            HeaderNew.cellDesignbroadline(cellIN, table, "Vision Note", medInfo.getVisionNote());
           // cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
            cellIN.setPaddingTop(10);
            table.addCell(cellIN);

            cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);

            //Smoking -13
            //HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

            if (medInfo != null) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Smoking/Tobacco Use", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);


                cellIN = new PdfPCell();
                if (!medInfo.getTobaco().equalsIgnoreCase("never")) {
                    HeaderNew.cellDesign(cellIN, tableIN, "Smoking/Tobacco Use", medInfo.getTobaco());
                } else {
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "Smoking/Tobacco Use", medInfo.getTobaco());
                }
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);


                if (!medInfo.getTobaco().equalsIgnoreCase("never")) {
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Type", medInfo.getT_type());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);


                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "Amount/Frequency", medInfo.getT_amt());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);


                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "Number of Years & When Stopped (if applicable)", medInfo.getT_year());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                } else {
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "", "Empty");
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);
                }
                cell1.addElement(tableIN);
                table1.addCell(cell1);
            }
            cell.addElement(table1);
            table.addCell(cell);

            /*cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);*/

            //Drink -14
           // HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

            if (medInfo != null) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Alcohol", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);


                cellIN = new PdfPCell();
                if (!medInfo.getDrink().equalsIgnoreCase("never")) {
                    HeaderNew.cellDesign(cellIN, tableIN, "Alcohol", medInfo.getDrink());
                } else {
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "Alcohol", medInfo.getDrink());
                }
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);


                if (!medInfo.getDrink().equalsIgnoreCase("never")) {
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Amount/Frequency", medInfo.getDrink_amt());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);


                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "Number of Years & When Stopped (if applicable)", medInfo.getDrink_year());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "", "Empty");
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                } else {
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "", "Empty");
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);
                }
                cell1.addElement(tableIN);
                table1.addCell(cell1);
            }
            cell.addElement(table1);
            table.addCell(cell);

           /* cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);*/

            //Drug -15
          //  HeaderNew.addEmptyLine(1);
            cell = new PdfPCell();
            cell.setPaddingTop(5);
            cell.setPaddingBottom(5);
            cell.setPaddingLeft(10);
            cell.setPaddingRight(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(WebColors.getRGBColor("#Ffffff"));
            table.setKeepTogether(false);
            table.setSplitLate(false);
            table1 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setKeepTogether(false);
            table1.setSplitLate(false);
            table1.setWidthPercentage(100);

            if (medInfo != null) {

                BlackFont.setColor(WebColors.getRGBColor("#24AAE0"));//255, 99, 26);
                BlackFont.setSize(11.5f);
                BlackFont.setStyle(Font.BOLD);

                 pf = new Paragraph();
                 pps = new Phrase();
                 underlined = new Chunk("  ", BlackFont);
                pps.add(underlined);
                pf.add(pps);

                pps = new Phrase();
                 underline = new Chunk("Recreational Drug Use", BlackFont);
                pps.add(underline);

                pf.add(pps);
                pf.setAlignment(Element.ALIGN_LEFT);

                cell1 = new PdfPCell();
                cell1.addElement(pf);

                cell1.setPaddingTop(10);
                cell1.setBackgroundColor(WebColors.getRGBColor("#ffffff"));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setColspan(2);
                cell1.addElement(new Paragraph(" "));


                tableIN = new PdfPTable(2);

                tableIN.setWidthPercentage(98);
                tableIN.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                tableIN.setTableEvent(new RoundedBorder());
                tableIN.getDefaultCell().setPadding(2);
                tableIN.setKeepTogether(false);
                tableIN.setSplitLate(false);


                cellIN = new PdfPCell();
                if (!medInfo.getDrug().equalsIgnoreCase("never")) {
                    HeaderNew.cellDesign(cellIN, tableIN, "Recreational Drug Use", medInfo.getDrug());
                } else {
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "Recreational Drug Use", medInfo.getDrug());
                }
                cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                cellIN.setPaddingTop(14);
                tableIN.addCell(cellIN);


                if (!medInfo.getDrug().equalsIgnoreCase("never")) {
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesign(cellIN, tableIN, "Type", medInfo.getDrug_type());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);


                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "Amount/Frequency", medInfo.getDrug_amt());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);


                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "Number of Years & When Stopped (if applicable)", medInfo.getDrug_year());
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);

                } else {
                    cellIN = new PdfPCell();
                    HeaderNew.cellDesignNoline(cellIN, tableIN, "", "Empty");
                    cellIN.setBackgroundColor(WebColors.getRGBColor("#FBFBFB"));
                    cellIN.setPaddingTop(14);
                    tableIN.addCell(cellIN);
                }
                cell1.addElement(tableIN);
                table1.addCell(cell1);
            }
            cell.addElement(table1);
            table.addCell(cell);

           /* cellIN = new PdfPCell();
            HeaderNew.cellDesign(cellIN, table, "", "Empty");
            table.addCell(cellIN);
*/
            // last full table
            HeaderNew.document.add(table);
            HeaderNew.addEmptyLine(1);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    public IndividualNew(ArrayList<Living> livingList, int i) {

        try {
            HeaderNew.addEmptyLine(1);
            HeaderNew.addChank("Activities of Daily Living");
            messageLiving.add("Activities of Daily Living");
            HeaderNew.addEmptyLine(1);

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

                HeaderNew.document.add(table1);
                Paragraph p = new Paragraph(" ");
                DottedLineSeparator line = new DottedLineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.BLACK);
                p.add(line);
                HeaderNew.document.add(p);
                HeaderNew.addEmptyLine(1);

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


                HeaderNew.document.add(table2);
                Paragraph p1 = new Paragraph(" ");
                DottedLineSeparator line1 = new DottedLineSeparator();
                line1.setOffset(-4);
                line1.setLineColor(BaseColor.BLACK);
                p1.add(line1);
                HeaderNew.document.add(p1);
                HeaderNew.addEmptyLine(1);

            }
            HeaderNew.document.add(table);
            HeaderNew.addEmptyLine(1);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public IndividualNew(String emergency1, Emergency e, ArrayList<ContactData> phonelist,
                         int i, Image ppys) {
        // Font
        IndividualNewFont();
        try {
            // HeaderNew.addEmptyLine(1);
            if (i == 0) {
                HeaderNew.addNewChank("Emergency Contacts & Health Care Proxy Agents", ppys);
                messageEmergency.add("Emergency Contacts & Health Care Proxy Agents");
                HeaderNew.addEmptyLine(1);
            }


            PdfPTable table1;
            table1 = new PdfPTable(1);
            table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table1.setTableEvent(new RoundedBorder());
          //  table1.getDefaultCell().setPaddingBottom(15);
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

            String name = "";
            if (e.getName() != null) {
                name = e.getName();
            }
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Name:", name);
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
            // cell = new PdfPCell(new Phrase("Relationship:" + reationType));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Relationship:", reationType);
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
            } else if (e.getIsPrimary() == 4) {
                priority = "Primary Emergency Contact and Health Care Proxy Agent ";
            }


            //  cell = new PdfPCell(new Phrase("Priority:" + priority));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Priority:", priority);
            table.addCell(cell);

            messageEmergency.add("Priority :");
            messageEmergency.add(priority);


            String email = "";
            if (e.getEmail() != null) {
                email = e.getEmail();
            }
            //cell = new PdfPCell(new Phrase("Email:" + email));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Email:", email);
            table.addCell(cell);

            messageEmergency.add("Email :");
            messageEmergency.add(email);

            if (e.getAddress() != null) {
                address = e.getAddress();
            }
            //  cell = new PdfPCell(new Phrase("Home Address:" + address));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Home Address:", address);
            table.addCell(cell);

            messageEmergency.add("Home Address :");
            messageEmergency.add(address);

            String note = "";
            if (e.getNote() != null) {
                note = e.getNote();
            }
            // cell = new PdfPCell(new Phrase("Notes:" + note));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Notes:", note);
            table.addCell(cell);

            messageEmergency.add("Notes :");
            messageEmergency.add(note);


            String ascard = "";
            if (e.getHas_card() != null) {
                ascard = e.getHas_card();
            }
            // cell = new PdfPCell(new Phrase("Do you have a business card:"+ascard));
            if (phonelist.size() != 0) {

                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table1, "Do you have a business card:", ascard);
                table.addCell(cell);

                messageEmergency.add("Do you have a business card:");
                messageEmergency.add(ascard);

                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);

                cell = new PdfPCell();
                HeaderNew.addDottedLine(cell);
                table.addCell(cell);
            }
            else{
                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table1, "Do you have a business card:", ascard);
                table.addCell(cell);

                messageEmergency.add("Do you have a business card:");
                messageEmergency.add(ascard);

                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);
            }


            for (int t = 0; t < phonelist.size(); t++) {
                ContactData c = phonelist.get(t);
                String num = "";
                String type = "";
                if (c.getValue() != null) {
                    num = c.getValue();
                }

                if (c.getContactType() != null) {
                    type = c.getContactType();
                }
                int j = t + 1;
                if (phonelist.size() % 2 != 0) {
                    if (j==phonelist.size())
                    {
                        cell = new PdfPCell();
                        HeaderNew.cellDesignNoline(cell, table, "Contact" + j + ":", type + " : " + num);
                        table.addCell(cell);
                    }
                    else{
                        cell = new PdfPCell();
                        HeaderNew.cellDesign(cell, table, "Contact" + j + ":", type + " : " + num);
                        table.addCell(cell);
                    }
                }
                else {
                    if (j==phonelist.size()||j==phonelist.size()-1) {
                        cell = new PdfPCell();
                        HeaderNew.cellDesignNoline(cell, table, "Contact" + j + ":", type + " : " + num);
                        table.addCell(cell);
                    }else{
                        cell = new PdfPCell();
                        HeaderNew.cellDesign(cell, table, "Contact" + j + ":", type + " : " + num);
                        table.addCell(cell);
                    }

                }
                messageEmergency.add(type + " Phone:");
                messageEmergency.add(num);
            }
            if (phonelist.size() % 2 != 0) {
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);

            }

            cell1.addElement(table);
            table1.addCell(cell1);
            HeaderNew.document.add(table1);
            HeaderNew.addEmptyLine(1);

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public IndividualNew(String physician, Specialist s, ArrayList<ContactData> phonelists,
                         int i, Image ppys) {
        IndividualNewFont();
        try {
            // HeaderNew.addEmptyLine(1);
            if (i == 0) {
                HeaderNew.addNewChank("Primary Physician", ppys);
                messagePhysician.add("Primary Physician");
                HeaderNew.addEmptyLine(1);
            }


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


            String speciality = "";
            if (s.getType() != null) {
                speciality = s.getType();
                if (speciality.equals("Other")) {
                    speciality = speciality + " - " + s.getOtherType();
                }
            }
            // cell = new PdfPCell(new Phrase("Speciality:" + speciality));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Specialty:", speciality);
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
            //  cell = new PdfPCell(new Phrase("Name:" + name));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Name of Doctor/Health Professionals:", name);
            table.addCell(cell);

            messagePhysician.add("Name of Doctor/Health Professionals:");
            messagePhysician.add(name);


            String address = "";
            if (s.getAddress() != null) {
                address = s.getAddress();
            }
            // cell = new PdfPCell(new Phrase("Address:" + address));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Address:", address);
            table.addCell(cell);
            messagePhysician.add("Address :");
            messagePhysician.add(address);

            String website = "";
            if (s.getWebsite() != null) {
                website = s.getWebsite();
            }
            // cell = new PdfPCell(new Phrase("Website:" + website));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Website:", website);
            table.addCell(cell);
            messagePhysician.add("Website :");
            messagePhysician.add(website);

            String medicalPracticeName = "";
            if (s.getPracticeName() != null) {
                medicalPracticeName = s.getPracticeName();
            }
            // cell = new PdfPCell(new Phrase("Medical Practice Name:" + medicalPracticeName));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Medical Practice Name:", medicalPracticeName);
            table.addCell(cell);
            messagePhysician.add("Medical Practice Name :");
            messagePhysician.add(medicalPracticeName);

            String hospitalAffiliations = "";
            if (s.getHospAffiliation() != null) {
                hospitalAffiliations = s.getHospAffiliation();
            }
            // cell = new PdfPCell(new Phrase("Hospital Affiliations:" + hospitalAffiliations));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Hospital Affiliations:", hospitalAffiliations);
            table.addCell(cell);
            messagePhysician.add("Hospital Affiliations :");
            messagePhysician.add(hospitalAffiliations);

            String networkStatus = "";
            if (s.getNetwork() != null) {
                networkStatus = s.getNetwork();
            }
            //   cell = new PdfPCell(new Phrase("In Network Status:" + networkStatus));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "In Network Status:", networkStatus);
            table.addCell(cell);
            messagePhysician.add("In Network Status :");
            messagePhysician.add(networkStatus);

            String lastSeen = "";
            if (s.getLastseen() != null) {
                lastSeen = s.getLastseen();
            }
            // cell = new PdfPCell(new Phrase("Last Seen:" + lastSeen));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Last Seen:", lastSeen);
            table.addCell(cell);
            messagePhysician.add("Last Seen :");
            messagePhysician.add(lastSeen);

            String locator = "";
            if (s.getLocator() != null) {
                locator = s.getLocator();
            }
            // cell = new PdfPCell(new Phrase("Electronic Health Record (add web address, username and password):" + locator));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Electronic Health Record (add web address, username and password):", locator);
            table.addCell(cell);
            messagePhysician.add("Electronic Health Record (add web address, username and password) :");
            messagePhysician.add(locator);

            String note = "";
            if (s.getNote() != null) {
                note = s.getNote();
            }
            //  cell = new PdfPCell(new Phrase("Notes:" + note));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Notes:", note);
            table.addCell(cell);
            messagePhysician.add("Notes :");
            messagePhysician.add(note);

            String ascard = "";
            if (s.getHas_card() != null) {
                ascard = s.getHas_card();
            }
            // cell = new PdfPCell(new Phrase("Do you have a business card:"+ascard));


            if (phonelists.size() != 0) {

                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table1, "Do you have a business card:", ascard);
                table.addCell(cell);

                messagePhysician.add("Do you have a business card:");
                messagePhysician.add(ascard);

                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);

                cell = new PdfPCell();
                HeaderNew.addDottedLine(cell);
                table.addCell(cell);
            }
            else{
                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table1, "Do you have a business card:", ascard);
                table.addCell(cell);

                messagePhysician.add("Do you have a business card:");
                messagePhysician.add(ascard);

                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);
            }

            for (int t = 0; t < phonelists.size(); t++) {
                ContactData c = phonelists.get(t);
                String num = "";
                String type = "";
                if (c.getValue() != null) {
                    num = c.getValue();
                }

                if (c.getContactType() != null) {
                    type = c.getContactType();
                }
                int j = t + 1;
                if (phonelists.size() % 2 != 0) {
                    if (j==phonelists.size())
                    {
                        cell = new PdfPCell();
                        HeaderNew.cellDesignNoline(cell, table, "Contact" + j + ":", type + " : " + num);
                        table.addCell(cell);
                    }
                    else{
                        cell = new PdfPCell();
                        HeaderNew.cellDesign(cell, table, "Contact" + j + ":", type + " : " + num);
                        table.addCell(cell);
                    }
                }
                else {
                    if (j==phonelists.size()||j==phonelists.size()-1) {
                        cell = new PdfPCell();
                        HeaderNew.cellDesignNoline(cell, table, "Contact" + j + ":", type + " : " + num);
                        table.addCell(cell);
                    }else{
                        cell = new PdfPCell();
                        HeaderNew.cellDesign(cell, table, "Contact" + j + ":", type + " : " + num);
                        table.addCell(cell);
                    }

                }

                messagePhysician.add(type + " Phone:");
                messagePhysician.add(num);
            }

            if (phonelists.size() % 2 != 0) {
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);

            }


            cell1.addElement(table);
            table1.addCell(cell1);
            HeaderNew.document.add(table1);
            HeaderNew.addEmptyLine(1);

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}

