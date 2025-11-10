package org.example.graph;

import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;

public class GraphLoader {

    public static class GraphMeta {
        public int source = 0;
        public String weightModel = "edge";
    }

    public static class GraphData {
        public final Graph graph;
        public final GraphMeta meta;
        public GraphData(Graph graph, GraphMeta meta) {
            this.graph = graph;
            this.meta = meta;
        }
    }

    public static GraphData loadFromJson(String path) throws IOException {
        try (FileReader fr = new FileReader(path)) {
            JsonObject root = JsonParser.parseReader(fr).getAsJsonObject();
            int n = root.get("n").getAsInt();
            Graph g = new Graph(n);
            JsonArray edges = root.getAsJsonArray("edges");
            for (JsonElement e : edges) {
                JsonObject obj = e.getAsJsonObject();
                int u = obj.get("u").getAsInt();
                int v = obj.get("v").getAsInt();
                double w = obj.get("w").getAsDouble();
                g.addEdge(u, v, w);
            }
            GraphMeta meta = new GraphMeta();
            if (root.has("source")) meta.source = root.get("source").getAsInt();
            if (root.has("weight_model")) meta.weightModel = root.get("weight_model").getAsString();
            return new GraphData(g, meta);
        }
    }
}
