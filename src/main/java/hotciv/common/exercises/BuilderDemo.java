package hotciv.common.exercises;

public class BuilderDemo {
  public static void main(String[] args) {
    System.out.println( "======= Demonstration of Builder =======");
    WordProcessor wp = new WordProcessor();

    // This code act as the client role that
    // creates the concrete builders and instruct
    // the director to construct objects.
    AsciiBuilder asciiBuilder;
    asciiBuilder = new AsciiBuilder();
    wp.construct(asciiBuilder);
    System.out.println( "--- The ASCII Builder output ---");
    System.out.println( asciiBuilder.getResult() );
    
    HTMLBuilder htmlBuilder;
    htmlBuilder = new HTMLBuilder();
    wp.construct(htmlBuilder);
    System.out.println( "--- The HTML Builder ---");
    System.out.println( htmlBuilder.getResult() );
 
//    CountBuilder countBuilder;
//    countBuilder = new CountBuilder();
//    wp.construct(countBuilder);
//    System.out.println( "--- Counting types ---");
//    System.out.println( "Sections   : "+countBuilder.getSectionCount() );
//    System.out.println( "Subcections: "+countBuilder.getSubSectionCount() );
//    System.out.println( "Paragraphs : "+countBuilder.getParagraphCount() );
  }
}

/** This class plays the director role of the builder pattern */
class WordProcessor {
  /** The document
    1. The Builder Pattern
    ===================
      1.1 Intent
        Separate the construction of a complex object 
        from its representation so that the same construction 
        process can create different representations.
      1.2 Problem
        (The problem goes here)

    is coded as named strings to avoid defining a large
    datastructure.
  */
  private String section = "The Builder Pattern";
  private String subSection1 = "Intent";
  private String paragraph1 = 
    "Separate the construction of a complex object\n"+
    "from its representation so that the same construction\n"+
    "process can create different representations.";
  private String subSection2 = "Problem";
  private String paragraph2 = "(The problem goes here)";

  public void construct(Builder builder) {
    /* a real constructor would iterate over a
     * data structure, here I have hardcoded the
     * document to keep the code small */
    builder.buildSection(section);
    builder.buildSubsection(subSection1);
    builder.buildParagraph(paragraph1);
    builder.buildSubsection(subSection2);
    builder.buildParagraph(paragraph2);
  }
}                         

/** This is the Builder role, the interface that
 * defines the parts that can be built */
interface Builder<T> {
  public void buildSection(String text);
  public void buildSubsection(String text);
  public void buildParagraph(String text);
  public T getResult();
}

/** A concrete builder implementing a ASCII format */
class AsciiBuilder implements Builder {
  private String result;
  int sectionCounter, subSectionCounter;
  public AsciiBuilder() {
    result = new String();
    sectionCounter = subSectionCounter = 0;
  }
  public void buildSection(String text) {
    sectionCounter++;
    result += ""+sectionCounter+". "+text+"\n";
    result += "=======================\n";
  }
  public void buildSubsection(String text) {
    subSectionCounter++;
    result += ""+sectionCounter+"."+subSectionCounter+" "+text+"\n";
  }
  public void buildParagraph(String text) {
    result += text + "\n"; 
  }
  public String getResult() {
    return result;
  }
}

/** A concrete builder implementing a HTML format */
class HTMLBuilder implements Builder {
  private String result;
  public HTMLBuilder() {
    result = new String();
  }
  public void buildSection(String text) {
    result += "<H1>"+text+"</H1>\n";
  }
  public void buildSubsection(String text) {
    result += "<H2>"+text+"</H2>\n";
  }
  public void buildParagraph(String text) {
    result += "<P>\n"+text + "\n</P>\n"; 
  }
  public String getResult() {
    return result;
  }
}

/** A concrete builder that simply counts parts */
//class CountBuilder implements Builder<Integer> {
//  private int section, subsection, paragraph;
//  public CountBuilder() {
//    section = subsection = paragraph = 0;
//  }
//
//  public void buildSection(String text) { section++; }
//  public void buildSubsection(String text) { subsection++; }
//  public void buildParagraph(String text) { paragraph++; }
//
//  public int getSectionCount() { return section; }
//  public int getSubSectionCount() { return subsection; }
//  public int getParagraphCount() { return paragraph; }
//}
