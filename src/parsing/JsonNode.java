package parsing;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-01.
 */
public class JsonNode {

    private String name;
    private NodeType nodeType;
    private LinkedList<JsonNode> members = new LinkedList<>();

    public JsonNode(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public JsonNode(String name, NodeType nodeType) {
        this.name = name;
        this.nodeType = nodeType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void addMember(JsonNode jsonNode) {
        if (jsonNode != null) {
            members.add(jsonNode);
        }
    }

    public void printTree(int depth) {
        System.out.println(String.format("%s%s, %s", getTab(depth), name, nodeType.toString()));
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
