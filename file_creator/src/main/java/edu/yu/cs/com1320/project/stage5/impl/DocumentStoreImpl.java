package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.BTree;
import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.BTreeImpl;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore {

    private BTree<URI, DocumentImpl> bTree;
    private StackImpl<Undoable> stackCommand;
    private HashMap<URI, StackImpl<DocumentImpl>> deletedHashTable = new HashMap<>();
    private TrieImpl<URI> trie = new TrieImpl<>();
    private MinHeapImpl<FakeDocument> heap = new MinHeapImpl<>();
    private ArrayList<FakeDocument> memoryList = new ArrayList<>();
    private HashSet<URI> set = new HashSet<>();
    private long startTime;
    private long tempTime = 0;
    private int documentMaxBytes = Integer.MAX_VALUE;
    private int maxDocs = Integer.MAX_VALUE;
    private int byteCount = 0;
    private int docCount = 0;
    private PersistenceManager pm;



    private class FakeDocument implements Comparable<FakeDocument>{

        private URI uri;

        protected FakeDocument(URI uri){
            this.uri = uri;
        }

        protected URI getUri(){
            return uri;
        }




        @Override
        public int compareTo(FakeDocument o) {
            if (bTree.get(uri).getLastUseTime() - bTree.get(o.getUri()).getLastUseTime() > 0){
                return 1;
            } else if (bTree.get(uri).getLastUseTime() - bTree.get(o.getUri()).getLastUseTime() < 0){
                return -1;
            } else {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FakeDocument that = (FakeDocument) o;
            return Objects.equals(uri, that.uri);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uri);
        }

        protected int getByteNumber() {
            return docByteNumber(bTree.get(uri));
        }

    }



    public DocumentStoreImpl(File baseDir){

        this.stackCommand = new StackImpl<Undoable>();
        startTime = System.nanoTime();
        bTree = new BTreeImpl<URI, DocumentImpl>();
        pm = new DocumentPersistenceManager(baseDir);
        bTree.setPersistenceManager(pm);
        try {
            bTree.put(new URI(""), null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public DocumentStoreImpl(){
        this.stackCommand = new StackImpl<Undoable>();
        startTime = System.nanoTime();
        bTree = new BTreeImpl<URI, DocumentImpl>();
        pm = new DocumentPersistenceManager(null);
        bTree.setPersistenceManager(pm);
        try {
            bTree.put(new URI(""), null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private GenericCommand makePutLambda(URI uri1, DocumentImpl doc){

        Function<URI, Boolean> putDoc =
                (uri) -> {
                    if (doc == null){
                        return true;
                    }
                    bTree.put(uri, doc);
                    docCount++;
                    byteCount += docByteNumber(doc);
                    rectifyHeap();
                    putTrie(doc);
                    doc.setLastUseTime(tempTime);
                    FakeDocument fd = new FakeDocument(uri);
                    heap.insert(fd);
                    set.add(uri);

                    return true;
                };
        GenericCommand command = new GenericCommand(uri1, putDoc);
        return command;

    }

    private GenericCommand makeDeleteLambda(URI uri1, DocumentImpl putDoc, DocumentImpl removeDoc){


        Function<URI, Boolean> deleteDoc =
                (uri) -> {
                    if (putDoc != null && removeDoc != null){
                        FakeDocument fd = new FakeDocument(uri);
                        bTree.put(uri, putDoc);
                        deleteDocFromTrie(removeDoc);
                        removeDoc.setLastUseTime(startTime);
                        long time = System.nanoTime();
                        putDoc.setLastUseTime(tempTime);

                        if (set.contains(uri)){
                            heap.reHeapify(fd);
                            byteCount -= docByteNumber(removeDoc);
                            docCount--;
                        } else {
                            heap.insert(fd);
                            set.add(uri);
                        }
                        byteCount += docByteNumber(putDoc);
                        docCount++;
                        rectifyHeap();

                        putTrie(putDoc);
                    } else {
                        if (removeDoc != null){
                            removeDoc.setLastUseTime(startTime);
                            FakeDocument fd = new FakeDocument(uri);
                            if (set.contains(uri)){
                                byteCount -= docByteNumber(removeDoc);
                                docCount--;
                                set.remove(uri);
                                heap.reHeapify(fd);
                                heap.removeMin();
                            }
                            rectifyHeap();
                            deleteDocFromTrie(removeDoc);
                        }
                        bTree.put(uri, null);

                    }

                    return true;


                };
        GenericCommand command = new GenericCommand(uri1, deleteDoc);
        return command;
    }




    @Override
    public void undo() throws IllegalStateException {
        if (stackCommand.size() == 0){
            throw new IllegalStateException();
        }
        tempTime = System.nanoTime();
        if (stackCommand.peek() instanceof GenericCommand){
            GenericCommand temp = (GenericCommand) stackCommand.peek();
            stackCommand.pop();
            temp.undo();
        }
        if (stackCommand.peek() instanceof CommandSet){
            CommandSet temp2 = (CommandSet) stackCommand.peek();
            stackCommand.pop();
            Set comSet = temp2.undoAll();
        }
        return;
    }

    @Override
    public void undo(URI uri) throws IllegalStateException {
        if (stackCommand.size() == 0){
            throw new IllegalStateException();
        }
        tempTime = System.nanoTime();
        int count = stackCommand.size();
        StackImpl<Undoable> tempStack = new StackImpl<>();
        for (int i = 0; i < count; i++){
            if (stackCommand.peek() instanceof CommandSet){
                CommandSet cmdSet = (CommandSet) stackCommand.peek();
                if (cmdSet.containsTarget(uri)){
                    cmdSet.undo(uri);
                    if(cmdSet.isEmpty()){
                        stackCommand.pop();
                    }
                    break;
                } else {
                    tempStack.push(cmdSet);
                    stackCommand.pop();
                    if (stackCommand.size() == 0){
                        throw new IllegalStateException();
                    }
                    continue;
                }
            } else {
                GenericCommand cmd = (GenericCommand) stackCommand.peek();
                if(cmd.getTarget().equals(uri)){

                    cmd.undo();
                    stackCommand.pop();
                    break;
                } else {
                    tempStack.push(cmd);
                    stackCommand.pop();
                    if (stackCommand.size() == 0){
                        throw new IllegalStateException();
                    }
                    continue;
                }
            }
        }
        while(tempStack.size() != 0){
            Undoable variable = tempStack.pop();
            stackCommand.push(variable);
        }
        return;
    }

    private int insertNullDoc(URI uri){
        DocumentImpl ret = bTree.get(uri);
        deleteDocFromTrie(ret);
        GenericCommand command = this.makePutLambda(uri, ret);
        stackCommand.push(command);
        FakeDocument fd = new FakeDocument(uri);
        if (set.contains(uri)){
            ret.setLastUseTime(startTime);
            heap.reHeapify(fd);
            heap.removeMin();
            docCount--;
            byteCount -= docByteNumber(ret);
        }
        DocumentImpl temp = bTree.put(uri, null);

        if (temp != null){
            return (temp.getDocumentTextHashCode());
        }
        return 0;
    }

    @Override
    public int putDocument(InputStream input, URI uri, DocumentFormat format) {
        if(uri == null || format == null){
            throw new IllegalArgumentException();
        }
        if (input == null && bTree.get(uri) != null){
            Document tem = bTree.get(uri);
            this.deleteDocument(uri);
            return tem.hashCode();
        }
        if (input == null){
            Document doc = bTree.put(uri, null);
            GenericCommand com = this.makePutLambda(uri, null);
            stackCommand.push(com);
            return 0;
        }
        try{
            switch (format) {
                case TXT:
                    byte[] inputBytes = byteArrayGenerator(input);
                    String text = new String(inputBytes);
                    int toReturn = this.txtIntoHashCode(uri, text, text.hashCode());
                    return toReturn;

                case PDF:
                    byte[] pdfBytes = byteArrayGenerator(input);
                    PDFTextStripper stripper = new PDFTextStripper();
                    PDDocument document = PDDocument.load(pdfBytes);
                    String txt = stripper.getText(document).trim();
                    int pdfHashCode = this.pdfIntoHashCode(uri, txt, txt.hashCode(), pdfBytes);
                    document.close();
                    return pdfHashCode;
                default:
                    return 0; }
        } catch (IOException e) { e.printStackTrace(); }
        return 0;
    }

    private int txtIntoHashCode(URI uri, String string, int hashCode){
        DocumentImpl test = bTree.get(uri);
        if (test == null) {
            try {

                pm.deserialize(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DocumentImpl doc = new DocumentImpl(uri, string, hashCode);
        FakeDocument fd = new FakeDocument(uri);
        Long time = System.nanoTime();
        doc.setLastUseTime(time);
        DocumentImpl doc2 = bTree.put(uri, doc);
        docCount++;
        byteCount += docByteNumber(doc);

        if (set.contains(uri)){
            byteCount -= docByteNumber(doc2);
            docCount--;
            heap.reHeapify(fd);
        } else {
            set.add(uri);
            heap.insert(fd);
        }

        rectifyHeap();

        GenericCommand command = this.makeDeleteLambda(uri, doc2, doc);
        stackCommand.push(command);
        putTrie(doc);
        if (doc2 == null){
            return 0;
        } else {
            deleteDocFromTrie(doc2);
            return doc2.getDocumentTextHashCode();
        }
    }

    private int pdfIntoHashCode(URI uri, String string, int hashCode, byte[] pdfBytes){
        DocumentImpl test = bTree.get(uri);
        if (test == null) {
            try {

                pm.deserialize(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bTree.get(uri);
        DocumentImpl doc = new DocumentImpl(uri, string, hashCode, pdfBytes);
        FakeDocument fd = new FakeDocument(uri);
        Long time = System.nanoTime();
        doc.setLastUseTime(time);
        DocumentImpl doc2 = bTree.put(uri, doc);
        docCount++;
        byteCount += docByteNumber(doc);

        if (set.contains(uri)){
            byteCount -= docByteNumber(doc2);
            docCount--;
            heap.reHeapify(fd);
        } else {
            set.add(uri);
            heap.insert(fd);
        }
        rectifyHeap();

        GenericCommand command = this.makeDeleteLambda(uri, doc2, doc);
        stackCommand.push(command);
        putTrie(doc);
        if (doc2 == null){
            return 0;
        } else {
            deleteDocFromTrie(doc2);
            return doc2.getDocumentTextHashCode();
        }
    }



    private void heapRemove(FakeDocument f){
        HashSet<FakeDocument> fdSet = new HashSet<>();
        fdSet.add(f);
        int tempCount = docCount;
        while (tempCount > 0){
            fdSet.add(heap.removeMin());
            tempCount--;
        }
        List<FakeDocument> list = new ArrayList<>(fdSet);
        while(!list.isEmpty()){
            FakeDocument temp = list.remove(0);
            heap.insert(temp);
            heap.reHeapify(temp);

        }
    }

    private void rectifyHeap(){
        while (byteCount > documentMaxBytes || docCount > maxDocs){
            FakeDocument temp = heap.removeMin();
            set.remove(temp.getUri());
            byteCount -= temp.getByteNumber();
            docCount--;
            try {
                bTree.moveToDisk(temp.getUri());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    private void deleteDocFromTrie(Document doc){
        String[] arrayText = doc.getDocumentAsTxt().replaceAll("[^A-Za-z0-9 ]", "").split(" ");
        for (int i = 0; i < arrayText.length; i++){
            trie.delete(arrayText[i], doc.getKey());
        }
    }

    private void putTrie(DocumentImpl doc){
        String text = doc.getDocumentAsTxt();
        String[] arrayText = text.split(" ");
        for (int i = 0; i < arrayText.length; i++){
            trie.put(arrayText[i], doc.getKey());
        }

    }

    private byte[] byteArrayGenerator(InputStream input) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = input.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            byte[] byteArray = buffer.toByteArray();
            return byteArray;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new byte[0];
    }

    private int docByteNumber(Document doc){
        byte[] bites = doc.getDocumentAsPdf();
        int biteSize = bites.length;
        String txt = doc.getDocumentAsTxt();
        byte[] stringBites = txt.getBytes();
        int stringSize = stringBites.length;
        return stringSize + biteSize;
    }

    protected void deserializeDoc(URI uri){
        bTree.get(uri);
    }

    @Override
    public byte[] getDocumentAsPdf(URI uri) {
        if( uri == null){
            throw new IllegalArgumentException();
        }
        DocumentImpl doc = bTree.get(uri);
        if(doc == null){
            return null;
        }
        doc.setLastUseTime(System.nanoTime());
        FakeDocument fd = new FakeDocument(doc.getKey());
        if (set.contains(uri)){
            heap.reHeapify(fd);
        } else {
            heap.insert(fd);
            set.add(uri);
            byteCount += docByteNumber(doc);
            docCount++;
        }
        rectifyHeap();
        return doc.getDocumentAsPdf();
    }

    @Override
    public String getDocumentAsTxt(URI uri) {
        if (uri == null){
            throw new IllegalArgumentException();
        }
        DocumentImpl doc = bTree.get(uri);
        if(doc == null){
            return null;
        }
        doc.setLastUseTime(System.nanoTime());
        FakeDocument fd = new FakeDocument(doc.getKey());
        if (set.contains(uri)){
            heap.reHeapify(fd);
        } else {
            heap.insert(fd);
            set.add(uri);
            byteCount += docByteNumber(doc);
            docCount++;
        }
        rectifyHeap();
        return doc.getDocumentAsTxt();
    }

    @Override
    public boolean deleteDocument(URI uri) {
        if( uri == null){
            throw new IllegalArgumentException();
        }
        if(bTree.get(uri) == null){
            GenericCommand delete = this.makePutLambda(uri, null);
            stackCommand.push(delete);
            return false;
        }
        if(bTree.get(uri) != null){
            DocumentImpl temp = bTree.get(uri);
            FakeDocument fd = new FakeDocument(uri);
            GenericCommand delete = this.makePutLambda(uri, temp);
            stackCommand.push(delete);
            temp.setLastUseTime(startTime);
            if (set.contains(uri)){
                set.remove(uri);
                heap.reHeapify(fd);
                heap.removeMin();
                docCount--;
                byteCount -= docByteNumber(temp);
            }
            bTree.put(uri, null);
            deleteDocFromTrie(temp);
            return true;
        }
        return false;
    }



    @Override
    public List<String> search(String keyword) {
        if (keyword == null){ return new ArrayList<>();}
        keyword.replaceAll("[^A-Za-z0-9 ]", "");
        Comparator<URI> com = new ComparesDocument(keyword, bTree);
        Long tempTime = System.nanoTime();
        List<URI> list = trie.getAllSorted(keyword, com);
        List<String> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i ++){

            URI uri = list.get(i);
            Document doc = bTree.get(uri);
            FakeDocument fd = new FakeDocument(uri);
            doc.setLastUseTime(tempTime);
            if (set.contains(uri)){
                heap.reHeapify(fd);
            } else {
                set.add(uri);
                heap.insert(fd);
                byteCount += docByteNumber(doc);
                docCount++;
            }
            returnList.add(doc.getDocumentAsTxt());
        }
        rectifyHeap();
        return returnList;
    }

    @Override
    public List<byte[]> searchPDFs(String keyword) {
        if (keyword == null){ return new ArrayList<>();}
        keyword = keyword.replaceAll("[^A-Za-z0-9 ]", "");
        Comparator<URI> com = new ComparesDocument(keyword, bTree);
        Long tempTime = System.nanoTime();
        List<URI> list = trie.getAllSorted(keyword, com);
        List<byte[]> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i ++){

            URI uri = list.get(i);
            Document doc = bTree.get(uri);
            FakeDocument fd = new FakeDocument(uri);
            doc.setLastUseTime(tempTime);
            if (set.contains(uri)){
                heap.reHeapify(fd);
            } else {
                set.add(uri);
                heap.insert(fd);
                byteCount += docByteNumber(doc);
                docCount++;
            }
            returnList.add(doc.getDocumentAsPdf());

        }
        rectifyHeap();
        return returnList;
    }

    @Override
    public List<String> searchByPrefix(String keywordPrefix) {
        if (keywordPrefix == null){ return new ArrayList<>();}
        String prefix = keywordPrefix.replaceAll("[^A-Za-z0-9 ]", "");
        Comparator<URI> com = new ComparesDocumentPrefixes(prefix, bTree);
        Long tempTime = System.nanoTime();
        List<URI> list = trie.getAllWithPrefixSorted(prefix, com);
        List<String> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i ++){

            URI uri = list.get(i);
            Document doc = bTree.get(uri);
            FakeDocument fd = new FakeDocument(uri);
            doc.setLastUseTime(tempTime);
            if (set.contains(uri)){
                heap.reHeapify(fd);
            } else {
                set.add(uri);
                heap.insert(fd);
                byteCount += docByteNumber(doc);
                docCount++;
            }
            returnList.add(doc.getDocumentAsTxt());

        }
        rectifyHeap();
        return returnList;
    }

    @Override
    public List<byte[]> searchPDFsByPrefix(String keywordPrefix) {
        if (keywordPrefix == null){ return new ArrayList<>();}
        String prefix = keywordPrefix.replaceAll("[^A-Za-z0-9 ]", "");
        Comparator<URI> com = new ComparesDocumentPrefixes(prefix, bTree);
        Long tempTime = System.nanoTime();
        List<URI> list = trie.getAllWithPrefixSorted(prefix, com);
        List<byte[]> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i ++){


            URI uri = list.get(i);
            Document doc = bTree.get(uri);
            FakeDocument fd = new FakeDocument(uri);
            doc.setLastUseTime(tempTime);
            if (set.contains(uri)){
                heap.reHeapify(fd);
            } else {
                set.add(uri);
                heap.insert(fd);
                byteCount += docByteNumber(doc);
                docCount++;
            }
            returnList.add(doc.getDocumentAsPdf());

        }
        rectifyHeap();
        return returnList;
    }

    @Override
    public Set<URI> deleteAll(String keyword) {
        if (keyword == null){ return new HashSet<>();}
        Set<URI> setTrue = trie.deleteAll(keyword);
        if (set.isEmpty()){
            CommandSet cmdset = new CommandSet();
            stackCommand.push(cmdset);
            return new HashSet<>();
        }
        CommandSet cmdset = new CommandSet();
        List<URI> list = new ArrayList<>(setTrue);
        Set<URI> returnSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++){
            URI uri = list.get(i);
            returnSet.add(uri);
            DocumentImpl doc = bTree.get(uri);
            GenericCommand com = this.makePutLambda(uri, doc);
            cmdset.addCommand(com);
            FakeDocument fd = new FakeDocument(uri);
            doc.setLastUseTime(startTime);
            if (set.contains(uri)){
                set.remove(uri);
                heap.reHeapify(fd);
                heap.removeMin();
                byteCount -= docByteNumber(doc);
                docCount--;
            }


            deleteDocFromTrie(doc);
            Document doc1 = bTree.put(uri, null);

        }
        stackCommand.push(cmdset);

        return returnSet;
    }

    @Override
    public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
        if (keywordPrefix == null){
            CommandSet cmdset = new CommandSet();
            stackCommand.push(cmdset);
            return new HashSet<>();}

        Set<URI> setTrue = trie.deleteAllWithPrefix(keywordPrefix);
        if (set == null){
            CommandSet cmdset = new CommandSet();
            stackCommand.push(cmdset);
            return  new HashSet<>();
        }
        if (set.isEmpty()){
            CommandSet cmdset = new CommandSet();
            stackCommand.push(cmdset);
            return new HashSet<>();
        }
        CommandSet cmdset = new CommandSet();
        List<URI> list = new ArrayList<>(setTrue);
        Set<URI> returnSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++){
            URI uri = list.get(i);
            returnSet.add(uri);

            DocumentImpl doc = bTree.get(uri);
            GenericCommand com = this.makePutLambda(uri, doc);
            cmdset.addCommand(com);
            FakeDocument fd = new FakeDocument(uri);
            doc.setLastUseTime(startTime);
            if (set.contains(uri)){
                set.remove(uri);
                heap.reHeapify(fd);
                heap.removeMin();
                byteCount -= docByteNumber(doc);
                docCount--;
            }
            deleteDocFromTrie(doc);
            Document doc1 = bTree.put(list.get(i), null);
        }
        stackCommand.push(cmdset);
        return returnSet;
    }

    @Override
    public void setMaxDocumentCount(int limit) {
        this.maxDocs = limit;
        if (docCount > maxDocs){
            rectifyHeap();
        }
    }

    @Override
    public void setMaxDocumentBytes(int limit) {
        this.documentMaxBytes = limit;
        if (byteCount > documentMaxBytes){
            rectifyHeap();
        }
    }

    protected Document getDocument(URI uri){
        if (uri == null){
            throw new IllegalArgumentException();
        }
        if(set.contains(uri)){
            return bTree.get(uri);
        } else {
            return null;
        }
    }
}

class ComparesDocument implements Comparator<URI> {

    private String keyword;
    private BTree<URI, Document> bTree;

    public ComparesDocument(String keyword, BTree bTree){

        this.keyword = keyword.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        this.bTree = bTree;
    }

    @Override
    public int compare(URI uri1, URI uri2) {
        Document doc1 = bTree.get(uri1);
        Document doc2 = bTree.get(uri2);
        int doc1Count = doc1.wordCount(this.keyword);
        int doc2Count = doc2.wordCount(this.keyword);
        return doc2Count - doc1Count;
    }
}

class ComparesDocumentPrefixes implements Comparator<URI> {

    private String keyword;
    private BTree<URI, Document> bTree;
    public ComparesDocumentPrefixes(String keyword, BTree bTree){

        this.keyword = keyword.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        this.bTree = bTree;
    }

    @Override
    public int compare(URI uri1, URI uri2) {
        Document doc1 = bTree.get(uri1);
        Document doc2 = bTree.get(uri2);
        String[] stringArray1 = doc1.getDocumentAsTxt().replaceAll("[^A-Za-z0-9 ]", "").toUpperCase().split(" ");
        String[] stringArray2 = doc2.getDocumentAsTxt().replaceAll("[^A-Za-z0-9 ]", "").toUpperCase().split(" ");
        int doc1Count = 0;
        int doc2Count = 0;
        for (int i = 0; i < stringArray1.length; i++){
            if (keyword.length() > stringArray1[i].length()){
                continue;
            }
            if (keyword.equals(stringArray1[i].substring(0, keyword.length()))){
                doc1Count++;
            }
        }
        for (int i = 0; i < stringArray2.length; i++){
            if (keyword.length() > stringArray2[i].length()){
                continue;
            }
            if (keyword.equals(stringArray2[i].substring(0, keyword.length()))){
                doc2Count++;
            }
        }
        return doc2Count - doc1Count;
    }
}


