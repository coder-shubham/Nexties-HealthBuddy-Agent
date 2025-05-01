/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.data.segment.TextSegmentTransformer;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.azure.AzureOpenAiChatModelName;
import dev.langchain4j.model.azure.AzureOpenAiTokenizer;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */

@Configuration
public class HealthBuddyAgentConfiguration {


    @Autowired ChatLanguageModel chatLanguageModel;

    @Bean
    ChatMemoryProvider chatMemoryProvider(Tokenizer tokenizer) {
        return memoryId -> TokenWindowChatMemory.builder()
                .id(memoryId)
                .maxTokens(10000, tokenizer)
                .build();
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    EmbeddingStore<TextSegment> embeddingStore( EmbeddingModel embeddingModel, ResourceLoader resourceLoader, Tokenizer tokenizer)
            throws IOException {

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

//        Resource resource = resourceLoader.getResource("classpath:symptom-disease-train-dataset.json");
        Resource resource = resourceLoader.getResource("classpath:disease_symptoms_kb.json");

        File file = resource.getFile();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonArray = objectMapper.readTree(file);

        List<TextSegment> segments = new ArrayList<>();

        for (JsonNode node : jsonArray) {
            String text = node.get("symptoms").asText();
            String label = node.get("disease").asText();

            Metadata metadata = Metadata.from("disease", label);
            TextSegment segment = TextSegment.from(text, metadata);
            segments.add(segment);
        }

        // 3. Ingest segments into embedding store
        for (TextSegment segment : segments) {
            var embedding = embeddingModel.embed(segment.text()).content();
            embeddingStore.add(embedding, segment);
        }

        return embeddingStore;
    }


    @Bean
    ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {

        //Handling retrieval from Tool so we attach the embedding store to the retriever only if required
        return null;
    }


}
