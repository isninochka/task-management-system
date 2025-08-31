package isaeva.notificationservice.mapper;

import isaeva.notificationservice.dto.NotificationRequest;
import isaeva.notificationservice.dto.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "status", constant = "SENT")
    NotificationResponse toResponse(NotificationRequest notificationRequest);
}
