package com.cropdata.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cropdata.value.OrderStatusVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderNumber;

	private LocalDate shippedDate;

	@NotNull(message = "Status cannot be null")
	private OrderStatusVo orderStatusVo;

	@Size(max = 500, message = "Comments cannot be more than 500 characters")
	private String comments;

	private Integer customerNumber;

	private String userName;
	private String password;

	@ElementCollection
	@CollectionTable(name = "rolesTab", joinColumns = @JoinColumn(referencedColumnName = "id"))
	private Set<String> roles;

	// private String status;
	// private String orderStatusVo;

	// @JsonIgnore

	// @OneToMany(mappedBy = "orderDTO", cascade = CascadeType.ALL, orphanRemoval =
	// true)
	private List<OrderDetailsDTO> orderDetails;

	@Override
	public String toString() {
		return "OrderDTO [orderStatusVo=" + orderStatusVo + "]";
	}

}
