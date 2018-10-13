package Source;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    static final int ALPHABET_SIZE = 26;
    public int number =0;
    public Node root ;
    List<Integer> array ;
    Dictionary(){
        root = new Node();
    }
    static public class Node{
        Node[] children = new Node[ALPHABET_SIZE];
        Node parent;
        Word word;
        boolean ending;
        Node(){
            this.ending = false;
            for (int i = 0; i < ALPHABET_SIZE; i++) children[i] = null;
            word = null;
            parent = null;
        }
    }


    public void insert(Word word){

        array = new ArrayList<Integer>();
        int length = word.word_target.length();
        int index;
        boolean flag = true;
        Node node = root;

        for (int num=0;num<length;num++){
            index =  word.word_target.charAt(num)-'a';
            if (index<0||index >=26) return;
        }
        for (int num=0;num<length;num++){
           index =  word.word_target.charAt(num)-'a';


           if (node.children[index]==null)
                node.children[index] = new Node();
            node.children[index].parent = node;
           node = node.children[index];

        }

        node.ending = true;
        node.word = word;
        number++;

    }

    public List<Word> crawlnode(Node node){
        if (node ==null) return null;
        List<Word> res = new ArrayList<Word>();
        if (node.ending==true){
            res.add(node.word);
        }

        for (int i=0;i<ALPHABET_SIZE;i++){
            if (crawlnode(node.children[i])==null) continue;
            res.addAll(crawlnode(node.children[i]));
        }
        return res;

    }

    public List<Word> Lookup(String word){
        List<Word> res = new ArrayList<Word>();
        Node node = root;
        for (int index=0;index<word.length();index++){
            int ix =word.charAt(index)-'a';
            if (ix<0 || ix>=26) return null;
            if (node.children[word.charAt(index)-'a'] ==null) return null;
            node = node.children[word.charAt(index)-'a'];
        }
        return crawlnode(node);
    }

    public Node get_node(Word word){
        int length = word.word_target.length();
        int index;

        Node node = root;
        for (int num=0;num<length;num++){
            index =  word.word_target.charAt(num)-'a';

            if (node.children[index]==null)
                return null;
            node = node.children[index];
        }
        return node;
    }


    public void delete(String word_){
        Word word = new Word(word_,"");
        Node node = get_node(word);
        if (node ==null) return;

        node.ending = false;
        while (node != root){
            int count =0;
            for (int i =0;i<ALPHABET_SIZE;i++){
                if (node.children[i]!=null) count++;
            }
            if (count >0) break;
            if (node.ending==true) break;
            node = node.parent;
        }

    }

}

