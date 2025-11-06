package org.example.graph;

import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;

public class GraphLoader {

    public static class GraphMeta {
        public int source;
        public String weightModel;
    }

    public static class GraphData {
        public Graph graph;
        public GraphMeta meta;

        public GraphData(Graph graph, GraphMeta meta) {
            this.graph = graph;
            this.meta = meta;
        }
    }

    public static GraphData loadFromJson(String path) throws IOException {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(new FileReader(path), JsonObject.class);

        int n = root.get("n").getAsInt();
        Graph g = new Graph(n);

        JsonArray edges = root.getAsJsonArray("edges");
        for (JsonElement e : edges) {
            JsonObject obj = e.getAsJsonObject();
            int from = obj.get("u").getAsInt();
            int to = obj.get("v").getAsInt();
            double w = obj.get("w").getAsDouble();
            g.addEdge(from, to, w);
        }

        GraphMeta meta = new GraphMeta();
        if (root.has("source")) meta.source = root.get("source").getAsInt();
        if (root.has("weight_model")) meta.weightModel = root.get("weight_model").getAsString();

        return new GraphData(g, meta);
    }
}
