package com.example.dziennikmechanika;

import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import java.util.ArrayList;
import java.util.Arrays;

import org.spongycastle.jcajce.provider.symmetric.ARC4;

import androidx.appcompat.app.AppCompatActivity;

import static android.os.Environment.getExternalStorageDirectory;


public class Metodos extends AppCompatActivity {

    static BaseFont helvetica;
    static BaseFont helveticaB;
    String[] Opisy;
    public class Rotate extends PdfPageEventHelper {

        protected PdfNumber orientation = PdfPage.PORTRAIT;

        public void setOrientation(PdfNumber orientation) {
            this.orientation = orientation;
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            writer.addPageDictEntry(PdfName.ROTATE, orientation);
        }
    }
    public Boolean write(String fname, String fcontent, ArrayList<ArrayList<String>> tableContent) {

        try {
            String fpath = Environment.getExternalStorageDirectory() +"/"+ fname + ".pdf";
            File file = new File(String.valueOf(fpath));
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            if (file.exists ()) {
                file.delete();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            Paragraph pl;
            Paragraph eng;
            String plane = (tableContent.get(0)).get(0);
            String engine = (tableContent.get(0)).get(1);
            //fonts declaration
            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            helveticaB = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED);

            Font polskieFonty7=new Font(helvetica,7);
            Font polskieFontyB7=new Font(helvetica,7);
            Font polskieFonty8=new Font(helvetica,8);
            Font polskieFontyB8=new Font(helveticaB,8);
            Font polskieFonty10=new Font(helvetica,10);
            Font polskieFontyB10=new Font(helveticaB,10);
            Font polskieFonty12=new Font(helvetica,12);
            Font polskieFontyB12=new Font(helveticaB,12);


            //Opening document
            Document document = new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
            document.open();

            //PDF layout

            LineSeparator separator = new LineSeparator();
            separator.setLineWidth(2);
            Chunk linebreak = new Chunk(separator);
            PdfPTable table8 = new PdfPTable(8);
            table8.setWidthPercentage(100);

            document.add(new Paragraph("Urząd Lotnictwa Cywilnego – Civil Aviation Office", polskieFonty8));
            document.add(linebreak);


            Chunk glue = new Chunk(new VerticalPositionMark());
            Paragraph p = new Paragraph("Rozdział 3.3. – Doświadczenie zawodowe",polskieFonty12);
            p.add(new Chunk(glue));
            p.add("Section 3.3 – Maintenance  Experience\n"+"\n");
            document.add(p);

            PdfPCell cell;
            table8.setWidths(new int[]{1, 1, 1, 1 ,1,1,5,3});

            cell =(new PdfPCell(new Phrase("Typ SP/silnik\n" +"\n" + "A/C Type/engine",polskieFontyB10 )));
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBorderWidthLeft(1);
            cell.setBorderWidthTop(1);
            cell.setBorderWidthBottom(1);
            table8.addCell(cell);

            cell =(new PdfPCell(new Phrase(""+ plane +"/" + engine,polskieFontyB10 ))); // STRING Z FORMY
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBorderWidthTop(1);
            cell.setBorderWidthBottom(1);
            table8.addCell(cell);

            cell =(new PdfPCell(new Phrase("Organizacja:\n" +"\n"+ "Organization:",polskieFontyB10 )));
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBorderWidthLeft(1);
            cell.setBorderWidthTop(1);
            cell.setBorderWidthBottom(1);
            table8.addCell(cell);

            cell = (new PdfPCell(new Phrase("" + "INVESTA aero SERVICE",polskieFontyB12 )));
            cell.setColspan(1);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBorderWidthRight(1);
            cell.setBorderWidthTop(1);
            cell.setBorderWidthBottom(1);
            table8.addCell(cell);

            cell =(new PdfPCell(new Phrase("Właściciel SP:\n" +"\n"+ "A/C holder:",polskieFontyB10 )));
            cell.setColspan(1);
            table8.addCell(cell);


            //Row2
             pl = new Paragraph("Data\n" ,polskieFontyB10 );
             eng = new Paragraph("Date\n" ,polskieFonty8 );
            pl.setAlignment(Element.ALIGN_CENTER);
            eng.setAlignment(Element.ALIGN_CENTER);            cell =(new PdfPCell());
            //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.addElement(pl);
            cell.addElement(eng);
            table8.addCell(cell);

            pl = new Paragraph("Znaki rej.\n" ,polskieFontyB10 );
            eng = new Paragraph("A/C Reg\n" ,polskieFonty8 );
            pl.setAlignment(Element.ALIGN_CENTER);
            eng.setAlignment(Element.ALIGN_CENTER);
            cell =(new PdfPCell());
            //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.addElement(pl);
            cell.addElement(eng);

            table8.addCell(cell);

            pl = new Paragraph("Numer zadania / ATA\n" ,polskieFontyB10 );
            eng = new Paragraph("Job No\n" ,polskieFonty8 );
            pl.setAlignment(Element.ALIGN_CENTER);
            eng.setAlignment(Element.ALIGN_CENTER);            cell =(new PdfPCell());
            //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.addElement(pl);
            cell.addElement(eng);

            table8.addCell(cell);

            pl = new Paragraph("Hangar / Linia\n" ,polskieFontyB10 );
            eng = new Paragraph("Hangar / Line\n" ,polskieFonty8 );
            pl.setAlignment(Element.ALIGN_CENTER);
            eng.setAlignment(Element.ALIGN_CENTER);
            cell =(new PdfPCell());
           // cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.addElement(pl);
            cell.addElement(eng);

            table8.addCell(cell);

            pl = new Paragraph("Typ czynności\n" ,polskieFontyB10 );
            eng = new Paragraph("Action type\n" ,polskieFonty8 );
            pl.setAlignment(Element.ALIGN_CENTER);
            eng.setAlignment(Element.ALIGN_CENTER);
            cell =(new PdfPCell());
            //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.addElement(pl);
            cell.addElement(eng);

            table8.addCell(cell);

            pl = new Paragraph("Użyta podkategoria licencji\n" ,polskieFontyB7 );
            eng = new Paragraph("Used category\n" ,polskieFonty8 );
            pl.setAlignment(Element.ALIGN_CENTER);
            eng.setAlignment(Element.ALIGN_CENTER);
            cell =(new PdfPCell());
            //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.addElement(pl);
            cell.addElement(eng);

            table8.addCell(cell);

            pl = new Paragraph("Opis zadania / numer AMM\n" ,polskieFontyB10 );
            eng = new Paragraph("Task detail / AMM no.\n" ,polskieFonty8 );
            pl.setAlignment(Element.ALIGN_CENTER);
            eng.setAlignment(Element.ALIGN_CENTER);
            //cell =(new PdfPCell(new Phrase("Opis zadania / numer AMM\n" + "Task detail / AMM no.",polskieFontyB10 )));
            cell =(new PdfPCell());
            //cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.addElement(pl);
            cell.addElement(eng);
            table8.addCell(cell);

            pl = new Paragraph("Nazwisko osoby poświadczającej, podpis, organizacja, nr licencji:\n" ,polskieFontyB10 );
            eng = new Paragraph("Name, Signature, Position, Organization, Approval No. or Holder of A/C:\n" ,polskieFonty8 );
            pl.setAlignment(Element.ALIGN_CENTER);
            eng.setAlignment(Element.ALIGN_CENTER);
            cell =(new PdfPCell());
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.addElement(pl);
            cell.addElement(eng);
            table8.addCell(cell);


            for (int i =0;i<tableContent.size();i++) {

                ArrayList<String> row =tableContent.get(i);
                if(row.get(7).contains("@"))
                {
                    Opisy = row.get(7).split("@");
                    for(int j=0;j<Opisy.length;j++)
                    {
                        Paragraph par1 = new Paragraph(row.get(2), polskieFonty10);
                        cell = (new PdfPCell());
                        cell.addElement(par1);
                        table8.addCell(cell);

                        Paragraph par2 = new Paragraph(row.get(3), polskieFonty10);
                        cell = (new PdfPCell());
                        cell.addElement(par2);
                        table8.addCell(cell);

                        Paragraph par3 = new Paragraph(row.get(4), polskieFonty10);
                        cell = (new PdfPCell());
                        cell.addElement(par3);
                        table8.addCell(cell);

                        Paragraph par4 = new Paragraph(row.get(5), polskieFonty10);
                        cell = (new PdfPCell());
                        cell.addElement(par4);
                        table8.addCell(cell);

                        Paragraph par5 = new Paragraph(row.get(6), polskieFonty10);
                        cell = (new PdfPCell());
                        cell.addElement(par5);
                        table8.addCell(cell);

                        Paragraph par6 = new Paragraph(" ");
                        cell = (new PdfPCell());
                        cell.addElement(par6);
                        table8.addCell(cell);

                        Paragraph par7 = new Paragraph(Opisy[j], polskieFonty10);
                        cell = (new PdfPCell());
                        cell.addElement(par7);
                        table8.addCell(cell);

                        Paragraph par8 = new Paragraph(" ");
                        cell = (new PdfPCell());
                        cell.addElement(par8);
                        table8.addCell(cell);
                    }
                }
                else {
                    Paragraph par1 = new Paragraph(row.get(2), polskieFonty10);
                    cell = (new PdfPCell());
                    cell.addElement(par1);
                    table8.addCell(cell);

                    Paragraph par2 = new Paragraph(row.get(3), polskieFonty10);
                    cell = (new PdfPCell());
                    cell.addElement(par2);
                    table8.addCell(cell);

                    Paragraph par3 = new Paragraph(row.get(4), polskieFonty10);
                    cell = (new PdfPCell());
                    cell.addElement(par3);
                    table8.addCell(cell);

                    Paragraph par4 = new Paragraph(row.get(5), polskieFonty10);
                    cell = (new PdfPCell());
                    cell.addElement(par4);
                    table8.addCell(cell);

                    Paragraph par5 = new Paragraph(row.get(6), polskieFonty10);
                    cell = (new PdfPCell());
                    cell.addElement(par5);
                    table8.addCell(cell);

                    Paragraph par6 = new Paragraph(" ");
                    cell = (new PdfPCell());
                    cell.addElement(par6);
                    table8.addCell(cell);

                    Paragraph par7 = new Paragraph(row.get(7), polskieFonty10);
                    cell = (new PdfPCell());
                    cell.addElement(par7);
                    table8.addCell(cell);

                    Paragraph par8 = new Paragraph(" ");
                    cell = (new PdfPCell());
                    cell.addElement(par8);
                    table8.addCell(cell);
                }
            }

            pl = new Paragraph("Imię nazwisko:\n" ,polskieFontyB10 );
            eng = new Paragraph("Name surname:\n" ,polskieFonty8 );
           // pl.setAlignment(Element.ALIGN_CENTER);
           // eng.setAlignment(Element.ALIGN_CENTER);
            cell =(new PdfPCell());
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBorderWidthLeft(1);
            cell.setBorderWidthBottom(1);
            cell.addElement(pl);
            cell.addElement(eng);
            table8.addCell(cell);

            pl = new Paragraph("Filip Kopeć\n" ,polskieFontyB10 );
            //eng = new Paragraph("Name surname:\n" ,polskieFonty8 );
            // pl.setAlignment(Element.ALIGN_CENTER);
            // eng.setAlignment(Element.ALIGN_CENTER);
            cell =(new PdfPCell());
            cell.setColspan(4);
            cell.setBorder(Rectangle.NO_BORDER);
            //cell.setBorderWidthLeft(1);
            cell.setBorderWidthBottom(1);
            cell.addElement(pl);
            table8.addCell(cell);

            pl = new Paragraph("Podpis:\n" ,polskieFontyB10 );
            eng = new Paragraph("Sign:\n" ,polskieFonty8 );
            // pl.setAlignment(Element.ALIGN_CENTER);
            // eng.setAlignment(Element.ALIGN_CENTER);
            cell =(new PdfPCell());
            cell.setColspan(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBorderWidthRight(1);
            cell.setBorderWidthBottom(1);
            cell.addElement(pl);
            cell.addElement(eng);
            table8.addCell(cell);

            document.add(table8);
            document.add(new Paragraph("\n"));
            document.add(linebreak);
            Chunk glue1 = new Chunk(new VerticalPositionMark());
            Paragraph p1 = new Paragraph("Książka Mechanika Lotniczego – Aircraft Maintenance Engineer’s Log Book",polskieFonty8);
            p1.add(new Chunk(glue1));
            p1.add("ROZDZIAŁ 3.3 – SECTION 3. 3\n"+"\n");
            document.add(p1);
            document.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {

            e.printStackTrace();
            return false;
        }


    }
    }

