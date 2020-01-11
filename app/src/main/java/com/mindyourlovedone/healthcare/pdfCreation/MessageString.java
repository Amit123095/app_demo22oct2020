package com.mindyourlovedone.healthcare.pdfCreation;

import com.mindyourlovedone.healthcare.model.PersonalInfo;
import com.mindyourlovedone.healthcare.pdfdesign.DocumentPdfNew;
import com.mindyourlovedone.healthcare.pdfdesign.IndividualNew;
import com.mindyourlovedone.healthcare.pdfdesign.InsurancePdfNew;
import com.mindyourlovedone.healthcare.pdfdesign.SpecialtyNew;

/**
 * Created by welcome on 11/1/2017.
 */

public class MessageString {
    public StringBuffer getProfileProfile() {
        StringBuffer result = new StringBuffer();
        if (IndividualNew.messageInfo.size() > 0) {
            result.append(IndividualNew.messageInfo.get(0));
            result.append("\n");
            for (int i = 1; i < IndividualNew.messageInfo.size(); i++) {

                result.append(IndividualNew.messageInfo.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;

    }

    public StringBuffer getProfileUser(PersonalInfo personalInfo) {
        StringBuffer result = new StringBuffer();
        if (IndividualNew.messageInfo.size() > 0) {
            result.append(IndividualNew.messageInfo.get(0));
            result.append("\n");
            for (int i = 1; i < IndividualNew.messageInfo.size(); i++) {

                result.append(IndividualNew.messageInfo.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");
                }
            }
        }

        return result;
    }

    public StringBuffer getProfileUser() {

        StringBuffer result = new StringBuffer();
        if (IndividualNew.messageInfo2.size() > 0) {
            result.append(IndividualNew.messageInfo2.get(0));
            result.append("\n");
            for (int i = 1; i < IndividualNew.messageInfo2.size(); i++) {

                result.append(IndividualNew.messageInfo2.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getMedicalInfo() {
        StringBuffer result = new StringBuffer();
        if (IndividualNew.messageInfo3.size() > 0) {
            result.append(IndividualNew.messageInfo3.get(0));
            result.append("\n");
            for (int i = 1; i < IndividualNew.messageInfo3.size(); i++) {

                result.append(IndividualNew.messageInfo3.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;

    }

    public StringBuffer getEmergencyInfo() {
        StringBuffer result = new StringBuffer();
        if (IndividualNew.messageEmergency.size() > 0) {
            result.append(IndividualNew.messageEmergency.get(0));
            result.append("\n");
            for (int i = 1; i < IndividualNew.messageEmergency.size(); i++) {

                result.append(IndividualNew.messageEmergency.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getPhysicianInfo() {
        StringBuffer result = new StringBuffer();
        if (IndividualNew.messagePhysician.size() > 0) {
            result.append(IndividualNew.messagePhysician.get(0));
            result.append("\n");
            for (int i = 1; i < IndividualNew.messagePhysician.size(); i++) {

                result.append(IndividualNew.messagePhysician.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getProxyInfo() {
        StringBuffer result = new StringBuffer();
        if (IndividualNew.messageProxy.size() > 0) {
            result.append(IndividualNew.messageProxy.get(0));
            result.append("\n");
            for (int i = 1; i < IndividualNew.messageProxy.size(); i++) {

                result.append(IndividualNew.messageProxy.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getDoctorsInfo() {
        StringBuffer result = new StringBuffer();
        if (SpecialtyNew.messageDoctor.size() > 0) {
            result.append(SpecialtyNew.messageDoctor.get(0));
            result.append("\n");
            for (int i = 1; i < SpecialtyNew.messageDoctor.size(); i++) {

                result.append(SpecialtyNew.messageDoctor.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getHospitalInfo() {
        StringBuffer result = new StringBuffer();
        if (SpecialtyNew.messageHospital.size() > 0) {
            result.append(SpecialtyNew.messageHospital.get(0));
            result.append("\n");
            for (int i = 1; i < SpecialtyNew.messageHospital.size(); i++) {

                result.append(SpecialtyNew.messageHospital.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getPharmacyInfo() {
        StringBuffer result = new StringBuffer();
        if (SpecialtyNew.messagePharmacy.size() > 0) {
            result.append(SpecialtyNew.messagePharmacy.get(0));
            result.append("\n");
            for (int i = 1; i < SpecialtyNew.messagePharmacy.size(); i++) {

                result.append(SpecialtyNew.messagePharmacy.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getAideInfo() {
        StringBuffer result = new StringBuffer();
        if (SpecialtyNew.messageAides.size() > 0) {
            result.append(SpecialtyNew.messageAides.get(0));
            result.append("\n");
            for (int i = 1; i < SpecialtyNew.messageAides.size(); i++) {

                result.append(SpecialtyNew.messageAides.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getFinanceInfo() {
        StringBuffer result = new StringBuffer();
        if (SpecialtyNew.messageFinance.size() > 0) {
            result.append(SpecialtyNew.messageFinance.get(0));
            result.append("\n");
            for (int i = 1; i < SpecialtyNew.messageFinance.size(); i++) {

                result.append(SpecialtyNew.messageFinance.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getInsuranceInfo() {
        StringBuffer result = new StringBuffer();
        if (InsurancePdfNew.messageInsurance.size() > 0) {
            result.append(InsurancePdfNew.messageInsurance.get(0));
            result.append("\n");
            for (int i = 1; i < InsurancePdfNew.messageInsurance.size(); i++) {

                result.append(InsurancePdfNew.messageInsurance.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getInsuranceCard() {
        StringBuffer result = new StringBuffer();
        if (InsurancePdfNew.messageCard.size() > 0) {
            result.append(InsurancePdfNew.messageCard.get(0));
            result.append("\n");
            for (int i = 1; i < InsurancePdfNew.messageCard.size(); i++) {

                result.append(InsurancePdfNew.messageCard.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getAppointInfo() {
        StringBuffer result = new StringBuffer();
        if (EventPdfNew.messageAppoint.size() > 0) {
            result.append(EventPdfNew.messageAppoint.get(0));
            result.append("\n");
            for (int i = 1; i < EventPdfNew.messageAppoint.size(); i++) {

                result.append(EventPdfNew.messageAppoint.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getEventInfo() {
        StringBuffer result = new StringBuffer();
        if (EventPdfNew.messageEvent.size() > 0) {
            result.append(EventPdfNew.messageEvent.get(0));
            result.append("\n");
            for (int i = 1; i < EventPdfNew.messageEvent.size(); i++) {

                result.append(EventPdfNew.messageEvent.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getLivingInfo() {
        StringBuffer result = new StringBuffer();
        if (EventPdfNew.messageLiving.size() > 0) {
            result.append(EventPdfNew.messageLiving.get(0));
            result.append("\n");
            for (int i = 1; i < EventPdfNew.messageLiving.size(); i++) {

                result.append(EventPdfNew.messageLiving.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }


    public StringBuffer getAdvanceDocuments() {
        StringBuffer result = new StringBuffer();
        if (DocumentPdfNew.messageAdvance.size() > 0) {
            result.append(DocumentPdfNew.messageAdvance.get(0));
            result.append("\n");
            for (int i = 1; i < DocumentPdfNew.messageAdvance.size(); i++) {

                result.append(DocumentPdfNew.messageAdvance.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getOtherDocuments() {
        StringBuffer result = new StringBuffer();
        if (DocumentPdfNew.messageOther.size() > 0) {
            result.append(DocumentPdfNew.messageOther.get(0));
            result.append("\n");
            for (int i = 1; i < DocumentPdfNew.messageOther.size(); i++) {

                result.append(DocumentPdfNew.messageOther.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getRecordDocuments() {
        StringBuffer result = new StringBuffer();
        if (DocumentPdfNew.messageRecord.size() > 0) {
            result.append(DocumentPdfNew.messageRecord.get(0));
            result.append("\n");
            for (int i = 1; i < DocumentPdfNew.messageRecord.size(); i++) {

                result.append(DocumentPdfNew.messageRecord.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getFormInfo() {
        StringBuffer result = new StringBuffer();
        if (InsurancePdfNew.messageForm.size() > 0) {
            result.append(InsurancePdfNew.messageForm.get(0));
            result.append("\n");
            for (int i = 1; i < InsurancePdfNew.messageForm.size(); i++) {

                result.append(InsurancePdfNew.messageForm.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }
}
