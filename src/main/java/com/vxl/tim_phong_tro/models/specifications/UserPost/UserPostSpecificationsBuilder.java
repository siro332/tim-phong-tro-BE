package com.vxl.tim_phong_tro.models.specifications.UserPost;

import com.vxl.tim_phong_tro.models.entities.UserPost;
import com.vxl.tim_phong_tro.models.specifications.SearchCriteria;
import com.vxl.tim_phong_tro.models.specifications.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserPostSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public UserPostSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    // API

    public final UserPostSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final UserPostSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<UserPost> build() {
        if (params.size() == 0)
            return null;

        Specification<UserPost> result = new UserPostSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new UserPostSpecification(params.get(i)))
                    : Specification.where(result).and(new UserPostSpecification(params.get(i)));
        }

        return result;
    }

    public final UserPostSpecificationsBuilder with(UserPostSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final UserPostSpecificationsBuilder with(SearchCriteria criteria) {
        params.add(criteria);
        return this;
    }

}
