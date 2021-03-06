package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DocumentPersistenceManagerTest {

    private File baseDir;
    //variables to hold possible values for doc1
    private URI uri1;
    private String txt1;
    private Document doc1;
    //variables to hold possible values for doc2
    private URI uri2;
    private String txt2;
    private Document doc2;
    //variables to hold possible values for doc2
    private URI uri3;
    private String txt3;
    private Document doc3;

    @Before
    public void init()throws Exception{
        this.baseDir = Files.createTempDirectory("stage5").toFile();
        //init values for doc1
        this.uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        this.txt1 = "This is the text of doc1 in plain text No fancy file format just plain old String Computer Headphones.";
        this.doc1 = new DocumentImpl(this.uri1,this.txt1,this.txt1.hashCode());
        //init values for doc2
        this.uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
        this.txt2 = "Text for doc2 A plain old String";
        this.doc2 = new DocumentImpl(this.uri2,this.txt2,this.txt2.hashCode());
        //init values for doc3
        this.uri3 = new URI("http://cs.nyu.edu/datastructs/project/doc2");
        this.txt3 = "Text for NYU doc2 A plain old String";
        this.doc3 = new DocumentImpl(this.uri3,this.txt3,this.txt3.hashCode());
    }

    @After
    public void cleanUp(){
        TestUtils.deleteTree(this.baseDir);
        this.baseDir.delete();
    }

    @Test
    public void stage5TestSerializationPath()throws Exception{
        DocumentPersistenceManager dpm = new DocumentPersistenceManager(this.baseDir);
        dpm.serialize(this.uri1,this.doc1);
        assertTrue("file was not created where expected", TestUtils.uriToFile(this.baseDir,this.uri1).exists());
        dpm.serialize(this.uri2,this.doc2);
        assertTrue("file was not created where expected", TestUtils.uriToFile(this.baseDir,this.uri2).exists());
        dpm.serialize(this.uri3,this.doc3);
        assertTrue("file was not created where expected", TestUtils.uriToFile(this.baseDir,this.uri3).exists());
    }

    @Test
    public void stage5TestSerializationContent()throws Exception{
        DocumentPersistenceManager dpm = new DocumentPersistenceManager(this.baseDir);
        dpm.serialize(this.uri1,this.doc1);
        String contents = TestUtils.getContents(this.baseDir,this.uri1).toLowerCase();
        assertTrue("doc1 text contents not found in serialized file",contents.lastIndexOf(this.txt1.toLowerCase())>=0);

        dpm.serialize(this.uri2,this.doc2);
        contents = TestUtils.getContents(this.baseDir,this.uri2).toLowerCase();
        assertTrue("doc2 text contents not found in serialized file",contents.lastIndexOf(this.txt2.toLowerCase())>=0);

        dpm.serialize(this.uri3,this.doc3);
        contents = TestUtils.getContents(this.baseDir,this.uri3).toLowerCase();
        assertTrue("doc3 text contents not found in serialized file",contents.lastIndexOf(this.txt3.toLowerCase())>=0);
    }

    @Test
    public void stage5TestDeserialization()throws Exception{
        DocumentPersistenceManager dpm = new DocumentPersistenceManager(this.baseDir);
        //serialize all 3 documents
        dpm.serialize(this.uri1,this.doc1);
        dpm.serialize(this.uri2,this.doc2);
        dpm.serialize(this.uri3,this.doc3);
        TestUtils.equalButNotIdentical(this.doc1,dpm.deserialize(this.uri1));
        TestUtils.equalButNotIdentical(this.doc2,dpm.deserialize(this.uri2));
        TestUtils.equalButNotIdentical(this.doc3,dpm.deserialize(this.uri3));
    }
}

//package edu.yu.cs.com1320.project.stage5.impl;
//
//import edu.yu.cs.com1320.project.stage5.Document;
//import edu.yu.cs.com1320.project.stage5.PersistenceManager;
//import org.junit.Test;
//
//import java.io.*;
//import java.net.URI;
//import java.net.URISyntaxException;
//
//import static org.junit.Assert.*;
//
//public class DocumentPersistenceManagerTest {
//
//    @Test
//    public void shouldAnswerWithTrue() throws IOException, URISyntaxException {
//        DocumentPersistenceManager docIO = new DocumentPersistenceManager(null);
//        String txtDoc1 = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "TestDoc1.txt";
//        InputStream td1 = new ByteArrayInputStream(txtDoc1.getBytes());
//        URI uriTd1 = new URI("http://www.yu.com/documents/doc3");
//        Document doc = new DocumentImpl(uriTd1, "hello there how are you", "hello there how are you".hashCode());
//        System.out.println("hello there how are you".hashCode());
//        docIO.serialize(uriTd1, doc);
//        //System.out.println(docIO.serialize(doc));
//        Document y = docIO.deserialize(uriTd1);
//        System.out.println(y.getDocumentAsTxt());
//        System.out.println(y.getKey());
//        System.out.println(y.getDocumentTextHashCode());
//        assertEquals(uriTd1, y.getKey());
//
//    }
//
//    @Test
//    public void putDocWithBaseDirTest() throws URISyntaxException, IOException {
//        DocumentPersistenceManager test = new DocumentPersistenceManager(new File("//Users/danielzolty/Desktop/"));
//        String txt = "http://www.yu.edu/documents/dz";
//        URI uri = new URI(txt);
//        String st = "Superbowl champions - Broncos";
//        Document doc = new DocumentImpl(uri, st, st.hashCode());
//        test.serialize(uri, doc);
//    }
//
//    @Test
//    public void removeNullDocTest() throws URISyntaxException, IOException {
//        DocumentPersistenceManager test = new DocumentPersistenceManager(new File("//Users/danielzolty/Downloads/"));
//        String txt = "http://www.yu.edu/documents/dz";
//        URI uri = new URI(txt);
//        String st = "Superbowl champions - Broncos";
//        Document doc =  test.deserialize(uri);
//        assertNull(doc);
//
//    }
//
//
//
//}