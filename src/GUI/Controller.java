package GUI;

import Source.Dictionary;
import Source.DictionaryManagement;
import Source.Word;
import Speech.TextToSpeech;

import Translator.Translator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    DictionaryManagement dictionaryManagement ;
    boolean audio_playing;
    public String current_word="";



    @FXML private TextField no1;
    @FXML private TextArea no2;
    @FXML private ListView list;

    @FXML
    public void initialize(){
        dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile();
        audio_playing = false;
        no1.setOnAction(e -> {
            // add your code to be run here
            translate();
        });

    }






    @FXML
    public void audio(){
        if (audio_playing) return;
        String word=no1.getText();
        if (no1.equals("")){
            return;
        }
        word = word.toLowerCase();
        TextToSpeech text = new TextToSpeech();
        text.setVoice("cmu-rms-hsmm");
        audio_playing = true;
        text.speak(word,2.0f,false,true);
        audio_playing = false;
    }

    @FXML
    public void delete(){
        String word =no1.getText();
        word = word.toLowerCase();
        dictionaryManagement.dictionary.delete(word);
        no1.setText("");
        no2.setText("");
        ObservableList<String> data = FXCollections.observableArrayList();
        list.setItems(data);
        translate();
    }


    @FXML
    public void translate(){

        String new_word =no1.getText();
        new_word = new_word.toLowerCase();
        Word word = dictionaryManagement.dictionaryLookup(new_word);
        if (word==null) return ;
        no2.setText(word.word_explain);
        }


    @FXML
    public void edit(){
        String word_explain = no2.getText();
        String word_target = no1.getText();
        word_target = word_target.toLowerCase();
        dictionaryManagement.dictionary.delete(word_target);
        Word word = new Word(word_target,word_explain);
        dictionaryManagement.dictionary.insert(word);
}


    @FXML
    public void choose(){
        ObservableList choose=list.getSelectionModel().getSelectedItems();
        if (choose.size()==0) return;

        no1.setText(choose.get(0).toString());
        translate();
    }
    @FXML
    public void suggestion(){

        String word=no1.getText();
        word = word.toLowerCase();
        List<String> suggest = dictionaryManagement.autocompele(word);
        if (list.getItems().size()!=0)
            if (!list.getItems().get(0).toString().equals(word)) no2.setText("");
        if (dictionaryManagement.autocompele(word)==null) return;
        ObservableList<String> data = FXCollections.observableArrayList(suggest);
        data = FXCollections.observableArrayList(dictionaryManagement.autocompele(word));
        list.setItems(data);


    }





}
