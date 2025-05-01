/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.tools;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.msreactor.hackathon.myhealthbuddy.model.SymptomDiseaseMapping;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.azure.AzureOpenAiChatModelName;
import dev.langchain4j.model.azure.AzureOpenAiTokenizer;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */

@Component
public class SymptomTool {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    EmbeddingModel embeddingModel;

    @Autowired
    EmbeddingStore<TextSegment> embeddingStore;

    @Tool(
            name = "GetSymptomInformation",
            value = "Get information about a specific or list of symptoms as which symptoms belong to which disease. "
                    + "Input should be in the format: 'symptom: <single or list of symptoms seperated by comma>'"
                    + "Output will be the details of the symptoms and its expected disease ."
    )
    public List<SymptomDiseaseMapping> getSymptomInformation(String symptomQuery) {

        try{
            // Logic to get information about the symptom
            // This is a placeholder implementation
            System.out.println("Fetching information for symptom: " + symptomQuery);

            int maxResults = 3;
            double minScore = 0.6;

            EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                    .embeddingStore(embeddingStore)
                    .embeddingModel(embeddingModel)
                    .maxResults(maxResults)
                    .minScore(minScore)
                    .build();

            Query query = Query.from(symptomQuery);

            List<Content> retrievedContent = retriever.retrieve(query);
            List<SymptomDiseaseMapping> diseases = new java.util.ArrayList<>();
            for(Content content : retrievedContent) {
               System.out.println("Content: " + content);
               if(content.textSegment() != null) {
                   Metadata metadata = content.textSegment().metadata();
                   System.out.println("Metadata: " + metadata);
                   SymptomDiseaseMapping disease = new SymptomDiseaseMapping(content.textSegment().text(),
                           metadata.getString("disease"));
                   diseases.add(disease);
               }

            }

            return diseases;

        }catch (Exception e){
            System.out.println("Error fetching information for symptom: " + symptomQuery + " - and trace is: " + Arrays.asList(e.getStackTrace()));
        }

        return null;
    }

}
