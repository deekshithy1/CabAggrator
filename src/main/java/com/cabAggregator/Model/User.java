package com.cabAggregator.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user") // ✅ "collection" is more standard than "value"
public class User {

    @Id
    private String id;

    private String name;

    private String mobileNumber; // ✅ field names in camelCase

    private String email;

    private String password; // ✅ lowercase — matches Spring Security expectations

    private List<Ride> rides; // ✅ consistent naming
}
