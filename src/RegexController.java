

public class RegexController {




    /**
     * Adds Leetspeak and replaces the Image sources with the HM-logo
     * @param in Text to modify
     * @return modiefyed text
     */
    public static String toLeet(String in){
      //add leet
      in =  in.replaceAll("MMIX","MM!}{");
      in =  in.replaceAll("Java","J4v4");
      in =  in.replaceAll("Computer","(om|Du7er");
      in =   in.replaceAll("RISC","R!5C");
      in =   in.replaceAll("CISC","(IS(");
      in =   in.replaceAll("Debugger","D4bu993r");
      in =   in.replaceAll("Informatik","!nfom47ik");
      in =   in.replaceAll("Student","5tud3nt");
      in =   in.replaceAll("Studentin","5tud3nt!n");
      in =   in.replaceAll("Studierende","5tud!r3nd3");
      in =   in.replaceAll("Windows","W!n|)ows");
      in =   in.replaceAll("Linux","|_inu><");
      //add image sources
      in =   in.replaceAll("<img.*>","<img src = \"https://upload.wikimedia.org/wikipedia/de/thumb/e/e8/Hochschule_Muenchen_Logo.svg/200px-Hochschule_Muenchen_Logo.svg.png\"  height=\"100\" width=\"200\">");

      return in;
    }
}
