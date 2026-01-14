
package com.userservice.dto.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    private Integer userId;
    private String fullName;
}
