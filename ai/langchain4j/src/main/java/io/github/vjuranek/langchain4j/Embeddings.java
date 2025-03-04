package io.github.vjuranek.langchain4j;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;

public class Embeddings {
    public static void main(String[] args) {
        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        TextSegment segment = TextSegment.from("just a test");
        Embedding embedding = embeddingModel.embed(segment).content();

        System.out.println(embedding.dimension());
        System.out.println(embedding.vectorAsList());
    }
}
