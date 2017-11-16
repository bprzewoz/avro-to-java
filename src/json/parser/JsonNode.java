package json.parser;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-01.
 */
public class JsonNode {

    private String string;
    private JsonNodeType jsonNodeType;
    private LinkedList<JsonNode> membersList;

    public JsonNode(JsonNodeType jsonNodeType) {
        this(null, jsonNodeType);
    }

    public JsonNode(String string, JsonNodeType jsonNodeType) {
        this.string = string;
        this.jsonNodeType = jsonNodeType;
        this.membersList = new LinkedList<>();
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public JsonNodeType getJsonNodeType() {
        return jsonNodeType;
    }

    public void addMember(JsonNode jsonNode) {
        membersList.add(jsonNode);
    }

    public JsonNode getMember(int index) {
        if (!membersList.isEmpty()) {
            if (membersList.size() > index) {
                return membersList.get(index);
            }
        }
        return null;
    }

    public JsonNode getMember(String string) {
        if (!membersList.isEmpty()) {
            for (int i = 0; i < membersList.size(); i++) {
                JsonNode jsonNode = membersList.get(i);
                if (jsonNode.getString().equalsIgnoreCase(string)) {
                    return jsonNode;
                }
            }
        }
        return null;
    }

    public String getMemberValue(String string) {
        for (int i = 0; i < membersList.size(); i++) {
            JsonNode jsonNode = membersList.get(i);
            if (jsonNode.getString().equalsIgnoreCase(string)) {
                return jsonNode.membersList.get(1).getString();
            }
        }
        return null;
    }

    public void printTree(int depth) {
        printNode(depth);
        if (!membersList.isEmpty()) {
            for (int i = 0; i < membersList.size(); i++) {
                membersList.get(i).printTree(depth + 1);
            }
        }
    }

    public void printNode(int depth) {
        System.out.println(String.format("%s%s - %s", getTab(depth), string, jsonNodeType.toString()));
    }

    private String getTab(int depth) {
        String tab = "";
        for (int i = 0; i < depth; i++) {
            tab += "\t";
        }
        return tab;
    }
}
