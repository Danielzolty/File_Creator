//package edu.yu.cs.com1320.project.stage5.impl;
//
//
//import edu.yu.cs.com1320.project.stage5.Document;
//import edu.yu.cs.com1320.project.stage5.DocumentStore;
//import edu.yu.cs.com1320.project.stage5.impl.DocumentStoreImpl;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.text.PDFTextStripper;
//import org.junit.Test;
//
//import java.io.*;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.*;
//
//import static org.junit.Assert.*;
//
//public class DocumentStoreImplTest {
//
//    private File pdfGenerator(String filename, String text) throws IOException {
//        File file = new File(filename);
//        PDDocument pdDocument = new PDDocument();
//        PDPage page = new PDPage();
//        pdDocument.addPage(page);
//        ByteArrayOutputStream h = new ByteArrayOutputStream();
//        PDPageContentStream book = new PDPageContentStream(pdDocument, page);
//        book.setFont(PDType1Font.TIMES_ROMAN, 11);
//        book.beginText();
//        book.newLineAtOffset(100, 550);
//        book.showText(text);
//        book.endText();
//        book.close();
//        pdDocument.save(file);
//
//        pdDocument.close();
//        return file;
//    }
//
//
//
//    private byte[] byteArrayGenerator(InputStream input) throws IOException {
//        //InputStream is = new ByteArrayInputStream(new byte[] { 0, 1, 2 }); // not really unknown
//
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        int nRead;
//        byte[] data = new byte[1024];
//        while ((nRead = input.read(data, 0, data.length)) != -1) {
//            buffer.write(data, 0, nRead);
//        }
//
//        buffer.flush();
//        byte[] byteArray = buffer.toByteArray();
//        return byteArray;
//    }
//
//    @Test
//    public void putDocumentTest() throws  IOException, URISyntaxException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        String myString = "This is a test.";
//        InputStream is = new ByteArrayInputStream( myString.getBytes() );
//        URI uri = new URI("Whynot?");
//        test.putDocument(is, uri, DocumentStore.DocumentFormat.TXT);
//        String stringTest = test.getDocumentAsTxt(uri);
//        assertEquals(myString, stringTest);
//    }
//
//    @Test
//    public void putDocumentPDFs() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals("Nuggets", txt);
//        boolean yes = test.deleteDocument(uri4);
//        assertTrue(yes);
//        byte[] doc = test.getDocumentAsPdf(uri4);
//        assertNull(doc);
//        String message = test.getDocumentAsTxt(uri8);
//        assertEquals("Mets", message);
//        boolean no = test.deleteDocument(uri4);
//        assertFalse(no);
//        test.putDocument(null, uri2, DocumentStore.DocumentFormat.TXT);
//        String noMessage = test.getDocumentAsTxt(uri2);
//        assertNull(noMessage);
////        test.deserializeDoc(uri);
////        test.deserializeDoc(uri2);
////        test.deserializeDoc(uri3);
////        test.deserializeDoc(uri4);
////        test.deserializeDoc(uri6);
////        test.deserializeDoc(uri5);
////        test.deserializeDoc(uri7);
////        test.deserializeDoc(uri8);
//
//
//    }
//
//    @Test
//    public void testNullAsFirstDocument() throws IOException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(null, uri2, DocumentStore.DocumentFormat.TXT);
//        String noMessage = test.getDocumentAsTxt(uri2);
//        assertNull(noMessage);
//        boolean no = test.deleteDocument(uri2);
//        assertFalse(no);
////        test.deserializeDoc(uri);
////        test.deserializeDoc(uri2);
////        test.deserializeDoc(uri3);
////        test.deserializeDoc(uri4);
////        test.deserializeDoc(uri6);
////        test.deserializeDoc(uri5);
////        test.deserializeDoc(uri7);
////        test.deserializeDoc(uri8);
//    }
//
//    @Test
//    public void testNullAsFirstDocument2() throws IOException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file2", "Denver");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(null, uri2, DocumentStore.DocumentFormat.TXT);
//        String noMessage = test.getDocumentAsTxt(uri2);
//        assertNull(noMessage);
//        boolean no = test.deleteDocument(uri2);
//        assertFalse(no);
////        test.deserializeDoc(uri);
////        test.deserializeDoc(uri2);
////        test.deserializeDoc(uri3);
////        test.deserializeDoc(uri4);
////        test.deserializeDoc(uri6);
////        test.deserializeDoc(uri5);
////        test.deserializeDoc(uri7);
////        test.deserializeDoc(uri8);
//    }
//
//    @Test
//    public void testNullAsFirstDocument3() throws IOException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(null, uri2, DocumentStore.DocumentFormat.TXT);
//        String noMessage = test.getDocumentAsTxt(uri2);
//        assertNull(noMessage);
//        boolean no = test.deleteDocument(uri2);
//        assertFalse(no);
////        test.deserializeDoc(uri);
////        test.deserializeDoc(uri2);
////        test.deserializeDoc(uri3);
////        test.deserializeDoc(uri4);
////        test.deserializeDoc(uri6);
////        test.deserializeDoc(uri5);
////        test.deserializeDoc(uri7);
////        test.deserializeDoc(uri8);
//    }
//
//    @Test
//    public void testNullAsFirstDocument4() throws IOException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "BBBB");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "AaAa");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "AaBB");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(null, uri, DocumentStore.DocumentFormat.TXT);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(null, uri3, DocumentStore.DocumentFormat.TXT);
//        String noMessage = test.getDocumentAsTxt(uri3);
//        assertNull(noMessage);
//        boolean no = test.deleteDocument(uri3);
//        assertFalse(no);
////        test.deserializeDoc(uri);
////        test.deserializeDoc(uri2);
////        test.deserializeDoc(uri3);
////        test.deserializeDoc(uri4);
////        test.deserializeDoc(uri6);
////        test.deserializeDoc(uri5);
////        test.deserializeDoc(uri7);
////        test.deserializeDoc(uri8);
//    }
//
//
//    @Test
//    public void putDocumentPDF1() throws IOException, URISyntaxException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = new File("Test");
//        PDDocument pdDocument = new PDDocument();
//        ByteArrayOutputStream urt = null;
//        PDPage page = new PDPage();
//        pdDocument.addPage(page);
//        ByteArrayOutputStream h = new ByteArrayOutputStream();
//        String bText = "Teaneck";
//        File file2 = pdfGenerator("filename", "guest");
//        InputStream inputStream2 = new FileInputStream(file2);
//
//
//        int y = bText.hashCode();
//        //PDFont font = PDType1Font.COURIER;
//        PDPageContentStream book = new PDPageContentStream(pdDocument, page);
//        book.setFont(PDType1Font.TIMES_ROMAN, 11);
//        book.beginText();
//        book.newLineAtOffset(100, 550);
//        book.showText(bText);
//        book.endText();
//        book.close();
//        pdDocument.save(file);
//
//        pdDocument.close();
//        URI uri = file.toURI();
//        InputStream inputStream1 = new FileInputStream(file);
//
//        int v = test.putDocument(inputStream1, uri, DocumentStore.DocumentFormat.PDF);
//        int u = test.putDocument(inputStream2, uri, DocumentStore.DocumentFormat.PDF);
//
//        assertEquals(y, u);
//
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals("guest", txt);
//
//    }
//
//    @Test
//    public void putDocumentTXT() throws IOException, URISyntaxException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        String string = "I am the best.";
//        ByteArrayOutputStream x = new ByteArrayOutputStream();
//        InputStream input = new ByteArrayInputStream(string.getBytes());
//        URI uri = new URI("testing.");
//        String string2 = "Denver is one.";
//        InputStream input2 = new ByteArrayInputStream((string2.getBytes()));
//        int y = test.putDocument(input, uri, DocumentStore.DocumentFormat.TXT);
//        int c = test.putDocument(input2, uri, DocumentStore.DocumentFormat.TXT);
//        int z = string.hashCode();
//        assertEquals(z, c);
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals(string2, txt);
//    }
//
//    @Test
//    public void undoTest() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.undo();
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals("Denver", txt);
////        test.deserializeDoc(uri);
////        test.deserializeDoc(uri2);
////        test.deserializeDoc(uri3);
////        test.deserializeDoc(uri4);
////        test.deserializeDoc(uri6);
////        test.deserializeDoc(uri5);
////        test.deserializeDoc(uri7);
////        test.deserializeDoc(uri8);
//
//    }
//
//    @Test
//    public void undoURITest() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.undo(uri);
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals("Denver", txt);
//        Document exam = test.getDocument(uri4);
//        String examString = exam.getDocumentAsTxt();
//        assertEquals("Rockies", examString);
////        test.deserializeDoc(uri);
////        test.deserializeDoc(uri2);
////        test.deserializeDoc(uri3);
////        test.deserializeDoc(uri4);
////        test.deserializeDoc(uri6);
////        test.deserializeDoc(uri5);
////        test.deserializeDoc(uri7);
////        test.deserializeDoc(uri8);
//
//
//    }
//
//    @Test
//    public void undoURITest2() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.undo();
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals("Denver", txt);
//
//    }
//
//    @Test
//    public void testDeleteUndo() throws IOException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "BBBB0");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "AaAa0");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "AaBB0");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies0");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche0");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks0");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees0");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets0");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(null, uri, DocumentStore.DocumentFormat.TXT);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(null, uri3, DocumentStore.DocumentFormat.TXT);
//        test.undo();
//        String noMessage = test.getDocumentAsTxt(uri3);
//        assertEquals("AaBB0", noMessage);
////        test.deserializeDoc(uri);
////        test.deserializeDoc(uri2);
////        test.deserializeDoc(uri3);
////        test.deserializeDoc(uri4);
////        test.deserializeDoc(uri6);
////        test.deserializeDoc(uri5);
////        test.deserializeDoc(uri7);
////        test.deserializeDoc(uri8);
//    }
//
//    @Test
//    public void testDeleteUndoURI() throws IOException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "BBBB1");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "AaAa1");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "AaBB1");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies1");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche1");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks1");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees1");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets1");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(null, uri, DocumentStore.DocumentFormat.TXT);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(null, uri3, DocumentStore.DocumentFormat.TXT);
//        test.undo(uri);
//        String noMessage = test.getDocumentAsTxt(uri);
//        assertEquals("BBBB1", noMessage);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri8);
//    }
//
//    @Test
//    public void undoManyTest() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver2");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos2");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets2");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies2");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche2");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks2");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees2");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets2");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.undo();
//        test.undo(uri);
//        test.undo(uri);
//        test.undo(uri);
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals("Denver2", txt);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri8);
//
//    }
//
////    @Test
////    public void uriTest() throws URISyntaxException {
////        String txt = "http://www.yu.edu/documents/doc1";
////        URI uri = new URI(txt);
////        String fileText = txt.replaceAll("http:/", "");
////        String baseText = "Desktop";
////        String filePathText = baseText + fileText + ".json";
////        System.out.println("xxxxxxxxxxxxxxx:  " + filePathText);
////
////    }
//
////    @Test
////    public void methodCount() {//need only test for non constructors
////        Method[] methods = DocumentImpl.class.getDeclaredMethods();
////        int publicMethodCount = 0;
////        for (Method method : methods) {
////            if (Modifier.isPublic(method.getModifiers())) {
////                if(!method.getName().equals("equals") && !method.getName().equals("hashCode") && !method.getName().equals("compareTo")) {
////                    publicMethodCount++;
////                }
////            }
////        }
////        assertEquals(7, publicMethodCount);
////        //assertTrue(publicMethodCount == 7);
////    }
////
//    @Test (expected = IllegalStateException.class)
//    public void nullUndoTest2() throws IOException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver3");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos3");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.undo(uri2);
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals("Denver3", txt);
//    }
//
//    @Test
//    public void samePDFUndo() throws IOException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver4");
//        InputStream inputStream = new FileInputStream(file);
//        InputStream inputStreamAlt = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos4");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStreamAlt, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.undo();
//        test.undo();
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals("Denver4", txt);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//
//
//    }
//
//
//    @Test
//    public void undoPutURI() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver5");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos5");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets5");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies5");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche5");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks5");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees5");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets5");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream5, uri, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream7, uri, DocumentStore.DocumentFormat.PDF);
////            test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(null, uri, DocumentStore.DocumentFormat.PDF);
//        test.undo(uri);
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals("Denver5", txt);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri8);
//
//    }
//
//    @Test
//    public void undoMutliTest() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver6");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos6");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets6");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies6");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche6");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks6");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees6");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets6");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        //test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        //test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri, DocumentStore.DocumentFormat.PDF);
//        //test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//
//        test.deleteDocument(uri);
//
//        test.deleteDocument(uri);
//        // test.putDocument(inputStream7, uri, DocumentStore.DocumentFormat.PDF);
//        //test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.undo(uri);
//        test.undo(uri);
//        byte[] check = test.getDocumentAsPdf(uri);
//        PDFTextStripper stripper = new PDFTextStripper();
//        String txt = stripper.getText(PDDocument.load(check)).trim();
//        assertEquals("Avalanche6", txt);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri8);
//
//    }
//
//
//
//    public File createPDFFile(String string, String filename) throws IOException {
//        PDDocument document = new PDDocument();
//        PDPage page = new PDPage();
//        document.addPage(page);
//        PDPageContentStream contentStream = new PDPageContentStream(document, page);
//        contentStream.setFont(PDType1Font.COURIER, 12);
//        contentStream.beginText();
//        contentStream.showText(string);
//        contentStream.endText();
//        contentStream.close();
//        document.save(filename);
//        document.close();
//        return new File(filename);
//    }
//    private byte[] convertToByte(InputStream input) throws IOException {
//        int integer;
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        byte[] bytearray = new byte[1024];
//        while ((integer = input.read(bytearray, 0, bytearray.length)) != -1) {
//            output.write(bytearray, 0, integer);
//        }
//        output.flush();
//        return output.toByteArray();
//    }
//
//
//
//    @Test
//    public void putDocumentPDFTrie() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        List<String> list = test.search("they");
//        assertEquals(4, list.size());
//        String text1 = list.get(0);
//        assertEquals("Rockies, they aren't just great, they are amazing. Are.", text1);
//        List<String> prefixList = test.searchByPrefix("ar");
//        assertEquals(5, prefixList.size());
//        String txt1 = prefixList.get(0);
//        assertEquals("Rockies, they aren't just great, they are amazing. Are.", txt1);
//        String txt2 = prefixList.get(1);
//        assertEquals("Mets are just Yankee wannabees. I don't know how they aren't as good.", txt2);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri8);
//
//
//    }
//
//
//    @Test
//    public void putDocumentPDFTrieDeletes() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        Set<URI> set = test.deleteAll("are");
//        assertEquals(4, set.size());
//        String string = test.getDocumentAsTxt(uri7);
//        assertNull(string);
//        List<String> stringList = test.search("are");
//        assertEquals(0, stringList.size());
//        test.undo();
//        List<byte[]> list = test.searchPDFs("are");
//        assertEquals(4, list.size());
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri8);
//
//
//
//    }
//
//    @Test
//    public void trieDeletesPrefixUndos() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports.");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        Set<URI> set = test.deleteAllWithPrefix("ar");
//        assertEquals(5, set.size());
//        String string = test.getDocumentAsTxt(uri7);
//        assertNull(string);
//        List<String> stringList = test.search("are");
//        assertEquals(0, stringList.size());
//        test.undo(uri7);
//        String text = test.getDocumentAsTxt(uri7);
//        assertEquals("Yankees are the most hated team in all of sports.", text);
//        String nul = test.getDocumentAsTxt(uri8);
//        assertNull(nul);
//        test.undo(uri8);
//        test.undo(uri6);
//        test.undo(uri4);
//        String nul2 = test.getDocumentAsTxt(uri3);
//        assertNull(nul2);
//        test.undo(uri3);
//        String text8 = test.getDocumentAsTxt(uri8);
//        assertEquals("Mets are just Yankee wannabees. I don't know how they aren't as good.", text8);
//        test.undo();
//        String nul3 = test.getDocumentAsTxt(uri8);
//        assertNull(nul3);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri8);
//
//
//
//
//    }
//
//    @Test
//    public void trieDeletesPrefix() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        Set<URI> set = test.deleteAllWithPrefix("ar");
//        assertEquals(5, set.size());
//        String string = test.getDocumentAsTxt(uri7);
//        assertNull(string);
//        List<String> stringList = test.search("are");
//        assertEquals(0, stringList.size());
//        test.undo();
//        List<byte[]> list = test.searchPDFs("are");
//        assertEquals(4, list.size());
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri5);
//
//
//
//    }
//
//    @Test
//    public void trieDeletesPrefix2() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        List<String> list = test.search("below");
//        assertEquals(0, list.size());
//        Set<URI> set = test.deleteAllWithPrefix("ar");
//        assertEquals(5, set.size());
//        String string = test.getDocumentAsTxt(uri7);
//        assertNull(string);
//        List<String> stringList = test.search("are");
//        assertEquals(0, stringList.size());
//        test.undo();
//        List<byte[]> list1 = test.searchPDFs("are");
//        assertEquals(4, list1.size());
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri5);
//
//
//
//    }
//
//    @Test
//    public void trieDeletesPrefixWeirdCharacters() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, a%^&*ren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they a*^&*^>.re amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks ar)(*)(&*^&e the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are*()&*^&^ the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets ar^&^&*^%&*^*e just Yankee wannabees. I don't know how they ar*(&)(&()*en't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        Set<URI> set = test.deleteAllWithPrefix("a$%&$%#&^r");
//        assertEquals(5, set.size());
//        String string = test.getDocumentAsTxt(uri7);
//        assertNull(string);
//        List<String> stringList = test.search("ar&()&(*()e");
//        assertEquals(0, stringList.size());
//        test.undo();
//        List<byte[]> list = test.searchPDFs("A*(_*)(*()&(*&rE");
//        assertEquals(4, list.size());
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri5);
//
//
//
//    }
//
//    @Test
//    public void trieDeletesPrefix2WithMaxDocs() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        test.setMaxDocumentCount(4);
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
////        System.out.println("Doc2: " + test.getDocument(uri2) + "  URI number:  " + uri2);
////        System.out.println("Doc3: " + test.getDocument(uri) + "  URI number:  " + uri);
////        System.out.println("Doc4: " + test.getDocument(uri4) + "  URI number:  " + uri4);
////        System.out.println("Doc5: " + test.getDocument(uri5) + "  URI number:  " + uri5);
////        System.out.println("Doc6: " + test.getDocument(uri6) + "  URI number:  " + uri6);
////        System.out.println("Doc7: " + test.getDocument(uri7) + "  URI number:  " + uri7);
////        System.out.println("Doc8: " + test.getDocument(uri8) + "  URI number:  " + uri8);
//        List<String> list = test.search("below");
//        assertEquals(0, list.size());
//        Set<URI> set = test.deleteAllWithPrefix("ar");
//        assertEquals(5, set.size());
//        String string = test.getDocumentAsTxt(uri7);
//        assertNull(string);
//        List<String> stringList = test.search("are");
//        assertEquals(0, stringList.size());
//        test.undo();
//        System.out.println("Test28--------------");
//        List<byte[]> list1 = test.searchPDFs("are");
//        assertEquals(4, list1.size());
//        System.out.println("End of test 28---------");
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri5);
//    }
//
//    private int docByteNumber(Document doc){
//        byte[] bites = doc.getDocumentAsPdf();
//        int biteSize = bites.length;
//        String txt = doc.getDocumentAsTxt();
//        byte[] stringBites = txt.getBytes();
//        int stringSize = stringBites.length;
//        return stringSize + biteSize;
//    }
//
//    @Test
//    public void trieDeletes2WithMaxDocs() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        test.setMaxDocumentCount(4);
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        //test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        String text4 = test.getDocumentAsTxt(uri4);
//        assertEquals(text4, "Rockies, they aren't just great, they are amazing. Are.");
//        List<String> list1 = test.search("because");
//        assertEquals("Avalanche, I don't really care for because I don't follow Hockey.", list1.get(0));
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
////        String text6 = test.getDocumentAsTxt(uri6);
////        assertNull(text6);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri8);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri6);
//
//    }
//
//    @Test
//    public void printDocSize() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        Document doc1 = test.getDocument(uri);
//        Document doc2 = test.getDocument(uri2);
//        Document doc3 = test.getDocument(uri3);
//        Document doc4 = test.getDocument(uri4);
//        Document doc5 = test.getDocument(uri5);
//        Document doc6 = test.getDocument(uri6);
//        Document doc7 = test.getDocument(uri7);
//        Document doc8 = test.getDocument(uri8);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri8);
//        System.out.println("Doc1 is: " + docByteNumber(doc1));
//        System.out.println("Doc2 is: " + docByteNumber(doc2));
//        System.out.println("Doc3 is: " + docByteNumber(doc3));
//        System.out.println("Doc4 is: " + docByteNumber(doc4));
//        System.out.println("Doc5 is: " + docByteNumber(doc5));
//        System.out.println("Doc6 is: " + docByteNumber(doc6));
//        System.out.println("Doc7 is: " + docByteNumber(doc7));
//        System.out.println("Doc8 is: " + docByteNumber(doc8));
//
//    }
//
//    @Test
//    public void heapDeleteWithUndos() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        test.setMaxDocumentCount(4);
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.TXT);
//        String text5 = test.getDocumentAsTxt(uri5);
//        assertEquals("Avalanche, I don't really care for because I don't follow Hockey.", text5);
//        test.undo(uri8);
//        String text4 = test.getDocumentAsTxt(uri4);
//        assertEquals("Rockies, they aren't just great, they are amazing. Are.", text4);
//        String text8 = test.getDocumentAsTxt(uri8);
//        assertNull(text8);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri6);
//
//
//    }
//
//    @Test
//    public void setMaxAfter() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.TXT);
//        test.setMaxDocumentCount(4);
//        String text5 = test.getDocumentAsTxt(uri5);
//        assertEquals("Avalanche, I don't really care for because I don't follow Hockey.", text5);
//        test.undo(uri8);
//        String text4 = test.getDocumentAsTxt(uri4);
//        assertEquals(text4, "Rockies, they aren't just great, they are amazing. Are.");
//        String text8 = test.getDocumentAsTxt(uri8);
//        assertNull(text8);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri6);
//
//
//    }
//
//
//
//    @Test
//    public void maxByteCount() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        test.setMaxDocumentBytes(4000);
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        List<String> list = test.search("below");
//        assertEquals(0, list.size());
//        Set<URI> set = test.deleteAllWithPrefix("ar");
//        assertEquals(5, set.size());
//        String string = test.getDocumentAsTxt(uri7);
//        assertNull(string);
//        List<String> stringList = test.search("are");
//        assertEquals(0, stringList.size());
//        test.undo();
//        List<byte[]> list1 = test.searchPDFs("are");
//        assertEquals(4, list1.size());
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri5);
//
//    }
//
//    @Test
//    public void heapDeleteWithUndosAndMaxByte() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        test.setMaxDocumentBytes(4000);
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.TXT);
//        String text5 = test.getDocumentAsTxt(uri5);
//        assertEquals("Avalanche, I don't really care for because I don't follow Hockey.", text5);
//        test.undo(uri8);
//        String text4 = test.getDocumentAsTxt(uri4);
//        assertEquals(text4, "Rockies, they aren't just great, they are amazing. Are.");
//        String text8 = test.getDocumentAsTxt(uri8);
//        assertNull(text8);
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri7);
//    }
//
//
//    @Test
//    public void heapDeleteWithMaxByteOverride() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl(null);
//        test.setMaxDocumentBytes(3600);
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        String text2 = test.getDocumentAsTxt(uri2);
//        assertEquals("Broncos, isn't that an amazing team", text2);
//        test.putDocument(inputStream5, uri, DocumentStore.DocumentFormat.PDF);
//        String string3 = test.getDocumentAsTxt(uri3);
//        assertEquals(string3, "Nuggets, my my, aren't they great to watch");
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri4);
//    }
//
//    @Test
//    public void setMaxAfter2() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.setMaxDocumentCount(4);
//        List list1 = test.search("are");
//        assertEquals(3, list1.size());
//        test.setMaxDocumentCount(2);
//        test.undo();
//        List list2 = test.search("are");
//        assertEquals(2, list2.size());
//        test.deserializeDoc(uri8);
//        test.deserializeDoc(uri7);
//
//    }
//
//    @Test
//    public void setMaxAfter3() throws  IOException{
//        DocumentStoreImpl test = new DocumentStoreImpl();
//
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        //test.setMaxDocumentCount(4);
//        Set list1 = test.deleteAll("are");
//        assertEquals(2, list1.size());
//        test.setMaxDocumentCount(1);
//        test.undo();
//        List list2 = test.search("are");
//        assertEquals(2, list2.size());
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri8);
//
//
//
//    }
//
//
//    @Test
//    public void undoTestWithMax() throws  IOException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        test.setMaxDocumentBytes(4000);
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        InputStream inputStream71 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        int number = test.getDocument(uri).getDocumentTextHashCode();
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.deleteDocument(uri7);
//        assertNull(test.getDocument(uri7));
//        test.undo(uri8);
//        assertNull(test.getDocument(uri8));
//        test.undo();
//        assertEquals(test.getDocumentAsTxt(uri7), "Yankees are the most hated team in all of sports.");
//        int hashcode1 = test.putDocument(inputStream71, uri, DocumentStore.DocumentFormat.PDF);
//        assertEquals(number, hashcode1);
//        test.undo(uri);
//        assertEquals("Denver is my favorite team", test.getDocument(uri).getDocumentAsTxt());
//        test.deserializeDoc(uri);
//        test.deserializeDoc(uri2);
//        test.deserializeDoc(uri3);
//        test.deserializeDoc(uri4);
//        test.deserializeDoc(uri6);
//        test.deserializeDoc(uri5);
//        test.deserializeDoc(uri7);
//        test.deserializeDoc(uri8);
//    }
//
//    @Test
//    public void undoTestWithMax2() throws  IOException {
//        DocumentStoreImpl test = new DocumentStoreImpl();
//        test.setMaxDocumentCount(4);
//        File file = pdfGenerator("file", "Denver is my favorite team");
//        InputStream inputStream = new FileInputStream(file);
//        URI uri = file.toURI();
//        File file2 = pdfGenerator("file2", "Broncos, isn't that an amazing team");
//        URI uri2 = file2.toURI();
//        InputStream inputStream2 = new FileInputStream(file2);
//        File file3 = pdfGenerator("file3", "Nuggets, my my, aren't they great to watch");
//        URI uri3 = file3.toURI();
//        InputStream inputStream3 = new FileInputStream(file3);
//        File file4 = pdfGenerator("file4", "Rockies, they aren't just great, they are amazing. Are.");
//        URI uri4 = file4.toURI();
//        InputStream inputStream4 = new FileInputStream(file4);
//        File file5 = pdfGenerator("file5", "Avalanche, I don't really care for because I don't follow Hockey.");
//        URI uri5 = file5.toURI();
//        InputStream inputStream5 = new FileInputStream(file5);
//        File file6 = pdfGenerator("file6", "Knicks are the worst team in the NBA. They just suck.");
//        URI uri6 = file6.toURI();
//        InputStream inputStream6 = new FileInputStream(file6);
//        File file7 = pdfGenerator("file7", "Yankees are the most hated team in all of sports. ");
//        URI uri7 = file7.toURI();
//        InputStream inputStream7 = new FileInputStream(file7);
//        InputStream inputStream71 = new FileInputStream(file7);
//        File file8 = pdfGenerator("file8", "Mets are just Yankee wannabees. I don't know how they aren't as good.");
//        URI uri8 = file8.toURI();
//        InputStream inputStream8 = new FileInputStream(file8);
//        test.putDocument(inputStream, uri, DocumentStore.DocumentFormat.PDF);
//        int number = test.getDocument(uri).getDocumentTextHashCode();
//        test.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream5, uri5, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream6, uri6, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream7, uri7, DocumentStore.DocumentFormat.PDF);
//        test.putDocument(inputStream8, uri8, DocumentStore.DocumentFormat.PDF);
//        test.searchPDFsByPrefix("Denver");
//        assertNull(test.getDocument(uri5));
//
//    }
//
//
//
//
//}
//

package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.Utils;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class DocumentStoreImplTest {

    //variables to hold possible values for doc1
    private URI uri1;
    private String txt1;
    private byte[] pdfData1;
    private String pdfTxt1;

    //variables to hold possible values for doc2
    private URI uri2;
    private String txt2;
    private byte[] pdfData2;
    private String pdfTxt2;

    //variables to hold possible values for doc3
    private URI uri3;
    private String txt3;
    private byte[] pdfData3;
    private String pdfTxt3;

    //variables to hold possible values for doc4
    private URI uri4;
    private String txt4;
    private byte[] pdfData4;
    private String pdfTxt4;

    private int bytes1;
    private int bytes2;
    private int bytes3;
    private int bytes4;

    private File baseDir;

    private String updateAddition;

    @Before
    public void init() throws Exception {
        //init possible values for doc1
        this.uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        this.txt1 = "This is the text of doc1 in plain text No fancy file format just plain old String Computer Headphones";
        this.pdfTxt1 = "This is some PDF text for doc1 hat tip to Adobe";
        this.pdfData1 = Utils.textToPdfData(this.pdfTxt1);

        //init possible values for doc2
        this.uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
        this.txt2 = "Text for doc2 A plain old String";
        this.pdfTxt2 = "PDF content for doc2 PDF format was opened in 2008";
        this.pdfData2 = Utils.textToPdfData(this.pdfTxt2);

        //init possible values for doc3
        this.uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
        this.txt3 = "Text for doc3 A plain old String";
        this.pdfTxt3 = "This is some PDF text for doc3 hat tip to Adobe";
        this.pdfData3 = Utils.textToPdfData(this.pdfTxt3);

        //init possible values for doc4
        this.uri4 = new URI("http://edu.yu.cs/com1320/project/doc4");
        this.txt4 = "Text for doc4 A plain old String";
        this.pdfTxt4 = "This is some PDF text for doc4 which is open source";
        this.pdfData4 = Utils.textToPdfData(this.pdfTxt4);

        this.bytes1 = this.pdfTxt1.getBytes().length + this.pdfData1.length;
        this.bytes2 = this.pdfTxt2.getBytes().length + this.pdfData2.length;
        this.bytes3 = this.pdfTxt3.getBytes().length + this.pdfData3.length;
        this.bytes4 = this.pdfTxt4.getBytes().length + this.pdfData4.length;

        //create baseDir
        this.baseDir = Files.createTempDirectory("stage5").toFile();

        this.updateAddition = "UPDATED-UPDATED";
    }
    @After
    public void cleanUp(){
        TestUtils.deleteTree(this.baseDir);
        this.baseDir.delete();
    }
    @Test
    public void testPutPdfDocumentNoPreviousDocAtURI(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        int returned = store.putDocument(new ByteArrayInputStream(this.pdfData1),this.uri1, DocumentStore.DocumentFormat.PDF);
        //TODO allowing for student following old API comment. To be changed for stage 2 to insist on following new comment.
        assertTrue(returned == 0 || returned == this.pdfTxt1.hashCode());
    }

    @Test
    public void testPutTxtDocumentNoPreviousDocAtURI(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        int returned = store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        //TODO allowing for student following old API comment. To be changed for stage 2 to insist on following new comment.
        assertTrue(returned == 0 || returned == this.txt1.hashCode());
    }

    @Test
    public void testPutDocumentWithNullArguments(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        try {
            store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), null, DocumentStore.DocumentFormat.TXT);
            fail("null URI should've thrown IllegalArgumentException");
        }catch(IllegalArgumentException e){}
        try {
            store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, null);
            fail("null format should've thrown IllegalArgumentException");
        }catch(IllegalArgumentException e){}
    }

    @Test
    public void testPutNewVersionOfDocumentPdf(){
        //put the first version
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        int returned = store.putDocument(new ByteArrayInputStream(this.pdfData1),this.uri1, DocumentStore.DocumentFormat.PDF);
        //TODO allowing for student following old API comment. To be changed for stage 2 to insist on following new comment.
        assertTrue(returned == 0 || returned == this.pdfTxt1.hashCode());
        assertEquals("failed to return correct pdf text",this.pdfTxt1,Utils.pdfDataToText(store.getDocumentAsPdf(this.uri1)));

        //put the second version, testing both return value of put and see if it gets the correct text
        returned = store.putDocument(new ByteArrayInputStream(this.pdfData2),this.uri1, DocumentStore.DocumentFormat.PDF);
        //TODO allowing for student following old API comment. To be changed for stage 2 to insist on following new comment.
        assertTrue("should return hashcode of old text",this.pdfTxt1.hashCode() == returned || this.pdfTxt2.hashCode() == returned);
        assertEquals("failed to return correct pdf text", this.pdfTxt2,Utils.pdfDataToText(store.getDocumentAsPdf(this.uri1)));
    }

    @Test
    public void testPutNewVersionOfDocumentTxt(){
        //put the first version
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        int returned = store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        //TODO allowing for student following old API comment. To be changed for stage 2 to insist on following new comment.
        assertTrue(returned == 0 || returned == this.txt1.hashCode());
        assertEquals("failed to return correct text",this.txt1,store.getDocumentAsTxt(this.uri1));

        //put the second version, testing both return value of put and see if it gets the correct text
        returned = store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        //TODO allowing for student following old API comment. To be changed for stage 2 to insist on following new comment.
        assertTrue("should return hashcode of old text",this.txt1.hashCode() == returned || this.txt2.hashCode() == returned);
        assertEquals("failed to return correct text",this.txt2,store.getDocumentAsTxt(this.uri1));
    }

    @Test
    public void testGetTxtDocAsPdf(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        int returned = store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        //TODO allowing for student following old API comment. To be changed for stage 2 to insist on following new comment.
        assertTrue(returned == 0 || returned == this.txt1.hashCode());
        assertEquals("failed to return correct pdf text",this.txt1,Utils.pdfDataToText(store.getDocumentAsPdf(this.uri1)));
    }

    @Test
    public void testGetTxtDocAsTxt(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        int returned = store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        //TODO allowing for student following old API comment. To be changed for stage 2 to insist on following new comment.
        assertTrue(returned == 0 || returned == this.txt1.hashCode());
        assertEquals("failed to return correct text",this.txt1,store.getDocumentAsTxt(this.uri1));
    }

    @Test
    public void testGetPdfDocAsPdf(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        int returned = store.putDocument(new ByteArrayInputStream(this.pdfData1),this.uri1, DocumentStore.DocumentFormat.PDF);
        //TODO allowing for student following old API comment. To be changed for stage 2 to insist on following new comment.
        assertTrue(returned == 0 || returned == this.pdfTxt1.hashCode());
        assertEquals("failed to return correct pdf text",this.pdfTxt1,Utils.pdfDataToText(store.getDocumentAsPdf(this.uri1)));
    }

    @Test
    public void testGetPdfDocAsTxt(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        int returned = store.putDocument(new ByteArrayInputStream(this.pdfData1),this.uri1, DocumentStore.DocumentFormat.PDF);
        //TODO allowing for student following old API comment. To be changed for stage 2 to insist on following new comment.
        assertTrue(returned == 0 || returned == this.pdfTxt1.hashCode());
        assertEquals("failed to return correct text",this.pdfTxt1,store.getDocumentAsTxt(this.uri1));
    }

    @Test
    public void testDeleteDoc(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1),this.uri1, DocumentStore.DocumentFormat.PDF);
        store.deleteDocument(this.uri1);
        assertEquals("calling get on URI from which doc was deleted should've returned null", null, store.getDocumentAsPdf(this.uri1));
    }

    @Test
    public void testDeleteDocReturnValue(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1),this.uri1, DocumentStore.DocumentFormat.PDF);
        //should return true when deleting a document
        assertEquals("failed to return true when deleting a document",true,store.deleteDocument(this.uri1));
        //should return false if I try to delete the same doc again
        assertEquals("failed to return false when trying to delete that which was already deleted",false,store.deleteDocument(this.uri1));
        //should return false if I try to delete something that was never there to begin with
        assertEquals("failed to return false when trying to delete that which was never there to begin with",false,store.deleteDocument(this.uri2));
    }

    @Test
    public void stage3Search(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);

        List<String> results = store.search("plain");
        assertEquals("expected 2 matches, only received " + results.size(),2,results.size());
        results = store.search("missing");
        assertEquals("expected 0 matches, received " + results.size(),0,results.size());
    }
    @Test
    public void stage3SearchPDFs(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1),this.uri1, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData2),this.uri2, DocumentStore.DocumentFormat.PDF);

        List<byte[]> results = store.searchPDFs("pdf");
        assertEquals("expected 2 matches, only received " + results.size(),2,results.size());
        results = store.searchPDFs("missing");
        assertEquals("expected 0 matches, received " + results.size(),0,results.size());
    }

    @Test
    public void stage3DeleteAll(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        //search, get results
        List<String> results = store.search("plain");
        assertEquals("expected 2 matches, only received " + results.size(),2,results.size());
        //delete all, get no matches
        store.deleteAll("plain");
        results = store.search("plain");
        assertEquals("expected 0 matches, received " + results.size(),0,results.size());
    }

    @Test
    public void stage3SearchByPrefix(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        //search, get results
        List<String> results = store.searchByPrefix("str");
        assertEquals("expected 2 matches, only received " + results.size(),2,results.size());
        results = store.searchByPrefix("comp");
        assertEquals("expected 1 match, only received " + results.size(),1,results.size());
        results = store.searchByPrefix("doc2");
        assertEquals("expected 1 match, only received " + results.size(),1,results.size());
        results = store.searchByPrefix("blah");
        assertEquals("expected 0 match, received " + results.size(),0,results.size());
    }
    @Test
    public void stage3SearchPDFsByPrefix(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1),this.uri1, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData2),this.uri2, DocumentStore.DocumentFormat.PDF);
        //search, get results
        List<byte[]> results = store.searchPDFsByPrefix("pd");
        assertEquals("expected 2 matches, only received " + results.size(),2,results.size());
        results = store.searchPDFsByPrefix("ado");
        assertEquals("expected 1 match, only received " + results.size(),1,results.size());
        results = store.searchPDFsByPrefix("blah");
        assertEquals("expected 0 match, received " + results.size(),0,results.size());
    }

    @Test
    public void stage3DeleteAllWithPrefix(){
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1),this.uri1, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData2),this.uri2, DocumentStore.DocumentFormat.PDF);
        //search, get results
        List<byte[]> results = store.searchPDFsByPrefix("pd");
        assertEquals("expected 2 matches, only received " + results.size(),2,results.size());
        //delete all starting with pd
        store.deleteAllWithPrefix("pd");
        //search again, should be empty
        results = store.searchPDFsByPrefix("pd");
        assertEquals("expected 0 matches, received " + results.size(),0,results.size());
    }

    @Test
    public void stage3TestSearchByKeyword() {
        //put the four docs into the doc store
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData2), this.uri2, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData3), this.uri3, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData4), this.uri4, DocumentStore.DocumentFormat.PDF);
        //search by keyword
        List<String> results = store.search("adobe");
        assertEquals("search should've returned 2 results",2,results.size());
        //make sure we have the correct two documents
        boolean found1, found3;
        found1 = found3 = false;
        String lower1 = this.pdfTxt1.toLowerCase();
        String lower3 = this.pdfTxt3.toLowerCase();
        for(String txt:results){
            if(txt.toLowerCase().equals(lower1)) {
                found1 = true;
            }else if(txt.toLowerCase().equals(lower3)){
                found3 = true;
            }
        }
        assertTrue("should've found doc1 and doc3",found1 && found3);
    }

    @Test
    public void stage3TestSearchByPrefix() {
        //put the four docs into the doc store
        DocumentStore store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData2), this.uri2, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData3), this.uri3, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData4), this.uri4, DocumentStore.DocumentFormat.PDF);
        //search by prefix
        List<String> results = store.searchByPrefix("ha");
        assertEquals("search should've returned 2 results",2,results.size());
        //make sure we have the correct two documents
        boolean found1, found3;
        found1 = found3 = false;
        String lower1 = this.pdfTxt1.toLowerCase();
        String lower3 = this.pdfTxt3.toLowerCase();
        for(String txt:results){
            if(txt.toLowerCase().equals(lower1)) {
                found1 = true;
            }else if(txt.toLowerCase().equals(lower3)){
                found3 = true;
            }
        }
        assertTrue("should've found doc1 and doc3",found1 && found3);
    }

    @Test
    public void stage3TestDeleteAllByKeyword() {
        //put the four docs into the doc store
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData2), this.uri2, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData3), this.uri3, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData4), this.uri4, DocumentStore.DocumentFormat.PDF);
        //delete by keyword
        store.deleteAll("adobe");
        //search by keyword
        List<String> results = store.search("adobe");
        assertEquals("search should've returned 0 results",0,results.size());
        //make sure the correct two documents were deleted
        assertNull("doc1 should've been deleted",store.getDocument(this.uri1));
        assertNull("doc3 should've been deleted",store.getDocument(this.uri3));
        //make sure the other two documents were NOT deleted
        assertNotNull("doc2 should NOT been deleted",store.getDocument(this.uri2));
        assertNotNull("doc4 should NOT been deleted",store.getDocument(this.uri4));
    }

    @Test
    public void stage3TestDeleteAllByPrefix() {
        //put the four docs into the doc store
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData2), this.uri2, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData3), this.uri3, DocumentStore.DocumentFormat.PDF);
        store.putDocument(new ByteArrayInputStream(this.pdfData4), this.uri4, DocumentStore.DocumentFormat.PDF);
        String prefix = "ha";
        //delete by prefix
        store.deleteAllWithPrefix(prefix);
        //search by keyword
        List<String> results = store.searchByPrefix(prefix);
        assertEquals("search should've returned 0 results",0,results.size());
        //make sure the correct two documents were deleted
        assertNull("doc1 should've been deleted",store.getDocument(this.uri1));
        assertNull("doc3 should've been deleted",store.getDocument(this.uri3));
        //make sure the other two documents were NOT deleted
        assertNotNull("doc2 should NOT been deleted",store.getDocument(this.uri2));
        assertNotNull("doc4 should NOT been deleted",store.getDocument(this.uri4));
    }

    @Test
    public void stage4TestNoUpdateDocLastUseTimeOnProtectedGet(){
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        Document doc = store.getDocument(this.uri1);
        long first = doc.getLastUseTime();
        doc = store.getDocument(this.uri1);
        long second = doc.getLastUseTime();
        //was last use time updated on the put?
        assertTrue("last use time should NOT be changed when the protected DocStore.getDoc method is called", first == second);
    }

    @Test
    public void stage4TestUpdateDocLastUseTimeOnPut(){
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        long before = System.nanoTime();
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        Document doc = store.getDocument(this.uri1);
        //was last use time updated on the put?
        assertTrue("last use time should be after the time at which the document was put", before < doc.getLastUseTime());
    }
    @Test
    public void stage4TestUpdateDocLastUseTimeOnOverwrite(){
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        //was last use time updated on the put?
        long before = System.nanoTime();
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        Document doc = store.getDocument(this.uri1);
        assertTrue("last use time should be after the time at which the document was put", before < doc.getLastUseTime());
        before = System.nanoTime();
        //was last use time updated on overwrite?
        store.putDocument(new ByteArrayInputStream(this.pdfData2), this.uri1, DocumentStore.DocumentFormat.PDF);
        Document doc2 = store.getDocument(this.uri1);
        assertTrue("last use time should be after the time at which the document was overwritten", before < doc2.getLastUseTime());
    }

    @Test
    public void stage4TestUpdateDocLastUseTimeOnSearch(){
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        long before = System.nanoTime();
        //this search should return the contents of the doc at uri1
        List<String> results = store.search("pdf");
        Document doc = store.getDocument(this.uri1);
        //was last use time updated on the search?
        assertTrue("last use time should be after the time at which the document was put", before < doc.getLastUseTime());
    }
    @Test
    public void stage4TestUpdateDocLastUseTimeOnSearchByPrefix(){
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        long before = System.nanoTime();
        //this search should return the contents of the doc at uri1
        List<String> results = store.searchByPrefix("pdf");
        Document doc = store.getDocument(this.uri1);
        //was last use time updated on the searchByPrefix?
        assertTrue("last use time should be after the time at which the document was put", before < doc.getLastUseTime());
    }
    @Test
    public void stage4TestUpdateDocLastUseTimeOnSearchPDFs(){
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        long before = System.nanoTime();
        //this search should return the contents of the doc at uri1
        List<byte[]> results = store.searchPDFs("pdf");
        Document doc = store.getDocument(this.uri1);
        //was last use time updated on the searchPDFs?
        assertTrue("last use time should be after the time at which the document was put", before < doc.getLastUseTime());
    }
    @Test
    public void stage4TestUpdateDocLastUseTimeOnSearchPDFsByPrefix(){
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        store.putDocument(new ByteArrayInputStream(this.pdfData1), this.uri1, DocumentStore.DocumentFormat.PDF);
        long before = System.nanoTime();
        //this search should return the contents of the doc at uri1
        List<byte[]> results = store.searchPDFsByPrefix("pdf");
        Document doc = store.getDocument(this.uri1);
        //was last use time updated on the searchPDFs?
        assertTrue("last use time should be after the time at which the document was put", before < doc.getLastUseTime());
    }

    /**
     * ***************************************************************************************************************
     * ***************************************************************************************************************
     * ***********************************************STAGE 5 TESTS***************************************************
     * ***************************************************************************************************************
     * ***************************************************************************************************************
     */

    private void checkContents(String errorMsg, String contents,String expected){
        assertNotNull(errorMsg + ": contents were null",contents);
        assertTrue(errorMsg + ": expected content not found",contents.toLowerCase().indexOf(expected.toLowerCase()) >= 0);
    }

    //in each of the tests below, assert as a precondtion that whatever should be on disk is, and whatever should be in memory is

    //test1a:
    // 1) put docA which didn't exist, and thus causes docB to be written to disk due to reaching MAX DOC COUNT
    // 2) get docA which was on disk, thus going over DOCUMENT COUNT limit and causing docB to be written to disk
    @Test
    public void stage5PushToDiskViaMaxDocCount() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        store.setMaxDocumentCount(2);
        pushAboveMaxViaPutNew(store);
    }

    private void pushAboveMaxViaPutNew(DocumentStoreImpl store) throws IOException{
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        Document doc1 = store.getDocument(this.uri1);
        Document doc2 = store.getDocument(this.uri2);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);

        //at this point, 2 and 3 should be in memory, and 1 should be on disk, pushed out when doc3 was put
        String doc1Str = TestUtils.getContents(this.baseDir,this.uri1);
        checkContents("doc1 should've been on disk, but was not",doc1Str,this.txt1);
        assertNotNull("doc2 should be in memory",store.getDocument(this.uri2));
        assertNotNull("doc3 should be in memory",store.getDocument(this.uri3));
        assertNull("doc2 should NOT have been on disk",TestUtils.getContents(this.baseDir,this.uri2));
        assertNull("doc3 should NOT have been on disk",TestUtils.getContents(this.baseDir,this.uri3));
        //make sure that when doc1 is requested, it is NOT the same object as doc1 above, which was gotten BEFORE it was kicked out of memory
        //this search should bring doc1 back into memory and push doc2 out to disk
        store.search("doc1");
        Document doc1v2 = store.getDocument(this.uri1);
        assertTrue("the original doc1 object should NOT have been returned - should be a different object in memory now",TestUtils.equalButNotIdentical(doc1,doc1v2));

        //check that doc2 is now on disk, but 1 and 3 are in memory
        String doc2Str = TestUtils.getContents(this.baseDir,this.uri2);
        checkContents("doc2 should've been on disk, but was not",doc2Str,this.txt2);
        assertNull("doc1 should NOT have been on disk",TestUtils.getContents(this.baseDir,this.uri1));
        assertNull("doc3 should NOT have been on disk",TestUtils.getContents(this.baseDir,this.uri3));

        //make sure that when doc2 is requested, it is NOT the same object as docs above, which was gotten BEFORE it was kicked out of memory
        //this search should bring doc2 back into memory
        store.search("doc2");
        Document doc2v2 = store.getDocument(this.uri2);
        assertTrue("the original doc2 object should NOT have been returned - should be a different object in memory now",TestUtils.equalButNotIdentical(doc2,doc2v2));
    }

    //test4a: reach MAX MEMORY and have some docs on disk. Delete docs in memory. Assert that no docs were brought in from disk. Get docs that are on disk, assert they are back in memory and off disk.
    @Test
    public void stage5PushToDiskViaMaxDocCountBringBackInViaDelete() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        store.setMaxDocumentCount(2);
        deleteDocInMemoryBringInDocFromDisk(store);
    }

    /**
     * This method assumes only 2 docs fit in memory for whatever reason. It does the following:
     * 1) put docs1, doc2, and then doc3
     * 2) assert that doc1 is NOT in memory and IS on disk, that doc2 and doc3 ARE in memory
     * 3) deletes doc3, making room in memory for doc1
     * 4) assert that doc1 is still NOT in memory even though doc3 was deleted
     * 5) do a search that brings doc1 back into memory
     * 6) assert that doc2 is still in memory and doc1 is back in memory
     * @param store
     * @throws IOException
     */
    private void deleteDocInMemoryBringInDocFromDisk(DocumentStoreImpl store) throws IOException{
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        Document doc1 = store.getDocument(this.uri1);
        Document doc2 = store.getDocument(this.uri2);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);

        //at this point, 2 and 3 should be in memory, and 1 should be on disk, pushed out when doc3 was put
        //assertNull("doc1 should NOT be in memory",store.getDocument(this.uri1));
        String doc1Str = TestUtils.getContents(this.baseDir,this.uri1);
        checkContents("doc1 should've been on disk, but was not",doc1Str,this.txt1);
        assertNotNull("doc2 should be in memory",store.getDocument(this.uri2));
        assertNotNull("doc3 should be in memory",store.getDocument(this.uri3));
        assertNull("doc2 should NOT have been on disk",TestUtils.getContents(this.baseDir,this.uri2));
        assertNull("doc3 should NOT have been on disk",TestUtils.getContents(this.baseDir,this.uri3));

        //delete doc3, making room for doc1; assert that doc3 is gone but doc1 still not in memory
        store.deleteDocument(this.uri3);
        //assertNull("doc3 should be gone/deleted",store.getDocument(this.uri3));
        // assertNull("doc1 should STILL not be in memory",store.getDocument(this.uri1));

        //do a search that brings doc1 back into memory, assert that doc2 is still unaffected and doc1 is back in memory
        store.search("doc1");
        assertNotNull("doc1 should be back in memory",store.getDocument(this.uri1));
        assertNull("doc1 should have been removed from disk",TestUtils.getContents(this.baseDir,this.uri1));
        assertTrue("doc1 should NOT be the same exact object in memory as earlier - a new object should've been created  when deserializing",TestUtils.equalButNotIdentical(doc1,store.getDocument(this.uri1)));
        assertFalse("doc2 should still be the same exact object in memory",TestUtils.equalButNotIdentical(doc2,store.getDocument(this.uri2)));
    }


    //test5a: undo a delete which causes doc store to go over MAX MEMORY, causing docs to be written to disk. Assert docs being in memory and on disk as pre/post conditions.
    @Test
    public void stage5PushToDiskViaMaxDocCountViaUndoDelete() throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(this.baseDir);
        store.setMaxDocumentCount(2);
        overLimitViaUndo(store);
    }

    private void overLimitViaUndo(DocumentStoreImpl store) throws IOException{
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        Document doc1 = store.getDocument(this.uri1);
        Document doc2 = store.getDocument(this.uri2);
        //delete doc2, making room for doc3
        store.deleteDocument(this.uri2);
        //put doc 3
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        //at this point, 1 and 3 should be in memory, and 2 should be gone
        assertNotNull("doc3 should be in memory",store.getDocument(this.uri3));
        assertNotNull("doc1 should be in memory",store.getDocument(this.uri1));
        //assertNull("doc2 should be null because it was deleted",store.getDocument(this.uri2));
        //undo the deletion of doc2, which should push doc1 out to disk. doc2 and doc3 should be in memory
        store.undo(this.uri2);
        //assertNull("doc1 should NOT be in memory",store.getDocument(this.uri1));
        String doc1Str = TestUtils.getContents(this.baseDir,this.uri1);
        checkContents("doc1 should've been written out to disk, but was not",doc1Str,this.txt1);
        assertNull("doc2 should NOT be on disk",TestUtils.getContents(this.baseDir,this.uri2));
        assertNotNull("doc2 should be in memory",store.getDocument(this.uri2));
        assertNull("doc3 should NOT be on disk",TestUtils.getContents(this.baseDir,this.uri3));
        assertNotNull("doc3 should be in memory",store.getDocument(this.uri3));
    }
}