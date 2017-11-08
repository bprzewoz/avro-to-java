package parser;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-01.
 */
public class JsonNode {

    private String name;
    private JsonNodeType jsonNodeType;
    private LinkedList<JsonNode> members = new LinkedList<>();

    public JsonNode(JsonNodeType jsonNodeType) {
        this.jsonNodeType = jsonNodeType;
    }

    public JsonNode(String name, JsonNodeType jsonNodeType) {
        this.name = name;
        this.jsonNodeType = jsonNodeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonNodeType getJsonNodeType() {
        return jsonNodeType;
    }

    public void setJsonNodeType(JsonNodeType jsonNodeType) {
        this.jsonNodeType = jsonNodeType;
    }

    public void addMember(JsonNode jsonNode) {
        members.add(jsonNode);
    }

    public void printTree(int depth) {
        System.out.println(String.format("%s%s, %s", getTab(depth), name, jsonNodeType.toString()));
        if (!members.isEmpty()) {
            for (int i = 0; i < members.size(); i++) {
                members.get(i).printTree(depth + 1);
            }
        }
    }

    private String getTab(int depth) {
        String tab = "";
        for (int i = 0; i < depth; i++) {
            tab += "\t";
        }
        return tab;
    }
}
