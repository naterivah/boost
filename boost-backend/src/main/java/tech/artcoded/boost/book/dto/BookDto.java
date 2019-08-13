package tech.artcoded.boost.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BookDto {
    private String title;
    private String category;
    private String description;
    private byte[] cover;
    private String fileName;
    private String contentType;
}