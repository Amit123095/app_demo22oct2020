package com.mindyourlovedone.healthcare.pdfCreation;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.mindyourlovedone.healthcare.model.Allergy;
import com.mindyourlovedone.healthcare.model.Emergency;
import com.mindyourlovedone.healthcare.model.Living;
import com.mindyourlovedone.healthcare.model.MedInfo;
import com.mindyourlovedone.healthcare.model.PersonalInfo;
import com.mindyourlovedone.healthcare.model.Pet;
import com.mindyourlovedone.healthcare.model.Proxy;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.model.Specialist;
import com.mindyourlovedone.healthcare.utility.Header;

import java.util.ArrayList;

/**
 * Created by welcome on 11/1/2017.
 */

public class Individual {
    public static ArrayList<String> messageInfo = new ArrayList<String>();
    public static ArrayList<String> messageInfo2 = new ArrayList<String>();
    public static ArrayList<String> messageInfo3 = new ArrayList<String>();
    public static ArrayList<String> messageEmergency = new ArrayList<String>();
    public static ArrayList<String> messagePhysician = new ArrayList<String>();
    public static ArrayList<String> messageProxy = new ArrayList<String>();
    public static ArrayList<String> messageLiving = new ArrayList<>();
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


    public Individual(RelativeConnection connection, ArrayList<Pet> Petlist) {
        Header.addChank("Personal Profile");
        messageInfo.add("Personal Profile");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        if (connection.getName() != null) {
            name = connection.getName();
        }
        Header.addTable("Profile Name :");
        Header.addTable(name);
        messageInfo.add("Profile Name :");
        messageInfo.add(name);

        if (connection.getRelationType() != null) {
            realtion = connection.getRelationType();
        }
        Header.addTable("Relationship :");
        Header.addTable(realtion);
        messageInfo.add("Relationship :");
        messageInfo.add(realtion);

        if (connection.getAddress() != null) {
            address = connection.getAddress();
        }
        Header.addTable("Address :");
        Header.addTable(address);
        messageInfo.add("Address :");
        messageInfo.add(address);

        if (connection.getMobile() != null) {
            mPhone = connection.getMobile();
        }
        Header.addTable("Mobile :");
        Header.addTable(mPhone);
        messageInfo.add("Mobile :");
        messageInfo.add(mPhone);
        String bdate = "";

        if (connection.getPhone() != null) {
            hPhone = connection.getPhone();
        }
        Header.addTable("Home Phone :");
        Header.addTable(hPhone);
        messageInfo.add("Home Phone :");
        messageInfo.add(hPhone);

        if (connection.getHeight() != null) {
            height = connection.getHeight();
        }
        Header.addTable("Height :");
        Header.addTable(height);
        messageInfo.add("Height :");
        messageInfo.add(height);

        if (connection.getWeight() != null) {
            weight = connection.getWeight();
        }
        Header.addTable("Weight :");
        Header.addTable(weight);
        messageInfo.add("Weight :");
        messageInfo.add(weight);

        if (connection.getEyes() != null) {
            eyes = connection.getEyes();
        }
        Header.addTable("Eyes :");
        Header.addTable(eyes);
        messageInfo.add("Eyes :");
        messageInfo.add(eyes);

        if (connection.getWeight() != null) {
            employedBy = connection.getWeight();
        }
        Header.addTable("Employed By :");
        Header.addTable(employedBy);
        messageInfo.add("Employed By :");
        messageInfo.add(employedBy);

        if (connection.getManager_phone() != null) {
            telephone = connection.getManager_phone();
        }
        Header.addTable("Manager Phone :");
        Header.addTable(telephone);
        messageInfo.add("Manager Phone :");
        messageInfo.add(telephone);

        if (connection.getLanguage() != null) {
            language = connection.getLanguage();
        }
        Header.addTable("Language Spoken :");
        Header.addTable(language);
        messageInfo.add("Language Spoken :");
        messageInfo.add(language);

        if (connection.getMarital_status() != null) {
            marital_status = connection.getMarital_status();
        }
        Header.addTable("Marital Status :");
        Header.addTable(marital_status);
        messageInfo.add("Marital Status :");
        messageInfo.add(marital_status);

        if (connection.getReligion() != null) {
            religionNote = connection.getReligion();
        }
        Header.addTable("Religion Note :");
        Header.addTable(religionNote);
        messageInfo.add("Religion Note :");
        messageInfo.add(religionNote);

        if (connection.getProfession() != null) {
            profession = connection.getProfession();
        }
        Header.addTable("Profession :");
        Header.addTable(profession);
        messageInfo.add("Profession :");
        messageInfo.add(profession);

        if (connection.getVeteran() != null) {
            Veteran = connection.getVeteran();
        }
        Header.addTable("Veteran :");
        Header.addTable(Veteran);
        messageInfo.add("Veteran :");
        messageInfo.add(Veteran);

        if (connection.getIdnumber() != null) {
            idNumber = connection.getIdnumber();
        }
        Header.addTable("Id Number :");
        Header.addTable(idNumber);
        messageInfo.add("Id Number :");
        messageInfo.add(idNumber);


        if (connection.getPet() != null) {
            Pets = connection.getPet();
        }
        Header.addTable("Pets :");
        Header.addTable(Pets);
        messageInfo.add("Pets :");
        messageInfo.add(Pets);

        String name = "";
        String breed = "";
        String color = "";
        String microchip = "";
        String veterian = "";
        String person = "";
        for (int i = 0; i < Petlist.size(); i++) {
            int k = i + 1;
            Header.addTable("Pets " + k + " :");
            Header.addTable("");
            messageInfo3.add("Pets " + k + " :");
            messageInfo3.add("");

            Pet a = Petlist.get(i);
            if (a.getName() != null) {
                name = a.getName();
            }
            Header.addTable("Name :");
            Header.addTable(name);
            messageInfo3.add("Name :");
            messageInfo3.add(name);

            if (a.getBreed() != null) {
                breed = a.getBreed();
            }
            Header.addTable("Breed :");
            Header.addTable(breed);
            messageInfo3.add("Breed :");
            messageInfo3.add(breed);

            if (a.getColor() != null) {
                color = a.getColor();
            }
            Header.addTable("Color :");
            Header.addTable(color);
            messageInfo3.add("Color :");
            messageInfo3.add(color);

            if (a.getChip() != null) {
                microchip = a.getChip();
            }
            Header.addTable("Microchip number :");
            Header.addTable(microchip);
            messageInfo3.add("Microchip number :");
            messageInfo3.add(microchip);


            if (a.getVeterian() != null) {
                veterian = a.getVeterian();
            }
            Header.addTable("Veterinarian name,address,person :");
            Header.addTable(veterian);
            messageInfo3.add("Veterinarian name,address,person :");
            messageInfo3.add(veterian);


            if (a.getGuard() != null) {
                person = a.getGuard();
            }
            Header.addTable("Person(s) who will care for pet :");
            Header.addTable(person);
            messageInfo3.add("Person(s) who will care for pet :");
            messageInfo3.add(person);

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
        Header.table.setWidthPercentage(100f);

        try {

            Header.document.add(Header.table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Header.addEmptyLine(1);
    }

    public Individual(PersonalInfo connection, ArrayList<Pet> PetList) {
        Header.addChank("Personal Profile");
        messageInfo2.add("Personal Profile");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        if (connection.getName() != null) {
            name = connection.getName();
        }
        Header.addTable("Profile Name :");
        Header.addTable(name);
        messageInfo.add("Profile Name :");
        messageInfo.add(name);

        if (connection.getEmail() != null) {
            realtion = connection.getEmail();
        }
        Header.addTable("Email :");
        Header.addTable(realtion);
        messageInfo.add("Email :");
        messageInfo.add(realtion);

        if (connection.getAddress() != null) {
            address = connection.getAddress();
        }
        Header.addTable("Address :");
        Header.addTable(address);
        messageInfo2.add("Address :");
        messageInfo2.add(address);
        if (connection.getPhone() != null) {
            mPhone = connection.getPhone();
        }
        Header.addTable("Mobile :");
        Header.addTable(mPhone);
        messageInfo2.add("Mobile :");
        messageInfo2.add(mPhone);

        if (connection.getHomePhone() != null) {
            hPhone = connection.getHomePhone();
        }
        Header.addTable("Home Phone :");
        Header.addTable(hPhone);
        messageInfo2.add("Home Phone :");
        messageInfo2.add(hPhone);

        if (connection.getGender() != null) {
            gender = connection.getGender();
        }
        Header.addTable("Gender :");
        Header.addTable(gender);
        messageInfo2.add("Gender :");
        messageInfo2.add(gender);

        if (connection.getHeight() != null) {
            height = connection.getHeight();
        }
        Header.addTable("Height :");
        Header.addTable(height);
        messageInfo2.add("Height :");
        messageInfo2.add(height);

        if (connection.getWeight() != null) {
            weight = connection.getWeight();
        }
        Header.addTable("Weight :");
        Header.addTable(weight);
        messageInfo2.add("Weight :");
        messageInfo2.add(weight);

        if (connection.getEyes() != null) {
            eyes = connection.getEyes();
        }
        Header.addTable("Eyes :");
        Header.addTable(eyes);
        messageInfo2.add("Eyes :");
        messageInfo2.add(eyes);

        if (connection.getWeight() != null) {
            employedBy = connection.getWeight();
        }
        Header.addTable("Employed By :");
        Header.addTable(employedBy);
        messageInfo2.add("Employed By :");
        messageInfo2.add(employedBy);

        if (connection.getManager_phone() != null) {
            telephone = connection.getManager_phone();
        }
        Header.addTable("Manager Phone :");
        Header.addTable(telephone);
        messageInfo2.add("Manager Phone :");
        messageInfo2.add(telephone);

        if (connection.getLanguage() != null) {
            language = connection.getLanguage();
        }
        Header.addTable("Language Spoken :");
        Header.addTable(language);
        messageInfo2.add("Language Spoken :");
        messageInfo2.add(language);

        if (connection.getMarital_status() != null) {
            marital_status = connection.getMarital_status();
        }
        Header.addTable("Marital Status :");
        Header.addTable(marital_status);
        messageInfo2.add("Marital Status :");
        messageInfo2.add(marital_status);

        if (connection.getReligion() != null) {
            religionNote = connection.getReligion();
        }
        Header.addTable("Religion Note :");
        Header.addTable(religionNote);
        messageInfo2.add("Religion Note :");
        messageInfo2.add(religionNote);

        if (connection.getProfession() != null) {
            profession = connection.getProfession();
        }
        Header.addTable("Profession :");
        Header.addTable(profession);
        messageInfo2.add("Profession :");
        messageInfo2.add(profession);

        if (connection.getVeteran() != null) {
            Veteran = connection.getVeteran();
        }
        Header.addTable("Veteran :");
        Header.addTable(Veteran);
        messageInfo2.add("Veteran :");
        messageInfo2.add(Veteran);

        if (connection.getIdnumber() != null) {
            idNumber = connection.getIdnumber();
        }
        Header.addTable("Id Number :");
        Header.addTable(idNumber);
        messageInfo2.add("Id Number :");
        messageInfo2.add(idNumber);

        if (connection.getPet() != null) {
            Pets = connection.getPet();
        }
        Header.addTable("Pets :");
        Header.addTable(Pets);
        messageInfo2.add("Pets :");
        messageInfo2.add(Pets);

        String name = "";
        String breed = "";
        String color = "";
        String microchip = "";
        String veterian = "";
        String person = "";
        for (int i = 0; i < PetList.size(); i++) {
            int k = i + 1;
            Header.addTable("Pets " + k + " :");
            Header.addTable("");
            messageInfo3.add("Pets " + k + " :");
            messageInfo3.add("");

            Pet a = PetList.get(i);
            if (a.getName() != null) {
                name = a.getName();
            }
            Header.addTable("Name :");
            Header.addTable(name);
            messageInfo3.add("Name :");
            messageInfo3.add(name);

            if (a.getBreed() != null) {
                breed = a.getBreed();
            }
            Header.addTable("Breed :");
            Header.addTable(breed);
            messageInfo3.add("Breed :");
            messageInfo3.add(breed);

            if (a.getColor() != null) {
                color = a.getColor();
            }
            Header.addTable("Color :");
            Header.addTable(color);
            messageInfo3.add("Color :");
            messageInfo3.add(color);

            if (a.getChip() != null) {
                microchip = a.getChip();
            }
            Header.addTable("Microchip number :");
            Header.addTable(microchip);
            messageInfo3.add("Microchip number :");
            messageInfo3.add(microchip);


            if (a.getVeterian() != null) {
                veterian = a.getVeterian();
            }
            Header.addTable("Veterinarian name,address,person :");
            Header.addTable(veterian);
            messageInfo3.add("Veterinarian name,address,person :");
            messageInfo3.add(veterian);


            if (a.getGuard() != null) {
                person = a.getGuard();
            }
            Header.addTable("Person(s) who will care for pet :");
            Header.addTable(person);
            messageInfo3.add("Person(s) who will care for pet :");
            messageInfo3.add(person);

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
        Header.table.setWidthPercentage(100f);

        try {

            Header.document.add(Header.table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Header.addEmptyLine(1);
    }

    public Individual(MedInfo medInfo, ArrayList<Allergy> allargyLists, ArrayList<String> implantsList, ArrayList<String> historList, ArrayList<String> hospitalList) {
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

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        if (medInfo.getNote() != null) {
            preNote = medInfo.getNote();
        }
        Header.addTable("Pre Existing Note :");
        Header.addTable(preNote);
        messageInfo3.add("Pre Existing Note :");
        messageInfo3.add(preNote);


        Header.addTable("Vision :");
        Header.addTable("");
        messageInfo3.add("Vision :");
        messageInfo3.add("");

        if (medInfo.getGlass() != null) {
            glass = medInfo.getGlass();
        }
        Header.addTable("Glasses :");
        Header.addTable(glass);
        messageInfo3.add("Glasses :");
        messageInfo3.add(glass);

        if (medInfo.getLense() != null) {
            lense = medInfo.getLense();
        }
        Header.addTable("Contact Lense :");
        Header.addTable(lense);
        messageInfo3.add("Contact Lense :");
        messageInfo3.add(lense);

        if (medInfo.getBlind() != null) {
            blind = medInfo.getBlind();
        }
        Header.addTable("Color Blind :");
        Header.addTable(blind);
        messageInfo3.add("Color Blind :");
        messageInfo3.add(blind);

        if (medInfo.getVisionNote() != null) {
            visionNote = medInfo.getVisionNote();
        }
        Header.addTable("Notes:");
        Header.addTable(visionNote);
        messageInfo3.add("Notes :");
        messageInfo3.add(visionNote);


        Header.addTable("Mouth :");
        Header.addTable("");
        messageInfo3.add("Mouth :");
        messageInfo3.add("");

        if (medInfo.getFalses() != null) {
            upper = medInfo.getFalses();
        }
        Header.addTable("Dentures- Removable Upper :");
        Header.addTable(upper);
        messageInfo3.add("Dentures- Removable Upper :");
        messageInfo3.add(upper);

        if (medInfo.getImplants() != null) {
            lower = medInfo.getImplants();
        }
        Header.addTable("Dentures- Removable Lower :");
        Header.addTable(lower);
        messageInfo3.add("Dentures- Removable Lower :");
        messageInfo3.add(lower);

        String drymouth = "";
        if (medInfo.getMouth() != null) {
            drymouth = medInfo.getMouth();
        }
        Header.addTable("Dry Mouth :");
        Header.addTable(drymouth);
        messageInfo3.add("Dry Mouth :");
        messageInfo3.add(drymouth);

        String mouthnote = "";
        if (medInfo.getMouthnote() != null) {
            mouthnote = medInfo.getMouthnote();
        }
        Header.addTable("Notes :");
        Header.addTable(mouthnote);
        messageInfo3.add("Notes :");
        messageInfo3.add(mouthnote);

        Header.addTable("Hearing & Speech :");
        Header.addTable("");
        messageInfo3.add("Hearing & Speech :");
        messageInfo3.add("");

        String aid = "";
        if (medInfo.getAid() != null) {
            aid = medInfo.getAid();
        }
        Header.addTable("Hearing Aids :");
        Header.addTable(aid);
        messageInfo3.add("Hearing Aids :");
        messageInfo3.add(aid);

        String speech = "";
        if (medInfo.getSpeech() != null) {
            speech = medInfo.getSpeech();
        }
        Header.addTable("Speech Impaired :");
        Header.addTable(speech);
        messageInfo3.add("Speech Impaired :");
        messageInfo3.add(speech);

        String aidNote = "";
        if (medInfo.getAideNote() != null) {
            aidNote = medInfo.getAideNote();
        }
        Header.addTable("Hearing Notes :");
        Header.addTable(aidNote);
        messageInfo3.add("Hearing Notes :");
        messageInfo3.add(aidNote);

        Header.addTable("Functional Status :");
        Header.addTable("");
        messageInfo3.add("Functional Status :");
        messageInfo3.add("");

        String feeding = "";
        if (medInfo.getFeed() != null) {
            feeding = medInfo.getFeed();
        }
        Header.addTable("Feeding :");
        Header.addTable(feeding);
        messageInfo3.add("Feeding :");
        messageInfo3.add(feeding);

        String toileting = "";
        if (medInfo.getToilet() != null) {
            toileting = medInfo.getToilet();
        }
        Header.addTable("Toileting :");
        Header.addTable(toileting);
        messageInfo3.add("Toileting :");
        messageInfo3.add(toileting);

        String selfMedicate = "";
        if (medInfo.getMedicate() != null) {
            selfMedicate = medInfo.getMedicate();
        }
        Header.addTable("Self Medicate :");
        Header.addTable(selfMedicate);
        messageInfo3.add("Self Medicate :");
        messageInfo3.add(selfMedicate);

        String functionNote = "";
        if (medInfo.getFunctionnote() != null) {
            functionNote = medInfo.getFunctionnote();
        }
        Header.addTable("Function Notes :");
        Header.addTable(functionNote);
        messageInfo3.add("Function Notes :");
        messageInfo3.add(functionNote);

        Header.addTable("Diet :");
        Header.addTable("");
        messageInfo3.add("Diet :");
        messageInfo3.add("");

        String dietNote = "";
        if (medInfo.getDietNote() != null) {
            dietNote = medInfo.getDietNote();
        }
        Header.addTable("Diet Notes :");
        Header.addTable(dietNote);
        messageInfo3.add("Diet Notes :");
        messageInfo3.add(dietNote);

        String bloodNote = "";
        if (medInfo.getBloodType() != null) {
            bloodNote = medInfo.getBloodType();
        }
        Header.addTable("Blood Type :");
        Header.addTable(bloodNote);
        messageInfo3.add("Blood Type :");
        messageInfo3.add(bloodNote);


        String organDonor = "";
        if (medInfo.getDonor() != null) {
            organDonor = medInfo.getDonor();
        }
        Header.addTable("Organ Donor :");
        Header.addTable(organDonor);
        messageInfo3.add("Organ Donor :");
        messageInfo3.add(organDonor);


        Header.addTable("Allergies :");
        Header.addTable("");
        messageInfo3.add("Allergies :");
        messageInfo3.add("");

        String allergy = "";
        String treatment = "";
        String reaction = "";
        for (int i = 0; i < allargyLists.size(); i++) {
            int k = i + 1;
            Header.addTable("Allergy " + k + " :");
            Header.addTable("");
            messageInfo3.add("Allergy " + k + " :");
            messageInfo3.add("");

            Allergy a = allargyLists.get(i);
            if (a.getAllergy() != null) {
                allergy = a.getAllergy();
            }
            Header.addTable("Allergy :");
            Header.addTable(allergy);
            messageInfo3.add("Allergy :");
            messageInfo3.add(allergy);

            if (a.getReaction() != null) {
                reaction = a.getReaction();
            }
            Header.addTable("Reaction :");
            Header.addTable(reaction);
            messageInfo3.add("Reaction :");
            messageInfo3.add(reaction);

            if (a.getTreatment() != null) {
                treatment = a.getTreatment();
            }
            Header.addTable("Treatment :");
            Header.addTable(treatment);
            messageInfo3.add("Treatment :");
            messageInfo3.add(treatment);

        }


        // String Implants="";
        Header.addTable("Medical Implants -");
        Header.addTable("");
        messageInfo3.add("Medical Implants -");
        messageInfo3.add("");

        for (int i = 0; i < implantsList.size(); i++) {
            int k = i + 1;
            Header.addTable("Medical Implants " + k + " :");
            Header.addTable("");
            messageInfo3.add("Medical Implants " + k + " :");
            messageInfo3.add("");


            Header.addTable("Implants :");
            Header.addTable(implantsList.get(i));
            messageInfo3.add("Implants :");
            messageInfo3.add(implantsList.get(i));
        }

        Header.addTable("Preferred Hospital -");
        Header.addTable("");
        messageInfo3.add("Preferred Hospital -");
        messageInfo3.add("");
        for (int i = 0; i < hospitalList.size(); i++) {
            int k = i + 1;
            Header.addTable("Preferred Hospital " + k + " :");
            Header.addTable("");
            messageInfo3.add("Preferred Hospital " + k + " :");
            messageInfo3.add("");


            Header.addTable("Hospital :");
            Header.addTable(hospitalList.get(i));
            messageInfo3.add("Hospital :");
            messageInfo3.add(hospitalList.get(i));
        }

        Header.addTable("Medical History -");
        Header.addTable("");
        messageInfo3.add("Medical History -:");
        messageInfo3.add("");
        for (int i = 0; i < historList.size(); i++) {
            int k = i + 1;
            Header.addTable("Medical History " + k + " :");
            Header.addTable("");
            messageInfo3.add("Medical History " + k + " :");
            messageInfo3.add("");

            Header.addTable("History :");
            Header.addTable(historList.get(i));
            messageInfo3.add("History :");
            messageInfo3.add(historList.get(i));
        }


        Header.table.setWidthPercentage(100f);
        try {

            Header.document.add(Header.table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Header.addEmptyLine(1);
    }

    public Individual(String emergency, ArrayList<Emergency> emergencyList) {
        Header.addChank("Emergency Contacts And Health Care Proxy Agent");
        messageEmergency.add("Emergency Contacts And Health Care Proxy Agent");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i = 0; i < emergencyList.size(); i++) {
            int k = i + 1;
            Header.addTable("Emergency Contact " + k + " :");
            Header.addTable("");
            messageEmergency.add("Emergency Contact " + k + " :");
            messageEmergency.add("");

            Emergency e = emergencyList.get(i);

            String name = "";
            if (e.getName() != null) {
                name = e.getName();
            }
            Header.addTable("Name :");
            Header.addTable(name);
            messageEmergency.add("Name :");
            messageEmergency.add(name);

            String reationType = "";
            if (e.getRelationType() != null) {
                reationType = e.getRelationType();
            }
            Header.addTable("Relation Type :");
            Header.addTable(reationType);
            messageEmergency.add("Relation Type :");
            messageEmergency.add(reationType);

            String priority = "";
            if (e.getIsPrimary() == 0) {
                priority = "Primary";
            } else {
                priority = "Secondary";
            }
            Header.addTable("Priority :");
            Header.addTable(priority);
            messageEmergency.add("Priority :");
            messageEmergency.add(priority);

            String officePhone = "";
            if (e.getWorkPhone() != null) {
                officePhone = e.getWorkPhone();
            }
            Header.addTable("Office Phone :");
            Header.addTable(officePhone);
            messageEmergency.add("Office Phone :");
            messageEmergency.add(officePhone);

            if (e.getMobile() != null) {
                mPhone = e.getMobile();
            }
            Header.addTable("Mobile :");
            Header.addTable(mPhone);
            messageEmergency.add("Mobile :");
            messageEmergency.add(mPhone);
            String bdate = "";

            if (e.getPhone() != null) {
                hPhone = e.getPhone();
            }
            Header.addTable("Home Phone :");
            Header.addTable(hPhone);
            messageEmergency.add("Home Phone :");
            messageEmergency.add(hPhone);

            String email = "";
            if (e.getEmail() != null) {
                email = e.getEmail();
            }
            Header.addTable("Email :");
            Header.addTable(email);
            messageEmergency.add("Email :");
            messageEmergency.add(email);

            if (e.getAddress() != null) {
                address = e.getAddress();
            }
            Header.addTable("Address :");
            Header.addTable(address);
            messageEmergency.add("Address :");
            messageEmergency.add(address);

            String note = "";
            if (e.getNote() != null) {
                note = e.getNote();
            }
            Header.addTable("Notes :");
            Header.addTable(note);
            messageEmergency.add("Notes :");
            messageEmergency.add(note);

        }
        Header.table.setWidthPercentage(100f);
        try {

            Header.document.add(Header.table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Header.addEmptyLine(1);
    }

    public Individual(ArrayList<Specialist> specialistsList, String physician) {

        Header.addChank("Primary Physician");
        messagePhysician.add("Primary Physician");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i = 0; i < specialistsList.size(); i++) {
            int k = i + 1;
            Header.addTable("Primary Physician " + k + " :");
            Header.addTable("");
            messagePhysician.add("Primary Physician " + k + " :");
            messagePhysician.add("");

            Specialist s = specialistsList.get(i);

            String speciality = "";
            if ("" + s.getIsPhysician() != null) {
                speciality = "" + s.getIsPhysician();
            }
            Header.addTable("Speciality :");
            Header.addTable(speciality);
            messagePhysician.add("Speciality :");
            messagePhysician.add(speciality);

            String name = "";
            if (s.getName() != null) {
                name = s.getName();
            }
            Header.addTable("Name :");
            Header.addTable(name);
            messagePhysician.add("Name :");
            messagePhysician.add(name);

            String officePhone = "";
            if (s.getOfficePhone() != null) {
                officePhone = s.getOfficePhone();
            }
            Header.addTable("Office Phone :");
            Header.addTable(officePhone);
            messagePhysician.add("Office Phone :");
            messagePhysician.add(officePhone);

            String afterHoursPhone = "";
            if (s.getHourPhone() != null) {
                afterHoursPhone = s.getHourPhone();
            }
            Header.addTable("Office Phone :");
            Header.addTable(afterHoursPhone);
            messagePhysician.add("Office Phone :");
            messagePhysician.add(afterHoursPhone);

            String otherPhone = "";
            if (s.getOtherPhone() != null) {
                otherPhone = s.getOtherPhone();
            }
            Header.addTable("Other Phone :");
            Header.addTable(otherPhone);
            messagePhysician.add("Other Phone :");
            messagePhysician.add(otherPhone);

            String officeFax = "";
            if (s.getFax() != null) {
                officeFax = s.getFax();
            }
            Header.addTable("Office Fax :");
            Header.addTable(officeFax);
            messagePhysician.add("Office Fax :");
            messagePhysician.add(officeFax);


            if (s.getAddress() != null) {
                address = s.getAddress();
            }
            Header.addTable("Address :");
            Header.addTable(address);
            messagePhysician.add("Address :");
            messagePhysician.add(address);

            String website = "";
            if (s.getWebsite() != null) {
                website = s.getWebsite();
            }
            Header.addTable("Website :");
            Header.addTable(website);
            messagePhysician.add("Website :");
            messagePhysician.add(website);

            String medicalPracticeName = "";
            if (s.getPracticeName() != null) {
                medicalPracticeName = s.getPracticeName();
            }
            Header.addTable("Medical Practice Name :");
            Header.addTable(medicalPracticeName);
            messagePhysician.add("Medical Practice Name :");
            messagePhysician.add(medicalPracticeName);

            String hospitalAffiliations = "";
            if (s.getHospAffiliation() != null) {
                hospitalAffiliations = s.getHospAffiliation();
            }
            Header.addTable("Hospital Affiliations :");
            Header.addTable(hospitalAffiliations);
            messagePhysician.add("Hospital Affiliations :");
            messagePhysician.add(hospitalAffiliations);

            String networkStatus = "";
            if (s.getNetwork() != null) {
                networkStatus = s.getNetwork();
            }
            Header.addTable("Network Status :");
            Header.addTable(networkStatus);
            messagePhysician.add("Network Status :");
            messagePhysician.add(networkStatus);

            String lastSeen = "";
            if (s.getLastseen() != null) {
                lastSeen = s.getLastseen();
            }
            Header.addTable("Last Visit :");
            Header.addTable(lastSeen);
            messagePhysician.add("Last Visit :");
            messagePhysician.add(lastSeen);

            String note = "";
            if (s.getNote() != null) {
                note = s.getNote();
            }
            Header.addTable("Notes :");
            Header.addTable(note);
            messagePhysician.add("Notes :");
            messagePhysician.add(note);

        }
        Header.table.setWidthPercentage(100f);
        try {

            Header.document.add(Header.table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Header.addEmptyLine(1);
    }

    public Individual(ArrayList<Proxy> proxyList) {

        Header.addChank("Health Care Proxy Agent");
        messageProxy.add("Health Care Proxy Agent");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (int i = 0; i < proxyList.size(); i++) {
            int k = i + 1;
            Header.addTable("Health Care Proxy Agent " + k + " :");
            Header.addTable("");
            messageProxy.add("Health Care Proxy Agent " + k + " :");
            messageProxy.add("");

            Proxy p = proxyList.get(i);

            String name = "";
            if (p.getName() != null) {
                name = p.getName();
            }
            Header.addTable("Name :");
            Header.addTable(name);
            messageProxy.add("Name :");
            messageProxy.add(name);

            String relationShip = "";
            if (p.getRelationType() != null) {
                relationShip = p.getRelationType();
            }
            Header.addTable("Relationship :");
            Header.addTable(relationShip);
            messageProxy.add("Relationship :");
            messageProxy.add(relationShip);

            String mobile = "";
            if (p.getMobile() != null) {
                mobile = p.getMobile();
            }
            Header.addTable("Mobile Number :");
            Header.addTable(mobile);
            messageProxy.add("Mobile Number :");
            messageProxy.add(mobile);

            String homePhone = "";
            if (p.getWorkPhone() != null) {
                homePhone = p.getWorkPhone();
            }
            Header.addTable("Home Phone :");
            Header.addTable(homePhone);
            messageProxy.add("Home Phone :");
            messageProxy.add(homePhone);

            String officePhone = "";
            if (p.getPhone() != null) {
                officePhone = p.getPhone();
            }
            Header.addTable("Office Phone :");
            Header.addTable(officePhone);
            messageProxy.add("Office Phone :");
            messageProxy.add(officePhone);

            String email = "";
            if (p.getEmail() != null) {
                email = p.getEmail();
            }
            Header.addTable("Email Address :");
            Header.addTable(email);
            messageProxy.add("Email Address :");
            messageProxy.add(email);

            String address = "";
            if (p.getAddress() != null) {
                address = p.getAddress();
            }
            Header.addTable("Address :");
            Header.addTable(address);
            messageProxy.add("Address :");
            messageProxy.add(address);

            String priority = "";
            if (p.getIsPrimary() == 0) {
                priority = "Primary";
            } else {
                priority = "Successor";
            }
            Header.addTable("Proxy Agent Priority :");
            Header.addTable(priority);
            messageProxy.add("Proxy Agent Priority :");
            messageProxy.add(priority);

        }
        Header.table.setWidthPercentage(100f);
        try {

            Header.document.add(Header.table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Header.addEmptyLine(1);
    }

    public Individual(ArrayList<Living> livingList, int i) {
        Header.addChank("Activities of Daily Living");
        messageLiving.add("Activities of Daily Living");
        Header.addEmptyLine(1);

        Header.widths[0] = 0.15f;
        Header.widths[1] = 0.85f;
        Header.table = new PdfPTable(Header.widths);
        Header.table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        for (i = 0; i < livingList.size(); i++) {
            Header.addTable("Activities of Daily Living(ADL)" + " :");
            Header.addTable("");
            messageLiving.add("Activities of Daily Living(ADL)" + " :");
            messageLiving.add("");

            Living s = livingList.get(i);

            String bathing = "";
            if (s.getBath() != null) {
                bathing = s.getBath();
            }
            Header.addTable("Bathing :");
            Header.addTable(bathing);
            messageLiving.add("Bathing :");
            messageLiving.add(bathing);

            String continence = "";
            if (s.getContinence() != null) {
                continence = s.getContinence();
            }
            Header.addTable("Continence :");
            Header.addTable(continence);
            messageLiving.add("Continence :");
            messageLiving.add(continence);

            String dressing = "";
            if (s.getDress() != null) {
                dressing = s.getDress();
            }
            Header.addTable("Dressing :");
            Header.addTable(dressing);
            messageLiving.add("Dressing :");
            messageLiving.add(dressing);

            String eating = "";
            if (s.getFeed() != null) {
                eating = s.getFeed();
            }
            Header.addTable("Eating :");
            Header.addTable(eating);
            messageLiving.add("Eating :");
            messageLiving.add(eating);

            String toileting = "";
            if (s.getToileting() != null) {
                toileting = s.getToileting();
            }
            Header.addTable("Toileting :");
            Header.addTable(toileting);
            messageLiving.add("Toileting :");
            messageLiving.add(toileting);

            String transfering = "";
            if (s.getTransfer() != null) {
                transfering = s.getTransfer();
            }
            Header.addTable("Transfering :");
            Header.addTable(transfering);
            messageLiving.add("Transfering :");
            messageLiving.add(transfering);

            String functionOther = "";
            if (s.getFunctionOther() != null) {
                functionOther = s.getFunctionOther();
            }
            Header.addTable("Other-specify :");
            Header.addTable(functionOther);
            messageLiving.add("Other-specify :");
            messageLiving.add(functionOther);

            String functionNote = "";
            if (s.getFunctionNote() != null) {
                functionNote = s.getFunctionNote();
            }
            Header.addTable("Note :");
            Header.addTable(functionNote);
            messageLiving.add("Note :");
            messageLiving.add(functionNote);


            Header.addTable("Instrumental Activities of Daily Living(IADL)" + " :");
            Header.addTable("");
            messageLiving.add("Instrumental Activities of Daily Living(IADL)" + " :");
            messageLiving.add("");

            String access = "";
            if (s.getTransport() != null) {
                access = s.getTransport();
            }
            Header.addTable("Accessing transportation :");
            Header.addTable(access);
            messageLiving.add("Accessing transportation :");
            messageLiving.add(access);

            String carePet = "";
            if (s.getPets() != null) {
                carePet = s.getPets();
            }
            Header.addTable("Caring for pets :");
            Header.addTable(carePet);
            messageLiving.add("Caring for pets :");
            messageLiving.add(carePet);

            String driving = "";
            if (s.getDrive() != null) {
                driving = s.getDrive();
            }
            Header.addTable("Driving :");
            Header.addTable(driving);
            messageLiving.add("Driving :");
            messageLiving.add(driving);

            String housekeeping = "";
            if (s.getKeep() != null) {
                housekeeping = s.getKeep();
            }
            Header.addTable("Housekeeping :");
            Header.addTable(housekeeping);
            messageLiving.add("Housekeeping :");
            messageLiving.add(housekeeping);

            String manage = "";
            if (s.getMedication() != null) {
                manage = s.getMedication();
            }
            Header.addTable("Manage medications :");
            Header.addTable(manage);
            messageLiving.add("Manage medications :");
            messageLiving.add(manage);

            String finance = "";
            if (s.getFinance() != null) {
                finance = s.getFinance();
            }
            Header.addTable("Managing personal finances :");
            Header.addTable(finance);
            messageLiving.add("Managing personal finances :");
            messageLiving.add(finance);

            String meal = "";
            if (s.getPrepare() != null) {
                meal = s.getPrepare();
            }
            Header.addTable("Preparing meals :");
            Header.addTable(meal);
            messageLiving.add("Preparing meals :");
            messageLiving.add(meal);

            String shopping = "";
            if (s.getShop() != null) {
                shopping = s.getShop();
            }
            Header.addTable("Shopping for groceries or clothes :");
            Header.addTable(shopping);
            messageLiving.add("Shopping for groceries or clothes :");
            messageLiving.add(shopping);

            String telephone = "";
            if (s.getUse() != null) {
                telephone = s.getUse();
            }
            Header.addTable("Using telephone:");
            Header.addTable(telephone);
            messageLiving.add("Using telephone :");
            messageLiving.add(telephone);

            String instOther = "";
            if (s.getInstOther() != null) {
                instOther = s.getInstOther();
            }
            Header.addTable("Other-specify :");
            Header.addTable(instOther);
            messageLiving.add("Other-specify :");
            messageLiving.add(instOther);

            String instNote = "";
            if (s.getInstNote() != null) {
                instNote = s.getInstNote();
            }
            Header.addTable("Note :");
            Header.addTable(instNote);
            messageLiving.add("Note :");
            messageLiving.add(instNote);

        }
        Header.table.setWidthPercentage(100f);
        try {

            Header.document.add(Header.table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Header.addEmptyLine(1);

    }
}
