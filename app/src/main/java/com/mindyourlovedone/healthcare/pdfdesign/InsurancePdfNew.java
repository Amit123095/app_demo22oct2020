package com.mindyourlovedone.healthcare.pdfdesign;

import android.app.Activity;
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
import com.mindyourlovedone.healthcare.model.Card;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Form;
import com.mindyourlovedone.healthcare.model.Insurance;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by welcome on 11/7/2017.
 */

/**
 * Class: InsurancePdfNew
 * Screen: Personal Insurance Sections
 * A class that manages pdf creation of Insurance Section
 */
public class InsurancePdfNew {
    public static ArrayList<String> messageInsurance = new ArrayList<String>();
    public static ArrayList<String> messageCard = new ArrayList<String>();
    public static ArrayList<String> messageForm = new ArrayList<String>();

    Font BlackFont;
//Set Font
    private void InsuranceNewFont() {
        try {
            BaseFont base = BaseFont.createFont("assets/Lato-Regular.ttf", "UTF-8",BaseFont.EMBEDDED);
            BlackFont = new Font(base, 19, Font.NORMAL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public InsurancePdfNew(ArrayList<Insurance> insuranceList) {
        try {
            PdfPTable table1;
            table1 = new PdfPTable(2);
            PdfPCell cell1;
            table1.setWidthPercentage(100);

         //   Header.addEmptyLine(1);
            Header.addChank("Insurance Companies");
            messageInsurance.add("Insurance Companies");
            Header.addEmptyLine(1);


            for (int i = 0; i < insuranceList.size(); i++) {
                PdfPTable table;
                table = new PdfPTable(2);
                PdfPCell cell;
                table.setWidthPercentage(100);

               /* int k = i + 1;
                cell = new PdfPCell(new Phrase("Insurance Information " + k + " :"));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Insurance Information " + k + " :");
                messageInsurance.add("");
*/
                Insurance s = insuranceList.get(i);

                String name = "";
                if (s.getName() != null) {
                    name = s.getName();
                }
                cell = new PdfPCell(new Phrase("Name of Insurance Company:" + name));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Name of Insurance Company:");
                messageInsurance.add(name);

                String type = "";
                if (s.getType() != null) {
                    type = s.getType();
                    if (type.equals("Other")) {
                        type = type + " - " + s.getOtherInsurance();
                    }
                }
                cell = new PdfPCell(new Phrase("Type of Insurance:" + type));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Type of Insurance :");
                messageInsurance.add(type);

                String memberId = "";
                if (s.getMember() != null) {
                    memberId = s.getMember();
                }
                cell = new PdfPCell(new Phrase("Member ID # (Policy Number):" + memberId));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Member ID # (Policy Number) :");
                messageInsurance.add(memberId);

                String group = "";
                if ("" + s.getGroup() != null) {
                    group = "" + s.getGroup();
                }
                cell = new PdfPCell(new Phrase("Group:" + group));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Group :");
                messageInsurance.add(group);

                String nameofInsured = "";
                if ("" + s.getSubscriber() != null) {
                    nameofInsured = "" + s.getSubscriber();
                }
                cell = new PdfPCell(new Phrase("Name of Insurance (Primary Subsriber):" + nameofInsured));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Name of Insurance (Primary Subsriber) :");
                messageInsurance.add(nameofInsured);

                String providerPhone = "";
                if (s.getPhone() != null) {
                    providerPhone = s.getPhone();
                }
                cell = new PdfPCell(new Phrase("Provider Phone:" + providerPhone));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Provider Phone :");
                messageInsurance.add(providerPhone);

                String providerFax = "";
                if (s.getFax() != null) {
                    providerFax = s.getFax();
                }
                cell = new PdfPCell(new Phrase("Provider Fax:" + providerFax));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Provider Fax :");
                messageInsurance.add(providerFax);

                String providerEmail = "";
                if (s.getEmail() != null) {
                    providerEmail = s.getEmail();
                }
                cell = new PdfPCell(new Phrase("Provider Email:" + providerEmail));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Provider Email :");
                messageInsurance.add(providerEmail);

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
                messageInsurance.add("Website :");
                messageInsurance.add(website);

                String agentname = "";
                if (s.getAgent() != null) {
                    agentname = s.getAgent();
                }
                cell = new PdfPCell(new Phrase("Agent Name, Phone, Email:" + agentname));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Agent Name, Phone, Email :");
                messageInsurance.add(agentname);

                String notes = "";
                if (s.getNote() != null) {
                    notes = s.getNote();
                }
                cell = new PdfPCell(new Phrase("Notes:" + notes));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("Notes :");
                messageInsurance.add(notes);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setUseBorderPadding(true);
                cell.setBorderWidthBottom(5);
                cell.setBorderColorBottom(BaseColor.WHITE);
                table.addCell(cell);
                messageInsurance.add("");
                messageInsurance.add(notes);

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

    /**
     * Function: Create Insurance Card list PDF
     * @param cardList
     * @param f
     * @param pp
     */
    public InsurancePdfNew(ArrayList<Card> cardList, int f, Image pp) {
        InsuranceNewFont();
        try {
            HeaderNew.addNewChank("Insurance Cards", pp);
            messageCard.add("Insurance Cards");
            HeaderNew.addEmptyLine(1);


            for (int i = 0; i < cardList.size(); i++) {
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

                Card s = cardList.get(i);

                String name = "";
                if (s.getName() != null) {
                    name = s.getName();
                }
              //  cell = new PdfPCell(new Phrase("Provider Name:" + name));
                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table, "Provider Name:", name);
                table.addCell(cell);
                messageCard.add("Provider Name:");
                messageCard.add(name);

                String type = "";
                if (s.getType() != null) {
                    type = s.getType();
                }
               // cell = new PdfPCell(new Phrase("Type: " + type));
                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table, "Type:", type);
                table.addCell(cell);
                messageCard.add("Type :");
                messageCard.add(type);

                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);

                messageCard.add("");
                messageCard.add("");


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
     * Function: Create Inurance Form PDF
     * @param formList
     * @param form
     * @param pp
     */
    public InsurancePdfNew(ArrayList<Form> formList, String form, Image pp) {
        InsuranceNewFont();
        try {
            HeaderNew.addNewChank("Insurance Form", pp);
            messageForm.add("Insurance Form");
            HeaderNew.addEmptyLine(1);


            for (int i = 0; i < formList.size(); i++) {
                PdfPTable table1;
                table1 = new PdfPTable(1);
                table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                table1.setTableEvent(new RoundedBorder());
                //table1.getDefaultCell().setPaddingBottom(15);
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
                Form s = formList.get(i);

                String name = "";
                if (s.getName() != null) {
                    name = s.getName();
                }
             //   cell = new PdfPCell(new Phrase("Form Name:" + name));
                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table, "Form Name:", name);
                table.addCell(cell);
                messageForm.add("Form Name:");
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
               // cell = new PdfPCell(new Phrase("Date::" + date));
                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table, "Date:", date);
                table.addCell(cell);
                messageForm.add("Date::");
                messageForm.add(date);
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);

                messageForm.add("");
                messageForm.add("");


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
     * Function: Create Insurance Companies PDF
     * @param s
     * @param insurance1
     * @param phonelists
     * @param i
     * @param aphonelists
     * @param pp
     * @param context
     */
    public InsurancePdfNew(Insurance s, String insurance1, ArrayList<ContactData> phonelists, int i, ArrayList<ContactData> aphonelists, Image pp, Context context) {
        Preferences preferences=new Preferences(context);

        InsuranceNewFont();
        try {
            // HeaderNew.addEmptyLine(1);
            if (i==0) {
                HeaderNew.addNewChank("Insurance Companies",pp);
                messageInsurance.add("Insurance Companies");
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
                if (s.getName() != null) {
                    name = s.getName();
                }
              //  cell = new PdfPCell(new Phrase("Name of Insurance Company:" + name));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell,table1,"Name of Insurance Company:",name+i);
            table.addCell(cell);
                messageInsurance.add("Name of Insurance Company:");
                messageInsurance.add(name);

                String type = "";
                if (s.getType() != null) {
                    type = s.getType();
                    if (type.equals("Other")) {
                        type = type + " - " + s.getOtherInsurance();
                    }
                }
               // cell = new PdfPCell(new Phrase("Type of Insurance:" + type));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell,table1,"Type of Insurance:",type);
            table.addCell(cell);
                messageInsurance.add("Type of Insurance :");
                messageInsurance.add(type);


            String nameofInsured = "";
            if ("" + s.getSubscriber() != null) {
                nameofInsured = "" + s.getSubscriber();
            }
           // cell = new PdfPCell(new Phrase("Name of Insured:" + nameofInsured));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell,table1,"Name of Insured:",nameofInsured);
            table.addCell(cell);
            messageInsurance.add("Name of Insured :");
            messageInsurance.add(nameofInsured);

                String memberId = "";
                if (s.getMember() != null) {
                    memberId = s.getMember();
                }
            if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(context.getResources().getString(R.string.India)))
            {
///    cell = new PdfPCell(new Phrase("Member Id (Policy Number):" + memberId));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell,table1,"Policy Number:",memberId);
                table.addCell(cell);
                messageInsurance.add("Policy Number:");
                messageInsurance.add(memberId);

            }else
            {
                //    cell = new PdfPCell(new Phrase("Member Id (Policy Number):" + memberId));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell,table1,"Member Id (Policy Number):",memberId);
                table.addCell(cell);
                messageInsurance.add("Member Id (Policy Number) :");
                messageInsurance.add(memberId);

            }


                String group = "";
                if ("" + s.getGroup() != null) {
                    group = "" + s.getGroup();
                }
            if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(context.getResources().getString(R.string.India)))
            {

            }else{
                // cell = new PdfPCell(new Phrase("Group #:" + group));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell,table1,"Group #:",group);
                table.addCell(cell);
                messageInsurance.add("Group # :");
                messageInsurance.add(group);

            }


                String providerEmail = "";
                if (s.getEmail() != null) {
                    providerEmail = s.getEmail();
                }
                //cell = new PdfPCell(new Phrase("Provider Email:" + providerEmail));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell,table1,"Provider Email:",providerEmail);
            table.addCell(cell);
                messageInsurance.add("Provider Email :");
                messageInsurance.add(providerEmail);

                String website = "";
                if (s.getWebsite() != null) {
                    website = s.getWebsite();
                }
               // cell = new PdfPCell(new Phrase("Website:" + website));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell,table1,"Website:",website);
            table.addCell(cell);
                messageInsurance.add("Website :");
                messageInsurance.add(website);

                String agentname = "";
                if (s.getAgent() != null) {
                    agentname = s.getAgent();
                }
              //  cell = new PdfPCell(new Phrase("Agent Name:" + agentname));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell,table1,"Agent Name:",agentname);
            table.addCell(cell);
                messageInsurance.add("Agent Name:");
                messageInsurance.add(agentname);
/*
            for (int t=0;t<aphonelists.size();t++)
            {
                ContactData c=aphonelists.get(t);
                String num="";
                String ctype="";
                if (c.getValue() != null) {
                    num =c.getValue();
                }

                if (c.getContactType() != null) {
                    ctype =c.getContactType();
                }
                //cell = new PdfPCell(new Phrase("Agent "+ctype+" Phone:" + num));
                cell = new PdfPCell();
                HeaderNew.cellDesign(cell,table1,"Agent "+ctype+" Phone:",num);
                table.addCell(cell);

                messageInsurance.add(ctype+"Agent "+ctype+" Phone:");
                messageInsurance.add(num);
            }*/
            String num="";
            String ctype="";
            if (s.getAgentPhone() != null) {
                num =s.getAgentPhone();

            }
           // cell = new PdfPCell(new Phrase("Agent Phone:" + num));
           /* cell = new PdfPCell();
            HeaderNew.cellDesign(cell,table1,"Agent Phone:",num);
            table.addCell(cell);

            messageInsurance.add(ctype+"Agent Phone:");
            messageInsurance.add(num);
*/
            String agentemail = "";
            if (s.getAgent_email()!= null) {
                agentemail = s.getAgent_email();
            }
           // cell = new PdfPCell(new Phrase("Agent Email:" + agentemail));
            cell = new PdfPCell();
            HeaderNew.cellDesign(cell,table1,"Agent Email:",agentemail);
            table.addCell(cell);
            messageInsurance.add("Agent Email :");
            messageInsurance.add(agentemail);


                String notes = "";
                if (s.getNote() != null) {
                    notes = s.getNote();
                }

            cell = new PdfPCell();
            HeaderNew.cellDesign(cell, table1, "Notes:", notes);
            table.addCell(cell);

            messageInsurance.add("Notes");
            messageInsurance.add(notes);
               // cell = new PdfPCell(new Phrase("Notes:" + notes));
            String ascard = "";
            if (s.getHas_card() != null) {
                ascard = s.getHas_card();
            }
            if (phonelists.size() != 0) {

                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table1, "Do you have a business card?:", ascard);
                table.addCell(cell);

                messageInsurance.add("Do you have a business card?");
                messageInsurance.add(ascard);

                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table, "", "Empty");
                table.addCell(cell);

                cell = new PdfPCell();
                HeaderNew.addDottedLine(cell);
                table.addCell(cell);
            }
            else{
                cell = new PdfPCell();
                HeaderNew.cellDesignNoline(cell, table1, "Do you have a business card?", ascard);
                table.addCell(cell);

                messageInsurance.add("Do you have a business card?");
                messageInsurance.add(ascard);

                cell = new PdfPCell();
                HeaderNew.cellDesign(cell, table, "", "Empty");
                table.addCell(cell);
            }



            for (int t=0;t<phonelists.size();t++)
                {
                    ContactData c=phonelists.get(t);
                    String nums="";
                    String ctypes="";
                    if (c.getValue() != null) {
                        nums =c.getValue();
                    }

                    if (c.getContactType() != null) {
                        ctypes =c.getContactType();
                    }
                    int j= t+1;
                    if (phonelists.size() % 2 != 0) {
                        if (j==phonelists.size())
                        {
                            cell = new PdfPCell();
                            HeaderNew.cellDesignNoline(cell, table, "Agent Contact" + j + ":", ctypes + " : " + nums);
                            table.addCell(cell);
                        }
                        else{
                            cell = new PdfPCell();
                            HeaderNew.cellDesign(cell, table, "Agent Contact" + j + ":", ctypes + " : " + nums);
                            table.addCell(cell);
                        }
                    }
                    else {
                        if (j==phonelists.size()||j==phonelists.size()-1) {
                            cell = new PdfPCell();
                            HeaderNew.cellDesignNoline(cell, table, "Agent Contact" + j + ":", ctypes + " : " + nums);
                            table.addCell(cell);
                        }else{
                            cell = new PdfPCell();
                            HeaderNew.cellDesign(cell, table, "Agent Contact" + j + ":", ctypes + " : " + nums);
                            table.addCell(cell);
                        }

                    }

                    messageInsurance.add(ctypes+" Phone:");
                    messageInsurance.add(nums);
                }


            if (phonelists.size()%2!=0)
            {
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
