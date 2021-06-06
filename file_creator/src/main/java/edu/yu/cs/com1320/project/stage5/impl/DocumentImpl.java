package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DocumentImpl implements Document {
    private final URI uri;
    private final int hashCode;
    private final String text;
    private final byte[] pdf;
    private long lastUseTime;
    private HashMap<String, Integer> hashTable = new HashMap<>();


    //If document being made is a pdf.
    public DocumentImpl(URI uri, String text, int txtHash, byte[] pdf){
        if (uri == null || text == null){
            throw new IllegalArgumentException("No null documents.");
        }
        this.text = text;
        this.hashCode = txtHash;
        this.uri = uri;
        this.pdf = pdf;
        this.insertHashtable(text);
    }

    //If document being made isn't a pdf.
    public DocumentImpl(URI uri, String text, int txtHash){
        if (uri == null || text == null){
            throw new IllegalArgumentException("No null documents.");
        }
        this.text = text;
        this.hashCode = txtHash;
        this.uri = uri;
        this.pdf = this.docToByte(text);
        this.insertHashtable(text);
    }

    private void insertHashtable(String string){
        String[] stringArray = string.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase().split(" ");
        for (int i = 0; i < stringArray.length; i++){
            Integer x = hashTable.get(stringArray[i]);
            if (x == null){
                hashTable.put(stringArray[i], 1);
            } else {
                hashTable.put(stringArray[i], (Integer) x + 1);
            }
        }
    }

    private byte[] docToByte(String text){
        PDDocument pdDocument = new PDDocument();
        PDPage page = new PDPage();
        pdDocument.addPage(page);
        ByteArrayOutputStream h = null;
        try {
            PDPageContentStream book = new PDPageContentStream(pdDocument, page);

            book.setFont(PDType1Font.TIMES_ROMAN, 11);
            book.beginText();
            book.newLineAtOffset(100, 550);
            book.showText(text);
            book.endText();
            book.close();
            h = new ByteArrayOutputStream();

            pdDocument.save(h);
            pdDocument.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return h.toByteArray();
    }



    public byte[] getDocumentAsPdf() {
        return pdf;


    }

    public String getDocumentAsTxt(){

        return text;
    }

    public int getDocumentTextHashCode(){

        return (hashCode);
    }

    public URI getKey(){
        return this.uri;
    }

    /**
     * how many times does the given word appear in the document?
     * @param word
     * @return the number of times the given words appears in the document
     */
    @Override
    public int wordCount(String word) {
        String text = word.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        Integer count = hashTable.get(text);
        if (count == null){
            return 0;
        } else {
            return (int) count;
        }
    }

    @Override
    public long getLastUseTime() {
        return lastUseTime;
    }

    @Override
    public void setLastUseTime(long timeInMilliseconds) {
        lastUseTime = timeInMilliseconds;
    }

    @Override
    public Map<String, Integer> getWordMap() {
        return hashTable;
    }

    @Override
    public void setWordMap(Map<String, Integer> wordMap) {
        hashTable = (HashMap<String, Integer>) wordMap;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentImpl document = (DocumentImpl) o;
        return Objects.equals(text, document.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public int compareTo(Document o) {
        if (this.getLastUseTime() - o.getLastUseTime() > 0){
            return 1;
        } else if (this.getLastUseTime() - o.getLastUseTime() < 0){
            return -1;
        } else {
            return 0;
        }
    }


}
