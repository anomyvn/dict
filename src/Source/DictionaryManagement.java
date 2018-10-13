package Source;

import Source.Dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {

    public Dictionary dictionary;

    public DictionaryManagement(){
        dictionary = new Dictionary();
    }

    public void InsertfromCommandline(){
        System.out.println("Nhập vào bàn phím số lượng từ vựng");
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        for (int i=0;i<n;i++){
            String word_target = reader.next();
            String worrd_meaning = reader.next();
            Word word = new Word(word_target,worrd_meaning);
            dictionary.insert(word);

        }
    }
    public void showAllWords(){
        System.out.print(String.format("%1$-20s","NO"));
        System.out.print(String.format("%1$-20s","|English"));
        System.out.print(String.format("%1$-20s","|Vietnammese"));
        System.out.println();
        List<Word> word = dictionary.crawlnode(dictionary.root);
        for (Integer i=0;i<word.size();i++){
            System.out.print(String.format("%1$-20s",i));
            System.out.print(String.format("%1$-20s","|"+word.get(i).word_target));
            System.out.print(String.format("%1$-20s","|"+word.get(i).word_explain));
            System.out.println();
        }
    }

    public void insertFromFile(){
        File file = new File(".\\src\\Source\\dictionaries.txt");
        try (Scanner scanner = new Scanner(file)) {
            int nu=0;

            while (scanner.hasNext()) {
                nu++;
                String mean = "";
                String meanings = "";
                String sound = "";
                String s = scanner.nextLine();
                s = s + "|*";
                String word = s.substring(0, s.indexOf('#', 0));
                int fi = s.indexOf('[');
                int la = s.indexOf(']');
                if (fi > 0) {
                    sound = s.substring(fi + 1, la);
                }


                int first =0;
                int last = 0;

                String out = "";
                first = s.lastIndexOf('#');

                int f = 0;
                if (s.indexOf('*') == s.length() - 1) {
                    f = first;
                    while (true) {
                        f = s.indexOf('-', f + 1);
                        int l = s.indexOf('|', f);
                        if (f < 0 || l < 0) break;
                        out = out + s.substring(f, l) + '\n';
                    }
                }
                else
                    while (true) {
                        first = s.indexOf('*', first);
                        last = s.indexOf('|', first);
                        if (last < 0 || first < 0) break;
                        out = out + s.substring(first, last) + '\n';
                        int next = s.indexOf('*', first + 1);
                        f = first;
                        while (true) {
                            f = s.indexOf('-', f + 1);
                            int l = s.indexOf('|', f);
                            if (f < 0 || l < 0 || f >= next) break;
                            out = out + s.substring(f, l) + '\n';
                        }
                        first = next;


                    }
                Word word1 = new Word(word,sound+'\n'+out);
                dictionary.insert(word1);



            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public List<String> autocompele(String word) {

        List<String> res = new ArrayList<String>();
        if (word.length()<=1) return res;
        List<Word> result = dictionary.Lookup(word);
        if (result==null) return null;
        for (int i=0;i<result.size();i++)
            res.add(result.get(i).word_target);
        return res;
    }

    public void dictionaryExportToFile(){
        try {
            FileWriter file = new FileWriter("export.txt");
            List<Word> lists=dictionary.crawlnode(dictionary.root);

            for (int i=0;i<lists.size();i++){
                file.write(lists.get(i).word_target+'\t'+lists.get(i).word_explain+'\n');
            }
            file.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Word dictionaryLookup(String target){
        Word word = new Word(target,"");
        if (dictionary.get_node(word)==null) return null;
        return(dictionary.get_node(word).word);
   }
    
}
