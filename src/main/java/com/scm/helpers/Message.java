package com.scm.helpers;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages") // Specify the MongoDB collection name
public class Message {
    @Id
    private String id; // Add an ID field for MongoDB documents
    private String content;
    
    @Builder.Default
    private MessageType type = MessageType.blue;
}
