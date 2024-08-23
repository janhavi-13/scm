package com.scm.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "socialLinks")
public class SocialLink {
    @Id
    private String id;
    private String link;
    private String title;

    private String contactId; // Store the ID of the related Contact
}
