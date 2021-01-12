/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Traitement;

/**
 *
 * @author Mohammed
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TraitementXml {

    public static void main(String argv[]) throws IOException {
        for (String l : OooutPut()) {
            if (l.split("-").length == 2) {
                System.out.println(l.split("-")[0] + " - " + l.split("-")[1]);
            }else
            System.out.println(l.split("-")[0] + " - " + l.split("-")[0]);

        }
    }
//        CreeInput("رحب الرئيس الأميركي المنتخب جو بايدن بقرار الرئيس دونالد ترامب عدم رغبته بحضور مراسم التنصيب، ووصف ترامب بأنه غير مؤهل للسلطة، محملا إياه مسؤولية اقتحام مبنى الكونغرس، ومؤكدا استعداده لاستلام الرئاسة");
//    }

    public TraitementXml() throws IOException {
    }

    public static List<String> OooutPut() {
//        CreeInput(MotTrait, myFile);

        List<String> diaclitise = new ArrayList();
        List<String> Ssstem = new ArrayList();
        List<Token> Tokens = new ArrayList();
        diaclitise.add("َ");
        diaclitise.add("ً");
        diaclitise.add("ُ");
        diaclitise.add("ٌ");
        diaclitise.add("ّ");
        diaclitise.add("ْ");
        diaclitise.add("ِ");
        diaclitise.add("ٍ");

        try {
//creating a constructor of file class and parsing an XML file  
            File file = new File("C:\\ProjectIL\\Resource\\sampleOutputFile.xml");
//an instance of factory that gives a document builder  
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file  
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("word");
// nodeList is not iterable, so we are using for loop  
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
//                Token t=new Token();
                Node node = nodeList.item(itr);
                String REs = "";
                System.out.println("\nNode Name :" + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    String mooot = eElement.getAttribute("word");
                    REs += mooot + "-";
                    NodeList nodeListSE = eElement.getElementsByTagName("morph_feature_set");
                    NodeList nodeListTok = eElement.getElementsByTagName("tokenized");
                    Element eElementDi = (Element) nodeListSE.item(0);

                    Node NOdeTook = nodeListTok.item(3);
                    Element eElementTok = (Element) NOdeTook;
                    NodeList nodeListTokFor = eElementTok.getElementsByTagName("tok");

                    String MotDiaclitise = normalize(eElementDi.getAttribute("diac"));
                    if (MotDiaclitise.isEmpty()) {
                        continue;
                    }
                    char[] caracterDiac = MotDiaclitise.toCharArray();

                    System.out.println("Diaclitise       :" + MotDiaclitise);
                    String MotDiaclitiseTrait = MotDiaclitise;
                    String Stem = "";
                    if (nodeListTokFor.getLength() <= 1) {
                        char[] caracterMot = MotDiaclitise.toCharArray();
                        List<String> carrMot = ConvertToList(caracterMot);
                        int LastDiaclitise = 0;
                        if (diaclitise.contains(carrMot.get(carrMot.size() - 1))) {
                            LastDiaclitise++;
                        }
                        if (diaclitise.contains(carrMot.get(carrMot.size() - 1)) && diaclitise.contains(carrMot.get(carrMot.size() - 2))) {
                            LastDiaclitise++;
                        }
                        for (int i = 0; i < carrMot.size() - LastDiaclitise; i++) {
                            Stem = Stem.concat(carrMot.get(i) + "");
                            System.out.println(carrMot.get(i) + "|");
                        }

                    } else {
                        String StemNDiac = "";
                        for (int itrTok = 0; itrTok < nodeListTokFor.getLength(); itrTok++) {

                            Node nodeTok = nodeListTokFor.item(itrTok);
                            Element eElementTokK = (Element) nodeTok;
                            String Formm = eElementTokK.getAttribute("form0");
                            char[] caracterFormm = Formm.toCharArray();
                            List<String> carr = ConvertToList(caracterFormm);

                            if (carr.get(0).equals("+")) {
                                MotDiaclitiseTrait = RemoveEnclitique(Formm, MotDiaclitiseTrait, diaclitise);
//                                t.setEncli(t.getEncli()+""+Formm.replaceAll("+",""));
                            } else if (carr.get(carr.size() - 1).equals("+")) {
                                MotDiaclitiseTrait = RemoveProclitique(Formm, MotDiaclitiseTrait, diaclitise);
//                                t.setProcl(Formm.replaceAll("+","")+""+t.getProcl());
                            } else {
                                StemNDiac = Formm;
                            }
                        }
                        Stem = MotDiaclitiseTrait;
                        char[] stemDiac = MotDiaclitiseTrait.toCharArray();
                        char[] stemNDiac = StemNDiac.toCharArray();
                        String test = "";

                        List<String> StemNoDia = ConvertToList(stemNDiac);

                        List<String> StemDia = ConvertToList(stemDiac);
                        List<String> StemDiaF = ConvertToList(stemDiac);
                        StemDia.removeAll(diaclitise);
                        for (String s : StemDiaF) {
                            if (diaclitise.contains(s)) {
                                test += s;
                            } else {
                                test += StemNoDia.get(0);
                                StemNoDia.remove(0);
                            }
                        }
                        if (!StemNoDia.isEmpty()) {

                            test += StemNoDia.get(0);
                            StemNoDia.remove(0);
                        }
                        Stem = test;
                        REs += Stem;
//                        t.setStem(Stem);
                    }
                    System.out.println("Steem == " + Stem);
//                    String stt = "";
//                    for (Character sCh : Stem.toCharArray()) {
//                        stt += (int) sCh + ",";
//                    }

                    Ssstem.add(REs);
//                    Tokens.add(t)
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Ssstem;
    }

    public static String normalize(String linee) {

        String line = linee.replaceAll("\\s+", " ");
        line = line.replaceAll("\\p{Punct}|[a-zA-Z]|[0-9]", "");
        line = line.replaceAll("(.)\\1{1,}", "$1");
        return line;
    }

    public static String RemoveProclitique(String Procl, String Mot, List<String> diaclitise) {
        char[] caracterProc = Procl.toCharArray();
        char[] caracterMot = Mot.toCharArray();

        List<String> carrProc = ConvertToList(caracterProc);
        List<String> carrMot = ConvertToList(caracterMot);
        carrProc.remove("+");

        while (!carrProc.isEmpty()) {
            String mm = carrProc.get(0);
            int index = Mot.indexOf(mm);

            if (mm.equals("ا")) {
                index = 0;
            }
            carrProc.remove(0);
            for (int i = 0; i <= index; i++) {
                carrMot.remove(0);
            }

            String Res = "";
            for (int i = 0; i < carrMot.size(); i++) {
                Res = Res.concat(carrMot.get(i) + "");
            }
            Mot = Res;
        }
        String Res = "";
        int LastDiaclitise = 0, DepuSDiaclitise = 0;
        if (diaclitise.contains(carrMot.get(0))) {
            DepuSDiaclitise++;
        }
        if (diaclitise.contains(carrMot.get(0)) && diaclitise.contains(carrMot.get(1))) {
            DepuSDiaclitise++;
        }
        if (diaclitise.contains(carrMot.get(carrMot.size() - 1))) {
            LastDiaclitise++;
        }
        if (diaclitise.contains(carrMot.get(carrMot.size() - 1)) && diaclitise.contains(carrMot.get(carrMot.size() - 2))) {
            LastDiaclitise++;
        }
        for (int i = DepuSDiaclitise; i < carrMot.size() - LastDiaclitise; i++) {
            Res = Res.concat(carrMot.get(i) + "");
        }
        return Res;

    }

    public static String RemoveEnclitique(String Proc, String Mot, List<String> diaclitise) {
        char[] caracterProc = Proc.toCharArray();
        char[] caracterMot = Mot.toCharArray();

        List<String> carrProc = ConvertToList(caracterProc);
        List<String> carrMot = ConvertToList(caracterMot);
        carrProc.remove("+");

        while (!carrProc.isEmpty()) {
            int kInd = carrProc.size() - 1;
            String mm = carrProc.get(kInd);
            int index = Mot.lastIndexOf(mm);
            carrProc.remove(kInd);
            for (int i = carrMot.size() - 1; i >= index; i--) {
                carrMot.remove(i);
            }

            String Res = "";
            for (int i = 0; i < carrMot.size(); i++) {
                Res = Res.concat(carrMot.get(i) + "");
            }
        }
        String Res = "";
        int LastDiaclitise = 0;
        if (diaclitise.contains(carrMot.get(carrMot.size() - 1))) {
            LastDiaclitise++;
        }
        if (diaclitise.contains(carrMot.get(carrMot.size() - 1)) && diaclitise.contains(carrMot.get(carrMot.size() - 2))) {
            LastDiaclitise++;
        }

        for (int i = 0; i < carrMot.size() - LastDiaclitise; i++) {
            Res = Res.concat(carrMot.get(i) + "");
        }
        return Res;

    }

    public static void CreeInput(char[] inn, String myFile) throws FileNotFoundException, IOException {
        String Header = " <?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<!--\n"
                + "  ~ Copyright (c) 2013. The Trustees of Columbia University in the City of New York.\n"
                + "  ~ The copyright owner has no objection to the reproduction of this work by anyone for\n"
                + "  ~ non-commercial use, but otherwise reserves all rights whatsoever.  For avoidance of\n"
                + "  ~ doubt, this work may not be reproduced, or modified, in whole or in part, for commercial\n"
                + "  ~ use without the prior written consent of the copyright owner.\n"
                + "  -->\n"
                + "\n"
                + "<madamira_input xmlns=\"urn:edu.columbia.ccls.madamira.configuration:0.1\">\n"
                + "    <madamira_configuration>\n"
                + "        <preprocessing sentence_ids=\"false\" separate_punct=\"true\" input_encoding=\"UTF8\"/>\n"
                + "        <overall_vars output_encoding=\"UTF8\" dialect=\"MSA\" output_analyses=\"TOP\" morph_backoff=\"NONE\"/>\n"
                + "        <requested_output>\n"
                + "            <req_variable name=\"PREPROCESSED\" value=\"true\" />\n"
                + "            <req_variable name=\"STEM\" value=\"true\" />\n"
                + "            <req_variable name=\"GLOSS\" value=\"false\" />\n"
                + "            <req_variable name=\"LEMMA\" value=\"false\" />\n"
                + "            <req_variable name=\"DIAC\" value=\"true\" />\n"
                + "            <req_variable name=\"ASP\" value=\"true\" />\n"
                + "            <req_variable name=\"CAS\" value=\"true\" />\n"
                + "            <req_variable name=\"ENC0\" value=\"true\" />\n"
                + "            <req_variable name=\"ENC1\" value=\"false\" />\n"
                + "            <req_variable name=\"ENC2\" value=\"false\" />\n"
                + "            <req_variable name=\"GEN\" value=\"true\" />\n"
                + "            <req_variable name=\"MOD\" value=\"true\" />\n"
                + "            <req_variable name=\"NUM\" value=\"true\" />\n"
                + "            <req_variable name=\"PER\" value=\"true\" />\n"
                + "            <req_variable name=\"POS\" value=\"true\" />\n"
                + "            <req_variable name=\"PRC0\" value=\"true\" />\n"
                + "            <req_variable name=\"PRC1\" value=\"true\" />\n"
                + "            <req_variable name=\"PRC2\" value=\"true\" />\n"
                + "            <req_variable name=\"PRC3\" value=\"true\" />\n"
                + "            <req_variable name=\"STT\" value=\"true\" />\n"
                + "            <req_variable name=\"VOX\" value=\"true\" />\n"
                + "            <req_variable name=\"BW\" value=\"false\" />\n"
                + "            <req_variable name=\"SOURCE\" value=\"false\" />\n"
                + "			<req_variable name=\"NER\" value=\"true\" />\n"
                + "			<req_variable name=\"BPC\" value=\"true\" />\n"
                + "        </requested_output>\n"
                + "        <tokenization>\n"
                + "            <scheme alias=\"ATB\" />\n"
                + "			<scheme alias=\"D3_BWPOS\" /> <!-- Required for NER -->\n"
                + "            <scheme alias=\"ATB4MT\" />\n"
                + "            <scheme alias=\"MyD3\">\n"
                + "				<!-- Same as D3 -->\n"
                + "				<scheme_override alias=\"MyD3\"\n"
                + "								 form_delimiter=\"\\u00B7\"\n"
                + "								 include_non_arabic=\"true\"\n"
                + "								 mark_no_analysis=\"false\"\n"
                + "								 token_delimiter=\" \"\n"
                + "								 tokenize_from_BW=\"false\">\n"
                + "					<split_term_spec term=\"PRC3\"/>\n"
                + "					<split_term_spec term=\"PRC2\"/>\n"
                + "					<split_term_spec term=\"PART\"/>\n"
                + "					<split_term_spec term=\"PRC0\"/>\n"
                + "					<split_term_spec term=\"REST\"/>\n"
                + "					<split_term_spec term=\"ENC0\"/>\n"
                + "					<token_form_spec enclitic_mark=\"+\"\n"
                + "									 proclitic_mark=\"+\"\n"
                + "									 token_form_base=\"WORD\"\n"
                + "									 transliteration=\"UTF8\">\n"
                + "						<normalization type=\"ALEF\"/>\n"
                + "						<normalization type=\"YAA\"/>\n"
                + "						<normalization type=\"DIAC\"/>\n"
                + "						<normalization type=\"LEFTPAREN\"/>\n"
                + "						<normalization type=\"RIGHTPAREN\"/>\n"
                + "					</token_form_spec>\n"
                + "				</scheme_override>\n"
                + "			</scheme>\n"
                + "        </tokenization>\n"
                + "    </madamira_configuration>\n"
                + "\n"
                + "\n"
                + "    <in_doc id=\"ExampleDocument\">\n";

        String footer = "       \n"
                + "    </in_doc>\n"
                + "\n"
                + "</madamira_input>\n"
                + "";

        //            FileInputStream fstream =                 new FileInputStream(getServletContext().getRealPath("/database.txt"));
        //            FileWriter fw = new FileWriter(getServletContext().getRealPath("\\src\\java\\Ressource")+"\\SampleInputFile.xml");
        //                    FileWriter myWriter = new FileWriter("MIni_Projet_IL\\src\\java\\Traitement\\TraitementXml.java")) {
        //                myWriter.write("Files in Java might be tricky, but it is fun enough!");
        ////                    FileWriter fw = new FileWriter("web\\SampleOutputFile.xml")) {
        //                myWriter.write(Header);
        //                myWriter.write(inn);
        //                myWriter.write(footer);
        //                myWriter.close();
        BufferedWriter lecteurAvecBuffer = new BufferedWriter(new FileWriter(myFile));

        lecteurAvecBuffer.write(Header);
        lecteurAvecBuffer.write("        <in_seg id=\"SENT1\">");
        String temp = new String(inn);

        lecteurAvecBuffer.write(temp);

        lecteurAvecBuffer.write("</in_seg>\n");
        lecteurAvecBuffer.write(footer);
        lecteurAvecBuffer.close();

    }

    public static List ConvertToList(char[] caracterProc) {
        List<String> res = new ArrayList();
        for (int i = 0; i < caracterProc.length; i++) {
            res.add(caracterProc[i] + "");
        }
        return res;
    }

}
