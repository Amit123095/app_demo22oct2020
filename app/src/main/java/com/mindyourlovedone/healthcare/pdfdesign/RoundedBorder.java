package com.mindyourlovedone.healthcare.pdfdesign;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;

public class RoundedBorder implements PdfPTableEvent {

    @Override
    public void tableLayout(PdfPTable table, float[][] width, float[] height,
                            int headerRows, int rowStart, PdfContentByte[] canvas) {
        float widths[] = width[0];
            float x1 = widths[0];
            float x2 = widths[widths.length - 1];
            float y1 = height[0];
            float y2 = height[height.length - 1];
            PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
         //   cb.rectangle(x1, y1, x2 - x1, y2 - y1);
            cb.roundRectangle(x1, y1, x2 - x1, y2 - y1,6);
            cb.setColorStroke(WebColors.getRGBColor("#D6D6D6"));
            cb.stroke();
            cb.setLineWidth(2.0f);
            cb.resetRGBColorStroke();

    }
}
