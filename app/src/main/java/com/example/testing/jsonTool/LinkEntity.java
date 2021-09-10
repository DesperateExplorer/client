package com.example.testing.jsonTool;

/**
 * [{
 * 	"start_index": 0,
 * 	"end_index": 1,
 * 	"entity": "中国",
 * 	"entity_type": "",
 * 	"entity_url": "http://edukb.org/knowledge/0.1/instance/geo#-33070e7e6be2460dd9ddcbce5d44eb28"
 * }, {
 * 	"start_index": 4,
 * 	"end_index": 5,
 * 	"entity": "亚洲",
 * 	"entity_type": "地理概念",
 * 	"entity_url": "http://edukb.org/knowledge/0.1/instance/geo#-0941b9a2df3e5f88d308098eda876fc2"
 * }, {
 * 	"start_index": 6,
 * 	"end_index": 7,
 * 	"entity": "东部",
 * 	"entity_type": "区域",
 * 	"entity_url": "http://edukb.org/knowledge/0.1/instance/geo#-785433ccb5b2698b9c4c85b4fa9cc4f6"
 * },
 * // ...
 * ]
 */

public class LinkEntity {
    private String start_index;
    private String end_index;
    private String entity;
    private String entity_type;
    private String entity_url;

    LinkEntity(String start_index, String end_index,String entity,String entity_type,String entity_url)
    {
        this.start_index = start_index;
        this.end_index = end_index;
        this.entity = entity;
        this.entity_url = entity_url;
        this.entity_type = entity_type;
    }

    public String getEnd_index() {
        return end_index;
    }

    public String getEntity() {
        return entity;
    }

    public String getEntity_type() {
        return entity_type;
    }

    public String getEntity_url() {
        return entity_url;
    }

    public String getStart_index() {
        return start_index;
    }
}
