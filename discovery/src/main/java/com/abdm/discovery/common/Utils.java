package com.abdm.discovery.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;

import static com.abdm.discovery.common.Constants.REQUEST_ID;

public class Utils {

    private Utils() {

    }

    public static JsonNode updateRequestId(JsonNode jsonNode, String callerRequestId) {
        ObjectNode mutableNode = (ObjectNode) jsonNode;
        mutableNode.put(REQUEST_ID, UUID.randomUUID().toString());
        ObjectNode respNode = (ObjectNode) mutableNode.get("resp");
        respNode.put(REQUEST_ID, callerRequestId);
        return jsonNode;

    }
}