package com.webStore.webStore.dto;

import com.webStore.webStore.model.ProductSpentTimeInStoreDate;
import com.webStore.webStore.model.ProductState;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {
    private long id;
    @NotBlank(message = "Field name name is Blank")
    @Size(min = 3)
    private String name;
    @PositiveOrZero(message = "Filed name price should be positive")
    private double price;
    private String photoUrl;
    @NotNull
    private ProductState productState;
    @NotNull
    @Past(message = "Field name arrivedStoreTime should be in past")
    private LocalDateTime arrivedAtStoreTime;
    @PastOrPresent(message = "Field name soldAtStoreTime should be in past or present")
    private LocalDateTime soldAtStoreTime;
    private ProductSpentTimeInStoreDate productSpentTimeInStoreDate;

    public ProductDTO setProductTimeSpentInStoreDate(int year, int month, int day) {
        this.productSpentTimeInStoreDate = new ProductSpentTimeInStoreDate(year, month, day);
        return this;
    }
}
