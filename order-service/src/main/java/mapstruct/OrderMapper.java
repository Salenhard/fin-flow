package mapstruct;

import dto.OrderRequestDto;
import dto.OrderResponseDto;
import entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order responseDtoToEntity(OrderResponseDto order);

    OrderResponseDto entityToResponseDto(Order order);
}
