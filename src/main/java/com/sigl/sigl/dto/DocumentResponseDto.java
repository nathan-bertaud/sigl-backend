package com.sigl.sigl.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DocumentResponseDto {
    private String documentNames;
    private String documentUploadDates;
    private String documentSemester;
    private Long id;
    private String documentAuthor;
    private String documentType;
    private String documentKeyword1;
    private String documentKeyword2;
    private String documentKeyword3;
    private String documentKeyword4;
    public DocumentResponseDto(String documentNames, String documentUploadDates, String semester, Long id, String documentAuthor, String documentType, String documentKeyword1, String documentKeyword2, String documentKeyword3, String documentKeyword4) {
        this.documentNames = documentNames;
        this.documentUploadDates = documentUploadDates;
        this.documentSemester = semester;
        this.id = id;
        this.documentAuthor = documentAuthor;
        this.documentType = documentType;
        this.documentKeyword1 = documentKeyword1;
        this.documentKeyword2 = documentKeyword2;
        this.documentKeyword3 = documentKeyword3;
        this.documentKeyword4 = documentKeyword4;

    }
}
