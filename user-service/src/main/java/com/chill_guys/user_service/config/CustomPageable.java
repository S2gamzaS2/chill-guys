package com.chill_guys.user_service.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CustomPageable extends PageableHandlerMethodArgumentResolver {

    @Override
    public @NonNull Pageable resolveArgument(@NonNull MethodParameter methodParameter,
                                                      ModelAndViewContainer mavContainer,
                                             @NonNull NativeWebRequest webRequest,
                                                      WebDataBinderFactory binderFactory
    ) {
        Pageable defaultPageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        int pageSize = defaultPageable.getPageSize();
        if (pageSize != 10 && pageSize != 30 && pageSize != 50) {
            pageSize = 10;
        }

        Sort sort = defaultPageable.getSort();
        if (sort.isUnsorted()) {
            sort = Sort.by(Direction.DESC, "createdAt");
        }

        return PageRequest.of(
            defaultPageable.getPageNumber(),
            pageSize,
            sort
        );
    }
}
