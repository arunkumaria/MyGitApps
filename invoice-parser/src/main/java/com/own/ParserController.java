package com.own;

import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class ParserController {

    /*
     * IMPORTANT:
     *
     * Change this path if Tesseract is installed somewhere else.
     *
     * Windows default:
     */
	private static final String TESSERACT_DATA_PATH =
			 "/opt/homebrew/opt/tesseract/share/tessdata";

    @PostMapping("/parse")
    public Map<String, Object> parse(
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Please upload a file");
        }

        String text = readDocument(file);

        Map<String, Object> result =
                new LinkedHashMap<>();

        result.put(
                "fileName",
                file.getOriginalFilename()
        );

        result.put(
                "documentType",
                findDocumentType(text)
        );

        result.put(
                "invoiceNumber",
                findInvoiceNumber(text)
        );

        result.put(
                "invoiceDate",
                findInvoiceDate(text)
        );

        result.put(
                "supplierName",
                findSupplier(text)
        );

        result.put(
                "supplierGstin",
                findGstin(text)
        );

        result.put(
                "customerGstin",
                findSecondGstin(text)
        );

        result.put(
                "subtotal",
                findMoney(text, "subtotal")
        );

        result.put(
                "discount",
                findMoney(text, "discount")
        );

        result.put(
                "cgst",
                findMoney(text, "cgst")
        );

        result.put(
                "sgst",
                findMoney(text, "sgst")
        );

        result.put(
                "igst",
                findMoney(text, "igst")
        );

        result.put(
                "taxableAmount",
                findMoney(text, "taxable\\s*amount")
        );

        result.put(
                "roundOff",
                findMoney(text, "round\\s*off")
        );

        result.put(
                "grandTotal",
                findGrandTotal(text)
        );

        result.put(
                "lineItems",
                findLineItems(text)
        );

        result.put(
                "validation",
                validate(result)
        );

        /*
         * Useful while developing.
         * Remove this later if you don't want raw OCR text.
         */
        result.put("extractedText", text);

        return result;
    }

    // =========================================================
    // DOCUMENT READING
    // =========================================================

    private String readDocument(
            MultipartFile file
    ) throws Exception {

        String filename =
                file.getOriginalFilename();

        String contentType =
                file.getContentType();

        byte[] data =
                file.getBytes();

        /*
         * PDF
         */
        if (isPdf(filename, contentType)) {

            try (PDDocument document =
                         Loader.loadPDF(data)) {

                /*
                 * First try normal PDF text.
                 */
                String text =
                        new PDFTextStripper()
                                .getText(document);

                if (text != null &&
                        !text.trim().isEmpty()) {

                    return text;
                }

                /*
                 * No text means scanned/image PDF.
                 * Render each page and OCR it.
                 */
                return ocrPdf(document);
            }
        }

        /*
         * JPG / JPEG / PNG
         */
        if (isImage(filename, contentType)) {

            return ocrImage(data);
        }

        throw new RuntimeException(
                "Unsupported file. " +
                "Please upload PDF, JPG, JPEG or PNG."
        );
    }

    private boolean isPdf(
            String filename,
            String contentType
    ) {

        return "application/pdf".equalsIgnoreCase(contentType)
                || filename != null
                && filename.toLowerCase()
                .endsWith(".pdf");
    }

    private boolean isImage(
            String filename,
            String contentType
    ) {

        if (contentType != null &&
                contentType.toLowerCase()
                        .startsWith("image/")) {

            return true;
        }

        if (filename == null) {
            return false;
        }

        String name =
                filename.toLowerCase();

        return name.endsWith(".jpg")
                || name.endsWith(".jpeg")
                || name.endsWith(".png");
    }

    // =========================================================
    // OCR
    // =========================================================

    private Tesseract createOcr() {

        Tesseract tesseract =
                new Tesseract();

        /*
         * Windows Tesseract installation.
         *
         * If your installation is elsewhere,
         * change TESSERACT_DATA_PATH above.
         */
        tesseract.setDatapath(
                TESSERACT_DATA_PATH
        );

        tesseract.setLanguage("eng");

        return tesseract;
    }

    private String ocrImage(
            byte[] data
    ) throws Exception {

        BufferedImage image =
                ImageIO.read(
                        new ByteArrayInputStream(data)
                );

        if (image == null) {

            throw new RuntimeException(
                    "Could not read image"
            );
        }

        return createOcr().doOCR(image);
    }

    private String ocrPdf(
            PDDocument document
    ) throws Exception {

        PDFRenderer renderer =
                new PDFRenderer(document);

        Tesseract tesseract =
                createOcr();

        StringBuilder result =
                new StringBuilder();

        /*
         * OCR every page.
         */
        for (int page = 0;
             page < document.getNumberOfPages();
             page++) {

            BufferedImage image =
                    renderer.renderImageWithDPI(
                            page,
                            200
                    );

            String pageText =
                    tesseract.doOCR(image);

            result.append(pageText);
            result.append("\n");
        }

        return result.toString();
    }

    // =========================================================
    // DOCUMENT TYPE
    // =========================================================

    private String findDocumentType(
            String text
    ) {

        String t =
                text.toLowerCase();

        if (t.contains("purchase order")) {
            return "PURCHASE_ORDER";
        }

        if (t.contains("delivery challan")) {
            return "DELIVERY_CHALLAN";
        }

        if (t.contains("credit note")) {
            return "CREDIT_NOTE";
        }

        if (t.contains("debit note")) {
            return "DEBIT_NOTE";
        }

        if (t.contains("goods receipt")
                || t.contains(" grn ")
                || t.startsWith("grn")) {

            return "GRN";
        }

        if (t.contains("receipt")) {
            return "RECEIPT";
        }

        if (t.contains("invoice")) {
            return "INVOICE";
        }

        return "UNKNOWN";
    }

    // =========================================================
    // INVOICE NUMBER
    // =========================================================

    private String findInvoiceNumber(
            String text
    ) {

        String[] patterns = {

                "(?i)invoice\\s*(?:no|number|#)"
                        + "\\s*[:.-]?\\s*([A-Za-z0-9/-]+)",

                "(?i)invoice\\s*[:.-]\\s*"
                        + "([A-Za-z0-9/-]+)"
        };

        for (String regex : patterns) {

            String value =
                    find(text, regex);

            if (value != null) {
                return value;
            }
        }

        return null;
    }

    // =========================================================
    // DATE
    // =========================================================

    private String findInvoiceDate(
            String text
    ) {

        String[] patterns = {

                "(?i)invoice\\s*date\\s*[:.-]?\\s*"
                        + "(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4})",

                "(?i)date\\s*[:.-]?\\s*"
                        + "(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4})",

                "\\b\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}\\b"
        };

        for (String regex : patterns) {

            String value =
                    find(text, regex);

            if (value != null) {
                return value;
            }
        }

        return null;
    }

    // =========================================================
    // GSTIN
    // =========================================================

    private List<String> findAllGstins(
            String text
    ) {

        Pattern pattern =
                Pattern.compile(
                        "\\b[0-9]{2}[A-Z]{5}[0-9]{4}"
                                + "[A-Z][1-9A-Z]Z[0-9A-Z]\\b"
                );

        Matcher matcher =
                pattern.matcher(
                        text.toUpperCase()
                );

        List<String> gstins =
                new ArrayList<>();

        while (matcher.find()) {

            if (!gstins.contains(
                    matcher.group()
            )) {
                gstins.add(
                        matcher.group()
                );
            }
        }

        return gstins;
    }

    private String findGstin(
            String text
    ) {

        List<String> gstins =
                findAllGstins(text);

        if (gstins.isEmpty()) {
            return null;
        }

        return gstins.get(0);
    }

    private String findSecondGstin(
            String text
    ) {

        List<String> gstins =
                findAllGstins(text);

        if (gstins.size() < 2) {
            return null;
        }

        return gstins.get(1);
    }

    // =========================================================
    // SUPPLIER
    // =========================================================

    private String findSupplier(
            String text
    ) {

        String[] patterns = {

                "(?i)supplier\\s*[:.-]\\s*(.+)",

                "(?i)supplier\\s*name\\s*[:.-]\\s*(.+)",

                "(?i)sold\\s*by\\s*[:.-]\\s*(.+)"
        };

        for (String regex : patterns) {

            String value =
                    find(text, regex);

            if (value != null) {

                return value
                        .split("\\R")[0]
                        .trim();
            }
        }

        return null;
    }

    // =========================================================
    // MONEY
    // =========================================================

    private String findMoney(
            String text,
            String field
    ) {

        String regex =
                "(?i)(?:"
                        + field
                        + ")"
                        + "\\s*[:₹$ ]*"
                        + "([0-9,]+(?:\\.\\d{1,2})?)";

        Matcher matcher =
                Pattern.compile(regex)
                        .matcher(text);

        if (!matcher.find()) {
            return "0.00";
        }

        return cleanNumber(
                matcher.group(1)
        );
    }

    private String findGrandTotal(
            String text
    ) {

        String regex =
                "(?i)(?:grand\\s*total"
                        + "|total\\s*amount"
                        + "|net\\s*amount"
                        + "|amount\\s*payable"
                        + "|total)"
                        + "\\s*[:₹$ ]*"
                        + "([0-9,]+(?:\\.\\d{1,2})?)";

        Matcher matcher =
                Pattern.compile(regex)
                        .matcher(text);

        if (!matcher.find()) {
            return "0.00";
        }

        return cleanNumber(
                matcher.group(1)
        );
    }

    private String cleanNumber(
            String value
    ) {

        try {

            return new BigDecimal(
                    value.replace(",", "")
            ).toPlainString();

        } catch (Exception e) {

            return "0.00";
        }
    }

    // =========================================================
    // LINE ITEMS
    // =========================================================

    private List<Map<String, Object>> findLineItems(
            String text
    ) {

        List<Map<String, Object>> items =
                new ArrayList<>();

        String[] lines =
                text.split("\\R");

        /*
         * Very simple MVP table detection.
         *
         * Example:
         *
         * Product A  10  PCS  100  18  1180
         */

        Pattern pattern =
                Pattern.compile(
                        "^(.+?)\\s+"
                                + "(\\d+(?:\\.\\d+)?)\\s+"
                                + "([A-Za-z]+)\\s+"
                                + "(\\d+(?:\\.\\d+)?)\\s+"
                                + "(\\d+(?:\\.\\d+)?)%?\\s+"
                                + "(\\d+(?:\\.\\d+)?)$"
                );

        for (String line : lines) {

            Matcher matcher =
                    pattern.matcher(
                            line.trim()
                    );

            if (matcher.matches()) {

                Map<String, Object> item =
                        new LinkedHashMap<>();

                item.put(
                        "productName",
                        matcher.group(1)
                );

                item.put(
                        "quantity",
                        matcher.group(2)
                );

                item.put(
                        "unit",
                        matcher.group(3)
                );

                item.put(
                        "rate",
                        matcher.group(4)
                );

                item.put(
                        "gstPercent",
                        matcher.group(5)
                );

                item.put(
                        "lineTotal",
                        matcher.group(6)
                );

                items.add(item);
            }
        }

        return items;
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private Map<String, Object> validate(
            Map<String, Object> result
    ) {

        Map<String, Object> validation =
                new LinkedHashMap<>();

        String gstin =
                (String) result.get(
                        "supplierGstin"
                );

        boolean gstinValid =
                gstin == null
                        || gstin.matches(
                        "[0-9]{2}[A-Z]{5}[0-9]{4}"
                                + "[A-Z][1-9A-Z]Z[0-9A-Z]"
                );

        boolean invoiceNumberFound =
                result.get("invoiceNumber") != null;

        boolean dateFound =
                result.get("invoiceDate") != null;

        String total =
                (String) result.get(
                        "grandTotal"
                );

        boolean totalFound =
                total != null
                        && !total.equals("0.00");

        boolean manualReview =
                !invoiceNumberFound
                        || !dateFound
                        || !totalFound
                        || !gstinValid;

        validation.put(
                "gstinValid",
                gstinValid
        );

        validation.put(
                "invoiceNumberFound",
                invoiceNumberFound
        );

        validation.put(
                "dateFound",
                dateFound
        );

        validation.put(
                "grandTotalFound",
                totalFound
        );

        validation.put(
                "manualReview",
                manualReview
        );

        return validation;
    }

    // =========================================================
    // REGEX HELPER
    // =========================================================

    private String find(
            String text,
            String regex
    ) {

        Matcher matcher =
                Pattern.compile(regex)
                        .matcher(text);

        if (matcher.find()) {

            /*
             * If regex contains a capture group,
             * return that group.
             */
            if (matcher.groupCount() >= 1) {
                return matcher.group(1).trim();
            }

            return matcher.group().trim();
        }

        return null;
    }
}
