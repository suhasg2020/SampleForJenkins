//package org.aia.testcases.ces;
//import edu.cmu.sphinx.api.Configuration;
//import edu.cmu.sphinx.api.LiveSpeechRecognizer;
//import java.io.IOException;
//
//public class SpeechToText {
//    public static void main(String[] args) throws Exception {
//        // Create a configuration object
//        Configuration configuration = new Configuration();
//
//        // Set path to acoustic model
//        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
//        
//        // Set path to dictionary
//        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
//        
//        // Set language model
//        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
//
//        // Create a live speech recognizer with the configuration
//        try (LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration)) {
//            // Start recognition
//            recognizer.startRecognition(true);
//            System.out.println("Speech recognition started...");
//
//            // Get the recognized speech
//            while (true) {
//                System.out.println("Speech: " + recognizer.getResult().getHypothesis());
//            }
//        }
//    }
//}
