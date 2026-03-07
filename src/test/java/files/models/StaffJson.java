package files.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffJson {
    private int id;
    private Integer managerId;
    private String role;
    private String name;
    private double salary;
}