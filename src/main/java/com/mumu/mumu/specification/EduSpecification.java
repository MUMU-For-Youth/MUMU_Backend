//package com.mumu.mumu.specification;
//
//import com.mumu.mumu.domain.Edu;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.List;
//
//public class EduSpecification {
//
//    public static Specification<Edu> hasRegionIn(List<String> regions) {
//        return (root, query, cb) -> regions == null ? null : root.get("eduRegion").in(regions);
//    }
//
//    public static Specification<Edu> hasFieldIn(List<String> fields) {
//        return (root, query, cb) -> fields == null ? null : root.get("field").in(fields);
//    }
//
//    public static Specification<Edu> hasStatusIn(List<String> statuses) {
//        return (root, query, cb) -> statuses == null ? null : root.get("recruitmentStatus").in(statuses);
//    }
//}
