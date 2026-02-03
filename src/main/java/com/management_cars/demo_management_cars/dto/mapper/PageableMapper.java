package com.management_cars.demo_management_cars.dto.mapper;

import com.management_cars.demo_management_cars.dto.pageable.PageableDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static <T, D> PageableDTO toDto(Page<T> page, java.util.function.Function<T, D> mapper) {
        PageableDTO dto = new PageableDTO();

        List<D> content = page.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());

        dto.setContent(content);
        dto.setFirst(page.isFirst());
        dto.setLast(page.isLast());
        dto.setNumber(page.getNumber());
        dto.setSize(page.getSize());
        dto.setNumberOfElements(page.getNumberOfElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements((int) page.getTotalElements());

        return dto;
    }
}
