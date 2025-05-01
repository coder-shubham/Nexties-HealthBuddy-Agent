/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisOutputFormat;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.audio.AudioInputStream;
import com.microsoft.cognitiveservices.speech.audio.AudioOutputStream;
import com.microsoft.cognitiveservices.speech.audio.PushAudioInputStream;
import com.msreactor.hackathon.myhealthbuddy.configuration.AppConfig;

import jakarta.annotation.PostConstruct;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 01/05/25
 */

@Service
public class HealthBuddySpeechService {

    @Autowired
    private AppConfig appConfig;

    SpeechConfig speechConfig;

    public HealthBuddySpeechService() {

        this.speechConfig = null;
    }

    @PostConstruct
    public void init() throws Exception{
        speechConfig = SpeechConfig.fromSubscription(appConfig.getSpeechSubscriptionKey(), appConfig.getSpeechRegion());
    }

    public List<String> convertTextToSpeech(String text) {
        // Implement the logic to convert text to speech using the SpeechConfig
        // For example, you can use the SpeechSynthesizer class from the SDK
        // to perform the text-to-speech conversion.

        List<String> base64Chunks = new ArrayList<>();

        speechConfig.setSpeechSynthesisVoiceName("en-US-AvaNeural");
        speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Audio24Khz160KBitRateMonoMp3);

        // Output audio to stream
        ByteArrayOutputStream audioBuffer = new ByteArrayOutputStream();
        AudioOutputStream outputStream = AudioOutputStream.createPullStream();
        AudioConfig audioConfig = AudioConfig.fromStreamOutput(outputStream);

        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);

        SpeechSynthesisResult result = synthesizer.SpeakText(text);

        if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
            byte[] audioBytes = result.getAudioData();

            // Chunk and base64 encode
            int chunkSize = 18000; // or 4096, as per your use case

            for (int i = 0; i < audioBytes.length; i += chunkSize) {
                int end = Math.min(audioBytes.length, i + chunkSize);
                byte[] chunk = Arrays.copyOfRange(audioBytes, i, end);
                base64Chunks.add(Base64.getEncoder().encodeToString(chunk));
            }

            // Example output
            for (String chunk : base64Chunks) {
                System.out.println("Chunk: " + chunk);
            }
            System.out.println("Speech synthesized successfully.");


        } else if (result.getReason() == ResultReason.Canceled) {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(result);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you update the subscription info?");
            }
        }

        result.close();
        synthesizer.close();

        return base64Chunks;
    }

    public String covertSpeechToText(byte[] audioBytes) throws Exception{
        speechConfig.setSpeechRecognitionLanguage("en-US");

        // Create an audio stream from the byte array
//        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
        AudioInputStream audioInputStream = AudioInputStream.createPushStream();

        // Push bytes into the stream
        ((PushAudioInputStream) audioInputStream).write(audioBytes);

        AudioConfig audioConfig = AudioConfig.fromStreamInput(audioInputStream);

        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);

        Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
        SpeechRecognitionResult result = task.get();

        recognizer.close();

        if (result.getReason() == ResultReason.RecognizedSpeech) {
            return result.getText();
        } else {
            throw new RuntimeException("Speech not recognized: " + result.getReason());
        }
    }


}
