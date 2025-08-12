
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class PdfDoldurucu {
    public static void main(String[] args) {
        // PDF dosya yolları
        String originalPdfPath = "odeaLsozlesme.pdf";
        String outputPdfPath = "DOLDURULMUS_ODeal_Sozlesmesi.pdf";
        String fontPath = "DejaVuSans.ttf";
        try (PDDocument document = PDDocument.load(new File(originalPdfPath));
             Scanner scanner = new Scanner(System.in, "UTF-8")) {
            // 1. Adım: Gerekli Kaynakları Hazırlaa
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            PDResources resources = acroForm.getDefaultResources();
            if (resources == null) {
                resources = new PDResources();
                acroForm.setDefaultResources(resources);
            }
            PDType0Font font = PDType0Font.load(document, new File(fontPath));
            String fontName = resources.add(font).getName();
            // 2. Adım: Kullanıcıdan Bilgileri Al
            System.out.println("Lütfen gerekli bilgileri giriniz:");
            System.out.print("Üye İşyeri (Ad Soyad / Firma Adı): ");
            String uyeIsyeri = scanner.nextLine();
            System.out.print("Unvan: ");
            String unvan = scanner.nextLine();
            System.out.print("TCKN / VKN & Vergi Dairesi: ");
            String tckn_vkn = scanner.nextLine();
            System.out.print("Telefon: ");
            String telefon = scanner.nextLine();
            System.out.print("Alternatif Telefon: ");
            String alternatif_telefon = scanner.nextLine();
            System.out.print("E-posta: ");
            String e_posta = scanner.nextLine();
            System.out.print("Adres: ");
            String adres = scanner.nextLine();
            System.out.print("Cihaz Marka / Model / Hizmet: ");
            String cihaz_marka_model = scanner.nextLine();
            System.out.print("Adet: ");
            String adet = scanner.nextLine();
            System.out.print("Üyelik Paket Tipi: ");
            String uyelik_paket_tipi = scanner.nextLine();
            System.out.print("IBAN No / IBAN Sahibi Adı Soyadı: ");
            String iban = scanner.nextLine();
            // 3. Adım: Alanları Doldurma (Font Bilgisiyle)
            setFieldValue(acroForm, "uye_isyeri", uyeIsyeri, fontName);
            setFieldValue(acroForm, "unvan", unvan, fontName);
            setFieldValue(acroForm, "tckn_vkn", tckn_vkn, fontName);
            setFieldValue(acroForm, "telefon", telefon, fontName);
            setFieldValue(acroForm, "alternatif_telefon", alternatif_telefon, fontName);
            setFieldValue(acroForm, "e_posta", e_posta, fontName);
            setFieldValue(acroForm, "adres", adres, fontName);
            setFieldValue(acroForm, "cihaz_marka_model", cihaz_marka_model, fontName);
            setFieldValue(acroForm, "adet", adet, fontName);
            setFieldValue(acroForm, "uyelik_paket_tipi", uyelik_paket_tipi, fontName);
            setFieldValue(acroForm, "iban", iban, fontName);
            // 4. Adım: Değişiklikleri Yeni Bir Dosyaya Kaydet
            document.save(outputPdfPath);
            System.out.println("Harika! Form başarıyla dolduruldu ve '" + outputPdfPath + "' olarak kaydedildi.");
        } catch (IOException e) {
            System.err.println("PDF işlenirken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**

 * Formdaki bir alanı adıyla bulup değerini ve yazı tipini ayarlayan yardımcı metot.
 */
private static void setFieldValue(PDAcroForm acroForm, String fieldName, String value, String fontName) throws IOException {
    PDField field = acroForm.getField(fieldName);
    if (field != null) {
        // Alanın bir metin alanı (TextField) olup olmadığını kontrol ediyoruz.
        if (field instanceof PDTextField) {
            // Alanı metin alanına dönüştürüyoruz (cast ediyoruz).
            PDTextField textField = (PDTextField) field;
            // Varsayılan görünümü bizim fontumuzu kullanacak şekilde ayarlıyoruz.
            String defaultAppearance = "/" + fontName + " 10 Tf";
            textField.setDefaultAppearance(defaultAppearance);
        }
        // Değeri atıyoruz.
        field.setValue(value);
    } else {
        System.err.println("UYARI: '" + fieldName + "' adında bir alan bulunamadı. Bu alan atlanacak.");
    }
}
}